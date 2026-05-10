package Model;

public class Article {
    private int idArticle;
    private String nom;
    private String type; // "fruit" ou "legume"
    private double prixUnitaire;
    private int quantiteStock;
    private int idFournisseur;
    private String nomFournisseur; // Pour l'affichage

    public Article() {}

    public Article(int idArticle, String nom, String type, double prixUnitaire, int quantiteStock, int idFournisseur) {
        this.idArticle = idArticle;
        this.nom = nom;
        this.type = type;
        this.prixUnitaire = prixUnitaire;
        this.quantiteStock = quantiteStock;
        this.idFournisseur = idFournisseur;
    }

    // Getters
    public int getIdArticle() { return idArticle; }
    public String getNom() { return nom; }
    public String getType() { return type; }
    public double getPrixUnitaire() { return prixUnitaire; }
    public int getQuantiteStock() { return quantiteStock; }
    public int getIdFournisseur() { return idFournisseur; }
    public String getNomFournisseur() { return nomFournisseur; }

    // Setters
    public void setIdArticle(int idArticle) { this.idArticle = idArticle; }
    public void setNom(String nom) { this.nom = nom; }
    public void setType(String type) { this.type = type; }
    public void setPrixUnitaire(double prixUnitaire) { this.prixUnitaire = prixUnitaire; }
    public void setQuantiteStock(int quantiteStock) { this.quantiteStock = quantiteStock; }
    public void setIdFournisseur(int idFournisseur) { this.idFournisseur = idFournisseur; }
    public void setNomFournisseur(String nomFournisseur) { this.nomFournisseur = nomFournisseur; }
}