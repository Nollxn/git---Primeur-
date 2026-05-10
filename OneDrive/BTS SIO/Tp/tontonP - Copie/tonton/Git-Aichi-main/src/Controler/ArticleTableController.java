package Controler;

import Model.ArticleDAO;
import Model.Articles;
import Model.ConnectionDAO;
import View.ArticleAddView;
import View.ArticleEditView;
import View.ArticlesTableView;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;

public class ArticleTableController {

    private ArticlesTableView view;
    private ArticleDAO articleDAO= new ArticleDAO();
    private ConnectionDAO connection = new ConnectionDAO();

    public ArticleTableController(ArticlesTableView view) {
        this.view = view;

        // Ajouter un article
        if (this.view != null) {
            this.view.getAjouterArticle().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {new ArticleAddView();}
            });
        }

        // Modifier un article
        if (this.view != null) {
            this.view.getModifierArticle().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int selectedRow = view.getTable().getSelectedRow();
                    if (selectedRow != -1) {
                        try {
                            int id = (int) view.getTableModel().getValueAt(selectedRow, 0);
                            ArrayList<Articles> articles = ArticleDAO.getArticles(connection.getConnection());
                            Articles article = articles.stream()
                                .filter(a -> a.get_ID() == id)
                                .findFirst()
                                .orElse(null);
                            
                            if (article != null) {
                                new ArticleEditView(article);
                            }
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Erreur lors de la récupération de l'article", "Erreur", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Veuillez sélectionner un article à modifier");
                    }
                }
            });
        }

        // Tri des articles
        if (this.view != null) {
            this.view.getTriComboBox().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String selectedTri = (String) view.getTriComboBox().getSelectedItem();
                    try {
                        trierArticles(selectedTri);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            });
        }

        //Rechercher un article
        if (this.view != null){
            this.view.getSearchTextField().getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    try {
                        view.recherche(view.getSearchTextField().getText());
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    try {
                        view.recherche(view.getSearchTextField().getText());
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    // Ne pas utiliser
                }
            });
        }

        // Supprimer un article
        if (this.view != null){
            this.view.getSupprimerArticle().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int selectedRow = view.getTable().getSelectedRow();
                    
                    if (selectedRow != -1) {
                        String nom_artcle = (String) view.getTableModel().getValueAt(selectedRow, 1);
                        int id = (int) view.getTableModel().getValueAt(selectedRow, 0);
                        
                        //Demande de confirmation avant suppression de l'article
                        int response = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment supprimer l'article '" + nom_artcle+"'", "Confirmation", JOptionPane.YES_NO_OPTION);

                        if (response == JOptionPane.YES_OPTION) {
                            view.getTableModel().removeRow(selectedRow);
                            try {
                                articleDAO.supprimerArticle(id, connection.getConnection());
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            }
                            JOptionPane.showMessageDialog(null, "L' article '"+nom_artcle+ "' a été supprimé avec succès");
                        }
                        else{
                            JOptionPane.showMessageDialog(null, "Suppression annulée");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Veuillez sélectionner un article à supprimer");
                    }
                }
            });
        }
    }

    private void trierArticles(String typeTri) throws SQLException {
        ArrayList<Articles> articles = ArticleDAO.getArticles(connection.getConnection());
        
        switch (typeTri) {
            case "Nom (A-Z)":
                articles.sort(Comparator.comparing(Articles::getNom_article));
                break;
            case "Nom (Z-A)":
                articles.sort(Comparator.comparing(Articles::getNom_article).reversed());
                break;
            case "Prix (croissant)":
                articles.sort(Comparator.comparing(Articles::getPrix_article));
                break;
            case "Prix (décroissant)":
                articles.sort(Comparator.comparing(Articles::getPrix_article).reversed());
                break;
            default:
                // Sans tri - ordre par défaut
                break;
        }
        
        // Mettre à jour le tableau
        view.getTableModel().setRowCount(0);
        articles.forEach(article -> {
            Object[] row = {article.get_ID(), article.getNom_article(), article.getType_article(), 
                article.getPrix_article(), article.getQuantite_article(), article.getId_fournisseur(), 
                article.getDescription_article()};
            view.getTableModel().addRow(row);
        });
    }
}
