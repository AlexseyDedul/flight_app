package by.alexdedul.jdbc.service;

import by.alexdedul.jdbc.dao.TicketDao;
import by.alexdedul.jdbc.dto.TicketDto;
import by.alexdedul.jdbc.entity.Ticket;

import java.util.List;
import java.util.stream.Collectors;

public class TicketService {
    private static final TicketService INSTANCE = new TicketService();
    private final TicketDao ticketDao = TicketDao.getInstance();

    public List<TicketDto> findTicketsByFlightId(Long flightId) {
        return ticketDao.findAllByFlightId(flightId)
                .stream()
                .map(ticket ->
                        new TicketDto(
                                ticket.getId(),
                                ticket.getFlight().getId(),
                                ticket.getSeatNo()
                        )
                ).collect(Collectors.toList());
    }

    public static TicketService getInstance() {
        return INSTANCE;
    }

    private TicketService() {}
}
