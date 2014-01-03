package org.spongycastle.cms;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.cert.CertStore;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1Set;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.BERSequence;
import org.spongycastle.asn1.DERSet;
import org.spongycastle.asn1.cms.ContentInfo;
import org.spongycastle.asn1.cms.SignedData;
import org.spongycastle.asn1.cms.SignerInfo;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.AttributeCertificate;
import org.spongycastle.asn1.x509.Certificate;
import org.spongycastle.asn1.x509.CertificateList;
import org.spongycastle.cert.X509AttributeCertificateHolder;
import org.spongycastle.cert.X509CRLHolder;
import org.spongycastle.cert.X509CertificateHolder;
import org.spongycastle.operator.DefaultSignatureAlgorithmIdentifierFinder;
import org.spongycastle.util.CollectionStore;
import org.spongycastle.util.Store;
import org.spongycastle.x509.NoSuchStoreException;
import org.spongycastle.x509.X509Store;

public class CMSSignedData
{
  private static final CMSSignedHelper HELPER = CMSSignedHelper.INSTANCE;
  X509Store attributeStore;
  X509Store certificateStore;
  ContentInfo contentInfo;
  X509Store crlStore;
  private Map hashes;
  CMSProcessable signedContent;
  SignedData signedData;
  SignerInformationStore signerInfoStore;
  
  public CMSSignedData(InputStream paramInputStream)
    throws CMSException
  {
    this(CMSUtils.readContentInfo(paramInputStream));
  }
  
  public CMSSignedData(Map paramMap, ContentInfo paramContentInfo)
    throws CMSException
  {
    this.hashes = paramMap;
    this.contentInfo = paramContentInfo;
    this.signedData = getSignedData();
  }
  
  public CMSSignedData(Map paramMap, byte[] paramArrayOfByte)
    throws CMSException
  {
    this(paramMap, CMSUtils.readContentInfo(paramArrayOfByte));
  }
  
  public CMSSignedData(ContentInfo paramContentInfo)
    throws CMSException
  {
    this.contentInfo = paramContentInfo;
    this.signedData = getSignedData();
    if (this.signedData.getEncapContentInfo().getContent() != null)
    {
      this.signedContent = new CMSProcessableByteArray(((ASN1OctetString)this.signedData.getEncapContentInfo().getContent()).getOctets());
      return;
    }
    this.signedContent = null;
  }
  
  public CMSSignedData(CMSProcessable paramCMSProcessable, InputStream paramInputStream)
    throws CMSException
  {
    this(paramCMSProcessable, CMSUtils.readContentInfo(new ASN1InputStream(paramInputStream)));
  }
  
  public CMSSignedData(CMSProcessable paramCMSProcessable, ContentInfo paramContentInfo)
    throws CMSException
  {
    this.signedContent = paramCMSProcessable;
    this.contentInfo = paramContentInfo;
    this.signedData = getSignedData();
  }
  
  public CMSSignedData(CMSProcessable paramCMSProcessable, byte[] paramArrayOfByte)
    throws CMSException
  {
    this(paramCMSProcessable, CMSUtils.readContentInfo(paramArrayOfByte));
  }
  
  private CMSSignedData(CMSSignedData paramCMSSignedData)
  {
    this.signedData = paramCMSSignedData.signedData;
    this.contentInfo = paramCMSSignedData.contentInfo;
    this.signedContent = paramCMSSignedData.signedContent;
    this.signerInfoStore = paramCMSSignedData.signerInfoStore;
  }
  
  public CMSSignedData(byte[] paramArrayOfByte)
    throws CMSException
  {
    this(CMSUtils.readContentInfo(paramArrayOfByte));
  }
  
