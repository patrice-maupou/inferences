/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.patricemaupou.inferences;

import java.util.List;

/**
 *
 * @author Patrice Maupou
 * 
 * TODO  une définition comporte une ou plusieurs constructions ,
 * exemple  construct : Cat(C)  (C est une catégorie, c'est la construction principale),
 *            fonction  : Ob(C)   ( Ob est une fonction sur Cat -> Set)
 *            fonction  : dom,cod:C -> Ob(C) 
 *            fonction  : si f:Ob(C) alors y:cod'(f)-> 
 */
public class Definition {
  
  private final Construct def;
  
  public Definition(List<Variable> vars, Construct def, Section section) {
    this.def = def;
  }
  
  
  
}
