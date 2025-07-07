package com.rvcode.Fealtyxassignment.services;


import com.rvcode.Fealtyxassignment.component.Validator;
import com.rvcode.Fealtyxassignment.entities.Student;
import com.rvcode.Fealtyxassignment.exceptionHandeller.MyCustomException;
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

    @Autowired
    private Validator validator;
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
            if(student.getAge()<18)
                throw new MyCustomException("Age must be greater than 18 years");
            else if (!validator.emailValidator(student.getEmail())) {
                throw new MyCustomException("Please enter the valid email");
            }
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
            if(student.getName()!=null && !student.getName().isBlank())
                dbStudent.setName(student.getName());
            if(student.getAge()!=null && student.getAge().intValue()>=18)
                dbStudent.setAge(student.getAge());

            if(student.getEmail()!=null && !student.getEmail().isBlank())
                dbStudent.setEmail(student.getEmail());
            studentMap.put(id,dbStudent);
            return studentMap.get(id);

        }catch (Exception e) {
            throw e;
        }
    }


    public String deleteStudentById(int id){
        try {
            if(!studentMap.containsKey(id))
                throw new StudentException("Student not found with id:"+id);
            studentMap.remove(id);
            return "Student data deleted successfully with id: "+ id;

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
                Summarize this student’s profile in 4–5 professional sentences: 
                Name: %s, Age: %d, Email: %s, ID: %d
                """.formatted(
                    dbStudent.getName(),
                    dbStudent.getAge(),
                    dbStudent.getEmail(),
                    dbStudent.getId()
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
