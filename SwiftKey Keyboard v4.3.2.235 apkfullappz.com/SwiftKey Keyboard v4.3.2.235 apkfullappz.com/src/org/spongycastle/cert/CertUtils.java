package org.spongycastle.cert;

import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERGeneralizedTime;
import org.spongycastle.asn1.DEROutputStream;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.AttributeCertificate;
import org.spongycastle.asn1.x509.AttributeCertificateInfo;
import org.spongycastle.asn1.x509.Certificate;
import org.spongycastle.asn1.x509.CertificateList;
import org.spongycastle.asn1.x509.Extensions;
import org.spongycastle.asn1.x509.ExtensionsGenerator;
import org.spongycastle.asn1.x509.TBSCertList;
import org.spongycastle.asn1.x509.TBSCertificate;
import org.spongycastle.operator.ContentSigner;

class CertUtils
{
  private static List EMPTY_LIST = Collections.unmodifiableList(new ArrayList());
  private static Set EMPTY_SET = Collections.unmodifiableSet(new HashSet());
  
  static void addExtension(ExtensionsGenerator paramExtensionsGenerator, ASN1ObjectIdentifier paramASN1ObjectIdentifier, boolean paramBoolean, ASN1Encodable paramASN1Encodable)
    throws CertIOException
  {
    try
    {
      paramExtensionsGenerator.addExtension(paramASN1ObjectIdentifier, paramBoolean, paramASN1Encodable);
      return;
    }
    catch (IOException localIOException)
    {
      throw new CertIOException("cannot encode extension: " + localIOException.getMessage(), localIOException);
    }
  }
  
  static boolean[] bitStringToBoolean(DERBitString paramDERBitString)
  {
    boolean[] arrayOfBoolean;
    if (paramDERBitString != null)
    {
      byte[] arrayOfByte = paramDERBitString.getBytes();
      arrayOfBoolean = new boolean[8 * arrayOfByte.length - paramDERBitString.getPadBits()];
      int i = 0;
      if (i != arrayOfBoolean.length)
      {
        if ((arrayOfByte[(i / 8)] & 128 >>> i % 8) != 0) {}
        for (int j = 1;; j = 0)
        {
          arrayOfBoolean[i] = j;
          i++;
          break;
        }
      }
    }
    else
    {
      arrayOfBoolean = null;
    }
    return arrayOfBoolean;
  }
  
  static DERBitString booleanToBitString(boolean[] paramArrayOfBoolean)
  {
    byte[] arrayOfByte = new byte[(7 + paramArrayOfBoolean.length) / 8];
    int i = 0;
    if (i != paramArrayOfBoolean.length)
    {
      int k = i / 8;
      int m = arrayOfByte[k];
      if (paramArrayOfBoolean[i] != 0) {}
      for (int n = 1 << 7 - i % 8;; n = 0)
      {
        arrayOfByte[k] = ((byte)(n | m));
        i++;
        break;
      }
    }
    int j = paramArrayOfBoolean.length % 8;
    if (j == 0) {
      return new DERBitString(arrayOfByte);
    }
    return new DERBitString(arrayOfByte, 8 - j);
  }
  
