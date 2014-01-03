package org.spongycastle.asn1.esf;

import java.util.Enumeration;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;

public class CompleteRevocationRefs
  extends ASN1Object
{
  private ASN1Sequence crlOcspRefs;
  
  private CompleteRevocationRefs(ASN1Sequence paramASN1Sequence)
  {
    Enumeration localEnumeration = paramASN1Sequence.getObjects();
    while (localEnumeration.hasMoreElements()) {
      CrlOcspRef.getInstance(localEnumeration.nextElement());
    }
    this.crlOcspRefs = paramASN1Sequence;
  }
  
  public CompleteRevocationRefs(CrlOcspRef[] paramArrayOfCrlOcspRef)
  {
    this.crlOcspRefs = new DERSequence(paramArrayOfCrlOcspRef);
  }
  
  public static CompleteRevocationRefs getInstance(Object paramObject)
  {
    if ((paramObject instanceof CompleteRevocationRefs)) {
      return (CompleteRevocationRefs)paramObject;
    }
    if (paramObject != null) {
      return new CompleteRevocationRefs(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public CrlOcspRef[] getCrlOcspRefs()
  {
    CrlOcspRef[] arrayOfCrlOcspRef = new CrlOcspRef[this.crlOcspRefs.size()];
    for (int i = 0; i < arrayOfCrlOcspRef.length; i++) {
      arrayOfCrlOcspRef[i] = CrlOcspRef.getInstance(this.crlOcspRefs.getObjectAt(i));
    }
    return arrayOfCrlOcspRef;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    return this.crlOcspRefs;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.esf.CompleteRevocationRefs
 * JD-Core Version:    0.7.0.1
 */