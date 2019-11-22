package com.luizalabs.rest;

import com.luizalabs.rest.RestRequestResolver.RestRequestResult;
import com.luizalabs.rest.RestRequestResolver.RestRequestResult.RestRequestType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RestRequestResolverTest {

    @Test
    void resolveEmptyPathInfo() {
        RestRequestResult result = RestRequestResolver.resolve(null);
        assertEquals(result.getType(), RestRequestType.NONE);
    }

    @Test
    void resolveWishlistIdPattern() {
        RestRequestResult result = RestRequestResolver.resolve("/1/wishlist/1bf0f365-fbdd-4e21-9786-da459d78dd1f");
        assertEquals(result.getType(), RestRequestType.PRODUCT);
        assertNotNull(result.getProductId());
    }

    @Test
    void resolveWishlistPattern() {
        RestRequestResult result = RestRequestResolver.resolve("/1/wishlist/");
        assertEquals(result.getType(), RestRequestType.PRODUCT);
        assertNull(result.getProductId());
    }

    @Test
    void resolveCustomerPattern() {
        RestRequestResult result = RestRequestResolver.resolve("/");
        assertEquals(result.getType(), RestRequestType.CUSTOMER);
        assertNull(result.getCustomerId());
    }

    @Test
    void resolveCustomerIdPattern() {
        RestRequestResult result = RestRequestResolver.resolve("/1");
        assertEquals(result.getType(), RestRequestType.CUSTOMER);
        assertNotNull(result.getCustomerId());
    }

    @Test
    void resolveUnknownPattern() {
        RestRequestResult result = RestRequestResolver.resolve("null");
        assertNotNull(result);
        assertEquals(result.getType(), RestRequestType.NONE);
    }
}