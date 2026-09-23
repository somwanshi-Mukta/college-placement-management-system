package placement_management_system.model;

import jakarta.persistence.*;

@Entity
@Table(name = "results")
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resultId;

    private Long studentId;
    private Long companyId;
    private String status; // Selected, Rejected
    private String announcedDate;

    public Result() {
    }

    public Result(Long studentId, Long companyId, String status, String announcedDate) {
        this.studentId = studentId;
        this.companyId = companyId;
        this.status = status;
        this.announcedDate = announcedDate;
    }

    public Long getResultId() {
        return resultId;
    }

    public void setResultId(Long resultId) {
        this.resultId = resultId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAnnouncedDate() {
        return announcedDate;
    }

    public void setAnnouncedDate(String announcedDate) {
        this.announcedDate = announcedDate;
    }
}
