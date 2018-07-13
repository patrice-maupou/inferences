/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.patricemaupou.inferences;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Patrice
 */
public class Expr extends AbExpr {
  
  private final String op;
  private final int priority;
  private final double auto; // autopriority 
  private final String pattern;

  public Expr(String op, List<AbExpr> list, Type type, String pattern, int priority, int auto) {
    this.type = type;
    this.op = op;
    this.list = list;
    this.priority = priority;
    this.pattern = pattern;
    this.auto = 0.5*auto;
  }

  
 @Override
  public boolean match(AbExpr abExpr, List<Variable> vars) {
    try {
     Expr expr = (Expr)abExpr;
     if(op.equals(expr.getOp())&& expr.getType().isSubType(getType())){ // même type, même op
       for (int i = 0; i < list.size(); i++) {
          if(!list.get(i).match(expr.getList().get(i), vars)) {
            return false;
          }
        }
       return true;
     }
   } catch (Exception e) {return false;}
   return false;
  }

  @Override
  public AbExpr takeVals(List<Variable> vs) {
    List<AbExpr> nlist = new ArrayList<>();
    list.forEach(e->nlist.add(e.takeVals(vs)));
    Expr e = new Expr(op, nlist, getType(), pattern, priority, 1);
    return e;
  }

  /**
   * Remplace toutes les occurrences de l'expression e0 et les remplace par l'expression e1
   * @param e0 à remplacer
   * @param e1 expression remplaçante
   * @return 
   */
  @Override
  public AbExpr replace(AbExpr e0, AbExpr e1) {
    if (!e0.getType().equals(e1.getType())) {
      return this;
    }
    if(this.equals(e0)) {
      return e1;
    } else {
      List<AbExpr> rlist = new ArrayList<>();
      list.forEach(e->rlist.add(e.replace(e0, e1)));
      return new Expr(op, rlist, type, pattern, priority, 1);
    }
  }
  
 /**
  * écriture normale de l'expression
  * @return la chaîne de caractère représentant l'expression
  */
 @Override
  public String getTxt() {
    String txt = pattern;
    for (int i = 0; i < list.size(); i++) {
      AbExpr sexpr = list.get(i);
      boolean isSeq = sexpr.getType().equals(Type.SEQ); // suppression éventuelle des parenthèses
      String stxt = (!isSeq && list.size() == 2 && (priority + auto*i > sexpr.getPriority()))? 
              "("+sexpr.getTxt()+")" : sexpr.getTxt();
      txt = txt.replaceFirst("\u0000", stxt);
    }
    return txt;
  }
  
 
  @Override
  public boolean equals(Object obj) {
    if(obj != null && obj instanceof Expr) {
      Expr e = (Expr)obj;
      return (op.equals(e.getOp()) && getType().equals(e.getType()) && list.equals(e.getList()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 89 * hash + Objects.hashCode(this.op);
    hash = 89 * hash + Objects.hashCode(this.list);
    return hash;
  }
  
  
  
  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder("(" + op);
    list.forEach((abExpr) -> {buf.append(",").append(abExpr.toString());});
    return buf.append(")").toString();
  } 

  @Override
  public int getPriority() {
    return priority;
  }

  public String getOp() {
    return op;
  }

  public List<AbExpr> getList() {
    return list;
  }

  public String getPattern() {
    return pattern;
  }

  @Override
  public Type getType() {
    return type;
  }

  
}
