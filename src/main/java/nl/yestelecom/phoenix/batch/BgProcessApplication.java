package nl.yestelecom.phoenix.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = { "nl.yestelecom.phoenix", "nl.yestelecom.batch" }, exclude = { SecurityAutoConfiguration.class, ManagementWebSecurityAutoConfiguration.class })
@EntityScan(basePackages = { "nl.yestelecom.phoenix", "nl.yestelecom.batch" })
@EnableJpaAuditing(auditorAwareRef = "authenticatedUserAuditor")
@EnableSpringDataWebSupport
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = { "nl.yestelecom.phoenix", "nl.yestelecom.batch.*" }, considerNestedRepositories = true)
@PropertySource(value = { "file:/opt/phoenix/configuration/database.properties", "file:/opt/phoenix/configuration/backgroudjobs.properties" })
@EnableScheduling
public class BgProcessApplication {

    public static void main(String[] args) {
        SpringApplication.run(BgProcessApplication.class, args);
    }

}
