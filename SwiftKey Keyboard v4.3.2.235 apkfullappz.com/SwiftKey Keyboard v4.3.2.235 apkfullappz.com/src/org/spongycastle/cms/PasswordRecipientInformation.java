package org.spongycastle.cms;

import java.io.IOException;
import java.math.BigInteger;
import java.security.AlgorithmParameters;
import java.security.Key;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.util.HashMap;
import java.util.Map;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.cms.PasswordRecipientInfo;
import org.spongycastle.asn1.pkcs.PBKDF2Params;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.cms.jcajce.JceAlgorithmIdentifierConverter;
import org.spongycastle.crypto.PBEParametersGenerator;
import org.spongycastle.crypto.generators.PKCS5S2ParametersGenerator;
import org.spongycastle.crypto.params.KeyParameter;

public class PasswordRecipientInformation
  extends RecipientInformation
{
  static Map BLOCKSIZES;
  static Map KEYSIZES = new HashMap();
  private PasswordRecipientInfo info;
  
  static
  {
    HashMap localHashMap = new HashMap();
    BLOCKSIZES = localHashMap;
    localHashMap.put(CMSAlgorithm.DES_EDE3_CBC, new Integer(8));
    BLOCKSIZES.put(CMSAlgorithm.AES128_CBC, new Integer(16));
    BLOCKSIZES.put(CMSAlgorithm.AES192_CBC, new Integer(16));
    BLOCKSIZES.put(CMSAlgorithm.AES256_CBC, new Integer(16));
    KEYSIZES.put(CMSAlgorithm.DES_EDE3_CBC, new Integer(192));
    KEYSIZES.put(CMSAlgorithm.AES128_CBC, new Integer(128));
    KEYSIZES.put(CMSAlgorithm.AES192_CBC, new Integer(192));
    KEYSIZES.put(CMSAlgorithm.AES256_CBC, new Integer(256));
  }
  
  PasswordRecipientInformation(PasswordRecipientInfo paramPasswordRecipientInfo, AlgorithmIdentifier paramAlgorithmIdentifier, CMSSecureReadable paramCMSSecureReadable, AuthAttributesProvider paramAuthAttributesProvider)
  {
    super(paramPasswordRecipientInfo.getKeyEncryptionAlgorithm(), paramAlgorithmIdentifier, paramCMSSecureReadable, paramAuthAttributesProvider);
    this.info = paramPasswordRecipientInfo;
    this.rid = new PasswordRecipientId();
  }
  
  public CMSTypedStream getContentStream(Key paramKey, String paramString)
    throws CMSException, NoSuchProviderException
  {
    return getContentStream(paramKey, CMSUtils.getProvider(paramString));
  }
  
  /* Error */
  public CMSTypedStream getContentStream(Key paramKey, Provider paramProvider)
    throws CMSException
  {
    // Byte code:
    //   0: aload_1
    //   1: checkcast 84	org/spongycastle/cms/CMSPBEKey
    //   4: astore 4
    //   6: aload_0
    //   7: getfield 88	org/spongycastle/cms/PasswordRecipientInformation:secureReadable	Lorg/spongycastle/cms/CMSSecureReadable;
    //   10: instanceof 90
    //   13: ifeq +54 -> 67
    //   16: new 92	org/spongycastle/cms/jcajce/JcePasswordEnvelopedRecipient
    //   19: dup
    //   20: aload 4
    //   22: invokevirtual 96	org/spongycastle/cms/CMSPBEKey:getPassword	()[C
    //   25: invokespecial 99	org/spongycastle/cms/jcajce/JcePasswordEnvelopedRecipient:<init>	([C)V
    //   28: astore 5
    //   30: aload 4
    //   32: instanceof 101
    //   35: ifeq +49 -> 84
    //   38: iconst_1
    //   39: istore 6
    //   41: aload 5
    //   43: iload 6
    //   45: invokevirtual 107	org/spongycastle/cms/jcajce/JcePasswordRecipient:setPasswordConversionScheme	(I)Lorg/spongycastle/cms/jcajce/JcePasswordRecipient;
    //   48: pop
    //   49: aload_2
    //   50: ifnull +10 -> 60
    //   53: aload 5
    //   55: aload_2
    //   56: invokevirtual 111	org/spongycastle/cms/jcajce/JcePasswordRecipient:setProvider	(Ljava/security/Provider;)Lorg/spongycastle/cms/jcajce/JcePasswordRecipient;
    //   59: pop
    //   60: aload_0
    //   61: aload 5
    //   63: invokevirtual 114	org/spongycastle/cms/PasswordRecipientInformation:getContentStream	(Lorg/spongycastle/cms/Recipient;)Lorg/spongycastle/cms/CMSTypedStream;
    //   66: areturn
    //   67: new 116	org/spongycastle/cms/jcajce/JcePasswordAuthenticatedRecipient
    //   70: dup
    //   71: aload 4
    //   73: invokevirtual 96	org/spongycastle/cms/CMSPBEKey:getPassword	()[C
    //   76: invokespecial 117	org/spongycastle/cms/jcajce/JcePasswordAuthenticatedRecipient:<init>	([C)V
    //   79: astore 5
    //   81: goto -51 -> 30
    //   84: iconst_0
    //   85: istore 6
    //   87: goto -46 -> 41
    //   90: astore_3
    //   91: new 69	org/spongycastle/cms/CMSException
    //   94: dup
    //   95: new 119	java/lang/StringBuilder
    //   98: dup
    //   99: ldc 121
    //   101: invokespecial 124	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   104: aload_3
    //   105: invokevirtual 128	java/io/IOException:getMessage	()Ljava/lang/String;
    //   108: invokevirtual 132	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   111: invokevirtual 135	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   114: aload_3
    //   115: invokespecial 138	org/spongycastle/cms/CMSException:<init>	(Ljava/lang/String;Ljava/lang/Exception;)V
    //   118: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	119	0	this	PasswordRecipientInformation
    //   0	119	1	paramKey	Key
    //   0	119	2	paramProvider	Provider
    //   90	25	3	localIOException	IOException
    //   4	68	4	localCMSPBEKey	CMSPBEKey
    //   28	52	5	localObject	java.lang.Object
    //   39	47	6	i	int
    // Exception table:
    //   from	to	target	type
    //   0	30	90	java/io/IOException
    //   30	38	90	java/io/IOException
    //   41	49	90	java/io/IOException
    //   53	60	90	java/io/IOException
    //   60	67	90	java/io/IOException
    //   67	81	90	java/io/IOException
  }
  
  public String getKeyDerivationAlgOID()
  {
    if (this.info.getKeyDerivationAlgorithm() != null) {
      return this.info.getKeyDerivationAlgorithm().getAlgorithm().getId();
    }
    return null;
  }
  
  public AlgorithmParameters getKeyDerivationAlgParameters(String paramString)
    throws NoSuchProviderException
  {
    return getKeyDerivationAlgParameters(CMSUtils.getProvider(paramString));
  }
  
  public AlgorithmParameters getKeyDerivationAlgParameters(Provider paramProvider)
  {
    try
    {
      AlgorithmParameters localAlgorithmParameters = new JceAlgorithmIdentifierConverter().setProvider(paramProvider).getAlgorithmParameters(this.info.getKeyDerivationAlgorithm());
      return localAlgorithmParameters;
    }
    catch (Exception localException)
    {
      throw new RuntimeException("exception getting encryption parameters " + localException);
    }
  }
  
  public byte[] getKeyDerivationAlgParams()
  {
    try
    {
      if (this.info.getKeyDerivationAlgorithm() != null)
      {
        ASN1Encodable localASN1Encodable = this.info.getKeyDerivationAlgorithm().getParameters();
        if (localASN1Encodable != null)
        {
          byte[] arrayOfByte = localASN1Encodable.toASN1Primitive().getEncoded();
          return arrayOfByte;
        }
      }
      return null;
    }
    catch (Exception localException)
    {
      throw new RuntimeException("exception getting encryption parameters " + localException);
    }
  }
  
  public AlgorithmIdentifier getKeyDerivationAlgorithm()
  {
    return this.info.getKeyDerivationAlgorithm();
  }
  
  protected byte[] getPasswordBytes(int paramInt, char[] paramArrayOfChar)
  {
    if (paramInt == 0) {
      return PBEParametersGenerator.PKCS5PasswordToBytes(paramArrayOfChar);
    }
    return PBEParametersGenerator.PKCS5PasswordToUTF8Bytes(paramArrayOfChar);
  }
  
  protected RecipientOperator getRecipientOperator(Recipient paramRecipient)
    throws CMSException, IOException
  {
    PasswordRecipient localPasswordRecipient = (PasswordRecipient)paramRecipient;
    AlgorithmIdentifier localAlgorithmIdentifier = AlgorithmIdentifier.getInstance(AlgorithmIdentifier.getInstance(this.info.getKeyEncryptionAlgorithm()).getParameters());
    byte[] arrayOfByte1 = getPasswordBytes(localPasswordRecipient.getPasswordConversionScheme(), localPasswordRecipient.getPassword());
    PBKDF2Params localPBKDF2Params = PBKDF2Params.getInstance(this.info.getKeyDerivationAlgorithm().getParameters());
    PKCS5S2ParametersGenerator localPKCS5S2ParametersGenerator = new PKCS5S2ParametersGenerator();
    localPKCS5S2ParametersGenerator.init(arrayOfByte1, localPBKDF2Params.getSalt(), localPBKDF2Params.getIterationCount().intValue());
    byte[] arrayOfByte2 = ((KeyParameter)localPKCS5S2ParametersGenerator.generateDerivedParameters(((Integer)KEYSIZES.get(localAlgorithmIdentifier.getAlgorithm())).intValue())).getKey();
    return localPasswordRecipient.getRecipientOperator(localAlgorithmIdentifier, this.messageAlgorithm, arrayOfByte2, this.info.getEncryptedKey().getOctets());
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.PasswordRecipientInformation
 * JD-Core Version:    0.7.0.1
 */