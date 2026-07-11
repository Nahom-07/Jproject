package pharmacy.ui;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.util.ArrayList;
import javax.swing.table.JTableHeader;
import pharmacy.dao.StockDAO;
import pharmacy.data.Stock;
import javax.swing.table.DefaultTableModel;

public class StockPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tmodel;
    JTextField searchf;
    JButton search;
    private static final Color NAVY_DARK = new Color(24, 30, 54);
    private static final Color ANCCENT_HOVER = new Color(52, 152, 219);
    private static final Color TEXT_DARK = new Color(44, 62, 80);
    private static final Color CANVAS_BG = new Color(240, 244, 248);

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
        btn.setPreferredSize(new Dimension(100, 32));
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

    public static void styleLeftButtons(JButton btn, Color bgColor, Color fgColor) {
        btn.setBackground(bgColor);
        btn.setForeground(fgColor);
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(true);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        btn.setPreferredSize(new Dimension(100, 30));
        btn.setMaximumSize(new Dimension(100, 30));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(ANCCENT_HOVER);
                btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(bgColor);
            }
        });
    }

    public StockPanel() {
        setLayout(new BorderLayout(0, 0));
        setBackground(Color.WHITE);

        JPanel topp = new JPanel();
        topp.setLayout(new BoxLayout(topp, BoxLayout.Y_AXIS));
        topp.setBackground(Color.WHITE);

        JLabel title = new JLabel("Stock");
        title.setFont(new Font("Segoe UI", Font.BOLD, 40));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        title.setBorder(BorderFactory.createEmptyBorder(20, 12, 10, 0));

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 10));
        top.setBackground(Color.WHITE);
        top.setMaximumSize(new Dimension(Integer.MAX_VALUE, 52));
        top.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(218, 224, 233)));

        JButton viewButton    = new JButton("View");
        JButton addButton     = new JButton("Add");
        JButton editButton    = new JButton("Edit");
        JButton deleteButton  = new JButton("Delete");

        styleTopButtons(viewButton,   Color.WHITE, TEXT_DARK);
        styleTopButtons(addButton,    Color.WHITE, TEXT_DARK);
        styleTopButtons(editButton,   Color.WHITE, TEXT_DARK);
        styleTopButtons(deleteButton, Color.WHITE, TEXT_DARK);

        top.add(viewButton);
        top.add(addButton);
        top.add(editButton);
        top.add(deleteButton);

        JPanel search_row = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        search_row.setBackground(Color.WHITE);
        search_row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        JLabel searchl = new JLabel("Search by medicine ID: ");
        searchl.setFont(new Font("Segoe UI", Font.BOLD, 16));
        searchf = new JTextField(10);
        search  = new JButton("Search");
        styleLeftButtons(search, NAVY_DARK, CANVAS_BG);

        search_row.add(searchl);
        search_row.add(searchf);
        search_row.add(search);

        topp.add(title);
        topp.add(top);
        topp.add(search_row);

        add(topp, BorderLayout.NORTH);

        String[] columns = {"Stock ID", "Medicine ID", "Batch Number", "Available Quantity", "Unit Cost", "Expiry Date", "Storage Condition"};
        tmodel = new DefaultTableModel(columns, 0);
        table = new JTable(tmodel) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table.getTableHeader().setReorderingAllowed(false);
        table.setRowHeight(30);
        table.setShowGrid(true);
        table.setGridColor(new Color(230, 235, 240));
        table.setSelectionBackground(new Color(225, 238, 250));
        table.setSelectionForeground(new Color(44, 62, 80));

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(new Color(245, 247, 250));
        header.setForeground(new Color(44, 62, 80));
        header.setPreferredSize(new Dimension(0, 36));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(218, 224, 233)));

        JScrollPane scroll = new JScrollPane(table);
        add(scroll, BorderLayout.CENTER);

        // VIEW
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshTableData();
            }
        });

        // ADD
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddDialog();
            }
        });

        // EDIT
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(StockPanel.this, "Please select a stock row to edit.", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                showEditDialog(selectedRow);
            }
        });

        // DELETE
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(StockPanel.this, "Please select a stock row to delete.", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                int id = (int) tmodel.getValueAt(selectedRow, 0);
                int confirm = JOptionPane.showConfirmDialog(StockPanel.this,
                    "Delete stock ID: " + id + "?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        StockDAO dao = new StockDAO();
                        dao.delete(id);
                        refreshTableData();
                        JOptionPane.showMessageDialog(StockPanel.this, "Stock deleted successfully.");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(StockPanel.this, "Delete failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // SEARCH
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a) {
                String text = searchf.getText().trim();
                if (text.equals("")) {
                    refreshTableData();
                } else {
                    try {
                        int medId = Integer.parseInt(text);
                        filterByMedicineId(medId);
                    } catch (NumberFormatException b) {
                        JOptionPane.showMessageDialog(StockPanel.this, "Please enter a valid medicine ID", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        refreshTableData();
    }

    private void showAddDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Add Stock Batch", true);
        dialog.setSize(420, 420);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(8, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JTextField medIdF      = new JTextField();
        JTextField batchNoF    = new JTextField();
        JTextField qtyF        = new JTextField();
        JTextField costF       = new JTextField();
        JTextField mfgDateF    = new JTextField("YYYY-MM-DD");
        JTextField expDateF    = new JTextField("YYYY-MM-DD");
        JTextField recDateF    = new JTextField("YYYY-MM-DD");
        JTextField storageF    = new JTextField("Room temperature");

        form.add(new JLabel("Medicine ID:"));       form.add(medIdF);
        form.add(new JLabel("Batch No:"));          form.add(batchNoF);
        form.add(new JLabel("Qty Available:"));     form.add(qtyF);
        form.add(new JLabel("Unit Cost:"));         form.add(costF);
        form.add(new JLabel("Manufacture Date:")); form.add(mfgDateF);
        form.add(new JLabel("Expiry Date:"));       form.add(expDateF);
        form.add(new JLabel("Received Date:"));     form.add(recDateF);
        form.add(new JLabel("Storage Condition:")); form.add(storageF);

        JButton saveBtn   = new JButton("Save");
        JButton cancelBtn = new JButton("Cancel");
        saveBtn.setBackground(NAVY_DARK);
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setFocusPainted(false);
        cancelBtn.setFocusPainted(false);

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnRow.add(cancelBtn);
        btnRow.add(saveBtn);

        dialog.add(form, BorderLayout.CENTER);
        dialog.add(btnRow, BorderLayout.SOUTH);

        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { dialog.dispose(); }
        });

        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int medId    = Integer.parseInt(medIdF.getText().trim());
                    String batch = batchNoF.getText().trim();
                    int qty      = Integer.parseInt(qtyF.getText().trim());
                    double cost  = Double.parseDouble(costF.getText().trim());
                    Date mfgDate = Date.valueOf(mfgDateF.getText().trim());
                    Date expDate = Date.valueOf(expDateF.getText().trim());
                    Date recDate = Date.valueOf(recDateF.getText().trim());
                    String storage = storageF.getText().trim();

                    if (batch.isEmpty() || storage.isEmpty()) {
                        JOptionPane.showMessageDialog(dialog, "All fields are required.", "Validation", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    Stock stock = new Stock(0, medId, batch, qty, cost, mfgDate, expDate, recDate, storage);
                    StockDAO dao = new StockDAO();
                    dao.insert(stock);
                    refreshTableData();
                    dialog.dispose();
                    JOptionPane.showMessageDialog(StockPanel.this, "Stock batch added successfully.");
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(dialog, "Invalid date format. Use YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dialog, "Error saving stock: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        dialog.setVisible(true);
    }

    private void showEditDialog(int selectedRow) {
        int id           = (int)    tmodel.getValueAt(selectedRow, 0);
        int medId        = (int)    tmodel.getValueAt(selectedRow, 1);
        String batch     = (String) tmodel.getValueAt(selectedRow, 2);
        int qty          = (int)    tmodel.getValueAt(selectedRow, 3);
        double cost      = (double) tmodel.getValueAt(selectedRow, 4);
        String expDate   = tmodel.getValueAt(selectedRow, 5).toString();
        String storage   = (String) tmodel.getValueAt(selectedRow, 6);

        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Edit Stock", true);
        dialog.setSize(420, 320);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(5, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JTextField qtyF     = new JTextField(String.valueOf(qty));
        JTextField costF    = new JTextField(String.valueOf(cost));
        JTextField expF     = new JTextField(expDate);
        JTextField storageF = new JTextField(storage);
        JTextField batchF   = new JTextField(batch);

        form.add(new JLabel("Batch No:"));          form.add(batchF);
        form.add(new JLabel("Available Qty:"));     form.add(qtyF);
        form.add(new JLabel("Unit Cost:"));         form.add(costF);
        form.add(new JLabel("Expiry Date:"));       form.add(expF);
        form.add(new JLabel("Storage Condition:")); form.add(storageF);

        JButton saveBtn   = new JButton("Update");
        JButton cancelBtn = new JButton("Cancel");
        saveBtn.setBackground(NAVY_DARK);
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setFocusPainted(false);
        cancelBtn.setFocusPainted(false);

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnRow.add(cancelBtn);
        btnRow.add(saveBtn);

        dialog.add(form, BorderLayout.CENTER);
        dialog.add(btnRow, BorderLayout.SOUTH);

        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { dialog.dispose(); }
        });

        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int newQty      = Integer.parseInt(qtyF.getText().trim());
                    double newCost  = Double.parseDouble(costF.getText().trim());
                    Date newExp     = Date.valueOf(expF.getText().trim());
                    String newStore = storageF.getText().trim();
                    String newBatch = batchF.getText().trim();

                    // we need all current dates to reconstruct the Stock object for update
                    StockDAO dao = new StockDAO();
                    Stock current = dao.getById(id);
                    Stock updated = new Stock(id, medId, newBatch, newQty, newCost,
                        current.getManufactureDate(), newExp,
                        current.getReceivedDate(), newStore);
                    dao.update(updated);
                    refreshTableData();
                    dialog.dispose();
                    JOptionPane.showMessageDialog(StockPanel.this, "Stock updated successfully.");
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(dialog, "Invalid date format. Use YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dialog, "Error updating stock: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        dialog.setVisible(true);
    }

    private void filterByMedicineId(int medId) {
        tmodel.setRowCount(0);
        try {
            StockDAO dao = new StockDAO();
            ArrayList<Stock> list = dao.getByMedId(medId);
            for (Stock s : list) {
                tmodel.addRow(new Object[]{
                    s.getStockID(), s.getMedicineID(), s.getBatchNo(),
                    s.getAvailableQuantity(), s.getUnitCost(),
                    s.getExpiryDate(), s.getStorageCondition()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Search Error: " + ex.getMessage());
        }
    }

    public void refreshTableData() {
        tmodel.setRowCount(0);
        try {
            StockDAO dao = new StockDAO();
            ArrayList<Stock> list = dao.getAll();
            for (Stock s : list) {
                tmodel.addRow(new Object[]{
                    s.getStockID(), s.getMedicineID(), s.getBatchNo(),
                    s.getAvailableQuantity(), s.getUnitCost(),
                    s.getExpiryDate(), s.getStorageCondition()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error loading stock: " + ex.getMessage());
        }
    }
}