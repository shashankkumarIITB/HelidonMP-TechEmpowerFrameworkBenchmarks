package io.helidon;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;
import io.helidon.microprofile.server.Server;

/**
 * The application main class
 * Step up logging
 * Start the server
 */
public final class Main {

  /** 
   * Main class cannot be instantiated
   */
  // Why ? (Security concerns ?)
  private Main() {}

  /** 
   * Application main entry point
   * @param args command line arguments
   * @throws IOException if there are problems with loggins properties
   */
  public static void main(final String[] args) throws IOException{
    setupLogging();

    /**
     * Start the server
     */
    final Server server = startServer();
    System.out.printf("Server started at http://localhost:%d\n", server.port());
  }
  
  /**
   * Method to create and start a server
   * @return returns a server instance
   */
  private static Server startServer() {
    return Server.create().start();
  }
  
  /**
   * Setup logging from logging.properties file 
   */
  // How?
  private static void setupLogging() throws IOException{
    try (InputStream is = Main.class.getResourceAsStream("/logging.properties")) {
      LogManager.getLogManager().readConfiguration(is);
    }
  }

}

