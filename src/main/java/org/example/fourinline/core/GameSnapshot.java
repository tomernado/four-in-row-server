package org.example.fourinline.core;

import org.example.fourinline.model.GameStatus;

public class GameSnapshot
{
    public String code;
    public String status;
    public char[][] board;
    public char currentTurn;
    public char yourSymbol;
    public boolean yourTurn;
    public Character winner;
    public boolean draw;
    public int movesCount;
    public String error;


    public String toPlainText()
    {
        if (error != null)
        {
            return "ERROR\n" + error + "\n";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("OK\n");
        sb.append("CODE: ").append(code).append('\n');
        sb.append("STATUS: ").append(status).append('\n');
        sb.append("YOU: ").append(yourSymbol).append('\n');
        sb.append("TURN: ").append(currentTurn).append('\n');
        sb.append("WINNER: ").append(winner == null ? "-" : winner).append('\n');
        sb.append("DRAW: ").append(draw).append('\n');
        sb.append("MOVES: ").append(movesCount).append('\n');
        sb.append("BOARD:\n");
        for (char[] row : board)
        {
            sb.append(new String(row)).append('\n');
        }
        return sb.toString();
    }


    public static char[][] copyBoard(char[][] src)
    {
        char[][] dst = new char[src.length][];
        for (int i = 0; i < src.length; i++)
        {
            dst[i] = java.util.Arrays.copyOf(src[i], src[i].length);
        }
        return dst;
    }
}
