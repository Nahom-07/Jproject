package pharmacy.ui;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.util.ArrayList;
import pharmacy.dao.PurchaseOrderDAO;
import pharmacy.data.PurchaseOrder;

public class PurchaseOrderPanel extends JPanel {

    private static final Color NAVY_DARK   = new Color(24, 30, 54);
    private static final Color ACCENT_BLUE = new Color(41, 128, 185);
    private static final Color TEXT_DARK   = new Color(44, 62, 80);
    private static final Color TEXT_LIGHT  = new Color(255, 255, 255);
    private static final Color CANVAS_BG   = new Color(240, 244, 248);

    private final Font titleFont  = new Font("Segoe UI", Font.BOLD, 22);
    private final Font normalFont = new Font("Segoe UI", Font.PLAIN, 13);
    private final Font headerFont = new Font("Segoe UI", Font.BOLD, 12);

    private JTextField searchField;
    private JTable orderTable;
    private DefaultTableModel tableModel;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton refreshButton;

    public PurchaseOrderPanel() {
        setLayout(new BorderLayout(0, 0));
        setBackground(CANVAS_BG);

        JPanel topContainer = new JPanel();
        topContainer.setLayout(new BoxLayout(topContainer, BoxLayout.Y_AXIS));
        topContainer.setBackground(CANVAS_BG);
        topContainer.add(createTitlePanel());
        topContainer.add(createSearchPanel());

        add(topContainer, BorderLayout.NORTH);
        add(createTablePanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);

        loadPurchaseOrders();
    }

