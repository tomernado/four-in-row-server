package org.example.fourinline.api;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.example.fourinline.core.GameManager;

@WebServlet(name = "Join", urlPatterns = "/api/join")
public class Join extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {
        String code = req.getParameter("code");
        String playerId = req.getParameter("playerId");

        if (code == null || code.isEmpty() || playerId == null || playerId.isEmpty())
        {
            resp.setStatus(400);
            resp.setContentType("text/plain;charset=UTF-8");
            resp.getWriter().write("FAIL: missing params");
            return;
        }

        boolean ok = GameManager.get().joinGame(code,playerId);

        resp.setContentType("text/plain;charset=UTF-8");
        resp.getWriter().write(ok?"OK":"FAIL");
    }

}