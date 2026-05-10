package Model;

import java.sql.*;
import java.util.ArrayList;

public class FournisseurDAO {
    
    // Récupérer tous les fournisseurs
    public ArrayList<Fournisseur> getAllFournisseurs(Connection connection) throws SQLException {
        ArrayList<Fournisseur> fournisseurs = new ArrayList<>();
        String query = "SELECT * FROM fournisseur ORDER BY nom";
        
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        
        while (rs.next()) {
            Fournisseur fournisseur = new Fournisseur();
            fournisseur.setIdFournisseur(rs.getInt("id_fournisseur"));
            fournisseur.setNom(rs.getString("nom"));
            fournisseur.setTelephone(rs.getString("telephone"));
            fournisseur.setEmail(rs.getString("email"));
            fournisseur.setAdresse(rs.getString("adresse"));
            fournisseurs.add(fournisseur);
        }
        return fournisseurs;
    }
    
    // Ajouter un fournisseur
    public boolean ajouterFournisseur(Fournisseur fournisseur, Connection connection) throws SQLException {
        String query = "INSERT INTO fournisseur (nom, telephone, email, adresse) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, fournisseur.getNom());
        ps.setString(2, fournisseur.getTelephone());
        ps.setString(3, fournisseur.getEmail());
        ps.setString(4, fournisseur.getAdresse());
        
        int result = ps.executeUpdate();
        return result > 0;
    }
    
    // Modifier un fournisseur
    public boolean modifierFournisseur(Fournisseur fournisseur, Connection connection) throws SQLException {
        String query = "UPDATE fournisseur SET nom=?, telephone=?, email=?, adresse=? WHERE id_fournisseur=?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, fournisseur.getNom());
        ps.setString(2, fournisseur.getTelephone());
        ps.setString(3, fournisseur.getEmail());
        ps.setString(4, fournisseur.getAdresse());
        ps.setInt(5, fournisseur.getIdFournisseur());
        
        int result = ps.executeUpdate();
        return result > 0;
    }
    
    // Supprimer un fournisseur (si aucun article ne lui est lié)
    public boolean supprimerFournisseur(int idFournisseur, Connection connection) throws SQLException {
        // Vérifier si des articles sont liés à ce fournisseur
        String checkQuery = "SELECT COUNT(*) as count FROM article WHERE id_fournisseur=?";
        PreparedStatement checkPs = connection.prepareStatement(checkQuery);
        checkPs.setInt(1, idFournisseur);
        ResultSet rs = checkPs.executeQuery();
        
        if (rs.next() && rs.getInt("count") > 0) {
            throw new SQLException("Impossible de supprimer ce fournisseur : des articles lui sont liés.");
        }
        
        String query = "DELETE FROM fournisseur WHERE id_fournisseur=?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, idFournisseur);
        
        int result = ps.executeUpdate();
        return result > 0;
    }
    
    // Vérifier si un fournisseur a des articles liés
    public boolean hasArticles(int idFournisseur, Connection connection) throws SQLException {
        String query = "SELECT COUNT(*) as count FROM article WHERE id_fournisseur=?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, idFournisseur);
        ResultSet rs = ps.executeQuery();
        
        if (rs.next()) {
            return rs.getInt("count") > 0;
        }
        return false;
    }
}