package View;

import javax.swing.*;

public class ConnectedView extends JFrame {
    private JPanel panel1;
    private JLabel welcomeLabel;

    public ConnectedView(String username) {
        // Initialize components
        panel1 = new JPanel();
        welcomeLabel = new JLabel("Welcome, " + username + "!");

        // Add components to panel
        panel1.add(welcomeLabel);

        // Set the content pane
        this.setContentPane(panel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(300, 200);
        this.setVisible(true);
    }
}