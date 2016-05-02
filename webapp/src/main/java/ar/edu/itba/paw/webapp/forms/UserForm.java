package ar.edu.itba.paw.webapp.forms;

import ar.edu.itba.paw.models.Course;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class UserForm {

    @Min(1)
    @NotNull
    private Integer dni;

    public Integer getDni() {
        return dni;
    }

    public void setDni(Integer dni) {
        this.dni = dni;
    }
}
