package com.luizalabs.domain.product;

import com.google.gson.Gson;
import com.luizalabs.infrastructure.exception.NotFoundException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodySubscriber;
import java.net.http.HttpResponse.BodySubscribers;
import java.net.http.HttpResponse.ResponseInfo;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

class ProductRestClient {

    private static final String PRODUCT_API_URL = "http://challenge-api.luizalabs.com/api/product/%s";

    private ProductRestClient() {
    }

    // TODO add resilience4j circuit breaker
    static ProductResponse get(final String productId) {
        HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5)).build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format(PRODUCT_API_URL, productId)))
                .build();

        try {
            HttpResponse<ProductResponse> response = client.send(request, JsonBodyHandler.ofJson());
            return response.body();
        } catch (IOException | InterruptedException e) {
            throw new NotFoundException("Product information not found.");
        }
    }

    private static class JsonBodyHandler implements HttpResponse.BodyHandler<ProductResponse> {

        private final Gson gson = new Gson();

        static JsonBodyHandler ofJson() {
            return new JsonBodyHandler();
        }

        @Override
        public BodySubscriber<ProductResponse> apply(ResponseInfo responseInfo) {
            return BodySubscribers.mapping(BodySubscribers.ofString(StandardCharsets.UTF_8),
                    value -> gson.fromJson(value, ProductResponse.class));
        }
    }
}