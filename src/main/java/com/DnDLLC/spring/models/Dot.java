package com.DnDLLC.spring.models;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
public class Dot {

    @Id
    private Long id;

    @ManyToOne
    private User user;

    private Double xValue;

    private Double yValue;

    private Long dateChecked;

    private Long worktime;

    public Dot(){
        id = System.currentTimeMillis();
    }

    public Dot(User user, Double xValue, Double yValue, Double rValue,
               Long dateChecked, Long worktime, Boolean result){
        id = System.currentTimeMillis();
        this.user = user;
        this.xValue = xValue;
        this.yValue = yValue;
        this.dateChecked = dateChecked;
        this.worktime = worktime;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Double getxValue() {
        return xValue;
    }

    public void setxValue(Double xValue) {
        this.xValue = xValue;
    }

    public Double getyValue() {
        return yValue;
    }

    public void setyValue(Double yValue) {
        this.yValue = yValue;
    }

    public Long getDateChecked() {
        return dateChecked;
    }

    public void setDateChecked(Long dateChecked) {
        this.dateChecked = dateChecked;
    }

    public Long getWorktime() {
        return worktime;
    }

    public void setWorktime(Long worktime) {
        this.worktime = worktime;
    }
}
