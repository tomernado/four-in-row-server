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
        String playetId = req.getParameter("playetId");

        boolean ok = GameManager.get().joinGame(code,playetId);

        resp.setContentType("text/plain;charset=UTF-8");
        resp.getWriter().write(ok?"OK":code);


    }

}