package rest;

import rest.RestRequestResolver.RestRequestResult.RestRequestType;
import util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RestRequestResolver {

    private static final Pattern wishlistIdPattern = Pattern.compile("/([0-9])/wishlist/([a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12})");
    private static final Pattern wishlistPattern = Pattern.compile("/([0-9])/wishlist");
    private static final Pattern customerPattern = Pattern.compile("/([0-9]*)");

    static RestRequestResult resolve(final String pathInfo) {
        if (StringUtils.isNullOrEmpty(pathInfo)) {
            return new RestRequestResult(RestRequestType.NONE);
        }

        // Check for wishlist/id case first, since most generic patterns would also match.
        Matcher matcher = wishlistIdPattern.matcher(pathInfo);
        if (matcher.find()) {
            final String customerId = matcher.group(1);
            final String productId = matcher.group(2);
            return new RestRequestResult(RestRequestType.PRODUCT, Long.valueOf(customerId), productId);
        }

        matcher = wishlistPattern.matcher(pathInfo);
        if (matcher.find()) {
            final String customerId = matcher.group(1);
            return new RestRequestResult(RestRequestType.PRODUCT, Long.valueOf(customerId));
        }

        matcher = customerPattern.matcher(pathInfo);
        if (matcher.find()) {
            final String customerId = matcher.group(1);
            if (!StringUtils.isNullOrEmpty(customerId)) {
                return new RestRequestResult(RestRequestType.CUSTOMER, Long.valueOf(customerId));
            }
            return new RestRequestResult(RestRequestType.CUSTOMER);
        }

        return new RestRequestResult(RestRequestType.NONE);
    }

    public static class RestRequestResult {

        enum RestRequestType { CUSTOMER, PRODUCT, NONE }

        private Long customerId;
        private String productId;
        private RestRequestType type;

        RestRequestResult(final RestRequestType type) {
            this.type = type;
        }

        RestRequestResult(final RestRequestType type, Long customerId) {
            this.type = type;
            this.customerId = customerId;
        }

        RestRequestResult(final RestRequestType type, Long customerId, final String productId) {
            this.type = type;
            this.customerId = customerId;
            this.productId = productId;
        }

        public Long getCustomerId() {
            return customerId;
        }

        public String getProductId() {
            return productId;
        }

        RestRequestType getType() {
            return type;
        }
    }
}