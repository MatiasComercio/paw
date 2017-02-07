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

    @ManyToMany(
            cascade={CascadeType.PERSIST, CascadeType.MERGE},
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

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
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

    public List<Student> getStudents() {
        return this.students;
    }

    public void setStudents(List<Student> students){
        this.students = students;
    }

    public int getSemester(){
        return this.semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
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
        if (!id.equals(course.id)) return false;
        if (!courseId.equals(course.courseId)) return false;
        return name.equals(course.name);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (courseId != null ? courseId.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + credits;
        result = 31 * result + semester;
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

        public void setId(int id) {
            this.id = id;
        }

        public String getCourseId() {
            return courseId;
        }

        public void setCourseId(String courseId) {
            this.courseId = courseId;
        }

        public Course build() {
            return new Course(this);
        }
    }
}
