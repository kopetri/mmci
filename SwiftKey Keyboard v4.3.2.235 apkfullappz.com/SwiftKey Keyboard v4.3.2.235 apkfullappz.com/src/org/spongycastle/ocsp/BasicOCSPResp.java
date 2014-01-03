package org.spongycastle.ocsp;

import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.CertStore;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERGeneralizedTime;
import org.spongycastle.asn1.DERObjectIdentifier;
import org.spongycastle.asn1.ocsp.BasicOCSPResponse;
import org.spongycastle.asn1.ocsp.ResponseData;
import org.spongycastle.asn1.ocsp.SingleResponse;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.X509Extensions;

public class BasicOCSPResp
  implements java.security.cert.X509Extension
{
  X509Certificate[] chain = null;
  ResponseData data;
  BasicOCSPResponse resp;
  
  public BasicOCSPResp(BasicOCSPResponse paramBasicOCSPResponse)
  {
    this.resp = paramBasicOCSPResponse;
    this.data = paramBasicOCSPResponse.getTbsResponseData();
  }
  
  /* Error */
  private List getCertList(String paramString)
    throws OCSPException, NoSuchProviderException
  {
    // Byte code:
    //   0: new 41	java/util/ArrayList
    //   3: dup
    //   4: invokespecial 42	java/util/ArrayList:<init>	()V
    //   7: astore_2
    //   8: new 44	java/io/ByteArrayOutputStream
    //   11: dup
    //   12: invokespecial 45	java/io/ByteArrayOutputStream:<init>	()V
    //   15: astore_3
    //   16: new 47	org/spongycastle/asn1/ASN1OutputStream
    //   19: dup
    //   20: aload_3
    //   21: invokespecial 50	org/spongycastle/asn1/ASN1OutputStream:<init>	(Ljava/io/OutputStream;)V
    //   24: astore 4
    //   26: aload_1
    //   27: invokestatic 56	org/spongycastle/ocsp/OCSPUtil:createX509CertificateFactory	(Ljava/lang/String;)Ljava/security/cert/CertificateFactory;
    //   30: astore 6
    //   32: aload_0
    //   33: getfield 21	org/spongycastle/ocsp/BasicOCSPResp:resp	Lorg/spongycastle/asn1/ocsp/BasicOCSPResponse;
    //   36: invokevirtual 60	org/spongycastle/asn1/ocsp/BasicOCSPResponse:getCerts	()Lorg/spongycastle/asn1/ASN1Sequence;
    //   39: astore 7
    //   41: aload 7
    //   43: ifnull +107 -> 150
    //   46: aload 7
    //   48: invokevirtual 66	org/spongycastle/asn1/ASN1Sequence:getObjects	()Ljava/util/Enumeration;
    //   51: astore 8
    //   53: aload 8
    //   55: invokeinterface 72 1 0
    //   60: ifeq +90 -> 150
    //   63: aload 4
    //   65: aload 8
    //   67: invokeinterface 76 1 0
    //   72: checkcast 78	org/spongycastle/asn1/ASN1Encodable
    //   75: invokevirtual 82	org/spongycastle/asn1/ASN1OutputStream:writeObject	(Lorg/spongycastle/asn1/ASN1Encodable;)V
    //   78: aload_2
    //   79: aload 6
    //   81: new 84	java/io/ByteArrayInputStream
    //   84: dup
    //   85: aload_3
    //   86: invokevirtual 88	java/io/ByteArrayOutputStream:toByteArray	()[B
    //   89: invokespecial 91	java/io/ByteArrayInputStream:<init>	([B)V
    //   92: invokevirtual 97	java/security/cert/CertificateFactory:generateCertificate	(Ljava/io/InputStream;)Ljava/security/cert/Certificate;
    //   95: invokeinterface 103 2 0
    //   100: pop
    //   101: aload_3
    //   102: invokevirtual 106	java/io/ByteArrayOutputStream:reset	()V
    //   105: goto -52 -> 53
    //   108: astore 5
    //   110: new 33	org/spongycastle/ocsp/OCSPException
    //   113: dup
    //   114: ldc 108
    //   116: aload 5
    //   118: invokespecial 111	org/spongycastle/ocsp/OCSPException:<init>	(Ljava/lang/String;Ljava/lang/Exception;)V
    //   121: athrow
    //   122: astore 10
    //   124: new 33	org/spongycastle/ocsp/OCSPException
    //   127: dup
    //   128: ldc 113
    //   130: aload 10
    //   132: invokespecial 111	org/spongycastle/ocsp/OCSPException:<init>	(Ljava/lang/String;Ljava/lang/Exception;)V
    //   135: athrow
    //   136: astore 9
    //   138: new 33	org/spongycastle/ocsp/OCSPException
    //   141: dup
    //   142: ldc 113
    //   144: aload 9
    //   146: invokespecial 111	org/spongycastle/ocsp/OCSPException:<init>	(Ljava/lang/String;Ljava/lang/Exception;)V
    //   149: athrow
    //   150: aload_2
    //   151: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	152	0	this	BasicOCSPResp
    //   0	152	1	paramString	String
    //   7	144	2	localArrayList	java.util.ArrayList
    //   15	87	3	localByteArrayOutputStream	java.io.ByteArrayOutputStream
    //   24	40	4	localASN1OutputStream	org.spongycastle.asn1.ASN1OutputStream
    //   108	9	5	localCertificateException1	java.security.cert.CertificateException
    //   30	50	6	localCertificateFactory	java.security.cert.CertificateFactory
    //   39	8	7	localASN1Sequence	ASN1Sequence
    //   51	15	8	localEnumeration	Enumeration
    //   136	9	9	localCertificateException2	java.security.cert.CertificateException
    //   122	9	10	localIOException	IOException
    // Exception table:
    //   from	to	target	type
    //   26	32	108	java/security/cert/CertificateException
    //   63	101	122	java/io/IOException
    //   63	101	136	java/security/cert/CertificateException
  }
  
  private Set getExtensionOIDs(boolean paramBoolean)
  {
    HashSet localHashSet = new HashSet();
    X509Extensions localX509Extensions = getResponseExtensions();
    if (localX509Extensions != null)
    {
      Enumeration localEnumeration = localX509Extensions.oids();
      while (localEnumeration.hasMoreElements())
      {
        DERObjectIdentifier localDERObjectIdentifier = (DERObjectIdentifier)localEnumeration.nextElement();
        if (paramBoolean == localX509Extensions.getExtension(localDERObjectIdentifier).isCritical()) {
          localHashSet.add(localDERObjectIdentifier.getId());
        }
      }
    }
    return localHashSet;
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
  
  public CertStore getCertificates(String paramString1, String paramString2)
    throws NoSuchAlgorithmException, NoSuchProviderException, OCSPException
  {
    try
    {
      CertStore localCertStore = OCSPUtil.createCertStoreInstance(paramString1, new CollectionCertStoreParameters(getCertList(paramString2)), paramString2);
      return localCertStore;
    }
    catch (InvalidAlgorithmParameterException localInvalidAlgorithmParameterException)
    {
      throw new OCSPException("can't setup the CertStore", localInvalidAlgorithmParameterException);
    }
  }
  
  public X509Certificate[] getCerts(String paramString)
    throws OCSPException, NoSuchProviderException
  {
    List localList = getCertList(paramString);
    return (X509Certificate[])localList.toArray(new X509Certificate[localList.size()]);
  }
  
  public Set getCriticalExtensionOIDs()
  {
    return getExtensionOIDs(true);
  }
  
  public byte[] getEncoded()
    throws IOException
  {
    return this.resp.getEncoded();
  }
  
  public byte[] getExtensionValue(String paramString)
  {
    X509Extensions localX509Extensions = getResponseExtensions();
    if (localX509Extensions != null)
    {
      org.spongycastle.asn1.x509.X509Extension localX509Extension = localX509Extensions.getExtension(new DERObjectIdentifier(paramString));
      if (localX509Extension != null) {
        try
        {
          byte[] arrayOfByte = localX509Extension.getValue().getEncoded("DER");
          return arrayOfByte;
        }
        catch (Exception localException)
        {
          throw new RuntimeException("error encoding " + localException.toString());
        }
      }
    }
    return null;
  }
  
  public Set getNonCriticalExtensionOIDs()
  {
    return getExtensionOIDs(false);
  }
  
  public Date getProducedAt()
  {
    try
    {
      Date localDate = this.data.getProducedAt().getDate();
      return localDate;
    }
    catch (ParseException localParseException)
    {
      throw new IllegalStateException("ParseException:" + localParseException.getMessage());
    }
  }
  
  public RespID getResponderId()
  {
    return new RespID(this.data.getResponderID());
  }
  
  public RespData getResponseData()
  {
    return new RespData(this.resp.getTbsResponseData());
  }
  
  public X509Extensions getResponseExtensions()
  {
    return X509Extensions.getInstance(this.data.getResponseExtensions());
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
  
  public String getSignatureAlgName()
  {
    return OCSPUtil.getAlgorithmName(this.resp.getSignatureAlgorithm().getObjectId());
  }
  
  public String getSignatureAlgOID()
  {
    return this.resp.getSignatureAlgorithm().getObjectId().getId();
  }
  
  public byte[] getTBSResponseData()
    throws OCSPException
  {
    try
    {
      byte[] arrayOfByte = this.resp.getTbsResponseData().getEncoded();
      return arrayOfByte;
    }
    catch (IOException localIOException)
    {
      throw new OCSPException("problem encoding tbsResponseData", localIOException);
    }
  }
  
  public int getVersion()
  {
    return 1 + this.data.getVersion().getValue().intValue();
  }
  
  public boolean hasUnsupportedCriticalExtension()
  {
    Set localSet = getCriticalExtensionOIDs();
    return (localSet != null) && (!localSet.isEmpty());
  }
  
  public int hashCode()
  {
    return this.resp.hashCode();
  }
  
  public boolean verify(PublicKey paramPublicKey, String paramString)
    throws OCSPException, NoSuchProviderException
  {
    try
    {
      Signature localSignature = OCSPUtil.createSignatureInstance(getSignatureAlgName(), paramString);
      localSignature.initVerify(paramPublicKey);
      localSignature.update(this.resp.getTbsResponseData().getEncoded("DER"));
      boolean bool = localSignature.verify(getSignature());
      return bool;
    }
    catch (NoSuchProviderException localNoSuchProviderException)
    {
      throw localNoSuchProviderException;
    }
    catch (Exception localException)
    {
      throw new OCSPException("exception processing sig: " + localException, localException);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.ocsp.BasicOCSPResp
 * JD-Core Version:    0.7.0.1
 */