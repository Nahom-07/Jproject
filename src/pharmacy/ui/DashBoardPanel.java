package pharmacy.ui;
import pharmacy.dao.DispensingDAO;
import pharmacy.dao.MedicineDAO;
import pharmacy.dao.StockDAO;
import pharmacy.data.Dispensing;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class DashBoardPanel extends JPanel {

    private static final Color NAVY_DARK = new Color(24, 30, 54);
    private static final Color TEXT_DARK = new Color(44, 62, 80);

    private JButton iButton;
    private JButton lsButton;

    private static void styleCard(JButton btn, Color bg) {
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setPreferredSize(new Dimension(200, 90));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public DashBoardPanel() {
        setLayout(new BorderLayout(0, 10));
        setBackground(Color.WHITE);

        // title
        JLabel dLabel = new JLabel("Dashboard");
        dLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        dLabel.setForeground(TEXT_DARK);
        dLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 0));
        add(dLabel, BorderLayout.NORTH);

        // cards panel
        JPanel cardsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        cardsPanel.setBackground(Color.WHITE);
        cardsPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));

        iButton = new JButton("Total Medicines: ...");
        lsButton = new JButton("Low Stock (< 10): ...");

        styleCard(iButton, NAVY_DARK);
        styleCard(lsButton, new Color(192, 57, 43));

        cardsPanel.add(iButton);
        cardsPanel.add(lsButton);

        // center panel holds cards + table label
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(Color.WHITE);

        centerPanel.add(cardsPanel);

        JLabel recentLabel = new JLabel("Recent Dispensing Activity");
        recentLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        recentLabel.setForeground(TEXT_DARK);
        recentLabel.setBorder(BorderFactory.createEmptyBorder(15, 20, 5, 0));
        centerPanel.add(recentLabel);

        add(centerPanel, BorderLayout.CENTER);

        // recent dispensing table
        String[] columnNames = {"Dispensing ID", "Stock ID", "Medicine ID", "Qty Dispensed", "Dispense Date"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable t = new JTable(model);
        t.getTableHeader().setReorderingAllowed(false);
        t.setRowHeight(30);
        t.setShowGrid(true);
        t.setGridColor(new Color(230, 235, 240));
        t.setSelectionBackground(new Color(225, 238, 250));
        t.setSelectionForeground(TEXT_DARK);

        javax.swing.table.JTableHeader header = t.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(new Color(245, 247, 250));
        header.setForeground(TEXT_DARK);
        header.setPreferredSize(new Dimension(0, 36));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(218, 224, 233)));

        JScrollPane scroll = new JScrollPane(t);
        scroll.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
        add(scroll, BorderLayout.SOUTH);

        // load real data
        loadCardData();
        loadRecentDispensing(model);

        // low stock dialog on button click
        lsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showLowStockDialog();
            }
        });
    }

    private void loadCardData() {
        try {
            MedicineDAO mDAO = new MedicineDAO();
            int totalMedicines = mDAO.getAll().size();
            iButton.setText("Total Medicines: " + totalMedicines);

            StockDAO sDAO = new StockDAO();
            int lowStockCount = sDAO.getByLowStock().size();
            lsButton.setText("Low Stock (< 10): " + lowStockCount);

        } catch (SQLException e) {
            iButton.setText("Total Medicines: Error");
            lsButton.setText("Low Stock: Error");
        }
    }

    private void loadRecentDispensing(DefaultTableModel model) {
        try {
            DispensingDAO disDAO = new DispensingDAO();
            ArrayList<Dispensing> list = disDAO.getAll();
            // show last 10 only
            int start = Math.max(0, list.size() - 10);
            for (int i = start; i < list.size(); i++) {
                Dispensing d = list.get(i);
                model.addRow(new Object[]{
                    d.getDispensingID(),
                    d.getStockID(),
                    d.getMedicineID(),
                    d.getQtyDispensed(),
                    d.getDispenseDate()
                });
            }
        } catch (SQLException e) {
            System.out.println("Error loading dispensing: " + e.getMessage());
        }
    }

    private void showLowStockDialog() {
        try {
            StockDAO sDAO = new StockDAO();
            ArrayList<pharmacy.data.Stock> lowList = sDAO.getByLowStock();

            String[] cols = {"Stock ID", "Medicine ID", "Batch No", "Available Qty", "Expiry Date"};
            DefaultTableModel lModel = new DefaultTableModel(cols, 0) {
                @Override
                public boolean isCellEditable(int row, int col) { return false; }
            };

            for (pharmacy.data.Stock s : lowList) {
                lModel.addRow(new Object[]{
                    s.getStockID(),
                    s.getMedicineID(),
                    s.getBatchNo(),
                    s.getAvailableQuantity(),
                    s.getExpiryDate()
                });
            }

            JTable lowTable = new JTable(lModel);
            lowTable.setRowHeight(28);
            lowTable.getTableHeader().setReorderingAllowed(false);

            // JDialog — popup window attached to parent, true = modal
            JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Low Stock Items", true);
            dialog.setSize(600, 350);
            dialog.setLocationRelativeTo(this);
            dialog.add(new JScrollPane(lowTable), BorderLayout.CENTER);

            JButton closeBtn = new JButton("Close");
            closeBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dialog.dispose();
                }
            });

            JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            btnPanel.add(closeBtn);
            dialog.add(btnPanel, BorderLayout.SOUTH);
            dialog.setVisible(true);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading low stock: " + e.getMessage());
        }
    }
}