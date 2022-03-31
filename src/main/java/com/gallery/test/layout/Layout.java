package com.gallery.test.layout;

import javax.swing.*;
import java.awt.*;

public class Layout extends FlowLayout {
    public JFrame mainFrame;
    public Layout(JFrame frame)
    {
        mainFrame=frame;
    }

    @Override
    public void layoutContainer(Container target) {
        Component list[] = target.getComponents();
        int currentX;
        int currentY=5;
        int height=0,width;
        for (int i = 0; i < 4; i++) {
            currentX=5;
            for (int j = 0; j < 4; j++) {
                if((i*4+j)>=list.length)
                    break;
                height=(int)Math.round(mainFrame.getHeight()*0.2);
                width=(int)Math.round(mainFrame.getWidth()*0.2);
                list[i*4+j].setBounds(currentX, currentY, width, height);
                currentX += (int)Math.round(mainFrame.getWidth()*0.25);
            }
            currentY+=height+9;
        }
    }
}
