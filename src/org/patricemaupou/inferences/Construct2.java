/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.patricemaupou.inferences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 * @author Patrice Maupou
 */
public class Construct2 {
  
  private final Section section;
  private Type type;
  private final String op; // représente l'opérateur
  private final int priority;
  private final int auto;
  private String patternText;
  private Pattern pattern;
  private final List<Variable> vars;
  private final static String Z = "\u0000";
  
/**
 * Exemple : Construct2( "(a+b)", "+", "real", "a:Ab b:Ab", "()", 10, "left")
 * 
 * @param patternText modèle de l'expression
 * @param varstxt liste des variables avec leur type
 * @param priority niveau de priorité
 * @param auto autopriorité
 * @param constrType 
 */
  public Construct2(String patternText,  String op, String typetxt, String varstxt, String pars, 
          int priority, int auto, Section section) {
    this.section = section;
    this.priority = priority;
    this.auto = auto;
    this.op = op;
    List<Type> types = section.getTypes();
    // Etablir la liste des variables
    vars = new ArrayList<>();
    ArrayList<String> vartxtlist = new ArrayList(Arrays.asList(varstxt.split(" "))); // "a,b:R c:N"
    
  }

 
  
  
}
