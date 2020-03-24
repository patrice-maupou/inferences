/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.patricemaupou.inferences;

import java.util.ArrayList;
import java.util.Arrays;
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
  
  /**
   * ajoute des variables à la liste suivant le format noms:"a,b,c:nombre"
   * TODO : "a,b:nombre::abelian"
   * @param varts texte des variables à ajouter
   * @param consts à remplir
   */
  public static void  addConsts(String vars, List<Const> consts, List<Type> types) {
    String[] varstypetxt = vars.split(":");    
    if (varstypetxt.length == 2 && varstypetxt[1].length() == 1) {  
      Type type = new Type(varstypetxt[1]);
      int index = types.indexOf(type);
      if(index == -1) types.add(type);
      final Type t = (index !=-1)? types.get(index):type;
      List<String> varstxt = Arrays.asList(varstypetxt[0].split(","));
      varstxt.forEach(x->consts.add(new Const(x,t)));
    }
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
