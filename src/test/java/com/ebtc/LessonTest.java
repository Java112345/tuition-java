package test.java.com.ebtc;




import java.time.LocalDate;
import java.time.LocalTime;

import main.java.com.ebtuition.model.Lesson;
import main.java.com.ebtuition.model.Student;

public class LessonTest {

    @Test
    void testLessonCapacity() {
        Lesson lesson = new Lesson("Math", 30.0, LocalDate.now(), LocalTime.of(10, 0));
        Student student1 = new Student("A", "M", LocalDate.of(2011, 1, 1), "", "");
        Student student2 = new Student("B", "F", LocalDate.of(2012, 2, 2), "", "");
        Student student3 = new Student("C", "M", LocalDate.of(2013, 3, 3), "", "");
        Student student4 = new Student("D", "F", LocalDate.of(2014, 4, 4), "", "");

        assertTrue(lesson.addStudent(student1));
        assertTrue(lesson.addStudent(student2));
        assertTrue(lesson.addStudent(student3));
        assertTrue(lesson.addStudent(student4));

        Student student5 = new Student("E", "M", LocalDate.of(2015, 5, 5), "", "");
        assertFalse(lesson.addStudent(student5));
    }

    private void assertFalse(Object student) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'assertFalse'");
    }

    private void assertTrue(Object student) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'assertTrue'");
    }
}
