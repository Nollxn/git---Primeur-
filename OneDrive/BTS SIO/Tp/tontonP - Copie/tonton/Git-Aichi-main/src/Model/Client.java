package Model;

public class Client {
    private int id_client;
    private String client_nom;
    private String client_prenom;
    private String client_adresse;
    private String client_mail;
    private String client_tel;

    public Client(int id_client, String client_nom, String client_prenom, String client_adresse, String client_mail, String client_tel) {
        this.id_client = id_client;
        this.client_nom = client_nom;
        this.client_prenom = client_prenom;
        this.client_adresse = client_adresse;
        this.client_mail = client_mail;
        this.client_tel = client_tel;
    }

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    public String getClient_nom() {
        return client_nom;
    }

    public void setClient_nom(String client_nom) {
        this.client_nom = client_nom;
    }

    public String getClient_prenom() {
        return client_prenom;
    }

    public void setClient_prenom(String client_prenom) {
        this.client_prenom = client_prenom;
    }

    public String getClient_adresse() {
        return client_adresse;
    }

    public void setClient_adresse(String client_adresse) {
        this.client_adresse = client_adresse;
    }

    public String getClient_mail() {
        return client_mail;
    }

    public void setClient_mail(String client_mail) {
        this.client_mail = client_mail;
    }

    public String getClient_tel() {
        return client_tel;
    }

    public void setClient_tel(String client_tel) {
        this.client_tel = client_tel;
    }
}