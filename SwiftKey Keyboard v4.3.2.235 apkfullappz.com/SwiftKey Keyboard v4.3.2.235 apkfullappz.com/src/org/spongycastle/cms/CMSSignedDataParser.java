package org.spongycastle.cms;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.cert.CertStore;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Generator;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetStringParser;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1SequenceParser;
import org.spongycastle.asn1.ASN1Set;
import org.spongycastle.asn1.ASN1SetParser;
import org.spongycastle.asn1.ASN1StreamParser;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.BERSequenceGenerator;
import org.spongycastle.asn1.BERSetParser;
import org.spongycastle.asn1.BERTaggedObject;
import org.spongycastle.asn1.DERSet;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.cms.CMSObjectIdentifiers;
import org.spongycastle.asn1.cms.ContentInfoParser;
import org.spongycastle.asn1.cms.SignedDataParser;
import org.spongycastle.asn1.cms.SignerInfo;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.AttributeCertificate;
import org.spongycastle.asn1.x509.Certificate;
import org.spongycastle.asn1.x509.CertificateList;
import org.spongycastle.cert.X509AttributeCertificateHolder;
import org.spongycastle.cert.X509CRLHolder;
import org.spongycastle.cert.X509CertificateHolder;
import org.spongycastle.operator.DigestCalculator;
import org.spongycastle.operator.DigestCalculatorProvider;
import org.spongycastle.operator.OperatorCreationException;
import org.spongycastle.operator.bc.BcDigestCalculatorProvider;
import org.spongycastle.util.CollectionStore;
import org.spongycastle.util.Store;
import org.spongycastle.util.io.Streams;
import org.spongycastle.x509.NoSuchStoreException;
import org.spongycastle.x509.X509Store;

