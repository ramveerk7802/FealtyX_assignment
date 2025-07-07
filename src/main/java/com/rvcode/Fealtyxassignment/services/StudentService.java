package com.rvcode.Fealtyxassignment.services;


import com.rvcode.Fealtyxassignment.entities.Student;
import com.rvcode.Fealtyxassignment.exceptionHandeller.StudentException;
import com.rvcode.Fealtyxassignment.services.clients.OllamaFiegnClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentService {

    @Autowired
    private OllamaFiegnClient ollamaFiegnClient;
    private int currentId=1;
    private Map<Integer, Student> studentMap = new HashMap<>();

    public List<Student> getAllStudents(){
        try {
            return studentMap.values().stream().toList();
        }catch (Exception e){
            throw e;
        }
    }

    public Student addNewStudent(Student student){
        try {
            student.setId(currentId);
            studentMap.put(currentId,student);
            currentId++;
            return student;
        }catch (Exception e){
            throw e;
        }

    }

    public Student getStudentById(int id){
        try {
            if(!studentMap.containsKey(id)){
                throw new StudentException("Student not found with id :"+id);
            }
            return studentMap.get(id);
        }catch (Exception e){
            throw e;
        }
    }

    public Student updateStudentById(int id, Student student){
        try {
            if(!studentMap.containsKey(id)){
                throw new StudentException("Student not found with id : "+ id);
            }
            Student dbStudent = studentMap.get(id);
            if(!student.getName().isBlank())
                dbStudent.setName(student.getName());
            if(student.getAge().intValue()!=0 && student.getAge().intValue()>=18)
                dbStudent.setAge(student.getAge());

            if(!student.getEmail().isBlank())
                dbStudent.setEmail(student.getEmail());
            studentMap.put(dbStudent.getId(),dbStudent);
            return dbStudent;


        }catch (Exception e) {
            throw e;
        }
    }


    public Student deleteStudentById(int id){
        try {
            if(!studentMap.containsKey(id))
                throw new StudentException("Student not found with id:"+id);
            Student dbStudent = studentMap.get(id);
            return dbStudent;

        }catch (Exception e){
            throw e;
        }
    }



    public String getSummaryOfStudentById(int id){
        try {
            if(!studentMap.containsKey(id))
                throw new StudentException("Student not found with id : "+id);
            Student dbStudent = studentMap.get(id);
            if(dbStudent.getSummary()!=null && !dbStudent.getSummary().isBlank() ){
                return dbStudent.getSummary();
            }



            String prompt = """
                            You are an AI assistant that writes formal summaries of student profiles for academic records.
                            Your task is to generate a well-structured summary in 4 to 5 full sentences.
                            Use a professional tone. Include the student’s name, age, email, and any other observations based on the provided data.

                            Student Details:
                            - ID: %d
                            - Name: %s
                            - Age: %d
                            - Email: %s

                            Write a paragraph summarizing this student's profile.
                            """.formatted(
                            dbStudent.getId(),
                            dbStudent.getName(),
                            dbStudent.getAge(),
                            dbStudent.getEmail()
                             );


            Map<String,Object> request = new HashMap<>();
            request.put("model","llama3.2");
            request.put("prompt",prompt);
            request.put("stream",false);
            Map<String,Object> response = ollamaFiegnClient.generatedText(request);
            if(response.isEmpty())
                throw new StudentException("Failed to generate summary via ollama");

            String summary = response.get("response").toString();
            dbStudent.setSummary(summary);
            studentMap.put(dbStudent.getId(), dbStudent);
            return summary;
        }
        catch (Exception e) {
            throw e;
        }
    }


}
