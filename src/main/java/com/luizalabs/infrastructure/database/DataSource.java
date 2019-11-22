package com.luizalabs.infrastructure.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.inject.Singleton;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

@Singleton
public class DataSource {

    private static final String PROPERTY_FILE_NAME = "datasource.properties";

    private final HikariDataSource dataSource;

    DataSource() throws IOException {
        Properties properties = loadProperties();
        HikariConfig config = new HikariConfig(properties);
        dataSource = new HikariDataSource(config);
    }

    private Properties loadProperties() throws IOException {
        ClassLoader classLoader = DataSource.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(PROPERTY_FILE_NAME);
        Objects.requireNonNull(inputStream, String.format("Could not retrieve configuration file %s", PROPERTY_FILE_NAME));
        Properties properties = new Properties();
        properties.load(inputStream);
        return properties;
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}