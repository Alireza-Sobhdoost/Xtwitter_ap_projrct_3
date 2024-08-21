package org.example;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Addpost extends JFrame implements ActionListener {

    private final FrameCallBack callBack;
    JButton signupButton;
    private JTextField contentField;
    private JTextField tagField;

    private long user ;

    public Addpost(long logedinUserId ,FrameCallBack callBack ) {
        user = logedinUserId ;
        this.callBack = callBack ;
        ImageIcon image = new ImageIcon("logo.png");
        this.setTitle("Add Post");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(420, 210);
        this.setIconImage(image.getImage());
        this.getContentPane().setBackground(new Color(16, 8, 32));

        JPanel panel = new JPanel();

        JLabel contentLabel = new JLabel("Post Content");
        contentField = new JTextField(20);
        contentField.setBackground(new Color(64, 64, 128));
        contentField.setBorder(BorderFactory.createLineBorder(new Color(64, 64, 128)));
        contentField.setForeground(Color.white);
        contentLabel.setForeground(new Color(255, 255, 255));

        JLabel tagLabel = new JLabel("tag");
        tagField = new JTextField(20);
        tagField.setBackground(new Color(64, 64, 128));
        tagField.setBorder(BorderFactory.createLineBorder(new Color(64, 64, 128)));
        tagField.setForeground(Color.yellow);
        tagLabel.setForeground(new Color(255, 255, 255));

        signupButton = new JButton("Add post");
        signupButton.setBackground(new Color(195, 166, 218));

        panel.setBackground(new Color(16, 8, 32));
        panel.add(contentLabel);
        panel.add(contentField);
        panel.add(tagLabel);
        panel.add(tagField);
        panel.add(signupButton);

        this.add(panel);

        signupButton.addActionListener(this);
        contentField.addActionListener(this);
        tagField.addActionListener(this);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == signupButton) {
            String content = contentField.getText();
            String tag = tagField.getText();
            this.dispose();
            try (Socket socket = new Socket("localhost", 5000);
                 DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                 DataInputStream in = new DataInputStream(socket.getInputStream())) {
                    out.writeInt(3);
                    out.writeLong(user);
                    out.writeUTF(content);
                    out.writeUTF(tag);
                    boolean isPostAddedIn = in.readBoolean();
                    callBack.onADDResult(isPostAddedIn);
//                System.out.println(isLoggedIn);


                    out.close();
                    in.close();
                    socket.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }


}