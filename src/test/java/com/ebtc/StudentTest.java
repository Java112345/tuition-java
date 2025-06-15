package test.java.com.ebtc;



import java.time.LocalDate;

import main.java.com.ebtuition.model.Student;

public class StudentTest {

    @Test
    void testStudentDetails() {
        Student s = new Student("Emma", "Female", LocalDate.of(2012, 3, 14),
                "221B Baker St", "9999888877");

        assertEquals("Emma", s.getName());
        assertEquals("Female", s.getGender());
        assertEquals(LocalDate.of(2012, 3, 14), s.getDob());
        assertEquals("221B Baker St", s.getAddress());
        assertEquals("9999888877", s.getEmergencyContact());
    }

    private void assertEquals(String string, Object address) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'assertEquals'");
    }

    private void assertEquals(LocalDate string, Object gender) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'assertEquals'");
    }
}
