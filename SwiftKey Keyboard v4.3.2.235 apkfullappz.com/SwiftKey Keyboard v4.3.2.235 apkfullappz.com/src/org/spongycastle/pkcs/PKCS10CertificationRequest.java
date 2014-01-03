package org.spongycastle.pkcs;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Set;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.pkcs.Attribute;
import org.spongycastle.asn1.pkcs.CertificationRequest;
import org.spongycastle.asn1.pkcs.CertificationRequestInfo;
import org.spongycastle.asn1.x500.X500Name;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.operator.ContentVerifier;
import org.spongycastle.operator.ContentVerifierProvider;

public class PKCS10CertificationRequest
{
  private static Attribute[] EMPTY_ARRAY = new Attribute[0];
  private CertificationRequest certificationRequest;
  
  public PKCS10CertificationRequest(CertificationRequest paramCertificationRequest)
  {
    this.certificationRequest = paramCertificationRequest;
  }
  
  public PKCS10CertificationRequest(byte[] paramArrayOfByte)
    throws IOException
  {
    this(parseBytes(paramArrayOfByte));
  }
  
  private static CertificationRequest parseBytes(byte[] paramArrayOfByte)
    throws IOException
  {
    try
    {
      CertificationRequest localCertificationRequest = CertificationRequest.getInstance(ASN1Primitive.fromByteArray(paramArrayOfByte));
      return localCertificationRequest;
    }
    catch (ClassCastException localClassCastException)
    {
      throw new PKCSIOException("malformed data: " + localClassCastException.getMessage(), localClassCastException);
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      throw new PKCSIOException("malformed data: " + localIllegalArgumentException.getMessage(), localIllegalArgumentException);
    }
  }
  
  public boolean equals(Object paramObject)
  {
    if (paramObject == this) {
      return true;
    }
    if (!(paramObject instanceof PKCS10CertificationRequest)) {
      return false;
    }
    PKCS10CertificationRequest localPKCS10CertificationRequest = (PKCS10CertificationRequest)paramObject;
    return toASN1Structure().equals(localPKCS10CertificationRequest.toASN1Structure());
  }
  
  public Attribute[] getAttributes()
  {
    ASN1Set localASN1Set = this.certificationRequest.getCertificationRequestInfo().getAttributes();
    Attribute[] arrayOfAttribute;
    if (localASN1Set == null) {
      arrayOfAttribute = EMPTY_ARRAY;
    }
    for (;;)
    {
      return arrayOfAttribute;
      arrayOfAttribute = new Attribute[localASN1Set.size()];
      for (int i = 0; i != localASN1Set.size(); i++) {
        arrayOfAttribute[i] = Attribute.getInstance(localASN1Set.getObjectAt(i));
      }
    }
  }
  
  public Attribute[] getAttributes(ASN1ObjectIdentifier paramASN1ObjectIdentifier)
  {
    ASN1Set localASN1Set = this.certificationRequest.getCertificationRequestInfo().getAttributes();
    if (localASN1Set == null) {
      return EMPTY_ARRAY;
    }
    ArrayList localArrayList = new ArrayList();
    for (int i = 0; i != localASN1Set.size(); i++)
    {
      Attribute localAttribute = Attribute.getInstance(localASN1Set.getObjectAt(i));
      if (localAttribute.getAttrType().equals(paramASN1ObjectIdentifier)) {
        localArrayList.add(localAttribute);
      }
    }
    if (localArrayList.size() == 0) {
      return EMPTY_ARRAY;
    }
    return (Attribute[])localArrayList.toArray(new Attribute[localArrayList.size()]);
  }
  
  public byte[] getEncoded()
    throws IOException
  {
    return this.certificationRequest.getEncoded();
  }
  
  public byte[] getSignature()
  {
    return this.certificationRequest.getSignature().getBytes();
  }
  
  public AlgorithmIdentifier getSignatureAlgorithm()
  {
    return this.certificationRequest.getSignatureAlgorithm();
  }
  
  public X500Name getSubject()
  {
    return X500Name.getInstance(this.certificationRequest.getCertificationRequestInfo().getSubject());
  }
  
  public SubjectPublicKeyInfo getSubjectPublicKeyInfo()
  {
    return this.certificationRequest.getCertificationRequestInfo().getSubjectPublicKeyInfo();
  }
  
  public int hashCode()
  {
    return toASN1Structure().hashCode();
  }
  
  public boolean isSignatureValid(ContentVerifierProvider paramContentVerifierProvider)
    throws PKCSException
  {
    CertificationRequestInfo localCertificationRequestInfo = this.certificationRequest.getCertificationRequestInfo();
    try
    {
      ContentVerifier localContentVerifier = paramContentVerifierProvider.get(this.certificationRequest.getSignatureAlgorithm());
      OutputStream localOutputStream = localContentVerifier.getOutputStream();
      localOutputStream.write(localCertificationRequestInfo.getEncoded("DER"));
      localOutputStream.close();
      return localContentVerifier.verify(this.certificationRequest.getSignature().getBytes());
    }
    catch (Exception localException)
    {
      throw new PKCSException("unable to process signature: " + localException.getMessage(), localException);
    }
  }
  
  public CertificationRequest toASN1Structure()
  {
    return this.certificationRequest;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.pkcs.PKCS10CertificationRequest
 * JD-Core Version:    0.7.0.1
 */