    private JPanel createTitlePanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panel.setBackground(CANVAS_BG);
        panel.setBorder(BorderFactory.createEmptyBorder(18, 20, 8, 20));
        JLabel titleLabel = new JLabel("Purchase Orders");
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(TEXT_DARK);
        panel.add(titleLabel);
        return panel;
    }

    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panel.setBackground(CANVAS_BG);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 20, 15, 20));

        JLabel searchLabel = new JLabel("Search by Status:");
        searchLabel.setFont(normalFont);
        searchLabel.setForeground(TEXT_DARK);

        searchField = new JTextField(25);
        searchField.setFont(normalFont);
        searchField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 204, 210), 1),
            BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));

        JButton searchButton = new JButton("Search");
        styleButton(searchButton, ACCENT_BLUE, TEXT_LIGHT);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String keyword = searchField.getText().trim();
                if (keyword.isEmpty()) {
                    loadPurchaseOrders();
                } else {
                    searchByStatus(keyword);
                }
            }
        });

        panel.add(searchLabel);
        panel.add(searchField);
        panel.add(searchButton);
        return panel;
    }

    private JPanel createTablePanel() {
        String[] columns = {"Order ID", "Order Date", "Expected Date", "Received Date", "Status", "Total Amount"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        orderTable = new JTable(tableModel);
        orderTable.setFont(normalFont);
        orderTable.setRowHeight(30);
        orderTable.setShowGrid(true);
        orderTable.setGridColor(new Color(230, 235, 240));
        orderTable.setSelectionBackground(new Color(225, 238, 250));
        orderTable.setSelectionForeground(TEXT_DARK);
        orderTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        orderTable.getTableHeader().setReorderingAllowed(false);

        javax.swing.table.JTableHeader header = orderTable.getTableHeader();
        header.setFont(headerFont);
        header.setBackground(new Color(245, 247, 250));
        header.setForeground(TEXT_DARK);
        header.setPreferredSize(new Dimension(0, 36));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(218, 224, 233)));

        JScrollPane scrollPane = new JScrollPane(orderTable);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(218, 224, 233), 1));

        JPanel container = new JPanel(new BorderLayout());
        container.setBackground(CANVAS_BG);
        container.setBorder(BorderFactory.createEmptyBorder(0, 20, 10, 20));
        container.add(scrollPane, BorderLayout.CENTER);
        return container;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        panel.setBackground(CANVAS_BG);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        addButton     = new JButton("Add");
        editButton    = new JButton("Edit Status");
        deleteButton  = new JButton("Delete");
        refreshButton = new JButton("Refresh");

        styleButton(addButton,     ACCENT_BLUE,              TEXT_LIGHT);
        styleButton(editButton,    new Color(127, 140, 141), TEXT_LIGHT);
        styleButton(deleteButton,  new Color(192, 57, 43),   TEXT_LIGHT);
        styleButton(refreshButton, NAVY_DARK,                TEXT_LIGHT);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { showAddDialog(); }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = orderTable.getSelectedRow();
                if (row == -1) {
                    JOptionPane.showMessageDialog(PurchaseOrderPanel.this, "Please select an order to edit.", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                showEditDialog(row);
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = orderTable.getSelectedRow();
                if (row == -1) {
                    JOptionPane.showMessageDialog(PurchaseOrderPanel.this, "Please select an order to delete.", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                int id = (int) tableModel.getValueAt(row, 0);
                int confirm = JOptionPane.showConfirmDialog(PurchaseOrderPanel.this,
                    "Delete Order ID: " + id + "?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        PurchaseOrderDAO dao = new PurchaseOrderDAO();
                        dao.delete(id);
                        loadPurchaseOrders();
                        JOptionPane.showMessageDialog(PurchaseOrderPanel.this, "Order deleted.");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(PurchaseOrderPanel.this, "Delete failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { loadPurchaseOrders(); }
        });

        panel.add(addButton);
        panel.add(editButton);
        panel.add(deleteButton);
        panel.add(refreshButton);
        return panel;
    }

    private void showAddDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Add Purchase Order", true);
        dialog.setSize(380, 280);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(4, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JTextField expectedF = new JTextField("YYYY-MM-DD");
        JTextField amountF   = new JTextField("0.00");
        JComboBox statusBox  = new JComboBox(new String[]{"draft", "submitted", "approved", "received", "closed"});

        form.add(new JLabel("Expected Date:"));  form.add(expectedF);
        form.add(new JLabel("Total Amount:"));   form.add(amountF);
        form.add(new JLabel("Status:"));         form.add(statusBox);

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
                    Date today    = new Date(System.currentTimeMillis());
                    Date expected = Date.valueOf(expectedF.getText().trim());
                    double amount = Double.parseDouble(amountF.getText().trim());
                    String status = (String) statusBox.getSelectedItem();

                    // orderID 0 — database assigns it via IDENTITY
                    // receivedDate null — not received yet
                    PurchaseOrder po = new PurchaseOrder(0, today, expected, null, status, amount);
                    PurchaseOrderDAO dao = new PurchaseOrderDAO();
                    dao.insert(po);
                    loadPurchaseOrders();
                    dialog.dispose();
                    JOptionPane.showMessageDialog(PurchaseOrderPanel.this, "Purchase Order added successfully.");
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(dialog, "Invalid date. Use YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dialog, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        dialog.setVisible(true);
    }

    private void showEditDialog(int row) {
        int id             = (int)    tableModel.getValueAt(row, 0);
        String currentStatus = (String) tableModel.getValueAt(row, 4);

        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Edit Order Status", true);
        dialog.setSize(320, 180);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(2, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JComboBox statusBox = new JComboBox(new String[]{"draft", "submitted", "approved", "received", "closed"});
        statusBox.setSelectedItem(currentStatus);

        JTextField receivedF = new JTextField("YYYY-MM-DD or leave blank");

        form.add(new JLabel("New Status:"));    form.add(statusBox);
        form.add(new JLabel("Received Date:")); form.add(receivedF);

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
                    String newStatus = (String) statusBox.getSelectedItem();
                    String recText   = receivedF.getText().trim();

                    PurchaseOrderDAO dao = new PurchaseOrderDAO();
                    PurchaseOrder current = dao.getById(id);

                    Date recDate = null;
                    if (!recText.isEmpty() && !recText.equals("YYYY-MM-DD or leave blank")) {
                        recDate = Date.valueOf(recText);
                    }

                    PurchaseOrder updated = new PurchaseOrder(id,
                        current.getOrderDate(),
                        current.getExpectedDate(),
                        recDate,
                        newStatus,
                        current.getTotalAmount());

                    dao.update(updated);
                    loadPurchaseOrders();
                    dialog.dispose();
                    JOptionPane.showMessageDialog(PurchaseOrderPanel.this, "Order updated successfully.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dialog, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        dialog.setVisible(true);
    }

    private void loadPurchaseOrders() {
        tableModel.setRowCount(0);
        try {
            PurchaseOrderDAO dao = new PurchaseOrderDAO();
            ArrayList<PurchaseOrder> list = dao.getAll();
            for (PurchaseOrder po : list) {
                tableModel.addRow(new Object[]{
                    po.getOrderID(),
                    po.getOrderDate(),
                    po.getExpectedDate(),
                    po.getReceivedDate(),
                    po.getStatus(),
                    po.getTotalAmount()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error loading orders: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchByStatus(String status) {
        tableModel.setRowCount(0);
        try {
            PurchaseOrderDAO dao = new PurchaseOrderDAO();
            ArrayList<PurchaseOrder> list = dao.getByStatus(status);
            for (PurchaseOrder po : list) {
                tableModel.addRow(new Object[]{
                    po.getOrderID(),
                    po.getOrderDate(),
                    po.getExpectedDate(),
                    po.getReceivedDate(),
                    po.getStatus(),
                    po.getTotalAmount()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Search error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void styleButton(JButton button, Color bgColor, Color fgColor) {
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        button.setPreferredSize(new Dimension(110, 34));
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.brighter());
                button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });
    }
}