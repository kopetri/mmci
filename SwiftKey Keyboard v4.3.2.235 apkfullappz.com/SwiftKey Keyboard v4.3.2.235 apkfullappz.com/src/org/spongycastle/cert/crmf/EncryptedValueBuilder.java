package org.spongycastle.cert.crmf;

import java.io.IOException;
import org.spongycastle.asn1.crmf.EncryptedValue;
import org.spongycastle.cert.X509CertificateHolder;
import org.spongycastle.operator.KeyWrapper;
import org.spongycastle.operator.OutputEncryptor;
import org.spongycastle.util.Strings;

public class EncryptedValueBuilder
{
  private OutputEncryptor encryptor;
  private EncryptedValuePadder padder;
  private KeyWrapper wrapper;
  
  public EncryptedValueBuilder(KeyWrapper paramKeyWrapper, OutputEncryptor paramOutputEncryptor)
  {
    this(paramKeyWrapper, paramOutputEncryptor, null);
  }
  
  public EncryptedValueBuilder(KeyWrapper paramKeyWrapper, OutputEncryptor paramOutputEncryptor, EncryptedValuePadder paramEncryptedValuePadder)
  {
    this.wrapper = paramKeyWrapper;
    this.encryptor = paramOutputEncryptor;
    this.padder = paramEncryptedValuePadder;
  }
  
