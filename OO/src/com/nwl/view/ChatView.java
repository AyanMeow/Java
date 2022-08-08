package com.nwl.view;

import java.util.Dictionary;
import java.awt.event.*;
import java.net.Socket;
import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import com.nwl.util.*;
import com.nwl.sever.ChatSevice;
import java.util.Date;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;

public class ChatView extends JFrame{
    /**
     *
     */
    private static final long serialVersionUID = -7940735652065820988L;
    
    String userme;
    String userlea;
    Dictionary<String, Boolean> frameDictionary=null;
    Date nowdate=new Date();
    Socket socket;

    JScrollPane jScrollPane=null;
    JTextArea recordtTextArea=null;

    JPanel buttomJPanel=null;
    JTextField sendingTextField=null;
    JButton sendButton=null;
    JButton imageButton=null;

    public  ChatView(String me,String lea) {
        this.userme=me;
        this.userlea=lea;
    }

    public void creatChatFrame(Dictionary<String, Boolean> frameDictionary)
    {
        recordtTextArea=new JTextArea();
        recordtTextArea.setEditable(false);
        recordtTextArea.setFont(new Font(Font.DIALOG,Font.PLAIN,18));
        Dimension size=recordtTextArea.getSize();
        jScrollPane=new JScrollPane(recordtTextArea);
        jScrollPane.setBounds(10, 10, (int)size.getWidth(), (int)size.getHeight());
        this.add(jScrollPane,BorderLayout.CENTER);
    
        buttomJPanel=new JPanel();
        sendingTextField=new JTextField(25);
        sendingTextField.setFont(new Font(Font.DIALOG,Font.PLAIN,18));
        sendButton=new JButton("发送");
        sendButton.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e)
            {
                String saying=sendingTextField.getText();
                if(!saying.equals(""))
                {
                    String date=nowdate.toString();
                    recordtTextArea.append("你 | "+date+"\n");
                    recordtTextArea.append("<<<<"+saying+"\n");
                    sendingTextField.setText("");
                    ChatSevice chatSevice=new ChatSevice();
                    chatSevice.chatto(userme+","+userlea+","+saying+","+date,socket);
                }
            }
        });
        //未实现：enter键发送
        sendingTextField.addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e){
                if(e.getKeyCode()==KeyEvent.VK_ENTER){
                    String saying=sendingTextField.getText();
                    if(!saying.equals("")){
                        String date=nowdate.toString();
                        recordtTextArea.append("你 | "+date+"\n");
                        recordtTextArea.append("<<<<"+saying+"\n");
                        sendingTextField.setText("");
                        ChatSevice chatSevice=new ChatSevice();
                        chatSevice.chatto(userme+","+userlea+","+saying+","+date,socket);
                    }
                }
            }
        });
        imageButton=new JButton("发送图片");
        /* 发送图片的功能 */
        imageButton.addMouseListener(new MouseAdapter(){
            private String permitFile[]={"jpg","png","jpeg"};
            @Override
            public void mouseClicked(MouseEvent e)
            {
                JFileChooser jFileChooser=new JFileChooser();
                jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                jFileChooser.setDialogTitle("发送图片");
                jFileChooser.setMultiSelectionEnabled(false);
                jFileChooser.setCurrentDirectory(FileSystemView.getFileSystemView().getHomeDirectory());
                jFileChooser.setFileFilter(new FileNameExtensionFilter("JPG&PNG&JPEG ONLY", permitFile));
                int retunVal=jFileChooser.showOpenDialog(null);
                if(retunVal==JFileChooser.APPROVE_OPTION)
                {
                    try{
                    BufferedImage bufferedImage=ImageIO.read(jFileChooser.getSelectedFile());
                    /* ImageView imageView=new ImageView(bufferedImage);
                    imageView.createFrame(); */
                    String date=nowdate.toString();
                    recordtTextArea.append("你 | "+date+"\n");
                    recordtTextArea.append("<<<<发送了一个图片\n");
                    ChatSevice chatSevice=new ChatSevice();
                    chatSevice.setImage(bufferedImage);
                    chatSevice.chatto(userme+","+userlea+","+"我发送了一个图片"+","+date,socket);

                    }catch(Exception pp)
                    {
                        pp.printStackTrace();
                    }
                }
            }
        });
        buttomJPanel.add(sendingTextField);
        buttomJPanel.add(sendButton);
        buttomJPanel.add(imageButton);
        this.add(buttomJPanel,BorderLayout.SOUTH);
       
        /* 建立与服务器的持续连接 */
        try {
            socket=new Socket(PropertiesUtil.gPropertiesUtil().getValue("hostip"), 9999);
            Message message=new Message();
            message.setMessageType(MessageType.START_TALK);
            message.setChatcontention(userme+"TO"+userlea);
            SocketUtil.geSocketUtil().writeObject(socket, message);

            ChatThread chatThread=new ChatThread(socket, recordtTextArea);
            chatThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        int width=0;
        int height=0;
        width=(java.awt.Toolkit.getDefaultToolkit().getScreenSize().width);
        height=(java.awt.Toolkit.getDefaultToolkit().getScreenSize().height);

        this.setTitle("与"+userlea+"的对话");
        this.setBounds((width-600)/2, (height-400)/2, 600, 400);
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e)
            {
                frameDictionary.put(userlea, true);
                try {
                    Message closinMessage=new Message();
                    closinMessage.setMessageType(MessageType.TALK_CLOSE);
                    closinMessage.setChatcontention(userme+"TO"+userlea);
                    SocketUtil.geSocketUtil().writeObject(socket, closinMessage);

                    File u=new File("C:\\OO\\"+userme+"\\"+userlea);
                    u.mkdirs();
                    String recordpath="C:\\OO\\"+userme+"\\"+userlea+"\\record.txt";
                    FileWriter fileWriter=new FileWriter(recordpath,true);
                    fileWriter.write(recordtTextArea.getText()+"\r\n");
                    fileWriter.close();

                } catch (Exception ep) {
                    ep.printStackTrace();
                }
            }
        });
    }

}
