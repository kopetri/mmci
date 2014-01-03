package org.spongycastle.jce.provider;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.Certificate;
import java.security.cert.CertificateParsingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1Set;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERObjectIdentifier;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.pkcs.SignedData;
import org.spongycastle.asn1.x509.X509CertificateStructure;
import org.spongycastle.x509.X509StreamParserSpi;
import org.spongycastle.x509.util.StreamParsingException;

public class X509CertParser
  extends X509StreamParserSpi
{
  private static final PEMUtil PEM_PARSER = new PEMUtil("CERTIFICATE");
  private InputStream currentStream = null;
  private ASN1Set sData = null;
  private int sDataObjectCount = 0;
  
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
  
  private Certificate readDERCertificate(InputStream paramInputStream)
    throws IOException, CertificateParsingException
  {
    ASN1Sequence localASN1Sequence = (ASN1Sequence)new ASN1InputStream(paramInputStream).readObject();
    if ((localASN1Sequence.size() > 1) && ((localASN1Sequence.getObjectAt(0) instanceof DERObjectIdentifier)) && (localASN1Sequence.getObjectAt(0).equals(PKCSObjectIdentifiers.signedData)))
    {
      this.sData = new SignedData(ASN1Sequence.getInstance((ASN1TaggedObject)localASN1Sequence.getObjectAt(1), true)).getCertificates();
      return getCertificate();
    }
    return new X509CertificateObject(X509CertificateStructure.getInstance(localASN1Sequence));
  }
  
  private Certificate readPEMCertificate(InputStream paramInputStream)
    throws IOException, CertificateParsingException
  {
    ASN1Sequence localASN1Sequence = PEM_PARSER.readPEMObject(paramInputStream);
    if (localASN1Sequence != null) {
      return new X509CertificateObject(X509CertificateStructure.getInstance(localASN1Sequence));
    }
    return null;
  }
  
  public void engineInit(InputStream paramInputStream)
  {
    this.currentStream = paramInputStream;
    this.sData = null;
    this.sDataObjectCount = 0;
    if (!this.currentStream.markSupported()) {
      this.currentStream = new BufferedInputStream(this.currentStream);
    }
  }
  
  public Object engineRead()
    throws StreamParsingException
  {
    try
    {
      if (this.sData != null)
      {
        if (this.sDataObjectCount != this.sData.size()) {
          return getCertificate();
        }
        this.sData = null;
        this.sDataObjectCount = 0;
        return null;
      }
    }
    catch (Exception localException)
    {
      throw new StreamParsingException(localException.toString(), localException);
    }
    this.currentStream.mark(10);
    int i = this.currentStream.read();
    Object localObject = null;
    if (i != -1)
    {
      if (i != 48)
      {
        this.currentStream.reset();
        return readPEMCertificate(this.currentStream);
      }
      this.currentStream.reset();
      Certificate localCertificate = readDERCertificate(this.currentStream);
      localObject = localCertificate;
    }
    return localObject;
  }
  
  public Collection engineReadAll()
    throws StreamParsingException
  {
    ArrayList localArrayList = new ArrayList();
    for (;;)
    {
      Certificate localCertificate = (Certificate)engineRead();
      if (localCertificate == null) {
        break;
      }
      localArrayList.add(localCertificate);
    }
    return localArrayList;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jce.provider.X509CertParser
 * JD-Core Version:    0.7.0.1
 */