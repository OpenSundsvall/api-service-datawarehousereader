package se.sundsvall.datawarehousereader;

import org.springframework.boot.SpringApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;

import se.sundsvall.dept44.ServiceApplication;

@EnableFeignClients
@EnableCaching
@ServiceApplication
public class Application {
	public static void main(String... args) {
		SpringApplication.run(Application.class, args);
	}
}
