package pharmacy.data;

import java.sql.Date;
public class Dispensing {
    private int dispensingID;
    private int stockID;
    private int medicineID;
    private int qtyDispensed;
    private Date dispenseDate;

    public Dispensing(int dID, int sID, int mID, int qDispensed, Date dDate) {
        dispensingID = dID;
        stockID = sID;
        medicineID = mID;
        qtyDispensed = qDispensed;
        dispenseDate = dDate;

    }

    public int getDispensingID() {
        return dispensingID;
    }

    public int getStockID() {
        return stockID;
    }

    public int getMedicineID() {
        return medicineID;
    }

    public int getQtyDispensed() {
        return qtyDispensed;
    }

    public Date getDispenseDate() {
        return dispenseDate;
    }

}
