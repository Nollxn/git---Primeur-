package View;

import Controler.AccountTableController;
import Controler.Privileges;
import Model.ConnectionDAO;
import Model.UserDAO;
import java.awt.*;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class AccountsTableView extends JPanel {
    private JTable table;
    private static DefaultTableModel tableModel;
    private static ConnectionDAO connection = new ConnectionDAO();
    private JButton ajouterCompteButton = new JButton("Ajouter un compte");
    private int selectedRow;
    private JButton supprimerCompteButton = new JButton("Supprimer le compte sélectionner");

    public AccountsTableView() throws SQLException {
        new AccountTableController(this);
        setLayout(new BorderLayout());

        // Définir les colonnes et les données de la table
        String[] columnNames = {"ID", "Username","Privilege"};

        //Data vide
        Object[][] data = {};

        // Initialiser le modèle de table
        tableModel = new DefaultTableModel(data, columnNames){
            @Override
            public boolean isCellEditable(int row, int column) {
                if(UserDAO.est_superadmin){
                    // Les colonnes ID (index 0) et Username (index 1) ne sont pas éditables
                    return column == 2; // Seule la colonne "Privilege" est éditable
                }
                return false; 
            }
        };

        // Créer la JTable avec le modèle
        add(ajouterCompteButton,BorderLayout.NORTH);
        table = new JTable(tableModel);

        // Ajouter la liste déroulante dans la colonne "Privilege"
        JComboBox<String> privilegeComboBox = new JComboBox<>(Privileges.privileges);
        TableColumn privilegeColumn = table.getColumnModel().getColumn(2);
        privilegeColumn.setCellEditor(new DefaultCellEditor(privilegeComboBox));

        // Ajouter la JTable dans un JScrollPane pour le défilement
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        //Affichage des comptes utilisateurs
        UserDAO.getUsers(connection.getConnection()).forEach(user -> {
            //Ajouter la possibiliter de supprimer un compte avec une icone poubelle
            Object[] row = {user.getID(), user.getIdentifiant(),user.getPrivilege()};
            addRow(row);
        });

        // Ajouter un bouton pour supprimer un compte
        this.add(supprimerCompteButton,BorderLayout.SOUTH);

    }

    // Méthode pour ajouter une ligne
    public static void addRow(Object[] rowData) {
        tableModel.addRow(rowData);
    }

    // Méthode pour mettre à jour une cellule
    public void updateCell(Object value, int row, int column) {
        tableModel.setValueAt(value, row, column);
    }

    public JButton getAjouterCompteButton() {
        return ajouterCompteButton;
    }

    public JButton getSupprimerCompteButton() {
        return supprimerCompteButton;
    }

    public JTable getTable() {
        return table;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public static void refreshTable() throws SQLException {
        tableModel.setRowCount(0);
        UserDAO.getUsers(connection.getConnection()).forEach(user -> {
            //Ajouter la possibiliter de supprimer un compte avec une icone poubelle
            Object[] row = {user.getID(), user.getIdentifiant(),user.getPrivilege()};
            addRow(row);
        });
    }
}
