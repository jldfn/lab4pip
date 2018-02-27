package com.DnDLLC.spring.models;


//import com.fasterxml.jackson.annotation.JsonIgnore;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Entity(name="labuser")
public class User {

    @Id
    private String username;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Dot> dots = new ArrayList<>();

    private String password;

    public BigInteger getPassword() {
        return new BigInteger(password);
    }

    public void setPassword(String password) {
        this.password = new CheckSumCalculator().calculate(password).toString();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.password = new CheckSumCalculator().calculate(password).toString();
    }

}
