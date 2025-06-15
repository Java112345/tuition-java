package test.java.com.ebtc;

import main.java.com.ebtuition.model.Student;

public class StudentTest {

    @Test
    void testStudentCreation() {
        Student student = new Student("Alice", "Female", "2010-05-14", "123 Street", "9876543210");
        assertEquals("Alice", student.getName());
        assertEquals("9876543210", student.getEmergencyContact());
    }

    private void assertEquals(String string, Object emergencyContact) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'assertEquals'");
    }
}
