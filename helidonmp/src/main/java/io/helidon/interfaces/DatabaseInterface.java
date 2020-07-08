package io.helidon.interfaces;

import java.sql.Connection;

import io.helidon.models.DatabaseModel;

/**
 * Interface to provide database configurations
 */
public interface DatabaseInterface {
  // File storing the database configuration
  String CONFIG_FILE = "/config.json";

  // Set the database configuration
  DatabaseModel DB_CONFIG = DatabaseModel.readConfig(CONFIG_FILE);
  
  // Method to implement
  abstract Connection connect();
}