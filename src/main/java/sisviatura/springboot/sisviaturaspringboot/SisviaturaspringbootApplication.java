package sisviatura.springboot.sisviaturaspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.Ordered;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

@EnableWebSecurity
@SpringBootApplication
@EntityScan(basePackages = "sisviatura.springboot.model")
@ComponentScan(basePackages= {"sisviatura.*"})
@EnableJpaRepositories(basePackages = {"sisviatura.springboot.repository"})
@EnableTransactionManagement
public class SisviaturaspringbootApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(SisviaturaspringbootApplication.class, args);
	
		
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SisviaturaspringbootApplication.class);
	}
	
    

}
