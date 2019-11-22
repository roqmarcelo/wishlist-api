package com.luizalabs.rest;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.luizalabs.rest.RestRequestResolver.RestRequestResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public abstract class AbstractResourceHandler {

    private final Gson gson = new Gson();

    public <T> T getRequestAsJson(HttpServletRequest request, Class<T> type) throws IOException {
        Objects.requireNonNull(request, "HttpServletRequest request cannot be null.");
        Objects.requireNonNull(type, "Class type cannot be null.");

        try {
            return gson.fromJson(request.getReader(), type);
        } catch (JsonSyntaxException e) {
            return null;
        }
    }

    public <T> void sendResponseAsJson(HttpServletResponse response, T entity, int status) throws IOException {
        Objects.requireNonNull(response, "HttpServletResponse response cannot be null.");
        Objects.requireNonNull(entity, "Response entity cannot be null.");

        response.setContentType("application/json");
        response.setStatus(status);

        response.getOutputStream().print(gson.toJson(entity));
        response.getOutputStream().flush();
    }

    public abstract void handleGet(RestRequestResult result, HttpServletResponse response);

    public abstract void handlePost(RestRequestResult result, HttpServletRequest request, HttpServletResponse response) throws IOException;

    public abstract void handlePut(RestRequestResult result, HttpServletRequest request, HttpServletResponse response) throws IOException;

    public abstract void handleDelete(RestRequestResult result, HttpServletResponse response);
}