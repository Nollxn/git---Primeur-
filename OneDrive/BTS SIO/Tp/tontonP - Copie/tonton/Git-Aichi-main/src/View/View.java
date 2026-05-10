package View;

import Controler.Controler;
import java.awt.*;
import javax.swing.*;

public class View extends JFrame{
    private JPanel panel1;
    private JTextField fieldIdentifiant;
    private JButton formSend;
    private JPasswordField fieldPassword;
    private JButton buttonCreateAccount;

    public View() {
        // Créer l'interface graphique manuellement
        createUIComponents();
        
        //Configuration de la fenetre
        this.setContentPane(panel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 500);
        new Controler(this);
        // Centrer la fenetre
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void createUIComponents() {
        // Créer le panel principal
        panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Titre
        JLabel titleLabel = new JLabel("Se connecter");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel1.add(titleLabel, gbc);

        // Label Identifiant
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel1.add(new JLabel("Identifiant"), gbc);

        // Champ Identifiant
        fieldIdentifiant = new JTextField(20);
        gbc.gridx = 1;
        panel1.add(fieldIdentifiant, gbc);

        // Label Mot de passe
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel1.add(new JLabel("Mot de passe"), gbc);

        // Champ Mot de passe
        fieldPassword = new JPasswordField(20);
        gbc.gridx = 1;
        panel1.add(fieldPassword, gbc);

        // Bouton Se connecter
        formSend = new JButton("Se connecter");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel1.add(formSend, gbc);

        // Bouton Créer un compte
        buttonCreateAccount = new JButton("Créer un compte");
        gbc.gridy = 4;
        panel1.add(buttonCreateAccount, gbc);
    }


    public String getFieldPassword() {
        return new String(fieldPassword.getPassword());
    }

    public String getFieldIdentifiant() {
        return fieldIdentifiant.getText();
    }

    public JButton getFormSend() {
        return formSend;
    }

    public JButton getButtonCreateAccount() {
        return buttonCreateAccount;
    }
}
