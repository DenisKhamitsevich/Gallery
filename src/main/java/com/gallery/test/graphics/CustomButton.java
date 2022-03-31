package com.gallery.test.graphics;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;

public class CustomButton extends JButton {
    public CustomButton(String text)
    {
        setText(text);
        Font fieldFont = new Font("Arial", Font.PLAIN, 16);
        setFont(fieldFont);
        setBackground(Color.WHITE);
        setBorder(new LineBorder(Color.orange));
        setFocusPainted(false);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(Color.lightGray);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(Color.white);
            }

        });


    }

    public Dimension getPreferredSize() {
        return new Dimension(90, 30);

    }
}
