package org.spongycastle.cert.ocsp;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.ocsp.OCSPRequest;
import org.spongycastle.asn1.ocsp.Request;
import org.spongycastle.asn1.ocsp.Signature;
import org.spongycastle.asn1.ocsp.TBSRequest;
import org.spongycastle.asn1.x500.X500Name;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.Extensions;
import org.spongycastle.asn1.x509.GeneralName;
import org.spongycastle.cert.X509CertificateHolder;
import org.spongycastle.operator.ContentSigner;

public class OCSPReqBuilder
{
  private List list = new ArrayList();
  private Extensions requestExtensions = null;
  private GeneralName requestorName = null;
  
  private OCSPReq generateRequest(ContentSigner paramContentSigner, X509CertificateHolder[] paramArrayOfX509CertificateHolder)
    throws OCSPException
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
    Signature localSignature = null;
    DERBitString localDERBitString;
    AlgorithmIdentifier localAlgorithmIdentifier;
    if (paramContentSigner != null)
    {
      if (this.requestorName == null) {
        throw new OCSPException("requestorName must be specified if request is signed.");
      }
      try
      {
        OutputStream localOutputStream = paramContentSigner.getOutputStream();
        localOutputStream.write(localTBSRequest.getEncoded("DER"));
        localOutputStream.close();
        localDERBitString = new DERBitString(paramContentSigner.getSignature());
        localAlgorithmIdentifier = paramContentSigner.getAlgorithmIdentifier();
        if ((paramArrayOfX509CertificateHolder != null) && (paramArrayOfX509CertificateHolder.length > 0))
        {
          ASN1EncodableVector localASN1EncodableVector2 = new ASN1EncodableVector();
          for (int i = 0; i != paramArrayOfX509CertificateHolder.length; i++) {
            localASN1EncodableVector2.add(paramArrayOfX509CertificateHolder[i].toASN1Structure());
          }
          localSignature = new Signature(localAlgorithmIdentifier, localDERBitString, new DERSequence(localASN1EncodableVector2));
        }
      }
      catch (Exception localException1)
      {
        throw new OCSPException("exception processing TBSRequest: " + localException1, localException1);
      }
    }
    for (;;)
    {
      return new OCSPReq(new OCSPRequest(localTBSRequest, localSignature));
      localSignature = new Signature(localAlgorithmIdentifier, localDERBitString);
    }
  }
  
  public OCSPReqBuilder addRequest(CertificateID paramCertificateID)
  {
    this.list.add(new RequestObject(paramCertificateID, null));
    return this;
  }
  
  public OCSPReqBuilder addRequest(CertificateID paramCertificateID, Extensions paramExtensions)
  {
    this.list.add(new RequestObject(paramCertificateID, paramExtensions));
    return this;
  }
  
  public OCSPReq build()
    throws OCSPException
  {
    return generateRequest(null, null);
  }
  
  public OCSPReq build(ContentSigner paramContentSigner, X509CertificateHolder[] paramArrayOfX509CertificateHolder)
    throws OCSPException, IllegalArgumentException
  {
    if (paramContentSigner == null) {
      throw new IllegalArgumentException("no signer specified");
    }
    return generateRequest(paramContentSigner, paramArrayOfX509CertificateHolder);
  }
  
  public OCSPReqBuilder setRequestExtensions(Extensions paramExtensions)
  {
    this.requestExtensions = paramExtensions;
    return this;
  }
  
  public OCSPReqBuilder setRequestorName(X500Name paramX500Name)
  {
    this.requestorName = new GeneralName(4, paramX500Name);
    return this;
  }
  
  public OCSPReqBuilder setRequestorName(GeneralName paramGeneralName)
  {
    this.requestorName = paramGeneralName;
    return this;
  }
  
  private class RequestObject
  {
    CertificateID certId;
    Extensions extensions;
    
    public RequestObject(CertificateID paramCertificateID, Extensions paramExtensions)
    {
      this.certId = paramCertificateID;
      this.extensions = paramExtensions;
    }
    
    public Request toRequest()
      throws Exception
    {
      return new Request(this.certId.toASN1Object(), this.extensions);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cert.ocsp.OCSPReqBuilder
 * JD-Core Version:    0.7.0.1
 */