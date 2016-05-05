package ar.edu.itba.paw.shared;

public class AdminFilter extends UserFilter {

    protected AdminFilter(AdminFilterBuilder builder) {
        super(builder);
    }

    public static class AdminFilterBuilder extends UserFilter.UserFilterBuilder<AdminFilter, AdminFilterBuilder> {
        private Object docket;

        public AdminFilterBuilder docket(final Object docket) {
            this.docket = docket;

            return this;
        }

        public AdminFilter build() {
            return new AdminFilterBuilder(this);
        }

        @Override
        public AdminFilterBuilder thisBuilder() {
            return this;
        }
    }
}
