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

    @Basic
    private int maxVacancy;

    @ManyToMany(
            cascade = CascadeType.ALL
    )
//    @JoinTable(name="inscription", joinColumns=@JoinColumn(name="course_id", referencedColumnName = "id"),
//            inverseJoinColumns=@JoinColumn(name="docket", referencedColumnName = "docket")
//    )
    private Set<Student> students;

    @ManyToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.EAGER)
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

    public void setMaxVacancy(int maxVacancy) {
        this.maxVacancy = maxVacancy;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
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
        this.maxVacancy = builder.maxVacancy;
        this.finalExamDate = builder.finalExamDate;
        this.vacancy = 0;
    }

    public void increaseVacancy() {
        this.vacancy++;
    }

    public void decreaseVacancy(){
        this.vacancy--;
    }

    public int getMaxVacancy() {
        return this.maxVacancy;
    }

    public static class Builder{
        private int id;
        private int vacancy;
        private int maxVacancy;
        private LocalDateTime finalExamDate;
        private Course course;

        public Builder(int id, int maxVacancy, LocalDateTime finalExamDate, Course course){
            this.id = id;
            this.maxVacancy = maxVacancy;
            this.finalExamDate = finalExamDate;

            //TODO: Debug purposes only
            //this.finalExamDate = LocalDateTime.now();
            this.course = course;
        }

        public FinalInscription build(){
            return new FinalInscription(this);
        }

        Builder vacancy (int vacancy){
            this.vacancy = vacancy;
            return this;
        }

        Builder maxVacancy(int maxVacancy){
            this.maxVacancy = maxVacancy;
            return this;
        }

        Builder finalExamDate (LocalDateTime finalExamDate){
            this.finalExamDate = finalExamDate;
            return this;
        }

        Builder course(Course course){
            this.course = course;
            return this;
        }

    }


}
