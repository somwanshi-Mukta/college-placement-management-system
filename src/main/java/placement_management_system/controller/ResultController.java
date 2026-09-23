package placement_management_system.controller;

import placement_management_system.model.Result;
import placement_management_system.repository.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/results")
public class ResultController {

    @Autowired
    private ResultRepository resultRepository;

    // Publish a result (admin action)
    @PostMapping
    public Result publishResult(@RequestBody Result result) {
        if (result.getAnnouncedDate() == null) {
            result.setAnnouncedDate(LocalDate.now().toString());
        }
        return resultRepository.save(result);
    }

    // Get all results
    @GetMapping
    public List<Result> getAllResults() {
        return resultRepository.findAll();
    }

    // Get results for a specific student
    @GetMapping("/student/{studentId}")
    public List<Result> getResultsByStudent(@PathVariable Long studentId) {
        return resultRepository.findAll().stream()
                .filter(r -> r.getStudentId().equals(studentId))
                .toList();
    }

    // Get results for a specific company
    @GetMapping("/company/{companyId}")
    public List<Result> getResultsByCompany(@PathVariable Long companyId) {
        return resultRepository.findAll().stream()
                .filter(r -> r.getCompanyId().equals(companyId))
                .toList();
    }

    // Delete a result (rare, but useful for corrections)
    @DeleteMapping("/{id}")
    public String deleteResult(@PathVariable Long id) {
        resultRepository.deleteById(id);
        return "Result deleted successfully";
    }
}
