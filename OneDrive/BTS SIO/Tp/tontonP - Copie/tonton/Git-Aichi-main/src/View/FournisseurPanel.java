package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FournisseurPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnAjouter;
    private JButton btnModifier;
    private JButton btnSupprimer;
    private JButton btnRafraichir;

    public FournisseurPanel() {
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
        
        add(panelBoutons, BorderLayout.NORTH);
        
        // Tableau des fournisseurs
        String[] colonnes = {"ID", "Nom", "Téléphone", "Email", "Adresse"};
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