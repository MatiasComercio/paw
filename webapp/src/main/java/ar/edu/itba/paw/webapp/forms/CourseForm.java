package ar.edu.itba.paw.webapp.forms;

import ar.edu.itba.paw.models.Course;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CourseForm {

    @Min(1)
    @NotNull
    private int id;

    @NotNull
    @Size(min=2, max=50)
    private String name;

    @NotNull
    @Min(0)
    private int credits;

    public CourseForm(){}

    public Course build(){
        return new Course.Builder(id).name(name).credits(credits).build();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }
}
