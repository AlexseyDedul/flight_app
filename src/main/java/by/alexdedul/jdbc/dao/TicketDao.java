package by.alexdedul.jdbc.dao;

import by.alexdedul.jdbc.dto.TicketFilter;
import by.alexdedul.jdbc.entity.Ticket;
import by.alexdedul.jdbc.exception.DaoException;
import by.alexdedul.jdbc.utlis.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TicketDao implements Dao<Long, Ticket>{
    private final static TicketDao INSTANCE = new TicketDao();

    private final FlightDao flightDao = FlightDao.getInstance();

    private final static String SAVE_SQL = """
                                            insert into ticket (passport_no, passenger_name, flight_id, seat_no, cost)
                                            values (?, ?, ?, ?, ?)
                                            """;

    private final static String DELETE_SQL = """
                                            DELETE FROM ticket 
                                            WHERE id = ?
                                            """;

    private final static String UPDATE_SQL = """
                                            UPDATE ticket 
                                            SET passport_no = ?, 
                                                passenger_name = ?, 
                                                flight_id = ?, 
                                                seat_no = ?, 
                                                cost = ?
                                            WHERE id = ?
                                            """;

    private final static String FIND_ALL_SQL = """
                                            SELECT t.id, t.passport_no, t.passenger_name, t.flight_id, t.seat_no, t.cost,
                                            f.id, f.flight_no, f.departure_date, f.departure_airport_code, f.arrival_date, f.arrival_airport_code, f.aircraft_id, f.status
                                            FROM ticket t
                                            JOIN flight f ON f.id = t.flight_id 
                                            """;

    private final static String FIND_BY_ID_SQL = FIND_ALL_SQL + """
                                            WHERE t.id = ?
                                            """;

    private final static String FIND_BY_FLIGHT_ID_SQL = FIND_ALL_SQL + """
                                            WHERE t.flight_id = ?
                                            """;

    public List<Ticket> findAllByFlightId(Long flightId) {
        try(Connection connection = ConnectionManager.get();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_FLIGHT_ID_SQL)){
            statement.setLong(1, flightId);
            List<Ticket> tickets = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                tickets.add(buildTicket(resultSet));
            }

            return tickets;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean update(Ticket ticket) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(UPDATE_SQL )) {
            statement.setString(1, ticket.getPassportNo());
            statement.setString(2, ticket.getPassengerName());
            statement.setLong(3, ticket.getFlight().getId());
            statement.setString(4, ticket.getSeatNo());
            statement.setBigDecimal(5, ticket.getCost());

            statement.setLong(6, ticket.getId());

            return statement.executeUpdate() > 0;
        }catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public List<Ticket> findAll(){
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_SQL)) {
            List<Ticket> tickets = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                tickets.add(
                        buildTicket(resultSet)
                );
            }

            return tickets;
        }catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public List<Ticket> findAll(TicketFilter ticketFilter){
        List<Object> parameters = new ArrayList<>();
        List<String> whereSql = new ArrayList<>();

        if(ticketFilter.passengerName() != null){
            parameters.add(ticketFilter.passengerName());
            whereSql.add("passenger_name = ?");
        }

        if(ticketFilter.seatNo() != null){
            parameters.add("%" + ticketFilter.seatNo() + "%");
            whereSql.add("seat_no like ?");
        }

        parameters.add(ticketFilter.limit());
        parameters.add(ticketFilter.offset());

        String where = whereSql.stream().collect(Collectors.joining(
                " AND ",
                parameters.size() > 2 ? " WHERE " : " ",
                " LIMIT ? OFFSET ?"
        ));

        String sql = FIND_ALL_SQL + where;

        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            List<Ticket> tickets = new ArrayList<>();

            for (int i = 0; i < parameters.size(); i++) {
                statement.setObject(i + 1, parameters.get(i));
            }
            
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                tickets.add(
                        buildTicket(resultSet)
                );
            }

            return tickets;
        }catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private Ticket buildTicket(ResultSet resultSet) throws SQLException {
        return new Ticket(resultSet.getLong("id"),
                resultSet.getString("passport_no"),
                resultSet.getString("passenger_name"),
                flightDao.findById(
                        resultSet.getLong("flight_id"),
                        resultSet.getStatement().getConnection()
                ).orElse(null),
                resultSet.getString("seat_no"),
                resultSet.getBigDecimal("cost")
        );
    }

    public Optional<Ticket> findById(Long id){
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            Ticket ticket = null;
            if(resultSet.next()){
                ticket = buildTicket(resultSet);
            }
            return Optional.ofNullable(ticket);
        }catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Ticket save(Ticket ticket){
        try(Connection connection = ConnectionManager.get();
            PreparedStatement statement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)){
            statement.setString(1, ticket.getPassportNo());
            statement.setString(2, ticket.getPassengerName());
            statement.setLong(3, ticket.getFlight().getId());
            statement.setString(4, ticket.getSeatNo());
            statement.setBigDecimal(5, ticket.getCost());

            statement.executeUpdate();
            ResultSet keys = statement.getGeneratedKeys();
            if(keys.next()){
                ticket.setId(keys.getLong("id"));
            }

            return ticket;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean delete(Long id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(DELETE_SQL)) {
            statement.setLong(1, id);

            return statement.executeUpdate() > 0;
        }catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public static TicketDao getInstance(){
        return INSTANCE;
    }

    private TicketDao(){}
}
