package vn.group24.shopalbackend.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import vn.group24.shopalbackend.domain.Customer;
import vn.group24.shopalbackend.domain.Enterprise;
import vn.group24.shopalbackend.repository.CustomerRepository;
import vn.group24.shopalbackend.repository.EnterpriseRepository;
import vn.group24.shopalbackend.security.domain.UserAccount;

/**
 * @author ttg
 */
@Component
public class UserUtils {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EnterpriseRepository enterpriseRepository;

    private UserUtils() {
    }

    public Customer getAuthenticateCustomer() {
        Customer authenticateCustomer = customerRepository.getByUserAccountId(getUserId());
        authenticateCustomer.setLoginEmail(UserUtils.getUserName());
        return authenticateCustomer;
    }

    public Enterprise getAuthenticateEnterprise() {
        Enterprise authenticateEnterprise = enterpriseRepository.getByUserAccountId(getUserId());
        authenticateEnterprise.setEmail(UserUtils.getUserName());
        return authenticateEnterprise;
    }

    public UserAccount getAuthenticateUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserAccount) {
            return ((UserAccount) authentication.getPrincipal());
        }
        throw new IllegalArgumentException("User not exists");
    }

    public static String getUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserAccount) {
            return ((UserAccount) authentication.getPrincipal()).getUsername();
        }
        return "dummyUser";
    }

    public static Integer getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserAccount) {
            return ((UserAccount) authentication.getPrincipal()).getId();
        }
        throw new IllegalArgumentException("User not exists");
    }
}
