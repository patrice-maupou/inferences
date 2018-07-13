/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.patricemaupou.inferences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author Patrice Maupou
 */
public class SectionTest {
  
  
  private static InferencesSuite suite;
  private final Section section;
  private final List<AbExpr> entries;
  
  public SectionTest() {
    section = new Section(0, "Section Test", null);
    suite.variables.forEach(section::addVar);
    suite.constructs.forEach(c->section.addConstruct(c));
    List<String> list = new ArrayList<>(Arrays.asList("a-2=b+1", "b=3*a", "b=3*a+3", "x-2=d+4+1", "d+4=3*x"));
    List<AbExpr> exprs = list.stream().map(s->AbExpr.decode(s, section.getConstructs(), section.getPars()))
            .collect(Collectors.toList());
    List<AbExpr> matchs = new ArrayList<>(exprs.subList(0, 2));
    List<AbExpr> results = new ArrayList<>(exprs.subList(2, 3));
    entries = new ArrayList<>(exprs.subList(3, 5));
    List<Variable> vs = new ArrayList<>(section.getVars().subList(0, 2));
    Infer infer = new Infer(vs, matchs, results, section, 0);
    section.getInfers().add(infer);
  }
  
  @BeforeClass
  public static void setUpClass() {
    suite = InferencesSuite.suite;
    if(suite == null) {
      suite = new InferencesSuite();
    }
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
   * Test of addConstruct method, of class Section.
   */
  @Ignore
  @Test
  public void testAddConstruct() {
    System.out.println("addConstruct");
    suite.constructs.forEach(c->section.addConstruct(c));
    testGetPars();
    testGetTypes();
  }

  /**
   * Test of getTypes method, of class Section.
   */
  @Test
  public void testGetTypes() {
    System.out.println("getTypes");
    List<Type> result = section.getTypes();
    System.out.println("Types : " + result.toString());
  }

  /**
   * Test of getConstructs method, of class Section.
   */
  @Test
  public void testGetConstructs() {
    System.out.println("getConstructs");
    List<Construct> result = section.getConstructs();
    result.forEach(System.out::println);
  }

  /**
   * Test of getPars method, of class Section.
   */
  @Test
  public void testGetPars() {
    System.out.println("getPars");
    Pattern result = section.getPars();
    System.out.println(result.pattern());
    String test = "2+(x-6+[3-2])*(7-a)";
    Matcher m = result.matcher(test);
    String[] expected = new String[]{"[3-2]","(7-a)"};
    int i = 0;
    while (m.find()) {
      System.out.println(m.group());
      assertEquals(expected[i++], m.group());
    }
  }

  /**
   * Test of getInfers method, of class Section.
   */
  @Test
  public void testGetInfers() {
    System.out.println("getInfers");
    List<Infer> result = section.getInfers();
    System.out.println(result);
    List<AbExpr> lookResults = result.get(0).lookResults(entries);
    String expResult = "d+4=3*x+3";
    if(lookResults.isEmpty()) {
      System.out.println("no result");
    } else {
      System.out.println(lookResults.get(0).getTxt());
    }
    assertEquals(expResult, lookResults.get(0).getTxt());
  }

  /**
   * Test of getTitle method, of class Section.
   */
  @Ignore
  @Test
  public void testGetTitle() {
    System.out.println("getTitle");
    String expResult = "";
    String result = section.getTitle();
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of setTitle method, of class Section.
   */
  @Ignore
  @Test
  public void testSetTitle() {
    System.out.println("setTitle");
    String title = "";
    section.setTitle(title);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of getParent method, of class Section.
   */
  @Ignore
  @Test
  public void testGetParent() {
    System.out.println("getParent");
    Section instance = section;
    Section expResult = null;
    Section result = instance.getParent();
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of getVars method, of class Section.
   */
  @Ignore
  @Test
  public void testGetVars() {
    System.out.println("getVars");
    Section instance = null;
    List<Variable> expResult = null;
    List<Variable> result = instance.getVars();
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }
  
}
