package Controler;

import Model.ArticleDAO;
import Model.Articles;
import Model.ConnectionDAO;
import View.ArticleEditView;
import View.ArticlesTableView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ArticleEditController {
    private ArticleEditView view;
    private ArticleDAO articleDAO = new ArticleDAO();
    private ConnectionDAO connectionDAO = new ConnectionDAO();

    public ArticleEditController(ArticleEditView view) {
        this.view = view;

        view.getFormSend().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Articles article = view.getArticle();
                    article.setNom_article(view.getNom());
                    article.setType_article(view.getTypeArticle());
                    article.setPrix_article(Double.parseDouble(view.getPrix()));
                    article.setQuantite_article(Integer.parseInt(view.getQuantite()));
                    article.setDescription_article(view.getDescription());
                    article.setId_fournisseur(Integer.parseInt(view.getFournisseurId()));

                    if(articleDAO.modifierArticle(article, connectionDAO.getConnection())){
                        JOptionPane.showMessageDialog(view, "Article modifié avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);
                        ArticlesTableView.refreshTable();
                        view.dispose();
                    } else {
                        JOptionPane.showMessageDialog(view, "Erreur lors de la modification", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(view, "Veuillez entrer des valeurs valides", "Erreur", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(view, "Erreur: " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
