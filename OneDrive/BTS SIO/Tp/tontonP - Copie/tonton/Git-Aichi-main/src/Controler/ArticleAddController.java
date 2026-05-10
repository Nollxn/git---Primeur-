package Controler;

import Model.ConnectionDAO;
import Model.Articles;
import Model.ArticleDAO;
import View.ArticleAddView;
import View.ArticlesTableView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ArticleAddController {
    private ArticleAddView view;
    private ConnectionDAO connectionDAO = new ConnectionDAO();
    private ArticleDAO articleDAO = new ArticleDAO();

    public ArticleAddController(ArticleAddView view) {
        this.view = view;

        view.getAjouterButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Récupérer les données du formulaire
                    String nom_article = view.getName();
                    String type_article = view.getTypeArticle();
                    double prix_article = view.getPrice();
                    int quantite_article = view.getStock();
                    String description_article = view.getDescription();
                    int id_fournisseur = view.getIdFournisseur();

                    // Créer l'article avec tous les champs
                    Articles article = new Articles(nom_article, type_article, prix_article, quantite_article, id_fournisseur);
                    article.setDescription_article(description_article);

                    // Ajouter l'article en base
                    if (articleDAO.ajouterArticle(article, connectionDAO.getConnection())) {
                        JOptionPane.showMessageDialog(null, "L'article '" + nom_article + "' a été ajouté avec succès");
                        ArticlesTableView.refreshTable();
                        view.dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Erreur lors de l'ajout de l'article '" + nom_article + "'", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Veuillez entrer des valeurs valides pour le prix et la quantité", "Erreur de saisie", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erreur: " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        view.getAnnulerButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.dispose();
            }
        });
    }
}
