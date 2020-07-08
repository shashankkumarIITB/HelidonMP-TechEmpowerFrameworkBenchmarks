package io.helidon.services;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Implements a class that returns a plaintext response
 */
@RequestScoped
@Path("/")
public class PlaintextService {
  
  /**
   * Returns "Hello, World!" in plaintext format
   * along-with appropriate headers
   * @return Response (Plaintext format)
   */
  @GET
  @Path("/plaintext")
  @Produces(MediaType.TEXT_PLAIN)
  public static Response GetPlaintext() {
    Response.ResponseBuilder responseBuilder = Response.ok("Hello, World!");
    return responseBuilder.header("Server", "helidon").build();
  }
}