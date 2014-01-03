package org.spongycastle.tsp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.cert.CRLException;
import java.security.cert.CertStore;
import java.security.cert.CertStoreException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.DERSet;
import org.spongycastle.asn1.cms.Attribute;
import org.spongycastle.asn1.cms.AttributeTable;
import org.spongycastle.asn1.ess.ESSCertID;
import org.spongycastle.asn1.ess.SigningCertificate;
import org.spongycastle.asn1.oiw.OIWObjectIdentifiers;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.GeneralName;
import org.spongycastle.cert.X509CertificateHolder;
import org.spongycastle.cert.jcajce.JcaX509CRLHolder;
import org.spongycastle.cert.jcajce.JcaX509CertificateHolder;
import org.spongycastle.cms.CMSAttributeTableGenerationException;
import org.spongycastle.cms.CMSAttributeTableGenerator;
import org.spongycastle.cms.CMSSignedGenerator;
import org.spongycastle.cms.DefaultSignedAttributeTableGenerator;
import org.spongycastle.cms.SignerInfoGenerator;
import org.spongycastle.cms.SimpleAttributeTableGenerator;
import org.spongycastle.cms.jcajce.JcaSignerInfoGeneratorBuilder;
import org.spongycastle.jce.interfaces.GOST3410PrivateKey;
import org.spongycastle.operator.DigestCalculator;
import org.spongycastle.operator.OperatorCreationException;
import org.spongycastle.operator.jcajce.JcaContentSignerBuilder;
import org.spongycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;
import org.spongycastle.util.Store;

public class TimeStampTokenGenerator
{
  int accuracyMicros = -1;
  int accuracyMillis = -1;
  int accuracySeconds = -1;
  private List attrCerts = new ArrayList();
  X509Certificate cert;
  private List certs = new ArrayList();
  private List crls = new ArrayList();
  String digestOID;
  PrivateKey key;
  boolean ordering = false;
  AttributeTable signedAttr;
  private SignerInfoGenerator signerInfoGen;
  GeneralName tsa = null;
  private ASN1ObjectIdentifier tsaPolicyOID;
  AttributeTable unsignedAttr;
  
  public TimeStampTokenGenerator(PrivateKey paramPrivateKey, X509Certificate paramX509Certificate, String paramString1, String paramString2)
    throws IllegalArgumentException, TSPException
  {
    this(paramPrivateKey, paramX509Certificate, paramString1, paramString2, null, null);
  }
  
  public TimeStampTokenGenerator(PrivateKey paramPrivateKey, X509Certificate paramX509Certificate, String paramString1, String paramString2, AttributeTable paramAttributeTable1, AttributeTable paramAttributeTable2)
    throws IllegalArgumentException, TSPException
  {
    this.key = paramPrivateKey;
    this.cert = paramX509Certificate;
    this.digestOID = paramString1;
    this.tsaPolicyOID = new ASN1ObjectIdentifier(paramString2);
    this.unsignedAttr = paramAttributeTable2;
    if (paramAttributeTable1 != null) {}
    for (Hashtable localHashtable = paramAttributeTable1.toHashtable();; localHashtable = new Hashtable())
    {
      TSPUtil.validateCertificate(paramX509Certificate);
      try
      {
        ESSCertID localESSCertID = new ESSCertID(MessageDigest.getInstance("SHA-1").digest(paramX509Certificate.getEncoded()));
        localHashtable.put(PKCSObjectIdentifiers.id_aa_signingCertificate, new Attribute(PKCSObjectIdentifiers.id_aa_signingCertificate, new DERSet(new SigningCertificate(localESSCertID))));
        this.signedAttr = new AttributeTable(localHashtable);
        return;
      }
      catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
      {
        throw new TSPException("Can't find a SHA-1 implementation.", localNoSuchAlgorithmException);
      }
      catch (CertificateEncodingException localCertificateEncodingException)
      {
        throw new TSPException("Exception processing certificate.", localCertificateEncodingException);
      }
    }
  }
  
