package org.spongycastle.asn1.isismtt.x509;

import java.util.Enumeration;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERPrintableString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.x500.DirectoryString;
import org.spongycastle.asn1.x509.GeneralName;
import org.spongycastle.asn1.x509.IssuerSerial;

public class ProcurationSyntax
  extends ASN1Object
{
  private IssuerSerial certRef;
  private String country;
  private GeneralName thirdPerson;
  private DirectoryString typeOfSubstitution;
  
  public ProcurationSyntax(String paramString, DirectoryString paramDirectoryString, GeneralName paramGeneralName)
  {
    this.country = paramString;
    this.typeOfSubstitution = paramDirectoryString;
    this.thirdPerson = paramGeneralName;
    this.certRef = null;
  }
  
  public ProcurationSyntax(String paramString, DirectoryString paramDirectoryString, IssuerSerial paramIssuerSerial)
  {
    this.country = paramString;
    this.typeOfSubstitution = paramDirectoryString;
    this.thirdPerson = null;
    this.certRef = paramIssuerSerial;
  }
  
  private ProcurationSyntax(ASN1Sequence paramASN1Sequence)
  {
    if ((paramASN1Sequence.size() <= 0) || (paramASN1Sequence.size() > 3)) {
      throw new IllegalArgumentException("Bad sequence size: " + paramASN1Sequence.size());
    }
    Enumeration localEnumeration = paramASN1Sequence.getObjects();
    while (localEnumeration.hasMoreElements())
    {
      ASN1TaggedObject localASN1TaggedObject = ASN1TaggedObject.getInstance(localEnumeration.nextElement());
      switch (localASN1TaggedObject.getTagNo())
      {
      default: 
        throw new IllegalArgumentException("Bad tag number: " + localASN1TaggedObject.getTagNo());
      case 1: 
        this.country = DERPrintableString.getInstance(localASN1TaggedObject, true).getString();
        break;
      case 2: 
        this.typeOfSubstitution = DirectoryString.getInstance(localASN1TaggedObject, true);
        break;
      case 3: 
        ASN1Primitive localASN1Primitive = localASN1TaggedObject.getObject();
        if ((localASN1Primitive instanceof ASN1TaggedObject)) {
          this.thirdPerson = GeneralName.getInstance(localASN1Primitive);
        } else {
          this.certRef = IssuerSerial.getInstance(localASN1Primitive);
        }
        break;
      }
    }
  }
  
  public static ProcurationSyntax getInstance(Object paramObject)
  {
    if ((paramObject == null) || ((paramObject instanceof ProcurationSyntax))) {
      return (ProcurationSyntax)paramObject;
    }
    if ((paramObject instanceof ASN1Sequence)) {
      return new ProcurationSyntax((ASN1Sequence)paramObject);
    }
    throw new IllegalArgumentException("illegal object in getInstance: " + paramObject.getClass().getName());
  }
  
  public IssuerSerial getCertRef()
  {
    return this.certRef;
  }
  
  public String getCountry()
  {
    return this.country;
  }
  
  public GeneralName getThirdPerson()
  {
    return this.thirdPerson;
  }
  
  public DirectoryString getTypeOfSubstitution()
  {
    return this.typeOfSubstitution;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    if (this.country != null) {
      localASN1EncodableVector.add(new DERTaggedObject(true, 1, new DERPrintableString(this.country, true)));
    }
    if (this.typeOfSubstitution != null) {
      localASN1EncodableVector.add(new DERTaggedObject(true, 2, this.typeOfSubstitution));
    }
    if (this.thirdPerson != null) {
      localASN1EncodableVector.add(new DERTaggedObject(true, 3, this.thirdPerson));
    }
    for (;;)
    {
      return new DERSequence(localASN1EncodableVector);
      localASN1EncodableVector.add(new DERTaggedObject(true, 3, this.certRef));
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.isismtt.x509.ProcurationSyntax
 * JD-Core Version:    0.7.0.1
 */