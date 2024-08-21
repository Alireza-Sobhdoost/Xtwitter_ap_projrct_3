package org.example;

import org.hibernate.type.LocalDateType;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private static final int PORT = 5000;
    private static int Task ;
    private static void handleSignup(String username, String password, String email , String phone , String bio , String dob ,DataOutputStream out) {
        try {
            if (!username.isEmpty()) {
                if (!UserDao.isUserExsit(username)) {
                    if ((!email.isEmpty()) && (!phone.isEmpty()) && (Validator.validateEmail(email)) && (Validator.validatePhone(phone))) {
                        String hashedPassword = PasswordUtils.hashPassword(password);
                        User user = new User();
                        user.setUsername(username);
                        user.setPassword(hashedPassword);
                        user.setEmail(email);
                        user.setPhoneNumber(phone);
                        user.setBio(bio);
                        user.setDateOfBirth(dob);
                        UserDao.saveUser(user);
                        out.writeUTF("Signup successful");
                        Message s = new Message(1, String.valueOf(user.getId()));
                    }
                    else {
                        Error error = new Error(6);
                    }
                } else {
                    Error error = new Error(2);

                }
            }
            else {
                Error error = new Error(1);
                out.writeUTF("Signup failed");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void handleLogin(String username, String password, DataOutputStream out) throws IOException {
        try {
            if (!username.isEmpty()) {
                String hashedPassword = PasswordUtils.hashPassword(password);
                User user = UserDao.getUserByUsername(username);

                if (user != null && hashedPassword != null && hashedPassword.equals(user.getPassword())) {
//                    out.writeUTF("Login successful");
//                    Appframe.isLogedIn = true ;
                    if (!username.equals("Parham")) {
                        Message l = new Message(2, user.getUsername());
                    }
                    out.writeBoolean(true);
                    out.writeLong(user.getId());
//                    Rootframe root = new Rootframe(user.getId()) ;
//                    root.ShowPost(PostDao.getAllPosts());
                } else {
                    Error error = new Error(4);
                    out.writeUTF("Login failed");
                }
            } else {
                Error error = new Error(3);
                out.writeUTF("Login failed");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            Error error = new Error(3);
            out.writeUTF("Invalid userid format");
        }
    }

    private static void handleAddpost(long user, String content, String tag, DataOutputStream out) {
        try {
            if (!content.isEmpty()) {
                    Post post = new Post();
                    post.setContent(content);
                    post.setUser(UserDao.getUserById(user));
                    PostDao.savePost(post);
//                    out.writeUTF("A successful");
                    if (!tag.isEmpty()) {
                        String[] tags = new String[0] ;
                        tags = tag.split(",");
                        for (String item : tags ){
                            Tag posttag = new Tag() ;
                            posttag.setPost(post);
                            posttag.setName(item);
                            TagDao.saveTag(posttag);
                        }
                        Message s = new Message(3, "with" + tags.length + " tag");

                    }
                    else {
                        Message s = new Message(3, "");
                    }
                    out.writeBoolean(true);
//                    Rootframe r = new Rootframe(user);
//                    r.refactor(PostDao.getAllPosts());
            }
            else {
                Error error = new Error(1);
                out.writeUTF("Posing failed");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleReaction (ReactTypes reactType , Long PostId , Long UserId , DataOutputStream out){
        Reaction reaction = new Reaction();
        reaction.setPost(PostDao.getPostById(PostId));
        reaction.setUser(UserDao.getUserById(UserId));
        Post post = PostDao.getPostById(PostId);
        if (reactType == ReactTypes.Like){
            Reaction temp1 = ReactionDao.getReactionByCriteria(PostId ,UserId,ReactTypes.Like) ;
            Reaction temp2 = ReactionDao.getReactionByCriteria(PostId ,UserId,ReactTypes.UnLike);
            if (temp1 != null){
                ReactionDao.deleteReaction(temp1);
                post.setCount_of_likes(post.getCount_of_likes() - 1);
                reaction.setReactType(ReactTypes.UnLike);
                ReactionDao.saveReaction(reaction);
            }
            else {
                if (temp2 != null){
                    ReactionDao.deleteReaction(temp2);
                }
                post.setCount_of_likes(post.getCount_of_likes() + 1);
                reaction.setReactType(ReactTypes.Like);
                ReactionDao.saveReaction(reaction);
            }


        }
        else if (reactType == ReactTypes.Save){
            Reaction temp1 = ReactionDao.getReactionByCriteria(PostId ,UserId,ReactTypes.Save) ;
            Reaction temp2 = ReactionDao.getReactionByCriteria(PostId ,UserId,ReactTypes.UnSave);
            if (temp1 != null){
                ReactionDao.deleteReaction(temp1);
                post.setCount_of_saves(post.getCount_of_saves() - 1);
                reaction.setReactType(ReactTypes.UnSave);
                ReactionDao.saveReaction(reaction);
            }
            else {
                if (temp2 != null){
                    ReactionDao.deleteReaction(temp2);
                }
                post.setCount_of_saves(post.getCount_of_saves() + 1);
                reaction.setReactType(ReactTypes.Save);
                ReactionDao.saveReaction(reaction);
            }
        }
        PostDao.updatePost(post);
        try {
            out.writeBoolean(true);
        }catch (IOException e) {
            e.printStackTrace();
        }


//        Rootframe r = new Rootframe(UserId);
//        r.refactor(PostDao.getAllPosts());
    }

    private static void handleSearch(Long id ,String term, String type, DataOutputStream out) throws IOException {
        try {
            if (!term.isEmpty()) {
                SearchHistory searchHistory = new SearchHistory() ;
                searchHistory.setUser(UserDao.getUserById(id));
                if (type.equals("User") && UserDao.isUserExsit(term)){
                    searchHistory.setSearchType(type);
                    searchHistory.setSerchedterm(UserDao.getUserByUsername(term).getUsername());
                    SearchHistoryDao.saveSearchHistory(searchHistory);
                    out.writeBoolean(true);
                }
                else if (type.equals("Tag") && TagDao.isTagExist(term)){
                    searchHistory.setSearchType(type);
                    searchHistory.setSerchedterm(TagDao.getTagByUName(term).getName());
                    SearchHistoryDao.saveSearchHistory(searchHistory);
                    out.writeBoolean(true);
                }
                else {
                    Error error = new Error(5);
                    out.writeBoolean(false);
                }
            } else {
                out.writeBoolean(false);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            out.writeBoolean(false);
        }
    }

    private static void  handleFollow(Long A , Long B , DataOutputStream out){
        User followed = UserDao.getUserById(A);
        User follower = UserDao.getUserById(B);
        Follow follow = new Follow();
        follow.setFollowed(followed);
        follow.setFollower(follower);
        follower.setCountFollowing( follower.getCountFollowing() +1 );
        UserDao.updateUser(follower);
        followed.setCountFollowers(followed.getCountFollowers() + 1);
        UserDao.updateUser(followed);
        FollowDao.saveFollow(follow);
        try {
            out.writeBoolean(true);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    private static void  handleUnfollow(Long A , Long B , DataOutputStream out){
        User followed = UserDao.getUserById(A);
        User follower = UserDao.getUserById(B);
        follower.setCountFollowing( follower.getCountFollowing() - 1 );
        UserDao.updateUser(follower);
        followed.setCountFollowers(followed.getCountFollowers() - 1);
        UserDao.updateUser(followed);
        FollowDao.deleteUserAFollowedByUserB(followed , follower);
        try {
            out.writeBoolean(true);
        }catch (IOException e){
            e.printStackTrace();
        }

    }
    private static void handleEditProfile(Long id,String username, String Currentpassword ,String password, String email , String phone , String bio , String dob ,DataOutputStream out) {
        try {
            if (!username.isEmpty()) {
                if (!UserDao.isUserExsit(username) || UserDao.getUserById(id).getUsername().equals(username)) {
                    if ((!email.isEmpty()) && (!phone.isEmpty()) && (Validator.validateEmail(email)) && (Validator.validatePhone(phone))) {

                        String hashedCPassword = PasswordUtils.hashPassword(Currentpassword);
                        String hashedPassword = PasswordUtils.hashPassword(password);
                        User user = UserDao.getUserById(id);
                        if (!(hashedCPassword.equals(user.getPassword())) && Currentpassword.isEmpty()){
                            user.setUsername(username);
                            user.setEmail(email);
                            user.setPhoneNumber(phone);
                            user.setBio(bio);
                            user.setDateOfBirth(dob);
                            UserDao.updateUser(user);
                            out.writeBoolean(true);
                            Message s = new Message(4, String.valueOf(user.getId()));
                        }
                        else {
                            if (hashedCPassword.equals(user.getPassword())){
                                user.setUsername(username);
                                user.setPassword(hashedPassword);
                                user.setEmail(email);
                                user.setPhoneNumber(phone);
                                user.setBio(bio);
                                user.setDateOfBirth(dob);
                                UserDao.updateUser(user);
                                out.writeBoolean(true);
                            Message s = new Message(4, String.valueOf(user.getId()));
                            }
                            else {
                                Error error = new Error(7);
                            }

                        }

                    }
                    else {
                        Error error = new Error(6);
                    }
                } else {
                    Error error = new Error(2);

                }
            }
            else {
                Error error = new Error(1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started. Waiting for clients...");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected: " + socket);

                // Handle client request in a new thread
                Thread clientThread = new Thread(new ClientHandler(socket));
                clientThread.setDaemon(true);
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static class ClientHandler implements Runnable {
        private Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (DataInputStream in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                 DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {

                // Read username and password from client
                Task = in.readInt();
//                if (Task == 0){
//                    Appframe appframe = new Appframe();
//                }
                if (Task == 1){
                    String username = in.readUTF();
                    String password = in.readUTF();
                    String email = in.readUTF() ;
                    String phone = in.readUTF() ;
                    String bio = in.readUTF() ;
                    String dob = in.readUTF() ;
                    handleSignup(username, password, email, phone, bio, dob,out);
                }
                if (Task == 2){
                    String username = in.readUTF();
                    String password = in.readUTF();
                    handleLogin(username , password ,out);
                }
                if (Task == 3){
                    long user = in.readLong();
                    String content = in.readUTF();
                    String tag = in.readUTF();
                    handleAddpost(user , content , tag ,out);
                }
                if (Task == 4){
                    ReactTypes reactType = ReactTypes.valueOf(in.readUTF())  ;
                    Long PostId = in.readLong() ;
                    Long UserId = in.readLong();
                    handleReaction(reactType , PostId , UserId , out);

                }
                if (Task ==5){
                    Long id = in.readLong();
                   String term = in.readUTF();
                   String type = in.readUTF();
                   handleSearch(id ,term , type , out) ;
                }
                if (Task == 6){
                    Long A = in.readLong();
                    Long B = in.readLong();
                    handleFollow(A , B , out);
                }
                if (Task == 7){
                    Long A = in.readLong();
                    Long B = in.readLong();
                    handleUnfollow(A , B , out);
                }
                if (Task == 8){
                    Long id = in.readLong();
                    String username = in.readUTF();
                    String Currentpassword = in.readUTF();
                    String password = in.readUTF();
                    String email = in.readUTF() ;
                    String phone = in.readUTF() ;
                    String bio = in.readUTF() ;
                    String dob = in.readUTF() ;
                    handleEditProfile(id,username, Currentpassword,password, email, phone, bio, dob,out);
                }




                // Handle signup task



            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}