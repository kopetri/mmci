package org.spongycastle.tsp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertStore;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.cms.Attribute;
import org.spongycastle.asn1.cms.AttributeTable;
import org.spongycastle.asn1.cms.ContentInfo;
import org.spongycastle.asn1.cms.IssuerAndSerialNumber;
import org.spongycastle.asn1.ess.ESSCertID;
import org.spongycastle.asn1.ess.ESSCertIDv2;
import org.spongycastle.asn1.nist.NISTObjectIdentifiers;
import org.spongycastle.asn1.oiw.OIWObjectIdentifiers;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.tsp.TSTInfo;
import org.spongycastle.asn1.x500.X500Name;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.GeneralName;
import org.spongycastle.asn1.x509.GeneralNames;
import org.spongycastle.asn1.x509.IssuerSerial;
import org.spongycastle.asn1.x509.X509Name;
import org.spongycastle.cert.X509CertificateHolder;
import org.spongycastle.cms.CMSException;
import org.spongycastle.cms.CMSProcessable;
import org.spongycastle.cms.CMSSignedData;
import org.spongycastle.cms.SignerId;
import org.spongycastle.cms.SignerInformation;
import org.spongycastle.cms.SignerInformationStore;
import org.spongycastle.cms.SignerInformationVerifier;
import org.spongycastle.jce.PrincipalUtil;
import org.spongycastle.jce.X509Principal;
import org.spongycastle.operator.DigestCalculator;
import org.spongycastle.operator.OperatorCreationException;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Store;

public class TimeStampToken
{
  CertID certID;
  Date genTime;
  CMSSignedData tsToken;
  SignerInformation tsaSignerInfo;
  TimeStampTokenInfo tstInfo;
  
  public TimeStampToken(ContentInfo paramContentInfo)
    throws TSPException, IOException
  {
    this(getSignedData(paramContentInfo));
  }
  
  public TimeStampToken(CMSSignedData paramCMSSignedData)
    throws TSPException, IOException
  {
    this.tsToken = paramCMSSignedData;
    if (!this.tsToken.getSignedContentTypeOID().equals(PKCSObjectIdentifiers.id_ct_TSTInfo.getId())) {
      throw new TSPValidationException("ContentInfo object not for a time stamp.");
    }
    Collection localCollection = this.tsToken.getSignerInfos().getSigners();
    if (localCollection.size() != 1) {
      throw new IllegalArgumentException("Time-stamp token signed by " + localCollection.size() + " signers, but it must contain just the TSA signature.");
    }
    this.tsaSignerInfo = ((SignerInformation)localCollection.iterator().next());
    Attribute localAttribute2;
    try
    {
      CMSProcessable localCMSProcessable = this.tsToken.getSignedContent();
      ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
      localCMSProcessable.write(localByteArrayOutputStream);
      this.tstInfo = new TimeStampTokenInfo(TSTInfo.getInstance(new ASN1InputStream(new ByteArrayInputStream(localByteArrayOutputStream.toByteArray())).readObject()));
      Attribute localAttribute1 = this.tsaSignerInfo.getSignedAttributes().get(PKCSObjectIdentifiers.id_aa_signingCertificate);
      if (localAttribute1 != null)
      {
        this.certID = new CertID(ESSCertID.getInstance(org.spongycastle.asn1.ess.SigningCertificate.getInstance(localAttribute1.getAttrValues().getObjectAt(0)).getCerts()[0]));
        return;
      }
      localAttribute2 = this.tsaSignerInfo.getSignedAttributes().get(PKCSObjectIdentifiers.id_aa_signingCertificateV2);
      if (localAttribute2 == null) {
        throw new TSPValidationException("no signing certificate attribute found, time stamp invalid.");
      }
    }
    catch (CMSException localCMSException)
    {
      throw new TSPException(localCMSException.getMessage(), localCMSException.getUnderlyingException());
    }
    this.certID = new CertID(ESSCertIDv2.getInstance(org.spongycastle.asn1.ess.SigningCertificateV2.getInstance(localAttribute2.getAttrValues().getObjectAt(0)).getCerts()[0]));
  }
  
  private static CMSSignedData getSignedData(ContentInfo paramContentInfo)
    throws TSPException
  {
    try
    {
      CMSSignedData localCMSSignedData = new CMSSignedData(paramContentInfo);
      return localCMSSignedData;
    }
    catch (CMSException localCMSException)
    {
      throw new TSPException("TSP parsing error: " + localCMSException.getMessage(), localCMSException.getCause());
    }
  }
  
