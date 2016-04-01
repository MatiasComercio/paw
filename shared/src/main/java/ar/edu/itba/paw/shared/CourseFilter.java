package ar.edu.itba.paw.shared;

public class CourseFilter {
    private String keyword; /* TODO: Decide whether the service is obliged to create a Filter with at least one field */
    private Integer id;

    private CourseFilter(CourseFilterBuilder builder) {
        this.keyword = builder.keyword;
        this.id = builder.id;
    }

    public String getKeyword() {
        return keyword;
    }

    public Integer getId() {
        return id;
    }

    public static class CourseFilterBuilder {
        private String keyword;
        private Integer id;

        public CourseFilterBuilder keyword(final String keyword) {
            this.keyword = keyword;

            return this;
        }

        public CourseFilterBuilder id(final Integer id) {
            this.id = id;

            return this;
        }

        public CourseFilter build() {
            return new CourseFilter(this);
        }
    }
}
