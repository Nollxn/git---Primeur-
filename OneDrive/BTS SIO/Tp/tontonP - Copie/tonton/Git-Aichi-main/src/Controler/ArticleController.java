package Controler;

import Model.ConnectionDAO;
import View.ArticlePanel;

public class ArticleController {
    private ArticlePanel view;
    private ConnectionDAO connectionDAO;

    public ArticleController(ArticlePanel view, ConnectionDAO connectionDAO) {
        this.view = view;
        this.connectionDAO = connectionDAO;
        
        // Initialize the controller with the view and database connection
        // Add event listeners for buttons here if needed
    }
    
    public ArticlePanel getView() {
        return view;
    }
    
    public ConnectionDAO getConnectionDAO() {
        return connectionDAO;
    }
}
