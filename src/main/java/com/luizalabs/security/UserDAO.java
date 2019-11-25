package com.luizalabs.security;

import com.luizalabs.infrastructure.database.DataSource;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class UserDAO {

    private static final String SELECT_USER_SQL = "SELECT username FROM loginuser WHERE username = ? and password = ?";

    private final DataSource dataSource;

    @Inject
    UserDAO(final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    String find(String username, String password) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_USER_SQL)) {
            statement.setString(1, username);
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    return null;
                }
                return resultSet.getString("username");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}