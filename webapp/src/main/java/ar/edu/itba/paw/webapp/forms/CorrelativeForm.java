package ar.edu.itba.paw.webapp.forms;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class CorrelativeForm {

    @Min(1)
    @NotNull
    private Integer courseId;

    @NotBlank
    private String courseName;

    @Min(1)
    @NotNull
    private Integer correlativeId;

    @NotBlank
    private String correlativeName;

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName.trim();
    }

    public Integer getCorrelativeId() {
        return correlativeId;
    }

    public void setCorrelativeId(Integer correlativeId) {
        this.correlativeId = correlativeId;
    }

    public String getCorrelativeName() {
        return correlativeName;
    }

    public void setCorrelativeName(String correlativeName) {
        this.correlativeName = correlativeName.trim();
    }
}
