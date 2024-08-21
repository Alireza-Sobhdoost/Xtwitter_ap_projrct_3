package org.example;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "users")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)

    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "bio")
    private String bio;

    @Column(name = "count_followers")
    private int countFollowers;

    @Column(name = "count_following")
    private int countFollowing;

    @Column(name = "date_of_join")
    private LocalDate dateOfJoin;
    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    // Getters and Setters
    // Add any additional properties, constructors, methods as needed


    public User() {
        this.id = id;
        this.username = username;
        this.password = password;
        this.bio = null;
        this.countFollowers = 0;
        this.countFollowing = 0;
        this.dateOfJoin = LocalDate.now();
        this.email = null;
        this.phoneNumber = null;
        this.dateOfBirth = null;
    }

    public Long getId(){
        return this.id ;
    }
    public String getUsername(){
        return this.username ;
    }
    public String getPassword(){
        return this.password;
    }
    public LocalDate getDateOfJoin(){
        return this.dateOfJoin;
    }
    public String getBio(){
        return this.bio;
    }
    public int getCountFollowers(){
        return this.countFollowers;
    }
    public int getCountFollowing(){
        return this.countFollowing;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public void setPassword(String password){
        this.password= password;
    }
    public void setBio(String bio){
        this.bio = bio;
    }
    public void setCountFollowers(int countFollowers){
        this.countFollowers = countFollowers;
    }
    public void setCountFollowing(int countFollowing){
        this.countFollowing = countFollowing;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getDateOfBirth() {
        return this.dateOfBirth;
    }

    public void setDateOfBirth(String dob) {
        // Split the input by "/"
        String[] date = dob.split("/");

        // Create a LocalDate object using the parts of the date
        int year = Integer.parseInt(date[2]);
        int month = Integer.parseInt(date[1]);
        int day = Integer.parseInt(date[0]);
        this.dateOfBirth = LocalDate.of(year, month, day);
    }
}