  /* Error */
  private EncryptedValue encryptData(byte[] paramArrayOfByte)
    throws CRMFException
  {
    // Byte code:
    //   0: new 34	java/io/ByteArrayOutputStream
    //   3: dup
    //   4: invokespecial 35	java/io/ByteArrayOutputStream:<init>	()V
    //   7: astore_2
    //   8: aload_0
    //   9: getfield 22	org/spongycastle/cert/crmf/EncryptedValueBuilder:encryptor	Lorg/spongycastle/operator/OutputEncryptor;
    //   12: aload_2
    //   13: invokeinterface 41 2 0
    //   18: astore_3
    //   19: aload_3
    //   20: aload_1
    //   21: invokevirtual 47	java/io/OutputStream:write	([B)V
    //   24: aload_3
    //   25: invokevirtual 50	java/io/OutputStream:close	()V
    //   28: aload_0
    //   29: getfield 22	org/spongycastle/cert/crmf/EncryptedValueBuilder:encryptor	Lorg/spongycastle/operator/OutputEncryptor;
    //   32: invokeinterface 54 1 0
    //   37: astore 5
    //   39: aload_0
    //   40: getfield 20	org/spongycastle/cert/crmf/EncryptedValueBuilder:wrapper	Lorg/spongycastle/operator/KeyWrapper;
    //   43: aload_0
    //   44: getfield 22	org/spongycastle/cert/crmf/EncryptedValueBuilder:encryptor	Lorg/spongycastle/operator/OutputEncryptor;
    //   47: invokeinterface 58 1 0
    //   52: invokeinterface 64 2 0
    //   57: pop
    //   58: new 66	org/spongycastle/asn1/DERBitString
    //   61: dup
    //   62: aload_0
    //   63: getfield 20	org/spongycastle/cert/crmf/EncryptedValueBuilder:wrapper	Lorg/spongycastle/operator/KeyWrapper;
    //   66: aload_0
    //   67: getfield 22	org/spongycastle/cert/crmf/EncryptedValueBuilder:encryptor	Lorg/spongycastle/operator/OutputEncryptor;
    //   70: invokeinterface 58 1 0
    //   75: invokeinterface 64 2 0
    //   80: invokespecial 68	org/spongycastle/asn1/DERBitString:<init>	([B)V
    //   83: astore 8
    //   85: new 70	org/spongycastle/asn1/crmf/EncryptedValue
    //   88: dup
    //   89: aconst_null
    //   90: aload 5
    //   92: aload 8
    //   94: aload_0
    //   95: getfield 20	org/spongycastle/cert/crmf/EncryptedValueBuilder:wrapper	Lorg/spongycastle/operator/KeyWrapper;
    //   98: invokeinterface 71 1 0
    //   103: aconst_null
    //   104: new 66	org/spongycastle/asn1/DERBitString
    //   107: dup
    //   108: aload_2
    //   109: invokevirtual 75	java/io/ByteArrayOutputStream:toByteArray	()[B
    //   112: invokespecial 68	org/spongycastle/asn1/DERBitString:<init>	([B)V
    //   115: invokespecial 78	org/spongycastle/asn1/crmf/EncryptedValue:<init>	(Lorg/spongycastle/asn1/x509/AlgorithmIdentifier;Lorg/spongycastle/asn1/x509/AlgorithmIdentifier;Lorg/spongycastle/asn1/DERBitString;Lorg/spongycastle/asn1/x509/AlgorithmIdentifier;Lorg/spongycastle/asn1/ASN1OctetString;Lorg/spongycastle/asn1/DERBitString;)V
    //   118: areturn
    //   119: astore 4
    //   121: new 28	org/spongycastle/cert/crmf/CRMFException
    //   124: dup
    //   125: new 80	java/lang/StringBuilder
    //   128: dup
    //   129: ldc 82
    //   131: invokespecial 85	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   134: aload 4
    //   136: invokevirtual 89	java/io/IOException:getMessage	()Ljava/lang/String;
    //   139: invokevirtual 93	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   142: invokevirtual 96	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   145: aload 4
    //   147: invokespecial 99	org/spongycastle/cert/crmf/CRMFException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   150: athrow
    //   151: astore 6
    //   153: new 28	org/spongycastle/cert/crmf/CRMFException
    //   156: dup
    //   157: new 80	java/lang/StringBuilder
    //   160: dup
    //   161: ldc 101
    //   163: invokespecial 85	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   166: aload 6
    //   168: invokevirtual 102	org/spongycastle/operator/OperatorException:getMessage	()Ljava/lang/String;
    //   171: invokevirtual 93	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   174: invokevirtual 96	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   177: aload 6
    //   179: invokespecial 99	org/spongycastle/cert/crmf/CRMFException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   182: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	183	0	this	EncryptedValueBuilder
    //   0	183	1	paramArrayOfByte	byte[]
    //   7	102	2	localByteArrayOutputStream	java.io.ByteArrayOutputStream
    //   18	7	3	localOutputStream	java.io.OutputStream
    //   119	27	4	localIOException	IOException
    //   37	54	5	localAlgorithmIdentifier	org.spongycastle.asn1.x509.AlgorithmIdentifier
    //   151	27	6	localOperatorException	org.spongycastle.operator.OperatorException
    //   83	10	8	localDERBitString	org.spongycastle.asn1.DERBitString
    // Exception table:
    //   from	to	target	type
    //   19	28	119	java/io/IOException
    //   39	85	151	org/spongycastle/operator/OperatorException
  }
  
  private byte[] padData(byte[] paramArrayOfByte)
  {
    if (this.padder != null) {
      paramArrayOfByte = this.padder.getPaddedData(paramArrayOfByte);
    }
    return paramArrayOfByte;
  }
  
  public EncryptedValue build(X509CertificateHolder paramX509CertificateHolder)
    throws CRMFException
  {
    try
    {
      EncryptedValue localEncryptedValue = encryptData(padData(paramX509CertificateHolder.getEncoded()));
      return localEncryptedValue;
    }
    catch (IOException localIOException)
    {
      throw new CRMFException("cannot encode certificate: " + localIOException.getMessage(), localIOException);
    }
  }
  
  public EncryptedValue build(char[] paramArrayOfChar)
    throws CRMFException
  {
    return encryptData(padData(Strings.toUTF8ByteArray(paramArrayOfChar)));
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cert.crmf.EncryptedValueBuilder
 * JD-Core Version:    0.7.0.1
 */