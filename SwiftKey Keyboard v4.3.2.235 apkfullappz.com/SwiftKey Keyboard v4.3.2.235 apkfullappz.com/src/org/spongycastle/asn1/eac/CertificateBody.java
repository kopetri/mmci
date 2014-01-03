package org.spongycastle.asn1.eac;

import java.io.IOException;
import java.io.PrintStream;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERApplicationSpecific;
import org.spongycastle.asn1.DEROctetString;

public class CertificateBody
  extends ASN1Object
{
  private static final int CAR = 2;
  private static final int CEfD = 32;
  private static final int CExD = 64;
  private static final int CHA = 16;
  private static final int CHR = 8;
  private static final int CPI = 1;
  private static final int PK = 4;
  public static final int profileType = 127;
  public static final int requestType = 13;
  private DERApplicationSpecific certificateEffectiveDate;
  private DERApplicationSpecific certificateExpirationDate;
  private CertificateHolderAuthorization certificateHolderAuthorization;
  private DERApplicationSpecific certificateHolderReference;
  private DERApplicationSpecific certificateProfileIdentifier;
  private int certificateType = 0;
  private DERApplicationSpecific certificationAuthorityReference;
  private PublicKeyDataObject publicKey;
  ASN1InputStream seq;
  
  private CertificateBody(DERApplicationSpecific paramDERApplicationSpecific)
    throws IOException
  {
    setIso7816CertificateBody(paramDERApplicationSpecific);
  }
  
  public CertificateBody(DERApplicationSpecific paramDERApplicationSpecific, CertificationAuthorityReference paramCertificationAuthorityReference, PublicKeyDataObject paramPublicKeyDataObject, CertificateHolderReference paramCertificateHolderReference, CertificateHolderAuthorization paramCertificateHolderAuthorization, PackedDate paramPackedDate1, PackedDate paramPackedDate2)
  {
    setCertificateProfileIdentifier(paramDERApplicationSpecific);
    setCertificationAuthorityReference(new DERApplicationSpecific(2, paramCertificationAuthorityReference.getEncoded()));
    setPublicKey(paramPublicKeyDataObject);
    setCertificateHolderReference(new DERApplicationSpecific(32, paramCertificateHolderReference.getEncoded()));
    setCertificateHolderAuthorization(paramCertificateHolderAuthorization);
    try
    {
      setCertificateEffectiveDate(new DERApplicationSpecific(false, 37, new DEROctetString(paramPackedDate1.getEncoding())));
      setCertificateExpirationDate(new DERApplicationSpecific(false, 36, new DEROctetString(paramPackedDate2.getEncoding())));
      return;
    }
    catch (IOException localIOException)
    {
      throw new IllegalArgumentException("unable to encode dates: " + localIOException.getMessage());
    }
  }
  
  public static CertificateBody getInstance(Object paramObject)
    throws IOException
  {
    if ((paramObject instanceof CertificateBody)) {
      return (CertificateBody)paramObject;
    }
    if (paramObject != null) {
      return new CertificateBody(DERApplicationSpecific.getInstance(paramObject));
    }
    return null;
  }
  
  private ASN1Primitive profileToASN1Object()
    throws IOException
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.certificateProfileIdentifier);
    localASN1EncodableVector.add(this.certificationAuthorityReference);
    localASN1EncodableVector.add(new DERApplicationSpecific(false, 73, this.publicKey));
    localASN1EncodableVector.add(this.certificateHolderReference);
    localASN1EncodableVector.add(this.certificateHolderAuthorization);
    localASN1EncodableVector.add(this.certificateEffectiveDate);
    localASN1EncodableVector.add(this.certificateExpirationDate);
    return new DERApplicationSpecific(78, localASN1EncodableVector);
  }
  
  private ASN1Primitive requestToASN1Object()
    throws IOException
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.certificateProfileIdentifier);
    localASN1EncodableVector.add(new DERApplicationSpecific(false, 73, this.publicKey));
    localASN1EncodableVector.add(this.certificateHolderReference);
    return new DERApplicationSpecific(78, localASN1EncodableVector);
  }
  
  private void setCertificateEffectiveDate(DERApplicationSpecific paramDERApplicationSpecific)
    throws IllegalArgumentException
  {
    if (paramDERApplicationSpecific.getApplicationTag() == 37)
    {
      this.certificateEffectiveDate = paramDERApplicationSpecific;
      this.certificateType = (0x20 | this.certificateType);
      return;
    }
    throw new IllegalArgumentException("Not an Iso7816Tags.APPLICATION_EFFECTIVE_DATE tag :" + EACTags.encodeTag(paramDERApplicationSpecific));
  }
  
  private void setCertificateExpirationDate(DERApplicationSpecific paramDERApplicationSpecific)
    throws IllegalArgumentException
  {
    if (paramDERApplicationSpecific.getApplicationTag() == 36)
    {
      this.certificateExpirationDate = paramDERApplicationSpecific;
      this.certificateType = (0x40 | this.certificateType);
      return;
    }
    throw new IllegalArgumentException("Not an Iso7816Tags.APPLICATION_EXPIRATION_DATE tag");
  }
  
  private void setCertificateHolderAuthorization(CertificateHolderAuthorization paramCertificateHolderAuthorization)
  {
    this.certificateHolderAuthorization = paramCertificateHolderAuthorization;
    this.certificateType = (0x10 | this.certificateType);
  }
  
  private void setCertificateHolderReference(DERApplicationSpecific paramDERApplicationSpecific)
    throws IllegalArgumentException
  {
    if (paramDERApplicationSpecific.getApplicationTag() == 32)
    {
      this.certificateHolderReference = paramDERApplicationSpecific;
      this.certificateType = (0x8 | this.certificateType);
      return;
    }
    throw new IllegalArgumentException("Not an Iso7816Tags.CARDHOLDER_NAME tag");
  }
  
  private void setCertificateProfileIdentifier(DERApplicationSpecific paramDERApplicationSpecific)
    throws IllegalArgumentException
  {
    if (paramDERApplicationSpecific.getApplicationTag() == 41)
    {
      this.certificateProfileIdentifier = paramDERApplicationSpecific;
      this.certificateType = (0x1 | this.certificateType);
      return;
    }
    throw new IllegalArgumentException("Not an Iso7816Tags.INTERCHANGE_PROFILE tag :" + EACTags.encodeTag(paramDERApplicationSpecific));
  }
  
  private void setCertificationAuthorityReference(DERApplicationSpecific paramDERApplicationSpecific)
    throws IllegalArgumentException
  {
    if (paramDERApplicationSpecific.getApplicationTag() == 2)
    {
      this.certificationAuthorityReference = paramDERApplicationSpecific;
      this.certificateType = (0x2 | this.certificateType);
      return;
    }
    throw new IllegalArgumentException("Not an Iso7816Tags.ISSUER_IDENTIFICATION_NUMBER tag");
  }
  
  private void setIso7816CertificateBody(DERApplicationSpecific paramDERApplicationSpecific)
    throws IOException
  {
    ASN1InputStream localASN1InputStream;
    if (paramDERApplicationSpecific.getApplicationTag() == 78) {
      localASN1InputStream = new ASN1InputStream(paramDERApplicationSpecific.getContents());
    }
    for (;;)
    {
      ASN1Primitive localASN1Primitive = localASN1InputStream.readObject();
      if (localASN1Primitive == null) {
        break;
      }
      DERApplicationSpecific localDERApplicationSpecific;
      if ((localASN1Primitive instanceof DERApplicationSpecific)) {
        localDERApplicationSpecific = (DERApplicationSpecific)localASN1Primitive;
      }
      switch (localDERApplicationSpecific.getApplicationTag())
      {
      default: 
        this.certificateType = 0;
        throw new IOException("Not a valid iso7816 DERApplicationSpecific tag " + localDERApplicationSpecific.getApplicationTag());
        throw new IOException("Bad tag : not an iso7816 CERTIFICATE_CONTENT_TEMPLATE");
        throw new IOException("Not a valid iso7816 content : not a DERApplicationSpecific Object :" + EACTags.encodeTag(paramDERApplicationSpecific) + localASN1Primitive.getClass());
      case 41: 
        setCertificateProfileIdentifier(localDERApplicationSpecific);
        break;
      case 2: 
        setCertificationAuthorityReference(localDERApplicationSpecific);
        break;
      case 73: 
        setPublicKey(PublicKeyDataObject.getInstance(localDERApplicationSpecific.getObject(16)));
        break;
      case 32: 
        setCertificateHolderReference(localDERApplicationSpecific);
        break;
      case 76: 
        setCertificateHolderAuthorization(new CertificateHolderAuthorization(localDERApplicationSpecific));
        break;
      case 37: 
        setCertificateEffectiveDate(localDERApplicationSpecific);
        break;
      case 36: 
        setCertificateExpirationDate(localDERApplicationSpecific);
      }
    }
  }
  
  private void setPublicKey(PublicKeyDataObject paramPublicKeyDataObject)
  {
    this.publicKey = PublicKeyDataObject.getInstance(paramPublicKeyDataObject);
    this.certificateType = (0x4 | this.certificateType);
  }
  
  public PackedDate getCertificateEffectiveDate()
  {
    if ((0x20 & this.certificateType) == 32) {
      return new PackedDate(this.certificateEffectiveDate.getContents());
    }
    return null;
  }
  
  public PackedDate getCertificateExpirationDate()
    throws IOException
  {
    if ((0x40 & this.certificateType) == 64) {
      return new PackedDate(this.certificateExpirationDate.getContents());
    }
    throw new IOException("certificate Expiration Date not set");
  }
  
  public CertificateHolderAuthorization getCertificateHolderAuthorization()
    throws IOException
  {
    if ((0x10 & this.certificateType) == 16) {
      return this.certificateHolderAuthorization;
    }
    throw new IOException("Certificate Holder Authorisation not set");
  }
  
  public CertificateHolderReference getCertificateHolderReference()
  {
    return new CertificateHolderReference(this.certificateHolderReference.getContents());
  }
  
  public DERApplicationSpecific getCertificateProfileIdentifier()
  {
    return this.certificateProfileIdentifier;
  }
  
  public int getCertificateType()
  {
    return this.certificateType;
  }
  
  public CertificationAuthorityReference getCertificationAuthorityReference()
    throws IOException
  {
    if ((0x2 & this.certificateType) == 2) {
      return new CertificationAuthorityReference(this.certificationAuthorityReference.getContents());
    }
    throw new IOException("Certification authority reference not set");
  }
  
  public PublicKeyDataObject getPublicKey()
  {
    return this.publicKey;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    try
    {
      if (this.certificateType == 127) {
        return profileToASN1Object();
      }
      if (this.certificateType == 13)
      {
        ASN1Primitive localASN1Primitive = requestToASN1Object();
        return localASN1Primitive;
      }
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
      return null;
    }
    System.err.println("returning null");
    return null;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.eac.CertificateBody
 * JD-Core Version:    0.7.0.1
 */