package org.spongycastle.ocsp;

import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.spongycastle.asn1.DERGeneralizedTime;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.ocsp.CertStatus;
import org.spongycastle.asn1.ocsp.RevokedInfo;
import org.spongycastle.asn1.ocsp.SingleResponse;
import org.spongycastle.asn1.x509.CRLReason;
import org.spongycastle.asn1.x509.X509Extensions;

public class BasicOCSPRespGenerator
{
  private List list = new ArrayList();
  private RespID responderID;
  private X509Extensions responseExtensions = null;
  
  public BasicOCSPRespGenerator(PublicKey paramPublicKey)
    throws OCSPException
  {
    this.responderID = new RespID(paramPublicKey);
  }
  
  public BasicOCSPRespGenerator(RespID paramRespID)
  {
    this.responderID = paramRespID;
  }
  
  /* Error */
  private BasicOCSPResp generateResponse(String paramString1, PrivateKey paramPrivateKey, X509Certificate[] paramArrayOfX509Certificate, Date paramDate, String paramString2, SecureRandom paramSecureRandom)
    throws OCSPException, NoSuchProviderException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 22	org/spongycastle/ocsp/BasicOCSPRespGenerator:list	Ljava/util/List;
    //   4: invokeinterface 49 1 0
    //   9: astore 7
    //   11: aload_1
    //   12: invokestatic 55	org/spongycastle/ocsp/OCSPUtil:getAlgorithmOID	(Ljava/lang/String;)Lorg/spongycastle/asn1/DERObjectIdentifier;
    //   15: astore 9
    //   17: new 57	org/spongycastle/asn1/ASN1EncodableVector
    //   20: dup
    //   21: invokespecial 58	org/spongycastle/asn1/ASN1EncodableVector:<init>	()V
    //   24: astore 10
    //   26: aload 7
    //   28: invokeinterface 64 1 0
    //   33: ifeq +50 -> 83
    //   36: aload 10
    //   38: aload 7
    //   40: invokeinterface 68 1 0
    //   45: checkcast 70	org/spongycastle/ocsp/BasicOCSPRespGenerator$ResponseObject
    //   48: invokevirtual 74	org/spongycastle/ocsp/BasicOCSPRespGenerator$ResponseObject:toResponse	()Lorg/spongycastle/asn1/ocsp/SingleResponse;
    //   51: invokevirtual 78	org/spongycastle/asn1/ASN1EncodableVector:add	(Lorg/spongycastle/asn1/ASN1Encodable;)V
    //   54: goto -28 -> 26
    //   57: astore 24
    //   59: new 14	org/spongycastle/ocsp/OCSPException
    //   62: dup
    //   63: ldc 80
    //   65: aload 24
    //   67: invokespecial 83	org/spongycastle/ocsp/OCSPException:<init>	(Ljava/lang/String;Ljava/lang/Exception;)V
    //   70: athrow
    //   71: astore 8
    //   73: new 85	java/lang/IllegalArgumentException
    //   76: dup
    //   77: ldc 87
    //   79: invokespecial 90	java/lang/IllegalArgumentException:<init>	(Ljava/lang/String;)V
    //   82: athrow
    //   83: new 92	org/spongycastle/asn1/ocsp/ResponseData
    //   86: dup
    //   87: aload_0
    //   88: getfield 30	org/spongycastle/ocsp/BasicOCSPRespGenerator:responderID	Lorg/spongycastle/ocsp/RespID;
    //   91: invokevirtual 96	org/spongycastle/ocsp/RespID:toASN1Object	()Lorg/spongycastle/asn1/ocsp/ResponderID;
    //   94: new 98	org/spongycastle/asn1/DERGeneralizedTime
    //   97: dup
    //   98: aload 4
    //   100: invokespecial 101	org/spongycastle/asn1/DERGeneralizedTime:<init>	(Ljava/util/Date;)V
    //   103: new 103	org/spongycastle/asn1/DERSequence
    //   106: dup
    //   107: aload 10
    //   109: invokespecial 106	org/spongycastle/asn1/DERSequence:<init>	(Lorg/spongycastle/asn1/ASN1EncodableVector;)V
    //   112: aload_0
    //   113: getfield 24	org/spongycastle/ocsp/BasicOCSPRespGenerator:responseExtensions	Lorg/spongycastle/asn1/x509/X509Extensions;
    //   116: invokespecial 109	org/spongycastle/asn1/ocsp/ResponseData:<init>	(Lorg/spongycastle/asn1/ocsp/ResponderID;Lorg/spongycastle/asn1/DERGeneralizedTime;Lorg/spongycastle/asn1/ASN1Sequence;Lorg/spongycastle/asn1/x509/X509Extensions;)V
    //   119: astore 11
    //   121: aload_1
    //   122: aload 5
    //   124: invokestatic 113	org/spongycastle/ocsp/OCSPUtil:createSignatureInstance	(Ljava/lang/String;Ljava/lang/String;)Ljava/security/Signature;
    //   127: astore 14
    //   129: aload 6
    //   131: ifnull +113 -> 244
    //   134: aload 14
    //   136: aload_2
    //   137: aload 6
    //   139: invokevirtual 119	java/security/Signature:initSign	(Ljava/security/PrivateKey;Ljava/security/SecureRandom;)V
    //   142: aload 14
    //   144: aload 11
    //   146: ldc 121
    //   148: invokevirtual 125	org/spongycastle/asn1/ocsp/ResponseData:getEncoded	(Ljava/lang/String;)[B
    //   151: invokevirtual 129	java/security/Signature:update	([B)V
    //   154: new 131	org/spongycastle/asn1/DERBitString
    //   157: dup
    //   158: aload 14
    //   160: invokevirtual 135	java/security/Signature:sign	()[B
    //   163: invokespecial 137	org/spongycastle/asn1/DERBitString:<init>	([B)V
    //   166: astore 16
    //   168: aload 9
    //   170: invokestatic 141	org/spongycastle/ocsp/OCSPUtil:getSigAlgID	(Lorg/spongycastle/asn1/DERObjectIdentifier;)Lorg/spongycastle/asn1/x509/AlgorithmIdentifier;
    //   173: astore 17
    //   175: aconst_null
    //   176: astore 18
    //   178: aload_3
    //   179: ifnull +176 -> 355
    //   182: aload_3
    //   183: arraylength
    //   184: istore 19
    //   186: aconst_null
    //   187: astore 18
    //   189: iload 19
    //   191: ifle +164 -> 355
    //   194: new 57	org/spongycastle/asn1/ASN1EncodableVector
    //   197: dup
    //   198: invokespecial 58	org/spongycastle/asn1/ASN1EncodableVector:<init>	()V
    //   201: astore 20
    //   203: iconst_0
    //   204: istore 21
    //   206: iload 21
    //   208: aload_3
    //   209: arraylength
    //   210: if_icmpeq +134 -> 344
    //   213: aload 20
    //   215: new 143	org/spongycastle/asn1/x509/X509CertificateStructure
    //   218: dup
    //   219: aload_3
    //   220: iload 21
    //   222: aaload
    //   223: invokevirtual 147	java/security/cert/X509Certificate:getEncoded	()[B
    //   226: invokestatic 153	org/spongycastle/asn1/ASN1Primitive:fromByteArray	([B)Lorg/spongycastle/asn1/ASN1Primitive;
    //   229: checkcast 155	org/spongycastle/asn1/ASN1Sequence
    //   232: invokespecial 158	org/spongycastle/asn1/x509/X509CertificateStructure:<init>	(Lorg/spongycastle/asn1/ASN1Sequence;)V
    //   235: invokevirtual 78	org/spongycastle/asn1/ASN1EncodableVector:add	(Lorg/spongycastle/asn1/ASN1Encodable;)V
    //   238: iinc 21 1
    //   241: goto -35 -> 206
    //   244: aload 14
    //   246: aload_2
    //   247: invokevirtual 161	java/security/Signature:initSign	(Ljava/security/PrivateKey;)V
    //   250: goto -108 -> 142
    //   253: astore 13
    //   255: aload 13
    //   257: athrow
    //   258: astore 12
    //   260: new 14	org/spongycastle/ocsp/OCSPException
    //   263: dup
    //   264: new 163	java/lang/StringBuilder
    //   267: dup
    //   268: ldc 165
    //   270: invokespecial 166	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   273: aload 12
    //   275: invokevirtual 170	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   278: invokevirtual 174	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   281: aload 12
    //   283: invokespecial 83	org/spongycastle/ocsp/OCSPException:<init>	(Ljava/lang/String;Ljava/lang/Exception;)V
    //   286: athrow
    //   287: astore 15
    //   289: new 14	org/spongycastle/ocsp/OCSPException
    //   292: dup
    //   293: new 163	java/lang/StringBuilder
    //   296: dup
    //   297: ldc 176
    //   299: invokespecial 166	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   302: aload 15
    //   304: invokevirtual 170	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   307: invokevirtual 174	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   310: aload 15
    //   312: invokespecial 83	org/spongycastle/ocsp/OCSPException:<init>	(Ljava/lang/String;Ljava/lang/Exception;)V
    //   315: athrow
    //   316: astore 23
    //   318: new 14	org/spongycastle/ocsp/OCSPException
    //   321: dup
    //   322: ldc 178
    //   324: aload 23
    //   326: invokespecial 83	org/spongycastle/ocsp/OCSPException:<init>	(Ljava/lang/String;Ljava/lang/Exception;)V
    //   329: athrow
    //   330: astore 22
    //   332: new 14	org/spongycastle/ocsp/OCSPException
    //   335: dup
    //   336: ldc 180
    //   338: aload 22
    //   340: invokespecial 83	org/spongycastle/ocsp/OCSPException:<init>	(Ljava/lang/String;Ljava/lang/Exception;)V
    //   343: athrow
    //   344: new 103	org/spongycastle/asn1/DERSequence
    //   347: dup
    //   348: aload 20
    //   350: invokespecial 106	org/spongycastle/asn1/DERSequence:<init>	(Lorg/spongycastle/asn1/ASN1EncodableVector;)V
    //   353: astore 18
    //   355: new 182	org/spongycastle/ocsp/BasicOCSPResp
    //   358: dup
    //   359: new 184	org/spongycastle/asn1/ocsp/BasicOCSPResponse
    //   362: dup
    //   363: aload 11
    //   365: aload 17
    //   367: aload 16
    //   369: aload 18
    //   371: invokespecial 187	org/spongycastle/asn1/ocsp/BasicOCSPResponse:<init>	(Lorg/spongycastle/asn1/ocsp/ResponseData;Lorg/spongycastle/asn1/x509/AlgorithmIdentifier;Lorg/spongycastle/asn1/DERBitString;Lorg/spongycastle/asn1/ASN1Sequence;)V
    //   374: invokespecial 190	org/spongycastle/ocsp/BasicOCSPResp:<init>	(Lorg/spongycastle/asn1/ocsp/BasicOCSPResponse;)V
    //   377: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	378	0	this	BasicOCSPRespGenerator
    //   0	378	1	paramString1	String
    //   0	378	2	paramPrivateKey	PrivateKey
    //   0	378	3	paramArrayOfX509Certificate	X509Certificate[]
    //   0	378	4	paramDate	Date
    //   0	378	5	paramString2	String
    //   0	378	6	paramSecureRandom	SecureRandom
    //   9	30	7	localIterator	Iterator
    //   71	1	8	localException1	Exception
    //   15	154	9	localDERObjectIdentifier	org.spongycastle.asn1.DERObjectIdentifier
    //   24	84	10	localASN1EncodableVector1	org.spongycastle.asn1.ASN1EncodableVector
    //   119	245	11	localResponseData	org.spongycastle.asn1.ocsp.ResponseData
    //   258	24	12	localGeneralSecurityException	java.security.GeneralSecurityException
    //   253	3	13	localNoSuchProviderException	NoSuchProviderException
    //   127	118	14	localSignature	java.security.Signature
    //   287	24	15	localException2	Exception
    //   166	202	16	localDERBitString	org.spongycastle.asn1.DERBitString
    //   173	193	17	localAlgorithmIdentifier	org.spongycastle.asn1.x509.AlgorithmIdentifier
    //   176	194	18	localDERSequence	org.spongycastle.asn1.DERSequence
    //   184	6	19	i	int
    //   201	148	20	localASN1EncodableVector2	org.spongycastle.asn1.ASN1EncodableVector
    //   204	35	21	j	int
    //   330	9	22	localCertificateEncodingException	java.security.cert.CertificateEncodingException
    //   316	9	23	localIOException	java.io.IOException
    //   57	9	24	localException3	Exception
    // Exception table:
    //   from	to	target	type
    //   36	54	57	java/lang/Exception
    //   11	17	71	java/lang/Exception
    //   121	129	253	java/security/NoSuchProviderException
    //   134	142	253	java/security/NoSuchProviderException
    //   244	250	253	java/security/NoSuchProviderException
    //   121	129	258	java/security/GeneralSecurityException
    //   134	142	258	java/security/GeneralSecurityException
    //   244	250	258	java/security/GeneralSecurityException
    //   142	168	287	java/lang/Exception
    //   206	238	316	java/io/IOException
    //   206	238	330	java/security/cert/CertificateEncodingException
  }
  
  public void addResponse(CertificateID paramCertificateID, CertificateStatus paramCertificateStatus)
  {
    this.list.add(new ResponseObject(paramCertificateID, paramCertificateStatus, new Date(), null, null));
  }
  
  public void addResponse(CertificateID paramCertificateID, CertificateStatus paramCertificateStatus, Date paramDate1, Date paramDate2, X509Extensions paramX509Extensions)
  {
    this.list.add(new ResponseObject(paramCertificateID, paramCertificateStatus, paramDate1, paramDate2, paramX509Extensions));
  }
  
  public void addResponse(CertificateID paramCertificateID, CertificateStatus paramCertificateStatus, Date paramDate, X509Extensions paramX509Extensions)
  {
    this.list.add(new ResponseObject(paramCertificateID, paramCertificateStatus, new Date(), paramDate, paramX509Extensions));
  }
  
  public void addResponse(CertificateID paramCertificateID, CertificateStatus paramCertificateStatus, X509Extensions paramX509Extensions)
  {
    this.list.add(new ResponseObject(paramCertificateID, paramCertificateStatus, new Date(), null, paramX509Extensions));
  }
  
  public BasicOCSPResp generate(String paramString1, PrivateKey paramPrivateKey, X509Certificate[] paramArrayOfX509Certificate, Date paramDate, String paramString2)
    throws OCSPException, NoSuchProviderException, IllegalArgumentException
  {
    return generate(paramString1, paramPrivateKey, paramArrayOfX509Certificate, paramDate, paramString2, null);
  }
  
  public BasicOCSPResp generate(String paramString1, PrivateKey paramPrivateKey, X509Certificate[] paramArrayOfX509Certificate, Date paramDate, String paramString2, SecureRandom paramSecureRandom)
    throws OCSPException, NoSuchProviderException, IllegalArgumentException
  {
    if (paramString1 == null) {
      throw new IllegalArgumentException("no signing algorithm specified");
    }
    return generateResponse(paramString1, paramPrivateKey, paramArrayOfX509Certificate, paramDate, paramString2, paramSecureRandom);
  }
  
  public Iterator getSignatureAlgNames()
  {
    return OCSPUtil.getAlgNames();
  }
  
  public void setResponseExtensions(X509Extensions paramX509Extensions)
  {
    this.responseExtensions = paramX509Extensions;
  }
  
  private class ResponseObject
  {
    CertificateID certId;
    CertStatus certStatus;
    X509Extensions extensions;
    DERGeneralizedTime nextUpdate;
    DERGeneralizedTime thisUpdate;
    
    public ResponseObject(CertificateID paramCertificateID, CertificateStatus paramCertificateStatus, Date paramDate1, Date paramDate2, X509Extensions paramX509Extensions)
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
        this.extensions = paramX509Extensions;
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
 * Qualified Name:     org.spongycastle.ocsp.BasicOCSPRespGenerator
 * JD-Core Version:    0.7.0.1
 */