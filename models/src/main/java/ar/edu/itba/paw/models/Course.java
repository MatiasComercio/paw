package ar.edu.itba.paw.models;

import ar.edu.itba.paw.models.users.Student;

import java.util.List;

public class Course {

    private int id;
    private String name;
    private int credits;
    private int semester;
    private List<Student> students;

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
