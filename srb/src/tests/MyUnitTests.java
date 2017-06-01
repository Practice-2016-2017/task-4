import com.roi.config.DataConfig;
import com.roi.config.MvcConfig;
import com.roi.config.SecurityConfig;
import com.roi.controller.admin.EditStudentController;
import com.roi.entity.Student;
import com.roi.entity.Year;
import com.roi.repository.StudentRepository;
import com.roi.repository.YearRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Alexander on 01.06.2017.
 */
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MvcConfig.class,DataConfig.class,SecurityConfig.class})

public class MyUnitTests {
    private static final int UNKNOWN_ID = Integer.MAX_VALUE;

    private MockMvc mockMvc;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private YearRepository yearRepository;

    @InjectMocks
    private EditStudentController editStudentController;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(editStudentController)
                .build();
    }

    @Test
    public void testAddingStudent() throws Exception {
        System.out.println("Eee Testtt");

        Year mockyear = yearRepository.findByName(3);
        Student mockstudent = new Student(110000,"stuna","Student Name",mockyear);
        doReturn(null).when(studentRepository).save(mockstudent);

        mockMvc.perform(
                post("/admin/studentsList/addStudent")
                        .param("studentName", "Student Name")
                        .param("login", "110000")
                        .param("password", "stuna")
                        .param("year", "3"))
                .andExpect(model().attributeExists("message"))
                .andExpect(status().isOk());
    }

    @Test
    public void testStudentExists() throws Exception {
        System.out.println("Wow Tttest");

        Year mockyear = yearRepository.findByName(3);
        Student mockstudent = new Student(110000,"stuna","Student Name",mockyear);
        doReturn(mockstudent).when(studentRepository).findByLogin(110000);

        mockMvc.perform(
                post("/admin/studentsList/addStudent")
                        .param("studentName", "Student Name")
                        .param("login", "110000")
                        .param("password", "stuna")
                        .param("year", "3"))
                .andExpect(model().attributeExists("error"))
                .andExpect(status().isOk());
    }

    @Test
    public void testEditNonExistingStudent() throws Exception {
        System.out.println("Mmm Tessst");

        //Year mockyear = yearRepository.findByName(3);
        //Student mockstudent = new Student(110000,"stuna","Student Name",mockyear);
        doReturn(false).when(studentRepository).exists(1);

        mockMvc.perform(
                post("/admin/studentsList/edit/1")
                        .param("studentName", "Student Name")
                        .param("login", "110000")
                        .param("password", "stuna")
                        .param("year", "3"))
                //.andExpect(model().attributeExists("error"))
                .andExpect(forwardedUrl("error"))
                .andExpect(status().isOk());
    }
}
