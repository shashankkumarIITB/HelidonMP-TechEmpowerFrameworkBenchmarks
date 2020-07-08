package io.helidon.interfaces;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import io.helidon.models.FortuneModel;

public interface FortuneInterface extends DatabaseInterface{
  abstract List<FortuneModel> selectAll(Connection conn) throws SQLException; 
}