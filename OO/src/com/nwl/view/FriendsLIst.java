package com.nwl.view;

import java.awt.Image;
import java.util.List;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import com.nwl.entiy.HeadPhotos;
import com.nwl.entiy.User;
import com.nwl.sever.LoginSevice;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.*;
import com.nwl.util.*;

public class FriendsLIst extends JFrame {

    JPanel jPanel = null;
    JScrollPane scrollPane = null;
    User user;
    HeadPhotos headPhotos = new HeadPhotos();
    List<String> heads = headPhotos.getPhotos();
    Dictionary<String, Boolean> frameDictionary = new Hashtable<String, Boolean>();
    JLabel[] jLabels;
    public String username;

    public FriendsLIst(User user) {
        this.user = user;
        this.username = user.getUsername();
    }

    public void creatFriendListView() {
        jPanel=new JPanel(new GridLayout(heads.size()>10?heads.size():10,1,5,5));
        /* 获取好友列表 */
        List<User> friends=new ArrayList<User>();
        LoginSevice loginSevice= new LoginSevice();
        friends=loginSevice.friends(user,this);
        if(friends==null)
        {
            JOptionPane.showMessageDialog(FriendsLIst.this, "获取好友失败！"," ",JOptionPane.WARNING_MESSAGE);
            LoginView loginView=new LoginView();
            loginView.createFrame();
        }
        /* 把自己放在第一个显示 */
        ImageIcon icon=new ImageIcon(FriendsLIst.class.getClassLoader().getResource("image/"+heads.get(user.getPhoto())));
        icon.setImage(icon.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        JLabel label=new JLabel(user.getUsername()+"("+user.getId()+")",icon,JLabel.LEFT);
        label.setSize(50,50);
        label.setBorder(BorderFactory.createLineBorder(Color.RED));
        label.setOpaque(true);
        label.setBackground(Color.RED);
        jPanel.add(label);
        /* 循环添加好友显示 */
        jLabels=new JLabel[friends.size()];
        for(int i=0;i<friends.size();i++)
        {
            User u=new User();
            u=friends.get(i);
            frameDictionary.put(u.getUsername()+"("+u.getId()+")", true);
            ImageIcon imageIcon=new ImageIcon(FriendsLIst.class.getClassLoader().getResource("image/"+heads.get(u.getPhoto())));
            int w=50;
            imageIcon.setImage(imageIcon.getImage().getScaledInstance(w, w, Image.SCALE_DEFAULT));
            JLabel headlJLabel=new JLabel(u.getUsername()+"("+u.getId()+")",imageIcon,JLabel.LEFT);
            headlJLabel.setSize(w, w);
            headlJLabel.setBorder(BorderFactory.createLineBorder(Color.BLUE));
            headlJLabel.setOpaque(true);
            if(u.isIsonline()){
                headlJLabel.setBackground(Color.WHITE);
            }else{
                headlJLabel.setForeground(Color.LIGHT_GRAY);
                //headlJLabel.setBackground(Color.GRAY);
                headlJLabel.setEnabled(false);
                }
            /* 建立鼠标事件监听：移动、双击打开聊天界面 */
            headlJLabel.addMouseListener(new MouseAdapter(){
               Color pri;

                @Override
                public void mouseClicked(MouseEvent e)
                {
                    ChatView chatView;
                    if(e.getClickCount()==2)
                    {
                        if(frameDictionary.get(headlJLabel.getText())){
                            frameDictionary.put(headlJLabel.getText(),false);
                            chatView=new ChatView(label.getText(), headlJLabel.getText());
                            chatView.creatChatFrame(frameDictionary);
                         }
                         headlJLabel.setBackground(Color.WHITE);
                         pri=Color.WHITE;
                    }
                }
                @Override
                public void mouseEntered(MouseEvent e)
                {
                    pri=headlJLabel.getBackground();
                    headlJLabel.setBackground(Color.GRAY);
                }
                @Override
                public void mouseExited(MouseEvent e)
                {
                    headlJLabel.setBackground(pri);
                }
            });
            jLabels[i]=headlJLabel;
            jPanel.add(headlJLabel);
        }
        scrollPane=new JScrollPane(jPanel);
        this.add(scrollPane,BorderLayout.CENTER);

        int width=0;
        int height=0;
        width=(java.awt.Toolkit.getDefaultToolkit().getScreenSize().width);
        height=(java.awt.Toolkit.getDefaultToolkit().getScreenSize().height);

        this.setTitle(username+"的脑瘫朋友们");
        this.setBounds(4*(width-400)/4, (height-350)/5, 300, 600);
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e)
            {
                   loginSevice.logout(username);
            }
        });
        
    }

    public JLabel[] getjLabels() {
        return jLabels;
    }

    public void setjLabels(JLabel[] jLabels) {
        this.jLabels = jLabels;
    }

}
