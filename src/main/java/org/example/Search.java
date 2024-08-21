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

public class Search extends JFrame implements  SearchCallBack{

    private static Long user;

    private static JPanel mainPanel;
    private static JTextField searchBar ;

    private static Rootframe root ;
    public Search(Long id , boolean lastone , Rootframe rootf) {
        root = rootf ;
        user = id;
        this.setBackground(new Color(16, 8, 32));
        this.setTitle("Search");
        ImageIcon image = new ImageIcon("logo.png");
        this.setIconImage(image.getImage());
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(16,8,32));

        // Placeholder for search history data retrieval
        List<SearchHistory> searchHistoryList = SearchHistoryDao.getSearchHistoryByUserId(user);

        refactor(searchHistoryList , lastone);
        // Display search history for today
//        for (SearchHistory history : searchHistoryList) {
//            if (history.getDateOfsearch().equals(LocalDate.now())) {
//                if (history.getSearchtype().equals("User") || history.getSearchtype().equals("Tag")) {
//                    JPanel searchPanel = new JPanel();
//                    JButton button = createButton(history.getSerchedTerm(), history.getSearchtype());
//                    searchPanel.add(button);
//                    searchPanel.setBackground(new Color(16,8,32));
//                    mainPanel.add(searchPanel);
//                }
//            }
//        }

        Search search = this ;
        searchBar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchTerm = searchBar.getText();
                // Perform search operation based on search term
                System.out.println("Searching for: " + searchTerm);
                SearchOperator searchOperator = new SearchOperator(searchTerm , user , search);
            }
        });


        this.getContentPane().setBackground(new Color(16,8,32));
        this.add(mainPanel, BorderLayout.NORTH);
        this.setSize(350, 490);
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public static JButton createButton(String text, String type) {
        JButton button = new JButton(text);
        button.setBackground(new Color(183, 129, 224, 255));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle button click action based on type (User or Tag)
                if (type.equals("User")) {
                    // Perform action for User button
                    System.out.println(text.substring(0));
                    Profile profile = new Profile("User" , user ,UserDao.getUserByUsername(text.substring(0)).getId());
                } else if (type.equals("Tag")) {
                    // Perform action for Tag button
                    System.out.println("Tag search: " + text);
                    ShowPostInOrder showPostInOrder = new ShowPostInOrder(user , "tag" , text ,root);
                    showPostInOrder.refactor(PostDao.getAllPosts());
                }
            }
        });
        return button;
    }
    public static void ShowHistory (List<SearchHistory> histories , boolean lastone){
        searchBar = new JTextField(20);
        searchBar.setBackground(new Color(178, 112, 192));
        mainPanel.add(searchBar);
        if (lastone){
            if (histories.get(histories.size() - 1).getSearchtype().equals("User") || histories.get(histories.size() - 1).getSearchtype().equals("Tag")) {
                JPanel searchPanel = new JPanel();
                JButton button = createButton(histories.get(histories.size() - 1).getSerchedTerm(), histories.get(histories.size() - 1).getSearchtype());
                searchPanel.add(button);
                searchPanel.setBackground(new Color(16,8,32));
                mainPanel.add(searchPanel);
            }
        }
        else {
            for (SearchHistory history : histories) {
                if (history.getDateOfsearch().equals(LocalDate.now())) {
                    if (history.getSearchtype().equals("User") || history.getSearchtype().equals("Tag")) {
                        JPanel searchPanel = new JPanel();
                        JButton button = createButton(history.getSerchedTerm(), history.getSearchtype());
                        searchPanel.add(button);
                        searchPanel.setBackground(new Color(16, 8, 32));
                        mainPanel.add(searchPanel);
                    }
                }
            }
        }

    }

    public void refactor(List<SearchHistory> histories , boolean lastone) {
        this.getContentPane().removeAll(); // Remove all components from the frame
        this.getContentPane().invalidate(); // Invalidate the frame
        this.ShowHistory(histories , lastone); // Call ShowPost to display updated posts
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
            List<SearchHistory> updatedSearchHistory = SearchHistoryDao.getSearchHistoryByUserId(user);
            this.refactor(updatedSearchHistory, true); // Passing true to show only the last search
            this.dispose();
            Search search = new Search(user , true , root);
            System.out.println(successSearch);
        }
    }
}