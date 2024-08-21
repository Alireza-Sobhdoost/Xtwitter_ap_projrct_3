package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Error extends JFrame implements ActionListener {

    JButton button;

    public Error(int type) {
        ImageIcon image = new ImageIcon("logo.png");
        this.setSize(210, 91);
        this.setTitle("X twitter");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setIconImage(image.getImage());

        // Set the background color of the content pane
        getContentPane().setBackground(new Color(16, 8, 32));

        JLabel label = new JLabel();
        label.setBackground(new Color(16, 8, 32));
        if (type == 1) {
            label.setText("Username couldn't be null");
            label.setForeground(new Color(207, 120, 207));
        }
        else if (type == 2) {
            label.setText("The Username is already exists !");
            label.setForeground(new Color(207, 120, 207));
        }
        else if (type == 3) {
            label.setText("Please fill the blanks");
            label.setForeground(new Color(207, 120, 207));
        }
        else if (type == 4) {
            label.setText("The password in incorrect");
            label.setForeground(new Color(207, 120, 207));
        }
        else if (type == 5) {
            label.setText("Use '@' key to search for a user and '#' for a tag");
            label.setForeground(new Color(207, 120, 207));
        }
        else if (type == 6) {
            label.setText("Invalid phone our email");
            label.setForeground(new Color(207, 120, 207));
        }
        else if (type == 7) {
            label.setText("Enter your current password\nin correct order");
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
            this.dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Error(1);
            }
        });
    }
}