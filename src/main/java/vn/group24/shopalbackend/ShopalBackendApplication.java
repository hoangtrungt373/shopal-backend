package vn.group24.shopalbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import vn.group24.shopalbackend.domain.Product;
import vn.group24.shopalbackend.repository.ProductRepository;
import vn.group24.shopalbackend.security.domain.UserAccount;
import vn.group24.shopalbackend.security.repository.UserAccountRepository;

@SpringBootApplication
@EntityScan(basePackageClasses = {UserAccount.class, Product.class})
@EnableJpaRepositories(basePackageClasses = {UserAccountRepository.class, ProductRepository.class})
public class ShopalBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopalBackendApplication.class, args);
    }

}
