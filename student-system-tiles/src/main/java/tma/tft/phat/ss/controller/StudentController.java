package tma.tft.phat.ss.controller;

import static org.springframework.http.ResponseEntity.ok;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tma.tft.phat.ss.services.StudentService;

@RestController
@RequestMapping("/v1/students")
public class StudentController {
    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);
    @Autowired
    private StudentService studentService;
    
    @GetMapping("")
    public ResponseEntity all(){
        
        return ok(studentService.findAll());
    }
    
}
