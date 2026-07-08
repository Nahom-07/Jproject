package pharmacy.db;

import java.sql.*;

public class DBConnect {
    private static String url = 
        "jdbc:sqlserver://localhost:1433;" + 
        "databaseName=PharmacyDB;" + 
        "encrypt=false;" +
        "trustServerCertificate=true;";
    private static String userName = "kalab";
    private static String password = "Pharmacy123!";

    public static Connection getConnection() throws SQLException {
        Connection c = DriverManager.getConnection(url, userName, password);
        return c;
    }
}
