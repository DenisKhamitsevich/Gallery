package com.gallery.test.fullscreen;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class FullscreenImage {

    public FullscreenImage(BufferedImage bf,String text)
    {
        JFrame frame = new JFrame(text);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setMinimumSize(new Dimension(bf.getWidth(),bf.getHeight()));
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        JLabel jl=new JLabel(new ImageIcon(bf));
        frame.add(jl);
    }

}
