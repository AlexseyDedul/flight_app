package by.alexdedul.jdbc.servlet;

import by.alexdedul.jdbc.service.TicketService;
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
        try(var writer = resp.getWriter()){
            writer.write("<h1>Purchased tickets:</h1>");
            writer.write("<ul>");
            ticketService.findTicketsByFlightId(flightId)
                    .stream()
                    .forEach(ticketDto -> writer.write(
                            """
                                <li>%s</li>
                                """.formatted(ticketDto.seatNo())
                    ));
            writer.write("</ul>");
        }

    }
}
