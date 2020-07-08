package io.helidon.services;

import java.sql.Connection;
import java.sql.SQLException;

import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import io.helidon.config.WorldConfigWithHikari;
import io.helidon.config.WorldConfigWithoutHikari;
import io.helidon.interfaces.WorldInterface;

/**
 * Implements World benchmarks
 */
@RequestScoped
@Path("/")
public class WorldService {
  private static WorldInterface worldConfig = WorldInterface.DB_CONFIG.getUseHikari() ? 
  new WorldConfigWithHikari() : new WorldConfigWithoutHikari();

  /**
   * Fetch a record from the database
   * 
   * @return Response (JSON format)
   * @throws SQLException
   * @throws ClassNotFoundException
   */
  @GET
  @Path("/db")
  @Produces(MediaType.APPLICATION_JSON)
  public static Response SingleQuery() throws ClassNotFoundException, SQLException {
    // Create a connection to the database
    Connection conn = worldConfig.connect();
    
    // Query the database and fetch the JSON object
    JsonObject jsonObject = worldConfig.select(conn, RandomNumberService.GetRandomNumber());
    
    // Close the connection
    conn.close();

    // Return the response with appropriate headers
    Response.ResponseBuilder responseBuilder = Response.ok(jsonObject);
    return responseBuilder.header("Server", "helidon").build();
  }
  
  
  /**
   * Fetch multiple records from the database
   * 
   * @param queries - number of rows to fetch
   * @return Response (JSON format)
   * @throws SQLException
   * @throws ClassNotFoundException
   */
  @GET
  @Path("/queries")
  @Produces(MediaType.APPLICATION_JSON)
  public static Response MultipleQueries(@DefaultValue("1") @QueryParam("queries") String queries)
  throws ClassNotFoundException, SQLException {
    // count = number of queries = [1 .. 500] (both inclusive)
    int count = 1;
    try {
      count = Integer.parseInt(queries);
    } catch (NumberFormatException e) {
      count = 1;
    }
    count = (count <= 0) ? 1 : count;
    count = (count > 500) ? 500 : count;
    
    // Create a connection to the database
    Connection conn = worldConfig.connect();
    
    // Generate the JSON object
    JsonArrayBuilder jsonArray = Json.createArrayBuilder();
    for (int i = 0; i < count; i++) {
      jsonArray.add(worldConfig.select(conn, RandomNumberService.GetRandomNumber()));
    }

    // Close the connection
    conn.close();

    // Return the response with appropriate headers
    Response.ResponseBuilder responseBuilder = Response.ok(jsonArray.build());
    return responseBuilder.header("Server", "helidon").build();
  }
  
  /**
   * Update a record in the database
   * 
   * @param queries - number of updates to be executed
   * @return Response (JSON format)
   * @throws SQLException
   * @throws ClassNotFoundException
   */
  @GET
  @Path("/updates")
  @Produces(MediaType.APPLICATION_JSON)
  public static Response MultipleUpdates(@DefaultValue("1") @QueryParam("queries") String queries)
      throws ClassNotFoundException, SQLException {
    // count = number of queries = [1 .. 500] (both inclusive)
    int count = 1;
    try {
      count = Integer.parseInt(queries);
    } catch (NumberFormatException e) {
      count = 1;
    }
    count = (count <= 0) ? 1 : count;
    count = (count > 500) ? 500 : count;
    
    // Create a connection to the database
    Connection conn = worldConfig.connect();

    // Generate the JSON object
    JsonArrayBuilder jsonArray = Json.createArrayBuilder();
    for (int i = 0; i < count; i++) {
      // Generate a random number and execute update
      int id = RandomNumberService.GetRandomNumber();
      worldConfig.update(conn, id);
      jsonArray.add(worldConfig.select(conn, id));
    }

    // Close the connection
    conn.close();
    
    // Return the response with appropriate headers
    ResponseBuilder responseBuilder = Response.ok(jsonArray.build());
    return responseBuilder.header("Server", "helidon").build();
  }
}