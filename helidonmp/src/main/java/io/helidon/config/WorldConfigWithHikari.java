package io.helidon.config;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import io.helidon.services.RandomNumberService;
import io.helidon.interfaces.WorldInterface;

/**
 * Class to implement methods to access World Service using Hikari
 */
public class WorldConfigWithHikari extends DatabaseConfigWithHikari implements WorldInterface {
  private static String TABLE_NAME = "World";

  /**
   * Method to return a row from the table world based on the id provided
   * 
   * @param id where id is the primary key in the table TABLE_NAME
   * @return JsonObject for the executed query
   * @throws SQLException
   */
  public JsonObject select(Connection conn, int id) throws SQLException {
    // SQL statement to be prepared
    String sql = String.format("SELECT id, randomNumber FROM %s WHERE id = ?;", TABLE_NAME);
 
    // Create a prepared statement 
    PreparedStatement stmt = conn.prepareStatement(sql);
    
    // Execute the statement
    stmt.setInt(1, id);
    ResultSet rs = stmt.executeQuery();
    
    // Build a JSON reponse
    JsonObjectBuilder jsonObject = Json.createObjectBuilder();
    if (rs.next()) {
      jsonObject.add("id", rs.getInt("id"));
      jsonObject.add("randomNumber", rs.getInt("randomNumber"));
    }
    
    // Close the statement
    stmt.close();
    return jsonObject.build();
  } 
  
  /**
   * Method to make updates to the database
   * Given an id, update the randomNumber corresponding to it
   * 
   * @param id where id is the primary key in the database TABLE_NAME
   * @return void
   * @throws SQLException
   * @throws ClassNotFoundException
   */
  public void update(Connection conn, int id) throws SQLException {
    // SQL statement to be prepared
    String sql = String.format("UPDATE %s SET randomNumber = ? WHERE id = ?;", TABLE_NAME);
    
    // Prepare a statement
    PreparedStatement stmt = conn.prepareStatement(sql);

    // Execute the statement
    stmt.setInt(1, RandomNumberService.GetRandomNumber());
    stmt.setInt(2, id);
    int rowsModified = stmt.executeUpdate();
    assert(rowsModified == 1);

    // Close the statement
    stmt.close();
  }
}