package com.bol.reactornetty.leakdetection.leakdetection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
public class SampleController {

    private static final Logger log = LoggerFactory.getLogger(SampleController.class);

    private final JsonHttpClient httpClient;

    public SampleController(JsonHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @GetMapping("/json")
    public DeferredResult<String> getJsonResponse() {
        log.info("Received request");

        DeferredResult<String> response = new DeferredResult<>();

        httpClient.getString()
                .doOnError(e -> log.error("error", e))
                .subscribe(
                        response::setResult,
                        error -> response.setErrorResult("ERROR")
                );

        return response;
    }
}
