package ar.edu.itba.paw.webapp.forms;

import ar.edu.itba.paw.models.Course;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class CourseForm {


    @NotNull
    @Min(30)
    private int id;

    @NotNull
    private String name;

    @NotNull
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
