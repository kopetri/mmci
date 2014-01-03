package org.spongycastle.asn1.x509.sigi;

import java.math.BigInteger;
import java.util.Enumeration;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERGeneralizedTime;
import org.spongycastle.asn1.DERPrintableString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.x500.DirectoryString;

public class PersonalData
  extends ASN1Object
{
  private DERGeneralizedTime dateOfBirth;
  private String gender;
  private BigInteger nameDistinguisher;
  private NameOrPseudonym nameOrPseudonym;
  private DirectoryString placeOfBirth;
  private DirectoryString postalAddress;
  
  private PersonalData(ASN1Sequence paramASN1Sequence)
  {
    if (paramASN1Sequence.size() <= 0) {
      throw new IllegalArgumentException("Bad sequence size: " + paramASN1Sequence.size());
    }
    Enumeration localEnumeration = paramASN1Sequence.getObjects();
    this.nameOrPseudonym = NameOrPseudonym.getInstance(localEnumeration.nextElement());
    while (localEnumeration.hasMoreElements())
    {
      ASN1TaggedObject localASN1TaggedObject = ASN1TaggedObject.getInstance(localEnumeration.nextElement());
      switch (localASN1TaggedObject.getTagNo())
      {
      default: 
        throw new IllegalArgumentException("Bad tag number: " + localASN1TaggedObject.getTagNo());
      case 0: 
        this.nameDistinguisher = ASN1Integer.getInstance(localASN1TaggedObject, false).getValue();
        break;
      case 1: 
        this.dateOfBirth = DERGeneralizedTime.getInstance(localASN1TaggedObject, false);
        break;
      case 2: 
        this.placeOfBirth = DirectoryString.getInstance(localASN1TaggedObject, true);
        break;
      case 3: 
        this.gender = DERPrintableString.getInstance(localASN1TaggedObject, false).getString();
        break;
      case 4: 
        this.postalAddress = DirectoryString.getInstance(localASN1TaggedObject, true);
      }
    }
  }
  
  public PersonalData(NameOrPseudonym paramNameOrPseudonym, BigInteger paramBigInteger, DERGeneralizedTime paramDERGeneralizedTime, DirectoryString paramDirectoryString1, String paramString, DirectoryString paramDirectoryString2)
  {
    this.nameOrPseudonym = paramNameOrPseudonym;
    this.dateOfBirth = paramDERGeneralizedTime;
    this.gender = paramString;
    this.nameDistinguisher = paramBigInteger;
    this.postalAddress = paramDirectoryString2;
    this.placeOfBirth = paramDirectoryString1;
  }
  
  public static PersonalData getInstance(Object paramObject)
  {
    if ((paramObject == null) || ((paramObject instanceof PersonalData))) {
      return (PersonalData)paramObject;
    }
    if ((paramObject instanceof ASN1Sequence)) {
      return new PersonalData((ASN1Sequence)paramObject);
    }
    throw new IllegalArgumentException("illegal object in getInstance: " + paramObject.getClass().getName());
  }
  
  public DERGeneralizedTime getDateOfBirth()
  {
    return this.dateOfBirth;
  }
  
  public String getGender()
  {
    return this.gender;
  }
  
  public BigInteger getNameDistinguisher()
  {
    return this.nameDistinguisher;
  }
  
  public NameOrPseudonym getNameOrPseudonym()
  {
    return this.nameOrPseudonym;
  }
  
  public DirectoryString getPlaceOfBirth()
  {
    return this.placeOfBirth;
  }
  
  public DirectoryString getPostalAddress()
  {
    return this.postalAddress;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.nameOrPseudonym);
    if (this.nameDistinguisher != null) {
      localASN1EncodableVector.add(new DERTaggedObject(false, 0, new ASN1Integer(this.nameDistinguisher)));
    }
    if (this.dateOfBirth != null) {
      localASN1EncodableVector.add(new DERTaggedObject(false, 1, this.dateOfBirth));
    }
    if (this.placeOfBirth != null) {
      localASN1EncodableVector.add(new DERTaggedObject(true, 2, this.placeOfBirth));
    }
    if (this.gender != null) {
      localASN1EncodableVector.add(new DERTaggedObject(false, 3, new DERPrintableString(this.gender, true)));
    }
    if (this.postalAddress != null) {
      localASN1EncodableVector.add(new DERTaggedObject(true, 4, this.postalAddress));
    }
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.x509.sigi.PersonalData
 * JD-Core Version:    0.7.0.1
 */