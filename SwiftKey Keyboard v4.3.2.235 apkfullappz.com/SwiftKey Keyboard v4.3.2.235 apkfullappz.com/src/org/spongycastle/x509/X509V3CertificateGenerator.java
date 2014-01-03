package org.spongycastle.x509;

import java.io.IOException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.Iterator;
import javax.security.auth.x500.X500Principal;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERObjectIdentifier;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.asn1.x509.TBSCertificate;
import org.spongycastle.asn1.x509.Time;
import org.spongycastle.asn1.x509.V3TBSCertificateGenerator;
import org.spongycastle.asn1.x509.X509CertificateStructure;
import org.spongycastle.asn1.x509.X509ExtensionsGenerator;
import org.spongycastle.asn1.x509.X509Name;
import org.spongycastle.jce.X509Principal;
import org.spongycastle.jce.provider.X509CertificateObject;
import org.spongycastle.x509.extension.X509ExtensionUtil;

public class X509V3CertificateGenerator
{
  private X509ExtensionsGenerator extGenerator = new X509ExtensionsGenerator();
  private AlgorithmIdentifier sigAlgId;
  private DERObjectIdentifier sigOID;
  private String signatureAlgorithm;
  private V3TBSCertificateGenerator tbsGen = new V3TBSCertificateGenerator();
  
  private DERBitString booleanToBitString(boolean[] paramArrayOfBoolean)
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
  
