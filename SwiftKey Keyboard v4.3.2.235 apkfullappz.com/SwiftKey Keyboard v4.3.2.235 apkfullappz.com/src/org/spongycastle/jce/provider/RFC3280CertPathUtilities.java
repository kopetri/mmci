package org.spongycastle.jce.provider;

import java.io.IOException;
import java.math.BigInteger;
import java.security.PublicKey;
import java.security.cert.CertPath;
import java.security.cert.CertPathValidatorException;
import java.security.cert.PKIXCertPathChecker;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.security.cert.X509Extension;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.security.auth.x500.X500Principal;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERInteger;
import org.spongycastle.asn1.DERObjectIdentifier;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x509.BasicConstraints;
import org.spongycastle.asn1.x509.CRLDistPoint;
import org.spongycastle.asn1.x509.DistributionPoint;
import org.spongycastle.asn1.x509.DistributionPointName;
import org.spongycastle.asn1.x509.GeneralName;
import org.spongycastle.asn1.x509.GeneralNames;
import org.spongycastle.asn1.x509.IssuingDistributionPoint;
import org.spongycastle.asn1.x509.PolicyInformation;
import org.spongycastle.asn1.x509.X509Extensions;
import org.spongycastle.asn1.x509.X509Name;
import org.spongycastle.jce.exception.ExtCertPathValidatorException;
import org.spongycastle.util.Arrays;
import org.spongycastle.x509.ExtendedPKIXParameters;

public class RFC3280CertPathUtilities
{
  protected static final String ANY_POLICY = "2.5.29.32.0";
  protected static final String AUTHORITY_KEY_IDENTIFIER;
  protected static final String BASIC_CONSTRAINTS;
  protected static final String CERTIFICATE_POLICIES;
  protected static final String CRL_DISTRIBUTION_POINTS;
  protected static final String CRL_NUMBER = X509Extensions.CRLNumber.getId();
  protected static final int CRL_SIGN = 6;
  private static final PKIXCRLUtil CRL_UTIL = new PKIXCRLUtil();
  protected static final String DELTA_CRL_INDICATOR;
  protected static final String FRESHEST_CRL;
  protected static final String INHIBIT_ANY_POLICY;
  protected static final String ISSUING_DISTRIBUTION_POINT;
  protected static final int KEY_CERT_SIGN = 5;
  protected static final String KEY_USAGE;
  protected static final String NAME_CONSTRAINTS;
  protected static final String POLICY_CONSTRAINTS;
  protected static final String POLICY_MAPPINGS;
  protected static final String SUBJECT_ALTERNATIVE_NAME;
  protected static final String[] crlReasons = { "unspecified", "keyCompromise", "cACompromise", "affiliationChanged", "superseded", "cessationOfOperation", "certificateHold", "unknown", "removeFromCRL", "privilegeWithdrawn", "aACompromise" };
  
  static
  {
    CERTIFICATE_POLICIES = X509Extensions.CertificatePolicies.getId();
    POLICY_MAPPINGS = X509Extensions.PolicyMappings.getId();
    INHIBIT_ANY_POLICY = X509Extensions.InhibitAnyPolicy.getId();
    ISSUING_DISTRIBUTION_POINT = X509Extensions.IssuingDistributionPoint.getId();
    FRESHEST_CRL = X509Extensions.FreshestCRL.getId();
    DELTA_CRL_INDICATOR = X509Extensions.DeltaCRLIndicator.getId();
    POLICY_CONSTRAINTS = X509Extensions.PolicyConstraints.getId();
    BASIC_CONSTRAINTS = X509Extensions.BasicConstraints.getId();
    CRL_DISTRIBUTION_POINTS = X509Extensions.CRLDistributionPoints.getId();
    SUBJECT_ALTERNATIVE_NAME = X509Extensions.SubjectAlternativeName.getId();
    NAME_CONSTRAINTS = X509Extensions.NameConstraints.getId();
    AUTHORITY_KEY_IDENTIFIER = X509Extensions.AuthorityKeyIdentifier.getId();
    KEY_USAGE = X509Extensions.KeyUsage.getId();
  }
  
  private static void checkCRL(DistributionPoint paramDistributionPoint, ExtendedPKIXParameters paramExtendedPKIXParameters, X509Certificate paramX509Certificate1, Date paramDate, X509Certificate paramX509Certificate2, PublicKey paramPublicKey, CertStatus paramCertStatus, ReasonsMask paramReasonsMask, List paramList)
    throws AnnotatedException
  {
    Date localDate = new Date(System.currentTimeMillis());
    if (paramDate.getTime() > localDate.getTime()) {
      throw new AnnotatedException("Validation time is in future.");
    }
    Set localSet1 = CertPathValidatorUtilities.getCompleteCRLs(paramDistributionPoint, paramX509Certificate1, localDate, paramExtendedPKIXParameters);
    int i = 0;
    Object localObject = null;
    Iterator localIterator = localSet1.iterator();
    if ((localIterator.hasNext()) && (paramCertStatus.getCertStatus() == 11) && (!paramReasonsMask.isAllReasons())) {}
    label438:
    for (;;)
    {
      try
      {
        X509CRL localX509CRL1 = (X509CRL)localIterator.next();
        ReasonsMask localReasonsMask = processCRLD(localX509CRL1, paramDistributionPoint);
        if (!localReasonsMask.hasNewReasons(paramReasonsMask)) {
          break;
        }
        PublicKey localPublicKey = processCRLG(localX509CRL1, processCRLF(localX509CRL1, paramX509Certificate1, paramX509Certificate2, paramPublicKey, paramExtendedPKIXParameters, paramList));
        boolean bool = paramExtendedPKIXParameters.isUseDeltasEnabled();
        X509CRL localX509CRL2 = null;
        if (bool) {
          localX509CRL2 = processCRLH(CertPathValidatorUtilities.getDeltaCRLs(localDate, paramExtendedPKIXParameters, localX509CRL1), localPublicKey);
        }
        if ((paramExtendedPKIXParameters.getValidityModel() != 1) && (paramX509Certificate1.getNotAfter().getTime() < localX509CRL1.getThisUpdate().getTime())) {
          throw new AnnotatedException("No valid CRL for current time found.");
        }
        processCRLB1(paramDistributionPoint, paramX509Certificate1, localX509CRL1);
        processCRLB2(paramDistributionPoint, paramX509Certificate1, localX509CRL1);
        processCRLC(localX509CRL2, localX509CRL1, paramExtendedPKIXParameters);
        processCRLI(paramDate, localX509CRL2, paramX509Certificate1, paramCertStatus, paramExtendedPKIXParameters);
        processCRLJ(paramDate, localX509CRL1, paramX509Certificate1, paramCertStatus);
        if (paramCertStatus.getCertStatus() == 8) {
          paramCertStatus.setCertStatus(11);
        }
        paramReasonsMask.addReasons(localReasonsMask);
        Set localSet2 = localX509CRL1.getCriticalExtensionOIDs();
        if (localSet2 != null)
        {
          HashSet localHashSet1 = new HashSet(localSet2);
          localHashSet1.remove(X509Extensions.IssuingDistributionPoint.getId());
          localHashSet1.remove(X509Extensions.DeltaCRLIndicator.getId());
          if (localHashSet1.isEmpty()) {
            break label438;
          }
          throw new AnnotatedException("CRL contains unsupported critical extensions.");
        }
        if (localX509CRL2 != null)
        {
          Set localSet3 = localX509CRL2.getCriticalExtensionOIDs();
          if (localSet3 != null)
          {
            HashSet localHashSet2 = new HashSet(localSet3);
            localHashSet2.remove(X509Extensions.IssuingDistributionPoint.getId());
            localHashSet2.remove(X509Extensions.DeltaCRLIndicator.getId());
            if (!localHashSet2.isEmpty()) {
              throw new AnnotatedException("Delta CRL contains unsupported critical extension.");
            }
          }
        }
        i = 1;
      }
      catch (AnnotatedException localAnnotatedException) {}
      if (i == 0) {
        throw localObject;
      }
      return;
      break;
    }
  }
  
  protected static void checkCRLs(ExtendedPKIXParameters paramExtendedPKIXParameters, X509Certificate paramX509Certificate1, Date paramDate, X509Certificate paramX509Certificate2, PublicKey paramPublicKey, List paramList)
    throws AnnotatedException
  {
    CertStatus localCertStatus;
    ReasonsMask localReasonsMask;
    int i;
    for (;;)
    {
      try
      {
        localCRLDistPoint = CRLDistPoint.getInstance(CertPathValidatorUtilities.getExtensionValue(paramX509Certificate1, CRL_DISTRIBUTION_POINTS));
      }
      catch (Exception localException1)
      {
        CRLDistPoint localCRLDistPoint;
        Object localObject;
        DistributionPoint[] arrayOfDistributionPoint;
        int j;
        ExtendedPKIXParameters localExtendedPKIXParameters;
        throw new AnnotatedException("CRL distribution point extension could not be read.", localException1);
      }
      try
      {
        CertPathValidatorUtilities.addAdditionalStoresFromCRLDistributionPoint(localCRLDistPoint, paramExtendedPKIXParameters);
        localCertStatus = new CertStatus();
        localReasonsMask = new ReasonsMask();
        localObject = null;
        i = 0;
        if (localCRLDistPoint == null) {
          break label183;
        }
      }
      catch (AnnotatedException localAnnotatedException1)
      {
        throw new AnnotatedException("No additional CRL locations could be decoded from CRL distribution point extension.", localAnnotatedException1);
      }
      try
      {
        arrayOfDistributionPoint = localCRLDistPoint.getDistributionPoints();
        localObject = null;
        i = 0;
        if (arrayOfDistributionPoint == null) {
          break label183;
        }
        j = 0;
        if ((j >= arrayOfDistributionPoint.length) || (localCertStatus.getCertStatus() != 11) || (localReasonsMask.isAllReasons())) {
          break label183;
        }
        localExtendedPKIXParameters = (ExtendedPKIXParameters)paramExtendedPKIXParameters.clone();
      }
      catch (Exception localException3)
      {
        throw new AnnotatedException("Distribution points could not be read.", localException3);
      }
      try
      {
        checkCRL(arrayOfDistributionPoint[j], localExtendedPKIXParameters, paramX509Certificate1, paramDate, paramX509Certificate2, paramPublicKey, localCertStatus, localReasonsMask, paramList);
        i = 1;
        j++;
      }
      catch (AnnotatedException localAnnotatedException2) {}
    }
    label183:
    if ((localCertStatus.getCertStatus() == 11) && (!localReasonsMask.isAllReasons())) {}
    try
    {
      ASN1Primitive localASN1Primitive = new ASN1InputStream(CertPathValidatorUtilities.getEncodedIssuerPrincipal(paramX509Certificate1).getEncoded()).readObject();
      checkCRL(new DistributionPoint(new DistributionPointName(0, new GeneralNames(new GeneralName(4, localASN1Primitive))), null, null), (ExtendedPKIXParameters)paramExtendedPKIXParameters.clone(), paramX509Certificate1, paramDate, paramX509Certificate2, paramPublicKey, localCertStatus, localReasonsMask, paramList);
      i = 1;
    }
    catch (Exception localException2)
    {
      throw new AnnotatedException("Issuer from certificate for CRL could not be reencoded.", localException2);
    }
    catch (AnnotatedException localAnnotatedException3)
    {
      label278:
      break label278;
      throw new AnnotatedException("No valid CRL found.", localAnnotatedException3);
    }
    if (i == 0) {
      if ((localAnnotatedException2 instanceof AnnotatedException)) {
        throw localAnnotatedException2;
      }
    }
    if (localCertStatus.getCertStatus() != 11)
    {
      String str = "Certificate revocation after " + localCertStatus.getRevocationDate();
      throw new AnnotatedException(str + ", reason: " + crlReasons[localCertStatus.getCertStatus()]);
    }
    if ((!localReasonsMask.isAllReasons()) && (localCertStatus.getCertStatus() == 11)) {
      localCertStatus.setCertStatus(12);
    }
    if (localCertStatus.getCertStatus() == 12) {
      throw new AnnotatedException("Certificate status could not be determined.");
    }
  }
  
