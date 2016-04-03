package ar.edu.itba.paw.shared;

public class StudentFilter extends UserFilter {
    private final Object docket;

    private StudentFilter(final StudentFilterBuilder builder) {
        super(builder);
        this.docket = builder;
    }

    public Object getDocket() {
        return docket;
    }

    public static class StudentFilterBuilder extends UserFilter.UserFilterBuilder<StudentFilter, StudentFilterBuilder> {
        private Object docket;

        public StudentFilterBuilder docket(final Object docket) {
            this.docket = docket;

            return this;
        }

        public StudentFilter build() {
            return new StudentFilter(this);
        }

        @Override
        public StudentFilterBuilder thisBuilder() {
            return this;
        }
    }
}
