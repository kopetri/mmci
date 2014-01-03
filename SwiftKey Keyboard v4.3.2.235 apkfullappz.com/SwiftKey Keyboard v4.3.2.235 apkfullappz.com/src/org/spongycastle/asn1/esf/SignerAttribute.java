package org.spongycastle.asn1.esf;

import java.util.Enumeration;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.x509.Attribute;
import org.spongycastle.asn1.x509.AttributeCertificate;

public class SignerAttribute
  extends ASN1Object
{
  private Object[] values;
  
  private SignerAttribute(ASN1Sequence paramASN1Sequence)
  {
    int i = 0;
    this.values = new Object[paramASN1Sequence.size()];
    Enumeration localEnumeration = paramASN1Sequence.getObjects();
    if (localEnumeration.hasMoreElements())
    {
      ASN1TaggedObject localASN1TaggedObject = ASN1TaggedObject.getInstance(localEnumeration.nextElement());
      if (localASN1TaggedObject.getTagNo() == 0)
      {
        ASN1Sequence localASN1Sequence = ASN1Sequence.getInstance(localASN1TaggedObject, true);
        Attribute[] arrayOfAttribute = new Attribute[localASN1Sequence.size()];
        for (int j = 0; j != arrayOfAttribute.length; j++) {
          arrayOfAttribute[j] = Attribute.getInstance(localASN1Sequence.getObjectAt(j));
        }
        this.values[i] = arrayOfAttribute;
      }
      for (;;)
      {
        i++;
        break;
        if (localASN1TaggedObject.getTagNo() != 1) {
          break label141;
        }
        this.values[i] = AttributeCertificate.getInstance(ASN1Sequence.getInstance(localASN1TaggedObject, true));
      }
      label141:
      throw new IllegalArgumentException("illegal tag: " + localASN1TaggedObject.getTagNo());
    }
  }
  
  public SignerAttribute(AttributeCertificate paramAttributeCertificate)
  {
    this.values = new Object[1];
    this.values[0] = paramAttributeCertificate;
  }
  
  public SignerAttribute(Attribute[] paramArrayOfAttribute)
  {
    this.values = new Object[1];
    this.values[0] = paramArrayOfAttribute;
  }
  
  public static SignerAttribute getInstance(Object paramObject)
  {
    if ((paramObject instanceof SignerAttribute)) {
      return (SignerAttribute)paramObject;
    }
    if (paramObject != null) {
      return new SignerAttribute(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public Object[] getValues()
  {
    return this.values;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    int i = 0;
    if (i != this.values.length)
    {
      if ((this.values[i] instanceof Attribute[])) {
        localASN1EncodableVector.add(new DERTaggedObject(0, new DERSequence((Attribute[])this.values[i])));
      }
      for (;;)
      {
        i++;
        break;
        localASN1EncodableVector.add(new DERTaggedObject(1, (AttributeCertificate)this.values[i]));
      }
    }
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.esf.SignerAttribute
 * JD-Core Version:    0.7.0.1
 */