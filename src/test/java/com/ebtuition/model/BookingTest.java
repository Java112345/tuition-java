package com.ebtuition.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDate;

public class BookingTest {

    @Test
    void testBookingAndCancel() {
        Student student = new Student("Liam", "Male", LocalDate.of(2010, 10, 10), "", "",1);
    Lesson lesson = new Lesson("English", "Monday", "Afternoon", 25.0, LocalDate.now()); // ✅ define lesson
        Booking booking = new Booking(student, lesson);

        assertEquals("booked", booking.getStatus());

        booking.checkIn();
        assertEquals("attended", booking.getStatus());

        booking.addReview("Great class", 5);
        assertEquals(5, booking.getRating());
        assertEquals("Great class", booking.getReview());
    }

   
}
