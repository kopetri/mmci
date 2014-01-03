package org.spongycastle.jce.provider;

import java.security.PublicKey;
import java.security.cert.CertPath;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.TrustAnchor;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.security.auth.x500.X500Principal;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.x509.DistributionPoint;
import org.spongycastle.asn1.x509.TargetInformation;
import org.spongycastle.asn1.x509.X509Extensions;
import org.spongycastle.jce.exception.ExtCertPathValidatorException;
import org.spongycastle.x509.ExtendedPKIXParameters;
import org.spongycastle.x509.PKIXAttrCertChecker;
import org.spongycastle.x509.X509AttributeCertificate;

class RFC3281CertPathUtilities
{
  private static final String AUTHORITY_INFO_ACCESS = X509Extensions.AuthorityInfoAccess.getId();
  private static final String CRL_DISTRIBUTION_POINTS;
  private static final String NO_REV_AVAIL;
  private static final String TARGET_INFORMATION = X509Extensions.TargetInformation.getId();
  
  static
  {
    NO_REV_AVAIL = X509Extensions.NoRevAvail.getId();
    CRL_DISTRIBUTION_POINTS = X509Extensions.CRLDistributionPoints.getId();
  }
  
  protected static void additionalChecks(X509AttributeCertificate paramX509AttributeCertificate, ExtendedPKIXParameters paramExtendedPKIXParameters)
    throws CertPathValidatorException
  {
    Iterator localIterator1 = paramExtendedPKIXParameters.getProhibitedACAttributes().iterator();
    while (localIterator1.hasNext())
    {
      String str2 = (String)localIterator1.next();
      if (paramX509AttributeCertificate.getAttributes(str2) != null) {
        throw new CertPathValidatorException("Attribute certificate contains prohibited attribute: " + str2 + ".");
      }
    }
    Iterator localIterator2 = paramExtendedPKIXParameters.getNecessaryACAttributes().iterator();
    while (localIterator2.hasNext())
    {
      String str1 = (String)localIterator2.next();
      if (paramX509AttributeCertificate.getAttributes(str1) == null) {
        throw new CertPathValidatorException("Attribute certificate does not contain necessary attribute: " + str1 + ".");
      }
    }
  }
  
  private static void checkCRL(DistributionPoint paramDistributionPoint, X509AttributeCertificate paramX509AttributeCertificate, ExtendedPKIXParameters paramExtendedPKIXParameters, Date paramDate, X509Certificate paramX509Certificate, CertStatus paramCertStatus, ReasonsMask paramReasonsMask, List paramList)
    throws AnnotatedException
  {
    if (paramX509AttributeCertificate.getExtensionValue(X509Extensions.NoRevAvail.getId()) != null) {
      return;
    }
    Date localDate = new Date(System.currentTimeMillis());
    if (paramDate.getTime() > localDate.getTime()) {
      throw new AnnotatedException("Validation time is in future.");
    }
    Set localSet = CertPathValidatorUtilities.getCompleteCRLs(paramDistributionPoint, paramX509AttributeCertificate, localDate, paramExtendedPKIXParameters);
    int i = 0;
    Object localObject = null;
    Iterator localIterator = localSet.iterator();
    for (;;)
    {
      if ((localIterator.hasNext()) && (paramCertStatus.getCertStatus() == 11) && (!paramReasonsMask.isAllReasons())) {}
      try
      {
        X509CRL localX509CRL1 = (X509CRL)localIterator.next();
        ReasonsMask localReasonsMask = RFC3280CertPathUtilities.processCRLD(localX509CRL1, paramDistributionPoint);
        if (!localReasonsMask.hasNewReasons(paramReasonsMask)) {
          continue;
        }
        PublicKey localPublicKey = RFC3280CertPathUtilities.processCRLG(localX509CRL1, RFC3280CertPathUtilities.processCRLF(localX509CRL1, paramX509AttributeCertificate, null, null, paramExtendedPKIXParameters, paramList));
        boolean bool = paramExtendedPKIXParameters.isUseDeltasEnabled();
        X509CRL localX509CRL2 = null;
        if (bool) {
          localX509CRL2 = RFC3280CertPathUtilities.processCRLH(CertPathValidatorUtilities.getDeltaCRLs(localDate, paramExtendedPKIXParameters, localX509CRL1), localPublicKey);
        }
        if ((paramExtendedPKIXParameters.getValidityModel() != 1) && (paramX509AttributeCertificate.getNotAfter().getTime() < localX509CRL1.getThisUpdate().getTime())) {
          throw new AnnotatedException("No valid CRL for current time found.");
        }
        RFC3280CertPathUtilities.processCRLB1(paramDistributionPoint, paramX509AttributeCertificate, localX509CRL1);
        RFC3280CertPathUtilities.processCRLB2(paramDistributionPoint, paramX509AttributeCertificate, localX509CRL1);
        RFC3280CertPathUtilities.processCRLC(localX509CRL2, localX509CRL1, paramExtendedPKIXParameters);
        RFC3280CertPathUtilities.processCRLI(paramDate, localX509CRL2, paramX509AttributeCertificate, paramCertStatus, paramExtendedPKIXParameters);
        RFC3280CertPathUtilities.processCRLJ(paramDate, localX509CRL1, paramX509AttributeCertificate, paramCertStatus);
        if (paramCertStatus.getCertStatus() == 8) {
          paramCertStatus.setCertStatus(11);
        }
        paramReasonsMask.addReasons(localReasonsMask);
        i = 1;
      }
      catch (AnnotatedException localAnnotatedException) {}
      if (i != 0) {
        break;
      }
      throw localObject;
    }
  }
  
