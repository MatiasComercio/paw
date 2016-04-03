package ar.edu.itba.paw.models;

import ar.edu.itba.paw.models.users.Student;

import java.util.List;

public class Course {

    private int id;
    private String name;
    private int credits;
    private List<Student> students;

    private Course(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.credits = builder.credits;
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

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", credits=" + credits +
                '}';
    }

    public static class Builder {
        private int id;
        private String name;
        private int credits;

        public Builder(int id) {
            this.id = id;
        }

        public Builder() {

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
