package vn.group24.shopalbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import vn.group24.shopalbackend.security.domain.ShopalUser;
import vn.group24.shopalbackend.security.repository.ShopalUserRepository;

@SpringBootApplication
@EntityScan(basePackageClasses = {ShopalUser.class})
@EnableJpaRepositories(basePackageClasses = {ShopalUserRepository.class})
public class ShopalBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopalBackendApplication.class, args);
    }

}
