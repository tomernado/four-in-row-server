package org.example.fourinline.api;
import jakarta.servlet.*;
import jakarta.servlet.http.*;


import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.fourinline.core.GameManager;
import org.example.fourinline.core.MoveResult;

import java.io.IOException;

@WebServlet(name = "Move", urlPatterns = "/api/move")

   public class Move extends HttpServlet
{
    @Override
    protected  void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {
        String code = req.getParameter("code");
        String playerId = req.getParameter("playerId");
        String colStr = req.getParameter("col");
        int col;
        try
        {
            col = Integer.parseInt(colStr);
        }
        catch (Exception e)
        {
            col = -1;
        }

        MoveResult mr = GameManager.get().makeMove(code,playerId,col);

        resp.setContentType("text/plain;charset=UTF-8");
        switch (mr.status)
        {
            case OK:
                resp.getWriter().printf("OK row=%d col=%d next=%c status=%s%n", mr.row, mr.col, mr.nextTurn, mr.gameStatus);
                break;

            case WIN:
                resp.getWriter().printf("WIN row=%d col=%d winner=%c status=%s%n", mr.row, mr.col, mr.winner, mr.gameStatus);
                break;

            case DRAW:
                resp.getWriter().printf("DRAW row=%d col=%d status=%s%n", mr.row, mr.col, mr.gameStatus);
                break;

                default:
                    resp.getWriter().printf("ERROR %s status=%s%n", mr.status, mr.gameStatus);
        }

    }




}
