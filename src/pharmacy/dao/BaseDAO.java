package pharmacy.dao;
import java.sql.*;
import pharmacy.db.DBConnect;


public abstract class BaseDAO {
    protected Connection c;
    public BaseDAO() throws SQLException {
        c = DBConnect.getConnection();
    }

    public abstract void insert(Object obj) throws SQLException;

    public abstract void delete(int id) throws SQLException;

    public void closeConnection() throws SQLException {
        if (c != null) {
            try {
                c.close();
            } catch (SQLException e) {
                throw new SQLException("Error closing connection: " + e.getMessage());
            }
        }
    }
}
