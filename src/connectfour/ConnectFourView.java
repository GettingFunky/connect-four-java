package connectfour;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.LineUnavailableException;

@SuppressWarnings("serial")
public class ConnectFourView extends JPanel {
    private final ConnectFourModel model;
    private static final int CELL_SIZE = 80;
    private static final Color BOARD_COLOR = new Color(0, 58, 212); // Original Connect4 blue
    private static final Color PLAYER1_COLOR = Color.RED;
    private static final Color PLAYER2_COLOR = Color.YELLOW;
    private static final Color GRID_COLOR = (new Color(0, 0, 0, 80)); // Color for grid lines

    public ConnectFourView() {
        model = new ConnectFourModel();
        setPreferredSize(new Dimension(
            CELL_SIZE * ConnectFourModel.COLS,
            CELL_SIZE * ConnectFourModel.ROWS
        ));
        setupMouseListener();
    }
    
    private void playSound(String soundFile) {
        try {
            File file = new File(soundFile);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            ex.printStackTrace();
        }
    }

    private void setupMouseListener() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (model.isGameOver()) return;

                int column = e.getX() / CELL_SIZE;
                int row = model.dropToken(column);

                if (row != -1) {
                	playSound("assets/sounds/Token_Toss.wav");
                    handleValidMove(row, column);
                } else {
                	playSound("assets/sounds/Col_Full.wav");
                }
                repaint();
            }
        });
    }

    private void handleValidMove(int row, int column) {
        if (model.checkWin(row, column)) {
            model.setGameOver(true);
            showGameResult(false);
        } else if (model.isBoardFull()) {
            model.setGameOver(true);
            showGameResult(true);
        } else {
            model.switchPlayer();
        }
    }

    private void showGameResult(boolean isDraw) {
        String message = isDraw ? "Game Over - It's a draw!" : 
            "Player " + (model.getCurrentPlayer() == '●' ? "1" : "2") + " wins!";
        playSound("assets/sounds/Game_End_Jingle.wav");
        JOptionPane.showMessageDialog(this, message, "Game Over", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        drawBoardBackground(g2d);
        drawGridLines(g2d); // Draw grid lines
        drawTokens(g2d);
    }

    private void drawBoardBackground(Graphics2D g2d) {
        g2d.setColor(BOARD_COLOR);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }

    private void drawGridLines(Graphics2D g2d) {
        g2d.setColor(GRID_COLOR);

        // Draw vertical lines
        for (int col = 0; col <= ConnectFourModel.COLS; col++) {
            int x = col * CELL_SIZE;
            g2d.drawLine(x, 0, x, ConnectFourModel.ROWS * CELL_SIZE);
        }

        // Draw horizontal lines
        for (int row = 0; row <= ConnectFourModel.ROWS; row++) {
            int y = row * CELL_SIZE;
            g2d.drawLine(0, y, ConnectFourModel.COLS * CELL_SIZE, y);
        }
    }

    private void drawTokens(Graphics2D g2d) {
        for (int row = 0; row < ConnectFourModel.ROWS; row++) {
            for (int col = 0; col < ConnectFourModel.COLS; col++) {
                int viewRow = ConnectFourModel.ROWS - 1 - row;
                int x = col * CELL_SIZE;
                int y = viewRow * CELL_SIZE;
                
                drawToken(g2d, row, col, x, y);
            }
        }
    }

    private void drawToken(Graphics2D g2d, int modelRow, int col, int x, int y) {
        char token = model.getBoard()[modelRow][col];
        if (token != ' ') {
            g2d.setColor(token == '●' ? PLAYER1_COLOR : PLAYER2_COLOR);
            g2d.fillOval(x + 5, y + 5, CELL_SIZE - 10, CELL_SIZE - 10);
        }
    }
}