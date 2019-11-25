package com.luizalabs.security;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.luizalabs.util.JWTUtils;
import com.luizalabs.util.PasswordUtils;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.luizalabs.util.StringUtils.isNullOrEmpty;

@Singleton
public class AuthServlet extends HttpServlet {

    private final Gson gson = new Gson();

    private final UserService userService;

    @Inject
    AuthServlet(final UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Credentials credentials = extractCredentialsFromRequest(request);
        if (credentials == null || isNullOrEmpty(credentials.getUsername()) || isNullOrEmpty(credentials.getPassword())) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        String username = credentials.getUsername();
        String password = PasswordUtils.digestPassword(credentials.getPassword());
        String retrievedUsername = userService.find(username, password);
        if (isNullOrEmpty(retrievedUsername)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        String token = JWTUtils.create(retrievedUsername);
        response.setHeader(JWTUtils.TOKEN_HEADER, String.format("Bearer %s", token));
    }

    private Credentials extractCredentialsFromRequest(HttpServletRequest request) throws IOException {
        try {
            return gson.fromJson(request.getReader(), Credentials.class);
        } catch (JsonSyntaxException e) {
            return null;
        }
    }
}