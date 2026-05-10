package Controler;

import Model.ConnectionDAO;
import Model.Fournisseur;
import Model.FournisseurDAO;
import View.FournisseurFormView;
import View.FournisseurTableView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FournisseurFormController {
    private FournisseurFormView view;
    private ConnectionDAO connectionDAO = new ConnectionDAO();
    private FournisseurDAO fournisseurDAO = new FournisseurDAO();

    public FournisseurFormController(FournisseurFormView view) {
        this.view = view;

        // Bouton Valider (Ajouter ou Modifier)
        view.getValiderButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String nom = view.getNom();
                    String telephone = view.getTelephone();
                    String email = view.getEmail();
                    String adresse = view.getAdresse();

                    // Validation basique
                    if (nom == null || nom.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Le nom du fournisseur est obligatoire", "Erreur de saisie", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (view.isEditMode()) {
                        // Mode modification
                        Fournisseur fournisseur = view.getFournisseur();
                        fournisseur.setNom(nom);
                        fournisseur.setTelephone(telephone);
                        fournisseur.setEmail(email);
                        fournisseur.setAdresse(adresse);

                        if (fournisseurDAO.updateFournisseur(fournisseur, connectionDAO.getConnection())) {
                            JOptionPane.showMessageDialog(null, "Le fournisseur '" + nom + "' a été modifié avec succès");
                            view.dispose();
                        } else {
                            JOptionPane.showMessageDialog(null, "Erreur lors de la modification du fournisseur", "Erreur", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        // Mode ajout
                        Fournisseur fournisseur = new Fournisseur(nom, telephone, email, adresse);

                        if (fournisseurDAO.addFournisseur(fournisseur, connectionDAO.getConnection())) {
                            JOptionPane.showMessageDialog(null, "Le fournisseur '" + nom + "' a été ajouté avec succès");
                            view.dispose();
                        } else {
                            JOptionPane.showMessageDialog(null, "Erreur lors de l'ajout du fournisseur", "Erreur", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erreur: " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Bouton Annuler
        view.getAnnulerButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.dispose();
            }
        });
    }
}
