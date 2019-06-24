package tma.tft.phat.ss.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tma.tft.phat.ss.entities.Student;
import tma.tft.phat.ss.repositories.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentRepository studentRepository;

    @Override
    public Iterable<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public Student findById(String studentId) {
        return studentRepository.findById(studentId).get();
    }

    @Override
    public void save(Student student) {
        studentRepository.save(student);
    }

    @Override
    public void deleteStudent(String studentId) {
        studentRepository.deleteById(studentId);
    }

    @Override
    public <T> T findByName(String name, Class<T> type) {
        return studentRepository.findByName(name, type);
    }

}
