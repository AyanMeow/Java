package com.nwl.view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.nwl.util.PropertiesUtil;

import java.awt.Font;
import java.awt.event.*;

public class SetipView extends JFrame{
    public void createFrame() {

        JTextArea ipTextField=new JTextArea();
        ipTextField.setFont(new Font(Font.DIALOG,Font.PLAIN,16));
        ipTextField.append(PropertiesUtil.gPropertiesUtil().getValue("hostip"));
        JButton setButton=new JButton("确认");
        setButton.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                PropertiesUtil.gPropertiesUtil().setIP(ipTextField.getText());
                SetipView.this.dispose();
            }
        });
        JPanel jPanel=new JPanel();
        jPanel.add(ipTextField);
        jPanel.add(setButton);
        this.add(jPanel);

        int width=0;
        int height=0;
        width=(java.awt.Toolkit.getDefaultToolkit().getScreenSize().width);
        height=(java.awt.Toolkit.getDefaultToolkit().getScreenSize().height);

        this.setTitle("修改ip");
        this.setBounds((width-150)/2, (height-50)/2, 250, 80);
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    
}
