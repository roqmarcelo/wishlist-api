package com.luizalabs.domain.product;

import com.luizalabs.infrastructure.exception.NotFoundException;
import com.luizalabs.infrastructure.exception.ProductAlreadyAddedException;
import com.luizalabs.mock.MockServletOutputStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import static com.luizalabs.rest.RestRequestResolver.RestRequestResult;
import static com.luizalabs.rest.RestRequestResolver.RestRequestResult.RestRequestType;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductResourceHandlerTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private ProductService productService;

    private ProductResourceHandler productResourceHandler;

    @BeforeEach
    void init() {
        productResourceHandler = new ProductResourceHandler(productService);
    }

    @Test
    void handleGetNullResult() {
        assertThrows(NullPointerException.class,
                () -> productResourceHandler.handleGet(null, response),
                "RestRequestResult result cannot be null.");
    }

    @Test
    void handleGetNullResponse() {
        assertThrows(NullPointerException.class,
                () -> productResourceHandler.handleGet(new RestRequestResult(RestRequestType.NONE), null),
                "HttpServletResponse response cannot be null.");
    }

    @Test
    void handleGetNotFoundException() {
        when(productService.list(1L)).thenThrow(NotFoundException.class);
        RestRequestResult result = new RestRequestResult(RestRequestType.CUSTOMER, 1L);
        productResourceHandler.handleGet(result, response);
    }

    @Test
    void handleGetNullProductId() throws IOException {
        when(productService.list(1L)).thenReturn(new ArrayList<>());
        when(response.getOutputStream()).thenReturn(new MockServletOutputStream());
        RestRequestResult result = new RestRequestResult(RestRequestType.CUSTOMER, 1L);
        productResourceHandler.handleGet(result, response);
    }

    @Test
    void handleGetSuccess() throws IOException {
        Product product = new Product("1bf0f365-fbdd-4e21-9786-da459d78dd1f", "Title", "Image", 10.0, 1);
        when(productService.find(1L, "1bf0f365-fbdd-4e21-9786-da459d78dd1f")).thenReturn(product);
        when(response.getOutputStream()).thenReturn(new MockServletOutputStream());
        RestRequestResult result = new RestRequestResult(RestRequestType.CUSTOMER, 1L, "1bf0f365-fbdd-4e21-9786-da459d78dd1f");
        productResourceHandler.handleGet(result, response);
    }

    @Test
    void handlePostNullRequest() {
        assertThrows(NullPointerException.class,
                () -> productResourceHandler.handlePost(new RestRequestResult(RestRequestType.NONE), null, response),
                "HttpServletRequest request cannot be null.");
    }

    @Test
    void handlePostNullResponse() {
        assertThrows(NullPointerException.class,
                () -> productResourceHandler.handlePost(new RestRequestResult(RestRequestType.NONE), request, null),
                "HttpServletResponse response cannot be null.");
    }

    @Test
    void handlePostNotFoundException() throws IOException {
        RestRequestResult result = new RestRequestResult(RestRequestType.CUSTOMER, 1L, "1bf0f365-fbdd-4e21-9786-da459d78dd1f");
        doThrow(NotFoundException.class).when(productService).save(anyLong(), anyString());
        productResourceHandler.handlePost(result, request, response);
    }

    @Test
    void handlePostProductAlreadyAddedException () throws IOException {
        RestRequestResult result = new RestRequestResult(RestRequestType.CUSTOMER, 1L, "1bf0f365-fbdd-4e21-9786-da459d78dd1f");
        when(response.getOutputStream()).thenReturn(new MockServletOutputStream());
        doThrow(ProductAlreadyAddedException.class).when(productService).save(anyLong(), anyString());
        productResourceHandler.handlePost(result, request, response);
    }

    @Test
    void handlePostSuccess() throws IOException {
        RestRequestResult result = new RestRequestResult(RestRequestType.CUSTOMER, 1L, "1bf0f365-fbdd-4e21-9786-da459d78dd1f");
        productResourceHandler.handlePost(result, request, response);
    }

    @Test
    void handlePutNullResponse() {
        assertThrows(NullPointerException.class,
                () -> productResourceHandler.handlePut(new RestRequestResult(RestRequestType.NONE), request, null),
                "HttpServletResponse response cannot be null.");
    }

    @Test
    void handlePutMethodNotAllowed() {
        productResourceHandler.handlePut(new RestRequestResult(RestRequestType.NONE), request, response);
    }

    @Test
    void handleDeleteNullResult() {
        assertThrows(NullPointerException.class,
                () -> productResourceHandler.handleDelete(null, response),
                "RestRequestResult result cannot be null.");
    }

    @Test
    void handleDeleteNullResponse() {
        assertThrows(NullPointerException.class,
                () -> productResourceHandler.handleDelete(new RestRequestResult(RestRequestType.NONE), null),
                "HttpServletResponse response cannot be null.");
    }

    @Test
    void handleDeleteNoContent() {
        when(productService.delete(1L, "1bf0f365-fbdd-4e21-9786-da459d78dd1f")).thenReturn(true);
        RestRequestResult result = new RestRequestResult(RestRequestType.CUSTOMER, 1L, "1bf0f365-fbdd-4e21-9786-da459d78dd1f");
        productResourceHandler.handleDelete(result, response);
    }

    @Test
    void handleDeleteNotFound() {
        when(productService.delete(1L, "1bf0f365-fbdd-4e21-9786-da459d78dd1f")).thenReturn(false);
        RestRequestResult result = new RestRequestResult(RestRequestType.CUSTOMER, 1L, "1bf0f365-fbdd-4e21-9786-da459d78dd1f");
        productResourceHandler.handleDelete(result, response);
    }
}