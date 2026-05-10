package Model;

public class User {
    private String identifiant;
    private String password;
    private int ID;
    private String privilege;

    public User(){
    }

    public User(String identifiant,String password){
        this.identifiant=identifiant;
        this.password=password;
    }

    public User(int ID,String identifiant,String password){
        this.identifiant=identifiant;
        this.password=password;
        this.ID=ID;
    }

    public User(int ID,String identifiant){
        this.identifiant=identifiant;
        this.ID=ID;
    }

    public User(String identifiant, String password, String privilege) {
        this.identifiant = identifiant;
        this.password = password;
        this.privilege = privilege;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }

    public String getPrivilege() {
        return privilege;
    }
}

