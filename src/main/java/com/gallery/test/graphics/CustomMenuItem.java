package com.gallery.test.graphics;

import javax.swing.*;
import java.awt.*;

public class CustomMenuItem extends JMenuItem {
    public CustomMenuItem(String text)
    {
        setText(text);
        setBorderPainted(false);
        setBackground(Color.WHITE);
        Font fieldFont = new Font("Arial", Font.PLAIN, 16);
        setFont(fieldFont);
        CustomMenuItemUI menuUI = new CustomMenuItemUI(Color.lightGray);
        setUI(menuUI);
    }
    public Dimension getPreferredSize() {
        return new Dimension(60, 30);

    }
}
