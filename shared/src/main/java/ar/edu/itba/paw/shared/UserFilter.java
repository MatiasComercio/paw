package ar.edu.itba.paw.shared;

import ar.edu.itba.paw.models.users.User;

public abstract class UserFilter {
    private final Object dni;

    private final Object firstName;
    private final Object lastName;
    private final Object genre;

    protected UserFilter(final UserFilterBuilder builder) {
        this.dni = builder.dni;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.genre = builder.genre;
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

    public Object getGenre() {
        return genre;
    }

    protected static abstract class UserFilterBuilder<V extends UserFilter, T extends UserFilterBuilder<V,T>> {
        private Object dni;

        private Object firstName;
        private Object lastName;
        private Object genre;

        private final T thisBuilder;

        public abstract V build();

        public abstract T thisBuilder();

        public UserFilterBuilder() {
            this.thisBuilder = thisBuilder();
        }

        public T dni(final Object dni) {
            this.dni = dni;

            return thisBuilder;
        }

        public T firstName(final Object firstName) {
            this.firstName = firstName;

            return thisBuilder;
        }

        public T lastName(final Object lastName) {
            this.lastName = lastName;

            return thisBuilder;
        }

        public T genre(final Object genre) {
            if(genre == null) {
                return thisBuilder;
            }
            if(genre.toString().equals("")) {
                this.genre = null;
            } else {
                this.genre = User.Genre.getGenre(genre.toString());
                if(this.genre == null) {
                    this.genre = "";
                }
            }
            return thisBuilder;
        }
    }
}
