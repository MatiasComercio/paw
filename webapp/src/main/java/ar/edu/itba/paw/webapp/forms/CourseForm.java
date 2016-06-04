package ar.edu.itba.paw.webapp.forms;

import ar.edu.itba.paw.models.Course;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CourseForm {
    @Min(1)
    private Integer id;

    @NotNull
    @Size(min=2, max=50)
    private String name;

    @NotNull
    @Min(1)
    @Max(20)
    private Integer credits;

    @NotNull
    @Min(1)
    private Integer semester;

    public CourseForm(){}

    public Course build(){
        return new Course.Builder(id).name(name).credits(credits).semester(semester).build();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.trim();
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    public void loadFromCourse(final Course course){
        this.id = course.getId();
        this.name = course.getName();
        this.credits = course.getCredits();
        this.semester = course.getSemester();
    }
}
