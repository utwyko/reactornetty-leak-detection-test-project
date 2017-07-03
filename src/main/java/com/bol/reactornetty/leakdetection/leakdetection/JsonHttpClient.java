package com.bol.reactornetty.leakdetection.leakdetection;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.ipc.netty.http.client.HttpClient;
import rx.Observable;
import rx.RxReactiveStreams;

@Component
public class JsonHttpClient {

    private static final Logger log = LoggerFactory.getLogger(JsonHttpClient.class);

    private static final HttpClient httpClient = HttpClient.create(ops ->
            ops
                    .connect("jsonplaceholder.typicode.com", 80));

    @HystrixCommand(commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "50"),
            @HystrixProperty(name = "circuitBreaker.enabled", value = "false")
    })
    public Observable<String> getString() {
        final Mono<String> monoResponse = httpClient.get("/posts", request ->
                request
                        .addHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE))
                .then(response -> response.receive().aggregate().asString());

        return RxReactiveStreams.toObservable(monoResponse);
    }
}
