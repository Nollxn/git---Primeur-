package Controler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import Model.ConnectionDAO;
import Model.User;
import Model.UserDAO;
import View.AccountAddView;
import View.View;
import View.MainFrame;
import javax.swing.*;

public class Controler {
    private View view;
    private UserDAO userDAO = new UserDAO();
    private ConnectionDAO connection = new ConnectionDAO();

    public Controler(View view) {
        this.view = view;

        // Permet à l'utilisateur de se connecter en cliquant sur le touche Entrer de son ordinateur
        this.view.getRootPane().setDefaultButton(view.getFormSend());

        this.view.getFormSend().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                User user = new User(view.getFieldIdentifiant(), view.getFieldPassword());
                try {
                    if (userDAO.loginUser(user, connection.getConnection())) {
                        // Handle successful login
                        System.out.println("Connexion réussi.");
                        JOptionPane.showMessageDialog(view, "Connexion réussie.", "Succès", JOptionPane.INFORMATION_MESSAGE);
                        //new ConnectedView(user.getIdentifiant());

                        new MainFrame();
                        view.dispose(); // Fermer la fenêtre de connexion
                    } else {
                        // Handle failed login
                        System.out.println("Erreur de connexion.");
                        JOptionPane.showMessageDialog(view, "Erreur de connexion.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException e1) {
                    // Handle SQL exception
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(view, "Erreur lors de la tentative de connexion.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Bouton pour créer un nouveau compte
        this.view.getButtonCreateAccount().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AccountAddView();
            }
        });
    }
}