package com.ebtuition.service;

import java.time.LocalDate;
import java.util.*;
import com.ebtuition.model.Student;
import com.ebtuition.model.Lesson;
import com.ebtuition.model.Booking;

public class TuitionCentre {
    public Map<Integer, Student> students = new HashMap<>();
    public List<Lesson> lessons = new ArrayList<>();
    public Map<String, Double> subjectPrices = Map.of(
        "English", 20.0,
        "Math", 25.0,
        "Verbal Reasoning", 30.0,
        "Non-verbal Reasoning", 35.0
    );

    public void addStudent(Student student) {
        if (student != null && student.getId() > 0) {
            students.put(student.getId(), student);
        } else {
            System.out.println("Invalid student ID or student is null: " + student);
        }
    }

    public void createLessons(LocalDate startDate) {
        for (int week = 0; week < 8; week++) {
            LocalDate weekend = startDate.plusWeeks(week);
            for (int i = 0; i < 2; i++) {
                String day = i == 0 ? "Saturday" : "Sunday";
                LocalDate lessonDate = weekend.plusDays(i);
                for (String subject : subjectPrices.keySet()) {
                    for (String timeSlot : List.of("Morning", "Afternoon")) {
                        Lesson lesson = new Lesson(subject, day, timeSlot, subjectPrices.get(subject), lessonDate);
                        lessons.add(lesson);
                    }
                }
            }
        }
    }

    public Booking bookLesson(int studentId, String subject, LocalDate date, String timeSlot) {
        Student student = students.get(studentId);
        if (student == null) return null;

        for (Lesson lesson : lessons) {
            if (lesson.getSubject().equals(subject) &&
                lesson.getDate().equals(date) &&
                lesson.getTimeSlot().equals(timeSlot)) {

                if (lesson.bookings.size() < 4) {
                    for (Booking b : student.bookings) {
                        if (b.getLesson().getDate().equals(date) &&
                            b.getLesson().getTimeSlot().equals(timeSlot)) {
                            return null; // time conflict
                        }
                    }

                    Booking booking = new Booking(student, lesson);
                    student.bookings.add(booking);
                    lesson.bookings.add(booking);
                    return booking;
                }
            }
        }
        return null;
    }

    public void cancelBooking(Booking booking) {
        if (booking.getStatus().equals("booked")) {
            booking.setStatus("cancelled");
            booking.getStudent().bookings.remove(booking);
            booking.getLesson().bookings.remove(booking);
        }
    }

    public Booking changeBooking(Booking booking, LocalDate newDate, String newTime) {
        if (!"booked".equals(booking.getStatus())) return null;
        String subject = booking.getLesson().getSubject();

        for (Lesson lesson : lessons) {
            if (lesson.getSubject().equals(subject) &&
                lesson.getDate().equals(newDate) &&
                lesson.getTimeSlot().equals(newTime)) {

                if (lesson.bookings.size() < 4) {
                    cancelBooking(booking);
                    return bookLesson(booking.getStudent().getId(), subject, newDate, newTime);
                }
            }
        }
        return null;
    }

    public void attendLesson(Booking booking) {
        booking.attend();
    }

    public void reviewLesson(Booking booking, String review, int rating) {
        booking.addReview(review, rating);
    }

    public void reportStudentsPerLesson() {
        System.out.println("\n--- Students per Lesson with Avg Rating ---");
        for (Lesson lesson : lessons) {
            int count = lesson.bookings.size();
            List<Integer> ratings = new ArrayList<>();
            for (Booking b : lesson.bookings) {
                if (b.getRating() != null) ratings.add(b.getRating());
            }

            double avg = ratings.isEmpty() ? 0 : ratings.stream().mapToInt(r -> r).average().orElse(0);
            System.out.printf("%s - %s %s %s: %d students, Avg Rating: %.2f%n",
                    lesson.getDate(), lesson.day, lesson.getTimeSlot(), lesson.getSubject(), count, avg);
        }
    }

    public void reportHighestIncomeSubject() {
        Map<String, Double> income = new HashMap<>();
        for (Lesson lesson : lessons) {
            for (Booking b : lesson.bookings) {
                if (List.of("booked", "attended").contains(b.getStatus())) {
                    income.merge(lesson.getSubject(), lesson.price, Double::sum);
                }
            }
        }
        if (!income.isEmpty()) {
            String maxSubject = Collections.max(income.entrySet(), Map.Entry.comparingByValue()).getKey();
            System.out.println("\n--- Highest Income Subject ---");
            System.out.printf("%s: £%.2f%n", maxSubject, income.get(maxSubject));
        }
    }

    public List<Lesson> getAllLessons() {
        return lessons;
    }

    public void attendLesson(Lesson l1) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'attendLesson'");
    }
}
