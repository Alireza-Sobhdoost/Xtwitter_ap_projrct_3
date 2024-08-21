package org.example;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class Profile extends JFrame implements ActionListener , ProfileCallBack {

    private JButton button2;
    private JPanel profilePanel ;

    private String clickedText = "";
    private User user;
    private User visited;

//    protected static DataInputStream in ;
//    protected static DataOutputStream out ;




    public Profile(String type ,long logedinUserId , long visited) {
        this.user = UserDao.getUserById(logedinUserId) ;
        this.visited = UserDao.getUserById(visited) ;
        ImageIcon image = new ImageIcon("logo.png");
        Image img = image.getImage();
        Image newImg  = img.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon scaledImage = new ImageIcon(newImg);
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(9, 4, 36));
        if (type.equals("User")) {
            if (logedinUserId == visited) {
                JMenu menu = new JMenu("Menu");
                menu.setFocusable(false);
                menu.setForeground(new Color(225, 216, 6));
                menu.setIcon(scaledImage);
                JMenuItem editProfileItem = new JMenuItem("Edit Profile");
                editProfileItem.setBackground(new Color(16, 8, 32));
                editProfileItem.setForeground(new Color(255, 255, 255));
                editProfileItem.addActionListener(this);

                JMenuItem SeelikedItem = new JMenuItem("See Liked posts");
                SeelikedItem.setBackground(new Color(16, 8, 32));
                SeelikedItem.setForeground(new Color(255, 255, 255));
                SeelikedItem.addActionListener(this);

                JMenuItem SeesavedItem = new JMenuItem("See Saved posts");
                SeesavedItem.setBackground(new Color(16, 8, 32));
                SeesavedItem.setForeground(new Color(255, 255, 255));
                SeesavedItem.addActionListener(this);


                menu.add(editProfileItem);
                menu.add(SeelikedItem);
                menu.add(SeesavedItem);
                menuBar.add(menu);
            } else {
                JMenu menu = new JMenu("Menu");
                menu.setFocusable(false);
                menu.setForeground(new Color(225, 216, 6));
                menu.setIcon(scaledImage);
                JMenuItem editProfileItem;
                if (FollowDao.isUserAFollowedByUserB( this.visited,this.user)) {
                    editProfileItem= new JMenuItem("Unfollow");
                }
                else {
                    editProfileItem= new JMenuItem("Follow");
                }

                editProfileItem.setBackground(new Color(16, 8, 32));
                editProfileItem.setForeground(new Color(255, 255, 255));

                editProfileItem.addActionListener(this);


                menu.add(editProfileItem);
                menuBar.add(menu);


            }
        }
        this.setIconImage(img);
        this.getContentPane().setBackground(new Color(16, 2, 32));
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setSize(350 , 560);
        this.setTitle("X twitter");
        this.setJMenuBar(menuBar);
        this.setVisible(true);

        button2 = new JButton();
        // Additional button setup code

        JPanel panelTopBar = new JPanel();
        // Additional panel setup code

        // Frame setup code
        this.refactor(this.visited);

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Edit Profile")) {
            System.out.println("Start Edit Profile operation");
            EditProfile editProfile = new EditProfile(this.user.getId() , this);

        } else if (e.getActionCommand().equals("See Liked posts")) {
            SeePosts seePosts = new SeePosts(this.visited.getId() , "Like");
            seePosts.refactor(PostDao.getAllPosts());

        } else if (e.getActionCommand().equals("See Saved posts")) {
            SeePosts seePosts = new SeePosts(this.visited.getId() , "Save");
            seePosts.refactor(PostDao.getAllPosts());
        }
        else if (e.getActionCommand().equals("Follow")) {
            System.out.println("Start Follow operation");
            FollowingOperations followingOperations = new FollowingOperations(this.visited , this.user , "Follow" , this);
            this.refactor(this.user);

        } else if (e.getActionCommand().equals("Unfollow")) {
            System.out.println("Start Unfollow operation");
            FollowingOperations followingOperations = new FollowingOperations( this.visited , this.user, "Unfollow" , this);

            // Search functionality
        }
    }


    public void Dispose() {
        this.dispose();
    }
    public void Showprofile (User user) {

        profilePanel = new JPanel();
        profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.Y_AXIS));
        profilePanel.setBackground(new Color(16,8,32));
        JLabel UserLabel = new JLabel( user.getUsername());
        UserLabel.setForeground(new Color(178, 112, 192));
        profilePanel.add(UserLabel);
        JLabel BioLabel = new JLabel( user.getBio());
        BioLabel.setForeground(new Color(178, 112, 192));
        profilePanel.add(BioLabel);
        JLabel DobLabel = new JLabel( user.getDateOfBirth().toString());
        DobLabel.setForeground(new Color(178, 112, 192));
        profilePanel.add(DobLabel);
        JLabel EmailLabel = new JLabel( user.getEmail());
        EmailLabel.setForeground(new Color(255, 225, 33));
        profilePanel.add(EmailLabel);
        JLabel PhoneLabel = new JLabel( user.getPhoneNumber());
        PhoneLabel.setForeground(new Color(255, 225, 33));
        profilePanel.add(PhoneLabel);
        JLabel FollowerLabel = new JLabel("Follower: " + String.valueOf(user.getCountFollowers()));
        FollowerLabel.setForeground(new Color(82, 118, 225));

        FollowerLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        Profile p = this ;
        FollowerLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clickedText = FollowerLabel.getText();
                System.out.println(clickedText);
                UserShow userShow = new UserShow(p.visited.getId() , "Followers" ,0L , p.user.getId());

                }
            });
        profilePanel.add(FollowerLabel);
        JLabel FollowingLabel = new JLabel("Following: " + String.valueOf(user.getCountFollowing()));
        FollowingLabel.setForeground(new Color(82, 118, 225));

        FollowingLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        FollowingLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clickedText = FollowingLabel.getText();
                System.out.println(clickedText);
                UserShow userShow = new UserShow(p.visited.getId() , "Followings" ,0L , p.user.getId());

            }
        });
        profilePanel.add(FollowingLabel);

            profilePanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add spacing between profiles

        this.add(profilePanel);
    }

//        this.revalidate(); // Revalidate the frame to reflect the changes
    public void refactor(User user) {
        this.getContentPane().removeAll(); // Remove all components from the frame
        this.getContentPane().invalidate(); // Invalidate the frame
        this.Showprofile(user); // Call Showprofile to display updated profiles
        this.getContentPane().revalidate(); // Revalidate the frame to reflect the changes
        this.getContentPane().repaint(); // Repaint the frame
    }
    public static void main(String[] args) {
        Profile profile = new Profile("User" , 1L , 1L);
        profile.refactor(UserDao.getUserById(1L));
    }

    @Override
    public void onProfileResult(boolean isCompleted) {
        if (isCompleted){
            User v = this.visited ;
            User l = this.user ;
            this.dispose();
            Profile profile = new Profile("User" , l.getId() , v.getId());
            profile.refactor(UserDao.getUserById(v.getId()));
        }
    }

//    @Override
//    public void onADDResult(boolean isprofileAdded) {
//        if (isprofileAdded){
//            this.refactor(profileDao.getAllprofiles());
//        }
//    }
//
//    @Override
//    public void onLikeResult(boolean isprofileLiked) {
//        if (isprofileLiked){
//            this.refactor(profileDao.getAllprofiles());
//        }
//    }


}