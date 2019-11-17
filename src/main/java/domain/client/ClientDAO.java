package domain.client;

import infrastructure.database.DataSource;

import java.sql.*;

class ClientDAO {

    private static final String SELECT_CLIENT_SQL = "SELECT name, email FROM client WHERE id = ?";
    private static final String INSERT_CLIENT_SQL = "INSERT INTO client(name, email) VALUES (?, ?)";
    private static final String UPDATE_CLIENT_SQL = "UPDATE client SET name = ?, email = ? WHERE id = ?";
    private static final String DELETE_CLIENT_SQL = "DELETE FROM client WHERE id = ?";
    private static final String EXISTS_CLIENT_SQL = "SELECT count(id) FROM client WHERE email = ?";

    Client find(final Long id) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_CLIENT_SQL)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    return null;
                }
                final String name = resultSet.getString("name");
                final String email = resultSet.getString("email");
                return new Client(id, name, email);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    Long save(final Client client) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_CLIENT_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, client.getName());
            statement.setString(2, client.getEmail());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Could not create client.");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (!generatedKeys.next()) {
                    throw new SQLException("Could not create client.");
                }
                return generatedKeys.getLong(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    boolean update(final Client client) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_CLIENT_SQL)) {
            statement.setString(1, client.getName());
            statement.setString(2, client.getEmail());
            statement.setLong(3, client.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    boolean delete(final Long id) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_CLIENT_SQL)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    boolean existsBy(final String email) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(EXISTS_CLIENT_SQL)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() && resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}