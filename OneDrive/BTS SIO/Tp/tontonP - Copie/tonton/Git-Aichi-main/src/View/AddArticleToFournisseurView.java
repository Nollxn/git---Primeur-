package View;

import Controler.FournisseurDetailController;
import Model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class AddArticleToFournisseurView {
    private JPanel panel1;
    private JTable tableArticles;
    private JButton ajouterArticleButton = new JButton("Ajouter Article");
    private JButton annulerButton = new JButton("Annuler");
    private JPanel actionPanel = new JPanel();
    private DefaultTableModel tableModel;
    private int fournisseurId;
    private FournisseurDAO fournisseurDAO = new FournisseurDAO();
    private ConnectionDAO connectionDAO = new ConnectionDAO();
    private Fournisseur fournisseur;
    private JScrollPane scrollPane ;
    private JTextField searchTextField = new JTextField(30);

    public AddArticleToFournisseurView(int fournisseurId) throws SQLException {
        this.fournisseurId = fournisseurId;
        fournisseur = fournisseurDAO.getFournisseurById(fournisseurId, connectionDAO.getConnection());

        JFrame frame = new JFrame("Ajouter un article à la fournisseur");
        panel1 = new JPanel();
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        panel1.setLayout(new BorderLayout());
        actionPanel.setLayout(new BorderLayout());
        actionPanel.add(ajouterArticleButton, BorderLayout.WEST);
        actionPanel.add(annulerButton, BorderLayout.EAST);
        panel1.add(actionPanel, BorderLayout.SOUTH);
        panel1.add(searchTextField, BorderLayout.NORTH);

        //Controlleur
        new FournisseurDetailController(fournisseurId,this);

        String[] columnNames = {"ID", "Nom", "Description", "Quantité disponible"};
        Object[][] data = {};
        tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableArticles = new JTable(tableModel);
        scrollPane = new JScrollPane(tableArticles);

        ConnectionDAO connection = new ConnectionDAO();
        //Affichage des articles
        try {
            ArrayList<Articles> articles = ArticleDAO.getArticles(connection.getConnection());
            articles.forEach(article -> {
                Object[] row = {article.get_ID(), article.getNom_article(), article.getPrix_article(), article.getQuantite_article(), article.getDescription_article()};
                tableModel.addRow(row);
            });
        }catch(SQLException e){
            e.printStackTrace();
        }

        panel1.add(scrollPane, BorderLayout.CENTER);

        frame.pack();
        frame.setVisible(true);
    }


    public JTable getTableArticles() {
        return tableArticles;
    }

    public JPanel getPanel1() {
        return panel1;
    }

    public JButton getAjouterArticleButton() {
        return ajouterArticleButton;
    }

    public JButton getAnnulerButton() {
        return annulerButton;
    }

    public JPanel getActionPanel() {
        return actionPanel;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public int getFournisseurId() {
        return fournisseurId;
    }

    public FournisseurDAO getFournisseurDAO() {
        return fournisseurDAO;
    }

    public ConnectionDAO getConnectionDAO() {
        return connectionDAO;
    }

    public Fournisseur getFournisseur() {
        return fournisseur;
    }

    public JTextField getSearchTextField() {
        return searchTextField;
    }

    public void recherche(String text) {
        tableModel.setRowCount(0);
        try {
            ArrayList<Articles> articles = ArticleDAO.getArticles(connectionDAO.getConnection());
            articles.forEach(article -> {
                if(article.getNom_article().toLowerCase().contains(text.toLowerCase())){
                    Object[] row = {article.get_ID(), article.getNom_article(), article.getPrix_article(), article.getQuantite_article(), article.getDescription_article()};
                    tableModel.addRow(row);
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
    }
}

    public void dispose() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel1);
        frame.dispose();
    }
}
