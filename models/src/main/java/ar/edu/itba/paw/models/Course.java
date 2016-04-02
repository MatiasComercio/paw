package ar.edu.itba.paw.models;

/**
 * Created by mati on 30/03/16.
 */
public class Course {

    private int id;
    private String name;
    private int credits;

    public Course(Builder builder) {
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

        public Course build() {
            return new Course(this);
        }
    }
}
