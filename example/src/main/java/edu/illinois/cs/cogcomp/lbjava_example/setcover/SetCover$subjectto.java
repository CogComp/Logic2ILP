// Modifying this comment will cause the next execution of LBJava to overwrite this file.
// F1B88000000000000000B4ECFCB2E292A4CCCCB21580E4D217ECF2B4D22592E2D4ACA4D4E2929C7D07ECC29A45846D458A65078CBC77DCD2829A4FB4DCC4FC84ACF2AC8CFCF4926D846D4B658A50008D11AADB34000000

package edu.illinois.cs.cogcomp.lbjava_example.setcover;

//import edu.illinois.cs.cogcomp.infer.ilp.OJalgoHook;
import edu.illinois.cs.cogcomp.lbjava.classify.*;
import edu.illinois.cs.cogcomp.lbjava.infer.*;


public class SetCover$subjectto extends ParameterizedConstraint
{
  private static final noEmptyNeighborhoods __noEmptyNeighborhoods = new noEmptyNeighborhoods();

  public SetCover$subjectto() { super("edu.illinois.cs.cogcomp.lbjava.examples.setCover.SetCover$subjectto"); }

  public String getInputType() { return "edu.illinois.cs.cogcomp.lbjava.examples.setCover.City"; }

  public String discreteValue(Object __example)
  {
    if (!(__example instanceof City))
    {
      String type = __example == null ? "null" : __example.getClass().getName();
      System.err.println("Constraint 'SetCover$subjectto(City)' defined on line 18 of SetCover.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    City c = (City) __example;

    {
      boolean LBJava$constraint$result$0;
      LBJava$constraint$result$0 = __noEmptyNeighborhoods.discreteValue(c).equals("true");
      if (!LBJava$constraint$result$0) return "false";
    }

    return "true";
  }

  public FeatureVector[] classify(Object[] examples)
  {
    if (!(examples instanceof City[]))
    {
      String type = examples == null ? "null" : examples.getClass().getName();
      System.err.println("Classifier 'SetCover$subjectto(City)' defined on line 18 of SetCover.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    return super.classify(examples);
  }

  public int hashCode() { return "SetCover$subjectto".hashCode(); }
  public boolean equals(Object o) { return o instanceof SetCover$subjectto; }

  public FirstOrderConstraint makeConstraint(Object __example)
  {
    if (!(__example instanceof City))
    {
      String type = __example == null ? "null" : __example.getClass().getName();
      System.err.println("Constraint 'SetCover$subjectto(City)' defined on line 18 of SetCover.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    City c = (City) __example;
    FirstOrderConstraint __result = new FirstOrderConstant(true);

    {
      FirstOrderConstraint LBJava$constraint$result$0 = null;
      LBJava$constraint$result$0 = __noEmptyNeighborhoods.makeConstraint(c);
      __result = new FirstOrderConjunction(__result, LBJava$constraint$result$0);
    }

    return __result;
  }
}

