package Controler;

import Model.ConnectionDAO;
import Model.User;
import Model.UserDAO;
import View.AccountAddView;
import View.AccountsTableView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AccountAddController {
    private AccountAddView view;
    private UserDAO userDAO = new UserDAO();
    private ConnectionDAO connectionDAO = new ConnectionDAO();

    public AccountAddController(AccountAddView view) {
        this.view = view;

        view.getFormSend().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PasswordHasher passwordHasher = new PasswordHasher();

                String privilege = view.getPrivilege();
                String identifiant = view.getIdentifiant();
                String password = PasswordHasher.hashPassword(view.getPassword());

                // Ajouter le compte
                User user = new User(identifiant, password, privilege);
                try {
                    if(userDAO.addUser(user, connectionDAO.getConnection())){
                        System.out.println("Utilisateur ajouté avec succès");
                        AccountsTableView.refreshTable();
                        view.dispose();
                    }else{
                        System.out.println("Erreur lors de l'ajout de l'utilisateur");
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                    System.out.println(e1);
                }


            }
        });
    }
}
