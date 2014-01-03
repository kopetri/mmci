package org.spongycastle.x509;

import java.io.IOException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.security.PublicKey;
import java.security.cert.CertPath;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertificateFactory;
import java.security.cert.PKIXCertPathChecker;
import java.security.cert.PKIXParameters;
import java.security.cert.PolicyNode;
import java.security.cert.TrustAnchor;
import java.security.cert.X509CRL;
import java.security.cert.X509CertSelector;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import javax.security.auth.x500.X500Principal;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERIA5String;
import org.spongycastle.asn1.DERObjectIdentifier;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.x509.AccessDescription;
import org.spongycastle.asn1.x509.AuthorityInformationAccess;
import org.spongycastle.asn1.x509.AuthorityKeyIdentifier;
import org.spongycastle.asn1.x509.BasicConstraints;
import org.spongycastle.asn1.x509.CRLDistPoint;
import org.spongycastle.asn1.x509.DistributionPoint;
import org.spongycastle.asn1.x509.DistributionPointName;
import org.spongycastle.asn1.x509.GeneralName;
import org.spongycastle.asn1.x509.GeneralNames;
import org.spongycastle.asn1.x509.X509Extensions;
import org.spongycastle.asn1.x509.qualified.Iso4217CurrencyCode;
import org.spongycastle.asn1.x509.qualified.MonetaryValue;
import org.spongycastle.asn1.x509.qualified.QCStatement;
import org.spongycastle.i18n.ErrorBundle;
import org.spongycastle.i18n.filter.TrustedInput;
import org.spongycastle.i18n.filter.UntrustedInput;
import org.spongycastle.jce.provider.AnnotatedException;
import org.spongycastle.jce.provider.CertPathValidatorUtilities;

