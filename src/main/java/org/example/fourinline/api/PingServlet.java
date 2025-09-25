package org.example.fourinline.api;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/api/ping")
public class PingServlet extends HttpServlet
{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {
        resp.setContentType("text/plain; charset=UTF-8");

        String msg = req.getParameter("msg");
        if (msg == null || msg.isBlank()) {
            msg = "check the api connection";
        }

        resp.getWriter().println("OK");
        resp.getWriter().println("ECHO=" + msg);
        resp.getWriter().println("TIME=" + System.currentTimeMillis());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {

        doGet(req, resp);
    }
}
