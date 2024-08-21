package org.example;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tags")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "name", columnDefinition = "TEXT")
    private String name;

    @ManyToOne
    @JoinColumn(name = "postid", referencedColumnName = "id", nullable = false)
    private Post post;

    // Constructors, Getters, and Setters

    public Tag() {

    }

    public Long getId() {
        return this.id;
    }

    public Post getPost() {
        return this.post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public  String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

}