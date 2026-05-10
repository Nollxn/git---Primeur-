package View;

import Controler.ArticleController;
import Controler.FournisseurController;
import Model.ConnectionDAO;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private JTabbedPane tabbedPane;
    private ArticlePanel articlePanel;
    private FournisseurPanel fournisseurPanel;
    private ConnectionDAO connectionDAO;

    public MainFrame() {
        // Connexion à la base de données
        connectionDAO = new ConnectionDAO();
        
        // Configuration de la fenêtre
        setTitle("Gestion de Stock - Primeur");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Création des onglets
        tabbedPane = new JTabbedPane();
        
        // Panel Articles
        articlePanel = new ArticlePanel();
        new ArticleController(articlePanel, connectionDAO);
        tabbedPane.addTab("Articles", articlePanel);
        
        // Panel Fournisseurs
        fournisseurPanel = new FournisseurPanel();
        new FournisseurController(fournisseurPanel, connectionDAO);
        tabbedPane.addTab("Fournisseurs", fournisseurPanel);
        
        // Ajout du tabbedPane à la fenêtre
        add(tabbedPane);
        
        setVisible(true);
    }
    
    public ArticlePanel getArticlePanel() {
        return articlePanel;
    }
    
    public FournisseurPanel getFournisseurPanel() {
        return fournisseurPanel;
    }
}