public class PKIXCertPathReviewer
  extends CertPathValidatorUtilities
{
  private static final String AUTH_INFO_ACCESS = X509Extensions.AuthorityInfoAccess.getId();
  private static final String CRL_DIST_POINTS;
  private static final String QC_STATEMENT = X509Extensions.QCStatements.getId();
  private static final String RESOURCE_NAME = "org.spongycastle.x509.CertPathReviewerMessages";
  protected CertPath certPath;
  protected List certs;
  protected List[] errors;
  private boolean initialized;
  protected int n;
  protected List[] notifications;
  protected PKIXParameters pkixParams;
  protected PolicyNode policyTree;
  protected PublicKey subjectPublicKey;
  protected TrustAnchor trustAnchor;
  protected Date validDate;
  
  static
  {
    CRL_DIST_POINTS = X509Extensions.CRLDistributionPoints.getId();
  }
  
  public PKIXCertPathReviewer() {}
  
  public PKIXCertPathReviewer(CertPath paramCertPath, PKIXParameters paramPKIXParameters)
    throws CertPathReviewerException
  {
    init(paramCertPath, paramPKIXParameters);
  }
  
  private String IPtoString(byte[] paramArrayOfByte)
  {
    try
    {
      String str = InetAddress.getByAddress(paramArrayOfByte).getHostAddress();
      return str;
    }
    catch (Exception localException)
    {
      StringBuffer localStringBuffer = new StringBuffer();
      for (int i = 0; i != paramArrayOfByte.length; i++)
      {
        localStringBuffer.append(Integer.toHexString(0xFF & paramArrayOfByte[i]));
        localStringBuffer.append(' ');
      }
      return localStringBuffer.toString();
    }
  }
  
  private void checkCriticalExtensions()
  {
    List localList = this.pkixParams.getCertPathCheckers();
    Iterator localIterator1 = localList.iterator();
    try
    {
      while (localIterator1.hasNext())
      {
        ((PKIXCertPathChecker)localIterator1.next()).init(false);
        continue;
        Object[] arrayOfObject1;
        return;
      }
    }
    catch (CertPathValidatorException localCertPathValidatorException1)
    {
      arrayOfObject1 = new Object[3];
      arrayOfObject1[0] = localCertPathValidatorException1.getMessage();
      arrayOfObject1[1] = localCertPathValidatorException1;
      arrayOfObject1[2] = localCertPathValidatorException1.getClass().getName();
      throw new CertPathReviewerException(new ErrorBundle("org.spongycastle.x509.CertPathReviewerMessages", "CertPathReviewer.certPathCheckerError", arrayOfObject1), localCertPathValidatorException1);
    }
    catch (CertPathReviewerException localCertPathReviewerException)
    {
      addError(localCertPathReviewerException.getErrorMessage(), localCertPathReviewerException.getIndex());
    }
    for (;;)
    {
      for (int i = -1 + this.certs.size(); i >= 0; i--)
      {
        X509Certificate localX509Certificate = (X509Certificate)this.certs.get(i);
        Set localSet = localX509Certificate.getCriticalExtensionOIDs();
        if ((localSet != null) && (!localSet.isEmpty()))
        {
          localSet.remove(KEY_USAGE);
          localSet.remove(CERTIFICATE_POLICIES);
          localSet.remove(POLICY_MAPPINGS);
          localSet.remove(INHIBIT_ANY_POLICY);
          localSet.remove(ISSUING_DISTRIBUTION_POINT);
          localSet.remove(DELTA_CRL_INDICATOR);
          localSet.remove(POLICY_CONSTRAINTS);
          localSet.remove(BASIC_CONSTRAINTS);
          localSet.remove(SUBJECT_ALTERNATIVE_NAME);
          localSet.remove(NAME_CONSTRAINTS);
          if ((localSet.contains(QC_STATEMENT)) && (processQcStatements(localX509Certificate, i))) {
            localSet.remove(QC_STATEMENT);
          }
          Iterator localIterator2 = localList.iterator();
          for (;;)
          {
            boolean bool = localIterator2.hasNext();
            if (bool) {
              try
              {
                ((PKIXCertPathChecker)localIterator2.next()).check(localX509Certificate, localSet);
              }
              catch (CertPathValidatorException localCertPathValidatorException2)
              {
                Object[] arrayOfObject3 = new Object[3];
                arrayOfObject3[0] = localCertPathValidatorException2.getMessage();
                arrayOfObject3[1] = localCertPathValidatorException2;
                arrayOfObject3[2] = localCertPathValidatorException2.getClass().getName();
                throw new CertPathReviewerException(new ErrorBundle("org.spongycastle.x509.CertPathReviewerMessages", "CertPathReviewer.criticalExtensionError", arrayOfObject3), localCertPathValidatorException2.getCause(), this.certPath, i);
              }
            }
          }
          if (!localSet.isEmpty())
          {
            Iterator localIterator3 = localSet.iterator();
            while (localIterator3.hasNext())
            {
              Object[] arrayOfObject2 = new Object[1];
              DERObjectIdentifier localDERObjectIdentifier = new DERObjectIdentifier((String)localIterator3.next());
              arrayOfObject2[0] = localDERObjectIdentifier;
              addError(new ErrorBundle("org.spongycastle.x509.CertPathReviewerMessages", "CertPathReviewer.unknownCriticalExt", arrayOfObject2), i);
            }
          }
        }
      }
    }
  }
  
  /* Error */
  private void checkNameConstraints()
  {
    // Byte code:
    //   0: new 262	org/spongycastle/jce/provider/PKIXNameConstraintValidator
    //   3: dup
    //   4: invokespecial 263	org/spongycastle/jce/provider/PKIXNameConstraintValidator:<init>	()V
    //   7: astore_1
    //   8: iconst_m1
    //   9: aload_0
    //   10: getfield 169	org/spongycastle/x509/PKIXCertPathReviewer:certs	Ljava/util/List;
    //   13: invokeinterface 172 1 0
    //   18: iadd
    //   19: istore_3
    //   20: iload_3
    //   21: ifle +217 -> 238
    //   24: aload_0
    //   25: getfield 265	org/spongycastle/x509/PKIXCertPathReviewer:n	I
    //   28: pop
    //   29: aload_0
    //   30: getfield 169	org/spongycastle/x509/PKIXCertPathReviewer:certs	Ljava/util/List;
    //   33: iload_3
    //   34: invokeinterface 176 2 0
    //   39: checkcast 178	java/security/cert/X509Certificate
    //   42: astore 5
    //   44: aload 5
    //   46: invokestatic 269	org/spongycastle/x509/PKIXCertPathReviewer:isSelfIssued	(Ljava/security/cert/X509Certificate;)Z
    //   49: ifne +411 -> 460
    //   52: aload 5
    //   54: invokestatic 273	org/spongycastle/x509/PKIXCertPathReviewer:getSubjectPrincipal	(Ljava/security/cert/X509Certificate;)Ljavax/security/auth/x500/X500Principal;
    //   57: astore 14
    //   59: new 275	org/spongycastle/asn1/ASN1InputStream
    //   62: dup
    //   63: new 277	java/io/ByteArrayInputStream
    //   66: dup
    //   67: aload 14
    //   69: invokevirtual 283	javax/security/auth/x500/X500Principal:getEncoded	()[B
    //   72: invokespecial 286	java/io/ByteArrayInputStream:<init>	([B)V
    //   75: invokespecial 289	org/spongycastle/asn1/ASN1InputStream:<init>	(Ljava/io/InputStream;)V
    //   78: astore 15
    //   80: aload 15
    //   82: invokevirtual 293	org/spongycastle/asn1/ASN1InputStream:readObject	()Lorg/spongycastle/asn1/ASN1Primitive;
    //   85: checkcast 295	org/spongycastle/asn1/ASN1Sequence
    //   88: astore 21
    //   90: aload_1
    //   91: aload 21
    //   93: invokevirtual 299	org/spongycastle/jce/provider/PKIXNameConstraintValidator:checkPermittedDN	(Lorg/spongycastle/asn1/ASN1Sequence;)V
    //   96: aload_1
    //   97: aload 21
    //   99: invokevirtual 302	org/spongycastle/jce/provider/PKIXNameConstraintValidator:checkExcludedDN	(Lorg/spongycastle/asn1/ASN1Sequence;)V
    //   102: aload 5
    //   104: getstatic 218	org/spongycastle/x509/PKIXCertPathReviewer:SUBJECT_ALTERNATIVE_NAME	Ljava/lang/String;
    //   107: invokestatic 306	org/spongycastle/x509/PKIXCertPathReviewer:getExtensionValue	(Ljava/security/cert/X509Extension;Ljava/lang/String;)Lorg/spongycastle/asn1/ASN1Primitive;
    //   110: checkcast 295	org/spongycastle/asn1/ASN1Sequence
    //   113: astore 33
    //   115: aload 33
    //   117: ifnull +343 -> 460
    //   120: iconst_0
    //   121: istore 34
    //   123: iload 34
    //   125: aload 33
    //   127: invokevirtual 307	org/spongycastle/asn1/ASN1Sequence:size	()I
    //   130: if_icmpge +330 -> 460
    //   133: aload 33
    //   135: iload 34
    //   137: invokevirtual 311	org/spongycastle/asn1/ASN1Sequence:getObjectAt	(I)Lorg/spongycastle/asn1/ASN1Encodable;
    //   140: invokestatic 317	org/spongycastle/asn1/x509/GeneralName:getInstance	(Ljava/lang/Object;)Lorg/spongycastle/asn1/x509/GeneralName;
    //   143: astore 35
    //   145: aload_1
    //   146: aload 35
    //   148: invokevirtual 321	org/spongycastle/jce/provider/PKIXNameConstraintValidator:checkPermitted	(Lorg/spongycastle/asn1/x509/GeneralName;)V
    //   151: aload_1
    //   152: aload 35
    //   154: invokevirtual 324	org/spongycastle/jce/provider/PKIXNameConstraintValidator:checkExcluded	(Lorg/spongycastle/asn1/x509/GeneralName;)V
    //   157: iinc 34 1
    //   160: goto -37 -> 123
    //   163: astore 16
    //   165: iconst_1
    //   166: anewarray 133	java/lang/Object
    //   169: astore 17
    //   171: new 326	org/spongycastle/i18n/filter/UntrustedInput
    //   174: dup
    //   175: aload 14
    //   177: invokespecial 329	org/spongycastle/i18n/filter/UntrustedInput:<init>	(Ljava/lang/Object;)V
    //   180: astore 18
    //   182: aload 17
    //   184: iconst_0
    //   185: aload 18
    //   187: aastore
    //   188: new 147	org/spongycastle/i18n/ErrorBundle
    //   191: dup
    //   192: ldc 11
    //   194: ldc_w 331
    //   197: aload 17
    //   199: invokespecial 152	org/spongycastle/i18n/ErrorBundle:<init>	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   202: astore 19
    //   204: new 64	org/spongycastle/x509/CertPathReviewerException
    //   207: dup
    //   208: aload 19
    //   210: aload 16
    //   212: aload_0
    //   213: getfield 240	org/spongycastle/x509/PKIXCertPathReviewer:certPath	Ljava/security/cert/CertPath;
    //   216: iload_3
    //   217: invokespecial 243	org/spongycastle/x509/CertPathReviewerException:<init>	(Lorg/spongycastle/i18n/ErrorBundle;Ljava/lang/Throwable;Ljava/security/cert/CertPath;I)V
    //   220: astore 20
    //   222: aload 20
    //   224: athrow
    //   225: astore_2
    //   226: aload_0
    //   227: aload_2
    //   228: invokevirtual 159	org/spongycastle/x509/CertPathReviewerException:getErrorMessage	()Lorg/spongycastle/i18n/ErrorBundle;
    //   231: aload_2
    //   232: invokevirtual 163	org/spongycastle/x509/CertPathReviewerException:getIndex	()I
    //   235: invokevirtual 167	org/spongycastle/x509/PKIXCertPathReviewer:addError	(Lorg/spongycastle/i18n/ErrorBundle;I)V
    //   238: return
    //   239: astore 22
    //   241: iconst_1
    //   242: anewarray 133	java/lang/Object
    //   245: astore 23
    //   247: aload 23
    //   249: iconst_0
    //   250: new 326	org/spongycastle/i18n/filter/UntrustedInput
    //   253: dup
    //   254: aload 14
    //   256: invokevirtual 332	javax/security/auth/x500/X500Principal:getName	()Ljava/lang/String;
    //   259: invokespecial 329	org/spongycastle/i18n/filter/UntrustedInput:<init>	(Ljava/lang/Object;)V
    //   262: aastore
    //   263: new 147	org/spongycastle/i18n/ErrorBundle
    //   266: dup
    //   267: ldc 11
    //   269: ldc_w 334
    //   272: aload 23
    //   274: invokespecial 152	org/spongycastle/i18n/ErrorBundle:<init>	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   277: astore 24
    //   279: new 64	org/spongycastle/x509/CertPathReviewerException
    //   282: dup
    //   283: aload 24
    //   285: aload 22
    //   287: aload_0
    //   288: getfield 240	org/spongycastle/x509/PKIXCertPathReviewer:certPath	Ljava/security/cert/CertPath;
    //   291: iload_3
    //   292: invokespecial 243	org/spongycastle/x509/CertPathReviewerException:<init>	(Lorg/spongycastle/i18n/ErrorBundle;Ljava/lang/Throwable;Ljava/security/cert/CertPath;I)V
    //   295: astore 25
    //   297: aload 25
    //   299: athrow
    //   300: astore 26
    //   302: iconst_1
    //   303: anewarray 133	java/lang/Object
    //   306: astore 27
    //   308: aload 27
    //   310: iconst_0
    //   311: new 326	org/spongycastle/i18n/filter/UntrustedInput
    //   314: dup
    //   315: aload 14
    //   317: invokevirtual 332	javax/security/auth/x500/X500Principal:getName	()Ljava/lang/String;
    //   320: invokespecial 329	org/spongycastle/i18n/filter/UntrustedInput:<init>	(Ljava/lang/Object;)V
    //   323: aastore
    //   324: new 147	org/spongycastle/i18n/ErrorBundle
    //   327: dup
    //   328: ldc 11
    //   330: ldc_w 336
    //   333: aload 27
    //   335: invokespecial 152	org/spongycastle/i18n/ErrorBundle:<init>	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   338: astore 28
    //   340: new 64	org/spongycastle/x509/CertPathReviewerException
    //   343: dup
    //   344: aload 28
    //   346: aload 26
    //   348: aload_0
    //   349: getfield 240	org/spongycastle/x509/PKIXCertPathReviewer:certPath	Ljava/security/cert/CertPath;
    //   352: iload_3
    //   353: invokespecial 243	org/spongycastle/x509/CertPathReviewerException:<init>	(Lorg/spongycastle/i18n/ErrorBundle;Ljava/lang/Throwable;Ljava/security/cert/CertPath;I)V
    //   356: astore 29
    //   358: aload 29
    //   360: athrow
    //   361: astore 30
    //   363: new 147	org/spongycastle/i18n/ErrorBundle
    //   366: dup
    //   367: ldc 11
    //   369: ldc_w 338
    //   372: invokespecial 341	org/spongycastle/i18n/ErrorBundle:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   375: astore 31
    //   377: new 64	org/spongycastle/x509/CertPathReviewerException
    //   380: dup
    //   381: aload 31
    //   383: aload 30
    //   385: aload_0
    //   386: getfield 240	org/spongycastle/x509/PKIXCertPathReviewer:certPath	Ljava/security/cert/CertPath;
    //   389: iload_3
    //   390: invokespecial 243	org/spongycastle/x509/CertPathReviewerException:<init>	(Lorg/spongycastle/i18n/ErrorBundle;Ljava/lang/Throwable;Ljava/security/cert/CertPath;I)V
    //   393: astore 32
    //   395: aload 32
    //   397: athrow
    //   398: astore 36
    //   400: iconst_1
    //   401: anewarray 133	java/lang/Object
    //   404: astore 37
    //   406: new 326	org/spongycastle/i18n/filter/UntrustedInput
    //   409: dup
    //   410: aload 35
    //   412: invokespecial 329	org/spongycastle/i18n/filter/UntrustedInput:<init>	(Ljava/lang/Object;)V
    //   415: astore 38
    //   417: aload 37
    //   419: iconst_0
    //   420: aload 38
    //   422: aastore
    //   423: new 147	org/spongycastle/i18n/ErrorBundle
    //   426: dup
    //   427: ldc 11
    //   429: ldc_w 343
    //   432: aload 37
    //   434: invokespecial 152	org/spongycastle/i18n/ErrorBundle:<init>	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   437: astore 39
    //   439: new 64	org/spongycastle/x509/CertPathReviewerException
    //   442: dup
    //   443: aload 39
    //   445: aload 36
    //   447: aload_0
    //   448: getfield 240	org/spongycastle/x509/PKIXCertPathReviewer:certPath	Ljava/security/cert/CertPath;
    //   451: iload_3
    //   452: invokespecial 243	org/spongycastle/x509/CertPathReviewerException:<init>	(Lorg/spongycastle/i18n/ErrorBundle;Ljava/lang/Throwable;Ljava/security/cert/CertPath;I)V
    //   455: astore 40
    //   457: aload 40
    //   459: athrow
    //   460: aload 5
    //   462: getstatic 221	org/spongycastle/x509/PKIXCertPathReviewer:NAME_CONSTRAINTS	Ljava/lang/String;
    //   465: invokestatic 306	org/spongycastle/x509/PKIXCertPathReviewer:getExtensionValue	(Ljava/security/cert/X509Extension;Ljava/lang/String;)Lorg/spongycastle/asn1/ASN1Primitive;
    //   468: checkcast 295	org/spongycastle/asn1/ASN1Sequence
    //   471: astore 9
    //   473: aload 9
    //   475: ifnull +111 -> 586
    //   478: aload 9
    //   480: invokestatic 348	org/spongycastle/asn1/x509/NameConstraints:getInstance	(Ljava/lang/Object;)Lorg/spongycastle/asn1/x509/NameConstraints;
    //   483: astore 10
    //   485: aload 10
    //   487: invokevirtual 352	org/spongycastle/asn1/x509/NameConstraints:getPermittedSubtrees	()Lorg/spongycastle/asn1/ASN1Sequence;
    //   490: astore 11
    //   492: aload 11
    //   494: ifnull +9 -> 503
    //   497: aload_1
    //   498: aload 11
    //   500: invokevirtual 355	org/spongycastle/jce/provider/PKIXNameConstraintValidator:intersectPermittedSubtree	(Lorg/spongycastle/asn1/ASN1Sequence;)V
    //   503: aload 10
    //   505: invokevirtual 358	org/spongycastle/asn1/x509/NameConstraints:getExcludedSubtrees	()Lorg/spongycastle/asn1/ASN1Sequence;
    //   508: astore 12
    //   510: aload 12
    //   512: ifnull +74 -> 586
    //   515: aload 12
    //   517: invokevirtual 362	org/spongycastle/asn1/ASN1Sequence:getObjects	()Ljava/util/Enumeration;
    //   520: astore 13
    //   522: aload 13
    //   524: invokeinterface 367 1 0
    //   529: ifeq +57 -> 586
    //   532: aload_1
    //   533: aload 13
    //   535: invokeinterface 370 1 0
    //   540: invokestatic 375	org/spongycastle/asn1/x509/GeneralSubtree:getInstance	(Ljava/lang/Object;)Lorg/spongycastle/asn1/x509/GeneralSubtree;
    //   543: invokevirtual 379	org/spongycastle/jce/provider/PKIXNameConstraintValidator:addExcludedSubtree	(Lorg/spongycastle/asn1/x509/GeneralSubtree;)V
    //   546: goto -24 -> 522
    //   549: astore 6
    //   551: new 147	org/spongycastle/i18n/ErrorBundle
    //   554: dup
    //   555: ldc 11
    //   557: ldc_w 381
    //   560: invokespecial 341	org/spongycastle/i18n/ErrorBundle:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   563: astore 7
    //   565: new 64	org/spongycastle/x509/CertPathReviewerException
    //   568: dup
    //   569: aload 7
    //   571: aload 6
    //   573: aload_0
    //   574: getfield 240	org/spongycastle/x509/PKIXCertPathReviewer:certPath	Ljava/security/cert/CertPath;
    //   577: iload_3
    //   578: invokespecial 243	org/spongycastle/x509/CertPathReviewerException:<init>	(Lorg/spongycastle/i18n/ErrorBundle;Ljava/lang/Throwable;Ljava/security/cert/CertPath;I)V
    //   581: astore 8
    //   583: aload 8
    //   585: athrow
    //   586: iinc 3 255
    //   589: goto -569 -> 20
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	592	0	this	PKIXCertPathReviewer
    //   7	526	1	localPKIXNameConstraintValidator	org.spongycastle.jce.provider.PKIXNameConstraintValidator
    //   225	7	2	localCertPathReviewerException1	CertPathReviewerException
    //   19	568	3	i	int
    //   42	419	5	localX509Certificate	X509Certificate
    //   549	23	6	localAnnotatedException1	AnnotatedException
    //   563	7	7	localErrorBundle1	ErrorBundle
    //   581	3	8	localCertPathReviewerException2	CertPathReviewerException
    //   471	8	9	localASN1Sequence1	ASN1Sequence
    //   483	21	10	localNameConstraints	org.spongycastle.asn1.x509.NameConstraints
    //   490	9	11	localASN1Sequence2	ASN1Sequence
    //   508	8	12	localASN1Sequence3	ASN1Sequence
    //   520	14	13	localEnumeration	java.util.Enumeration
    //   57	259	14	localX500Principal	X500Principal
    //   78	3	15	localASN1InputStream	org.spongycastle.asn1.ASN1InputStream
    //   163	48	16	localIOException	IOException
    //   169	29	17	arrayOfObject1	Object[]
    //   180	6	18	localUntrustedInput1	UntrustedInput
    //   202	7	19	localErrorBundle2	ErrorBundle
    //   220	3	20	localCertPathReviewerException3	CertPathReviewerException
    //   88	10	21	localASN1Sequence4	ASN1Sequence
    //   239	47	22	localPKIXNameConstraintValidatorException1	org.spongycastle.jce.provider.PKIXNameConstraintValidatorException
    //   245	28	23	arrayOfObject2	Object[]
    //   277	7	24	localErrorBundle3	ErrorBundle
    //   295	3	25	localCertPathReviewerException4	CertPathReviewerException
    //   300	47	26	localPKIXNameConstraintValidatorException2	org.spongycastle.jce.provider.PKIXNameConstraintValidatorException
    //   306	28	27	arrayOfObject3	Object[]
    //   338	7	28	localErrorBundle4	ErrorBundle
    //   356	3	29	localCertPathReviewerException5	CertPathReviewerException
    //   361	23	30	localAnnotatedException2	AnnotatedException
    //   375	7	31	localErrorBundle5	ErrorBundle
    //   393	3	32	localCertPathReviewerException6	CertPathReviewerException
    //   113	21	33	localASN1Sequence5	ASN1Sequence
    //   121	37	34	j	int
    //   143	268	35	localGeneralName	GeneralName
    //   398	48	36	localPKIXNameConstraintValidatorException3	org.spongycastle.jce.provider.PKIXNameConstraintValidatorException
    //   404	29	37	arrayOfObject4	Object[]
    //   415	6	38	localUntrustedInput2	UntrustedInput
    //   437	7	39	localErrorBundle6	ErrorBundle
    //   455	3	40	localCertPathReviewerException7	CertPathReviewerException
    // Exception table:
    //   from	to	target	type
    //   80	90	163	java/io/IOException
    //   8	20	225	org/spongycastle/x509/CertPathReviewerException
    //   24	80	225	org/spongycastle/x509/CertPathReviewerException
    //   80	90	225	org/spongycastle/x509/CertPathReviewerException
    //   90	96	225	org/spongycastle/x509/CertPathReviewerException
    //   96	102	225	org/spongycastle/x509/CertPathReviewerException
    //   102	115	225	org/spongycastle/x509/CertPathReviewerException
    //   123	145	225	org/spongycastle/x509/CertPathReviewerException
    //   145	157	225	org/spongycastle/x509/CertPathReviewerException
    //   165	225	225	org/spongycastle/x509/CertPathReviewerException
    //   241	300	225	org/spongycastle/x509/CertPathReviewerException
    //   302	361	225	org/spongycastle/x509/CertPathReviewerException
    //   363	398	225	org/spongycastle/x509/CertPathReviewerException
    //   400	460	225	org/spongycastle/x509/CertPathReviewerException
    //   460	473	225	org/spongycastle/x509/CertPathReviewerException
    //   478	492	225	org/spongycastle/x509/CertPathReviewerException
    //   497	503	225	org/spongycastle/x509/CertPathReviewerException
    //   503	510	225	org/spongycastle/x509/CertPathReviewerException
    //   515	522	225	org/spongycastle/x509/CertPathReviewerException
    //   522	546	225	org/spongycastle/x509/CertPathReviewerException
    //   551	586	225	org/spongycastle/x509/CertPathReviewerException
    //   90	96	239	org/spongycastle/jce/provider/PKIXNameConstraintValidatorException
    //   96	102	300	org/spongycastle/jce/provider/PKIXNameConstraintValidatorException
    //   102	115	361	org/spongycastle/jce/provider/AnnotatedException
    //   145	157	398	org/spongycastle/jce/provider/PKIXNameConstraintValidatorException
    //   460	473	549	org/spongycastle/jce/provider/AnnotatedException
  }
  
  private void checkPathLength()
  {
    int i = this.n;
    int j = 0;
    for (int k = -1 + this.certs.size();; k--)
    {
      if (k <= 0) {
        break label158;
      }
      X509Certificate localX509Certificate = (X509Certificate)this.certs.get(k);
      if (!isSelfIssued(localX509Certificate))
      {
        if (i <= 0) {
          addError(new ErrorBundle("org.spongycastle.x509.CertPathReviewerMessages", "CertPathReviewer.pathLenghtExtended"));
        }
        i--;
        j++;
      }
      try
      {
        BasicConstraints localBasicConstraints2 = BasicConstraints.getInstance(getExtensionValue(localX509Certificate, BASIC_CONSTRAINTS));
        localBasicConstraints1 = localBasicConstraints2;
      }
      catch (AnnotatedException localAnnotatedException)
      {
        for (;;)
        {
          BigInteger localBigInteger;
          int m;
          addError(new ErrorBundle("org.spongycastle.x509.CertPathReviewerMessages", "CertPathReviewer.processLengthConstError"), k);
          BasicConstraints localBasicConstraints1 = null;
        }
      }
      if (localBasicConstraints1 != null)
      {
        localBigInteger = localBasicConstraints1.getPathLenConstraint();
        if (localBigInteger != null)
        {
          m = localBigInteger.intValue();
          if (m < i) {
            i = m;
          }
        }
      }
    }
    label158:
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = new Integer(j);
    addNotification(new ErrorBundle("org.spongycastle.x509.CertPathReviewerMessages", "CertPathReviewer.totalPathLength", arrayOfObject));
  }
  
  /* Error */
  private void checkPolicy()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 104	org/spongycastle/x509/PKIXCertPathReviewer:pkixParams	Ljava/security/cert/PKIXParameters;
    //   4: invokevirtual 415	java/security/cert/PKIXParameters:getInitialPolicies	()Ljava/util/Set;
    //   7: astore_1
    //   8: iconst_1
    //   9: aload_0
    //   10: getfield 265	org/spongycastle/x509/PKIXCertPathReviewer:n	I
    //   13: iadd
    //   14: anewarray 417	java/util/ArrayList
    //   17: astore_2
    //   18: iconst_0
    //   19: istore_3
    //   20: aload_2
    //   21: arraylength
    //   22: istore 4
    //   24: iload_3
    //   25: iload 4
    //   27: if_icmpge +19 -> 46
    //   30: aload_2
    //   31: iload_3
    //   32: new 417	java/util/ArrayList
    //   35: dup
    //   36: invokespecial 418	java/util/ArrayList:<init>	()V
    //   39: aastore
    //   40: iinc 3 1
    //   43: goto -23 -> 20
    //   46: new 420	java/util/HashSet
    //   49: dup
    //   50: invokespecial 421	java/util/HashSet:<init>	()V
    //   53: astore 5
    //   55: aload 5
    //   57: ldc_w 423
    //   60: invokeinterface 426 2 0
    //   65: pop
    //   66: new 428	org/spongycastle/jce/provider/PKIXPolicyNode
    //   69: dup
    //   70: new 417	java/util/ArrayList
    //   73: dup
    //   74: invokespecial 418	java/util/ArrayList:<init>	()V
    //   77: iconst_0
    //   78: aload 5
    //   80: aconst_null
    //   81: new 420	java/util/HashSet
    //   84: dup
    //   85: invokespecial 421	java/util/HashSet:<init>	()V
    //   88: ldc_w 423
    //   91: iconst_0
    //   92: invokespecial 431	org/spongycastle/jce/provider/PKIXPolicyNode:<init>	(Ljava/util/List;ILjava/util/Set;Ljava/security/cert/PolicyNode;Ljava/util/Set;Ljava/lang/String;Z)V
    //   95: astore 7
    //   97: aload_2
    //   98: iconst_0
    //   99: aaload
    //   100: aload 7
    //   102: invokeinterface 432 2 0
    //   107: pop
    //   108: aload_0
    //   109: getfield 104	org/spongycastle/x509/PKIXCertPathReviewer:pkixParams	Ljava/security/cert/PKIXParameters;
    //   112: invokevirtual 435	java/security/cert/PKIXParameters:isExplicitPolicyRequired	()Z
    //   115: ifeq +237 -> 352
    //   118: iconst_0
    //   119: istore 9
    //   121: aload_0
    //   122: getfield 104	org/spongycastle/x509/PKIXCertPathReviewer:pkixParams	Ljava/security/cert/PKIXParameters;
    //   125: invokevirtual 438	java/security/cert/PKIXParameters:isAnyPolicyInhibited	()Z
    //   128: ifeq +235 -> 363
    //   131: iconst_0
    //   132: istore 10
    //   134: aload_0
    //   135: getfield 104	org/spongycastle/x509/PKIXCertPathReviewer:pkixParams	Ljava/security/cert/PKIXParameters;
    //   138: invokevirtual 441	java/security/cert/PKIXParameters:isPolicyMappingInhibited	()Z
    //   141: ifeq +233 -> 374
    //   144: iconst_0
    //   145: istore 11
    //   147: aconst_null
    //   148: astore 12
    //   150: aconst_null
    //   151: astore 13
    //   153: iconst_m1
    //   154: aload_0
    //   155: getfield 169	org/spongycastle/x509/PKIXCertPathReviewer:certs	Ljava/util/List;
    //   158: invokeinterface 172 1 0
    //   163: iadd
    //   164: istore 15
    //   166: iload 15
    //   168: iflt +1693 -> 1861
    //   171: aload_0
    //   172: getfield 265	org/spongycastle/x509/PKIXCertPathReviewer:n	I
    //   175: iload 15
    //   177: isub
    //   178: istore 16
    //   180: aload_0
    //   181: getfield 169	org/spongycastle/x509/PKIXCertPathReviewer:certs	Ljava/util/List;
    //   184: iload 15
    //   186: invokeinterface 176 2 0
    //   191: checkcast 178	java/security/cert/X509Certificate
    //   194: astore 13
    //   196: getstatic 197	org/spongycastle/x509/PKIXCertPathReviewer:CERTIFICATE_POLICIES	Ljava/lang/String;
    //   199: astore 19
    //   201: aload 13
    //   203: aload 19
    //   205: invokestatic 306	org/spongycastle/x509/PKIXCertPathReviewer:getExtensionValue	(Ljava/security/cert/X509Extension;Ljava/lang/String;)Lorg/spongycastle/asn1/ASN1Primitive;
    //   208: checkcast 295	org/spongycastle/asn1/ASN1Sequence
    //   211: astore 20
    //   213: aload 20
    //   215: ifnull +2499 -> 2714
    //   218: aload 7
    //   220: ifnull +2494 -> 2714
    //   223: aload 20
    //   225: invokevirtual 362	org/spongycastle/asn1/ASN1Sequence:getObjects	()Ljava/util/Enumeration;
    //   228: astore 68
    //   230: new 420	java/util/HashSet
    //   233: dup
    //   234: invokespecial 421	java/util/HashSet:<init>	()V
    //   237: astore 69
    //   239: aload 68
    //   241: invokeinterface 367 1 0
    //   246: ifeq +207 -> 453
    //   249: aload 68
    //   251: invokeinterface 370 1 0
    //   256: invokestatic 446	org/spongycastle/asn1/x509/PolicyInformation:getInstance	(Ljava/lang/Object;)Lorg/spongycastle/asn1/x509/PolicyInformation;
    //   259: astore 104
    //   261: aload 104
    //   263: invokevirtual 450	org/spongycastle/asn1/x509/PolicyInformation:getPolicyIdentifier	()Lorg/spongycastle/asn1/ASN1ObjectIdentifier;
    //   266: astore 105
    //   268: aload 69
    //   270: aload 105
    //   272: invokevirtual 451	org/spongycastle/asn1/DERObjectIdentifier:getId	()Ljava/lang/String;
    //   275: invokeinterface 426 2 0
    //   280: pop
    //   281: ldc_w 423
    //   284: aload 105
    //   286: invokevirtual 451	org/spongycastle/asn1/DERObjectIdentifier:getId	()Ljava/lang/String;
    //   289: invokevirtual 454	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   292: istore 107
    //   294: iload 107
    //   296: ifne -57 -> 239
    //   299: aload 104
    //   301: invokevirtual 457	org/spongycastle/asn1/x509/PolicyInformation:getPolicyQualifiers	()Lorg/spongycastle/asn1/ASN1Sequence;
    //   304: invokestatic 461	org/spongycastle/x509/PKIXCertPathReviewer:getQualifierSet	(Lorg/spongycastle/asn1/ASN1Sequence;)Ljava/util/Set;
    //   307: astore 110
    //   309: iload 16
    //   311: aload_2
    //   312: aload 105
    //   314: aload 110
    //   316: invokestatic 465	org/spongycastle/x509/PKIXCertPathReviewer:processCertD1i	(I[Ljava/util/List;Lorg/spongycastle/asn1/DERObjectIdentifier;Ljava/util/Set;)Z
    //   319: ifne -80 -> 239
    //   322: iload 16
    //   324: aload_2
    //   325: aload 105
    //   327: aload 110
    //   329: invokestatic 469	org/spongycastle/x509/PKIXCertPathReviewer:processCertD1ii	(I[Ljava/util/List;Lorg/spongycastle/asn1/DERObjectIdentifier;Ljava/util/Set;)V
    //   332: goto -93 -> 239
    //   335: astore 14
    //   337: aload_0
    //   338: aload 14
    //   340: invokevirtual 159	org/spongycastle/x509/CertPathReviewerException:getErrorMessage	()Lorg/spongycastle/i18n/ErrorBundle;
    //   343: aload 14
    //   345: invokevirtual 163	org/spongycastle/x509/CertPathReviewerException:getIndex	()I
    //   348: invokevirtual 167	org/spongycastle/x509/PKIXCertPathReviewer:addError	(Lorg/spongycastle/i18n/ErrorBundle;I)V
    //   351: return
    //   352: iconst_1
    //   353: aload_0
    //   354: getfield 265	org/spongycastle/x509/PKIXCertPathReviewer:n	I
    //   357: iadd
    //   358: istore 9
    //   360: goto -239 -> 121
    //   363: iconst_1
    //   364: aload_0
    //   365: getfield 265	org/spongycastle/x509/PKIXCertPathReviewer:n	I
    //   368: iadd
    //   369: istore 10
    //   371: goto -237 -> 134
    //   374: iconst_1
    //   375: aload_0
    //   376: getfield 265	org/spongycastle/x509/PKIXCertPathReviewer:n	I
    //   379: iadd
    //   380: istore 11
    //   382: goto -235 -> 147
    //   385: astore 17
    //   387: new 147	org/spongycastle/i18n/ErrorBundle
    //   390: dup
    //   391: ldc 11
    //   393: ldc_w 471
    //   396: invokespecial 341	org/spongycastle/i18n/ErrorBundle:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   399: astore 18
    //   401: new 64	org/spongycastle/x509/CertPathReviewerException
    //   404: dup
    //   405: aload 18
    //   407: aload 17
    //   409: aload_0
    //   410: getfield 240	org/spongycastle/x509/PKIXCertPathReviewer:certPath	Ljava/security/cert/CertPath;
    //   413: iload 15
    //   415: invokespecial 243	org/spongycastle/x509/CertPathReviewerException:<init>	(Lorg/spongycastle/i18n/ErrorBundle;Ljava/lang/Throwable;Ljava/security/cert/CertPath;I)V
    //   418: athrow
    //   419: astore 108
    //   421: new 147	org/spongycastle/i18n/ErrorBundle
    //   424: dup
    //   425: ldc 11
    //   427: ldc_w 473
    //   430: invokespecial 341	org/spongycastle/i18n/ErrorBundle:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   433: astore 109
    //   435: new 64	org/spongycastle/x509/CertPathReviewerException
    //   438: dup
    //   439: aload 109
    //   441: aload 108
    //   443: aload_0
    //   444: getfield 240	org/spongycastle/x509/PKIXCertPathReviewer:certPath	Ljava/security/cert/CertPath;
    //   447: iload 15
    //   449: invokespecial 243	org/spongycastle/x509/CertPathReviewerException:<init>	(Lorg/spongycastle/i18n/ErrorBundle;Ljava/lang/Throwable;Ljava/security/cert/CertPath;I)V
    //   452: athrow
    //   453: aload 12
    //   455: ifnull +2218 -> 2673
    //   458: aload 12
    //   460: ldc_w 423
    //   463: invokeinterface 224 2 0
    //   468: ifeq +226 -> 694
    //   471: goto +2202 -> 2673
    //   474: iload 10
    //   476: ifgt +20 -> 496
    //   479: iload 16
    //   481: aload_0
    //   482: getfield 265	org/spongycastle/x509/PKIXCertPathReviewer:n	I
    //   485: if_icmpge +2208 -> 2693
    //   488: aload 13
    //   490: invokestatic 269	org/spongycastle/x509/PKIXCertPathReviewer:isSelfIssued	(Ljava/security/cert/X509Certificate;)Z
    //   493: ifeq +2200 -> 2693
    //   496: aload 20
    //   498: invokevirtual 362	org/spongycastle/asn1/ASN1Sequence:getObjects	()Ljava/util/Enumeration;
    //   501: astore 80
    //   503: aload 80
    //   505: invokeinterface 367 1 0
    //   510: ifeq +2183 -> 2693
    //   513: aload 80
    //   515: invokeinterface 370 1 0
    //   520: invokestatic 446	org/spongycastle/asn1/x509/PolicyInformation:getInstance	(Ljava/lang/Object;)Lorg/spongycastle/asn1/x509/PolicyInformation;
    //   523: astore 81
    //   525: ldc_w 423
    //   528: aload 81
    //   530: invokevirtual 450	org/spongycastle/asn1/x509/PolicyInformation:getPolicyIdentifier	()Lorg/spongycastle/asn1/ASN1ObjectIdentifier;
    //   533: invokevirtual 46	org/spongycastle/asn1/ASN1ObjectIdentifier:getId	()Ljava/lang/String;
    //   536: invokevirtual 454	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   539: istore 82
    //   541: iload 82
    //   543: ifeq -40 -> 503
    //   546: aload 81
    //   548: invokevirtual 457	org/spongycastle/asn1/x509/PolicyInformation:getPolicyQualifiers	()Lorg/spongycastle/asn1/ASN1Sequence;
    //   551: invokestatic 461	org/spongycastle/x509/PKIXCertPathReviewer:getQualifierSet	(Lorg/spongycastle/asn1/ASN1Sequence;)Ljava/util/Set;
    //   554: astore 85
    //   556: iload 16
    //   558: iconst_1
    //   559: isub
    //   560: istore 86
    //   562: aload_2
    //   563: iload 86
    //   565: aaload
    //   566: astore 87
    //   568: iconst_0
    //   569: istore 88
    //   571: aload 87
    //   573: invokeinterface 172 1 0
    //   578: istore 89
    //   580: iload 88
    //   582: iload 89
    //   584: if_icmpge +2109 -> 2693
    //   587: aload 87
    //   589: iload 88
    //   591: invokeinterface 176 2 0
    //   596: checkcast 428	org/spongycastle/jce/provider/PKIXPolicyNode
    //   599: astore 90
    //   601: aload 90
    //   603: invokevirtual 476	org/spongycastle/jce/provider/PKIXPolicyNode:getExpectedPolicies	()Ljava/util/Set;
    //   606: invokeinterface 244 1 0
    //   611: astore 91
    //   613: aload 91
    //   615: invokeinterface 122 1 0
    //   620: ifeq +2067 -> 2687
    //   623: aload 91
    //   625: invokeinterface 126 1 0
    //   630: astore 92
    //   632: aload 92
    //   634: instanceof 248
    //   637: ifeq +153 -> 790
    //   640: aload 92
    //   642: checkcast 248	java/lang/String
    //   645: astore 93
    //   647: iconst_0
    //   648: istore 94
    //   650: aload 90
    //   652: invokevirtual 479	org/spongycastle/jce/provider/PKIXPolicyNode:getChildren	()Ljava/util/Iterator;
    //   655: astore 95
    //   657: aload 95
    //   659: invokeinterface 122 1 0
    //   664: ifeq +147 -> 811
    //   667: aload 93
    //   669: aload 95
    //   671: invokeinterface 126 1 0
    //   676: checkcast 428	org/spongycastle/jce/provider/PKIXPolicyNode
    //   679: invokevirtual 482	org/spongycastle/jce/provider/PKIXPolicyNode:getValidPolicy	()Ljava/lang/String;
    //   682: invokevirtual 454	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   685: ifeq -28 -> 657
    //   688: iconst_1
    //   689: istore 94
    //   691: goto -34 -> 657
    //   694: aload 12
    //   696: invokeinterface 244 1 0
    //   701: astore 100
    //   703: new 420	java/util/HashSet
    //   706: dup
    //   707: invokespecial 421	java/util/HashSet:<init>	()V
    //   710: astore 101
    //   712: aload 100
    //   714: invokeinterface 122 1 0
    //   719: ifeq +1961 -> 2680
    //   722: aload 100
    //   724: invokeinterface 126 1 0
    //   729: astore 102
    //   731: aload 69
    //   733: aload 102
    //   735: invokeinterface 224 2 0
    //   740: ifeq -28 -> 712
    //   743: aload 101
    //   745: aload 102
    //   747: invokeinterface 426 2 0
    //   752: pop
    //   753: goto -41 -> 712
    //   756: astore 83
    //   758: new 147	org/spongycastle/i18n/ErrorBundle
    //   761: dup
    //   762: ldc 11
    //   764: ldc_w 473
    //   767: invokespecial 341	org/spongycastle/i18n/ErrorBundle:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   770: astore 84
    //   772: new 64	org/spongycastle/x509/CertPathReviewerException
    //   775: dup
    //   776: aload 84
    //   778: aload 83
    //   780: aload_0
    //   781: getfield 240	org/spongycastle/x509/PKIXCertPathReviewer:certPath	Ljava/security/cert/CertPath;
    //   784: iload 15
    //   786: invokespecial 243	org/spongycastle/x509/CertPathReviewerException:<init>	(Lorg/spongycastle/i18n/ErrorBundle;Ljava/lang/Throwable;Ljava/security/cert/CertPath;I)V
    //   789: athrow
    //   790: aload 92
    //   792: instanceof 246
    //   795: ifeq -182 -> 613
    //   798: aload 92
    //   800: checkcast 246	org/spongycastle/asn1/DERObjectIdentifier
    //   803: invokevirtual 451	org/spongycastle/asn1/DERObjectIdentifier:getId	()Ljava/lang/String;
    //   806: astore 93
    //   808: goto -161 -> 647
    //   811: iload 94
    //   813: ifne -200 -> 613
    //   816: new 420	java/util/HashSet
    //   819: dup
    //   820: invokespecial 421	java/util/HashSet:<init>	()V
    //   823: astore 96
    //   825: aload 96
    //   827: aload 93
    //   829: invokeinterface 426 2 0
    //   834: pop
    //   835: new 428	org/spongycastle/jce/provider/PKIXPolicyNode
    //   838: dup
    //   839: new 417	java/util/ArrayList
    //   842: dup
    //   843: invokespecial 418	java/util/ArrayList:<init>	()V
    //   846: iload 16
    //   848: aload 96
    //   850: aload 90
    //   852: aload 85
    //   854: aload 93
    //   856: iconst_0
    //   857: invokespecial 431	org/spongycastle/jce/provider/PKIXPolicyNode:<init>	(Ljava/util/List;ILjava/util/Set;Ljava/security/cert/PolicyNode;Ljava/util/Set;Ljava/lang/String;Z)V
    //   860: astore 98
    //   862: aload 90
    //   864: aload 98
    //   866: invokevirtual 486	org/spongycastle/jce/provider/PKIXPolicyNode:addChild	(Lorg/spongycastle/jce/provider/PKIXPolicyNode;)V
    //   869: aload_2
    //   870: iload 16
    //   872: aaload
    //   873: aload 98
    //   875: invokeinterface 432 2 0
    //   880: pop
    //   881: goto -268 -> 613
    //   884: iload 70
    //   886: iflt +68 -> 954
    //   889: aload_2
    //   890: iload 70
    //   892: aaload
    //   893: astore 71
    //   895: iconst_0
    //   896: istore 72
    //   898: aload 71
    //   900: invokeinterface 172 1 0
    //   905: istore 73
    //   907: iload 72
    //   909: iload 73
    //   911: if_icmpge +1797 -> 2708
    //   914: aload 71
    //   916: iload 72
    //   918: invokeinterface 176 2 0
    //   923: checkcast 428	org/spongycastle/jce/provider/PKIXPolicyNode
    //   926: astore 74
    //   928: aload 74
    //   930: invokevirtual 489	org/spongycastle/jce/provider/PKIXPolicyNode:hasChildren	()Z
    //   933: ifne +1769 -> 2702
    //   936: aload 7
    //   938: aload_2
    //   939: aload 74
    //   941: invokestatic 493	org/spongycastle/x509/PKIXCertPathReviewer:removePolicyNode	(Lorg/spongycastle/jce/provider/PKIXPolicyNode;[Ljava/util/List;Lorg/spongycastle/jce/provider/PKIXPolicyNode;)Lorg/spongycastle/jce/provider/PKIXPolicyNode;
    //   944: astore 7
    //   946: aload 7
    //   948: ifnull +1760 -> 2708
    //   951: goto +1751 -> 2702
    //   954: aload 13
    //   956: invokevirtual 182	java/security/cert/X509Certificate:getCriticalExtensionOIDs	()Ljava/util/Set;
    //   959: astore 75
    //   961: aload 75
    //   963: ifnull +1751 -> 2714
    //   966: aload 75
    //   968: getstatic 197	org/spongycastle/x509/PKIXCertPathReviewer:CERTIFICATE_POLICIES	Ljava/lang/String;
    //   971: invokeinterface 224 2 0
    //   976: istore 76
    //   978: aload_2
    //   979: iload 16
    //   981: aaload
    //   982: astore 77
    //   984: iconst_0
    //   985: istore 78
    //   987: aload 77
    //   989: invokeinterface 172 1 0
    //   994: istore 79
    //   996: iload 78
    //   998: iload 79
    //   1000: if_icmpge +1714 -> 2714
    //   1003: aload 77
    //   1005: iload 78
    //   1007: invokeinterface 176 2 0
    //   1012: checkcast 428	org/spongycastle/jce/provider/PKIXPolicyNode
    //   1015: iload 76
    //   1017: invokevirtual 496	org/spongycastle/jce/provider/PKIXPolicyNode:setCritical	(Z)V
    //   1020: iinc 78 1
    //   1023: goto -36 -> 987
    //   1026: iload 9
    //   1028: ifgt +32 -> 1060
    //   1031: aload 7
    //   1033: ifnonnull +27 -> 1060
    //   1036: new 147	org/spongycastle/i18n/ErrorBundle
    //   1039: dup
    //   1040: ldc 11
    //   1042: ldc_w 498
    //   1045: invokespecial 341	org/spongycastle/i18n/ErrorBundle:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   1048: astore 67
    //   1050: new 64	org/spongycastle/x509/CertPathReviewerException
    //   1053: dup
    //   1054: aload 67
    //   1056: invokespecial 500	org/spongycastle/x509/CertPathReviewerException:<init>	(Lorg/spongycastle/i18n/ErrorBundle;)V
    //   1059: athrow
    //   1060: aload_0
    //   1061: getfield 265	org/spongycastle/x509/PKIXCertPathReviewer:n	I
    //   1064: istore 21
    //   1066: iload 16
    //   1068: iload 21
    //   1070: if_icmpeq +753 -> 1823
    //   1073: getstatic 200	org/spongycastle/x509/PKIXCertPathReviewer:POLICY_MAPPINGS	Ljava/lang/String;
    //   1076: astore 24
    //   1078: aload 13
    //   1080: aload 24
    //   1082: invokestatic 306	org/spongycastle/x509/PKIXCertPathReviewer:getExtensionValue	(Ljava/security/cert/X509Extension;Ljava/lang/String;)Lorg/spongycastle/asn1/ASN1Primitive;
    //   1085: astore 25
    //   1087: aload 25
    //   1089: ifnull +183 -> 1272
    //   1092: aload 25
    //   1094: checkcast 295	org/spongycastle/asn1/ASN1Sequence
    //   1097: astore 59
    //   1099: iconst_0
    //   1100: istore 60
    //   1102: aload 59
    //   1104: invokevirtual 307	org/spongycastle/asn1/ASN1Sequence:size	()I
    //   1107: istore 61
    //   1109: iload 60
    //   1111: iload 61
    //   1113: if_icmpge +159 -> 1272
    //   1116: aload 59
    //   1118: iload 60
    //   1120: invokevirtual 311	org/spongycastle/asn1/ASN1Sequence:getObjectAt	(I)Lorg/spongycastle/asn1/ASN1Encodable;
    //   1123: checkcast 295	org/spongycastle/asn1/ASN1Sequence
    //   1126: astore 62
    //   1128: aload 62
    //   1130: iconst_0
    //   1131: invokevirtual 311	org/spongycastle/asn1/ASN1Sequence:getObjectAt	(I)Lorg/spongycastle/asn1/ASN1Encodable;
    //   1134: checkcast 246	org/spongycastle/asn1/DERObjectIdentifier
    //   1137: astore 63
    //   1139: aload 62
    //   1141: iconst_1
    //   1142: invokevirtual 311	org/spongycastle/asn1/ASN1Sequence:getObjectAt	(I)Lorg/spongycastle/asn1/ASN1Encodable;
    //   1145: checkcast 246	org/spongycastle/asn1/DERObjectIdentifier
    //   1148: astore 64
    //   1150: ldc_w 423
    //   1153: aload 63
    //   1155: invokevirtual 451	org/spongycastle/asn1/DERObjectIdentifier:getId	()Ljava/lang/String;
    //   1158: invokevirtual 454	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   1161: ifeq +67 -> 1228
    //   1164: new 147	org/spongycastle/i18n/ErrorBundle
    //   1167: dup
    //   1168: ldc 11
    //   1170: ldc_w 502
    //   1173: invokespecial 341	org/spongycastle/i18n/ErrorBundle:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   1176: astore 65
    //   1178: new 64	org/spongycastle/x509/CertPathReviewerException
    //   1181: dup
    //   1182: aload 65
    //   1184: aload_0
    //   1185: getfield 240	org/spongycastle/x509/PKIXCertPathReviewer:certPath	Ljava/security/cert/CertPath;
    //   1188: iload 15
    //   1190: invokespecial 505	org/spongycastle/x509/CertPathReviewerException:<init>	(Lorg/spongycastle/i18n/ErrorBundle;Ljava/security/cert/CertPath;I)V
    //   1193: athrow
    //   1194: astore 22
    //   1196: new 147	org/spongycastle/i18n/ErrorBundle
    //   1199: dup
    //   1200: ldc 11
    //   1202: ldc_w 507
    //   1205: invokespecial 341	org/spongycastle/i18n/ErrorBundle:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   1208: astore 23
    //   1210: new 64	org/spongycastle/x509/CertPathReviewerException
    //   1213: dup
    //   1214: aload 23
    //   1216: aload 22
    //   1218: aload_0
    //   1219: getfield 240	org/spongycastle/x509/PKIXCertPathReviewer:certPath	Ljava/security/cert/CertPath;
    //   1222: iload 15
    //   1224: invokespecial 243	org/spongycastle/x509/CertPathReviewerException:<init>	(Lorg/spongycastle/i18n/ErrorBundle;Ljava/lang/Throwable;Ljava/security/cert/CertPath;I)V
    //   1227: athrow
    //   1228: ldc_w 423
    //   1231: aload 64
    //   1233: invokevirtual 451	org/spongycastle/asn1/DERObjectIdentifier:getId	()Ljava/lang/String;
    //   1236: invokevirtual 454	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   1239: ifeq +1486 -> 2725
    //   1242: new 147	org/spongycastle/i18n/ErrorBundle
    //   1245: dup
    //   1246: ldc 11
    //   1248: ldc_w 502
    //   1251: invokespecial 341	org/spongycastle/i18n/ErrorBundle:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   1254: astore 66
    //   1256: new 64	org/spongycastle/x509/CertPathReviewerException
    //   1259: dup
    //   1260: aload 66
    //   1262: aload_0
    //   1263: getfield 240	org/spongycastle/x509/PKIXCertPathReviewer:certPath	Ljava/security/cert/CertPath;
    //   1266: iload 15
    //   1268: invokespecial 505	org/spongycastle/x509/CertPathReviewerException:<init>	(Lorg/spongycastle/i18n/ErrorBundle;Ljava/security/cert/CertPath;I)V
    //   1271: athrow
    //   1272: aload 25
    //   1274: ifnull +303 -> 1577
    //   1277: aload 25
    //   1279: checkcast 295	org/spongycastle/asn1/ASN1Sequence
    //   1282: astore 26
    //   1284: new 509	java/util/HashMap
    //   1287: dup
    //   1288: invokespecial 510	java/util/HashMap:<init>	()V
    //   1291: astore 27
    //   1293: new 420	java/util/HashSet
    //   1296: dup
    //   1297: invokespecial 421	java/util/HashSet:<init>	()V
    //   1300: astore 28
    //   1302: iconst_0
    //   1303: istore 29
    //   1305: aload 26
    //   1307: invokevirtual 307	org/spongycastle/asn1/ASN1Sequence:size	()I
    //   1310: istore 30
    //   1312: iload 29
    //   1314: iload 30
    //   1316: if_icmpge +122 -> 1438
    //   1319: aload 26
    //   1321: iload 29
    //   1323: invokevirtual 311	org/spongycastle/asn1/ASN1Sequence:getObjectAt	(I)Lorg/spongycastle/asn1/ASN1Encodable;
    //   1326: checkcast 295	org/spongycastle/asn1/ASN1Sequence
    //   1329: astore 31
    //   1331: aload 31
    //   1333: iconst_0
    //   1334: invokevirtual 311	org/spongycastle/asn1/ASN1Sequence:getObjectAt	(I)Lorg/spongycastle/asn1/ASN1Encodable;
    //   1337: checkcast 246	org/spongycastle/asn1/DERObjectIdentifier
    //   1340: invokevirtual 451	org/spongycastle/asn1/DERObjectIdentifier:getId	()Ljava/lang/String;
    //   1343: astore 32
    //   1345: aload 31
    //   1347: iconst_1
    //   1348: invokevirtual 311	org/spongycastle/asn1/ASN1Sequence:getObjectAt	(I)Lorg/spongycastle/asn1/ASN1Encodable;
    //   1351: checkcast 246	org/spongycastle/asn1/DERObjectIdentifier
    //   1354: invokevirtual 451	org/spongycastle/asn1/DERObjectIdentifier:getId	()Ljava/lang/String;
    //   1357: astore 33
    //   1359: aload 27
    //   1361: aload 32
    //   1363: invokeinterface 515 2 0
    //   1368: ifne +47 -> 1415
    //   1371: new 420	java/util/HashSet
    //   1374: dup
    //   1375: invokespecial 421	java/util/HashSet:<init>	()V
    //   1378: astore 34
    //   1380: aload 34
    //   1382: aload 33
    //   1384: invokeinterface 426 2 0
    //   1389: pop
    //   1390: aload 27
    //   1392: aload 32
    //   1394: aload 34
    //   1396: invokeinterface 519 3 0
    //   1401: pop
    //   1402: aload 28
    //   1404: aload 32
    //   1406: invokeinterface 426 2 0
    //   1411: pop
    //   1412: goto +1319 -> 2731
    //   1415: aload 27
    //   1417: aload 32
    //   1419: invokeinterface 522 2 0
    //   1424: checkcast 184	java/util/Set
    //   1427: aload 33
    //   1429: invokeinterface 426 2 0
    //   1434: pop
    //   1435: goto +1296 -> 2731
    //   1438: aload 28
    //   1440: invokeinterface 244 1 0
    //   1445: astore 39
    //   1447: aload 39
    //   1449: invokeinterface 122 1 0
    //   1454: ifeq +123 -> 1577
    //   1457: aload 39
    //   1459: invokeinterface 126 1 0
    //   1464: checkcast 248	java/lang/String
    //   1467: astore 54
    //   1469: iload 11
    //   1471: ifle +86 -> 1557
    //   1474: iload 16
    //   1476: aload_2
    //   1477: aload 54
    //   1479: aload 27
    //   1481: aload 13
    //   1483: invokestatic 526	org/spongycastle/x509/PKIXCertPathReviewer:prepareNextCertB1	(I[Ljava/util/List;Ljava/lang/String;Ljava/util/Map;Ljava/security/cert/X509Certificate;)V
    //   1486: goto -39 -> 1447
    //   1489: astore 57
    //   1491: new 147	org/spongycastle/i18n/ErrorBundle
    //   1494: dup
    //   1495: ldc 11
    //   1497: ldc_w 471
    //   1500: invokespecial 341	org/spongycastle/i18n/ErrorBundle:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   1503: astore 58
    //   1505: new 64	org/spongycastle/x509/CertPathReviewerException
    //   1508: dup
    //   1509: aload 58
    //   1511: aload 57
    //   1513: aload_0
    //   1514: getfield 240	org/spongycastle/x509/PKIXCertPathReviewer:certPath	Ljava/security/cert/CertPath;
    //   1517: iload 15
    //   1519: invokespecial 243	org/spongycastle/x509/CertPathReviewerException:<init>	(Lorg/spongycastle/i18n/ErrorBundle;Ljava/lang/Throwable;Ljava/security/cert/CertPath;I)V
    //   1522: athrow
    //   1523: astore 55
    //   1525: new 147	org/spongycastle/i18n/ErrorBundle
    //   1528: dup
    //   1529: ldc 11
    //   1531: ldc_w 473
    //   1534: invokespecial 341	org/spongycastle/i18n/ErrorBundle:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   1537: astore 56
    //   1539: new 64	org/spongycastle/x509/CertPathReviewerException
    //   1542: dup
    //   1543: aload 56
    //   1545: aload 55
    //   1547: aload_0
    //   1548: getfield 240	org/spongycastle/x509/PKIXCertPathReviewer:certPath	Ljava/security/cert/CertPath;
    //   1551: iload 15
    //   1553: invokespecial 243	org/spongycastle/x509/CertPathReviewerException:<init>	(Lorg/spongycastle/i18n/ErrorBundle;Ljava/lang/Throwable;Ljava/security/cert/CertPath;I)V
    //   1556: athrow
    //   1557: iload 11
    //   1559: ifgt -112 -> 1447
    //   1562: iload 16
    //   1564: aload_2
    //   1565: aload 54
    //   1567: aload 7
    //   1569: invokestatic 530	org/spongycastle/x509/PKIXCertPathReviewer:prepareNextCertB2	(I[Ljava/util/List;Ljava/lang/String;Lorg/spongycastle/jce/provider/PKIXPolicyNode;)Lorg/spongycastle/jce/provider/PKIXPolicyNode;
    //   1572: astore 7
    //   1574: goto -127 -> 1447
    //   1577: aload 13
    //   1579: invokestatic 269	org/spongycastle/x509/PKIXCertPathReviewer:isSelfIssued	(Ljava/security/cert/X509Certificate;)Z
    //   1582: istore 40
    //   1584: iload 40
    //   1586: ifne +27 -> 1613
    //   1589: iload 9
    //   1591: ifeq +6 -> 1597
    //   1594: iinc 9 255
    //   1597: iload 11
    //   1599: ifeq +6 -> 1605
    //   1602: iinc 11 255
    //   1605: iload 10
    //   1607: ifeq +6 -> 1613
    //   1610: iinc 10 255
    //   1613: getstatic 212	org/spongycastle/x509/PKIXCertPathReviewer:POLICY_CONSTRAINTS	Ljava/lang/String;
    //   1616: astore 43
    //   1618: aload 13
    //   1620: aload 43
    //   1622: invokestatic 306	org/spongycastle/x509/PKIXCertPathReviewer:getExtensionValue	(Ljava/security/cert/X509Extension;Ljava/lang/String;)Lorg/spongycastle/asn1/ASN1Primitive;
    //   1625: checkcast 295	org/spongycastle/asn1/ASN1Sequence
    //   1628: astore 44
    //   1630: aload 44
    //   1632: ifnull +148 -> 1780
    //   1635: aload 44
    //   1637: invokevirtual 362	org/spongycastle/asn1/ASN1Sequence:getObjects	()Ljava/util/Enumeration;
    //   1640: astore 45
    //   1642: aload 45
    //   1644: invokeinterface 367 1 0
    //   1649: ifeq +131 -> 1780
    //   1652: aload 45
    //   1654: invokeinterface 370 1 0
    //   1659: checkcast 532	org/spongycastle/asn1/ASN1TaggedObject
    //   1662: astore 51
    //   1664: aload 51
    //   1666: invokevirtual 535	org/spongycastle/asn1/ASN1TaggedObject:getTagNo	()I
    //   1669: tableswitch	default:+1068 -> 2737, 0:+23->1692, 1:+51->1720
    //   1693: baload
    //   1694: iconst_0
    //   1695: invokestatic 540	org/spongycastle/asn1/DERInteger:getInstance	(Lorg/spongycastle/asn1/ASN1TaggedObject;Z)Lorg/spongycastle/asn1/ASN1Integer;
    //   1698: invokevirtual 545	org/spongycastle/asn1/ASN1Integer:getValue	()Ljava/math/BigInteger;
    //   1701: invokevirtual 401	java/math/BigInteger:intValue	()I
    //   1704: istore 53
    //   1706: iload 53
    //   1708: iload 9
    //   1710: if_icmpge -68 -> 1642
    //   1713: iload 53
    //   1715: istore 9
    //   1717: goto -75 -> 1642
    //   1720: aload 51
    //   1722: iconst_0
    //   1723: invokestatic 540	org/spongycastle/asn1/DERInteger:getInstance	(Lorg/spongycastle/asn1/ASN1TaggedObject;Z)Lorg/spongycastle/asn1/ASN1Integer;
    //   1726: invokevirtual 545	org/spongycastle/asn1/ASN1Integer:getValue	()Ljava/math/BigInteger;
    //   1729: invokevirtual 401	java/math/BigInteger:intValue	()I
    //   1732: istore 52
    //   1734: iload 52
    //   1736: iload 11
    //   1738: if_icmpge -96 -> 1642
    //   1741: iload 52
    //   1743: istore 11
    //   1745: goto -103 -> 1642
    //   1748: astore 41
    //   1750: new 147	org/spongycastle/i18n/ErrorBundle
    //   1753: dup
    //   1754: ldc 11
    //   1756: ldc_w 547
    //   1759: invokespecial 341	org/spongycastle/i18n/ErrorBundle:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   1762: astore 42
    //   1764: new 64	org/spongycastle/x509/CertPathReviewerException
    //   1767: dup
    //   1768: aload 42
    //   1770: aload_0
    //   1771: getfield 240	org/spongycastle/x509/PKIXCertPathReviewer:certPath	Ljava/security/cert/CertPath;
    //   1774: iload 15
    //   1776: invokespecial 505	org/spongycastle/x509/CertPathReviewerException:<init>	(Lorg/spongycastle/i18n/ErrorBundle;Ljava/security/cert/CertPath;I)V
    //   1779: athrow
    //   1780: getstatic 203	org/spongycastle/x509/PKIXCertPathReviewer:INHIBIT_ANY_POLICY	Ljava/lang/String;
    //   1783: astore 48
    //   1785: aload 13
    //   1787: aload 48
    //   1789: invokestatic 306	org/spongycastle/x509/PKIXCertPathReviewer:getExtensionValue	(Ljava/security/cert/X509Extension;Ljava/lang/String;)Lorg/spongycastle/asn1/ASN1Primitive;
    //   1792: checkcast 537	org/spongycastle/asn1/DERInteger
    //   1795: astore 49
    //   1797: aload 49
    //   1799: ifnull +24 -> 1823
    //   1802: aload 49
    //   1804: invokevirtual 548	org/spongycastle/asn1/DERInteger:getValue	()Ljava/math/BigInteger;
    //   1807: invokevirtual 401	java/math/BigInteger:intValue	()I
    //   1810: istore 50
    //   1812: iload 50
    //   1814: iload 10
    //   1816: if_icmpge +7 -> 1823
    //   1819: iload 50
    //   1821: istore 10
    //   1823: iinc 15 255
    //   1826: goto -1660 -> 166
    //   1829: astore 46
    //   1831: new 147	org/spongycastle/i18n/ErrorBundle
    //   1834: dup
    //   1835: ldc 11
    //   1837: ldc_w 550
    //   1840: invokespecial 341	org/spongycastle/i18n/ErrorBundle:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   1843: astore 47
    //   1845: new 64	org/spongycastle/x509/CertPathReviewerException
    //   1848: dup
    //   1849: aload 47
    //   1851: aload_0
    //   1852: getfield 240	org/spongycastle/x509/PKIXCertPathReviewer:certPath	Ljava/security/cert/CertPath;
    //   1855: iload 15
    //   1857: invokespecial 505	org/spongycastle/x509/CertPathReviewerException:<init>	(Lorg/spongycastle/i18n/ErrorBundle;Ljava/security/cert/CertPath;I)V
    //   1860: athrow
    //   1861: aload 13
    //   1863: invokestatic 269	org/spongycastle/x509/PKIXCertPathReviewer:isSelfIssued	(Ljava/security/cert/X509Certificate;)Z
    //   1866: istore 111
    //   1868: iload 111
    //   1870: ifne +11 -> 1881
    //   1873: iload 9
    //   1875: ifle +6 -> 1881
    //   1878: iinc 9 255
    //   1881: getstatic 212	org/spongycastle/x509/PKIXCertPathReviewer:POLICY_CONSTRAINTS	Ljava/lang/String;
    //   1884: astore 114
    //   1886: aload 13
    //   1888: aload 114
    //   1890: invokestatic 306	org/spongycastle/x509/PKIXCertPathReviewer:getExtensionValue	(Ljava/security/cert/X509Extension;Ljava/lang/String;)Lorg/spongycastle/asn1/ASN1Primitive;
    //   1893: checkcast 295	org/spongycastle/asn1/ASN1Sequence
    //   1896: astore 115
    //   1898: aload 115
    //   1900: ifnull +113 -> 2013
    //   1903: aload 115
    //   1905: invokevirtual 362	org/spongycastle/asn1/ASN1Sequence:getObjects	()Ljava/util/Enumeration;
    //   1908: astore 116
    //   1910: aload 116
    //   1912: invokeinterface 367 1 0
    //   1917: ifeq +96 -> 2013
    //   1920: aload 116
    //   1922: invokeinterface 370 1 0
    //   1927: checkcast 532	org/spongycastle/asn1/ASN1TaggedObject
    //   1930: astore 156
    //   1932: aload 156
    //   1934: invokevirtual 535	org/spongycastle/asn1/ASN1TaggedObject:getTagNo	()I
    //   1937: tableswitch	default:+803 -> 2740, 0:+19->1956
    //   1957: ifge +952 -> 2909
    //   1960: iconst_m1
    //   1961: iload_2
    //   1962: invokevirtual 545	org/spongycastle/asn1/ASN1Integer:getValue	()Ljava/math/BigInteger;
    //   1965: invokevirtual 401	java/math/BigInteger:intValue	()I
    //   1968: istore 157
    //   1970: iload 157
    //   1972: ifne -62 -> 1910
    //   1975: iconst_0
    //   1976: istore 9
    //   1978: goto -68 -> 1910
    //   1981: astore 112
    //   1983: new 147	org/spongycastle/i18n/ErrorBundle
    //   1986: dup
    //   1987: ldc 11
    //   1989: ldc_w 547
    //   1992: invokespecial 341	org/spongycastle/i18n/ErrorBundle:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   1995: astore 113
    //   1997: new 64	org/spongycastle/x509/CertPathReviewerException
    //   2000: dup
    //   2001: aload 113
    //   2003: aload_0
    //   2004: getfield 240	org/spongycastle/x509/PKIXCertPathReviewer:certPath	Ljava/security/cert/CertPath;
    //   2007: iload 15
    //   2009: invokespecial 505	org/spongycastle/x509/CertPathReviewerException:<init>	(Lorg/spongycastle/i18n/ErrorBundle;Ljava/security/cert/CertPath;I)V
    //   2012: athrow
    //   2013: aload 7
    //   2015: ifnonnull +77 -> 2092
    //   2018: aload_0
    //   2019: getfield 104	org/spongycastle/x509/PKIXCertPathReviewer:pkixParams	Ljava/security/cert/PKIXParameters;
    //   2022: invokevirtual 435	java/security/cert/PKIXParameters:isExplicitPolicyRequired	()Z
    //   2025: ifeq +718 -> 2743
    //   2028: new 147	org/spongycastle/i18n/ErrorBundle
    //   2031: dup
    //   2032: ldc 11
    //   2034: ldc_w 552
    //   2037: invokespecial 341	org/spongycastle/i18n/ErrorBundle:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   2040: astore 117
    //   2042: new 64	org/spongycastle/x509/CertPathReviewerException
    //   2045: dup
    //   2046: aload 117
    //   2048: aload_0
    //   2049: getfield 240	org/spongycastle/x509/PKIXCertPathReviewer:certPath	Ljava/security/cert/CertPath;
    //   2052: iload 15
    //   2054: invokespecial 505	org/spongycastle/x509/CertPathReviewerException:<init>	(Lorg/spongycastle/i18n/ErrorBundle;Ljava/security/cert/CertPath;I)V
    //   2057: athrow
    //   2058: iload 9
    //   2060: ifgt -1709 -> 351
    //   2063: aload 118
    //   2065: ifnonnull -1714 -> 351
    //   2068: new 147	org/spongycastle/i18n/ErrorBundle
    //   2071: dup
    //   2072: ldc 11
    //   2074: ldc_w 554
    //   2077: invokespecial 341	org/spongycastle/i18n/ErrorBundle:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   2080: astore 119
    //   2082: new 64	org/spongycastle/x509/CertPathReviewerException
    //   2085: dup
    //   2086: aload 119
    //   2088: invokespecial 500	org/spongycastle/x509/CertPathReviewerException:<init>	(Lorg/spongycastle/i18n/ErrorBundle;)V
    //   2091: athrow
    //   2092: aload_1
    //   2093: invokestatic 558	org/spongycastle/x509/PKIXCertPathReviewer:isAnyPolicy	(Ljava/util/Set;)Z
    //   2096: ifeq +289 -> 2385
    //   2099: aload_0
    //   2100: getfield 104	org/spongycastle/x509/PKIXCertPathReviewer:pkixParams	Ljava/security/cert/PKIXParameters;
    //   2103: invokevirtual 435	java/security/cert/PKIXParameters:isExplicitPolicyRequired	()Z
    //   2106: ifeq +667 -> 2773
    //   2109: aload 12
    //   2111: invokeinterface 187 1 0
    //   2116: ifeq +33 -> 2149
    //   2119: new 147	org/spongycastle/i18n/ErrorBundle
    //   2122: dup
    //   2123: ldc 11
    //   2125: ldc_w 552
    //   2128: invokespecial 341	org/spongycastle/i18n/ErrorBundle:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   2131: astore 138
    //   2133: new 64	org/spongycastle/x509/CertPathReviewerException
    //   2136: dup
    //   2137: aload 138
    //   2139: aload_0
    //   2140: getfield 240	org/spongycastle/x509/PKIXCertPathReviewer:certPath	Ljava/security/cert/CertPath;
    //   2143: iload 15
    //   2145: invokespecial 505	org/spongycastle/x509/CertPathReviewerException:<init>	(Lorg/spongycastle/i18n/ErrorBundle;Ljava/security/cert/CertPath;I)V
    //   2148: athrow
    //   2149: new 420	java/util/HashSet
    //   2152: dup
    //   2153: invokespecial 421	java/util/HashSet:<init>	()V
    //   2156: astore 139
    //   2158: iconst_0
    //   2159: istore 140
    //   2161: aload_2
    //   2162: arraylength
    //   2163: istore 141
    //   2165: iload 140
    //   2167: iload 141
    //   2169: if_icmpge +91 -> 2260
    //   2172: aload_2
    //   2173: iload 140
    //   2175: aaload
    //   2176: astore 142
    //   2178: iconst_0
    //   2179: istore 143
    //   2181: aload 142
    //   2183: invokeinterface 172 1 0
    //   2188: istore 144
    //   2190: iload 143
    //   2192: iload 144
    //   2194: if_icmpge +561 -> 2755
    //   2197: aload 142
    //   2199: iload 143
    //   2201: invokeinterface 176 2 0
    //   2206: checkcast 428	org/spongycastle/jce/provider/PKIXPolicyNode
    //   2209: astore 145
    //   2211: ldc_w 423
    //   2214: aload 145
    //   2216: invokevirtual 482	org/spongycastle/jce/provider/PKIXPolicyNode:getValidPolicy	()Ljava/lang/String;
    //   2219: invokevirtual 454	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   2222: ifeq +527 -> 2749
    //   2225: aload 145
    //   2227: invokevirtual 479	org/spongycastle/jce/provider/PKIXPolicyNode:getChildren	()Ljava/util/Iterator;
    //   2230: astore 146
    //   2232: aload 146
    //   2234: invokeinterface 122 1 0
    //   2239: ifeq +510 -> 2749
    //   2242: aload 139
    //   2244: aload 146
    //   2246: invokeinterface 126 1 0
    //   2251: invokeinterface 426 2 0
    //   2256: pop
    //   2257: goto -25 -> 2232
    //   2260: aload 139
    //   2262: invokeinterface 244 1 0
    //   2267: astore 148
    //   2269: aload 148
    //   2271: invokeinterface 122 1 0
    //   2276: ifeq +31 -> 2307
    //   2279: aload 148
    //   2281: invokeinterface 126 1 0
    //   2286: checkcast 428	org/spongycastle/jce/provider/PKIXPolicyNode
    //   2289: invokevirtual 482	org/spongycastle/jce/provider/PKIXPolicyNode:getValidPolicy	()Ljava/lang/String;
    //   2292: astore 154
    //   2294: aload 12
    //   2296: aload 154
    //   2298: invokeinterface 224 2 0
    //   2303: pop
    //   2304: goto -35 -> 2269
    //   2307: aload 7
    //   2309: ifnull +464 -> 2773
    //   2312: iconst_m1
    //   2313: aload_0
    //   2314: getfield 265	org/spongycastle/x509/PKIXCertPathReviewer:n	I
    //   2317: iadd
    //   2318: istore 149
    //   2320: iload 149
    //   2322: iflt +451 -> 2773
    //   2325: aload_2
    //   2326: iload 149
    //   2328: aaload
    //   2329: astore 150
    //   2331: iconst_0
    //   2332: istore 151
    //   2334: aload 150
    //   2336: invokeinterface 172 1 0
    //   2341: istore 152
    //   2343: iload 151
    //   2345: iload 152
    //   2347: if_icmpge +420 -> 2767
    //   2350: aload 150
    //   2352: iload 151
    //   2354: invokeinterface 176 2 0
    //   2359: checkcast 428	org/spongycastle/jce/provider/PKIXPolicyNode
    //   2362: astore 153
    //   2364: aload 153
    //   2366: invokevirtual 489	org/spongycastle/jce/provider/PKIXPolicyNode:hasChildren	()Z
    //   2369: ifne +392 -> 2761
    //   2372: aload 7
    //   2374: aload_2
    //   2375: aload 153
    //   2377: invokestatic 493	org/spongycastle/x509/PKIXCertPathReviewer:removePolicyNode	(Lorg/spongycastle/jce/provider/PKIXPolicyNode;[Ljava/util/List;Lorg/spongycastle/jce/provider/PKIXPolicyNode;)Lorg/spongycastle/jce/provider/PKIXPolicyNode;
    //   2380: astore 7
    //   2382: goto +379 -> 2761
    //   2385: new 420	java/util/HashSet
    //   2388: dup
    //   2389: invokespecial 421	java/util/HashSet:<init>	()V
    //   2392: astore 120
    //   2394: iconst_0
    //   2395: istore 121
    //   2397: aload_2
    //   2398: arraylength
    //   2399: istore 122
    //   2401: iload 121
    //   2403: iload 122
    //   2405: if_icmpge +112 -> 2517
    //   2408: aload_2
    //   2409: iload 121
    //   2411: aaload
    //   2412: astore 123
    //   2414: iconst_0
    //   2415: istore 124
    //   2417: aload 123
    //   2419: invokeinterface 172 1 0
    //   2424: istore 125
    //   2426: iload 124
    //   2428: iload 125
    //   2430: if_icmpge +356 -> 2786
    //   2433: aload 123
    //   2435: iload 124
    //   2437: invokeinterface 176 2 0
    //   2442: checkcast 428	org/spongycastle/jce/provider/PKIXPolicyNode
    //   2445: astore 126
    //   2447: ldc_w 423
    //   2450: aload 126
    //   2452: invokevirtual 482	org/spongycastle/jce/provider/PKIXPolicyNode:getValidPolicy	()Ljava/lang/String;
    //   2455: invokevirtual 454	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   2458: ifeq +322 -> 2780
    //   2461: aload 126
    //   2463: invokevirtual 479	org/spongycastle/jce/provider/PKIXPolicyNode:getChildren	()Ljava/util/Iterator;
    //   2466: astore 127
    //   2468: aload 127
    //   2470: invokeinterface 122 1 0
    //   2475: ifeq +305 -> 2780
    //   2478: aload 127
    //   2480: invokeinterface 126 1 0
    //   2485: checkcast 428	org/spongycastle/jce/provider/PKIXPolicyNode
    //   2488: astore 128
    //   2490: ldc_w 423
    //   2493: aload 128
    //   2495: invokevirtual 482	org/spongycastle/jce/provider/PKIXPolicyNode:getValidPolicy	()Ljava/lang/String;
    //   2498: invokevirtual 454	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   2501: ifne -33 -> 2468
    //   2504: aload 120
    //   2506: aload 128
    //   2508: invokeinterface 426 2 0
    //   2513: pop
    //   2514: goto -46 -> 2468
    //   2517: aload 120
    //   2519: invokeinterface 244 1 0
    //   2524: astore 130
    //   2526: aload 130
    //   2528: invokeinterface 122 1 0
    //   2533: ifeq +42 -> 2575
    //   2536: aload 130
    //   2538: invokeinterface 126 1 0
    //   2543: checkcast 428	org/spongycastle/jce/provider/PKIXPolicyNode
    //   2546: astore 137
    //   2548: aload_1
    //   2549: aload 137
    //   2551: invokevirtual 482	org/spongycastle/jce/provider/PKIXPolicyNode:getValidPolicy	()Ljava/lang/String;
    //   2554: invokeinterface 224 2 0
    //   2559: ifne -33 -> 2526
    //   2562: aload 7
    //   2564: aload_2
    //   2565: aload 137
    //   2567: invokestatic 493	org/spongycastle/x509/PKIXCertPathReviewer:removePolicyNode	(Lorg/spongycastle/jce/provider/PKIXPolicyNode;[Ljava/util/List;Lorg/spongycastle/jce/provider/PKIXPolicyNode;)Lorg/spongycastle/jce/provider/PKIXPolicyNode;
    //   2570: astore 7
    //   2572: goto -46 -> 2526
    //   2575: aload 7
    //   2577: ifnull +89 -> 2666
    //   2580: iconst_m1
    //   2581: aload_0
    //   2582: getfield 265	org/spongycastle/x509/PKIXCertPathReviewer:n	I
    //   2585: iadd
    //   2586: istore 131
    //   2588: iload 131
    //   2590: iflt +76 -> 2666
    //   2593: aload_2
    //   2594: iload 131
    //   2596: aaload
    //   2597: astore 132
    //   2599: iconst_0
    //   2600: istore 133
    //   2602: aload 132
    //   2604: invokeinterface 172 1 0
    //   2609: istore 134
    //   2611: iload 133
    //   2613: iload 134
    //   2615: if_icmpge +45 -> 2660
    //   2618: aload 132
    //   2620: iload 133
    //   2622: invokeinterface 176 2 0
    //   2627: checkcast 428	org/spongycastle/jce/provider/PKIXPolicyNode
    //   2630: astore 135
    //   2632: aload 135
    //   2634: invokevirtual 489	org/spongycastle/jce/provider/PKIXPolicyNode:hasChildren	()Z
    //   2637: ifne +17 -> 2654
    //   2640: aload 7
    //   2642: aload_2
    //   2643: aload 135
    //   2645: invokestatic 493	org/spongycastle/x509/PKIXCertPathReviewer:removePolicyNode	(Lorg/spongycastle/jce/provider/PKIXPolicyNode;[Ljava/util/List;Lorg/spongycastle/jce/provider/PKIXPolicyNode;)Lorg/spongycastle/jce/provider/PKIXPolicyNode;
    //   2648: astore 136
    //   2650: aload 136
    //   2652: astore 7
    //   2654: iinc 133 1
    //   2657: goto -55 -> 2602
    //   2660: iinc 131 255
    //   2663: goto -75 -> 2588
    //   2666: aload 7
    //   2668: astore 118
    //   2670: goto -612 -> 2058
    //   2673: aload 69
    //   2675: astore 12
    //   2677: goto -2203 -> 474
    //   2680: aload 101
    //   2682: astore 12
    //   2684: goto -2210 -> 474
    //   2687: iinc 88 1
    //   2690: goto -2119 -> 571
    //   2693: iload 16
    //   2695: iconst_1
    //   2696: isub
    //   2697: istore 70
    //   2699: goto -1815 -> 884
    //   2702: iinc 72 1
    //   2705: goto -1807 -> 898
    //   2708: iinc 70 255
    //   2711: goto -1827 -> 884
    //   2714: aload 20
    //   2716: ifnonnull -1690 -> 1026
    //   2719: aconst_null
    //   2720: astore 7
    //   2722: goto -1696 -> 1026
    //   2725: iinc 60 1
    //   2728: goto -1626 -> 1102
    //   2731: iinc 29 1
    //   2734: goto -1429 -> 1305
    //   2737: goto -1095 -> 1642
    //   2740: goto -830 -> 1910
    //   2743: aconst_null
    //   2744: astore 118
    //   2746: goto -688 -> 2058
    //   2749: iinc 143 1
    //   2752: goto -571 -> 2181
    //   2755: iinc 140 1
    //   2758: goto -597 -> 2161
    //   2761: iinc 151 1
    //   2764: goto -430 -> 2334
    //   2767: iinc 149 255
    //   2770: goto -450 -> 2320
    //   2773: aload 7
    //   2775: astore 118
    //   2777: goto -719 -> 2058
    //   2780: iinc 124 1
    //   2783: goto -366 -> 2417
    //   2786: iinc 121 1
    //   2789: goto -392 -> 2397
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	2792	0	this	PKIXCertPathReviewer
    //   7	2542	1	localSet1	Set
    //   17	2626	2	arrayOfArrayList	ArrayList[]
    //   19	22	3	i	int
    //   22	6	4	j	int
    //   53	26	5	localHashSet1	java.util.HashSet
    //   95	2679	7	localObject1	Object
    //   119	1940	9	k	int
    //   132	1690	10	m	int
    //   145	1599	11	i1	int
    //   148	2535	12	localObject2	Object
    //   151	1736	13	localX509Certificate	X509Certificate
    //   335	9	14	localCertPathReviewerException	CertPathReviewerException
    //   164	1980	15	i2	int
    //   178	2519	16	i3	int
    //   385	23	17	localAnnotatedException1	AnnotatedException
    //   399	7	18	localErrorBundle1	ErrorBundle
    //   199	5	19	str1	String
    //   211	2504	20	localASN1Sequence1	ASN1Sequence
    //   1064	7	21	i4	int
    //   1194	23	22	localAnnotatedException2	AnnotatedException
    //   1208	7	23	localErrorBundle2	ErrorBundle
    //   1076	5	24	str2	String
    //   1085	193	25	localASN1Primitive	ASN1Primitive
    //   1282	38	26	localASN1Sequence2	ASN1Sequence
    //   1291	189	27	localHashMap	java.util.HashMap
    //   1300	139	28	localHashSet2	java.util.HashSet
    //   1303	1429	29	i5	int
    //   1310	7	30	i6	int
    //   1329	17	31	localASN1Sequence3	ASN1Sequence
    //   1343	75	32	str3	String
    //   1357	71	33	str4	String
    //   1378	17	34	localHashSet3	java.util.HashSet
    //   1445	13	39	localIterator1	Iterator
    //   1582	3	40	bool1	boolean
    //   1748	1	41	localAnnotatedException3	AnnotatedException
    //   1762	7	42	localErrorBundle3	ErrorBundle
    //   1616	5	43	str5	String
    //   1628	8	44	localASN1Sequence4	ASN1Sequence
    //   1640	13	45	localEnumeration1	java.util.Enumeration
    //   1829	1	46	localAnnotatedException4	AnnotatedException
    //   1843	7	47	localErrorBundle4	ErrorBundle
    //   1783	5	48	str6	String
    //   1795	8	49	localDERInteger	org.spongycastle.asn1.DERInteger
    //   1810	10	50	i7	int
    //   1662	59	51	localASN1TaggedObject1	org.spongycastle.asn1.ASN1TaggedObject
    //   1732	10	52	i8	int
    //   1704	10	53	i9	int
    //   1467	99	54	str7	String
    //   1523	23	55	localCertPathValidatorException1	CertPathValidatorException
    //   1537	7	56	localErrorBundle5	ErrorBundle
    //   1489	23	57	localAnnotatedException5	AnnotatedException
    //   1503	7	58	localErrorBundle6	ErrorBundle
    //   1097	20	59	localASN1Sequence5	ASN1Sequence
    //   1100	1626	60	i10	int
    //   1107	7	61	i11	int
    //   1126	14	62	localASN1Sequence6	ASN1Sequence
    //   1137	17	63	localDERObjectIdentifier1	DERObjectIdentifier
    //   1148	84	64	localDERObjectIdentifier2	DERObjectIdentifier
    //   1176	7	65	localErrorBundle7	ErrorBundle
    //   1254	7	66	localErrorBundle8	ErrorBundle
    //   1048	7	67	localErrorBundle9	ErrorBundle
    //   228	22	68	localEnumeration2	java.util.Enumeration
    //   237	2437	69	localHashSet4	java.util.HashSet
    //   884	1825	70	i12	int
    //   893	22	71	localArrayList1	ArrayList
    //   896	1807	72	i13	int
    //   905	7	73	i14	int
    //   926	14	74	localPKIXPolicyNode1	org.spongycastle.jce.provider.PKIXPolicyNode
    //   959	8	75	localSet2	Set
    //   976	40	76	bool2	boolean
    //   982	22	77	localArrayList2	ArrayList
    //   985	36	78	i15	int
    //   994	7	79	i16	int
    //   501	13	80	localEnumeration3	java.util.Enumeration
    //   523	24	81	localPolicyInformation1	org.spongycastle.asn1.x509.PolicyInformation
    //   539	3	82	bool3	boolean
    //   756	23	83	localCertPathValidatorException2	CertPathValidatorException
    //   770	7	84	localErrorBundle10	ErrorBundle
    //   554	299	85	localSet3	Set
    //   560	4	86	i17	int
    //   566	22	87	localArrayList3	ArrayList
    //   569	2119	88	i18	int
    //   578	7	89	i19	int
    //   599	264	90	localPKIXPolicyNode2	org.spongycastle.jce.provider.PKIXPolicyNode
    //   611	13	91	localIterator2	Iterator
    //   630	169	92	localObject3	Object
    //   645	210	93	str8	String
    //   648	164	94	i20	int
    //   655	15	95	localIterator3	Iterator
    //   823	26	96	localHashSet5	java.util.HashSet
    //   860	14	98	localPKIXPolicyNode3	org.spongycastle.jce.provider.PKIXPolicyNode
    //   701	22	100	localIterator4	Iterator
    //   710	1971	101	localHashSet6	java.util.HashSet
    //   729	17	102	localObject4	Object
    //   259	41	104	localPolicyInformation2	org.spongycastle.asn1.x509.PolicyInformation
    //   266	60	105	localASN1ObjectIdentifier	ASN1ObjectIdentifier
    //   292	3	107	bool4	boolean
    //   419	23	108	localCertPathValidatorException3	CertPathValidatorException
    //   433	7	109	localErrorBundle11	ErrorBundle
    //   307	21	110	localSet4	Set
    //   1866	3	111	bool5	boolean
    //   1981	1	112	localAnnotatedException6	AnnotatedException
    //   1995	7	113	localErrorBundle12	ErrorBundle
    //   1884	5	114	str9	String
    //   1896	8	115	localASN1Sequence7	ASN1Sequence
    //   1908	13	116	localEnumeration4	java.util.Enumeration
    //   2040	7	117	localErrorBundle13	ErrorBundle
    //   2063	713	118	localObject5	Object
    //   2080	7	119	localErrorBundle14	ErrorBundle
    //   2392	126	120	localHashSet7	java.util.HashSet
    //   2395	392	121	i21	int
    //   2399	7	122	i22	int
    //   2412	22	123	localArrayList4	ArrayList
    //   2415	366	124	i23	int
    //   2424	7	125	i24	int
    //   2445	17	126	localPKIXPolicyNode4	org.spongycastle.jce.provider.PKIXPolicyNode
    //   2466	13	127	localIterator5	Iterator
    //   2488	19	128	localPKIXPolicyNode5	org.spongycastle.jce.provider.PKIXPolicyNode
    //   2524	13	130	localIterator6	Iterator
    //   2586	75	131	i25	int
    //   2597	22	132	localArrayList5	ArrayList
    //   2600	55	133	i26	int
    //   2609	7	134	i27	int
    //   2630	14	135	localPKIXPolicyNode6	org.spongycastle.jce.provider.PKIXPolicyNode
    //   2648	3	136	localPKIXPolicyNode7	org.spongycastle.jce.provider.PKIXPolicyNode
    //   2546	20	137	localPKIXPolicyNode8	org.spongycastle.jce.provider.PKIXPolicyNode
    //   2131	7	138	localErrorBundle15	ErrorBundle
    //   2156	105	139	localHashSet8	java.util.HashSet
    //   2159	597	140	i28	int
    //   2163	7	141	i29	int
    //   2176	22	142	localArrayList6	ArrayList
    //   2179	571	143	i30	int
    //   2188	7	144	i31	int
    //   2209	17	145	localPKIXPolicyNode9	org.spongycastle.jce.provider.PKIXPolicyNode
    //   2230	15	146	localIterator7	Iterator
    //   2267	13	148	localIterator8	Iterator
    //   2318	450	149	i32	int
    //   2329	22	150	localArrayList7	ArrayList
    //   2332	430	151	i33	int
    //   2341	7	152	i34	int
    //   2362	14	153	localPKIXPolicyNode10	org.spongycastle.jce.provider.PKIXPolicyNode
    //   2292	5	154	str10	String
    //   1930	27	156	localASN1TaggedObject2	org.spongycastle.asn1.ASN1TaggedObject
    //   1968	3	157	i35	int
    // Exception table:
    //   from	to	target	type
    //   153	166	335	org/spongycastle/x509/CertPathReviewerException
    //   171	196	335	org/spongycastle/x509/CertPathReviewerException
    //   196	213	335	org/spongycastle/x509/CertPathReviewerException
    //   223	239	335	org/spongycastle/x509/CertPathReviewerException
    //   239	294	335	org/spongycastle/x509/CertPathReviewerException
    //   299	309	335	org/spongycastle/x509/CertPathReviewerException
    //   309	332	335	org/spongycastle/x509/CertPathReviewerException
    //   387	419	335	org/spongycastle/x509/CertPathReviewerException
    //   421	453	335	org/spongycastle/x509/CertPathReviewerException
    //   458	471	335	org/spongycastle/x509/CertPathReviewerException
    //   479	496	335	org/spongycastle/x509/CertPathReviewerException
    //   496	503	335	org/spongycastle/x509/CertPathReviewerException
    //   503	541	335	org/spongycastle/x509/CertPathReviewerException
    //   546	556	335	org/spongycastle/x509/CertPathReviewerException
    //   562	568	335	org/spongycastle/x509/CertPathReviewerException
    //   571	580	335	org/spongycastle/x509/CertPathReviewerException
    //   587	613	335	org/spongycastle/x509/CertPathReviewerException
    //   613	647	335	org/spongycastle/x509/CertPathReviewerException
    //   650	657	335	org/spongycastle/x509/CertPathReviewerException
    //   657	688	335	org/spongycastle/x509/CertPathReviewerException
    //   694	712	335	org/spongycastle/x509/CertPathReviewerException
    //   712	753	335	org/spongycastle/x509/CertPathReviewerException
    //   758	790	335	org/spongycastle/x509/CertPathReviewerException
    //   790	808	335	org/spongycastle/x509/CertPathReviewerException
    //   816	881	335	org/spongycastle/x509/CertPathReviewerException
    //   889	895	335	org/spongycastle/x509/CertPathReviewerException
    //   898	907	335	org/spongycastle/x509/CertPathReviewerException
    //   914	946	335	org/spongycastle/x509/CertPathReviewerException
    //   954	961	335	org/spongycastle/x509/CertPathReviewerException
    //   966	984	335	org/spongycastle/x509/CertPathReviewerException
    //   987	996	335	org/spongycastle/x509/CertPathReviewerException
    //   1003	1020	335	org/spongycastle/x509/CertPathReviewerException
    //   1036	1060	335	org/spongycastle/x509/CertPathReviewerException
    //   1060	1066	335	org/spongycastle/x509/CertPathReviewerException
    //   1073	1087	335	org/spongycastle/x509/CertPathReviewerException
    //   1092	1099	335	org/spongycastle/x509/CertPathReviewerException
    //   1102	1109	335	org/spongycastle/x509/CertPathReviewerException
    //   1116	1194	335	org/spongycastle/x509/CertPathReviewerException
    //   1196	1228	335	org/spongycastle/x509/CertPathReviewerException
    //   1228	1272	335	org/spongycastle/x509/CertPathReviewerException
    //   1277	1302	335	org/spongycastle/x509/CertPathReviewerException
    //   1305	1312	335	org/spongycastle/x509/CertPathReviewerException
    //   1319	1412	335	org/spongycastle/x509/CertPathReviewerException
    //   1415	1435	335	org/spongycastle/x509/CertPathReviewerException
    //   1438	1447	335	org/spongycastle/x509/CertPathReviewerException
    //   1447	1469	335	org/spongycastle/x509/CertPathReviewerException
    //   1474	1486	335	org/spongycastle/x509/CertPathReviewerException
    //   1491	1523	335	org/spongycastle/x509/CertPathReviewerException
    //   1525	1557	335	org/spongycastle/x509/CertPathReviewerException
    //   1562	1574	335	org/spongycastle/x509/CertPathReviewerException
    //   1577	1584	335	org/spongycastle/x509/CertPathReviewerException
    //   1613	1630	335	org/spongycastle/x509/CertPathReviewerException
    //   1635	1642	335	org/spongycastle/x509/CertPathReviewerException
    //   1642	1692	335	org/spongycastle/x509/CertPathReviewerException
    //   1692	1706	335	org/spongycastle/x509/CertPathReviewerException
    //   1720	1734	335	org/spongycastle/x509/CertPathReviewerException
    //   1750	1780	335	org/spongycastle/x509/CertPathReviewerException
    //   1780	1797	335	org/spongycastle/x509/CertPathReviewerException
    //   1802	1812	335	org/spongycastle/x509/CertPathReviewerException
    //   1831	1861	335	org/spongycastle/x509/CertPathReviewerException
    //   1861	1868	335	org/spongycastle/x509/CertPathReviewerException
    //   1881	1898	335	org/spongycastle/x509/CertPathReviewerException
    //   1903	1910	335	org/spongycastle/x509/CertPathReviewerException
    //   1910	1956	335	org/spongycastle/x509/CertPathReviewerException
    //   1956	1970	335	org/spongycastle/x509/CertPathReviewerException
    //   1983	2013	335	org/spongycastle/x509/CertPathReviewerException
    //   2018	2058	335	org/spongycastle/x509/CertPathReviewerException
    //   2068	2092	335	org/spongycastle/x509/CertPathReviewerException
    //   2092	2149	335	org/spongycastle/x509/CertPathReviewerException
    //   2149	2158	335	org/spongycastle/x509/CertPathReviewerException
    //   2161	2165	335	org/spongycastle/x509/CertPathReviewerException
    //   2172	2178	335	org/spongycastle/x509/CertPathReviewerException
    //   2181	2190	335	org/spongycastle/x509/CertPathReviewerException
    //   2197	2232	335	org/spongycastle/x509/CertPathReviewerException
    //   2232	2257	335	org/spongycastle/x509/CertPathReviewerException
    //   2260	2269	335	org/spongycastle/x509/CertPathReviewerException
    //   2269	2304	335	org/spongycastle/x509/CertPathReviewerException
    //   2312	2320	335	org/spongycastle/x509/CertPathReviewerException
    //   2325	2331	335	org/spongycastle/x509/CertPathReviewerException
    //   2334	2343	335	org/spongycastle/x509/CertPathReviewerException
    //   2350	2382	335	org/spongycastle/x509/CertPathReviewerException
    //   2385	2394	335	org/spongycastle/x509/CertPathReviewerException
    //   2397	2401	335	org/spongycastle/x509/CertPathReviewerException
    //   2408	2414	335	org/spongycastle/x509/CertPathReviewerException
    //   2417	2426	335	org/spongycastle/x509/CertPathReviewerException
    //   2433	2468	335	org/spongycastle/x509/CertPathReviewerException
    //   2468	2514	335	org/spongycastle/x509/CertPathReviewerException
    //   2517	2526	335	org/spongycastle/x509/CertPathReviewerException
    //   2526	2572	335	org/spongycastle/x509/CertPathReviewerException
    //   2580	2588	335	org/spongycastle/x509/CertPathReviewerException
    //   2593	2599	335	org/spongycastle/x509/CertPathReviewerException
    //   2602	2611	335	org/spongycastle/x509/CertPathReviewerException
    //   2618	2650	335	org/spongycastle/x509/CertPathReviewerException
    //   196	213	385	org/spongycastle/jce/provider/AnnotatedException
    //   299	309	419	java/security/cert/CertPathValidatorException
    //   546	556	756	java/security/cert/CertPathValidatorException
    //   1073	1087	1194	org/spongycastle/jce/provider/AnnotatedException
    //   1474	1486	1489	org/spongycastle/jce/provider/AnnotatedException
    //   1474	1486	1523	java/security/cert/CertPathValidatorException
    //   1613	1630	1748	org/spongycastle/jce/provider/AnnotatedException
    //   1635	1642	1748	org/spongycastle/jce/provider/AnnotatedException
    //   1642	1692	1748	org/spongycastle/jce/provider/AnnotatedException
    //   1692	1706	1748	org/spongycastle/jce/provider/AnnotatedException
    //   1720	1734	1748	org/spongycastle/jce/provider/AnnotatedException
    //   1780	1797	1829	org/spongycastle/jce/provider/AnnotatedException
    //   1802	1812	1829	org/spongycastle/jce/provider/AnnotatedException
    //   1881	1898	1981	org/spongycastle/jce/provider/AnnotatedException
    //   1903	1910	1981	org/spongycastle/jce/provider/AnnotatedException
    //   1910	1956	1981	org/spongycastle/jce/provider/AnnotatedException
    //   1956	1970	1981	org/spongycastle/jce/provider/AnnotatedException
  }
  
  /* Error */
  private void checkSignatures()
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_1
    //   2: iconst_2
    //   3: anewarray 133	java/lang/Object
    //   6: astore_2
    //   7: aload_2
    //   8: iconst_0
    //   9: new 573	org/spongycastle/i18n/filter/TrustedInput
    //   12: dup
    //   13: aload_0
    //   14: getfield 575	org/spongycastle/x509/PKIXCertPathReviewer:validDate	Ljava/util/Date;
    //   17: invokespecial 576	org/spongycastle/i18n/filter/TrustedInput:<init>	(Ljava/lang/Object;)V
    //   20: aastore
    //   21: aload_2
    //   22: iconst_1
    //   23: new 573	org/spongycastle/i18n/filter/TrustedInput
    //   26: dup
    //   27: new 578	java/util/Date
    //   30: dup
    //   31: invokespecial 579	java/util/Date:<init>	()V
    //   34: invokespecial 576	org/spongycastle/i18n/filter/TrustedInput:<init>	(Ljava/lang/Object;)V
    //   37: aastore
    //   38: new 147	org/spongycastle/i18n/ErrorBundle
    //   41: dup
    //   42: ldc 11
    //   44: ldc_w 581
    //   47: aload_2
    //   48: invokespecial 152	org/spongycastle/i18n/ErrorBundle:<init>	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   51: astore_3
    //   52: aload_0
    //   53: aload_3
    //   54: invokevirtual 411	org/spongycastle/x509/PKIXCertPathReviewer:addNotification	(Lorg/spongycastle/i18n/ErrorBundle;)V
    //   57: aload_0
    //   58: getfield 169	org/spongycastle/x509/PKIXCertPathReviewer:certs	Ljava/util/List;
    //   61: iconst_m1
    //   62: aload_0
    //   63: getfield 169	org/spongycastle/x509/PKIXCertPathReviewer:certs	Ljava/util/List;
    //   66: invokeinterface 172 1 0
    //   71: iadd
    //   72: invokeinterface 176 2 0
    //   77: checkcast 178	java/security/cert/X509Certificate
    //   80: astore 84
    //   82: aload_0
    //   83: aload 84
    //   85: aload_0
    //   86: getfield 104	org/spongycastle/x509/PKIXCertPathReviewer:pkixParams	Ljava/security/cert/PKIXParameters;
    //   89: invokevirtual 584	java/security/cert/PKIXParameters:getTrustAnchors	()Ljava/util/Set;
    //   92: invokevirtual 587	org/spongycastle/x509/PKIXCertPathReviewer:getTrustAnchors	(Ljava/security/cert/X509Certificate;Ljava/util/Set;)Ljava/util/Collection;
    //   95: astore 85
    //   97: aload 85
    //   99: invokeinterface 590 1 0
    //   104: istore 86
    //   106: aconst_null
    //   107: astore_1
    //   108: iload 86
    //   110: iconst_1
    //   111: if_icmple +412 -> 523
    //   114: iconst_2
    //   115: anewarray 133	java/lang/Object
    //   118: astore 87
    //   120: aload 87
    //   122: iconst_0
    //   123: new 85	java/lang/Integer
    //   126: dup
    //   127: aload 85
    //   129: invokeinterface 590 1 0
    //   134: invokespecial 406	java/lang/Integer:<init>	(I)V
    //   137: aastore
    //   138: aload 87
    //   140: iconst_1
    //   141: new 326	org/spongycastle/i18n/filter/UntrustedInput
    //   144: dup
    //   145: aload 84
    //   147: invokevirtual 594	java/security/cert/X509Certificate:getIssuerX500Principal	()Ljavax/security/auth/x500/X500Principal;
    //   150: invokespecial 329	org/spongycastle/i18n/filter/UntrustedInput:<init>	(Ljava/lang/Object;)V
    //   153: aastore
    //   154: new 147	org/spongycastle/i18n/ErrorBundle
    //   157: dup
    //   158: ldc 11
    //   160: ldc_w 596
    //   163: aload 87
    //   165: invokespecial 152	org/spongycastle/i18n/ErrorBundle:<init>	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   168: astore 88
    //   170: aload_0
    //   171: aload 88
    //   173: invokevirtual 387	org/spongycastle/x509/PKIXCertPathReviewer:addError	(Lorg/spongycastle/i18n/ErrorBundle;)V
    //   176: aconst_null
    //   177: astore 8
    //   179: aload_1
    //   180: ifnull +69 -> 249
    //   183: aload_1
    //   184: invokevirtual 602	java/security/cert/TrustAnchor:getTrustedCert	()Ljava/security/cert/X509Certificate;
    //   187: astore 75
    //   189: aload 75
    //   191: ifnull +595 -> 786
    //   194: aload 75
    //   196: invokestatic 273	org/spongycastle/x509/PKIXCertPathReviewer:getSubjectPrincipal	(Ljava/security/cert/X509Certificate;)Ljavax/security/auth/x500/X500Principal;
    //   199: astore 82
    //   201: aload 82
    //   203: astore 8
    //   205: aload 75
    //   207: ifnull +42 -> 249
    //   210: aload 75
    //   212: invokevirtual 606	java/security/cert/X509Certificate:getKeyUsage	()[Z
    //   215: astore 77
    //   217: aload 77
    //   219: ifnull +30 -> 249
    //   222: aload 77
    //   224: iconst_5
    //   225: baload
    //   226: ifne +23 -> 249
    //   229: new 147	org/spongycastle/i18n/ErrorBundle
    //   232: dup
    //   233: ldc 11
    //   235: ldc_w 608
    //   238: invokespecial 341	org/spongycastle/i18n/ErrorBundle:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   241: astore 78
    //   243: aload_0
    //   244: aload 78
    //   246: invokevirtual 411	org/spongycastle/x509/PKIXCertPathReviewer:addNotification	(Lorg/spongycastle/i18n/ErrorBundle;)V
    //   249: aload 8
    //   251: astore 9
    //   253: aconst_null
    //   254: astore 10
    //   256: aconst_null
    //   257: astore 11
    //   259: aload_1
    //   260: ifnull +40 -> 300
    //   263: aload_1
    //   264: invokevirtual 602	java/security/cert/TrustAnchor:getTrustedCert	()Ljava/security/cert/X509Certificate;
    //   267: astore 10
    //   269: aload 10
    //   271: ifnull +586 -> 857
    //   274: aload 10
    //   276: invokevirtual 612	java/security/cert/X509Certificate:getPublicKey	()Ljava/security/PublicKey;
    //   279: astore 11
    //   281: aload 11
    //   283: invokestatic 616	org/spongycastle/x509/PKIXCertPathReviewer:getAlgorithmIdentifier	(Ljava/security/PublicKey;)Lorg/spongycastle/asn1/x509/AlgorithmIdentifier;
    //   286: astore 72
    //   288: aload 72
    //   290: invokevirtual 621	org/spongycastle/asn1/x509/AlgorithmIdentifier:getObjectId	()Lorg/spongycastle/asn1/ASN1ObjectIdentifier;
    //   293: pop
    //   294: aload 72
    //   296: invokevirtual 625	org/spongycastle/asn1/x509/AlgorithmIdentifier:getParameters	()Lorg/spongycastle/asn1/ASN1Encodable;
    //   299: pop
    //   300: iconst_m1
    //   301: aload_0
    //   302: getfield 169	org/spongycastle/x509/PKIXCertPathReviewer:certs	Ljava/util/List;
    //   305: invokeinterface 172 1 0
    //   310: iadd
    //   311: istore 12
    //   313: iload 12
    //   315: iflt +1515 -> 1830
    //   318: aload_0
    //   319: getfield 265	org/spongycastle/x509/PKIXCertPathReviewer:n	I
    //   322: iload 12
    //   324: isub
    //   325: istore 13
    //   327: aload_0
    //   328: getfield 169	org/spongycastle/x509/PKIXCertPathReviewer:certs	Ljava/util/List;
    //   331: iload 12
    //   333: invokeinterface 176 2 0
    //   338: checkcast 178	java/security/cert/X509Certificate
    //   341: astore 14
    //   343: aload 11
    //   345: ifnull +608 -> 953
    //   348: aload 14
    //   350: aload 11
    //   352: aload_0
    //   353: getfield 104	org/spongycastle/x509/PKIXCertPathReviewer:pkixParams	Ljava/security/cert/PKIXParameters;
    //   356: invokevirtual 628	java/security/cert/PKIXParameters:getSigProvider	()Ljava/lang/String;
    //   359: invokestatic 632	org/spongycastle/jce/provider/CertPathValidatorUtilities:verifyX509Certificate	(Ljava/security/cert/X509Certificate;Ljava/security/PublicKey;Ljava/lang/String;)V
    //   362: aload 14
    //   364: aload_0
    //   365: getfield 575	org/spongycastle/x509/PKIXCertPathReviewer:validDate	Ljava/util/Date;
    //   368: invokevirtual 636	java/security/cert/X509Certificate:checkValidity	(Ljava/util/Date;)V
    //   371: aload_0
    //   372: getfield 104	org/spongycastle/x509/PKIXCertPathReviewer:pkixParams	Ljava/security/cert/PKIXParameters;
    //   375: invokevirtual 639	java/security/cert/PKIXParameters:isRevocationEnabled	()Z
    //   378: ifeq +1107 -> 1485
    //   381: aload 14
    //   383: getstatic 53	org/spongycastle/x509/PKIXCertPathReviewer:CRL_DIST_POINTS	Ljava/lang/String;
    //   386: invokestatic 306	org/spongycastle/x509/PKIXCertPathReviewer:getExtensionValue	(Ljava/security/cert/X509Extension;Ljava/lang/String;)Lorg/spongycastle/asn1/ASN1Primitive;
    //   389: astore 52
    //   391: aconst_null
    //   392: astore 37
    //   394: aload 52
    //   396: ifnull +14 -> 410
    //   399: aload 52
    //   401: invokestatic 644	org/spongycastle/asn1/x509/CRLDistPoint:getInstance	(Ljava/lang/Object;)Lorg/spongycastle/asn1/x509/CRLDistPoint;
    //   404: astore 53
    //   406: aload 53
    //   408: astore 37
    //   410: aload 14
    //   412: getstatic 58	org/spongycastle/x509/PKIXCertPathReviewer:AUTH_INFO_ACCESS	Ljava/lang/String;
    //   415: invokestatic 306	org/spongycastle/x509/PKIXCertPathReviewer:getExtensionValue	(Ljava/security/cert/X509Extension;Ljava/lang/String;)Lorg/spongycastle/asn1/ASN1Primitive;
    //   418: astore 50
    //   420: aconst_null
    //   421: astore 40
    //   423: aload 50
    //   425: ifnull +14 -> 439
    //   428: aload 50
    //   430: invokestatic 649	org/spongycastle/asn1/x509/AuthorityInformationAccess:getInstance	(Ljava/lang/Object;)Lorg/spongycastle/asn1/x509/AuthorityInformationAccess;
    //   433: astore 51
    //   435: aload 51
    //   437: astore 40
    //   439: aload_0
    //   440: aload 37
    //   442: invokevirtual 653	org/spongycastle/x509/PKIXCertPathReviewer:getCRLDistUrls	(Lorg/spongycastle/asn1/x509/CRLDistPoint;)Ljava/util/Vector;
    //   445: astore 41
    //   447: aload_0
    //   448: aload 40
    //   450: invokevirtual 657	org/spongycastle/x509/PKIXCertPathReviewer:getOCSPUrls	(Lorg/spongycastle/asn1/x509/AuthorityInformationAccess;)Ljava/util/Vector;
    //   453: astore 42
    //   455: aload 41
    //   457: invokevirtual 660	java/util/Vector:iterator	()Ljava/util/Iterator;
    //   460: astore 43
    //   462: aload 43
    //   464: invokeinterface 122 1 0
    //   469: ifeq +924 -> 1393
    //   472: iconst_1
    //   473: anewarray 133	java/lang/Object
    //   476: astore 48
    //   478: aload 48
    //   480: iconst_0
    //   481: new 662	org/spongycastle/i18n/filter/UntrustedUrlInput
    //   484: dup
    //   485: aload 43
    //   487: invokeinterface 126 1 0
    //   492: invokespecial 663	org/spongycastle/i18n/filter/UntrustedUrlInput:<init>	(Ljava/lang/Object;)V
    //   495: aastore
    //   496: new 147	org/spongycastle/i18n/ErrorBundle
    //   499: dup
    //   500: ldc 11
    //   502: ldc_w 665
    //   505: aload 48
    //   507: invokespecial 152	org/spongycastle/i18n/ErrorBundle:<init>	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   510: astore 49
    //   512: aload_0
    //   513: aload 49
    //   515: iload 12
    //   517: invokevirtual 667	org/spongycastle/x509/PKIXCertPathReviewer:addNotification	(Lorg/spongycastle/i18n/ErrorBundle;I)V
    //   520: goto -58 -> 462
    //   523: aload 85
    //   525: invokeinterface 668 1 0
    //   530: istore 89
    //   532: aconst_null
    //   533: astore_1
    //   534: iload 89
    //   536: ifeq +89 -> 625
    //   539: iconst_2
    //   540: anewarray 133	java/lang/Object
    //   543: astore 90
    //   545: aload 90
    //   547: iconst_0
    //   548: new 326	org/spongycastle/i18n/filter/UntrustedInput
    //   551: dup
    //   552: aload 84
    //   554: invokevirtual 594	java/security/cert/X509Certificate:getIssuerX500Principal	()Ljavax/security/auth/x500/X500Principal;
    //   557: invokespecial 329	org/spongycastle/i18n/filter/UntrustedInput:<init>	(Ljava/lang/Object;)V
    //   560: aastore
    //   561: aload 90
    //   563: iconst_1
    //   564: new 85	java/lang/Integer
    //   567: dup
    //   568: aload_0
    //   569: getfield 104	org/spongycastle/x509/PKIXCertPathReviewer:pkixParams	Ljava/security/cert/PKIXParameters;
    //   572: invokevirtual 584	java/security/cert/PKIXParameters:getTrustAnchors	()Ljava/util/Set;
    //   575: invokeinterface 669 1 0
    //   580: invokespecial 406	java/lang/Integer:<init>	(I)V
    //   583: aastore
    //   584: new 147	org/spongycastle/i18n/ErrorBundle
    //   587: dup
    //   588: ldc 11
    //   590: ldc_w 671
    //   593: aload 90
    //   595: invokespecial 152	org/spongycastle/i18n/ErrorBundle:<init>	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   598: astore 91
    //   600: aload_0
    //   601: aload 91
    //   603: invokevirtual 387	org/spongycastle/x509/PKIXCertPathReviewer:addError	(Lorg/spongycastle/i18n/ErrorBundle;)V
    //   606: aconst_null
    //   607: astore_1
    //   608: goto -432 -> 176
    //   611: astore 83
    //   613: aload_0
    //   614: aload 83
    //   616: invokevirtual 159	org/spongycastle/x509/CertPathReviewerException:getErrorMessage	()Lorg/spongycastle/i18n/ErrorBundle;
    //   619: invokevirtual 387	org/spongycastle/x509/PKIXCertPathReviewer:addError	(Lorg/spongycastle/i18n/ErrorBundle;)V
    //   622: goto -446 -> 176
    //   625: aload 85
    //   627: invokeinterface 672 1 0
    //   632: invokeinterface 126 1 0
    //   637: checkcast 598	java/security/cert/TrustAnchor
    //   640: astore_1
    //   641: aload_1
    //   642: invokevirtual 602	java/security/cert/TrustAnchor:getTrustedCert	()Ljava/security/cert/X509Certificate;
    //   645: ifnull +128 -> 773
    //   648: aload_1
    //   649: invokevirtual 602	java/security/cert/TrustAnchor:getTrustedCert	()Ljava/security/cert/X509Certificate;
    //   652: invokevirtual 612	java/security/cert/X509Certificate:getPublicKey	()Ljava/security/PublicKey;
    //   655: astore 98
    //   657: aload 98
    //   659: astore 93
    //   661: aload_0
    //   662: getfield 104	org/spongycastle/x509/PKIXCertPathReviewer:pkixParams	Ljava/security/cert/PKIXParameters;
    //   665: invokevirtual 628	java/security/cert/PKIXParameters:getSigProvider	()Ljava/lang/String;
    //   668: astore 97
    //   670: aload 84
    //   672: aload 93
    //   674: aload 97
    //   676: invokestatic 632	org/spongycastle/jce/provider/CertPathValidatorUtilities:verifyX509Certificate	(Ljava/security/cert/X509Certificate;Ljava/security/PublicKey;Ljava/lang/String;)V
    //   679: goto -503 -> 176
    //   682: astore 95
    //   684: new 147	org/spongycastle/i18n/ErrorBundle
    //   687: dup
    //   688: ldc 11
    //   690: ldc_w 674
    //   693: invokespecial 341	org/spongycastle/i18n/ErrorBundle:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   696: astore 96
    //   698: aload_0
    //   699: aload 96
    //   701: invokevirtual 387	org/spongycastle/x509/PKIXCertPathReviewer:addError	(Lorg/spongycastle/i18n/ErrorBundle;)V
    //   704: goto -528 -> 176
    //   707: astore 4
    //   709: iconst_2
    //   710: anewarray 133	java/lang/Object
    //   713: astore 5
    //   715: aload 5
    //   717: iconst_0
    //   718: new 326	org/spongycastle/i18n/filter/UntrustedInput
    //   721: dup
    //   722: aload 4
    //   724: invokevirtual 675	java/lang/Throwable:getMessage	()Ljava/lang/String;
    //   727: invokespecial 329	org/spongycastle/i18n/filter/UntrustedInput:<init>	(Ljava/lang/Object;)V
    //   730: aastore
    //   731: new 326	org/spongycastle/i18n/filter/UntrustedInput
    //   734: dup
    //   735: aload 4
    //   737: invokespecial 329	org/spongycastle/i18n/filter/UntrustedInput:<init>	(Ljava/lang/Object;)V
    //   740: astore 6
    //   742: aload 5
    //   744: iconst_1
    //   745: aload 6
    //   747: aastore
    //   748: new 147	org/spongycastle/i18n/ErrorBundle
    //   751: dup
    //   752: ldc 11
    //   754: ldc_w 677
    //   757: aload 5
    //   759: invokespecial 152	org/spongycastle/i18n/ErrorBundle:<init>	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   762: astore 7
    //   764: aload_0
    //   765: aload 7
    //   767: invokevirtual 387	org/spongycastle/x509/PKIXCertPathReviewer:addError	(Lorg/spongycastle/i18n/ErrorBundle;)V
    //   770: goto -594 -> 176
    //   773: aload_1
    //   774: invokevirtual 680	java/security/cert/TrustAnchor:getCAPublicKey	()Ljava/security/PublicKey;
    //   777: astore 92
    //   779: aload 92
    //   781: astore 93
    //   783: goto -122 -> 661
    //   786: new 279	javax/security/auth/x500/X500Principal
    //   789: dup
    //   790: aload_1
    //   791: invokevirtual 683	java/security/cert/TrustAnchor:getCAName	()Ljava/lang/String;
    //   794: invokespecial 684	javax/security/auth/x500/X500Principal:<init>	(Ljava/lang/String;)V
    //   797: astore 76
    //   799: aload 76
    //   801: astore 8
    //   803: goto -598 -> 205
    //   806: astore 79
    //   808: iconst_1
    //   809: anewarray 133	java/lang/Object
    //   812: astore 80
    //   814: aload 80
    //   816: iconst_0
    //   817: new 326	org/spongycastle/i18n/filter/UntrustedInput
    //   820: dup
    //   821: aload_1
    //   822: invokevirtual 683	java/security/cert/TrustAnchor:getCAName	()Ljava/lang/String;
    //   825: invokespecial 329	org/spongycastle/i18n/filter/UntrustedInput:<init>	(Ljava/lang/Object;)V
    //   828: aastore
    //   829: new 147	org/spongycastle/i18n/ErrorBundle
    //   832: dup
    //   833: ldc 11
    //   835: ldc_w 686
    //   838: aload 80
    //   840: invokespecial 152	org/spongycastle/i18n/ErrorBundle:<init>	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   843: astore 81
    //   845: aload_0
    //   846: aload 81
    //   848: invokevirtual 387	org/spongycastle/x509/PKIXCertPathReviewer:addError	(Lorg/spongycastle/i18n/ErrorBundle;)V
    //   851: aconst_null
    //   852: astore 8
    //   854: goto -649 -> 205
    //   857: aload_1
    //   858: invokevirtual 680	java/security/cert/TrustAnchor:getCAPublicKey	()Ljava/security/PublicKey;
    //   861: astore 11
    //   863: goto -582 -> 281
    //   866: astore 70
    //   868: new 147	org/spongycastle/i18n/ErrorBundle
    //   871: dup
    //   872: ldc 11
    //   874: ldc_w 688
    //   877: invokespecial 341	org/spongycastle/i18n/ErrorBundle:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   880: astore 71
    //   882: aload_0
    //   883: aload 71
    //   885: invokevirtual 387	org/spongycastle/x509/PKIXCertPathReviewer:addError	(Lorg/spongycastle/i18n/ErrorBundle;)V
    //   888: goto -588 -> 300
    //   891: astore 67
    //   893: iconst_3
    //   894: anewarray 133	java/lang/Object
    //   897: astore 68
    //   899: aload 68
    //   901: iconst_0
    //   902: aload 67
    //   904: invokevirtual 689	java/security/GeneralSecurityException:getMessage	()Ljava/lang/String;
    //   907: aastore
    //   908: aload 68
    //   910: iconst_1
    //   911: aload 67
    //   913: aastore
    //   914: aload 68
    //   916: iconst_2
    //   917: aload 67
    //   919: invokevirtual 140	java/lang/Object:getClass	()Ljava/lang/Class;
    //   922: invokevirtual 145	java/lang/Class:getName	()Ljava/lang/String;
    //   925: aastore
    //   926: new 147	org/spongycastle/i18n/ErrorBundle
    //   929: dup
    //   930: ldc 11
    //   932: ldc_w 691
    //   935: aload 68
    //   937: invokespecial 152	org/spongycastle/i18n/ErrorBundle:<init>	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   940: astore 69
    //   942: aload_0
    //   943: aload 69
    //   945: iload 12
    //   947: invokevirtual 167	org/spongycastle/x509/PKIXCertPathReviewer:addError	(Lorg/spongycastle/i18n/ErrorBundle;I)V
    //   950: goto -588 -> 362
    //   953: aload 14
    //   955: invokestatic 269	org/spongycastle/x509/PKIXCertPathReviewer:isSelfIssued	(Ljava/security/cert/X509Certificate;)Z
    //   958: ifeq +107 -> 1065
    //   961: aload 14
    //   963: aload 14
    //   965: invokevirtual 612	java/security/cert/X509Certificate:getPublicKey	()Ljava/security/PublicKey;
    //   968: aload_0
    //   969: getfield 104	org/spongycastle/x509/PKIXCertPathReviewer:pkixParams	Ljava/security/cert/PKIXParameters;
    //   972: invokevirtual 628	java/security/cert/PKIXParameters:getSigProvider	()Ljava/lang/String;
    //   975: invokestatic 632	org/spongycastle/jce/provider/CertPathValidatorUtilities:verifyX509Certificate	(Ljava/security/cert/X509Certificate;Ljava/security/PublicKey;Ljava/lang/String;)V
    //   978: new 147	org/spongycastle/i18n/ErrorBundle
    //   981: dup
    //   982: ldc 11
    //   984: ldc_w 693
    //   987: invokespecial 341	org/spongycastle/i18n/ErrorBundle:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   990: astore 66
    //   992: aload_0
    //   993: aload 66
    //   995: iload 12
    //   997: invokevirtual 167	org/spongycastle/x509/PKIXCertPathReviewer:addError	(Lorg/spongycastle/i18n/ErrorBundle;I)V
    //   1000: goto -638 -> 362
    //   1003: astore 63
    //   1005: iconst_3
    //   1006: anewarray 133	java/lang/Object
    //   1009: astore 64
    //   1011: aload 64
    //   1013: iconst_0
    //   1014: aload 63
    //   1016: invokevirtual 689	java/security/GeneralSecurityException:getMessage	()Ljava/lang/String;
    //   1019: aastore
    //   1020: aload 64
    //   1022: iconst_1
    //   1023: aload 63
    //   1025: aastore
    //   1026: aload 64
    //   1028: iconst_2
    //   1029: aload 63
    //   1031: invokevirtual 140	java/lang/Object:getClass	()Ljava/lang/Class;
    //   1034: invokevirtual 145	java/lang/Class:getName	()Ljava/lang/String;
    //   1037: aastore
    //   1038: new 147	org/spongycastle/i18n/ErrorBundle
    //   1041: dup
    //   1042: ldc 11
    //   1044: ldc_w 691
    //   1047: aload 64
    //   1049: invokespecial 152	org/spongycastle/i18n/ErrorBundle:<init>	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   1052: astore 65
    //   1054: aload_0
    //   1055: aload 65
    //   1057: iload 12
    //   1059: invokevirtual 167	org/spongycastle/x509/PKIXCertPathReviewer:addError	(Lorg/spongycastle/i18n/ErrorBundle;I)V
    //   1062: goto -700 -> 362
    //   1065: new 147	org/spongycastle/i18n/ErrorBundle
    //   1068: dup
    //   1069: ldc 11
    //   1071: ldc_w 695
    //   1074: invokespecial 341	org/spongycastle/i18n/ErrorBundle:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   1077: astore 15
    //   1079: aload 14
    //   1081: getstatic 698	org/spongycastle/asn1/x509/X509Extensions:AuthorityKeyIdentifier	Lorg/spongycastle/asn1/ASN1ObjectIdentifier;
    //   1084: invokevirtual 46	org/spongycastle/asn1/ASN1ObjectIdentifier:getId	()Ljava/lang/String;
    //   1087: invokevirtual 701	java/security/cert/X509Certificate:getExtensionValue	(Ljava/lang/String;)[B
    //   1090: astore 16
    //   1092: aload 16
    //   1094: ifnull +126 -> 1220
    //   1097: aload 16
    //   1099: invokestatic 707	org/spongycastle/x509/extension/X509ExtensionUtil:fromExtensionValue	([B)Lorg/spongycastle/asn1/ASN1Primitive;
    //   1102: invokestatic 712	org/spongycastle/asn1/x509/AuthorityKeyIdentifier:getInstance	(Ljava/lang/Object;)Lorg/spongycastle/asn1/x509/AuthorityKeyIdentifier;
    //   1105: astore 58
    //   1107: aload 58
    //   1109: invokevirtual 716	org/spongycastle/asn1/x509/AuthorityKeyIdentifier:getAuthorityCertIssuer	()Lorg/spongycastle/asn1/x509/GeneralNames;
    //   1112: astore 59
    //   1114: aload 59
    //   1116: ifnull +104 -> 1220
    //   1119: aload 59
    //   1121: invokevirtual 722	org/spongycastle/asn1/x509/GeneralNames:getNames	()[Lorg/spongycastle/asn1/x509/GeneralName;
    //   1124: iconst_0
    //   1125: aaload
    //   1126: astore 60
    //   1128: aload 58
    //   1130: invokevirtual 725	org/spongycastle/asn1/x509/AuthorityKeyIdentifier:getAuthorityCertSerialNumber	()Ljava/math/BigInteger;
    //   1133: astore 61
    //   1135: aload 61
    //   1137: ifnull +83 -> 1220
    //   1140: bipush 7
    //   1142: anewarray 133	java/lang/Object
    //   1145: astore 62
    //   1147: aload 62
    //   1149: iconst_0
    //   1150: new 727	org/spongycastle/i18n/LocaleString
    //   1153: dup
    //   1154: ldc 11
    //   1156: ldc_w 729
    //   1159: invokespecial 730	org/spongycastle/i18n/LocaleString:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   1162: aastore
    //   1163: aload 62
    //   1165: iconst_1
    //   1166: ldc_w 732
    //   1169: aastore
    //   1170: aload 62
    //   1172: iconst_2
    //   1173: aload 60
    //   1175: aastore
    //   1176: aload 62
    //   1178: iconst_3
    //   1179: ldc_w 734
    //   1182: aastore
    //   1183: aload 62
    //   1185: iconst_4
    //   1186: new 727	org/spongycastle/i18n/LocaleString
    //   1189: dup
    //   1190: ldc 11
    //   1192: ldc_w 736
    //   1195: invokespecial 730	org/spongycastle/i18n/LocaleString:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   1198: aastore
    //   1199: aload 62
    //   1201: iconst_5
    //   1202: ldc_w 738
    //   1205: aastore
    //   1206: aload 62
    //   1208: bipush 6
    //   1210: aload 61
    //   1212: aastore
    //   1213: aload 15
    //   1215: aload 62
    //   1217: invokevirtual 742	org/spongycastle/i18n/ErrorBundle:setExtraArguments	([Ljava/lang/Object;)V
    //   1220: aload_0
    //   1221: aload 15
    //   1223: iload 12
    //   1225: invokevirtual 167	org/spongycastle/x509/PKIXCertPathReviewer:addError	(Lorg/spongycastle/i18n/ErrorBundle;I)V
    //   1228: goto -866 -> 362
    //   1231: astore 54
    //   1233: iconst_1
    //   1234: anewarray 133	java/lang/Object
    //   1237: astore 55
    //   1239: aload 55
    //   1241: iconst_0
    //   1242: new 573	org/spongycastle/i18n/filter/TrustedInput
    //   1245: dup
    //   1246: aload 14
    //   1248: invokevirtual 746	java/security/cert/X509Certificate:getNotBefore	()Ljava/util/Date;
    //   1251: invokespecial 576	org/spongycastle/i18n/filter/TrustedInput:<init>	(Ljava/lang/Object;)V
    //   1254: aastore
    //   1255: new 147	org/spongycastle/i18n/ErrorBundle
    //   1258: dup
    //   1259: ldc 11
    //   1261: ldc_w 748
    //   1264: aload 55
    //   1266: invokespecial 152	org/spongycastle/i18n/ErrorBundle:<init>	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   1269: astore 56
    //   1271: aload_0
    //   1272: aload 56
    //   1274: iload 12
    //   1276: invokevirtual 167	org/spongycastle/x509/PKIXCertPathReviewer:addError	(Lorg/spongycastle/i18n/ErrorBundle;I)V
    //   1279: goto -908 -> 371
    //   1282: astore 17
    //   1284: iconst_1
    //   1285: anewarray 133	java/lang/Object
    //   1288: astore 18
    //   1290: aload 18
    //   1292: iconst_0
    //   1293: new 573	org/spongycastle/i18n/filter/TrustedInput
    //   1296: dup
    //   1297: aload 14
    //   1299: invokevirtual 751	java/security/cert/X509Certificate:getNotAfter	()Ljava/util/Date;
    //   1302: invokespecial 576	org/spongycastle/i18n/filter/TrustedInput:<init>	(Ljava/lang/Object;)V
    //   1305: aastore
    //   1306: new 147	org/spongycastle/i18n/ErrorBundle
    //   1309: dup
    //   1310: ldc 11
    //   1312: ldc_w 753
    //   1315: aload 18
    //   1317: invokespecial 152	org/spongycastle/i18n/ErrorBundle:<init>	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   1320: astore 19
    //   1322: aload_0
    //   1323: aload 19
    //   1325: iload 12
    //   1327: invokevirtual 167	org/spongycastle/x509/PKIXCertPathReviewer:addError	(Lorg/spongycastle/i18n/ErrorBundle;I)V
    //   1330: goto -959 -> 371
    //   1333: astore 35
    //   1335: new 147	org/spongycastle/i18n/ErrorBundle
    //   1338: dup
    //   1339: ldc 11
    //   1341: ldc_w 755
    //   1344: invokespecial 341	org/spongycastle/i18n/ErrorBundle:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   1347: astore 36
    //   1349: aload_0
    //   1350: aload 36
    //   1352: iload 12
    //   1354: invokevirtual 167	org/spongycastle/x509/PKIXCertPathReviewer:addError	(Lorg/spongycastle/i18n/ErrorBundle;I)V
    //   1357: aconst_null
    //   1358: astore 37
    //   1360: goto -950 -> 410
    //   1363: astore 38
    //   1365: new 147	org/spongycastle/i18n/ErrorBundle
    //   1368: dup
    //   1369: ldc 11
    //   1371: ldc_w 757
    //   1374: invokespecial 341	org/spongycastle/i18n/ErrorBundle:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   1377: astore 39
    //   1379: aload_0
    //   1380: aload 39
    //   1382: iload 12
    //   1384: invokevirtual 167	org/spongycastle/x509/PKIXCertPathReviewer:addError	(Lorg/spongycastle/i18n/ErrorBundle;I)V
    //   1387: aconst_null
    //   1388: astore 40
    //   1390: goto -951 -> 439
    //   1393: aload 42
    //   1395: invokevirtual 660	java/util/Vector:iterator	()Ljava/util/Iterator;
    //   1398: astore 44
    //   1400: aload 44
    //   1402: invokeinterface 122 1 0
    //   1407: ifeq +54 -> 1461
    //   1410: iconst_1
    //   1411: anewarray 133	java/lang/Object
    //   1414: astore 46
    //   1416: aload 46
    //   1418: iconst_0
    //   1419: new 662	org/spongycastle/i18n/filter/UntrustedUrlInput
    //   1422: dup
    //   1423: aload 44
    //   1425: invokeinterface 126 1 0
    //   1430: invokespecial 663	org/spongycastle/i18n/filter/UntrustedUrlInput:<init>	(Ljava/lang/Object;)V
    //   1433: aastore
    //   1434: new 147	org/spongycastle/i18n/ErrorBundle
    //   1437: dup
    //   1438: ldc 11
    //   1440: ldc_w 759
    //   1443: aload 46
    //   1445: invokespecial 152	org/spongycastle/i18n/ErrorBundle:<init>	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   1448: astore 47
    //   1450: aload_0
    //   1451: aload 47
    //   1453: iload 12
    //   1455: invokevirtual 667	org/spongycastle/x509/PKIXCertPathReviewer:addNotification	(Lorg/spongycastle/i18n/ErrorBundle;I)V
    //   1458: goto -58 -> 1400
    //   1461: aload_0
    //   1462: aload_0
    //   1463: getfield 104	org/spongycastle/x509/PKIXCertPathReviewer:pkixParams	Ljava/security/cert/PKIXParameters;
    //   1466: aload 14
    //   1468: aload_0
    //   1469: getfield 575	org/spongycastle/x509/PKIXCertPathReviewer:validDate	Ljava/util/Date;
    //   1472: aload 10
    //   1474: aload 11
    //   1476: aload 41
    //   1478: aload 42
    //   1480: iload 12
    //   1482: invokevirtual 763	org/spongycastle/x509/PKIXCertPathReviewer:checkRevocation	(Ljava/security/cert/PKIXParameters;Ljava/security/cert/X509Certificate;Ljava/util/Date;Ljava/security/cert/X509Certificate;Ljava/security/PublicKey;Ljava/util/Vector;Ljava/util/Vector;I)V
    //   1485: aload 9
    //   1487: ifnull +67 -> 1554
    //   1490: aload 14
    //   1492: invokevirtual 594	java/security/cert/X509Certificate:getIssuerX500Principal	()Ljavax/security/auth/x500/X500Principal;
    //   1495: aload 9
    //   1497: invokevirtual 764	javax/security/auth/x500/X500Principal:equals	(Ljava/lang/Object;)Z
    //   1500: ifne +54 -> 1554
    //   1503: iconst_2
    //   1504: anewarray 133	java/lang/Object
    //   1507: astore 33
    //   1509: aload 33
    //   1511: iconst_0
    //   1512: aload 9
    //   1514: invokevirtual 332	javax/security/auth/x500/X500Principal:getName	()Ljava/lang/String;
    //   1517: aastore
    //   1518: aload 33
    //   1520: iconst_1
    //   1521: aload 14
    //   1523: invokevirtual 594	java/security/cert/X509Certificate:getIssuerX500Principal	()Ljavax/security/auth/x500/X500Principal;
    //   1526: invokevirtual 332	javax/security/auth/x500/X500Principal:getName	()Ljava/lang/String;
    //   1529: aastore
    //   1530: new 147	org/spongycastle/i18n/ErrorBundle
    //   1533: dup
    //   1534: ldc 11
    //   1536: ldc_w 766
    //   1539: aload 33
    //   1541: invokespecial 152	org/spongycastle/i18n/ErrorBundle:<init>	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   1544: astore 34
    //   1546: aload_0
    //   1547: aload 34
    //   1549: iload 12
    //   1551: invokevirtual 167	org/spongycastle/x509/PKIXCertPathReviewer:addError	(Lorg/spongycastle/i18n/ErrorBundle;I)V
    //   1554: iload 13
    //   1556: aload_0
    //   1557: getfield 265	org/spongycastle/x509/PKIXCertPathReviewer:n	I
    //   1560: if_icmpeq +128 -> 1688
    //   1563: aload 14
    //   1565: ifnull +34 -> 1599
    //   1568: aload 14
    //   1570: invokevirtual 769	java/security/cert/X509Certificate:getVersion	()I
    //   1573: iconst_1
    //   1574: if_icmpne +25 -> 1599
    //   1577: new 147	org/spongycastle/i18n/ErrorBundle
    //   1580: dup
    //   1581: ldc 11
    //   1583: ldc_w 771
    //   1586: invokespecial 341	org/spongycastle/i18n/ErrorBundle:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   1589: astore 32
    //   1591: aload_0
    //   1592: aload 32
    //   1594: iload 12
    //   1596: invokevirtual 167	org/spongycastle/x509/PKIXCertPathReviewer:addError	(Lorg/spongycastle/i18n/ErrorBundle;I)V
    //   1599: aload 14
    //   1601: getstatic 215	org/spongycastle/x509/PKIXCertPathReviewer:BASIC_CONSTRAINTS	Ljava/lang/String;
    //   1604: invokestatic 306	org/spongycastle/x509/PKIXCertPathReviewer:getExtensionValue	(Ljava/security/cert/X509Extension;Ljava/lang/String;)Lorg/spongycastle/asn1/ASN1Primitive;
    //   1607: invokestatic 392	org/spongycastle/asn1/x509/BasicConstraints:getInstance	(Ljava/lang/Object;)Lorg/spongycastle/asn1/x509/BasicConstraints;
    //   1610: astore 29
    //   1612: aload 29
    //   1614: ifnull +137 -> 1751
    //   1617: aload 29
    //   1619: invokevirtual 774	org/spongycastle/asn1/x509/BasicConstraints:isCA	()Z
    //   1622: ifne +25 -> 1647
    //   1625: new 147	org/spongycastle/i18n/ErrorBundle
    //   1628: dup
    //   1629: ldc 11
    //   1631: ldc_w 771
    //   1634: invokespecial 341	org/spongycastle/i18n/ErrorBundle:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   1637: astore 30
    //   1639: aload_0
    //   1640: aload 30
    //   1642: iload 12
    //   1644: invokevirtual 167	org/spongycastle/x509/PKIXCertPathReviewer:addError	(Lorg/spongycastle/i18n/ErrorBundle;I)V
    //   1647: aload 14
    //   1649: invokevirtual 606	java/security/cert/X509Certificate:getKeyUsage	()[Z
    //   1652: astore 27
    //   1654: aload 27
    //   1656: ifnull +32 -> 1688
    //   1659: aload 27
    //   1661: iconst_5
    //   1662: baload
    //   1663: ifne +25 -> 1688
    //   1666: new 147	org/spongycastle/i18n/ErrorBundle
    //   1669: dup
    //   1670: ldc 11
    //   1672: ldc_w 776
    //   1675: invokespecial 341	org/spongycastle/i18n/ErrorBundle:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   1678: astore 28
    //   1680: aload_0
    //   1681: aload 28
    //   1683: iload 12
    //   1685: invokevirtual 167	org/spongycastle/x509/PKIXCertPathReviewer:addError	(Lorg/spongycastle/i18n/ErrorBundle;I)V
    //   1688: aload 14
    //   1690: astore 10
    //   1692: aload 14
    //   1694: invokevirtual 779	java/security/cert/X509Certificate:getSubjectX500Principal	()Ljavax/security/auth/x500/X500Principal;
    //   1697: astore 9
    //   1699: aload_0
    //   1700: getfield 169	org/spongycastle/x509/PKIXCertPathReviewer:certs	Ljava/util/List;
    //   1703: iload 12
    //   1705: invokestatic 783	org/spongycastle/x509/PKIXCertPathReviewer:getNextWorkingKey	(Ljava/util/List;I)Ljava/security/PublicKey;
    //   1708: astore 11
    //   1710: aload 11
    //   1712: invokestatic 616	org/spongycastle/x509/PKIXCertPathReviewer:getAlgorithmIdentifier	(Ljava/security/PublicKey;)Lorg/spongycastle/asn1/x509/AlgorithmIdentifier;
    //   1715: astore 22
    //   1717: aload 22
    //   1719: invokevirtual 621	org/spongycastle/asn1/x509/AlgorithmIdentifier:getObjectId	()Lorg/spongycastle/asn1/ASN1ObjectIdentifier;
    //   1722: pop
    //   1723: aload 22
    //   1725: invokevirtual 625	org/spongycastle/asn1/x509/AlgorithmIdentifier:getParameters	()Lorg/spongycastle/asn1/ASN1Encodable;
    //   1728: pop
    //   1729: iinc 12 255
    //   1732: goto -1419 -> 313
    //   1735: astore 45
    //   1737: aload_0
    //   1738: aload 45
    //   1740: invokevirtual 159	org/spongycastle/x509/CertPathReviewerException:getErrorMessage	()Lorg/spongycastle/i18n/ErrorBundle;
    //   1743: iload 12
    //   1745: invokevirtual 167	org/spongycastle/x509/PKIXCertPathReviewer:addError	(Lorg/spongycastle/i18n/ErrorBundle;I)V
    //   1748: goto -263 -> 1485
    //   1751: new 147	org/spongycastle/i18n/ErrorBundle
    //   1754: dup
    //   1755: ldc 11
    //   1757: ldc_w 785
    //   1760: invokespecial 341	org/spongycastle/i18n/ErrorBundle:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   1763: astore 31
    //   1765: aload_0
    //   1766: aload 31
    //   1768: iload 12
    //   1770: invokevirtual 167	org/spongycastle/x509/PKIXCertPathReviewer:addError	(Lorg/spongycastle/i18n/ErrorBundle;I)V
    //   1773: goto -126 -> 1647
    //   1776: astore 25
    //   1778: new 147	org/spongycastle/i18n/ErrorBundle
    //   1781: dup
    //   1782: ldc 11
    //   1784: ldc_w 787
    //   1787: invokespecial 341	org/spongycastle/i18n/ErrorBundle:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   1790: astore 26
    //   1792: aload_0
    //   1793: aload 26
    //   1795: iload 12
    //   1797: invokevirtual 167	org/spongycastle/x509/PKIXCertPathReviewer:addError	(Lorg/spongycastle/i18n/ErrorBundle;I)V
    //   1800: goto -153 -> 1647
    //   1803: astore 20
    //   1805: new 147	org/spongycastle/i18n/ErrorBundle
    //   1808: dup
    //   1809: ldc 11
    //   1811: ldc_w 789
    //   1814: invokespecial 341	org/spongycastle/i18n/ErrorBundle:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   1817: astore 21
    //   1819: aload_0
    //   1820: aload 21
    //   1822: iload 12
    //   1824: invokevirtual 167	org/spongycastle/x509/PKIXCertPathReviewer:addError	(Lorg/spongycastle/i18n/ErrorBundle;I)V
    //   1827: goto -98 -> 1729
    //   1830: aload_0
    //   1831: aload_1
    //   1832: putfield 791	org/spongycastle/x509/PKIXCertPathReviewer:trustAnchor	Ljava/security/cert/TrustAnchor;
    //   1835: aload_0
    //   1836: aload 11
    //   1838: putfield 793	org/spongycastle/x509/PKIXCertPathReviewer:subjectPublicKey	Ljava/security/PublicKey;
    //   1841: return
    //   1842: astore 57
    //   1844: goto -624 -> 1220
    //   1847: astore 94
    //   1849: goto -1673 -> 176
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	1852	0	this	PKIXCertPathReviewer
    //   1	1831	1	localTrustAnchor	TrustAnchor
    //   6	42	2	arrayOfObject1	Object[]
    //   51	3	3	localErrorBundle1	ErrorBundle
    //   707	29	4	localThrowable	java.lang.Throwable
    //   713	45	5	arrayOfObject2	Object[]
    //   740	6	6	localUntrustedInput	UntrustedInput
    //   762	4	7	localErrorBundle2	ErrorBundle
    //   177	676	8	localObject1	Object
    //   251	1447	9	localObject2	Object
    //   254	1437	10	localObject3	Object
    //   257	1580	11	localPublicKey1	PublicKey
    //   311	1512	12	i	int
    //   325	1236	13	j	int
    //   341	1352	14	localX509Certificate1	X509Certificate
    //   1077	145	15	localErrorBundle3	ErrorBundle
    //   1090	8	16	arrayOfByte	byte[]
    //   1282	1	17	localCertificateExpiredException	java.security.cert.CertificateExpiredException
    //   1288	28	18	arrayOfObject3	Object[]
    //   1320	4	19	localErrorBundle4	ErrorBundle
    //   1803	1	20	localCertPathValidatorException1	CertPathValidatorException
    //   1817	4	21	localErrorBundle5	ErrorBundle
    //   1715	9	22	localAlgorithmIdentifier1	org.spongycastle.asn1.x509.AlgorithmIdentifier
    //   1776	1	25	localAnnotatedException1	AnnotatedException
    //   1790	4	26	localErrorBundle6	ErrorBundle
    //   1652	8	27	arrayOfBoolean1	boolean[]
    //   1678	4	28	localErrorBundle7	ErrorBundle
    //   1610	8	29	localBasicConstraints	BasicConstraints
    //   1637	4	30	localErrorBundle8	ErrorBundle
    //   1763	4	31	localErrorBundle9	ErrorBundle
    //   1589	4	32	localErrorBundle10	ErrorBundle
    //   1507	33	33	arrayOfObject4	Object[]
    //   1544	4	34	localErrorBundle11	ErrorBundle
    //   1333	1	35	localAnnotatedException2	AnnotatedException
    //   1347	4	36	localErrorBundle12	ErrorBundle
    //   392	967	37	localObject4	Object
    //   1363	1	38	localAnnotatedException3	AnnotatedException
    //   1377	4	39	localErrorBundle13	ErrorBundle
    //   421	968	40	localObject5	Object
    //   445	1032	41	localVector1	Vector
    //   453	1026	42	localVector2	Vector
    //   460	26	43	localIterator1	Iterator
    //   1398	26	44	localIterator2	Iterator
    //   1735	4	45	localCertPathReviewerException1	CertPathReviewerException
    //   1414	30	46	arrayOfObject5	Object[]
    //   1448	4	47	localErrorBundle14	ErrorBundle
    //   476	30	48	arrayOfObject6	Object[]
    //   510	4	49	localErrorBundle15	ErrorBundle
    //   418	11	50	localASN1Primitive1	ASN1Primitive
    //   433	3	51	localAuthorityInformationAccess	AuthorityInformationAccess
    //   389	11	52	localASN1Primitive2	ASN1Primitive
    //   404	3	53	localCRLDistPoint	CRLDistPoint
    //   1231	1	54	localCertificateNotYetValidException	java.security.cert.CertificateNotYetValidException
    //   1237	28	55	arrayOfObject7	Object[]
    //   1269	4	56	localErrorBundle16	ErrorBundle
    //   1842	1	57	localIOException	IOException
    //   1105	24	58	localAuthorityKeyIdentifier	AuthorityKeyIdentifier
    //   1112	8	59	localGeneralNames	GeneralNames
    //   1126	48	60	localGeneralName	GeneralName
    //   1133	78	61	localBigInteger	BigInteger
    //   1145	71	62	arrayOfObject8	Object[]
    //   1003	27	63	localGeneralSecurityException1	java.security.GeneralSecurityException
    //   1009	39	64	arrayOfObject9	Object[]
    //   1052	4	65	localErrorBundle17	ErrorBundle
    //   990	4	66	localErrorBundle18	ErrorBundle
    //   891	27	67	localGeneralSecurityException2	java.security.GeneralSecurityException
    //   897	39	68	arrayOfObject10	Object[]
    //   940	4	69	localErrorBundle19	ErrorBundle
    //   866	1	70	localCertPathValidatorException2	CertPathValidatorException
    //   880	4	71	localErrorBundle20	ErrorBundle
    //   286	9	72	localAlgorithmIdentifier2	org.spongycastle.asn1.x509.AlgorithmIdentifier
    //   187	24	75	localX509Certificate2	X509Certificate
    //   797	3	76	localX500Principal1	X500Principal
    //   215	8	77	arrayOfBoolean2	boolean[]
    //   241	4	78	localErrorBundle21	ErrorBundle
    //   806	1	79	localIllegalArgumentException	java.lang.IllegalArgumentException
    //   812	27	80	arrayOfObject11	Object[]
    //   843	4	81	localErrorBundle22	ErrorBundle
    //   199	3	82	localX500Principal2	X500Principal
    //   611	4	83	localCertPathReviewerException2	CertPathReviewerException
    //   80	591	84	localX509Certificate3	X509Certificate
    //   95	531	85	localCollection	Collection
    //   104	8	86	k	int
    //   118	46	87	arrayOfObject12	Object[]
    //   168	4	88	localErrorBundle23	ErrorBundle
    //   530	5	89	bool	boolean
    //   543	51	90	arrayOfObject13	Object[]
    //   598	4	91	localErrorBundle24	ErrorBundle
    //   777	3	92	localPublicKey2	PublicKey
    //   659	123	93	localObject6	Object
    //   1847	1	94	localException	Exception
    //   682	1	95	localSignatureException	java.security.SignatureException
    //   696	4	96	localErrorBundle25	ErrorBundle
    //   668	7	97	str	String
    //   655	3	98	localPublicKey3	PublicKey
    // Exception table:
    //   from	to	target	type
    //   57	106	611	org/spongycastle/x509/CertPathReviewerException
    //   114	176	611	org/spongycastle/x509/CertPathReviewerException
    //   523	532	611	org/spongycastle/x509/CertPathReviewerException
    //   539	606	611	org/spongycastle/x509/CertPathReviewerException
    //   625	657	611	org/spongycastle/x509/CertPathReviewerException
    //   661	679	611	org/spongycastle/x509/CertPathReviewerException
    //   684	704	611	org/spongycastle/x509/CertPathReviewerException
    //   773	779	611	org/spongycastle/x509/CertPathReviewerException
    //   661	679	682	java/security/SignatureException
    //   57	106	707	java/lang/Throwable
    //   114	176	707	java/lang/Throwable
    //   523	532	707	java/lang/Throwable
    //   539	606	707	java/lang/Throwable
    //   625	657	707	java/lang/Throwable
    //   661	679	707	java/lang/Throwable
    //   684	704	707	java/lang/Throwable
    //   773	779	707	java/lang/Throwable
    //   194	201	806	java/lang/IllegalArgumentException
    //   786	799	806	java/lang/IllegalArgumentException
    //   281	300	866	java/security/cert/CertPathValidatorException
    //   348	362	891	java/security/GeneralSecurityException
    //   961	1000	1003	java/security/GeneralSecurityException
    //   362	371	1231	java/security/cert/CertificateNotYetValidException
    //   362	371	1282	java/security/cert/CertificateExpiredException
    //   381	391	1333	org/spongycastle/jce/provider/AnnotatedException
    //   399	406	1333	org/spongycastle/jce/provider/AnnotatedException
    //   410	420	1363	org/spongycastle/jce/provider/AnnotatedException
    //   428	435	1363	org/spongycastle/jce/provider/AnnotatedException
    //   1461	1485	1735	org/spongycastle/x509/CertPathReviewerException
    //   1599	1612	1776	org/spongycastle/jce/provider/AnnotatedException
    //   1617	1647	1776	org/spongycastle/jce/provider/AnnotatedException
    //   1751	1773	1776	org/spongycastle/jce/provider/AnnotatedException
    //   1699	1729	1803	java/security/cert/CertPathValidatorException
    //   1097	1114	1842	java/io/IOException
    //   1119	1135	1842	java/io/IOException
    //   1140	1220	1842	java/io/IOException
    //   661	679	1847	java/lang/Exception
  }
  
  private X509CRL getCRL(String paramString)
    throws CertPathReviewerException
  {
    try
    {
      URL localURL = new URL(paramString);
      if ((localURL.getProtocol().equals("http")) || (localURL.getProtocol().equals("https")))
      {
        HttpURLConnection localHttpURLConnection = (HttpURLConnection)localURL.openConnection();
        localHttpURLConnection.setUseCaches(false);
        localHttpURLConnection.setDoInput(true);
        localHttpURLConnection.connect();
        if (localHttpURLConnection.getResponseCode() == 200) {
          return (X509CRL)CertificateFactory.getInstance("X.509", "SC").generateCRL(localHttpURLConnection.getInputStream());
        }
        throw new Exception(localHttpURLConnection.getResponseMessage());
      }
    }
    catch (Exception localException)
    {
      Object[] arrayOfObject = new Object[4];
      arrayOfObject[0] = new UntrustedInput(paramString);
      arrayOfObject[1] = localException.getMessage();
      arrayOfObject[2] = localException;
      arrayOfObject[3] = localException.getClass().getName();
      throw new CertPathReviewerException(new ErrorBundle("org.spongycastle.x509.CertPathReviewerMessages", "CertPathReviewer.loadCrlDistPointError", arrayOfObject));
    }
    return null;
  }
  
  private boolean processQcStatements(X509Certificate paramX509Certificate, int paramInt)
  {
    int i = 0;
    for (;;)
    {
      int j;
      QCStatement localQCStatement;
      try
      {
        ASN1Sequence localASN1Sequence = (ASN1Sequence)getExtensionValue(paramX509Certificate, QC_STATEMENT);
        j = 0;
        if (j >= localASN1Sequence.size()) {
          break label400;
        }
        localQCStatement = QCStatement.getInstance(localASN1Sequence.getObjectAt(j));
        if (QCStatement.id_etsi_qcs_QcCompliance.equals(localQCStatement.getStatementId())) {
          addNotification(new ErrorBundle("org.spongycastle.x509.CertPathReviewerMessages", "CertPathReviewer.QcEuCompliance"), paramInt);
        } else if (!QCStatement.id_qcs_pkixQCSyntax_v1.equals(localQCStatement.getStatementId())) {
          if (QCStatement.id_etsi_qcs_QcSSCD.equals(localQCStatement.getStatementId())) {
            addNotification(new ErrorBundle("org.spongycastle.x509.CertPathReviewerMessages", "CertPathReviewer.QcSSCD"), paramInt);
          }
        }
      }
      catch (AnnotatedException localAnnotatedException)
      {
        addError(new ErrorBundle("org.spongycastle.x509.CertPathReviewerMessages", "CertPathReviewer.QcStatementExtError"), paramInt);
        return false;
      }
      if (QCStatement.id_etsi_qcs_LimiteValue.equals(localQCStatement.getStatementId()))
      {
        MonetaryValue localMonetaryValue = MonetaryValue.getInstance(localQCStatement.getStatementInfo());
        localMonetaryValue.getCurrency();
        double d = localMonetaryValue.getAmount().doubleValue() * Math.pow(10.0D, localMonetaryValue.getExponent().doubleValue());
        Object[] arrayOfObject3;
        if (localMonetaryValue.getCurrency().isAlphabetic())
        {
          arrayOfObject3 = new Object[3];
          arrayOfObject3[0] = localMonetaryValue.getCurrency().getAlphabetic();
          arrayOfObject3[1] = new TrustedInput(new Double(d));
          arrayOfObject3[2] = localMonetaryValue;
        }
        Object[] arrayOfObject2;
        for (ErrorBundle localErrorBundle = new ErrorBundle("org.spongycastle.x509.CertPathReviewerMessages", "CertPathReviewer.QcLimitValueAlpha", arrayOfObject3);; localErrorBundle = new ErrorBundle("org.spongycastle.x509.CertPathReviewerMessages", "CertPathReviewer.QcLimitValueNum", arrayOfObject2))
        {
          addNotification(localErrorBundle, paramInt);
          break;
          arrayOfObject2 = new Object[3];
          arrayOfObject2[0] = new Integer(localMonetaryValue.getCurrency().getNumeric());
          arrayOfObject2[1] = new TrustedInput(new Double(d));
          arrayOfObject2[2] = localMonetaryValue;
        }
      }
      Object[] arrayOfObject1 = new Object[2];
      arrayOfObject1[0] = localQCStatement.getStatementId();
      arrayOfObject1[1] = new UntrustedInput(localQCStatement);
      addNotification(new ErrorBundle("org.spongycastle.x509.CertPathReviewerMessages", "CertPathReviewer.QcUnknownStatement", arrayOfObject1), paramInt);
      i = 1;
      break label408;
      label400:
      return i == 0;
      label408:
      j++;
    }
  }
  
  protected void addError(ErrorBundle paramErrorBundle)
  {
    this.errors[0].add(paramErrorBundle);
  }
  
  protected void addError(ErrorBundle paramErrorBundle, int paramInt)
  {
    if ((paramInt < -1) || (paramInt >= this.n)) {
      throw new IndexOutOfBoundsException();
    }
    this.errors[(paramInt + 1)].add(paramErrorBundle);
  }
  
  protected void addNotification(ErrorBundle paramErrorBundle)
  {
    this.notifications[0].add(paramErrorBundle);
  }
  
  protected void addNotification(ErrorBundle paramErrorBundle, int paramInt)
  {
    if ((paramInt < -1) || (paramInt >= this.n)) {
      throw new IndexOutOfBoundsException();
    }
    this.notifications[(paramInt + 1)].add(paramErrorBundle);
  }
  
  /* Error */
  protected void checkCRLs(PKIXParameters paramPKIXParameters, X509Certificate paramX509Certificate1, Date paramDate, X509Certificate paramX509Certificate2, PublicKey paramPublicKey, Vector paramVector, int paramInt)
    throws CertPathReviewerException
  {
    // Byte code:
    //   0: new 939	org/spongycastle/x509/X509CRLStoreSelector
    //   3: dup
    //   4: invokespecial 940	org/spongycastle/x509/X509CRLStoreSelector:<init>	()V
    //   7: astore 8
    //   9: aload 8
    //   11: aload_2
    //   12: invokestatic 944	org/spongycastle/x509/PKIXCertPathReviewer:getEncodedIssuerPrincipal	(Ljava/lang/Object;)Ljavax/security/auth/x500/X500Principal;
    //   15: invokevirtual 283	javax/security/auth/x500/X500Principal:getEncoded	()[B
    //   18: invokevirtual 947	org/spongycastle/x509/X509CRLStoreSelector:addIssuerName	([B)V
    //   21: aload 8
    //   23: aload_2
    //   24: invokevirtual 951	org/spongycastle/x509/X509CRLStoreSelector:setCertificateChecking	(Ljava/security/cert/X509Certificate;)V
    //   27: getstatic 955	org/spongycastle/x509/PKIXCertPathReviewer:CRL_UTIL	Lorg/spongycastle/jce/provider/PKIXCRLUtil;
    //   30: aload 8
    //   32: aload_1
    //   33: invokevirtual 961	org/spongycastle/jce/provider/PKIXCRLUtil:findCRLs	(Lorg/spongycastle/x509/X509CRLStoreSelector;Ljava/security/cert/PKIXParameters;)Ljava/util/Set;
    //   36: astore 101
    //   38: aload 101
    //   40: invokeinterface 672 1 0
    //   45: astore 15
    //   47: aload 101
    //   49: invokeinterface 668 1 0
    //   54: ifeq +147 -> 201
    //   57: getstatic 955	org/spongycastle/x509/PKIXCertPathReviewer:CRL_UTIL	Lorg/spongycastle/jce/provider/PKIXCRLUtil;
    //   60: new 939	org/spongycastle/x509/X509CRLStoreSelector
    //   63: dup
    //   64: invokespecial 940	org/spongycastle/x509/X509CRLStoreSelector:<init>	()V
    //   67: aload_1
    //   68: invokevirtual 961	org/spongycastle/jce/provider/PKIXCRLUtil:findCRLs	(Lorg/spongycastle/x509/X509CRLStoreSelector;Ljava/security/cert/PKIXParameters;)Ljava/util/Set;
    //   71: invokeinterface 672 1 0
    //   76: astore 102
    //   78: new 417	java/util/ArrayList
    //   81: dup
    //   82: invokespecial 418	java/util/ArrayList:<init>	()V
    //   85: astore 103
    //   87: aload 102
    //   89: invokeinterface 122 1 0
    //   94: ifeq +424 -> 518
    //   97: aload 103
    //   99: aload 102
    //   101: invokeinterface 126 1 0
    //   106: checkcast 842	java/security/cert/X509CRL
    //   109: invokevirtual 962	java/security/cert/X509CRL:getIssuerX500Principal	()Ljavax/security/auth/x500/X500Principal;
    //   112: invokeinterface 432 2 0
    //   117: pop
    //   118: goto -31 -> 87
    //   121: astore 12
    //   123: iconst_3
    //   124: anewarray 133	java/lang/Object
    //   127: astore 13
    //   129: aload 13
    //   131: iconst_0
    //   132: aload 12
    //   134: invokevirtual 963	org/spongycastle/jce/provider/AnnotatedException:getCause	()Ljava/lang/Throwable;
    //   137: invokevirtual 675	java/lang/Throwable:getMessage	()Ljava/lang/String;
    //   140: aastore
    //   141: aload 13
    //   143: iconst_1
    //   144: aload 12
    //   146: invokevirtual 963	org/spongycastle/jce/provider/AnnotatedException:getCause	()Ljava/lang/Throwable;
    //   149: aastore
    //   150: aload 13
    //   152: iconst_2
    //   153: aload 12
    //   155: invokevirtual 963	org/spongycastle/jce/provider/AnnotatedException:getCause	()Ljava/lang/Throwable;
    //   158: invokevirtual 140	java/lang/Object:getClass	()Ljava/lang/Class;
    //   161: invokevirtual 145	java/lang/Class:getName	()Ljava/lang/String;
    //   164: aastore
    //   165: new 147	org/spongycastle/i18n/ErrorBundle
    //   168: dup
    //   169: ldc 11
    //   171: ldc_w 965
    //   174: aload 13
    //   176: invokespecial 152	org/spongycastle/i18n/ErrorBundle:<init>	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   179: astore 14
    //   181: aload_0
    //   182: aload 14
    //   184: iload 7
    //   186: invokevirtual 167	org/spongycastle/x509/PKIXCertPathReviewer:addError	(Lorg/spongycastle/i18n/ErrorBundle;I)V
    //   189: new 417	java/util/ArrayList
    //   192: dup
    //   193: invokespecial 418	java/util/ArrayList:<init>	()V
    //   196: invokevirtual 966	java/util/ArrayList:iterator	()Ljava/util/Iterator;
    //   199: astore 15
    //   201: aconst_null
    //   202: astore 16
    //   204: aload 15
    //   206: invokeinterface 122 1 0
    //   211: istore 17
    //   213: iconst_0
    //   214: istore 18
    //   216: iload 17
    //   218: ifeq +103 -> 321
    //   221: aload 15
    //   223: invokeinterface 126 1 0
    //   228: checkcast 842	java/security/cert/X509CRL
    //   231: astore 16
    //   233: aload 16
    //   235: invokevirtual 969	java/security/cert/X509CRL:getNextUpdate	()Ljava/util/Date;
    //   238: ifnull +18 -> 256
    //   241: aload_1
    //   242: invokevirtual 972	java/security/cert/PKIXParameters:getDate	()Ljava/util/Date;
    //   245: aload 16
    //   247: invokevirtual 969	java/security/cert/X509CRL:getNextUpdate	()Ljava/util/Date;
    //   250: invokevirtual 976	java/util/Date:before	(Ljava/util/Date;)Z
    //   253: ifeq +357 -> 610
    //   256: iconst_1
    //   257: istore 18
    //   259: iconst_2
    //   260: anewarray 133	java/lang/Object
    //   263: astore 97
    //   265: aload 97
    //   267: iconst_0
    //   268: new 573	org/spongycastle/i18n/filter/TrustedInput
    //   271: dup
    //   272: aload 16
    //   274: invokevirtual 979	java/security/cert/X509CRL:getThisUpdate	()Ljava/util/Date;
    //   277: invokespecial 576	org/spongycastle/i18n/filter/TrustedInput:<init>	(Ljava/lang/Object;)V
    //   280: aastore
    //   281: aload 97
    //   283: iconst_1
    //   284: new 573	org/spongycastle/i18n/filter/TrustedInput
    //   287: dup
    //   288: aload 16
    //   290: invokevirtual 969	java/security/cert/X509CRL:getNextUpdate	()Ljava/util/Date;
    //   293: invokespecial 576	org/spongycastle/i18n/filter/TrustedInput:<init>	(Ljava/lang/Object;)V
    //   296: aastore
    //   297: new 147	org/spongycastle/i18n/ErrorBundle
    //   300: dup
    //   301: ldc 11
    //   303: ldc_w 981
    //   306: aload 97
    //   308: invokespecial 152	org/spongycastle/i18n/ErrorBundle:<init>	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   311: astore 98
    //   313: aload_0
    //   314: aload 98
    //   316: iload 7
    //   318: invokevirtual 667	org/spongycastle/x509/PKIXCertPathReviewer:addNotification	(Lorg/spongycastle/i18n/ErrorBundle;I)V
    //   321: iload 18
    //   323: ifne +464 -> 787
    //   326: aload 6
    //   328: invokevirtual 660	java/util/Vector:iterator	()Ljava/util/Iterator;
    //   331: astore 84
    //   333: aload 84
    //   335: invokeinterface 122 1 0
    //   340: ifeq +447 -> 787
    //   343: aload 84
    //   345: invokeinterface 126 1 0
    //   350: checkcast 248	java/lang/String
    //   353: astore 86
    //   355: aload_0
    //   356: aload 86
    //   358: invokespecial 983	org/spongycastle/x509/PKIXCertPathReviewer:getCRL	(Ljava/lang/String;)Ljava/security/cert/X509CRL;
    //   361: astore 87
    //   363: aload 87
    //   365: ifnull -32 -> 333
    //   368: aload_2
    //   369: invokevirtual 594	java/security/cert/X509Certificate:getIssuerX500Principal	()Ljavax/security/auth/x500/X500Principal;
    //   372: aload 87
    //   374: invokevirtual 962	java/security/cert/X509CRL:getIssuerX500Principal	()Ljavax/security/auth/x500/X500Principal;
    //   377: invokevirtual 764	javax/security/auth/x500/X500Principal:equals	(Ljava/lang/Object;)Z
    //   380: ifne +295 -> 675
    //   383: iconst_3
    //   384: anewarray 133	java/lang/Object
    //   387: astore 94
    //   389: aload 94
    //   391: iconst_0
    //   392: new 326	org/spongycastle/i18n/filter/UntrustedInput
    //   395: dup
    //   396: aload 87
    //   398: invokevirtual 962	java/security/cert/X509CRL:getIssuerX500Principal	()Ljavax/security/auth/x500/X500Principal;
    //   401: invokevirtual 332	javax/security/auth/x500/X500Principal:getName	()Ljava/lang/String;
    //   404: invokespecial 329	org/spongycastle/i18n/filter/UntrustedInput:<init>	(Ljava/lang/Object;)V
    //   407: aastore
    //   408: aload 94
    //   410: iconst_1
    //   411: new 326	org/spongycastle/i18n/filter/UntrustedInput
    //   414: dup
    //   415: aload_2
    //   416: invokevirtual 594	java/security/cert/X509Certificate:getIssuerX500Principal	()Ljavax/security/auth/x500/X500Principal;
    //   419: invokevirtual 332	javax/security/auth/x500/X500Principal:getName	()Ljava/lang/String;
    //   422: invokespecial 329	org/spongycastle/i18n/filter/UntrustedInput:<init>	(Ljava/lang/Object;)V
    //   425: aastore
    //   426: new 662	org/spongycastle/i18n/filter/UntrustedUrlInput
    //   429: dup
    //   430: aload 86
    //   432: invokespecial 663	org/spongycastle/i18n/filter/UntrustedUrlInput:<init>	(Ljava/lang/Object;)V
    //   435: astore 95
    //   437: aload 94
    //   439: iconst_2
    //   440: aload 95
    //   442: aastore
    //   443: new 147	org/spongycastle/i18n/ErrorBundle
    //   446: dup
    //   447: ldc 11
    //   449: ldc_w 985
    //   452: aload 94
    //   454: invokespecial 152	org/spongycastle/i18n/ErrorBundle:<init>	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   457: astore 96
    //   459: aload_0
    //   460: aload 96
    //   462: iload 7
    //   464: invokevirtual 667	org/spongycastle/x509/PKIXCertPathReviewer:addNotification	(Lorg/spongycastle/i18n/ErrorBundle;I)V
    //   467: goto -134 -> 333
    //   470: astore 85
    //   472: aload_0
    //   473: aload 85
    //   475: invokevirtual 159	org/spongycastle/x509/CertPathReviewerException:getErrorMessage	()Lorg/spongycastle/i18n/ErrorBundle;
    //   478: iload 7
    //   480: invokevirtual 667	org/spongycastle/x509/PKIXCertPathReviewer:addNotification	(Lorg/spongycastle/i18n/ErrorBundle;I)V
    //   483: goto -150 -> 333
    //   486: astore 9
    //   488: new 147	org/spongycastle/i18n/ErrorBundle
    //   491: dup
    //   492: ldc 11
    //   494: ldc_w 987
    //   497: invokespecial 341	org/spongycastle/i18n/ErrorBundle:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   500: astore 10
    //   502: new 64	org/spongycastle/x509/CertPathReviewerException
    //   505: dup
    //   506: aload 10
    //   508: aload 9
    //   510: invokespecial 155	org/spongycastle/x509/CertPathReviewerException:<init>	(Lorg/spongycastle/i18n/ErrorBundle;Ljava/lang/Throwable;)V
    //   513: astore 11
    //   515: aload 11
    //   517: athrow
    //   518: aload 103
    //   520: invokeinterface 172 1 0
    //   525: istore 104
    //   527: iconst_3
    //   528: anewarray 133	java/lang/Object
    //   531: astore 105
    //   533: aload 105
    //   535: iconst_0
    //   536: new 326	org/spongycastle/i18n/filter/UntrustedInput
    //   539: dup
    //   540: aload 8
    //   542: invokevirtual 991	org/spongycastle/x509/X509CRLStoreSelector:getIssuerNames	()Ljava/util/Collection;
    //   545: invokespecial 329	org/spongycastle/i18n/filter/UntrustedInput:<init>	(Ljava/lang/Object;)V
    //   548: aastore
    //   549: new 326	org/spongycastle/i18n/filter/UntrustedInput
    //   552: dup
    //   553: aload 103
    //   555: invokespecial 329	org/spongycastle/i18n/filter/UntrustedInput:<init>	(Ljava/lang/Object;)V
    //   558: astore 106
    //   560: aload 105
    //   562: iconst_1
    //   563: aload 106
    //   565: aastore
    //   566: new 85	java/lang/Integer
    //   569: dup
    //   570: iload 104
    //   572: invokespecial 406	java/lang/Integer:<init>	(I)V
    //   575: astore 107
    //   577: aload 105
    //   579: iconst_2
    //   580: aload 107
    //   582: aastore
    //   583: new 147	org/spongycastle/i18n/ErrorBundle
    //   586: dup
    //   587: ldc 11
    //   589: ldc_w 993
    //   592: aload 105
    //   594: invokespecial 152	org/spongycastle/i18n/ErrorBundle:<init>	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   597: astore 108
    //   599: aload_0
    //   600: aload 108
    //   602: iload 7
    //   604: invokevirtual 667	org/spongycastle/x509/PKIXCertPathReviewer:addNotification	(Lorg/spongycastle/i18n/ErrorBundle;I)V
    //   607: goto -406 -> 201
    //   610: iconst_2
    //   611: anewarray 133	java/lang/Object
    //   614: astore 99
    //   616: aload 99
    //   618: iconst_0
    //   619: new 573	org/spongycastle/i18n/filter/TrustedInput
    //   622: dup
    //   623: aload 16
    //   625: invokevirtual 979	java/security/cert/X509CRL:getThisUpdate	()Ljava/util/Date;
    //   628: invokespecial 576	org/spongycastle/i18n/filter/TrustedInput:<init>	(Ljava/lang/Object;)V
    //   631: aastore
    //   632: aload 99
    //   634: iconst_1
    //   635: new 573	org/spongycastle/i18n/filter/TrustedInput
    //   638: dup
    //   639: aload 16
    //   641: invokevirtual 969	java/security/cert/X509CRL:getNextUpdate	()Ljava/util/Date;
    //   644: invokespecial 576	org/spongycastle/i18n/filter/TrustedInput:<init>	(Ljava/lang/Object;)V
    //   647: aastore
    //   648: new 147	org/spongycastle/i18n/ErrorBundle
    //   651: dup
    //   652: ldc 11
    //   654: ldc_w 995
    //   657: aload 99
    //   659: invokespecial 152	org/spongycastle/i18n/ErrorBundle:<init>	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   662: astore 100
    //   664: aload_0
    //   665: aload 100
    //   667: iload 7
    //   669: invokevirtual 667	org/spongycastle/x509/PKIXCertPathReviewer:addNotification	(Lorg/spongycastle/i18n/ErrorBundle;I)V
    //   672: goto -468 -> 204
    //   675: aload 87
    //   677: invokevirtual 969	java/security/cert/X509CRL:getNextUpdate	()Ljava/util/Date;
    //   680: ifnull +21 -> 701
    //   683: aload_0
    //   684: getfield 104	org/spongycastle/x509/PKIXCertPathReviewer:pkixParams	Ljava/security/cert/PKIXParameters;
    //   687: invokevirtual 972	java/security/cert/PKIXParameters:getDate	()Ljava/util/Date;
    //   690: aload 87
    //   692: invokevirtual 969	java/security/cert/X509CRL:getNextUpdate	()Ljava/util/Date;
    //   695: invokevirtual 976	java/util/Date:before	(Ljava/util/Date;)Z
    //   698: ifeq +155 -> 853
    //   701: iconst_1
    //   702: istore 18
    //   704: iconst_3
    //   705: anewarray 133	java/lang/Object
    //   708: astore 88
    //   710: aload 88
    //   712: iconst_0
    //   713: new 573	org/spongycastle/i18n/filter/TrustedInput
    //   716: dup
    //   717: aload 87
    //   719: invokevirtual 979	java/security/cert/X509CRL:getThisUpdate	()Ljava/util/Date;
    //   722: invokespecial 576	org/spongycastle/i18n/filter/TrustedInput:<init>	(Ljava/lang/Object;)V
    //   725: aastore
    //   726: aload 88
    //   728: iconst_1
    //   729: new 573	org/spongycastle/i18n/filter/TrustedInput
    //   732: dup
    //   733: aload 87
    //   735: invokevirtual 969	java/security/cert/X509CRL:getNextUpdate	()Ljava/util/Date;
    //   738: invokespecial 576	org/spongycastle/i18n/filter/TrustedInput:<init>	(Ljava/lang/Object;)V
    //   741: aastore
    //   742: new 662	org/spongycastle/i18n/filter/UntrustedUrlInput
    //   745: dup
    //   746: aload 86
    //   748: invokespecial 663	org/spongycastle/i18n/filter/UntrustedUrlInput:<init>	(Ljava/lang/Object;)V
    //   751: astore 89
    //   753: aload 88
    //   755: iconst_2
    //   756: aload 89
    //   758: aastore
    //   759: new 147	org/spongycastle/i18n/ErrorBundle
    //   762: dup
    //   763: ldc 11
    //   765: ldc_w 997
    //   768: aload 88
    //   770: invokespecial 152	org/spongycastle/i18n/ErrorBundle:<init>	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   773: astore 90
    //   775: aload_0
    //   776: aload 90
    //   778: iload 7
    //   780: invokevirtual 667	org/spongycastle/x509/PKIXCertPathReviewer:addNotification	(Lorg/spongycastle/i18n/ErrorBundle;I)V
    //   783: aload 87
    //   785: astore 16
    //   787: aload 16
    //   789: ifnull +1148 -> 1937
    //   792: aload 4
    //   794: ifnull +141 -> 935
    //   797: aload 4
    //   799: invokevirtual 606	java/security/cert/X509Certificate:getKeyUsage	()[Z
    //   802: astore 81
    //   804: aload 81
    //   806: ifnull +129 -> 935
    //   809: aload 81
    //   811: arraylength
    //   812: bipush 7
    //   814: if_icmplt +11 -> 825
    //   817: aload 81
    //   819: bipush 6
    //   821: baload
    //   822: ifne +113 -> 935
    //   825: new 147	org/spongycastle/i18n/ErrorBundle
    //   828: dup
    //   829: ldc 11
    //   831: ldc_w 999
    //   834: invokespecial 341	org/spongycastle/i18n/ErrorBundle:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   837: astore 82
    //   839: new 64	org/spongycastle/x509/CertPathReviewerException
    //   842: dup
    //   843: aload 82
    //   845: invokespecial 500	org/spongycastle/x509/CertPathReviewerException:<init>	(Lorg/spongycastle/i18n/ErrorBundle;)V
    //   848: astore 83
    //   850: aload 83
    //   852: athrow
    //   853: iconst_3
    //   854: anewarray 133	java/lang/Object
    //   857: astore 91
    //   859: aload 91
    //   861: iconst_0
    //   862: new 573	org/spongycastle/i18n/filter/TrustedInput
    //   865: dup
    //   866: aload 87
    //   868: invokevirtual 979	java/security/cert/X509CRL:getThisUpdate	()Ljava/util/Date;
    //   871: invokespecial 576	org/spongycastle/i18n/filter/TrustedInput:<init>	(Ljava/lang/Object;)V
    //   874: aastore
    //   875: aload 91
    //   877: iconst_1
    //   878: new 573	org/spongycastle/i18n/filter/TrustedInput
    //   881: dup
    //   882: aload 87
    //   884: invokevirtual 969	java/security/cert/X509CRL:getNextUpdate	()Ljava/util/Date;
    //   887: invokespecial 576	org/spongycastle/i18n/filter/TrustedInput:<init>	(Ljava/lang/Object;)V
    //   890: aastore
    //   891: new 662	org/spongycastle/i18n/filter/UntrustedUrlInput
    //   894: dup
    //   895: aload 86
    //   897: invokespecial 663	org/spongycastle/i18n/filter/UntrustedUrlInput:<init>	(Ljava/lang/Object;)V
    //   900: astore 92
    //   902: aload 91
    //   904: iconst_2
    //   905: aload 92
    //   907: aastore
    //   908: new 147	org/spongycastle/i18n/ErrorBundle
    //   911: dup
    //   912: ldc 11
    //   914: ldc_w 1001
    //   917: aload 91
    //   919: invokespecial 152	org/spongycastle/i18n/ErrorBundle:<init>	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   922: astore 93
    //   924: aload_0
    //   925: aload 93
    //   927: iload 7
    //   929: invokevirtual 667	org/spongycastle/x509/PKIXCertPathReviewer:addNotification	(Lorg/spongycastle/i18n/ErrorBundle;I)V
    //   932: goto -599 -> 333
    //   935: aload 5
    //   937: ifnull +210 -> 1147
    //   940: aload 16
    //   942: aload 5
    //   944: ldc_w 827
    //   947: invokevirtual 1005	java/security/cert/X509CRL:verify	(Ljava/security/PublicKey;Ljava/lang/String;)V
    //   950: aload 16
    //   952: aload_2
    //   953: invokevirtual 1008	java/security/cert/X509Certificate:getSerialNumber	()Ljava/math/BigInteger;
    //   956: invokevirtual 1012	java/security/cert/X509CRL:getRevokedCertificate	(Ljava/math/BigInteger;)Ljava/security/cert/X509CRLEntry;
    //   959: astore 26
    //   961: aload 26
    //   963: ifnull +555 -> 1518
    //   966: aload 26
    //   968: invokevirtual 1017	java/security/cert/X509CRLEntry:hasExtensions	()Z
    //   971: istore 69
    //   973: aconst_null
    //   974: astore 70
    //   976: iload 69
    //   978: ifeq +41 -> 1019
    //   981: aload 26
    //   983: getstatic 1020	org/spongycastle/asn1/x509/X509Extensions:ReasonCode	Lorg/spongycastle/asn1/ASN1ObjectIdentifier;
    //   986: invokevirtual 46	org/spongycastle/asn1/ASN1ObjectIdentifier:getId	()Ljava/lang/String;
    //   989: invokestatic 306	org/spongycastle/x509/PKIXCertPathReviewer:getExtensionValue	(Ljava/security/cert/X509Extension;Ljava/lang/String;)Lorg/spongycastle/asn1/ASN1Primitive;
    //   992: invokestatic 1025	org/spongycastle/asn1/DEREnumerated:getInstance	(Ljava/lang/Object;)Lorg/spongycastle/asn1/ASN1Enumerated;
    //   995: astore 80
    //   997: aconst_null
    //   998: astore 70
    //   1000: aload 80
    //   1002: ifnull +17 -> 1019
    //   1005: getstatic 1029	org/spongycastle/x509/PKIXCertPathReviewer:crlReasons	[Ljava/lang/String;
    //   1008: aload 80
    //   1010: invokevirtual 1030	org/spongycastle/asn1/DEREnumerated:getValue	()Ljava/math/BigInteger;
    //   1013: invokevirtual 401	java/math/BigInteger:intValue	()I
    //   1016: aaload
    //   1017: astore 70
    //   1019: aload 70
    //   1021: ifnonnull +11 -> 1032
    //   1024: getstatic 1029	org/spongycastle/x509/PKIXCertPathReviewer:crlReasons	[Ljava/lang/String;
    //   1027: bipush 7
    //   1029: aaload
    //   1030: astore 70
    //   1032: new 727	org/spongycastle/i18n/LocaleString
    //   1035: dup
    //   1036: ldc 11
    //   1038: aload 70
    //   1040: invokespecial 730	org/spongycastle/i18n/LocaleString:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   1043: astore 71
    //   1045: aload_3
    //   1046: aload 26
    //   1048: invokevirtual 1033	java/security/cert/X509CRLEntry:getRevocationDate	()Ljava/util/Date;
    //   1051: invokevirtual 976	java/util/Date:before	(Ljava/util/Date;)Z
    //   1054: ifne +153 -> 1207
    //   1057: iconst_2
    //   1058: anewarray 133	java/lang/Object
    //   1061: astore 74
    //   1063: aload 74
    //   1065: iconst_0
    //   1066: new 573	org/spongycastle/i18n/filter/TrustedInput
    //   1069: dup
    //   1070: aload 26
    //   1072: invokevirtual 1033	java/security/cert/X509CRLEntry:getRevocationDate	()Ljava/util/Date;
    //   1075: invokespecial 576	org/spongycastle/i18n/filter/TrustedInput:<init>	(Ljava/lang/Object;)V
    //   1078: aastore
    //   1079: aload 74
    //   1081: iconst_1
    //   1082: aload 71
    //   1084: aastore
    //   1085: new 147	org/spongycastle/i18n/ErrorBundle
    //   1088: dup
    //   1089: ldc 11
    //   1091: ldc_w 1035
    //   1094: aload 74
    //   1096: invokespecial 152	org/spongycastle/i18n/ErrorBundle:<init>	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   1099: astore 75
    //   1101: new 64	org/spongycastle/x509/CertPathReviewerException
    //   1104: dup
    //   1105: aload 75
    //   1107: invokespecial 500	org/spongycastle/x509/CertPathReviewerException:<init>	(Lorg/spongycastle/i18n/ErrorBundle;)V
    //   1110: astore 76
    //   1112: aload 76
    //   1114: athrow
    //   1115: astore 23
    //   1117: new 147	org/spongycastle/i18n/ErrorBundle
    //   1120: dup
    //   1121: ldc 11
    //   1123: ldc_w 1037
    //   1126: invokespecial 341	org/spongycastle/i18n/ErrorBundle:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   1129: astore 24
    //   1131: new 64	org/spongycastle/x509/CertPathReviewerException
    //   1134: dup
    //   1135: aload 24
    //   1137: aload 23
    //   1139: invokespecial 155	org/spongycastle/x509/CertPathReviewerException:<init>	(Lorg/spongycastle/i18n/ErrorBundle;Ljava/lang/Throwable;)V
    //   1142: astore 25
    //   1144: aload 25
    //   1146: athrow
    //   1147: new 147	org/spongycastle/i18n/ErrorBundle
    //   1150: dup
    //   1151: ldc 11
    //   1153: ldc_w 1039
    //   1156: invokespecial 341	org/spongycastle/i18n/ErrorBundle:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   1159: astore 21
    //   1161: new 64	org/spongycastle/x509/CertPathReviewerException
    //   1164: dup
    //   1165: aload 21
    //   1167: invokespecial 500	org/spongycastle/x509/CertPathReviewerException:<init>	(Lorg/spongycastle/i18n/ErrorBundle;)V
    //   1170: astore 22
    //   1172: aload 22
    //   1174: athrow
    //   1175: astore 77
    //   1177: new 147	org/spongycastle/i18n/ErrorBundle
    //   1180: dup
    //   1181: ldc 11
    //   1183: ldc_w 1041
    //   1186: invokespecial 341	org/spongycastle/i18n/ErrorBundle:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   1189: astore 78
    //   1191: new 64	org/spongycastle/x509/CertPathReviewerException
    //   1194: dup
    //   1195: aload 78
    //   1197: aload 77
    //   1199: invokespecial 155	org/spongycastle/x509/CertPathReviewerException:<init>	(Lorg/spongycastle/i18n/ErrorBundle;Ljava/lang/Throwable;)V
    //   1202: astore 79
    //   1204: aload 79
    //   1206: athrow
    //   1207: iconst_2
    //   1208: anewarray 133	java/lang/Object
    //   1211: astore 72
    //   1213: aload 72
    //   1215: iconst_0
    //   1216: new 573	org/spongycastle/i18n/filter/TrustedInput
    //   1219: dup
    //   1220: aload 26
    //   1222: invokevirtual 1033	java/security/cert/X509CRLEntry:getRevocationDate	()Ljava/util/Date;
    //   1225: invokespecial 576	org/spongycastle/i18n/filter/TrustedInput:<init>	(Ljava/lang/Object;)V
    //   1228: aastore
    //   1229: aload 72
    //   1231: iconst_1
    //   1232: aload 71
    //   1234: aastore
    //   1235: new 147	org/spongycastle/i18n/ErrorBundle
    //   1238: dup
    //   1239: ldc 11
    //   1241: ldc_w 1043
    //   1244: aload 72
    //   1246: invokespecial 152	org/spongycastle/i18n/ErrorBundle:<init>	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   1249: astore 73
    //   1251: aload_0
    //   1252: aload 73
    //   1254: iload 7
    //   1256: invokevirtual 667	org/spongycastle/x509/PKIXCertPathReviewer:addNotification	(Lorg/spongycastle/i18n/ErrorBundle;I)V
    //   1259: aload 16
    //   1261: invokevirtual 969	java/security/cert/X509CRL:getNextUpdate	()Ljava/util/Date;
    //   1264: ifnull +67 -> 1331
    //   1267: aload 16
    //   1269: invokevirtual 969	java/security/cert/X509CRL:getNextUpdate	()Ljava/util/Date;
    //   1272: aload_0
    //   1273: getfield 104	org/spongycastle/x509/PKIXCertPathReviewer:pkixParams	Ljava/security/cert/PKIXParameters;
    //   1276: invokevirtual 972	java/security/cert/PKIXParameters:getDate	()Ljava/util/Date;
    //   1279: invokevirtual 976	java/util/Date:before	(Ljava/util/Date;)Z
    //   1282: ifeq +49 -> 1331
    //   1285: iconst_1
    //   1286: anewarray 133	java/lang/Object
    //   1289: astore 67
    //   1291: aload 67
    //   1293: iconst_0
    //   1294: new 573	org/spongycastle/i18n/filter/TrustedInput
    //   1297: dup
    //   1298: aload 16
    //   1300: invokevirtual 969	java/security/cert/X509CRL:getNextUpdate	()Ljava/util/Date;
    //   1303: invokespecial 576	org/spongycastle/i18n/filter/TrustedInput:<init>	(Ljava/lang/Object;)V
    //   1306: aastore
    //   1307: new 147	org/spongycastle/i18n/ErrorBundle
    //   1310: dup
    //   1311: ldc 11
    //   1313: ldc_w 1045
    //   1316: aload 67
    //   1318: invokespecial 152	org/spongycastle/i18n/ErrorBundle:<init>	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   1321: astore 68
    //   1323: aload_0
    //   1324: aload 68
    //   1326: iload 7
    //   1328: invokevirtual 667	org/spongycastle/x509/PKIXCertPathReviewer:addNotification	(Lorg/spongycastle/i18n/ErrorBundle;I)V
    //   1331: aload 16
    //   1333: getstatic 206	org/spongycastle/x509/PKIXCertPathReviewer:ISSUING_DISTRIBUTION_POINT	Ljava/lang/String;
    //   1336: invokestatic 306	org/spongycastle/x509/PKIXCertPathReviewer:getExtensionValue	(Ljava/security/cert/X509Extension;Ljava/lang/String;)Lorg/spongycastle/asn1/ASN1Primitive;
    //   1339: astore 31
    //   1341: aload 16
    //   1343: getstatic 209	org/spongycastle/x509/PKIXCertPathReviewer:DELTA_CRL_INDICATOR	Ljava/lang/String;
    //   1346: invokestatic 306	org/spongycastle/x509/PKIXCertPathReviewer:getExtensionValue	(Ljava/security/cert/X509Extension;Ljava/lang/String;)Lorg/spongycastle/asn1/ASN1Primitive;
    //   1349: astore 35
    //   1351: aload 35
    //   1353: ifnull +394 -> 1747
    //   1356: new 939	org/spongycastle/x509/X509CRLStoreSelector
    //   1359: dup
    //   1360: invokespecial 940	org/spongycastle/x509/X509CRLStoreSelector:<init>	()V
    //   1363: astore 36
    //   1365: aload 36
    //   1367: aload 16
    //   1369: invokestatic 1049	org/spongycastle/x509/PKIXCertPathReviewer:getIssuerPrincipal	(Ljava/security/cert/X509CRL;)Ljavax/security/auth/x500/X500Principal;
    //   1372: invokevirtual 283	javax/security/auth/x500/X500Principal:getEncoded	()[B
    //   1375: invokevirtual 947	org/spongycastle/x509/X509CRLStoreSelector:addIssuerName	([B)V
    //   1378: aload 36
    //   1380: aload 35
    //   1382: checkcast 537	org/spongycastle/asn1/DERInteger
    //   1385: invokevirtual 1052	org/spongycastle/asn1/DERInteger:getPositiveValue	()Ljava/math/BigInteger;
    //   1388: invokevirtual 1056	org/spongycastle/x509/X509CRLStoreSelector:setMinCRLNumber	(Ljava/math/BigInteger;)V
    //   1391: aload 36
    //   1393: aload 16
    //   1395: getstatic 1059	org/spongycastle/x509/PKIXCertPathReviewer:CRL_NUMBER	Ljava/lang/String;
    //   1398: invokestatic 306	org/spongycastle/x509/PKIXCertPathReviewer:getExtensionValue	(Ljava/security/cert/X509Extension;Ljava/lang/String;)Lorg/spongycastle/asn1/ASN1Primitive;
    //   1401: checkcast 537	org/spongycastle/asn1/DERInteger
    //   1404: invokevirtual 1052	org/spongycastle/asn1/DERInteger:getPositiveValue	()Ljava/math/BigInteger;
    //   1407: lconst_1
    //   1408: invokestatic 1063	java/math/BigInteger:valueOf	(J)Ljava/math/BigInteger;
    //   1411: invokevirtual 1067	java/math/BigInteger:subtract	(Ljava/math/BigInteger;)Ljava/math/BigInteger;
    //   1414: invokevirtual 1070	org/spongycastle/x509/X509CRLStoreSelector:setMaxCRLNumber	(Ljava/math/BigInteger;)V
    //   1417: getstatic 955	org/spongycastle/x509/PKIXCertPathReviewer:CRL_UTIL	Lorg/spongycastle/jce/provider/PKIXCRLUtil;
    //   1420: aload 36
    //   1422: aload_1
    //   1423: invokevirtual 961	org/spongycastle/jce/provider/PKIXCRLUtil:findCRLs	(Lorg/spongycastle/x509/X509CRLStoreSelector;Ljava/security/cert/PKIXParameters;)Ljava/util/Set;
    //   1426: invokeinterface 244 1 0
    //   1431: astore 46
    //   1433: aload 46
    //   1435: invokeinterface 122 1 0
    //   1440: istore 47
    //   1442: iconst_0
    //   1443: istore 48
    //   1445: iload 47
    //   1447: ifeq +38 -> 1485
    //   1450: aload 46
    //   1452: invokeinterface 126 1 0
    //   1457: checkcast 842	java/security/cert/X509CRL
    //   1460: astore 62
    //   1462: aload 62
    //   1464: getstatic 206	org/spongycastle/x509/PKIXCertPathReviewer:ISSUING_DISTRIBUTION_POINT	Ljava/lang/String;
    //   1467: invokestatic 306	org/spongycastle/x509/PKIXCertPathReviewer:getExtensionValue	(Ljava/security/cert/X509Extension;Ljava/lang/String;)Lorg/spongycastle/asn1/ASN1Primitive;
    //   1470: astore 66
    //   1472: aload 31
    //   1474: ifnonnull +257 -> 1731
    //   1477: aload 66
    //   1479: ifnonnull -46 -> 1433
    //   1482: iconst_1
    //   1483: istore 48
    //   1485: iload 48
    //   1487: ifne +260 -> 1747
    //   1490: new 147	org/spongycastle/i18n/ErrorBundle
    //   1493: dup
    //   1494: ldc 11
    //   1496: ldc_w 1072
    //   1499: invokespecial 341	org/spongycastle/i18n/ErrorBundle:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   1502: astore 49
    //   1504: new 64	org/spongycastle/x509/CertPathReviewerException
    //   1507: dup
    //   1508: aload 49
    //   1510: invokespecial 500	org/spongycastle/x509/CertPathReviewerException:<init>	(Lorg/spongycastle/i18n/ErrorBundle;)V
    //   1513: astore 50
    //   1515: aload 50
    //   1517: athrow
    //   1518: new 147	org/spongycastle/i18n/ErrorBundle
    //   1521: dup
    //   1522: ldc 11
    //   1524: ldc_w 1074
    //   1527: invokespecial 341	org/spongycastle/i18n/ErrorBundle:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   1530: astore 27
    //   1532: aload_0
    //   1533: aload 27
    //   1535: iload 7
    //   1537: invokevirtual 667	org/spongycastle/x509/PKIXCertPathReviewer:addNotification	(Lorg/spongycastle/i18n/ErrorBundle;I)V
    //   1540: goto -281 -> 1259
    //   1543: astore 28
    //   1545: new 147	org/spongycastle/i18n/ErrorBundle
    //   1548: dup
    //   1549: ldc 11
    //   1551: ldc_w 1076
    //   1554: invokespecial 341	org/spongycastle/i18n/ErrorBundle:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   1557: astore 29
    //   1559: new 64	org/spongycastle/x509/CertPathReviewerException
    //   1562: dup
    //   1563: aload 29
    //   1565: invokespecial 500	org/spongycastle/x509/CertPathReviewerException:<init>	(Lorg/spongycastle/i18n/ErrorBundle;)V
    //   1568: astore 30
    //   1570: aload 30
    //   1572: athrow
    //   1573: astore 32
    //   1575: new 147	org/spongycastle/i18n/ErrorBundle
    //   1578: dup
    //   1579: ldc 11
    //   1581: ldc_w 1078
    //   1584: invokespecial 341	org/spongycastle/i18n/ErrorBundle:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   1587: astore 33
    //   1589: new 64	org/spongycastle/x509/CertPathReviewerException
    //   1592: dup
    //   1593: aload 33
    //   1595: invokespecial 500	org/spongycastle/x509/CertPathReviewerException:<init>	(Lorg/spongycastle/i18n/ErrorBundle;)V
    //   1598: astore 34
    //   1600: aload 34
    //   1602: athrow
    //   1603: astore 37
    //   1605: new 147	org/spongycastle/i18n/ErrorBundle
    //   1608: dup
    //   1609: ldc 11
    //   1611: ldc_w 987
    //   1614: invokespecial 341	org/spongycastle/i18n/ErrorBundle:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   1617: astore 38
    //   1619: new 64	org/spongycastle/x509/CertPathReviewerException
    //   1622: dup
    //   1623: aload 38
    //   1625: aload 37
    //   1627: invokespecial 155	org/spongycastle/x509/CertPathReviewerException:<init>	(Lorg/spongycastle/i18n/ErrorBundle;Ljava/lang/Throwable;)V
    //   1630: astore 39
    //   1632: aload 39
    //   1634: athrow
    //   1635: astore 40
    //   1637: new 147	org/spongycastle/i18n/ErrorBundle
    //   1640: dup
    //   1641: ldc 11
    //   1643: ldc_w 1080
    //   1646: invokespecial 341	org/spongycastle/i18n/ErrorBundle:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   1649: astore 41
    //   1651: new 64	org/spongycastle/x509/CertPathReviewerException
    //   1654: dup
    //   1655: aload 41
    //   1657: aload 40
    //   1659: invokespecial 155	org/spongycastle/x509/CertPathReviewerException:<init>	(Lorg/spongycastle/i18n/ErrorBundle;Ljava/lang/Throwable;)V
    //   1662: astore 42
    //   1664: aload 42
    //   1666: athrow
    //   1667: astore 43
    //   1669: new 147	org/spongycastle/i18n/ErrorBundle
    //   1672: dup
    //   1673: ldc 11
    //   1675: ldc_w 965
    //   1678: invokespecial 341	org/spongycastle/i18n/ErrorBundle:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   1681: astore 44
    //   1683: new 64	org/spongycastle/x509/CertPathReviewerException
    //   1686: dup
    //   1687: aload 44
    //   1689: aload 43
    //   1691: invokespecial 155	org/spongycastle/x509/CertPathReviewerException:<init>	(Lorg/spongycastle/i18n/ErrorBundle;Ljava/lang/Throwable;)V
    //   1694: astore 45
    //   1696: aload 45
    //   1698: athrow
    //   1699: astore 63
    //   1701: new 147	org/spongycastle/i18n/ErrorBundle
    //   1704: dup
    //   1705: ldc 11
    //   1707: ldc_w 1076
    //   1710: invokespecial 341	org/spongycastle/i18n/ErrorBundle:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   1713: astore 64
    //   1715: new 64	org/spongycastle/x509/CertPathReviewerException
    //   1718: dup
    //   1719: aload 64
    //   1721: aload 63
    //   1723: invokespecial 155	org/spongycastle/x509/CertPathReviewerException:<init>	(Lorg/spongycastle/i18n/ErrorBundle;Ljava/lang/Throwable;)V
    //   1726: astore 65
    //   1728: aload 65
    //   1730: athrow
    //   1731: aload 31
    //   1733: aload 66
    //   1735: invokevirtual 1083	org/spongycastle/asn1/ASN1Primitive:equals	(Ljava/lang/Object;)Z
    //   1738: ifeq -305 -> 1433
    //   1741: iconst_1
    //   1742: istore 48
    //   1744: goto -259 -> 1485
    //   1747: aload 31
    //   1749: ifnull +188 -> 1937
    //   1752: aload 31
    //   1754: invokestatic 1088	org/spongycastle/asn1/x509/IssuingDistributionPoint:getInstance	(Ljava/lang/Object;)Lorg/spongycastle/asn1/x509/IssuingDistributionPoint;
    //   1757: astore 51
    //   1759: aload_2
    //   1760: getstatic 215	org/spongycastle/x509/PKIXCertPathReviewer:BASIC_CONSTRAINTS	Ljava/lang/String;
    //   1763: invokestatic 306	org/spongycastle/x509/PKIXCertPathReviewer:getExtensionValue	(Ljava/security/cert/X509Extension;Ljava/lang/String;)Lorg/spongycastle/asn1/ASN1Primitive;
    //   1766: invokestatic 392	org/spongycastle/asn1/x509/BasicConstraints:getInstance	(Ljava/lang/Object;)Lorg/spongycastle/asn1/x509/BasicConstraints;
    //   1769: astore 55
    //   1771: aload 51
    //   1773: invokevirtual 1091	org/spongycastle/asn1/x509/IssuingDistributionPoint:onlyContainsUserCerts	()Z
    //   1776: ifeq +76 -> 1852
    //   1779: aload 55
    //   1781: ifnull +71 -> 1852
    //   1784: aload 55
    //   1786: invokevirtual 774	org/spongycastle/asn1/x509/BasicConstraints:isCA	()Z
    //   1789: ifeq +63 -> 1852
    //   1792: new 147	org/spongycastle/i18n/ErrorBundle
    //   1795: dup
    //   1796: ldc 11
    //   1798: ldc_w 1093
    //   1801: invokespecial 341	org/spongycastle/i18n/ErrorBundle:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   1804: astore 60
    //   1806: new 64	org/spongycastle/x509/CertPathReviewerException
    //   1809: dup
    //   1810: aload 60
    //   1812: invokespecial 500	org/spongycastle/x509/CertPathReviewerException:<init>	(Lorg/spongycastle/i18n/ErrorBundle;)V
    //   1815: astore 61
    //   1817: aload 61
    //   1819: athrow
    //   1820: astore 52
    //   1822: new 147	org/spongycastle/i18n/ErrorBundle
    //   1825: dup
    //   1826: ldc 11
    //   1828: ldc_w 1095
    //   1831: invokespecial 341	org/spongycastle/i18n/ErrorBundle:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   1834: astore 53
    //   1836: new 64	org/spongycastle/x509/CertPathReviewerException
    //   1839: dup
    //   1840: aload 53
    //   1842: aload 52
    //   1844: invokespecial 155	org/spongycastle/x509/CertPathReviewerException:<init>	(Lorg/spongycastle/i18n/ErrorBundle;Ljava/lang/Throwable;)V
    //   1847: astore 54
    //   1849: aload 54
    //   1851: athrow
    //   1852: aload 51
    //   1854: invokevirtual 1098	org/spongycastle/asn1/x509/IssuingDistributionPoint:onlyContainsCACerts	()Z
    //   1857: ifeq +44 -> 1901
    //   1860: aload 55
    //   1862: ifnull +11 -> 1873
    //   1865: aload 55
    //   1867: invokevirtual 774	org/spongycastle/asn1/x509/BasicConstraints:isCA	()Z
    //   1870: ifne +31 -> 1901
    //   1873: new 147	org/spongycastle/i18n/ErrorBundle
    //   1876: dup
    //   1877: ldc 11
    //   1879: ldc_w 1100
    //   1882: invokespecial 341	org/spongycastle/i18n/ErrorBundle:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   1885: astore 58
    //   1887: new 64	org/spongycastle/x509/CertPathReviewerException
    //   1890: dup
    //   1891: aload 58
    //   1893: invokespecial 500	org/spongycastle/x509/CertPathReviewerException:<init>	(Lorg/spongycastle/i18n/ErrorBundle;)V
    //   1896: astore 59
    //   1898: aload 59
    //   1900: athrow
    //   1901: aload 51
    //   1903: invokevirtual 1103	org/spongycastle/asn1/x509/IssuingDistributionPoint:onlyContainsAttributeCerts	()Z
    //   1906: ifeq +31 -> 1937
    //   1909: new 147	org/spongycastle/i18n/ErrorBundle
    //   1912: dup
    //   1913: ldc 11
    //   1915: ldc_w 1105
    //   1918: invokespecial 341	org/spongycastle/i18n/ErrorBundle:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   1921: astore 56
    //   1923: new 64	org/spongycastle/x509/CertPathReviewerException
    //   1926: dup
    //   1927: aload 56
    //   1929: invokespecial 500	org/spongycastle/x509/CertPathReviewerException:<init>	(Lorg/spongycastle/i18n/ErrorBundle;)V
    //   1932: astore 57
    //   1934: aload 57
    //   1936: athrow
    //   1937: iload 18
    //   1939: ifne +31 -> 1970
    //   1942: new 147	org/spongycastle/i18n/ErrorBundle
    //   1945: dup
    //   1946: ldc 11
    //   1948: ldc_w 1107
    //   1951: invokespecial 341	org/spongycastle/i18n/ErrorBundle:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   1954: astore 19
    //   1956: new 64	org/spongycastle/x509/CertPathReviewerException
    //   1959: dup
    //   1960: aload 19
    //   1962: invokespecial 500	org/spongycastle/x509/CertPathReviewerException:<init>	(Lorg/spongycastle/i18n/ErrorBundle;)V
    //   1965: astore 20
    //   1967: aload 20
    //   1969: athrow
    //   1970: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	1971	0	this	PKIXCertPathReviewer
    //   0	1971	1	paramPKIXParameters	PKIXParameters
    //   0	1971	2	paramX509Certificate1	X509Certificate
    //   0	1971	3	paramDate	Date
    //   0	1971	4	paramX509Certificate2	X509Certificate
    //   0	1971	5	paramPublicKey	PublicKey
    //   0	1971	6	paramVector	Vector
    //   0	1971	7	paramInt	int
    //   7	534	8	localX509CRLStoreSelector1	X509CRLStoreSelector
    //   486	23	9	localIOException1	IOException
    //   500	7	10	localErrorBundle1	ErrorBundle
    //   513	3	11	localCertPathReviewerException1	CertPathReviewerException
    //   121	33	12	localAnnotatedException1	AnnotatedException
    //   127	48	13	arrayOfObject1	Object[]
    //   179	4	14	localErrorBundle2	ErrorBundle
    //   45	177	15	localIterator1	Iterator
    //   202	1192	16	localObject	Object
    //   211	6	17	bool1	boolean
    //   214	1724	18	i	int
    //   1954	7	19	localErrorBundle3	ErrorBundle
    //   1965	3	20	localCertPathReviewerException2	CertPathReviewerException
    //   1159	7	21	localErrorBundle4	ErrorBundle
    //   1170	3	22	localCertPathReviewerException3	CertPathReviewerException
    //   1115	23	23	localException	Exception
    //   1129	7	24	localErrorBundle5	ErrorBundle
    //   1142	3	25	localCertPathReviewerException4	CertPathReviewerException
    //   959	262	26	localX509CRLEntry	java.security.cert.X509CRLEntry
    //   1530	4	27	localErrorBundle6	ErrorBundle
    //   1543	1	28	localAnnotatedException2	AnnotatedException
    //   1557	7	29	localErrorBundle7	ErrorBundle
    //   1568	3	30	localCertPathReviewerException5	CertPathReviewerException
    //   1339	414	31	localASN1Primitive1	ASN1Primitive
    //   1573	1	32	localAnnotatedException3	AnnotatedException
    //   1587	7	33	localErrorBundle8	ErrorBundle
    //   1598	3	34	localCertPathReviewerException6	CertPathReviewerException
    //   1349	32	35	localASN1Primitive2	ASN1Primitive
    //   1363	58	36	localX509CRLStoreSelector2	X509CRLStoreSelector
    //   1603	23	37	localIOException2	IOException
    //   1617	7	38	localErrorBundle9	ErrorBundle
    //   1630	3	39	localCertPathReviewerException7	CertPathReviewerException
    //   1635	23	40	localAnnotatedException4	AnnotatedException
    //   1649	7	41	localErrorBundle10	ErrorBundle
    //   1662	3	42	localCertPathReviewerException8	CertPathReviewerException
    //   1667	23	43	localAnnotatedException5	AnnotatedException
    //   1681	7	44	localErrorBundle11	ErrorBundle
    //   1694	3	45	localCertPathReviewerException9	CertPathReviewerException
    //   1431	20	46	localIterator2	Iterator
    //   1440	6	47	bool2	boolean
    //   1443	300	48	j	int
    //   1502	7	49	localErrorBundle12	ErrorBundle
    //   1513	3	50	localCertPathReviewerException10	CertPathReviewerException
    //   1757	145	51	localIssuingDistributionPoint	org.spongycastle.asn1.x509.IssuingDistributionPoint
    //   1820	23	52	localAnnotatedException6	AnnotatedException
    //   1834	7	53	localErrorBundle13	ErrorBundle
    //   1847	3	54	localCertPathReviewerException11	CertPathReviewerException
    //   1769	97	55	localBasicConstraints	BasicConstraints
    //   1921	7	56	localErrorBundle14	ErrorBundle
    //   1932	3	57	localCertPathReviewerException12	CertPathReviewerException
    //   1885	7	58	localErrorBundle15	ErrorBundle
    //   1896	3	59	localCertPathReviewerException13	CertPathReviewerException
    //   1804	7	60	localErrorBundle16	ErrorBundle
    //   1815	3	61	localCertPathReviewerException14	CertPathReviewerException
    //   1460	3	62	localX509CRL1	X509CRL
    //   1699	23	63	localAnnotatedException7	AnnotatedException
    //   1713	7	64	localErrorBundle17	ErrorBundle
    //   1726	3	65	localCertPathReviewerException15	CertPathReviewerException
    //   1470	264	66	localASN1Primitive3	ASN1Primitive
    //   1289	28	67	arrayOfObject2	Object[]
    //   1321	4	68	localErrorBundle18	ErrorBundle
    //   971	6	69	bool3	boolean
    //   974	65	70	str1	String
    //   1043	190	71	localLocaleString	org.spongycastle.i18n.LocaleString
    //   1211	34	72	arrayOfObject3	Object[]
    //   1249	4	73	localErrorBundle19	ErrorBundle
    //   1061	34	74	arrayOfObject4	Object[]
    //   1099	7	75	localErrorBundle20	ErrorBundle
    //   1110	3	76	localCertPathReviewerException16	CertPathReviewerException
    //   1175	23	77	localAnnotatedException8	AnnotatedException
    //   1189	7	78	localErrorBundle21	ErrorBundle
    //   1202	3	79	localCertPathReviewerException17	CertPathReviewerException
    //   995	14	80	localASN1Enumerated	org.spongycastle.asn1.ASN1Enumerated
    //   802	16	81	arrayOfBoolean	boolean[]
    //   837	7	82	localErrorBundle22	ErrorBundle
    //   848	3	83	localCertPathReviewerException18	CertPathReviewerException
    //   331	13	84	localIterator3	Iterator
    //   470	4	85	localCertPathReviewerException19	CertPathReviewerException
    //   353	543	86	str2	String
    //   361	522	87	localX509CRL2	X509CRL
    //   708	61	88	arrayOfObject5	Object[]
    //   751	6	89	localUntrustedUrlInput1	org.spongycastle.i18n.filter.UntrustedUrlInput
    //   773	4	90	localErrorBundle23	ErrorBundle
    //   857	61	91	arrayOfObject6	Object[]
    //   900	6	92	localUntrustedUrlInput2	org.spongycastle.i18n.filter.UntrustedUrlInput
    //   922	4	93	localErrorBundle24	ErrorBundle
    //   387	66	94	arrayOfObject7	Object[]
    //   435	6	95	localUntrustedUrlInput3	org.spongycastle.i18n.filter.UntrustedUrlInput
    //   457	4	96	localErrorBundle25	ErrorBundle
    //   263	44	97	arrayOfObject8	Object[]
    //   311	4	98	localErrorBundle26	ErrorBundle
    //   614	44	99	arrayOfObject9	Object[]
    //   662	4	100	localErrorBundle27	ErrorBundle
    //   36	12	101	localSet	Set
    //   76	24	102	localIterator4	Iterator
    //   85	469	103	localArrayList	ArrayList
    //   525	46	104	k	int
    //   531	62	105	arrayOfObject10	Object[]
    //   558	6	106	localUntrustedInput	UntrustedInput
    //   575	6	107	localInteger	Integer
    //   597	4	108	localErrorBundle28	ErrorBundle
    // Exception table:
    //   from	to	target	type
    //   27	87	121	org/spongycastle/jce/provider/AnnotatedException
    //   87	118	121	org/spongycastle/jce/provider/AnnotatedException
    //   518	607	121	org/spongycastle/jce/provider/AnnotatedException
    //   343	363	470	org/spongycastle/x509/CertPathReviewerException
    //   368	467	470	org/spongycastle/x509/CertPathReviewerException
    //   675	701	470	org/spongycastle/x509/CertPathReviewerException
    //   704	783	470	org/spongycastle/x509/CertPathReviewerException
    //   853	932	470	org/spongycastle/x509/CertPathReviewerException
    //   9	21	486	java/io/IOException
    //   940	950	1115	java/lang/Exception
    //   981	997	1175	org/spongycastle/jce/provider/AnnotatedException
    //   1331	1341	1543	org/spongycastle/jce/provider/AnnotatedException
    //   1341	1351	1573	org/spongycastle/jce/provider/AnnotatedException
    //   1365	1378	1603	java/io/IOException
    //   1391	1417	1635	org/spongycastle/jce/provider/AnnotatedException
    //   1417	1433	1667	org/spongycastle/jce/provider/AnnotatedException
    //   1462	1472	1699	org/spongycastle/jce/provider/AnnotatedException
    //   1759	1771	1820	org/spongycastle/jce/provider/AnnotatedException
  }
  
  protected void checkRevocation(PKIXParameters paramPKIXParameters, X509Certificate paramX509Certificate1, Date paramDate, X509Certificate paramX509Certificate2, PublicKey paramPublicKey, Vector paramVector1, Vector paramVector2, int paramInt)
    throws CertPathReviewerException
  {
    checkCRLs(paramPKIXParameters, paramX509Certificate1, paramDate, paramX509Certificate2, paramPublicKey, paramVector1, paramInt);
  }
  
  protected void doChecks()
  {
    if (!this.initialized) {
      throw new IllegalStateException("Object not initialized. Call init() first.");
    }
    if (this.notifications == null)
    {
      this.notifications = new List[1 + this.n];
      this.errors = new List[1 + this.n];
      for (int i = 0; i < this.notifications.length; i++)
      {
        this.notifications[i] = new ArrayList();
        this.errors[i] = new ArrayList();
      }
      checkSignatures();
      checkNameConstraints();
      checkPathLength();
      checkPolicy();
      checkCriticalExtensions();
    }
  }
  
  protected Vector getCRLDistUrls(CRLDistPoint paramCRLDistPoint)
  {
    Vector localVector = new Vector();
    if (paramCRLDistPoint != null)
    {
      DistributionPoint[] arrayOfDistributionPoint = paramCRLDistPoint.getDistributionPoints();
      for (int i = 0; i < arrayOfDistributionPoint.length; i++)
      {
        DistributionPointName localDistributionPointName = arrayOfDistributionPoint[i].getDistributionPoint();
        if (localDistributionPointName.getType() == 0)
        {
          GeneralName[] arrayOfGeneralName = GeneralNames.getInstance(localDistributionPointName.getName()).getNames();
          for (int j = 0; j < arrayOfGeneralName.length; j++) {
            if (arrayOfGeneralName[j].getTagNo() == 6) {
              localVector.add(((DERIA5String)arrayOfGeneralName[j].getName()).getString());
            }
          }
        }
      }
    }
    return localVector;
  }
  
  public CertPath getCertPath()
  {
    return this.certPath;
  }
  
  public int getCertPathSize()
  {
    return this.n;
  }
  
  public List getErrors(int paramInt)
  {
    doChecks();
    return this.errors[(paramInt + 1)];
  }
  
  public List[] getErrors()
  {
    doChecks();
    return this.errors;
  }
  
  public List getNotifications(int paramInt)
  {
    doChecks();
    return this.notifications[(paramInt + 1)];
  }
  
  public List[] getNotifications()
  {
    doChecks();
    return this.notifications;
  }
  
  protected Vector getOCSPUrls(AuthorityInformationAccess paramAuthorityInformationAccess)
  {
    Vector localVector = new Vector();
    if (paramAuthorityInformationAccess != null)
    {
      AccessDescription[] arrayOfAccessDescription = paramAuthorityInformationAccess.getAccessDescriptions();
      for (int i = 0; i < arrayOfAccessDescription.length; i++) {
        if (arrayOfAccessDescription[i].getAccessMethod().equals(AccessDescription.id_ad_ocsp))
        {
          GeneralName localGeneralName = arrayOfAccessDescription[i].getAccessLocation();
          if (localGeneralName.getTagNo() == 6) {
            localVector.add(((DERIA5String)localGeneralName.getName()).getString());
          }
        }
      }
    }
    return localVector;
  }
  
  public PolicyNode getPolicyTree()
  {
    doChecks();
    return this.policyTree;
  }
  
  public PublicKey getSubjectPublicKey()
  {
    doChecks();
    return this.subjectPublicKey;
  }
  
  public TrustAnchor getTrustAnchor()
  {
    doChecks();
    return this.trustAnchor;
  }
  
  protected Collection getTrustAnchors(X509Certificate paramX509Certificate, Set paramSet)
    throws CertPathReviewerException
  {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = paramSet.iterator();
    X509CertSelector localX509CertSelector = new X509CertSelector();
    for (;;)
    {
      TrustAnchor localTrustAnchor;
      try
      {
        localX509CertSelector.setSubject(getEncodedIssuerPrincipal(paramX509Certificate).getEncoded());
        byte[] arrayOfByte1 = paramX509Certificate.getExtensionValue(X509Extensions.AuthorityKeyIdentifier.getId());
        if (arrayOfByte1 != null)
        {
          AuthorityKeyIdentifier localAuthorityKeyIdentifier = AuthorityKeyIdentifier.getInstance(ASN1Primitive.fromByteArray(((ASN1OctetString)ASN1Primitive.fromByteArray(arrayOfByte1)).getOctets()));
          localX509CertSelector.setSerialNumber(localAuthorityKeyIdentifier.getAuthorityCertSerialNumber());
          byte[] arrayOfByte2 = localAuthorityKeyIdentifier.getKeyIdentifier();
          if (arrayOfByte2 != null) {
            localX509CertSelector.setSubjectKeyIdentifier(new DEROctetString(arrayOfByte2).getEncoded());
          }
        }
        if (!localIterator.hasNext()) {
          break;
        }
        localTrustAnchor = (TrustAnchor)localIterator.next();
        if (localTrustAnchor.getTrustedCert() != null)
        {
          if (!localX509CertSelector.match(localTrustAnchor.getTrustedCert())) {
            continue;
          }
          localArrayList.add(localTrustAnchor);
          continue;
        }
        if (localTrustAnchor.getCAName() == null) {
          continue;
        }
      }
      catch (IOException localIOException)
      {
        throw new CertPathReviewerException(new ErrorBundle("org.spongycastle.x509.CertPathReviewerMessages", "CertPathReviewer.trustAnchorIssuerError"));
      }
      if ((localTrustAnchor.getCAPublicKey() != null) && (getEncodedIssuerPrincipal(paramX509Certificate).equals(new X500Principal(localTrustAnchor.getCAName())))) {
        localArrayList.add(localTrustAnchor);
      }
    }
    return localArrayList;
  }
  
  public void init(CertPath paramCertPath, PKIXParameters paramPKIXParameters)
    throws CertPathReviewerException
  {
    if (this.initialized) {
      throw new IllegalStateException("object is already initialized!");
    }
    this.initialized = true;
    if (paramCertPath == null) {
      throw new NullPointerException("certPath was null");
    }
    this.certPath = paramCertPath;
    this.certs = paramCertPath.getCertificates();
    this.n = this.certs.size();
    if (this.certs.isEmpty()) {
      throw new CertPathReviewerException(new ErrorBundle("org.spongycastle.x509.CertPathReviewerMessages", "CertPathReviewer.emptyCertPath"));
    }
    this.pkixParams = ((PKIXParameters)paramPKIXParameters.clone());
    this.validDate = getValidDate(this.pkixParams);
    this.notifications = null;
    this.errors = null;
    this.trustAnchor = null;
    this.subjectPublicKey = null;
    this.policyTree = null;
  }
  
  public boolean isValidCertPath()
  {
    doChecks();
    boolean bool = true;
    for (int i = 0;; i++) {
      if (i < this.errors.length)
      {
        if (!this.errors[i].isEmpty()) {
          bool = false;
        }
      }
      else {
        return bool;
      }
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.x509.PKIXCertPathReviewer
 * JD-Core Version:    0.7.0.1
 */