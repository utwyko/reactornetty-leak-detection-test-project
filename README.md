# Reactor Netty Leak Detection

### Reproducing

- Run the main with `-Dio.netty.leakDetection.level=PARANOID` argument
- Use Apache benchmark to query the /json endpoint

``` ab -n 100 -c 1  http://localhost:8080/json```