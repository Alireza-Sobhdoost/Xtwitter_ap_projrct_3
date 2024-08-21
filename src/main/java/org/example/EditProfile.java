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
import java.io.DataInputStream;
import java.net.Socket;
import java.io.DataOutputStream;
import java.io.IOException;

public class EditProfile extends JFrame implements ActionListener {

    JButton EditButton;

    private JTextField usernameField;
    private JPasswordField CurrentpasswordField;
    private JPasswordField passwordField;
    private JTextField emailField;
    private JTextField phoneField; // Added phone number field
    private JTextField bioField; // Added bio field
    private JComboBox<String> dayComboBox;
    private JComboBox<String> monthComboBox;
    private JComboBox<String> yearComboBox;
    private static ProfileCallBack callBack ;
    private User user ;

    public EditProfile(Long Id , ProfileCallBack call) {
        callBack = call;
        user = UserDao.getUserById(Id);
        ImageIcon image = new ImageIcon("logo.png");
        this.setTitle("Sign up Form");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(245, 350); // Adjusted the size to accommodate the new fields
        this.setIconImage(image.getImage());
        this.getContentPane().setBackground(new Color(16, 8, 32));

        JPanel panel = new JPanel();

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(20);
        usernameField.setText(user.getUsername());
        usernameField.setBackground(new Color(64, 64, 128));
        usernameField.setBorder(BorderFactory.createLineBorder(new Color(64, 64, 128)));
        usernameField.setForeground(Color.white);
        usernameLabel.setForeground(new Color(255, 255, 255));

        JLabel CurrentpasswordLabel = new JLabel("Current Password:");
        CurrentpasswordField = new JPasswordField(20);
        CurrentpasswordField.setBackground(new Color(64, 64, 128));
        CurrentpasswordField.setBorder(BorderFactory.createLineBorder(new Color(64, 64, 128)));
        CurrentpasswordField.setForeground(Color.yellow);
        CurrentpasswordLabel.setForeground(new Color(255, 255, 255));

        JLabel passwordLabel = new JLabel("New Password:");
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
        emailField.setText(user.getEmail());
        emailLabel.setForeground(new Color(255, 255, 255));

        JLabel phoneLabel = new JLabel("Phone Number:");
        phoneField = new JTextField(20);
        phoneField.setText(user.getPhoneNumber());
        phoneField.setBackground(new Color(64, 64, 128));
        phoneField.setBorder(BorderFactory.createLineBorder(new Color(64, 64, 128)));
        phoneField.setForeground(Color.yellow);
        phoneLabel.setForeground(new Color(255, 255, 255));

        JLabel bioLabel = new JLabel("Bio:");
        bioField = new JTextField(20);
        bioField.setText(user.getBio());
        bioField.setBackground(new Color(64, 64, 128));
        bioField.setBorder(BorderFactory.createLineBorder(new Color(64, 64, 128)));
        bioField.setForeground(Color.yellow);
        bioLabel.setForeground(new Color(255, 255, 255));

        System.out.println(user.getDateOfBirth().toString());
        String[] dobParts = user.getDateOfBirth().toString().split("-");
        System.out.println(String.valueOf(Integer.parseInt(dobParts[1])));
        String userYear = String.valueOf(Integer.parseInt(dobParts[0]));
        String userMonth = String.valueOf(Integer.parseInt(dobParts[1]))  ;
        String userDay = String.valueOf(Integer.parseInt(dobParts[2]));

        // Set the day, month, and year in the JComboBoxes




        String[] days = new String[31];
        for (int i = 1; i <= 31; i++) {
            days[i - 1] = Integer.toString(i);
        }
        dayComboBox = new JComboBox<>(days);
        dayComboBox.setSelectedItem(userDay);

        String[] months = new String[12];
        for (int i = 1; i <= 12; i++) {
            months[i - 1] = Integer.toString(i);
        }
        monthComboBox = new JComboBox<>(months);
        monthComboBox.setSelectedItem(userMonth);

        String[] years = new String[101];
        for (int i = 1924; i <= 2024; i++) {
            years[i - 1924] = Integer.toString(i);
        }
        yearComboBox = new JComboBox<>(years);
        yearComboBox.setSelectedItem(userYear);



        EditButton = new JButton("Edit Profile");
        EditButton.setBackground(new Color(195, 166, 218));

        panel.setBackground(new Color(16, 8, 32));
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(CurrentpasswordLabel);
        panel.add(CurrentpasswordField);
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
        panel.add(EditButton);
        this.add(panel);

        EditButton.addActionListener(this);
        usernameField.addActionListener(this);
        passwordField.addActionListener(this);

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == EditButton) {
            String username = usernameField.getText();
            String Currentpassword = new String(CurrentpasswordField.getPassword());
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
                 DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                 DataInputStream in = new DataInputStream(socket.getInputStream())) {

                out.writeInt(8);
                out.writeLong(user.getId());
                out.writeUTF(username);
                out.writeUTF(Currentpassword);
                out.writeUTF(password);
                out.writeUTF(email);
                out.writeUTF(phone); // Send phone number to server
                out.writeUTF(bio); // Send bio to server
                out.writeUTF(dob);
                boolean isLoggedIn = in.readBoolean();
//                System.out.println(isLoggedIn);
                callBack.onProfileResult(isLoggedIn);
                out.close();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main (String[] args){
//        EditProfile editProfile = new EditProfile(2L);
    }
}