package org.spongycastle.jcajce.provider.asymmetric.x509;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.security.cert.CRL;
import java.security.cert.CRLException;
import java.security.cert.CertPath;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactorySpi;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1Set;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.pkcs.SignedData;
import org.spongycastle.asn1.x509.CertificateList;
import org.spongycastle.asn1.x509.X509CertificateStructure;
import org.spongycastle.jce.provider.X509CRLObject;
import org.spongycastle.jce.provider.X509CertificateObject;

public class CertificateFactory
  extends CertificateFactorySpi
{
  private static final PEMUtil PEM_CERT_PARSER = new PEMUtil("CERTIFICATE");
  private static final PEMUtil PEM_CRL_PARSER = new PEMUtil("CRL");
  private InputStream currentCrlStream = null;
  private InputStream currentStream = null;
  private ASN1Set sCrlData = null;
  private int sCrlDataObjectCount = 0;
  private ASN1Set sData = null;
  private int sDataObjectCount = 0;
  
  private CRL getCRL()
    throws CRLException
  {
    if ((this.sCrlData == null) || (this.sCrlDataObjectCount >= this.sCrlData.size())) {
      return null;
    }
    ASN1Set localASN1Set = this.sCrlData;
    int i = this.sCrlDataObjectCount;
    this.sCrlDataObjectCount = (i + 1);
    return createCRL(CertificateList.getInstance(localASN1Set.getObjectAt(i)));
  }
  
  private Certificate getCertificate()
    throws CertificateParsingException
  {
    if (this.sData != null) {
      while (this.sDataObjectCount < this.sData.size())
      {
        ASN1Set localASN1Set = this.sData;
        int i = this.sDataObjectCount;
        this.sDataObjectCount = (i + 1);
        ASN1Encodable localASN1Encodable = localASN1Set.getObjectAt(i);
        if ((localASN1Encodable instanceof ASN1Sequence)) {
          return new X509CertificateObject(X509CertificateStructure.getInstance(localASN1Encodable));
        }
      }
    }
    return null;
  }
  
  private CRL readDERCRL(ASN1InputStream paramASN1InputStream)
    throws IOException, CRLException
  {
    ASN1Sequence localASN1Sequence = (ASN1Sequence)paramASN1InputStream.readObject();
    if ((localASN1Sequence.size() > 1) && ((localASN1Sequence.getObjectAt(0) instanceof ASN1ObjectIdentifier)) && (localASN1Sequence.getObjectAt(0).equals(PKCSObjectIdentifiers.signedData)))
    {
      this.sCrlData = SignedData.getInstance(ASN1Sequence.getInstance((ASN1TaggedObject)localASN1Sequence.getObjectAt(1), true)).getCRLs();
      return getCRL();
    }
    return createCRL(CertificateList.getInstance(localASN1Sequence));
  }
  
  private Certificate readDERCertificate(ASN1InputStream paramASN1InputStream)
    throws IOException, CertificateParsingException
  {
    ASN1Sequence localASN1Sequence = (ASN1Sequence)paramASN1InputStream.readObject();
    if ((localASN1Sequence.size() > 1) && ((localASN1Sequence.getObjectAt(0) instanceof ASN1ObjectIdentifier)) && (localASN1Sequence.getObjectAt(0).equals(PKCSObjectIdentifiers.signedData)))
    {
      this.sData = SignedData.getInstance(ASN1Sequence.getInstance((ASN1TaggedObject)localASN1Sequence.getObjectAt(1), true)).getCertificates();
      return getCertificate();
    }
    return new X509CertificateObject(X509CertificateStructure.getInstance(localASN1Sequence));
  }
  
  private CRL readPEMCRL(InputStream paramInputStream)
    throws IOException, CRLException
  {
    ASN1Sequence localASN1Sequence = PEM_CRL_PARSER.readPEMObject(paramInputStream);
    if (localASN1Sequence != null) {
      return createCRL(CertificateList.getInstance(localASN1Sequence));
    }
    return null;
  }
  
  private Certificate readPEMCertificate(InputStream paramInputStream)
    throws IOException, CertificateParsingException
  {
    ASN1Sequence localASN1Sequence = PEM_CERT_PARSER.readPEMObject(paramInputStream);
    if (localASN1Sequence != null) {
      return new X509CertificateObject(X509CertificateStructure.getInstance(localASN1Sequence));
    }
    return null;
  }
  
  protected CRL createCRL(CertificateList paramCertificateList)
    throws CRLException
  {
    return new X509CRLObject(paramCertificateList);
  }
  
  public CRL engineGenerateCRL(InputStream paramInputStream)
    throws CRLException
  {
    if (this.currentCrlStream == null)
    {
      this.currentCrlStream = paramInputStream;
      this.sCrlData = null;
      this.sCrlDataObjectCount = 0;
    }
    for (;;)
    {
      try
      {
        if (this.sCrlData == null) {
          continue;
        }
        if (this.sCrlDataObjectCount == this.sCrlData.size()) {
          continue;
        }
        CRL localCRL3 = getCRL();
        localCRL1 = localCRL3;
      }
      catch (CRLException localCRLException)
      {
        throw localCRLException;
        PushbackInputStream localPushbackInputStream = new PushbackInputStream(paramInputStream);
        int i = localPushbackInputStream.read();
        CRL localCRL1 = null;
        if (i == -1) {
          continue;
        }
        localPushbackInputStream.unread(i);
        if (i == 48) {
          continue;
        }
        return readPEMCRL(localPushbackInputStream);
        CRL localCRL2 = readDERCRL(new ASN1InputStream(localPushbackInputStream, true));
        return localCRL2;
      }
      catch (Exception localException)
      {
        throw new CRLException(localException.toString());
      }
      return localCRL1;
      if (this.currentCrlStream != paramInputStream)
      {
        this.currentCrlStream = paramInputStream;
        this.sCrlData = null;
        this.sCrlDataObjectCount = 0;
      }
    }
    this.sCrlData = null;
    this.sCrlDataObjectCount = 0;
    return null;
  }
  
  public Collection engineGenerateCRLs(InputStream paramInputStream)
    throws CRLException
  {
    ArrayList localArrayList = new ArrayList();
    for (;;)
    {
      CRL localCRL = engineGenerateCRL(paramInputStream);
      if (localCRL == null) {
        break;
      }
      localArrayList.add(localCRL);
    }
    return localArrayList;
  }
  
  public CertPath engineGenerateCertPath(InputStream paramInputStream)
    throws CertificateException
  {
    return engineGenerateCertPath(paramInputStream, "PkiPath");
  }
  
  public CertPath engineGenerateCertPath(InputStream paramInputStream, String paramString)
    throws CertificateException
  {
    return new PKIXCertPath(paramInputStream, paramString);
  }
  
  public CertPath engineGenerateCertPath(List paramList)
    throws CertificateException
  {
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
    {
      Object localObject = localIterator.next();
      if ((localObject != null) && (!(localObject instanceof X509Certificate))) {
        throw new CertificateException("list contains non X509Certificate object while creating CertPath\n" + localObject.toString());
      }
    }
    return new PKIXCertPath(paramList);
  }
  
  public Certificate engineGenerateCertificate(InputStream paramInputStream)
    throws CertificateException
  {
    if (this.currentStream == null)
    {
      this.currentStream = paramInputStream;
      this.sData = null;
      this.sDataObjectCount = 0;
    }
    PushbackInputStream localPushbackInputStream;
    int i;
    do
    {
      try
      {
        while (this.sData != null) {
          if (this.sDataObjectCount != this.sData.size())
          {
            Certificate localCertificate3 = getCertificate();
            localCertificate1 = localCertificate3;
            return localCertificate1;
            if (this.currentStream != paramInputStream)
            {
              this.currentStream = paramInputStream;
              this.sData = null;
              this.sDataObjectCount = 0;
            }
          }
          else
          {
            this.sData = null;
            this.sDataObjectCount = 0;
            return null;
          }
        }
      }
      catch (Exception localException)
      {
        throw new ExCertificateException(localException);
      }
      localPushbackInputStream = new PushbackInputStream(paramInputStream);
      i = localPushbackInputStream.read();
      Certificate localCertificate1 = null;
    } while (i == -1);
    localPushbackInputStream.unread(i);
    if (i != 48) {
      return readPEMCertificate(localPushbackInputStream);
    }
    Certificate localCertificate2 = readDERCertificate(new ASN1InputStream(localPushbackInputStream));
    return localCertificate2;
  }
  
  public Collection engineGenerateCertificates(InputStream paramInputStream)
    throws CertificateException
  {
    ArrayList localArrayList = new ArrayList();
    for (;;)
    {
      Certificate localCertificate = engineGenerateCertificate(paramInputStream);
      if (localCertificate == null) {
        break;
      }
      localArrayList.add(localCertificate);
    }
    return localArrayList;
  }
  
  public Iterator engineGetCertPathEncodings()
  {
    return null;
  }
  
  private class ExCertificateException
    extends CertificateException
  {
    private Throwable cause;
    
    public ExCertificateException(String paramString, Throwable paramThrowable)
    {
      super();
      this.cause = paramThrowable;
    }
    
    public ExCertificateException(Throwable paramThrowable)
    {
      this.cause = paramThrowable;
    }
    
    public Throwable getCause()
    {
      return this.cause;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jcajce.provider.asymmetric.x509.CertificateFactory
 * JD-Core Version:    0.7.0.1
 */