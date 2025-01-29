package by.alexdedul.jdbc.dao;

import by.alexdedul.jdbc.entity.Flight;
import by.alexdedul.jdbc.entity.FlightStatus;
import by.alexdedul.jdbc.entity.Ticket;
import by.alexdedul.jdbc.exception.DaoException;
import by.alexdedul.jdbc.utlis.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FlightDao implements Dao<Long, Flight> {
    private final static FlightDao INSTANCE = new FlightDao();

    private final static String SAVE_SQL = """
                                            insert into flight (flight_no, departure_date, departure_airport_code, arrival_date, arrival_airport_code, aircraft_id, status)
                                            values (?, ?, ?, ?, ?, ?, ?)
                                            """;

    private final static String UPDATE_SQL = """
                                            UPDATE flight 
                                            SET flight_no = ?, 
                                            departure_date = ?, 
                                            departure_airport_code = ?, 
                                            arrival_date = ?, 
                                            arrival_airport_code = ?, 
                                            aircraft_id = ?, 
                                            status = ?
                                            WHERE id = ?
                                            """;

    private final static String DELETE_SQL = """
                                            DELETE FROM flight 
                                            WHERE id = ?
                                            """;

    private final static String FIND_ALL_SQL = """
                                            SELECT id, flight_no, departure_date, departure_airport_code, arrival_date, arrival_airport_code, aircraft_id, status 
                                            FROM flight
                                            """;

    private final static String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ?
            """;

    @Override
    public boolean update(Flight flight) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(UPDATE_SQL )) {
            statement.setString(1, flight.getFlightNo());
            statement.setTimestamp(2, Timestamp.valueOf(flight.getDepartureDate()));
            statement.setInt(3, flight.getDepartureAirportCode());
            statement.setTimestamp(4, Timestamp.valueOf(flight.getArrivalDate()));
            statement.setInt(5, flight.getArrivalAirportCode());
            statement.setInt(6, flight.getAircraftId());
            statement.setString(7, flight.getStatus().name());

            statement.setLong(8, flight.getId());

            return statement.executeUpdate() > 0;
        }catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Flight> findAll() {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_SQL)) {
            List<Flight> flights = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                flights.add(
                        buildFlight(resultSet)
                );
            }

            return flights;
        }catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private Flight buildFlight(ResultSet resultSet) throws SQLException {
        return new Flight(
            resultSet.getLong("id"),
                resultSet.getString("flight_no"),
                resultSet.getTimestamp("departure_date").toLocalDateTime(),
                resultSet.getInt("departure_airport_code"),
                resultSet.getTimestamp("arrival_date").toLocalDateTime(),
                resultSet.getInt("arrival_airport_code"),
                resultSet.getInt("aircraft_id"),
                FlightStatus.valueOf(resultSet.getString("status"))
        );
    }

    public Optional<Flight> findById(Long id, Connection connection) {
        try (PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            Flight flight = null;
            if(resultSet.next()){
                flight = buildFlight(resultSet);
            }
            return Optional.ofNullable(flight);
        }catch (SQLException e) {
            throw new DaoException(e);
        }
    }


    @Override
    public Optional<Flight> findById(Long id) {
        try(Connection connection = ConnectionManager.get()){
            return findById(id, connection);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Flight save(Flight flight) {
        try(Connection connection = ConnectionManager.get();
            PreparedStatement statement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)){
            statement.setString(1, flight.getFlightNo());
            statement.setTimestamp(2, Timestamp.valueOf(flight.getDepartureDate()));
            statement.setInt(3, flight.getDepartureAirportCode());
            statement.setTimestamp(4, Timestamp.valueOf(flight.getArrivalDate()));
            statement.setInt(5, flight.getArrivalAirportCode());
            statement.setInt(6, flight.getAircraftId());
            statement.setString(7, flight.getStatus().name());

            statement.executeUpdate();
            ResultSet keys = statement.getGeneratedKeys();
            if(keys.next()){
                flight.setId(keys.getLong("id"));
            }

            return flight;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(DELETE_SQL)) {
            statement.setLong(1, id);

            return statement.executeUpdate() > 0;
        }catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public static FlightDao getInstance(){
        return INSTANCE;
    }

    private FlightDao() {
    }
}
