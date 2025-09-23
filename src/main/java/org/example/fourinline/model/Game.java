package org.example.fourinline.model;


public class Game
{
    public static final int ROWS = 6;
    public static final int COLS = 7;
    public static final char EMPTY = '.';

    public final String code;
    public final char[][] board;
    public GameStatus status;
    public Player p1;
    public Player p2;
    public char nextTurn;
    public Character winner;


    public Game(String code, String firstPlayerId)
    {
        this.code = code;
        this.board = new char[ROWS][COLS];

        for (int r = 0; r < ROWS; r++)
        {
            for (int c = 0; c < COLS; c++)
            {
                board[r][c] = EMPTY;
            }
        }

        this.p1 = new Player(firstPlayerId, 'R');
        this.p2 = null;
        this.status = GameStatus.WAITING;
        this.nextTurn = 'R';
        this.winner = null;
    }
}

public void addSecondPlayer(String secondPlayerId)
{
    if ( this.p2 != null)
    {
        throw new IllegalStateException("Second player already set");
    }
    if (this.p1 != null && this.p1.id.equals(secondPlayerId))
    {
        throw new IllegalStateException("Same player cannot join twice");
    }
    this.p2 = new Player(secondPlayerId, 'Y');
}

public boolean hasPlayer(String playerId)
{
    return (p1 != null && p1.id.equals(playerId)) || (p2 != null && p2.id.equals(playerId));
}





