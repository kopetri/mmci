package org.spongycastle.asn1.isismtt.x509;

import java.util.Enumeration;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x509.GeneralName;

public class AdmissionSyntax
  extends ASN1Object
{
  private GeneralName admissionAuthority;
  private ASN1Sequence contentsOfAdmissions;
  
  private AdmissionSyntax(ASN1Sequence paramASN1Sequence)
  {
    switch (paramASN1Sequence.size())
    {
    default: 
      throw new IllegalArgumentException("Bad sequence size: " + paramASN1Sequence.size());
    case 1: 
      this.contentsOfAdmissions = DERSequence.getInstance(paramASN1Sequence.getObjectAt(0));
      return;
    }
    this.admissionAuthority = GeneralName.getInstance(paramASN1Sequence.getObjectAt(0));
    this.contentsOfAdmissions = DERSequence.getInstance(paramASN1Sequence.getObjectAt(1));
  }
  
  public AdmissionSyntax(GeneralName paramGeneralName, ASN1Sequence paramASN1Sequence)
  {
    this.admissionAuthority = paramGeneralName;
    this.contentsOfAdmissions = paramASN1Sequence;
  }
  
  public static AdmissionSyntax getInstance(Object paramObject)
  {
    if ((paramObject == null) || ((paramObject instanceof AdmissionSyntax))) {
      return (AdmissionSyntax)paramObject;
    }
    if ((paramObject instanceof ASN1Sequence)) {
      return new AdmissionSyntax((ASN1Sequence)paramObject);
    }
    throw new IllegalArgumentException("illegal object in getInstance: " + paramObject.getClass().getName());
  }
  
  public GeneralName getAdmissionAuthority()
  {
    return this.admissionAuthority;
  }
  
  public Admissions[] getContentsOfAdmissions()
  {
    Admissions[] arrayOfAdmissions = new Admissions[this.contentsOfAdmissions.size()];
    int i = 0;
    Enumeration localEnumeration = this.contentsOfAdmissions.getObjects();
    while (localEnumeration.hasMoreElements())
    {
      int j = i + 1;
      arrayOfAdmissions[i] = Admissions.getInstance(localEnumeration.nextElement());
      i = j;
    }
    return arrayOfAdmissions;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    if (this.admissionAuthority != null) {
      localASN1EncodableVector.add(this.admissionAuthority);
    }
    localASN1EncodableVector.add(this.contentsOfAdmissions);
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.isismtt.x509.AdmissionSyntax
 * JD-Core Version:    0.7.0.1
 */