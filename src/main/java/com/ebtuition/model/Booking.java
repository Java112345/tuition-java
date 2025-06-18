package com.ebtuition.model;

import java.util.List;

public class Booking {
    private Student student;
    private Lesson lesson;
    private String status = "booked";
    private String review;
    private Integer rating;

    public Booking(Student student, Lesson lesson) {
        this.student = student;
        this.lesson = lesson;
        this.status = "booked";
    }

    public String getStatus() {
        return status;
    }

    public void checkIn() {
        attend();
    }

    public void attend() {
        if ("booked".equals(status)) {
            status = "attended";
        }
    }

    public void addReview(String review, int rating) {
        if ("attended".equals(status)) {
            this.review = review;
            this.rating = rating;
        }
    }

    public String getReview() {
        return review;
    }

    public Integer getRating() {
        return rating;
    }

    public Student getStudent() {
        return student;
    }

    public Lesson getLesson() {
        return lesson;
    }

  




    public void setStatus(String status) {
    this.status = status;
}



}
