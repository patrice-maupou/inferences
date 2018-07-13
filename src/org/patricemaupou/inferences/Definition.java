/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.patricemaupou.inferences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Patrice Maupou
 */
public class Definition extends AbInfer {
  
  private final AbExpr def;
  
  public Definition(List<Variable> vars, AbExpr def, List<AbExpr> conds, Section section) {
    super(vars, new ArrayList<>(Arrays.asList(def)), conds, section);
    this.def = def;
  }
  
  
  
}
