// Modifying this comment will cause the next execution of LBJava to overwrite this file.
// F1B88000000000000000D6CC1BA02C0401541DF5975EE63A0923691C6D6C6D66D57D4602EC8CEC31214CF775C242A85F5EE9C6AECA944905BDE5EAC97754AF1E865703B3978E4833274CD176BA96C11BCC0588224E55F58FDB5C88EC49F27D7FC4413D0A11D6B06DB51C16D823983DF74B6EDC9E29BFF453F1B6387C315FDB2C7A3C000000

package edu.illinois.cs.cogcomp.benchmark.lbjava.setCover;

import edu.illinois.cs.cogcomp.lbjava.classify.FeatureVector;
import edu.illinois.cs.cogcomp.lbjava.infer.EqualityArgumentReplacer;
import edu.illinois.cs.cogcomp.lbjava.infer.ExistentialQuantifier;
import edu.illinois.cs.cogcomp.lbjava.infer.FirstOrderConjunction;
import edu.illinois.cs.cogcomp.lbjava.infer.FirstOrderConstant;
import edu.illinois.cs.cogcomp.lbjava.infer.FirstOrderConstraint;
import edu.illinois.cs.cogcomp.lbjava.infer.FirstOrderDisjunction;
import edu.illinois.cs.cogcomp.lbjava.infer.FirstOrderEqualityWithValue;
import edu.illinois.cs.cogcomp.lbjava.infer.FirstOrderVariable;
import edu.illinois.cs.cogcomp.lbjava.infer.ParameterizedConstraint;
import edu.illinois.cs.cogcomp.lbjava.infer.QuantifierArgumentReplacer;
import edu.illinois.cs.cogcomp.lbjava.infer.UniversalQuantifier;


public class noEmptyNeighborhoods extends ParameterizedConstraint
{
  private static final ContainsStation __ContainsStation = new ContainsStation();

  public noEmptyNeighborhoods() { super("edu.illinois.cs.cogcomp.lbjava.examples.setCover.noEmptyNeighborhoods"); }

  public String getInputType() { return "edu.illinois.cs.cogcomp.lbjava.examples.setCover.City"; }

  public String discreteValue(Object __example)
  {
    if (!(__example instanceof City))
    {
      String type = __example == null ? "null" : __example.getClass().getName();
      System.err.println("Constraint 'noEmptyNeighborhoods(City)' defined on line 9 of SetCover.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    City c = (City) __example;

    {
      boolean LBJava$constraint$result$0;
      {
        LBJava$constraint$result$0 = true;
        for (java.util.Iterator __I0 = (c.getNeighborhoods()).iterator(); __I0.hasNext() && LBJava$constraint$result$0; )
        {
          Neighborhood n = (Neighborhood) __I0.next();
          {
            boolean LBJava$constraint$result$1;
            LBJava$constraint$result$1 = ("" + (__ContainsStation.discreteValue(n))).equals("" + (true));
            if (!LBJava$constraint$result$1)
              {
                LBJava$constraint$result$0 = false;
                for (java.util.Iterator __I1 = (n.getNeighbors()).iterator(); __I1.hasNext() && !LBJava$constraint$result$0; )
                {
                  Neighborhood n2 = (Neighborhood) __I1.next();
                  LBJava$constraint$result$0 = ("" + (__ContainsStation.discreteValue(n2))).equals("" + (true));
                }
              }
            else LBJava$constraint$result$0 = true;
          }
        }
      }
      if (!LBJava$constraint$result$0) return "false";
    }

    return "true";
  }

  public FeatureVector[] classify(Object[] examples)
  {
    if (!(examples instanceof City[]))
    {
      String type = examples == null ? "null" : examples.getClass().getName();
      System.err.println("Classifier 'noEmptyNeighborhoods(City)' defined on line 9 of SetCover.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    return super.classify(examples);
  }

  public int hashCode() { return "noEmptyNeighborhoods".hashCode(); }
  public boolean equals(Object o) { return o instanceof noEmptyNeighborhoods; }

  public FirstOrderConstraint makeConstraint(Object __example)
  {
    if (!(__example instanceof City))
    {
      String type = __example == null ? "null" : __example.getClass().getName();
      System.err.println("Constraint 'noEmptyNeighborhoods(City)' defined on line 9 of SetCover.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    City c = (City) __example;
    FirstOrderConstraint __result = new FirstOrderConstant(true);

    {
      Object[] LBJ$constraint$context = new Object[1];
      LBJ$constraint$context[0] = c;
      FirstOrderConstraint LBJava$constraint$result$0 = null;
      {
        FirstOrderConstraint LBJava$constraint$result$1 = null;
        {
          FirstOrderConstraint LBJava$constraint$result$2 = null;
          {
            EqualityArgumentReplacer LBJ$EAR =
              new EqualityArgumentReplacer(LBJ$constraint$context, true)
              {
                public Object getLeftObject()
                {
                  Neighborhood n = (Neighborhood) quantificationVariables.get(0);
                  return n;
                }
              };
            LBJava$constraint$result$2 = new FirstOrderEqualityWithValue(true, new FirstOrderVariable(__ContainsStation, null), "" + (true), LBJ$EAR);
          }
          FirstOrderConstraint LBJava$constraint$result$3 = null;
          {
            FirstOrderConstraint LBJava$constraint$result$4 = null;
            {
              EqualityArgumentReplacer LBJ$EAR =
                new EqualityArgumentReplacer(LBJ$constraint$context, true)
                {
                  public Object getLeftObject()
                  {
                    Neighborhood n2 = (Neighborhood) quantificationVariables.get(1);
                    return n2;
                  }
                };
              LBJava$constraint$result$4 = new FirstOrderEqualityWithValue(true, new FirstOrderVariable(__ContainsStation, null), "" + (true), LBJ$EAR);
            }
            QuantifierArgumentReplacer LBJ$QAR =
              new QuantifierArgumentReplacer(LBJ$constraint$context)
              {
                public java.util.Collection getCollection()
                {
                  Neighborhood n = (Neighborhood) quantificationVariables.get(0);
                  return n.getNeighbors();
                }
              };
            LBJava$constraint$result$3 = new ExistentialQuantifier("n2", null, LBJava$constraint$result$4, LBJ$QAR);
          }
          LBJava$constraint$result$1 = new FirstOrderDisjunction(LBJava$constraint$result$2, LBJava$constraint$result$3);
        }
        LBJava$constraint$result$0 = new UniversalQuantifier("n", c.getNeighborhoods(), LBJava$constraint$result$1);
      }
      __result = new FirstOrderConjunction(__result, LBJava$constraint$result$0);
    }

    return __result;
  }
}

