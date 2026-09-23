package placement_management_system.controller;

import placement_management_system.model.Company;
import placement_management_system.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    @Autowired
    private CompanyRepository companyRepository;

    @GetMapping
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Company> getCompanyById(@PathVariable Long id) {
        return companyRepository.findById(id);
    }

    @PostMapping
    public Company addCompany(@RequestBody Company company) {
        return companyRepository.save(company);
    }

    @PutMapping("/{id}")
    public Company updateCompany(@PathVariable Long id, @RequestBody Company updatedCompany) {
        return companyRepository.findById(id).map(company -> {
            company.setCompanyName(updatedCompany.getCompanyName());
            company.setRole(updatedCompany.getRole());
            company.setPackageOffered(updatedCompany.getPackageOffered());
            company.setLocation(updatedCompany.getLocation());
            company.setMinCgpa(updatedCompany.getMinCgpa());
            company.setEligibleBranches(updatedCompany.getEligibleBranches());
            company.setRequiredSkills(updatedCompany.getRequiredSkills());
            company.setLastDate(updatedCompany.getLastDate());
            return companyRepository.save(company);
        }).orElseGet(() -> {
            updatedCompany.setCompanyId(id);
            return companyRepository.save(updatedCompany);
        });
    }

    @DeleteMapping("/{id}")
    public String deleteCompany(@PathVariable Long id) {
        companyRepository.deleteById(id);
        return "Company deleted successfully";
    }
}
