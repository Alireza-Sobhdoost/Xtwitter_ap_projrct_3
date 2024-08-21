package org.example;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "follows")
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_of_follow")
    private LocalDate dateOffollow;


    @ManyToOne
    @JoinColumn(name = "follower_id", referencedColumnName = "id", nullable = false)
    private User follower;

    @ManyToOne
    @JoinColumn(name = "followed_id", referencedColumnName = "id", nullable = false)
    private User followed;

    // Constructors, Getters, and Setters

    public Follow() {
        this.dateOffollow = LocalDate.now();
    }

    public Long getId() {
        return this.id;
    }

    public User getFollower() {
        return this.follower;
    }

    public User getFollowed() {
        return this.followed;
    }

    public void setFollower(User user) {
        this.follower = user;
    }

    public void setFollowed(User user) {
        this.followed = user;
    }


    public LocalDate getDateOffollow() {
        return this.dateOffollow;
    }
}