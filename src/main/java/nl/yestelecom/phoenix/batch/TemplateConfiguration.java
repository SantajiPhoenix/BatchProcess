package nl.yestelecom.phoenix.batch;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.resourceresolver.ClassLoaderResourceResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

@Configuration
public class TemplateConfiguration {

	@Bean(name = "classPathTemplateEngineXML")
	public TemplateEngine templateEngineXML() {
		TemplateResolver templateResolver = new TemplateResolver();
		templateResolver.setPrefix("templates/");
		templateResolver.setSuffix(".xml");
		templateResolver.setResourceResolver(new ClassLoaderResourceResolver());

		TemplateEngine templateEngine = new TemplateEngine();
		templateEngine.setTemplateResolver(templateResolver);

		return templateEngine;
	}
}
