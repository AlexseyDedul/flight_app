package by.alexdedul.jdbc.servlet;

import by.alexdedul.jdbc.service.TicketService;
import by.alexdedul.jdbc.utlis.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet("/tickets")
public class TicketServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TicketService ticketService = TicketService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        Long flightId = Long.valueOf(req.getParameter("flightId"));

        req.setAttribute("tickets", ticketService.findTicketsByFlightId(flightId));

        req.getRequestDispatcher(JspHelper.getPath("tickets")).forward(req, resp);

    }
}
