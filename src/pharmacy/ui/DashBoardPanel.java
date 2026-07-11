package pharmacy.ui;
import pharmacy.dao.DispensingDAO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class DashBoardPanel extends JPanel{

    

    private static void styleTopButtons(JButton btn) {
        Color navy = new Color(28, 37, 65);
        btn.setBackground(navy);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setFont(new Font("Arial", Font.PLAIN, 13));
        btn.setPreferredSize(new Dimension(180, 80));
    }
    public DashBoardPanel(){
        setLayout(new BorderLayout());

        JLabel dLable = new JLabel("DashBoard");
        dLable.setLayout(new BorderLayout());
        add(dLable, BorderLayout.NORTH);
        dLable.setFont(new Font("Arial", Font.BOLD, 24));
        dLable.setHorizontalAlignment(SwingConstants.CENTER);


        JPanel top = new JPanel();
        top.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton iButton = new JButton("Inventory count");
        JButton lsButton = new JButton("Low stocks");

        styleTopButtons(iButton);
        styleTopButtons(lsButton);
        top.add(iButton);
        top.add(lsButton);
        add(top, BorderLayout.CENTER);
        
        // String[] colms = {"Dispensing ID", "", "", "", ""};
        // try{
        //     DispensingDAO disDAO = new DispensingDAO();
        //     JTable t = new JTable();t = disDAO.getAll();
        //     JPanel bottom = new JPanel();
        //     bottom.add();
        // } catch(SQLException e) {
        //     System.out.println(e.getMessage());
        // }

        

        
        
    }
}