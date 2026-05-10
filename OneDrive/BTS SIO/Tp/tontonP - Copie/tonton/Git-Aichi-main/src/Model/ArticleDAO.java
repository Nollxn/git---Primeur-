package Model;

import java.sql.*;
import java.util.ArrayList;

public class ArticleDAO {
    
    // Récupérer tous les articles avec le nom du fournisseur
    public ArrayList<Article> getAllArticles(Connection connection) throws SQLException {
        ArrayList<Article> articles = new ArrayList<>();
        String query = "SELECT a.*, f.nom as nom_fournisseur FROM article a " +
                      "LEFT JOIN fournisseur f ON a.id_fournisseur = f.id_fournisseur " +
                      "ORDER BY a.nom";
        
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        
        while (rs.next()) {
            Article article = new Article();
            article.setIdArticle(rs.getInt("id_article"));
            article.setNom(rs.getString("nom"));
            article.setType(rs.getString("type"));
            article.setPrixUnitaire(rs.getDouble("prix_unitaire"));
            article.setQuantiteStock(rs.getInt("quantite_stock"));
            article.setIdFournisseur(rs.getInt("id_fournisseur"));
            article.setNomFournisseur(rs.getString("nom_fournisseur"));
            articles.add(article);
        }
        return articles;
    }
    
    // Ajouter un article
    public boolean ajouterArticle(Article article, Connection connection) throws SQLException {
        String query = "INSERT INTO article (nom, type, prix_unitaire, quantite_stock, id_fournisseur) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, article.getNom());
        ps.setString(2, article.getType());
        ps.setDouble(3, article.getPrixUnitaire());
        ps.setInt(4, article.getQuantiteStock());
        ps.setInt(5, article.getIdFournisseur());
        
        int result = ps.executeUpdate();
        return result > 0;
    }
    
    // Modifier un article
    public boolean modifierArticle(Article article, Connection connection) throws SQLException {
        String query = "UPDATE article SET nom=?, type=?, prix_unitaire=?, quantite_stock=?, id_fournisseur=? WHERE id_article=?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, article.getNom());
        ps.setString(2, article.getType());
        ps.setDouble(3, article.getPrixUnitaire());
        ps.setInt(4, article.getQuantiteStock());
        ps.setInt(5, article.getIdFournisseur());
        ps.setInt(6, article.getIdArticle());
        
        int result = ps.executeUpdate();
        return result > 0;
    }
    
    // Supprimer un article
    public boolean supprimerArticle(int idArticle, Connection connection) throws SQLException {
        String query = "DELETE FROM article WHERE id_article=?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, idArticle);
        
        int result = ps.executeUpdate();
        return result > 0;
    }
    
    // Trier les articles par nom (croissant ou décroissant)
    public ArrayList<Article> trierParNom(Connection connection, boolean croissant) throws SQLException {
        ArrayList<Article> articles = new ArrayList<>();
        String ordre = croissant ? "ASC" : "DESC";
        String query = "SELECT a.*, f.nom as nom_fournisseur FROM article a " +
                      "LEFT JOIN fournisseur f ON a.id_fournisseur = f.id_fournisseur " +
                      "ORDER BY a.nom " + ordre;
        
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        
        while (rs.next()) {
            Article article = new Article();
            article.setIdArticle(rs.getInt("id_article"));
            article.setNom(rs.getString("nom"));
            article.setType(rs.getString("type"));
            article.setPrixUnitaire(rs.getDouble("prix_unitaire"));
            article.setQuantiteStock(rs.getInt("quantite_stock"));
            article.setIdFournisseur(rs.getInt("id_fournisseur"));
            article.setNomFournisseur(rs.getString("nom_fournisseur"));
            articles.add(article);
        }
        return articles;
    }
    
    // Trier les articles par prix (croissant ou décroissant)
    public ArrayList<Article> trierParPrix(Connection connection, boolean croissant) throws SQLException {
        ArrayList<Article> articles = new ArrayList<>();
        String ordre = croissant ? "ASC" : "DESC";
        String query = "SELECT a.*, f.nom as nom_fournisseur FROM article a " +
                      "LEFT JOIN fournisseur f ON a.id_fournisseur = f.id_fournisseur " +
                      "ORDER BY a.prix_unitaire " + ordre;
        
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        
        while (rs.next()) {
            Article article = new Article();
            article.setIdArticle(rs.getInt("id_article"));
            article.setNom(rs.getString("nom"));
            article.setType(rs.getString("type"));
            article.setPrixUnitaire(rs.getDouble("prix_unitaire"));
            article.setQuantiteStock(rs.getInt("quantite_stock"));
            article.setIdFournisseur(rs.getInt("id_fournisseur"));
            article.setNomFournisseur(rs.getString("nom_fournisseur"));
            articles.add(article);
        }
        return articles;
    }
}