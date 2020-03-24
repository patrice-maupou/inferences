/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.patricemaupou.inferences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
public class AbExprTest {
  
  private static InferencesSuite suite;
  private static List<AbExpr> abExprs;
  
  public AbExprTest() {
  }
  
  @BeforeClass
  public static void setUpClass() {
    suite = InferencesSuite.suite;
    if(suite == null) {
      suite = new InferencesSuite();
    }
    List<String> entries = new ArrayList<>(Arrays.asList("(a+b)*c", "a*(b-c)", "(a+3)*(5+(b+1))", "5+(5+x+1)", 
            "(1+1,2,3,4)", "a+b*(1+c)", "a+b*c+b*d", "x+12*3+7*4", "3+f(x)", "2*1+f(3)"));
    abExprs = new ArrayList<>();
    entries.forEach((String s) -> {abExprs.add(AbExpr.decode(s, suite.constructs, suite.enclose));});
  }
  
  @AfterClass
  public static void tearDownClass() {
  }
  
  @Before
  public void setUp() throws Exception {
  }
  
  @After
  public void tearDown() {
  }

  /**
   * Test of decode method, of class AbExpr.
   * @throws java.lang.Exception
   */
  @Test
  public void testDecode() throws Exception {
    System.out.println("decode");
    String[] entries = new String[]{"(a+b)*c", "a*(b-c)", "(a+3)*(5+(b+1))", "5+(5+x+1)", "(1+1,2,3,4)", "a+b*(1+c)", 
      "a+b*c+b*d", "x+12*3+7*4", "3+f(x)", "2*1+f(3)", "1+2=2+1"};
    String[] expResults = new String[]{"(a+b)*c", "a*(b-c)", "(a+3)*(5+(b+1))", "5+(5+x+1)", "(1+1,2,3,4)", "a+b*(1+c)", 
      "a+b*c+b*d", "x+12*3+7*4", "3+f(x)", "2*1+f(3)", "1+2=2+1"};
    for (int i = 0; i < entries.length; i++) {
      AbExpr result = AbExpr.decode(entries[i], suite.constructs, suite.enclose);
      System.out.println("result : "+result.getTxt()+"  expected : "+expResults[i]);
      assertEquals(expResults[i], result.getTxt());
    }
  }

  /**
   * Test of replace method, of class AbExpr.
   */
  @Test
  public void testReplace() {
    System.out.println("replace");
    List<String> entries = new ArrayList<>(Arrays.asList("a","b","b-c","a+1","a+b","a-c")); 
    String[] expected = new String[]{"(a+1+b)*c", "a*(a+b-c)"};
    List<AbExpr> exprs = new ArrayList<>(abExprs.subList(0, 2));
    List<AbExpr> e0e1s = new ArrayList<>();
    entries.forEach((String s) -> { e0e1s.add(AbExpr.decode(s, suite.constructs, suite.enclose)); });
    for (int i = 0; i < 2; i++) {
      AbExpr e0 = e0e1s.get(i), e1= e0e1s.get(i+3);
      System.out.println("e0 : "+e0.getTxt()+"  e1 : "+e1.getTxt());
      AbExpr expr = exprs.get(i).replace(e0, e1);
      System.out.println("origine : "+exprs.get(i).getTxt()+ " vers : "+ expr.getTxt());
      assertEquals(expected[i],expr.getTxt());
    }
  }

  /**
   * Test of match method, of class AbExpr.
   */
  @Test
  public void testMatch() {
    System.out.println("match");
    String[] modelstxt = new String[]{"a+b*c", "(a+b)*c", "a*(b+a)"};
    String[] exprtxt = new String[]{"1+c*2", "(c+5)*(a+1)", "(b+1)*(5+(b+1))"};
    List<AbExpr> models = txtToExprs(modelstxt);
    List<AbExpr> exprs = txtToExprs(exprtxt);
    boolean[][] res = new boolean[][]{{true, false, false}, {false, true, true}, {false, false, true}};
    for (int i = 0; i < 3; i++) {
      AbExpr model = models.get(i);
      for (int j = 0; j < 3; j++) {
        AbExpr abExpr = exprs.get(j);
        boolean expResult = res[i][j];
        List<Variable> exprVars = new ArrayList<>(); // variables restent modifiées
        suite.variables.subList(0, 3).forEach(v -> exprVars.add(v.copy()));
        boolean result = model.match(abExpr, exprVars);
        System.out.println("model : " + model.getTxt() + " expr : " + abExpr.getTxt() + "   " + result+"|"+expResult);
        System.out.println(exprVars);
        assertEquals("model : " + model.getTxt() + "\t\t expr : " + abExpr.getTxt(), expResult, result);
      }
    }
  }
  
  private List<AbExpr> txtToExprs(String[] exprstxt) {
    ArrayList<AbExpr> exprs = new ArrayList<>();
    AbExpr result;
    for (String etxt : exprstxt) {
      result = AbExpr.decode(etxt, suite.constructs, suite.enclose);
      exprs.add(result);
    }    
    return exprs;
  }

  /**
   * Test of takeVals method, of class AbExpr.
   */
  @Test
  public void testTakeVals() {
    String[] modelstxt = new String[]{"(a+b)*c", "a*c+b*c"}; 
    String[] exprtxt = new String[]{"(c+5)*(a+2)", "c*(a+2)+5*(a+2)"};
    List<AbExpr> models = txtToExprs(modelstxt);
    List<AbExpr> exprs = txtToExprs(exprtxt);
    AbExpr expResult = exprs.get(1);
    List<Variable> vs = new ArrayList<>(); // variables restent modifiées
    suite.variables.subList(0, 3).forEach(v -> vs.add(v.copy()));
    boolean fit = models.get(0).match(exprs.get(0), vs);
    System.out.println("model : "+models.get(0).getTxt() + "\t expr : "+ exprs.get(0).getTxt() + "\t" +fit + "\t"+ vs);
    AbExpr result = models.get(1).takeVals(vs);
    System.out.println(models.get(1).getTxt()+ " -> " + result.getTxt()+ "  expected : "+exprs.get(1).getTxt());
    assertEquals(expResult, result);
  }

  /**
   * Test of getTxt method, of class AbExpr.
   */
  @Ignore
  @Test
  public void testGetTxt() {
    System.out.println("getTxt");
    abExprs.forEach(e->System.out.println(e.getTxt()));
  }

  /**
   * Test of getType method, of class AbExpr.
   */
  @Test
  public void testGetType() {
    System.out.println("getType");
    abExprs.forEach(e->System.out.println(e.getTxt()+"  type : "+e.getType()));    
  }

  /**
   * Test of getPriority method, of class AbExpr.
   */
  @Ignore
  @Test
  public void testGetPriority() {
    System.out.println("getPriority");
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

    
}
