// src/test/java/com/ebtuition/model/LessonTest.java
package com.ebtuition.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

public class LessonTest {

    @Test
    void testLessonCapacity() {
        Lesson lesson = new Lesson("Math", "Saturday", "Morning", 30.0, LocalDate.now());

        Student student1 = new Student("A", "M", LocalDate.of(2011, 1, 1), "", "", 1);
        Student student2 = new Student("B", "F", LocalDate.of(2012, 2, 2), "", "", 2);
        Student student3 = new Student("C", "M", LocalDate.of(2013, 3, 3), "", "", 3);
        Student student4 = new Student("D", "F", LocalDate.of(2014, 4, 4), "", "", 4);

        assertTrue(lesson.addStudent(student1));
        assertTrue(lesson.addStudent(student2));
        assertTrue(lesson.addStudent(student3));
        assertTrue(lesson.addStudent(student4));

        Student student5 = new Student("E", "M", LocalDate.of(2015, 5, 5), "", "", 5);
        assertFalse(lesson.addStudent(student5));
    }
}
