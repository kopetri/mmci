package org.spongycastle.cms;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.AlgorithmParameters;
import java.security.Key;
import java.security.NoSuchProviderException;
import java.security.Provider;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Set;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.cms.jcajce.JceAlgorithmIdentifierConverter;
import org.spongycastle.util.io.Streams;

public abstract class RecipientInformation
{
  private AuthAttributesProvider additionalData;
  protected AlgorithmIdentifier keyEncAlg;
  protected AlgorithmIdentifier messageAlgorithm;
  private RecipientOperator operator;
  private byte[] resultMac;
  protected RecipientId rid;
  protected CMSSecureReadable secureReadable;
  
  RecipientInformation(AlgorithmIdentifier paramAlgorithmIdentifier1, AlgorithmIdentifier paramAlgorithmIdentifier2, CMSSecureReadable paramCMSSecureReadable, AuthAttributesProvider paramAuthAttributesProvider)
  {
    this.keyEncAlg = paramAlgorithmIdentifier1;
    this.messageAlgorithm = paramAlgorithmIdentifier2;
    this.secureReadable = paramCMSSecureReadable;
    this.additionalData = paramAuthAttributesProvider;
  }
  
  private byte[] encodeObj(ASN1Encodable paramASN1Encodable)
    throws IOException
  {
    if (paramASN1Encodable != null) {
      return paramASN1Encodable.toASN1Primitive().getEncoded();
    }
    return null;
  }
  
  public byte[] getContent(Key paramKey, String paramString)
    throws CMSException, NoSuchProviderException
  {
    return getContent(paramKey, CMSUtils.getProvider(paramString));
  }
  
  public byte[] getContent(Key paramKey, Provider paramProvider)
    throws CMSException
  {
    try
    {
      byte[] arrayOfByte = CMSUtils.streamToByteArray(getContentStream(paramKey, paramProvider).getContentStream());
      return arrayOfByte;
    }
    catch (IOException localIOException)
    {
      throw new RuntimeException("unable to parse internal stream: " + localIOException);
    }
  }
  
  public byte[] getContent(Recipient paramRecipient)
    throws CMSException
  {
    try
    {
      byte[] arrayOfByte = CMSUtils.streamToByteArray(getContentStream(paramRecipient).getContentStream());
      return arrayOfByte;
    }
    catch (IOException localIOException)
    {
      throw new CMSException("unable to parse internal stream: " + localIOException.getMessage(), localIOException);
    }
  }
  
  public byte[] getContentDigest()
  {
    if ((this.secureReadable instanceof CMSEnvelopedHelper.CMSDigestAuthenticatedSecureReadable)) {
      return ((CMSEnvelopedHelper.CMSDigestAuthenticatedSecureReadable)this.secureReadable).getDigest();
    }
    return null;
  }
  
  public CMSTypedStream getContentStream(Key paramKey, String paramString)
    throws CMSException, NoSuchProviderException
  {
    return getContentStream(paramKey, CMSUtils.getProvider(paramString));
  }
  
  public abstract CMSTypedStream getContentStream(Key paramKey, Provider paramProvider)
    throws CMSException;
  
  public CMSTypedStream getContentStream(Recipient paramRecipient)
    throws CMSException, IOException
  {
    this.operator = getRecipientOperator(paramRecipient);
    if (this.additionalData != null) {
      return new CMSTypedStream(this.secureReadable.getInputStream());
    }
    return new CMSTypedStream(this.operator.getInputStream(this.secureReadable.getInputStream()));
  }
  
  public String getKeyEncryptionAlgOID()
  {
    return this.keyEncAlg.getObjectId().getId();
  }
  
  public byte[] getKeyEncryptionAlgParams()
  {
    try
    {
      byte[] arrayOfByte = encodeObj(this.keyEncAlg.getParameters());
      return arrayOfByte;
    }
    catch (Exception localException)
    {
      throw new RuntimeException("exception getting encryption parameters " + localException);
    }
  }
  
  public AlgorithmIdentifier getKeyEncryptionAlgorithm()
  {
    return this.keyEncAlg;
  }
  
  public AlgorithmParameters getKeyEncryptionAlgorithmParameters(String paramString)
    throws CMSException, NoSuchProviderException
  {
    return new JceAlgorithmIdentifierConverter().setProvider(paramString).getAlgorithmParameters(this.keyEncAlg);
  }
  
  public AlgorithmParameters getKeyEncryptionAlgorithmParameters(Provider paramProvider)
    throws CMSException
  {
    return new JceAlgorithmIdentifierConverter().setProvider(paramProvider).getAlgorithmParameters(this.keyEncAlg);
  }
  
  public byte[] getMac()
  {
    if ((this.resultMac != null) || (!this.operator.isMacBased()) || (this.additionalData != null)) {}
    try
    {
      Streams.drain(this.operator.getInputStream(new ByteArrayInputStream(this.additionalData.getAuthAttributes().getEncoded("DER"))));
      this.resultMac = this.operator.getMac();
      return this.resultMac;
    }
    catch (IOException localIOException)
    {
      for (;;)
      {
        localIOException.printStackTrace();
      }
    }
  }
  
  public RecipientId getRID()
  {
    return this.rid;
  }
  
  protected abstract RecipientOperator getRecipientOperator(Recipient paramRecipient)
    throws CMSException, IOException;
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.RecipientInformation
 * JD-Core Version:    0.7.0.1
 */