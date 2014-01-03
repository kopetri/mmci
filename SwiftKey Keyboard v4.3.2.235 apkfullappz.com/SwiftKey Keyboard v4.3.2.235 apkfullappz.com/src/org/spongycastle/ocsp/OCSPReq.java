package org.spongycastle.ocsp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.cert.CertStore;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1OutputStream;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.ocsp.OCSPRequest;
import org.spongycastle.asn1.ocsp.Request;
import org.spongycastle.asn1.ocsp.TBSRequest;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.GeneralName;
import org.spongycastle.asn1.x509.X509Extensions;

public class OCSPReq
  implements java.security.cert.X509Extension
{
  private OCSPRequest req;
  
  public OCSPReq(InputStream paramInputStream)
    throws IOException
  {
    this(new ASN1InputStream(paramInputStream));
  }
  
  private OCSPReq(ASN1InputStream paramASN1InputStream)
    throws IOException
  {
    try
    {
      this.req = OCSPRequest.getInstance(paramASN1InputStream.readObject());
      return;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      throw new IOException("malformed request: " + localIllegalArgumentException.getMessage());
    }
    catch (ClassCastException localClassCastException)
    {
      throw new IOException("malformed request: " + localClassCastException.getMessage());
    }
  }
  
  public OCSPReq(OCSPRequest paramOCSPRequest)
  {
    this.req = paramOCSPRequest;
  }
  
  public OCSPReq(byte[] paramArrayOfByte)
    throws IOException
  {
    this(new ASN1InputStream(paramArrayOfByte));
  }
  
  /* Error */
  private List getCertList(String paramString)
    throws OCSPException, NoSuchProviderException
  {
    // Byte code:
    //   0: new 72	java/util/ArrayList
    //   3: dup
    //   4: invokespecial 73	java/util/ArrayList:<init>	()V
    //   7: astore_2
    //   8: new 75	java/io/ByteArrayOutputStream
    //   11: dup
    //   12: invokespecial 76	java/io/ByteArrayOutputStream:<init>	()V
    //   15: astore_3
    //   16: new 78	org/spongycastle/asn1/ASN1OutputStream
    //   19: dup
    //   20: aload_3
    //   21: invokespecial 81	org/spongycastle/asn1/ASN1OutputStream:<init>	(Ljava/io/OutputStream;)V
    //   24: astore 4
    //   26: aload_1
    //   27: invokestatic 87	org/spongycastle/ocsp/OCSPUtil:createX509CertificateFactory	(Ljava/lang/String;)Ljava/security/cert/CertificateFactory;
    //   30: astore 6
    //   32: aload_0
    //   33: getfield 38	org/spongycastle/ocsp/OCSPReq:req	Lorg/spongycastle/asn1/ocsp/OCSPRequest;
    //   36: invokevirtual 91	org/spongycastle/asn1/ocsp/OCSPRequest:getOptionalSignature	()Lorg/spongycastle/asn1/ocsp/Signature;
    //   39: invokevirtual 97	org/spongycastle/asn1/ocsp/Signature:getCerts	()Lorg/spongycastle/asn1/ASN1Sequence;
    //   42: astore 7
    //   44: aload 7
    //   46: ifnull +107 -> 153
    //   49: aload 7
    //   51: invokevirtual 103	org/spongycastle/asn1/ASN1Sequence:getObjects	()Ljava/util/Enumeration;
    //   54: astore 8
    //   56: aload 8
    //   58: invokeinterface 109 1 0
    //   63: ifeq +90 -> 153
    //   66: aload 4
    //   68: aload 8
    //   70: invokeinterface 113 1 0
    //   75: checkcast 115	org/spongycastle/asn1/ASN1Encodable
    //   78: invokevirtual 119	org/spongycastle/asn1/ASN1OutputStream:writeObject	(Lorg/spongycastle/asn1/ASN1Encodable;)V
    //   81: aload_2
    //   82: aload 6
    //   84: new 121	java/io/ByteArrayInputStream
    //   87: dup
    //   88: aload_3
    //   89: invokevirtual 125	java/io/ByteArrayOutputStream:toByteArray	()[B
    //   92: invokespecial 126	java/io/ByteArrayInputStream:<init>	([B)V
    //   95: invokevirtual 132	java/security/cert/CertificateFactory:generateCertificate	(Ljava/io/InputStream;)Ljava/security/cert/Certificate;
    //   98: invokeinterface 138 2 0
    //   103: pop
    //   104: aload_3
    //   105: invokevirtual 141	java/io/ByteArrayOutputStream:reset	()V
    //   108: goto -52 -> 56
    //   111: astore 5
    //   113: new 66	org/spongycastle/ocsp/OCSPException
    //   116: dup
    //   117: ldc 143
    //   119: aload 5
    //   121: invokespecial 146	org/spongycastle/ocsp/OCSPException:<init>	(Ljava/lang/String;Ljava/lang/Exception;)V
    //   124: athrow
    //   125: astore 10
    //   127: new 66	org/spongycastle/ocsp/OCSPException
    //   130: dup
    //   131: ldc 148
    //   133: aload 10
    //   135: invokespecial 146	org/spongycastle/ocsp/OCSPException:<init>	(Ljava/lang/String;Ljava/lang/Exception;)V
    //   138: athrow
    //   139: astore 9
    //   141: new 66	org/spongycastle/ocsp/OCSPException
    //   144: dup
    //   145: ldc 148
    //   147: aload 9
    //   149: invokespecial 146	org/spongycastle/ocsp/OCSPException:<init>	(Ljava/lang/String;Ljava/lang/Exception;)V
    //   152: athrow
    //   153: aload_2
    //   154: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	155	0	this	OCSPReq
    //   0	155	1	paramString	String
    //   7	147	2	localArrayList	java.util.ArrayList
    //   15	90	3	localByteArrayOutputStream	ByteArrayOutputStream
    //   24	43	4	localASN1OutputStream	ASN1OutputStream
    //   111	9	5	localCertificateException1	java.security.cert.CertificateException
    //   30	53	6	localCertificateFactory	java.security.cert.CertificateFactory
    //   42	8	7	localASN1Sequence	ASN1Sequence
    //   54	15	8	localEnumeration	Enumeration
    //   139	9	9	localCertificateException2	java.security.cert.CertificateException
    //   125	9	10	localIOException	IOException
    // Exception table:
    //   from	to	target	type
    //   26	32	111	java/security/cert/CertificateException
    //   66	104	125	java/io/IOException
    //   66	104	139	java/security/cert/CertificateException
  }
  
  private Set getExtensionOIDs(boolean paramBoolean)
  {
    HashSet localHashSet = new HashSet();
    X509Extensions localX509Extensions = getRequestExtensions();
    if (localX509Extensions != null)
    {
      Enumeration localEnumeration = localX509Extensions.oids();
      while (localEnumeration.hasMoreElements())
      {
        ASN1ObjectIdentifier localASN1ObjectIdentifier = (ASN1ObjectIdentifier)localEnumeration.nextElement();
        if (paramBoolean == localX509Extensions.getExtension(localASN1ObjectIdentifier).isCritical()) {
          localHashSet.add(localASN1ObjectIdentifier.getId());
        }
      }
    }
    return localHashSet;
  }
  
  public CertStore getCertificates(String paramString1, String paramString2)
    throws NoSuchAlgorithmException, NoSuchProviderException, OCSPException
  {
    if (!isSigned()) {
      return null;
    }
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
    if (!isSigned()) {
      return null;
    }
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
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    new ASN1OutputStream(localByteArrayOutputStream).writeObject(this.req);
    return localByteArrayOutputStream.toByteArray();
  }
  
  public byte[] getExtensionValue(String paramString)
  {
    X509Extensions localX509Extensions = getRequestExtensions();
    if (localX509Extensions != null)
    {
      org.spongycastle.asn1.x509.X509Extension localX509Extension = localX509Extensions.getExtension(new ASN1ObjectIdentifier(paramString));
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
  
  public X509Extensions getRequestExtensions()
  {
    return X509Extensions.getInstance(this.req.getTbsRequest().getRequestExtensions());
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
  
  public String getSignatureAlgOID()
  {
    if (!isSigned()) {
      return null;
    }
    return this.req.getOptionalSignature().getSignatureAlgorithm().getObjectId().getId();
  }
  
  public byte[] getTBSRequest()
    throws OCSPException
  {
    try
    {
      byte[] arrayOfByte = this.req.getTbsRequest().getEncoded();
      return arrayOfByte;
    }
    catch (IOException localIOException)
    {
      throw new OCSPException("problem encoding tbsRequest", localIOException);
    }
  }
  
  public int getVersion()
  {
    return 1 + this.req.getTbsRequest().getVersion().getValue().intValue();
  }
  
  public boolean hasUnsupportedCriticalExtension()
  {
    Set localSet = getCriticalExtensionOIDs();
    return (localSet != null) && (!localSet.isEmpty());
  }
  
  public boolean isSigned()
  {
    return this.req.getOptionalSignature() != null;
  }
  
  public boolean verify(PublicKey paramPublicKey, String paramString)
    throws OCSPException, NoSuchProviderException
  {
    if (!isSigned()) {
      throw new OCSPException("attempt to verify signature on unsigned object");
    }
    try
    {
      java.security.Signature localSignature = OCSPUtil.createSignatureInstance(getSignatureAlgOID(), paramString);
      localSignature.initVerify(paramPublicKey);
      ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
      new ASN1OutputStream(localByteArrayOutputStream).writeObject(this.req.getTbsRequest());
      localSignature.update(localByteArrayOutputStream.toByteArray());
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
 * Qualified Name:     org.spongycastle.ocsp.OCSPReq
 * JD-Core Version:    0.7.0.1
 */