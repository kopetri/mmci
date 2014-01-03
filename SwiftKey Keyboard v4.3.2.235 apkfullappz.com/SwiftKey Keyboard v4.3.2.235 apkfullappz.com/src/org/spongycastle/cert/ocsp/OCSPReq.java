package org.spongycastle.cert.ocsp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.List;
import java.util.Set;
import org.spongycastle.asn1.ASN1Exception;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OutputStream;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.ocsp.OCSPRequest;
import org.spongycastle.asn1.ocsp.Request;
import org.spongycastle.asn1.ocsp.Signature;
import org.spongycastle.asn1.ocsp.TBSRequest;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.Certificate;
import org.spongycastle.asn1.x509.Extension;
import org.spongycastle.asn1.x509.Extensions;
import org.spongycastle.asn1.x509.GeneralName;
import org.spongycastle.cert.CertIOException;
import org.spongycastle.cert.X509CertificateHolder;
import org.spongycastle.operator.ContentVerifier;
import org.spongycastle.operator.ContentVerifierProvider;

public class OCSPReq
{
  private static final X509CertificateHolder[] EMPTY_CERTS = new X509CertificateHolder[0];
  private Extensions extensions;
  private OCSPRequest req;
  
  private OCSPReq(ASN1InputStream paramASN1InputStream)
    throws IOException
  {
    try
    {
      this.req = OCSPRequest.getInstance(paramASN1InputStream.readObject());
      this.extensions = this.req.getTbsRequest().getRequestExtensions();
      return;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      throw new CertIOException("malformed request: " + localIllegalArgumentException.getMessage(), localIllegalArgumentException);
    }
    catch (ClassCastException localClassCastException)
    {
      throw new CertIOException("malformed request: " + localClassCastException.getMessage(), localClassCastException);
    }
    catch (ASN1Exception localASN1Exception)
    {
      throw new CertIOException("malformed request: " + localASN1Exception.getMessage(), localASN1Exception);
    }
  }
  
  public OCSPReq(OCSPRequest paramOCSPRequest)
  {
    this.req = paramOCSPRequest;
    this.extensions = paramOCSPRequest.getTbsRequest().getRequestExtensions();
  }
  
  public OCSPReq(byte[] paramArrayOfByte)
    throws IOException
  {
    this(new ASN1InputStream(paramArrayOfByte));
  }
  
  public X509CertificateHolder[] getCerts()
  {
    if (this.req.getOptionalSignature() != null)
    {
      ASN1Sequence localASN1Sequence = this.req.getOptionalSignature().getCerts();
      if (localASN1Sequence != null)
      {
        arrayOfX509CertificateHolder = new X509CertificateHolder[localASN1Sequence.size()];
        for (int i = 0; i != arrayOfX509CertificateHolder.length; i++) {
          arrayOfX509CertificateHolder[i] = new X509CertificateHolder(Certificate.getInstance(localASN1Sequence.getObjectAt(i)));
        }
      }
      X509CertificateHolder[] arrayOfX509CertificateHolder = EMPTY_CERTS;
      return arrayOfX509CertificateHolder;
    }
    return EMPTY_CERTS;
  }
  
  public Set getCriticalExtensionOIDs()
  {
    return OCSPUtils.getCriticalExtensionOIDs(this.extensions);
  }
  
  public byte[] getEncoded()
    throws IOException
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    new ASN1OutputStream(localByteArrayOutputStream).writeObject(this.req);
    return localByteArrayOutputStream.toByteArray();
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
  
  public Req[] getRequestList()
  {
    ASN1Sequence localASN1Sequence = this.req.getTbsRequest().getRequestList();
    Req[] arrayOfReq = new Req[localASN1Sequence.size()];
    for (int i = 0; i != arrayOfReq.length; i++) {
      arrayOfReq[i] = new Req(Request.getInstance(localASN1Sequence.getObjectAt(i)));
    }
    return arrayOfReq;
  }
  
  public GeneralName getRequestorName()
  {
    return GeneralName.getInstance(this.req.getTbsRequest().getRequestorName());
  }
  
  public byte[] getSignature()
  {
    if (!isSigned()) {
      return null;
    }
    return this.req.getOptionalSignature().getSignature().getBytes();
  }
  
  public ASN1ObjectIdentifier getSignatureAlgOID()
  {
    if (!isSigned()) {
      return null;
    }
    return this.req.getOptionalSignature().getSignatureAlgorithm().getAlgorithm();
  }
  
  public int getVersionNumber()
  {
    return 1 + this.req.getTbsRequest().getVersion().getValue().intValue();
  }
  
  public boolean hasExtensions()
  {
    return this.extensions != null;
  }
  
  public boolean isSignatureValid(ContentVerifierProvider paramContentVerifierProvider)
    throws OCSPException
  {
    if (!isSigned()) {
      throw new OCSPException("attempt to verify signature on unsigned object");
    }
    try
    {
      ContentVerifier localContentVerifier = paramContentVerifierProvider.get(this.req.getOptionalSignature().getSignatureAlgorithm());
      localContentVerifier.getOutputStream().write(this.req.getTbsRequest().getEncoded("DER"));
      boolean bool = localContentVerifier.verify(getSignature());
      return bool;
    }
    catch (Exception localException)
    {
      throw new OCSPException("exception processing signature: " + localException, localException);
    }
  }
  
  public boolean isSigned()
  {
    return this.req.getOptionalSignature() != null;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cert.ocsp.OCSPReq
 * JD-Core Version:    0.7.0.1
 */