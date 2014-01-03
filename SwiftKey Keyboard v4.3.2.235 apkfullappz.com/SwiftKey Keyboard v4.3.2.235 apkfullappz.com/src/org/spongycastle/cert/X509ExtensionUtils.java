package org.spongycastle.cert;

import java.io.IOException;
import java.io.OutputStream;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.x509.AuthorityKeyIdentifier;
import org.spongycastle.asn1.x509.Extension;
import org.spongycastle.asn1.x509.GeneralName;
import org.spongycastle.asn1.x509.GeneralNames;
import org.spongycastle.asn1.x509.SubjectKeyIdentifier;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.operator.DigestCalculator;

public class X509ExtensionUtils
{
  private DigestCalculator calculator;
  
  public X509ExtensionUtils(DigestCalculator paramDigestCalculator)
  {
    this.calculator = paramDigestCalculator;
  }
  
  private byte[] calculateIdentifier(SubjectPublicKeyInfo paramSubjectPublicKeyInfo)
  {
    byte[] arrayOfByte = paramSubjectPublicKeyInfo.getPublicKeyData().getBytes();
    OutputStream localOutputStream = this.calculator.getOutputStream();
    try
    {
      localOutputStream.write(arrayOfByte);
      localOutputStream.close();
      label27:
      return this.calculator.getDigest();
    }
    catch (IOException localIOException)
    {
      break label27;
    }
  }
  
  public AuthorityKeyIdentifier createAuthorityKeyIdentifier(SubjectPublicKeyInfo paramSubjectPublicKeyInfo)
  {
    return new AuthorityKeyIdentifier(calculateIdentifier(paramSubjectPublicKeyInfo));
  }
  
  public AuthorityKeyIdentifier createAuthorityKeyIdentifier(X509CertificateHolder paramX509CertificateHolder)
  {
    if (paramX509CertificateHolder.getVersionNumber() != 3)
    {
      GeneralName localGeneralName1 = new GeneralName(paramX509CertificateHolder.getIssuer());
      return new AuthorityKeyIdentifier(calculateIdentifier(paramX509CertificateHolder.getSubjectPublicKeyInfo()), new GeneralNames(localGeneralName1), paramX509CertificateHolder.getSerialNumber());
    }
    GeneralName localGeneralName2 = new GeneralName(paramX509CertificateHolder.getIssuer());
    Extension localExtension = paramX509CertificateHolder.getExtension(Extension.subjectKeyIdentifier);
    if (localExtension != null) {
      return new AuthorityKeyIdentifier(ASN1OctetString.getInstance(localExtension.getParsedValue()).getOctets(), new GeneralNames(localGeneralName2), paramX509CertificateHolder.getSerialNumber());
    }
    return new AuthorityKeyIdentifier(calculateIdentifier(paramX509CertificateHolder.getSubjectPublicKeyInfo()), new GeneralNames(localGeneralName2), paramX509CertificateHolder.getSerialNumber());
  }
  
  public SubjectKeyIdentifier createSubjectKeyIdentifier(SubjectPublicKeyInfo paramSubjectPublicKeyInfo)
  {
    return new SubjectKeyIdentifier(calculateIdentifier(paramSubjectPublicKeyInfo));
  }
  
  public SubjectKeyIdentifier createTruncatedSubjectKeyIdentifier(SubjectPublicKeyInfo paramSubjectPublicKeyInfo)
  {
    byte[] arrayOfByte1 = calculateIdentifier(paramSubjectPublicKeyInfo);
    byte[] arrayOfByte2 = new byte[8];
    System.arraycopy(arrayOfByte1, -8 + arrayOfByte1.length, arrayOfByte2, 0, arrayOfByte2.length);
    arrayOfByte2[0] = ((byte)(0xF & arrayOfByte2[0]));
    arrayOfByte2[0] = ((byte)(0x40 | arrayOfByte2[0]));
    return new SubjectKeyIdentifier(arrayOfByte2);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cert.X509ExtensionUtils
 * JD-Core Version:    0.7.0.1
 */