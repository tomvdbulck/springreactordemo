package be.ordina.reactive;


import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class DemoFluxWithFlatMap {


    private Flux<String> flux;

    public DemoFluxWithFlatMap() {
        flux = Flux.just("red", "white", "blue");

    }

    public Flux printOutputFlux() {
        Flux<String> upperFlux = flux
                .log()
                .flatMap(value ->
                                Mono.just(value.toUpperCase())
                                        .subscribeOn(Schedulers.parallel()),
                        2);

        return upperFlux;

    }

}
