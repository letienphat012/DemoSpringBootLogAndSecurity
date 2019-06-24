package tma.tft.phat.ss.repository;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit4.SpringRunner;

import tma.tft.phat.ss.entities.dto.StudentView;
import tma.tft.phat.ss.repositories.StudentRepository;

@DataJpaTest
@RunWith(SpringRunner.class)
@Sql(scripts = "/projection-insert-data.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/projection-clear-data.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
public class JpaIntegrationProjectionTest {

    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void whenUsingClosedProjections_thenViewWithRequirementPropertiesIsReturn() {
//		StudentDto studentDto = studentRepository.findByName("John", StudentDto.class);
//		StudentView studentView = studentRepository.findByName("John", StudentView.class);
//		assertEquals("John", studentView.getName());
//		assertEquals("0123456789", studentView.getPhoneNumber());

//		StudentDto studentDto = studentRepository.findByName("John", StudentDto.class);
//		assertEquals("John", studentDto.getName());
//		assertEquals("0123456789", studentDto.getPhoneNumber());

        List<StudentView> studentViews = studentRepository.getAll(StudentView.class);
        for (StudentView studentView : studentViews) {
            System.out.println(studentView.getPhoneNumber());
            System.out.println(studentView.getName());
        }
    }
}
