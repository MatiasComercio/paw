package ar.edu.itba.paw.shared;

public class CourseFilter {
    private Object keyword; /* TODO: Decide whether the service is obliged to create a Filter with at least one field */
    private Object id;

    private CourseFilter(CourseFilterBuilder builder) {
        this.keyword = builder.keyword;
        this.id = builder.id;
    }

    public Object getKeyword() {
        return keyword;
    }

    public Object getId() {
        return id;
    }

    public static class CourseFilterBuilder {
        private Object keyword;
        private Object id;

        public CourseFilterBuilder keyword(final Object keyword) {
            this.keyword = keyword;

            return this;
        }

        public CourseFilterBuilder id(final Object id) {
            this.id = id;

            return this;
        }

        public CourseFilter build() {
            return new CourseFilter(this);
        }
    }
}
