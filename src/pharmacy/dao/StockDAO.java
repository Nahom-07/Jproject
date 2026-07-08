package pharmacy.dao;


import pharmacy.data.Stock;
import java.sql.*;
import java.util.ArrayList;


public class StockDAO extends BaseDAO {
    public StockDAO() throws SQLException {
        super();
    }

    public void insert(Object obj) throws SQLException {
        Stock st = (Stock) obj;
        String sql = "insert into Stock (medicineID, batchNo, availableQuantity, unitCost, manufactureDate, expiryDate, receivedDate, storageCondition) values (?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement ps = c.prepareStatement(sql);
        ps.setInt(1, st.getMedicineID());
        ps.setString(2, st.getBatchNo());
        ps.setInt(3, st.getAvailableQuantity());
        ps.setDouble(4, st.getUnitCost());
        ps.setDate(5, st.getManufactureDate());
        ps.setDate(6, st.getExpiryDate());
        ps.setDate(7, st.getReceivedDate());
        ps.setString(8, st.getStorageCondition());
        ps.executeUpdate();
        ps.close();
        
    }

    public void delete (int id) throws SQLException {
        String sql = "delete from Stock where stockID = ?";
        PreparedStatement ps = c.prepareStatement(sql);

        ps.setInt(1, id);
        ps.executeUpdate();
        ps.close();
    }

    public void update (Stock s) throws SQLException{
        String sql = "update Stock set medicineID = ?, batchNo = ?, availableQuantity = ?, unitCost = ?, manufactureDate = ?, expiryDate = ?, receivedDate = ?, storageCondition = ? where stockID = ? ";

        PreparedStatement ps = c.prepareStatement(sql);

        ps.setInt(1, s.getMedicineID());
        ps.setString(2, s.getBatchNo());
        ps.setInt(3, s.getAvailableQuantity());
        ps.setDouble(4, s.getUnitCost());
        ps.setDate(5, s.getManufactureDate());
        ps.setDate(6, s.getExpiryDate());
        ps.setDate(7, s.getReceivedDate());
        ps.setString(8, s.getStorageCondition());
        ps.setInt(9, s.getStockID());

        ps.executeUpdate();
        ps.close();
    }

    public ArrayList<Stock> getAll() throws SQLException {

        ArrayList <Stock> stks = new ArrayList<>();
        String sql = "select * from Stock";
        PreparedStatement ps = c.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int sID = rs.getInt("stockID");
            int mid = rs.getInt ("medicineID");
            String bNo = rs.getString("batchNo");
            int aQuantity = rs.getInt("availableQuantity");
            double uCost = rs.getDouble("unitCost");
            Date mDate = rs.getDate("manufactureDate");
            Date eDate = rs.getDate("expiryDate");
            Date rDate = rs.getDate("receivedDate");
            String sCondition = rs.getString("storageCondition");

            Stock st = new Stock(sID, mid, bNo, aQuantity, uCost, mDate, eDate, rDate, sCondition);
            stks.add(st);
        }
        rs.close();
        ps.close();




        return stks;
    }

    public Stock getById(int id) throws SQLException{
        String sql = "select * from Stock where stockID = ?";

        PreparedStatement ps = c.prepareStatement(sql);

        ps.setInt(1, id);
        
        ResultSet rs = ps.executeQuery();
        Stock st = null;
        if(rs.next()){
            int sID = rs .getInt("stockID");
            int mid = rs.getInt ("medicineID");
            String bNo = rs.getString("batchNo");
            int aQuantity = rs.getInt("availableQuantity");
            double uCost = rs.getDouble("unitCost");
            Date mDate = rs.getDate("manufactureDate");
            Date eDate = rs.getDate("expiryDate");
            Date rDate = rs.getDate("receivedDate");
            String sCondition = rs.getString("storageCondition");

            st = new Stock(sID, mid, bNo, aQuantity, uCost, mDate, eDate, rDate, sCondition);
            
        } else {
            throw new SQLException("Stock with id " + id + "doesn't exist");
        }

        rs.close();
        ps.close();
        return st;
    }
    
    public ArrayList<Stock> getByMedId(int id) throws SQLException {
        ArrayList<Stock> stks = new ArrayList<>();
        String sql = "select * from Stock where medicineID = ?";

        PreparedStatement ps = c.prepareStatement(sql);
        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();

        while (rs.next()){
            
            int sID = rs .getInt("stockID");
            int mid = rs.getInt ("medicineID");
            String bNo = rs.getString("batchNo");
            int aQuantity = rs.getInt("availableQuantity");
            double uCost = rs.getDouble("unitCost");
            Date mDate = rs.getDate("manufactureDate");
            Date eDate = rs.getDate("expiryDate");
            Date rDate = rs.getDate("receivedDate");
            String sCondition = rs.getString("storageCondition");

            Stock st = new Stock(sID, mid, bNo, aQuantity, uCost, mDate, eDate, rDate, sCondition);
            stks.add(st);
            
        }

        rs.close();
        ps.close();

        return stks;
    }

}