  protected static PKIXPolicyNode prepareCertB(CertPath paramCertPath, int paramInt1, List[] paramArrayOfList, PKIXPolicyNode paramPKIXPolicyNode, int paramInt2)
    throws CertPathValidatorException
  {
    List localList1 = paramCertPath.getCertificates();
    X509Certificate localX509Certificate = (X509Certificate)localList1.get(paramInt1);
    int i = localList1.size() - paramInt1;
    PKIXPolicyNode localPKIXPolicyNode1;
    HashMap localHashMap;
    HashSet localHashSet1;
    for (;;)
    {
      try
      {
        ASN1Sequence localASN1Sequence1 = DERSequence.getInstance(CertPathValidatorUtilities.getExtensionValue(localX509Certificate, POLICY_MAPPINGS));
        localPKIXPolicyNode1 = paramPKIXPolicyNode;
        if (localASN1Sequence1 == null) {
          break label784;
        }
        localHashMap = new HashMap();
        localHashSet1 = new HashSet();
        int j = 0;
        int k = localASN1Sequence1.size();
        if (j >= k) {
          break;
        }
        ASN1Sequence localASN1Sequence3 = (ASN1Sequence)localASN1Sequence1.getObjectAt(j);
        String str2 = ((DERObjectIdentifier)localASN1Sequence3.getObjectAt(0)).getId();
        String str3 = ((DERObjectIdentifier)localASN1Sequence3.getObjectAt(1)).getId();
        if (!localHashMap.containsKey(str2))
        {
          HashSet localHashSet2 = new HashSet();
          localHashSet2.add(str3);
          localHashMap.put(str2, localHashSet2);
          localHashSet1.add(str2);
          j++;
        }
        else
        {
          ((Set)localHashMap.get(str2)).add(str3);
        }
      }
      catch (AnnotatedException localAnnotatedException1)
      {
        throw new ExtCertPathValidatorException("Policy mappings extension could not be decoded.", localAnnotatedException1, paramCertPath, paramInt1);
      }
    }
    Iterator localIterator1 = localHashSet1.iterator();
    break label644;
    label234:
    String str1;
    do
    {
      for (;;)
      {
        if (!localIterator1.hasNext()) {
          break label784;
        }
        str1 = (String)localIterator1.next();
        if (paramInt2 > 0)
        {
          Iterator localIterator3 = paramArrayOfList[i].iterator();
          PKIXPolicyNode localPKIXPolicyNode7;
          do
          {
            boolean bool1 = localIterator3.hasNext();
            i2 = 0;
            if (!bool1) {
              break;
            }
            localPKIXPolicyNode7 = (PKIXPolicyNode)localIterator3.next();
          } while (!localPKIXPolicyNode7.getValidPolicy().equals(str1));
          int i2 = 1;
          localPKIXPolicyNode7.expectedPolicies = ((Set)localHashMap.get(str1));
          if (i2 != 0) {
            continue;
          }
          Iterator localIterator4 = paramArrayOfList[i].iterator();
          if (!localIterator4.hasNext()) {
            continue;
          }
          PKIXPolicyNode localPKIXPolicyNode4 = (PKIXPolicyNode)localIterator4.next();
          if (!"2.5.29.32.0".equals(localPKIXPolicyNode4.getValidPolicy())) {
            break;
          }
          try
          {
            ASN1Sequence localASN1Sequence2 = (ASN1Sequence)CertPathValidatorUtilities.getExtensionValue(localX509Certificate, CERTIFICATE_POLICIES);
            localEnumeration = localASN1Sequence2.getObjects();
            boolean bool2 = localEnumeration.hasMoreElements();
            localObject = null;
            if (!bool2) {}
          }
          catch (AnnotatedException localAnnotatedException2)
          {
            try
            {
              do
              {
                Enumeration localEnumeration;
                localPolicyInformation = PolicyInformation.getInstance(localEnumeration.nextElement());
              } while (!"2.5.29.32.0".equals(localPolicyInformation.getPolicyIdentifier().getId()));
            }
            catch (Exception localException)
            {
              Object localObject;
              PolicyInformation localPolicyInformation;
              Set localSet2;
              Set localSet1;
              boolean bool3;
              PKIXPolicyNode localPKIXPolicyNode5;
              PKIXPolicyNode localPKIXPolicyNode6;
              throw new CertPathValidatorException("Policy information could not be decoded.", localException, paramCertPath, paramInt1);
            }
            try
            {
              localSet2 = CertPathValidatorUtilities.getQualifierSet(localPolicyInformation.getPolicyQualifiers());
              localObject = localSet2;
              localSet1 = localX509Certificate.getCriticalExtensionOIDs();
              bool3 = false;
              if (localSet1 != null) {
                bool3 = localX509Certificate.getCriticalExtensionOIDs().contains(CERTIFICATE_POLICIES);
              }
              localPKIXPolicyNode5 = (PKIXPolicyNode)localPKIXPolicyNode4.getParent();
              if (!"2.5.29.32.0".equals(localPKIXPolicyNode5.getValidPolicy())) {
                continue;
              }
              localPKIXPolicyNode6 = new PKIXPolicyNode(new ArrayList(), i, (Set)localHashMap.get(str1), localPKIXPolicyNode5, localObject, str1, bool3);
              localPKIXPolicyNode5.addChild(localPKIXPolicyNode6);
              paramArrayOfList[i].add(localPKIXPolicyNode6);
            }
            catch (CertPathValidatorException localCertPathValidatorException)
            {
              throw new ExtCertPathValidatorException("Policy qualifier info set could not be decoded.", localCertPathValidatorException, paramCertPath, paramInt1);
            }
            localAnnotatedException2 = localAnnotatedException2;
            throw new ExtCertPathValidatorException("Certificate policies extension could not be decoded.", localAnnotatedException2, paramCertPath, paramInt1);
          }
        }
      }
    } while (paramInt2 > 0);
    Iterator localIterator2 = paramArrayOfList[i].iterator();
    for (;;)
    {
      label644:
      if (!localIterator2.hasNext()) {
        break label234;
      }
      PKIXPolicyNode localPKIXPolicyNode2 = (PKIXPolicyNode)localIterator2.next();
      if (!localPKIXPolicyNode2.getValidPolicy().equals(str1)) {
        break;
      }
      ((PKIXPolicyNode)localPKIXPolicyNode2.getParent()).removeChild(localPKIXPolicyNode2);
      localIterator2.remove();
      for (int m = i - 1; m >= 0; m--)
      {
        List localList2 = paramArrayOfList[m];
        for (int n = 0;; n++)
        {
          int i1 = localList2.size();
          if (n >= i1) {
            break;
          }
          PKIXPolicyNode localPKIXPolicyNode3 = (PKIXPolicyNode)localList2.get(n);
          if (!localPKIXPolicyNode3.hasChildren())
          {
            localPKIXPolicyNode1 = CertPathValidatorUtilities.removePolicyNode(localPKIXPolicyNode1, paramArrayOfList, localPKIXPolicyNode3);
            if (localPKIXPolicyNode1 == null) {
              break;
            }
          }
        }
      }
    }
    label784:
    return localPKIXPolicyNode1;
  }
  
  protected static void prepareNextCertA(CertPath paramCertPath, int paramInt)
    throws CertPathValidatorException
  {
    X509Certificate localX509Certificate = (X509Certificate)paramCertPath.getCertificates().get(paramInt);
    for (;;)
    {
      int i;
      try
      {
        ASN1Sequence localASN1Sequence1 = DERSequence.getInstance(CertPathValidatorUtilities.getExtensionValue(localX509Certificate, POLICY_MAPPINGS));
        if (localASN1Sequence1 == null) {
          break;
        }
        i = 0;
        if (i >= localASN1Sequence1.size()) {
          break;
        }
        ASN1Sequence localASN1Sequence2;
        ASN1ObjectIdentifier localASN1ObjectIdentifier1;
        ASN1ObjectIdentifier localASN1ObjectIdentifier2;
        if (!"2.5.29.32.0".equals(localASN1ObjectIdentifier2.getId())) {
          break label164;
        }
      }
      catch (AnnotatedException localAnnotatedException)
      {
        try
        {
          localASN1Sequence2 = DERSequence.getInstance(localASN1Sequence1.getObjectAt(i));
          localASN1ObjectIdentifier1 = DERObjectIdentifier.getInstance(localASN1Sequence2.getObjectAt(0));
          localASN1ObjectIdentifier2 = DERObjectIdentifier.getInstance(localASN1Sequence2.getObjectAt(1));
          if (!"2.5.29.32.0".equals(localASN1ObjectIdentifier1.getId())) {
            break label137;
          }
          throw new CertPathValidatorException("IssuerDomainPolicy is anyPolicy", null, paramCertPath, paramInt);
        }
        catch (Exception localException)
        {
          throw new ExtCertPathValidatorException("Policy mappings extension contents could not be decoded.", localException, paramCertPath, paramInt);
        }
        localAnnotatedException = localAnnotatedException;
        throw new ExtCertPathValidatorException("Policy mappings extension could not be decoded.", localAnnotatedException, paramCertPath, paramInt);
      }
      label137:
      throw new CertPathValidatorException("SubjectDomainPolicy is anyPolicy,", null, paramCertPath, paramInt);
      label164:
      i++;
    }
  }
  
  /* Error */
  protected static void prepareNextCertG(CertPath paramCertPath, int paramInt, PKIXNameConstraintValidator paramPKIXNameConstraintValidator)
    throws CertPathValidatorException
  {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual 406	java/security/cert/CertPath:getCertificates	()Ljava/util/List;
    //   4: iload_1
    //   5: invokeinterface 412 2 0
    //   10: checkcast 238	java/security/cert/X509Certificate
    //   13: astore_3
    //   14: aload_3
    //   15: getstatic 104	org/spongycastle/jce/provider/RFC3280CertPathUtilities:NAME_CONSTRAINTS	Ljava/lang/String;
    //   18: invokestatic 302	org/spongycastle/jce/provider/CertPathValidatorUtilities:getExtensionValue	(Ljava/security/cert/X509Extension;Ljava/lang/String;)Lorg/spongycastle/asn1/ASN1Primitive;
    //   21: invokestatic 420	org/spongycastle/asn1/DERSequence:getInstance	(Ljava/lang/Object;)Lorg/spongycastle/asn1/ASN1Sequence;
    //   24: astore 5
    //   26: aconst_null
    //   27: astore 6
    //   29: aload 5
    //   31: ifnull +14 -> 45
    //   34: aload 5
    //   36: invokestatic 554	org/spongycastle/asn1/x509/NameConstraints:getInstance	(Ljava/lang/Object;)Lorg/spongycastle/asn1/x509/NameConstraints;
    //   39: astore 7
    //   41: aload 7
    //   43: astore 6
    //   45: aload 6
    //   47: ifnull +118 -> 165
    //   50: aload 6
    //   52: invokevirtual 557	org/spongycastle/asn1/x509/NameConstraints:getPermittedSubtrees	()Lorg/spongycastle/asn1/ASN1Sequence;
    //   55: astore 8
    //   57: aload 8
    //   59: ifnull +9 -> 68
    //   62: aload_2
    //   63: aload 8
    //   65: invokevirtual 563	org/spongycastle/jce/provider/PKIXNameConstraintValidator:intersectPermittedSubtree	(Lorg/spongycastle/asn1/ASN1Sequence;)V
    //   68: aload 6
    //   70: invokevirtual 566	org/spongycastle/asn1/x509/NameConstraints:getExcludedSubtrees	()Lorg/spongycastle/asn1/ASN1Sequence;
    //   73: astore 9
    //   75: aload 9
    //   77: ifnull +88 -> 165
    //   80: aload 9
    //   82: invokevirtual 473	org/spongycastle/asn1/ASN1Sequence:getObjects	()Ljava/util/Enumeration;
    //   85: astore 10
    //   87: aload 10
    //   89: invokeinterface 478 1 0
    //   94: ifeq +71 -> 165
    //   97: aload_2
    //   98: aload 10
    //   100: invokeinterface 481 1 0
    //   105: invokestatic 571	org/spongycastle/asn1/x509/GeneralSubtree:getInstance	(Ljava/lang/Object;)Lorg/spongycastle/asn1/x509/GeneralSubtree;
    //   108: invokevirtual 575	org/spongycastle/jce/provider/PKIXNameConstraintValidator:addExcludedSubtree	(Lorg/spongycastle/asn1/x509/GeneralSubtree;)V
    //   111: goto -24 -> 87
    //   114: astore 11
    //   116: new 448	org/spongycastle/jce/exception/ExtCertPathValidatorException
    //   119: dup
    //   120: ldc_w 577
    //   123: aload 11
    //   125: aload_0
    //   126: iload_1
    //   127: invokespecial 453	org/spongycastle/jce/exception/ExtCertPathValidatorException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;Ljava/security/cert/CertPath;I)V
    //   130: athrow
    //   131: astore 4
    //   133: new 448	org/spongycastle/jce/exception/ExtCertPathValidatorException
    //   136: dup
    //   137: ldc_w 579
    //   140: aload 4
    //   142: aload_0
    //   143: iload_1
    //   144: invokespecial 453	org/spongycastle/jce/exception/ExtCertPathValidatorException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;Ljava/security/cert/CertPath;I)V
    //   147: athrow
    //   148: astore 12
    //   150: new 448	org/spongycastle/jce/exception/ExtCertPathValidatorException
    //   153: dup
    //   154: ldc_w 581
    //   157: aload 12
    //   159: aload_0
    //   160: iload_1
    //   161: invokespecial 453	org/spongycastle/jce/exception/ExtCertPathValidatorException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;Ljava/security/cert/CertPath;I)V
    //   164: athrow
    //   165: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	166	0	paramCertPath	CertPath
    //   0	166	1	paramInt	int
    //   0	166	2	paramPKIXNameConstraintValidator	PKIXNameConstraintValidator
    //   13	2	3	localX509Certificate	X509Certificate
    //   131	10	4	localException1	Exception
    //   24	11	5	localASN1Sequence1	ASN1Sequence
    //   27	42	6	localObject	Object
    //   39	3	7	localNameConstraints	org.spongycastle.asn1.x509.NameConstraints
    //   55	9	8	localASN1Sequence2	ASN1Sequence
    //   73	8	9	localASN1Sequence3	ASN1Sequence
    //   85	14	10	localEnumeration	Enumeration
    //   114	10	11	localException2	Exception
    //   148	10	12	localException3	Exception
    // Exception table:
    //   from	to	target	type
    //   87	111	114	java/lang/Exception
    //   14	26	131	java/lang/Exception
    //   34	41	131	java/lang/Exception
    //   62	68	148	java/lang/Exception
  }
  
  protected static int prepareNextCertH1(CertPath paramCertPath, int paramInt1, int paramInt2)
  {
    if ((!CertPathValidatorUtilities.isSelfIssued((X509Certificate)paramCertPath.getCertificates().get(paramInt1))) && (paramInt2 != 0)) {
      paramInt2--;
    }
    return paramInt2;
  }
  
  protected static int prepareNextCertH2(CertPath paramCertPath, int paramInt1, int paramInt2)
  {
    if ((!CertPathValidatorUtilities.isSelfIssued((X509Certificate)paramCertPath.getCertificates().get(paramInt1))) && (paramInt2 != 0)) {
      paramInt2--;
    }
    return paramInt2;
  }
  
