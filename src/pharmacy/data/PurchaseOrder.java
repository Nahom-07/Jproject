package pharmacy.data;

import java.sql.Date;
public class PurchaseOrder {
    private int orderID;
    private Date orderDate;
    private Date expectedDate;
    private Date receivedDate;
    private String status;
    private double totalAmount;

    public PurchaseOrder (int oID, Date oDate, Date eDate, Date rDate, String stat, double tAmount){
        orderID = oID;
        orderDate = oDate;
        expectedDate = eDate;
        receivedDate = rDate;
        status = stat;
        totalAmount = tAmount;
    }

    public int getOrderID(){
        return orderID;
    }

    public Date getOrderDate(){
        return orderDate;
    }

    public Date getExpectedDate(){
        return expectedDate;
    }

    public Date getReceivedDate(){
        return receivedDate;
    }

    public String getStatus(){
        return status;
    }

    public double getTotalAmount(){
        return totalAmount;
    }
    
    public void setOrderDate(Date oDateS){
        orderDate = oDateS;
    }

    public void setExpectedDate(Date eDateS){
        expectedDate = eDateS;
    }

    public void setReceivedDate(Date rDateS){
        receivedDate = rDateS;
    }

    public void setStatus(String statS){
        status = statS;
    }

    public void setTotalAmount(double tAmountS){
        totalAmount = tAmountS;
    }

}

