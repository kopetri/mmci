package org.spongycastle.cms;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Set;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.BERSequenceGenerator;
import org.spongycastle.asn1.BERTaggedObject;
import org.spongycastle.asn1.DERInteger;
import org.spongycastle.asn1.DERSet;
import org.spongycastle.asn1.cms.AttributeTable;
import org.spongycastle.asn1.cms.CMSObjectIdentifiers;
import org.spongycastle.asn1.cms.SignerInfo;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;

public class CMSSignedDataStreamGenerator
  extends CMSSignedGenerator
{
  private int _bufferSize;
  
  public CMSSignedDataStreamGenerator() {}
  
  public CMSSignedDataStreamGenerator(SecureRandom paramSecureRandom)
  {
    super(paramSecureRandom);
  }
  
  private DERInteger calculateVersion(ASN1ObjectIdentifier paramASN1ObjectIdentifier)
  {
    List localList1 = this.certs;
    int i = 0;
    int j = 0;
    int k = 0;
    if (localList1 != null)
    {
      Iterator localIterator2 = this.certs.iterator();
      while (localIterator2.hasNext())
      {
        Object localObject = localIterator2.next();
        if ((localObject instanceof ASN1TaggedObject))
        {
          ASN1TaggedObject localASN1TaggedObject = (ASN1TaggedObject)localObject;
          if (localASN1TaggedObject.getTagNo() == 1) {
            i = 1;
          } else if (localASN1TaggedObject.getTagNo() == 2) {
            j = 1;
          } else if (localASN1TaggedObject.getTagNo() == 3) {
            k = 1;
          }
        }
      }
    }
    if (k != 0) {
      return new DERInteger(5);
    }
    List localList2 = this.crls;
    int m = 0;
    if (localList2 != null)
    {
      Iterator localIterator1 = this.crls.iterator();
      while (localIterator1.hasNext()) {
        if ((localIterator1.next() instanceof ASN1TaggedObject)) {
          m = 1;
        }
      }
    }
    if (m != 0) {
      return new DERInteger(5);
    }
    if (j != 0) {
      return new DERInteger(4);
    }
    if (i != 0) {
      return new DERInteger(3);
    }
    if (checkForVersion3(this._signers)) {
      return new DERInteger(3);
    }
    if (!CMSObjectIdentifiers.data.equals(paramASN1ObjectIdentifier)) {
      return new DERInteger(3);
    }
    return new DERInteger(1);
  }
  
  private boolean checkForVersion3(List paramList)
  {
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext()) {
      if (SignerInfo.getInstance(((SignerInformation)localIterator.next()).toASN1Structure()).getVersion().getValue().intValue() == 3) {
        return true;
      }
    }
    return false;
  }
  
  /* Error */
  private void doAddSigner(PrivateKey paramPrivateKey, Object paramObject, String paramString1, String paramString2, CMSAttributeTableGenerator paramCMSAttributeTableGenerator1, CMSAttributeTableGenerator paramCMSAttributeTableGenerator2, Provider paramProvider1, Provider paramProvider2)
    throws NoSuchAlgorithmException, InvalidKeyException
  {
    // Byte code:
    //   0: getstatic 113	org/spongycastle/cms/CMSSignedHelper:INSTANCE	Lorg/spongycastle/cms/CMSSignedHelper;
    //   3: aload 4
    //   5: invokevirtual 117	org/spongycastle/cms/CMSSignedHelper:getDigestAlgName	(Ljava/lang/String;)Ljava/lang/String;
    //   8: astore 9
    //   10: new 119	java/lang/StringBuilder
    //   13: dup
    //   14: invokespecial 120	java/lang/StringBuilder:<init>	()V
    //   17: aload 9
    //   19: invokevirtual 124	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   22: ldc 126
    //   24: invokevirtual 124	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   27: getstatic 113	org/spongycastle/cms/CMSSignedHelper:INSTANCE	Lorg/spongycastle/cms/CMSSignedHelper;
    //   30: aload_3
    //   31: invokevirtual 129	org/spongycastle/cms/CMSSignedHelper:getEncryptionAlgName	(Ljava/lang/String;)Ljava/lang/String;
    //   34: invokevirtual 124	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   37: invokevirtual 133	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   40: astore 10
    //   42: new 135	org/spongycastle/operator/jcajce/JcaContentSignerBuilder
    //   45: dup
    //   46: aload 10
    //   48: invokespecial 138	org/spongycastle/operator/jcajce/JcaContentSignerBuilder:<init>	(Ljava/lang/String;)V
    //   51: aload_0
    //   52: getfield 142	org/spongycastle/cms/CMSSignedDataStreamGenerator:rand	Ljava/security/SecureRandom;
    //   55: invokevirtual 146	org/spongycastle/operator/jcajce/JcaContentSignerBuilder:setSecureRandom	(Ljava/security/SecureRandom;)Lorg/spongycastle/operator/jcajce/JcaContentSignerBuilder;
    //   58: astore 12
    //   60: aload 7
    //   62: ifnull +11 -> 73
    //   65: aload 12
    //   67: aload 7
    //   69: invokevirtual 150	org/spongycastle/operator/jcajce/JcaContentSignerBuilder:setProvider	(Ljava/security/Provider;)Lorg/spongycastle/operator/jcajce/JcaContentSignerBuilder;
    //   72: pop
    //   73: new 152	org/spongycastle/operator/jcajce/JcaDigestCalculatorProviderBuilder
    //   76: dup
    //   77: invokespecial 153	org/spongycastle/operator/jcajce/JcaDigestCalculatorProviderBuilder:<init>	()V
    //   80: astore 13
    //   82: aload 8
    //   84: ifnull +24 -> 108
    //   87: aload 8
    //   89: invokevirtual 158	java/security/Provider:getName	()Ljava/lang/String;
    //   92: ldc 160
    //   94: invokevirtual 166	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   97: ifne +11 -> 108
    //   100: aload 13
    //   102: aload 8
    //   104: invokevirtual 169	org/spongycastle/operator/jcajce/JcaDigestCalculatorProviderBuilder:setProvider	(Ljava/security/Provider;)Lorg/spongycastle/operator/jcajce/JcaDigestCalculatorProviderBuilder;
    //   107: pop
    //   108: new 171	org/spongycastle/cms/jcajce/JcaSignerInfoGeneratorBuilder
    //   111: dup
    //   112: aload 13
    //   114: invokevirtual 175	org/spongycastle/operator/jcajce/JcaDigestCalculatorProviderBuilder:build	()Lorg/spongycastle/operator/DigestCalculatorProvider;
    //   117: invokespecial 178	org/spongycastle/cms/jcajce/JcaSignerInfoGeneratorBuilder:<init>	(Lorg/spongycastle/operator/DigestCalculatorProvider;)V
    //   120: astore 16
    //   122: aload 16
    //   124: aload 5
    //   126: invokevirtual 182	org/spongycastle/cms/jcajce/JcaSignerInfoGeneratorBuilder:setSignedAttributeGenerator	(Lorg/spongycastle/cms/CMSAttributeTableGenerator;)Lorg/spongycastle/cms/jcajce/JcaSignerInfoGeneratorBuilder;
    //   129: pop
    //   130: aload 16
    //   132: aload 6
    //   134: invokevirtual 185	org/spongycastle/cms/jcajce/JcaSignerInfoGeneratorBuilder:setUnsignedAttributeGenerator	(Lorg/spongycastle/cms/CMSAttributeTableGenerator;)Lorg/spongycastle/cms/jcajce/JcaSignerInfoGeneratorBuilder;
    //   137: pop
    //   138: aload 12
    //   140: aload_1
    //   141: invokevirtual 188	org/spongycastle/operator/jcajce/JcaContentSignerBuilder:build	(Ljava/security/PrivateKey;)Lorg/spongycastle/operator/ContentSigner;
    //   144: astore 20
    //   146: aload_2
    //   147: instanceof 190
    //   150: ifeq +34 -> 184
    //   153: aload_0
    //   154: aload 16
    //   156: aload 20
    //   158: aload_2
    //   159: checkcast 190	java/security/cert/X509Certificate
    //   162: invokevirtual 193	org/spongycastle/cms/jcajce/JcaSignerInfoGeneratorBuilder:build	(Lorg/spongycastle/operator/ContentSigner;Ljava/security/cert/X509Certificate;)Lorg/spongycastle/cms/SignerInfoGenerator;
    //   165: invokevirtual 197	org/spongycastle/cms/CMSSignedDataStreamGenerator:addSignerInfoGenerator	(Lorg/spongycastle/cms/SignerInfoGenerator;)V
    //   168: return
    //   169: astore 11
    //   171: new 99	java/security/NoSuchAlgorithmException
    //   174: dup
    //   175: aload 11
    //   177: invokevirtual 200	java/lang/IllegalArgumentException:getMessage	()Ljava/lang/String;
    //   180: invokespecial 201	java/security/NoSuchAlgorithmException:<init>	(Ljava/lang/String;)V
    //   183: athrow
    //   184: aload_0
    //   185: aload 16
    //   187: aload 20
    //   189: aload_2
    //   190: checkcast 203	[B
    //   193: checkcast 203	[B
    //   196: invokevirtual 206	org/spongycastle/cms/jcajce/JcaSignerInfoGeneratorBuilder:build	(Lorg/spongycastle/operator/ContentSigner;[B)Lorg/spongycastle/cms/SignerInfoGenerator;
    //   199: invokevirtual 197	org/spongycastle/cms/CMSSignedDataStreamGenerator:addSignerInfoGenerator	(Lorg/spongycastle/cms/SignerInfoGenerator;)V
    //   202: return
    //   203: astore 19
    //   205: aload 19
    //   207: invokevirtual 210	org/spongycastle/operator/OperatorCreationException:getCause	()Ljava/lang/Throwable;
    //   210: instanceof 99
    //   213: ifeq +42 -> 255
    //   216: aload 19
    //   218: invokevirtual 210	org/spongycastle/operator/OperatorCreationException:getCause	()Ljava/lang/Throwable;
    //   221: checkcast 99	java/security/NoSuchAlgorithmException
    //   224: athrow
    //   225: astore 15
    //   227: new 99	java/security/NoSuchAlgorithmException
    //   230: dup
    //   231: new 119	java/lang/StringBuilder
    //   234: dup
    //   235: ldc 212
    //   237: invokespecial 213	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   240: aload 15
    //   242: invokevirtual 214	org/spongycastle/operator/OperatorCreationException:getMessage	()Ljava/lang/String;
    //   245: invokevirtual 124	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   248: invokevirtual 133	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   251: invokespecial 201	java/security/NoSuchAlgorithmException:<init>	(Ljava/lang/String;)V
    //   254: athrow
    //   255: aload 19
    //   257: invokevirtual 210	org/spongycastle/operator/OperatorCreationException:getCause	()Ljava/lang/Throwable;
    //   260: instanceof 101
    //   263: ifeq -95 -> 168
    //   266: aload 19
    //   268: invokevirtual 210	org/spongycastle/operator/OperatorCreationException:getCause	()Ljava/lang/Throwable;
    //   271: checkcast 101	java/security/InvalidKeyException
    //   274: athrow
    //   275: astore 14
    //   277: new 216	java/lang/IllegalStateException
    //   280: dup
    //   281: ldc 218
    //   283: invokespecial 219	java/lang/IllegalStateException:<init>	(Ljava/lang/String;)V
    //   286: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	287	0	this	CMSSignedDataStreamGenerator
    //   0	287	1	paramPrivateKey	PrivateKey
    //   0	287	2	paramObject	Object
    //   0	287	3	paramString1	String
    //   0	287	4	paramString2	String
    //   0	287	5	paramCMSAttributeTableGenerator1	CMSAttributeTableGenerator
    //   0	287	6	paramCMSAttributeTableGenerator2	CMSAttributeTableGenerator
    //   0	287	7	paramProvider1	Provider
    //   0	287	8	paramProvider2	Provider
    //   8	10	9	str1	String
    //   40	7	10	str2	String
    //   169	7	11	localIllegalArgumentException	java.lang.IllegalArgumentException
    //   58	81	12	localJcaContentSignerBuilder	org.spongycastle.operator.jcajce.JcaContentSignerBuilder
    //   80	33	13	localJcaDigestCalculatorProviderBuilder	org.spongycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder
    //   275	1	14	localCertificateEncodingException	java.security.cert.CertificateEncodingException
    //   225	16	15	localOperatorCreationException1	org.spongycastle.operator.OperatorCreationException
    //   120	66	16	localJcaSignerInfoGeneratorBuilder	org.spongycastle.cms.jcajce.JcaSignerInfoGeneratorBuilder
    //   203	64	19	localOperatorCreationException2	org.spongycastle.operator.OperatorCreationException
    //   144	44	20	localContentSigner	org.spongycastle.operator.ContentSigner
    // Exception table:
    //   from	to	target	type
    //   42	60	169	java/lang/IllegalArgumentException
    //   138	168	203	org/spongycastle/operator/OperatorCreationException
    //   184	202	203	org/spongycastle/operator/OperatorCreationException
    //   73	82	225	org/spongycastle/operator/OperatorCreationException
    //   87	108	225	org/spongycastle/operator/OperatorCreationException
    //   108	138	225	org/spongycastle/operator/OperatorCreationException
    //   205	225	225	org/spongycastle/operator/OperatorCreationException
    //   255	275	225	org/spongycastle/operator/OperatorCreationException
    //   73	82	275	java/security/cert/CertificateEncodingException
    //   87	108	275	java/security/cert/CertificateEncodingException
    //   108	138	275	java/security/cert/CertificateEncodingException
    //   138	168	275	java/security/cert/CertificateEncodingException
    //   184	202	275	java/security/cert/CertificateEncodingException
    //   205	225	275	java/security/cert/CertificateEncodingException
    //   255	275	275	java/security/cert/CertificateEncodingException
  }
  
  public void addSigner(PrivateKey paramPrivateKey, X509Certificate paramX509Certificate, String paramString1, String paramString2)
    throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException
  {
    addSigner(paramPrivateKey, paramX509Certificate, paramString1, CMSUtils.getProvider(paramString2));
  }
  
  public void addSigner(PrivateKey paramPrivateKey, X509Certificate paramX509Certificate, String paramString1, String paramString2, String paramString3)
    throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException
  {
    addSigner(paramPrivateKey, paramX509Certificate, paramString1, paramString2, CMSUtils.getProvider(paramString3));
  }
  
  public void addSigner(PrivateKey paramPrivateKey, X509Certificate paramX509Certificate, String paramString1, String paramString2, Provider paramProvider)
    throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException
  {
    addSigner(paramPrivateKey, paramX509Certificate, paramString1, paramString2, new DefaultSignedAttributeTableGenerator(), null, paramProvider);
  }
  
  public void addSigner(PrivateKey paramPrivateKey, X509Certificate paramX509Certificate, String paramString1, String paramString2, AttributeTable paramAttributeTable1, AttributeTable paramAttributeTable2, String paramString3)
    throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException
  {
    addSigner(paramPrivateKey, paramX509Certificate, paramString1, paramString2, paramAttributeTable1, paramAttributeTable2, CMSUtils.getProvider(paramString3));
  }
  
  public void addSigner(PrivateKey paramPrivateKey, X509Certificate paramX509Certificate, String paramString1, String paramString2, AttributeTable paramAttributeTable1, AttributeTable paramAttributeTable2, Provider paramProvider)
    throws NoSuchAlgorithmException, InvalidKeyException
  {
    addSigner(paramPrivateKey, paramX509Certificate, paramString1, paramString2, new DefaultSignedAttributeTableGenerator(paramAttributeTable1), new SimpleAttributeTableGenerator(paramAttributeTable2), paramProvider);
  }
  
  public void addSigner(PrivateKey paramPrivateKey, X509Certificate paramX509Certificate, String paramString1, String paramString2, CMSAttributeTableGenerator paramCMSAttributeTableGenerator1, CMSAttributeTableGenerator paramCMSAttributeTableGenerator2, String paramString3)
    throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException
  {
    addSigner(paramPrivateKey, paramX509Certificate, paramString1, paramString2, paramCMSAttributeTableGenerator1, paramCMSAttributeTableGenerator2, CMSUtils.getProvider(paramString3));
  }
  
  public void addSigner(PrivateKey paramPrivateKey, X509Certificate paramX509Certificate, String paramString1, String paramString2, CMSAttributeTableGenerator paramCMSAttributeTableGenerator1, CMSAttributeTableGenerator paramCMSAttributeTableGenerator2, Provider paramProvider)
    throws NoSuchAlgorithmException, InvalidKeyException
  {
    addSigner(paramPrivateKey, paramX509Certificate, paramString1, paramString2, paramCMSAttributeTableGenerator1, paramCMSAttributeTableGenerator2, paramProvider, paramProvider);
  }
  
  public void addSigner(PrivateKey paramPrivateKey, X509Certificate paramX509Certificate, String paramString1, String paramString2, CMSAttributeTableGenerator paramCMSAttributeTableGenerator1, CMSAttributeTableGenerator paramCMSAttributeTableGenerator2, Provider paramProvider1, Provider paramProvider2)
    throws NoSuchAlgorithmException, InvalidKeyException
  {
    doAddSigner(paramPrivateKey, paramX509Certificate, paramString1, paramString2, paramCMSAttributeTableGenerator1, paramCMSAttributeTableGenerator2, paramProvider1, paramProvider2);
  }
  
  public void addSigner(PrivateKey paramPrivateKey, X509Certificate paramX509Certificate, String paramString, Provider paramProvider)
    throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException
  {
    addSigner(paramPrivateKey, paramX509Certificate, paramString, new DefaultSignedAttributeTableGenerator(), null, paramProvider);
  }
  
  public void addSigner(PrivateKey paramPrivateKey, X509Certificate paramX509Certificate, String paramString1, AttributeTable paramAttributeTable1, AttributeTable paramAttributeTable2, String paramString2)
    throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException
  {
    addSigner(paramPrivateKey, paramX509Certificate, paramString1, paramAttributeTable1, paramAttributeTable2, CMSUtils.getProvider(paramString2));
  }
  
  public void addSigner(PrivateKey paramPrivateKey, X509Certificate paramX509Certificate, String paramString, AttributeTable paramAttributeTable1, AttributeTable paramAttributeTable2, Provider paramProvider)
    throws NoSuchAlgorithmException, InvalidKeyException
  {
    addSigner(paramPrivateKey, paramX509Certificate, paramString, new DefaultSignedAttributeTableGenerator(paramAttributeTable1), new SimpleAttributeTableGenerator(paramAttributeTable2), paramProvider);
  }
  
  public void addSigner(PrivateKey paramPrivateKey, X509Certificate paramX509Certificate, String paramString1, CMSAttributeTableGenerator paramCMSAttributeTableGenerator1, CMSAttributeTableGenerator paramCMSAttributeTableGenerator2, String paramString2)
    throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException
  {
    addSigner(paramPrivateKey, paramX509Certificate, paramString1, paramCMSAttributeTableGenerator1, paramCMSAttributeTableGenerator2, CMSUtils.getProvider(paramString2));
  }
  
  public void addSigner(PrivateKey paramPrivateKey, X509Certificate paramX509Certificate, String paramString, CMSAttributeTableGenerator paramCMSAttributeTableGenerator1, CMSAttributeTableGenerator paramCMSAttributeTableGenerator2, Provider paramProvider)
    throws NoSuchAlgorithmException, InvalidKeyException
  {
    addSigner(paramPrivateKey, paramX509Certificate, getEncOID(paramPrivateKey, paramString), paramString, paramCMSAttributeTableGenerator1, paramCMSAttributeTableGenerator2, paramProvider);
  }
  
  public void addSigner(PrivateKey paramPrivateKey, byte[] paramArrayOfByte, String paramString1, String paramString2)
    throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException
  {
    addSigner(paramPrivateKey, paramArrayOfByte, paramString1, CMSUtils.getProvider(paramString2));
  }
  
  public void addSigner(PrivateKey paramPrivateKey, byte[] paramArrayOfByte, String paramString1, String paramString2, String paramString3)
    throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException
  {
    addSigner(paramPrivateKey, paramArrayOfByte, paramString1, paramString2, CMSUtils.getProvider(paramString3));
  }
  
  public void addSigner(PrivateKey paramPrivateKey, byte[] paramArrayOfByte, String paramString1, String paramString2, Provider paramProvider)
    throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException
  {
    addSigner(paramPrivateKey, paramArrayOfByte, paramString1, paramString2, new DefaultSignedAttributeTableGenerator(), null, paramProvider);
  }
  
  public void addSigner(PrivateKey paramPrivateKey, byte[] paramArrayOfByte, String paramString1, String paramString2, CMSAttributeTableGenerator paramCMSAttributeTableGenerator1, CMSAttributeTableGenerator paramCMSAttributeTableGenerator2, String paramString3)
    throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException
  {
    addSigner(paramPrivateKey, paramArrayOfByte, paramString1, paramString2, paramCMSAttributeTableGenerator1, paramCMSAttributeTableGenerator2, CMSUtils.getProvider(paramString3));
  }
  
  public void addSigner(PrivateKey paramPrivateKey, byte[] paramArrayOfByte, String paramString1, String paramString2, CMSAttributeTableGenerator paramCMSAttributeTableGenerator1, CMSAttributeTableGenerator paramCMSAttributeTableGenerator2, Provider paramProvider)
    throws NoSuchAlgorithmException, InvalidKeyException
  {
    addSigner(paramPrivateKey, paramArrayOfByte, paramString1, paramString2, paramCMSAttributeTableGenerator1, paramCMSAttributeTableGenerator2, paramProvider, paramProvider);
  }
  
  public void addSigner(PrivateKey paramPrivateKey, byte[] paramArrayOfByte, String paramString1, String paramString2, CMSAttributeTableGenerator paramCMSAttributeTableGenerator1, CMSAttributeTableGenerator paramCMSAttributeTableGenerator2, Provider paramProvider1, Provider paramProvider2)
    throws NoSuchAlgorithmException, InvalidKeyException
  {
    doAddSigner(paramPrivateKey, paramArrayOfByte, paramString1, paramString2, paramCMSAttributeTableGenerator1, paramCMSAttributeTableGenerator2, paramProvider1, paramProvider2);
  }
  
  public void addSigner(PrivateKey paramPrivateKey, byte[] paramArrayOfByte, String paramString, Provider paramProvider)
    throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException
  {
    addSigner(paramPrivateKey, paramArrayOfByte, paramString, new DefaultSignedAttributeTableGenerator(), null, paramProvider);
  }
  
  public void addSigner(PrivateKey paramPrivateKey, byte[] paramArrayOfByte, String paramString1, AttributeTable paramAttributeTable1, AttributeTable paramAttributeTable2, String paramString2)
    throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException
  {
    addSigner(paramPrivateKey, paramArrayOfByte, paramString1, paramAttributeTable1, paramAttributeTable2, CMSUtils.getProvider(paramString2));
  }
  
  public void addSigner(PrivateKey paramPrivateKey, byte[] paramArrayOfByte, String paramString, AttributeTable paramAttributeTable1, AttributeTable paramAttributeTable2, Provider paramProvider)
    throws NoSuchAlgorithmException, InvalidKeyException
  {
    addSigner(paramPrivateKey, paramArrayOfByte, paramString, new DefaultSignedAttributeTableGenerator(paramAttributeTable1), new SimpleAttributeTableGenerator(paramAttributeTable2), paramProvider);
  }
  
  public void addSigner(PrivateKey paramPrivateKey, byte[] paramArrayOfByte, String paramString1, CMSAttributeTableGenerator paramCMSAttributeTableGenerator1, CMSAttributeTableGenerator paramCMSAttributeTableGenerator2, String paramString2)
    throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException
  {
    addSigner(paramPrivateKey, paramArrayOfByte, paramString1, paramCMSAttributeTableGenerator1, paramCMSAttributeTableGenerator2, CMSUtils.getProvider(paramString2));
  }
  
  public void addSigner(PrivateKey paramPrivateKey, byte[] paramArrayOfByte, String paramString, CMSAttributeTableGenerator paramCMSAttributeTableGenerator1, CMSAttributeTableGenerator paramCMSAttributeTableGenerator2, Provider paramProvider)
    throws NoSuchAlgorithmException, InvalidKeyException
  {
    addSigner(paramPrivateKey, paramArrayOfByte, getEncOID(paramPrivateKey, paramString), paramString, paramCMSAttributeTableGenerator1, paramCMSAttributeTableGenerator2, paramProvider);
  }
  
  void generate(OutputStream paramOutputStream1, String paramString, boolean paramBoolean, OutputStream paramOutputStream2, CMSProcessable paramCMSProcessable)
    throws CMSException, IOException
  {
    OutputStream localOutputStream = open(paramOutputStream1, paramString, paramBoolean, paramOutputStream2);
    if (paramCMSProcessable != null) {
      paramCMSProcessable.write(localOutputStream);
    }
    localOutputStream.close();
  }
  
  public OutputStream open(OutputStream paramOutputStream)
    throws IOException
  {
    return open(paramOutputStream, false);
  }
  
  public OutputStream open(OutputStream paramOutputStream, String paramString, boolean paramBoolean)
    throws IOException
  {
    return open(paramOutputStream, paramString, paramBoolean, null);
  }
  
  public OutputStream open(OutputStream paramOutputStream1, String paramString, boolean paramBoolean, OutputStream paramOutputStream2)
    throws IOException
  {
    return open(new ASN1ObjectIdentifier(paramString), paramOutputStream1, paramBoolean, paramOutputStream2);
  }
  
  public OutputStream open(OutputStream paramOutputStream, boolean paramBoolean)
    throws IOException
  {
    return open(CMSObjectIdentifiers.data, paramOutputStream, paramBoolean);
  }
  
  public OutputStream open(OutputStream paramOutputStream1, boolean paramBoolean, OutputStream paramOutputStream2)
    throws IOException
  {
    return open(CMSObjectIdentifiers.data, paramOutputStream1, paramBoolean, paramOutputStream2);
  }
  
  public OutputStream open(ASN1ObjectIdentifier paramASN1ObjectIdentifier, OutputStream paramOutputStream, boolean paramBoolean)
    throws IOException
  {
    return open(paramASN1ObjectIdentifier, paramOutputStream, paramBoolean, null);
  }
  
  public OutputStream open(ASN1ObjectIdentifier paramASN1ObjectIdentifier, OutputStream paramOutputStream1, boolean paramBoolean, OutputStream paramOutputStream2)
    throws IOException
  {
    BERSequenceGenerator localBERSequenceGenerator1 = new BERSequenceGenerator(paramOutputStream1);
    localBERSequenceGenerator1.addObject(CMSObjectIdentifiers.signedData);
    BERSequenceGenerator localBERSequenceGenerator2 = new BERSequenceGenerator(localBERSequenceGenerator1.getRawOutputStream(), 0, true);
    localBERSequenceGenerator2.addObject(calculateVersion(paramASN1ObjectIdentifier));
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    Iterator localIterator1 = this._signers.iterator();
    while (localIterator1.hasNext())
    {
      SignerInformation localSignerInformation = (SignerInformation)localIterator1.next();
      localASN1EncodableVector.add(CMSSignedHelper.INSTANCE.fixAlgID(localSignerInformation.getDigestAlgorithmID()));
    }
    Iterator localIterator2 = this.signerGens.iterator();
    while (localIterator2.hasNext()) {
      localASN1EncodableVector.add(((SignerInfoGenerator)localIterator2.next()).getDigestAlgorithm());
    }
    localBERSequenceGenerator2.getRawOutputStream().write(new DERSet(localASN1EncodableVector).getEncoded());
    BERSequenceGenerator localBERSequenceGenerator3 = new BERSequenceGenerator(localBERSequenceGenerator2.getRawOutputStream());
    localBERSequenceGenerator3.addObject(paramASN1ObjectIdentifier);
    if (paramBoolean) {}
    for (OutputStream localOutputStream1 = CMSUtils.createBEROctetOutputStream(localBERSequenceGenerator3.getRawOutputStream(), 0, true, this._bufferSize);; localOutputStream1 = null)
    {
      OutputStream localOutputStream2 = CMSUtils.getSafeTeeOutputStream(paramOutputStream2, localOutputStream1);
      return new CmsSignedDataOutputStream(CMSUtils.attachSignersToOutputStream(this.signerGens, localOutputStream2), paramASN1ObjectIdentifier, localBERSequenceGenerator1, localBERSequenceGenerator2, localBERSequenceGenerator3);
    }
  }
  
  public void setBufferSize(int paramInt)
  {
    this._bufferSize = paramInt;
  }
  
  private class CmsSignedDataOutputStream
    extends OutputStream
  {
    private ASN1ObjectIdentifier _contentOID;
    private BERSequenceGenerator _eiGen;
    private OutputStream _out;
    private BERSequenceGenerator _sGen;
    private BERSequenceGenerator _sigGen;
    
    public CmsSignedDataOutputStream(OutputStream paramOutputStream, ASN1ObjectIdentifier paramASN1ObjectIdentifier, BERSequenceGenerator paramBERSequenceGenerator1, BERSequenceGenerator paramBERSequenceGenerator2, BERSequenceGenerator paramBERSequenceGenerator3)
    {
      this._out = paramOutputStream;
      this._contentOID = paramASN1ObjectIdentifier;
      this._sGen = paramBERSequenceGenerator1;
      this._sigGen = paramBERSequenceGenerator2;
      this._eiGen = paramBERSequenceGenerator3;
    }
    
    public void close()
      throws IOException
    {
      this._out.close();
      this._eiGen.close();
      CMSSignedDataStreamGenerator.this.digests.clear();
      if (CMSSignedDataStreamGenerator.this.certs.size() != 0)
      {
        ASN1Set localASN1Set2 = CMSUtils.createBerSetFromList(CMSSignedDataStreamGenerator.this.certs);
        this._sigGen.getRawOutputStream().write(new BERTaggedObject(false, 0, localASN1Set2).getEncoded());
      }
      if (CMSSignedDataStreamGenerator.this.crls.size() != 0)
      {
        ASN1Set localASN1Set1 = CMSUtils.createBerSetFromList(CMSSignedDataStreamGenerator.this.crls);
        this._sigGen.getRawOutputStream().write(new BERTaggedObject(false, 1, localASN1Set1).getEncoded());
      }
      ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
      Iterator localIterator1 = CMSSignedDataStreamGenerator.this.signerGens.iterator();
      while (localIterator1.hasNext())
      {
        SignerInfoGenerator localSignerInfoGenerator = (SignerInfoGenerator)localIterator1.next();
        try
        {
          localASN1EncodableVector.add(localSignerInfoGenerator.generate(this._contentOID));
          byte[] arrayOfByte = localSignerInfoGenerator.getCalculatedDigest();
          CMSSignedDataStreamGenerator.this.digests.put(localSignerInfoGenerator.getDigestAlgorithm().getAlgorithm().getId(), arrayOfByte);
        }
        catch (CMSException localCMSException)
        {
          throw new CMSStreamException("exception generating signers: " + localCMSException.getMessage(), localCMSException);
        }
      }
      Iterator localIterator2 = CMSSignedDataStreamGenerator.this._signers.iterator();
      while (localIterator2.hasNext()) {
        localASN1EncodableVector.add(((SignerInformation)localIterator2.next()).toASN1Structure());
      }
      this._sigGen.getRawOutputStream().write(new DERSet(localASN1EncodableVector).getEncoded());
      this._sigGen.close();
      this._sGen.close();
    }
    
    public void write(int paramInt)
      throws IOException
    {
      this._out.write(paramInt);
    }
    
    public void write(byte[] paramArrayOfByte)
      throws IOException
    {
      this._out.write(paramArrayOfByte);
    }
    
    public void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
      throws IOException
    {
      this._out.write(paramArrayOfByte, paramInt1, paramInt2);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.CMSSignedDataStreamGenerator
 * JD-Core Version:    0.7.0.1
 */