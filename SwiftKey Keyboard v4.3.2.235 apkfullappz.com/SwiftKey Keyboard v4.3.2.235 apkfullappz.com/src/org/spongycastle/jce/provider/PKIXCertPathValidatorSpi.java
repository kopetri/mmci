package org.spongycastle.jce.provider;

import java.security.InvalidAlgorithmParameterException;
import java.security.PublicKey;
import java.security.cert.CertPath;
import java.security.cert.CertPathParameters;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertPathValidatorResult;
import java.security.cert.CertPathValidatorSpi;
import java.security.cert.PKIXCertPathChecker;
import java.security.cert.PKIXCertPathValidatorResult;
import java.security.cert.PKIXParameters;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.security.auth.x500.X500Principal;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.jce.exception.ExtCertPathValidatorException;
import org.spongycastle.util.Selector;
import org.spongycastle.x509.ExtendedPKIXParameters;

public class PKIXCertPathValidatorSpi
  extends CertPathValidatorSpi
{
  public CertPathValidatorResult engineValidate(CertPath paramCertPath, CertPathParameters paramCertPathParameters)
    throws CertPathValidatorException, InvalidAlgorithmParameterException
  {
    if (!(paramCertPathParameters instanceof PKIXParameters)) {
      throw new InvalidAlgorithmParameterException("Parameters must be a " + PKIXParameters.class.getName() + " instance.");
    }
    if ((paramCertPathParameters instanceof ExtendedPKIXParameters)) {}
    for (ExtendedPKIXParameters localExtendedPKIXParameters = (ExtendedPKIXParameters)paramCertPathParameters; localExtendedPKIXParameters.getTrustAnchors() == null; localExtendedPKIXParameters = ExtendedPKIXParameters.getInstance((PKIXParameters)paramCertPathParameters)) {
      throw new InvalidAlgorithmParameterException("trustAnchors is null, this is not allowed for certification path validation.");
    }
    List localList1 = paramCertPath.getCertificates();
    int i = localList1.size();
    if (localList1.isEmpty()) {
      throw new CertPathValidatorException("Certification path is empty.", null, paramCertPath, 0);
    }
    Set localSet1 = localExtendedPKIXParameters.getInitialPolicies();
    TrustAnchor localTrustAnchor;
    try
    {
      localTrustAnchor = CertPathValidatorUtilities.findTrustAnchor((X509Certificate)localList1.get(-1 + localList1.size()), localExtendedPKIXParameters.getTrustAnchors(), localExtendedPKIXParameters.getSigProvider());
      if (localTrustAnchor == null) {
        throw new CertPathValidatorException("Trust anchor for certification path not found.", null, paramCertPath, -1);
      }
    }
    catch (AnnotatedException localAnnotatedException)
    {
      throw new CertPathValidatorException(localAnnotatedException.getMessage(), localAnnotatedException, paramCertPath, -1 + localList1.size());
    }
    ArrayList[] arrayOfArrayList = new ArrayList[i + 1];
    for (int j = 0;; j++)
    {
      int k = arrayOfArrayList.length;
      if (j >= k) {
        break;
      }
      arrayOfArrayList[j] = new ArrayList();
    }
    HashSet localHashSet1 = new HashSet();
    localHashSet1.add("2.5.29.32.0");
    PKIXPolicyNode localPKIXPolicyNode1 = new PKIXPolicyNode(new ArrayList(), 0, localHashSet1, null, new HashSet(), "2.5.29.32.0", false);
    arrayOfArrayList[0].add(localPKIXPolicyNode1);
    PKIXNameConstraintValidator localPKIXNameConstraintValidator = new PKIXNameConstraintValidator();
    HashSet localHashSet2 = new HashSet();
    int m;
    int n;
    label342:
    int i1;
    label352:
    Object localObject1;
    if (localExtendedPKIXParameters.isExplicitPolicyRequired())
    {
      m = 0;
      if (!localExtendedPKIXParameters.isAnyPolicyInhibited()) {
        break label457;
      }
      n = 0;
      if (!localExtendedPKIXParameters.isPolicyMappingInhibited()) {
        break label466;
      }
      i1 = 0;
      localObject1 = localTrustAnchor.getTrustedCert();
      if (localObject1 == null) {
        break label475;
      }
    }
    X500Principal localX500Principal;
    Object localObject2;
    int i2;
    for (;;)
    {
      try
      {
        localX500Principal = CertPathValidatorUtilities.getSubjectPrincipal((X509Certificate)localObject1);
        PublicKey localPublicKey3 = ((X509Certificate)localObject1).getPublicKey();
        localObject2 = localPublicKey3;
      }
      catch (IllegalArgumentException localIllegalArgumentException)
      {
        AlgorithmIdentifier localAlgorithmIdentifier1;
        label457:
        label466:
        label475:
        PublicKey localPublicKey1;
        throw new ExtCertPathValidatorException("Subject of trust anchor could not be (re)encoded.", localIllegalArgumentException, paramCertPath, -1);
      }
      try
      {
        localAlgorithmIdentifier1 = CertPathValidatorUtilities.getAlgorithmIdentifier((PublicKey)localObject2);
        localAlgorithmIdentifier1.getObjectId();
        localAlgorithmIdentifier1.getParameters();
        i2 = i;
        if ((localExtendedPKIXParameters.getTargetConstraints() == null) || (localExtendedPKIXParameters.getTargetConstraints().match((X509Certificate)localList1.get(0)))) {
          break label535;
        }
        throw new ExtCertPathValidatorException("Target certificate in certification path does not match targetConstraints.", null, paramCertPath, 0);
      }
      catch (CertPathValidatorException localCertPathValidatorException1)
      {
        throw new ExtCertPathValidatorException("Algorithm identifier of public key of trust anchor could not be read.", localCertPathValidatorException1, paramCertPath, -1);
      }
      m = i + 1;
      break;
      n = i + 1;
      break label342;
      i1 = i + 1;
      break label352;
      localX500Principal = new X500Principal(localTrustAnchor.getCAName());
      localPublicKey1 = localTrustAnchor.getCAPublicKey();
      localObject2 = localPublicKey1;
    }
    label535:
    List localList2 = localExtendedPKIXParameters.getCertPathCheckers();
    Iterator localIterator = localList2.iterator();
    while (localIterator.hasNext()) {
      ((PKIXCertPathChecker)localIterator.next()).init(false);
    }
    X509Certificate localX509Certificate = null;
    int i3 = -1 + localList1.size();
    if (i3 >= 0)
    {
      int i6 = i - i3;
      localX509Certificate = (X509Certificate)localList1.get(i3);
      if (i3 == -1 + localList1.size()) {}
      for (boolean bool = true;; bool = false)
      {
        RFC3280CertPathUtilities.processCertA(paramCertPath, localExtendedPKIXParameters, i3, (PublicKey)localObject2, bool, localX500Principal, (X509Certificate)localObject1);
        RFC3280CertPathUtilities.processCertBC(paramCertPath, i3, localPKIXNameConstraintValidator);
        localPKIXPolicyNode1 = RFC3280CertPathUtilities.processCertE(paramCertPath, i3, RFC3280CertPathUtilities.processCertD(paramCertPath, i3, localHashSet2, localPKIXPolicyNode1, arrayOfArrayList, n));
        RFC3280CertPathUtilities.processCertF(paramCertPath, i3, localPKIXPolicyNode1, m);
        if (i6 == i) {
          break label1038;
        }
        if ((localX509Certificate == null) || (localX509Certificate.getVersion() != 1)) {
          break;
        }
        throw new CertPathValidatorException("Version 1 certificates can't be used as CA ones.", null, paramCertPath, i3);
      }
      RFC3280CertPathUtilities.prepareNextCertA(paramCertPath, i3);
      localPKIXPolicyNode1 = RFC3280CertPathUtilities.prepareCertB(paramCertPath, i3, arrayOfArrayList, localPKIXPolicyNode1, i1);
      RFC3280CertPathUtilities.prepareNextCertG(paramCertPath, i3, localPKIXNameConstraintValidator);
      int i7 = RFC3280CertPathUtilities.prepareNextCertH1(paramCertPath, i3, m);
      int i8 = RFC3280CertPathUtilities.prepareNextCertH2(paramCertPath, i3, i1);
      int i9 = RFC3280CertPathUtilities.prepareNextCertH3(paramCertPath, i3, n);
      m = RFC3280CertPathUtilities.prepareNextCertI1(paramCertPath, i3, i7);
      i1 = RFC3280CertPathUtilities.prepareNextCertI2(paramCertPath, i3, i8);
      n = RFC3280CertPathUtilities.prepareNextCertJ(paramCertPath, i3, i9);
      RFC3280CertPathUtilities.prepareNextCertK(paramCertPath, i3);
      i2 = RFC3280CertPathUtilities.prepareNextCertM(paramCertPath, i3, RFC3280CertPathUtilities.prepareNextCertL(paramCertPath, i3, i2));
      RFC3280CertPathUtilities.prepareNextCertN(paramCertPath, i3);
      Set localSet3 = localX509Certificate.getCriticalExtensionOIDs();
      HashSet localHashSet5;
      if (localSet3 != null)
      {
        localHashSet5 = new HashSet(localSet3);
        localHashSet5.remove(RFC3280CertPathUtilities.KEY_USAGE);
        localHashSet5.remove(RFC3280CertPathUtilities.CERTIFICATE_POLICIES);
        localHashSet5.remove(RFC3280CertPathUtilities.POLICY_MAPPINGS);
        localHashSet5.remove(RFC3280CertPathUtilities.INHIBIT_ANY_POLICY);
        localHashSet5.remove(RFC3280CertPathUtilities.ISSUING_DISTRIBUTION_POINT);
        localHashSet5.remove(RFC3280CertPathUtilities.DELTA_CRL_INDICATOR);
        localHashSet5.remove(RFC3280CertPathUtilities.POLICY_CONSTRAINTS);
        localHashSet5.remove(RFC3280CertPathUtilities.BASIC_CONSTRAINTS);
        localHashSet5.remove(RFC3280CertPathUtilities.SUBJECT_ALTERNATIVE_NAME);
        localHashSet5.remove(RFC3280CertPathUtilities.NAME_CONSTRAINTS);
      }
      for (HashSet localHashSet6 = localHashSet5;; localHashSet6 = new HashSet()) {
        for (;;)
        {
          RFC3280CertPathUtilities.prepareNextCertO(paramCertPath, i3, localHashSet6, localList2);
          localObject1 = localX509Certificate;
          localX500Principal = CertPathValidatorUtilities.getSubjectPrincipal(localX509Certificate);
          try
          {
            PublicKey localPublicKey2 = CertPathValidatorUtilities.getNextWorkingKey(paramCertPath.getCertificates(), i3);
            localObject2 = localPublicKey2;
            AlgorithmIdentifier localAlgorithmIdentifier2 = CertPathValidatorUtilities.getAlgorithmIdentifier((PublicKey)localObject2);
            localAlgorithmIdentifier2.getObjectId();
            localAlgorithmIdentifier2.getParameters();
            label1038:
            i3--;
          }
          catch (CertPathValidatorException localCertPathValidatorException2)
          {
            throw new CertPathValidatorException("Next working key could not be retrieved.", localCertPathValidatorException2, paramCertPath, i3);
          }
        }
      }
    }
    int i4 = RFC3280CertPathUtilities.wrapupCertA(m, localX509Certificate);
    int i5 = RFC3280CertPathUtilities.wrapupCertB(paramCertPath, i3 + 1, i4);
    Set localSet2 = localX509Certificate.getCriticalExtensionOIDs();
    HashSet localHashSet3;
    if (localSet2 != null)
    {
      localHashSet3 = new HashSet(localSet2);
      localHashSet3.remove(RFC3280CertPathUtilities.KEY_USAGE);
      localHashSet3.remove(RFC3280CertPathUtilities.CERTIFICATE_POLICIES);
      localHashSet3.remove(RFC3280CertPathUtilities.POLICY_MAPPINGS);
      localHashSet3.remove(RFC3280CertPathUtilities.INHIBIT_ANY_POLICY);
      localHashSet3.remove(RFC3280CertPathUtilities.ISSUING_DISTRIBUTION_POINT);
      localHashSet3.remove(RFC3280CertPathUtilities.DELTA_CRL_INDICATOR);
      localHashSet3.remove(RFC3280CertPathUtilities.POLICY_CONSTRAINTS);
      localHashSet3.remove(RFC3280CertPathUtilities.BASIC_CONSTRAINTS);
      localHashSet3.remove(RFC3280CertPathUtilities.SUBJECT_ALTERNATIVE_NAME);
      localHashSet3.remove(RFC3280CertPathUtilities.NAME_CONSTRAINTS);
      localHashSet3.remove(RFC3280CertPathUtilities.CRL_DISTRIBUTION_POINTS);
    }
    for (HashSet localHashSet4 = localHashSet3;; localHashSet4 = new HashSet())
    {
      RFC3280CertPathUtilities.wrapupCertF(paramCertPath, i3 + 1, localList2, localHashSet4);
      PKIXPolicyNode localPKIXPolicyNode2 = RFC3280CertPathUtilities.wrapupCertG(paramCertPath, localExtendedPKIXParameters, localSet1, i3 + 1, arrayOfArrayList, localPKIXPolicyNode1, localHashSet2);
      if ((i5 <= 0) && (localPKIXPolicyNode2 == null)) {
        break;
      }
      return new PKIXCertPathValidatorResult(localTrustAnchor, localPKIXPolicyNode2, localX509Certificate.getPublicKey());
    }
    throw new CertPathValidatorException("Path processing failed on policy.", null, paramCertPath, i3);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jce.provider.PKIXCertPathValidatorSpi
 * JD-Core Version:    0.7.0.1
 */