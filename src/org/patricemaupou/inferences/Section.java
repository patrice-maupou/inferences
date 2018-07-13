/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.patricemaupou.inferences;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Patrice Maupou
 */
public class Section {

  private String title;
  private final Section parent;
  private List<Type> types;
  private List<Variable> vars;
  private List<Construct> constructs;
  private Pattern pars;
  private List<Infer> infers;
  private final List<AbExpr> exprs;

  public Section(int level, String title, Section parent) {
    this.title = title;
    this.parent = parent;
    if (parent != null) {
      types = parent.types;
      vars = parent.vars;
      constructs = parent.constructs;
      pars = parent.pars;
      infers = parent.infers;
    } else {
      types = new ArrayList<>();
      vars = new ArrayList<>();
      constructs = new ArrayList<>();
      infers = new ArrayList<>();
    }
    exprs = new ArrayList<>();
  }
  
  /**
   * ajoute une construction à la liste et la trie par priorité décroissante
   * @param construct 
   */
  public void addConstruct(Construct construct) {
    if(!types.contains(construct.getType())) {
      types.add(construct.getType());
    }
    if (construct.getConstrType() < 0) { // mise à jour du pattern gouvernant les parenthèses
      String p = construct.getPattern().toString();
      p = p.substring(1, p.length() - 5);
      int spe = (p.startsWith("\\")) ? 2 : 1;
      String open = p.substring(0, spe);
      String close = p.substring(p.length() - spe);
      String middle = open + close;
      String newpar = "(" + open + "[^" + middle + "]+" + close + ")|"; // nouveau type de parenthèses
      if (pars == null) {
        pars = Pattern.compile(newpar+"(\u0000)");
      } else {
        Pattern pattern = Pattern.compile("(\\[\\^[^|]+)(\\]\\+)");
        Matcher m = pattern.matcher(pars.pattern());
        if(m.find()) {
          middle = m.group(1) + middle + m.group(2);
          String[] split = pattern.splitAsStream(newpar + pars.pattern()).toArray(size->new String[size]);
          newpar = split[0];
          for (int i = 1; i < split.length; i++) {
            newpar+= middle + split[i];            
          }          
        }
        pars = Pattern.compile(newpar);
      }
    }
    constructs.add(construct);
    constructs.sort(Comparator.comparingInt(Construct::getPriority).reversed());
  }
  
  public boolean addVar(Variable var) {
     return vars.add(var);
  }

  public List<Type> getTypes() {
    return types;
  }

  public List<Construct> getConstructs() {
    return constructs;
  }

  public Pattern getPars() {
    return pars;
  }

  public List<Infer> getInfers() {
    return infers;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Section getParent() {
    return parent;
  }

  public List<Variable> getVars() {
    return vars;
  }

  public List<AbExpr> getExprs() {
    return exprs;
  }

}
