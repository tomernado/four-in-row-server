package org.example.fourinline.core;
import org.example.fourinline.model.Game;
import org.example.fourinline.model.GameStatus;

import java.util.concurrent.ConcurrentHashMap;

public class GameManager
{
    private static final GameManager instance = new GameManager();

    public static GameManager get()
    {
        return instance;
    }


    private GameManager() {}

    private final ConcurrentHashMap<String, Game> games = new ConcurrentHashMap<>();


    public boolean createGame (String code,String playerId)
    {
     if (code == null || playerId == null || code.isEmpty() || playerId.isEmpty())
     {
         return false;
     }
     Game g = new Game(code,playerId);

     return games.putIfAbsent(code,g) == null;
    }


    public Game getGame (String code)
    {
        return games.get(code);
    }

    public boolean joinGame (String code,String playerId)
    {
        if (code == null || playerId == null || code.isEmpty() || playerId.isEmpty())
        {
            return false;
        }
        Game g = games.get(code);
        if (g == null)
        {
            return false;
        }
        synchronized (g)
        {
            try
            {
                g.addSecondPlayer(playerId);
                return true;
            }
            catch (IllegalStateException e)
            {
                return false;
            }
        }
    }

    public MoveResult makeMove(String code,String playerId,int col)
    {
        if (code == null || code.isBlank())
        {
            return MoveResult.error(MoveResult.Status.GAME_NOT_FOUND, GameStatus.WAITING);
        }
        if (playerId == null || playerId.isBlank())
        {
            return MoveResult.error(MoveResult.Status.NOT_IN_GAME, GameStatus.WAITING);
        }
        Game g = games.get(code);
        if (g==null)
        {
            return MoveResult.error(MoveResult.Status.GAME_NOT_FOUND, GameStatus.WAITING);
        }
        synchronized (g)
        {
            if (g.status != GameStatus.IN_PROGRESS)
            {
                return MoveResult.error(MoveResult.Status.GAME_NOT_IN_PROGRESS, g.status);
            }

            if (!g.hasPlayer(playerId))
            {
                return MoveResult.error(MoveResult.Status.NOT_IN_GAME,g.status);
            }

            char sym = g.symbolOf(playerId);
            if (sym != g.nextTurn)
            {
                return MoveResult.error(MoveResult.Status.NOT_YOUR_TURN,g.status);
            }
            if (col < 0 || col >=Game.COLS)
            {
                return MoveResult.error(MoveResult.Status.INVALID_COLUMN,g.status);
            }

            if (g.isColumnFull(col))
            {
                return MoveResult.error(MoveResult.Status.COLUMN_FULL,g.status);
            }

            int row = g.dropDisc(sym,col);
            if (row < 0)
            {
                return MoveResult.error(MoveResult.Status.COLUMN_FULL,g.status);
            }

            boolean won = g.checkWin(row,col);
            if (won)
            {
                g.status = GameStatus.FINISHED;
                g.winner = sym;
                return MoveResult.win(row,col,sym,g.status);
            }
            if (g.isDraw())
            {
                g.status = GameStatus.FINISHED;
                g.winner = null;
                return MoveResult.draw(row,col,g.status);
            }

            g.nextTurn = (sym == 'R') ? 'Y' : 'R';
            return MoveResult.ok(row,col,sym,g.status);
        }
    }
    public GameSnapshot getSnapshot(String code, String playerId)
    {
        Game g = getGame(code);
        if (g == null)
        {
            GameSnapshot s = new GameSnapshot();
            s.error = "Game not found";
            return s;
        }
        return g.snapshotFor(playerId);
    }
}






