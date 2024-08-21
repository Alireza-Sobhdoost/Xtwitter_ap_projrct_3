package  org.example ;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Login extends JFrame implements ActionListener {

    JButton loginButton;
    private JTextField usernameField;
    private JPasswordField passwordField;

    private LoginCallback callback;

    public Login(LoginCallback callback) {
        ImageIcon image = new ImageIcon("logo.png");
        this.setTitle("Log in Form");
        this.callback = callback;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(280, 150);
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

        loginButton = new JButton("Login");
        loginButton.setBackground(new Color(195, 166, 218));

        panel.setBackground(new Color(16, 8, 32));
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);
        this.add(panel);

        loginButton.addActionListener(this);
        usernameField.addActionListener(this);
        passwordField.addActionListener(this);

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == loginButton) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            this.dispose();

            try (Socket socket = new Socket("localhost", 5000);
                 DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                 DataInputStream in = new DataInputStream(socket.getInputStream())) {
                out.writeInt(2);
                out.writeUTF(username); // Send username to server
                out.writeUTF(password); // Send password to server
                boolean isLoggedIn = in.readBoolean();
                Long logedinUserId = in.readLong() ;
//                System.out.println(isLoggedIn);
                callback.onLoginResult(isLoggedIn , logedinUserId);
                out.close();
                in.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}


//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                new Login();
//            }
//        });
//    }
//}