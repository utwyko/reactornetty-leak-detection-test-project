package com.bol.reactornetty.leakdetection.leakdetection;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.annotation.ObservableExecutionMode;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.ipc.netty.http.client.HttpClient;
import reactor.ipc.netty.resources.PoolResources;
import rx.Observable;
import rx.RxReactiveStreams;

@Component
public class JsonHttpClient {

    private static final HttpClient httpClient = HttpClient.create(ops ->
            ops.connect("jsonplaceholder.typicode.com", 80)
                    .poolResources(PoolResources.elastic("JSON")));

    @HystrixCommand(groupKey = "STRING", commandKey = "STRING" + ".GetString", observableExecutionMode = ObservableExecutionMode.LAZY,
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "80"),
            })
    public Observable<String> getString() {
        final Mono<String> monoResponse = httpClient.get("/posts", request -> request
                .addHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE))
                .then(response -> response.receive().aggregate().asString());
        return RxReactiveStreams.toObservable(monoResponse);
    }
}
