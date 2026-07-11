// import javax.swing.*;
// import java.awt.*;

// /**
//  * LayoutDemo.java
//  *
//  * A single-file, runnable tour of the major AWT/Swing layout managers.
//  * Each tab is built with a different LayoutManager so you can compare
//  * them side by side.
//  *
//  * Compile:  javac LayoutDemo.java
//  * Run:      java LayoutDemo
//  */
// public class LayoutDemo {

//     public static void main(String[] args) {
//         SwingUtilities.invokeLater(LayoutDemo::createAndShowGui);
//     }

//     private static void createAndShowGui() {
//         JFrame frame = new JFrame("Java Swing Layout Manager Tour");
//         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         frame.setSize(700, 500);
//         frame.setLocationRelativeTo(null);

//         JTabbedPane tabs = new JTabbedPane();
//         tabs.addTab("FlowLayout", flowLayoutDemo());
//         tabs.addTab("BorderLayout", borderLayoutDemo());
//         tabs.addTab("GridLayout", gridLayoutDemo());
//         tabs.addTab("BoxLayout", boxLayoutDemo());
//         tabs.addTab("GridBagLayout", gridBagLayoutDemo());
//         tabs.addTab("CardLayout", cardLayoutDemo());
//         tabs.addTab("Null Layout", nullLayoutDemo());

//         frame.setContentPane(tabs);
//         frame.setVisible(true);
//     }

//     // ---------------------------------------------------------------
//     // 1. FlowLayout — components wrap left-to-right like text
//     // ---------------------------------------------------------------
//     private static JPanel flowLayoutDemo() {
//         JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
//         panel.setBorder(BorderFactory.createTitledBorder("FlowLayout(LEFT, hgap=10, vgap=10)"));
//         for (int i = 1; i <= 8; i++) {
//             panel.add(new JButton("Btn " + i));
//         }
//         return panel;
//     }

//     // ---------------------------------------------------------------
//     // 2. BorderLayout — 5 regions: NORTH, SOUTH, EAST, WEST, CENTER
//     // ---------------------------------------------------------------
//     private static JPanel borderLayoutDemo() {
//         JPanel panel = new JPanel(new BorderLayout(5, 5));
//         panel.setBorder(BorderFactory.createTitledBorder("BorderLayout"));

//         panel.add(labeledButton("NORTH", Color.LIGHT_GRAY), BorderLayout.NORTH);
//         panel.add(labeledButton("SOUTH", Color.LIGHT_GRAY), BorderLayout.SOUTH);
//         panel.add(labeledButton("WEST", Color.LIGHT_GRAY), BorderLayout.WEST);
//         panel.add(labeledButton("EAST", Color.LIGHT_GRAY), BorderLayout.EAST);
//         panel.add(labeledButton("CENTER (takes remaining space)", Color.WHITE), BorderLayout.CENTER);

//         return panel;
//     }

//     private static JButton labeledButton(String text, Color bg) {
//         JButton b = new JButton(text);
//         b.setBackground(bg);
//         return b;
//     }

//     // ---------------------------------------------------------------
//     // 3. GridLayout — strict rows x cols, all cells equal size
//     // ---------------------------------------------------------------
//     private static JPanel gridLayoutDemo() {
//         JPanel panel = new JPanel(new GridLayout(3, 3, 5, 5)); // rows, cols, hgap, vgap
//         panel.setBorder(BorderFactory.createTitledBorder("GridLayout(3, 3, 5, 5)"));
//         // simple calculator-style grid
//         String[] labels = {"7", "8", "9", "4", "5", "6", "1", "2", "3"};
//         for (String s : labels) {
//             panel.add(new JButton(s));
//         }
//         return panel;
//     }

//     // ---------------------------------------------------------------
//     // 4. BoxLayout — single row/column with struts and glue
//     // ---------------------------------------------------------------
//     private static JPanel boxLayoutDemo() {
//         JPanel outer = new JPanel(new BorderLayout());
//         outer.setBorder(BorderFactory.createTitledBorder("BoxLayout (Y_AXIS) with struts + glue"));

//         JPanel box = new JPanel();
//         box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));

//         JButton top = new JButton("Top (aligned LEFT)");
//         top.setAlignmentX(Component.LEFT_ALIGNMENT);

//         JButton middle = new JButton("Middle (aligned CENTER)");
//         middle.setAlignmentX(Component.CENTER_ALIGNMENT);

//         JButton bottom = new JButton("Bottom (aligned RIGHT)");
//         bottom.setAlignmentX(Component.RIGHT_ALIGNMENT);

