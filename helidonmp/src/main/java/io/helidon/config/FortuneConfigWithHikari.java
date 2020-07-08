package io.helidon.config;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import io.helidon.interfaces.FortuneInterface;
import io.helidon.models.FortuneModel;

public class FortuneConfigWithHikari extends DatabaseConfigWithHikari implements FortuneInterface {
  private static String TABLE_NAME = "Fortune";

  /**
   * Method to return all the rows from the table fortunes
   * 
   * @return JsonObject for the executed query
   * @throws SQLException
   * @throws ClassNotFoundException
   */
  public List<FortuneModel> selectAll(Connection conn) throws SQLException {
    // Prepare and cache the statement
    String sql = String.format("SELECT id, message FROM %s;", TABLE_NAME);
    PreparedStatement stmt = conn.prepareStatement(sql);

    // Execute the query
    ResultSet rs = stmt.executeQuery();

    // Build a list as response
    List<FortuneModel> fortunesList = new ArrayList<>();
    while (rs.next()) {
      fortunesList.add(new FortuneModel(rs.getInt("id"), rs.getString("message")));
    }

    // Close the statement
    stmt.close();
    return fortunesList;
  }
}