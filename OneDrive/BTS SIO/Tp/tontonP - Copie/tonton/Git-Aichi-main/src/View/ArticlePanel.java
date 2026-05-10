package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ArticlePanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnAjouter;
    private JButton btnModifier;
    private JButton btnSupprimer;
    private JButton btnTrierNomCroissant;
    private JButton btnTrierNomDecroissant;
    private JButton btnTrierPrixCroissant;
    private JButton btnTrierPrixDecroissant;
    private JButton btnRafraichir;

    public ArticlePanel() {
        setLayout(new BorderLayout());
        
        // Panel du haut avec les boutons
        JPanel panelBoutons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnAjouter = new JButton("Ajouter");
        btnModifier = new JButton("Modifier");
        btnSupprimer = new JButton("Supprimer");
        btnRafraichir = new JButton("Rafraîchir");
        
        panelBoutons.add(btnAjouter);
        panelBoutons.add(btnModifier);
        panelBoutons.add(btnSupprimer);
        panelBoutons.add(btnRafraichir);
        
        // Panel de tri
        JPanel panelTri = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelTri.add(new JLabel("Trier par :"));
        btnTrierNomCroissant = new JButton("Nom ↑");
        btnTrierNomDecroissant = new JButton("Nom ↓");
        btnTrierPrixCroissant = new JButton("Prix ↑");
        btnTrierPrixDecroissant = new JButton("Prix ↓");
        
        panelTri.add(btnTrierNomCroissant);
        panelTri.add(btnTrierNomDecroissant);
        panelTri.add(btnTrierPrixCroissant);
        panelTri.add(btnTrierPrixDecroissant);
        
        // Panel combinant boutons et tri
        JPanel panelHaut = new JPanel(new BorderLayout());
        panelHaut.add(panelBoutons, BorderLayout.WEST);
        panelHaut.add(panelTri, BorderLayout.EAST);
        
        add(panelHaut, BorderLayout.NORTH);
        
        // Tableau des articles
        String[] colonnes = {"ID", "Nom", "Type", "Prix Unitaire (€)", "Quantité Stock", "ID Fournisseur", "Nom Fournisseur"};
        tableModel = new DefaultTableModel(colonnes, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    // Getters
    public JTable getTable() { return table; }
    public DefaultTableModel getTableModel() { return tableModel; }
    public JButton getBtnAjouter() { return btnAjouter; }
    public JButton getBtnModifier() { return btnModifier; }
    public JButton getBtnSupprimer() { return btnSupprimer; }
    public JButton getBtnTrierNomCroissant() { return btnTrierNomCroissant; }
    public JButton getBtnTrierNomDecroissant() { return btnTrierNomDecroissant; }
    public JButton getBtnTrierPrixCroissant() { return btnTrierPrixCroissant; }
    public JButton getBtnTrierPrixDecroissant() { return btnTrierPrixDecroissant; }
    public JButton getBtnRafraichir() { return btnRafraichir; }
    
    // Méthode pour ajouter une ligne au tableau
    public void ajouterLigne(Object[] data) {
        tableModel.addRow(data);
    }
    
    // Méthode pour vider le tableau
    public void viderTableau() {
        tableModel.setRowCount(0);
    }
}