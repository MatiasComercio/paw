package ar.edu.itba.paw.models;

import ar.edu.itba.paw.models.users.Student;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_courseid_seq")
    @SequenceGenerator(sequenceName = "course_courseid_seq", name = "course_courseid_seq", allocationSize = 1)
    private int id;

    @Column(length = 100, nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private int credits;

    @Column(nullable = false)
    private int semester;

    @ManyToMany(
            cascade={CascadeType.PERSIST, CascadeType.MERGE},
            targetEntity=Course.class
    )
    @JoinTable(
            name="correlative",
            joinColumns=@JoinColumn(name="course_id"),
            inverseJoinColumns=@JoinColumn(name="correlative_id")
    )
    private Set<Course> correlatives;

    /*@Column(length=5, unique=true)
    private String code;
    */

    //@Column(name = "code", length = 5, unique = true)
    //private String code;

    //@ManyToMany()
    //@Column(nullable = true)
    //@JoinTable(name="inscription", joinColumns=@JoinColumn(name="course_id"),
    //        inverseJoinColumns=@JoinColumn(name="docket"))
    //private List<Student> students;


    protected Course(){}

    private Course(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.credits = builder.credits;
        this.semester = builder.semester;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Student> getStudents() {
        return null;//this.students;
    }

    public void setStudents(List<Student> students){
        //this.students = students;
    }

    public void setSemester(int semester){
        this.semester = semester;
    }

    public int getSemester(){
        return this.semester;
    }

    public Set<Course> getCorrelatives() {
        return correlatives;
    }

    public void setCorrelatives(Set<Course> correlatives) {
        this.correlatives = correlatives;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;

        final Course course = (Course) o;

        return id == course.id;

    }

    @Override
    public int hashCode() {
        return id;
    }

    public static class Builder {
        private int id;
        private String name;
        private int credits;
        private int semester;

        public Builder(int id) {
            this.id = id;
        }

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder credits(int credits) {
            this.credits = credits;
            return this;
        }

        public Builder semester(int semester) {
            this.semester = semester;
            return this;
        }

        public int getCredits() {
            return credits;
        }

        public void setCredits(int credits) {
            this.credits = credits;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Course build() {
            return new Course(this);
        }
    }
}
