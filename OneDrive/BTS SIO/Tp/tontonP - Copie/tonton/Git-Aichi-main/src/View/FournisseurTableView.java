package View;

import Controler.FournisseurTableController;
import Model.ConnectionDAO;
import Model.FournisseurDAO;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class FournisseurTableView extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton ajouterFournisseurButton = new JButton("Ajouter Fournisseur");
    private JButton modifierButton = new JButton("Modifier");
    private JButton supprimerButton = new JButton("Supprimer");
    private ConnectionDAO connection = new ConnectionDAO();
    private JButton updateButton = new JButton("Actualiser");
    private JPanel topPanel = new JPanel();
    private JPanel bottomPanel = new JPanel();

    public FournisseurTableView() {
        FournisseurDAO fournisseurDAO = new FournisseurDAO();
        FournisseurTableController controller = new FournisseurTableController(this);

        setLayout(new BorderLayout());

        String[] columnNames = {"ID", "Nom", "Téléphone", "Email", "Adresse"};
        Object[][] data = {};

        tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        // Panel du haut avec boutons d'ajout et actualisation
        topPanel.setLayout(new BorderLayout());
        topPanel.add(updateButton, BorderLayout.WEST);
        topPanel.add(ajouterFournisseurButton, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Panel du bas avec boutons de modification et suppression
        bottomPanel.setLayout(new FlowLayout());
        bottomPanel.add(modifierButton);
        bottomPanel.add(supprimerButton);
        add(bottomPanel, BorderLayout.SOUTH);

        // Chargement initial des données
        fournisseurDAO.getFournisseurs(connection.getConnection()).forEach(fournisseur -> {
            Object[] row = {fournisseur.getId(), fournisseur.getNom(), fournisseur.getTelephone(), 
                fournisseur.getEmail(), fournisseur.getAdresse()};
            tableModel.addRow(row);
        });
    }

    public void updateTable() {
        tableModel.setRowCount(0);
        FournisseurDAO fournisseurDAO = new FournisseurDAO();
        fournisseurDAO.getFournisseurs(connection.getConnection()).forEach(fournisseur -> {
            Object[] row = {fournisseur.getId(), fournisseur.getNom(), fournisseur.getTelephone(), 
                fournisseur.getEmail(), fournisseur.getAdresse()};
            tableModel.addRow(row);
        });
    }

    public JButton getUpdateButton() {
        return updateButton;
    }
    
    public JTable getTable() {
        return table;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public JButton getAjouterFournisseur() {
        return ajouterFournisseurButton;
    }

    public JButton getModifierButton() {
        return modifierButton;
    }

    public JButton getSupprimerButton() {
        return supprimerButton;
    }
}