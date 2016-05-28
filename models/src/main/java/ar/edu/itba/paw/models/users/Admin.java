package ar.edu.itba.paw.models.users;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.Table;

@Entity
@Table(name = "admin")
@DiscriminatorValue("ADMIN")
public class Admin extends User {

    private Admin(final Builder builder) {
        super(builder);
    }

    protected Admin() {
        // just for Hibernate
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public static class Builder extends User.Builder<Admin, Builder> {
        public Builder(int dni) {
            super(dni);
        }

        @Override
        public Admin build() {
            return new Admin(this);
        }

        @Override
        public Builder thisBuilder() {
            return this;
        }
    }
}
