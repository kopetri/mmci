package org.spongycastle.asn1.x509;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;

public class Holder
  extends ASN1Object
{
  public static final int V1_CERTIFICATE_HOLDER = 0;
  public static final int V2_CERTIFICATE_HOLDER = 1;
  IssuerSerial baseCertificateID;
  GeneralNames entityName;
  ObjectDigestInfo objectDigestInfo;
  private int version = 1;
  
  private Holder(ASN1Sequence paramASN1Sequence)
  {
    if (paramASN1Sequence.size() > 3) {
      throw new IllegalArgumentException("Bad sequence size: " + paramASN1Sequence.size());
    }
    int i = 0;
    if (i != paramASN1Sequence.size())
    {
      ASN1TaggedObject localASN1TaggedObject = ASN1TaggedObject.getInstance(paramASN1Sequence.getObjectAt(i));
      switch (localASN1TaggedObject.getTagNo())
      {
      default: 
        throw new IllegalArgumentException("unknown tag in Holder");
      case 0: 
        this.baseCertificateID = IssuerSerial.getInstance(localASN1TaggedObject, false);
      }
      for (;;)
      {
        i++;
        break;
        this.entityName = GeneralNames.getInstance(localASN1TaggedObject, false);
        continue;
        this.objectDigestInfo = ObjectDigestInfo.getInstance(localASN1TaggedObject, false);
      }
    }
    this.version = 1;
  }
  
  private Holder(ASN1TaggedObject paramASN1TaggedObject)
  {
    switch (paramASN1TaggedObject.getTagNo())
    {
    default: 
      throw new IllegalArgumentException("unknown tag in Holder");
    case 0: 
      this.baseCertificateID = IssuerSerial.getInstance(paramASN1TaggedObject, false);
    }
    for (;;)
    {
      this.version = 0;
      return;
      this.entityName = GeneralNames.getInstance(paramASN1TaggedObject, false);
    }
  }
  
  public Holder(GeneralNames paramGeneralNames)
  {
    this(paramGeneralNames, 1);
  }
  
  public Holder(GeneralNames paramGeneralNames, int paramInt)
  {
    this.entityName = paramGeneralNames;
    this.version = paramInt;
  }
  
  public Holder(IssuerSerial paramIssuerSerial)
  {
    this(paramIssuerSerial, 1);
  }
  
  public Holder(IssuerSerial paramIssuerSerial, int paramInt)
  {
    this.baseCertificateID = paramIssuerSerial;
    this.version = paramInt;
  }
  
  public Holder(ObjectDigestInfo paramObjectDigestInfo)
  {
    this.objectDigestInfo = paramObjectDigestInfo;
  }
  
  public static Holder getInstance(Object paramObject)
  {
    if ((paramObject instanceof Holder)) {
      return (Holder)paramObject;
    }
    if ((paramObject instanceof ASN1TaggedObject)) {
      return new Holder(ASN1TaggedObject.getInstance(paramObject));
    }
    if (paramObject != null) {
      return new Holder(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public IssuerSerial getBaseCertificateID()
  {
    return this.baseCertificateID;
  }
  
  public GeneralNames getEntityName()
  {
    return this.entityName;
  }
  
  public ObjectDigestInfo getObjectDigestInfo()
  {
    return this.objectDigestInfo;
  }
  
  public int getVersion()
  {
    return this.version;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    if (this.version == 1)
    {
      ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
      if (this.baseCertificateID != null) {
        localASN1EncodableVector.add(new DERTaggedObject(false, 0, this.baseCertificateID));
      }
      if (this.entityName != null) {
        localASN1EncodableVector.add(new DERTaggedObject(false, 1, this.entityName));
      }
      if (this.objectDigestInfo != null) {
        localASN1EncodableVector.add(new DERTaggedObject(false, 2, this.objectDigestInfo));
      }
      return new DERSequence(localASN1EncodableVector);
    }
    if (this.entityName != null) {
      return new DERTaggedObject(false, 1, this.entityName);
    }
    return new DERTaggedObject(false, 0, this.baseCertificateID);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.x509.Holder
 * JD-Core Version:    0.7.0.1
 */