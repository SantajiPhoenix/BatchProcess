package nl.yestelecom.phoenix.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableAutoConfiguration(exclude = { SecurityAutoConfiguration.class })
@EnableJpaAuditing
@EnableSpringDataWebSupport
@EnableTransactionManagement
@PropertySource("file:/opt/phoenix/configuration/backgroudjobs.properties")
public class BgProcessApplication {

    public static void main(String[] args) {
        SpringApplication.run(BgProcessApplication.class, args);
    }

}
