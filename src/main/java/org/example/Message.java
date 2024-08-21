package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Message extends JFrame implements ActionListener {

    JButton button;

    public Message(int type , String staus) {
        ImageIcon image = new ImageIcon("logo.png");
        this.setSize(210, 98);
        this.setTitle("X twitter");
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setIconImage(image.getImage());

        // Set the background color of the content pane
        getContentPane().setBackground(new Color(16, 8, 32));

        JLabel label = new JLabel();
        label.setBackground(new Color(16, 8, 32));
        if (type == 1) {
            label.setText("Your ID is "+staus);
            label.setForeground(new Color(207, 120, 207));
        }
        else if (type == 2) {
            label.setText("Wellcome dear "+staus);
            label.setForeground(new Color(207, 120, 207));
        }
        else if (type == 3) {
            label.setText("Posting Successfull");
            label.setForeground(new Color(207, 120, 207));
        }
        else if (type == 4) {
            label.setText("Profile has been edited successfully");
            label.setForeground(new Color(207, 120, 207));
        }


        button = new JButton();
        button.setText("OK");
        button.setBackground(new Color(195, 166, 218));
        label.setHorizontalTextPosition(JLabel.CENTER);
        label.setVerticalTextPosition(JLabel.CENTER);
        button.setHorizontalTextPosition(JButton.CENTER);
        button.setVerticalTextPosition(JButton.CENTER);
        button.addActionListener(this);

        this.setLayout(new FlowLayout());
        this.add(label);
        this.add(button);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == button) {
//            Appframe.isLogedIn = true ;
            this.dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Message(1,"1");
            }
        });
    }
}