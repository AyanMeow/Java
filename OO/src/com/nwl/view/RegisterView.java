package com.nwl.view;

import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import com.nwl.sever.RegistService;
import com.nwl.entiy.HeadPhotos;
import com.nwl.entiy.User;
import java.awt.event.MouseEvent;
import java.util.List;

public class RegisterView extends JFrame{
    JPanel northJPanel=null;
    JLabel nameJLabel=null;
    JTextField nameTextField=null;

    JPanel centerPanel=null;
    JLabel pwdLabel=null;
    JPasswordField pwdJPasswordField=null;
    JLabel confirmLabel=null;
    JPasswordField confirmPasswordField=null;
    JLabel teleLabel=null;
    JTextField teleTextField=null;
    JLabel photoJLabel=null;
    JComboBox jComboBox=new JComboBox();
    HeadPhotos headPhotos=new HeadPhotos();
    List<String> heads;

    JPanel sourthJPanel=null;
    JButton registButton=null;
    JButton backButton=null;

    public void creatRegistView() {
        nameJLabel=new JLabel("用  户 名：");
        nameTextField =new JTextField(20);
        nameTextField.setFont(new Font(Font.DIALOG,Font.PLAIN,16));
        teleLabel=new JLabel("联系电话：");
        teleTextField =new JTextField(20);
        teleTextField.setFont(new Font(Font.DIALOG,Font.PLAIN,16));
        northJPanel=new JPanel();
        northJPanel.add(nameJLabel);
        northJPanel.add(nameTextField);

        pwdLabel=new JLabel("密      码：");
        pwdJPasswordField=new JPasswordField(20);
        pwdJPasswordField.setFont(new Font(Font.DIALOG,Font.PLAIN,16));
        confirmLabel=new JLabel("确认密码：");
        confirmPasswordField= new JPasswordField(20);
        confirmPasswordField.setFont(new Font(Font.DIALOG,Font.PLAIN,16));
        photoJLabel=new JLabel("请选择头像：");
        heads=headPhotos.getPhotos();
        for(int j=0;j<heads.size();j++)
        {
            ImageIcon icon=new ImageIcon(RegisterView.class.getClassLoader().getResource("image/"+heads.get(j)));
            icon.setImage(icon.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
            jComboBox.addItem(icon);
        }
        centerPanel= new JPanel();
        centerPanel.add(pwdLabel);
        centerPanel.add(pwdJPasswordField);
        centerPanel.add(confirmLabel);
        centerPanel.add(confirmPasswordField);
        centerPanel.add(teleLabel);
        centerPanel.add(teleTextField);
        centerPanel.add(photoJLabel);
        centerPanel.add(jComboBox);

        this.add(northJPanel,BorderLayout.NORTH);
        this.add(centerPanel,BorderLayout.CENTER);

        registButton=new JButton("注册");
        backButton=new JButton("返回");
        sourthJPanel=new JPanel();
        sourthJPanel.add(registButton);
        sourthJPanel.add(backButton);
        this.add(sourthJPanel,BorderLayout.SOUTH);
        
        registButton.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                String confirmString;
                User user=new User();
                user.setUsername(nameTextField.getText());
                user.setPassword(new String(pwdJPasswordField.getPassword()));
                user.setTele(teleTextField.getText());
                user.setPhoto(jComboBox.getSelectedIndex());
                confirmString =new String(confirmPasswordField.getPassword());
                if(confirmString.equals(user.getPassword())){
                    RegistService registService=new RegistService();
                    String id=registService.regist(user);
                    if(id!=null)
                    {
                        JOptionPane.showMessageDialog(RegisterView.this, "注册成功！账号为："+id);
                        RegisterView.this.dispose();
                        LoginView loginView=new LoginView();
                        loginView.createFrame();
                    }else
                    {
                        JOptionPane.showMessageDialog(RegisterView.this, "注册失败！昵称重复！","",JOptionPane.WARNING_MESSAGE);
                    }
                }else{
                    JOptionPane.showMessageDialog(RegisterView.this, "两次密码不一致！","",JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        backButton.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                RegisterView.this.dispose();
                LoginView loginView=new LoginView();
                loginView.createFrame();
            }
        });

        int width=0;
        int height=0;
        width=(java.awt.Toolkit.getDefaultToolkit().getScreenSize().width);
        height=(java.awt.Toolkit.getDefaultToolkit().getScreenSize().height);

        this.setTitle("不会有人注册都不会吧？不会吧不会吧");
        this.setBounds((width-350)/2, (height-300)/2, 350, 300);
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
}
