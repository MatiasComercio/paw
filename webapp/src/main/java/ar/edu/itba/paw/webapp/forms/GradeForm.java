package ar.edu.itba.paw.webapp.forms;


import ar.edu.itba.paw.models.Grade;
import ar.edu.itba.paw.models.users.Student;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class GradeForm {
    private Integer id;

    @NotNull
    @Min(1)
    private Integer docket;

    @NotNull
    @Min(1)
    private Integer courseId;

    @NotBlank
    private String courseCodId;

    @NotBlank
    private String courseName;

    @NotNull
    @Min(0)
    @Max(10)
    private BigDecimal grade;

    private BigDecimal oldGrade;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime modified;


    private Student student;

    public Grade build() {
        return new Grade.Builder(id, student, courseId, courseCodId, grade).modified(modified).build();
    }

    public Integer getDocket() {
        return docket;
    }

    public void setDocket(Integer docket) {
        this.docket = docket;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(final String courseName) {
        this.courseName = courseName.trim();
    }

    public BigDecimal getGrade() {
        return grade;
    }

    public void setGrade(BigDecimal grade) {
        this.grade = grade;
    }

    public LocalDateTime getModified() {
        return modified;
    }

    public void setModified(LocalDateTime modified) {
        this.modified = modified;
    }

    public void loadFromGrade(final Grade grade){
        this.id = grade.getId();
        this.docket = grade.getStudent().getDocket();
        this.courseId = grade.getCourseId();
        this.courseCodId = grade.getCourseCodId();
        this.grade = grade.getGrade();
        this.student = grade.getStudent();
    }

    public BigDecimal getOldGrade() {
        return oldGrade;
    }

    public void setOldGrade(BigDecimal oldGrade) {
        this.oldGrade = oldGrade;
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public void setStudent(final Student student) {
        this.student = student;
    }

    public Student getStudent() {
        return student;
    }

    public String getCourseCodId() {
        return courseCodId;
    }

    public void setCourseCodId(final String courseCodId) {
        this.courseCodId = courseCodId;
    }
}
