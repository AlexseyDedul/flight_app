package by.alexdedul.jdbc.dto;

public record TicketFilter (String passengerName,
                            String seatNo,
                            int limit,
                            int offset){
}
