package org.example.fourinline.api;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.example.fourinline.core.GameManager;

@WebServlet(name = "Create",urlPatterns = "/api/create")

public class Create extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {
        String code = req.getParameter("code");
        String playerId = req.getParameter("playerId");
        boolean ok = GameManager.get().createGame(code,playerId);

        resp.setContentType("text/plain;charset=UTF-8");
        resp.getWriter().println(ok?"ok":"failed");
    }
}
