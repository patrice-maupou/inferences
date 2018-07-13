/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.patricemaupou.inferences;

import java.util.List;
import java.util.Objects;

/**
 * TODO variable représentant des listes, exemple : R[] N[] ..
 * 
 * @author Patrice
 */
public class Variable extends Const {
  
  private AbExpr val;
  public static Type hasvalue = new Type("HASVALUE");
  
  public Variable(String name, Type type) {
    super(name, type);
    var = true;
  }

  public Variable copy() {
    return new Variable(getTxt(), getType());
  }
  
  @Override
  public boolean match(AbExpr abExpr, List<Variable> vars) {
    for (Variable v : vars) {
      if(getTxt().equals(v.getTxt())) {
        if(hasvalue.equals(v.getType())) { // valeur déjà fixée
          return v.getVal().equals(abExpr);
        } else {
          v.setVal(abExpr);
          return true;
        }
      }
    }
    return this.equals(abExpr);
  }

  @Override
  public AbExpr replace(AbExpr e0, AbExpr e1) {
    return (this.equals(e0))? e1 : this;
  }
  
  

  @Override
  public AbExpr takeVals(List<Variable> vs) {
    for (Variable v : vs) {
      if(getTxt().equals(v.getTxt())) {
        return v.getVal();
      }
    }
    return this.copy();
  }
  
  public AbExpr getVal() {
    return val;
  }

  public void setVal(AbExpr val) {
    this.val = val;
    type = hasvalue;
  }

  
  @Override
  public String toString() {
    if(getType().equals(Variable.hasvalue)) {
      return getTxt()+"->"+val.toString();
    }
    return super.toString(); 
  }
  
  
    
}
