package Controler;

import Model.ConnectionDAO;
import Model.Fournisseur;
import Model.FournisseurDAO;
import View.FournisseurFormView;
import View.FournisseurTableView;
import java.sql.SQLException;
import javax.swing.*;

public class FournisseurTableController {
    private FournisseurDAO fournisseurDAO = new FournisseurDAO();
    private FournisseurTableView fournisseurTableView;
    private ConnectionDAO connectionDAO = new ConnectionDAO();

    public FournisseurTableController(FournisseurTableView fournisseurTableView) {
        this.fournisseurTableView = fournisseurTableView;

        // Mettre à jour la table
        fournisseurTableView.getUpdateButton().addActionListener(e -> {
            fournisseurTableView.updateTable();
        });

        // Ajouter un fournisseur
        fournisseurTableView.getAjouterFournisseur().addActionListener(e -> {
            new FournisseurFormView();
        });

        // Modifier un fournisseur
        fournisseurTableView.getModifierButton().addActionListener(e -> {
            int selectedRow = fournisseurTableView.getTable().getSelectedRow();
            if (selectedRow != -1) {
                try {
                    int id = (int) fournisseurTableView.getTableModel().getValueAt(selectedRow, 0);
                    Fournisseur fournisseur = fournisseurDAO.getFournisseurById(id, connectionDAO.getConnection());
                    
                    if (fournisseur != null) {
                        new FournisseurFormView(fournisseur);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erreur lors de la récupération du fournisseur", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Veuillez sélectionner un fournisseur à modifier");
            }
        });

        // Supprimer un fournisseur
        fournisseurTableView.getSupprimerButton().addActionListener(e -> {
            int selectedRow = fournisseurTableView.getTable().getSelectedRow();
            
            if (selectedRow != -1) {
                int id = (int) fournisseurTableView.getTableModel().getValueAt(selectedRow, 0);
                String nom = (String) fournisseurTableView.getTableModel().getValueAt(selectedRow, 1);
                
                try {
                    // Vérifier si le fournisseur a des articles associés
                    if (fournisseurDAO.hasFournisseurArticles(id, connectionDAO.getConnection())) {
                        JOptionPane.showMessageDialog(null, 
                            "Impossible de supprimer le fournisseur '" + nom + "' car il a des articles associés.\nVeuillez d'abord supprimer ou réassigner ces articles.", 
                            "Suppression impossible", 
                            JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    
                    // Demande de confirmation
                    int response = JOptionPane.showConfirmDialog(null, 
                        "Voulez-vous vraiment supprimer le fournisseur '" + nom + "' ?", 
                        "Confirmation", 
                        JOptionPane.YES_NO_OPTION);

                    if (response == JOptionPane.YES_OPTION) {
                        if (fournisseurDAO.deleteFournisseur(id, connectionDAO.getConnection())) {
                            fournisseurTableView.getTableModel().removeRow(selectedRow);
                            JOptionPane.showMessageDialog(null, "Le fournisseur '" + nom + "' a été supprimé avec succès");
                        } else {
                            JOptionPane.showMessageDialog(null, "Erreur lors de la suppression", "Erreur", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Suppression annulée");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erreur de base de données: " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Veuillez sélectionner un fournisseur à supprimer");
            }
        });
    }
}
