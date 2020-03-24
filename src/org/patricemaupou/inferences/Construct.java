/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.patricemaupou.inferences;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Exemple : <constr><op="+" type="R" vars="a,b:R" pattern="a+b"/>
 *
 * @author Patrice Maupou
 */
public class Construct {

  private final Type type;
  private final String op;
  private final int priority;
  private final int auto;
  private final int constrType; //  0:constant ou variable, <0: parenthésage, 1:élimine parenthèses à -1
  private String patternText;
  private final Pattern pattern;
  private final List<Variable> vars;
  private final static String Z = "\u0000";

  public enum Simple {
    variable, constant
  };

  /**
   * pour les suites, on remplace "(a,b)" par "\\(a(,b)*\\)" avec repeat=",b"
   * Le rang de chaque variable du texte (sans les \\) est notée dans 
   * dans patternText, chaque variable est remplacée par (nul)rang, par exemple _1, _2 etc..
   * 
   * @param op l'opérateur de l'expression
   * @param type so type
   * @param patternText le modèle
   * @param repeat 
   * @param vars la liste des variables utilisées
   * @param priority niveau de priorité de l'opération
   * @param constrType négatif si parenthésage
   * @param autopriority priorité de l'opérateur par rapport à lui-même (left pour 3+4+5=(3+4)+5 )
   */
  public Construct(String op, Type type, String patternText, String repeat, List<Variable> vars, int priority, 
          int constrType, String autopriority) {
    this.op = op;
    this.type = type;
    this.priority = priority;
    auto = 1;
    this.constrType = constrType;
    String text = patternText.replace("\\", "");                                  // "(a,b)"
    if (repeat != null) {
      patternText = patternText.replace(repeat, "(" + repeat + ")*");             // "\\(a(,b)*\\)"
    }
    this.patternText = (repeat == null) ? text : text.replace(repeat, repeat + "..."); //"(a,b...)"
    final String ftext = text;  // ranger les variables suivant l'ordre du texte
    TreeMap<Integer, Variable> treeVars = new TreeMap<>();
    vars.forEach((Variable x) -> { treeVars.put(ftext.indexOf(x.getTxt()), x); });
    this.vars = new ArrayList<>(treeVars.values());
    for (int i = 0; i < vars.size(); i++) {
      Variable var = vars.get(i);
      patternText = patternText.replace(var.getTxt(), Z);  // exemple : " (, )+"
      if (repeat != null && i == vars.size() - 1) {
        repeat = repeat.replace(var.getTxt(), Z);            // ", 1"
        text = text.replace(var.getTxt(), Z + i + Z);
      } else {
        text = text.replace(var.getTxt(), Z + i);                  // "( 0, 1)"
      }
    }
    pattern = Pattern.compile("(" + patternText + ")|(\u0000)");
  }

  /**
   * Construction normale sans répétition de termes (pas dr suite à nombre indeterminé d'items)
   *
   * @param op
   * @param type
   * @param vars
   * @param patternText
   * @param priority
   */
  public Construct(String op, Type type, String patternText, List<Variable> vars, int priority, 
          int parenthesis, String autopriority) {
    this(op, type, patternText, null, vars, priority, parenthesis, autopriority);
  }

  /**
   * définit une construction directe et sans variable
   *
   * @param s "variable" ou "constant"
   * @param type le type de l'expression
   * @param patternText le regex définissant le modèle
   */ 
  public Construct(Simple s, Type type, String patternText, int priority) {
    this(s.name(), type, patternText, null, new ArrayList<Variable>(), priority, 0, "left");
  }  
  

/**
 * analyse txtexpr, remplace les chaînes conformes au modèle par le caractère nul, remplace les sous-expressions
 par l'expression décodée dans la liste listExprs
 * @param txtexpr la chaîne à analyser
 * @param listExprs sous-expressions déjè trouvées
 * @param fit true si trouvé avant
 * @param start nombre de valeurs trouvées avant dans la chaîne principale
 * @return true si trouvé
 */
  public boolean searchExpr(StringBuilder txtexpr, List<AbExpr> listExprs, boolean fit, int start) {
    Matcher matcher = pattern.matcher(txtexpr);
    int vstart = start;
    String txtstart = "";
    loop_find:
    while (matcher.find()) {
      String m = matcher.group();
      if (!m.equals(Z)) { // expression trouvée  
        int end = vstart + (m + " ").split(Z).length - 1;
        List<AbExpr> childs = new ArrayList<>(listExprs.subList(vstart, end));
        for (int i = 0; i < childs.size(); i++) {
          Variable var = (i >= vars.size()) ? vars.get(vars.size() - 1) : vars.get(i); // garder le dernier type   
          if(constrType == 1 && childs.get(i).getType().equals(Type.SEQ)){ // remplace la séquence par ses éléments
            List<AbExpr> childlist = childs.get(i).list;
            if (childlist.size()==1) {
              childs.set(i, childlist.get(0));
            }
          }       
          if (!childs.get(i).getType().isSubType(var.getType())) { // vérification du type
            vstart = end;
            continue loop_find;
          }
        }
        listExprs.subList(vstart, end).clear();
        if (vars.isEmpty()) {
          Const expr = (op.equals("variable")) ? new Variable(m, type) : new Const(m, type);
          listExprs.add(vstart, expr);
        } else {
          Expr expr = new Expr(op, childs, type, m, priority, auto);
          listExprs.add(vstart, expr);
        }
        txtexpr.replace(matcher.start(), matcher.end(), Z);
        txtstart += txtexpr.substring(0, matcher.start());
        txtexpr.delete(0, matcher.start());
        matcher = pattern.matcher(txtexpr);
        fit = true;
      } else {
        vstart++; // expression déjà reconnue
      }
    }
    txtexpr.insert(0, txtstart);
    return fit;
  }

  public Pattern getPattern() {
    return pattern;
  }

  public void setPatternText(String patternText) {
    this.patternText = patternText;
  }

  public int getConstrType() {
    return constrType;
  }

  public Type getType() {
    return type;
  }

  public String getOp() {
    return op;
  }

  public int getPriority() {
    return priority;
  }

  public List<Variable> getVars() {
    return vars;
  }

  @Override
  public String toString() {
    String ret = (vars.isEmpty()) ? "  " : "  vars=" + vars.toString();
    return patternText + "  type=" + type + ret;
  }

}
