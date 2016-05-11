package ar.edu.itba.paw.shared;

public class AdminFilter extends UserFilter {

    private AdminFilter(AdminFilterBuilder builder) {
        super(builder);
    }

    public static class AdminFilterBuilder extends UserFilter.UserFilterBuilder<AdminFilter, AdminFilterBuilder> {
        public AdminFilter build() {
            return new AdminFilter(this);
        }

        @Override
        public AdminFilterBuilder thisBuilder() {
            return this;
        }
    }
}
