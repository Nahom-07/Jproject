package pharmacy.ui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class MainWindow {


    public static void styleTopButtons(JButton btn) {
        Color navy = new Color(28, 37, 65);
        btn.setBackground(navy);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setFont(new Font("Arial", Font.PLAIN, 13));
        btn.setPreferredSize(new Dimension(100, 30));
    }
    
    public static void styleLeftButtons(JButton btn) {
        Color navy = new Color(28, 37, 65);
        btn.setBackground(navy);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setFont(new Font("Arial", Font.PLAIN, 13));
        btn.setPreferredSize(new Dimension(150, 40));
        btn.setMaximumSize(new Dimension(150, 40));
        //btn.setMinimumSize(new Dimension(150, 40));

        
    }
    public static void main(String[] args) {

        JFrame f = new JFrame("Pharmacy Management System");
        f.setSize(1200, 700);
        f.setMinimumSize(new Dimension(800, 600));
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Color navy = new Color(28, 37, 65);
        Color lightBlue = new Color(255, 255, 255);

        CardLayout cl = new CardLayout();
        JPanel main = new JPanel(cl);

        DashBoardPanel dashboardPanel = new DashBoardPanel();

        JPanel inventoryPanel = new JPanel();
        inventoryPanel.setBackground(lightBlue);
        inventoryPanel.add(new JLabel("Inventory"));

        JPanel stockPanel = new JPanel();
        stockPanel.setBackground(lightBlue);
        stockPanel.add(new JLabel("Stock"));

        JPanel dispensingPanel = new JPanel();
        dispensingPanel.setBackground(lightBlue);
        dispensingPanel.add(new JLabel("Dispensing"));

        JPanel purchaseOrderPanel = new JPanel();
        purchaseOrderPanel.setBackground(lightBlue);
        purchaseOrderPanel.add(new JLabel("Purchase Orders"));

        main.add(dashboardPanel, "dashboard");
        main.add(inventoryPanel, "inventory");
        main.add(stockPanel, "stock");
        main.add(dispensingPanel, "dispensing");
        main.add(purchaseOrderPanel, "purchaseorders");




        cl.show(main, "dashboard");



        main.setBackground(lightBlue);



        JPanel top = new JPanel();
        top.setLayout(new FlowLayout(FlowLayout.LEFT, 3, 3));

        JButton viewButton = new JButton("View");
        JButton addButton = new JButton("Add");
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");
        JButton dispenseButton = new JButton("Dispense");

        

        styleTopButtons(viewButton);
        styleTopButtons(addButton);
        styleTopButtons(editButton);
        styleTopButtons(deleteButton);
        styleTopButtons(dispenseButton);

        top.add(viewButton);
        top.add(addButton);
        top.add(editButton);
        top.add(deleteButton);
        top.add(dispenseButton);

        top.setBackground(lightBlue);
        //f.add(top, BorderLayout.NORTH);
        JPanel right = new JPanel(new BorderLayout());

        right.add(top, BorderLayout.NORTH);
        right.add(main, BorderLayout.CENTER);

        //right.setBackground(brown);

        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));

        JButton logoButton = new JButton("PharmacyDB");
        JButton medicineButton = new JButton("Dashboard");
        JButton inventoryButton = new JButton("Inventory");
        JButton stockButton = new JButton("Stock");
        JButton dispenseButton2 = new JButton("Dispense");
        JButton purchaseOrderButton = new JButton("Purchase Orders");
        
        medicineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                cl.show(main, "dashboard");
            }
        });

        inventoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                cl.show(main, "inventory");
            }
        });

        stockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                cl.show(main, "stock");
            }
        });


        styleLeftButtons(logoButton);
        styleLeftButtons(medicineButton);
        styleLeftButtons(inventoryButton);
        styleLeftButtons(stockButton);
        styleLeftButtons(dispenseButton2);
        styleLeftButtons(purchaseOrderButton);

        left.add(logoButton);
        left.add(medicineButton);
        left.add(inventoryButton);
        left.add(stockButton);
        left.add(dispenseButton2);
        left.add(purchaseOrderButton);

        left.setBackground(navy);


  

        right.setBackground(lightBlue);

        JPanel bottom = new JPanel();
        bottom.setBackground(lightBlue);

        bottom.setPreferredSize(new Dimension(0, 20));

        bottom.add(new JLabel("© 2026 Pharmacy Management System. All rights reserved."));

        f.add(left, BorderLayout.WEST);
        f.add(right, BorderLayout.CENTER);
        f.add(bottom, BorderLayout.SOUTH);



        f.setVisible(true);

        
    }
}
