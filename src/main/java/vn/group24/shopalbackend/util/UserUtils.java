package vn.group24.shopalbackend.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import vn.group24.shopalbackend.security.domain.ShopalUser;

/**
 * @author ttg
 */
public class UserUtils {

    private UserUtils() {
    }

    public static String getUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof ShopalUser) {
            return ((ShopalUser) authentication.getPrincipal()).getUsername();
        }
        return "dummyUser";
    }

    public static Integer getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof ShopalUser) {
            return ((ShopalUser) authentication.getPrincipal()).getId();
        }
        throw new IllegalArgumentException("User not exists");
    }
}
