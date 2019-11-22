package com.luizalabs.rest;

import com.luizalabs.mock.MockBufferedReader;
import com.luizalabs.mock.MockResourceHandler;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AbstractResourceHandlerTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    private MockResourceHandler handler;

    @BeforeEach
    void init() {
        handler = new MockResourceHandler();
    }

    @Test
    void getRequestAsJsonNullRequest() {
        assertThrows(NullPointerException.class,
                () -> handler.getRequestAsJson(null, getClass()),
                "HttpServletRequest request cannot be null.");
    }

    @Test
    void getRequestAsJsonNullType() {
        assertThrows(NullPointerException.class,
                () -> handler.getRequestAsJson(request, null),
                "Class type cannot be null.");
    }

    @Test
    void getRequestAsJsonIOException() throws IOException {
        when(request.getReader()).thenReturn(new MockBufferedReader());
        assertNull(handler.getRequestAsJson(request, Object.class));
    }

    @Test
    void getRequestAsJsonSuccess() throws IOException {
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader("Resource")));
        assertNotNull(handler.getRequestAsJson(request, Object.class));
    }

    @Test
    void sendResponseAsJsonNullResponse() {
        assertThrows(NullPointerException.class,
                () -> handler.sendResponseAsJson(null, new Object(), 1),
                "HttpServletResponse response cannot be null.");
    }

    @Test
    void sendResponseAsJsonNullEntity() {
        assertThrows(NullPointerException.class,
                () -> handler.sendResponseAsJson(response, null, 1),
                "Response entity cannot be null.");
    }

    @Test
    void sendResponseAsJsonSuccess() throws IOException {
        when(response.getOutputStream()).thenReturn(new MockServletOutputStream());
        handler.sendResponseAsJson(response, new Object(), HttpServletResponse.SC_OK);
    }
}