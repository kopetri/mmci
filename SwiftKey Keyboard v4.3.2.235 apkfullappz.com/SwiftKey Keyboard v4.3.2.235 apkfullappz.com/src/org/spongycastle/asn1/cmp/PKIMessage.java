package org.spongycastle.asn1.cmp;

import java.util.Enumeration;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;

public class PKIMessage
  extends ASN1Object
{
  private PKIBody body;
  private ASN1Sequence extraCerts;
  private PKIHeader header;
  private DERBitString protection;
  
  private PKIMessage(ASN1Sequence paramASN1Sequence)
  {
    Enumeration localEnumeration = paramASN1Sequence.getObjects();
    this.header = PKIHeader.getInstance(localEnumeration.nextElement());
    this.body = PKIBody.getInstance(localEnumeration.nextElement());
    while (localEnumeration.hasMoreElements())
    {
      ASN1TaggedObject localASN1TaggedObject = (ASN1TaggedObject)localEnumeration.nextElement();
      if (localASN1TaggedObject.getTagNo() == 0) {
        this.protection = DERBitString.getInstance(localASN1TaggedObject, true);
      } else {
        this.extraCerts = ASN1Sequence.getInstance(localASN1TaggedObject, true);
      }
    }
  }
  
  public PKIMessage(PKIHeader paramPKIHeader, PKIBody paramPKIBody)
  {
    this(paramPKIHeader, paramPKIBody, null, null);
  }
  
  public PKIMessage(PKIHeader paramPKIHeader, PKIBody paramPKIBody, DERBitString paramDERBitString)
  {
    this(paramPKIHeader, paramPKIBody, paramDERBitString, null);
  }
  
  public PKIMessage(PKIHeader paramPKIHeader, PKIBody paramPKIBody, DERBitString paramDERBitString, CMPCertificate[] paramArrayOfCMPCertificate)
  {
    this.header = paramPKIHeader;
    this.body = paramPKIBody;
    this.protection = paramDERBitString;
    if (paramArrayOfCMPCertificate != null)
    {
      ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
      for (int i = 0; i < paramArrayOfCMPCertificate.length; i++) {
        localASN1EncodableVector.add(paramArrayOfCMPCertificate[i]);
      }
      this.extraCerts = new DERSequence(localASN1EncodableVector);
    }
  }
  
  private void addOptional(ASN1EncodableVector paramASN1EncodableVector, int paramInt, ASN1Encodable paramASN1Encodable)
  {
    if (paramASN1Encodable != null) {
      paramASN1EncodableVector.add(new DERTaggedObject(true, paramInt, paramASN1Encodable));
    }
  }
  
  public static PKIMessage getInstance(Object paramObject)
  {
    if ((paramObject instanceof PKIMessage)) {
      return (PKIMessage)paramObject;
    }
    if (paramObject != null) {
      return new PKIMessage(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public PKIBody getBody()
  {
    return this.body;
  }
  
  public CMPCertificate[] getExtraCerts()
  {
    CMPCertificate[] arrayOfCMPCertificate;
    if (this.extraCerts == null) {
      arrayOfCMPCertificate = null;
    }
    for (;;)
    {
      return arrayOfCMPCertificate;
      arrayOfCMPCertificate = new CMPCertificate[this.extraCerts.size()];
      for (int i = 0; i < arrayOfCMPCertificate.length; i++) {
        arrayOfCMPCertificate[i] = CMPCertificate.getInstance(this.extraCerts.getObjectAt(i));
      }
    }
  }
  
  public PKIHeader getHeader()
  {
    return this.header;
  }
  
  public DERBitString getProtection()
  {
    return this.protection;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.header);
    localASN1EncodableVector.add(this.body);
    addOptional(localASN1EncodableVector, 0, this.protection);
    addOptional(localASN1EncodableVector, 1, this.extraCerts);
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.cmp.PKIMessage
 * JD-Core Version:    0.7.0.1
 */