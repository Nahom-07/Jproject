package pharmacy.err;

public class LowStockWarningException extends Exception {
    public LowStockWarningException(String m) {
        super(m);
    }
}
