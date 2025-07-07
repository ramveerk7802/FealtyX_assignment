package com.rvcode.Fealtyxassignment.controllers;

import com.rvcode.Fealtyxassignment.entities.Student;
import com.rvcode.Fealtyxassignment.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;



    @GetMapping
    public ResponseEntity<?> getAllStudents(){
        List<Student> list = studentService.getAllStudents();
        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<?> addNewStudent(@RequestBody Student student){
        Student student1 = studentService.addNewStudent(student);
        return ResponseEntity.ok(student1);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable int id){
        return ResponseEntity.ok(studentService.getStudentById(id));
    }



    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudentById(@PathVariable int id,Student student){
        return ResponseEntity.ok(studentService.updateStudentById(id, student));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable int id){
        return ResponseEntity.ok(studentService.deleteStudentById(id));
    }

    @PostMapping("/{id}/summary")
    public ResponseEntity<?> getSummaryOfStudentById(@PathVariable int id){
        return ResponseEntity.ok(studentService.getSummaryOfStudentById(id));
    }
}
