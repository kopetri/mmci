package org.spongycastle.ocsp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.security.auth.x500.X500Principal;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1OutputStream;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.DERObjectIdentifier;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.ocsp.OCSPRequest;
import org.spongycastle.asn1.ocsp.Request;
import org.spongycastle.asn1.ocsp.TBSRequest;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.Extensions;
import org.spongycastle.asn1.x509.GeneralName;
import org.spongycastle.asn1.x509.X509CertificateStructure;
import org.spongycastle.asn1.x509.X509Extensions;
import org.spongycastle.jce.X509Principal;

public class OCSPReqGenerator
{
  private List list = new ArrayList();
  private X509Extensions requestExtensions = null;
  private GeneralName requestorName = null;
  
  private OCSPReq generateRequest(DERObjectIdentifier paramDERObjectIdentifier, PrivateKey paramPrivateKey, X509Certificate[] paramArrayOfX509Certificate, String paramString, SecureRandom paramSecureRandom)
    throws OCSPException, NoSuchProviderException
  {
    Iterator localIterator = this.list.iterator();
    ASN1EncodableVector localASN1EncodableVector1 = new ASN1EncodableVector();
    while (localIterator.hasNext()) {
      try
      {
        localASN1EncodableVector1.add(((RequestObject)localIterator.next()).toRequest());
      }
      catch (Exception localException2)
      {
        throw new OCSPException("exception creating Request", localException2);
      }
    }
    TBSRequest localTBSRequest = new TBSRequest(this.requestorName, new DERSequence(localASN1EncodableVector1), this.requestExtensions);
    org.spongycastle.asn1.ocsp.Signature localSignature = null;
    DERBitString localDERBitString;
    AlgorithmIdentifier localAlgorithmIdentifier;
    if (paramDERObjectIdentifier != null)
    {
      if (this.requestorName == null) {
        throw new OCSPException("requestorName must be specified if request is signed.");
      }
      try
      {
        java.security.Signature localSignature1 = OCSPUtil.createSignatureInstance(paramDERObjectIdentifier.getId(), paramString);
        if (paramSecureRandom != null) {
          localSignature1.initSign(paramPrivateKey, paramSecureRandom);
        }
        ByteArrayOutputStream localByteArrayOutputStream;
        ASN1EncodableVector localASN1EncodableVector2;
        int i;
        localSignature = new org.spongycastle.asn1.ocsp.Signature(localAlgorithmIdentifier, localDERBitString, new DERSequence(localASN1EncodableVector2));
      }
      catch (NoSuchProviderException localNoSuchProviderException)
      {
        for (;;)
        {
          try
          {
            localByteArrayOutputStream = new ByteArrayOutputStream();
            new ASN1OutputStream(localByteArrayOutputStream).writeObject(localTBSRequest);
            localSignature1.update(localByteArrayOutputStream.toByteArray());
            localDERBitString = new DERBitString(localSignature1.sign());
            localAlgorithmIdentifier = new AlgorithmIdentifier(paramDERObjectIdentifier, new DERNull());
            if ((paramArrayOfX509Certificate == null) || (paramArrayOfX509Certificate.length <= 0)) {
              break label403;
            }
            localASN1EncodableVector2 = new ASN1EncodableVector();
            i = 0;
          }
          catch (Exception localException1)
          {
            throw new OCSPException("exception processing TBSRequest: " + localException1, localException1);
          }
          try
          {
            if (i == paramArrayOfX509Certificate.length) {
              break;
            }
            localASN1EncodableVector2.add(new X509CertificateStructure((ASN1Sequence)ASN1Primitive.fromByteArray(paramArrayOfX509Certificate[i].getEncoded())));
            i++;
            continue;
            localSignature1.initSign(paramPrivateKey);
          }
          catch (IOException localIOException)
          {
            throw new OCSPException("error processing certs", localIOException);
          }
          catch (CertificateEncodingException localCertificateEncodingException)
          {
            throw new OCSPException("error encoding certs", localCertificateEncodingException);
          }
        }
        localNoSuchProviderException = localNoSuchProviderException;
        throw localNoSuchProviderException;
      }
      catch (GeneralSecurityException localGeneralSecurityException)
      {
        throw new OCSPException("exception creating signature: " + localGeneralSecurityException, localGeneralSecurityException);
      }
    }
    for (;;)
    {
      return new OCSPReq(new OCSPRequest(localTBSRequest, localSignature));
      label403:
      localSignature = new org.spongycastle.asn1.ocsp.Signature(localAlgorithmIdentifier, localDERBitString);
    }
  }
  
  public void addRequest(CertificateID paramCertificateID)
  {
    this.list.add(new RequestObject(paramCertificateID, null));
  }
  
  public void addRequest(CertificateID paramCertificateID, X509Extensions paramX509Extensions)
  {
    this.list.add(new RequestObject(paramCertificateID, paramX509Extensions));
  }
  
  public OCSPReq generate()
    throws OCSPException
  {
    try
    {
      OCSPReq localOCSPReq = generateRequest(null, null, null, null, null);
      return localOCSPReq;
    }
    catch (NoSuchProviderException localNoSuchProviderException)
    {
      throw new OCSPException("no provider! - " + localNoSuchProviderException, localNoSuchProviderException);
    }
  }
  
  public OCSPReq generate(String paramString1, PrivateKey paramPrivateKey, X509Certificate[] paramArrayOfX509Certificate, String paramString2)
    throws OCSPException, NoSuchProviderException, IllegalArgumentException
  {
    return generate(paramString1, paramPrivateKey, paramArrayOfX509Certificate, paramString2, null);
  }
  
  public OCSPReq generate(String paramString1, PrivateKey paramPrivateKey, X509Certificate[] paramArrayOfX509Certificate, String paramString2, SecureRandom paramSecureRandom)
    throws OCSPException, NoSuchProviderException, IllegalArgumentException
  {
    if (paramString1 == null) {
      throw new IllegalArgumentException("no signing algorithm specified");
    }
    try
    {
      OCSPReq localOCSPReq = generateRequest(OCSPUtil.getAlgorithmOID(paramString1), paramPrivateKey, paramArrayOfX509Certificate, paramString2, paramSecureRandom);
      return localOCSPReq;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      throw new IllegalArgumentException("unknown signing algorithm specified: " + paramString1);
    }
  }
  
  public Iterator getSignatureAlgNames()
  {
    return OCSPUtil.getAlgNames();
  }
  
  public void setRequestExtensions(X509Extensions paramX509Extensions)
  {
    this.requestExtensions = paramX509Extensions;
  }
  
  public void setRequestorName(X500Principal paramX500Principal)
  {
    try
    {
      this.requestorName = new GeneralName(4, new X509Principal(paramX500Principal.getEncoded()));
      return;
    }
    catch (IOException localIOException)
    {
      throw new IllegalArgumentException("cannot encode principal: " + localIOException);
    }
  }
  
  public void setRequestorName(GeneralName paramGeneralName)
  {
    this.requestorName = paramGeneralName;
  }
  
  private class RequestObject
  {
    CertificateID certId;
    X509Extensions extensions;
    
    public RequestObject(CertificateID paramCertificateID, X509Extensions paramX509Extensions)
    {
      this.certId = paramCertificateID;
      this.extensions = paramX509Extensions;
    }
    
    public Request toRequest()
      throws Exception
    {
      return new Request(this.certId.toASN1Object(), Extensions.getInstance(this.extensions));
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.ocsp.OCSPReqGenerator
 * JD-Core Version:    0.7.0.1
 */