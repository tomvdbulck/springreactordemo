package be.ordina.reactive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);

		System.out.println("initiliaze demo");
		DemoFlux demoFlux = new DemoFlux();

		System.out.println("received flux with upper");
		Flux fluxConvertToUpper = demoFlux.printOutputFlux();

		System.out.println("No log statements have been printed => now it is just a plan of execution");

		System.out.println("Subscribe to the flux in order to let the data flow");
		fluxConvertToUpper.subscribe(System.out::println);
	}
}
