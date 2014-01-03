package org.spongycastle.asn1.esf;

import java.util.Enumeration;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;

public class CrlOcspRef
  extends ASN1Object
{
  private CrlListID crlids;
  private OcspListID ocspids;
  private OtherRevRefs otherRev;
  
  private CrlOcspRef(ASN1Sequence paramASN1Sequence)
  {
    Enumeration localEnumeration = paramASN1Sequence.getObjects();
    while (localEnumeration.hasMoreElements())
    {
      DERTaggedObject localDERTaggedObject = (DERTaggedObject)localEnumeration.nextElement();
      switch (localDERTaggedObject.getTagNo())
      {
      default: 
        throw new IllegalArgumentException("illegal tag");
      case 0: 
        this.crlids = CrlListID.getInstance(localDERTaggedObject.getObject());
        break;
      case 1: 
        this.ocspids = OcspListID.getInstance(localDERTaggedObject.getObject());
        break;
      case 2: 
        this.otherRev = OtherRevRefs.getInstance(localDERTaggedObject.getObject());
      }
    }
  }
  
  public CrlOcspRef(CrlListID paramCrlListID, OcspListID paramOcspListID, OtherRevRefs paramOtherRevRefs)
  {
    this.crlids = paramCrlListID;
    this.ocspids = paramOcspListID;
    this.otherRev = paramOtherRevRefs;
  }
  
  public static CrlOcspRef getInstance(Object paramObject)
  {
    if ((paramObject instanceof CrlOcspRef)) {
      return (CrlOcspRef)paramObject;
    }
    if (paramObject != null) {
      return new CrlOcspRef(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public CrlListID getCrlids()
  {
    return this.crlids;
  }
  
  public OcspListID getOcspids()
  {
    return this.ocspids;
  }
  
  public OtherRevRefs getOtherRev()
  {
    return this.otherRev;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    if (this.crlids != null) {
      localASN1EncodableVector.add(new DERTaggedObject(true, 0, this.crlids.toASN1Primitive()));
    }
    if (this.ocspids != null) {
      localASN1EncodableVector.add(new DERTaggedObject(true, 1, this.ocspids.toASN1Primitive()));
    }
    if (this.otherRev != null) {
      localASN1EncodableVector.add(new DERTaggedObject(true, 2, this.otherRev.toASN1Primitive()));
    }
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.esf.CrlOcspRef
 * JD-Core Version:    0.7.0.1
 */