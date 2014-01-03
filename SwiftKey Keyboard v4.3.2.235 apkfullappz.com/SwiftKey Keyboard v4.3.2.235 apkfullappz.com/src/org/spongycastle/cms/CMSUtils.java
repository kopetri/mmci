package org.spongycastle.cms;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.Security;
import java.security.cert.CRLException;
import java.security.cert.CertStore;
import java.security.cert.CertStoreException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Set;
import org.spongycastle.asn1.BEROctetStringGenerator;
import org.spongycastle.asn1.BERSet;
import org.spongycastle.asn1.DERSet;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.cms.ContentInfo;
import org.spongycastle.asn1.cms.IssuerAndSerialNumber;
import org.spongycastle.asn1.x509.CertificateList;
import org.spongycastle.asn1.x509.TBSCertificateStructure;
import org.spongycastle.asn1.x509.X509CertificateStructure;
import org.spongycastle.cert.X509AttributeCertificateHolder;
import org.spongycastle.cert.X509CRLHolder;
import org.spongycastle.cert.X509CertificateHolder;
import org.spongycastle.operator.DigestCalculator;
import org.spongycastle.util.Store;
import org.spongycastle.util.io.Streams;
import org.spongycastle.util.io.TeeInputStream;
import org.spongycastle.util.io.TeeOutputStream;

class CMSUtils
{
  static InputStream attachDigestsToInputStream(Collection paramCollection, InputStream paramInputStream)
  {
    Object localObject = paramInputStream;
    Iterator localIterator = paramCollection.iterator();
    while (localIterator.hasNext()) {
      localObject = new TeeInputStream((InputStream)localObject, ((DigestCalculator)localIterator.next()).getOutputStream());
    }
    return localObject;
  }
  
  static OutputStream attachSignersToOutputStream(Collection paramCollection, OutputStream paramOutputStream)
  {
    OutputStream localOutputStream = paramOutputStream;
    Iterator localIterator = paramCollection.iterator();
    while (localIterator.hasNext()) {
      localOutputStream = getSafeTeeOutputStream(localOutputStream, ((SignerInfoGenerator)localIterator.next()).getCalculatingOutputStream());
    }
    return localOutputStream;
  }
  
  static OutputStream createBEROctetOutputStream(OutputStream paramOutputStream, int paramInt1, boolean paramBoolean, int paramInt2)
    throws IOException
  {
    BEROctetStringGenerator localBEROctetStringGenerator = new BEROctetStringGenerator(paramOutputStream, paramInt1, paramBoolean);
    if (paramInt2 != 0) {
      return localBEROctetStringGenerator.getOctetOutputStream(new byte[paramInt2]);
    }
    return localBEROctetStringGenerator.getOctetOutputStream();
  }
  
