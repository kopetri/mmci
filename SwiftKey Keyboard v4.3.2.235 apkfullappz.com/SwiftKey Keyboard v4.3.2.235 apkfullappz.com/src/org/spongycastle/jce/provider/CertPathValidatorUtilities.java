package org.spongycastle.jce.provider;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.cert.CRLException;
import java.security.cert.CertPath;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertStore;
import java.security.cert.CertStoreException;
import java.security.cert.Certificate;
import java.security.cert.CertificateParsingException;
import java.security.cert.PKIXParameters;
import java.security.cert.PolicyQualifierInfo;
import java.security.cert.TrustAnchor;
import java.security.cert.X509CRL;
import java.security.cert.X509CRLEntry;
import java.security.cert.X509CRLSelector;
import java.security.cert.X509CertSelector;
import java.security.cert.X509Certificate;
import java.security.interfaces.DSAParams;
import java.security.interfaces.DSAPublicKey;
import java.security.spec.DSAPublicKeySpec;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.security.auth.x500.X500Principal;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Enumerated;
import org.spongycastle.asn1.ASN1GeneralizedTime;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1OutputStream;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DEREnumerated;
import org.spongycastle.asn1.DERGeneralizedTime;
import org.spongycastle.asn1.DERIA5String;
import org.spongycastle.asn1.DERObjectIdentifier;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.isismtt.ISISMTTObjectIdentifiers;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.CRLDistPoint;
import org.spongycastle.asn1.x509.DistributionPoint;
import org.spongycastle.asn1.x509.DistributionPointName;
import org.spongycastle.asn1.x509.GeneralName;
import org.spongycastle.asn1.x509.GeneralNames;
import org.spongycastle.asn1.x509.PolicyInformation;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.asn1.x509.X509Extensions;
import org.spongycastle.jce.X509LDAPCertStoreParameters;
import org.spongycastle.jce.X509LDAPCertStoreParameters.Builder;
import org.spongycastle.jce.exception.ExtCertPathValidatorException;
import org.spongycastle.util.StoreException;
import org.spongycastle.x509.ExtendedPKIXParameters;
import org.spongycastle.x509.X509AttributeCertStoreSelector;
import org.spongycastle.x509.X509AttributeCertificate;
import org.spongycastle.x509.X509CRLStoreSelector;
import org.spongycastle.x509.X509CertStoreSelector;
import org.spongycastle.x509.X509Store;

