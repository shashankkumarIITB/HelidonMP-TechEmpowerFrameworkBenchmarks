package io.helidon.services;

import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Implements a class that returns a JSON response
 */
@RequestScoped
@Path("/")
public class JsonService {
  /**
   * Returns "Hello, World!" in JSON format along-with appropriate headers
   * 
   * @return Response (JSON format)
   */
  @GET
  @Path("/json")
  @Produces(MediaType.APPLICATION_JSON)
  public static Response GetJson() {
    JsonObject message = Json.createObjectBuilder().add("message", "Hello, World!").build();
    Response.ResponseBuilder responseBuilder = Response.ok(message);
    return responseBuilder.header("Server", "helidon").build();
  }
}