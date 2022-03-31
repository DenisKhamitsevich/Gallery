package com.gallery.test.model;


import java.awt.image.BufferedImage;

public class CustomImage {
    private String name;
    private BufferedImage image;

    public CustomImage(String name, BufferedImage image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public BufferedImage getImage() {
        return image;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if(obj.getClass()!=getClass())
            return false;
        CustomImage temp=(CustomImage) obj;
        return getName().equals(temp.getName());
    }
}
