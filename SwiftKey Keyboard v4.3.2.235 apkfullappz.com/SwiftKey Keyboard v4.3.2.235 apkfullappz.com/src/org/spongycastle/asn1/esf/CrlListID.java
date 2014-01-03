package org.spongycastle.asn1.esf;

import java.util.Enumeration;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;

public class CrlListID
  extends ASN1Object
{
  private ASN1Sequence crls;
  
  private CrlListID(ASN1Sequence paramASN1Sequence)
  {
    this.crls = ((ASN1Sequence)paramASN1Sequence.getObjectAt(0));
    Enumeration localEnumeration = this.crls.getObjects();
    while (localEnumeration.hasMoreElements()) {
      CrlValidatedID.getInstance(localEnumeration.nextElement());
    }
  }
  
  public CrlListID(CrlValidatedID[] paramArrayOfCrlValidatedID)
  {
    this.crls = new DERSequence(paramArrayOfCrlValidatedID);
  }
  
  public static CrlListID getInstance(Object paramObject)
  {
    if ((paramObject instanceof CrlListID)) {
      return (CrlListID)paramObject;
    }
    if (paramObject != null) {
      return new CrlListID(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public CrlValidatedID[] getCrls()
  {
    CrlValidatedID[] arrayOfCrlValidatedID = new CrlValidatedID[this.crls.size()];
    for (int i = 0; i < arrayOfCrlValidatedID.length; i++) {
      arrayOfCrlValidatedID[i] = CrlValidatedID.getInstance(this.crls.getObjectAt(i));
    }
    return arrayOfCrlValidatedID;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    return new DERSequence(this.crls);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.esf.CrlListID
 * JD-Core Version:    0.7.0.1
 */