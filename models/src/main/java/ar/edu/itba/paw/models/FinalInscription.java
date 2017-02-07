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
    private Integer id;

    @Basic
    private int vacancy;

    @Basic
    private int maxVacancy;

    @ManyToMany()
    @JoinTable(name="finalinscription_student_history")
    private Set<Student> history;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Course course;

    @Column(nullable = false)
    private LocalDateTime finalExamDate;

    @Enumerated(value = EnumType.STRING)
    private FinalInscriptionState state = FinalInscriptionState.OPEN;


    protected FinalInscription() {
        //Just for hibernate
    }

    private FinalInscription(Builder builder) {
        this.id = builder.id;
        this.maxVacancy = builder.maxVacancy;
        this.finalExamDate = builder.finalExamDate;
        this.vacancy = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getVacancy() {
        return vacancy;
    }

    public void setVacancy(int vacancy) {
        this.vacancy = vacancy;
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

    public FinalInscriptionState getState() {
        return state;
    }

    public void setState(FinalInscriptionState state) {
        this.state = state;
    }

    public Set<Student> getHistory() {
        return history;
    }

    public void setHistory(Set<Student> history) {
        this.history = history;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FinalInscription that = (FinalInscription) o;

        if (maxVacancy != that.maxVacancy) return false;
        if (!id.equals(that.id)) return false;
        if (!course.equals(that.course)) return false;
        return finalExamDate.equals(that.finalExamDate);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + maxVacancy;
        result = 31 * result + course.hashCode();
        result = 31 * result + finalExamDate.hashCode();
        return result;
    }

    public boolean isOpen(){
        return this.state == FinalInscriptionState.OPEN;
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

    public void setMaxVacancy(int maxVacancy) {
        this.maxVacancy = maxVacancy;
    }

    public enum FinalInscriptionState {
        OPEN("OPEN"),
        CLOSED("CLOSED");

        private String state;

        FinalInscriptionState(final String state) {
            this.state = state;
        }

        public String getState() {
            return state;
        }
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
