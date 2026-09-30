package placement_management_system.controller;
import org.springframework.security.crypto.password.PasswordEncoder;

import placement_management_system.model.Student;
import placement_management_system.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Get all students
    @GetMapping
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // Get one student by ID
    @GetMapping("/{id}")
    public Optional<Student> getStudentById(@PathVariable Long id) {
        return studentRepository.findById(id);
    }

    // Add a new student
    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        student.setPassword(passwordEncoder.encode(student.getPassword()));
        return studentRepository.save(student);
    }

    // Update a student
    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable Long id, @RequestBody Student updatedStudent) {
        return studentRepository.findById(id).map(student -> {
            student.setName(updatedStudent.getName());
            student.setEmail(updatedStudent.getEmail());
            student.setBranch(updatedStudent.getBranch());
            student.setCgpa(updatedStudent.getCgpa());
            student.setSkills(updatedStudent.getSkills());
            return studentRepository.save(student);
        }).orElseGet(() -> {
            updatedStudent.setStudentId(id);
            return studentRepository.save(updatedStudent);
        });
    }

    // Delete a student
    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentRepository.deleteById(id);
        return "Student deleted successfully";
    }
}