  private SignedData getSignedData()
    throws CMSException
  {
    try
    {
      SignedData localSignedData = SignedData.getInstance(this.contentInfo.getContent());
      return localSignedData;
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
  
  /* Error */
  public static CMSSignedData replaceCertificatesAndCRLs(CMSSignedData paramCMSSignedData, CertStore paramCertStore)
    throws CMSException
  {
    // Byte code:
    //   0: new 2	org/spongycastle/cms/CMSSignedData
    //   3: dup
    //   4: aload_0
    //   5: invokespecial 117	org/spongycastle/cms/CMSSignedData:<init>	(Lorg/spongycastle/cms/CMSSignedData;)V
    //   8: astore_2
    //   9: aload_1
    //   10: invokestatic 121	org/spongycastle/cms/CMSUtils:getCertificatesFromStore	(Ljava/security/cert/CertStore;)Ljava/util/List;
    //   13: invokestatic 125	org/spongycastle/cms/CMSUtils:createBerSetFromList	(Ljava/util/List;)Lorg/spongycastle/asn1/ASN1Set;
    //   16: astore 4
    //   18: aload 4
    //   20: invokevirtual 131	org/spongycastle/asn1/ASN1Set:size	()I
    //   23: istore 5
    //   25: aconst_null
    //   26: astore 6
    //   28: iload 5
    //   30: ifeq +7 -> 37
    //   33: aload 4
    //   35: astore 6
    //   37: aload_1
    //   38: invokestatic 134	org/spongycastle/cms/CMSUtils:getCRLsFromStore	(Ljava/security/cert/CertStore;)Ljava/util/List;
    //   41: invokestatic 125	org/spongycastle/cms/CMSUtils:createBerSetFromList	(Ljava/util/List;)Lorg/spongycastle/asn1/ASN1Set;
    //   44: astore 8
    //   46: aload 8
    //   48: invokevirtual 131	org/spongycastle/asn1/ASN1Set:size	()I
    //   51: istore 9
    //   53: aconst_null
    //   54: astore 10
    //   56: iload 9
    //   58: ifeq +7 -> 65
    //   61: aload 8
    //   63: astore 10
    //   65: aload_2
    //   66: new 63	org/spongycastle/asn1/cms/SignedData
    //   69: dup
    //   70: aload_0
    //   71: getfield 55	org/spongycastle/cms/CMSSignedData:signedData	Lorg/spongycastle/asn1/cms/SignedData;
    //   74: invokevirtual 138	org/spongycastle/asn1/cms/SignedData:getDigestAlgorithms	()Lorg/spongycastle/asn1/ASN1Set;
    //   77: aload_0
    //   78: getfield 55	org/spongycastle/cms/CMSSignedData:signedData	Lorg/spongycastle/asn1/cms/SignedData;
    //   81: invokevirtual 67	org/spongycastle/asn1/cms/SignedData:getEncapContentInfo	()Lorg/spongycastle/asn1/cms/ContentInfo;
    //   84: aload 6
    //   86: aload 10
    //   88: aload_0
    //   89: getfield 55	org/spongycastle/cms/CMSSignedData:signedData	Lorg/spongycastle/asn1/cms/SignedData;
    //   92: invokevirtual 141	org/spongycastle/asn1/cms/SignedData:getSignerInfos	()Lorg/spongycastle/asn1/ASN1Set;
    //   95: invokespecial 144	org/spongycastle/asn1/cms/SignedData:<init>	(Lorg/spongycastle/asn1/ASN1Set;Lorg/spongycastle/asn1/cms/ContentInfo;Lorg/spongycastle/asn1/ASN1Set;Lorg/spongycastle/asn1/ASN1Set;Lorg/spongycastle/asn1/ASN1Set;)V
    //   98: putfield 55	org/spongycastle/cms/CMSSignedData:signedData	Lorg/spongycastle/asn1/cms/SignedData;
    //   101: aload_2
    //   102: new 69	org/spongycastle/asn1/cms/ContentInfo
    //   105: dup
    //   106: aload_2
    //   107: getfield 49	org/spongycastle/cms/CMSSignedData:contentInfo	Lorg/spongycastle/asn1/cms/ContentInfo;
    //   110: invokevirtual 148	org/spongycastle/asn1/cms/ContentInfo:getContentType	()Lorg/spongycastle/asn1/ASN1ObjectIdentifier;
    //   113: aload_2
    //   114: getfield 55	org/spongycastle/cms/CMSSignedData:signedData	Lorg/spongycastle/asn1/cms/SignedData;
    //   117: invokespecial 151	org/spongycastle/asn1/cms/ContentInfo:<init>	(Lorg/spongycastle/asn1/ASN1ObjectIdentifier;Lorg/spongycastle/asn1/ASN1Encodable;)V
    //   120: putfield 49	org/spongycastle/cms/CMSSignedData:contentInfo	Lorg/spongycastle/asn1/cms/ContentInfo;
    //   123: aload_2
    //   124: areturn
    //   125: astore_3
    //   126: new 33	org/spongycastle/cms/CMSException
    //   129: dup
    //   130: ldc 153
    //   132: aload_3
    //   133: invokespecial 111	org/spongycastle/cms/CMSException:<init>	(Ljava/lang/String;Ljava/lang/Exception;)V
    //   136: athrow
    //   137: astore 7
    //   139: new 33	org/spongycastle/cms/CMSException
    //   142: dup
    //   143: ldc 155
    //   145: aload 7
    //   147: invokespecial 111	org/spongycastle/cms/CMSException:<init>	(Ljava/lang/String;Ljava/lang/Exception;)V
    //   150: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	151	0	paramCMSSignedData	CMSSignedData
    //   0	151	1	paramCertStore	CertStore
    //   8	116	2	localCMSSignedData	CMSSignedData
    //   125	8	3	localCertStoreException1	java.security.cert.CertStoreException
    //   16	18	4	localASN1Set1	ASN1Set
    //   23	6	5	i	int
    //   26	59	6	localASN1Set2	ASN1Set
    //   137	9	7	localCertStoreException2	java.security.cert.CertStoreException
    //   44	18	8	localASN1Set3	ASN1Set
    //   51	6	9	j	int
    //   54	33	10	localASN1Set4	ASN1Set
    // Exception table:
    //   from	to	target	type
    //   9	25	125	java/security/cert/CertStoreException
    //   37	53	137	java/security/cert/CertStoreException
  }
  
  public static CMSSignedData replaceCertificatesAndCRLs(CMSSignedData paramCMSSignedData, Store paramStore1, Store paramStore2, Store paramStore3)
    throws CMSException
  {
    CMSSignedData localCMSSignedData = new CMSSignedData(paramCMSSignedData);
    Object localObject1;
    if (paramStore1 == null)
    {
      localObject1 = null;
      if (paramStore2 == null) {}
    }
    else
    {
      ArrayList localArrayList = new ArrayList();
      if (paramStore1 != null) {
        localArrayList.addAll(CMSUtils.getCertificatesFromStore(paramStore1));
      }
      if (paramStore2 != null) {
        localArrayList.addAll(CMSUtils.getAttributeCertificatesFromStore(paramStore2));
      }
      ASN1Set localASN1Set2 = CMSUtils.createBerSetFromList(localArrayList);
      int j = localASN1Set2.size();
      localObject1 = null;
      if (j != 0) {
        localObject1 = localASN1Set2;
      }
    }
    Object localObject2 = null;
    if (paramStore3 != null)
    {
      ASN1Set localASN1Set1 = CMSUtils.createBerSetFromList(CMSUtils.getCRLsFromStore(paramStore3));
      int i = localASN1Set1.size();
      localObject2 = null;
      if (i != 0) {
        localObject2 = localASN1Set1;
      }
    }
    localCMSSignedData.signedData = new SignedData(paramCMSSignedData.signedData.getDigestAlgorithms(), paramCMSSignedData.signedData.getEncapContentInfo(), localObject1, localObject2, paramCMSSignedData.signedData.getSignerInfos());
    localCMSSignedData.contentInfo = new ContentInfo(localCMSSignedData.contentInfo.getContentType(), localCMSSignedData.signedData);
    return localCMSSignedData;
  }
  
  public static CMSSignedData replaceSigners(CMSSignedData paramCMSSignedData, SignerInformationStore paramSignerInformationStore)
  {
    CMSSignedData localCMSSignedData = new CMSSignedData(paramCMSSignedData);
    localCMSSignedData.signerInfoStore = paramSignerInformationStore;
    ASN1EncodableVector localASN1EncodableVector1 = new ASN1EncodableVector();
    ASN1EncodableVector localASN1EncodableVector2 = new ASN1EncodableVector();
    Iterator localIterator = paramSignerInformationStore.getSigners().iterator();
    while (localIterator.hasNext())
    {
      SignerInformation localSignerInformation = (SignerInformation)localIterator.next();
      localASN1EncodableVector1.add(CMSSignedHelper.INSTANCE.fixAlgID(localSignerInformation.getDigestAlgorithmID()));
      localASN1EncodableVector2.add(localSignerInformation.toASN1Structure());
    }
    DERSet localDERSet1 = new DERSet(localASN1EncodableVector1);
    DERSet localDERSet2 = new DERSet(localASN1EncodableVector2);
    ASN1Sequence localASN1Sequence = (ASN1Sequence)paramCMSSignedData.signedData.toASN1Primitive();
    ASN1EncodableVector localASN1EncodableVector3 = new ASN1EncodableVector();
    localASN1EncodableVector3.add(localASN1Sequence.getObjectAt(0));
    localASN1EncodableVector3.add(localDERSet1);
    for (int i = 2; i != -1 + localASN1Sequence.size(); i++) {
      localASN1EncodableVector3.add(localASN1Sequence.getObjectAt(i));
    }
    localASN1EncodableVector3.add(localDERSet2);
    localCMSSignedData.signedData = SignedData.getInstance(new BERSequence(localASN1EncodableVector3));
    localCMSSignedData.contentInfo = new ContentInfo(localCMSSignedData.contentInfo.getContentType(), localCMSSignedData.signedData);
    return localCMSSignedData;
  }
  
  public Store getAttributeCertificates()
  {
    ASN1Set localASN1Set = this.signedData.getCertificates();
    if (localASN1Set != null)
    {
      ArrayList localArrayList = new ArrayList(localASN1Set.size());
      Enumeration localEnumeration = localASN1Set.getObjects();
      while (localEnumeration.hasMoreElements())
      {
        ASN1Primitive localASN1Primitive = ((ASN1Encodable)localEnumeration.nextElement()).toASN1Primitive();
        if ((localASN1Primitive instanceof ASN1TaggedObject)) {
          localArrayList.add(new X509AttributeCertificateHolder(AttributeCertificate.getInstance(((ASN1TaggedObject)localASN1Primitive).getObject())));
        }
      }
      return new CollectionStore(localArrayList);
    }
    return new CollectionStore(new ArrayList());
  }
  
  public X509Store getAttributeCertificates(String paramString1, String paramString2)
    throws NoSuchStoreException, NoSuchProviderException, CMSException
  {
    return getAttributeCertificates(paramString1, CMSUtils.getProvider(paramString2));
  }
  
  public X509Store getAttributeCertificates(String paramString, Provider paramProvider)
    throws NoSuchStoreException, CMSException
  {
    if (this.attributeStore == null) {
      this.attributeStore = HELPER.createAttributeStore(paramString, paramProvider, this.signedData.getCertificates());
    }
    return this.attributeStore;
  }
  
  public Store getCRLs()
  {
    ASN1Set localASN1Set = this.signedData.getCRLs();
    if (localASN1Set != null)
    {
      ArrayList localArrayList = new ArrayList(localASN1Set.size());
      Enumeration localEnumeration = localASN1Set.getObjects();
      while (localEnumeration.hasMoreElements())
      {
        ASN1Primitive localASN1Primitive = ((ASN1Encodable)localEnumeration.nextElement()).toASN1Primitive();
        if ((localASN1Primitive instanceof ASN1Sequence)) {
          localArrayList.add(new X509CRLHolder(CertificateList.getInstance(localASN1Primitive)));
        }
      }
      return new CollectionStore(localArrayList);
    }
    return new CollectionStore(new ArrayList());
  }
  
  public X509Store getCRLs(String paramString1, String paramString2)
    throws NoSuchStoreException, NoSuchProviderException, CMSException
  {
    return getCRLs(paramString1, CMSUtils.getProvider(paramString2));
  }
  
  public X509Store getCRLs(String paramString, Provider paramProvider)
    throws NoSuchStoreException, CMSException
  {
    if (this.crlStore == null) {
      this.crlStore = HELPER.createCRLsStore(paramString, paramProvider, this.signedData.getCRLs());
    }
    return this.crlStore;
  }
  
  public Store getCertificates()
  {
    ASN1Set localASN1Set = this.signedData.getCertificates();
    if (localASN1Set != null)
    {
      ArrayList localArrayList = new ArrayList(localASN1Set.size());
      Enumeration localEnumeration = localASN1Set.getObjects();
      while (localEnumeration.hasMoreElements())
      {
        ASN1Primitive localASN1Primitive = ((ASN1Encodable)localEnumeration.nextElement()).toASN1Primitive();
        if ((localASN1Primitive instanceof ASN1Sequence)) {
          localArrayList.add(new X509CertificateHolder(Certificate.getInstance(localASN1Primitive)));
        }
      }
      return new CollectionStore(localArrayList);
    }
    return new CollectionStore(new ArrayList());
  }
  
  public X509Store getCertificates(String paramString1, String paramString2)
    throws NoSuchStoreException, NoSuchProviderException, CMSException
  {
    return getCertificates(paramString1, CMSUtils.getProvider(paramString2));
  }
  
  public X509Store getCertificates(String paramString, Provider paramProvider)
    throws NoSuchStoreException, CMSException
  {
    if (this.certificateStore == null) {
      this.certificateStore = HELPER.createCertificateStore(paramString, paramProvider, this.signedData.getCertificates());
    }
    return this.certificateStore;
  }
  
  public CertStore getCertificatesAndCRLs(String paramString1, String paramString2)
    throws NoSuchAlgorithmException, NoSuchProviderException, CMSException
  {
    return getCertificatesAndCRLs(paramString1, CMSUtils.getProvider(paramString2));
  }
  
  public CertStore getCertificatesAndCRLs(String paramString, Provider paramProvider)
    throws NoSuchAlgorithmException, CMSException
  {
    ASN1Set localASN1Set1 = this.signedData.getCertificates();
    ASN1Set localASN1Set2 = this.signedData.getCRLs();
    return HELPER.createCertStore(paramString, paramProvider, localASN1Set1, localASN1Set2);
  }
  
  public ContentInfo getContentInfo()
  {
    return this.contentInfo;
  }
  
  public byte[] getEncoded()
    throws IOException
  {
    return this.contentInfo.getEncoded();
  }
  
  public CMSProcessable getSignedContent()
  {
    return this.signedContent;
  }
  
  public String getSignedContentTypeOID()
  {
    return this.signedData.getEncapContentInfo().getContentType().getId();
  }
  
  public SignerInformationStore getSignerInfos()
  {
    if (this.signerInfoStore == null)
    {
      ASN1Set localASN1Set = this.signedData.getSignerInfos();
      ArrayList localArrayList = new ArrayList();
      new DefaultSignatureAlgorithmIdentifierFinder();
      int i = 0;
      while (i != localASN1Set.size())
      {
        SignerInfo localSignerInfo = SignerInfo.getInstance(localASN1Set.getObjectAt(i));
        ASN1ObjectIdentifier localASN1ObjectIdentifier = this.signedData.getEncapContentInfo().getContentType();
        if (this.hashes == null)
        {
          localArrayList.add(new SignerInformation(localSignerInfo, localASN1ObjectIdentifier, this.signedContent, null));
          i++;
        }
        else
        {
          if ((this.hashes.keySet().iterator().next() instanceof String)) {}
          for (byte[] arrayOfByte = (byte[])this.hashes.get(localSignerInfo.getDigestAlgorithm().getAlgorithm().getId());; arrayOfByte = (byte[])this.hashes.get(localSignerInfo.getDigestAlgorithm().getAlgorithm()))
          {
            localArrayList.add(new SignerInformation(localSignerInfo, localASN1ObjectIdentifier, null, arrayOfByte));
            break;
          }
        }
      }
      this.signerInfoStore = new SignerInformationStore(localArrayList);
    }
    return this.signerInfoStore;
  }
  
  public int getVersion()
  {
    return this.signedData.getVersion().getValue().intValue();
  }
  
  public ContentInfo toASN1Structure()
  {
    return this.contentInfo;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.CMSSignedData
 * JD-Core Version:    0.7.0.1
 */