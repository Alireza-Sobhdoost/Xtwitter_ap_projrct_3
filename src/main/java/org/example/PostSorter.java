//package org.example;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.List;
//
//public class PostSorter {
//
//    public static void sortByFollowedUsers(List<Post> posts, User user) {
//        // Sort the list of posts based on whether the user who made the post is followed by the given user
//        Collections.sort(posts, Comparator.comparing(post -> {
//            if (user.getFollowedUsers().contains(post.user)) {
//                return 0; // Followed user's post
//            } else {
//                return 1; // Other user's post
//            }
//        }));
//    }
//
//    static class Post {
//        private Long id;
//        private String content;
//        private User user;
//
//        // Getters and setters
//
//        @Override
//        public String toString() {
//            return "Post{" +
//                    "id=" + id +
//                    ", content='" + content + '\'' +
//                    ", user=" + user.name +
//                    '}';
//        }
//    }
//
//    static class User {
//        private Long id;
//        private String name;
//        private List<User> followedUsers;
//
//        // Getters and setters for User attributes
//
//        public List<User> getFollowedUsers() {
//            return followedUsers;
//        }
//    }
//
//    public static void main(String[] args) {
//        User currentUser = new User();
//        currentUser.setId(1L);
//        currentUser.setName("Alice");
//
//        User followedUser = new User();
//        followedUser.setId(2L);
//        followedUser.setName("Bob");
//
//        List<Post> posts = List.of(
//                new Post(1L, "Post by Bob", followedUser),
//                new Post(2L, "Post by Alice", currentUser),
//                new Post(3L, "Another post by Bob", followedUser),
//                new Post(4L, "Post by Charlie", new User())
//        );
//
//        sortByFollowedUsers(posts, currentUser);
//
//        posts.forEach(System.out::println);
//    }
//}