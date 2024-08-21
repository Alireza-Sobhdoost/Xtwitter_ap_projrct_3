package org.example;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class Main {

    public static void main(String[] args) {

//        Mainframe m = new Mainframe();
//        SearchHistory searchHistory = new SearchHistory() ;
//        searchHistory.setSearchType("User");
//        searchHistory.setUser(UserDao.getUserById(1L));
//        searchHistory.setSercheduser(UserDao.getUserById(10L));
//        SearchHistoryDao.saveSearchHistory(searchHistory);
//        if (TagDao.isTagExist("test")){
//            System.out.println("test");
//        }
//        String a = "#test";
//        System.out.println(a.substring(1));
//        List<SearchHistory> histories= SearchHistoryDao.getSearchHistoryByUserId(1L);
//        for (SearchHistory history : histories) {
//            if (history.getDateOfsearch().equals(LocalDate.now())) {
//                if (history.getSearchtype().equals("User") || history.getSearchtype().equals("Tag")) {
//                    System.out.println(history.getSerchedTerm());
//                }
//            }
//        }
//        Follow follow = new Follow();
//        follow.setFollowed(UserDao.getUserById(2L));
//        follow.setFollower(UserDao.getUserById(1L));
//        User follower = UserDao.getUserById(1L);
//        follower.setCountFollowing( follower.getCountFollowing() +1 );
//        UserDao.updateUser(follower);
//        User followed = UserDao.getUserById(2L);
//        followed.setCountFollowers(followed.getCountFollowers() + 1);
//        UserDao.updateUser(followed);
//        FollowDao.saveFollow(follow);

//        if (FollowDao.isUserAFollowedByUserB(UserDao.getUserById(2L) , UserDao.getUserById(1L))){
//            FollowDao.deleteUserAFollowedByUserB(UserDao.getUserById(2L) , UserDao.getUserById(1L));
//        }
        User u = UserDao.getUserById(1L);
        u.setCountFollowers(0);
        UserDao.updateUser(u);




//        Reaction r = new Reaction();
//        r.setReactrype(ReactTypes.Like);
//        r.setPost(PostDao.getPostById(1L));
//        r.setUser(UserDao.getUserById(8L));
//        Post p = PostDao.getPostById(1L);
//        p.setCount_of_likes(0);
//        PostDao.updatePost(p);
//        ReactionDao.saveReaction(r);
//        System.out.println(PostDao.getPostById(1L).getCount_of_likes());

//        Post post = new Post() ;
//        post.setUser(UserDao.getUserByUsername("Sepehr"));
//        post.setContent("hi this is test twit");
//        Tag tag = new Tag();
//        tag.setName("Ap");
//        tag.setPost(PostDao.getPostById(new Long(2)));
////        PostDao.savePost(post);
//        TagDao.saveTag(tag);
//        List<String> mytag = TagDao.getTagNamesByPostId(new Long(4));
//        for (String i : mytag){
//            System.out.println(i);
//        }
////        PostDao.savePost(post);
//        Rootframe root = new Rootframe();

//        Client client = new Client("127.0.0.1", 5000);
//        Appframe twitter = new Appframe(client) ;



    }
}