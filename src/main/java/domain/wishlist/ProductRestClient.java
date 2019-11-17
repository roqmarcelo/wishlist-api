package domain.wishlist;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodySubscriber;
import java.net.http.HttpResponse.BodySubscribers;
import java.net.http.HttpResponse.ResponseInfo;
import java.time.Duration;

import static jdk.internal.net.http.common.Utils.charsetFrom;

class ProductRestClient {

    private static final String PRODUCT_API_URL = "http://challenge-api.luizalabs.com/api/product/%s";

    // TODO add resilience4j circuit breaker
    ProductResponse get(final String productId) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(3)).build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format(PRODUCT_API_URL, productId)))
                .build();

        HttpResponse<ProductResponse> response = client.send(request, JsonBodyHandler.ofJson());

        return response.body();
    }

    private static class JsonBodyHandler implements HttpResponse.BodyHandler<ProductResponse> {

        private final Gson gson = new Gson();

        static JsonBodyHandler ofJson() {
            return new JsonBodyHandler();
        }

        @Override
        public BodySubscriber<ProductResponse> apply(ResponseInfo responseInfo) {
            return BodySubscribers.mapping(BodySubscribers.ofString(charsetFrom(responseInfo.headers())),
                    value -> gson.fromJson(value, ProductResponse.class));
        }
    }
}