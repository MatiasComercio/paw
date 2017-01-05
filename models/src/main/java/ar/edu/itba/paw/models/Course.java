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
    private Integer id;

    @Column(name = "course_id", length = 5, unique = true)
    private String courseId;

    //TODO: Add Basic annotation
    @Column(length = 100, nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private int credits;

    @Column(nullable = false)
    private int semester;

    @ManyToMany(
            cascade={CascadeType.PERSIST, CascadeType.MERGE},
            //TODO: Delete targetEntity: Since Collection use generics (Set<Course>) it's not needed
            targetEntity=Course.class
    )
    @JoinTable(
            name="correlative",
            joinColumns=@JoinColumn(name="course_id"),
            inverseJoinColumns=@JoinColumn(name="correlative_id")
    )
    private Set<Course> correlatives;

    @ManyToMany(
            cascade={CascadeType.PERSIST, CascadeType.MERGE},
            //TODO: Delete targetEntity: Since Collection use generics (Set<Course>) it's not needed
            targetEntity=Course.class
    )
    @JoinTable(
            name="correlative",
            joinColumns=@JoinColumn(name="correlative_id"),
            inverseJoinColumns=@JoinColumn(name="course_id")
    )
    private Set<Course> upperCorrelatives;

    @ManyToMany(mappedBy = "studentCourses")
    private List<Student> students;

    @Transient
    private List<Student> approvedStudents;


    protected Course(){}

    private Course(Builder builder) {
        this.id = builder.id;
        this.courseId = builder.courseId;
        this.name = builder.name;
        this.credits = builder.credits;
        this.semester = builder.semester;
    }

    public int getId() {
        return id;
    }

    public String getCourseId() {
        return courseId;
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

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public List<Student> getStudents() {
        return this.students;
    }

    public void setStudents(List<Student> students){
        this.students = students;
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

    public Set<Course> getUpperCorrelatives() {
        return upperCorrelatives;
    }

    public void setUpperCorrelatives(Set<Course> upperCorrelatives) {
        this.upperCorrelatives = upperCorrelatives;
    }

    public List<Student> getApprovedStudents() {
        return approvedStudents;
    }

    public void setApprovedStudents(List<Student> approvedStudents) {
        this.approvedStudents = approvedStudents;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Course course = (Course) o;

        if (credits != course.credits) return false;
        if (semester != course.semester) return false;
        if (id != null ? !id.equals(course.id) : course.id != null) return false;
        if (courseId != null ? !courseId.equals(course.courseId) : course.courseId != null) return false;
        if (name != null ? !name.equals(course.name) : course.name != null) return false;
        if (correlatives != null ? !correlatives.equals(course.correlatives) : course.correlatives != null)
            return false;
        if (upperCorrelatives != null ? !upperCorrelatives.equals(course.upperCorrelatives) : course.upperCorrelatives != null)
            return false;
        if (students != null ? !students.equals(course.students) : course.students != null) return false;
        return approvedStudents != null ? approvedStudents.equals(course.approvedStudents) : course.approvedStudents == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (courseId != null ? courseId.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + credits;
        result = 31 * result + semester;
        //TODO:Removed due to SO firing
        //result = 31 * result + (correlatives != null ? correlatives.hashCode() : 0);
        //result = 31 * result + (upperCorrelatives != null ? upperCorrelatives.hashCode() : 0);
        result = 31 * result + (students != null ? students.hashCode() : 0);
        result = 31 * result + (approvedStudents != null ? approvedStudents.hashCode() : 0);
        return result;
    }

    public static class Builder {
        private Integer id;
        private String courseId;
        private String name;
        private int credits;
        private int semester;

        public Builder(Integer id) {
            this.id = id;
        }

        public Builder id(Integer id) {
            this.id = id;

            return this;
        }

        public Builder courseId(String courseId) {
            this.courseId = courseId;
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

        public String getCourseId() {
            return courseId;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setCourseId(String courseId) {
            this.courseId = courseId;
        }

        public Course build() {
            return new Course(this);
        }
    }
}
