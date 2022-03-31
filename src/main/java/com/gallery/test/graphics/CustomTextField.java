package com.gallery.test.graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CustomTextField extends JTextField {
    public CustomTextField(){
        setText("Search");
        setBorder(BorderFactory.createLineBorder(Color.orange));
        Font fieldFont = new Font("Arial", Font.ITALIC, 20);
        setFont(fieldFont);
        setColumns(12);
        setCursor(new Cursor(Cursor.TEXT_CURSOR));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setText("");
            }
        });

    }


}
