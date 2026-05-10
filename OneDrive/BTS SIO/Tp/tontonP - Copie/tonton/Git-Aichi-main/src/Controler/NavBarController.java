package Controler;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import View.*;

public class NavBarController {
    private MainFrame mainFrame;

    public NavBarController(MainFrame mainFrame) throws SQLException {
        this.mainFrame = mainFrame;

        // Gestion des clics sur la navbar
        mainFrame.getArticlesTablePageButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPage("ArticlesTableView");
            }
        });

        mainFrame.getCommandesTablePageButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPage("FournisseurTableView");
            }
        });
    }

    public void showPage(String pageName) {
        CardLayout layout = (CardLayout) mainFrame.getContentPanel().getLayout();
        layout.show(mainFrame.getContentPanel(), pageName);
    }

    public MainFrame getMainFrame() {
        return mainFrame;
    }
}
