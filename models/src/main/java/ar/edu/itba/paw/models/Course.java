package ar.edu.itba.paw.models;

import ar.edu.itba.paw.models.users.Student;

import java.util.List;

public class Course {

    private int id;
    private String name;
    private int credits;
    private List<Student> students;

    public Course(int id, String name, int credits) {
        this.id = id;
        this.name = name;
        this.credits = credits;
    }

    public Course(int id, String name, int credits, List<Student> students) {
        this.id = id;
        this.name = name;
        this.credits = credits;
        this.students = students;
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
}
