package infrastructure.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

public class DataSource {

    private static final String PROPERTY_FILE_NAME = "datasource.properties";
    private static HikariDataSource dataSource;

    static {
        InputStream inputStream = DataSource.class.getClassLoader().getResourceAsStream(PROPERTY_FILE_NAME);
        Objects.requireNonNull(inputStream, String.format("Could not retrieve configuration file %s", PROPERTY_FILE_NAME));
        try {
            Properties properties = new Properties();
            properties.load(inputStream);
            HikariConfig config = new HikariConfig(properties);
            dataSource = new HikariDataSource(config);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private DataSource() {
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}