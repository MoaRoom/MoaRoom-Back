package sookmyung.moaroom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MoaroomApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoaroomApplication.class, args);
	}

}
