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
import java.util.*;
import java.util.List;

public class Rootframe extends JFrame implements ActionListener , FrameCallBack {

    private JButton button2;
    private JPanel postPanel ;

    private String clickedText = "";
    private long user;

//    protected static DataInputStream in ;
//    protected static DataOutputStream out ;




    public Rootframe(long logedinUserId ) {
        user = logedinUserId ;
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
        JMenuItem addPostItem = new JMenuItem("Add Post");
        addPostItem.setBackground(new Color(16,8,32));
        addPostItem.setForeground(new Color(255, 255, 255));
        JMenuItem visitProfileItem = new JMenuItem("Visit Profile");
        visitProfileItem.setBackground(new Color(16,8,32));
        visitProfileItem.setForeground(new Color(255, 255, 255));
        JMenuItem searchItem = new JMenuItem("Search");
        searchItem.setBackground(new Color(16,8,32));
        searchItem.setForeground(new Color(255, 255, 255));

        addPostItem.addActionListener(this);
        visitProfileItem.addActionListener(this);
        searchItem.addActionListener(this);


        menu.add(addPostItem);
        menu.add(visitProfileItem);
        menu.add(searchItem);
        menuBar.add(menu);
        this.setIconImage(img);
        this.getContentPane().setBackground(new Color(16, 2, 32));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setSize(700 , 560);
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
        if (e.getActionCommand().equals("Add Post")) {
            System.out.println("Start add post operation");
            Addpost addpost = new Addpost(user , this);
//            this.dispose();
//            // Fetch all posts again and update the displayed posts
//            List<Post> allPosts = PostDao.getAllPosts();
//            ShowPost(allPosts);
//            this.refactor(); // Refresh the frame to reflect the changes
        } else if (e.getActionCommand().equals("Visit Profile")) {
            System.out.println("Start visit profile operation");
            Profile profile = new Profile("User" , user , user);
            // Visit profile functionality
        } else if (e.getActionCommand().equals("Search")) {
            System.out.println("Start search operation");
            Search search = new Search(user , false , this);

            // Search functionality
        }
    }
    public void Dispose() {
        this.dispose();
    }
    public void ShowPost(List<Post> posts) {
        postPanel = new JPanel();
        postPanel.setLayout(new BoxLayout(postPanel, BoxLayout.Y_AXIS));
        postPanel.setBackground(new Color(16, 8, 32));

        List<User> followedUsers = FollowDao.getFollowedUsers(UserDao.getUserById(user));
//        for (User post :followedUsers) {
//            System.out.println(post.getId());
//        }
        List<Post> followedUsersPosts = new ArrayList<>();

        for (User followedUser : followedUsers) {
            followedUsersPosts.addAll(PostDao.getPostsByUser(followedUser));
        }

        for (Post followedUsersPost : followedUsersPosts) {
            if (posts.contains(followedUsersPost)){
                System.out.println(followedUsersPost.getId());
                posts.add(followedUsersPost);
            }
        }
        // Append followed users' posts to the main list of posts
//        posts.addAll(followedUsersPosts);
                for (Post post :posts) {
            System.out.println(post.getId());
        }

        // Remove duplicate posts based on their content
        Set<Post> uniquePosts = new HashSet<>(posts);
        posts.clear();
        posts.addAll(uniquePosts);

        // Sort posts based on whether the user who made the post is followed by the current user
        posts.sort((post1, post2) -> {
            boolean isPost1Followed = FollowDao.isUserAFollowedByUserB(post1.getUser(), UserDao.getUserById(user));
            boolean isPost2Followed = FollowDao.isUserAFollowedByUserB(post2.getUser(), UserDao.getUserById(user));

            if (isPost1Followed == isPost2Followed) {
                return 0;
            } else if (isPost1Followed) {
                return -1;
            } else {
                return 1;
            }
        });
//        for (Post post :posts) {
//            System.out.println(post.getId());
//        }

        for (Post post : posts) {
            JLabel UserLabel = new JLabel("<html><u>" + post.getUser().getUsername() + "</u> said at " + post.getDateOfPost() + "</html>");
            UserLabel.setForeground(new Color(178, 112, 192));
            UserLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            Rootframe root = this ;
            UserLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    clickedText = post.getUser().getUsername();
                    ShowPostInOrder showPostInOrder = new ShowPostInOrder(user , "name" , clickedText , root);
                    showPostInOrder.refactor(PostDao.getAllPosts());
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
                        ShowPostInOrder showPostInOrder = new ShowPostInOrder(user , "tag" , clickedText , root);
                        showPostInOrder.refactor(PostDao.getAllPosts());

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
            Rootframe r = this ;
            saveButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    clickedText = saveButton.getText();
                    System.out.println(clickedText);
                    ReactOperation reactOperation = new ReactOperation("Save", post, user, r);
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
                    ReactOperation reactOperation = new ReactOperation("Like", post, user, r);


                    // Handle click event for tag
                }


            });


            likeCountLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    clickedText = likeCountLabel.getText();
                    System.out.println(clickedText);
                    UserShow userShow = new UserShow(user , "Likes" ,post.getId() , user);
                    // Handle click event for tag
                }
            });
            postPanel.add(likeButton);
            postPanel.add(likeCountLabel);



            postPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add spacing between posts

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
        Rootframe r = new Rootframe(1L);
        r.refactor(PostDao.getAllPosts());
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
        }
    }
}