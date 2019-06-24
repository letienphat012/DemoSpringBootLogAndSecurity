package tma.tft.phat.ss.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import tma.tft.phat.ss.entities.Student;
import tma.tft.phat.ss.entities.dto.StudentView;

public interface StudentRepository extends CrudRepository<Student, String> {

    <T> T findByName(String name, Class<T> type);

//	List<StudentView> findAll();

    @Query(value = "SELECT s from Student s")
    <T> List<T> getAll(Class<T> type);
}
