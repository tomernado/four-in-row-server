package org.example.fourinline.api;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.example.fourinline.core.GameManager;
import org.example.fourinline.core.GameSnapshot;

import java.io.IOException;

@WebServlet(name = "State", urlPatterns = {"/api/state"})
public class State extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {
        String code = req.getParameter("code");
        String playerId = req.getParameter("playerId");

        resp.setContentType("text/plain;charset=UTF-8");

        if (code ==null || code.isBlank())
        {
            resp.getWriter().println("Invalid code"); return;
        }
        if (playerId == null  || playerId.isBlank())
        {
            resp.getWriter().println("Invalid playerId"); return;
        }
        GameSnapshot snap = GameManager.get().getSnapshot(code,playerId);
        resp.getWriter().println(snap.toPlainText());

    }

}