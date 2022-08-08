package com.xyygame;

import java.awt.*;
import java.util.LinkedList;
import java.util.Locale;
import java.util.TimerTask;
import java.util.concurrent.Flow;
import java.util.Timer;
import javax.swing.*;
import javax.swing.JFrame; 
import javax.swing.JLabel;
import javax.swing.border.Border;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MainFrame extends JFrame {

    private Snake snake;
    private Timer timer;
    private JPanel panel;
    private JPanel paneltext;
    private  JLabel jLabel;
    private Gnode food;
    private Gnode food2;
    private Blocks blocks;
    private int f1=0;
    private int f2=0;
    private int b=0;
    private int speed=100;
    private boolean paused=false;

    public MainFrame() throws HeadlessException {
        initFrame();
        initSnake();
        initGamePanel();
        initFood();
        initFood2();
        initBlocks();
        initTimer();
        setKeyListener();
    }
    
    private void initBlocks() {
       blocks=new Blocks();
       b=0;
    }

    private void initFood2(){
        food2=new Gnode();
        food2.genrandom();
        f2=0;
    }
    private void initFood() {
        food=new Gnode();
        food.genrandom();
        f1=0;
    }
    public void reinit() {
        timer.cancel();
        initSnake();
        initFood(); 
        initFood2();
        initBlocks();
        initTimer();
    }
    private void setKeyListener() {
        this.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                case KeyEvent.VK_W:
                case KeyEvent.VK_UP:
                    if (snake.getDrt() != Dreiction.DOWN)
                        snake.setDrt(Dreiction.UP);
                    break;
                case KeyEvent.VK_S:
                case KeyEvent.VK_DOWN:
                    if (snake.getDrt() != Dreiction.UP)
                        snake.setDrt(Dreiction.DOWN);
                    break;
                case KeyEvent.VK_D:
                case KeyEvent.VK_RIGHT:
                    if (snake.getDrt() != Dreiction.LIFHT)
                        snake.setDrt(Dreiction.RIGHT);
                    break;
                case KeyEvent.VK_A:
                case KeyEvent.VK_LEFT:
                    if (snake.getDrt() != Dreiction.RIGHT)
                        snake.setDrt(Dreiction.LIFHT);
                    break;
                case KeyEvent.VK_F1:
                    speed=200;
                    reinit();
                    break;
                    case KeyEvent.VK_F2:
                    speed=100;
                    reinit();
                    break;
                    case KeyEvent.VK_F3:
                    speed=50;
                    reinit();
                    break;
                    case KeyEvent.VK_F4:
                    speed=10;
                    reinit();
                    break;
                case KeyEvent.VK_F5:
                    reinit();
                    paused=false;
                    break;
                case KeyEvent.VK_F6:
                    paused=!paused;
                    break;
                    default:
                }
            }
        });
    }

    private void initTimer() {
        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            public void run() {
                if(!paused){
                if(snake.getLiving()){f1++;f2++;b++;}
                Gnode head = snake.getBody().getFirst();
                if (head.getX() == food.getX() && head.getY() == food.getY()) {
                    snake.eatFood();
                    snake.setScore(snake.getScore()+1);
                    initFood();
                }
                if (head.getX() == food2.getX() && head.getY() == food2.getY()) {
                    snake.eatFood();
                    snake.setScore(snake.getScore()+3);
                    initFood2();
                }
                if(f1>(6500/speed)&&snake.getLiving()){initFood();}
                if(f2>(8000/speed)&&snake.getLiving()){initFood2();}
                if(b%(5000/speed)==0&&snake.getLiving()&&blocks.getBlocks().size()<16){blocks.addnewBlcok();}
                Gnode h=snake.getBody().getFirst();
                for(int i=1;i<blocks.getBlocks().size();i++)
                {
                    Gnode bb=blocks.getBlocks().get(i);
                    if(h.getX()>=bb.getX()&&h.getX()<=bb.getX()+2&&h.getY()>=bb.getY()&&h.getY()<=bb.getY()+2)
                        snake.setLiving(false);
                }
                snake.move();
                panel.repaint();
                jLabel.setText("Score:"+snake.getScore());
            }
        }
        };
        timer.scheduleAtFixedRate(timerTask, 0, speed);
        
    }

    private void initSnake() {
        snake = new Snake();

    }

    private void initGamePanel() {
        /*IMPORTANT before u draw ,clear the panel first;*/

        jLabel=new JLabel();
        jLabel.setFont(new java.awt.Font("Dialog",1,20));
        paneltext=new JPanel();
        paneltext.add(jLabel);
        panel = new JPanel() {
            public void paint(Graphics g) {
                g.clearRect(0, 0, 600, 600);
                for (int i = 0; i <= 40; i++) {
                    g.drawLine(0, i * 15, 600, i * 15);
                    g.drawLine(i * 15, 0, i * 15, 600);
                }
                g.setColor(Color.BLACK);
                LinkedList<Gnode> body = snake.getBody();
                for (Gnode node : body) {
                    g.fillRect(15 * node.getX(), 15 * node.getY(), 15, 15);

                }

                g.setColor(Color.GREEN);
                g.fillRect(food.getX() * 15, food.getY() * 15, 15, 15);
                if(f2>(3000/speed)){
                g.setColor(Color.RED);
                g.fillRect(food2.getX() * 15, food2.getY() * 15, 15, 15);
                }
                
                for(int i=1;i<blocks.getBlocks().size();i++)
                {
                    g.setColor(Color.BLUE);
                    g.fillRect(blocks.getBlocks().get(i).getX()*15, blocks.getBlocks().get(i).getY()*15, 15*3, 15*3);
                }
            }
        };
        add(panel);
        add(paneltext,BorderLayout.SOUTH);
        
    }

    private void initFrame() {
        int width,lenth;
        width=(int)java.awt.Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        lenth=(int)java.awt.Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        setSize(620, 670);
        setLocation((width-620)/2, (lenth-670)/2);
        // set colse presser's use(but cant use JFrame.EXIT)?->the EXIT has already
        // confirmed the interface for wdsContant
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
    }

    public static void main(String[] args) {
        new MainFrame().setVisible(true);
    }
}