package org.spongycastle.cert.ocsp;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Set;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.ocsp.BasicOCSPResponse;
import org.spongycastle.asn1.ocsp.ResponseData;
import org.spongycastle.asn1.ocsp.SingleResponse;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.Certificate;
import org.spongycastle.asn1.x509.Extension;
import org.spongycastle.asn1.x509.Extensions;
import org.spongycastle.cert.X509CertificateHolder;
import org.spongycastle.operator.ContentVerifier;
import org.spongycastle.operator.ContentVerifierProvider;

public class BasicOCSPResp
{
  private ResponseData data;
  private Extensions extensions;
  private BasicOCSPResponse resp;
  
  public BasicOCSPResp(BasicOCSPResponse paramBasicOCSPResponse)
  {
    this.resp = paramBasicOCSPResponse;
    this.data = paramBasicOCSPResponse.getTbsResponseData();
    this.extensions = Extensions.getInstance(paramBasicOCSPResponse.getTbsResponseData().getResponseExtensions());
  }
  
  public boolean equals(Object paramObject)
  {
    if (paramObject == this) {
      return true;
    }
    if (!(paramObject instanceof BasicOCSPResp)) {
      return false;
    }
    BasicOCSPResp localBasicOCSPResp = (BasicOCSPResp)paramObject;
    return this.resp.equals(localBasicOCSPResp.resp);
  }
  
  public X509CertificateHolder[] getCerts()
  {
    if (this.resp.getCerts() != null)
    {
      ASN1Sequence localASN1Sequence = this.resp.getCerts();
      if (localASN1Sequence != null)
      {
        arrayOfX509CertificateHolder = new X509CertificateHolder[localASN1Sequence.size()];
        for (int i = 0; i != arrayOfX509CertificateHolder.length; i++) {
          arrayOfX509CertificateHolder[i] = new X509CertificateHolder(Certificate.getInstance(localASN1Sequence.getObjectAt(i)));
        }
      }
      X509CertificateHolder[] arrayOfX509CertificateHolder = OCSPUtils.EMPTY_CERTS;
      return arrayOfX509CertificateHolder;
    }
    return OCSPUtils.EMPTY_CERTS;
  }
  
  public Set getCriticalExtensionOIDs()
  {
    return OCSPUtils.getCriticalExtensionOIDs(this.extensions);
  }
  
  public byte[] getEncoded()
    throws IOException
  {
    return this.resp.getEncoded();
  }
  
  public Extension getExtension(ASN1ObjectIdentifier paramASN1ObjectIdentifier)
  {
    if (this.extensions != null) {
      return this.extensions.getExtension(paramASN1ObjectIdentifier);
    }
    return null;
  }
  
  public List getExtensionOIDs()
  {
    return OCSPUtils.getExtensionOIDs(this.extensions);
  }
  
  public Set getNonCriticalExtensionOIDs()
  {
    return OCSPUtils.getNonCriticalExtensionOIDs(this.extensions);
  }
  
  public Date getProducedAt()
  {
    return OCSPUtils.extractDate(this.data.getProducedAt());
  }
  
  public RespID getResponderId()
  {
    return new RespID(this.data.getResponderID());
  }
  
  public SingleResp[] getResponses()
  {
    ASN1Sequence localASN1Sequence = this.data.getResponses();
    SingleResp[] arrayOfSingleResp = new SingleResp[localASN1Sequence.size()];
    for (int i = 0; i != arrayOfSingleResp.length; i++) {
      arrayOfSingleResp[i] = new SingleResp(SingleResponse.getInstance(localASN1Sequence.getObjectAt(i)));
    }
    return arrayOfSingleResp;
  }
  
  public byte[] getSignature()
  {
    return this.resp.getSignature().getBytes();
  }
  
  public ASN1ObjectIdentifier getSignatureAlgOID()
  {
    return this.resp.getSignatureAlgorithm().getAlgorithm();
  }
  
  public byte[] getTBSResponseData()
  {
    try
    {
      byte[] arrayOfByte = this.resp.getTbsResponseData().getEncoded("DER");
      return arrayOfByte;
    }
    catch (IOException localIOException) {}
    return null;
  }
  
  public int getVersion()
  {
    return 1 + this.data.getVersion().getValue().intValue();
  }
  
  public boolean hasExtensions()
  {
    return this.extensions != null;
  }
  
  public int hashCode()
  {
    return this.resp.hashCode();
  }
  
  public boolean isSignatureValid(ContentVerifierProvider paramContentVerifierProvider)
    throws OCSPException
  {
    try
    {
      ContentVerifier localContentVerifier = paramContentVerifierProvider.get(this.resp.getSignatureAlgorithm());
      OutputStream localOutputStream = localContentVerifier.getOutputStream();
      localOutputStream.write(this.resp.getTbsResponseData().getEncoded("DER"));
      localOutputStream.close();
      boolean bool = localContentVerifier.verify(getSignature());
      return bool;
    }
    catch (Exception localException)
    {
      throw new OCSPException("exception processing sig: " + localException, localException);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cert.ocsp.BasicOCSPResp
 * JD-Core Version:    0.7.0.1
 */