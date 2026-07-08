package pharmacy.data;

import java.sql.Date;
public class Stock {
    private int stockID;
    private int medicineID;
    private String batchNo;
    private int availableQuantity;
    private double unitCost;
    private Date manufactureDate;
    private Date expiryDate;
    private Date receivedDate;
    private String storageCondition;

    public Stock(int sID, int mID, String bNo, int aQuantity, double uCost, Date mDate, Date eDate, Date rDate, String sCondition) {
        stockID = sID;
        medicineID = mID;
        batchNo = bNo;
        availableQuantity = aQuantity;
        unitCost = uCost;
        manufactureDate = mDate;
        expiryDate = eDate;
        receivedDate = rDate;
        storageCondition = sCondition;

    }

    public int getStockID() {
        return stockID;
    }

    public int getMedicineID() {
        return medicineID;
    }   

    public String getBatchNo() {
        return batchNo;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public double getUnitCost() {
        return unitCost;
    }

    public Date getManufactureDate() {
        return manufactureDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public Date getReceivedDate() {
        return receivedDate;
    }

    public String getStorageCondition() {
        return storageCondition;
    }

    public void setManufactureDate(Date mDateS) {
        manufactureDate = mDateS;
    }

    public void setExpiryDate(Date eDateS) {
        expiryDate = eDateS;
    }

    public void setReceivedDate(Date rDateS) {
        receivedDate = rDateS;
    }

    public void setBatchNo(String bNoS) {
        batchNo = bNoS;
    }

    public void setAvailableQuantity(int aQuantityS) {
        availableQuantity = aQuantityS;
    }

    public void setUnitCost(double uCostS) {
        unitCost = uCostS;
    }

    public void setStorageCondition(String sConditionS) {
        storageCondition = sConditionS;
    }


}
