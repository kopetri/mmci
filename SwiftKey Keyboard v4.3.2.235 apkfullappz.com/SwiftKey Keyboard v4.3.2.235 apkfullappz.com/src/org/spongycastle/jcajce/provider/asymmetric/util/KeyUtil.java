package org.spongycastle.jcajce.provider.asymmetric.util;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.pkcs.PrivateKeyInfo;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;

public class KeyUtil
{
  public static byte[] getEncodedPrivateKeyInfo(PrivateKeyInfo paramPrivateKeyInfo)
  {
    try
    {
      byte[] arrayOfByte = paramPrivateKeyInfo.getEncoded("DER");
      return arrayOfByte;
    }
    catch (Exception localException) {}
    return null;
  }
  
  public static byte[] getEncodedPrivateKeyInfo(AlgorithmIdentifier paramAlgorithmIdentifier, ASN1Encodable paramASN1Encodable)
  {
    try
    {
      byte[] arrayOfByte = getEncodedPrivateKeyInfo(new PrivateKeyInfo(paramAlgorithmIdentifier, paramASN1Encodable.toASN1Primitive()));
      return arrayOfByte;
    }
    catch (Exception localException) {}
    return null;
  }
  
  public static byte[] getEncodedSubjectPublicKeyInfo(AlgorithmIdentifier paramAlgorithmIdentifier, ASN1Encodable paramASN1Encodable)
  {
    try
    {
      byte[] arrayOfByte = getEncodedSubjectPublicKeyInfo(new SubjectPublicKeyInfo(paramAlgorithmIdentifier, paramASN1Encodable));
      return arrayOfByte;
    }
    catch (Exception localException) {}
    return null;
  }
  
  public static byte[] getEncodedSubjectPublicKeyInfo(AlgorithmIdentifier paramAlgorithmIdentifier, byte[] paramArrayOfByte)
  {
    try
    {
      byte[] arrayOfByte = getEncodedSubjectPublicKeyInfo(new SubjectPublicKeyInfo(paramAlgorithmIdentifier, paramArrayOfByte));
      return arrayOfByte;
    }
    catch (Exception localException) {}
    return null;
  }
  
  public static byte[] getEncodedSubjectPublicKeyInfo(SubjectPublicKeyInfo paramSubjectPublicKeyInfo)
  {
    try
    {
      byte[] arrayOfByte = paramSubjectPublicKeyInfo.getEncoded("DER");
      return arrayOfByte;
    }
    catch (Exception localException) {}
    return null;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jcajce.provider.asymmetric.util.KeyUtil
 * JD-Core Version:    0.7.0.1
 */