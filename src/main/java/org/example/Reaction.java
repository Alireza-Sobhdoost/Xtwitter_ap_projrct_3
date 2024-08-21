package org.example;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "reactions")
public class Reaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_of_react")
    private LocalDate dateOfreact;

    @Column(name = "react_type")
    private ReactTypes reactType;

    @ManyToOne
    @JoinColumn(name = "postid", referencedColumnName = "id", nullable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "userid", referencedColumnName = "id", nullable = false)
    private User user;

    // Constructors, Getters, and Setters

    public Reaction() {
        this.dateOfreact = LocalDate.now();
    }

    public Long getId() {
        return this.id;
    }

    public User getUser() {
        return this.user;
    }

    public Post getPost() {
        return this.post;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public ReactTypes getReactType() {
        return this.reactType;
    }

    public void setReactType(ReactTypes reactType) {
        this.reactType = reactType;
    }

    public LocalDate getDateOfreact() {
        return this.dateOfreact;
    }
}