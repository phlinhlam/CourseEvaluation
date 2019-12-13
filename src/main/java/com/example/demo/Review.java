package com.example.demo;

import javax.persistence.*;

@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name ="review_id")
    private long review_id;

    //for first section
    private String q1;
    private String q2;
    private String q3;
    private String q4;
    private String q5;
    private String q6;

    //for second section
    private String qs1;
    private String qs2;
    private String qs3;
    private String qs4;
    private String qs5;

    //for section that they're in
    private String section;

    private String username;
    //linked with user
    @ManyToOne(cascade = CascadeType.ALL)
    public User user;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    //Constructors
    public Review() {
    }
    //All questions must be answered, so the constructor will include all the fields


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getReview_id() {
        return review_id;
    }

    public void setReview_id(long review_id) {
        this.review_id = review_id;
    }

    public String getQ1() {
        return q1;
    }

    public void setQ1(String q1) {
        this.q1 = q1;
    }

    public String getQ2() {
        return q2;
    }

    public void setQ2(String q2) {
        this.q2 = q2;
    }

    public String getQ3() {
        return q3;
    }

    public void setQ3(String q3) {
        this.q3 = q3;
    }

    public String getQ4() {
        return q4;
    }

    public void setQ4(String q4) {
        this.q4 = q4;
    }

    public String getQ5() {
        return q5;
    }

    public void setQ5(String q5) {
        this.q5 = q5;
    }

    public String getQ6() {
        return q6;
    }

    public void setQ6(String q6) {
        this.q6 = q6;
    }

    public String getQs1() {
        return qs1;
    }

    public void setQs1(String qs1) {
        this.qs1 = qs1;
    }

    public String getQs2() {
        return qs2;
    }

    public void setQs2(String qs2) {
        this.qs2 = qs2;
    }

    public String getQs3() {
        return qs3;
    }

    public void setQs3(String qs3) {
        this.qs3 = qs3;
    }

    public String getQs4() {
        return qs4;
    }

    public void setQs4(String qs4) {
        this.qs4 = qs4;
    }

    public String getQs5() {
        return qs5;
    }

    public void setQs5(String qs5) {
        this.qs5 = qs5;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public Review(String q1, String q2, String q3, String q4, String q5, String q6, String qs1, String qs2, String qs3, String qs4, String qs5, String section) {
        this.q1 = q1;
        this.q2 = q2;
        this.q3 = q3;
        this.q4 = q4;
        this.q5 = q5;
        this.q6 = q6;
        this.qs1 = qs1;
        this.qs2 = qs2;
        this.qs3 = qs3;
        this.qs4 = qs4;
        this.qs5 = qs5;
        this.section = section;
    }

    public Review(String q1, String q2, String q3, String q4, String q5, String q6, String qs1, String qs2, String qs3, String qs4, String qs5, String section, User user) {
        this.q1 = q1;
        this.q2 = q2;
        this.q3 = q3;
        this.q4 = q4;
        this.q5 = q5;
        this.q6 = q6;
        this.qs1 = qs1;
        this.qs2 = qs2;
        this.qs3 = qs3;
        this.qs4 = qs4;
        this.qs5 = qs5;
        this.section = section;
        this.user = user;
    }
}
