package com.bol.reactornetty.leakdetection.leakdetection;

import org.asynchttpclient.*;
import reactor.core.publisher.Mono;

import java.util.function.Supplier;

/**
 * Reactor Mono adaptation of org.asynchttpclient.extras.rxjava.AsyncHttpObservable.
 */
public final class AsyncHttpMono {

    /**
     * Observe a request execution and emit the response to the observer.
     *
     * @param supplier the supplier
     * @return The cold Mono (must be subscribed to in order to execute).
     */
    public static Mono<Response> toMono(final Supplier<BoundRequestBuilder> supplier) {
        return Mono.create(emitter -> {
            try {
                AsyncCompletionHandler<Void> handler = new AsyncCompletionHandler<Void>() {
                    @Override
                    public Void onCompleted(Response response) throws Exception {
                        emitter.success(response);
                        return null;
                    }

                    @Override
                    public void onThrowable(Throwable t) {
                        emitter.error(t);
                    }
                };
                supplier.get().execute(handler);
            } catch (Throwable t) {
                emitter.error(t);
            }
        });
    }
}