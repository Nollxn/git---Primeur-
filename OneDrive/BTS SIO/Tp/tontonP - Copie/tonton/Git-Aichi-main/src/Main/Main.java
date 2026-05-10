package Main;

import View.MainFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Définir le Look and Feel du système
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Lancer l'application dans le thread Swing
        SwingUtilities.invokeLater(() -> {
            new MainFrame();
        });
    }
}