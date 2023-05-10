package main;

import javax.swing.*;

import java.sql.SQLException;


public class Main {
    public static JFrame window;

    public static void main(String[] args) throws SQLException {
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Hotaka, The Last Path");


        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        gamePanel.Config.getOptions(gamePanel.c,gamePanel.stmt);
        if(gamePanel.FullScreenOn == true)
        {
            window.setUndecorated(true);
        }

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.setupGame();
        gamePanel.startGameThread();
    }
    ///4087 linii cod
}
