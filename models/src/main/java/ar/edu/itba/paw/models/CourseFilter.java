package ar.edu.itba.paw.models;

public class CourseFilter {
    private String keyword; /* TODO: Decide whether the service is obliged to create a Filter with at least one field */

    private CourseFilter(CourseFilterBuilder builder) {
        this.keyword = builder.keyword;
    }

    public String getKeyword() {
        return keyword;
    }

    protected static class CourseFilterBuilder {
        private String keyword;

        public CourseFilterBuilder keyword(final String keyword) {
            this.keyword = keyword;

            return this;
        }

        public CourseFilter build() {
            return new CourseFilter(this);
        }
    }
}
