package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.models.Address;
import ar.edu.itba.paw.models.users.Admin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class AdminDetails extends User {
    private Admin admin;

    private AdminDetails(final Builder builder) {
        super (
                String.valueOf(builder.admin.getDni()),
                builder.password,
                builder.enabled,
                builder.accountNonExpired,
                builder.credentialsNonExpired,
                builder.accountNonLocked,
                builder.authorities
        );

        this.admin = builder.admin;
    }

    public int getDni() {
        return admin.getDni();
    }

    public String getFirstName() {
        return admin.getFirstName();
    }

    public LocalDate getBirthday() {
        return admin.getBirthday();
    }

    public Address getAddress() {
        return admin.getAddress();
    }

    public String getFullName() {
        return admin.getFullName();
    }

    public ar.edu.itba.paw.models.users.User.Genre getGenre() {
        return admin.getGenre();
    }

    public String getLastName() {
        return admin.getLastName();
    }

    public String getEmail() {
        return admin.getEmail();
    }

    public static class Builder {
        private final Set<GrantedAuthority> authorities;
        private final Admin.Builder adminBuilder;

        /* For accessing Student Model fields */
        private Admin admin;

        /* For accessing Spring.User model fields */
        private String password = "pass";
        private boolean enabled = true;
        private boolean accountNonExpired = true;
        private boolean credentialsNonExpired = true;
        private boolean accountNonLocked = true;

        public Builder(final int dni) {
            authorities = new HashSet<>();
            adminBuilder = new Admin.Builder(dni);
        }

        public Builder email(final String email) {
            adminBuilder.email(email);
            return this;
        }

        public Builder lastName(final String lastName) {
            adminBuilder.lastName(lastName);
            return this;
        }

        public Builder firstName(final String firstName) {
            adminBuilder.firstName(firstName);
            return this;
        }

        public Builder address(final Address address) {
            adminBuilder.address(address);
            return this;
        }

        public Builder genre(final ar.edu.itba.paw.models.users.User.Genre genre) {
            adminBuilder.genre(genre);
            return this;
        }

        public Builder birthday(final LocalDate birthday) {
            adminBuilder.birthday(birthday);
            return this;
        }

        /* Spring.User builder fields */
        public Builder password(final String password) {
            this.password = password;
            return this;
        }

        public Builder enabled(final boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public Builder accountNonExpired(final boolean accountNonExpired) {
            this.accountNonExpired = accountNonExpired;
            return this;
        }

        public Builder credentialsNonExpired(final boolean credentialsNonExpired) {
            this.credentialsNonExpired = credentialsNonExpired;
            return this;
        }

        public Builder accountNonLocked(final boolean accountNonLocked) {
            this.accountNonLocked = accountNonLocked;
            return this;
        }

        public Builder authority(final GrantedAuthority authority) {
            this.authorities.add(authority);
            return this;
        }

        public Builder authorities(final Collection<GrantedAuthority> authorities) {
            this.authorities.addAll(authorities);
            return this;
        }

        public AdminDetails build() {
            admin = adminBuilder.build();
            return new AdminDetails(this);
        }
    }
}