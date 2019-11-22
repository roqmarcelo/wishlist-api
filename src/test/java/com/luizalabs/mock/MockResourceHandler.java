package com.luizalabs.mock;

import com.luizalabs.rest.AbstractResourceHandler;
import com.luizalabs.rest.RestRequestResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MockResourceHandler extends AbstractResourceHandler {
    public void handleGet(RestRequestResolver.RestRequestResult result, HttpServletResponse response) {
    }

    public void handlePost(RestRequestResolver.RestRequestResult result, HttpServletRequest request, HttpServletResponse response) {
    }

    public void handlePut(RestRequestResolver.RestRequestResult result, HttpServletRequest request, HttpServletResponse response) {
    }

    public void handleDelete(RestRequestResolver.RestRequestResult result, HttpServletResponse response) {
    }
}