package io.helidon.services;

import java.io.IOException;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.nio.charset.StandardCharsets;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

import io.helidon.config.FortuneConfigWithHikari;
import io.helidon.config.FortuneConfigWithoutHikari;
import io.helidon.interfaces.FortuneInterface;
import io.helidon.models.FortuneModel;

/**
 * Implements Fortunes benchmark
 */
@RequestScoped
@Path("/")
public class FortuneService {
  private static FortuneInterface fortuneConfig = FortuneInterface.DB_CONFIG.getUseHikari() ? 
  new FortuneConfigWithHikari() : new FortuneConfigWithoutHikari();

  /**
   * Fortunes specific test
   * 
   * @return Response (JSON format)
   * @throws SQLException
   * @throws ClassNotFoundException
   * @throws IOException
   */
  @GET
  @Path("/fortunes")
  @Produces(MediaType.TEXT_HTML + "; charset=utf-8")
  public static Response Fortunes() throws ClassNotFoundException, SQLException, IOException {
    // Create the connection to the database
    Connection conn = fortuneConfig.connect();

    // Get all the messages from the database along-with the additional message
    List<FortuneModel> fortunesList = fortuneConfig.selectAll(conn);
    fortunesList.add(new FortuneModel(0, "Additional fortune added at request time."));

    // Sort the list of fortune messages
    fortunesList = SortFortunes(fortunesList);
    
    // Create a context to return in the response
    Map<String, List<FortuneModel>> context = new HashMap<>();
    context.put("fortunes", fortunesList);

    // Return response through moustache template
    MustacheFactory mustacheFactory = new DefaultMustacheFactory();
    Mustache mustache = mustacheFactory.compile("fortunes.mustache");
    StringWriter writer = new StringWriter();
    mustache.execute(writer, context).flush();

    // Create response from the bytes and replace initial quotes from every message
    String response = writer.toString();

    // Close the connection
    conn.close();

    // Return the response with appropriate headers
    ResponseBuilder responseBuilder = Response.ok(response);
    return responseBuilder.header("Server", "helidon").encoding(StandardCharsets.UTF_8.name()).build();
  }

  /**
   * Sort list of fortune objects based on their message content lexicographically
   */
  private static List<FortuneModel> SortFortunes(List<FortuneModel> fortunesList) {
    Collections.sort(fortunesList, new Comparator<FortuneModel>() {
      @Override
      public int compare(FortuneModel a, FortuneModel b) {
        String str_A = a.message;
        String str_B = b.message;
        return str_A.compareTo(str_B);
      }
    });
    return fortunesList;
  }
}