  protected static int prepareNextCertH3(CertPath paramCertPath, int paramInt1, int paramInt2)
  {
    if ((!CertPathValidatorUtilities.isSelfIssued((X509Certificate)paramCertPath.getCertificates().get(paramInt1))) && (paramInt2 != 0)) {
      paramInt2--;
    }
    return paramInt2;
  }
  
  protected static int prepareNextCertI1(CertPath paramCertPath, int paramInt1, int paramInt2)
    throws CertPathValidatorException
  {
    X509Certificate localX509Certificate = (X509Certificate)paramCertPath.getCertificates().get(paramInt1);
    try
    {
      ASN1Sequence localASN1Sequence = DERSequence.getInstance(CertPathValidatorUtilities.getExtensionValue(localX509Certificate, POLICY_CONSTRAINTS));
      if (localASN1Sequence == null) {
        return paramInt2;
      }
      localEnumeration = localASN1Sequence.getObjects();
    }
    catch (Exception localException)
    {
      try
      {
        ASN1TaggedObject localASN1TaggedObject;
        do
        {
          Enumeration localEnumeration;
          localASN1TaggedObject = ASN1TaggedObject.getInstance(localEnumeration.nextElement());
        } while (localASN1TaggedObject.getTagNo() != 0);
        int i = DERInteger.getInstance(localASN1TaggedObject, false).getValue().intValue();
        if (i >= paramInt2) {
          return paramInt2;
        }
        return i;
      }
      catch (IllegalArgumentException localIllegalArgumentException)
      {
        throw new ExtCertPathValidatorException("Policy constraints extension contents cannot be decoded.", localIllegalArgumentException, paramCertPath, paramInt1);
      }
      localException = localException;
      throw new ExtCertPathValidatorException("Policy constraints extension cannot be decoded.", localException, paramCertPath, paramInt1);
    }
    if (localEnumeration.hasMoreElements()) {}
    return paramInt2;
  }
  
  protected static int prepareNextCertI2(CertPath paramCertPath, int paramInt1, int paramInt2)
    throws CertPathValidatorException
  {
    X509Certificate localX509Certificate = (X509Certificate)paramCertPath.getCertificates().get(paramInt1);
    try
    {
      ASN1Sequence localASN1Sequence = DERSequence.getInstance(CertPathValidatorUtilities.getExtensionValue(localX509Certificate, POLICY_CONSTRAINTS));
      if (localASN1Sequence == null) {
        return paramInt2;
      }
      localEnumeration = localASN1Sequence.getObjects();
    }
    catch (Exception localException)
    {
      try
      {
        ASN1TaggedObject localASN1TaggedObject;
        do
        {
          Enumeration localEnumeration;
          localASN1TaggedObject = ASN1TaggedObject.getInstance(localEnumeration.nextElement());
        } while (localASN1TaggedObject.getTagNo() != 1);
        int i = DERInteger.getInstance(localASN1TaggedObject, false).getValue().intValue();
        if (i >= paramInt2) {
          return paramInt2;
        }
        return i;
      }
      catch (IllegalArgumentException localIllegalArgumentException)
      {
        throw new ExtCertPathValidatorException("Policy constraints extension contents cannot be decoded.", localIllegalArgumentException, paramCertPath, paramInt1);
      }
      localException = localException;
      throw new ExtCertPathValidatorException("Policy constraints extension cannot be decoded.", localException, paramCertPath, paramInt1);
    }
    if (localEnumeration.hasMoreElements()) {}
    return paramInt2;
  }
  
  protected static int prepareNextCertJ(CertPath paramCertPath, int paramInt1, int paramInt2)
    throws CertPathValidatorException
  {
    X509Certificate localX509Certificate = (X509Certificate)paramCertPath.getCertificates().get(paramInt1);
    try
    {
      ASN1Integer localASN1Integer = DERInteger.getInstance(CertPathValidatorUtilities.getExtensionValue(localX509Certificate, INHIBIT_ANY_POLICY));
      if (localASN1Integer != null)
      {
        int i = localASN1Integer.getValue().intValue();
        if (i < paramInt2) {
          return i;
        }
      }
    }
    catch (Exception localException)
    {
      throw new ExtCertPathValidatorException("Inhibit any-policy extension cannot be decoded.", localException, paramCertPath, paramInt1);
    }
    return paramInt2;
  }
  
  protected static void prepareNextCertK(CertPath paramCertPath, int paramInt)
    throws CertPathValidatorException
  {
    X509Certificate localX509Certificate = (X509Certificate)paramCertPath.getCertificates().get(paramInt);
    try
    {
      BasicConstraints localBasicConstraints = BasicConstraints.getInstance(CertPathValidatorUtilities.getExtensionValue(localX509Certificate, BASIC_CONSTRAINTS));
      if (localBasicConstraints != null)
      {
        if (localBasicConstraints.isCA()) {
          return;
        }
        throw new CertPathValidatorException("Not a CA certificate");
      }
    }
    catch (Exception localException)
    {
      throw new ExtCertPathValidatorException("Basic constraints extension cannot be decoded.", localException, paramCertPath, paramInt);
    }
    throw new CertPathValidatorException("Intermediate certificate lacks BasicConstraints");
  }
  
  protected static int prepareNextCertL(CertPath paramCertPath, int paramInt1, int paramInt2)
    throws CertPathValidatorException
  {
    if (!CertPathValidatorUtilities.isSelfIssued((X509Certificate)paramCertPath.getCertificates().get(paramInt1)))
    {
      if (paramInt2 <= 0) {
        throw new ExtCertPathValidatorException("Max path length not greater than zero", null, paramCertPath, paramInt1);
      }
      paramInt2--;
    }
    return paramInt2;
  }
  
  protected static int prepareNextCertM(CertPath paramCertPath, int paramInt1, int paramInt2)
    throws CertPathValidatorException
  {
    X509Certificate localX509Certificate = (X509Certificate)paramCertPath.getCertificates().get(paramInt1);
    try
    {
      BasicConstraints localBasicConstraints = BasicConstraints.getInstance(CertPathValidatorUtilities.getExtensionValue(localX509Certificate, BASIC_CONSTRAINTS));
      if (localBasicConstraints != null)
      {
        BigInteger localBigInteger = localBasicConstraints.getPathLenConstraint();
        if (localBigInteger != null)
        {
          int i = localBigInteger.intValue();
          if (i < paramInt2) {
            return i;
          }
        }
      }
    }
    catch (Exception localException)
    {
      throw new ExtCertPathValidatorException("Basic constraints extension cannot be decoded.", localException, paramCertPath, paramInt1);
    }
    return paramInt2;
  }
  
  protected static void prepareNextCertN(CertPath paramCertPath, int paramInt)
    throws CertPathValidatorException
  {
    boolean[] arrayOfBoolean = ((X509Certificate)paramCertPath.getCertificates().get(paramInt)).getKeyUsage();
    if ((arrayOfBoolean != null) && (arrayOfBoolean[5] == 0)) {
      throw new ExtCertPathValidatorException("Issuer certificate keyusage extension is critical and does not permit key signing.", null, paramCertPath, paramInt);
    }
  }
  
