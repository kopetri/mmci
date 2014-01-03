package org.spongycastle.asn1.x509;

import org.spongycastle.asn1.ASN1Choice;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERTaggedObject;

public class AttCertIssuer
  extends ASN1Object
  implements ASN1Choice
{
  ASN1Primitive choiceObj;
  ASN1Encodable obj;
  
  public AttCertIssuer(GeneralNames paramGeneralNames)
  {
    this.obj = paramGeneralNames;
    this.choiceObj = this.obj.toASN1Primitive();
  }
  
  public AttCertIssuer(V2Form paramV2Form)
  {
    this.obj = paramV2Form;
    this.choiceObj = new DERTaggedObject(false, 0, this.obj);
  }
  
  public static AttCertIssuer getInstance(Object paramObject)
  {
    if ((paramObject == null) || ((paramObject instanceof AttCertIssuer))) {
      return (AttCertIssuer)paramObject;
    }
    if ((paramObject instanceof V2Form)) {
      return new AttCertIssuer(V2Form.getInstance(paramObject));
    }
    if ((paramObject instanceof GeneralNames)) {
      return new AttCertIssuer((GeneralNames)paramObject);
    }
    if ((paramObject instanceof ASN1TaggedObject)) {
      return new AttCertIssuer(V2Form.getInstance((ASN1TaggedObject)paramObject, false));
    }
    if ((paramObject instanceof ASN1Sequence)) {
      return new AttCertIssuer(GeneralNames.getInstance(paramObject));
    }
    throw new IllegalArgumentException("unknown object in factory: " + paramObject.getClass().getName());
  }
  
  public static AttCertIssuer getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean)
  {
    return getInstance(paramASN1TaggedObject.getObject());
  }
  
  public ASN1Encodable getIssuer()
  {
    return this.obj;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    return this.choiceObj;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.x509.AttCertIssuer
 * JD-Core Version:    0.7.0.1
 */