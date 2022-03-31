package com.gallery.test.graphics;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class CustomPopupMenu extends JPopupMenu {
    public CustomPopupMenu()
    {
        setBorder(new LineBorder(Color.orange));
        setLightWeightPopupEnabled(false);
    }

    @Override
    public void show(Component invoker, int x, int y) {
        Point location=invoker.getLocation();
        if(location.x+invoker.getWidth()<(x+70))
        {
            x-=80;
        }
        x+=10;
        super.show(invoker, x-location.x, y-location.y);
    }
}
