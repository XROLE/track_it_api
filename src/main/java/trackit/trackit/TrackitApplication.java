package trackit.trackit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"trackit.trackit.utils"})
public class TrackitApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrackitApplication.class, args);
	}

}
