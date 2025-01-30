package by.alexdedul.jdbc.servlet;

import by.alexdedul.jdbc.service.FlightService;
import by.alexdedul.jdbc.utlis.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/content")
public class ContentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final FlightService flightService = FlightService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var flights = flightService.findAll();
        req.setAttribute("flights", flights);

        req.getRequestDispatcher(JspHelper.getPath("content")).forward(req, resp);
    }
}
