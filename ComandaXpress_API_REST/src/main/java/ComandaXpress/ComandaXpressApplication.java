package ComandaXpress;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class ComandaXpressApplication {

	public static void main(String[] args) {
		SpringApplication.run(ComandaXpressApplication.class, args);
	}

}
