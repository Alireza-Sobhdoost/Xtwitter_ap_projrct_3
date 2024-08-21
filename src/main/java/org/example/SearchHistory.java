package org.example;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "searchs")
public class SearchHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_of_search")
    private LocalDate dateOfsearch;


    @Column(name = "search_type")
    private String searchtype;

    @ManyToOne
    @JoinColumn(name = "userid", referencedColumnName = "id", nullable = false)
    private User user;


    @Column(name = "searched_term")
    private String serchedterm;


    // Constructors, Getters, and Setters

    public SearchHistory() {
        this.dateOfsearch = LocalDate.now();
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

    public String getSerchedTerm() {
        return this.serchedterm;
    }

    public void setSerchedterm(String term) {
        this.serchedterm = term;
    }


    public String getSearchtype() {
        return this.searchtype;
    }

    public void setSearchType(String searchtype) {
        this.searchtype = searchtype;
    }

    public LocalDate getDateOfsearch() {
        return this.dateOfsearch;
    }

}