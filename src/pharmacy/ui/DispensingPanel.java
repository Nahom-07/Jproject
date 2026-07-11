package pharmacy.ui;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import pharmacy.dao.DispensingDAO;
import pharmacy.dao.MedicineDAO;
import pharmacy.dao.StockDAO;
import pharmacy.data.Dispensing;
import pharmacy.data.Medicine;
import pharmacy.data.Stock;
import pharmacy.err.InsufficientStockException;
import pharmacy.err.LowStockWarningException;

public class DispensingPanel extends JPanel {

    private DefaultTableModel tmodel;
    private DispensingDAO dispensingDAO;
    private JComboBox<Medicine> medicineCombo;
    private JLabel availableQtyLabel;
    private JSpinner qtySpinner;
    private JButton dispenseButton;
    private JTable t;

    private static final Color NAVY_DARK = new Color(24, 30, 54);
    private static final Color CANVAS_BG = new Color(240, 244, 248);
    private static final Color ANCCENT_HOVER = new Color(52, 152, 219);
    private static final Color TEXT_DARK = new Color(44, 62, 80);

    public static void styleTopButtons(JButton btn, Color bgColor, Color fgColor) {
        btn.setBackground(bgColor);
        btn.setForeground(fgColor);
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(true);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        Border line = BorderFactory.createLineBorder(new Color(200, 204, 210));
        Border space = BorderFactory.createEmptyBorder(6, 14, 6, 14);
        btn.setBorder(BorderFactory.createCompoundBorder(line, space));
        btn.setPreferredSize(new Dimension(120, 32));
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(230, 238, 245));
                btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(bgColor);
            }
        });
    }

    private static class MedicineItem extends Medicine {
        MedicineItem(Medicine m) {
            super(m.getMedicineID(), m.getMedicineName(), m.getGenericName(),
                  m.getCategory(), m.getDosageForm(), m.getUnit(), m.getReorderLevel());
        }
        @Override
        public String toString() {
            return getMedicineName() + " (" + getUnit() + ")";
        }
    }

    public DispensingPanel() {
        setLayout(new BorderLayout(0, 0));
        setBackground(Color.WHITE);

        JPanel topp = new JPanel();
        topp.setLayout(new BoxLayout(topp, BoxLayout.Y_AXIS));
        topp.setBackground(Color.WHITE);

        JLabel title = new JLabel("Dispense Medicine");
        title.setFont(new Font("Segoe UI", Font.BOLD, 40));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        title.setBorder(BorderFactory.createEmptyBorder(20, 12, 10, 0));

        JPanel form_row = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 10));
        form_row.setBackground(Color.WHITE);
        form_row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        form_row.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(218, 224, 233)));

        JLabel medL = new JLabel("Medicine:");
        medL.setFont(new Font("Segoe UI", Font.BOLD, 16));
        medicineCombo = new JComboBox<>();

        JLabel qtyL = new JLabel("Quantity:");
        qtyL.setFont(new Font("Segoe UI", Font.BOLD, 16));
        qtySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100000, 1));
        qtySpinner.setPreferredSize(new Dimension(80, 28));

        dispenseButton = new JButton("Dispense");
        styleTopButtons(dispenseButton, Color.WHITE, TEXT_DARK);

        availableQtyLabel = new JLabel("Available: --");
        availableQtyLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        availableQtyLabel.setForeground(new Color(120, 130, 140));

        form_row.add(medL);
        form_row.add(medicineCombo);
        form_row.add(qtyL);
        form_row.add(qtySpinner);
        form_row.add(dispenseButton);
        form_row.add(availableQtyLabel);

        topp.add(title);
        topp.add(form_row);

        add(topp, BorderLayout.NORTH);

        String[] columnnames = {"Dispensing ID", "Stock ID", "Medicine ID", "Qty Dispensed", "Dispense Date"};
        tmodel = new DefaultTableModel(columnnames, 0);
        t = new JTable(tmodel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        t.getTableHeader().setReorderingAllowed(false);
        t.setRowHeight(30);
        t.setShowGrid(true);
        t.setGridColor(new Color(230, 235, 240));
        t.setSelectionBackground(new Color(225, 238, 250));
        t.setSelectionForeground(new Color(44, 62, 80));

        JTableHeader header = t.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 16));
        header.setBackground(new Color(245, 247, 250));
        header.setForeground(new Color(44, 62, 80));
        header.setPreferredSize(new Dimension(0, 36));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(218, 224, 233)));

        JScrollPane scroll = new JScrollPane(t);
        add(scroll, BorderLayout.CENTER);


        medicineCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshAvailableQtyLabel();
            }
        });

        dispenseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleDispense();
            }
        });

        loadMedicines();
        refreshTableData();
    }

    private void loadMedicines() {
        medicineCombo.removeAllItems();
        try {
            MedicineDAO medicineDAO = new MedicineDAO();
            ArrayList<Medicine> list = medicineDAO.getAll();
            for (Medicine m : list) {
                medicineCombo.addItem(new MedicineItem(m));
            }
            refreshAvailableQtyLabel();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading medicines: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refreshAvailableQtyLabel() {
        Medicine selected = (Medicine) medicineCombo.getSelectedItem();
        if (selected == null) {
            availableQtyLabel.setText("Available: --");
            return;
        }
        try {
            StockDAO stockDAO = new StockDAO();
            ArrayList<Stock> batches = stockDAO.getByMedId(selected.getMedicineID());
            int total = 0;
            for (Stock s : batches) {
                total += s.getAvailableQuantity();
            }
            availableQtyLabel.setText("Available: " + total);
        } catch (Exception e) {
            availableQtyLabel.setText("Available: --");
        }
    }

    private void handleDispense() {
        Medicine selected = (Medicine) medicineCombo.getSelectedItem();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Please select a medicine.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int qtyRequested = (Integer) qtySpinner.getValue();

        try {
            StockDAO stockDAO = new StockDAO();
            ArrayList<Stock> batches = stockDAO.getByMedId(selected.getMedicineID());

            // pick the first batch that alone can cover the requested quantity
            Stock chosenBatch = null;
            for (Stock s : batches) {
                if (s.getAvailableQuantity() >= qtyRequested) {
                    chosenBatch = s;
                    break;
                }
            }

            if (chosenBatch == null) {
                throw new InsufficientStockException(
                    "Not enough stock of " + selected.getMedicineName() + " to dispense " + qtyRequested + " units.");
            }

            Dispensing d = new Dispensing(0, chosenBatch.getStockID(), selected.getMedicineID(),
                    qtyRequested, new java.sql.Date(System.currentTimeMillis()));

            dispensingDAO = new DispensingDAO();
            dispensingDAO.insert(d);

            refreshTableData();
            refreshAvailableQtyLabel();

            int remaining = chosenBatch.getAvailableQuantity() - qtyRequested;
            if (remaining < 10) {
                throw new LowStockWarningException(
                    selected.getMedicineName() + " is now low on stock (" + remaining + " units left in batch "
                    + chosenBatch.getBatchNo() + ").");
            }

            JOptionPane.showMessageDialog(this, "Dispensed " + qtyRequested + " unit(s) of " + selected.getMedicineName() + ".");

        } catch (InsufficientStockException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Insufficient Stock", JOptionPane.ERROR_MESSAGE);
        } catch (LowStockWarningException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Low Stock Warning", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error dispensing medicine: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void refreshTableData() {
        tmodel.setRowCount(0);
        try {
            dispensingDAO = new DispensingDAO();
            ArrayList<Dispensing> list = dispensingDAO.getAll();
            for (Dispensing d : list) {
                Object[] rdata = new Object[]{
                    d.getDispensingID(),
                    d.getStockID(),
                    d.getMedicineID(),
                    d.getQtyDispensed(),
                    d.getDispenseDate()
                };
                tmodel.addRow(rdata);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading dispensing history: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}