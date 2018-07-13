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
 */
public class Infer extends AbInfer {
  
  private final int changelevel;

  public Infer(List<Variable> vars, List<AbExpr> models, List<AbExpr> results, Section section, int changelevel) {
    super(vars, models, results, section);
    this.changelevel = changelevel;
  }
  
  

  @Override
  public String toString() {
    StringBuilder ret = new StringBuilder("variables : ");
    vars.forEach(m->ret.append(m.getTxt()).append(" "));
    ret.append(" models : ");
    models.forEach(m->ret.append(m.getTxt()).append(" "));
    ret.append(" results : ");
    results.forEach(r->ret.append(r.getTxt()).append(" "));
    return ret.toString();
  }

 

  public int getChangelevel() {
    return changelevel;
  }
  
  
  
}
