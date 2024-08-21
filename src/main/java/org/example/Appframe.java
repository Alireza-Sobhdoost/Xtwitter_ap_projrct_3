package org.example;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Appframe extends JFrame implements ActionListener , LoginCallback {

    JButton button1;
    JButton button2;

//    public static Boolean isLogedIn = false;
//    protected static DataInputStream in ;
//    protected static DataOutputStream out ;
//    protected static Socket socket ;


    public Appframe() {


        button1 = new JButton();  // Update this line
        button1.setBounds(450, 175, 105, 50);
        button1.setBackground(new Color(50, 5, 170));
        button1.addActionListener(this);
        button1.setText("Sing up");
        button1.setHorizontalTextPosition(JButton.RIGHT);
        button1.setHorizontalAlignment(JButton.LEFT);
        button1.setVerticalTextPosition(JButton.CENTER);
        button1.setVerticalAlignment(JButton.CENTER);
        button1.setFocusable(false);
        ImageIcon image = new ImageIcon("logo.png");
        Image img = image.getImage();
        Image newImg = img.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon scaledImage = new ImageIcon(newImg);
        button1.setIcon(scaledImage);
//        button1.setIconTextGap(-15);
        button1.setForeground(new Color(16, 128, 200));
        button1.setBorder(BorderFactory.createEtchedBorder());


        button2 = new JButton();  // Update this line
        button2.setBounds(450, 275, 105, 50);
        button2.setBackground(new Color(50, 5, 170));
        button2.addActionListener(this);
        button2.setText("Log in");
        button2.setIcon(scaledImage);
        button2.setHorizontalTextPosition(JButton.RIGHT);
        button2.setHorizontalAlignment(JButton.LEFT);
        button2.setVerticalTextPosition(JButton.CENTER);
        button2.setVerticalAlignment(JButton.CENTER);
        button2.setFocusable(false);
//        ImageIcon loginImg = new ImageIcon("login.png");
//        Image loginimg  = loginImg.getImage();
//        Image newloginImg  = loginimg.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
//        ImageIcon scaledlogImage = new ImageIcon(newloginImg);

//        button1.setIconTextGap(-15);
        button2.setForeground(new Color(16, 128, 200));
        button2.setBorder(BorderFactory.createEtchedBorder());

        JPanel panel1 = new JPanel();
        panel1.setBackground(new Color(16, 8, 32));
        panel1.setBounds(50 ,100 , 350, 350);
        JLabel label = new JLabel() ;
//        label.setSize(4, 420);
//        label.setText("Welcome to X");
        img = image.getImage();
        Image XnewImg = img.getScaledInstance(300, 300, 4);
        ImageIcon XscaledImage = new ImageIcon(XnewImg);

        label.setIcon(XscaledImage);
//        twitter.setLayout(new BorderLayout());
        panel1.add(label);
        
        this.setLayout(null);
        this.setTitle("X twitter");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(700, 560);
        this.setVisible(true);
        this.setIconImage(image.getImage());
        this.getContentPane().setBackground(new Color(16, 8, 32));
        this.add(button1);
        this.add(button2);
        this.add(panel1);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button1) {
            System.out.println("Start sign up operation");
            Signup signup = new Signup();
//            Server.Task= 1 ;
//            button1.setEnabled(false);
        }
        else if (e.getSource() == button2) {
            System.out.println("Start log in operation");
//            Server.Task= 2 ;
            Login login = new Login(this) ;

        }
    }




    @Override
    public void onLoginResult(boolean isLoggedIn, long logedinUserId) {
        System.out.println(isLoggedIn);
        if (isLoggedIn) {
//            Rootframe  r = new Rootframe(logedinUserId);
//            r.setVisible(false);

//            Mainframe mainframe = new Mainframe() ;
            this.dispose();
            Rootframe rootframe = new Rootframe(logedinUserId);
            rootframe.refactor(PostDao.getAllPosts());
//            Rootframe root = new Rootframe(logedinUserId) ;
        }
    }
}