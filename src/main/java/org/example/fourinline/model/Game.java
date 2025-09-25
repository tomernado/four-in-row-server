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
    public void addSecondPlayer(String secondPlayerId)
    {
     if (secondPlayerId == null || secondPlayerId.isEmpty())
     {
         throw new IllegalArgumentException("secondPlayerId cannot be null or empty");
     }
     if (status != GameStatus.WAITING)
     {
         throw  new IllegalStateException("No open game yet");
     }
     if (this.p2 != null)
     {
         throw new IllegalStateException("Second player already exists");
     }
     if (this.p1 != null && this.p1.id.equals(secondPlayerId))
     {
         throw new IllegalStateException("Same player cannot join twice");
     }

     this.p2 = new Player(secondPlayerId, 'Y');
     this.status =GameStatus.IN_PROGRESS;


    }

    public boolean hasPlayer(String playerId)
    {
        return (p1 != null && p1.id.equals(playerId)) || (p2 != null && p2.id.equals(playerId));
    }
    public char symbolOf (String playerId)
    {
    if (p1 != null && p1.id.equals(playerId)) return p1.symbol;
    if (p2 != null && p2.id.equals(playerId)) return p2.symbol;
    return '?';
    }

    public boolean isColumnFull(int col)
    {
        return board[0][col] != EMPTY;
    }

    public  int dropDisc(char symbol,int col)
    {
        if (col < 0 || col >= COLS) return -1;
        if (isColumnFull(col)) return -1;

        for (int r= ROWS-1; r >= 0; r--)
        {
            if (board[r][col] == EMPTY)
            {
                board[r][col] = symbol;
                return r;
            }
        }
        return -1;
    }


    private int countDirection(int r,int c,int dr,int dc,int s)
    {
        int count = 0;
        int rr= r;
        int cc = c;
        while (rr >= 0 && rr<ROWS && cc >= 0 && cc<COLS && board[rr][cc] == s)
        {
            count++;
            rr+=dr;
            cc+=dc;
        }
        return count;
    }

    public boolean checkWin(int r, int c)
    {
        char s = board[r][c];
        if (s == EMPTY) return false;

        if (countDirection(r,c,0,1,s) + countDirection(r,c,0,-1,s) -1 >= 4) return true;

        if (countDirection(r,c,1,0,s)+countDirection(r,c,-1,0,s) -1 >= 4) return true;

        if (countDirection(r,c,1,1,s)+countDirection(r,c,-1,-1,s) -1 >= 4) return true;

        if (countDirection(r,c,1,-1,s)+countDirection(r,c,-1,1,s) -1 >= 4) return true;

        return false;
    }

    public boolean isDraw()
    {
        for (int c = 0; c < COLS; c++)
        {
            if (!isColumnFull(c))
            {
                return false;
            }
        }
        return true;
    }

 public org.example.fourinline.core.GameSnapshot snapshotFor(String playerId)
 {
    synchronized (this)
    {
        synchronized (this) {
            org.example.fourinline.core.GameSnapshot s = new org.example.fourinline.core.GameSnapshot();
            s.code = this.code;
            s.status = this.status.name();
            s.board = org.example.fourinline.core.GameSnapshot.copyBoard(this.board);

            s.currentTurn = (this.status == GameStatus.IN_PROGRESS) ? this.nextTurn : '-';

            if (this.hasPlayer(playerId)) {
                s.yourSymbol = this.symbolOf(playerId);
            } else {
                s.yourSymbol = '-';
            }

            s.yourTurn = (s.currentTurn != '-' && s.yourSymbol == s.currentTurn);

            s.winner = (this.status == GameStatus.FINISHED) ? this.winner : null;
            s.draw = (this.status == GameStatus.FINISHED && this.winner == null);

            s.movesCount = countDisc();
            s.error = null;
            return s;
        }
    }


 }
    private int countDisc()
    {
        int cnt = 0;
        for (int r = 0; r < board.length; r++)
        {
            for (int c = 0; c < board[0].length; c++)
            {
                char ch = board[r][c];
                if (ch == 'R' || ch == 'Y') cnt++;
            }
        }
        return cnt;
    }
}







