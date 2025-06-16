package com.ebtuition.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Student {
    private Integer studentId;
    private String name;
    private String gender;
    private LocalDate dob;
    private String address;
    private String phone;
    public List<Booking> bookings = new ArrayList<>();

    public Student(String name, String gender, LocalDate dob, String address, String phone, int studentId) {
        
        this.name = name;
        this.gender = gender;
        this.dob = dob;
        this.address = address;
        this.phone = phone;
        this.studentId = studentId;
    }

    

    public Integer getId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public LocalDate getDob() {
        return dob;
    }

    public String getAddress() {
        return address;
    }

    public String getEmergencyContact() {
        return phone;
    }

    public List<Booking> getBookings() {
        return bookings;
    }
}
