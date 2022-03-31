package com.gallery.test.graphics;

import javax.swing.plaf.basic.BasicMenuItemUI;
import java.awt.*;

public class CustomMenuItemUI extends BasicMenuItemUI {
    public CustomMenuItemUI(Color color){
        super.selectionBackground = color;
    }
}
