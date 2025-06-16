package com.ebtuition.service;
import org.junit.jupiter.api.Test;
import com.ebtuition.model.Student;
import com.ebtuition.model.Booking;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;


import java.time.LocalDate;

class TuitionCentreTest {

    TuitionCentre centre;
    LocalDate startDate;

    @BeforeEach
    void setUp() {
        centre = new TuitionCentre();
centre.addStudent(new Student("Alice", "Female", LocalDate.of(2010, 1, 1), "123 Street", "9876543210", 1));
        startDate = LocalDate.of(2024, 6, 1);
        centre.createLessons(startDate);
    }

    @Test
    void testBookLesson_Success() {
        Booking booking = centre.bookLesson(1, "Math", startDate, "Morning");
        assertNotNull(booking, "Booking should be successful and not null");
        assertEquals("booked", booking.getStatus());
        assertEquals("Math", booking.getLesson().subject);
        assertEquals("Morning", booking.getLesson().timeSlot);
    }

   

    @Test
    void testBookLesson_TimeConflict() {
        // First booking
        centre.bookLesson(1, "Math", startDate, "Morning");

        // Second booking at the same time but different subject
        Booking conflictBooking = centre.bookLesson(1, "English", startDate, "Morning");

        assertNull(conflictBooking, "Booking should fail due to time conflict");
    }

   

    @Test
    void testCancelBooking() {
        Booking booking = centre.bookLesson(1, "English", startDate, "Afternoon");
        assertNotNull(booking);

        centre.cancelBooking(booking);
        assertEquals("cancelled", booking.getStatus());
    }

   
    @Test
    void testChangeBooking() {
        Booking oldBooking = centre.bookLesson(1, "Math", startDate, "Morning");
        assertNotNull(oldBooking);

        LocalDate newDate = startDate.plusDays(1); // Sunday
        Booking newBooking = centre.changeBooking(oldBooking, newDate, "Afternoon");

        assertNotNull(newBooking);
        assertEquals("Math", newBooking.getLesson().subject);
        assertEquals("Afternoon", newBooking.getLesson().timeSlot);
    }

    @Test
    void testAttendLesson() {
        Booking booking = centre.bookLesson(1, "Verbal Reasoning", startDate, "Morning");
        assertNotNull(booking);

        centre.attendLesson(booking);
        assertEquals("attended", booking.getStatus());
    }

    @Test
    void testReviewLesson() {
        Booking booking = centre.bookLesson(1, "Non-verbal Reasoning", startDate, "Afternoon");
        centre.attendLesson(booking);

        centre.reviewLesson(booking, "Great lesson", 5);
        assertEquals("Great lesson", booking.getReview());
        assertEquals(5, booking.getRating());
    }

   
}