public class CMSSignedDataParser
  extends CMSContentInfoParser
{
  private static final CMSSignedHelper HELPER = CMSSignedHelper.INSTANCE;
  private X509Store _attributeStore;
  private ASN1Set _certSet;
  private X509Store _certificateStore;
  private ASN1Set _crlSet;
  private X509Store _crlStore;
  private boolean _isCertCrlParsed;
  private CMSTypedStream _signedContent;
  private ASN1ObjectIdentifier _signedContentType;
  private SignedDataParser _signedData;
  private SignerInformationStore _signerInfoStore;
  private Map digests;
  
  public CMSSignedDataParser(InputStream paramInputStream)
    throws CMSException
  {
    this(createDefaultDigestProvider(), null, paramInputStream);
  }
  
  public CMSSignedDataParser(CMSTypedStream paramCMSTypedStream, InputStream paramInputStream)
    throws CMSException
  {
    this(createDefaultDigestProvider(), paramCMSTypedStream, paramInputStream);
  }
  
  public CMSSignedDataParser(CMSTypedStream paramCMSTypedStream, byte[] paramArrayOfByte)
    throws CMSException
  {
    this(createDefaultDigestProvider(), paramCMSTypedStream, new ByteArrayInputStream(paramArrayOfByte));
  }
  
  public CMSSignedDataParser(DigestCalculatorProvider paramDigestCalculatorProvider, InputStream paramInputStream)
    throws CMSException
  {
    this(paramDigestCalculatorProvider, null, paramInputStream);
  }
  
  public CMSSignedDataParser(DigestCalculatorProvider paramDigestCalculatorProvider, CMSTypedStream paramCMSTypedStream, InputStream paramInputStream)
    throws CMSException
  {
    super(paramInputStream);
    label244:
    for (;;)
    {
      try
      {
        this._signedContent = paramCMSTypedStream;
        this._signedData = SignedDataParser.getInstance(this._contentInfo.getContent(16));
        this.digests = new HashMap();
        ASN1SetParser localASN1SetParser = this._signedData.getDigestAlgorithms();
        ASN1Encodable localASN1Encodable = localASN1SetParser.readObject();
        if (localASN1Encodable != null)
        {
          AlgorithmIdentifier localAlgorithmIdentifier = AlgorithmIdentifier.getInstance(localASN1Encodable);
          try
          {
            DigestCalculator localDigestCalculator = paramDigestCalculatorProvider.get(localAlgorithmIdentifier);
            if (localDigestCalculator == null) {
              continue;
            }
            this.digests.put(localAlgorithmIdentifier.getAlgorithm(), localDigestCalculator);
          }
          catch (OperatorCreationException localOperatorCreationException) {}
        }
        else
        {
          ContentInfoParser localContentInfoParser = this._signedData.getEncapContentInfo();
          ASN1OctetStringParser localASN1OctetStringParser = (ASN1OctetStringParser)localContentInfoParser.getContent(4);
          CMSTypedStream localCMSTypedStream;
          if (localASN1OctetStringParser != null)
          {
            localCMSTypedStream = new CMSTypedStream(localContentInfoParser.getContentType().getId(), localASN1OctetStringParser.getOctetStream());
            if (this._signedContent == null) {
              this._signedContent = localCMSTypedStream;
            }
          }
          else
          {
            if (paramCMSTypedStream != null) {
              break label244;
            }
            this._signedContentType = localContentInfoParser.getContentType();
            if (!this.digests.isEmpty()) {
              break;
            }
            throw new CMSException("no digests could be created for message.");
          }
          localCMSTypedStream.drain();
          continue;
          this._signedContentType = this._signedContent.getContentType();
        }
      }
      catch (IOException localIOException)
      {
        throw new CMSException("io exception: " + localIOException.getMessage(), localIOException);
      }
    }
  }
  
  public CMSSignedDataParser(DigestCalculatorProvider paramDigestCalculatorProvider, CMSTypedStream paramCMSTypedStream, byte[] paramArrayOfByte)
    throws CMSException
  {
    this(paramDigestCalculatorProvider, paramCMSTypedStream, new ByteArrayInputStream(paramArrayOfByte));
  }
  
  public CMSSignedDataParser(DigestCalculatorProvider paramDigestCalculatorProvider, byte[] paramArrayOfByte)
    throws CMSException
  {
    this(paramDigestCalculatorProvider, new ByteArrayInputStream(paramArrayOfByte));
  }
  
  public CMSSignedDataParser(byte[] paramArrayOfByte)
    throws CMSException
  {
    this(createDefaultDigestProvider(), new ByteArrayInputStream(paramArrayOfByte));
  }
  
  private static DigestCalculatorProvider createDefaultDigestProvider()
    throws CMSException
  {
    return new BcDigestCalculatorProvider();
  }
  
  private static ASN1Set getASN1Set(ASN1SetParser paramASN1SetParser)
  {
    if (paramASN1SetParser == null) {
      return null;
    }
    return ASN1Set.getInstance(paramASN1SetParser.toASN1Primitive());
  }
  
  private static void pipeEncapsulatedOctetString(ContentInfoParser paramContentInfoParser, OutputStream paramOutputStream)
    throws IOException
  {
    ASN1OctetStringParser localASN1OctetStringParser = (ASN1OctetStringParser)paramContentInfoParser.getContent(4);
    if (localASN1OctetStringParser != null) {
      pipeOctetString(localASN1OctetStringParser, paramOutputStream);
    }
  }
  
  private static void pipeOctetString(ASN1OctetStringParser paramASN1OctetStringParser, OutputStream paramOutputStream)
    throws IOException
  {
    OutputStream localOutputStream = CMSUtils.createBEROctetOutputStream(paramOutputStream, 0, true, 0);
    Streams.pipeAll(paramASN1OctetStringParser.getOctetStream(), localOutputStream);
    localOutputStream.close();
  }
  
  private void populateCertCrlSets()
    throws CMSException
  {
    if (this._isCertCrlParsed) {
      return;
    }
    this._isCertCrlParsed = true;
    try
    {
      this._certSet = getASN1Set(this._signedData.getCertificates());
      this._crlSet = getASN1Set(this._signedData.getCrls());
      return;
    }
    catch (IOException localIOException)
    {
      throw new CMSException("problem parsing cert/crl sets", localIOException);
    }
  }
  
  /* Error */
  public static OutputStream replaceCertificatesAndCRLs(InputStream paramInputStream, CertStore paramCertStore, OutputStream paramOutputStream)
    throws CMSException, IOException
  {
    // Byte code:
    //   0: new 67	org/spongycastle/asn1/cms/ContentInfoParser
    //   3: dup
    //   4: new 237	org/spongycastle/asn1/ASN1StreamParser
    //   7: dup
    //   8: aload_0
    //   9: invokespecial 238	org/spongycastle/asn1/ASN1StreamParser:<init>	(Ljava/io/InputStream;)V
    //   12: invokevirtual 239	org/spongycastle/asn1/ASN1StreamParser:readObject	()Lorg/spongycastle/asn1/ASN1Encodable;
    //   15: checkcast 241	org/spongycastle/asn1/ASN1SequenceParser
    //   18: invokespecial 244	org/spongycastle/asn1/cms/ContentInfoParser:<init>	(Lorg/spongycastle/asn1/ASN1SequenceParser;)V
    //   21: bipush 16
    //   23: invokevirtual 71	org/spongycastle/asn1/cms/ContentInfoParser:getContent	(I)Lorg/spongycastle/asn1/ASN1Encodable;
    //   26: invokestatic 77	org/spongycastle/asn1/cms/SignedDataParser:getInstance	(Ljava/lang/Object;)Lorg/spongycastle/asn1/cms/SignedDataParser;
    //   29: astore_3
    //   30: new 246	org/spongycastle/asn1/BERSequenceGenerator
    //   33: dup
    //   34: aload_2
    //   35: invokespecial 249	org/spongycastle/asn1/BERSequenceGenerator:<init>	(Ljava/io/OutputStream;)V
    //   38: astore 4
    //   40: aload 4
    //   42: getstatic 254	org/spongycastle/asn1/cms/CMSObjectIdentifiers:signedData	Lorg/spongycastle/asn1/ASN1ObjectIdentifier;
    //   45: invokevirtual 258	org/spongycastle/asn1/BERSequenceGenerator:addObject	(Lorg/spongycastle/asn1/ASN1Encodable;)V
    //   48: new 246	org/spongycastle/asn1/BERSequenceGenerator
    //   51: dup
    //   52: aload 4
    //   54: invokevirtual 262	org/spongycastle/asn1/BERSequenceGenerator:getRawOutputStream	()Ljava/io/OutputStream;
    //   57: iconst_0
    //   58: iconst_1
    //   59: invokespecial 265	org/spongycastle/asn1/BERSequenceGenerator:<init>	(Ljava/io/OutputStream;IZ)V
    //   62: astore 5
    //   64: aload 5
    //   66: aload_3
    //   67: invokevirtual 269	org/spongycastle/asn1/cms/SignedDataParser:getVersion	()Lorg/spongycastle/asn1/ASN1Integer;
    //   70: invokevirtual 258	org/spongycastle/asn1/BERSequenceGenerator:addObject	(Lorg/spongycastle/asn1/ASN1Encodable;)V
    //   73: aload 5
    //   75: invokevirtual 262	org/spongycastle/asn1/BERSequenceGenerator:getRawOutputStream	()Ljava/io/OutputStream;
    //   78: aload_3
    //   79: invokevirtual 89	org/spongycastle/asn1/cms/SignedDataParser:getDigestAlgorithms	()Lorg/spongycastle/asn1/ASN1SetParser;
    //   82: invokeinterface 186 1 0
    //   87: invokevirtual 275	org/spongycastle/asn1/ASN1Primitive:getEncoded	()[B
    //   90: invokevirtual 278	java/io/OutputStream:write	([B)V
    //   93: aload_3
    //   94: invokevirtual 120	org/spongycastle/asn1/cms/SignedDataParser:getEncapContentInfo	()Lorg/spongycastle/asn1/cms/ContentInfoParser;
    //   97: astore 6
    //   99: new 246	org/spongycastle/asn1/BERSequenceGenerator
    //   102: dup
    //   103: aload 5
    //   105: invokevirtual 262	org/spongycastle/asn1/BERSequenceGenerator:getRawOutputStream	()Ljava/io/OutputStream;
    //   108: invokespecial 249	org/spongycastle/asn1/BERSequenceGenerator:<init>	(Ljava/io/OutputStream;)V
    //   111: astore 7
    //   113: aload 7
    //   115: aload 6
    //   117: invokevirtual 127	org/spongycastle/asn1/cms/ContentInfoParser:getContentType	()Lorg/spongycastle/asn1/ASN1ObjectIdentifier;
    //   120: invokevirtual 258	org/spongycastle/asn1/BERSequenceGenerator:addObject	(Lorg/spongycastle/asn1/ASN1Encodable;)V
    //   123: aload 6
    //   125: aload 7
    //   127: invokevirtual 262	org/spongycastle/asn1/BERSequenceGenerator:getRawOutputStream	()Ljava/io/OutputStream;
    //   130: invokestatic 280	org/spongycastle/cms/CMSSignedDataParser:pipeEncapsulatedOctetString	(Lorg/spongycastle/asn1/cms/ContentInfoParser;Ljava/io/OutputStream;)V
    //   133: aload 7
    //   135: invokevirtual 281	org/spongycastle/asn1/BERSequenceGenerator:close	()V
    //   138: aload_3
    //   139: invokevirtual 220	org/spongycastle/asn1/cms/SignedDataParser:getCertificates	()Lorg/spongycastle/asn1/ASN1SetParser;
    //   142: invokestatic 222	org/spongycastle/cms/CMSSignedDataParser:getASN1Set	(Lorg/spongycastle/asn1/ASN1SetParser;)Lorg/spongycastle/asn1/ASN1Set;
    //   145: pop
    //   146: aload_3
    //   147: invokevirtual 227	org/spongycastle/asn1/cms/SignedDataParser:getCrls	()Lorg/spongycastle/asn1/ASN1SetParser;
    //   150: invokestatic 222	org/spongycastle/cms/CMSSignedDataParser:getASN1Set	(Lorg/spongycastle/asn1/ASN1SetParser;)Lorg/spongycastle/asn1/ASN1Set;
    //   153: pop
    //   154: aload_1
    //   155: invokestatic 285	org/spongycastle/cms/CMSUtils:getCertificatesFromStore	(Ljava/security/cert/CertStore;)Ljava/util/List;
    //   158: invokestatic 289	org/spongycastle/cms/CMSUtils:createBerSetFromList	(Ljava/util/List;)Lorg/spongycastle/asn1/ASN1Set;
    //   161: astore 11
    //   163: aload 11
    //   165: invokevirtual 293	org/spongycastle/asn1/ASN1Set:size	()I
    //   168: ifle +25 -> 193
    //   171: aload 5
    //   173: invokevirtual 262	org/spongycastle/asn1/BERSequenceGenerator:getRawOutputStream	()Ljava/io/OutputStream;
    //   176: new 295	org/spongycastle/asn1/DERTaggedObject
    //   179: dup
    //   180: iconst_0
    //   181: iconst_0
    //   182: aload 11
    //   184: invokespecial 298	org/spongycastle/asn1/DERTaggedObject:<init>	(ZILorg/spongycastle/asn1/ASN1Encodable;)V
    //   187: invokevirtual 299	org/spongycastle/asn1/DERTaggedObject:getEncoded	()[B
    //   190: invokevirtual 278	java/io/OutputStream:write	([B)V
    //   193: aload_1
    //   194: invokestatic 302	org/spongycastle/cms/CMSUtils:getCRLsFromStore	(Ljava/security/cert/CertStore;)Ljava/util/List;
    //   197: invokestatic 289	org/spongycastle/cms/CMSUtils:createBerSetFromList	(Ljava/util/List;)Lorg/spongycastle/asn1/ASN1Set;
    //   200: astore 13
    //   202: aload 13
    //   204: invokevirtual 293	org/spongycastle/asn1/ASN1Set:size	()I
    //   207: ifle +25 -> 232
    //   210: aload 5
    //   212: invokevirtual 262	org/spongycastle/asn1/BERSequenceGenerator:getRawOutputStream	()Ljava/io/OutputStream;
    //   215: new 295	org/spongycastle/asn1/DERTaggedObject
    //   218: dup
    //   219: iconst_0
    //   220: iconst_1
    //   221: aload 13
    //   223: invokespecial 298	org/spongycastle/asn1/DERTaggedObject:<init>	(ZILorg/spongycastle/asn1/ASN1Encodable;)V
    //   226: invokevirtual 299	org/spongycastle/asn1/DERTaggedObject:getEncoded	()[B
    //   229: invokevirtual 278	java/io/OutputStream:write	([B)V
    //   232: aload 5
    //   234: invokevirtual 262	org/spongycastle/asn1/BERSequenceGenerator:getRawOutputStream	()Ljava/io/OutputStream;
    //   237: aload_3
    //   238: invokevirtual 305	org/spongycastle/asn1/cms/SignedDataParser:getSignerInfos	()Lorg/spongycastle/asn1/ASN1SetParser;
    //   241: invokeinterface 186 1 0
    //   246: invokevirtual 275	org/spongycastle/asn1/ASN1Primitive:getEncoded	()[B
    //   249: invokevirtual 278	java/io/OutputStream:write	([B)V
    //   252: aload 5
    //   254: invokevirtual 281	org/spongycastle/asn1/BERSequenceGenerator:close	()V
    //   257: aload 4
    //   259: invokevirtual 281	org/spongycastle/asn1/BERSequenceGenerator:close	()V
    //   262: aload_2
    //   263: areturn
    //   264: astore 10
    //   266: new 38	org/spongycastle/cms/CMSException
    //   269: dup
    //   270: ldc_w 307
    //   273: aload 10
    //   275: invokespecial 172	org/spongycastle/cms/CMSException:<init>	(Ljava/lang/String;Ljava/lang/Exception;)V
    //   278: athrow
    //   279: astore 12
    //   281: new 38	org/spongycastle/cms/CMSException
    //   284: dup
    //   285: ldc_w 309
    //   288: aload 12
    //   290: invokespecial 172	org/spongycastle/cms/CMSException:<init>	(Ljava/lang/String;Ljava/lang/Exception;)V
    //   293: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	294	0	paramInputStream	InputStream
    //   0	294	1	paramCertStore	CertStore
    //   0	294	2	paramOutputStream	OutputStream
    //   29	209	3	localSignedDataParser	SignedDataParser
    //   38	220	4	localBERSequenceGenerator1	BERSequenceGenerator
    //   62	191	5	localBERSequenceGenerator2	BERSequenceGenerator
    //   97	27	6	localContentInfoParser	ContentInfoParser
    //   111	23	7	localBERSequenceGenerator3	BERSequenceGenerator
    //   264	10	10	localCertStoreException1	java.security.cert.CertStoreException
    //   161	22	11	localASN1Set1	ASN1Set
    //   279	10	12	localCertStoreException2	java.security.cert.CertStoreException
    //   200	22	13	localASN1Set2	ASN1Set
    // Exception table:
    //   from	to	target	type
    //   154	163	264	java/security/cert/CertStoreException
    //   193	202	279	java/security/cert/CertStoreException
  }
  
  public static OutputStream replaceCertificatesAndCRLs(InputStream paramInputStream, Store paramStore1, Store paramStore2, Store paramStore3, OutputStream paramOutputStream)
    throws CMSException, IOException
  {
    SignedDataParser localSignedDataParser = SignedDataParser.getInstance(new ContentInfoParser((ASN1SequenceParser)new ASN1StreamParser(paramInputStream).readObject()).getContent(16));
    BERSequenceGenerator localBERSequenceGenerator1 = new BERSequenceGenerator(paramOutputStream);
    localBERSequenceGenerator1.addObject(CMSObjectIdentifiers.signedData);
    BERSequenceGenerator localBERSequenceGenerator2 = new BERSequenceGenerator(localBERSequenceGenerator1.getRawOutputStream(), 0, true);
    localBERSequenceGenerator2.addObject(localSignedDataParser.getVersion());
    localBERSequenceGenerator2.getRawOutputStream().write(localSignedDataParser.getDigestAlgorithms().toASN1Primitive().getEncoded());
    ContentInfoParser localContentInfoParser = localSignedDataParser.getEncapContentInfo();
    BERSequenceGenerator localBERSequenceGenerator3 = new BERSequenceGenerator(localBERSequenceGenerator2.getRawOutputStream());
    localBERSequenceGenerator3.addObject(localContentInfoParser.getContentType());
    pipeEncapsulatedOctetString(localContentInfoParser, localBERSequenceGenerator3.getRawOutputStream());
    localBERSequenceGenerator3.close();
    getASN1Set(localSignedDataParser.getCertificates());
    getASN1Set(localSignedDataParser.getCrls());
    if ((paramStore1 != null) || (paramStore3 != null))
    {
      ArrayList localArrayList = new ArrayList();
      if (paramStore1 != null) {
        localArrayList.addAll(CMSUtils.getCertificatesFromStore(paramStore1));
      }
      if (paramStore3 != null) {
        localArrayList.addAll(CMSUtils.getAttributeCertificatesFromStore(paramStore3));
      }
      ASN1Set localASN1Set1 = CMSUtils.createBerSetFromList(localArrayList);
      if (localASN1Set1.size() > 0) {
        localBERSequenceGenerator2.getRawOutputStream().write(new DERTaggedObject(false, 0, localASN1Set1).getEncoded());
      }
    }
    if (paramStore2 != null)
    {
      ASN1Set localASN1Set2 = CMSUtils.createBerSetFromList(CMSUtils.getCRLsFromStore(paramStore2));
      if (localASN1Set2.size() > 0) {
        localBERSequenceGenerator2.getRawOutputStream().write(new DERTaggedObject(false, 1, localASN1Set2).getEncoded());
      }
    }
    localBERSequenceGenerator2.getRawOutputStream().write(localSignedDataParser.getSignerInfos().toASN1Primitive().getEncoded());
    localBERSequenceGenerator2.close();
    localBERSequenceGenerator1.close();
    return paramOutputStream;
  }
  
  public static OutputStream replaceSigners(InputStream paramInputStream, SignerInformationStore paramSignerInformationStore, OutputStream paramOutputStream)
    throws CMSException, IOException
  {
    SignedDataParser localSignedDataParser = SignedDataParser.getInstance(new ContentInfoParser((ASN1SequenceParser)new ASN1StreamParser(paramInputStream).readObject()).getContent(16));
    BERSequenceGenerator localBERSequenceGenerator1 = new BERSequenceGenerator(paramOutputStream);
    localBERSequenceGenerator1.addObject(CMSObjectIdentifiers.signedData);
    BERSequenceGenerator localBERSequenceGenerator2 = new BERSequenceGenerator(localBERSequenceGenerator1.getRawOutputStream(), 0, true);
    localBERSequenceGenerator2.addObject(localSignedDataParser.getVersion());
    localSignedDataParser.getDigestAlgorithms().toASN1Primitive();
    ASN1EncodableVector localASN1EncodableVector1 = new ASN1EncodableVector();
    Iterator localIterator1 = paramSignerInformationStore.getSigners().iterator();
    while (localIterator1.hasNext())
    {
      SignerInformation localSignerInformation = (SignerInformation)localIterator1.next();
      localASN1EncodableVector1.add(CMSSignedHelper.INSTANCE.fixAlgID(localSignerInformation.getDigestAlgorithmID()));
    }
    localBERSequenceGenerator2.getRawOutputStream().write(new DERSet(localASN1EncodableVector1).getEncoded());
    ContentInfoParser localContentInfoParser = localSignedDataParser.getEncapContentInfo();
    BERSequenceGenerator localBERSequenceGenerator3 = new BERSequenceGenerator(localBERSequenceGenerator2.getRawOutputStream());
    localBERSequenceGenerator3.addObject(localContentInfoParser.getContentType());
    pipeEncapsulatedOctetString(localContentInfoParser, localBERSequenceGenerator3.getRawOutputStream());
    localBERSequenceGenerator3.close();
    writeSetToGeneratorTagged(localBERSequenceGenerator2, localSignedDataParser.getCertificates(), 0);
    writeSetToGeneratorTagged(localBERSequenceGenerator2, localSignedDataParser.getCrls(), 1);
    ASN1EncodableVector localASN1EncodableVector2 = new ASN1EncodableVector();
    Iterator localIterator2 = paramSignerInformationStore.getSigners().iterator();
    while (localIterator2.hasNext()) {
      localASN1EncodableVector2.add(((SignerInformation)localIterator2.next()).toASN1Structure());
    }
    localBERSequenceGenerator2.getRawOutputStream().write(new DERSet(localASN1EncodableVector2).getEncoded());
    localBERSequenceGenerator2.close();
    localBERSequenceGenerator1.close();
    return paramOutputStream;
  }
  
  private static void writeSetToGeneratorTagged(ASN1Generator paramASN1Generator, ASN1SetParser paramASN1SetParser, int paramInt)
    throws IOException
  {
    ASN1Set localASN1Set = getASN1Set(paramASN1SetParser);
    if (localASN1Set != null)
    {
      if ((paramASN1SetParser instanceof BERSetParser)) {
        paramASN1Generator.getRawOutputStream().write(new BERTaggedObject(false, paramInt, localASN1Set).getEncoded());
      }
    }
    else {
      return;
    }
    paramASN1Generator.getRawOutputStream().write(new DERTaggedObject(false, paramInt, localASN1Set).getEncoded());
  }
  
  public Store getAttributeCertificates()
    throws CMSException
  {
    populateCertCrlSets();
    ASN1Set localASN1Set = this._certSet;
    if (localASN1Set != null)
    {
      ArrayList localArrayList = new ArrayList(localASN1Set.size());
      Enumeration localEnumeration = localASN1Set.getObjects();
      while (localEnumeration.hasMoreElements())
      {
        ASN1Primitive localASN1Primitive = ((ASN1Encodable)localEnumeration.nextElement()).toASN1Primitive();
        if ((localASN1Primitive instanceof ASN1TaggedObject))
        {
          ASN1TaggedObject localASN1TaggedObject = (ASN1TaggedObject)localASN1Primitive;
          if (localASN1TaggedObject.getTagNo() == 2) {
            localArrayList.add(new X509AttributeCertificateHolder(AttributeCertificate.getInstance(ASN1Sequence.getInstance(localASN1TaggedObject, false))));
          }
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
    if (this._attributeStore == null)
    {
      populateCertCrlSets();
      this._attributeStore = HELPER.createAttributeStore(paramString, paramProvider, this._certSet);
    }
    return this._attributeStore;
  }
  
  public Store getCRLs()
    throws CMSException
  {
    populateCertCrlSets();
    ASN1Set localASN1Set = this._crlSet;
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
    if (this._crlStore == null)
    {
      populateCertCrlSets();
      this._crlStore = HELPER.createCRLsStore(paramString, paramProvider, this._crlSet);
    }
    return this._crlStore;
  }
  
  public Store getCertificates()
    throws CMSException
  {
    populateCertCrlSets();
    ASN1Set localASN1Set = this._certSet;
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
    if (this._certificateStore == null)
    {
      populateCertCrlSets();
      this._certificateStore = HELPER.createCertificateStore(paramString, paramProvider, this._certSet);
    }
    return this._certificateStore;
  }
  
  public CertStore getCertificatesAndCRLs(String paramString1, String paramString2)
    throws NoSuchAlgorithmException, NoSuchProviderException, CMSException
  {
    return getCertificatesAndCRLs(paramString1, CMSUtils.getProvider(paramString2));
  }
  
  public CertStore getCertificatesAndCRLs(String paramString, Provider paramProvider)
    throws NoSuchAlgorithmException, NoSuchProviderException, CMSException
  {
    populateCertCrlSets();
    return HELPER.createCertStore(paramString, paramProvider, this._certSet, this._crlSet);
  }
  
  public CMSTypedStream getSignedContent()
  {
    if (this._signedContent == null) {
      return null;
    }
    InputStream localInputStream = CMSUtils.attachDigestsToInputStream(this.digests.values(), this._signedContent.getContentStream());
    return new CMSTypedStream(this._signedContent.getContentType(), localInputStream);
  }
  
  public String getSignedContentTypeOID()
  {
    return this._signedContentType.getId();
  }
  
  public SignerInformationStore getSignerInfos()
    throws CMSException
  {
    ArrayList localArrayList;
    if (this._signerInfoStore == null)
    {
      populateCertCrlSets();
      localArrayList = new ArrayList();
      HashMap localHashMap = new HashMap();
      Iterator localIterator = this.digests.keySet().iterator();
      while (localIterator.hasNext())
      {
        Object localObject = localIterator.next();
        localHashMap.put(localObject, ((DigestCalculator)this.digests.get(localObject)).getDigest());
      }
      try
      {
        ASN1SetParser localASN1SetParser = this._signedData.getSignerInfos();
        for (;;)
        {
          ASN1Encodable localASN1Encodable = localASN1SetParser.readObject();
          if (localASN1Encodable == null) {
            break;
          }
          SignerInfo localSignerInfo = SignerInfo.getInstance(localASN1Encodable.toASN1Primitive());
          byte[] arrayOfByte = (byte[])localHashMap.get(localSignerInfo.getDigestAlgorithm().getAlgorithm());
          localArrayList.add(new SignerInformation(localSignerInfo, this._signedContentType, null, arrayOfByte));
        }
        this._signerInfoStore = new SignerInformationStore(localArrayList);
      }
      catch (IOException localIOException)
      {
        throw new CMSException("io exception: " + localIOException.getMessage(), localIOException);
      }
    }
    return this._signerInfoStore;
  }
  
  public int getVersion()
  {
    return this._signedData.getVersion().getValue().intValue();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.CMSSignedDataParser
 * JD-Core Version:    0.7.0.1
 */