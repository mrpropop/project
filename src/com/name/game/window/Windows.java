package com.name.game.window;

import javax.swing.*;
import java.awt.*;

public class Windows {


    public Windows(int width, int height, String title, Game game){

        JFrame frame = new JFrame(title);
        frame.requestFocus();
        frame.setPreferredSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width, height));
        frame.setMaximumSize(new Dimension(width, height));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.pack();
        frame.setResizable(false);
        frame.add(game);
        game.start();
    }

}



