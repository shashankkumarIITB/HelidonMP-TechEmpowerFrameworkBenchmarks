package io.helidon.services;

import java.util.Random;

/**
 * Class to generate random numbers
 */
public class RandomNumberService {
  // Instance of random number generator
  private static Random random = new Random();
  
  /**
   * Generate a random number in the range [1 .. 10000] (both inclusive)
   * 
   * @return random integer
   */
  public static int GetRandomNumber() {
    return random.nextInt(10000) + 1;
  }
}