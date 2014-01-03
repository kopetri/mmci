package org.spongycastle.cms;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.crypto.KeyGenerator;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.BEROctetString;
import org.spongycastle.asn1.BERSet;
import org.spongycastle.asn1.DERSet;
import org.spongycastle.asn1.cms.AttributeTable;
import org.spongycastle.asn1.cms.CMSObjectIdentifiers;
import org.spongycastle.asn1.cms.ContentInfo;
import org.spongycastle.asn1.cms.EncryptedContentInfo;
import org.spongycastle.asn1.cms.EnvelopedData;
import org.spongycastle.asn1.cms.OriginatorInfo;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.cms.jcajce.JceCMSContentEncryptorBuilder;
import org.spongycastle.operator.GenericKey;
import org.spongycastle.operator.OutputEncryptor;

public class CMSEnvelopedDataGenerator
  extends CMSEnvelopedGenerator
{
  public CMSEnvelopedDataGenerator() {}
  
  public CMSEnvelopedDataGenerator(SecureRandom paramSecureRandom)
  {
    super(paramSecureRandom);
  }
  
  private CMSEnvelopedData doGenerate(CMSTypedData paramCMSTypedData, OutputEncryptor paramOutputEncryptor)
    throws CMSException
  {
    if (!this.oldRecipientInfoGenerators.isEmpty()) {
      throw new IllegalStateException("can only use addRecipientGenerator() with this method");
    }
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    try
    {
      OutputStream localOutputStream = paramOutputEncryptor.getOutputStream(localByteArrayOutputStream);
      paramCMSTypedData.write(localOutputStream);
      localOutputStream.close();
      byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
      AlgorithmIdentifier localAlgorithmIdentifier = paramOutputEncryptor.getAlgorithmIdentifier();
      BEROctetString localBEROctetString = new BEROctetString(arrayOfByte);
      GenericKey localGenericKey = paramOutputEncryptor.getKey();
      Iterator localIterator = this.recipientInfoGenerators.iterator();
      while (localIterator.hasNext()) {
        localASN1EncodableVector.add(((RecipientInfoGenerator)localIterator.next()).generate(localGenericKey));
      }
      localEncryptedContentInfo = new EncryptedContentInfo(paramCMSTypedData.getContentType(), localAlgorithmIdentifier, localBEROctetString);
    }
    catch (IOException localIOException)
    {
      throw new CMSException("");
    }
    EncryptedContentInfo localEncryptedContentInfo;
    CMSAttributeTableGenerator localCMSAttributeTableGenerator = this.unprotectedAttributeGenerator;
    BERSet localBERSet = null;
    if (localCMSAttributeTableGenerator != null) {
      localBERSet = new BERSet(this.unprotectedAttributeGenerator.getAttributes(new HashMap()).toASN1EncodableVector());
    }
    ASN1ObjectIdentifier localASN1ObjectIdentifier = CMSObjectIdentifiers.envelopedData;
    OriginatorInfo localOriginatorInfo = this.originatorInfo;
    DERSet localDERSet = new DERSet(localASN1EncodableVector);
    EnvelopedData localEnvelopedData = new EnvelopedData(localOriginatorInfo, localDERSet, localEncryptedContentInfo, localBERSet);
    ContentInfo localContentInfo = new ContentInfo(localASN1ObjectIdentifier, localEnvelopedData);
    CMSEnvelopedData localCMSEnvelopedData = new CMSEnvelopedData(localContentInfo);
    return localCMSEnvelopedData;
  }
  
  private CMSEnvelopedData generate(final CMSProcessable paramCMSProcessable, String paramString, int paramInt, Provider paramProvider1, Provider paramProvider2)
    throws NoSuchAlgorithmException, CMSException
  {
    convertOldRecipients(this.rand, paramProvider2);
    if (paramInt != -1) {}
    for (JceCMSContentEncryptorBuilder localJceCMSContentEncryptorBuilder = new JceCMSContentEncryptorBuilder(new ASN1ObjectIdentifier(paramString), paramInt);; localJceCMSContentEncryptorBuilder = new JceCMSContentEncryptorBuilder(new ASN1ObjectIdentifier(paramString)))
    {
      localJceCMSContentEncryptorBuilder.setProvider(paramProvider1);
      localJceCMSContentEncryptorBuilder.setSecureRandom(this.rand);
      doGenerate(new CMSTypedData()
      {
        public Object getContent()
        {
          return paramCMSProcessable;
        }
        
        public ASN1ObjectIdentifier getContentType()
        {
          return CMSObjectIdentifiers.data;
        }
        
        public void write(OutputStream paramAnonymousOutputStream)
          throws IOException, CMSException
        {
          paramCMSProcessable.write(paramAnonymousOutputStream);
        }
      }, localJceCMSContentEncryptorBuilder.build());
    }
  }
  
  public CMSEnvelopedData generate(CMSProcessable paramCMSProcessable, String paramString1, int paramInt, String paramString2)
    throws NoSuchAlgorithmException, NoSuchProviderException, CMSException
  {
    return generate(paramCMSProcessable, paramString1, paramInt, CMSUtils.getProvider(paramString2));
  }
  
  public CMSEnvelopedData generate(CMSProcessable paramCMSProcessable, String paramString, int paramInt, Provider paramProvider)
    throws NoSuchAlgorithmException, NoSuchProviderException, CMSException
  {
    return generate(paramCMSProcessable, paramString, paramInt, CMSEnvelopedHelper.INSTANCE.createSymmetricKeyGenerator(paramString, paramProvider).getProvider(), paramProvider);
  }
  
  public CMSEnvelopedData generate(CMSProcessable paramCMSProcessable, String paramString1, String paramString2)
    throws NoSuchAlgorithmException, NoSuchProviderException, CMSException
  {
    return generate(paramCMSProcessable, paramString1, CMSUtils.getProvider(paramString2));
  }
  
  public CMSEnvelopedData generate(CMSProcessable paramCMSProcessable, String paramString, Provider paramProvider)
    throws NoSuchAlgorithmException, CMSException
  {
    return generate(paramCMSProcessable, paramString, -1, CMSEnvelopedHelper.INSTANCE.createSymmetricKeyGenerator(paramString, paramProvider).getProvider(), paramProvider);
  }
  
  public CMSEnvelopedData generate(CMSTypedData paramCMSTypedData, OutputEncryptor paramOutputEncryptor)
    throws CMSException
  {
    return doGenerate(paramCMSTypedData, paramOutputEncryptor);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.CMSEnvelopedDataGenerator
 * JD-Core Version:    0.7.0.1
 */