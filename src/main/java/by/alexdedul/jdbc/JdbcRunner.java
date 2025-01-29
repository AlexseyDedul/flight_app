package by.alexdedul.jdbc;

import by.alexdedul.jdbc.dao.FlightDao;
import by.alexdedul.jdbc.dao.TicketDao;
import by.alexdedul.jdbc.entity.Flight;
import by.alexdedul.jdbc.entity.FlightStatus;

import java.time.LocalDateTime;

public class JdbcRunner {
    public static void main(String[] args) {
        FlightDao flightDao = FlightDao.getInstance();

        Flight flight = new Flight(
                null,
                "1252",
                LocalDateTime.of(2024, 12, 1, 0, 0),
                1,
                LocalDateTime.of(2024, 12, 3, 0, 0),
                2,
                1,
                FlightStatus.BOARDING
        );

//        System.out.println(flightDao.save(flight));
        System.out.println(flightDao.findById(1L));

        TicketDao ticketDao = TicketDao.getInstance();
//        Ticket ticket = new Ticket();
//        ticket.setId(5L);
//        ticket.setPassportNo("123241r1");
//        ticket.setPassengerName("Pavel");
//        ticket.setFlightId(1L);
//        ticket.setSeatNo("14");
//        ticket.setCost(BigDecimal.valueOf(12.43));

//        System.out.println(ticketDao.save(ticket));
//        System.out.println(ticketDao.delete(4L));

//        System.out.println(ticketDao.findAll());
        System.out.println(ticketDao.findById(5L));
//        System.out.println(ticketDao.update(ticket));
//        System.out.println(ticketDao.findById(5L));

//        TicketFilter ticketFilter = new TicketFilter(null, null, 1, 0);
//        System.out.println(ticketDao.findAll(ticketFilter));
//
//        FlightDao flightDao = FlightDao.getInstance();
//
//        System.out.println(flightDao.findAll());
    }

}
