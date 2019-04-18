package curso.spring.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EntityScan(basePackages="curso.springboot.model") //para reconher as entidades (@Entity)
@ComponentScan(basePackages= {"curso.*"}) //para reconhecer os controllers
@EnableJpaRepositories(basePackages= {"curso.springboot.repository"}) //para encontrar/reconhecer as intefaces do pacote repository
@EnableTransactionManagement //
public class SpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootApplication.class, args);
	}

}
