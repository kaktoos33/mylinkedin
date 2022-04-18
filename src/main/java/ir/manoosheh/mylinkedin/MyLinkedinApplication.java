package ir.manoosheh.mylinkedin;

import ir.manoosheh.mylinkedin.configuration.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class MyLinkedinApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyLinkedinApplication.class, args);
	}

}
