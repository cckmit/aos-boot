package ink.aos.boot.security;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * Utility class for Spring Security.
 */
public final class SecurityUtils {

    private SecurityUtils() {
    }

    /**
     * Get the login of the current user.
     *
     * @return the login of the current user.
     */
    public static Optional<String> getCurrentUserId() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication())
                .map(authentication -> {
                    if (User.class.isInstance(authentication.getPrincipal())) {
                        User user = (User) authentication.getPrincipal();
                        return user.getId();
                    } else if (authentication.getPrincipal() instanceof String) {
                        return (String) authentication.getPrincipal();
                    }
                    return null;
                });
    }

    public static Optional<User> getCurrentUser() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication())
                .map(authentication -> {
                    if (User.class.isInstance(authentication.getPrincipal())) {
                        return (User) authentication.getPrincipal();
                    }
                    return null;
                });
    }

    /**
     * Check if a user is authenticated.
     *
     * @return true if the user is authenticated, false otherwise.
     */
    public static boolean isAuthenticated() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication())
                .map(authentication -> authentication.getAuthorities().size() > 0)
                .orElse(false);
    }

    /**
     * If the current user has a specific authority (security role).
     * <p>
     * The name of this method comes from the {@code isUserInRole()} method in the Servlet API.
     *
     * @param authority the authority to check.
     * @return true if the current user has the authority, false otherwise.
     */
    public static boolean hasPermission(String authority) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication())
                .map(authentication -> authentication.getAuthorities().stream()
                        .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(authority)))
                .orElse(false);
    }

//    public static String getToken() {
//        SecurityContext securityContext = SecurityContextHolder.getContext();
//        Authentication authentication = securityContext.getAuthentication();
//        if (authentication != null && authentication.getDetails() instanceof OAuth2AuthenticationDetails) {
//            OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
//            return details.getTokenValue();
//        }
//        return null;
//    }
}