  private static AttributeCertificate generateAttrStructure(AttributeCertificateInfo paramAttributeCertificateInfo, AlgorithmIdentifier paramAlgorithmIdentifier, byte[] paramArrayOfByte)
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(paramAttributeCertificateInfo);
    localASN1EncodableVector.add(paramAlgorithmIdentifier);
    localASN1EncodableVector.add(new DERBitString(paramArrayOfByte));
    return AttributeCertificate.getInstance(new DERSequence(localASN1EncodableVector));
  }
  
  private static CertificateList generateCRLStructure(TBSCertList paramTBSCertList, AlgorithmIdentifier paramAlgorithmIdentifier, byte[] paramArrayOfByte)
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(paramTBSCertList);
    localASN1EncodableVector.add(paramAlgorithmIdentifier);
    localASN1EncodableVector.add(new DERBitString(paramArrayOfByte));
    return CertificateList.getInstance(new DERSequence(localASN1EncodableVector));
  }
  
  static X509AttributeCertificateHolder generateFullAttrCert(ContentSigner paramContentSigner, AttributeCertificateInfo paramAttributeCertificateInfo)
  {
    try
    {
      X509AttributeCertificateHolder localX509AttributeCertificateHolder = new X509AttributeCertificateHolder(generateAttrStructure(paramAttributeCertificateInfo, paramContentSigner.getAlgorithmIdentifier(), generateSig(paramContentSigner, paramAttributeCertificateInfo)));
      return localX509AttributeCertificateHolder;
    }
    catch (IOException localIOException)
    {
      throw new IllegalStateException("cannot produce attribute certificate signature");
    }
  }
  
  static X509CRLHolder generateFullCRL(ContentSigner paramContentSigner, TBSCertList paramTBSCertList)
  {
    try
    {
      X509CRLHolder localX509CRLHolder = new X509CRLHolder(generateCRLStructure(paramTBSCertList, paramContentSigner.getAlgorithmIdentifier(), generateSig(paramContentSigner, paramTBSCertList)));
      return localX509CRLHolder;
    }
    catch (IOException localIOException)
    {
      throw new IllegalStateException("cannot produce certificate signature");
    }
  }
  
  static X509CertificateHolder generateFullCert(ContentSigner paramContentSigner, TBSCertificate paramTBSCertificate)
  {
    try
    {
      X509CertificateHolder localX509CertificateHolder = new X509CertificateHolder(generateStructure(paramTBSCertificate, paramContentSigner.getAlgorithmIdentifier(), generateSig(paramContentSigner, paramTBSCertificate)));
      return localX509CertificateHolder;
    }
    catch (IOException localIOException)
    {
      throw new IllegalStateException("cannot produce certificate signature");
    }
  }
  
  private static byte[] generateSig(ContentSigner paramContentSigner, ASN1Encodable paramASN1Encodable)
    throws IOException
  {
    OutputStream localOutputStream = paramContentSigner.getOutputStream();
    new DEROutputStream(localOutputStream).writeObject(paramASN1Encodable);
    localOutputStream.close();
    return paramContentSigner.getSignature();
  }
  
  private static Certificate generateStructure(TBSCertificate paramTBSCertificate, AlgorithmIdentifier paramAlgorithmIdentifier, byte[] paramArrayOfByte)
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(paramTBSCertificate);
    localASN1EncodableVector.add(paramAlgorithmIdentifier);
    localASN1EncodableVector.add(new DERBitString(paramArrayOfByte));
    return Certificate.getInstance(new DERSequence(localASN1EncodableVector));
  }
  
  static Set getCriticalExtensionOIDs(Extensions paramExtensions)
  {
    if (paramExtensions == null) {
      return EMPTY_SET;
    }
    return Collections.unmodifiableSet(new HashSet(Arrays.asList(paramExtensions.getCriticalExtensionOIDs())));
  }
  
  static List getExtensionOIDs(Extensions paramExtensions)
  {
    if (paramExtensions == null) {
      return EMPTY_LIST;
    }
    return Collections.unmodifiableList(Arrays.asList(paramExtensions.getExtensionOIDs()));
  }
  
  static Set getNonCriticalExtensionOIDs(Extensions paramExtensions)
  {
    if (paramExtensions == null) {
      return EMPTY_SET;
    }
    return Collections.unmodifiableSet(new HashSet(Arrays.asList(paramExtensions.getNonCriticalExtensionOIDs())));
  }
  
  static Date recoverDate(DERGeneralizedTime paramDERGeneralizedTime)
  {
    try
    {
      Date localDate = paramDERGeneralizedTime.getDate();
      return localDate;
    }
    catch (ParseException localParseException)
    {
      throw new IllegalStateException("unable to recover date: " + localParseException.getMessage());
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cert.CertUtils
 * JD-Core Version:    0.7.0.1
 */