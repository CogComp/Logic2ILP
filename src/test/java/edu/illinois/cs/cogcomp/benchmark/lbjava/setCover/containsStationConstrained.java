// Modifying this comment will cause the next execution of LBJava to overwrite this file.
// discrete{false, true} containsStationConstrained(Neighborhood n) <- SetCover(ContainsStation)

package edu.illinois.cs.cogcomp.benchmark.lbjava.setCover;

import edu.illinois.cs.cogcomp.lbjava.classify.Classifier;
import edu.illinois.cs.cogcomp.lbjava.classify.DiscreteFeature;
import edu.illinois.cs.cogcomp.lbjava.classify.DiscretePrimitiveStringFeature;
import edu.illinois.cs.cogcomp.lbjava.classify.Feature;
import edu.illinois.cs.cogcomp.lbjava.classify.FeatureVector;
import edu.illinois.cs.cogcomp.lbjava.infer.InferenceManager;


public class containsStationConstrained extends Classifier
{
  private static final ContainsStation __ContainsStation = new ContainsStation();

  public containsStationConstrained()
  {
    containingPackage = "edu.illinois.cs.cogcomp.lbjava.examples.setCover";
    name = "containsStationConstrained";
  }

  public String getInputType() { return "edu.illinois.cs.cogcomp.lbjava.examples.setCover.Neighborhood"; }
  public String getOutputType() { return "discrete"; }

  private static String[] __allowableValues = DiscreteFeature.BooleanValues;
  public static String[] getAllowableValues() { return __allowableValues; }
  public String[] allowableValues() { return __allowableValues; }


  public FeatureVector classify(Object __example)
  {
    return new FeatureVector(featureValue(__example));
  }

  public Feature featureValue(Object __example)
  {
    String result = discreteValue(__example);
    return new DiscretePrimitiveStringFeature(containingPackage, name, "", result, valueIndexOf(result), (short) allowableValues().length);
  }

  public String discreteValue(Object __example)
  {
    if (!(__example instanceof Neighborhood))
    {
      String type = __example == null ? "null" : __example.getClass().getName();
      System.err.println("Classifier 'containsStationConstrained(Neighborhood)' defined on line 23 of SetCover.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    City head = SetCover.findHead((Neighborhood) __example);
    SetCover inference = (SetCover) InferenceManager
        .get("edu.illinois.cs.cogcomp.lbjava.examples.setCover.SetCover", head);

    if (inference == null)
    {
      inference = new SetCover(head);
      InferenceManager.put(inference);
    }

    String result = null;

    try { result = inference.valueOf(__ContainsStation, __example); }
    catch (Exception e)
    {
      System.err.println("LBJava ERROR: Fatal error while evaluating classifier containsStationConstrained: " + e);
      e.printStackTrace();
      System.exit(1);
    }

    return result;
  }

  public FeatureVector[] classify(Object[] examples)
  {
    if (!(examples instanceof Neighborhood[]))
    {
      String type = examples == null ? "null" : examples.getClass().getName();
      System.err.println("Classifier 'containsStationConstrained(edu.illinois.cs.cogcomp.lbjava.examples.setCover.Neighborhood)' defined on line 23 of SetCover.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    return super.classify(examples);
  }

  public int hashCode() { return "containsStationConstrained".hashCode(); }
  public boolean equals(Object o) { return o instanceof containsStationConstrained; }
}

