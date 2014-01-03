package org.spongycastle.cert.cmp;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERGeneralizedTime;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.cmp.CMPCertificate;
import org.spongycastle.asn1.cmp.InfoTypeAndValue;
import org.spongycastle.asn1.cmp.PKIBody;
import org.spongycastle.asn1.cmp.PKIFreeText;
import org.spongycastle.asn1.cmp.PKIHeader;
import org.spongycastle.asn1.cmp.PKIHeaderBuilder;
import org.spongycastle.asn1.cmp.PKIMessage;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.GeneralName;
import org.spongycastle.cert.X509CertificateHolder;
import org.spongycastle.operator.ContentSigner;
import org.spongycastle.operator.MacCalculator;

public class ProtectedPKIMessageBuilder
{
  private PKIBody body;
  private List extraCerts = new ArrayList();
  private List generalInfos = new ArrayList();
  private PKIHeaderBuilder hdrBuilder;
  
  public ProtectedPKIMessageBuilder(int paramInt, GeneralName paramGeneralName1, GeneralName paramGeneralName2)
  {
    this.hdrBuilder = new PKIHeaderBuilder(paramInt, paramGeneralName1, paramGeneralName2);
  }
  
  public ProtectedPKIMessageBuilder(GeneralName paramGeneralName1, GeneralName paramGeneralName2)
  {
    this(2, paramGeneralName1, paramGeneralName2);
  }
  
  private byte[] calculateMac(MacCalculator paramMacCalculator, PKIHeader paramPKIHeader, PKIBody paramPKIBody)
    throws IOException
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(paramPKIHeader);
    localASN1EncodableVector.add(paramPKIBody);
    OutputStream localOutputStream = paramMacCalculator.getOutputStream();
    localOutputStream.write(new DERSequence(localASN1EncodableVector).getEncoded("DER"));
    localOutputStream.close();
    return paramMacCalculator.getMac();
  }
  
  private byte[] calculateSignature(ContentSigner paramContentSigner, PKIHeader paramPKIHeader, PKIBody paramPKIBody)
    throws IOException
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(paramPKIHeader);
    localASN1EncodableVector.add(paramPKIBody);
    OutputStream localOutputStream = paramContentSigner.getOutputStream();
    localOutputStream.write(new DERSequence(localASN1EncodableVector).getEncoded("DER"));
    localOutputStream.close();
    return paramContentSigner.getSignature();
  }
  
  private void finaliseHeader(AlgorithmIdentifier paramAlgorithmIdentifier)
  {
    this.hdrBuilder.setProtectionAlg(paramAlgorithmIdentifier);
    if (!this.generalInfos.isEmpty())
    {
      InfoTypeAndValue[] arrayOfInfoTypeAndValue = new InfoTypeAndValue[this.generalInfos.size()];
      this.hdrBuilder.setGeneralInfo((InfoTypeAndValue[])this.generalInfos.toArray(arrayOfInfoTypeAndValue));
    }
  }
  
  private ProtectedPKIMessage finaliseMessage(PKIHeader paramPKIHeader, DERBitString paramDERBitString)
  {
    if (!this.extraCerts.isEmpty())
    {
      CMPCertificate[] arrayOfCMPCertificate = new CMPCertificate[this.extraCerts.size()];
      for (int i = 0; i != arrayOfCMPCertificate.length; i++) {
        arrayOfCMPCertificate[i] = new CMPCertificate(((X509CertificateHolder)this.extraCerts.get(i)).toASN1Structure());
      }
      return new ProtectedPKIMessage(new PKIMessage(paramPKIHeader, this.body, paramDERBitString, arrayOfCMPCertificate));
    }
    return new ProtectedPKIMessage(new PKIMessage(paramPKIHeader, this.body, paramDERBitString));
  }
  
  public ProtectedPKIMessageBuilder addCMPCertificate(X509CertificateHolder paramX509CertificateHolder)
  {
    this.extraCerts.add(paramX509CertificateHolder);
    return this;
  }
  
  public ProtectedPKIMessageBuilder addGeneralInfo(InfoTypeAndValue paramInfoTypeAndValue)
  {
    this.generalInfos.add(paramInfoTypeAndValue);
    return this;
  }
  
  public ProtectedPKIMessage build(ContentSigner paramContentSigner)
    throws CMPException
  {
    finaliseHeader(paramContentSigner.getAlgorithmIdentifier());
    PKIHeader localPKIHeader = this.hdrBuilder.build();
    try
    {
      ProtectedPKIMessage localProtectedPKIMessage = finaliseMessage(localPKIHeader, new DERBitString(calculateSignature(paramContentSigner, localPKIHeader, this.body)));
      return localProtectedPKIMessage;
    }
    catch (IOException localIOException)
    {
      throw new CMPException("unable to encode signature input: " + localIOException.getMessage(), localIOException);
    }
  }
  
  public ProtectedPKIMessage build(MacCalculator paramMacCalculator)
    throws CMPException
  {
    finaliseHeader(paramMacCalculator.getAlgorithmIdentifier());
    PKIHeader localPKIHeader = this.hdrBuilder.build();
    try
    {
      ProtectedPKIMessage localProtectedPKIMessage = finaliseMessage(localPKIHeader, new DERBitString(calculateMac(paramMacCalculator, localPKIHeader, this.body)));
      return localProtectedPKIMessage;
    }
    catch (IOException localIOException)
    {
      throw new CMPException("unable to encode MAC input: " + localIOException.getMessage(), localIOException);
    }
  }
  
  public ProtectedPKIMessageBuilder setBody(PKIBody paramPKIBody)
  {
    this.body = paramPKIBody;
    return this;
  }
  
  public ProtectedPKIMessageBuilder setFreeText(PKIFreeText paramPKIFreeText)
  {
    this.hdrBuilder.setFreeText(paramPKIFreeText);
    return this;
  }
  
  public ProtectedPKIMessageBuilder setMessageTime(Date paramDate)
  {
    this.hdrBuilder.setMessageTime(new DERGeneralizedTime(paramDate));
    return this;
  }
  
  public ProtectedPKIMessageBuilder setRecipKID(byte[] paramArrayOfByte)
  {
    this.hdrBuilder.setRecipKID(paramArrayOfByte);
    return this;
  }
  
  public ProtectedPKIMessageBuilder setRecipNonce(byte[] paramArrayOfByte)
  {
    this.hdrBuilder.setRecipNonce(paramArrayOfByte);
    return this;
  }
  
  public ProtectedPKIMessageBuilder setSenderKID(byte[] paramArrayOfByte)
  {
    this.hdrBuilder.setSenderKID(paramArrayOfByte);
    return this;
  }
  
  public ProtectedPKIMessageBuilder setSenderNonce(byte[] paramArrayOfByte)
  {
    this.hdrBuilder.setSenderNonce(paramArrayOfByte);
    return this;
  }
  
  public ProtectedPKIMessageBuilder setTransactionID(byte[] paramArrayOfByte)
  {
    this.hdrBuilder.setTransactionID(paramArrayOfByte);
    return this;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cert.cmp.ProtectedPKIMessageBuilder
 * JD-Core Version:    0.7.0.1
 */