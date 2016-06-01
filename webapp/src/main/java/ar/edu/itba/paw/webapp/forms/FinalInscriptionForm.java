package ar.edu.itba.paw.webapp.forms;

import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.FinalInscription;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class FinalInscriptionForm {

    //@NotNull
    //private Integer studentDocket;

    @Min(1)
    private Integer courseId;

    @NotBlank
    private String courseName;

    //finalInscriptionId
    @NotNull
    private int id;

    @NotNull
    private int vacancy;

    @NotNull
    private LocalDateTime finalExamDate;

//    public FinalInscription build(){
//        return new FinalInscription(id).Builder(vacancy).build();
//    }


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
        this.courseName = courseName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVacancy() {
        return vacancy;
    }

    public void setVacancy(int vacancy) {
        this.vacancy = vacancy;
    }

    public LocalDateTime getFinalExamDate() {
        return finalExamDate;
    }

    public void setFinalExamDate(LocalDateTime finalExamDate) {
        this.finalExamDate = finalExamDate;
    }
}
