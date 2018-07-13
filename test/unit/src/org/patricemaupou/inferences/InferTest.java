/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.patricemaupou.inferences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.patricemaupou.inferences.Type.SEQ;

/**
 *
 * @author Patrice
 */
public class InferTest {
  
  private final List<Infer> infers;
  private static InferencesSuite suite;
  
  public InferTest() { 
    String[][] sModels = new String[][]{{"a*(b-c)"},{"a+(a+b)"}};
    String[][] sResults = new String[][]{{"a*b-a*c"},{"2*a+b"}};
    infers = new ArrayList<>(); 
    List<Variable> vars = new ArrayList<>();
    String[] tv = new String[]{"a", "b", "c"};
    txtToExprs(tv).forEach(v->vars.add((Variable)v));
    for (int i = 0; i < sModels.length; i++) {
        infers.add(new Infer(vars, txtToExprs(sModels[i]), txtToExprs(sResults[i]), null, 0));
    }    
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
    String pars = suite.enclose.pattern();
    String ajout = "\\{\\}";
    String inside = "";
    System.out.println("parenthesis : " + pars);
    Pattern p = Pattern.compile("(\\[\\^[^|]+)(\\]\\+)");
    Matcher m = p.matcher(pars);
    if(m.find()) {
      inside = m.group(1) + ajout + m.group(2);      
      System.out.println(inside);
    }
    Stream<String> spS = p.splitAsStream(pars);
    String[] st = spS.toArray(size->new String[size]);
    String np = st[0];
    for (int i = 1; i < st.length; i++) {
      np+= inside + st[i];
    }
    System.out.println(np);
  }
  
  @Before
  public void setUp() {
  }
  
  @After
  public void tearDown() {
  }

  /**
   * Test of lookResults method, of class Infer.
   */
  @Test
  public void testLookResults() {
    System.out.println("lookResults");
    List<AbExpr> entries;
    String[][] texprs = new String[][]{{"(x+1)*(x-2)"},{"5+(5+(x+1))"}};
    String[] texpect = new String[]{"(x+1)*x-(x+1)*2","2*5+(x+1)"};
    for (int i = 0; i < infers.size(); i++) {
      System.out.println(infers.get(i));
      entries =txtToExprs(texprs[i]);
      printList("\tentries", entries);
      List<AbExpr> output = infers.get(i).lookResults(entries);
      printList("-> output", output);
      assertEquals(texpect[i], output.get(0).getTxt());
    }
  }
  
  private static List<AbExpr> txtToExprs(String[] exprstxt) {
    ArrayList<AbExpr> abExprs = new ArrayList<>();
    for (String etxt : exprstxt) {
        AbExpr expr = AbExpr.decode(etxt, suite.constructs, suite.enclose);
        abExprs.add(expr);
    }
    return abExprs;
  }
  
  private void printList(String name, List<AbExpr> abExprs) {
    StringBuilder message = new StringBuilder(name).append(" :");
    abExprs.forEach(e->message.append("  ").append(e.getTxt()));
    System.out.println(message);
  }
  
}
