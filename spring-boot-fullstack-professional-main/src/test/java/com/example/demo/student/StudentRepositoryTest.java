package com.example.demo.student;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @Test
    void selectExistsEmail() {
        //given
        Student student = new Student("test","test@gmail.com",Gender.MALE);
        studentRepository.save(student);
        //when
        boolean expected = studentRepository.selectExistsEmail("nafiuli131@gmail.com");
        //then
        assertThat(expected).isTrue();
    }

    @Test
    void checkExistsEmail() {
        //given
        Student student = new Student("nafiul","nafiuli131@gmail.com",Gender.MALE);
        //when
        boolean expected = studentRepository.selectExistsEmail(student.getEmail());
        //then
        assertThat(expected).isTrue();
    }


}