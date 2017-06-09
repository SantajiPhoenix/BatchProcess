package nl.yestelecom.phoenix.batch;

import javax.servlet.Filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.isrsal.logging.LoggingFilter;

@Configuration
public class LoggerConfig {

	@Bean
	public Filter springMvcLogger() {
		return new LoggingFilter();
	}
}
