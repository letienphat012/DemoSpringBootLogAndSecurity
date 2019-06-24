package tma.tft.phat.ss.services;

import tma.tft.phat.ss.entities.Student;

public interface StudentService {
    Iterable<Student> findAll();

    Student findById(String studentId);

    void save(Student student);

    void deleteStudent(String studentId);

    <T> T findByName(String name, Class<T> type);
}
