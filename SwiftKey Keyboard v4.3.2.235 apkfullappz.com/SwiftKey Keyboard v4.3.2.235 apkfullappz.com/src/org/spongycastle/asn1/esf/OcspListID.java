package org.spongycastle.asn1.esf;

import java.util.Enumeration;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;

public class OcspListID
  extends ASN1Object
{
  private ASN1Sequence ocspResponses;
  
  private OcspListID(ASN1Sequence paramASN1Sequence)
  {
    if (paramASN1Sequence.size() != 1) {
      throw new IllegalArgumentException("Bad sequence size: " + paramASN1Sequence.size());
    }
    this.ocspResponses = ((ASN1Sequence)paramASN1Sequence.getObjectAt(0));
    Enumeration localEnumeration = this.ocspResponses.getObjects();
    while (localEnumeration.hasMoreElements()) {
      OcspResponsesID.getInstance(localEnumeration.nextElement());
    }
  }
  
  public OcspListID(OcspResponsesID[] paramArrayOfOcspResponsesID)
  {
    this.ocspResponses = new DERSequence(paramArrayOfOcspResponsesID);
  }
  
  public static OcspListID getInstance(Object paramObject)
  {
    if ((paramObject instanceof OcspListID)) {
      return (OcspListID)paramObject;
    }
    if (paramObject != null) {
      return new OcspListID(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public OcspResponsesID[] getOcspResponses()
  {
    OcspResponsesID[] arrayOfOcspResponsesID = new OcspResponsesID[this.ocspResponses.size()];
    for (int i = 0; i < arrayOfOcspResponsesID.length; i++) {
      arrayOfOcspResponsesID[i] = OcspResponsesID.getInstance(this.ocspResponses.getObjectAt(i));
    }
    return arrayOfOcspResponsesID;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    return new DERSequence(this.ocspResponses);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.esf.OcspListID
 * JD-Core Version:    0.7.0.1
 */