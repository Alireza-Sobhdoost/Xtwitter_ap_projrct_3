package org.example;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.time.LocalDate;
import java.util.List;

public class UserShow extends JFrame implements  SearchCallBack{

    private static Long user;

    private static JPanel mainPanel;
    private static JTextField searchBar ;


    private static Long postid ;
    private static long caller;
    private static String type ;
    public UserShow(Long id , String type  ,Long post , Long call) {
        caller = call;
        this.type = type ;
        user = id;
        postid = post;
        this.setBackground(new Color(16, 8, 32));
        this.setTitle(type);
        ImageIcon image = new ImageIcon("logo.png");
        this.setIconImage(image.getImage());
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(16,8,32));

        // Placeholder for search history data retrieval
        List<User> users = UserDao.getAllUsers();

        refactor(users , type , postid , user);

//        }


        this.getContentPane().setBackground(new Color(16,8,32));
        this.add(mainPanel, BorderLayout.NORTH);
        this.setSize(350, 490);
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public static JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(183, 129, 224, 255));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle button click action based on type (User or Tag)
                    // Perform action for User button
                    System.out.println(text.substring(0));
                    Profile profile = new Profile("User" , user ,UserDao.getUserByUsername(text.substring(0)).getId());

            }
        });
        return button;
    }
    public static void ShowinOrder (List<User> users , String type , Long postid , Long userId ){

            for (User user : users) {
                if (type.equals("Likes")) {
//                    System.out.println("whyyyy???");
                    if (ReactionDao.isReactionExist(postid , user.getId() , ReactTypes.Like)){
                        JPanel searchPanel = new JPanel();
                        JButton button = createButton(user.getUsername());
                        if (FollowDao.isUserAFollowedByUserB(user , UserDao.getUserById(caller))){
                            button.setBackground(new Color(139, 44, 236));
                        }
                        if (user.getId() == caller){
                            button.setBackground(new Color(16, 223, 238));
                        }
                        searchPanel.add(button);
                        searchPanel.setBackground(new Color(16, 8, 32));
                        mainPanel.add(searchPanel);
                    }
                }
                else if (type.equals("Followers")) {
                    if (FollowDao.isUserAFollowedByUserB(UserDao.getUserById(userId), user)){
                        JPanel searchPanel = new JPanel();
                        JButton button = createButton(user.getUsername());
                        if (FollowDao.isUserAFollowedByUserB(user , UserDao.getUserById(caller))){
                            button.setBackground(new Color(139, 44, 236));
                        }
                        searchPanel.add(button);
                        searchPanel.setBackground(new Color(16, 8, 32));
                        mainPanel.add(searchPanel);
                    }
                }
                else if (type.equals("Followings")) {
                    if (FollowDao.isUserAFollowedByUserB(user , UserDao.getUserById(userId))){
                        JPanel searchPanel = new JPanel();
                        JButton button = createButton(user.getUsername());
                        if (FollowDao.isUserAFollowedByUserB(user , UserDao.getUserById(caller))){
                            button.setBackground(new Color(139, 44, 236));
                        }
                        searchPanel.add(button);
                        searchPanel.setBackground(new Color(16, 8, 32));
                        mainPanel.add(searchPanel);
                    }
                }
            }


    }

    public void refactor(List<User> users , String type , Long postid , long user ) {
        this.getContentPane().removeAll(); // Remove all components from the frame
        this.getContentPane().invalidate(); // Invalidate the frame
        this.ShowinOrder(users, type , postid , user ); // Call ShowPost to display updated posts
        this.getContentPane().revalidate(); // Revalidate the frame to reflect the changes
        this.getContentPane().repaint(); // Repaint the frame
    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            new Search(1L , false);
//        });
//    }

    @Override
    public void onSearchResult(boolean successSearch) {
        if (successSearch) {
            List<User> users = UserDao.getAllUsers();
            String t = this.type ;
            refactor(users , this.type , postid , user );
            this.dispose();
            UserShow userShow = new UserShow(user , t , postid, caller);
        }
    }
}