package nl.yestelecom.phoenix.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = { "nl.yestelecom.phoenix", "nl.yestelecom.phoenix.batch" }, exclude = { SecurityAutoConfiguration.class, ManagementWebSecurityAutoConfiguration.class })
@EntityScan(basePackages = { "nl.yestelecom.phoenix", "nl.yestelecom.phoenix.batch" })
@EnableAutoConfiguration(exclude = { SecurityAutoConfiguration.class, ManagementWebSecurityAutoConfiguration.class })
@EnableJpaAuditing
@EnableSpringDataWebSupport
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = { "nl.yestelecom.phoenix", "nl.yestelecom.phoenix.batch" }, considerNestedRepositories = true)
@ComponentScan(basePackages = { "nl.yestelecom.phoenix", "nl.yestelecom.phoenix.batch" })
@PropertySource(value = { "file:/opt/phoenix/configuration/database.properties", "file:/opt/phoenix/configuration/backgroudjobs.properties"})
@EnableScheduling
public class BgProcessApplication {

    public static void main(String[] args) {
        SpringApplication.run(BgProcessApplication.class, args);
    }

}
