package io.helidon.interfaces;

import java.sql.Connection;
import java.sql.SQLException;

import javax.json.JsonObject;

/**
 * Interface to provide configurations for the World table
 */
public interface WorldInterface extends DatabaseInterface {
  abstract JsonObject select(Connection conn, int id) throws SQLException;
  abstract void update(Connection conn, int id) throws SQLException;
}