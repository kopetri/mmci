package org.spongycastle.asn1.isismtt.x509;

import java.util.Enumeration;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.x509.GeneralName;

public class Admissions
  extends ASN1Object
{
  private GeneralName admissionAuthority;
  private NamingAuthority namingAuthority;
  private ASN1Sequence professionInfos;
  
  private Admissions(ASN1Sequence paramASN1Sequence)
  {
    if (paramASN1Sequence.size() > 3) {
      throw new IllegalArgumentException("Bad sequence size: " + paramASN1Sequence.size());
    }
    Enumeration localEnumeration = paramASN1Sequence.getObjects();
    ASN1Encodable localASN1Encodable = (ASN1Encodable)localEnumeration.nextElement();
    if ((localASN1Encodable instanceof ASN1TaggedObject)) {
      switch (((ASN1TaggedObject)localASN1Encodable).getTagNo())
      {
      default: 
        throw new IllegalArgumentException("Bad tag number: " + ((ASN1TaggedObject)localASN1Encodable).getTagNo());
      case 0: 
        this.admissionAuthority = GeneralName.getInstance((ASN1TaggedObject)localASN1Encodable, true);
      }
    }
    for (;;)
    {
      localASN1Encodable = (ASN1Encodable)localEnumeration.nextElement();
      if (!(localASN1Encodable instanceof ASN1TaggedObject)) {
        break;
      }
      switch (((ASN1TaggedObject)localASN1Encodable).getTagNo())
      {
      default: 
        throw new IllegalArgumentException("Bad tag number: " + ((ASN1TaggedObject)localASN1Encodable).getTagNo());
        this.namingAuthority = NamingAuthority.getInstance((ASN1TaggedObject)localASN1Encodable, true);
      }
    }
    this.namingAuthority = NamingAuthority.getInstance((ASN1TaggedObject)localASN1Encodable, true);
    localASN1Encodable = (ASN1Encodable)localEnumeration.nextElement();
    this.professionInfos = ASN1Sequence.getInstance(localASN1Encodable);
    if (localEnumeration.hasMoreElements()) {
      throw new IllegalArgumentException("Bad object encountered: " + localEnumeration.nextElement().getClass());
    }
  }
  
  public Admissions(GeneralName paramGeneralName, NamingAuthority paramNamingAuthority, ProfessionInfo[] paramArrayOfProfessionInfo)
  {
    this.admissionAuthority = paramGeneralName;
    this.namingAuthority = paramNamingAuthority;
    this.professionInfos = new DERSequence(paramArrayOfProfessionInfo);
  }
  
  public static Admissions getInstance(Object paramObject)
  {
    if ((paramObject == null) || ((paramObject instanceof Admissions))) {
      return (Admissions)paramObject;
    }
    if ((paramObject instanceof ASN1Sequence)) {
      return new Admissions((ASN1Sequence)paramObject);
    }
    throw new IllegalArgumentException("illegal object in getInstance: " + paramObject.getClass().getName());
  }
  
  public GeneralName getAdmissionAuthority()
  {
    return this.admissionAuthority;
  }
  
  public NamingAuthority getNamingAuthority()
  {
    return this.namingAuthority;
  }
  
  public ProfessionInfo[] getProfessionInfos()
  {
    ProfessionInfo[] arrayOfProfessionInfo = new ProfessionInfo[this.professionInfos.size()];
    int i = 0;
    Enumeration localEnumeration = this.professionInfos.getObjects();
    while (localEnumeration.hasMoreElements())
    {
      int j = i + 1;
      arrayOfProfessionInfo[i] = ProfessionInfo.getInstance(localEnumeration.nextElement());
      i = j;
    }
    return arrayOfProfessionInfo;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    if (this.admissionAuthority != null) {
      localASN1EncodableVector.add(new DERTaggedObject(true, 0, this.admissionAuthority));
    }
    if (this.namingAuthority != null) {
      localASN1EncodableVector.add(new DERTaggedObject(true, 1, this.namingAuthority));
    }
    localASN1EncodableVector.add(this.professionInfos);
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.isismtt.x509.Admissions
 * JD-Core Version:    0.7.0.1
 */