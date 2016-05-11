package ar.edu.itba.paw.webapp.forms;

import ar.edu.itba.paw.models.Course;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class UserForm {
    @Digits(integer=8, fraction=0)
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
