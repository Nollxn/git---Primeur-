package Controler;

import Model.ConnectionDAO;
import View.FournisseurPanel;

public class FournisseurController {
    private FournisseurPanel view;
    private ConnectionDAO connectionDAO;

    public FournisseurController(FournisseurPanel view, ConnectionDAO connectionDAO) {
        this.view = view;
        this.connectionDAO = connectionDAO;
        
        // Initialize the controller with the view and database connection
        // Add event listeners for buttons here if needed
    }
    
    public FournisseurPanel getView() {
        return view;
    }
    
    public ConnectionDAO getConnectionDAO() {
        return connectionDAO;
    }
}
