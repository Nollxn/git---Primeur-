package View;

import Controler.ArticleEditController;
import Model.Articles;
import Model.ConnectionDAO;
import Model.Fournisseur;
import Model.FournisseurDAO;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class ArticleEditView extends JFrame {
    private JTextField name_field;
    private JTextField price_field;
    private JTextField stock_field;
    private JTextField description_field;
    private JComboBox<String> type_comboBox;
    private JComboBox<Fournisseur> fournisseur_comboBox;
    private JButton modifierButton;
    private JButton annulerButton;
    private JPanel editArticle_panel;
    private Articles article;
    private ConnectionDAO connectionDAO = new ConnectionDAO();

    public ArticleEditView(Articles article) {
        this.article = article;
        
        // Créer l'interface manuellement
        createUIComponents();
        
        this.setTitle("Modifier l'article");
        this.setContentPane(editArticle_panel);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(450, 650);
        this.setLocationRelativeTo(null);
        
        // Charger les fournisseurs
        chargerFournisseurs();
        
        // Pré-remplir les champs avec les données de l'article
        name_field.setText(article.getNom_article());
        type_comboBox.setSelectedItem(article.getType_article());
        price_field.setText(String.valueOf(article.getPrix_article()));
        stock_field.setText(String.valueOf(article.getQuantite_article()));
        description_field.setText(article.getDescription_article());
        
        // Sélectionner le fournisseur actuel
        if (article.getId_fournisseur() > 0) {
            for (int i = 0; i < fournisseur_comboBox.getItemCount(); i++) {
                Fournisseur f = fournisseur_comboBox.getItemAt(i);
                if (f != null && f.getId() == article.getId_fournisseur()) {
                    fournisseur_comboBox.setSelectedIndex(i);
                    break;
                }
            }
        }
        
        new ArticleEditController(this);
        this.setVisible(true);
    }

    private void createUIComponents() {
        editArticle_panel = new JPanel();
        editArticle_panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Titre
        JLabel titleLabel = new JLabel("Modifier l'article");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        editArticle_panel.add(titleLabel, gbc);

        // Nom
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        editArticle_panel.add(new JLabel("Nom article :"), gbc);

        name_field = new JTextField(20);
        gbc.gridx = 1;
        editArticle_panel.add(name_field, gbc);

        // Type
        gbc.gridx = 0;
        gbc.gridy = 2;
        editArticle_panel.add(new JLabel("Type :"), gbc);

        type_comboBox = new JComboBox<>(new String[]{"fruit", "legume"});
        gbc.gridx = 1;
        editArticle_panel.add(type_comboBox, gbc);

        // Prix
        gbc.gridx = 0;
        gbc.gridy = 3;
        editArticle_panel.add(new JLabel("Prix unitaire (€) :"), gbc);

        price_field = new JTextField(20);
        gbc.gridx = 1;
        editArticle_panel.add(price_field, gbc);

        // Quantité
        gbc.gridx = 0;
        gbc.gridy = 4;
        editArticle_panel.add(new JLabel("Quantité en stock :"), gbc);

        stock_field = new JTextField(20);
        gbc.gridx = 1;
        editArticle_panel.add(stock_field, gbc);

        // Fournisseur
        gbc.gridx = 0;
        gbc.gridy = 5;
        editArticle_panel.add(new JLabel("Fournisseur :"), gbc);

        fournisseur_comboBox = new JComboBox<>();
        gbc.gridx = 1;
        editArticle_panel.add(fournisseur_comboBox, gbc);

        // Description
        gbc.gridx = 0;
        gbc.gridy = 6;
        editArticle_panel.add(new JLabel("Description :"), gbc);

        description_field = new JTextField(20);
        gbc.gridx = 1;
        editArticle_panel.add(description_field, gbc);

        // Boutons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        modifierButton = new JButton("Modifier");
        annulerButton = new JButton("Annuler");
        buttonPanel.add(modifierButton);
        buttonPanel.add(annulerButton);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        editArticle_panel.add(buttonPanel, gbc);
    }

    private void chargerFournisseurs() {
        try {
            FournisseurDAO fournisseurDAO = new FournisseurDAO();
            List<Fournisseur> fournisseurs = fournisseurDAO.getFournisseurs(connectionDAO.getConnection());
            
            fournisseur_comboBox.removeAllItems();
            for (Fournisseur f : fournisseurs) {
                fournisseur_comboBox.addItem(f);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des fournisseurs", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Méthode pour compatibilité avec le contrôleur existant
    public JButton getFormSend() {
        return modifierButton;
    }

    public JButton getModifierButton() {
        return modifierButton;
    }

    public JButton getAnnulerButton() {
        return annulerButton;
    }

    public String getNom() {
        return name_field.getText();
    }

    public String getTypeArticle() {
        return (String) type_comboBox.getSelectedItem();
    }

    public String getPrix() {
        return price_field.getText();
    }

    public String getQuantite() {
        return stock_field.getText();
    }

    public String getDescription() {
        return description_field.getText();
    }

    public String getFournisseurId() {
        Fournisseur f = (Fournisseur) fournisseur_comboBox.getSelectedItem();
        return f != null ? String.valueOf(f.getId()) : "0";
    }

    public Articles getArticle() {
        return article;
    }
}

