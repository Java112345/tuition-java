package com.ebtuition;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.ebtuition.model.Booking;
import com.ebtuition.model.Lesson;
import com.ebtuition.service.TuitionCentre;
import com.ebtuition.model.Student;

public class Main {
    public static void main(String[] args) {
        TuitionCentre centre = new TuitionCentre();
        LocalDate startDate = LocalDate.of(2025, 1, 4);
        centre.createLessons(startDate);

        // Add 15 students
        for (int i = 1; i <= 15; i++) {
    Student s = new Student(
        "Student" + i,                            // name
        (i % 2 == 0 ? "F" : "M"),                 // gender
        LocalDate.of(2012, (i % 12) + 1, (i % 28) + 1), // dob
        "Address " + i,                           // address
        "99999999" + i  ,                         // phone
          i                                     // studentId
    );
    centre.addStudent(s);
}

// Test cancelBooking
Booking cancelTest = null;
for (Booking b : centre.getAllBookings()) {
    if (b.getStatus().equals("booked")) {
        cancelTest = b;
        break;
    }
}
if (cancelTest != null) {
    centre.cancelBooking(cancelTest);
    System.out.println("\nCancelled booking for: " + cancelTest.getStudent().getName() +
            ", Subject: " + cancelTest.getLesson().getSubject());
} else {
    System.out.println("\nNo booking found to cancel.");
}

// Test changeBooking
Booking changeTest = null;
for (Booking b : centre.getAllBookings()) {
    if (b.getStatus().equals("booked")) {
        changeTest = b;
        break;
    }
}
if (changeTest != null) {
    LocalDate newDate = changeTest.getLesson().getDate().plusWeeks(1);
    String newTimeSlot = changeTest.getLesson().getTimeSlot().equals("Morning") ? "Afternoon" : "Morning";

    Booking changed = centre.changeBooking(changeTest, newDate, newTimeSlot);
    if (changed != null) {
        System.out.println("\nChanged booking to new date: " + newDate + " and time slot: " + newTimeSlot);
    } else {
        System.out.println("\nChange booking failed.");
    }
} else {
    System.out.println("\nNo booking found to change.");
}



        // ✅ Debug check to confirm students are added
        if (centre.students.isEmpty()) {
            System.err.println("No students were added!");
            return;
        } else {
            System.out.println("Total students added: " + centre.students.size());
        }

        Random rand = new Random();
        List<String> subjects = new ArrayList<>(centre.subjectPrices.keySet());

        // Ensure at least one student books each lesson
        int studentIndex = 1;
        List<Integer> studentIds = new ArrayList<>(centre.students.keySet()); // safer than relying on index math

        for (Lesson lesson : centre.getAllLessons()) {
            int studentId = studentIds.get((studentIndex - 1) % studentIds.size());

            Booking b = centre.bookLesson(studentId, lesson.getSubject(), lesson.getDate(), lesson.getTimeSlot());
            if (b != null) {
                centre.attendLesson(b);
                centre.reviewLesson(b, "Auto-filled: Good class", rand.nextInt(3) + 3);
            } else {
                System.err.println("Booking failed for student ID: " + studentId);
            }
            studentIndex++;
        }

        // Random additional bookings
        for (int id : centre.students.keySet()) {
            for (int week = 0; week < 4; week++) {
                String subject = subjects.get(rand.nextInt(subjects.size()));
                String day = rand.nextBoolean() ? "Saturday" : "Sunday";
                String timeSlot = rand.nextBoolean() ? "Morning" : "Afternoon";
                LocalDate date = startDate.plusWeeks(week).plusDays(day.equals("Saturday") ? 0 : 1);
                Booking b = centre.bookLesson(id, subject, date, timeSlot);
                if (b != null) {
                    centre.attendLesson(b);
                    centre.reviewLesson(b, "Great lesson in " + subject, rand.nextInt(5) + 1);
                }
            }
        }

        centre.reportStudentsPerLesson();
        centre.reportHighestIncomeSubject();
    }
    
}
