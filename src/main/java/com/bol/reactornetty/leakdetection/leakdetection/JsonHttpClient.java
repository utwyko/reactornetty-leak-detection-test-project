package com.bol.reactornetty.leakdetection.leakdetection;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Duration;

import static com.bol.reactornetty.leakdetection.leakdetection.AsyncHttpMono.toMono;

@Component
public class JsonHttpClient {

    private static final AsyncHttpClient httpClient = new DefaultAsyncHttpClient();

    public Mono<String> getString() {
        return toMono(() -> httpClient
                .prepareGet("http://jsonplaceholder.typicode.com/posts")
                .setHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE))
                .map(Response::getResponseBody)
                .timeout(Duration.ofMillis(30));
    }
}
