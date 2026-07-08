package pharmacy.dao;
import pharmacy.data.OrderItem;
import java.sql.*;
import java.util.ArrayList;


public class OrderItemDAO extends BaseDAO {
    public OrderItemDAO() throws SQLException {
        super();
    }

    public void insert (Object obj) throws SQLException {
        OrderItem oim = (OrderItem) obj;
        String sql = "insert into OrderItem (orderID, medicineID, qtyOrdered, qtyReceived, unitCost, totalCost) values (?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setInt(1, oim.getOrderID());
        ps.setInt(2, oim.getMedicineID());
        ps.setInt(3, oim.getQtyOrdered());
        ps.setInt(4, oim.getQtyReceived());
        ps.setDouble(5, oim.getUnitCost());
        ps.setDouble(6, oim.getTotalCost());
        ps.executeUpdate();
        ps.close();
    }

    public void delete (int id) throws SQLException {
        String sql = "delete from OrderItem where orderItemID = ?";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        ps.close();
    }

    public void update (OrderItem oi) throws SQLException {
        String sql = "update OrderItem set orderID = ?, medicineID = ?, qtyOrdered = ?, qtyReceived = ?, unitCost = ?, totalCost = ? where orderItemID = ?";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setInt(1, oi.getOrderID());
        ps.setInt(2, oi.getMedicineID());
        ps.setInt(3, oi.getQtyOrdered());
        ps.setInt(4, oi.getQtyReceived());
        ps.setDouble(5, oi.getUnitCost());
        ps.setDouble(6, oi.getTotalCost());
        ps.setInt(7, oi.getOrderItemID());
        ps.executeUpdate();
        ps.close();
    }

    public ArrayList<OrderItem> getAll() throws SQLException {
        ArrayList <OrderItem> ois = new ArrayList<>();
        String sql = "select * from OrderItem";
        PreparedStatement ps = c.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int oItemID = rs.getInt("orderItemID");
            int oID = rs.getInt("orderID");
            int mID = rs.getInt("medicineID");
            int qtyO = rs.getInt("qtyOrdered");
            int qtyR = rs.getInt("qtyReceived");
            double uCost = rs.getDouble("unitCost");
            double tCost = rs.getDouble("totalCost");

            OrderItem oim = new OrderItem(oItemID, oID, mID, qtyO, qtyR, uCost, tCost);
            ois.add(oim);
        }
        rs.close();
        ps.close();
        return ois;
    }

    public OrderItem getById(int id) throws SQLException {
        String sql = "select * from OrderItem where orderItemID = ?";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        OrderItem oi = null;
        if (rs.next()) {
            int oItemID = rs.getInt("orderItemID");
            int oID = rs.getInt("orderID");
            int mID = rs.getInt("medicineID");
            int qtyO = rs.getInt("qtyOrdered");
            int qtyR = rs.getInt("qtyReceived");
            double uCost = rs.getDouble("unitCost");
            double tCost = rs.getDouble("totalCost");

            oi = new OrderItem(oItemID, oID, mID, qtyO, qtyR, uCost, tCost);
        } else {
            throw new SQLException("OrderItem with id " + id + " doesn't exist");
        }
        rs.close();
        ps.close();
        return oi;
    }   

    public ArrayList<OrderItem> getByOrderId(int orderId) throws SQLException {
        ArrayList<OrderItem> ois = new ArrayList<>();
        String sql = "SELECT * FROM OrderItem WHERE orderID = ?";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setInt(1, orderId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int oItemID = rs.getInt("orderItemID");
            int oID = rs.getInt("orderID");
            int mID = rs.getInt("medicineID");
            int qtyO = rs.getInt("qtyOrdered");
            int qtyR = rs.getInt("qtyReceived");
            double uCost = rs.getDouble("unitCost");
            double tCost = rs.getDouble("totalCost");

            OrderItem oi = new OrderItem(oItemID, oID, mID, qtyO, qtyR, uCost, tCost);
            ois.add(oi);
        }
        rs.close();
        ps.close();
        return ois;
    }


}
