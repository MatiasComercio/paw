package ar.edu.itba.paw.webapp.forms;


import ar.edu.itba.paw.models.Grade;

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

    @NotNull
    @Min(0)
    @Max(10)
    private BigDecimal grade;

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
}
