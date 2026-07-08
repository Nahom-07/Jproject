package pharmacy.data;

public class OrderItem {
    private int orderItemID;
    private int orderID;
    private int medicineID;
    private int qtyOrdered;
    private int qtyReceived;
    private double unitCost;
    private double totalCost;
    public OrderItem(int oItemID, int oID, int mID, int qtyO, int qtyR, double uCost, double tCost) {
        orderItemID = oItemID;
        orderID = oID;
        medicineID = mID;
        qtyOrdered = qtyO;
        qtyReceived = qtyR;
        unitCost = uCost;
        totalCost = tCost;
    }

    public int getOrderItemID() {
        return orderItemID;
    }

    public int getOrderID() {
        return orderID;
    }

    public int getMedicineID() {
        return medicineID;
    }

    public int getQtyOrdered() {
        return qtyOrdered;
    }

    public int getQtyReceived() {
        return qtyReceived;
    }

    public double getUnitCost() {
        return unitCost;
    }

    public double getTotalCost() {
        return totalCost;
    }

    

    public void setQtyOrdered(int qtyOS) {
        qtyOrdered = qtyOS;
    }

    public void setQtyReceived(int qtyRS) {
        qtyReceived = qtyRS;
    }

    public void setUnitCost(double uCostS) {
        unitCost = uCostS;   
    }

    public void setTotalCost(double tCostS) {
        totalCost = tCostS;
    }

    


}
