package main.java.com.ebtuition;

import java.time.LocalDate;
import java.util.Random;

import main.java.com.ebtuition.model.Booking;
import main.java.com.ebtuition.model.Student;
import main.java.com.ebtuition.service.TuitionCentre;

public class Main {
    public static void main(String[] args) {
        TuitionCentre centre = new TuitionCentre();
        LocalDate startDate = LocalDate.of(2025, 1, 4);
        centre.createLessons(startDate);

        for (int i = 1; i <= 15; i++) {
            Student s = new Student(i, "Student" + i, (i % 2 == 0 ? "F" : "M"),
                    LocalDate.of(2012, i % 12 + 1, i % 28 + 1),
                    "Address " + i, "99999999" + i);
            centre.addStudent(s);
        }

        Random rand = new Random();
        for (int id : centre.students.keySet()) {
            for (int week = 0; week < 4; week++) {
                String subject = centre.subjectPrices.keySet().stream().toList().get(rand.nextInt(4));
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
