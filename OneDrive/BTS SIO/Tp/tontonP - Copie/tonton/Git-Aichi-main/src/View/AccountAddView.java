package View;

import Controler.AccountAddController;
import java.awt.*;
import javax.swing.*;

public class AccountAddView extends JFrame{
    private JPanel panel1;
    private JPasswordField passwordField1;
    private JTextField fieldIdentifiant;
    private JButton formSend;
    private JComboBox<String> privilegeComboBox;

    public AccountAddView() {
        createUIComponents();
        
        //Configuration de la fenetre
        this.setTitle("Création de compte");
        this.setContentPane(panel1);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(400, 500);
        // Centrer la fenetre
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        new AccountAddController(this);
    }

    private void createUIComponents() {
        panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Titre
        JLabel titleLabel = new JLabel("Création de compte");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel1.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        // Identifiant
        gbc.gridy = 1;
        gbc.gridx = 0;
        panel1.add(new JLabel("Identifiant :"), gbc);
        fieldIdentifiant = new JTextField(20);
        gbc.gridx = 1;
        panel1.add(fieldIdentifiant, gbc);

        // Mot de passe
        gbc.gridy = 2;
        gbc.gridx = 0;
        panel1.add(new JLabel("Mot de passe :"), gbc);
        passwordField1 = new JPasswordField(20);
        gbc.gridx = 1;
        panel1.add(passwordField1, gbc);

        // Privilège
        gbc.gridy = 3;
        gbc.gridx = 0;
        panel1.add(new JLabel("Privilège :"), gbc);
        privilegeComboBox = new JComboBox<>(new String[]{"User", "Admin", "SuperAdmin"});
        gbc.gridx = 1;
        panel1.add(privilegeComboBox, gbc);

        // Bouton
        formSend = new JButton("Créer le compte");
        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel1.add(formSend, gbc);
    }

    public JButton getFormSend() {
        return formSend;
    }

    public String getIdentifiant(){
        return fieldIdentifiant.getText();
    }

    public String getPassword(){
        return new String(passwordField1.getPassword());
    }

    public String getPrivilege(){
        return privilegeComboBox.getSelectedItem().toString();
    }
}
