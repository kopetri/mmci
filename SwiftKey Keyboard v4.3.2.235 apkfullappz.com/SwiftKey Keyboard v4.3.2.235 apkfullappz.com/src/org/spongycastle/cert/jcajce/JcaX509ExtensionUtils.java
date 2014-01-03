package org.spongycastle.cert.jcajce;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.oiw.OIWObjectIdentifiers;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.AuthorityKeyIdentifier;
import org.spongycastle.asn1.x509.SubjectKeyIdentifier;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.cert.X509ExtensionUtils;
import org.spongycastle.operator.DigestCalculator;

public class JcaX509ExtensionUtils
  extends X509ExtensionUtils
{
  public JcaX509ExtensionUtils()
    throws NoSuchAlgorithmException
  {
    super(new SHA1DigestCalculator(MessageDigest.getInstance("SHA1")));
  }
  
  public JcaX509ExtensionUtils(DigestCalculator paramDigestCalculator)
  {
    super(paramDigestCalculator);
  }
  
  public static ASN1Primitive parseExtensionValue(byte[] paramArrayOfByte)
    throws IOException
  {
    return ASN1Primitive.fromByteArray(ASN1OctetString.getInstance(paramArrayOfByte).getOctets());
  }
  
  public AuthorityKeyIdentifier createAuthorityKeyIdentifier(PublicKey paramPublicKey)
  {
    return super.createAuthorityKeyIdentifier(SubjectPublicKeyInfo.getInstance(paramPublicKey.getEncoded()));
  }
  
  public AuthorityKeyIdentifier createAuthorityKeyIdentifier(X509Certificate paramX509Certificate)
    throws CertificateEncodingException
  {
    return super.createAuthorityKeyIdentifier(new JcaX509CertificateHolder(paramX509Certificate));
  }
  
  public SubjectKeyIdentifier createSubjectKeyIdentifier(PublicKey paramPublicKey)
  {
    return super.createSubjectKeyIdentifier(SubjectPublicKeyInfo.getInstance(paramPublicKey.getEncoded()));
  }
  
  public SubjectKeyIdentifier createTruncatedSubjectKeyIdentifier(PublicKey paramPublicKey)
  {
    return super.createSubjectKeyIdentifier(SubjectPublicKeyInfo.getInstance(paramPublicKey.getEncoded()));
  }
  
  private static class SHA1DigestCalculator
    implements DigestCalculator
  {
    private ByteArrayOutputStream bOut = new ByteArrayOutputStream();
    private MessageDigest digest;
    
    public SHA1DigestCalculator(MessageDigest paramMessageDigest)
    {
      this.digest = paramMessageDigest;
    }
    
    public AlgorithmIdentifier getAlgorithmIdentifier()
    {
      return new AlgorithmIdentifier(OIWObjectIdentifiers.idSHA1);
    }
    
    public byte[] getDigest()
    {
      byte[] arrayOfByte = this.digest.digest(this.bOut.toByteArray());
      this.bOut.reset();
      return arrayOfByte;
    }
    
    public OutputStream getOutputStream()
    {
      return this.bOut;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cert.jcajce.JcaX509ExtensionUtils
 * JD-Core Version:    0.7.0.1
 */