/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.patricemaupou.inferences;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * abstract class of constants, variables and composite expressions
 * 
 *
 * @author Patrice
 */
public abstract class AbExpr {

  protected Type type;
  protected List<AbExpr> list;
  
  
  /**
   * 
   * @param etxt chaîne à analyser
   * @param constructs liste des différents opérateurs de reconnaissance
   * @param pars pattern pour obtenir les groupes n'ayant que des parenthèses à l'extérieur
   * @return l'expression ou variable de type FAIL en cas d'échec
   */
  public static AbExpr decode(String etxt, List<Construct> constructs, Pattern pars) {
    boolean found;
    ArrayList<AbExpr> exprList = new ArrayList<>();
    StringBuilder txt = new StringBuilder(etxt);
    do { 
      found = false;
      Matcher mpar = pars.matcher(txt.toString());
      int vstart = 0; // nombre d'expressions trouvées avant
      while (mpar.find()) {        
        if (!"\u0000".equals(mpar.group())) {
          StringBuilder subtxt = new StringBuilder(mpar.group());
          int s = mpar.start(), e = mpar.end(), start = vstart; // les bornes dans etxt
          constructs.forEach(c->c.searchExpr(subtxt, exprList, false, start));
          if(found = "\u0000".equals(subtxt.toString())) {
            txt.replace(s, e, "\u0000");
          }
          break;
        } else { vstart++; }
      }
    } while (found);
    constructs.forEach(c->c.searchExpr(txt, exprList, false, 0));
    return (txt.toString().equals("\u0000"))? exprList.get(0) : new Variable(etxt+"?", Type.FAIL);
  }

  public abstract AbExpr replace(AbExpr e0, AbExpr e1);
  
  public abstract boolean match(AbExpr expr, List<Variable> vars);  

  public abstract AbExpr takeVals(List<Variable> vs);

  public abstract String getTxt();

  public abstract Type getType();
  
  public abstract int getPriority();

 
  
}
