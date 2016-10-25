package be.ordina.reactive.subscriber;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class SubscriberWithLimit implements Subscriber {

    private final int limit;
    private long count;
    private Subscription subscription;

    public SubscriberWithLimit(final int limit) {
        this.limit = limit;
        this.count = 0;
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        this.subscription = subscription;
        subscription.request(limit);
    }

    @Override
    public void onNext(Object o) {
        count++;
        if (count>=limit) {
            count = 0;
            subscription.request(limit);
        }
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onComplete() {

    }
}
