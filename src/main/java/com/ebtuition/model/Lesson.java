package main.java.com.ebtuition.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Lesson {
    public String subject;
    public String day;
    public String timeSlot;
    public double price;
    public LocalDate date;
    public List<Object> bookings = new ArrayList<>();

    public Lesson(String subject, String day, String timeSlot, double price, LocalDate date) {
        this.subject = subject;
        this.day = day;
        this.timeSlot = timeSlot;
        this.price = price;
        this.date = date;
    }
}
