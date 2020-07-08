package io.helidon.models;

/**
 * Class to store fortune objects
 */
public class FortuneModel {
  public int id;
  public String message;

  public FortuneModel(int id, String message) {
    this.id = id;
    this.message = message;
  }
}