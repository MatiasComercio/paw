package ar.edu.itba.paw.models;

public class CourseFilter {
    private String keyword;

    private CourseFilter(CourseFilterBuilder builder) {
        this.keyword = builder.keyword;
    }

    protected static class CourseFilterBuilder {
        private String keyword;

        public CourseFilterBuilder keyword(final String keyword) {
            this.keyword = keyword;

            return this;
        }
    }
}
