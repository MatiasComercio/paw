package ar.edu.itba.paw.models.forms;

import ar.edu.itba.paw.models.Course;

public class CourseForm {

    private int id;
    private String name;
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
