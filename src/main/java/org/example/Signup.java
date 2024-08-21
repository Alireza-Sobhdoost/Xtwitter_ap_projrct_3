package org.example;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import java.net.Socket;
import java.io.DataOutputStream;
import java.io.IOException;

public class Signup extends JFrame implements ActionListener {

    JButton signupButton;

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField emailField;
    private JTextField phoneField; // Added phone number field
    private JTextField bioField; // Added bio field
    private JComboBox<String> dayComboBox;
    private JComboBox<String> monthComboBox;
    private JComboBox<String> yearComboBox;

    public Signup() {
        ImageIcon image = new ImageIcon("logo.png");
        this.setTitle("Sign up Form");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(245, 300); // Adjusted the size to accommodate the new fields
        this.setIconImage(image.getImage());
        this.getContentPane().setBackground(new Color(16, 8, 32));

        JPanel panel = new JPanel();

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(20);
        usernameField.setBackground(new Color(64, 64, 128));
        usernameField.setBorder(BorderFactory.createLineBorder(new Color(64, 64, 128)));
        usernameField.setForeground(Color.white);
        usernameLabel.setForeground(new Color(255, 255, 255));

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);
        passwordField.setBackground(new Color(64, 64, 128));
        passwordField.setBorder(BorderFactory.createLineBorder(new Color(64, 64, 128)));
        passwordField.setForeground(Color.yellow);
        passwordLabel.setForeground(new Color(255, 255, 255));

        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField(20);
        emailField.setBackground(new Color(64, 64, 128));
        emailField.setBorder(BorderFactory.createLineBorder(new Color(64, 64, 128)));
        emailField.setForeground(Color.yellow);
        emailLabel.setForeground(new Color(255, 255, 255));

        JLabel phoneLabel = new JLabel("Phone Number:");
        phoneField = new JTextField(20);
        phoneField.setBackground(new Color(64, 64, 128));
        phoneField.setBorder(BorderFactory.createLineBorder(new Color(64, 64, 128)));
        phoneField.setForeground(Color.yellow);
        phoneLabel.setForeground(new Color(255, 255, 255));

        JLabel bioLabel = new JLabel("Bio:");
        bioField = new JTextField(20);
        bioField.setBackground(new Color(64, 64, 128));
        bioField.setBorder(BorderFactory.createLineBorder(new Color(64, 64, 128)));
        bioField.setForeground(Color.yellow);
        bioLabel.setForeground(new Color(255, 255, 255));

        String[] days = new String[31];
        for (int i = 1; i <= 31; i++) {
            days[i - 1] = Integer.toString(i);
        }
        dayComboBox = new JComboBox<>(days);

        String[] months = new String[12];
        for (int i = 1; i <= 12; i++) {
            months[i - 1] = Integer.toString(i);
        }
        monthComboBox = new JComboBox<>(months);

        String[] years = new String[101];
        for (int i = 1924; i <= 2024; i++) {
            years[i - 1924] = Integer.toString(i);
        }
        yearComboBox = new JComboBox<>(years);

        signupButton = new JButton("Signup");
        signupButton.setBackground(new Color(195, 166, 218));

        panel.setBackground(new Color(16, 8, 32));
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(phoneLabel);
        panel.add(phoneField);
        panel.add(bioLabel);
        panel.add(bioField);
        JLabel dOb = new JLabel("Date of Birth:");
        dOb.setForeground(Color.white);
        panel.add(dOb);
        panel.add(dayComboBox);
        panel.add(monthComboBox);
        panel.add(yearComboBox);
        panel.add(signupButton);
        this.add(panel);

        signupButton.addActionListener(this);
        usernameField.addActionListener(this);
        passwordField.addActionListener(this);

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == signupButton) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String email = emailField.getText();
            String phone = phoneField.getText(); // Get the phone number entered by the user
            String bio = bioField.getText(); // Get the bio entered by the user
            String day = (String) dayComboBox.getSelectedItem();
            String month = (String) monthComboBox.getSelectedItem();
            String year = (String) yearComboBox.getSelectedItem();
            String dob = day + "/" + month + "/" + year;
            this.dispose();

            try (Socket socket = new Socket("localhost", 5000);
                 DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {

                out.writeInt(1);
                out.writeUTF(username);
                out.writeUTF(password);
                out.writeUTF(email);
                out.writeUTF(phone); // Send phone number to server
                out.writeUTF(bio); // Send bio to server
                out.writeUTF(dob);
                out.close();
                socket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}