package ar.edu.itba.paw.webapp.forms;


import ar.edu.itba.paw.models.Grade;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class GradeForm {
    @NotNull
    @Min(1)
    private Integer docket;

    @NotNull
    @Min(1)
    private Integer courseId;

    @NotBlank
    private String courseName;

    @NotNull
    @Min(0)
    @Max(10)
    private BigDecimal grade;

    private BigDecimal oldGrade;

    private Timestamp modified;

    public Grade build() {
        return new Grade.Builder(docket, courseId, grade).modified(modified).build();
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
        this.courseName = courseName;
    }

    public BigDecimal getGrade() {
        return grade;
    }

    public void setGrade(BigDecimal grade) {
        this.grade = grade;
    }

    public Timestamp getModified() {
        return modified;
    }

    public void setModified(Timestamp modified) {
        this.modified = modified;
    }

    public void loadFromGrade(final Grade grade){
        this.docket = grade.getStudentDocket();
        this.courseId = grade.getCourseId();
        this.grade = grade.getGrade();
    }

    public BigDecimal getOldGrade() {
        return oldGrade;
    }

    public void setOldGrade(BigDecimal oldGrade) {
        this.oldGrade = oldGrade;
    }
}
