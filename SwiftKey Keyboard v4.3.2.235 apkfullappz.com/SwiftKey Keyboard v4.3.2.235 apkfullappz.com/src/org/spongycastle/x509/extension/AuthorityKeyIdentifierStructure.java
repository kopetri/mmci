package org.spongycastle.x509.extension;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.PublicKey;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.x509.AuthorityKeyIdentifier;
import org.spongycastle.asn1.x509.Extension;
import org.spongycastle.asn1.x509.GeneralName;
import org.spongycastle.asn1.x509.GeneralNames;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.asn1.x509.X509Extension;
import org.spongycastle.asn1.x509.X509Extensions;
import org.spongycastle.jce.PrincipalUtil;

public class AuthorityKeyIdentifierStructure
  extends AuthorityKeyIdentifier
{
  public AuthorityKeyIdentifierStructure(PublicKey paramPublicKey)
    throws InvalidKeyException
  {
    super(fromKey(paramPublicKey));
  }
  
  public AuthorityKeyIdentifierStructure(X509Certificate paramX509Certificate)
    throws CertificateParsingException
  {
    super(fromCertificate(paramX509Certificate));
  }
  
  public AuthorityKeyIdentifierStructure(Extension paramExtension)
  {
    super((ASN1Sequence)paramExtension.getParsedValue());
  }
  
  public AuthorityKeyIdentifierStructure(X509Extension paramX509Extension)
  {
    super((ASN1Sequence)paramX509Extension.getParsedValue());
  }
  
  public AuthorityKeyIdentifierStructure(byte[] paramArrayOfByte)
    throws IOException
  {
    super((ASN1Sequence)X509ExtensionUtil.fromExtensionValue(paramArrayOfByte));
  }
  
  private static ASN1Sequence fromCertificate(X509Certificate paramX509Certificate)
    throws CertificateParsingException
  {
    try
    {
      if (paramX509Certificate.getVersion() != 3)
      {
        GeneralName localGeneralName1 = new GeneralName(PrincipalUtil.getIssuerX509Principal(paramX509Certificate));
        return (ASN1Sequence)new AuthorityKeyIdentifier(new SubjectPublicKeyInfo((ASN1Sequence)new ASN1InputStream(paramX509Certificate.getPublicKey().getEncoded()).readObject()), new GeneralNames(localGeneralName1), paramX509Certificate.getSerialNumber()).toASN1Object();
      }
      GeneralName localGeneralName2 = new GeneralName(PrincipalUtil.getIssuerX509Principal(paramX509Certificate));
      byte[] arrayOfByte = paramX509Certificate.getExtensionValue(X509Extensions.SubjectKeyIdentifier.getId());
      if (arrayOfByte != null) {
        return (ASN1Sequence)new AuthorityKeyIdentifier(((ASN1OctetString)X509ExtensionUtil.fromExtensionValue(arrayOfByte)).getOctets(), new GeneralNames(localGeneralName2), paramX509Certificate.getSerialNumber()).toASN1Object();
      }
      ASN1Sequence localASN1Sequence = (ASN1Sequence)new AuthorityKeyIdentifier(new SubjectPublicKeyInfo((ASN1Sequence)new ASN1InputStream(paramX509Certificate.getPublicKey().getEncoded()).readObject()), new GeneralNames(localGeneralName2), paramX509Certificate.getSerialNumber()).toASN1Object();
      return localASN1Sequence;
    }
    catch (Exception localException)
    {
      throw new CertificateParsingException("Exception extracting certificate details: " + localException.toString());
    }
  }
  
  private static ASN1Sequence fromKey(PublicKey paramPublicKey)
    throws InvalidKeyException
  {
    try
    {
      ASN1Sequence localASN1Sequence = (ASN1Sequence)new AuthorityKeyIdentifier(new SubjectPublicKeyInfo((ASN1Sequence)new ASN1InputStream(paramPublicKey.getEncoded()).readObject())).toASN1Object();
      return localASN1Sequence;
    }
    catch (Exception localException)
    {
      throw new InvalidKeyException("can't process key: " + localException);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.x509.extension.AuthorityKeyIdentifierStructure
 * JD-Core Version:    0.7.0.1
 */