  public TimeStampTokenGenerator(PrivateKey paramPrivateKey, X509Certificate paramX509Certificate, ASN1ObjectIdentifier paramASN1ObjectIdentifier, String paramString)
    throws IllegalArgumentException, TSPException
  {
    this(paramPrivateKey, paramX509Certificate, paramASN1ObjectIdentifier.getId(), paramString, null, null);
  }
  
  public TimeStampTokenGenerator(SignerInfoGenerator paramSignerInfoGenerator, ASN1ObjectIdentifier paramASN1ObjectIdentifier)
    throws IllegalArgumentException, TSPException
  {
    this(new DigestCalculator()
    {
      private ByteArrayOutputStream bOut = new ByteArrayOutputStream();
      
      public AlgorithmIdentifier getAlgorithmIdentifier()
      {
        return new AlgorithmIdentifier(OIWObjectIdentifiers.idSHA1, DERNull.INSTANCE);
      }
      
      public byte[] getDigest()
      {
        try
        {
          byte[] arrayOfByte = MessageDigest.getInstance("SHA-1").digest(this.bOut.toByteArray());
          return arrayOfByte;
        }
        catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
        {
          throw new IllegalStateException("cannot find sha-1: " + localNoSuchAlgorithmException.getMessage());
        }
      }
      
      public OutputStream getOutputStream()
      {
        return this.bOut;
      }
    }, paramSignerInfoGenerator, paramASN1ObjectIdentifier);
  }
  
  public TimeStampTokenGenerator(DigestCalculator paramDigestCalculator, final SignerInfoGenerator paramSignerInfoGenerator, ASN1ObjectIdentifier paramASN1ObjectIdentifier)
    throws IllegalArgumentException, TSPException
  {
    this.signerInfoGen = paramSignerInfoGenerator;
    this.tsaPolicyOID = paramASN1ObjectIdentifier;
    if (!paramDigestCalculator.getAlgorithmIdentifier().getAlgorithm().equals(OIWObjectIdentifiers.idSHA1)) {
      throw new IllegalArgumentException("Digest calculator must be for SHA-1");
    }
    if (!paramSignerInfoGenerator.hasAssociatedCertificate()) {
      throw new IllegalArgumentException("SignerInfoGenerator must have an associated certificate");
    }
    TSPUtil.validateCertificate(paramSignerInfoGenerator.getAssociatedCertificate());
    try
    {
      OutputStream localOutputStream = paramDigestCalculator.getOutputStream();
      localOutputStream.write(paramSignerInfoGenerator.getAssociatedCertificate().getEncoded());
      localOutputStream.close();
      this.signerInfoGen = new SignerInfoGenerator(paramSignerInfoGenerator, new CMSAttributeTableGenerator()
      {
        public AttributeTable getAttributes(Map paramAnonymousMap)
          throws CMSAttributeTableGenerationException
        {
          return paramSignerInfoGenerator.getSignedAttributeTableGenerator().getAttributes(paramAnonymousMap).add(PKCSObjectIdentifiers.id_aa_signingCertificate, new SigningCertificate(this.val$essCertid));
        }
      }, paramSignerInfoGenerator.getUnsignedAttributeTableGenerator());
      return;
    }
    catch (IOException localIOException)
    {
      throw new TSPException("Exception processing certificate.", localIOException);
    }
  }
  
  private String getSigAlgorithm(PrivateKey paramPrivateKey, String paramString)
  {
    String str;
    if (((paramPrivateKey instanceof RSAPrivateKey)) || ("RSA".equalsIgnoreCase(paramPrivateKey.getAlgorithm()))) {
      str = "RSA";
    }
    for (;;)
    {
      return TSPUtil.getDigestAlgName(paramString) + "with" + str;
      if (((paramPrivateKey instanceof DSAPrivateKey)) || ("DSA".equalsIgnoreCase(paramPrivateKey.getAlgorithm())))
      {
        str = "DSA";
      }
      else if (("ECDSA".equalsIgnoreCase(paramPrivateKey.getAlgorithm())) || ("EC".equalsIgnoreCase(paramPrivateKey.getAlgorithm())))
      {
        str = "ECDSA";
      }
      else if (((paramPrivateKey instanceof GOST3410PrivateKey)) || ("GOST3410".equalsIgnoreCase(paramPrivateKey.getAlgorithm())))
      {
        str = "GOST3410";
      }
      else
      {
        boolean bool = "ECGOST3410".equalsIgnoreCase(paramPrivateKey.getAlgorithm());
        str = null;
        if (bool) {
          str = CMSSignedGenerator.ENCRYPTION_ECGOST3410;
        }
      }
    }
  }
  
