package org.spongycastle.cert.ocsp;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERGeneralizedTime;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.ocsp.BasicOCSPResponse;
import org.spongycastle.asn1.ocsp.CertStatus;
import org.spongycastle.asn1.ocsp.ResponseData;
import org.spongycastle.asn1.ocsp.RevokedInfo;
import org.spongycastle.asn1.ocsp.SingleResponse;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.CRLReason;
import org.spongycastle.asn1.x509.Extensions;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.cert.X509CertificateHolder;
import org.spongycastle.operator.ContentSigner;
import org.spongycastle.operator.DigestCalculator;

public class BasicOCSPRespBuilder
{
  private List list = new ArrayList();
  private RespID responderID;
  private Extensions responseExtensions = null;
  
  public BasicOCSPRespBuilder(SubjectPublicKeyInfo paramSubjectPublicKeyInfo, DigestCalculator paramDigestCalculator)
    throws OCSPException
  {
    this.responderID = new RespID(paramSubjectPublicKeyInfo, paramDigestCalculator);
  }
  
  public BasicOCSPRespBuilder(RespID paramRespID)
  {
    this.responderID = paramRespID;
  }
  
  public BasicOCSPRespBuilder addResponse(CertificateID paramCertificateID, CertificateStatus paramCertificateStatus)
  {
    this.list.add(new ResponseObject(paramCertificateID, paramCertificateStatus, new Date(), null, null));
    return this;
  }
  
  public BasicOCSPRespBuilder addResponse(CertificateID paramCertificateID, CertificateStatus paramCertificateStatus, Date paramDate1, Date paramDate2, Extensions paramExtensions)
  {
    this.list.add(new ResponseObject(paramCertificateID, paramCertificateStatus, paramDate1, paramDate2, paramExtensions));
    return this;
  }
  
  public BasicOCSPRespBuilder addResponse(CertificateID paramCertificateID, CertificateStatus paramCertificateStatus, Date paramDate, Extensions paramExtensions)
  {
    this.list.add(new ResponseObject(paramCertificateID, paramCertificateStatus, new Date(), paramDate, paramExtensions));
    return this;
  }
  
  public BasicOCSPRespBuilder addResponse(CertificateID paramCertificateID, CertificateStatus paramCertificateStatus, Extensions paramExtensions)
  {
    this.list.add(new ResponseObject(paramCertificateID, paramCertificateStatus, new Date(), null, paramExtensions));
    return this;
  }
  
  public BasicOCSPResp build(ContentSigner paramContentSigner, X509CertificateHolder[] paramArrayOfX509CertificateHolder, Date paramDate)
    throws OCSPException
  {
    Iterator localIterator = this.list.iterator();
    ASN1EncodableVector localASN1EncodableVector1 = new ASN1EncodableVector();
    while (localIterator.hasNext()) {
      try
      {
        localASN1EncodableVector1.add(((ResponseObject)localIterator.next()).toResponse());
      }
      catch (Exception localException2)
      {
        throw new OCSPException("exception creating Request", localException2);
      }
    }
    ResponseData localResponseData = new ResponseData(this.responderID.toASN1Object(), new DERGeneralizedTime(paramDate), new DERSequence(localASN1EncodableVector1), this.responseExtensions);
    DERBitString localDERBitString;
    AlgorithmIdentifier localAlgorithmIdentifier;
    DERSequence localDERSequence;
    try
    {
      OutputStream localOutputStream = paramContentSigner.getOutputStream();
      localOutputStream.write(localResponseData.getEncoded("DER"));
      localOutputStream.close();
      localDERBitString = new DERBitString(paramContentSigner.getSignature());
      localAlgorithmIdentifier = paramContentSigner.getAlgorithmIdentifier();
      localDERSequence = null;
      if (paramArrayOfX509CertificateHolder != null)
      {
        int i = paramArrayOfX509CertificateHolder.length;
        localDERSequence = null;
        if (i > 0)
        {
          ASN1EncodableVector localASN1EncodableVector2 = new ASN1EncodableVector();
          for (int j = 0; j != paramArrayOfX509CertificateHolder.length; j++) {
            localASN1EncodableVector2.add(paramArrayOfX509CertificateHolder[j].toASN1Structure());
          }
          localDERSequence = new DERSequence(localASN1EncodableVector2);
        }
      }
    }
    catch (Exception localException1)
    {
      throw new OCSPException("exception processing TBSRequest: " + localException1.getMessage(), localException1);
    }
    return new BasicOCSPResp(new BasicOCSPResponse(localResponseData, localAlgorithmIdentifier, localDERBitString, localDERSequence));
  }
  
  public BasicOCSPRespBuilder setResponseExtensions(Extensions paramExtensions)
  {
    this.responseExtensions = paramExtensions;
    return this;
  }
  
  private class ResponseObject
  {
    CertificateID certId;
    CertStatus certStatus;
    Extensions extensions;
    DERGeneralizedTime nextUpdate;
    DERGeneralizedTime thisUpdate;
    
    public ResponseObject(CertificateID paramCertificateID, CertificateStatus paramCertificateStatus, Date paramDate1, Date paramDate2, Extensions paramExtensions)
    {
      this.certId = paramCertificateID;
      if (paramCertificateStatus == null)
      {
        this.certStatus = new CertStatus();
        this.thisUpdate = new DERGeneralizedTime(paramDate1);
        if (paramDate2 == null) {
          break label185;
        }
      }
      label185:
      for (this.nextUpdate = new DERGeneralizedTime(paramDate2);; this.nextUpdate = null)
      {
        this.extensions = paramExtensions;
        return;
        if ((paramCertificateStatus instanceof UnknownStatus))
        {
          this.certStatus = new CertStatus(2, new DERNull());
          break;
        }
        RevokedStatus localRevokedStatus = (RevokedStatus)paramCertificateStatus;
        if (localRevokedStatus.hasRevocationReason())
        {
          this.certStatus = new CertStatus(new RevokedInfo(new DERGeneralizedTime(localRevokedStatus.getRevocationTime()), CRLReason.lookup(localRevokedStatus.getRevocationReason())));
          break;
        }
        this.certStatus = new CertStatus(new RevokedInfo(new DERGeneralizedTime(localRevokedStatus.getRevocationTime()), null));
        break;
      }
    }
    
    public SingleResponse toResponse()
      throws Exception
    {
      return new SingleResponse(this.certId.toASN1Object(), this.certStatus, this.thisUpdate, this.nextUpdate, this.extensions);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cert.ocsp.BasicOCSPRespBuilder
 * JD-Core Version:    0.7.0.1
 */