package pharmacy.ui;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import pharmacy.dao.MedicineDAO;
import pharmacy.data.Medicine;

public class MedicinePanel extends JPanel {

    private DefaultTableModel tmodel;
    private MedicineDAO medicineDAO;
    private JTextField searchf;
    private JButton search;
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

    public MedicinePanel() {
        setLayout(new BorderLayout(0, 0));
        setBackground(Color.WHITE);

        JPanel topp = new JPanel();
        topp.setLayout(new BoxLayout(topp, BoxLayout.Y_AXIS));
        topp.setBackground(Color.WHITE);

        JLabel title = new JLabel("Medicine Inventory");
        title.setFont(new Font("Segoe UI", Font.BOLD, 40));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        title.setBorder(BorderFactory.createEmptyBorder(20, 12, 10, 0));

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 10));
        top.setBackground(Color.WHITE);
        top.setMaximumSize(new Dimension(Integer.MAX_VALUE, 52));
        top.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(218, 224, 233)));

        JButton viewButton = new JButton("View");
        JButton addButton = new JButton("Add");
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");
        JButton dispenseButton = new JButton("Dispense");

        styleTopButtons(viewButton, Color.WHITE, TEXT_DARK);
        styleTopButtons(addButton, Color.WHITE, TEXT_DARK);
        styleTopButtons(editButton, Color.WHITE, TEXT_DARK);
        styleTopButtons(deleteButton, Color.WHITE, TEXT_DARK);
        styleTopButtons(dispenseButton, Color.WHITE, TEXT_DARK);

        top.add(viewButton);
        top.add(addButton);
        top.add(editButton);
        top.add(deleteButton);
        top.add(dispenseButton);

        JPanel search_row = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        search_row.setBackground(Color.WHITE);
        search_row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        JLabel searchl = new JLabel("Search by Name : ");
        searchl.setFont(new Font("Segoe UI", Font.BOLD, 16));
        searchf = new JTextField(10);
        search = new JButton("Search");
        styleLeftButtons(search, NAVY_DARK, CANVAS_BG);

        search_row.add(searchl);
        search_row.add(searchf);
        search_row.add(search);

        topp.add(title);
        topp.add(top);
        topp.add(search_row);

        add(topp, BorderLayout.NORTH);

        String[] columnnames = {"ID", "Name", "Generic Name", "Category", "Dosage Form", "Unit", "Reorder Level"};
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

        // --- BUTTON ACTION LISTENERS ---

        // VIEW — refreshes the table
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshTableData();
            }
        });

        // ADD — opens a form dialog to add a new medicine
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddDialog();
            }
        });

        // EDIT — opens a form dialog pre-filled with selected row data
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = t.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(MedicinePanel.this, "Please select a medicine to edit.", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                showEditDialog(selectedRow);
            }
        });

        // DELETE — deletes selected row after confirmation
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = t.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(MedicinePanel.this, "Please select a medicine to delete.", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                int id = (int) tmodel.getValueAt(selectedRow, 0);
                int confirm = JOptionPane.showConfirmDialog(MedicinePanel.this,
                    "Are you sure you want to delete medicine ID: " + id + "?",
                    "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        medicineDAO = new MedicineDAO();
                        medicineDAO.delete(id);
                        refreshTableData();
                        JOptionPane.showMessageDialog(MedicinePanel.this, "Medicine deleted successfully.");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(MedicinePanel.this, "Delete failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // SEARCH
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = searchf.getText().trim();
                if (text.equals("")) {
                    refreshTableData();
                } else {
                    filterTableByName(text);
                }
            }
        });

        refreshTableData();
    }

    private void showAddDialog() {
        // JDialog — modal popup for adding a new medicine
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Add Medicine", true);
        dialog.setSize(400, 380);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(7, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JTextField nameF = new JTextField();
        JTextField genericF = new JTextField();
        JTextField categoryF = new JTextField();
        JTextField dosageF = new JTextField();
        JTextField unitF = new JTextField();
        JTextField reorderF = new JTextField();

        form.add(new JLabel("Medicine Name:"));   form.add(nameF);
        form.add(new JLabel("Generic Name:"));    form.add(genericF);
        form.add(new JLabel("Category:"));        form.add(categoryF);
        form.add(new JLabel("Dosage Form:"));     form.add(dosageF);
        form.add(new JLabel("Unit:"));            form.add(unitF);
        form.add(new JLabel("Reorder Level:"));   form.add(reorderF);

        JButton saveBtn = new JButton("Save");
        saveBtn.setBackground(NAVY_DARK);
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setFocusPainted(false);

        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.setFocusPainted(false);

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnRow.add(cancelBtn);
        btnRow.add(saveBtn);

        dialog.add(form, BorderLayout.CENTER);
        dialog.add(btnRow, BorderLayout.SOUTH);

        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // validate that no field is empty
                if (nameF.getText().trim().isEmpty() ||
                    genericF.getText().trim().isEmpty() ||
                    categoryF.getText().trim().isEmpty() ||
                    dosageF.getText().trim().isEmpty() ||
                    unitF.getText().trim().isEmpty() ||
                    reorderF.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "All fields are required.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    int reorderLevel = Integer.parseInt(reorderF.getText().trim());
                    // medicineID is 0 here — database assigns the real ID via IDENTITY
                    Medicine medicine = new Medicine(0,
                        nameF.getText().trim(),
                        genericF.getText().trim(),
                        categoryF.getText().trim(),
                        dosageF.getText().trim(),
                        unitF.getText().trim(),
                        reorderLevel);
                    medicineDAO = new MedicineDAO();
                    medicineDAO.insert(medicine);
                    refreshTableData();
                    dialog.dispose();
                    JOptionPane.showMessageDialog(MedicinePanel.this, "Medicine added successfully.");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialog, "Reorder Level must be a number.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dialog, "Error saving medicine: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        dialog.setVisible(true);
    }

    private void showEditDialog(int selectedRow) {
        int id = (int) tmodel.getValueAt(selectedRow, 0);
        String currentName     = (String) tmodel.getValueAt(selectedRow, 1);
        String currentGeneric  = (String) tmodel.getValueAt(selectedRow, 2);
        String currentCategory = (String) tmodel.getValueAt(selectedRow, 3);
        String currentDosage   = (String) tmodel.getValueAt(selectedRow, 4);
        String currentUnit     = (String) tmodel.getValueAt(selectedRow, 5);
        int currentReorder     = (int) tmodel.getValueAt(selectedRow, 6);

        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Edit Medicine", true);
        dialog.setSize(400, 380);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(7, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        // pre-fill fields with current values
        JTextField nameF     = new JTextField(currentName);
        JTextField genericF  = new JTextField(currentGeneric);
        JTextField categoryF = new JTextField(currentCategory);
        JTextField dosageF   = new JTextField(currentDosage);
        JTextField unitF     = new JTextField(currentUnit);
        JTextField reorderF  = new JTextField(String.valueOf(currentReorder));

        form.add(new JLabel("Medicine Name:"));   form.add(nameF);
        form.add(new JLabel("Generic Name:"));    form.add(genericF);
        form.add(new JLabel("Category:"));        form.add(categoryF);
        form.add(new JLabel("Dosage Form:"));     form.add(dosageF);
        form.add(new JLabel("Unit:"));            form.add(unitF);
        form.add(new JLabel("Reorder Level:"));   form.add(reorderF);

        JButton saveBtn = new JButton("Update");
        saveBtn.setBackground(NAVY_DARK);
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setFocusPainted(false);

        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.setFocusPainted(false);

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnRow.add(cancelBtn);
        btnRow.add(saveBtn);

        dialog.add(form, BorderLayout.CENTER);
        dialog.add(btnRow, BorderLayout.SOUTH);

        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (nameF.getText().trim().isEmpty() ||
                    genericF.getText().trim().isEmpty() ||
                    categoryF.getText().trim().isEmpty() ||
                    dosageF.getText().trim().isEmpty() ||
                    unitF.getText().trim().isEmpty() ||
                    reorderF.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "All fields are required.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    int reorderLevel = Integer.parseInt(reorderF.getText().trim());
                    Medicine medicine = new Medicine(id,
                        nameF.getText().trim(),
                        genericF.getText().trim(),
                        categoryF.getText().trim(),
                        dosageF.getText().trim(),
                        unitF.getText().trim(),
                        reorderLevel);
                    medicineDAO = new MedicineDAO();
                    medicineDAO.update(medicine);
                    refreshTableData();
                    dialog.dispose();
                    JOptionPane.showMessageDialog(MedicinePanel.this, "Medicine updated successfully.");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialog, "Reorder Level must be a number.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dialog, "Error updating medicine: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        dialog.setVisible(true);
    }

    public void refreshTableData() {
        tmodel.setRowCount(0);
        try {
            medicineDAO = new MedicineDAO();
            ArrayList<Medicine> list = medicineDAO.getAll();
            for (Medicine m : list) {
                Object[] rdata = new Object[]{
                    m.getMedicineID(),
                    m.getMedicineName(),
                    m.getGenericName(),
                    m.getCategory(),
                    m.getDosageForm(),
                    m.getUnit(),
                    m.getReorderLevel()
                };
                tmodel.addRow(rdata);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading medicines: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void filterTableByName(String name) {
        tmodel.setRowCount(0);
        try {
            ArrayList<Medicine> list = medicineDAO.getByName(name);
            for (Medicine m : list) {
                Object[] rdata = new Object[]{
                    m.getMedicineID(),
                    m.getMedicineName(),
                    m.getGenericName(),
                    m.getCategory(),
                    m.getDosageForm(),
                    m.getUnit(),
                    m.getReorderLevel()
                };
                tmodel.addRow(rdata);
            }
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(MedicinePanel.this, "Search error: " + exc.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}