//         box.add(Box.createVerticalStrut(10));   // fixed gap
//         box.add(top);
//         box.add(Box.createRigidArea(new Dimension(0, 20))); // fixed 2D gap
//         box.add(middle);
//         box.add(Box.createVerticalGlue());       // flexible spacer — pushes bottom down
//         box.add(bottom);
//         box.add(Box.createVerticalStrut(10));

//         outer.add(box, BorderLayout.CENTER);
//         return outer;
//     }

//     // ---------------------------------------------------------------
//     // 5. GridBagLayout — the powerful, verbose, constraint-based one
//     // ---------------------------------------------------------------
//     private static JPanel gridBagLayoutDemo() {
//         JPanel panel = new JPanel(new GridBagLayout());
//         panel.setBorder(BorderFactory.createTitledBorder("GridBagLayout — a simple form"));

//         GridBagConstraints gbc = new GridBagConstraints();
//         gbc.insets = new Insets(5, 5, 5, 5);
//         gbc.anchor = GridBagConstraints.WEST;

//         // Row 0: Name label + text field (field expands horizontally)
//         gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
//         gbc.fill = GridBagConstraints.NONE;
//         panel.add(new JLabel("Name:"), gbc);

//         gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0;
//         gbc.fill = GridBagConstraints.HORIZONTAL;
//         panel.add(new JTextField(15), gbc);

//         // Row 1: Email label + text field
//         gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
//         gbc.fill = GridBagConstraints.NONE;
//         panel.add(new JLabel("Email:"), gbc);

//         gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1.0;
//         gbc.fill = GridBagConstraints.HORIZONTAL;
//         panel.add(new JTextField(15), gbc);

//         // Row 2: Message label + text area spanning 2 rows, growing both ways
//         gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0; gbc.weighty = 0;
//         gbc.fill = GridBagConstraints.NONE;
//         gbc.anchor = GridBagConstraints.NORTHWEST;
//         panel.add(new JLabel("Message:"), gbc);

//         gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 1.0; gbc.weighty = 1.0;
//         gbc.fill = GridBagConstraints.BOTH;
//         JTextArea area = new JTextArea(5, 15);
//         panel.add(new JScrollPane(area), gbc);

//         // Row 3: Submit button spanning both columns, aligned right
//         gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2; gbc.weighty = 0;
//         gbc.fill = GridBagConstraints.NONE;
//         gbc.anchor = GridBagConstraints.EAST;
//         panel.add(new JButton("Submit"), gbc);

//         return panel;
//     }

//     // ---------------------------------------------------------------
//     // 6. CardLayout — swap between panels like a wizard
//     // ---------------------------------------------------------------
//     private static JPanel cardLayoutDemo() {
//         JPanel outer = new JPanel(new BorderLayout());
//         outer.setBorder(BorderFactory.createTitledBorder("CardLayout — click buttons to switch cards"));

//         CardLayout cardLayout = new CardLayout();
//         JPanel cards = new JPanel(cardLayout);

//         JPanel card1 = new JPanel(new FlowLayout());
//         card1.add(new JLabel("This is CARD ONE"));

//         JPanel card2 = new JPanel(new FlowLayout());
//         card2.add(new JLabel("This is CARD TWO"));

//         JPanel card3 = new JPanel(new FlowLayout());
//         card3.add(new JLabel("This is CARD THREE"));

//         cards.add(card1, "ONE");
//         cards.add(card2, "TWO");
//         cards.add(card3, "THREE");

//         JPanel controls = new JPanel(new FlowLayout());
//         JButton prev = new JButton("< Previous");
//         JButton next = new JButton("Next >");
//         prev.addActionListener(e -> cardLayout.previous(cards));
//         next.addActionListener(e -> cardLayout.next(cards));
//         controls.add(prev);
//         controls.add(next);

//         outer.add(cards, BorderLayout.CENTER);
//         outer.add(controls, BorderLayout.SOUTH);
//         return outer;
//     }

//     // ---------------------------------------------------------------
//     // 7. Null layout — absolute positioning with setBounds()
//     // ---------------------------------------------------------------
//     private static JPanel nullLayoutDemo() {
//         JPanel panel = new JPanel();
//         panel.setLayout(null); // no layout manager — manual positioning
//         panel.setBorder(BorderFactory.createTitledBorder(
//                 "Null layout — setBounds(x, y, w, h). Try resizing the window!"));

//         JButton b1 = new JButton("x=20,y=30");
//         b1.setBounds(20, 30, 120, 30);

//         JButton b2 = new JButton("x=160,y=80");
//         b2.setBounds(160, 80, 140, 40);

//         JButton b3 = new JButton("x=60,y=150");
//         b3.setBounds(60, 150, 160, 30);

//         panel.add(b1);
//         panel.add(b2);
//         panel.add(b3);

//         return panel;
//     }
// }