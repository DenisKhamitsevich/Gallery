package com.gallery.test.base;

import com.gallery.test.graphics.CustomButton;
import com.gallery.test.graphics.CustomMenuItem;
import com.gallery.test.graphics.CustomTextField;
import com.gallery.test.layout.Layout;
import com.gallery.test.fullscreen.FullscreenImage;
import com.gallery.test.graphics.CustomPopupMenu;
import com.gallery.test.model.CustomImage;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Base extends JFrame{
    private CustomButton addNewImageButton,nextButton,prevButton,clearButton;
    private JFileChooser jFileChooser;
    private JPanel CenterPanel;
    private CustomTextField searchField;
    private final JFrame mainFrame;
    private ArrayList<CustomImage> imageData;
    private ArrayList<CustomImage> searchData;
    private boolean dataThreadActive;
    private boolean drawThreadActive;
    private boolean searchFilterActive;
    CustomMenuItem deleteImage;
    CustomImage imageToBeDeleted;
    private int currentPage;
    private boolean assetsPreloaded;

    public Base(JFrame jFrame,boolean ind)
    {
        this.mainFrame= jFrame;
        assetsPreloaded=ind;
        init();
    }
    private void init()
    {
        FlowLayout fl=new FlowLayout(FlowLayout.CENTER);
        fl.setHgap(120);
        JPanel topPanel = new JPanel(fl);
        JPanel bottomPanel = new JPanel();
        addNewImageButton=new CustomButton("Add file");
        nextButton=new CustomButton("Next");
        prevButton=new CustomButton("Prev");
        clearButton=new CustomButton("Clear");
        searchField=new CustomTextField();
        nextButton.setVisible(false);
        prevButton.setVisible(false);
        clearButton.setVisible(false);
        jFileChooser=new JFileChooser();
        imageData=new ArrayList<>();
        searchData=new ArrayList<>();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Images", "jpg","jpeg","jfif","jpe","png");
        jFileChooser.setFileFilter(filter);
        File file=new File(System.getProperty("user.dir")+"\\assets");
        jFileChooser.setCurrentDirectory(file);
        topPanel.add(addNewImageButton);
        topPanel.add(searchField);
        topPanel.add(clearButton);
        bottomPanel.add(prevButton);
        bottomPanel.add(nextButton);
        mainFrame.add(topPanel,BorderLayout.NORTH);
        mainFrame.add(bottomPanel,BorderLayout.SOUTH);
        deleteImage = new CustomMenuItem("Delete");
        addListeners();
        showMessage("No images yet! You can upload them using 'Add file' button.");
        if(assetsPreloaded)
        {
            DataThread dataThread=new DataThread();
            dataThread.start();
            DrawThread drawThread=new DrawThread(imageData,currentPage);
            drawThread.start();
        }
    }
    private void addListeners()
    {
        addNewImageButton.addActionListener(e -> {
            jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            jFileChooser.setMultiSelectionEnabled(true);
            int result = jFileChooser.showOpenDialog(Base.this);
            if (result == JFileChooser.APPROVE_OPTION )
            {
                DataThread dataThread=new DataThread();
                dataThread.setPriority(9);
                dataThread.start();
                DrawThread drawThread=new DrawThread(imageData,currentPage);
                drawThread.setPriority(8);
                drawThread.start();
                searchField.setText("Search");
                clearButton.setVisible(false);
            }
        });
        nextButton.addActionListener(e->{
            if(drawThreadActive)
                return;
            DrawThread drawThread;
            if(searchFilterActive)
            {
                drawThread = new DrawThread(searchData, currentPage + 1);
            }
            else
            {
                drawThread = new DrawThread(imageData, currentPage + 1);
            }
            drawThread.start();
            currentPage++;
        });
        prevButton.addActionListener(e->{
            if(drawThreadActive)
                return;
            DrawThread drawThread;
            if(searchFilterActive)
            {
                drawThread = new DrawThread(searchData, currentPage - 1);
            }
            else
            {
                drawThread = new DrawThread(imageData, currentPage - 1);
            }
            drawThread.start();
            currentPage--;
        });
        searchField.addActionListener(e->{
            String text=searchField.getText();
            if(text.length()!=0)
            {
                search(text);
                clearButton.setVisible(true);
                if(searchData.size()!=0)
                {
                    DrawThread drawThread=new DrawThread(searchData,0);
                    drawThread.start();
                }
                else
                {
                    showMessage("Nothing found! Use another search words or upload files.");
                }

            }
        });
        clearButton.addActionListener(e->{
            currentPage=0;
            searchFilterActive=false;
            DrawThread drawThread=new DrawThread(imageData,currentPage);
            drawThread.start();
            clearButton.setVisible(false);
            searchField.setText("Search");
        });
        deleteImage.addActionListener(e-> {
            if(((imageData.size()%16==1)||((searchData.size()%16==1)&&(searchFilterActive)))&&(currentPage!=0))
            {
                currentPage--;
            }
            imageData.remove(imageToBeDeleted);
            DrawThread drawThread;
            if((!searchFilterActive)||(searchData.size()==1))
            {
                drawThread = new DrawThread(imageData, currentPage);
                searchField.setText("Search");
                clearButton.setVisible(false);
                searchFilterActive=false;
            }
            else
            {
                searchData.remove(imageToBeDeleted);
                drawThread = new DrawThread(searchData, currentPage);
            }
            drawThread.start();
        });
    }

    private class DrawThread extends Thread{
        private final int page;
        final ArrayList<CustomImage> Data;
        public DrawThread(ArrayList<CustomImage> customData,int customPage)
        {
            page=customPage;
            Data=customData;
        }
        @Override
        public void run() {
            boolean ind=true;
            drawThreadActive=true;
            while(ind)
            {
                int size;
                synchronized (Data)
                {
                    size=Data.size();
                }
                if(CenterPanel!=null)
                    mainFrame.remove(CenterPanel);
                CenterPanel=new JPanel();
                Layout fl=new Layout(mainFrame);
                fl.setAlignment(FlowLayout.LEFT);
                fl.setHgap(40);
                fl.setVgap(10);
                CenterPanel.setLayout(fl);
                if(((size-page*16)>16)||(!dataThreadActive))
                    ind=false;
                for (int i = 0; i < 16; i++) {

                    if(page*16+i<size)
                    {
                        CustomImage temp=Data.get(page*16+i);
                        BufferedImage bf=Data.get(page*16+i).getImage();
                        Image im= bf.getScaledInstance(205,110,Image.SCALE_DEFAULT);
                        JLabel jl=new JLabel(new ImageIcon(im));
                        String text=Data.get(page*16+i).getName();
                        jl.setText(text);
                        jl.setHorizontalTextPosition(JLabel.HORIZONTAL);
                        jl.setVerticalTextPosition(JLabel.BOTTOM);
                        jl.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                if(e.getButton()==MouseEvent.BUTTON3)
                                {
                                    CustomPopupMenu jPopupMenu=new CustomPopupMenu();
                                    jPopupMenu.add(deleteImage);
                                    imageToBeDeleted=temp;
                                    Point p=MouseInfo.getPointerInfo().getLocation();
                                    jPopupMenu.show(mainFrame,p.x,p.y);
                                }
                                else
                                {
                                    new FullscreenImage(bf,text);
                                }

                            }
                        });
                        CenterPanel.add(jl);
                    }

                }
                nextButton.setVisible(size - page * 16 >= 17);
                prevButton.setVisible(currentPage != 0);
                mainFrame.add(CenterPanel,BorderLayout.CENTER);
                mainFrame.revalidate();
                mainFrame.repaint();
                try {
                    Thread.sleep(40);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            drawThreadActive=false;
            if(imageData.size()==0)
                showMessage("No images yet! You can upload them using 'Add file' button.");

        }
    }

    private class DataThread extends Thread{
        @Override
        public void run() {
            dataThreadActive=true;
            File[] files;
            if(assetsPreloaded)
            {
                File file=new File(System.getProperty("user.dir")+"\\assets");
                files=file.listFiles();
                assetsPreloaded=false;
            }
            else
            {
                files=jFileChooser.getSelectedFiles();
            }
            try{
                for (File file : files) {
                    BufferedImage image = ImageIO.read(file);
                    String name = jFileChooser.getName(file);
                    CustomImage temp = new CustomImage(name, image);
                    if(!imageData.contains(temp))
                        imageData.add(temp);
                }

            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
            dataThreadActive=false;
        }
    }

    private void search(String text)
    {
        searchData=new ArrayList<>();
        for (CustomImage imageDatum : imageData) {
            if (imageDatum.getName().contains(text)) {
                searchData.add(imageDatum);
            }

        }
        searchFilterActive=true;
        currentPage=0;

    }

    private void showMessage(String message)
    {
        FlowLayout fl=new FlowLayout(FlowLayout.CENTER);
        fl.setVgap(270);
        if(CenterPanel!=null)
            mainFrame.remove(CenterPanel);
        CenterPanel=new JPanel(fl);
        JLabel label=new JLabel(message);
        label.setFont(new Font("Arial", Font.PLAIN, 30));
        CenterPanel.add(label);
        mainFrame.add(CenterPanel,BorderLayout.CENTER);
        mainFrame.revalidate();
        mainFrame.repaint();
    }

}
