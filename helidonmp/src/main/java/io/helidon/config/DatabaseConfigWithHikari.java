package io.helidon.config;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import io.helidon.interfaces.DatabaseInterface;

public class DatabaseConfigWithHikari implements DatabaseInterface {
  private static HikariConfig hikariConfig;
  private static HikariDataSource hikariDS;

  // Initialization
  static {
    // Set up Hikari configuration
    hikariConfig = new HikariConfig();
    hikariConfig.setJdbcUrl(DB_CONFIG.getUrl() + DB_CONFIG.getName());
    hikariConfig.setUsername(DB_CONFIG.getUser());
    hikariConfig.setPassword(DB_CONFIG.getPassword());
    hikariConfig.setMaximumPoolSize(512);
    hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
    hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
    hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
    hikariDS = new HikariDataSource(hikariConfig);
  }

  /**
   * Method to provide connection to the database
   * 
   * @return Connection
   */
  public Connection connect() {
    Connection conn = null;
    try {
      conn = hikariDS.getConnection();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return conn;
  }
}