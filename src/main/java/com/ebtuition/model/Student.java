package main.java.com.ebtuition.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Student {
    public int studentId;
    public String name;
    public String gender;
    public LocalDate dob;
    public String address;
    public String emergencyContact;
    public List<Booking> bookings = new ArrayList<>();


    public Student(int studentId, String name, String gender, LocalDate dob, String address, String emergencyContact) {
        this.studentId = studentId;
        this.name = name;
        this.gender = gender;
        this.dob = dob;
        this.address = address;
        this.emergencyContact = emergencyContact;
    }


    public Student(String string, String string2, String string3, String string4, String string5) {
        //TODO Auto-generated constructor stub
    }


    public Object getEmergencyContact() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getEmergencyContact'");
    }


    public Object getName() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getName'");
    }
}
