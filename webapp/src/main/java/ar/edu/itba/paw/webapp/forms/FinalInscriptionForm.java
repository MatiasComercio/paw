package ar.edu.itba.paw.webapp.forms;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

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

    @NotBlank
    private String courseCode;

    //finalInscriptionId
    @NotNull
    private int id;

    @NotNull
    private int vacancy;

    //pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX"
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
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

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }
}
