package com.nwl.view;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import com.nwl.sever.LoginSevice;
import com.nwl.entiy.User;
import java.awt.event.MouseEvent;
import java.io.File;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;

public class LoginView extends JFrame {

    JPanel  topjPanel=null;
    JLabel  imgLabel=null;

    JPanel  centrJPanel=null;
    JLabel  usernameJLabel=null;
    JTextField useridTextField=null;
    JLabel pwdLabel=null;
    JPasswordField passwordField=null;

    JPanel bottomJPanel =null;
    JButton logiButton=null;
    JButton registerButton=null;

  /*   public  LoginView() throws HeadlessException{
        createFrame();
    } */
    public static void main(String[] args)  {
        LoginView loginView=new LoginView();
        loginView.createFrame();
        String desString="C:\\OO";
        File userFile=new File(desString);
        userFile.mkdirs();
    }
    public void createFrame() {
       /* 顶部图片 */
        ImageIcon imageIcon=new ImageIcon(LoginView.class.getClassLoader().getResource("image/img.jpg"));
        imgLabel=new JLabel(imageIcon,JLabel.CENTER);
        topjPanel =new JPanel(new FlowLayout(FlowLayout.CENTER));
        topjPanel.add(imgLabel);
        this.add(topjPanel,BorderLayout.NORTH);
        /* 中部填写id和密码的区域 */
        usernameJLabel=new JLabel("账号：");
        pwdLabel=new JLabel("密码：");
        useridTextField= new JTextField(20);
        useridTextField.setFont(new Font(Font.DIALOG,Font.PLAIN,16));
        passwordField= new JPasswordField(20);
        passwordField.setFont(new Font(Font.DIALOG,Font.PLAIN,16));
        /* centrJPanel=new JPanel(new GridLayout(2,2,0,20)); */
        centrJPanel=new JPanel(new FlowLayout(FlowLayout.CENTER));
        centrJPanel.add(usernameJLabel);
        centrJPanel.add(useridTextField);
        centrJPanel.add(pwdLabel);
        centrJPanel.add(passwordField);
        this.add(centrJPanel,BorderLayout.CENTER);
        /* 下面按钮区域 */
        logiButton=new JButton("登录");
        logiButton.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                User user=new User();
                user.setId(useridTextField.getText());
                user.setPassword(new String(passwordField.getPassword()));
                LoginSevice loginSevice=new LoginSevice();
                user=loginSevice.login(user);
                if(user!=null){
                    LoginView.this.dispose();
                    FriendsLIst friendsLIst=new FriendsLIst(user);
                    friendsLIst.creatFriendListView();
                }else{
                    JOptionPane.showMessageDialog(LoginView.this, "登陆失败！"," ",JOptionPane.WARNING_MESSAGE);

                }
                
            }
        });
        registerButton=new JButton("注册");
        registerButton.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                LoginView.this.dispose();
                RegisterView registerView =new RegisterView();
                registerView.creatRegistView();
            }
        });
        JButton setipButton=new JButton("修改ip");
        setipButton.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                SetipView setipView=new SetipView();
                setipView.createFrame();
            }
        });
        bottomJPanel=new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomJPanel.add(logiButton);
        bottomJPanel.add(registerButton);
        bottomJPanel.add(setipButton);
        this.add(bottomJPanel,BorderLayout.SOUTH);

        int width=0;
        int height=0;
        width=(java.awt.Toolkit.getDefaultToolkit().getScreenSize().width);
        height=(java.awt.Toolkit.getDefaultToolkit().getScreenSize().height);

        this.setTitle("傻逼来登录");
        this.setBounds((width-400)/2, (height-350)/2, 350, 300);
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

}