public class CertPathValidatorUtilities
{
  protected static final String ANY_POLICY = "2.5.29.32.0";
  protected static final String AUTHORITY_KEY_IDENTIFIER = X509Extensions.AuthorityKeyIdentifier.getId();
  protected static final String BASIC_CONSTRAINTS;
  protected static final String CERTIFICATE_POLICIES;
  protected static final String CRL_DISTRIBUTION_POINTS;
  protected static final String CRL_NUMBER = X509Extensions.CRLNumber.getId();
  protected static final int CRL_SIGN = 6;
  protected static final PKIXCRLUtil CRL_UTIL = new PKIXCRLUtil();
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
    BASIC_CONSTRAINTS = X509Extensions.BasicConstraints.getId();
    POLICY_MAPPINGS = X509Extensions.PolicyMappings.getId();
    SUBJECT_ALTERNATIVE_NAME = X509Extensions.SubjectAlternativeName.getId();
    NAME_CONSTRAINTS = X509Extensions.NameConstraints.getId();
    KEY_USAGE = X509Extensions.KeyUsage.getId();
    INHIBIT_ANY_POLICY = X509Extensions.InhibitAnyPolicy.getId();
    ISSUING_DISTRIBUTION_POINT = X509Extensions.IssuingDistributionPoint.getId();
    DELTA_CRL_INDICATOR = X509Extensions.DeltaCRLIndicator.getId();
    POLICY_CONSTRAINTS = X509Extensions.PolicyConstraints.getId();
    FRESHEST_CRL = X509Extensions.FreshestCRL.getId();
    CRL_DISTRIBUTION_POINTS = X509Extensions.CRLDistributionPoints.getId();
  }
  
  protected static void addAdditionalStoreFromLocation(String paramString, ExtendedPKIXParameters paramExtendedPKIXParameters)
  {
    if (paramExtendedPKIXParameters.isAdditionalLocationsEnabled()) {
      try
      {
        if (paramString.startsWith("ldap://"))
        {
          String str1 = paramString.substring(7);
          String str3;
          Object localObject;
          if (str1.indexOf("/") != -1)
          {
            str3 = str1.substring(str1.indexOf("/"));
            localObject = "ldap://" + str1.substring(0, str1.indexOf("/"));
          }
          for (;;)
          {
            X509LDAPCertStoreParameters localX509LDAPCertStoreParameters = new X509LDAPCertStoreParameters.Builder((String)localObject, str3).build();
            paramExtendedPKIXParameters.addAdditionalStore(X509Store.getInstance("CERTIFICATE/LDAP", localX509LDAPCertStoreParameters, BouncyCastleProvider.PROVIDER_NAME));
            paramExtendedPKIXParameters.addAdditionalStore(X509Store.getInstance("CRL/LDAP", localX509LDAPCertStoreParameters, BouncyCastleProvider.PROVIDER_NAME));
            paramExtendedPKIXParameters.addAdditionalStore(X509Store.getInstance("ATTRIBUTECERTIFICATE/LDAP", localX509LDAPCertStoreParameters, BouncyCastleProvider.PROVIDER_NAME));
            paramExtendedPKIXParameters.addAdditionalStore(X509Store.getInstance("CERTIFICATEPAIR/LDAP", localX509LDAPCertStoreParameters, BouncyCastleProvider.PROVIDER_NAME));
            return;
            String str2 = "ldap://" + str1;
            localObject = str2;
            str3 = null;
          }
        }
        return;
      }
      catch (Exception localException)
      {
        throw new RuntimeException("Exception adding X.509 stores.");
      }
    }
  }
  
  protected static void addAdditionalStoresFromAltNames(X509Certificate paramX509Certificate, ExtendedPKIXParameters paramExtendedPKIXParameters)
    throws CertificateParsingException
  {
    if (paramX509Certificate.getIssuerAlternativeNames() != null)
    {
      Iterator localIterator = paramX509Certificate.getIssuerAlternativeNames().iterator();
      while (localIterator.hasNext())
      {
        List localList = (List)localIterator.next();
        if (localList.get(0).equals(new Integer(6))) {
          addAdditionalStoreFromLocation((String)localList.get(1), paramExtendedPKIXParameters);
        }
      }
    }
  }
  
  protected static void addAdditionalStoresFromCRLDistributionPoint(CRLDistPoint paramCRLDistPoint, ExtendedPKIXParameters paramExtendedPKIXParameters)
    throws AnnotatedException
  {
    if (paramCRLDistPoint != null) {
      for (;;)
      {
        try
        {
          DistributionPoint[] arrayOfDistributionPoint = paramCRLDistPoint.getDistributionPoints();
          int i = 0;
          if (i >= arrayOfDistributionPoint.length) {
            break;
          }
          DistributionPointName localDistributionPointName = arrayOfDistributionPoint[i].getDistributionPoint();
          if ((localDistributionPointName != null) && (localDistributionPointName.getType() == 0))
          {
            GeneralName[] arrayOfGeneralName = GeneralNames.getInstance(localDistributionPointName.getName()).getNames();
            int j = 0;
            if (j < arrayOfGeneralName.length)
            {
              if (arrayOfGeneralName[j].getTagNo() == 6) {
                addAdditionalStoreFromLocation(DERIA5String.getInstance(arrayOfGeneralName[j].getName()).getString(), paramExtendedPKIXParameters);
              }
              j++;
              continue;
            }
          }
          i++;
        }
        catch (Exception localException)
        {
          throw new AnnotatedException("Distribution points could not be read.", localException);
        }
      }
    }
  }
  
  protected static Collection findCertificates(X509AttributeCertStoreSelector paramX509AttributeCertStoreSelector, List paramList)
    throws AnnotatedException
  {
    HashSet localHashSet = new HashSet();
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
    {
      Object localObject = localIterator.next();
      if ((localObject instanceof X509Store))
      {
        X509Store localX509Store = (X509Store)localObject;
        try
        {
          localHashSet.addAll(localX509Store.getMatches(paramX509AttributeCertStoreSelector));
        }
        catch (StoreException localStoreException)
        {
          throw new AnnotatedException("Problem while picking certificates from X.509 store.", localStoreException);
        }
      }
    }
    return localHashSet;
  }
  
  protected static Collection findCertificates(X509CertStoreSelector paramX509CertStoreSelector, List paramList)
    throws AnnotatedException
  {
    HashSet localHashSet = new HashSet();
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
    {
      Object localObject = localIterator.next();
      if ((localObject instanceof X509Store))
      {
        X509Store localX509Store = (X509Store)localObject;
        try
        {
          localHashSet.addAll(localX509Store.getMatches(paramX509CertStoreSelector));
        }
        catch (StoreException localStoreException)
        {
          throw new AnnotatedException("Problem while picking certificates from X.509 store.", localStoreException);
        }
      }
      else
      {
        CertStore localCertStore = (CertStore)localObject;
        try
        {
          localHashSet.addAll(localCertStore.getCertificates(paramX509CertStoreSelector));
        }
        catch (CertStoreException localCertStoreException)
        {
          throw new AnnotatedException("Problem while picking certificates from certificate store.", localCertStoreException);
        }
      }
    }
    return localHashSet;
  }
  
  /* Error */
  protected static Collection findIssuerCerts(X509Certificate paramX509Certificate, org.spongycastle.x509.ExtendedPKIXBuilderParameters paramExtendedPKIXBuilderParameters)
    throws AnnotatedException
  {
    // Byte code:
    //   0: new 357	org/spongycastle/x509/X509CertStoreSelector
    //   3: dup
    //   4: invokespecial 358	org/spongycastle/x509/X509CertStoreSelector:<init>	()V
    //   7: astore_2
    //   8: new 326	java/util/HashSet
    //   11: dup
    //   12: invokespecial 327	java/util/HashSet:<init>	()V
    //   15: astore_3
    //   16: aload_2
    //   17: aload_0
    //   18: invokevirtual 362	java/security/cert/X509Certificate:getIssuerX500Principal	()Ljavax/security/auth/x500/X500Principal;
    //   21: invokevirtual 368	javax/security/auth/x500/X500Principal:getEncoded	()[B
    //   24: invokevirtual 372	org/spongycastle/x509/X509CertStoreSelector:setSubject	([B)V
    //   27: new 374	java/util/ArrayList
    //   30: dup
    //   31: invokespecial 375	java/util/ArrayList:<init>	()V
    //   34: astore 5
    //   36: aload 5
    //   38: aload_2
    //   39: aload_1
    //   40: invokevirtual 381	org/spongycastle/x509/ExtendedPKIXBuilderParameters:getCertStores	()Ljava/util/List;
    //   43: invokestatic 383	org/spongycastle/jce/provider/CertPathValidatorUtilities:findCertificates	(Lorg/spongycastle/x509/X509CertStoreSelector;Ljava/util/List;)Ljava/util/Collection;
    //   46: invokeinterface 384 2 0
    //   51: pop
    //   52: aload 5
    //   54: aload_2
    //   55: aload_1
    //   56: invokevirtual 387	org/spongycastle/x509/ExtendedPKIXBuilderParameters:getStores	()Ljava/util/List;
    //   59: invokestatic 383	org/spongycastle/jce/provider/CertPathValidatorUtilities:findCertificates	(Lorg/spongycastle/x509/X509CertStoreSelector;Ljava/util/List;)Ljava/util/Collection;
    //   62: invokeinterface 384 2 0
    //   67: pop
    //   68: aload 5
    //   70: aload_2
    //   71: aload_1
    //   72: invokevirtual 390	org/spongycastle/x509/ExtendedPKIXBuilderParameters:getAdditionalStores	()Ljava/util/List;
    //   75: invokestatic 383	org/spongycastle/jce/provider/CertPathValidatorUtilities:findCertificates	(Lorg/spongycastle/x509/X509CertStoreSelector;Ljava/util/List;)Ljava/util/Collection;
    //   78: invokeinterface 384 2 0
    //   83: pop
    //   84: aload 5
    //   86: invokeinterface 328 1 0
    //   91: astore 10
    //   93: aload 10
    //   95: invokeinterface 245 1 0
    //   100: ifeq +53 -> 153
    //   103: aload_3
    //   104: aload 10
    //   106: invokeinterface 249 1 0
    //   111: checkcast 230	java/security/cert/X509Certificate
    //   114: invokeinterface 393 2 0
    //   119: pop
    //   120: goto -27 -> 93
    //   123: astore 4
    //   125: new 270	org/spongycastle/jce/provider/AnnotatedException
    //   128: dup
    //   129: ldc_w 395
    //   132: aload 4
    //   134: invokespecial 320	org/spongycastle/jce/provider/AnnotatedException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   137: athrow
    //   138: astore 6
    //   140: new 270	org/spongycastle/jce/provider/AnnotatedException
    //   143: dup
    //   144: ldc_w 397
    //   147: aload 6
    //   149: invokespecial 320	org/spongycastle/jce/provider/AnnotatedException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   152: athrow
    //   153: aload_3
    //   154: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	155	0	paramX509Certificate	X509Certificate
    //   0	155	1	paramExtendedPKIXBuilderParameters	org.spongycastle.x509.ExtendedPKIXBuilderParameters
    //   7	64	2	localX509CertStoreSelector	X509CertStoreSelector
    //   15	139	3	localHashSet	HashSet
    //   123	10	4	localIOException	IOException
    //   34	51	5	localArrayList	ArrayList
    //   138	10	6	localAnnotatedException	AnnotatedException
    //   91	14	10	localIterator	Iterator
    // Exception table:
    //   from	to	target	type
    //   16	27	123	java/io/IOException
    //   27	93	138	org/spongycastle/jce/provider/AnnotatedException
  }
  
  protected static TrustAnchor findTrustAnchor(X509Certificate paramX509Certificate, Set paramSet)
    throws AnnotatedException
  {
    return findTrustAnchor(paramX509Certificate, paramSet, null);
  }
  
  protected static TrustAnchor findTrustAnchor(X509Certificate paramX509Certificate, Set paramSet, String paramString)
    throws AnnotatedException
  {
    TrustAnchor localTrustAnchor = null;
    Object localObject1 = null;
    Object localObject2 = null;
    X509CertSelector localX509CertSelector = new X509CertSelector();
    X500Principal localX500Principal = getEncodedIssuerPrincipal(paramX509Certificate);
    for (;;)
    {
      try
      {
        localX509CertSelector.setSubject(localX500Principal.getEncoded());
        Iterator localIterator = paramSet.iterator();
        if ((!localIterator.hasNext()) || (localTrustAnchor != null)) {
          break;
        }
        localTrustAnchor = (TrustAnchor)localIterator.next();
        if (localTrustAnchor.getTrustedCert() == null) {
          break label139;
        }
        if (localX509CertSelector.match(localTrustAnchor.getTrustedCert()))
        {
          localObject1 = localTrustAnchor.getTrustedCert().getPublicKey();
          if (localObject1 == null) {
            continue;
          }
          try
          {
            verifyX509Certificate(paramX509Certificate, (PublicKey)localObject1, paramString);
          }
          catch (Exception localException)
          {
            localTrustAnchor = null;
            localObject1 = null;
          }
          continue;
        }
        localTrustAnchor = null;
      }
      catch (IOException localIOException)
      {
        throw new AnnotatedException("Cannot set subject search criteria for trust anchor.", localIOException);
      }
      continue;
      label139:
      if ((localTrustAnchor.getCAName() != null) && (localTrustAnchor.getCAPublicKey() != null)) {
        try
        {
          if (localX500Principal.equals(new X500Principal(localTrustAnchor.getCAName())))
          {
            PublicKey localPublicKey = localTrustAnchor.getCAPublicKey();
            localObject1 = localPublicKey;
            continue;
          }
          localTrustAnchor = null;
        }
        catch (IllegalArgumentException localIllegalArgumentException) {}
      } else {
        localTrustAnchor = null;
      }
    }
    if ((localTrustAnchor == null) && (localException != null)) {
      throw new AnnotatedException("TrustAnchor found but certificate validation failed.", localException);
    }
    return localTrustAnchor;
  }
  
  protected static AlgorithmIdentifier getAlgorithmIdentifier(PublicKey paramPublicKey)
    throws CertPathValidatorException
  {
    try
    {
      AlgorithmIdentifier localAlgorithmIdentifier = SubjectPublicKeyInfo.getInstance(new ASN1InputStream(paramPublicKey.getEncoded()).readObject()).getAlgorithmId();
      return localAlgorithmIdentifier;
    }
    catch (Exception localException)
    {
      throw new ExtCertPathValidatorException("Subject public key cannot be decoded.", localException);
    }
  }
  
  protected static void getCRLIssuersFromDistributionPoint(DistributionPoint paramDistributionPoint, Collection paramCollection, X509CRLSelector paramX509CRLSelector, ExtendedPKIXParameters paramExtendedPKIXParameters)
    throws AnnotatedException
  {
    ArrayList localArrayList = new ArrayList();
    if (paramDistributionPoint.getCRLIssuer() != null)
    {
      GeneralName[] arrayOfGeneralName = paramDistributionPoint.getCRLIssuer().getNames();
      int i = 0;
      for (;;)
      {
        if (i >= arrayOfGeneralName.length) {
          break label157;
        }
        if (arrayOfGeneralName[i].getTagNo() == 4) {}
        try
        {
          localArrayList.add(new X500Principal(arrayOfGeneralName[i].getName().toASN1Primitive().getEncoded()));
          i++;
        }
        catch (IOException localIOException2)
        {
          throw new AnnotatedException("CRL issuer information from distribution point cannot be decoded.", localIOException2);
        }
      }
    }
    if (paramDistributionPoint.getDistributionPoint() == null) {
      throw new AnnotatedException("CRL issuer is omitted from distribution point but no distributionPoint field present.");
    }
    Iterator localIterator1 = paramCollection.iterator();
    while (localIterator1.hasNext()) {
      localArrayList.add((X500Principal)localIterator1.next());
    }
    label157:
    Iterator localIterator2 = localArrayList.iterator();
    while (localIterator2.hasNext()) {
      try
      {
        paramX509CRLSelector.addIssuerName(((X500Principal)localIterator2.next()).getEncoded());
      }
      catch (IOException localIOException1)
      {
        throw new AnnotatedException("Cannot decode CRL issuer information.", localIOException1);
      }
    }
  }
  
  protected static void getCertStatus(Date paramDate, X509CRL paramX509CRL, Object paramObject, CertStatus paramCertStatus)
    throws AnnotatedException
  {
    X509CRLEntry localX509CRLEntry;
    label72:
    Object localObject;
    for (;;)
    {
      try
      {
        boolean bool1 = X509CRLObject.isIndirectCRL(paramX509CRL);
        if (!bool1) {
          break label203;
        }
        localX509CRLEntry = paramX509CRL.getRevokedCertificate(getSerialNumber(paramObject));
        if (localX509CRLEntry == null) {
          return;
        }
      }
      catch (CRLException localCRLException)
      {
        throw new AnnotatedException("Failed check for indirect CRL.", localCRLException);
      }
      X500Principal localX500Principal = localX509CRLEntry.getCertificateIssuer();
      if (localX500Principal == null) {
        localX500Principal = getIssuerPrincipal(paramX509CRL);
      }
      if (getEncodedIssuerPrincipal(paramObject).equals(localX500Principal))
      {
        boolean bool2 = localX509CRLEntry.hasExtensions();
        localObject = null;
        if (!bool2) {
          break;
        }
      }
    }
    for (;;)
    {
      try
      {
        ASN1Enumerated localASN1Enumerated = DEREnumerated.getInstance(getExtensionValue(localX509CRLEntry, org.spongycastle.asn1.x509.X509Extension.reasonCode.getId()));
        localObject = localASN1Enumerated;
        if ((paramDate.getTime() < localX509CRLEntry.getRevocationDate().getTime()) && (localObject != null) && (localObject.getValue().intValue() != 0) && (localObject.getValue().intValue() != 1) && (localObject.getValue().intValue() != 2) && (localObject.getValue().intValue() != 8)) {
          break;
        }
        if (localObject == null) {
          break label248;
        }
        paramCertStatus.setCertStatus(localObject.getValue().intValue());
        paramCertStatus.setRevocationDate(localX509CRLEntry.getRevocationDate());
        return;
      }
      catch (Exception localException)
      {
        label203:
        throw new AnnotatedException("Reason code CRL entry extension could not be decoded.", localException);
      }
      if (!getEncodedIssuerPrincipal(paramObject).equals(getIssuerPrincipal(paramX509CRL))) {
        break;
      }
      localX509CRLEntry = paramX509CRL.getRevokedCertificate(getSerialNumber(paramObject));
      if (localX509CRLEntry != null) {
        break label72;
      }
      return;
      label248:
      paramCertStatus.setCertStatus(0);
    }
  }
  
  protected static Set getCompleteCRLs(DistributionPoint paramDistributionPoint, Object paramObject, Date paramDate, ExtendedPKIXParameters paramExtendedPKIXParameters)
    throws AnnotatedException
  {
    X509CRLStoreSelector localX509CRLStoreSelector = new X509CRLStoreSelector();
    Set localSet;
    for (;;)
    {
      try
      {
        HashSet localHashSet = new HashSet();
        if ((paramObject instanceof X509AttributeCertificate))
        {
          localHashSet.add(((X509AttributeCertificate)paramObject).getIssuer().getPrincipals()[0]);
          getCRLIssuersFromDistributionPoint(paramDistributionPoint, localHashSet, localX509CRLStoreSelector, paramExtendedPKIXParameters);
          if ((paramObject instanceof X509Certificate))
          {
            localX509CRLStoreSelector.setCertificateChecking((X509Certificate)paramObject);
            localX509CRLStoreSelector.setCompleteCRLEnabled(true);
            localSet = CRL_UTIL.findCRLs(localX509CRLStoreSelector, paramExtendedPKIXParameters, paramDate);
            if (!localSet.isEmpty()) {
              break label245;
            }
            if (!(paramObject instanceof X509AttributeCertificate)) {
              break;
            }
            X509AttributeCertificate localX509AttributeCertificate = (X509AttributeCertificate)paramObject;
            throw new AnnotatedException("No CRLs found for issuer \"" + localX509AttributeCertificate.getIssuer().getPrincipals()[0] + "\"");
          }
        }
        else
        {
          localHashSet.add(getEncodedIssuerPrincipal(paramObject));
          continue;
        }
        if (!(paramObject instanceof X509AttributeCertificate)) {
          continue;
        }
      }
      catch (AnnotatedException localAnnotatedException)
      {
        throw new AnnotatedException("Could not get issuer information from distribution point.", localAnnotatedException);
      }
      localX509CRLStoreSelector.setAttrCertificateChecking((X509AttributeCertificate)paramObject);
    }
    X509Certificate localX509Certificate = (X509Certificate)paramObject;
    throw new AnnotatedException("No CRLs found for issuer \"" + localX509Certificate.getIssuerX500Principal() + "\"");
    label245:
    return localSet;
  }
  
  /* Error */
  protected static Set getDeltaCRLs(Date paramDate, ExtendedPKIXParameters paramExtendedPKIXParameters, X509CRL paramX509CRL)
    throws AnnotatedException
  {
    // Byte code:
    //   0: new 582	org/spongycastle/x509/X509CRLStoreSelector
    //   3: dup
    //   4: invokespecial 583	org/spongycastle/x509/X509CRLStoreSelector:<init>	()V
    //   7: astore_3
    //   8: aload_3
    //   9: aload_2
    //   10: invokestatic 531	org/spongycastle/jce/provider/CertPathValidatorUtilities:getIssuerPrincipal	(Ljava/security/cert/X509CRL;)Ljavax/security/auth/x500/X500Principal;
    //   13: invokevirtual 368	javax/security/auth/x500/X500Principal:getEncoded	()[B
    //   16: invokevirtual 628	org/spongycastle/x509/X509CRLStoreSelector:addIssuerName	([B)V
    //   19: aload_2
    //   20: getstatic 119	org/spongycastle/jce/provider/CertPathValidatorUtilities:CRL_NUMBER	Ljava/lang/String;
    //   23: invokestatic 543	org/spongycastle/jce/provider/CertPathValidatorUtilities:getExtensionValue	(Ljava/security/cert/X509Extension;Ljava/lang/String;)Lorg/spongycastle/asn1/ASN1Primitive;
    //   26: astore 6
    //   28: aconst_null
    //   29: astore 7
    //   31: aload 6
    //   33: ifnull +17 -> 50
    //   36: aload 6
    //   38: invokestatic 633	org/spongycastle/asn1/ASN1Integer:getInstance	(Ljava/lang/Object;)Lorg/spongycastle/asn1/ASN1Integer;
    //   41: invokevirtual 636	org/spongycastle/asn1/ASN1Integer:getPositiveValue	()Ljava/math/BigInteger;
    //   44: astore 8
    //   46: aload 8
    //   48: astore 7
    //   50: aload_2
    //   51: getstatic 89	org/spongycastle/jce/provider/CertPathValidatorUtilities:ISSUING_DISTRIBUTION_POINT	Ljava/lang/String;
    //   54: invokevirtual 639	java/security/cert/X509CRL:getExtensionValue	(Ljava/lang/String;)[B
    //   57: astore 10
    //   59: aload 7
    //   61: ifnonnull +146 -> 207
    //   64: aconst_null
    //   65: astore 11
    //   67: aload_3
    //   68: aload 11
    //   70: invokevirtual 643	org/spongycastle/x509/X509CRLStoreSelector:setMinCRLNumber	(Ljava/math/BigInteger;)V
    //   73: aload_3
    //   74: aload 10
    //   76: invokevirtual 646	org/spongycastle/x509/X509CRLStoreSelector:setIssuingDistributionPoint	([B)V
    //   79: aload_3
    //   80: iconst_1
    //   81: invokevirtual 649	org/spongycastle/x509/X509CRLStoreSelector:setIssuingDistributionPointEnabled	(Z)V
    //   84: aload_3
    //   85: aload 7
    //   87: invokevirtual 652	org/spongycastle/x509/X509CRLStoreSelector:setMaxBaseCRLNumber	(Ljava/math/BigInteger;)V
    //   90: getstatic 40	org/spongycastle/jce/provider/CertPathValidatorUtilities:CRL_UTIL	Lorg/spongycastle/jce/provider/PKIXCRLUtil;
    //   93: aload_3
    //   94: aload_1
    //   95: aload_0
    //   96: invokevirtual 609	org/spongycastle/jce/provider/PKIXCRLUtil:findCRLs	(Lorg/spongycastle/x509/X509CRLStoreSelector;Lorg/spongycastle/x509/ExtendedPKIXParameters;Ljava/util/Date;)Ljava/util/Set;
    //   99: astore 12
    //   101: new 326	java/util/HashSet
    //   104: dup
    //   105: invokespecial 327	java/util/HashSet:<init>	()V
    //   108: astore 13
    //   110: aload 12
    //   112: invokeinterface 413 1 0
    //   117: astore 14
    //   119: aload 14
    //   121: invokeinterface 245 1 0
    //   126: ifeq +95 -> 221
    //   129: aload 14
    //   131: invokeinterface 249 1 0
    //   136: checkcast 516	java/security/cert/X509CRL
    //   139: astore 15
    //   141: aload 15
    //   143: invokestatic 655	org/spongycastle/jce/provider/CertPathValidatorUtilities:isDeltaCRL	(Ljava/security/cert/X509CRL;)Z
    //   146: ifeq -27 -> 119
    //   149: aload 13
    //   151: aload 15
    //   153: invokeinterface 393 2 0
    //   158: pop
    //   159: goto -40 -> 119
    //   162: astore 4
    //   164: new 270	org/spongycastle/jce/provider/AnnotatedException
    //   167: dup
    //   168: ldc_w 657
    //   171: aload 4
    //   173: invokespecial 320	org/spongycastle/jce/provider/AnnotatedException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   176: athrow
    //   177: astore 5
    //   179: new 270	org/spongycastle/jce/provider/AnnotatedException
    //   182: dup
    //   183: ldc_w 659
    //   186: aload 5
    //   188: invokespecial 320	org/spongycastle/jce/provider/AnnotatedException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   191: athrow
    //   192: astore 9
    //   194: new 270	org/spongycastle/jce/provider/AnnotatedException
    //   197: dup
    //   198: ldc_w 661
    //   201: aload 9
    //   203: invokespecial 320	org/spongycastle/jce/provider/AnnotatedException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   206: athrow
    //   207: aload 7
    //   209: lconst_1
    //   210: invokestatic 665	java/math/BigInteger:valueOf	(J)Ljava/math/BigInteger;
    //   213: invokevirtual 668	java/math/BigInteger:add	(Ljava/math/BigInteger;)Ljava/math/BigInteger;
    //   216: astore 11
    //   218: goto -151 -> 67
    //   221: aload 13
    //   223: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	224	0	paramDate	Date
    //   0	224	1	paramExtendedPKIXParameters	ExtendedPKIXParameters
    //   0	224	2	paramX509CRL	X509CRL
    //   7	87	3	localX509CRLStoreSelector	X509CRLStoreSelector
    //   162	10	4	localIOException	IOException
    //   177	10	5	localException1	Exception
    //   26	11	6	localASN1Primitive	ASN1Primitive
    //   29	179	7	localObject	Object
    //   44	3	8	localBigInteger1	BigInteger
    //   192	10	9	localException2	Exception
    //   57	18	10	arrayOfByte	byte[]
    //   65	152	11	localBigInteger2	BigInteger
    //   99	12	12	localSet	Set
    //   108	114	13	localHashSet	HashSet
    //   117	13	14	localIterator	Iterator
    //   139	13	15	localX509CRL	X509CRL
    // Exception table:
    //   from	to	target	type
    //   8	19	162	java/io/IOException
    //   19	28	177	java/lang/Exception
    //   36	46	177	java/lang/Exception
    //   50	59	192	java/lang/Exception
  }
  
  protected static X500Principal getEncodedIssuerPrincipal(Object paramObject)
  {
    if ((paramObject instanceof X509Certificate)) {
      return ((X509Certificate)paramObject).getIssuerX500Principal();
    }
    return (X500Principal)((X509AttributeCertificate)paramObject).getIssuer().getPrincipals()[0];
  }
  
  protected static ASN1Primitive getExtensionValue(java.security.cert.X509Extension paramX509Extension, String paramString)
    throws AnnotatedException
  {
    byte[] arrayOfByte = paramX509Extension.getExtensionValue(paramString);
    if (arrayOfByte == null) {
      return null;
    }
    return getObject(paramString, arrayOfByte);
  }
  
  protected static X500Principal getIssuerPrincipal(X509CRL paramX509CRL)
  {
    return paramX509CRL.getIssuerX500Principal();
  }
  
  protected static PublicKey getNextWorkingKey(List paramList, int paramInt)
    throws CertPathValidatorException
  {
    PublicKey localPublicKey1 = ((Certificate)paramList.get(paramInt)).getPublicKey();
    Object localObject;
    if (!(localPublicKey1 instanceof DSAPublicKey)) {
      localObject = localPublicKey1;
    }
    do
    {
      return localObject;
      localObject = (DSAPublicKey)localPublicKey1;
    } while (((DSAPublicKey)localObject).getParams() != null);
    for (int i = paramInt + 1; i < paramList.size(); i++)
    {
      PublicKey localPublicKey2 = ((X509Certificate)paramList.get(i)).getPublicKey();
      if (!(localPublicKey2 instanceof DSAPublicKey)) {
        throw new CertPathValidatorException("DSA parameters cannot be inherited from previous certificate.");
      }
      DSAPublicKey localDSAPublicKey = (DSAPublicKey)localPublicKey2;
      if (localDSAPublicKey.getParams() != null)
      {
        DSAParams localDSAParams = localDSAPublicKey.getParams();
        DSAPublicKeySpec localDSAPublicKeySpec = new DSAPublicKeySpec(((DSAPublicKey)localObject).getY(), localDSAParams.getP(), localDSAParams.getQ(), localDSAParams.getG());
        try
        {
          PublicKey localPublicKey3 = KeyFactory.getInstance("DSA", BouncyCastleProvider.PROVIDER_NAME).generatePublic(localDSAPublicKeySpec);
          return localPublicKey3;
        }
        catch (Exception localException)
        {
          throw new RuntimeException(localException.getMessage());
        }
      }
    }
    throw new CertPathValidatorException("DSA parameters cannot be inherited from previous certificate.");
  }
  
  private static ASN1Primitive getObject(String paramString, byte[] paramArrayOfByte)
    throws AnnotatedException
  {
    try
    {
      ASN1Primitive localASN1Primitive = new ASN1InputStream(((ASN1OctetString)new ASN1InputStream(paramArrayOfByte).readObject()).getOctets()).readObject();
      return localASN1Primitive;
    }
    catch (Exception localException)
    {
      throw new AnnotatedException("exception processing extension " + paramString, localException);
    }
  }
  
  protected static final Set getQualifierSet(ASN1Sequence paramASN1Sequence)
    throws CertPathValidatorException
  {
    HashSet localHashSet = new HashSet();
    if (paramASN1Sequence == null) {
      return localHashSet;
    }
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    ASN1OutputStream localASN1OutputStream = new ASN1OutputStream(localByteArrayOutputStream);
    Enumeration localEnumeration = paramASN1Sequence.getObjects();
    while (localEnumeration.hasMoreElements()) {
      try
      {
        localASN1OutputStream.writeObject((ASN1Encodable)localEnumeration.nextElement());
        localHashSet.add(new PolicyQualifierInfo(localByteArrayOutputStream.toByteArray()));
        localByteArrayOutputStream.reset();
      }
      catch (IOException localIOException)
      {
        throw new ExtCertPathValidatorException("Policy qualifier info cannot be decoded.", localIOException);
      }
    }
  }
  
  private static BigInteger getSerialNumber(Object paramObject)
  {
    if ((paramObject instanceof X509Certificate)) {
      return ((X509Certificate)paramObject).getSerialNumber();
    }
    return ((X509AttributeCertificate)paramObject).getSerialNumber();
  }
  
  protected static X500Principal getSubjectPrincipal(X509Certificate paramX509Certificate)
  {
    return paramX509Certificate.getSubjectX500Principal();
  }
  
  protected static Date getValidCertDateFromValidityModel(ExtendedPKIXParameters paramExtendedPKIXParameters, CertPath paramCertPath, int paramInt)
    throws AnnotatedException
  {
    if (paramExtendedPKIXParameters.getValidityModel() == 1)
    {
      if (paramInt <= 0) {
        return getValidDate(paramExtendedPKIXParameters);
      }
      if (paramInt - 1 == 0) {
        try
        {
          byte[] arrayOfByte = ((X509Certificate)paramCertPath.getCertificates().get(paramInt - 1)).getExtensionValue(ISISMTTObjectIdentifiers.id_isismtt_at_dateOfCertGen.getId());
          Object localObject = null;
          if (arrayOfByte != null)
          {
            ASN1GeneralizedTime localASN1GeneralizedTime = DERGeneralizedTime.getInstance(ASN1Primitive.fromByteArray(arrayOfByte));
            localObject = localASN1GeneralizedTime;
          }
          if (localObject != null) {}
          Date localDate;
          return ((X509Certificate)paramCertPath.getCertificates().get(paramInt - 1)).getNotBefore();
        }
        catch (IOException localIOException)
        {
          try
          {
            localDate = localObject.getDate();
            return localDate;
          }
          catch (ParseException localParseException)
          {
            throw new AnnotatedException("Date from date of cert gen extension could not be parsed.", localParseException);
          }
          localIOException = localIOException;
          throw new AnnotatedException("Date of cert gen extension could not be read.");
        }
        catch (IllegalArgumentException localIllegalArgumentException)
        {
          throw new AnnotatedException("Date of cert gen extension could not be read.");
        }
      }
      return ((X509Certificate)paramCertPath.getCertificates().get(paramInt - 1)).getNotBefore();
    }
    return getValidDate(paramExtendedPKIXParameters);
  }
  
  protected static Date getValidDate(PKIXParameters paramPKIXParameters)
  {
    Date localDate = paramPKIXParameters.getDate();
    if (localDate == null) {
      localDate = new Date();
    }
    return localDate;
  }
  
  protected static boolean isAnyPolicy(Set paramSet)
  {
    return (paramSet == null) || (paramSet.contains("2.5.29.32.0")) || (paramSet.isEmpty());
  }
  
  private static boolean isDeltaCRL(X509CRL paramX509CRL)
  {
    Set localSet = paramX509CRL.getCriticalExtensionOIDs();
    if (localSet == null) {
      return false;
    }
    return localSet.contains(RFC3280CertPathUtilities.DELTA_CRL_INDICATOR);
  }
  
  protected static boolean isSelfIssued(X509Certificate paramX509Certificate)
  {
    return paramX509Certificate.getSubjectDN().equals(paramX509Certificate.getIssuerDN());
  }
  
  protected static void prepareNextCertB1(int paramInt, List[] paramArrayOfList, String paramString, Map paramMap, X509Certificate paramX509Certificate)
    throws AnnotatedException, CertPathValidatorException
  {
    Iterator localIterator1 = paramArrayOfList[paramInt].iterator();
    PKIXPolicyNode localPKIXPolicyNode4;
    do
    {
      boolean bool1 = localIterator1.hasNext();
      i = 0;
      if (!bool1) {
        break;
      }
      localPKIXPolicyNode4 = (PKIXPolicyNode)localIterator1.next();
    } while (!localPKIXPolicyNode4.getValidPolicy().equals(paramString));
    int i = 1;
    localPKIXPolicyNode4.expectedPolicies = ((Set)paramMap.get(paramString));
    if (i == 0)
    {
      Iterator localIterator2 = paramArrayOfList[paramInt].iterator();
      do
      {
        if (!localIterator2.hasNext()) {
          break;
        }
        localPKIXPolicyNode1 = (PKIXPolicyNode)localIterator2.next();
      } while (!"2.5.29.32.0".equals(localPKIXPolicyNode1.getValidPolicy()));
    }
    try
    {
      ASN1Sequence localASN1Sequence = DERSequence.getInstance(getExtensionValue(paramX509Certificate, CERTIFICATE_POLICIES));
      localEnumeration = localASN1Sequence.getObjects();
    }
    catch (Exception localException1)
    {
      try
      {
        do
        {
          Enumeration localEnumeration;
          boolean bool2;
          localPolicyInformation = PolicyInformation.getInstance(localEnumeration.nextElement());
        } while (!"2.5.29.32.0".equals(localPolicyInformation.getPolicyIdentifier().getId()));
      }
      catch (Exception localException2)
      {
        Object localObject;
        PolicyInformation localPolicyInformation;
        Set localSet2;
        Set localSet1;
        boolean bool3;
        PKIXPolicyNode localPKIXPolicyNode2;
        throw new AnnotatedException("Policy information cannot be decoded.", localException2);
      }
      try
      {
        localSet2 = getQualifierSet(localPolicyInformation.getPolicyQualifiers());
        localObject = localSet2;
        localSet1 = paramX509Certificate.getCriticalExtensionOIDs();
        bool3 = false;
        if (localSet1 != null) {
          bool3 = paramX509Certificate.getCriticalExtensionOIDs().contains(CERTIFICATE_POLICIES);
        }
        localPKIXPolicyNode2 = (PKIXPolicyNode)localPKIXPolicyNode1.getParent();
        if ("2.5.29.32.0".equals(localPKIXPolicyNode2.getValidPolicy()))
        {
          PKIXPolicyNode localPKIXPolicyNode3 = new PKIXPolicyNode(new ArrayList(), paramInt, (Set)paramMap.get(paramString), localPKIXPolicyNode2, localObject, paramString, bool3);
          localPKIXPolicyNode2.addChild(localPKIXPolicyNode3);
          paramArrayOfList[paramInt].add(localPKIXPolicyNode3);
        }
        return;
      }
      catch (CertPathValidatorException localCertPathValidatorException)
      {
        throw new ExtCertPathValidatorException("Policy qualifier info set could not be built.", localCertPathValidatorException);
      }
      localException1 = localException1;
      throw new AnnotatedException("Certificate policies cannot be decoded.", localException1);
    }
    bool2 = localEnumeration.hasMoreElements();
    localObject = null;
    if (!bool2) {}
  }
  
  protected static PKIXPolicyNode prepareNextCertB2(int paramInt, List[] paramArrayOfList, String paramString, PKIXPolicyNode paramPKIXPolicyNode)
  {
    Iterator localIterator = paramArrayOfList[paramInt].iterator();
    while (localIterator.hasNext())
    {
      PKIXPolicyNode localPKIXPolicyNode1 = (PKIXPolicyNode)localIterator.next();
      if (localPKIXPolicyNode1.getValidPolicy().equals(paramString))
      {
        ((PKIXPolicyNode)localPKIXPolicyNode1.getParent()).removeChild(localPKIXPolicyNode1);
        localIterator.remove();
        for (int i = paramInt - 1; i >= 0; i--)
        {
          List localList = paramArrayOfList[i];
          for (int j = 0; j < localList.size(); j++)
          {
            PKIXPolicyNode localPKIXPolicyNode2 = (PKIXPolicyNode)localList.get(j);
            if (!localPKIXPolicyNode2.hasChildren())
            {
              paramPKIXPolicyNode = removePolicyNode(paramPKIXPolicyNode, paramArrayOfList, localPKIXPolicyNode2);
              if (paramPKIXPolicyNode == null) {
                break;
              }
            }
          }
        }
      }
    }
    return paramPKIXPolicyNode;
  }
  
  protected static boolean processCertD1i(int paramInt, List[] paramArrayOfList, DERObjectIdentifier paramDERObjectIdentifier, Set paramSet)
  {
    List localList = paramArrayOfList[(paramInt - 1)];
    for (int i = 0;; i++)
    {
      int j = localList.size();
      boolean bool = false;
      if (i < j)
      {
        PKIXPolicyNode localPKIXPolicyNode1 = (PKIXPolicyNode)localList.get(i);
        if (localPKIXPolicyNode1.getExpectedPolicies().contains(paramDERObjectIdentifier.getId()))
        {
          HashSet localHashSet = new HashSet();
          localHashSet.add(paramDERObjectIdentifier.getId());
          PKIXPolicyNode localPKIXPolicyNode2 = new PKIXPolicyNode(new ArrayList(), paramInt, localHashSet, localPKIXPolicyNode1, paramSet, paramDERObjectIdentifier.getId(), false);
          localPKIXPolicyNode1.addChild(localPKIXPolicyNode2);
          paramArrayOfList[paramInt].add(localPKIXPolicyNode2);
          bool = true;
        }
      }
      else
      {
        return bool;
      }
    }
  }
  
  protected static void processCertD1ii(int paramInt, List[] paramArrayOfList, DERObjectIdentifier paramDERObjectIdentifier, Set paramSet)
  {
    List localList = paramArrayOfList[(paramInt - 1)];
    for (int i = 0;; i++) {
      if (i < localList.size())
      {
        PKIXPolicyNode localPKIXPolicyNode1 = (PKIXPolicyNode)localList.get(i);
        if ("2.5.29.32.0".equals(localPKIXPolicyNode1.getValidPolicy()))
        {
          HashSet localHashSet = new HashSet();
          localHashSet.add(paramDERObjectIdentifier.getId());
          PKIXPolicyNode localPKIXPolicyNode2 = new PKIXPolicyNode(new ArrayList(), paramInt, localHashSet, localPKIXPolicyNode1, paramSet, paramDERObjectIdentifier.getId(), false);
          localPKIXPolicyNode1.addChild(localPKIXPolicyNode2);
          paramArrayOfList[paramInt].add(localPKIXPolicyNode2);
        }
      }
      else
      {
        return;
      }
    }
  }
  
  protected static PKIXPolicyNode removePolicyNode(PKIXPolicyNode paramPKIXPolicyNode1, List[] paramArrayOfList, PKIXPolicyNode paramPKIXPolicyNode2)
  {
    PKIXPolicyNode localPKIXPolicyNode = (PKIXPolicyNode)paramPKIXPolicyNode2.getParent();
    if (paramPKIXPolicyNode1 == null) {
      return null;
    }
    if (localPKIXPolicyNode == null)
    {
      for (int i = 0; i < paramArrayOfList.length; i++) {
        paramArrayOfList[i] = new ArrayList();
      }
      return null;
    }
    localPKIXPolicyNode.removeChild(paramPKIXPolicyNode2);
    removePolicyNodeRecurse(paramArrayOfList, paramPKIXPolicyNode2);
    return paramPKIXPolicyNode1;
  }
  
  private static void removePolicyNodeRecurse(List[] paramArrayOfList, PKIXPolicyNode paramPKIXPolicyNode)
  {
    paramArrayOfList[paramPKIXPolicyNode.getDepth()].remove(paramPKIXPolicyNode);
    if (paramPKIXPolicyNode.hasChildren())
    {
      Iterator localIterator = paramPKIXPolicyNode.getChildren();
      while (localIterator.hasNext()) {
        removePolicyNodeRecurse(paramArrayOfList, (PKIXPolicyNode)localIterator.next());
      }
    }
  }
  
  protected static void verifyX509Certificate(X509Certificate paramX509Certificate, PublicKey paramPublicKey, String paramString)
    throws GeneralSecurityException
  {
    if (paramString == null)
    {
      paramX509Certificate.verify(paramPublicKey);
      return;
    }
    paramX509Certificate.verify(paramPublicKey, paramString);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jce.provider.CertPathValidatorUtilities
 * JD-Core Version:    0.7.0.1
 */