  public Store getAttributeCertificates()
  {
    return this.tsToken.getAttributeCertificates();
  }
  
  public Store getCRLs()
  {
    return this.tsToken.getCRLs();
  }
  
  public Store getCertificates()
  {
    return this.tsToken.getCertificates();
  }
  
  public CertStore getCertificatesAndCRLs(String paramString1, String paramString2)
    throws NoSuchAlgorithmException, NoSuchProviderException, CMSException
  {
    return this.tsToken.getCertificatesAndCRLs(paramString1, paramString2);
  }
  
  public byte[] getEncoded()
    throws IOException
  {
    return this.tsToken.getEncoded();
  }
  
  public SignerId getSID()
  {
    return this.tsaSignerInfo.getSID();
  }
  
  public AttributeTable getSignedAttributes()
  {
    return this.tsaSignerInfo.getSignedAttributes();
  }
  
  public TimeStampTokenInfo getTimeStampInfo()
  {
    return this.tstInfo;
  }
  
  public AttributeTable getUnsignedAttributes()
  {
    return this.tsaSignerInfo.getUnsignedAttributes();
  }
  
  public boolean isSignatureValid(SignerInformationVerifier paramSignerInformationVerifier)
    throws TSPException
  {
    try
    {
      boolean bool = this.tsaSignerInfo.verify(paramSignerInformationVerifier);
      return bool;
    }
    catch (CMSException localCMSException)
    {
      if (localCMSException.getUnderlyingException() != null) {
        throw new TSPException(localCMSException.getMessage(), localCMSException.getUnderlyingException());
      }
      throw new TSPException("CMS exception: " + localCMSException, localCMSException);
    }
  }
  
  public CMSSignedData toCMSSignedData()
  {
    return this.tsToken;
  }
  
  public void validate(X509Certificate paramX509Certificate, String paramString)
    throws TSPException, TSPValidationException, CertificateExpiredException, CertificateNotYetValidException, NoSuchProviderException
  {
    for (;;)
    {
      int i;
      try
      {
        if (!Arrays.constantTimeAreEqual(this.certID.getCertHash(), MessageDigest.getInstance(this.certID.getHashAlgorithmName()).digest(paramX509Certificate.getEncoded()))) {
          throw new TSPValidationException("certificate hash does not match certID hash.");
        }
      }
      catch (CMSException localCMSException)
      {
        if (localCMSException.getUnderlyingException() == null) {
          break label310;
        }
        throw new TSPException(localCMSException.getMessage(), localCMSException.getUnderlyingException());
        if (this.certID.getIssuerSerial() == null) {
          break;
        }
        if (!this.certID.getIssuerSerial().getSerial().getValue().equals(paramX509Certificate.getSerialNumber())) {
          throw new TSPValidationException("certificate serial number does not match certID for signature.");
        }
      }
      catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
      {
        throw new TSPException("cannot find algorithm: " + localNoSuchAlgorithmException, localNoSuchAlgorithmException);
        GeneralName[] arrayOfGeneralName = this.certID.getIssuerSerial().getIssuer().getNames();
        X509Principal localX509Principal = PrincipalUtil.getIssuerX509Principal(paramX509Certificate);
        i = 0;
        int j = arrayOfGeneralName.length;
        int k = 0;
        if (i != j)
        {
          if ((arrayOfGeneralName[i].getTagNo() == 4) && (new X509Principal(X509Name.getInstance(arrayOfGeneralName[i].getName())).equals(localX509Principal))) {
            k = 1;
          }
        }
        else
        {
          if (k != 0) {
            break;
          }
          throw new TSPValidationException("certificate name does not match certID for signature. ");
        }
      }
      catch (CertificateEncodingException localCertificateEncodingException)
      {
        throw new TSPException("problem processing certificate: " + localCertificateEncodingException, localCertificateEncodingException);
      }
      i++;
    }
    TSPUtil.validateCertificate(paramX509Certificate);
    paramX509Certificate.checkValidity(this.tstInfo.getGenTime());
    if (!this.tsaSignerInfo.verify(paramX509Certificate, paramString))
    {
      throw new TSPValidationException("signature not created by certificate.");
      label310:
      throw new TSPException("CMS exception: " + localCMSException, localCMSException);
    }
  }
  
