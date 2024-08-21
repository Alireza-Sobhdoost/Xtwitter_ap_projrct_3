package org.example;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_of_post")
    private LocalDate dateOfPost;

    @Column(name = "count_of_likes")
    private int count_of_likes;

    @Column(name = "count_of_saves")
    private int count_of_saves;

    @Lob
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "userid", referencedColumnName = "id", nullable = false)
    private User user;

    // Constructors, Getters, and Setters

    public Post() {
        this.dateOfPost = LocalDate.now();
    }

    public Long getId() {
        return this.id;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getDateOfPost() {
        return this.dateOfPost;
    }

    public  void  setCount_of_likes(int count_of_likes){
        this.count_of_likes = count_of_likes ;
    }
    public  void  setCount_of_saves(int count_of_saves){
        this.count_of_saves= count_of_saves;
    }

    public int getCount_of_likes () {
        return this.count_of_likes ;
    }
    public int getCount_of_saves(){
        return this.count_of_saves ;
    }
}