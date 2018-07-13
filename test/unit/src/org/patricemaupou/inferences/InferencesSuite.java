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
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import static org.patricemaupou.inferences.Type.*;
import static org.patricemaupou.inferences.Construct.Simple.*;

/**
 *
 * @author Patrice Maupou
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
  org.patricemaupou.inferences.AbExprTest.class,
  //org.patricemaupou.inferences.ConstructTest.class,
  org.patricemaupou.inferences.InferTest.class,
  org.patricemaupou.inferences.SectionTest.class
})
public class InferencesSuite {

  public List<Construct> constructs;
  public Pattern enclose;
  public List<Variable> variables;
  public static InferencesSuite suite = new InferencesSuite();

  public InferencesSuite() {
    Type R = new Type("R"), Z = new Type("Z", R), N = new Type("N", Z), Func = new Type("R->R");
    Type NbRel = new Type("NbRel");
    Variable[] reals = new Variable[3], ints = new Variable[3], nats = new Variable[3]; //a b c i j k x y z
    for (int p = 0; p < 3; p++) {
      char x = (char) ('x' + p), i = (char) ('i' + p), a = (char) ('a' + p), f = (char) ('f' + p); // or U+(hex value)
      reals[p] = new Variable(String.valueOf(a), R);
      ints[p] = new Variable(String.valueOf(i), Z);
      nats[p] = new Variable(String.valueOf(x), N);
    }
    variables = new ArrayList<>(Arrays.asList(reals));
    variables.addAll(Arrays.asList(ints));
    variables.addAll(Arrays.asList(nats));
    List<Variable> vars = new ArrayList<>(variables.subList(0, 2));
    Variable f = new Variable("f", Func);
    Variable sq = new Variable("s", SEQ);
    Construct eq = new Construct("=", NbRel, "a=b", vars, 8, 1, "left");
    Construct plus = new Construct("+", R, "a\\+b", vars, 10, 1, "left");
    Construct minus = new Construct("-", R, "a-b", vars, 10, 1, "left");
    Construct mul = new Construct("*", R, "a\\*b", vars, 20, 1, "left");
    Construct div = new Construct("/", R, "a/b", vars, 20, 1, "left");
    Construct varReal = new Construct(variable, R, "[a-d]", 50);
    Construct varNat = new Construct(variable, N, "[x-z]", 50);
    Construct fname = new Construct(variable, Func, "[f-h]", 80);
    Construct number = new Construct(constant, R, "\\d+", 100);
    Construct func = new Construct("func", R, "fs", new ArrayList<>(Arrays.asList(f,sq)), 30, 2, "left");
    Construct interval = new Construct("interval", new Type("I"), "\\[a,b\\]", vars, 6, -2, "left");
    Construct seq = new Construct("seq", SEQ, "\\(a,b\\)", ",b", vars, 0, -1, "left");
    constructs = new ArrayList<>(Arrays.asList(varReal, varNat, plus, minus, number, mul, div, func, fname, 
            seq, interval, eq));
    constructs.sort(Comparator.comparingInt(Construct::getPriority).reversed());
    List<Construct> pars = constructs.stream().filter(c -> c.getConstrType() < 0).collect(Collectors.toList()); 
    List<String> pres = new ArrayList<>(), posts = new ArrayList<>();
    String middle = "[^", s = "";
    for (Construct par : pars) {
      String p = par.getPattern().toString();
      p = p.substring(1, p.length()-5);
      int spe = (p.startsWith("\\"))? 2 : 1;
      String pre = p.substring(0, spe);
      String post = p.substring(p.length()-spe);
      middle += pre + post;
      pres.add("(" + pre);
      posts.add(post+")|");
     }
    for (int i = 0; i < pres.size(); i++) {
      s += pres.get(i)+middle+"]+"+posts.get(i);
    }
    enclose = Pattern.compile(s+"(\u0000)");
  }
  
     
  @BeforeClass
  public static void setUpClass() throws Exception {
  }

  @AfterClass
  public static void tearDownClass() throws Exception {
  }


  @Before
  public void setUp() throws Exception {
  }

  @After
  public void tearDown() throws Exception {
  }

  
}