  public void addAttributeCertificates(Store paramStore)
  {
    this.attrCerts.addAll(paramStore.getMatches(null));
  }
  
  public void addCRLs(Store paramStore)
  {
    this.crls.addAll(paramStore.getMatches(null));
  }
  
  public void addCertificates(Store paramStore)
  {
    this.certs.addAll(paramStore.getMatches(null));
  }
  
  /* Error */
  public TimeStampToken generate(TimeStampRequest paramTimeStampRequest, BigInteger paramBigInteger, Date paramDate)
    throws TSPException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 169	org/spongycastle/tsp/TimeStampTokenGenerator:signerInfoGen	Lorg/spongycastle/cms/SignerInfoGenerator;
    //   4: ifnonnull +14 -> 18
    //   7: new 311	java/lang/IllegalStateException
    //   10: dup
    //   11: ldc_w 313
    //   14: invokespecial 314	java/lang/IllegalStateException:<init>	(Ljava/lang/String;)V
    //   17: athrow
    //   18: new 316	org/spongycastle/asn1/tsp/MessageImprint
    //   21: dup
    //   22: new 177	org/spongycastle/asn1/x509/AlgorithmIdentifier
    //   25: dup
    //   26: aload_1
    //   27: invokevirtual 321	org/spongycastle/tsp/TimeStampRequest:getMessageImprintAlgOID	()Lorg/spongycastle/asn1/ASN1ObjectIdentifier;
    //   30: new 323	org/spongycastle/asn1/DERNull
    //   33: dup
    //   34: invokespecial 324	org/spongycastle/asn1/DERNull:<init>	()V
    //   37: invokespecial 327	org/spongycastle/asn1/x509/AlgorithmIdentifier:<init>	(Lorg/spongycastle/asn1/ASN1ObjectIdentifier;Lorg/spongycastle/asn1/ASN1Encodable;)V
    //   40: aload_1
    //   41: invokevirtual 330	org/spongycastle/tsp/TimeStampRequest:getMessageImprintDigest	()[B
    //   44: invokespecial 333	org/spongycastle/asn1/tsp/MessageImprint:<init>	(Lorg/spongycastle/asn1/x509/AlgorithmIdentifier;[B)V
    //   47: astore 4
    //   49: aload_0
    //   50: getfield 47	org/spongycastle/tsp/TimeStampTokenGenerator:accuracySeconds	I
    //   53: ifgt +24 -> 77
    //   56: aload_0
    //   57: getfield 49	org/spongycastle/tsp/TimeStampTokenGenerator:accuracyMillis	I
    //   60: ifgt +17 -> 77
    //   63: aload_0
    //   64: getfield 51	org/spongycastle/tsp/TimeStampTokenGenerator:accuracyMicros	I
    //   67: istore 25
    //   69: aconst_null
    //   70: astore 11
    //   72: iload 25
    //   74: ifle +111 -> 185
    //   77: aload_0
    //   78: getfield 47	org/spongycastle/tsp/TimeStampTokenGenerator:accuracySeconds	I
    //   81: istore 5
    //   83: aconst_null
    //   84: astore 6
    //   86: iload 5
    //   88: ifle +20 -> 108
    //   91: aload_0
    //   92: getfield 47	org/spongycastle/tsp/TimeStampTokenGenerator:accuracySeconds	I
    //   95: istore 24
    //   97: new 335	org/spongycastle/asn1/ASN1Integer
    //   100: dup
    //   101: iload 24
    //   103: invokespecial 338	org/spongycastle/asn1/ASN1Integer:<init>	(I)V
    //   106: astore 6
    //   108: aload_0
    //   109: getfield 49	org/spongycastle/tsp/TimeStampTokenGenerator:accuracyMillis	I
    //   112: istore 7
    //   114: aconst_null
    //   115: astore 8
    //   117: iload 7
    //   119: ifle +20 -> 139
    //   122: aload_0
    //   123: getfield 49	org/spongycastle/tsp/TimeStampTokenGenerator:accuracyMillis	I
    //   126: istore 23
    //   128: new 335	org/spongycastle/asn1/ASN1Integer
    //   131: dup
    //   132: iload 23
    //   134: invokespecial 338	org/spongycastle/asn1/ASN1Integer:<init>	(I)V
    //   137: astore 8
    //   139: aload_0
    //   140: getfield 51	org/spongycastle/tsp/TimeStampTokenGenerator:accuracyMicros	I
    //   143: istore 9
    //   145: aconst_null
    //   146: astore 10
    //   148: iload 9
    //   150: ifle +20 -> 170
    //   153: aload_0
    //   154: getfield 51	org/spongycastle/tsp/TimeStampTokenGenerator:accuracyMicros	I
    //   157: istore 22
    //   159: new 335	org/spongycastle/asn1/ASN1Integer
    //   162: dup
    //   163: iload 22
    //   165: invokespecial 338	org/spongycastle/asn1/ASN1Integer:<init>	(I)V
    //   168: astore 10
    //   170: new 340	org/spongycastle/asn1/tsp/Accuracy
    //   173: dup
    //   174: aload 6
    //   176: aload 8
    //   178: aload 10
    //   180: invokespecial 343	org/spongycastle/asn1/tsp/Accuracy:<init>	(Lorg/spongycastle/asn1/ASN1Integer;Lorg/spongycastle/asn1/ASN1Integer;Lorg/spongycastle/asn1/ASN1Integer;)V
    //   183: astore 11
    //   185: aload_0
    //   186: getfield 53	org/spongycastle/tsp/TimeStampTokenGenerator:ordering	Z
    //   189: istore 12
    //   191: aconst_null
    //   192: astore 13
    //   194: iload 12
    //   196: ifeq +16 -> 212
    //   199: new 345	org/spongycastle/asn1/ASN1Boolean
    //   202: dup
    //   203: aload_0
    //   204: getfield 53	org/spongycastle/tsp/TimeStampTokenGenerator:ordering	Z
    //   207: invokespecial 348	org/spongycastle/asn1/ASN1Boolean:<init>	(Z)V
    //   210: astore 13
    //   212: aload_1
    //   213: invokevirtual 352	org/spongycastle/tsp/TimeStampRequest:getNonce	()Ljava/math/BigInteger;
    //   216: astore 14
    //   218: aconst_null
    //   219: astore 15
    //   221: aload 14
    //   223: ifnull +16 -> 239
    //   226: new 335	org/spongycastle/asn1/ASN1Integer
    //   229: dup
    //   230: aload_1
    //   231: invokevirtual 352	org/spongycastle/tsp/TimeStampRequest:getNonce	()Ljava/math/BigInteger;
    //   234: invokespecial 355	org/spongycastle/asn1/ASN1Integer:<init>	(Ljava/math/BigInteger;)V
    //   237: astore 15
    //   239: aload_0
    //   240: getfield 77	org/spongycastle/tsp/TimeStampTokenGenerator:tsaPolicyOID	Lorg/spongycastle/asn1/ASN1ObjectIdentifier;
    //   243: astore 16
    //   245: aload_1
    //   246: invokevirtual 358	org/spongycastle/tsp/TimeStampRequest:getReqPolicy	()Lorg/spongycastle/asn1/ASN1ObjectIdentifier;
    //   249: ifnull +9 -> 258
    //   252: aload_1
    //   253: invokevirtual 358	org/spongycastle/tsp/TimeStampRequest:getReqPolicy	()Lorg/spongycastle/asn1/ASN1ObjectIdentifier;
    //   256: astore 16
    //   258: new 360	org/spongycastle/asn1/tsp/TSTInfo
    //   261: dup
    //   262: aload 16
    //   264: aload 4
    //   266: new 335	org/spongycastle/asn1/ASN1Integer
    //   269: dup
    //   270: aload_2
    //   271: invokespecial 355	org/spongycastle/asn1/ASN1Integer:<init>	(Ljava/math/BigInteger;)V
    //   274: new 362	org/spongycastle/asn1/ASN1GeneralizedTime
    //   277: dup
    //   278: aload_3
    //   279: invokespecial 365	org/spongycastle/asn1/ASN1GeneralizedTime:<init>	(Ljava/util/Date;)V
    //   282: aload 11
    //   284: aload 13
    //   286: aload 15
    //   288: aload_0
    //   289: getfield 55	org/spongycastle/tsp/TimeStampTokenGenerator:tsa	Lorg/spongycastle/asn1/x509/GeneralName;
    //   292: aload_1
    //   293: invokevirtual 369	org/spongycastle/tsp/TimeStampRequest:getExtensions	()Lorg/spongycastle/asn1/x509/Extensions;
    //   296: invokespecial 372	org/spongycastle/asn1/tsp/TSTInfo:<init>	(Lorg/spongycastle/asn1/ASN1ObjectIdentifier;Lorg/spongycastle/asn1/tsp/MessageImprint;Lorg/spongycastle/asn1/ASN1Integer;Lorg/spongycastle/asn1/ASN1GeneralizedTime;Lorg/spongycastle/asn1/tsp/Accuracy;Lorg/spongycastle/asn1/ASN1Boolean;Lorg/spongycastle/asn1/ASN1Integer;Lorg/spongycastle/asn1/x509/GeneralName;Lorg/spongycastle/asn1/x509/Extensions;)V
    //   299: astore 17
    //   301: new 374	org/spongycastle/cms/CMSSignedDataGenerator
    //   304: dup
    //   305: invokespecial 375	org/spongycastle/cms/CMSSignedDataGenerator:<init>	()V
    //   308: astore 18
    //   310: aload_1
    //   311: invokevirtual 378	org/spongycastle/tsp/TimeStampRequest:getCertReq	()Z
    //   314: ifeq +96 -> 410
    //   317: aload 18
    //   319: new 380	org/spongycastle/util/CollectionStore
    //   322: dup
    //   323: aload_0
    //   324: getfield 60	org/spongycastle/tsp/TimeStampTokenGenerator:certs	Ljava/util/List;
    //   327: invokespecial 383	org/spongycastle/util/CollectionStore:<init>	(Ljava/util/Collection;)V
    //   330: invokevirtual 385	org/spongycastle/cms/CMSSignedDataGenerator:addCertificates	(Lorg/spongycastle/util/Store;)V
    //   333: aload 18
    //   335: new 380	org/spongycastle/util/CollectionStore
    //   338: dup
    //   339: aload_0
    //   340: getfield 62	org/spongycastle/tsp/TimeStampTokenGenerator:crls	Ljava/util/List;
    //   343: invokespecial 383	org/spongycastle/util/CollectionStore:<init>	(Ljava/util/Collection;)V
    //   346: invokevirtual 387	org/spongycastle/cms/CMSSignedDataGenerator:addCRLs	(Lorg/spongycastle/util/Store;)V
    //   349: aload 18
    //   351: new 380	org/spongycastle/util/CollectionStore
    //   354: dup
    //   355: aload_0
    //   356: getfield 64	org/spongycastle/tsp/TimeStampTokenGenerator:attrCerts	Ljava/util/List;
    //   359: invokespecial 383	org/spongycastle/util/CollectionStore:<init>	(Ljava/util/Collection;)V
    //   362: invokevirtual 389	org/spongycastle/cms/CMSSignedDataGenerator:addAttributeCertificates	(Lorg/spongycastle/util/Store;)V
    //   365: aload 18
    //   367: aload_0
    //   368: getfield 169	org/spongycastle/tsp/TimeStampTokenGenerator:signerInfoGen	Lorg/spongycastle/cms/SignerInfoGenerator;
    //   371: invokevirtual 393	org/spongycastle/cms/CMSSignedDataGenerator:addSignerInfoGenerator	(Lorg/spongycastle/cms/SignerInfoGenerator;)V
    //   374: aload 17
    //   376: ldc_w 395
    //   379: invokevirtual 398	org/spongycastle/asn1/tsp/TSTInfo:getEncoded	(Ljava/lang/String;)[B
    //   382: astore 21
    //   384: new 400	org/spongycastle/tsp/TimeStampToken
    //   387: dup
    //   388: aload 18
    //   390: new 402	org/spongycastle/cms/CMSProcessableByteArray
    //   393: dup
    //   394: getstatic 405	org/spongycastle/asn1/pkcs/PKCSObjectIdentifiers:id_ct_TSTInfo	Lorg/spongycastle/asn1/ASN1ObjectIdentifier;
    //   397: aload 21
    //   399: invokespecial 408	org/spongycastle/cms/CMSProcessableByteArray:<init>	(Lorg/spongycastle/asn1/ASN1ObjectIdentifier;[B)V
    //   402: iconst_1
    //   403: invokevirtual 411	org/spongycastle/cms/CMSSignedDataGenerator:generate	(Lorg/spongycastle/cms/CMSTypedData;Z)Lorg/spongycastle/cms/CMSSignedData;
    //   406: invokespecial 414	org/spongycastle/tsp/TimeStampToken:<init>	(Lorg/spongycastle/cms/CMSSignedData;)V
    //   409: areturn
    //   410: aload 18
    //   412: new 380	org/spongycastle/util/CollectionStore
    //   415: dup
    //   416: aload_0
    //   417: getfield 62	org/spongycastle/tsp/TimeStampTokenGenerator:crls	Ljava/util/List;
    //   420: invokespecial 383	org/spongycastle/util/CollectionStore:<init>	(Ljava/util/Collection;)V
    //   423: invokevirtual 387	org/spongycastle/cms/CMSSignedDataGenerator:addCRLs	(Lorg/spongycastle/util/Store;)V
    //   426: goto -61 -> 365
    //   429: astore 20
    //   431: new 35	org/spongycastle/tsp/TSPException
    //   434: dup
    //   435: ldc_w 416
    //   438: aload 20
    //   440: invokespecial 151	org/spongycastle/tsp/TSPException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   443: athrow
    //   444: astore 19
    //   446: new 35	org/spongycastle/tsp/TSPException
    //   449: dup
    //   450: ldc_w 418
    //   453: aload 19
    //   455: invokespecial 151	org/spongycastle/tsp/TSPException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   458: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	459	0	this	TimeStampTokenGenerator
    //   0	459	1	paramTimeStampRequest	TimeStampRequest
    //   0	459	2	paramBigInteger	BigInteger
    //   0	459	3	paramDate	Date
    //   47	218	4	localMessageImprint	org.spongycastle.asn1.tsp.MessageImprint
    //   81	6	5	i	int
    //   84	91	6	localASN1Integer1	org.spongycastle.asn1.ASN1Integer
    //   112	6	7	j	int
    //   115	62	8	localASN1Integer2	org.spongycastle.asn1.ASN1Integer
    //   143	6	9	k	int
    //   146	33	10	localASN1Integer3	org.spongycastle.asn1.ASN1Integer
    //   70	213	11	localAccuracy	org.spongycastle.asn1.tsp.Accuracy
    //   189	6	12	bool	boolean
    //   192	93	13	localASN1Boolean	org.spongycastle.asn1.ASN1Boolean
    //   216	6	14	localBigInteger	BigInteger
    //   219	68	15	localASN1Integer4	org.spongycastle.asn1.ASN1Integer
    //   243	20	16	localASN1ObjectIdentifier	ASN1ObjectIdentifier
    //   299	76	17	localTSTInfo	org.spongycastle.asn1.tsp.TSTInfo
    //   308	103	18	localCMSSignedDataGenerator	org.spongycastle.cms.CMSSignedDataGenerator
    //   444	10	19	localIOException	IOException
    //   429	10	20	localCMSException	org.spongycastle.cms.CMSException
    //   382	16	21	arrayOfByte	byte[]
    //   157	7	22	m	int
    //   126	7	23	n	int
    //   95	7	24	i1	int
    //   67	6	25	i2	int
    // Exception table:
    //   from	to	target	type
    //   301	365	429	org/spongycastle/cms/CMSException
    //   365	410	429	org/spongycastle/cms/CMSException
    //   410	426	429	org/spongycastle/cms/CMSException
    //   301	365	444	java/io/IOException
    //   365	410	444	java/io/IOException
    //   410	426	444	java/io/IOException
  }
  
  public TimeStampToken generate(TimeStampRequest paramTimeStampRequest, BigInteger paramBigInteger, Date paramDate, String paramString)
    throws NoSuchAlgorithmException, NoSuchProviderException, TSPException
  {
    if (this.signerInfoGen == null) {}
    try
    {
      JcaSignerInfoGeneratorBuilder localJcaSignerInfoGeneratorBuilder = new JcaSignerInfoGeneratorBuilder(new JcaDigestCalculatorProviderBuilder().setProvider(paramString).build());
      localJcaSignerInfoGeneratorBuilder.setSignedAttributeGenerator(new DefaultSignedAttributeTableGenerator(this.signedAttr));
      if (this.unsignedAttr != null) {
        localJcaSignerInfoGeneratorBuilder.setUnsignedAttributeGenerator(new SimpleAttributeTableGenerator(this.unsignedAttr));
      }
      this.signerInfoGen = localJcaSignerInfoGeneratorBuilder.build(new JcaContentSignerBuilder(getSigAlgorithm(this.key, this.digestOID)).setProvider(paramString).build(this.key), this.cert);
      return generate(paramTimeStampRequest, paramBigInteger, paramDate);
    }
    catch (OperatorCreationException localOperatorCreationException)
    {
      throw new TSPException("Error generating signing operator", localOperatorCreationException);
    }
    catch (CertificateEncodingException localCertificateEncodingException)
    {
      throw new TSPException("Error encoding certificate", localCertificateEncodingException);
    }
  }
  
  public void setAccuracyMicros(int paramInt)
  {
    this.accuracyMicros = paramInt;
  }
  
  public void setAccuracyMillis(int paramInt)
  {
    this.accuracyMillis = paramInt;
  }
  
  public void setAccuracySeconds(int paramInt)
  {
    this.accuracySeconds = paramInt;
  }
  
  public void setCertificatesAndCRLs(CertStore paramCertStore)
    throws CertStoreException, TSPException
  {
    Iterator localIterator1 = paramCertStore.getCertificates(null).iterator();
    while (localIterator1.hasNext()) {
      try
      {
        this.certs.add(new JcaX509CertificateHolder((X509Certificate)localIterator1.next()));
      }
      catch (CertificateEncodingException localCertificateEncodingException)
      {
        throw new TSPException("cannot encode certificate: " + localCertificateEncodingException.getMessage(), localCertificateEncodingException);
      }
    }
    Iterator localIterator2 = paramCertStore.getCRLs(null).iterator();
    while (localIterator2.hasNext()) {
      try
      {
        this.crls.add(new JcaX509CRLHolder((X509CRL)localIterator2.next()));
      }
      catch (CRLException localCRLException)
      {
        throw new TSPException("cannot encode CRL: " + localCRLException.getMessage(), localCRLException);
      }
    }
  }
  
  public void setOrdering(boolean paramBoolean)
  {
    this.ordering = paramBoolean;
  }
  
  public void setTSA(GeneralName paramGeneralName)
  {
    this.tsa = paramGeneralName;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.tsp.TimeStampTokenGenerator
 * JD-Core Version:    0.7.0.1
 */