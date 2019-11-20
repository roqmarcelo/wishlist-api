package com.luizalabs.domain.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.luizalabs.rest.RestRequestResolver.RestRequestResult;
import static com.luizalabs.rest.RestRequestResolver.RestRequestResult.RestRequestType;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class CustomerResourceHandlerTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    private CustomerResourceHandler customerResourceHandler;

    @BeforeEach
    void init() {
        customerResourceHandler = new CustomerResourceHandler(null);
    }

    @Test
    void handleGet() {
        assertThrows(NullPointerException.class,
                () -> customerResourceHandler.handleGet(null, response),
                "RestRequestResult result cannot be null.");
        assertThrows(NullPointerException.class,
                () -> customerResourceHandler.handleGet(new RestRequestResult(RestRequestType.NONE), null),
                "HttpServletResponse response cannot be null.");
    }

    @Test
    void handlePost() {
        assertThrows(NullPointerException.class,
                () -> customerResourceHandler.handlePost(null, null, response),
                "HttpServletRequest request cannot be null.");
        assertThrows(NullPointerException.class,
                () -> customerResourceHandler.handlePost(null, request, null),
                "HttpServletResponse response cannot be null.");
    }

    @Test
    void handlePut() {
        assertThrows(NullPointerException.class,
                () -> customerResourceHandler.handlePut(null, request, response),
                "RestRequestResult result cannot be null.");
        assertThrows(NullPointerException.class,
                () -> customerResourceHandler.handlePut(new RestRequestResult(RestRequestType.NONE), null, response),
                "HttpServletRequest request cannot be null.");
        assertThrows(NullPointerException.class,
                () -> customerResourceHandler.handlePut(new RestRequestResult(RestRequestType.NONE), request, null),
                "HttpServletResponse response cannot be null.");
    }

    @Test
    void handleDelete() {
        assertThrows(NullPointerException.class,
                () -> customerResourceHandler.handleDelete(null, response),
                "RestRequestResult result cannot be null.");
        assertThrows(NullPointerException.class,
                () -> customerResourceHandler.handleDelete(new RestRequestResult(RestRequestType.NONE), null),
                "HttpServletResponse response cannot be null.");
    }
}