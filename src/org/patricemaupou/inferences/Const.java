/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.patricemaupou.inferences;

import java.util.List;
import java.util.Objects;

/**
 *
 * @author Patrice
 */
public class Const extends AbExpr {
  
  protected boolean var;
  protected String text;

  public Const(String text, Type type) {
    this.type = type;
    this.text = text;
    this.var = false;
  }  
  
  @Override
  public boolean match(AbExpr abExpr, List<Variable> vars) {
    try {
      Const c = (Const)abExpr;
      return getType().equals(c.getType())&&getTxt().equals(c.getTxt());
    } catch (Exception e) {return false;}
  }

  @Override
  public AbExpr takeVals(List<Variable> vs) {
    return this;
  }

  @Override
  public AbExpr replace(AbExpr e0, AbExpr e1) {
    return this;
  }
  
  
        
  @Override
  public boolean equals(Object obj) {
    if(obj != null && obj instanceof Const) {
      return (((Const)obj).getTxt().equals(getTxt())) && ((Const)obj).getType().equals(getType()) && ((Const)obj).var==(var);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 13 * hash + Objects.hashCode(getTxt());
    return hash;
  }

  @Override
  public Type getType() {
    return type;
  }
  
  @Override
  public String getTxt() {
    return text;
  }
  
  @Override
  public int getPriority() {
    return Integer.MAX_VALUE;
  }

  @Override
  public String toString() {
    return getTxt() + ":" + getType();
  }



}
