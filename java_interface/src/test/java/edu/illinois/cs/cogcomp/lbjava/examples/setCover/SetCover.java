// Modifying this comment will cause the next execution of LBJava to overwrite this file.
// F1B8800000000000000053C814E028040140FB2D7CDDB8F10F8C54C01421F50B24BBBB1DCC42391526C0F770E0C5B255555E943AC84CD9ED8E79682C431A9AEF3C88F3A76DC5E1A6545728C60C8EF1318C923D784B5BEBBB12E91B00375F201EC86BB1AD36D16707BB6A776DB8AEB24C885650C191976687000000

package edu.illinois.cs.cogcomp.lbjava.examples.setCover;

import edu.illinois.cs.cogcomp.lbjava.classify.*;
import edu.illinois.cs.cogcomp.lbjava.examples.setCover.City;
import edu.illinois.cs.cogcomp.lbjava.examples.setCover.ContainsStation;
import edu.illinois.cs.cogcomp.lbjava.examples.setCover.Neighborhood;
import edu.illinois.cs.cogcomp.lbjava.infer.*;
import edu.illinois.cs.cogcomp.lbjava.io.IOUtilities;
import edu.illinois.cs.cogcomp.lbjava.learn.*;
import edu.illinois.cs.cogcomp.lbjava.parse.*;
import java.util.*;


public class SetCover extends ILPInference
{
  public static City findHead(Neighborhood n)
  {
    return n.getParentCity();
  }


  public SetCover() { }
  public SetCover(City head)
  {
    super(head, new OJalgoHook());
    constraint = new SetCover$subjectto().makeConstraint(head);
  }

  public String getHeadType() { return "edu.illinois.cs.cogcomp.lbjava.examples.setCover.City"; }
  public String[] getHeadFinderTypes()
  {
    return new String[]{ "edu.illinois.cs.cogcomp.lbjava.examples.setCover.Neighborhood" };
  }

  public Normalizer getNormalizer(Learner c)
  {
    return new IdentityNormalizer();
  }
}

