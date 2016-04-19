package ar.edu.itba.paw.webapp.forms;

import ar.edu.itba.paw.models.Course;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;

public class CourseForm {

    @NotNull
    @Length
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
