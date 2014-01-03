package org.spongycastle.jcajce.provider.asymmetric.x509;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.security.NoSuchProviderException;
import java.security.cert.CertPath;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import javax.security.auth.x500.X500Principal;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERSet;
import org.spongycastle.asn1.pkcs.ContentInfo;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.pkcs.SignedData;
import org.spongycastle.jce.provider.BouncyCastleProvider;
import org.spongycastle.util.io.pem.PemObject;
import org.spongycastle.util.io.pem.PemWriter;

public class PKIXCertPath
  extends CertPath
{
  static final List certPathEncodings;
  private List certificates;
  
  static
  {
    ArrayList localArrayList = new ArrayList();
    localArrayList.add("PkiPath");
    localArrayList.add("PEM");
    localArrayList.add("PKCS7");
    certPathEncodings = Collections.unmodifiableList(localArrayList);
  }
  
  PKIXCertPath(InputStream paramInputStream, String paramString)
    throws CertificateException
  {
    super("X.509");
    try
    {
      if (paramString.equalsIgnoreCase("PkiPath"))
      {
        localASN1Primitive = new ASN1InputStream(paramInputStream).readObject();
        if (!(localASN1Primitive instanceof ASN1Sequence)) {
          throw new CertificateException("input stream does not contain a ASN1 SEQUENCE while reading PkiPath encoded data to load CertPath");
        }
      }
    }
    catch (IOException localIOException1)
    {
      ASN1Primitive localASN1Primitive;
      throw new CertificateException("IOException throw while decoding CertPath:\n" + localIOException1.toString());
      Enumeration localEnumeration = ((ASN1Sequence)localASN1Primitive).getObjects();
      this.certificates = new ArrayList();
      CertificateFactory localCertificateFactory2 = CertificateFactory.getInstance("X.509", BouncyCastleProvider.PROVIDER_NAME);
      while (localEnumeration.hasMoreElements())
      {
        byte[] arrayOfByte = ((ASN1Encodable)localEnumeration.nextElement()).toASN1Primitive().getEncoded("DER");
        this.certificates.add(0, localCertificateFactory2.generateCertificate(new ByteArrayInputStream(arrayOfByte)));
      }
    }
    catch (NoSuchProviderException localNoSuchProviderException1) {}
    for (;;)
    {
      for (;;)
      {
        throw new CertificateException("BouncyCastle provider not found while trying to get a CertificateFactory:\n" + localNoSuchProviderException1.toString());
        BufferedInputStream localBufferedInputStream;
        if ((paramString.equalsIgnoreCase("PKCS7")) || (paramString.equalsIgnoreCase("PEM"))) {
          localBufferedInputStream = new BufferedInputStream(paramInputStream);
        }
        try
        {
          this.certificates = new ArrayList();
          CertificateFactory localCertificateFactory1 = CertificateFactory.getInstance("X.509", BouncyCastleProvider.PROVIDER_NAME);
          for (;;)
          {
            Certificate localCertificate = localCertificateFactory1.generateCertificate(localBufferedInputStream);
            if (localCertificate == null) {
              break;
            }
            this.certificates.add(localCertificate);
          }
        }
        catch (IOException localIOException2)
        {
          break;
          this.certificates = sortCerts(this.certificates);
          return;
          throw new CertificateException("unsupported encoding: " + paramString);
        }
        catch (NoSuchProviderException localNoSuchProviderException2) {}
      }
    }
  }
  
  PKIXCertPath(List paramList)
  {
    super("X.509");
    this.certificates = sortCerts(new ArrayList(paramList));
  }
  
  private List sortCerts(List paramList)
  {
    if (paramList.size() < 2) {}
    int i;
    do
    {
      return paramList;
      X500Principal localX500Principal1 = ((X509Certificate)paramList.get(0)).getIssuerX500Principal();
      i = 1;
      int j = 1;
      while (j != paramList.size()) {
        if (localX500Principal1.equals(((X509Certificate)paramList.get(j)).getSubjectX500Principal()))
        {
          localX500Principal1 = ((X509Certificate)paramList.get(j)).getIssuerX500Principal();
          j++;
        }
        else
        {
          i = 0;
        }
      }
    } while (i != 0);
    ArrayList localArrayList1 = new ArrayList(paramList.size());
    ArrayList localArrayList2 = new ArrayList(paramList);
    int k = 0;
    if (k < paramList.size())
    {
      X509Certificate localX509Certificate2 = (X509Certificate)paramList.get(k);
      X500Principal localX500Principal3 = localX509Certificate2.getSubjectX500Principal();
      for (int i1 = 0;; i1++)
      {
        int i2 = paramList.size();
        int i3 = 0;
        if (i1 != i2)
        {
          if (((X509Certificate)paramList.get(i1)).getIssuerX500Principal().equals(localX500Principal3)) {
            i3 = 1;
          }
        }
        else
        {
          if (i3 == 0)
          {
            localArrayList1.add(localX509Certificate2);
            paramList.remove(k);
          }
          k++;
          break;
        }
      }
    }
    if (localArrayList1.size() > 1) {
      return localArrayList2;
    }
    int m = 0;
    if (m != localArrayList1.size())
    {
      X500Principal localX500Principal2 = ((X509Certificate)localArrayList1.get(m)).getIssuerX500Principal();
      for (int n = 0;; n++) {
        if (n < paramList.size())
        {
          X509Certificate localX509Certificate1 = (X509Certificate)paramList.get(n);
          if (localX500Principal2.equals(localX509Certificate1.getSubjectX500Principal()))
          {
            localArrayList1.add(localX509Certificate1);
            paramList.remove(n);
          }
        }
        else
        {
          m++;
          break;
        }
      }
    }
    if (paramList.size() > 0) {
      return localArrayList2;
    }
    return localArrayList1;
  }
  
  private ASN1Primitive toASN1Object(X509Certificate paramX509Certificate)
    throws CertificateEncodingException
  {
    try
    {
      ASN1Primitive localASN1Primitive = new ASN1InputStream(paramX509Certificate.getEncoded()).readObject();
      return localASN1Primitive;
    }
    catch (Exception localException)
    {
      throw new CertificateEncodingException("Exception while encoding certificate: " + localException.toString());
    }
  }
  
  private byte[] toDEREncoded(ASN1Encodable paramASN1Encodable)
    throws CertificateEncodingException
  {
    try
    {
      byte[] arrayOfByte = paramASN1Encodable.toASN1Primitive().getEncoded("DER");
      return arrayOfByte;
    }
    catch (IOException localIOException)
    {
      throw new CertificateEncodingException("Exception thrown: " + localIOException);
    }
  }
  
  public List getCertificates()
  {
    return Collections.unmodifiableList(new ArrayList(this.certificates));
  }
  
  public byte[] getEncoded()
    throws CertificateEncodingException
  {
    Iterator localIterator = getEncodings();
    if (localIterator.hasNext())
    {
      Object localObject = localIterator.next();
      if ((localObject instanceof String)) {
        return getEncoded((String)localObject);
      }
    }
    return null;
  }
  
  public byte[] getEncoded(String paramString)
    throws CertificateEncodingException
  {
    if (paramString.equalsIgnoreCase("PkiPath"))
    {
      ASN1EncodableVector localASN1EncodableVector1 = new ASN1EncodableVector();
      ListIterator localListIterator = this.certificates.listIterator(this.certificates.size());
      while (localListIterator.hasPrevious()) {
        localASN1EncodableVector1.add(toASN1Object((X509Certificate)localListIterator.previous()));
      }
      return toDEREncoded(new DERSequence(localASN1EncodableVector1));
    }
    if (paramString.equalsIgnoreCase("PKCS7"))
    {
      ContentInfo localContentInfo = new ContentInfo(PKCSObjectIdentifiers.data, null);
      ASN1EncodableVector localASN1EncodableVector2 = new ASN1EncodableVector();
      for (int i = 0; i != this.certificates.size(); i++) {
        localASN1EncodableVector2.add(toASN1Object((X509Certificate)this.certificates.get(i)));
      }
      SignedData localSignedData = new SignedData(new ASN1Integer(1), new DERSet(), localContentInfo, new DERSet(localASN1EncodableVector2), null, new DERSet());
      return toDEREncoded(new ContentInfo(PKCSObjectIdentifiers.signedData, localSignedData));
    }
    if (paramString.equalsIgnoreCase("PEM"))
    {
      ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
      PemWriter localPemWriter = new PemWriter(new OutputStreamWriter(localByteArrayOutputStream));
      int j = 0;
      try
      {
        while (j != this.certificates.size())
        {
          localPemWriter.writeObject(new PemObject("CERTIFICATE", ((X509Certificate)this.certificates.get(j)).getEncoded()));
          j++;
        }
        localPemWriter.close();
        return localByteArrayOutputStream.toByteArray();
      }
      catch (Exception localException)
      {
        throw new CertificateEncodingException("can't encode certificate for PEM encoded path");
      }
    }
    throw new CertificateEncodingException("unsupported encoding: " + paramString);
  }
  
  public Iterator getEncodings()
  {
    return certPathEncodings.iterator();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jcajce.provider.asymmetric.x509.PKIXCertPath
 * JD-Core Version:    0.7.0.1
 */