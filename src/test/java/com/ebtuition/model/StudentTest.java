package com.ebtuition.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;



public class StudentTest {
@Test
    void testStudentDetails() {
    Student s = new Student("Emma", "Female", LocalDate.of(2012, 3, 14),
            "221B Baker St", "9999888877",1);

    assertEquals("Emma", s.getName());
    assertEquals("Female", s.getGender());
    assertEquals(LocalDate.of(2012, 3, 14), s.getDob());
    assertEquals("221B Baker St", s.getAddress());
    assertEquals(1, s.getId());
}

   

   
}
