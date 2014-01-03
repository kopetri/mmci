package org.spongycastle.asn1.x509;

import java.util.Enumeration;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;

public class TargetInformation
  extends ASN1Object
{
  private ASN1Sequence targets;
  
  private TargetInformation(ASN1Sequence paramASN1Sequence)
  {
    this.targets = paramASN1Sequence;
  }
  
  public TargetInformation(Targets paramTargets)
  {
    this.targets = new DERSequence(paramTargets);
  }
  
  public TargetInformation(Target[] paramArrayOfTarget)
  {
    this(new Targets(paramArrayOfTarget));
  }
  
  public static TargetInformation getInstance(Object paramObject)
  {
    if ((paramObject instanceof TargetInformation)) {
      return (TargetInformation)paramObject;
    }
    if (paramObject != null) {
      return new TargetInformation(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public Targets[] getTargetsObjects()
  {
    Targets[] arrayOfTargets = new Targets[this.targets.size()];
    int i = 0;
    Enumeration localEnumeration = this.targets.getObjects();
    while (localEnumeration.hasMoreElements())
    {
      int j = i + 1;
      arrayOfTargets[i] = Targets.getInstance(localEnumeration.nextElement());
      i = j;
    }
    return arrayOfTargets;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    return this.targets;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.x509.TargetInformation
 * JD-Core Version:    0.7.0.1
 */