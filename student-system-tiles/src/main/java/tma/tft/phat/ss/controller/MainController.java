package tma.tft.phat.ss.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import tma.tft.phat.ss.domain.User;
import tma.tft.phat.ss.entities.Student;
import tma.tft.phat.ss.services.CustomUserDetailService;
import tma.tft.phat.ss.services.StudentService;

@Controller
public class MainController {

//    private static final Logger logger = LoggerFactory.getLogger(MainController.class);
    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(MainController.class);
    
    @Autowired
    StudentService studentService;

    @Autowired
    CustomUserDetailService userDetailService;

    @GetMapping(value = { "/" })
    public String index() {
        return "homePage";
    }

    @GetMapping(value = { "/admin" })
    public String admin() {
        return "adminPage";
    }

    @GetMapping(value = "/403")
    public String accessDenied() {
        return "403";
    }

    @GetMapping("/login")
    public String getLogin(Model model) {
        
        logger.trace("This is TRACE");
        logger.debug("This is DEBUG");
        logger.info("This is INFO");
        logger.warn("This is WARN");
        logger.error("This is ERROR");
        logger.warn("This is WARN");
        logger.error("This is ERROR");
        logger.warn("This is WARN");
        logger.error("This is ERROR");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(httpServletRequest, httpServletResponse, auth);
        }
        return "redirect:/login?logout";
    }

    @GetMapping(value = "/student")
    public String studentPage(Model model) {
        
        Iterable<Student> students = studentService.findAll();
        model.addAttribute("students", students);
        return "studentPage";
    }

    @GetMapping(value = "/student/{id}/edit")
    public String showFormEditStudent(@PathVariable(name = "id") String studentId, Model model) {
        Student student = studentService.findById(studentId);
        model.addAttribute("student", student);
        return "studentEditPage";
    }

    @GetMapping(value = "/student/{id}/delete")
    public String deleteStudent(@PathVariable(name = "id") String studentId, Model model,
            RedirectAttributes redirectAttributes) {
        studentService.deleteStudent(studentId);
        redirectAttributes.addFlashAttribute("success", "Delete student success");
        return "redirect:/student";
    }

    @PostMapping(value = "/student/update")
    public String updateStudent(@Valid @ModelAttribute(name = "student") Student student, BindingResult results,
            Model model, RedirectAttributes redirectAttributes) {
        System.out.println(student.getId());
        if (results.hasErrors()) {
            System.out.println(results.getAllErrors().get(0).getCode());
            return "studentEditPage";
        }
        studentService.save(student);

        return "redirect:/student";
    }

    @GetMapping(value = "/student/addStudent")
    public String addStudent(Model model) {
        Student student = new Student();
        model.addAttribute("student", student);
        return "studentEditPage";
    }

    @GetMapping(value = "/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("newUser", new User());
        return "registerPage";
    }

    @PostMapping(value = "/registerProcess")
    public String registerUser(@Valid User user, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "registerPage";
        }

        userDetailService.registerUser(user);
        redirectAttributes.addFlashAttribute("success", "Register account successful");
        return "redirect:/login";
    }
}
