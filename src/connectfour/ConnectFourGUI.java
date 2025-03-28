/**
 * The graphical user interface for the Connect Four game.
 * Displays a splash screen and initializes the game window.
 */

package connectfour;

import javax.swing.*;

public class ConnectFourGUI {
    /**
     * The main entry point for the application.
     * Displays a splash screen before launching the game.
     */
    public static void main(String[] args) {
        // Create a splash screen using JWindow
        JWindow splash = new JWindow();
        ImageIcon splashIcon = new ImageIcon("assets/images/introImage.jpg");
        JLabel splashLabel = new JLabel(splashIcon);
        splash.getContentPane().add(splashLabel);
        splash.pack();
        splash.setLocationRelativeTo(null); // Center the splash screen
        splash.setVisible(true);
        
        // Pause for a few seconds (e.g., 3 seconds)
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        
        // Dispose the splash screen
        splash.dispose();
        
        // Now launch the main Connect Four window
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Connect Four Game");
            // Add the game view (and other components as needed)
            ConnectFourView gameView = new ConnectFourView();
            frame.add(gameView);
            
            frame.pack();
            frame.setLocationRelativeTo(null); // Center window
            frame.setResizable(false);
            frame.setVisible(true);
        });
    }
}