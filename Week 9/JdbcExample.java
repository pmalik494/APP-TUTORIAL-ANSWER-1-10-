

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Demonstrates basic JDBC connectivity in the Quizzy application
 */
public class JdbcExample {

    // Database connection details
    private static final String URL = "jdbc:postgresql://localhost:5432/quizzy_db";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "your_password";

    public static void main(String[] args) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // Step 1: Load PostgreSQL JDBC driver
            Class.forName("org.postgresql.Driver");

            // Step 2: Establish connection
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("✅ Connected to the database successfully.");

            // Step 3: Prepare SQL query
            String query = "SELECT id, username, email FROM users WHERE id = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, 1);

            // Step 4: Execute query
            resultSet = statement.executeQuery();

            // Step 5: Display results
            while (resultSet.next()) {
                System.out.println("User ID: " + resultSet.getInt("id"));
                System.out.println("Username: " + resultSet.getString("username"));
                System.out.println("Email: " + resultSet.getString("email"));
            }

        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        } finally {
            // Step 6: Close resources
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
    }
}