package ar.edu.itba.paw.services;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for enabling Jacoco Coverage in case there are no other tests in this module
 */
public class JacocoCoverageEnableModuleTest extends TestCase {
  /**
   * Create the test case
   */
  public JacocoCoverageEnableModuleTest(String testName) {
      super(testName);
  }

  /**
   * @return the suite of tests being tested
   */
  public static Test suite() {
      return new TestSuite(JacocoCoverageEnableModuleTest.class);
  }

  public void testApp() {
      assertTrue(true);
  }
}
