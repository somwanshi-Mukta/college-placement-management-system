package placement_management_system.controller;

import placement_management_system.model.Interview;
import placement_management_system.repository.InterviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/interviews")
public class InterviewController {

    @Autowired
    private InterviewRepository interviewRepository;

    // Schedule a new interview (admin action)
    @PostMapping
    public Interview scheduleInterview(@RequestBody Interview interview) {
        return interviewRepository.save(interview);
    }

    // Get all interviews
    @GetMapping
    public List<Interview> getAllInterviews() {
        return interviewRepository.findAll();
    }

    // Get interviews for a specific student
    @GetMapping("/student/{studentId}")
    public List<Interview> getInterviewsByStudent(@PathVariable Long studentId) {
        return interviewRepository.findAll().stream()
                .filter(i -> i.getStudentId().equals(studentId))
                .toList();
    }

    // Get interviews for a specific company
    @GetMapping("/company/{companyId}")
    public List<Interview> getInterviewsByCompany(@PathVariable Long companyId) {
        return interviewRepository.findAll().stream()
                .filter(i -> i.getCompanyId().equals(companyId))
                .toList();
    }

    // Update interview details
    @PutMapping("/{id}")
    public Interview updateInterview(@PathVariable Long id, @RequestBody Interview updatedInterview) {
        return interviewRepository.findById(id).map(interview -> {
            interview.setInterviewDate(updatedInterview.getInterviewDate());
            interview.setInterviewTime(updatedInterview.getInterviewTime());
            interview.setVenue(updatedInterview.getVenue());
            return interviewRepository.save(interview);
        }).orElseGet(() -> {
            updatedInterview.setInterviewId(id);
            return interviewRepository.save(updatedInterview);
        });
    }

    // Cancel/delete an interview
    @DeleteMapping("/{id}")
    public String deleteInterview(@PathVariable Long id) {
        interviewRepository.deleteById(id);
        return "Interview cancelled successfully";
    }
}
