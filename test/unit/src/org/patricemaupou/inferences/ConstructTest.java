/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.patricemaupou.inferences;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 * TODO : construire le pattern parenthèses à partir de SEQ ou autres
 * ex : Construct("seq", Type.SEQ, "\\(a,b\\)", ",b", vars, parenthesis="()", 5, -1); (redondant avec -1)
 *      plus = new Construct("+", R, "a\\+b", vars, cancel=SEQ, 10, 0); (redondant avec 0)
 * @author Patrice
 */
public class ConstructTest { 

  private static InferencesSuite suite;
  
  public ConstructTest() {
  }

  @BeforeClass
  public static void setUpClass() { 
    suite = InferencesSuite.suite;
  }

  @AfterClass
  public static void tearDownClass() {
  }

  @Before
  public void setUp() {
  }

  @After
  public void tearDown() {  
  }

  /**
   * Test of searchExpr method, of class Construct.
   */
  @Ignore
  @Test
  public void testSearchExpr() { 
    System.out.println("searchExpr");
    String[] entries = new String[]{"(a+b)*c", "a*(b-c)", "(a+3)*(5+(b+1))", "5+(5+x+1)", "(1+1,2,3,4)", "a+b*(1+c)", 
      "a+b*c+b*d", "x+12*3+7*4", "3+f(x)", "2*1+f(3)"};
    String[] expResults = new String[]{"(a+b)*c", "a*(b-c)", "(a+3)*(5+(b+1))", "5+(5+x+1)", "(1+1,2,3,4)", "a+b*(1+c)", 
      "a+b*c+b*d", "x+12*3+7*4", "3+f(x)", "2*1+f(3)"};
  }

  /**
   * Test of toString method, of class Construct.
   */
  @Ignore
  @Test
  public void testToString() {
    System.out.println("toString");
    System.out.println("termes de base :");
    suite.constructs.forEach(c->System.out.println("\t"+c.toString()));
  }

}
