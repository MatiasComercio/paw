package ar.edu.itba.paw.models;


import ar.edu.itba.paw.models.users.Student;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class FinalInscription {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "finalInscription_finalInscriptionid_seq")
    @SequenceGenerator(sequenceName = "finalInscription_finalInscriptionid_seq", name = "finalInscription_finalInscriptionid_seq", allocationSize = 1)
    private int id;

    @Basic
    private int vacancy;


    @ManyToMany(
            cascade = CascadeType.ALL
    )
//    @JoinTable(name="inscription", joinColumns=@JoinColumn(name="course_id", referencedColumnName = "id"),
//            inverseJoinColumns=@JoinColumn(name="docket", referencedColumnName = "docket")
//    )
    private Set<Student> student;

    @ManyToOne(cascade = CascadeType.ALL)
    private Course course;

    @Column(nullable = false)
    private LocalDateTime finalExamDate;

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

    public Set<Student> getStudent() {
        return student;
    }

    public void setStudent(Set<Student> student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public LocalDateTime getFinalExamDate() {
        return finalExamDate;
    }

    public void setFinalExamDate(LocalDateTime finalExamDate) {
        this.finalExamDate = finalExamDate;
    }

    protected FinalInscription(){
        //Just for hibernate
    }

    private FinalInscription(Builder builder){
        this.id = builder.id;
        this.vacancy = builder.vacancy;
        this.finalExamDate = builder.finalExamDate;
    }

    public static class Builder{
        private int id;
        private int vacancy;
        private LocalDateTime finalExamDate;

        public Builder(int id, int vacancy, LocalDateTime finalExamDate){
            this.id = id;
            this.vacancy = vacancy;
            this.finalExamDate = finalExamDate;
        }

        public FinalInscription build(){
            return new FinalInscription(this);
        }

    }


}
