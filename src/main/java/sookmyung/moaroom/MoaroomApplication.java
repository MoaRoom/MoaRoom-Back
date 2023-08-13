package sookmyung.moaroom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableScheduling
public class MoaroomApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoaroomApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("http://moaroom-back.duckdns.org:3000");
				registry.addMapping("/**").allowedOrigins("https://2876411F1C9663FA39717D8BFE5137AB.gr7.ap-northeast-2.eks.amazonaws.com:8003");
			}
		};
	}
}
