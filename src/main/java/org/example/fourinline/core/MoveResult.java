package org.example.fourinline.core;

import org.example.fourinline.api.Move;
import org.example.fourinline.model.GameStatus;

public class MoveResult
{
public enum Status
{
    OK,
    WIN,
    DRAW,
    NOT_YOUR_TURN,
    INVALID_COLUMN,
    COLUMN_FULL,
    GAME_NOT_FOUND,
    NOT_IN_GAME,
    GAME_NOT_IN_PROGRESS
}
public final Status status;
public final int row;
public final int col;
public final char nextTurn;
public final GameStatus gameStatus;
public final Character winner;

private MoveResult(Status s, int r, int c, char next, GameStatus gs, Character w)
    {
        this.status = s;
        this.row = r;
        this.col = c;
        this.nextTurn = next;
        this.gameStatus = gs;
        this.winner = w;
    }
    public static MoveResult ok(int r, int c,char next,GameStatus gs)
    {
        return new MoveResult(Status.OK,r,c,next,gs,null);
    }
    public static MoveResult win(int r,int c,Character w,GameStatus gs)
    {
        return new MoveResult(Status.WIN,r,c,'\0',gs,null);
    }
    public static MoveResult draw(int r, int c, GameStatus gs)
    {
        return new MoveResult(Status.DRAW, r, c, '\0', gs, null);
    }

    public static MoveResult error(Status s, GameStatus gs)
    {
        return new MoveResult(s, -1, -1, '\0', gs, null);
    }

}
