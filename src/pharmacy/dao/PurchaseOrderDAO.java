package pharmacy.dao;
import java.sql.*;
import pharmacy.data.PurchaseOrder;
import java.util.ArrayList;

public class PurchaseOrderDAO extends BaseDAO {
    public PurchaseOrderDAO() throws SQLException {
        super();
    }

    public void insert(Object obj) throws SQLException {
        PurchaseOrder po = (PurchaseOrder) obj;
        String sql = "insert into PurchaseOrder (orderDate, expectedDate, receivedDate, status, totalAmount) values (?, ?, ?, ?, ?)";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setDate(1, po.getOrderDate());
        ps.setDate(2, po.getExpectedDate());
        ps.setDate(3, po.getReceivedDate());
        ps.setString(4, po.getStatus());
        ps.setDouble(5, po.getTotalAmount());
        ps.executeUpdate();
        ps.close();
    }

    public void delete(int id) throws SQLException {
        String sql = "delete from PurchaseOrder where orderID = ?";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        ps.close();
    }
    
    public void update(PurchaseOrder po) throws SQLException {
        String sql = "update PurchaseOrder set orderDate = ?, expectedDate = ?, receivedDate = ?, status = ?, totalAmount = ? where orderID = ?";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setDate(1, po.getOrderDate());
        ps.setDate(2, po.getExpectedDate());
        ps.setDate(3, po.getReceivedDate());
        ps.setString(4, po.getStatus());
        ps.setDouble(5, po.getTotalAmount());
        ps.setInt(6, po.getOrderID());
        ps.executeUpdate();
        ps.close();
    }

    public ArrayList<PurchaseOrder> getAll() throws SQLException {
        ArrayList<PurchaseOrder> pos = new ArrayList<>();
        String sql = "select * from PurchaseOrder";
        PreparedStatement ps = c.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int oID = rs.getInt("orderID");
            Date oDate = rs.getDate("orderDate");
            Date eDate = rs.getDate("expectedDate");
            Date rDate = rs.getDate("receivedDate");
            String stat = rs.getString("status");
            double tAmount = rs.getDouble("totalAmount");

            PurchaseOrder po = new PurchaseOrder(oID, oDate, eDate, rDate, stat, tAmount);
            pos.add(po);
        }
        rs.close();
        ps.close();
        return pos;
    }

    public PurchaseOrder getById(int id) throws SQLException {
        String sql = "select * from PurchaseOrder where orderID = ?";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        PurchaseOrder po = null;
        if (rs.next()) {
            int oID = rs.getInt("orderID");
            Date oDate = rs.getDate("orderDate");
            Date eDate = rs.getDate("expectedDate");
            Date rDate = rs.getDate("receivedDate");
            String stat = rs.getString("status");
            double tAmount = rs.getDouble("totalAmount");

            po = new PurchaseOrder(oID, oDate, eDate, rDate, stat, tAmount);
        } else {
            throw new SQLException("PurchaseOrder with ID " + id + " not found.");
        }
        rs.close();
        ps.close();
        return po;
    }

    public ArrayList<PurchaseOrder> getByStatus(String status) throws SQLException {
        ArrayList<PurchaseOrder> pos = new ArrayList<>();
        String sql = "select * from PurchaseOrder where status = ?";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setString(1, status);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int oID = rs.getInt("orderID");
            Date oDate = rs.getDate("orderDate");
            Date eDate = rs.getDate("expectedDate");
            Date rDate = rs.getDate("receivedDate");
            String stat = rs.getString("status");
            double tAmount = rs.getDouble("totalAmount");

            PurchaseOrder po = new PurchaseOrder(oID, oDate, eDate, rDate, stat, tAmount);
            pos.add(po);
        }
        rs.close();
        ps.close();
        return pos;
    }
}
