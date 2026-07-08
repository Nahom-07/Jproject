package pharmacy.dao;

import java.sql.*;
import java.util.ArrayList;
import pharmacy.data.Medicine;

public class MedicineDAO extends BaseDAO {
    public MedicineDAO() throws SQLException {
        super();
    }

    
    public void insert(Object obj) throws SQLException {
        Medicine med = (Medicine) obj;
        String sql = "insert into Medicine (medicineName, genericName, category, dosageForm, unit, reorderLevel) values (?, ?, ?, ?, ?, ?)";

        PreparedStatement ps = c.prepareStatement(sql);
        ps.setString(1, med.getMedicineName());
        ps.setString(2, med.getGenericName());
        ps.setString(3, med.getCategory());
        ps.setString(4, med.getDosageForm());
        ps.setString(5, med.getUnit());
        ps.setInt(6, med.getReorderLevel());
        ps.executeUpdate();
        ps.close();
    }
    public void delete(int id) throws SQLException {
        String sql = "delete from Medicine where medicineID = ?";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        ps.close();
    }

    public void update(Medicine md) throws SQLException {
        String sql = "update Medicine set medicineName = ?, genericName = ?, category = ?, dosageForm = ?, unit = ?, reorderLevel = ? where medicineID = ?";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setString(1, md.getMedicineName());
        ps.setString(2, md.getGenericName());
        ps.setString(3, md.getCategory());
        ps.setString(4, md.getDosageForm());
        ps.setString(5, md.getUnit());
        ps.setInt(6, md.getReorderLevel());
        ps.setInt(7, md.getMedicineID());
        ps.executeUpdate();
        ps.close();

    }
    

    public ArrayList<Medicine> getAll() throws SQLException {
        ArrayList <Medicine> meds = new ArrayList<>();
        String sql = "select * from Medicine";
        PreparedStatement ps = c.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int mid = rs.getInt("medicineID");
            String mName = rs.getString("medicineName");
            String gName = rs.getString("genericName");
            String cat = rs.getString("category");
            String dForm = rs.getString("dosageForm");
            String u = rs.getString("unit");
            int rLevel = rs.getInt("reorderLevel");

            Medicine med = new Medicine(mid, mName, gName, cat, dForm, u, rLevel);
            meds.add(med);
        }
        rs.close();
        ps.close();
        return meds;
    }

    public Medicine getById(int id) throws SQLException {
        String sql = "select * from Medicine where medicineID = ?";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        Medicine med = null;
        if (rs.next()) {
            int mid = rs.getInt("medicineID");
            String mName = rs.getString("medicineName");
            String gName = rs.getString("genericName");
            String cat = rs.getString("category");
            String dForm = rs.getString("dosageForm");
            String u = rs.getString("unit");
            int rLevel = rs.getInt("reorderLevel");

            med = new Medicine(mid, mName, gName, cat, dForm, u, rLevel);
        } else {
            throw new SQLException("Medicine with ID " + id + " not found.");
        }
        rs.close();
        ps.close();
        return med;
    }
    public ArrayList<Medicine> getByName(String name) throws SQLException {
        ArrayList <Medicine> meds = new ArrayList<>();
        String sql = "select * from Medicine where medicineName like ?";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setString(1, "%" + name + "%");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int mid = rs.getInt("medicineID");
            String mName = rs.getString("medicineName");
            String gName = rs.getString("genericName");
            String cat = rs.getString("category");
            String dForm = rs.getString("dosageForm");
            String u = rs.getString("unit");
            int rLevel = rs.getInt("reorderLevel");

            Medicine med = new Medicine(mid, mName, gName, cat, dForm, u, rLevel);
            meds.add(med);
        }
        rs.close();
        ps.close();
        return meds;
    }
}

