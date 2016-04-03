package ar.edu.itba.paw.shared;

public class UserFilter {
    private final Object dni;

    private final Object firstName;
    private final Object lastName;
//    private final Object genre;

    protected UserFilter(final UserFilterBuilder builder) {
        this.dni = builder.dni;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
    }

    public Object getDni() {
        return dni;
    }

    public Object getFirstName() {
        return firstName;
    }

    public Object getLastName() {
        return lastName;
    }

    protected static abstract class UserFilterBuilder<V extends UserFilter, T extends UserFilterBuilder<V,T>> {
        private final Object dni;

        private Object firstName;
        private Object lastName;

        private final T thisBuilder;

        public abstract V build();

        public abstract T thisBuilder();

        public UserFilterBuilder(final Object dni) {
            this.dni = dni;
            this.thisBuilder = thisBuilder();
        }

        public T firstName(final Object firstName) {
            this.firstName = firstName;

            return thisBuilder;
        }

        public T lastName(final Object lastName) {
            this.lastName = lastName;

            return thisBuilder;
        }
    }
}