  static ASN1Set createBerSetFromList(List paramList)
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext()) {
      localASN1EncodableVector.add((ASN1Encodable)localIterator.next());
    }
    return new BERSet(localASN1EncodableVector);
  }
  
  static ASN1Set createDerSetFromList(List paramList)
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext()) {
      localASN1EncodableVector.add((ASN1Encodable)localIterator.next());
    }
    return new DERSet(localASN1EncodableVector);
  }
  
  static List getAttributeCertificatesFromStore(Store paramStore)
    throws CMSException
  {
    localArrayList = new ArrayList();
    try
    {
      Iterator localIterator = paramStore.getMatches(null).iterator();
      while (localIterator.hasNext()) {
        localArrayList.add(new DERTaggedObject(false, 2, ((X509AttributeCertificateHolder)localIterator.next()).toASN1Structure()));
      }
      return localArrayList;
    }
    catch (ClassCastException localClassCastException)
    {
      throw new CMSException("error processing certs", localClassCastException);
    }
  }
  
  static List getCRLsFromStore(CertStore paramCertStore)
    throws CertStoreException, CMSException
  {
    localArrayList = new ArrayList();
    try
    {
      Iterator localIterator = paramCertStore.getCRLs(null).iterator();
      while (localIterator.hasNext()) {
        localArrayList.add(CertificateList.getInstance(ASN1Primitive.fromByteArray(((X509CRL)localIterator.next()).getEncoded())));
      }
      return localArrayList;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      throw new CMSException("error processing crls", localIllegalArgumentException);
    }
    catch (IOException localIOException)
    {
      throw new CMSException("error processing crls", localIOException);
    }
    catch (CRLException localCRLException)
    {
      throw new CMSException("error encoding crls", localCRLException);
    }
  }
  
  static List getCRLsFromStore(Store paramStore)
    throws CMSException
  {
    localArrayList = new ArrayList();
    try
    {
      Iterator localIterator = paramStore.getMatches(null).iterator();
      while (localIterator.hasNext()) {
        localArrayList.add(((X509CRLHolder)localIterator.next()).toASN1Structure());
      }
      return localArrayList;
    }
    catch (ClassCastException localClassCastException)
    {
      throw new CMSException("error processing certs", localClassCastException);
    }
  }
  
  static List getCertificatesFromStore(CertStore paramCertStore)
    throws CertStoreException, CMSException
  {
    localArrayList = new ArrayList();
    try
    {
      Iterator localIterator = paramCertStore.getCertificates(null).iterator();
      while (localIterator.hasNext()) {
        localArrayList.add(X509CertificateStructure.getInstance(ASN1Primitive.fromByteArray(((X509Certificate)localIterator.next()).getEncoded())));
      }
      return localArrayList;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      throw new CMSException("error processing certs", localIllegalArgumentException);
    }
    catch (IOException localIOException)
    {
      throw new CMSException("error processing certs", localIOException);
    }
    catch (CertificateEncodingException localCertificateEncodingException)
    {
      throw new CMSException("error encoding certs", localCertificateEncodingException);
    }
  }
  
  static List getCertificatesFromStore(Store paramStore)
    throws CMSException
  {
    localArrayList = new ArrayList();
    try
    {
      Iterator localIterator = paramStore.getMatches(null).iterator();
      while (localIterator.hasNext()) {
        localArrayList.add(((X509CertificateHolder)localIterator.next()).toASN1Structure());
      }
      return localArrayList;
    }
    catch (ClassCastException localClassCastException)
    {
      throw new CMSException("error processing certs", localClassCastException);
    }
  }
  
  static IssuerAndSerialNumber getIssuerAndSerialNumber(X509Certificate paramX509Certificate)
  {
    TBSCertificateStructure localTBSCertificateStructure = getTBSCertificateStructure(paramX509Certificate);
    return new IssuerAndSerialNumber(localTBSCertificateStructure.getIssuer(), localTBSCertificateStructure.getSerialNumber().getValue());
  }
  
  public static Provider getProvider(String paramString)
    throws NoSuchProviderException
  {
    if (paramString != null)
    {
      Provider localProvider = Security.getProvider(paramString);
      if (localProvider != null) {
        return localProvider;
      }
      throw new NoSuchProviderException("provider " + paramString + " not found.");
    }
    return null;
  }
  
  static OutputStream getSafeOutputStream(OutputStream paramOutputStream)
  {
    if (paramOutputStream == null) {
      paramOutputStream = new NullOutputStream();
    }
    return paramOutputStream;
  }
  
  static OutputStream getSafeTeeOutputStream(OutputStream paramOutputStream1, OutputStream paramOutputStream2)
  {
    if (paramOutputStream1 == null) {
      return getSafeOutputStream(paramOutputStream2);
    }
    if (paramOutputStream2 == null) {
      return getSafeOutputStream(paramOutputStream1);
    }
    return new TeeOutputStream(paramOutputStream1, paramOutputStream2);
  }
  
  static TBSCertificateStructure getTBSCertificateStructure(X509Certificate paramX509Certificate)
  {
    try
    {
      TBSCertificateStructure localTBSCertificateStructure = TBSCertificateStructure.getInstance(ASN1Primitive.fromByteArray(paramX509Certificate.getTBSCertificate()));
      return localTBSCertificateStructure;
    }
    catch (Exception localException)
    {
      throw new IllegalArgumentException("can't extract TBS structure from this cert");
    }
  }
  
  static ContentInfo readContentInfo(InputStream paramInputStream)
    throws CMSException
  {
    return readContentInfo(new ASN1InputStream(paramInputStream));
  }
  
  private static ContentInfo readContentInfo(ASN1InputStream paramASN1InputStream)
    throws CMSException
  {
    try
    {
      ContentInfo localContentInfo = ContentInfo.getInstance(paramASN1InputStream.readObject());
      return localContentInfo;
    }
    catch (IOException localIOException)
    {
      throw new CMSException("IOException reading content.", localIOException);
    }
    catch (ClassCastException localClassCastException)
    {
      throw new CMSException("Malformed content.", localClassCastException);
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      throw new CMSException("Malformed content.", localIllegalArgumentException);
    }
  }
  
  static ContentInfo readContentInfo(byte[] paramArrayOfByte)
    throws CMSException
  {
    return readContentInfo(new ASN1InputStream(paramArrayOfByte));
  }
  
  public static byte[] streamToByteArray(InputStream paramInputStream)
    throws IOException
  {
    return Streams.readAll(paramInputStream);
  }
  
  public static byte[] streamToByteArray(InputStream paramInputStream, int paramInt)
    throws IOException
  {
    return Streams.readAllLimited(paramInputStream, paramInt);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.CMSUtils
 * JD-Core Version:    0.7.0.1
 */