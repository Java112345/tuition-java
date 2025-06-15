package test.java.com.ebtc;



import java.time.LocalDate;
import java.time.LocalTime;

import main.java.com.ebtuition.model.Booking;
import main.java.com.ebtuition.model.Lesson;
import main.java.com.ebtuition.model.Student;

public class BookingTest {

    @Test
    void testBookingAndCancel() {
        Student student = new Student("Liam", "Male", LocalDate.of(2010, 10, 10), "", "");
        Lesson lesson = new Lesson("English", 25.0, LocalDate.now(), LocalTime.of(14, 0));
        Booking booking = new Booking(student, lesson);

        assertEquals("booked", booking.getStatus());

        booking.checkIn();
        assertEquals("attended", booking.getStatus());

        booking.addReview("Great class", 5);
        assertEquals(5, booking.getRating());
        assertEquals("Great class", booking.getReview());
    }

    private void assertEquals(int i, Integer rating) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'assertEquals'");
    }

    private void assertEquals(String string, Object status) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'assertEquals'");
    }
}
