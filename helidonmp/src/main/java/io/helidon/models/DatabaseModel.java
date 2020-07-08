package io.helidon.models;

import java.io.IOException;
import java.io.InputStream;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * POJO to store database properties
 */
public class DatabaseModel {
  private String name;
  private String user;
  private String password;
  private String url;
  private Boolean useHikari;

  @JsonCreator
  public DatabaseModel(@JsonProperty("name") String name, @JsonProperty("user") String user,
  @JsonProperty("password") String password, @JsonProperty("url") String url, @JsonProperty("useHikari") Boolean useHikari) {
    this.name = name;
    this.user = user;
    this.password = password;
    this.url = url;
    this.useHikari = useHikari;
  }

  public String getName() {
    return this.name;
  }

  public String getUser() {
    return this.user;
  }
  
  public String getPassword() {
    return this.password;
  }
  
  public String getUrl() {
    return this.url;
  }

  public Boolean getUseHikari() {
    return this.useHikari;
  }

  /**
   * Read the database configuration file
   * 
   * @param filename
   * @return
   * @throws IOException
   * @throws JsonMappingException
   * @throws JsonParseException
   */
  public static DatabaseModel readConfig(String filename) {
    System.out.println("DatabaseModel - Reading config file.");
    ObjectMapper mapper = new ObjectMapper();
    InputStream inputStream = DatabaseModel.class.getResourceAsStream(filename);
    DatabaseModel dbModel = null;
    try {
      dbModel = mapper.readValue(inputStream, DatabaseModel.class);
    } catch (IOException e) {
      e.printStackTrace();
    }
    System.out.println("DatabaseModel - Returning database at " + dbModel.getUrl());
    return dbModel;
  }
}