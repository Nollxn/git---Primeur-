package Model;
import Controler.PasswordHasher;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.*;


public class UserDAO {
    public static boolean est_connecte = false;
    public static boolean est_superadmin = false;
    public static boolean est_admin= false;

    public boolean addUser(User user,Connection connection) throws SQLException {
        String query = "CALL add_user(?,?,?)";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1,user.getIdentifiant());
        stmt.setString(2, user.getPassword());
        stmt.setString(3, user.getPrivilege());
        try {
            stmt.executeUpdate();
            System.out.println("Utilisateur ajouter avec succès");
            return true;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);

            return false;
        }

    }

    public boolean checkUser(User user, Connection connection) throws SQLException {
        String query = "SELECT user_password FROM users WHERE user_name=?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, user.getIdentifiant());
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            String storedHash = rs.getString("user_password");
            return PasswordHasher.verifyPassword(user.getPassword(), storedHash);
        }
        return false; // utilisateur non trouvé
    }

    public boolean checkPrivilege(User user, Connection connection, String privilege) throws SQLException {
        String query = "SELECT user_password FROM users WHERE user_name=? AND user_privilege=?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, user.getIdentifiant());
        stmt.setString(2, privilege);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            String storedHash = rs.getString("user_password");
            return PasswordHasher.verifyPassword(user.getPassword(), storedHash);
        }
        return false; // user not found or privilege does not match
    }

    public boolean loginUser(User user, Connection connection) throws SQLException {
        if (checkPrivilege(user, connection,"SuperAdmin")) {
            est_connecte = true;
            est_superadmin = true;
            est_admin = true;
            return true;
        } else if (checkPrivilege(user, connection,"Admin")) {
            est_connecte = true;
            est_admin = true;
            return true;

        } else if (checkUser(user, connection)) {
            est_connecte = true;
            return true;
        }
        return false;

    }

    public static ArrayList<User> getUsers(Connection connection) throws SQLException {
        ArrayList<User> lstUsers = new ArrayList<>();
        String query = "SELECT id_user, user_name,user_privilege FROM `users`";
        PreparedStatement stmt = connection.prepareStatement(query);
        try {
            ResultSet rs = stmt.executeQuery();
            // Display all the data in the table.
            while (rs.next()) {
                User user = new User();
                user.setID(rs.getInt("id_user"));
                user.setIdentifiant(rs.getString("user_name"));
                user.setPrivilege(rs.getString("user_privilege"));

                lstUsers.add(user);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return lstUsers;
    }

    public static void resetSession(){
        est_connecte = false;
        est_superadmin = false;
        est_admin = false;
    }

    public void deleteUser(int id, Connection connection) throws SQLException {
        String query = "DELETE FROM `users` WHERE id_user=?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, id);
        try {
            stmt.executeUpdate();
            System.out.println("Utilisateur supprimer avec succès");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
    }
}