  private X509Certificate generateJcaObject(TBSCertificate paramTBSCertificate, byte[] paramArrayOfByte)
    throws CertificateParsingException
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(paramTBSCertificate);
    localASN1EncodableVector.add(this.sigAlgId);
    localASN1EncodableVector.add(new DERBitString(paramArrayOfByte));
    return new X509CertificateObject(new X509CertificateStructure(new DERSequence(localASN1EncodableVector)));
  }
  
  private TBSCertificate generateTbsCert()
  {
    if (!this.extGenerator.isEmpty()) {
      this.tbsGen.setExtensions(this.extGenerator.generate());
    }
    return this.tbsGen.generateTBSCertificate();
  }
  
  public void addExtension(String paramString, boolean paramBoolean, ASN1Encodable paramASN1Encodable)
  {
    addExtension(new DERObjectIdentifier(paramString), paramBoolean, paramASN1Encodable);
  }
  
  public void addExtension(String paramString, boolean paramBoolean, byte[] paramArrayOfByte)
  {
    addExtension(new DERObjectIdentifier(paramString), paramBoolean, paramArrayOfByte);
  }
  
  public void addExtension(DERObjectIdentifier paramDERObjectIdentifier, boolean paramBoolean, ASN1Encodable paramASN1Encodable)
  {
    this.extGenerator.addExtension(new ASN1ObjectIdentifier(paramDERObjectIdentifier.getId()), paramBoolean, paramASN1Encodable);
  }
  
  public void addExtension(DERObjectIdentifier paramDERObjectIdentifier, boolean paramBoolean, byte[] paramArrayOfByte)
  {
    this.extGenerator.addExtension(new ASN1ObjectIdentifier(paramDERObjectIdentifier.getId()), paramBoolean, paramArrayOfByte);
  }
  
  public void copyAndAddExtension(String paramString, boolean paramBoolean, X509Certificate paramX509Certificate)
    throws CertificateParsingException
  {
    byte[] arrayOfByte = paramX509Certificate.getExtensionValue(paramString);
    if (arrayOfByte == null) {
      throw new CertificateParsingException("extension " + paramString + " not present");
    }
    try
    {
      addExtension(paramString, paramBoolean, X509ExtensionUtil.fromExtensionValue(arrayOfByte));
      return;
    }
    catch (IOException localIOException)
    {
      throw new CertificateParsingException(localIOException.toString());
    }
  }
  
  public void copyAndAddExtension(DERObjectIdentifier paramDERObjectIdentifier, boolean paramBoolean, X509Certificate paramX509Certificate)
    throws CertificateParsingException
  {
    copyAndAddExtension(paramDERObjectIdentifier.getId(), paramBoolean, paramX509Certificate);
  }
  
  public X509Certificate generate(PrivateKey paramPrivateKey)
    throws CertificateEncodingException, IllegalStateException, NoSuchAlgorithmException, SignatureException, InvalidKeyException
  {
    return generate(paramPrivateKey, null);
  }
  
  public X509Certificate generate(PrivateKey paramPrivateKey, String paramString)
    throws CertificateEncodingException, IllegalStateException, NoSuchProviderException, NoSuchAlgorithmException, SignatureException, InvalidKeyException
  {
    return generate(paramPrivateKey, paramString, null);
  }
  
  /* Error */
  public X509Certificate generate(PrivateKey paramPrivateKey, String paramString, SecureRandom paramSecureRandom)
    throws CertificateEncodingException, IllegalStateException, NoSuchProviderException, NoSuchAlgorithmException, SignatureException, InvalidKeyException
  {
    // Byte code:
    //   0: aload_0
    //   1: invokespecial 169	org/spongycastle/x509/X509V3CertificateGenerator:generateTbsCert	()Lorg/spongycastle/asn1/x509/TBSCertificate;
    //   4: astore 4
    //   6: aload_0
    //   7: getfield 171	org/spongycastle/x509/X509V3CertificateGenerator:sigOID	Lorg/spongycastle/asn1/DERObjectIdentifier;
    //   10: aload_0
    //   11: getfield 173	org/spongycastle/x509/X509V3CertificateGenerator:signatureAlgorithm	Ljava/lang/String;
    //   14: aload_2
    //   15: aload_1
    //   16: aload_3
    //   17: aload 4
    //   19: invokestatic 179	org/spongycastle/x509/X509Util:calculateSignature	(Lorg/spongycastle/asn1/DERObjectIdentifier;Ljava/lang/String;Ljava/lang/String;Ljava/security/PrivateKey;Ljava/security/SecureRandom;Lorg/spongycastle/asn1/ASN1Encodable;)[B
    //   22: astore 6
    //   24: aload_0
    //   25: aload 4
    //   27: aload 6
    //   29: invokespecial 181	org/spongycastle/x509/X509V3CertificateGenerator:generateJcaObject	(Lorg/spongycastle/asn1/x509/TBSCertificate;[B)Ljava/security/cert/X509Certificate;
    //   32: astore 8
    //   34: aload 8
    //   36: areturn
    //   37: astore 5
    //   39: new 183	org/spongycastle/x509/ExtCertificateEncodingException
    //   42: dup
    //   43: ldc 185
    //   45: aload 5
    //   47: invokespecial 188	org/spongycastle/x509/ExtCertificateEncodingException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   50: athrow
    //   51: astore 7
    //   53: new 183	org/spongycastle/x509/ExtCertificateEncodingException
    //   56: dup
    //   57: ldc 190
    //   59: aload 7
    //   61: invokespecial 188	org/spongycastle/x509/ExtCertificateEncodingException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   64: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	65	0	this	X509V3CertificateGenerator
    //   0	65	1	paramPrivateKey	PrivateKey
    //   0	65	2	paramString	String
    //   0	65	3	paramSecureRandom	SecureRandom
    //   4	22	4	localTBSCertificate	TBSCertificate
    //   37	9	5	localIOException	IOException
    //   22	6	6	arrayOfByte	byte[]
    //   51	9	7	localCertificateParsingException	CertificateParsingException
    //   32	3	8	localX509Certificate	X509Certificate
    // Exception table:
    //   from	to	target	type
    //   6	24	37	java/io/IOException
    //   24	34	51	java/security/cert/CertificateParsingException
  }
  
  /* Error */
  public X509Certificate generate(PrivateKey paramPrivateKey, SecureRandom paramSecureRandom)
    throws CertificateEncodingException, IllegalStateException, NoSuchAlgorithmException, SignatureException, InvalidKeyException
  {
    // Byte code:
    //   0: aload_0
    //   1: invokespecial 169	org/spongycastle/x509/X509V3CertificateGenerator:generateTbsCert	()Lorg/spongycastle/asn1/x509/TBSCertificate;
    //   4: astore_3
    //   5: aload_0
    //   6: getfield 171	org/spongycastle/x509/X509V3CertificateGenerator:sigOID	Lorg/spongycastle/asn1/DERObjectIdentifier;
    //   9: aload_0
    //   10: getfield 173	org/spongycastle/x509/X509V3CertificateGenerator:signatureAlgorithm	Ljava/lang/String;
    //   13: aload_1
    //   14: aload_2
    //   15: aload_3
    //   16: invokestatic 193	org/spongycastle/x509/X509Util:calculateSignature	(Lorg/spongycastle/asn1/DERObjectIdentifier;Ljava/lang/String;Ljava/security/PrivateKey;Ljava/security/SecureRandom;Lorg/spongycastle/asn1/ASN1Encodable;)[B
    //   19: astore 5
    //   21: aload_0
    //   22: aload_3
    //   23: aload 5
    //   25: invokespecial 181	org/spongycastle/x509/X509V3CertificateGenerator:generateJcaObject	(Lorg/spongycastle/asn1/x509/TBSCertificate;[B)Ljava/security/cert/X509Certificate;
    //   28: astore 7
    //   30: aload 7
    //   32: areturn
    //   33: astore 4
    //   35: new 183	org/spongycastle/x509/ExtCertificateEncodingException
    //   38: dup
    //   39: ldc 185
    //   41: aload 4
    //   43: invokespecial 188	org/spongycastle/x509/ExtCertificateEncodingException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   46: athrow
    //   47: astore 6
    //   49: new 183	org/spongycastle/x509/ExtCertificateEncodingException
    //   52: dup
    //   53: ldc 190
    //   55: aload 6
    //   57: invokespecial 188	org/spongycastle/x509/ExtCertificateEncodingException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   60: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	61	0	this	X509V3CertificateGenerator
    //   0	61	1	paramPrivateKey	PrivateKey
    //   0	61	2	paramSecureRandom	SecureRandom
    //   4	19	3	localTBSCertificate	TBSCertificate
    //   33	9	4	localIOException	IOException
    //   19	5	5	arrayOfByte	byte[]
    //   47	9	6	localCertificateParsingException	CertificateParsingException
    //   28	3	7	localX509Certificate	X509Certificate
    // Exception table:
    //   from	to	target	type
    //   5	21	33	java/io/IOException
    //   21	30	47	java/security/cert/CertificateParsingException
  }
  
  public X509Certificate generateX509Certificate(PrivateKey paramPrivateKey)
    throws SecurityException, SignatureException, InvalidKeyException
  {
    try
    {
      X509Certificate localX509Certificate = generateX509Certificate(paramPrivateKey, "SC", null);
      return localX509Certificate;
    }
    catch (NoSuchProviderException localNoSuchProviderException)
    {
      throw new SecurityException("BC provider not installed!");
    }
  }
  
  public X509Certificate generateX509Certificate(PrivateKey paramPrivateKey, String paramString)
    throws NoSuchProviderException, SecurityException, SignatureException, InvalidKeyException
  {
    return generateX509Certificate(paramPrivateKey, paramString, null);
  }
  
  public X509Certificate generateX509Certificate(PrivateKey paramPrivateKey, String paramString, SecureRandom paramSecureRandom)
    throws NoSuchProviderException, SecurityException, SignatureException, InvalidKeyException
  {
    try
    {
      X509Certificate localX509Certificate = generate(paramPrivateKey, paramString, paramSecureRandom);
      return localX509Certificate;
    }
    catch (NoSuchProviderException localNoSuchProviderException)
    {
      throw localNoSuchProviderException;
    }
    catch (SignatureException localSignatureException)
    {
      throw localSignatureException;
    }
    catch (InvalidKeyException localInvalidKeyException)
    {
      throw localInvalidKeyException;
    }
    catch (GeneralSecurityException localGeneralSecurityException)
    {
      throw new SecurityException("exception: " + localGeneralSecurityException);
    }
  }
  
  public X509Certificate generateX509Certificate(PrivateKey paramPrivateKey, SecureRandom paramSecureRandom)
    throws SecurityException, SignatureException, InvalidKeyException
  {
    try
    {
      X509Certificate localX509Certificate = generateX509Certificate(paramPrivateKey, "SC", paramSecureRandom);
      return localX509Certificate;
    }
    catch (NoSuchProviderException localNoSuchProviderException)
    {
      throw new SecurityException("BC provider not installed!");
    }
  }
  
  public Iterator getSignatureAlgNames()
  {
    return X509Util.getAlgNames();
  }
  
  public void reset()
  {
    this.tbsGen = new V3TBSCertificateGenerator();
    this.extGenerator.reset();
  }
  
  public void setIssuerDN(X500Principal paramX500Principal)
  {
    try
    {
      this.tbsGen.setIssuer(new X509Principal(paramX500Principal.getEncoded()));
      return;
    }
    catch (IOException localIOException)
    {
      throw new IllegalArgumentException("can't process principal: " + localIOException);
    }
  }
  
  public void setIssuerDN(X509Name paramX509Name)
  {
    this.tbsGen.setIssuer(paramX509Name);
  }
  
  public void setIssuerUniqueID(boolean[] paramArrayOfBoolean)
  {
    this.tbsGen.setIssuerUniqueID(booleanToBitString(paramArrayOfBoolean));
  }
  
  public void setNotAfter(Date paramDate)
  {
    this.tbsGen.setEndDate(new Time(paramDate));
  }
  
  public void setNotBefore(Date paramDate)
  {
    this.tbsGen.setStartDate(new Time(paramDate));
  }
  
  public void setPublicKey(PublicKey paramPublicKey)
    throws IllegalArgumentException
  {
    try
    {
      this.tbsGen.setSubjectPublicKeyInfo(SubjectPublicKeyInfo.getInstance(new ASN1InputStream(paramPublicKey.getEncoded()).readObject()));
      return;
    }
    catch (Exception localException)
    {
      throw new IllegalArgumentException("unable to process key - " + localException.toString());
    }
  }
  
  public void setSerialNumber(BigInteger paramBigInteger)
  {
    if (paramBigInteger.compareTo(BigInteger.ZERO) <= 0) {
      throw new IllegalArgumentException("serial number must be a positive integer");
    }
    this.tbsGen.setSerialNumber(new ASN1Integer(paramBigInteger));
  }
  
  public void setSignatureAlgorithm(String paramString)
  {
    this.signatureAlgorithm = paramString;
    try
    {
      this.sigOID = X509Util.getAlgorithmOID(paramString);
      this.sigAlgId = X509Util.getSigAlgID(this.sigOID, paramString);
      this.tbsGen.setSignature(this.sigAlgId);
      return;
    }
    catch (Exception localException)
    {
      throw new IllegalArgumentException("Unknown signature type requested: " + paramString);
    }
  }
  
  public void setSubjectDN(X500Principal paramX500Principal)
  {
    try
    {
      this.tbsGen.setSubject(new X509Principal(paramX500Principal.getEncoded()));
      return;
    }
    catch (IOException localIOException)
    {
      throw new IllegalArgumentException("can't process principal: " + localIOException);
    }
  }
  
  public void setSubjectDN(X509Name paramX509Name)
  {
    this.tbsGen.setSubject(paramX509Name);
  }
  
  public void setSubjectUniqueID(boolean[] paramArrayOfBoolean)
  {
    this.tbsGen.setSubjectUniqueID(booleanToBitString(paramArrayOfBoolean));
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.x509.X509V3CertificateGenerator
 * JD-Core Version:    0.7.0.1
 */