  protected static void prepareNextCertO(CertPath paramCertPath, int paramInt, Set paramSet, List paramList)
    throws CertPathValidatorException
  {
    X509Certificate localX509Certificate = (X509Certificate)paramCertPath.getCertificates().get(paramInt);
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext()) {
      try
      {
        ((PKIXCertPathChecker)localIterator.next()).check(localX509Certificate, paramSet);
      }
      catch (CertPathValidatorException localCertPathValidatorException)
      {
        throw new CertPathValidatorException(localCertPathValidatorException.getMessage(), localCertPathValidatorException.getCause(), paramCertPath, paramInt);
      }
    }
    if (!paramSet.isEmpty()) {
      throw new ExtCertPathValidatorException("Certificate has unsupported critical extension.", null, paramCertPath, paramInt);
    }
  }
  
  /* Error */
  protected static Set processCRLA1i(Date paramDate, ExtendedPKIXParameters paramExtendedPKIXParameters, X509Certificate paramX509Certificate, X509CRL paramX509CRL)
    throws AnnotatedException
  {
    // Byte code:
    //   0: new 280	java/util/HashSet
    //   3: dup
    //   4: invokespecial 424	java/util/HashSet:<init>	()V
    //   7: astore 4
    //   9: aload_1
    //   10: invokevirtual 225	org/spongycastle/x509/ExtendedPKIXParameters:isUseDeltasEnabled	()Z
    //   13: ifeq +65 -> 78
    //   16: aload_2
    //   17: getstatic 74	org/spongycastle/jce/provider/RFC3280CertPathUtilities:FRESHEST_CRL	Ljava/lang/String;
    //   20: invokestatic 302	org/spongycastle/jce/provider/CertPathValidatorUtilities:getExtensionValue	(Ljava/security/cert/X509Extension;Ljava/lang/String;)Lorg/spongycastle/asn1/ASN1Primitive;
    //   23: invokestatic 308	org/spongycastle/asn1/x509/CRLDistPoint:getInstance	(Ljava/lang/Object;)Lorg/spongycastle/asn1/x509/CRLDistPoint;
    //   26: astore 6
    //   28: aload 6
    //   30: astore 7
    //   32: aload 7
    //   34: ifnonnull +19 -> 53
    //   37: aload_3
    //   38: getstatic 74	org/spongycastle/jce/provider/RFC3280CertPathUtilities:FRESHEST_CRL	Ljava/lang/String;
    //   41: invokestatic 302	org/spongycastle/jce/provider/CertPathValidatorUtilities:getExtensionValue	(Ljava/security/cert/X509Extension;Ljava/lang/String;)Lorg/spongycastle/asn1/ASN1Primitive;
    //   44: invokestatic 308	org/spongycastle/asn1/x509/CRLDistPoint:getInstance	(Ljava/lang/Object;)Lorg/spongycastle/asn1/x509/CRLDistPoint;
    //   47: astore 12
    //   49: aload 12
    //   51: astore 7
    //   53: aload 7
    //   55: ifnull +23 -> 78
    //   58: aload 7
    //   60: aload_1
    //   61: invokestatic 312	org/spongycastle/jce/provider/CertPathValidatorUtilities:addAdditionalStoresFromCRLDistributionPoint	(Lorg/spongycastle/asn1/x509/CRLDistPoint;Lorg/spongycastle/x509/ExtendedPKIXParameters;)V
    //   64: aload 4
    //   66: aload_0
    //   67: aload_1
    //   68: aload_3
    //   69: invokestatic 229	org/spongycastle/jce/provider/CertPathValidatorUtilities:getDeltaCRLs	(Ljava/util/Date;Lorg/spongycastle/x509/ExtendedPKIXParameters;Ljava/security/cert/X509CRL;)Ljava/util/Set;
    //   72: invokeinterface 681 2 0
    //   77: pop
    //   78: aload 4
    //   80: areturn
    //   81: astore 5
    //   83: new 150	org/spongycastle/jce/provider/AnnotatedException
    //   86: dup
    //   87: ldc_w 683
    //   90: aload 5
    //   92: invokespecial 328	org/spongycastle/jce/provider/AnnotatedException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   95: athrow
    //   96: astore 11
    //   98: new 150	org/spongycastle/jce/provider/AnnotatedException
    //   101: dup
    //   102: ldc_w 685
    //   105: aload 11
    //   107: invokespecial 328	org/spongycastle/jce/provider/AnnotatedException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   110: athrow
    //   111: astore 8
    //   113: new 150	org/spongycastle/jce/provider/AnnotatedException
    //   116: dup
    //   117: ldc_w 687
    //   120: aload 8
    //   122: invokespecial 328	org/spongycastle/jce/provider/AnnotatedException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   125: athrow
    //   126: astore 9
    //   128: new 150	org/spongycastle/jce/provider/AnnotatedException
    //   131: dup
    //   132: ldc_w 689
    //   135: aload 9
    //   137: invokespecial 328	org/spongycastle/jce/provider/AnnotatedException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   140: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	141	0	paramDate	Date
    //   0	141	1	paramExtendedPKIXParameters	ExtendedPKIXParameters
    //   0	141	2	paramX509Certificate	X509Certificate
    //   0	141	3	paramX509CRL	X509CRL
    //   7	72	4	localHashSet	HashSet
    //   81	10	5	localAnnotatedException1	AnnotatedException
    //   26	3	6	localCRLDistPoint1	CRLDistPoint
    //   30	29	7	localObject	Object
    //   111	10	8	localAnnotatedException2	AnnotatedException
    //   126	10	9	localAnnotatedException3	AnnotatedException
    //   96	10	11	localAnnotatedException4	AnnotatedException
    //   47	3	12	localCRLDistPoint2	CRLDistPoint
    // Exception table:
    //   from	to	target	type
    //   16	28	81	org/spongycastle/jce/provider/AnnotatedException
    //   37	49	96	org/spongycastle/jce/provider/AnnotatedException
    //   58	64	111	org/spongycastle/jce/provider/AnnotatedException
    //   64	78	126	org/spongycastle/jce/provider/AnnotatedException
  }
  
  /* Error */
  protected static Set[] processCRLA1ii(Date paramDate, ExtendedPKIXParameters paramExtendedPKIXParameters, X509Certificate paramX509Certificate, X509CRL paramX509CRL)
    throws AnnotatedException
  {
    // Byte code:
    //   0: new 280	java/util/HashSet
    //   3: dup
    //   4: invokespecial 424	java/util/HashSet:<init>	()V
    //   7: astore 4
    //   9: new 695	org/spongycastle/x509/X509CRLStoreSelector
    //   12: dup
    //   13: invokespecial 696	org/spongycastle/x509/X509CRLStoreSelector:<init>	()V
    //   16: astore 5
    //   18: aload 5
    //   20: aload_2
    //   21: invokevirtual 700	org/spongycastle/x509/X509CRLStoreSelector:setCertificateChecking	(Ljava/security/cert/X509Certificate;)V
    //   24: aload 5
    //   26: aload_3
    //   27: invokevirtual 704	java/security/cert/X509CRL:getIssuerX500Principal	()Ljavax/security/auth/x500/X500Principal;
    //   30: invokevirtual 344	javax/security/auth/x500/X500Principal:getEncoded	()[B
    //   33: invokevirtual 707	org/spongycastle/x509/X509CRLStoreSelector:addIssuerName	([B)V
    //   36: aload 5
    //   38: iconst_1
    //   39: invokevirtual 711	org/spongycastle/x509/X509CRLStoreSelector:setCompleteCRLEnabled	(Z)V
    //   42: getstatic 40	org/spongycastle/jce/provider/RFC3280CertPathUtilities:CRL_UTIL	Lorg/spongycastle/jce/provider/PKIXCRLUtil;
    //   45: aload 5
    //   47: aload_1
    //   48: aload_0
    //   49: invokevirtual 715	org/spongycastle/jce/provider/PKIXCRLUtil:findCRLs	(Lorg/spongycastle/x509/X509CRLStoreSelector;Lorg/spongycastle/x509/ExtendedPKIXParameters;Ljava/util/Date;)Ljava/util/Set;
    //   52: astore 7
    //   54: aload_1
    //   55: invokevirtual 225	org/spongycastle/x509/ExtendedPKIXParameters:isUseDeltasEnabled	()Z
    //   58: ifeq +17 -> 75
    //   61: aload 4
    //   63: aload_0
    //   64: aload_1
    //   65: aload_3
    //   66: invokestatic 229	org/spongycastle/jce/provider/CertPathValidatorUtilities:getDeltaCRLs	(Ljava/util/Date;Lorg/spongycastle/x509/ExtendedPKIXParameters;Ljava/security/cert/X509CRL;)Ljava/util/Set;
    //   69: invokeinterface 681 2 0
    //   74: pop
    //   75: iconst_2
    //   76: anewarray 177	java/util/Set
    //   79: dup
    //   80: iconst_0
    //   81: aload 7
    //   83: aastore
    //   84: dup
    //   85: iconst_1
    //   86: aload 4
    //   88: aastore
    //   89: areturn
    //   90: astore 6
    //   92: new 150	org/spongycastle/jce/provider/AnnotatedException
    //   95: dup
    //   96: new 375	java/lang/StringBuilder
    //   99: dup
    //   100: ldc_w 717
    //   103: invokespecial 378	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   106: aload 6
    //   108: invokevirtual 385	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   111: invokevirtual 388	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   114: aload 6
    //   116: invokespecial 328	org/spongycastle/jce/provider/AnnotatedException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   119: athrow
    //   120: astore 8
    //   122: new 150	org/spongycastle/jce/provider/AnnotatedException
    //   125: dup
    //   126: ldc_w 689
    //   129: aload 8
    //   131: invokespecial 328	org/spongycastle/jce/provider/AnnotatedException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   134: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	135	0	paramDate	Date
    //   0	135	1	paramExtendedPKIXParameters	ExtendedPKIXParameters
    //   0	135	2	paramX509Certificate	X509Certificate
    //   0	135	3	paramX509CRL	X509CRL
    //   7	80	4	localHashSet	HashSet
    //   16	30	5	localX509CRLStoreSelector	org.spongycastle.x509.X509CRLStoreSelector
    //   90	25	6	localIOException	IOException
    //   52	30	7	localSet	Set
    //   120	10	8	localAnnotatedException	AnnotatedException
    // Exception table:
    //   from	to	target	type
    //   24	36	90	java/io/IOException
    //   61	75	120	org/spongycastle/jce/provider/AnnotatedException
  }
  
  protected static void processCRLB1(DistributionPoint paramDistributionPoint, Object paramObject, X509CRL paramX509CRL)
    throws AnnotatedException
  {
    ASN1Primitive localASN1Primitive = CertPathValidatorUtilities.getExtensionValue(paramX509CRL, ISSUING_DISTRIBUTION_POINT);
    int i = 0;
    if (localASN1Primitive != null)
    {
      boolean bool3 = IssuingDistributionPoint.getInstance(localASN1Primitive).isIndirectCRL();
      i = 0;
      if (bool3) {
        i = 1;
      }
    }
    byte[] arrayOfByte = CertPathValidatorUtilities.getIssuerPrincipal(paramX509CRL).getEncoded();
    int j = 0;
    if (paramDistributionPoint.getCRLIssuer() != null)
    {
      GeneralName[] arrayOfGeneralName = paramDistributionPoint.getCRLIssuer().getNames();
      int k = 0;
      while (k < arrayOfGeneralName.length)
      {
        if (arrayOfGeneralName[k].getTagNo() == 4) {}
        try
        {
          boolean bool2 = Arrays.areEqual(arrayOfGeneralName[k].getName().toASN1Primitive().getEncoded(), arrayOfByte);
          if (bool2) {
            j = 1;
          }
          k++;
        }
        catch (IOException localIOException)
        {
          throw new AnnotatedException("CRL issuer information from distribution point cannot be decoded.", localIOException);
        }
      }
      if ((j != 0) && (i == 0)) {
        throw new AnnotatedException("Distribution point contains cRLIssuer field but CRL is not indirect.");
      }
      if (j == 0) {
        throw new AnnotatedException("CRL issuer of CRL does not match CRL issuer of distribution point.");
      }
    }
    else
    {
      boolean bool1 = CertPathValidatorUtilities.getIssuerPrincipal(paramX509CRL).equals(CertPathValidatorUtilities.getEncodedIssuerPrincipal(paramObject));
      j = 0;
      if (bool1) {
        j = 1;
      }
    }
    if (j == 0) {
      throw new AnnotatedException("Cannot find matching CRL issuer for certificate.");
    }
  }
  
  protected static void processCRLB2(DistributionPoint paramDistributionPoint, Object paramObject, X509CRL paramX509CRL)
    throws AnnotatedException
  {
    IssuingDistributionPoint localIssuingDistributionPoint;
    DistributionPointName localDistributionPointName1;
    ArrayList localArrayList;
    try
    {
      localIssuingDistributionPoint = IssuingDistributionPoint.getInstance(CertPathValidatorUtilities.getExtensionValue(paramX509CRL, ISSUING_DISTRIBUTION_POINT));
      if (localIssuingDistributionPoint == null) {
        return;
      }
      if (localIssuingDistributionPoint.getDistributionPoint() == null) {
        break label609;
      }
      localDistributionPointName1 = IssuingDistributionPoint.getInstance(localIssuingDistributionPoint).getDistributionPoint();
      localArrayList = new ArrayList();
      if (localDistributionPointName1.getType() == 0)
      {
        GeneralName[] arrayOfGeneralName3 = GeneralNames.getInstance(localDistributionPointName1.getName()).getNames();
        for (int i4 = 0; i4 < arrayOfGeneralName3.length; i4++) {
          localArrayList.add(arrayOfGeneralName3[i4]);
        }
      }
      if (localDistributionPointName1.getType() != 1) {
        break label224;
      }
    }
    catch (Exception localException1)
    {
      throw new AnnotatedException("Issuing distribution point extension could not be decoded.", localException1);
    }
    ASN1EncodableVector localASN1EncodableVector1 = new ASN1EncodableVector();
    try
    {
      Enumeration localEnumeration1 = ASN1Sequence.getInstance(ASN1Sequence.fromByteArray(CertPathValidatorUtilities.getIssuerPrincipal(paramX509CRL).getEncoded())).getObjects();
      while (localEnumeration1.hasMoreElements()) {
        localASN1EncodableVector1.add((ASN1Encodable)localEnumeration1.nextElement());
      }
      localASN1EncodableVector1.add(localDistributionPointName1.getName());
    }
    catch (IOException localIOException1)
    {
      throw new AnnotatedException("Could not read CRL issuer.", localIOException1);
    }
    localArrayList.add(new GeneralName(X509Name.getInstance(new DERSequence(localASN1EncodableVector1))));
    label224:
    if (paramDistributionPoint.getDistributionPoint() != null)
    {
      DistributionPointName localDistributionPointName2 = paramDistributionPoint.getDistributionPoint();
      int m = localDistributionPointName2.getType();
      GeneralName[] arrayOfGeneralName2 = null;
      if (m == 0) {
        arrayOfGeneralName2 = GeneralNames.getInstance(localDistributionPointName2.getName()).getNames();
      }
      if (localDistributionPointName2.getType() == 1)
      {
        if (paramDistributionPoint.getCRLIssuer() != null) {
          arrayOfGeneralName2 = paramDistributionPoint.getCRLIssuer().getNames();
        }
        for (int i3 = 0;; i3++)
        {
          ASN1EncodableVector localASN1EncodableVector2;
          for (;;)
          {
            if (i3 >= arrayOfGeneralName2.length) {
              break label458;
            }
            Enumeration localEnumeration2 = ASN1Sequence.getInstance(arrayOfGeneralName2[i3].getName().toASN1Primitive()).getObjects();
            localASN1EncodableVector2 = new ASN1EncodableVector();
            while (localEnumeration2.hasMoreElements())
            {
              localASN1EncodableVector2.add((ASN1Encodable)localEnumeration2.nextElement());
              continue;
              arrayOfGeneralName2 = new GeneralName[1];
              try
              {
                arrayOfGeneralName2[0] = new GeneralName(new X509Name((ASN1Sequence)ASN1Sequence.fromByteArray(CertPathValidatorUtilities.getEncodedIssuerPrincipal(paramObject).getEncoded())));
              }
              catch (IOException localIOException2)
              {
                throw new AnnotatedException("Could not read certificate issuer.", localIOException2);
              }
            }
          }
          localASN1EncodableVector2.add(localDistributionPointName2.getName());
          arrayOfGeneralName2[i3] = new GeneralName(new X509Name(new DERSequence(localASN1EncodableVector2)));
        }
      }
      int n = 0;
      if (arrayOfGeneralName2 != null) {}
      for (int i1 = 0;; i1++)
      {
        int i2 = arrayOfGeneralName2.length;
        n = 0;
        if (i1 < i2)
        {
          if (localArrayList.contains(arrayOfGeneralName2[i1])) {
            n = 1;
          }
        }
        else
        {
          if (n != 0) {
            break;
          }
          throw new AnnotatedException("No match for certificate CRL issuing distribution point name to cRLIssuer CRL distribution point.");
        }
      }
    }
    label458:
    if (paramDistributionPoint.getCRLIssuer() == null) {
      throw new AnnotatedException("Either the cRLIssuer or the distributionPoint field must be contained in DistributionPoint.");
    }
    GeneralName[] arrayOfGeneralName1 = paramDistributionPoint.getCRLIssuer().getNames();
    for (int i = 0;; i++)
    {
      int j = arrayOfGeneralName1.length;
      int k = 0;
      if (i < j)
      {
        if (localArrayList.contains(arrayOfGeneralName1[i])) {
          k = 1;
        }
      }
      else
      {
        if (k != 0) {
          break;
        }
        throw new AnnotatedException("No match for certificate CRL issuing distribution point name to cRLIssuer CRL distribution point.");
      }
    }
    label609:
    BasicConstraints localBasicConstraints;
    try
    {
      localBasicConstraints = BasicConstraints.getInstance(CertPathValidatorUtilities.getExtensionValue((X509Extension)paramObject, BASIC_CONSTRAINTS));
      if (!(paramObject instanceof X509Certificate)) {
        break label710;
      }
      if ((localIssuingDistributionPoint.onlyContainsUserCerts()) && (localBasicConstraints != null) && (localBasicConstraints.isCA())) {
        throw new AnnotatedException("CA Cert CRL only contains user certificates.");
      }
    }
    catch (Exception localException2)
    {
      throw new AnnotatedException("Basic constraints extension could not be decoded.", localException2);
    }
    if ((localIssuingDistributionPoint.onlyContainsCACerts()) && ((localBasicConstraints == null) || (!localBasicConstraints.isCA()))) {
      throw new AnnotatedException("End CRL only contains CA certificates.");
    }
    label710:
    if (localIssuingDistributionPoint.onlyContainsAttributeCerts()) {
      throw new AnnotatedException("onlyContainsAttributeCerts boolean is asserted.");
    }
  }
  
  protected static void processCRLC(X509CRL paramX509CRL1, X509CRL paramX509CRL2, ExtendedPKIXParameters paramExtendedPKIXParameters)
    throws AnnotatedException
  {
    if (paramX509CRL1 == null) {}
    ASN1Primitive localASN1Primitive1;
    ASN1Primitive localASN1Primitive2;
    label208:
    do
    {
      IssuingDistributionPoint localIssuingDistributionPoint1;
      for (;;)
      {
        return;
        try
        {
          localIssuingDistributionPoint1 = IssuingDistributionPoint.getInstance(CertPathValidatorUtilities.getExtensionValue(paramX509CRL2, ISSUING_DISTRIBUTION_POINT));
          if (paramExtendedPKIXParameters.isUseDeltasEnabled()) {
            if (!paramX509CRL1.getIssuerX500Principal().equals(paramX509CRL2.getIssuerX500Principal())) {
              throw new AnnotatedException("Complete CRL issuer does not match delta CRL issuer.");
            }
          }
        }
        catch (Exception localException1)
        {
          throw new AnnotatedException("Issuing distribution point extension could not be decoded.", localException1);
        }
      }
      for (;;)
      {
        IssuingDistributionPoint localIssuingDistributionPoint2;
        try
        {
          localIssuingDistributionPoint2 = IssuingDistributionPoint.getInstance(CertPathValidatorUtilities.getExtensionValue(paramX509CRL1, ISSUING_DISTRIBUTION_POINT));
          if (localIssuingDistributionPoint1 == null)
          {
            i = 0;
            if (localIssuingDistributionPoint2 == null) {
              i = 1;
            }
            if (i != 0) {
              break;
            }
            throw new AnnotatedException("Issuing distribution point extension from delta CRL and complete CRL does not match.");
          }
        }
        catch (Exception localException2)
        {
          throw new AnnotatedException("Issuing distribution point extension from delta CRL could not be decoded.", localException2);
        }
        boolean bool = localIssuingDistributionPoint1.equals(localIssuingDistributionPoint2);
        int i = 0;
        if (bool) {
          i = 1;
        }
      }
      try
      {
        localASN1Primitive1 = CertPathValidatorUtilities.getExtensionValue(paramX509CRL2, AUTHORITY_KEY_IDENTIFIER);
        if (localASN1Primitive2 != null) {
          continue;
        }
      }
      catch (AnnotatedException localAnnotatedException1)
      {
        try
        {
          localASN1Primitive2 = CertPathValidatorUtilities.getExtensionValue(paramX509CRL1, AUTHORITY_KEY_IDENTIFIER);
          if (localASN1Primitive1 != null) {
            break label208;
          }
          throw new AnnotatedException("CRL authority key identifier is null.");
        }
        catch (AnnotatedException localAnnotatedException2)
        {
          throw new AnnotatedException("Authority key identifier extension could not be extracted from delta CRL.", localAnnotatedException2);
        }
        localAnnotatedException1 = localAnnotatedException1;
        throw new AnnotatedException("Authority key identifier extension could not be extracted from complete CRL.", localAnnotatedException1);
      }
      throw new AnnotatedException("Delta CRL authority key identifier is null.");
    } while (localASN1Primitive1.equals(localASN1Primitive2));
    throw new AnnotatedException("Delta CRL authority key identifier does not match complete CRL authority key identifier.");
  }
  
  protected static ReasonsMask processCRLD(X509CRL paramX509CRL, DistributionPoint paramDistributionPoint)
    throws AnnotatedException
  {
    IssuingDistributionPoint localIssuingDistributionPoint;
    try
    {
      localIssuingDistributionPoint = IssuingDistributionPoint.getInstance(CertPathValidatorUtilities.getExtensionValue(paramX509CRL, ISSUING_DISTRIBUTION_POINT));
      if ((localIssuingDistributionPoint != null) && (localIssuingDistributionPoint.getOnlySomeReasons() != null) && (paramDistributionPoint.getReasons() != null)) {
        return new ReasonsMask(paramDistributionPoint.getReasons()).intersect(new ReasonsMask(localIssuingDistributionPoint.getOnlySomeReasons()));
      }
    }
    catch (Exception localException)
    {
      throw new AnnotatedException("Issuing distribution point extension could not be decoded.", localException);
    }
    if (((localIssuingDistributionPoint == null) || (localIssuingDistributionPoint.getOnlySomeReasons() == null)) && (paramDistributionPoint.getReasons() == null)) {
      return ReasonsMask.allReasons;
    }
    ReasonsMask localReasonsMask1;
    if (paramDistributionPoint.getReasons() == null)
    {
      localReasonsMask1 = ReasonsMask.allReasons;
      if (localIssuingDistributionPoint != null) {
        break label135;
      }
    }
    label135:
    for (ReasonsMask localReasonsMask2 = ReasonsMask.allReasons;; localReasonsMask2 = new ReasonsMask(localIssuingDistributionPoint.getOnlySomeReasons()))
    {
      return localReasonsMask1.intersect(localReasonsMask2);
      localReasonsMask1 = new ReasonsMask(paramDistributionPoint.getReasons());
      break;
    }
  }
  
  /* Error */
  protected static Set processCRLF(X509CRL paramX509CRL, Object paramObject, X509Certificate paramX509Certificate, PublicKey paramPublicKey, ExtendedPKIXParameters paramExtendedPKIXParameters, List paramList)
    throws AnnotatedException
  {
    // Byte code:
    //   0: new 871	org/spongycastle/x509/X509CertStoreSelector
    //   3: dup
    //   4: invokespecial 872	org/spongycastle/x509/X509CertStoreSelector:<init>	()V
    //   7: astore 6
    //   9: aload 6
    //   11: aload_0
    //   12: invokestatic 729	org/spongycastle/jce/provider/CertPathValidatorUtilities:getIssuerPrincipal	(Ljava/security/cert/X509CRL;)Ljavax/security/auth/x500/X500Principal;
    //   15: invokevirtual 344	javax/security/auth/x500/X500Principal:getEncoded	()[B
    //   18: invokevirtual 875	org/spongycastle/x509/X509CertStoreSelector:setSubject	([B)V
    //   21: aload 6
    //   23: aload 4
    //   25: invokevirtual 878	org/spongycastle/x509/ExtendedPKIXParameters:getStores	()Ljava/util/List;
    //   28: invokestatic 882	org/spongycastle/jce/provider/CertPathValidatorUtilities:findCertificates	(Lorg/spongycastle/x509/X509CertStoreSelector;Ljava/util/List;)Ljava/util/Collection;
    //   31: astore 11
    //   33: aload 11
    //   35: aload 6
    //   37: aload 4
    //   39: invokevirtual 885	org/spongycastle/x509/ExtendedPKIXParameters:getAdditionalStores	()Ljava/util/List;
    //   42: invokestatic 882	org/spongycastle/jce/provider/CertPathValidatorUtilities:findCertificates	(Lorg/spongycastle/x509/X509CertStoreSelector;Ljava/util/List;)Ljava/util/Collection;
    //   45: invokeinterface 888 2 0
    //   50: pop
    //   51: aload 11
    //   53: aload 6
    //   55: aload 4
    //   57: invokevirtual 891	org/spongycastle/x509/ExtendedPKIXParameters:getCertStores	()Ljava/util/List;
    //   60: invokestatic 882	org/spongycastle/jce/provider/CertPathValidatorUtilities:findCertificates	(Lorg/spongycastle/x509/X509CertStoreSelector;Ljava/util/List;)Ljava/util/Collection;
    //   63: invokeinterface 888 2 0
    //   68: pop
    //   69: aload 11
    //   71: aload_2
    //   72: invokeinterface 892 2 0
    //   77: pop
    //   78: aload 11
    //   80: invokeinterface 893 1 0
    //   85: astore 15
    //   87: new 508	java/util/ArrayList
    //   90: dup
    //   91: invokespecial 509	java/util/ArrayList:<init>	()V
    //   94: astore 16
    //   96: new 508	java/util/ArrayList
    //   99: dup
    //   100: invokespecial 509	java/util/ArrayList:<init>	()V
    //   103: astore 17
    //   105: aload 15
    //   107: invokeinterface 187 1 0
    //   112: ifeq +262 -> 374
    //   115: aload 15
    //   117: invokeinterface 202 1 0
    //   122: checkcast 238	java/security/cert/X509Certificate
    //   125: astore 23
    //   127: aload 23
    //   129: aload_2
    //   130: invokevirtual 894	java/security/cert/X509Certificate:equals	(Ljava/lang/Object;)Z
    //   133: ifeq +63 -> 196
    //   136: aload 16
    //   138: aload 23
    //   140: invokeinterface 517 2 0
    //   145: pop
    //   146: aload 17
    //   148: aload_3
    //   149: invokeinterface 517 2 0
    //   154: pop
    //   155: goto -50 -> 105
    //   158: astore 7
    //   160: new 150	org/spongycastle/jce/provider/AnnotatedException
    //   163: dup
    //   164: ldc_w 896
    //   167: aload 7
    //   169: invokespecial 328	org/spongycastle/jce/provider/AnnotatedException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   172: astore 8
    //   174: aload 8
    //   176: athrow
    //   177: astore 9
    //   179: new 150	org/spongycastle/jce/provider/AnnotatedException
    //   182: dup
    //   183: ldc_w 898
    //   186: aload 9
    //   188: invokespecial 328	org/spongycastle/jce/provider/AnnotatedException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   191: astore 10
    //   193: aload 10
    //   195: athrow
    //   196: ldc_w 900
    //   199: getstatic 905	org/spongycastle/jce/provider/BouncyCastleProvider:PROVIDER_NAME	Ljava/lang/String;
    //   202: invokestatic 910	java/security/cert/CertPathBuilder:getInstance	(Ljava/lang/String;Ljava/lang/String;)Ljava/security/cert/CertPathBuilder;
    //   205: astore 29
    //   207: new 871	org/spongycastle/x509/X509CertStoreSelector
    //   210: dup
    //   211: invokespecial 872	org/spongycastle/x509/X509CertStoreSelector:<init>	()V
    //   214: astore 30
    //   216: aload 30
    //   218: aload 23
    //   220: invokevirtual 913	org/spongycastle/x509/X509CertStoreSelector:setCertificate	(Ljava/security/cert/X509Certificate;)V
    //   223: aload 4
    //   225: invokevirtual 321	org/spongycastle/x509/ExtendedPKIXParameters:clone	()Ljava/lang/Object;
    //   228: checkcast 222	org/spongycastle/x509/ExtendedPKIXParameters
    //   231: astore 31
    //   233: aload 31
    //   235: aload 30
    //   237: invokevirtual 917	org/spongycastle/x509/ExtendedPKIXParameters:setTargetCertConstraints	(Ljava/security/cert/CertSelector;)V
    //   240: aload 31
    //   242: invokestatic 922	org/spongycastle/x509/ExtendedPKIXBuilderParameters:getInstance	(Ljava/security/cert/PKIXParameters;)Lorg/spongycastle/x509/ExtendedPKIXParameters;
    //   245: checkcast 919	org/spongycastle/x509/ExtendedPKIXBuilderParameters
    //   248: astore 32
    //   250: aload 5
    //   252: aload 23
    //   254: invokeinterface 808 2 0
    //   259: ifeq +53 -> 312
    //   262: aload 32
    //   264: iconst_0
    //   265: invokevirtual 925	org/spongycastle/x509/ExtendedPKIXBuilderParameters:setRevocationEnabled	(Z)V
    //   268: aload 29
    //   270: aload 32
    //   272: invokevirtual 929	java/security/cert/CertPathBuilder:build	(Ljava/security/cert/CertPathParameters;)Ljava/security/cert/CertPathBuilderResult;
    //   275: invokeinterface 935 1 0
    //   280: invokevirtual 406	java/security/cert/CertPath:getCertificates	()Ljava/util/List;
    //   283: astore 33
    //   285: aload 16
    //   287: aload 23
    //   289: invokeinterface 517 2 0
    //   294: pop
    //   295: aload 17
    //   297: aload 33
    //   299: iconst_0
    //   300: invokestatic 939	org/spongycastle/jce/provider/CertPathValidatorUtilities:getNextWorkingKey	(Ljava/util/List;I)Ljava/security/PublicKey;
    //   303: invokeinterface 517 2 0
    //   308: pop
    //   309: goto -204 -> 105
    //   312: aload 32
    //   314: iconst_1
    //   315: invokevirtual 925	org/spongycastle/x509/ExtendedPKIXBuilderParameters:setRevocationEnabled	(Z)V
    //   318: goto -50 -> 268
    //   321: astore 24
    //   323: new 150	org/spongycastle/jce/provider/AnnotatedException
    //   326: dup
    //   327: ldc_w 941
    //   330: aload 24
    //   332: invokespecial 328	org/spongycastle/jce/provider/AnnotatedException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   335: astore 25
    //   337: aload 25
    //   339: athrow
    //   340: astore 27
    //   342: new 150	org/spongycastle/jce/provider/AnnotatedException
    //   345: dup
    //   346: ldc_w 943
    //   349: aload 27
    //   351: invokespecial 328	org/spongycastle/jce/provider/AnnotatedException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   354: astore 28
    //   356: aload 28
    //   358: athrow
    //   359: astore 26
    //   361: new 945	java/lang/RuntimeException
    //   364: dup
    //   365: aload 26
    //   367: invokevirtual 946	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   370: invokespecial 947	java/lang/RuntimeException:<init>	(Ljava/lang/String;)V
    //   373: athrow
    //   374: new 280	java/util/HashSet
    //   377: dup
    //   378: invokespecial 424	java/util/HashSet:<init>	()V
    //   381: astore 18
    //   383: aconst_null
    //   384: astore 19
    //   386: iconst_0
    //   387: istore 20
    //   389: iload 20
    //   391: aload 16
    //   393: invokeinterface 415 1 0
    //   398: if_icmpge +79 -> 477
    //   401: aload 16
    //   403: iload 20
    //   405: invokeinterface 412 2 0
    //   410: checkcast 238	java/security/cert/X509Certificate
    //   413: invokevirtual 656	java/security/cert/X509Certificate:getKeyUsage	()[Z
    //   416: astore 21
    //   418: aload 21
    //   420: ifnull +37 -> 457
    //   423: aload 21
    //   425: arraylength
    //   426: bipush 7
    //   428: if_icmplt +11 -> 439
    //   431: aload 21
    //   433: bipush 6
    //   435: baload
    //   436: ifne +21 -> 457
    //   439: new 150	org/spongycastle/jce/provider/AnnotatedException
    //   442: dup
    //   443: ldc_w 949
    //   446: invokespecial 169	org/spongycastle/jce/provider/AnnotatedException:<init>	(Ljava/lang/String;)V
    //   449: astore 19
    //   451: iinc 20 1
    //   454: goto -65 -> 389
    //   457: aload 18
    //   459: aload 17
    //   461: iload 20
    //   463: invokeinterface 412 2 0
    //   468: invokeinterface 442 2 0
    //   473: pop
    //   474: goto -23 -> 451
    //   477: aload 18
    //   479: invokeinterface 290 1 0
    //   484: ifeq +19 -> 503
    //   487: aload 19
    //   489: ifnonnull +14 -> 503
    //   492: new 150	org/spongycastle/jce/provider/AnnotatedException
    //   495: dup
    //   496: ldc_w 951
    //   499: invokespecial 169	org/spongycastle/jce/provider/AnnotatedException:<init>	(Ljava/lang/String;)V
    //   502: athrow
    //   503: aload 18
    //   505: invokeinterface 290 1 0
    //   510: ifeq +11 -> 521
    //   513: aload 19
    //   515: ifnull +6 -> 521
    //   518: aload 19
    //   520: athrow
    //   521: aload 18
    //   523: areturn
    //   524: astore 26
    //   526: goto -165 -> 361
    //   529: astore 27
    //   531: goto -189 -> 342
    //   534: astore 24
    //   536: goto -213 -> 323
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	539	0	paramX509CRL	X509CRL
    //   0	539	1	paramObject	Object
    //   0	539	2	paramX509Certificate	X509Certificate
    //   0	539	3	paramPublicKey	PublicKey
    //   0	539	4	paramExtendedPKIXParameters	ExtendedPKIXParameters
    //   0	539	5	paramList	List
    //   7	47	6	localX509CertStoreSelector1	org.spongycastle.x509.X509CertStoreSelector
    //   158	10	7	localIOException	IOException
    //   172	3	8	localAnnotatedException1	AnnotatedException
    //   177	10	9	localAnnotatedException2	AnnotatedException
    //   191	3	10	localAnnotatedException3	AnnotatedException
    //   31	48	11	localCollection	java.util.Collection
    //   85	31	15	localIterator	Iterator
    //   94	308	16	localArrayList1	ArrayList
    //   103	357	17	localArrayList2	ArrayList
    //   381	141	18	localHashSet	HashSet
    //   384	135	19	localAnnotatedException4	AnnotatedException
    //   387	75	20	i	int
    //   416	16	21	arrayOfBoolean	boolean[]
    //   125	163	23	localX509Certificate	X509Certificate
    //   321	10	24	localCertPathBuilderException1	java.security.cert.CertPathBuilderException
    //   534	1	24	localCertPathBuilderException2	java.security.cert.CertPathBuilderException
    //   335	3	25	localAnnotatedException5	AnnotatedException
    //   359	7	26	localException1	Exception
    //   524	1	26	localException2	Exception
    //   340	10	27	localCertPathValidatorException1	CertPathValidatorException
    //   529	1	27	localCertPathValidatorException2	CertPathValidatorException
    //   354	3	28	localAnnotatedException6	AnnotatedException
    //   205	64	29	localCertPathBuilder	java.security.cert.CertPathBuilder
    //   214	22	30	localX509CertStoreSelector2	org.spongycastle.x509.X509CertStoreSelector
    //   231	10	31	localExtendedPKIXParameters	ExtendedPKIXParameters
    //   248	65	32	localExtendedPKIXBuilderParameters	org.spongycastle.x509.ExtendedPKIXBuilderParameters
    //   283	15	33	localList	List
    // Exception table:
    //   from	to	target	type
    //   9	21	158	java/io/IOException
    //   21	69	177	org/spongycastle/jce/provider/AnnotatedException
    //   216	268	321	java/security/cert/CertPathBuilderException
    //   268	309	321	java/security/cert/CertPathBuilderException
    //   312	318	321	java/security/cert/CertPathBuilderException
    //   196	216	340	java/security/cert/CertPathValidatorException
    //   196	216	359	java/lang/Exception
    //   216	268	524	java/lang/Exception
    //   268	309	524	java/lang/Exception
    //   312	318	524	java/lang/Exception
    //   216	268	529	java/security/cert/CertPathValidatorException
    //   268	309	529	java/security/cert/CertPathValidatorException
    //   312	318	529	java/security/cert/CertPathValidatorException
    //   196	216	534	java/security/cert/CertPathBuilderException
  }
  
  protected static PublicKey processCRLG(X509CRL paramX509CRL, Set paramSet)
    throws AnnotatedException
  {
    Object localObject = null;
    Iterator localIterator = paramSet.iterator();
    while (localIterator.hasNext())
    {
      PublicKey localPublicKey = (PublicKey)localIterator.next();
      try
      {
        paramX509CRL.verify(localPublicKey);
        return localPublicKey;
      }
      catch (Exception localException) {}
    }
    throw new AnnotatedException("Cannot verify CRL.", localException);
  }
  
  protected static X509CRL processCRLH(Set paramSet, PublicKey paramPublicKey)
    throws AnnotatedException
  {
    Object localObject = null;
    Iterator localIterator = paramSet.iterator();
    while (localIterator.hasNext())
    {
      X509CRL localX509CRL = (X509CRL)localIterator.next();
      try
      {
        localX509CRL.verify(paramPublicKey);
        return localX509CRL;
      }
      catch (Exception localException) {}
    }
    if (localException != null) {
      throw new AnnotatedException("Cannot verify delta CRL.", localException);
    }
    return null;
  }
  
  protected static void processCRLI(Date paramDate, X509CRL paramX509CRL, Object paramObject, CertStatus paramCertStatus, ExtendedPKIXParameters paramExtendedPKIXParameters)
    throws AnnotatedException
  {
    if ((paramExtendedPKIXParameters.isUseDeltasEnabled()) && (paramX509CRL != null)) {
      CertPathValidatorUtilities.getCertStatus(paramDate, paramX509CRL, paramObject, paramCertStatus);
    }
  }
  
  protected static void processCRLJ(Date paramDate, X509CRL paramX509CRL, Object paramObject, CertStatus paramCertStatus)
    throws AnnotatedException
  {
    if (paramCertStatus.getCertStatus() == 11) {
      CertPathValidatorUtilities.getCertStatus(paramDate, paramX509CRL, paramObject, paramCertStatus);
    }
  }
  
  /* Error */
  protected static void processCertA(CertPath paramCertPath, ExtendedPKIXParameters paramExtendedPKIXParameters, int paramInt, PublicKey paramPublicKey, boolean paramBoolean, X500Principal paramX500Principal, X509Certificate paramX509Certificate)
    throws ExtCertPathValidatorException
  {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual 406	java/security/cert/CertPath:getCertificates	()Ljava/util/List;
    //   4: astore 7
    //   6: aload 7
    //   8: iload_2
    //   9: invokeinterface 412 2 0
    //   14: checkcast 238	java/security/cert/X509Certificate
    //   17: astore 8
    //   19: iload 4
    //   21: ifne +13 -> 34
    //   24: aload 8
    //   26: aload_3
    //   27: aload_1
    //   28: invokevirtual 974	org/spongycastle/x509/ExtendedPKIXParameters:getSigProvider	()Ljava/lang/String;
    //   31: invokestatic 978	org/spongycastle/jce/provider/CertPathValidatorUtilities:verifyX509Certificate	(Ljava/security/cert/X509Certificate;Ljava/security/PublicKey;Ljava/lang/String;)V
    //   34: aload 8
    //   36: aload_1
    //   37: aload_0
    //   38: iload_2
    //   39: invokestatic 982	org/spongycastle/jce/provider/CertPathValidatorUtilities:getValidCertDateFromValidityModel	(Lorg/spongycastle/x509/ExtendedPKIXParameters;Ljava/security/cert/CertPath;I)Ljava/util/Date;
    //   42: invokevirtual 986	java/security/cert/X509Certificate:checkValidity	(Ljava/util/Date;)V
    //   45: aload_1
    //   46: invokevirtual 989	org/spongycastle/x509/ExtendedPKIXParameters:isRevocationEnabled	()Z
    //   49: ifeq +20 -> 69
    //   52: aload_1
    //   53: aload 8
    //   55: aload_1
    //   56: aload_0
    //   57: iload_2
    //   58: invokestatic 982	org/spongycastle/jce/provider/CertPathValidatorUtilities:getValidCertDateFromValidityModel	(Lorg/spongycastle/x509/ExtendedPKIXParameters;Ljava/security/cert/CertPath;I)Ljava/util/Date;
    //   61: aload 6
    //   63: aload_3
    //   64: aload 7
    //   66: invokestatic 991	org/spongycastle/jce/provider/RFC3280CertPathUtilities:checkCRLs	(Lorg/spongycastle/x509/ExtendedPKIXParameters;Ljava/security/cert/X509Certificate;Ljava/util/Date;Ljava/security/cert/X509Certificate;Ljava/security/PublicKey;Ljava/util/List;)V
    //   69: aload 8
    //   71: invokestatic 338	org/spongycastle/jce/provider/CertPathValidatorUtilities:getEncodedIssuerPrincipal	(Ljava/lang/Object;)Ljavax/security/auth/x500/X500Principal;
    //   74: aload 5
    //   76: invokevirtual 763	javax/security/auth/x500/X500Principal:equals	(Ljava/lang/Object;)Z
    //   79: ifne +194 -> 273
    //   82: new 448	org/spongycastle/jce/exception/ExtCertPathValidatorException
    //   85: dup
    //   86: new 375	java/lang/StringBuilder
    //   89: dup
    //   90: ldc_w 993
    //   93: invokespecial 378	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   96: aload 8
    //   98: invokestatic 338	org/spongycastle/jce/provider/CertPathValidatorUtilities:getEncodedIssuerPrincipal	(Ljava/lang/Object;)Ljavax/security/auth/x500/X500Principal;
    //   101: invokevirtual 385	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   104: ldc_w 995
    //   107: invokevirtual 392	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   110: aload 5
    //   112: invokevirtual 385	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   115: ldc_w 997
    //   118: invokevirtual 392	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   121: invokevirtual 388	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   124: aconst_null
    //   125: aload_0
    //   126: iload_2
    //   127: invokespecial 453	org/spongycastle/jce/exception/ExtCertPathValidatorException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;Ljava/security/cert/CertPath;I)V
    //   130: athrow
    //   131: astore 14
    //   133: new 448	org/spongycastle/jce/exception/ExtCertPathValidatorException
    //   136: dup
    //   137: ldc_w 999
    //   140: aload 14
    //   142: aload_0
    //   143: iload_2
    //   144: invokespecial 453	org/spongycastle/jce/exception/ExtCertPathValidatorException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;Ljava/security/cert/CertPath;I)V
    //   147: athrow
    //   148: astore 11
    //   150: new 448	org/spongycastle/jce/exception/ExtCertPathValidatorException
    //   153: dup
    //   154: new 375	java/lang/StringBuilder
    //   157: dup
    //   158: ldc_w 1001
    //   161: invokespecial 378	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   164: aload 11
    //   166: invokevirtual 1002	java/security/cert/CertificateExpiredException:getMessage	()Ljava/lang/String;
    //   169: invokevirtual 392	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   172: invokevirtual 388	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   175: aload 11
    //   177: aload_0
    //   178: iload_2
    //   179: invokespecial 453	org/spongycastle/jce/exception/ExtCertPathValidatorException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;Ljava/security/cert/CertPath;I)V
    //   182: athrow
    //   183: astore 10
    //   185: new 448	org/spongycastle/jce/exception/ExtCertPathValidatorException
    //   188: dup
    //   189: new 375	java/lang/StringBuilder
    //   192: dup
    //   193: ldc_w 1001
    //   196: invokespecial 378	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   199: aload 10
    //   201: invokevirtual 1003	java/security/cert/CertificateNotYetValidException:getMessage	()Ljava/lang/String;
    //   204: invokevirtual 392	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   207: invokevirtual 388	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   210: aload 10
    //   212: aload_0
    //   213: iload_2
    //   214: invokespecial 453	org/spongycastle/jce/exception/ExtCertPathValidatorException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;Ljava/security/cert/CertPath;I)V
    //   217: athrow
    //   218: astore 9
    //   220: new 448	org/spongycastle/jce/exception/ExtCertPathValidatorException
    //   223: dup
    //   224: ldc_w 1005
    //   227: aload 9
    //   229: aload_0
    //   230: iload_2
    //   231: invokespecial 453	org/spongycastle/jce/exception/ExtCertPathValidatorException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;Ljava/security/cert/CertPath;I)V
    //   234: athrow
    //   235: astore 12
    //   237: aload 12
    //   239: astore 13
    //   241: aload 12
    //   243: invokevirtual 1006	org/spongycastle/jce/provider/AnnotatedException:getCause	()Ljava/lang/Throwable;
    //   246: ifnull +10 -> 256
    //   249: aload 12
    //   251: invokevirtual 1006	org/spongycastle/jce/provider/AnnotatedException:getCause	()Ljava/lang/Throwable;
    //   254: astore 13
    //   256: new 448	org/spongycastle/jce/exception/ExtCertPathValidatorException
    //   259: dup
    //   260: aload 12
    //   262: invokevirtual 1007	org/spongycastle/jce/provider/AnnotatedException:getMessage	()Ljava/lang/String;
    //   265: aload 13
    //   267: aload_0
    //   268: iload_2
    //   269: invokespecial 453	org/spongycastle/jce/exception/ExtCertPathValidatorException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;Ljava/security/cert/CertPath;I)V
    //   272: athrow
    //   273: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	274	0	paramCertPath	CertPath
    //   0	274	1	paramExtendedPKIXParameters	ExtendedPKIXParameters
    //   0	274	2	paramInt	int
    //   0	274	3	paramPublicKey	PublicKey
    //   0	274	4	paramBoolean	boolean
    //   0	274	5	paramX500Principal	X500Principal
    //   0	274	6	paramX509Certificate	X509Certificate
    //   4	61	7	localList	List
    //   17	80	8	localX509Certificate	X509Certificate
    //   218	10	9	localAnnotatedException1	AnnotatedException
    //   183	28	10	localCertificateNotYetValidException	java.security.cert.CertificateNotYetValidException
    //   148	28	11	localCertificateExpiredException	java.security.cert.CertificateExpiredException
    //   235	26	12	localAnnotatedException2	AnnotatedException
    //   239	27	13	localObject	Object
    //   131	10	14	localGeneralSecurityException	java.security.GeneralSecurityException
    // Exception table:
    //   from	to	target	type
    //   24	34	131	java/security/GeneralSecurityException
    //   34	45	148	java/security/cert/CertificateExpiredException
    //   34	45	183	java/security/cert/CertificateNotYetValidException
    //   34	45	218	org/spongycastle/jce/provider/AnnotatedException
    //   52	69	235	org/spongycastle/jce/provider/AnnotatedException
  }
  
  /* Error */
  protected static void processCertBC(CertPath paramCertPath, int paramInt, PKIXNameConstraintValidator paramPKIXNameConstraintValidator)
    throws CertPathValidatorException
  {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual 406	java/security/cert/CertPath:getCertificates	()Ljava/util/List;
    //   4: astore_3
    //   5: aload_3
    //   6: iload_1
    //   7: invokeinterface 412 2 0
    //   12: checkcast 238	java/security/cert/X509Certificate
    //   15: astore 4
    //   17: aload_3
    //   18: invokeinterface 415 1 0
    //   23: istore 5
    //   25: iload 5
    //   27: iload_1
    //   28: isub
    //   29: istore 6
    //   31: aload 4
    //   33: invokestatic 587	org/spongycastle/jce/provider/CertPathValidatorUtilities:isSelfIssued	(Ljava/security/cert/X509Certificate;)Z
    //   36: ifeq +10 -> 46
    //   39: iload 6
    //   41: iload 5
    //   43: if_icmplt +297 -> 340
    //   46: new 334	org/spongycastle/asn1/ASN1InputStream
    //   49: dup
    //   50: aload 4
    //   52: invokestatic 1014	org/spongycastle/jce/provider/CertPathValidatorUtilities:getSubjectPrincipal	(Ljava/security/cert/X509Certificate;)Ljavax/security/auth/x500/X500Principal;
    //   55: invokevirtual 344	javax/security/auth/x500/X500Principal:getEncoded	()[B
    //   58: invokespecial 347	org/spongycastle/asn1/ASN1InputStream:<init>	([B)V
    //   61: astore 7
    //   63: aload 7
    //   65: invokevirtual 351	org/spongycastle/asn1/ASN1InputStream:readObject	()Lorg/spongycastle/asn1/ASN1Primitive;
    //   68: invokestatic 420	org/spongycastle/asn1/DERSequence:getInstance	(Ljava/lang/Object;)Lorg/spongycastle/asn1/ASN1Sequence;
    //   71: astore 10
    //   73: aload_2
    //   74: aload 10
    //   76: invokevirtual 1017	org/spongycastle/jce/provider/PKIXNameConstraintValidator:checkPermittedDN	(Lorg/spongycastle/asn1/ASN1Sequence;)V
    //   79: aload_2
    //   80: aload 10
    //   82: invokevirtual 1020	org/spongycastle/jce/provider/PKIXNameConstraintValidator:checkExcludedDN	(Lorg/spongycastle/asn1/ASN1Sequence;)V
    //   85: aload 4
    //   87: getstatic 99	org/spongycastle/jce/provider/RFC3280CertPathUtilities:SUBJECT_ALTERNATIVE_NAME	Ljava/lang/String;
    //   90: invokestatic 302	org/spongycastle/jce/provider/CertPathValidatorUtilities:getExtensionValue	(Ljava/security/cert/X509Extension;Ljava/lang/String;)Lorg/spongycastle/asn1/ASN1Primitive;
    //   93: invokestatic 776	org/spongycastle/asn1/x509/GeneralNames:getInstance	(Ljava/lang/Object;)Lorg/spongycastle/asn1/x509/GeneralNames;
    //   96: astore 15
    //   98: new 796	org/spongycastle/asn1/x509/X509Name
    //   101: dup
    //   102: aload 10
    //   104: invokespecial 805	org/spongycastle/asn1/x509/X509Name:<init>	(Lorg/spongycastle/asn1/ASN1Sequence;)V
    //   107: astore 16
    //   109: aload 16
    //   111: getstatic 1023	org/spongycastle/asn1/x509/X509Name:EmailAddress	Lorg/spongycastle/asn1/ASN1ObjectIdentifier;
    //   114: invokevirtual 1027	org/spongycastle/asn1/x509/X509Name:getValues	(Lorg/spongycastle/asn1/ASN1ObjectIdentifier;)Ljava/util/Vector;
    //   117: invokevirtual 1032	java/util/Vector:elements	()Ljava/util/Enumeration;
    //   120: astore 17
    //   122: aload 17
    //   124: invokeinterface 478 1 0
    //   129: ifeq +122 -> 251
    //   132: new 359	org/spongycastle/asn1/x509/GeneralName
    //   135: dup
    //   136: iconst_1
    //   137: aload 17
    //   139: invokeinterface 481 1 0
    //   144: checkcast 121	java/lang/String
    //   147: invokespecial 1035	org/spongycastle/asn1/x509/GeneralName:<init>	(ILjava/lang/String;)V
    //   150: astore 18
    //   152: aload_2
    //   153: aload 18
    //   155: invokevirtual 1038	org/spongycastle/jce/provider/PKIXNameConstraintValidator:checkPermitted	(Lorg/spongycastle/asn1/x509/GeneralName;)V
    //   158: aload_2
    //   159: aload 18
    //   161: invokevirtual 1041	org/spongycastle/jce/provider/PKIXNameConstraintValidator:checkExcluded	(Lorg/spongycastle/asn1/x509/GeneralName;)V
    //   164: goto -42 -> 122
    //   167: astore 19
    //   169: new 400	java/security/cert/CertPathValidatorException
    //   172: dup
    //   173: ldc_w 1043
    //   176: aload 19
    //   178: aload_0
    //   179: iload_1
    //   180: invokespecial 522	java/security/cert/CertPathValidatorException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;Ljava/security/cert/CertPath;I)V
    //   183: astore 20
    //   185: aload 20
    //   187: athrow
    //   188: astore 8
    //   190: new 400	java/security/cert/CertPathValidatorException
    //   193: dup
    //   194: ldc_w 1045
    //   197: aload 8
    //   199: aload_0
    //   200: iload_1
    //   201: invokespecial 522	java/security/cert/CertPathValidatorException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;Ljava/security/cert/CertPath;I)V
    //   204: astore 9
    //   206: aload 9
    //   208: athrow
    //   209: astore 11
    //   211: new 400	java/security/cert/CertPathValidatorException
    //   214: dup
    //   215: ldc_w 1047
    //   218: aload 11
    //   220: aload_0
    //   221: iload_1
    //   222: invokespecial 522	java/security/cert/CertPathValidatorException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;Ljava/security/cert/CertPath;I)V
    //   225: astore 12
    //   227: aload 12
    //   229: athrow
    //   230: astore 13
    //   232: new 400	java/security/cert/CertPathValidatorException
    //   235: dup
    //   236: ldc_w 1049
    //   239: aload 13
    //   241: aload_0
    //   242: iload_1
    //   243: invokespecial 522	java/security/cert/CertPathValidatorException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;Ljava/security/cert/CertPath;I)V
    //   246: astore 14
    //   248: aload 14
    //   250: athrow
    //   251: aload 15
    //   253: ifnull +87 -> 340
    //   256: aload 15
    //   258: invokevirtual 737	org/spongycastle/asn1/x509/GeneralNames:getNames	()[Lorg/spongycastle/asn1/x509/GeneralName;
    //   261: astore 23
    //   263: iconst_0
    //   264: istore 24
    //   266: iload 24
    //   268: aload 23
    //   270: arraylength
    //   271: if_icmpge +69 -> 340
    //   274: aload_2
    //   275: aload 23
    //   277: iload 24
    //   279: aaload
    //   280: invokevirtual 1038	org/spongycastle/jce/provider/PKIXNameConstraintValidator:checkPermitted	(Lorg/spongycastle/asn1/x509/GeneralName;)V
    //   283: aload_2
    //   284: aload 23
    //   286: iload 24
    //   288: aaload
    //   289: invokevirtual 1041	org/spongycastle/jce/provider/PKIXNameConstraintValidator:checkExcluded	(Lorg/spongycastle/asn1/x509/GeneralName;)V
    //   292: iinc 24 1
    //   295: goto -29 -> 266
    //   298: astore 21
    //   300: new 400	java/security/cert/CertPathValidatorException
    //   303: dup
    //   304: ldc_w 1051
    //   307: aload 21
    //   309: aload_0
    //   310: iload_1
    //   311: invokespecial 522	java/security/cert/CertPathValidatorException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;Ljava/security/cert/CertPath;I)V
    //   314: astore 22
    //   316: aload 22
    //   318: athrow
    //   319: astore 25
    //   321: new 400	java/security/cert/CertPathValidatorException
    //   324: dup
    //   325: ldc_w 1053
    //   328: aload 25
    //   330: aload_0
    //   331: iload_1
    //   332: invokespecial 522	java/security/cert/CertPathValidatorException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;Ljava/security/cert/CertPath;I)V
    //   335: astore 26
    //   337: aload 26
    //   339: athrow
    //   340: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	341	0	paramCertPath	CertPath
    //   0	341	1	paramInt	int
    //   0	341	2	paramPKIXNameConstraintValidator	PKIXNameConstraintValidator
    //   4	14	3	localList	List
    //   15	71	4	localX509Certificate	X509Certificate
    //   23	21	5	i	int
    //   29	15	6	j	int
    //   61	3	7	localASN1InputStream	ASN1InputStream
    //   188	10	8	localException1	Exception
    //   204	3	9	localCertPathValidatorException1	CertPathValidatorException
    //   71	32	10	localASN1Sequence	ASN1Sequence
    //   209	10	11	localPKIXNameConstraintValidatorException1	PKIXNameConstraintValidatorException
    //   225	3	12	localCertPathValidatorException2	CertPathValidatorException
    //   230	10	13	localException2	Exception
    //   246	3	14	localCertPathValidatorException3	CertPathValidatorException
    //   96	161	15	localGeneralNames	GeneralNames
    //   107	3	16	localX509Name	X509Name
    //   120	18	17	localEnumeration	Enumeration
    //   150	10	18	localGeneralName	GeneralName
    //   167	10	19	localPKIXNameConstraintValidatorException2	PKIXNameConstraintValidatorException
    //   183	3	20	localCertPathValidatorException4	CertPathValidatorException
    //   298	10	21	localException3	Exception
    //   314	3	22	localCertPathValidatorException5	CertPathValidatorException
    //   261	24	23	arrayOfGeneralName	GeneralName[]
    //   264	29	24	k	int
    //   319	10	25	localPKIXNameConstraintValidatorException3	PKIXNameConstraintValidatorException
    //   335	3	26	localCertPathValidatorException6	CertPathValidatorException
    // Exception table:
    //   from	to	target	type
    //   152	164	167	org/spongycastle/jce/provider/PKIXNameConstraintValidatorException
    //   63	73	188	java/lang/Exception
    //   73	85	209	org/spongycastle/jce/provider/PKIXNameConstraintValidatorException
    //   85	98	230	java/lang/Exception
    //   256	263	298	java/lang/Exception
    //   274	292	319	org/spongycastle/jce/provider/PKIXNameConstraintValidatorException
  }
  
  protected static PKIXPolicyNode processCertD(CertPath paramCertPath, int paramInt1, Set paramSet, PKIXPolicyNode paramPKIXPolicyNode, List[] paramArrayOfList, int paramInt2)
    throws CertPathValidatorException
  {
    List localList1 = paramCertPath.getCertificates();
    X509Certificate localX509Certificate = (X509Certificate)localList1.get(paramInt1);
    int i = localList1.size();
    int j = i - paramInt1;
    for (;;)
    {
      ASN1Sequence localASN1Sequence;
      HashSet localHashSet1;
      try
      {
        localASN1Sequence = DERSequence.getInstance(CertPathValidatorUtilities.getExtensionValue(localX509Certificate, CERTIFICATE_POLICIES));
        if ((localASN1Sequence == null) || (paramPKIXPolicyNode == null)) {
          break;
        }
        localEnumeration1 = localASN1Sequence.getObjects();
        localHashSet1 = new HashSet();
      }
      catch (AnnotatedException localAnnotatedException)
      {
        try
        {
          Enumeration localEnumeration1;
          PolicyInformation localPolicyInformation2;
          ASN1ObjectIdentifier localASN1ObjectIdentifier;
          Set localSet3 = CertPathValidatorUtilities.getQualifierSet(localPolicyInformation2.getPolicyQualifiers());
          if (CertPathValidatorUtilities.processCertD1i(j, paramArrayOfList, localASN1ObjectIdentifier, localSet3)) {
            continue;
          }
          CertPathValidatorUtilities.processCertD1ii(j, paramArrayOfList, localASN1ObjectIdentifier, localSet3);
        }
        catch (CertPathValidatorException localCertPathValidatorException)
        {
          throw new ExtCertPathValidatorException("Policy qualifier info set could not be build.", localCertPathValidatorException, paramCertPath, paramInt1);
        }
        localAnnotatedException = localAnnotatedException;
        throw new ExtCertPathValidatorException("Could not read certificate policies extension from certificate.", localAnnotatedException, paramCertPath, paramInt1);
      }
      if (localEnumeration1.hasMoreElements())
      {
        localPolicyInformation2 = PolicyInformation.getInstance(localEnumeration1.nextElement());
        localASN1ObjectIdentifier = localPolicyInformation2.getPolicyIdentifier();
        localHashSet1.add(localASN1ObjectIdentifier.getId());
        if ("2.5.29.32.0".equals(localASN1ObjectIdentifier.getId())) {
          break;
        }
      }
      else
      {
        Enumeration localEnumeration2;
        if ((paramSet.isEmpty()) || (paramSet.contains("2.5.29.32.0")))
        {
          paramSet.clear();
          paramSet.addAll(localHashSet1);
          if ((paramInt2 > 0) || ((j < i) && (CertPathValidatorUtilities.isSelfIssued(localX509Certificate)))) {
            localEnumeration2 = localASN1Sequence.getObjects();
          }
        }
        else
        {
          while (localEnumeration2.hasMoreElements())
          {
            PolicyInformation localPolicyInformation1 = PolicyInformation.getInstance(localEnumeration2.nextElement());
            if ("2.5.29.32.0".equals(localPolicyInformation1.getPolicyIdentifier().getId()))
            {
              Set localSet2 = CertPathValidatorUtilities.getQualifierSet(localPolicyInformation1.getPolicyQualifiers());
              List localList4 = paramArrayOfList[(j - 1)];
              label618:
              for (int i3 = 0;; i3++)
              {
                int i4 = localList4.size();
                if (i3 >= i4) {
                  break label624;
                }
                PKIXPolicyNode localPKIXPolicyNode3 = (PKIXPolicyNode)localList4.get(i3);
                Iterator localIterator1 = localPKIXPolicyNode3.getExpectedPolicies().iterator();
                for (;;)
                {
                  label363:
                  if (!localIterator1.hasNext()) {
                    break label618;
                  }
                  Object localObject1 = localIterator1.next();
                  if ((localObject1 instanceof String)) {}
                  int i5;
                  for (String str = (String)localObject1;; str = ((DERObjectIdentifier)localObject1).getId())
                  {
                    i5 = 0;
                    Iterator localIterator2 = localPKIXPolicyNode3.getChildren();
                    while (localIterator2.hasNext()) {
                      if (str.equals(((PKIXPolicyNode)localIterator2.next()).getValidPolicy())) {
                        i5 = 1;
                      }
                    }
                    Iterator localIterator3 = paramSet.iterator();
                    HashSet localHashSet3 = new HashSet();
                    while (localIterator3.hasNext())
                    {
                      Object localObject2 = localIterator3.next();
                      if (localHashSet1.contains(localObject2)) {
                        localHashSet3.add(localObject2);
                      }
                    }
                    paramSet.clear();
                    paramSet.addAll(localHashSet3);
                    break;
                    if (!(localObject1 instanceof DERObjectIdentifier)) {
                      break label363;
                    }
                  }
                  if (i5 == 0)
                  {
                    HashSet localHashSet2 = new HashSet();
                    localHashSet2.add(str);
                    PKIXPolicyNode localPKIXPolicyNode4 = new PKIXPolicyNode(new ArrayList(), j, localHashSet2, localPKIXPolicyNode3, localSet2, str, false);
                    localPKIXPolicyNode3.addChild(localPKIXPolicyNode4);
                    paramArrayOfList[j].add(localPKIXPolicyNode4);
                  }
                }
              }
            }
          }
        }
        label624:
        localPKIXPolicyNode1 = paramPKIXPolicyNode;
        for (int k = j - 1; k >= 0; k--)
        {
          List localList3 = paramArrayOfList[k];
          for (int i1 = 0;; i1++)
          {
            int i2 = localList3.size();
            if (i1 >= i2) {
              break;
            }
            PKIXPolicyNode localPKIXPolicyNode2 = (PKIXPolicyNode)localList3.get(i1);
            if (!localPKIXPolicyNode2.hasChildren())
            {
              localPKIXPolicyNode1 = CertPathValidatorUtilities.removePolicyNode(localPKIXPolicyNode1, paramArrayOfList, localPKIXPolicyNode2);
              if (localPKIXPolicyNode1 == null) {
                break;
              }
            }
          }
        }
        Set localSet1 = localX509Certificate.getCriticalExtensionOIDs();
        if (localSet1 == null) {
          break label790;
        }
        boolean bool = localSet1.contains(CERTIFICATE_POLICIES);
        List localList2 = paramArrayOfList[j];
        for (int m = 0;; m++)
        {
          int n = localList2.size();
          if (m >= n) {
            break;
          }
          ((PKIXPolicyNode)localList2.get(m)).setCritical(bool);
        }
      }
    }
    PKIXPolicyNode localPKIXPolicyNode1 = null;
    label790:
    return localPKIXPolicyNode1;
  }
  
  protected static PKIXPolicyNode processCertE(CertPath paramCertPath, int paramInt, PKIXPolicyNode paramPKIXPolicyNode)
    throws CertPathValidatorException
  {
    X509Certificate localX509Certificate = (X509Certificate)paramCertPath.getCertificates().get(paramInt);
    try
    {
      ASN1Sequence localASN1Sequence = DERSequence.getInstance(CertPathValidatorUtilities.getExtensionValue(localX509Certificate, CERTIFICATE_POLICIES));
      if (localASN1Sequence == null) {
        paramPKIXPolicyNode = null;
      }
      return paramPKIXPolicyNode;
    }
    catch (AnnotatedException localAnnotatedException)
    {
      throw new ExtCertPathValidatorException("Could not read certificate policies extension from certificate.", localAnnotatedException, paramCertPath, paramInt);
    }
  }
  
  protected static void processCertF(CertPath paramCertPath, int paramInt1, PKIXPolicyNode paramPKIXPolicyNode, int paramInt2)
    throws CertPathValidatorException
  {
    if ((paramInt2 <= 0) && (paramPKIXPolicyNode == null)) {
      throw new ExtCertPathValidatorException("No valid policy tree found when one expected.", null, paramCertPath, paramInt1);
    }
  }
  
  protected static int wrapupCertA(int paramInt, X509Certificate paramX509Certificate)
  {
    if ((!CertPathValidatorUtilities.isSelfIssued(paramX509Certificate)) && (paramInt != 0)) {
      paramInt--;
    }
    return paramInt;
  }
  
  protected static int wrapupCertB(CertPath paramCertPath, int paramInt1, int paramInt2)
    throws CertPathValidatorException
  {
    X509Certificate localX509Certificate = (X509Certificate)paramCertPath.getCertificates().get(paramInt1);
    for (;;)
    {
      try
      {
        ASN1Sequence localASN1Sequence = DERSequence.getInstance(CertPathValidatorUtilities.getExtensionValue(localX509Certificate, POLICY_CONSTRAINTS));
        if (localASN1Sequence == null) {
          continue;
        }
        localEnumeration = localASN1Sequence.getObjects();
      }
      catch (AnnotatedException localAnnotatedException)
      {
        try
        {
          Enumeration localEnumeration;
          ASN1TaggedObject localASN1TaggedObject;
          int i = DERInteger.getInstance(localASN1TaggedObject, false).getValue().intValue();
          if (i != 0) {
            continue;
          }
          paramInt2 = 0;
          return paramInt2;
        }
        catch (Exception localException)
        {
          throw new ExtCertPathValidatorException("Policy constraints requireExplicitPolicy field could not be decoded.", localException, paramCertPath, paramInt1);
        }
        localAnnotatedException = localAnnotatedException;
        throw new ExtCertPathValidatorException("Policy constraints could not be decoded.", localAnnotatedException, paramCertPath, paramInt1);
      }
      if (!localEnumeration.hasMoreElements()) {
        continue;
      }
      localASN1TaggedObject = (ASN1TaggedObject)localEnumeration.nextElement();
      switch (localASN1TaggedObject.getTagNo())
      {
      }
    }
  }
  
  protected static void wrapupCertF(CertPath paramCertPath, int paramInt, List paramList, Set paramSet)
    throws CertPathValidatorException
  {
    X509Certificate localX509Certificate = (X509Certificate)paramCertPath.getCertificates().get(paramInt);
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext()) {
      try
      {
        ((PKIXCertPathChecker)localIterator.next()).check(localX509Certificate, paramSet);
      }
      catch (CertPathValidatorException localCertPathValidatorException)
      {
        throw new ExtCertPathValidatorException("Additional certificate path checker failed.", localCertPathValidatorException, paramCertPath, paramInt);
      }
    }
    if (!paramSet.isEmpty()) {
      throw new ExtCertPathValidatorException("Certificate has unsupported critical extension", null, paramCertPath, paramInt);
    }
  }
  
  protected static PKIXPolicyNode wrapupCertG(CertPath paramCertPath, ExtendedPKIXParameters paramExtendedPKIXParameters, Set paramSet1, int paramInt, List[] paramArrayOfList, PKIXPolicyNode paramPKIXPolicyNode, Set paramSet2)
    throws CertPathValidatorException
  {
    int i = paramCertPath.getCertificates().size();
    if (paramPKIXPolicyNode == null)
    {
      if (paramExtendedPKIXParameters.isExplicitPolicyRequired())
      {
        ExtCertPathValidatorException localExtCertPathValidatorException2 = new ExtCertPathValidatorException("Explicit policy requested but none available.", null, paramCertPath, paramInt);
        throw localExtCertPathValidatorException2;
      }
      return null;
    }
    if (CertPathValidatorUtilities.isAnyPolicy(paramSet1))
    {
      if (paramExtendedPKIXParameters.isExplicitPolicyRequired())
      {
        if (paramSet2.isEmpty())
        {
          ExtCertPathValidatorException localExtCertPathValidatorException1 = new ExtCertPathValidatorException("Explicit policy requested but none available.", null, paramCertPath, paramInt);
          throw localExtCertPathValidatorException1;
        }
        HashSet localHashSet2 = new HashSet();
        for (int i1 = 0; i1 < paramArrayOfList.length; i1++)
        {
          List localList4 = paramArrayOfList[i1];
          for (int i4 = 0; i4 < localList4.size(); i4++)
          {
            PKIXPolicyNode localPKIXPolicyNode6 = (PKIXPolicyNode)localList4.get(i4);
            if ("2.5.29.32.0".equals(localPKIXPolicyNode6.getValidPolicy()))
            {
              Iterator localIterator4 = localPKIXPolicyNode6.getChildren();
              while (localIterator4.hasNext()) {
                localHashSet2.add(localIterator4.next());
              }
            }
          }
        }
        Iterator localIterator3 = localHashSet2.iterator();
        while (localIterator3.hasNext()) {
          paramSet2.contains(((PKIXPolicyNode)localIterator3.next()).getValidPolicy());
        }
        if (paramPKIXPolicyNode != null) {
          for (int i2 = i - 1; i2 >= 0; i2--)
          {
            List localList3 = paramArrayOfList[i2];
            for (int i3 = 0; i3 < localList3.size(); i3++)
            {
              PKIXPolicyNode localPKIXPolicyNode5 = (PKIXPolicyNode)localList3.get(i3);
              if (!localPKIXPolicyNode5.hasChildren()) {
                paramPKIXPolicyNode = CertPathValidatorUtilities.removePolicyNode(paramPKIXPolicyNode, paramArrayOfList, localPKIXPolicyNode5);
              }
            }
          }
        }
      }
      return paramPKIXPolicyNode;
    }
    HashSet localHashSet1 = new HashSet();
    for (int j = 0; j < paramArrayOfList.length; j++)
    {
      List localList2 = paramArrayOfList[j];
      for (int n = 0; n < localList2.size(); n++)
      {
        PKIXPolicyNode localPKIXPolicyNode3 = (PKIXPolicyNode)localList2.get(n);
        if ("2.5.29.32.0".equals(localPKIXPolicyNode3.getValidPolicy()))
        {
          Iterator localIterator2 = localPKIXPolicyNode3.getChildren();
          while (localIterator2.hasNext())
          {
            PKIXPolicyNode localPKIXPolicyNode4 = (PKIXPolicyNode)localIterator2.next();
            if (!"2.5.29.32.0".equals(localPKIXPolicyNode4.getValidPolicy())) {
              localHashSet1.add(localPKIXPolicyNode4);
            }
          }
        }
      }
    }
    Iterator localIterator1 = localHashSet1.iterator();
    while (localIterator1.hasNext())
    {
      PKIXPolicyNode localPKIXPolicyNode2 = (PKIXPolicyNode)localIterator1.next();
      if (!paramSet1.contains(localPKIXPolicyNode2.getValidPolicy())) {
        paramPKIXPolicyNode = CertPathValidatorUtilities.removePolicyNode(paramPKIXPolicyNode, paramArrayOfList, localPKIXPolicyNode2);
      }
    }
    if (paramPKIXPolicyNode != null) {
      for (int k = i - 1; k >= 0; k--)
      {
        List localList1 = paramArrayOfList[k];
        for (int m = 0; m < localList1.size(); m++)
        {
          PKIXPolicyNode localPKIXPolicyNode1 = (PKIXPolicyNode)localList1.get(m);
          if (!localPKIXPolicyNode1.hasChildren()) {
            paramPKIXPolicyNode = CertPathValidatorUtilities.removePolicyNode(paramPKIXPolicyNode, paramArrayOfList, localPKIXPolicyNode1);
          }
        }
      }
    }
    return paramPKIXPolicyNode;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jce.provider.RFC3280CertPathUtilities
 * JD-Core Version:    0.7.0.1
 */