package com.nwl.view;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.event.*;
import java.awt.*;
import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Date;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

public class ImageView extends JFrame{
    
    BufferedImage bufferedImage=null;

    public void createFrame() {
        
        ImageIcon icon=new ImageIcon(bufferedImage);
        int w=icon.getIconWidth();
        int h=icon.getIconHeight();
        if(w>300||h>300)
        {
            double b=((double)w>(double)h?w:h);
            b=200/b;
            icon=change(icon,b);
        }
        w=icon.getIconWidth();
        h=icon.getIconHeight();
        JLabel jLabel=new JLabel(icon);
        JButton saveImageButton=new JButton("保存图片");
        saveImageButton.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e)
            {
                JFileChooser jFileChooser=new JFileChooser();
                jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                jFileChooser.setDialogTitle("保存图片");
                jFileChooser.setMultiSelectionEnabled(false);
                jFileChooser.setCurrentDirectory(FileSystemView.getFileSystemView().getHomeDirectory());
                int retunVal=jFileChooser.showSaveDialog(null);
                if(retunVal==JFileChooser.APPROVE_OPTION)
                {
                    String path=jFileChooser.getSelectedFile().getPath();
                    Graphics graphics=bufferedImage.getGraphics();
                    Date date=new Date();
                    try {
                        graphics.drawImage(bufferedImage, 0, 0, null);
                        ImageIO.write(bufferedImage,"jpg", new File(path+"\\"+date.getTime()+".jpg"));
                    } catch (Exception ee) {
                        ee.printStackTrace();
                    }

                }
            }
        });

        this.add(jLabel,BorderLayout.CENTER);
        this.add(saveImageButton,BorderLayout.SOUTH);

        int width=0;
        int height=0;
        width=(java.awt.Toolkit.getDefaultToolkit().getScreenSize().width);
        height=(java.awt.Toolkit.getDefaultToolkit().getScreenSize().height);

        this.setTitle("收到图片");
        this.setBounds((width-w)/2+300, (height-h)/2, w, h+100);
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public ImageIcon change(ImageIcon image,double i){//  i 为放缩的倍数
    int width=(int) (image.getIconWidth()*i);
    int height=(int) (image.getIconHeight()*i);
    Image img=image.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT);//第三个值可以去查api是图片转化的方式
    ImageIcon image2=new ImageIcon(img);
    return image2;
    }

    public ImageView(BufferedImage bimage) {
        this.bufferedImage=bimage;
    }
}
