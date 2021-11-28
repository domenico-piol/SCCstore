package name.piol.demo.sccstore.iam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class SCCstoreIAMApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(SCCstoreIAMApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(final SpringApplicationBuilder builder) {
		return builder.sources(this.getClass());
	}

}
