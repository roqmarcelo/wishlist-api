package com.luizalabs.domain.customer;

import com.luizalabs.infrastructure.exception.NotFoundException;
import com.luizalabs.mock.MockBufferedReader;
import com.luizalabs.mock.MockServletOutputStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import static com.luizalabs.rest.RestRequestResolver.RestRequestResult;
import static com.luizalabs.rest.RestRequestResolver.RestRequestResult.RestRequestType;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerResourceHandlerTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private CustomerService customerService;

    private CustomerResourceHandler customerResourceHandler;

    @BeforeEach
    void init() {
        customerResourceHandler = new CustomerResourceHandler(customerService);
    }

    @Test
    void handleGetNullResult() {
        assertThrows(NullPointerException.class,
                () -> customerResourceHandler.handleGet(null, response),
                "RestRequestResult result cannot be null.");
    }

    @Test
    void handleGetNullResponse() {
        assertThrows(NullPointerException.class,
                () -> customerResourceHandler.handleGet(new RestRequestResult(RestRequestType.NONE), null),
                "HttpServletResponse response cannot be null.");
    }

    @Test
    void handleGetNotFoundException () {
        when(customerService.find(1L)).thenThrow(NotFoundException.class);
        RestRequestResult result = new RestRequestResult(RestRequestType.CUSTOMER, 1L);
        customerResourceHandler.handleGet(result, response);
    }

    @Test
    void handleGet() throws IOException {
        when(customerService.find(1L)).thenReturn(new Customer(1L, "Customer", "valid@rmail.com"));
        when(response.getOutputStream()).thenReturn(new MockServletOutputStream());
        RestRequestResult result = new RestRequestResult(RestRequestType.CUSTOMER, 1L);
        customerResourceHandler.handleGet(result, response);
    }

    @Test
    void handlePostNullRequest() {
        assertThrows(NullPointerException.class,
                () -> customerResourceHandler.handlePost(null, null, response),
                "HttpServletRequest request cannot be null.");
    }

    @Test
    void handlePostNullResponse() {
        assertThrows(NullPointerException.class,
                () -> customerResourceHandler.handlePost(null, request, null),
                "HttpServletResponse response cannot be null.");
    }

    @Test
    void handlePostNullCustomer() throws IOException {
        when(request.getReader()).thenReturn(new MockBufferedReader());
        customerResourceHandler.handlePost(null, request, response);
    }

    @Test
    void handlePostCreated() throws IOException {
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader("Resource")));
        customerResourceHandler.handlePost(null, request, response);
    }

    @Test
    void handlePutNullResult() {
        assertThrows(NullPointerException.class,
                () -> customerResourceHandler.handlePut(null, request, response),
                "RestRequestResult result cannot be null.");
    }

    @Test
    void handlePutNullRequest() {
        assertThrows(NullPointerException.class,
                () -> customerResourceHandler.handlePut(new RestRequestResult(RestRequestType.NONE), null, response),
                "HttpServletRequest request cannot be null.");
    }

    @Test
    void handlePutNullResponse() {
        assertThrows(NullPointerException.class,
                () -> customerResourceHandler.handlePut(new RestRequestResult(RestRequestType.NONE), request, null),
                "HttpServletResponse response cannot be null.");
    }

    @Test
    void handlePutSuccess() throws IOException {
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader("Resource")));
        RestRequestResult result = new RestRequestResult(RestRequestType.CUSTOMER, 1L);
        customerResourceHandler.handlePut(result, request, response);
    }

    @Test
    void handleDeleteNullResult() {
        assertThrows(NullPointerException.class,
                () -> customerResourceHandler.handleDelete(null, response),
                "RestRequestResult result cannot be null.");
    }

    @Test
    void handleDeleteNullResponse() {
        assertThrows(NullPointerException.class,
                () -> customerResourceHandler.handleDelete(new RestRequestResult(RestRequestType.NONE), null),
                "HttpServletResponse response cannot be null.");
    }

    @Test
    void handleDeleteNoContent() {
        when(customerService.delete(1L)).thenReturn(true);
        RestRequestResult result = new RestRequestResult(RestRequestType.CUSTOMER, 1L);
        customerResourceHandler.handleDelete(result, response);
    }

    @Test
    void handleDeleteNotFound() {
        when(customerService.delete(1L)).thenReturn(false);
        RestRequestResult result = new RestRequestResult(RestRequestType.CUSTOMER, 1L);
        customerResourceHandler.handleDelete(result, response);
    }
}