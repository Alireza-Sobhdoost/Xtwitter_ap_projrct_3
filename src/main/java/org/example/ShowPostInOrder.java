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

public class ShowPostInOrder extends JFrame implements ActionListener , FrameCallBack {

    private JButton button2;
    private JPanel postPanel ;

    private String clickedText = "";
    private long user;
    private String myorder ;
    private String myname ;

    private Rootframe root ;
    public ShowPostInOrder(long logedinUserId , String order , String name , Rootframe caller) {
        user = logedinUserId ;
        root = caller;
        myorder = order ;
        myname = name ;
        ImageIcon image = new ImageIcon("logo.png");
        Image img = image.getImage();
        Image newImg  = img.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon scaledImage = new ImageIcon(newImg);
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(9, 4, 36));
        JMenu menu = new JMenu("Menu");
        menu.setFocusable(false);
        menu.setForeground(new Color(225, 216, 6));
        menu.setIcon(scaledImage);
        if (this.myorder.equals("name")){
        JMenuItem visitProfileItem = new JMenuItem("Visit Profile");
        visitProfileItem.setBackground(new Color(16,8,32));
        visitProfileItem.setForeground(new Color(255, 255, 255));

        visitProfileItem.addActionListener(this);



        menu.add(visitProfileItem);
        menuBar.add(menu);
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

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals("Visit Profile")) {
            System.out.println("Start visit profile operation");
            Profile profile = new Profile("User" , user , UserDao.getUserByUsername(this.myname).getId());
            // Visit profile functionality
        }
    }
    public void Dispose() {
        this.dispose();
    }
    public void ShowPost(List<Post> posts) {

        postPanel = new JPanel();
        postPanel.setLayout(new BoxLayout(postPanel, BoxLayout.Y_AXIS));
        postPanel.setBackground(new Color(16, 8, 32));

        for (Post post : posts) {
            List<String> t = TagDao.getTagNamesByPostId(post.getId());
            boolean show = false;
            if (myorder.equals("tag")) {
                for (String i : t) {
                    if (i.equals(myname)) {
                        show = true;
                        break;
                    }
                }
            }
            if (myorder.equals("name")) {
                if (myname.equals(post.getUser().getUsername())){
                    show = true ;
                }
            }


            if (show){
            JLabel UserLabel = new JLabel("<html><u>" + post.getUser().getUsername() + "</u> said at " + post.getDateOfPost() + "</html>");
            UserLabel.setForeground(new Color(178, 112, 192));
            UserLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            UserLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    clickedText = post.getUser().getUsername();
                    System.out.println(clickedText);
                    // Handle click event for username
                }
            });

            JLabel contentLabel = new JLabel(post.getContent());
            contentLabel.setForeground(Color.white);

            postPanel.add(UserLabel);
            postPanel.add(contentLabel);
            String tagbar = "";
            for (String tag : TagDao.getTagNamesByPostId(post.getId())) {
                JLabel tagLabel = new JLabel("#" + tag + " ");
                tagLabel.setForeground(new Color(12, 151, 232));
                tagLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                tagLabel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        clickedText = tag;


                        System.out.println(clickedText);
                        // Handle click event for tag
                    }
                });

                postPanel.add(tagLabel);
            }

            // Save Button
            JLabel saveButton = new JLabel("✉\uFE0F");
            saveButton.setForeground(new Color(255, 255, 255));
            saveButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            ShowPostInOrder s = this;
            saveButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    clickedText = saveButton.getText();
                    System.out.println(clickedText);
                    ReactOperation reactOperation = new ReactOperation("Save", post, user, s);
                    // Handle click event for tag
                }
            });
            postPanel.add(saveButton);

            // Like Button with Like Count
            JLabel likeButton = new JLabel("❤\uFE0F");
            likeButton.setForeground(Color.RED);
            JLabel likeCountLabel = new JLabel(String.valueOf(post.getCount_of_likes()));
            likeCountLabel.setForeground(new Color(178, 112, 192));
//            Rootframe r = this;
            likeButton.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent e) {
                    clickedText = likeButton.getText();
                    System.out.println(post.getId());
                    ReactOperation reactOperation = new ReactOperation("Like", post, user, s);


                    // Handle click event for tag
                }


            });


            likeCountLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    clickedText = likeCountLabel.getText();
                    System.out.println(clickedText);
                    // Handle click event for tag
                }
            });
            postPanel.add(likeButton);
            postPanel.add(likeCountLabel);


            postPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add spacing between posts

        }
    }

        JScrollPane scrollPane = new JScrollPane(postPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.getContentPane().add(scrollPane, BorderLayout.CENTER);

        this.revalidate(); // Revalidate the frame to reflect the changes
    }
    public void refactor(List<Post> posts) {
        this.getContentPane().removeAll(); // Remove all components from the frame
        this.getContentPane().invalidate(); // Invalidate the frame
        this.ShowPost(posts); // Call ShowPost to display updated posts
        this.getContentPane().revalidate(); // Revalidate the frame to reflect the changes
        this.getContentPane().repaint(); // Repaint the frame
    }
    public static void main(String[] args) {
//        ShowPostInOrder r = new ShowPostInOrder(1L , "name" , "Sepehr");
//        r.refactor(PostDao.getAllPosts());
    }

    @Override
    public void onADDResult(boolean isPostAdded) {
        if (isPostAdded){
            this.refactor(PostDao.getAllPosts());
        }
    }

    @Override
    public void onLikeResult(boolean isPostLiked) {
        if (isPostLiked){
            this.refactor(PostDao.getAllPosts());
            root.refactor(PostDao.getAllPosts());
        }
    }
}