  public void validate(SignerInformationVerifier paramSignerInformationVerifier)
    throws TSPException, TSPValidationException
  {
    if (!paramSignerInformationVerifier.hasAssociatedCertificate()) {
      throw new IllegalArgumentException("verifier provider needs an associated certificate");
    }
    X509CertificateHolder localX509CertificateHolder;
    for (;;)
    {
      int i;
      try
      {
        localX509CertificateHolder = paramSignerInformationVerifier.getAssociatedCertificate();
        DigestCalculator localDigestCalculator = paramSignerInformationVerifier.getDigestCalculator(this.certID.getHashAlgorithm());
        OutputStream localOutputStream = localDigestCalculator.getOutputStream();
        localOutputStream.write(localX509CertificateHolder.getEncoded());
        localOutputStream.close();
        if (!Arrays.constantTimeAreEqual(this.certID.getCertHash(), localDigestCalculator.getDigest())) {
          throw new TSPValidationException("certificate hash does not match certID hash.");
        }
      }
      catch (CMSException localCMSException)
      {
        if (localCMSException.getUnderlyingException() == null) {
          break label381;
        }
        throw new TSPException(localCMSException.getMessage(), localCMSException.getUnderlyingException());
        if (this.certID.getIssuerSerial() == null) {
          break;
        }
        localIssuerAndSerialNumber = new IssuerAndSerialNumber(localX509CertificateHolder.toASN1Structure());
        if (!this.certID.getIssuerSerial().getSerial().equals(localIssuerAndSerialNumber.getSerialNumber())) {
          throw new TSPValidationException("certificate serial number does not match certID for signature.");
        }
      }
      catch (IOException localIOException)
      {
        IssuerAndSerialNumber localIssuerAndSerialNumber;
        throw new TSPException("problem processing certificate: " + localIOException, localIOException);
        GeneralName[] arrayOfGeneralName = this.certID.getIssuerSerial().getIssuer().getNames();
        i = 0;
        int j = arrayOfGeneralName.length;
        int k = 0;
        if (i != j)
        {
          if ((arrayOfGeneralName[i].getTagNo() == 4) && (X500Name.getInstance(arrayOfGeneralName[i].getName()).equals(X500Name.getInstance(localIssuerAndSerialNumber.getName())))) {
            k = 1;
          }
        }
        else
        {
          if (k != 0) {
            break;
          }
          throw new TSPValidationException("certificate name does not match certID for signature. ");
        }
      }
      catch (OperatorCreationException localOperatorCreationException)
      {
        throw new TSPException("unable to create digest: " + localOperatorCreationException.getMessage(), localOperatorCreationException);
      }
      i++;
    }
    TSPUtil.validateCertificate(localX509CertificateHolder);
    if (!localX509CertificateHolder.isValidOn(this.tstInfo.getGenTime())) {
      throw new TSPValidationException("certificate not valid when time stamp created.");
    }
    if (!this.tsaSignerInfo.verify(paramSignerInformationVerifier))
    {
      throw new TSPValidationException("signature not created by certificate.");
      label381:
      throw new TSPException("CMS exception: " + localCMSException, localCMSException);
    }
  }
  
  private class CertID
  {
    private ESSCertID certID;
    private ESSCertIDv2 certIDv2;
    
    CertID(ESSCertID paramESSCertID)
    {
      this.certID = paramESSCertID;
      this.certIDv2 = null;
    }
    
    CertID(ESSCertIDv2 paramESSCertIDv2)
    {
      this.certIDv2 = paramESSCertIDv2;
      this.certID = null;
    }
    
    public byte[] getCertHash()
    {
      if (this.certID != null) {
        return this.certID.getCertHash();
      }
      return this.certIDv2.getCertHash();
    }
    
    public AlgorithmIdentifier getHashAlgorithm()
    {
      if (this.certID != null) {
        return new AlgorithmIdentifier(OIWObjectIdentifiers.idSHA1);
      }
      return this.certIDv2.getHashAlgorithm();
    }
    
    public String getHashAlgorithmName()
    {
      if (this.certID != null) {
        return "SHA-1";
      }
      if (NISTObjectIdentifiers.id_sha256.equals(this.certIDv2.getHashAlgorithm().getAlgorithm())) {
        return "SHA-256";
      }
      return this.certIDv2.getHashAlgorithm().getAlgorithm().getId();
    }
    
    public IssuerSerial getIssuerSerial()
    {
      if (this.certID != null) {
        return this.certID.getIssuerSerial();
      }
      return this.certIDv2.getIssuerSerial();
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.tsp.TimeStampToken
 * JD-Core Version:    0.7.0.1
 */