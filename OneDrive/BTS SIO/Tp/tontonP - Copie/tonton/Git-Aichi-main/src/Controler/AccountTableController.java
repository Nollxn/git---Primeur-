package Controler;

import Model.ConnectionDAO;
import Model.UserDAO;
import View.AccountAddView;
import View.AccountsTableView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class AccountTableController {
    private AccountsTableView view;
    private UserDAO userDAO = new UserDAO();
    private ConnectionDAO connection = new ConnectionDAO();

    public AccountTableController(AccountsTableView view){
        this.view = view;

        // Ajouter un compte
        if(this.view!=null){
            this.view.getAjouterCompteButton().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new AccountAddView();
                }
            } );
        }


        // Supprimer un compte
        if(this.view!=null){
            this.view.getSupprimerCompteButton().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int selectedRow = view.getTable().getSelectedRow();
                    if (selectedRow != -1) {
                        //Demande de confirmation avant suppression du compte
                        int response = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment supprimer ce compte ?", "Confirmation", JOptionPane.YES_NO_OPTION);
                        if (response == JOptionPane.YES_OPTION) {
                            int id = (int) view.getTableModel().getValueAt(selectedRow, 0);
                            view.getTableModel().removeRow(selectedRow);
                            try {
                                userDAO.deleteUser(id,connection.getConnection());
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            }
                            System.out.println("Suppression du compte avec l'ID: " + id);
                        }

                    }else{
                        JOptionPane.showMessageDialog(null, "Veuillez sélectionner un compte à supprimer");
                    }
                }
            });
        }
    }
}
