package org.spongycastle.cert.selector.jcajce;

import java.security.cert.X509CertSelector;
import org.spongycastle.cert.selector.X509CertificateHolderSelector;

public class JcaX509CertSelectorConverter
{
  /* Error */
  protected X509CertSelector doConversion(org.spongycastle.asn1.x500.X500Name paramX500Name, java.math.BigInteger paramBigInteger, byte[] paramArrayOfByte)
  {
    // Byte code:
    //   0: new 14	java/security/cert/X509CertSelector
    //   3: dup
    //   4: invokespecial 15	java/security/cert/X509CertSelector:<init>	()V
    //   7: astore 4
    //   9: aload_1
    //   10: ifnull +12 -> 22
    //   13: aload 4
    //   15: aload_1
    //   16: invokevirtual 21	org/spongycastle/asn1/x500/X500Name:getEncoded	()[B
    //   19: invokevirtual 25	java/security/cert/X509CertSelector:setIssuer	([B)V
    //   22: aload_2
    //   23: ifnull +9 -> 32
    //   26: aload 4
    //   28: aload_2
    //   29: invokevirtual 29	java/security/cert/X509CertSelector:setSerialNumber	(Ljava/math/BigInteger;)V
    //   32: aload_3
    //   33: ifnull +19 -> 52
    //   36: aload 4
    //   38: new 31	org/spongycastle/asn1/DEROctetString
    //   41: dup
    //   42: aload_3
    //   43: invokespecial 33	org/spongycastle/asn1/DEROctetString:<init>	([B)V
    //   46: invokevirtual 34	org/spongycastle/asn1/DEROctetString:getEncoded	()[B
    //   49: invokevirtual 37	java/security/cert/X509CertSelector:setSubjectKeyIdentifier	([B)V
    //   52: aload 4
    //   54: areturn
    //   55: astore 6
    //   57: new 39	java/lang/IllegalArgumentException
    //   60: dup
    //   61: new 41	java/lang/StringBuilder
    //   64: dup
    //   65: ldc 43
    //   67: invokespecial 46	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   70: aload 6
    //   72: invokevirtual 50	java/io/IOException:getMessage	()Ljava/lang/String;
    //   75: invokevirtual 54	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   78: invokevirtual 57	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   81: invokespecial 58	java/lang/IllegalArgumentException:<init>	(Ljava/lang/String;)V
    //   84: athrow
    //   85: astore 5
    //   87: new 39	java/lang/IllegalArgumentException
    //   90: dup
    //   91: new 41	java/lang/StringBuilder
    //   94: dup
    //   95: ldc 43
    //   97: invokespecial 46	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   100: aload 5
    //   102: invokevirtual 50	java/io/IOException:getMessage	()Ljava/lang/String;
    //   105: invokevirtual 54	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   108: invokevirtual 57	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   111: invokespecial 58	java/lang/IllegalArgumentException:<init>	(Ljava/lang/String;)V
    //   114: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	115	0	this	JcaX509CertSelectorConverter
    //   0	115	1	paramX500Name	org.spongycastle.asn1.x500.X500Name
    //   0	115	2	paramBigInteger	java.math.BigInteger
    //   0	115	3	paramArrayOfByte	byte[]
    //   7	46	4	localX509CertSelector	X509CertSelector
    //   85	16	5	localIOException1	java.io.IOException
    //   55	16	6	localIOException2	java.io.IOException
    // Exception table:
    //   from	to	target	type
    //   13	22	55	java/io/IOException
    //   36	52	85	java/io/IOException
  }
  
  public X509CertSelector getCertSelector(X509CertificateHolderSelector paramX509CertificateHolderSelector)
  {
    return doConversion(paramX509CertificateHolderSelector.getIssuer(), paramX509CertificateHolderSelector.getSerialNumber(), paramX509CertificateHolderSelector.getSubjectKeyIdentifier());
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cert.selector.jcajce.JcaX509CertSelectorConverter
 * JD-Core Version:    0.7.0.1
 */