  /* Error */
  protected static void checkCRLs(X509AttributeCertificate paramX509AttributeCertificate, ExtendedPKIXParameters paramExtendedPKIXParameters, X509Certificate paramX509Certificate, Date paramDate, List paramList)
    throws CertPathValidatorException
  {
    // Byte code:
    //   0: aload_1
    //   1: invokevirtual 218	org/spongycastle/x509/ExtendedPKIXParameters:isRevocationEnabled	()Z
    //   4: ifeq +524 -> 528
    //   7: aload_0
    //   8: getstatic 30	org/spongycastle/jce/provider/RFC3281CertPathUtilities:NO_REV_AVAIL	Ljava/lang/String;
    //   11: invokeinterface 107 2 0
    //   16: ifnonnull +477 -> 493
    //   19: aload_0
    //   20: getstatic 35	org/spongycastle/jce/provider/RFC3281CertPathUtilities:CRL_DISTRIBUTION_POINTS	Ljava/lang/String;
    //   23: invokestatic 221	org/spongycastle/jce/provider/CertPathValidatorUtilities:getExtensionValue	(Ljava/security/cert/X509Extension;Ljava/lang/String;)Lorg/spongycastle/asn1/ASN1Primitive;
    //   26: invokestatic 227	org/spongycastle/asn1/x509/CRLDistPoint:getInstance	(Ljava/lang/Object;)Lorg/spongycastle/asn1/x509/CRLDistPoint;
    //   29: astore 6
    //   31: aload 6
    //   33: aload_1
    //   34: invokestatic 231	org/spongycastle/jce/provider/CertPathValidatorUtilities:addAdditionalStoresFromCRLDistributionPoint	(Lorg/spongycastle/asn1/x509/CRLDistPoint;Lorg/spongycastle/x509/ExtendedPKIXParameters;)V
    //   37: new 132	org/spongycastle/jce/provider/CertStatus
    //   40: dup
    //   41: invokespecial 232	org/spongycastle/jce/provider/CertStatus:<init>	()V
    //   44: astore 8
    //   46: new 138	org/spongycastle/jce/provider/ReasonsMask
    //   49: dup
    //   50: invokespecial 233	org/spongycastle/jce/provider/ReasonsMask:<init>	()V
    //   53: astore 9
    //   55: aconst_null
    //   56: astore 10
    //   58: iconst_0
    //   59: istore 11
    //   61: aload 6
    //   63: ifnull +155 -> 218
    //   66: aload 6
    //   68: invokevirtual 237	org/spongycastle/asn1/x509/CRLDistPoint:getDistributionPoints	()[Lorg/spongycastle/asn1/x509/DistributionPoint;
    //   71: astore 17
    //   73: iconst_0
    //   74: istore 18
    //   76: aload 17
    //   78: arraylength
    //   79: istore 20
    //   81: aconst_null
    //   82: astore 10
    //   84: iload 18
    //   86: iload 20
    //   88: if_icmpge +130 -> 218
    //   91: aload 8
    //   93: invokevirtual 136	org/spongycastle/jce/provider/CertStatus:getCertStatus	()I
    //   96: istore 21
    //   98: aconst_null
    //   99: astore 10
    //   101: iload 21
    //   103: bipush 11
    //   105: if_icmpne +113 -> 218
    //   108: aload 9
    //   110: invokevirtual 141	org/spongycastle/jce/provider/ReasonsMask:isAllReasons	()Z
    //   113: istore 22
    //   115: aconst_null
    //   116: astore 10
    //   118: iload 22
    //   120: ifne +98 -> 218
    //   123: aload_1
    //   124: invokevirtual 240	org/spongycastle/x509/ExtendedPKIXParameters:clone	()Ljava/lang/Object;
    //   127: checkcast 49	org/spongycastle/x509/ExtendedPKIXParameters
    //   130: astore 23
    //   132: aload 17
    //   134: iload 18
    //   136: aaload
    //   137: aload_0
    //   138: aload 23
    //   140: aload_3
    //   141: aload_2
    //   142: aload 8
    //   144: aload 9
    //   146: aload 4
    //   148: invokestatic 242	org/spongycastle/jce/provider/RFC3281CertPathUtilities:checkCRL	(Lorg/spongycastle/asn1/x509/DistributionPoint;Lorg/spongycastle/x509/X509AttributeCertificate;Lorg/spongycastle/x509/ExtendedPKIXParameters;Ljava/util/Date;Ljava/security/cert/X509Certificate;Lorg/spongycastle/jce/provider/CertStatus;Lorg/spongycastle/jce/provider/ReasonsMask;Ljava/util/List;)V
    //   151: iconst_1
    //   152: istore 11
    //   154: iinc 18 1
    //   157: goto -81 -> 76
    //   160: astore 5
    //   162: new 47	java/security/cert/CertPathValidatorException
    //   165: dup
    //   166: ldc 244
    //   168: aload 5
    //   170: invokespecial 247	java/security/cert/CertPathValidatorException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   173: athrow
    //   174: astore 7
    //   176: new 47	java/security/cert/CertPathValidatorException
    //   179: dup
    //   180: ldc 249
    //   182: aload 7
    //   184: invokespecial 247	java/security/cert/CertPathValidatorException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   187: athrow
    //   188: astore 16
    //   190: new 251	org/spongycastle/jce/exception/ExtCertPathValidatorException
    //   193: dup
    //   194: ldc 253
    //   196: aload 16
    //   198: invokespecial 254	org/spongycastle/jce/exception/ExtCertPathValidatorException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   201: athrow
    //   202: astore 19
    //   204: new 103	org/spongycastle/jce/provider/AnnotatedException
    //   207: dup
    //   208: ldc_w 256
    //   211: aload 19
    //   213: invokespecial 257	org/spongycastle/jce/provider/AnnotatedException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   216: astore 10
    //   218: aload 8
    //   220: invokevirtual 136	org/spongycastle/jce/provider/CertStatus:getCertStatus	()I
    //   223: bipush 11
    //   225: if_icmpne +96 -> 321
    //   228: aload 9
    //   230: invokevirtual 141	org/spongycastle/jce/provider/ReasonsMask:isAllReasons	()Z
    //   233: ifne +88 -> 321
    //   236: new 259	org/spongycastle/asn1/ASN1InputStream
    //   239: dup
    //   240: aload_0
    //   241: invokeinterface 263 1 0
    //   246: invokevirtual 269	org/spongycastle/x509/AttributeCertificateIssuer:getPrincipals	()[Ljava/security/Principal;
    //   249: iconst_0
    //   250: aaload
    //   251: checkcast 271	javax/security/auth/x500/X500Principal
    //   254: invokevirtual 275	javax/security/auth/x500/X500Principal:getEncoded	()[B
    //   257: invokespecial 278	org/spongycastle/asn1/ASN1InputStream:<init>	([B)V
    //   260: invokevirtual 282	org/spongycastle/asn1/ASN1InputStream:readObject	()Lorg/spongycastle/asn1/ASN1Primitive;
    //   263: astore 15
    //   265: new 284	org/spongycastle/asn1/x509/DistributionPoint
    //   268: dup
    //   269: new 286	org/spongycastle/asn1/x509/DistributionPointName
    //   272: dup
    //   273: iconst_0
    //   274: new 288	org/spongycastle/asn1/x509/GeneralNames
    //   277: dup
    //   278: new 290	org/spongycastle/asn1/x509/GeneralName
    //   281: dup
    //   282: iconst_4
    //   283: aload 15
    //   285: invokespecial 293	org/spongycastle/asn1/x509/GeneralName:<init>	(ILorg/spongycastle/asn1/ASN1Encodable;)V
    //   288: invokespecial 296	org/spongycastle/asn1/x509/GeneralNames:<init>	(Lorg/spongycastle/asn1/x509/GeneralName;)V
    //   291: invokespecial 297	org/spongycastle/asn1/x509/DistributionPointName:<init>	(ILorg/spongycastle/asn1/ASN1Encodable;)V
    //   294: aconst_null
    //   295: aconst_null
    //   296: invokespecial 300	org/spongycastle/asn1/x509/DistributionPoint:<init>	(Lorg/spongycastle/asn1/x509/DistributionPointName;Lorg/spongycastle/asn1/x509/ReasonFlags;Lorg/spongycastle/asn1/x509/GeneralNames;)V
    //   299: aload_0
    //   300: aload_1
    //   301: invokevirtual 240	org/spongycastle/x509/ExtendedPKIXParameters:clone	()Ljava/lang/Object;
    //   304: checkcast 49	org/spongycastle/x509/ExtendedPKIXParameters
    //   307: aload_3
    //   308: aload_2
    //   309: aload 8
    //   311: aload 9
    //   313: aload 4
    //   315: invokestatic 242	org/spongycastle/jce/provider/RFC3281CertPathUtilities:checkCRL	(Lorg/spongycastle/asn1/x509/DistributionPoint;Lorg/spongycastle/x509/X509AttributeCertificate;Lorg/spongycastle/x509/ExtendedPKIXParameters;Ljava/util/Date;Ljava/security/cert/X509Certificate;Lorg/spongycastle/jce/provider/CertStatus;Lorg/spongycastle/jce/provider/ReasonsMask;Ljava/util/List;)V
    //   318: iconst_1
    //   319: istore 11
    //   321: iload 11
    //   323: ifne +50 -> 373
    //   326: new 251	org/spongycastle/jce/exception/ExtCertPathValidatorException
    //   329: dup
    //   330: ldc_w 302
    //   333: aload 10
    //   335: invokespecial 254	org/spongycastle/jce/exception/ExtCertPathValidatorException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   338: athrow
    //   339: astore 14
    //   341: new 103	org/spongycastle/jce/provider/AnnotatedException
    //   344: dup
    //   345: ldc_w 304
    //   348: aload 14
    //   350: invokespecial 257	org/spongycastle/jce/provider/AnnotatedException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   353: athrow
    //   354: astore 13
    //   356: new 103	org/spongycastle/jce/provider/AnnotatedException
    //   359: dup
    //   360: ldc_w 256
    //   363: aload 13
    //   365: invokespecial 257	org/spongycastle/jce/provider/AnnotatedException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   368: astore 10
    //   370: goto -49 -> 321
    //   373: aload 8
    //   375: invokevirtual 136	org/spongycastle/jce/provider/CertStatus:getCertStatus	()I
    //   378: bipush 11
    //   380: if_icmpeq +67 -> 447
    //   383: new 79	java/lang/StringBuilder
    //   386: dup
    //   387: ldc_w 306
    //   390: invokespecial 84	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   393: aload 8
    //   395: invokevirtual 309	org/spongycastle/jce/provider/CertStatus:getRevocationDate	()Ljava/util/Date;
    //   398: invokevirtual 312	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   401: invokevirtual 93	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   404: astore 12
    //   406: new 47	java/security/cert/CertPathValidatorException
    //   409: dup
    //   410: new 79	java/lang/StringBuilder
    //   413: dup
    //   414: invokespecial 313	java/lang/StringBuilder:<init>	()V
    //   417: aload 12
    //   419: invokevirtual 88	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   422: ldc_w 315
    //   425: invokevirtual 88	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   428: getstatic 319	org/spongycastle/jce/provider/RFC3280CertPathUtilities:crlReasons	[Ljava/lang/String;
    //   431: aload 8
    //   433: invokevirtual 136	org/spongycastle/jce/provider/CertStatus:getCertStatus	()I
    //   436: aaload
    //   437: invokevirtual 88	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   440: invokevirtual 93	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   443: invokespecial 94	java/security/cert/CertPathValidatorException:<init>	(Ljava/lang/String;)V
    //   446: athrow
    //   447: aload 9
    //   449: invokevirtual 141	org/spongycastle/jce/provider/ReasonsMask:isAllReasons	()Z
    //   452: ifne +20 -> 472
    //   455: aload 8
    //   457: invokevirtual 136	org/spongycastle/jce/provider/CertStatus:getCertStatus	()I
    //   460: bipush 11
    //   462: if_icmpne +10 -> 472
    //   465: aload 8
    //   467: bipush 12
    //   469: invokevirtual 207	org/spongycastle/jce/provider/CertStatus:setCertStatus	(I)V
    //   472: aload 8
    //   474: invokevirtual 136	org/spongycastle/jce/provider/CertStatus:getCertStatus	()I
    //   477: bipush 12
    //   479: if_icmpne +49 -> 528
    //   482: new 47	java/security/cert/CertPathValidatorException
    //   485: dup
    //   486: ldc_w 321
    //   489: invokespecial 94	java/security/cert/CertPathValidatorException:<init>	(Ljava/lang/String;)V
    //   492: athrow
    //   493: aload_0
    //   494: getstatic 35	org/spongycastle/jce/provider/RFC3281CertPathUtilities:CRL_DISTRIBUTION_POINTS	Ljava/lang/String;
    //   497: invokeinterface 107 2 0
    //   502: ifnonnull +15 -> 517
    //   505: aload_0
    //   506: getstatic 40	org/spongycastle/jce/provider/RFC3281CertPathUtilities:AUTHORITY_INFO_ACCESS	Ljava/lang/String;
    //   509: invokeinterface 107 2 0
    //   514: ifnull +14 -> 528
    //   517: new 47	java/security/cert/CertPathValidatorException
    //   520: dup
    //   521: ldc_w 323
    //   524: invokespecial 94	java/security/cert/CertPathValidatorException:<init>	(Ljava/lang/String;)V
    //   527: athrow
    //   528: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	529	0	paramX509AttributeCertificate	X509AttributeCertificate
    //   0	529	1	paramExtendedPKIXParameters	ExtendedPKIXParameters
    //   0	529	2	paramX509Certificate	X509Certificate
    //   0	529	3	paramDate	Date
    //   0	529	4	paramList	List
    //   160	9	5	localAnnotatedException1	AnnotatedException
    //   29	38	6	localCRLDistPoint	org.spongycastle.asn1.x509.CRLDistPoint
    //   174	9	7	localAnnotatedException2	AnnotatedException
    //   44	429	8	localCertStatus	CertStatus
    //   53	395	9	localReasonsMask	ReasonsMask
    //   56	313	10	localAnnotatedException3	AnnotatedException
    //   59	263	11	i	int
    //   404	14	12	str	String
    //   354	10	13	localAnnotatedException4	AnnotatedException
    //   339	10	14	localException1	java.lang.Exception
    //   263	21	15	localASN1Primitive	org.spongycastle.asn1.ASN1Primitive
    //   188	9	16	localException2	java.lang.Exception
    //   71	62	17	arrayOfDistributionPoint	DistributionPoint[]
    //   74	81	18	j	int
    //   202	10	19	localAnnotatedException5	AnnotatedException
    //   79	10	20	k	int
    //   96	10	21	m	int
    //   113	6	22	bool	boolean
    //   130	9	23	localExtendedPKIXParameters	ExtendedPKIXParameters
    // Exception table:
    //   from	to	target	type
    //   19	31	160	org/spongycastle/jce/provider/AnnotatedException
    //   31	37	174	org/spongycastle/jce/provider/AnnotatedException
    //   66	73	188	java/lang/Exception
    //   76	81	202	org/spongycastle/jce/provider/AnnotatedException
    //   91	98	202	org/spongycastle/jce/provider/AnnotatedException
    //   108	115	202	org/spongycastle/jce/provider/AnnotatedException
    //   123	151	202	org/spongycastle/jce/provider/AnnotatedException
    //   236	265	339	java/lang/Exception
    //   236	265	354	org/spongycastle/jce/provider/AnnotatedException
    //   265	318	354	org/spongycastle/jce/provider/AnnotatedException
    //   341	354	354	org/spongycastle/jce/provider/AnnotatedException
  }
  
