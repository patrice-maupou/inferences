/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.patricemaupou.inferences;

import java.util.Objects;

/**
 *
 * @author Patrice
 */
public class Type {
  
  private final String name;
  private Type supType;
  public static Type SEQ = new Type("Seq"), FAIL = new Type("fail");

  public Type(String name) {
    this.name = name;
  }

  public Type(String name, Type suptype) {
    this.name = name;
    this.supType = suptype;
  } 
  
  public boolean isSubType(Type t0) {
    Type t = this;
    while (t != null) {
      if(t.equals(t0)){ return true; }
      t = t.getSupType();
    }
    return false;    
  }

  @Override
  public boolean equals(Object obj) {
    if(obj != null && obj instanceof Type) {
      return (((Type)obj).toString() == null ? name == null : ((Type)obj).toString().equals(name));
    } else {    return false;  }
  }

  @Override
  public int hashCode() {
    int hash = 3;
    hash = 23 * hash + Objects.hashCode(this.name);
    return hash;
  }
   
  public Type getSupType() {
    return supType;
  }

  @Override
  public String toString() {
    return name;
  }

  
     
}
