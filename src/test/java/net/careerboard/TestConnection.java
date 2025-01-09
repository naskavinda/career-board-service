package net.careerboard;
import java.sql.Connection;
import java.sql.DriverManager;

public class TestConnection {

    public static void main(String[] args) {
        try {
            String url = "jdbc:postgresql://localhost:5432/career-board";
            String username = "postgres";
            String password = "1234";
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connection successful!");
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

