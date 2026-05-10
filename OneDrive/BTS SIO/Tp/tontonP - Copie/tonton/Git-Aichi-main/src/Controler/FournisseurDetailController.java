package Controler;

import Model.Fournisseur;
import Model.FournisseurDAO;
import Model.ConnectionDAO;
import View.AddArticleToFournisseurView;
import View.FournisseurAddView;
import View.FournisseurDetailViewV2;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class FournisseurDetailController {
    private  FournisseurDetailViewV2 fournisseurDetailViewV2;
    private ConnectionDAO connectionDAO;
    private FournisseurDAO fournisseurDAO = new FournisseurDAO();
    private int fournisseurId;

    public FournisseurDetailController(int fournisseurId, FournisseurDetailViewV2 fournisseurDetailViewV2) {
        connectionDAO = new ConnectionDAO();
        this.fournisseurId = fournisseurId;

        //Supprimer la commande
        fournisseurDetailViewV2.getSupprimerFournisseurButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connectionDAO.getConnection();
                int response = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment supprimer ce compte ?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    if (JOptionPane.YES_OPTION == 0) {
                        return;
                    }
                    fournisseurDAO.supprimerFournisseur(fournisseurId, connectionDAO.getConnection());
                    fournisseurDetailViewV2.dispose();
                }

            }
        });

        //Enlever l'article ou les articles sélectionner de la commande
        fournisseurDetailViewV2.getSupprimerArticleButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connectionDAO.getConnection();
                fournisseurDetailViewV2.getSelectedArticleIds().forEach(article ->{
                    fournisseurDAO.supprimerArticleFromFournisseur(fournisseurId, article, connectionDAO.getConnection());
                });

                fournisseurDetailViewV2.updateTable();
            }
        });


        //Ajouter un article à la commande
        fournisseurDetailViewV2.getAjouterArticleButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new AddArticleToFournisseurView(fournisseurId);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });

        //Mettre a jour la table
        fournisseurDetailViewV2.getUpdateButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fournisseurDetailViewV2.updateTable();
            }
        });



    }

    public FournisseurDetailController(int fournisseurId,AddArticleToFournisseurView view) {
        connectionDAO = new ConnectionDAO();
        this.fournisseurId = fournisseurId;

        //Ajouter un article à la commande
        view.getAjouterArticleButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connectionDAO.getConnection();
                int articleId = (int) view.getTableArticles().getValueAt(view.getTableArticles().getSelectedRow(), 0);

                String input = JOptionPane.showInputDialog(null, "Entrez la quantité d'article:", "Quantité", JOptionPane.QUESTION_MESSAGE);
                if (input != null && !input.isEmpty()) {
                    try {
                        int quantite = Integer.parseInt(input);
                        System.out.println("Quantité saisie: " + quantite);
                        fournisseurDAO.ajouterArticleToFournisseur(fournisseurId, articleId, quantite, connectionDAO.getConnection());
                    } catch (NumberFormatException event) {
                        JOptionPane.showMessageDialog(null, "Veuillez entrer un nombre valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    } finally {
                        JOptionPane.showMessageDialog(null, "L'article a été ajouté a la commande avec succès", "Succès", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Aucune quantité saisie.", "Information", JOptionPane.INFORMATION_MESSAGE);
                }

            }
        });

        //Rechercher un article
        if (view != null){
            view.getSearchTextField().getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    view.recherche(view.getSearchTextField().getText());
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    view.recherche(view.getSearchTextField().getText());
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    // Ne pas utiliser
                }
            });
        }

        //Cancel
        view.getAnnulerButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.dispose();
            }
        });
    }

    public FournisseurDetailController(FournisseurAddView fournisseurAddView) {
        connectionDAO = new ConnectionDAO();
        new JOptionPane().showMessageDialog(null, "Veuillez choisir un client, et vous pourrez ensuite agir sur la commande", "Ajouter une commande", JOptionPane.INFORMATION_MESSAGE);

        //Rechercher un client
        fournisseurAddView.getSearchTextFieldClient().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                try {
                    fournisseurAddView.rechercheClient(fournisseurAddView.getSearchTextFieldClient().getText());
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                try {
                    fournisseurAddView.rechercheClient(fournisseurAddView.getSearchTextFieldClient().getText());
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Ne pas utiliser
            }
        });

        //Cancel
        fournisseurAddView.getAnnulerButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fournisseurAddView.dispose();
            }
        });

        //Valider
        fournisseurAddView.getValiderButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connectionDAO.getConnection();
                int clientId = (int) fournisseurAddView.getTableClient().getValueAt(fournisseurAddView.getTableClient().getSelectedRow(), 0);
                try {
                    fournisseurDAO.addFournisseur(clientId, connectionDAO.getConnection());
                } finally {
                    JOptionPane.showMessageDialog(null, "La commande a été ajoutée avec succès", "Succès", JOptionPane.INFORMATION_MESSAGE);
                    fournisseurAddView.dispose();
                }
            }
        });

    }
}
