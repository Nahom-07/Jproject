package pharmacy.dao;
import pharmacy.data.Dispensing;
import java.sql.*;
import java.util.ArrayList;

public class DispensingDAO extends BaseDAO {
    public DispensingDAO() throws SQLException {
        super();
    }

    public void insert(Object obj) throws SQLException {
        Dispensing d = (Dispensing) obj;
        String sql = "insert into Dispensing (stockID, medicineID, qtyDispensed) values (?, ?, ?)";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setInt(1, d.getStockID());
        ps.setInt(2, d.getMedicineID());
        ps.setInt(3, d.getQtyDispensed());
        ps.executeUpdate();
        ps.close();
    }

 
    public void delete(int id) throws SQLException {
        String sql = "delete from Dispensing where dispensingID = ?";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        ps.close();
    }

    public ArrayList<Dispensing> getAll() throws SQLException {
        ArrayList<Dispensing> ds = new ArrayList<>();
        String sql = "select * from Dispensing";
        PreparedStatement ps = c.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int dID = rs.getInt("dispensingID");
            int sID = rs.getInt("stockID");
            int mID = rs.getInt("medicineID");
            int qtyD = rs.getInt("qtyDispensed");
            Date dDate = rs.getDate("dispenseDate");

            Dispensing d = new Dispensing(dID, sID, mID, qtyD, dDate);
            ds.add(d);
        }
        rs.close();
        ps.close();
        return ds;
    }

    public ArrayList<Dispensing> getByStockID(int id) throws SQLException {
        ArrayList<Dispensing> ds = new ArrayList<>();
        String sql = "select * from Dispensing where stockID = ?";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int dID = rs.getInt("dispensingID");
            int sID = rs.getInt("stockID");
            int mID = rs.getInt("medicineID");
            int qtyD = rs.getInt("qtyDispensed");
            Date dDate = rs.getDate("dispenseDate");

            Dispensing d = new Dispensing(dID, sID, mID, qtyD, dDate);
            ds.add(d);
        }
        rs.close();
        ps.close();
        return ds;
    }
    
}

