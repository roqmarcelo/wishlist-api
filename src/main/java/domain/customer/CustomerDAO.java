package domain.customer;

import infrastructure.database.DataSource;

import java.sql.*;

class CustomerDAO {

    private static final String SELECT_CUSTOMER_SQL = "SELECT name, email FROM customer WHERE id = ?";
    private static final String INSERT_CUSTOMER_SQL = "INSERT INTO customer(name, email) VALUES (?, ?)";
    private static final String UPDATE_CUSTOMER_SQL = "UPDATE customer SET name = ?, email = ? WHERE id = ?";
    private static final String DELETE_CUSTOMER_SQL = "DELETE FROM customer WHERE id = ?";
    private static final String EXISTS_CUSTOMER_SQL = "SELECT count(id) FROM customer WHERE email = ?";

    Customer find(final Long id) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_CUSTOMER_SQL)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    return null;
                }
                final String name = resultSet.getString("name");
                final String email = resultSet.getString("email");
                return new Customer(id, name, email);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    Long save(final Customer customer) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_CUSTOMER_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, customer.getName());
            statement.setString(2, customer.getEmail());
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

    boolean update(final Customer customer) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_CUSTOMER_SQL)) {
            statement.setString(1, customer.getName());
            statement.setString(2, customer.getEmail());
            statement.setLong(3, customer.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    boolean delete(final Long id) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_CUSTOMER_SQL)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    boolean existsBy(final String email) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(EXISTS_CUSTOMER_SQL)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() && resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}