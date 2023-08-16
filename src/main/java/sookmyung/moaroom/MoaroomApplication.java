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
				registry.addMapping("/**").allowedOrigins("http://localhost:3000");
				registry.addMapping("/**").allowedOrigins("http://moaroom-front.duckdns.org:3000");
				registry.addMapping("/**").allowedOrigins("http://a4916313160c74414a3426e5d3b9866c-383499736.ap-northeast-2.elb.amazonaws.com:8003");
			}
		};
	}
}
