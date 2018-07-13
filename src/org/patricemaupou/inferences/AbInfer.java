/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.patricemaupou.inferences;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Patrice Maupou
 */
public abstract class AbInfer {

  protected final List<Variable> vars; //variables utlisées pour les deux listes
  protected final List<AbExpr> models;
  protected final List<AbExpr> results;
  protected final Section section; // place dans la hiérarchie du document
  private String name;

  public AbInfer(List<Variable> vars, List<AbExpr> models, List<AbExpr> results, Section section) {
    this.vars = vars;
    this.models = models;
    this.results = results;
    this.section = section;
  }  
  
  
  public List<AbExpr> getResults() {
    return results;
  }

  /**
   * teste si les entrées correspondent aux modèles et si oui, retournent les résultats
   * @param entries expressions proposées
   * @return la liste des résultats ou la liste vide si les entrées ne conviennent pas
   */
  List<AbExpr> lookResults(List<AbExpr> entries){    
    List<Variable> vs = new ArrayList<>();
    List<AbExpr> outputs = new ArrayList<>();
    vars.forEach(v->vs.add(v.copy()));
    if(entries.size() == models.size()) {
      for (int i = 0; i < models.size(); i++) {
        if(!(models.get(i).match(entries.get(i), vs))) return outputs;
      }
      results.forEach(r->outputs.add(r.takeVals(vs)));
    }
    return outputs;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  
  
}
