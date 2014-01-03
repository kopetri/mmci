package org.spongycastle.cms;

import java.io.IOException;
import java.security.Key;
import java.security.NoSuchProviderException;
import java.util.List;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.cms.IssuerAndSerialNumber;
import org.spongycastle.asn1.cms.KeyAgreeRecipientIdentifier;
import org.spongycastle.asn1.cms.KeyAgreeRecipientInfo;
import org.spongycastle.asn1.cms.OriginatorIdentifierOrKey;
import org.spongycastle.asn1.cms.OriginatorPublicKey;
import org.spongycastle.asn1.cms.RecipientEncryptedKey;
import org.spongycastle.asn1.cms.RecipientKeyIdentifier;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.SubjectKeyIdentifier;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;

public class KeyAgreeRecipientInformation
  extends RecipientInformation
{
  private ASN1OctetString encryptedKey;
  private KeyAgreeRecipientInfo info;
  
  KeyAgreeRecipientInformation(KeyAgreeRecipientInfo paramKeyAgreeRecipientInfo, RecipientId paramRecipientId, ASN1OctetString paramASN1OctetString, AlgorithmIdentifier paramAlgorithmIdentifier, CMSSecureReadable paramCMSSecureReadable, AuthAttributesProvider paramAuthAttributesProvider)
  {
    super(paramKeyAgreeRecipientInfo.getKeyEncryptionAlgorithm(), paramAlgorithmIdentifier, paramCMSSecureReadable, paramAuthAttributesProvider);
    this.info = paramKeyAgreeRecipientInfo;
    this.rid = paramRecipientId;
    this.encryptedKey = paramASN1OctetString;
  }
  
  private SubjectPublicKeyInfo getPublicKeyInfoFromOriginatorId(OriginatorId paramOriginatorId)
    throws CMSException
  {
    throw new CMSException("No support for 'originator' as IssuerAndSerialNumber or SubjectKeyIdentifier");
  }
  
  private SubjectPublicKeyInfo getPublicKeyInfoFromOriginatorPublicKey(AlgorithmIdentifier paramAlgorithmIdentifier, OriginatorPublicKey paramOriginatorPublicKey)
  {
    return new SubjectPublicKeyInfo(paramAlgorithmIdentifier, paramOriginatorPublicKey.getPublicKey().getBytes());
  }
  
  private SubjectPublicKeyInfo getSenderPublicKeyInfo(AlgorithmIdentifier paramAlgorithmIdentifier, OriginatorIdentifierOrKey paramOriginatorIdentifierOrKey)
    throws CMSException, IOException
  {
    OriginatorPublicKey localOriginatorPublicKey = paramOriginatorIdentifierOrKey.getOriginatorKey();
    if (localOriginatorPublicKey != null) {
      return getPublicKeyInfoFromOriginatorPublicKey(paramAlgorithmIdentifier, localOriginatorPublicKey);
    }
    IssuerAndSerialNumber localIssuerAndSerialNumber = paramOriginatorIdentifierOrKey.getIssuerAndSerialNumber();
    if (localIssuerAndSerialNumber != null) {}
    for (OriginatorId localOriginatorId = new OriginatorId(localIssuerAndSerialNumber.getName(), localIssuerAndSerialNumber.getSerialNumber().getValue());; localOriginatorId = new OriginatorId(paramOriginatorIdentifierOrKey.getSubjectKeyIdentifier().getKeyIdentifier())) {
      return getPublicKeyInfoFromOriginatorId(localOriginatorId);
    }
  }
  
  static void readRecipientInfo(List paramList, KeyAgreeRecipientInfo paramKeyAgreeRecipientInfo, AlgorithmIdentifier paramAlgorithmIdentifier, CMSSecureReadable paramCMSSecureReadable, AuthAttributesProvider paramAuthAttributesProvider)
  {
    ASN1Sequence localASN1Sequence = paramKeyAgreeRecipientInfo.getRecipientEncryptedKeys();
    int i = 0;
    if (i < localASN1Sequence.size())
    {
      RecipientEncryptedKey localRecipientEncryptedKey = RecipientEncryptedKey.getInstance(localASN1Sequence.getObjectAt(i));
      KeyAgreeRecipientIdentifier localKeyAgreeRecipientIdentifier = localRecipientEncryptedKey.getIdentifier();
      IssuerAndSerialNumber localIssuerAndSerialNumber = localKeyAgreeRecipientIdentifier.getIssuerAndSerialNumber();
      if (localIssuerAndSerialNumber != null) {}
      for (KeyAgreeRecipientId localKeyAgreeRecipientId = new KeyAgreeRecipientId(localIssuerAndSerialNumber.getName(), localIssuerAndSerialNumber.getSerialNumber().getValue());; localKeyAgreeRecipientId = new KeyAgreeRecipientId(localKeyAgreeRecipientIdentifier.getRKeyID().getSubjectKeyIdentifier().getOctets()))
      {
        paramList.add(new KeyAgreeRecipientInformation(paramKeyAgreeRecipientInfo, localKeyAgreeRecipientId, localRecipientEncryptedKey.getEncryptedKey(), paramAlgorithmIdentifier, paramCMSSecureReadable, paramAuthAttributesProvider));
        i++;
        break;
      }
    }
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
    //   1: getfield 181	org/spongycastle/cms/KeyAgreeRecipientInformation:secureReadable	Lorg/spongycastle/cms/CMSSecureReadable;
    //   4: instanceof 183
    //   7: ifeq +53 -> 60
    //   10: new 185	org/spongycastle/cms/jcajce/JceKeyAgreeEnvelopedRecipient
    //   13: dup
    //   14: aload_1
    //   15: checkcast 187	java/security/PrivateKey
    //   18: invokespecial 190	org/spongycastle/cms/jcajce/JceKeyAgreeEnvelopedRecipient:<init>	(Ljava/security/PrivateKey;)V
    //   21: astore 4
    //   23: aload_2
    //   24: ifnull +29 -> 53
    //   27: aload 4
    //   29: aload_2
    //   30: invokevirtual 196	org/spongycastle/cms/jcajce/JceKeyAgreeRecipient:setProvider	(Ljava/security/Provider;)Lorg/spongycastle/cms/jcajce/JceKeyAgreeRecipient;
    //   33: pop
    //   34: aload_2
    //   35: invokevirtual 201	java/security/Provider:getName	()Ljava/lang/String;
    //   38: ldc 203
    //   40: invokevirtual 209	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   43: ifeq +10 -> 53
    //   46: aload 4
    //   48: aconst_null
    //   49: invokevirtual 213	org/spongycastle/cms/jcajce/JceKeyAgreeRecipient:setContentProvider	(Ljava/lang/String;)Lorg/spongycastle/cms/jcajce/JceKeyAgreeRecipient;
    //   52: pop
    //   53: aload_0
    //   54: aload 4
    //   56: invokevirtual 216	org/spongycastle/cms/KeyAgreeRecipientInformation:getContentStream	(Lorg/spongycastle/cms/Recipient;)Lorg/spongycastle/cms/CMSTypedStream;
    //   59: areturn
    //   60: new 218	org/spongycastle/cms/jcajce/JceKeyAgreeAuthenticatedRecipient
    //   63: dup
    //   64: aload_1
    //   65: checkcast 187	java/security/PrivateKey
    //   68: invokespecial 219	org/spongycastle/cms/jcajce/JceKeyAgreeAuthenticatedRecipient:<init>	(Ljava/security/PrivateKey;)V
    //   71: astore 4
    //   73: goto -50 -> 23
    //   76: astore_3
    //   77: new 31	org/spongycastle/cms/CMSException
    //   80: dup
    //   81: new 221	java/lang/StringBuilder
    //   84: dup
    //   85: ldc 223
    //   87: invokespecial 224	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   90: aload_3
    //   91: invokevirtual 227	java/io/IOException:getMessage	()Ljava/lang/String;
    //   94: invokevirtual 231	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   97: invokevirtual 234	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   100: aload_3
    //   101: invokespecial 237	org/spongycastle/cms/CMSException:<init>	(Ljava/lang/String;Ljava/lang/Exception;)V
    //   104: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	105	0	this	KeyAgreeRecipientInformation
    //   0	105	1	paramKey	Key
    //   0	105	2	paramProvider	java.security.Provider
    //   76	25	3	localIOException	IOException
    //   21	51	4	localObject	java.lang.Object
    // Exception table:
    //   from	to	target	type
    //   0	23	76	java/io/IOException
    //   27	53	76	java/io/IOException
    //   53	60	76	java/io/IOException
    //   60	73	76	java/io/IOException
  }
  
  protected RecipientOperator getRecipientOperator(Recipient paramRecipient)
    throws CMSException, IOException
  {
    AlgorithmIdentifier localAlgorithmIdentifier = ((KeyAgreeRecipient)paramRecipient).getPrivateKeyAlgorithmIdentifier();
    return ((KeyAgreeRecipient)paramRecipient).getRecipientOperator(this.keyEncAlg, this.messageAlgorithm, getSenderPublicKeyInfo(localAlgorithmIdentifier, this.info.getOriginator()), this.info.getUserKeyingMaterial(), this.encryptedKey.getOctets());
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.KeyAgreeRecipientInformation
 * JD-Core Version:    0.7.0.1
 */