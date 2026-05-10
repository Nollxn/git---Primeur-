package View;

import Controler.FournisseurDetailController;
import Model.Fournisseur;
import Model.FournisseurDAO;
import Model.ConnectionDAO;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FournisseurDetailViewV2 extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JPanel infoPanel;
    private JPanel actionPanel;
    private JLabel id_fournisseur;
    private JLabel date_fournisseur;
    private JLabel client;
    private JButton ajouterArticleButton= new JButton("Ajouter Article");
    private JButton supprimerArticleButton = new JButton("Supprimer Article");
    private JButton supprimerFournisseurButton;
    private int fournisseurId;
    private JButton updateButton = new JButton("Mettre à jour");

    public FournisseurDetailViewV2(int fournisseurId) {
        FournisseurDAO fournisseurDAO = new FournisseurDAO();
        ConnectionDAO connectionDAO = new ConnectionDAO();
        Fournisseur fournisseur = fournisseurDAO.getFournisseurById(fournisseurId, connectionDAO.getConnection());
        this.fournisseurId = fournisseurId;
        this.supprimerFournisseurButton= new JButton("Supprimer Fournisseur");
        //Controlleur
        new FournisseurDetailController(fournisseurId, this);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        actionPanel = new JPanel();
        actionPanel.setLayout(new BoxLayout(actionPanel, BoxLayout.X_AXIS));

        actionPanel.setLayout(new BorderLayout());
        actionPanel.add(supprimerFournisseurButton,BorderLayout.NORTH);
        actionPanel.add(ajouterArticleButton, BorderLayout.CENTER);
        actionPanel.add(supprimerArticleButton, BorderLayout.EAST);
        actionPanel.add(updateButton, BorderLayout.WEST);

        id_fournisseur = new JLabel("ID: " + fournisseur.getId());
        date_fournisseur = new JLabel("Date: " + fournisseur.getDate());
        client = new JLabel("Client: " + fournisseur.getClient().getClient_nom() + " " + fournisseur.getClient().getClient_prenom());
        infoPanel.setLayout(new BorderLayout());
        infoPanel.add(id_fournisseur, BorderLayout.CENTER);
        infoPanel.add(date_fournisseur, BorderLayout.WEST);
        infoPanel.add(client, BorderLayout.EAST);
        add(infoPanel);
        add(actionPanel);

        String[] columnNames = {"ID", "Nom", "Quantité", "Description"};
        Object[][] data = {};
        tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);

        fournisseurDAO.getArticles(fournisseur,connectionDAO.getConnection()).forEach(list-> {
            list.forEach(article -> {
                Object[] row = {article.get_ID(), article.getNom_article(), article.getQuantite_article(), article.getDescription_article()};
                tableModel.addRow(row);
            });
        });
    }

    public JButton getSupprimerFournisseurButton() {
        return supprimerFournisseurButton;
    }

    public void dispose() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.dispose();
    }

    //Retourner l'id des articles sélectionner dans la table
    public List<Integer> getSelectedArticleIds() {
        List<Integer> selectedArticleIds = new ArrayList<>();
        int[] selectedRows = table.getSelectedRows();
        for (int i = 0; i < selectedRows.length; i++) {
            selectedArticleIds.add((int) table.getValueAt(selectedRows[i], 0));
        }
        return selectedArticleIds;
    }

    public JButton getSupprimerArticleButton() {
        return supprimerArticleButton;
    }

    public JButton getAjouterArticleButton() {
        return ajouterArticleButton;
    }

    public void updateTable() {
        tableModel.setRowCount(0);
        FournisseurDAO fournisseurDAO = new FournisseurDAO();
        ConnectionDAO connectionDAO = new ConnectionDAO();
        Fournisseur fournisseur = fournisseurDAO.getFournisseurById(this.fournisseurId, connectionDAO.getConnection());
        fournisseurDAO.getArticles(fournisseur, connectionDAO.getConnection()).forEach(list -> {
            list.forEach(article -> {
                Object[] row = {article.get_ID(), article.getNom_article(), article.getQuantite_article(), article.getDescription_article()};
                tableModel.addRow(row);
            });
        });
    }

    public JButton getUpdateButton() {
        return updateButton;
    }
}

