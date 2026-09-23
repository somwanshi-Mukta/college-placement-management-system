package placement_management_system.controller;

import placement_management_system.model.Application;
import placement_management_system.model.Student;
import placement_management_system.model.Company;
import placement_management_system.repository.ApplicationRepository;
import placement_management_system.repository.StudentRepository;
import placement_management_system.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @PostMapping("/apply")
    public ResponseEntity<?> applyToCompany(@RequestParam Long studentId, @RequestParam Long companyId) {

        Optional<Student> studentOpt = studentRepository.findById(studentId);
        Optional<Company> companyOpt = companyRepository.findById(companyId);

        if (studentOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Student not found");
        }
        if (companyOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Company not found");
        }

        Student student = studentOpt.get();
        Company company = companyOpt.get();

        if (student.getCgpa() < company.getMinCgpa()) {
            Map<String, Object> response = new HashMap<>();
            response.put("eligible", false);
            response.put("reason", "CGPA too low. Required: " + company.getMinCgpa() + ", Your CGPA: " + student.getCgpa());
            return ResponseEntity.ok(response);
        }

        if (company.getEligibleBranches() != null &&
                !company.getEligibleBranches().toLowerCase().contains(student.getBranch().toLowerCase())) {
            Map<String, Object> response = new HashMap<>();
            response.put("eligible", false);
            response.put("reason", "Your branch (" + student.getBranch() + ") is not eligible for this company.");
            return ResponseEntity.ok(response);
        }

        Application application = new Application();
        application.setStudentId(studentId);
        application.setCompanyId(companyId);
        application.setStatus("Applied");
        application.setAppliedDate(LocalDate.now().toString());

        Application saved = applicationRepository.save(application);

        Map<String, Object> response = new HashMap<>();
        response.put("eligible", true);
        response.put("message", "Application submitted successfully!");
        response.put("application", saved);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }

    @GetMapping("/student/{studentId}")
    public List<Application> getApplicationsByStudent(@PathVariable Long studentId) {
        return applicationRepository.findAll().stream()
                .filter(app -> app.getStudentId().equals(studentId))
                .toList();
    }

    @PutMapping("/{id}/status")
    public Application updateStatus(@PathVariable Long id, @RequestParam String status) {
        Application application = applicationRepository.findById(id).orElseThrow();
        application.setStatus(status);
        return applicationRepository.save(application);
    }

    @DeleteMapping("/{id}")
    public String deleteApplication(@PathVariable Long id) {
        applicationRepository.deleteById(id);
        return "Application withdrawn successfully";
    }
}