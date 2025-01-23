package by.alexdedul.jdbc;

import by.alexdedul.jdbc.utlis.ConnectionManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcRunner {
    public static void main(String[] args) {
        getAirports();
    }

    private static void getAirports(){
        String query = """
                select * from airport a;
                """;

        try(Connection connection = ConnectionManager.get();
            Statement statement = connection.createStatement()){
            ResultSet result = statement.executeQuery(query);
            while(result.next()){
                System.out.println(result.getLong("code"));
                System.out.println(result.getString("country"));
                System.out.println(result.getString("city"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