  /* Error */
  protected static CertPath processAttrCert1(X509AttributeCertificate paramX509AttributeCertificate, ExtendedPKIXParameters paramExtendedPKIXParameters)
    throws CertPathValidatorException
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: new 337	java/util/HashSet
    //   5: dup
    //   6: invokespecial 338	java/util/HashSet:<init>	()V
    //   9: astore_3
    //   10: aload_0
    //   11: invokeinterface 342 1 0
    //   16: invokevirtual 346	org/spongycastle/x509/AttributeCertificateHolder:getIssuer	()[Ljava/security/Principal;
    //   19: ifnull +147 -> 166
    //   22: new 348	org/spongycastle/x509/X509CertStoreSelector
    //   25: dup
    //   26: invokespecial 349	org/spongycastle/x509/X509CertStoreSelector:<init>	()V
    //   29: astore 4
    //   31: aload 4
    //   33: aload_0
    //   34: invokeinterface 342 1 0
    //   39: invokevirtual 353	org/spongycastle/x509/AttributeCertificateHolder:getSerialNumber	()Ljava/math/BigInteger;
    //   42: invokevirtual 357	org/spongycastle/x509/X509CertStoreSelector:setSerialNumber	(Ljava/math/BigInteger;)V
    //   45: aload_0
    //   46: invokeinterface 342 1 0
    //   51: invokevirtual 346	org/spongycastle/x509/AttributeCertificateHolder:getIssuer	()[Ljava/security/Principal;
    //   54: astore 5
    //   56: iconst_0
    //   57: istore 6
    //   59: iload 6
    //   61: aload 5
    //   63: arraylength
    //   64: if_icmpge +82 -> 146
    //   67: aload 5
    //   69: iload 6
    //   71: aaload
    //   72: instanceof 271
    //   75: ifeq +19 -> 94
    //   78: aload 4
    //   80: aload 5
    //   82: iload 6
    //   84: aaload
    //   85: checkcast 271	javax/security/auth/x500/X500Principal
    //   88: invokevirtual 275	javax/security/auth/x500/X500Principal:getEncoded	()[B
    //   91: invokevirtual 360	org/spongycastle/x509/X509CertStoreSelector:setIssuer	([B)V
    //   94: aload_3
    //   95: aload 4
    //   97: aload_1
    //   98: invokevirtual 364	org/spongycastle/x509/ExtendedPKIXParameters:getStores	()Ljava/util/List;
    //   101: invokestatic 368	org/spongycastle/jce/provider/CertPathValidatorUtilities:findCertificates	(Lorg/spongycastle/x509/X509CertStoreSelector;Ljava/util/List;)Ljava/util/Collection;
    //   104: invokeinterface 372 2 0
    //   109: pop
    //   110: iinc 6 1
    //   113: goto -54 -> 59
    //   116: astore 24
    //   118: new 251	org/spongycastle/jce/exception/ExtCertPathValidatorException
    //   121: dup
    //   122: ldc_w 374
    //   125: aload 24
    //   127: invokespecial 254	org/spongycastle/jce/exception/ExtCertPathValidatorException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   130: athrow
    //   131: astore 23
    //   133: new 251	org/spongycastle/jce/exception/ExtCertPathValidatorException
    //   136: dup
    //   137: ldc_w 376
    //   140: aload 23
    //   142: invokespecial 254	org/spongycastle/jce/exception/ExtCertPathValidatorException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   145: athrow
    //   146: aload_3
    //   147: invokeinterface 379 1 0
    //   152: ifeq +14 -> 166
    //   155: new 47	java/security/cert/CertPathValidatorException
    //   158: dup
    //   159: ldc_w 381
    //   162: invokespecial 94	java/security/cert/CertPathValidatorException:<init>	(Ljava/lang/String;)V
    //   165: athrow
    //   166: aload_0
    //   167: invokeinterface 342 1 0
    //   172: invokevirtual 384	org/spongycastle/x509/AttributeCertificateHolder:getEntityNames	()[Ljava/security/Principal;
    //   175: ifnull +133 -> 308
    //   178: new 348	org/spongycastle/x509/X509CertStoreSelector
    //   181: dup
    //   182: invokespecial 349	org/spongycastle/x509/X509CertStoreSelector:<init>	()V
    //   185: astore 7
    //   187: aload_0
    //   188: invokeinterface 342 1 0
    //   193: invokevirtual 384	org/spongycastle/x509/AttributeCertificateHolder:getEntityNames	()[Ljava/security/Principal;
    //   196: astore 8
    //   198: iconst_0
    //   199: istore 9
    //   201: iload 9
    //   203: aload 8
    //   205: arraylength
    //   206: if_icmpge +82 -> 288
    //   209: aload 8
    //   211: iload 9
    //   213: aaload
    //   214: instanceof 271
    //   217: ifeq +19 -> 236
    //   220: aload 7
    //   222: aload 8
    //   224: iload 9
    //   226: aaload
    //   227: checkcast 271	javax/security/auth/x500/X500Principal
    //   230: invokevirtual 275	javax/security/auth/x500/X500Principal:getEncoded	()[B
    //   233: invokevirtual 360	org/spongycastle/x509/X509CertStoreSelector:setIssuer	([B)V
    //   236: aload_3
    //   237: aload 7
    //   239: aload_1
    //   240: invokevirtual 364	org/spongycastle/x509/ExtendedPKIXParameters:getStores	()Ljava/util/List;
    //   243: invokestatic 368	org/spongycastle/jce/provider/CertPathValidatorUtilities:findCertificates	(Lorg/spongycastle/x509/X509CertStoreSelector;Ljava/util/List;)Ljava/util/Collection;
    //   246: invokeinterface 372 2 0
    //   251: pop
    //   252: iinc 9 1
    //   255: goto -54 -> 201
    //   258: astore 21
    //   260: new 251	org/spongycastle/jce/exception/ExtCertPathValidatorException
    //   263: dup
    //   264: ldc_w 374
    //   267: aload 21
    //   269: invokespecial 254	org/spongycastle/jce/exception/ExtCertPathValidatorException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   272: athrow
    //   273: astore 20
    //   275: new 251	org/spongycastle/jce/exception/ExtCertPathValidatorException
    //   278: dup
    //   279: ldc_w 376
    //   282: aload 20
    //   284: invokespecial 254	org/spongycastle/jce/exception/ExtCertPathValidatorException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   287: athrow
    //   288: aload_3
    //   289: invokeinterface 379 1 0
    //   294: ifeq +14 -> 308
    //   297: new 47	java/security/cert/CertPathValidatorException
    //   300: dup
    //   301: ldc_w 386
    //   304: invokespecial 94	java/security/cert/CertPathValidatorException:<init>	(Ljava/lang/String;)V
    //   307: athrow
    //   308: aload_1
    //   309: invokestatic 391	org/spongycastle/x509/ExtendedPKIXBuilderParameters:getInstance	(Ljava/security/cert/PKIXParameters;)Lorg/spongycastle/x509/ExtendedPKIXParameters;
    //   312: checkcast 388	org/spongycastle/x509/ExtendedPKIXBuilderParameters
    //   315: astore 10
    //   317: aconst_null
    //   318: astore 11
    //   320: aload_3
    //   321: invokeinterface 59 1 0
    //   326: astore 12
    //   328: aload 12
    //   330: invokeinterface 65 1 0
    //   335: ifeq +127 -> 462
    //   338: new 348	org/spongycastle/x509/X509CertStoreSelector
    //   341: dup
    //   342: invokespecial 349	org/spongycastle/x509/X509CertStoreSelector:<init>	()V
    //   345: astore 13
    //   347: aload 13
    //   349: aload 12
    //   351: invokeinterface 69 1 0
    //   356: checkcast 393	java/security/cert/X509Certificate
    //   359: invokevirtual 397	org/spongycastle/x509/X509CertStoreSelector:setCertificate	(Ljava/security/cert/X509Certificate;)V
    //   362: aload 10
    //   364: aload 13
    //   366: invokevirtual 401	org/spongycastle/x509/ExtendedPKIXBuilderParameters:setTargetConstraints	(Lorg/spongycastle/util/Selector;)V
    //   369: ldc_w 403
    //   372: getstatic 408	org/spongycastle/jce/provider/BouncyCastleProvider:PROVIDER_NAME	Ljava/lang/String;
    //   375: invokestatic 413	java/security/cert/CertPathBuilder:getInstance	(Ljava/lang/String;Ljava/lang/String;)Ljava/security/cert/CertPathBuilder;
    //   378: astore 16
    //   380: aload 16
    //   382: aload 10
    //   384: invokestatic 391	org/spongycastle/x509/ExtendedPKIXBuilderParameters:getInstance	(Ljava/security/cert/PKIXParameters;)Lorg/spongycastle/x509/ExtendedPKIXParameters;
    //   387: invokevirtual 417	java/security/cert/CertPathBuilder:build	(Ljava/security/cert/CertPathParameters;)Ljava/security/cert/CertPathBuilderResult;
    //   390: astore 19
    //   392: aload 19
    //   394: astore_2
    //   395: goto -67 -> 328
    //   398: astore 15
    //   400: new 251	org/spongycastle/jce/exception/ExtCertPathValidatorException
    //   403: dup
    //   404: ldc_w 419
    //   407: aload 15
    //   409: invokespecial 254	org/spongycastle/jce/exception/ExtCertPathValidatorException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   412: athrow
    //   413: astore 14
    //   415: new 251	org/spongycastle/jce/exception/ExtCertPathValidatorException
    //   418: dup
    //   419: ldc_w 419
    //   422: aload 14
    //   424: invokespecial 254	org/spongycastle/jce/exception/ExtCertPathValidatorException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   427: athrow
    //   428: astore 18
    //   430: new 251	org/spongycastle/jce/exception/ExtCertPathValidatorException
    //   433: dup
    //   434: ldc_w 421
    //   437: aload 18
    //   439: invokespecial 254	org/spongycastle/jce/exception/ExtCertPathValidatorException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   442: astore 11
    //   444: goto -116 -> 328
    //   447: astore 17
    //   449: new 423	java/lang/RuntimeException
    //   452: dup
    //   453: aload 17
    //   455: invokevirtual 426	java/security/InvalidAlgorithmParameterException:getMessage	()Ljava/lang/String;
    //   458: invokespecial 427	java/lang/RuntimeException:<init>	(Ljava/lang/String;)V
    //   461: athrow
    //   462: aload 11
    //   464: ifnull +6 -> 470
    //   467: aload 11
    //   469: athrow
    //   470: aload_2
    //   471: invokeinterface 433 1 0
    //   476: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	477	0	paramX509AttributeCertificate	X509AttributeCertificate
    //   0	477	1	paramExtendedPKIXParameters	ExtendedPKIXParameters
    //   1	470	2	localObject	Object
    //   9	312	3	localHashSet	java.util.HashSet
    //   29	67	4	localX509CertStoreSelector1	org.spongycastle.x509.X509CertStoreSelector
    //   54	27	5	arrayOfPrincipal1	java.security.Principal[]
    //   57	54	6	i	int
    //   185	53	7	localX509CertStoreSelector2	org.spongycastle.x509.X509CertStoreSelector
    //   196	27	8	arrayOfPrincipal2	java.security.Principal[]
    //   199	54	9	j	int
    //   315	68	10	localExtendedPKIXBuilderParameters	org.spongycastle.x509.ExtendedPKIXBuilderParameters
    //   318	150	11	localExtCertPathValidatorException	ExtCertPathValidatorException
    //   326	24	12	localIterator	Iterator
    //   345	20	13	localX509CertStoreSelector3	org.spongycastle.x509.X509CertStoreSelector
    //   413	10	14	localNoSuchAlgorithmException	java.security.NoSuchAlgorithmException
    //   398	10	15	localNoSuchProviderException	java.security.NoSuchProviderException
    //   378	3	16	localCertPathBuilder	java.security.cert.CertPathBuilder
    //   447	7	17	localInvalidAlgorithmParameterException	java.security.InvalidAlgorithmParameterException
    //   428	10	18	localCertPathBuilderException	java.security.cert.CertPathBuilderException
    //   390	3	19	localCertPathBuilderResult	java.security.cert.CertPathBuilderResult
    //   273	10	20	localIOException1	java.io.IOException
    //   258	10	21	localAnnotatedException1	AnnotatedException
    //   131	10	23	localIOException2	java.io.IOException
    //   116	10	24	localAnnotatedException2	AnnotatedException
    // Exception table:
    //   from	to	target	type
    //   67	94	116	org/spongycastle/jce/provider/AnnotatedException
    //   94	110	116	org/spongycastle/jce/provider/AnnotatedException
    //   67	94	131	java/io/IOException
    //   94	110	131	java/io/IOException
    //   209	236	258	org/spongycastle/jce/provider/AnnotatedException
    //   236	252	258	org/spongycastle/jce/provider/AnnotatedException
    //   209	236	273	java/io/IOException
    //   236	252	273	java/io/IOException
    //   369	380	398	java/security/NoSuchProviderException
    //   369	380	413	java/security/NoSuchAlgorithmException
    //   380	392	428	java/security/cert/CertPathBuilderException
    //   380	392	447	java/security/InvalidAlgorithmParameterException
  }
  
  /* Error */
  protected static java.security.cert.CertPathValidatorResult processAttrCert2(CertPath paramCertPath, ExtendedPKIXParameters paramExtendedPKIXParameters)
    throws CertPathValidatorException
  {
    // Byte code:
    //   0: ldc_w 403
    //   3: getstatic 408	org/spongycastle/jce/provider/BouncyCastleProvider:PROVIDER_NAME	Ljava/lang/String;
    //   6: invokestatic 440	java/security/cert/CertPathValidator:getInstance	(Ljava/lang/String;Ljava/lang/String;)Ljava/security/cert/CertPathValidator;
    //   9: astore 4
    //   11: aload 4
    //   13: aload_0
    //   14: aload_1
    //   15: invokevirtual 444	java/security/cert/CertPathValidator:validate	(Ljava/security/cert/CertPath;Ljava/security/cert/CertPathParameters;)Ljava/security/cert/CertPathValidatorResult;
    //   18: astore 7
    //   20: aload 7
    //   22: areturn
    //   23: astore_3
    //   24: new 251	org/spongycastle/jce/exception/ExtCertPathValidatorException
    //   27: dup
    //   28: ldc_w 419
    //   31: aload_3
    //   32: invokespecial 254	org/spongycastle/jce/exception/ExtCertPathValidatorException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   35: athrow
    //   36: astore_2
    //   37: new 251	org/spongycastle/jce/exception/ExtCertPathValidatorException
    //   40: dup
    //   41: ldc_w 419
    //   44: aload_2
    //   45: invokespecial 254	org/spongycastle/jce/exception/ExtCertPathValidatorException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   48: athrow
    //   49: astore 6
    //   51: new 251	org/spongycastle/jce/exception/ExtCertPathValidatorException
    //   54: dup
    //   55: ldc_w 446
    //   58: aload 6
    //   60: invokespecial 254	org/spongycastle/jce/exception/ExtCertPathValidatorException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   63: athrow
    //   64: astore 5
    //   66: new 423	java/lang/RuntimeException
    //   69: dup
    //   70: aload 5
    //   72: invokevirtual 426	java/security/InvalidAlgorithmParameterException:getMessage	()Ljava/lang/String;
    //   75: invokespecial 427	java/lang/RuntimeException:<init>	(Ljava/lang/String;)V
    //   78: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	79	0	paramCertPath	CertPath
    //   0	79	1	paramExtendedPKIXParameters	ExtendedPKIXParameters
    //   36	9	2	localNoSuchAlgorithmException	java.security.NoSuchAlgorithmException
    //   23	9	3	localNoSuchProviderException	java.security.NoSuchProviderException
    //   9	3	4	localCertPathValidator	java.security.cert.CertPathValidator
    //   64	7	5	localInvalidAlgorithmParameterException	java.security.InvalidAlgorithmParameterException
    //   49	10	6	localCertPathValidatorException	CertPathValidatorException
    //   18	3	7	localCertPathValidatorResult	java.security.cert.CertPathValidatorResult
    // Exception table:
    //   from	to	target	type
    //   0	11	23	java/security/NoSuchProviderException
    //   0	11	36	java/security/NoSuchAlgorithmException
    //   11	20	49	java/security/cert/CertPathValidatorException
    //   11	20	64	java/security/InvalidAlgorithmParameterException
  }
  
  protected static void processAttrCert3(X509Certificate paramX509Certificate, ExtendedPKIXParameters paramExtendedPKIXParameters)
    throws CertPathValidatorException
  {
    if ((paramX509Certificate.getKeyUsage() != null) && (paramX509Certificate.getKeyUsage()[0] == 0) && (paramX509Certificate.getKeyUsage()[1] == 0)) {
      throw new CertPathValidatorException("Attribute certificate issuer public key cannot be used to validate digital signatures.");
    }
    if (paramX509Certificate.getBasicConstraints() != -1) {
      throw new CertPathValidatorException("Attribute certificate issuer is also a public key certificate issuer.");
    }
  }
  
  protected static void processAttrCert4(X509Certificate paramX509Certificate, ExtendedPKIXParameters paramExtendedPKIXParameters)
    throws CertPathValidatorException
  {
    Set localSet = paramExtendedPKIXParameters.getTrustedACIssuers();
    int i = 0;
    Iterator localIterator = localSet.iterator();
    while (localIterator.hasNext())
    {
      TrustAnchor localTrustAnchor = (TrustAnchor)localIterator.next();
      if ((paramX509Certificate.getSubjectX500Principal().getName("RFC2253").equals(localTrustAnchor.getCAName())) || (paramX509Certificate.equals(localTrustAnchor.getTrustedCert()))) {
        i = 1;
      }
    }
    if (i == 0) {
      throw new CertPathValidatorException("Attribute certificate issuer is not directly trusted.");
    }
  }
  
  protected static void processAttrCert5(X509AttributeCertificate paramX509AttributeCertificate, ExtendedPKIXParameters paramExtendedPKIXParameters)
    throws CertPathValidatorException
  {
    try
    {
      paramX509AttributeCertificate.checkValidity(CertPathValidatorUtilities.getValidDate(paramExtendedPKIXParameters));
      return;
    }
    catch (CertificateExpiredException localCertificateExpiredException)
    {
      throw new ExtCertPathValidatorException("Attribute certificate is not valid.", localCertificateExpiredException);
    }
    catch (CertificateNotYetValidException localCertificateNotYetValidException)
    {
      throw new ExtCertPathValidatorException("Attribute certificate is not valid.", localCertificateNotYetValidException);
    }
  }
  
  protected static void processAttrCert7(X509AttributeCertificate paramX509AttributeCertificate, CertPath paramCertPath1, CertPath paramCertPath2, ExtendedPKIXParameters paramExtendedPKIXParameters)
    throws CertPathValidatorException
  {
    Set localSet = paramX509AttributeCertificate.getCriticalExtensionOIDs();
    if (localSet.contains(TARGET_INFORMATION)) {}
    try
    {
      TargetInformation.getInstance(CertPathValidatorUtilities.getExtensionValue(paramX509AttributeCertificate, TARGET_INFORMATION));
      localSet.remove(TARGET_INFORMATION);
      Iterator localIterator = paramExtendedPKIXParameters.getAttrCertCheckers().iterator();
      while (localIterator.hasNext()) {
        ((PKIXAttrCertChecker)localIterator.next()).check(paramX509AttributeCertificate, paramCertPath1, paramCertPath2, localSet);
      }
      if (localSet.isEmpty()) {
        return;
      }
    }
    catch (AnnotatedException localAnnotatedException)
    {
      throw new ExtCertPathValidatorException("Target information extension could not be read.", localAnnotatedException);
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      throw new ExtCertPathValidatorException("Target information extension could not be read.", localIllegalArgumentException);
    }
    throw new CertPathValidatorException("Attribute certificate contains unsupported critical extensions: " + localSet);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jce.provider.RFC3281CertPathUtilities
 * JD-Core Version:    0.7.0.1
 */