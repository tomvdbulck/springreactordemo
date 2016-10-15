package be.ordina.reactive;


import reactor.core.publisher.Flux;

public class DemoFlux {


    private Flux<String> flux;

    public  DemoFlux () {
        flux = Flux.just("red", "white", "blue");

    }

    public Flux printOutputFlux() {
        Flux<String> upperFlux = flux
                .log()
                .map(String::toUpperCase);

        return upperFlux;

    }

}
