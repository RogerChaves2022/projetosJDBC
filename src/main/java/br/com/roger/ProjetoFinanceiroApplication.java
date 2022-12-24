package br.com.roger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class ProjetoFinanceiroApplication implements WebMvcConfigurer {
	
	@Override
	public void addCorsMappings( CorsRegistry registry) {
		registry.addMapping("/**").allowedMethods("GET","POST","PUT","DELETE", "OPTIONS");
	}
	
	public static void main(String[] args) {
		SpringApplication.run(ProjetoFinanceiroApplication.class, args);
	}

}
