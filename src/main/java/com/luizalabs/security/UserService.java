package com.luizalabs.security;

import javax.inject.Inject;

class UserService {

    private final UserDAO userDAO;

    @Inject
    UserService(final UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    String find(String username, String password) {
        return userDAO.find(username, password);
    }
}