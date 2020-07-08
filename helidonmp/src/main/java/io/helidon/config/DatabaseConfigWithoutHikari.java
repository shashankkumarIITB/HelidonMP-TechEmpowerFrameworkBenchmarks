package io.helidon.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import io.helidon.interfaces.DatabaseInterface;

/**
 * Class to configure database
 */
abstract class DatabaseConfigWithoutHikari implements DatabaseInterface {
  private static String driverClass = "org.postgresql.Driver";

  static {
    // Initialize driver
    try {
      Class.forName(driverClass);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * Method to provide connection to the database
   * 
   * @return Connection
   */  
  public Connection connect() {
    Connection conn = null;
    try {
      conn = DriverManager.getConnection(DB_CONFIG.getUrl() + DB_CONFIG.getName(), DB_CONFIG.getUser(),
          DB_CONFIG.getPassword());
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return conn;
  }
}