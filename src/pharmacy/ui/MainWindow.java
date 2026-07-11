package pharmacy.ui;
 import javax.swing.*;
 import javax.swing.border.Border;
 import java.awt.*;
 import java.awt.event.*;

public class MainWindow{
    private static final Color NAVY_DARK = new Color (24, 30 , 54);
    private static final Color CANVAS_BG = new Color (240, 244, 248);
    private static final Color ANCCENT_BLUE = new Color (41, 128 , 148);
    private static final Color ANCCENT_HOVER = new Color (52, 152, 219);
    private static final Color TEXT_DARK = new Color (44, 62, 80);



    public static void styleLeftButtons(JButton btn, Color bgColor, Color fgColor) {
        btn.setBackground(bgColor);
        btn.setForeground(fgColor);

        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(true);

        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));

        btn.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));

        btn.setPreferredSize(new Dimension(180, 44));
        btn.setMaximumSize(new Dimension(180, 44));

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

    public static void main(String[] args) {

        JFrame f = new JFrame("Belachew Pharmacy Management System");
        f.setSize(1250, 750);
        f.setMinimumSize(new Dimension(900, 650));
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
        CardLayout cl = new CardLayout();

        JPanel main = new JPanel(cl);
        main.setBackground(CANVAS_BG);   
        PurchaseOrderPanel purchaseOrderPanel = new PurchaseOrderPanel();
        DashBoardPanel dashboardPanel = new DashBoardPanel();
        MedicinePanel inventoryPanel = new MedicinePanel();
        StockPanel stockPanel = new StockPanel();
        DispensingPanel dispensingPanel = new DispensingPanel();
        main.add(dashboardPanel, "dashboard");
        main.add(inventoryPanel, "inventory");
        main.add(stockPanel, "stock");
        main.add(dispensingPanel, "dispensing");
        main.add(purchaseOrderPanel, "purchaseorders");
        cl.show(main,"dashboard");

        


        JPanel right = new JPanel(new BorderLayout());

        

        right.add(main, BorderLayout.CENTER);

        JPanel left = new JPanel();

        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));

        left.setBackground(NAVY_DARK);

        left.setBorder(BorderFactory.createMatteBorder(
                0, 0, 0, 1,
                new Color(35, 45, 75)
        ));

        JLabel logoLabel = new JLabel("PharmacyDB");

        logoLabel.setForeground(Color.WHITE);

        logoLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));

        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        logoLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JButton medicineButton = new JButton("Dashboard");

        JButton inventoryButton = new JButton("Inventory");

        JButton stockButton = new JButton("Stock");

        JButton dispenseButton2 = new JButton("Dispense");

        JButton purchaseOrderButton = new JButton("Purchase Orders");

        medicineButton.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {

            cl.show(main, "dashboard");

        }

    });

    inventoryButton.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {

            cl.show(main, "inventory");
        }
    });

    stockButton.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {

            cl.show(main, "stock");

        }

    });



    purchaseOrderButton.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {

            cl.show(main, "purchaseorders");

        }

    });

    dispenseButton2.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {

            cl.show(main, "dispensing");

        }

    });

    styleLeftButtons(medicineButton, NAVY_DARK, Color.WHITE);

    styleLeftButtons(inventoryButton, NAVY_DARK, Color.WHITE);

    styleLeftButtons(stockButton, NAVY_DARK, Color.WHITE);

    styleLeftButtons(dispenseButton2, NAVY_DARK, Color.WHITE);

    styleLeftButtons(purchaseOrderButton, NAVY_DARK, Color.WHITE);

    left.add(logoLabel);

    left.add(Box.createRigidArea(new Dimension(0, 10)));

    left.add(medicineButton);

    left.add(Box.createRigidArea(new Dimension(0, 5)));

    left.add(inventoryButton);

    left.add(Box.createRigidArea(new Dimension(0, 5)));

    left.add(stockButton);

    left.add(Box.createRigidArea(new Dimension(0, 5)));

    left.add(dispenseButton2);

    left.add(Box.createRigidArea(new Dimension(0, 5)));

    left.add(purchaseOrderButton);

    left.add(Box.createVerticalGlue());

    right.setBackground(CANVAS_BG);

    JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER));

    bottom.setBackground(Color.WHITE);

    bottom.setBorder(BorderFactory.createMatteBorder(
            1, 0, 0, 0,
            new Color(218, 224, 233)
    ));

    JLabel copyrightLabel =
            new JLabel("© 2026 Pharmacy Management System. All rights reserved.");

    copyrightLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));

    copyrightLabel.setForeground(new Color(120, 130, 140));

    bottom.add(copyrightLabel);

    f.add(left, BorderLayout.WEST);

    f.add(right, BorderLayout.CENTER);

    f.add(bottom, BorderLayout.SOUTH);

    f.setLocationRelativeTo(null);

    f.setVisible(true);

}

private static JPanel createPlaceholderPanel(String text) {

    JPanel panel = new JPanel(new GridBagLayout());

    panel.setBackground(CANVAS_BG);

    JLabel label = new JLabel(text);

    label.setFont(new Font("Segoe UI", Font.BOLD, 18));

    label.setForeground(TEXT_DARK);

    panel.add(label);

    return panel;

}
 }

