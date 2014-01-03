package org.spongycastle.cms;

import java.io.IOException;
import java.security.Key;
import java.security.NoSuchProviderException;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.cms.KEKIdentifier;
import org.spongycastle.asn1.cms.KEKRecipientInfo;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;

public class KEKRecipientInformation
  extends RecipientInformation
{
  private KEKRecipientInfo info;
  
  KEKRecipientInformation(KEKRecipientInfo paramKEKRecipientInfo, AlgorithmIdentifier paramAlgorithmIdentifier, CMSSecureReadable paramCMSSecureReadable, AuthAttributesProvider paramAuthAttributesProvider)
  {
    super(paramKEKRecipientInfo.getKeyEncryptionAlgorithm(), paramAlgorithmIdentifier, paramCMSSecureReadable, paramAuthAttributesProvider);
    this.info = paramKEKRecipientInfo;
    this.rid = new KEKRecipientId(paramKEKRecipientInfo.getKekid().getKeyIdentifier().getOctets());
  }
  
  public CMSTypedStream getContentStream(Key paramKey, String paramString)
    throws CMSException, NoSuchProviderException
  {
    return getContentStream(paramKey, CMSUtils.getProvider(paramString));
  }
  
  /* Error */
  public CMSTypedStream getContentStream(Key paramKey, java.security.Provider paramProvider)
    throws CMSException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 65	org/spongycastle/cms/KEKRecipientInformation:secureReadable	Lorg/spongycastle/cms/CMSSecureReadable;
    //   4: instanceof 67
    //   7: ifeq +34 -> 41
    //   10: new 69	org/spongycastle/cms/jcajce/JceKEKEnvelopedRecipient
    //   13: dup
    //   14: aload_1
    //   15: checkcast 71	javax/crypto/SecretKey
    //   18: invokespecial 74	org/spongycastle/cms/jcajce/JceKEKEnvelopedRecipient:<init>	(Ljavax/crypto/SecretKey;)V
    //   21: astore 4
    //   23: aload_2
    //   24: ifnull +10 -> 34
    //   27: aload 4
    //   29: aload_2
    //   30: invokevirtual 80	org/spongycastle/cms/jcajce/JceKEKRecipient:setProvider	(Ljava/security/Provider;)Lorg/spongycastle/cms/jcajce/JceKEKRecipient;
    //   33: pop
    //   34: aload_0
    //   35: aload 4
    //   37: invokevirtual 83	org/spongycastle/cms/KEKRecipientInformation:getContentStream	(Lorg/spongycastle/cms/Recipient;)Lorg/spongycastle/cms/CMSTypedStream;
    //   40: areturn
    //   41: new 85	org/spongycastle/cms/jcajce/JceKEKAuthenticatedRecipient
    //   44: dup
    //   45: aload_1
    //   46: checkcast 71	javax/crypto/SecretKey
    //   49: invokespecial 86	org/spongycastle/cms/jcajce/JceKEKAuthenticatedRecipient:<init>	(Ljavax/crypto/SecretKey;)V
    //   52: astore 4
    //   54: goto -31 -> 23
    //   57: astore_3
    //   58: new 48	org/spongycastle/cms/CMSException
    //   61: dup
    //   62: new 88	java/lang/StringBuilder
    //   65: dup
    //   66: ldc 90
    //   68: invokespecial 93	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   71: aload_3
    //   72: invokevirtual 97	java/io/IOException:getMessage	()Ljava/lang/String;
    //   75: invokevirtual 101	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   78: invokevirtual 104	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   81: aload_3
    //   82: invokespecial 107	org/spongycastle/cms/CMSException:<init>	(Ljava/lang/String;Ljava/lang/Exception;)V
    //   85: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	86	0	this	KEKRecipientInformation
    //   0	86	1	paramKey	Key
    //   0	86	2	paramProvider	java.security.Provider
    //   57	25	3	localIOException	IOException
    //   21	32	4	localObject	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   0	23	57	java/io/IOException
    //   27	34	57	java/io/IOException
    //   34	41	57	java/io/IOException
    //   41	54	57	java/io/IOException
  }
  
  protected RecipientOperator getRecipientOperator(Recipient paramRecipient)
    throws CMSException, IOException
  {
    return ((KEKRecipient)paramRecipient).getRecipientOperator(this.keyEncAlg, this.messageAlgorithm, this.info.getEncryptedKey().getOctets());
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.KEKRecipientInformation
 * JD-Core Version:    0.7.0.1
 */