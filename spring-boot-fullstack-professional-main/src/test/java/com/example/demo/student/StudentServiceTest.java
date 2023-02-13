package com.example.demo.student;

import com.example.demo.student.exception.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ObjectUtils;

import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock private StudentRepository studentRepository;
    private StudentService studentService;

    @BeforeEach
    void setUp() {
        studentService = new StudentService(studentRepository);
    }
    @Test
    void getAllStudents() {
        studentService.getAllStudents();
        verify(studentRepository).findAll();
    }

    @Test
    void addStudent() {
        //given
        Student student = new Student("Nafi","nafi@gmail.com",Gender.MALE);
        //when
        studentService.addStudent(student);

        //then
        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(studentRepository).save(studentArgumentCaptor.capture());
        Student studentArgumentCaptorValue = studentArgumentCaptor.getValue();
        assertThat(studentArgumentCaptorValue).isEqualTo(student);
    }

    @Test
    @Disabled
    void deleteStudent() {
    }

    @Test
    void thrownEmailsIsTaken(){
        //given
        Student student = new Student("Nafi","nafi@gmail.com",Gender.MALE);
        given(studentRepository.selectExistsEmail(student.getEmail())).willReturn(true);
        //then
        assertThatThrownBy(()->studentService.addStudent(student)).isInstanceOf(BadRequestException.class)
                .hasMessageContaining( "Email " + student.getEmail() + " taken");
    }

    @Test
    void emptyNameIsTrue() {
        //given
        Student student = new Student();
        //when
        boolean expected = ObjectUtils.isEmpty(student.getName());
        //then
        assertThat(expected).isTrue();
    }
}