package View;

import Controler.ArticleAddController;
import Model.ConnectionDAO;
import Model.Fournisseur;
import Model.FournisseurDAO;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ArticleAddView extends JFrame {
    private JTextField name_field;
    private JTextField price_field;
    private JTextField stock_field;
    private JTextField description_field;
    private JComboBox<String> type_comboBox;
    private JComboBox<Fournisseur> fournisseur_comboBox;
    private JButton ajouterButton;
    private JButton annulerButton;
    private JPanel addArticle_panel;
    private ConnectionDAO connectionDAO = new ConnectionDAO();

    public ArticleAddView() {
        // Créer l'interface manuellement
        createUIComponents();
        
        this.setTitle("Ajouter un article");
        this.setContentPane(addArticle_panel);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(450, 650);
        this.setLocationRelativeTo(null);
        
        // Charger les fournisseurs
        chargerFournisseurs();
        
        this.setVisible(true);
        new ArticleAddController(this);
    }

    private void createUIComponents() {
        addArticle_panel = new JPanel();
        addArticle_panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Titre
        JLabel titleLabel = new JLabel("Ajouter un article");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        addArticle_panel.add(titleLabel, gbc);

        // Nom
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        addArticle_panel.add(new JLabel("Nom article :"), gbc);

        name_field = new JTextField(20);
        gbc.gridx = 1;
        addArticle_panel.add(name_field, gbc);

        // Type
        gbc.gridx = 0;
        gbc.gridy = 2;
        addArticle_panel.add(new JLabel("Type :"), gbc);

        type_comboBox = new JComboBox<>(new String[]{"fruit", "legume"});
        gbc.gridx = 1;
        addArticle_panel.add(type_comboBox, gbc);

        // Prix
        gbc.gridx = 0;
        gbc.gridy = 3;
        addArticle_panel.add(new JLabel("Prix unitaire (€) :"), gbc);

        price_field = new JTextField(20);
        gbc.gridx = 1;
        addArticle_panel.add(price_field, gbc);

        // Quantité
        gbc.gridx = 0;
        gbc.gridy = 4;
        addArticle_panel.add(new JLabel("Quantité en stock :"), gbc);

        stock_field = new JTextField(20);
        gbc.gridx = 1;
        addArticle_panel.add(stock_field, gbc);

        // Fournisseur
        gbc.gridx = 0;
        gbc.gridy = 5;
        addArticle_panel.add(new JLabel("Fournisseur :"), gbc);

        fournisseur_comboBox = new JComboBox<>();
        gbc.gridx = 1;
        addArticle_panel.add(fournisseur_comboBox, gbc);

        // Description
        gbc.gridx = 0;
        gbc.gridy = 6;
        addArticle_panel.add(new JLabel("Description :"), gbc);

        description_field = new JTextField(20);
        gbc.gridx = 1;
        addArticle_panel.add(description_field, gbc);

        // Boutons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        ajouterButton = new JButton("Ajouter");
        annulerButton = new JButton("Annuler");
        buttonPanel.add(ajouterButton);
        buttonPanel.add(annulerButton);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        addArticle_panel.add(buttonPanel, gbc);
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

    public JButton getAjouterButton() {
        return ajouterButton;
    }

    public JButton getAnnulerButton() {
        return annulerButton;
    }

    public String getName() {
        return name_field.getText();
    }

    public String getTypeArticle() {
        return (String) type_comboBox.getSelectedItem();
    }

    public double getPrice() {
        return Double.parseDouble(price_field.getText());
    }

    public int getStock() {
        return Integer.parseInt(stock_field.getText());
    }

    public String getDescription() {
        return description_field.getText();
    }

    public int getIdFournisseur() {
        Fournisseur f = (Fournisseur) fournisseur_comboBox.getSelectedItem();
        return f != null ? f.getId() : 0;
    }
}
