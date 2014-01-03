package org.spongycastle.x509.extension;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.PublicKey;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.x509.SubjectKeyIdentifier;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;

public class SubjectKeyIdentifierStructure
  extends SubjectKeyIdentifier
{
  public SubjectKeyIdentifierStructure(PublicKey paramPublicKey)
    throws InvalidKeyException
  {
    super(fromPublicKey(paramPublicKey));
  }
  
  public SubjectKeyIdentifierStructure(byte[] paramArrayOfByte)
    throws IOException
  {
    super((ASN1OctetString)X509ExtensionUtil.fromExtensionValue(paramArrayOfByte));
  }
  
  private static ASN1OctetString fromPublicKey(PublicKey paramPublicKey)
    throws InvalidKeyException
  {
    try
    {
      ASN1OctetString localASN1OctetString = (ASN1OctetString)new SubjectKeyIdentifier(SubjectPublicKeyInfo.getInstance(paramPublicKey.getEncoded())).toASN1Object();
      return localASN1OctetString;
    }
    catch (Exception localException)
    {
      throw new InvalidKeyException("Exception extracting key details: " + localException.toString());
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.x509.extension.SubjectKeyIdentifierStructure
 * JD-Core Version:    0.7.0.1
 */