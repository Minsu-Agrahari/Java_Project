import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static Connection con = null;

    public static Connection getConnection() {
        String jdbcDriver = "com.mysql.cj.jdbc.Driver"; 
        String url = "jdbc:mysql://localhost:3306/BANK?serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true";

        String user = "root";
        String password = "password@12345";

        try {
            Class.forName(jdbcDriver); // load driver
            con = DriverManager.getConnection(url, user, password);
            System.out.println("-- Connected to database.");
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver class not found: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("SQL error while connecting: " + e.getMessage());
        }
        return con;
    }

    // Test DB connection
    public static void main(String[] args) {
        Connection conn = getConnection();
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Connection closed successfully.");
            } catch (Exception e) {
                System.out.println("Error closing connection: " + e.getMessage());
            }
        } else {
            System.out.println("Failed to obtain a connection.");
        }
    }
}
