package View;

import Controler.FournisseurDetailController;
import Model.*;
import java.awt.*;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class FournisseurAddView extends JFrame {
    private JPanel contentPanel = new JPanel();
    private JScrollPane scrollPaneClient;
    private DefaultTableModel tableModelClient = new DefaultTableModel();
    private JTable tableClient;
    private JButton annulerButton = new JButton("Annuler");
    private JButton validerButton = new JButton("Valider");
    private JPanel actionPanel = new JPanel();
    private JPanel clientPanel = new JPanel();
    private JTextField searchTextFieldClient = new JTextField(30);
    private ConnectionDAO connection = new ConnectionDAO();
    private ClientDAO clientDAO = new ClientDAO();

    public FournisseurAddView() throws SQLException {
        // Controlleur
        new FournisseurDetailController(this);

        setTitle("Ajouter une fournisseur, veuillez choisir un client ");
        setContentPane(contentPanel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        contentPanel.setLayout(new BorderLayout());

        // Bouton valider et annuler
        actionPanel.setLayout(new BorderLayout());
        actionPanel.add(validerButton, BorderLayout.WEST);
        actionPanel.add(annulerButton, BorderLayout.EAST);
        contentPanel.add(actionPanel, BorderLayout.SOUTH);

        // Panel client
        clientPanel.setLayout(new BorderLayout());
        clientPanel.add(searchTextFieldClient, BorderLayout.NORTH);

        // Data vide
        Object[][] data = {};

        // Définir les colonnes et les données de la table
        String[] columnNamesClient = {"ID", "Nom", "Prénom"};

        // Initialiser le modèle de table
        tableModelClient = new DefaultTableModel(data, columnNamesClient) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Initialize tableClient with tableModelClient
        tableClient = new JTable(tableModelClient);
        scrollPaneClient = new JScrollPane(tableClient);
        contentPanel.add(scrollPaneClient, BorderLayout.CENTER);

        // Affichage des clients
        try {
            clientDAO.getClients(connection.getConnection()).forEach(client -> {
                Object[] row = {client.getId_client(), client.getClient_nom(), client.getClient_prenom()};
                tableModelClient.addRow(row);
            });
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public JButton getAnnulerButton() {
        return annulerButton;
    }

    public JButton getValiderButton() {
        return validerButton;
    }

    public JTable getTableClient() {
        return tableClient;
    }

    public JTextField getSearchTextFieldClient() {
        return searchTextFieldClient;
    }

    public void rechercheClient(String input) throws SQLException {
        tableModelClient.setRowCount(0);
        clientDAO.getClients(connection.getConnection()).forEach(client -> {
            if (client.getClient_nom().toLowerCase().contains(input.toLowerCase())) {
                Object[] row = {client.getId_client(), client.getClient_nom(), client.getClient_prenom()};
                tableModelClient.addRow(row);
            }
        });
    }
}