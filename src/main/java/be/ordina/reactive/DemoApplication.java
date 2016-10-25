package be.ordina.reactive;

import be.ordina.reactive.subscriber.SubscriberWithLimit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);

		System.out.println("initiliaze demo");

		runSimpleFlux();

		runSimpleFluxWithOnly2PerTime();

		runInParallelThread();

		runInParralelThreadWithFlatMap();

		runInParralelThreadWithFlatMapWithPublishOn();
	}

	private static void runSimpleFlux() {
		DemoFlux demoFlux = new DemoFlux();

		System.out.println("received flux with upper");
		Flux fluxConvertToUpper = demoFlux.printOutputFlux();

		System.out.println("No log statements have been printed => now it is just a plan of execution");

		System.out.println("Subscribe to the flux in order to let the data flow");
		fluxConvertToUpper.subscribe(System.out::println);
	}

	private static void runSimpleFluxWithOnly2PerTime() {
		DemoFlux demoFlux = new DemoFlux();

		System.out.println("received flux with upper");
		Flux fluxConvertToUpper = demoFlux.printOutputFlux();

		System.out.println("Subscribe to the flux in order to let the data flow - only 2 elements at the same time");


		fluxConvertToUpper.subscribe(new SubscriberWithLimit(2));
	}

	private static void runInParallelThread() {

		DemoFlux demoFlux = new DemoFlux();

		System.out.println("received flux with upper - going to process parallel");
		Flux fluxConvertToUpper = demoFlux.printOutputFlux();

		//In the code sample of the blog post, .subscribe(null, 2) is used => but that will throw a NullPointerException on the consumer.
		fluxConvertToUpper.subscribeOn(Schedulers.parallel()).subscribe(new SubscriberWithLimit(2));
	}

	private static void runInParralelThreadWithFlatMap() {

		System.out.println("going to process a flux in parallel over multiple threads");

		Flux.just("red", "white", "blue")
			.log()
			.flatMap(value ->
							Mono.just(value.toUpperCase())
									.subscribeOn(Schedulers.parallel()),
					2)
			.subscribe(value -> {
				System.out.println("Consumed: " + value);
			});

	}

	private static void runInParralelThreadWithFlatMapWithPublishOn() {

		System.out.println("going to process a flux in parallel over multiple threads also with parallel publishOn");

		Flux.just("red", "white", "blue")
			.log()
			.map(String::toUpperCase)
			.subscribeOn(Schedulers.newParallel("sub"))
			.publishOn(Schedulers.newParallel("pub"), 2)
			.subscribe(value -> {
				System.out.println("Consumed: " + value);
			});
	}


}
