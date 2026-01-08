package com.chokmai.security;

import io.quarkus.oidc.runtime.OidcJwtCallerPrincipal;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.util.Optional;
import java.util.Set;

@RequestScoped
public class AuthContext {

    @Inject
    SecurityIdentity identity;

    private OidcJwtCallerPrincipal jwt() {
        if (identity.getPrincipal() instanceof OidcJwtCallerPrincipal principal) {
            return principal;
        }
        throw new IllegalStateException("Principal is not an OIDC JWT");
    }

    /* ---------- Core identity ---------- */

    public String subject() {
        return identity.getPrincipal().getName(); // JWT sub
    }

    public Set<String> roles() {
        return identity.getRoles();
    }

    public boolean hasRole(String role) {
        return roles().contains(role);
    }

    /* ---------- Claims ---------- */

    public String claim(String name) {
        return jwt().getClaim(name);
    }

    public Optional<String> claimOptional(String name) {
        return Optional.ofNullable(jwt().getClaim(name));
    }

    /* ---------- Actor ---------- */

    public String actor() {
        return claimOptional("email")
                .or(() -> claimOptional("preferred_username"))
                .orElse(subject());
    }
}
