package org.spongycastle.cms;

import java.security.Key;
import java.security.NoSuchProviderException;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.cms.IssuerAndSerialNumber;
import org.spongycastle.asn1.cms.KeyTransRecipientInfo;
import org.spongycastle.asn1.cms.RecipientIdentifier;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;

public class KeyTransRecipientInformation
  extends RecipientInformation
{
  private KeyTransRecipientInfo info;
  
  KeyTransRecipientInformation(KeyTransRecipientInfo paramKeyTransRecipientInfo, AlgorithmIdentifier paramAlgorithmIdentifier, CMSSecureReadable paramCMSSecureReadable, AuthAttributesProvider paramAuthAttributesProvider)
  {
    super(paramKeyTransRecipientInfo.getKeyEncryptionAlgorithm(), paramAlgorithmIdentifier, paramCMSSecureReadable, paramAuthAttributesProvider);
    this.info = paramKeyTransRecipientInfo;
    RecipientIdentifier localRecipientIdentifier = paramKeyTransRecipientInfo.getRecipientIdentifier();
    if (localRecipientIdentifier.isTagged())
    {
      this.rid = new KeyTransRecipientId(ASN1OctetString.getInstance(localRecipientIdentifier.getId()).getOctets());
      return;
    }
    IssuerAndSerialNumber localIssuerAndSerialNumber = IssuerAndSerialNumber.getInstance(localRecipientIdentifier.getId());
    this.rid = new KeyTransRecipientId(localIssuerAndSerialNumber.getName(), localIssuerAndSerialNumber.getSerialNumber().getValue());
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
    //   1: getfield 95	org/spongycastle/cms/KeyTransRecipientInformation:secureReadable	Lorg/spongycastle/cms/CMSSecureReadable;
    //   4: instanceof 97
    //   7: ifeq +53 -> 60
    //   10: new 99	org/spongycastle/cms/jcajce/JceKeyTransEnvelopedRecipient
    //   13: dup
    //   14: aload_1
    //   15: checkcast 101	java/security/PrivateKey
    //   18: invokespecial 104	org/spongycastle/cms/jcajce/JceKeyTransEnvelopedRecipient:<init>	(Ljava/security/PrivateKey;)V
    //   21: astore 4
    //   23: aload_2
    //   24: ifnull +29 -> 53
    //   27: aload 4
    //   29: aload_2
    //   30: invokevirtual 110	org/spongycastle/cms/jcajce/JceKeyTransRecipient:setProvider	(Ljava/security/Provider;)Lorg/spongycastle/cms/jcajce/JceKeyTransRecipient;
    //   33: pop
    //   34: aload_2
    //   35: invokevirtual 115	java/security/Provider:getName	()Ljava/lang/String;
    //   38: ldc 117
    //   40: invokevirtual 123	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   43: ifeq +10 -> 53
    //   46: aload 4
    //   48: aconst_null
    //   49: invokevirtual 127	org/spongycastle/cms/jcajce/JceKeyTransRecipient:setContentProvider	(Ljava/lang/String;)Lorg/spongycastle/cms/jcajce/JceKeyTransRecipient;
    //   52: pop
    //   53: aload_0
    //   54: aload 4
    //   56: invokevirtual 130	org/spongycastle/cms/KeyTransRecipientInformation:getContentStream	(Lorg/spongycastle/cms/Recipient;)Lorg/spongycastle/cms/CMSTypedStream;
    //   59: areturn
    //   60: new 132	org/spongycastle/cms/jcajce/JceKeyTransAuthenticatedRecipient
    //   63: dup
    //   64: aload_1
    //   65: checkcast 101	java/security/PrivateKey
    //   68: invokespecial 133	org/spongycastle/cms/jcajce/JceKeyTransAuthenticatedRecipient:<init>	(Ljava/security/PrivateKey;)V
    //   71: astore 4
    //   73: goto -50 -> 23
    //   76: astore_3
    //   77: new 78	org/spongycastle/cms/CMSException
    //   80: dup
    //   81: new 135	java/lang/StringBuilder
    //   84: dup
    //   85: ldc 137
    //   87: invokespecial 140	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   90: aload_3
    //   91: invokevirtual 143	java/io/IOException:getMessage	()Ljava/lang/String;
    //   94: invokevirtual 147	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   97: invokevirtual 150	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   100: aload_3
    //   101: invokespecial 153	org/spongycastle/cms/CMSException:<init>	(Ljava/lang/String;Ljava/lang/Exception;)V
    //   104: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	105	0	this	KeyTransRecipientInformation
    //   0	105	1	paramKey	Key
    //   0	105	2	paramProvider	java.security.Provider
    //   76	25	3	localIOException	java.io.IOException
    //   21	51	4	localObject	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   0	23	76	java/io/IOException
    //   27	53	76	java/io/IOException
    //   53	60	76	java/io/IOException
    //   60	73	76	java/io/IOException
  }
  
  protected RecipientOperator getRecipientOperator(Recipient paramRecipient)
    throws CMSException
  {
    return ((KeyTransRecipient)paramRecipient).getRecipientOperator(this.keyEncAlg, this.messageAlgorithm, this.info.getEncryptedKey().getOctets());
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.KeyTransRecipientInformation
 * JD-Core Version:    0.7.0.1
 */