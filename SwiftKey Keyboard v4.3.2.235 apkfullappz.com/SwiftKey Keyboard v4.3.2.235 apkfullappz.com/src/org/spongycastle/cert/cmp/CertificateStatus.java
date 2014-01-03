package org.spongycastle.cert.cmp;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.cmp.CertStatus;
import org.spongycastle.asn1.cmp.PKIStatusInfo;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.Certificate;
import org.spongycastle.cert.X509CertificateHolder;
import org.spongycastle.operator.DigestAlgorithmIdentifierFinder;
import org.spongycastle.operator.DigestCalculator;
import org.spongycastle.operator.DigestCalculatorProvider;
import org.spongycastle.operator.OperatorCreationException;
import org.spongycastle.util.Arrays;

public class CertificateStatus
{
  private CertStatus certStatus;
  private DigestAlgorithmIdentifierFinder digestAlgFinder;
  
  CertificateStatus(DigestAlgorithmIdentifierFinder paramDigestAlgorithmIdentifierFinder, CertStatus paramCertStatus)
  {
    this.digestAlgFinder = paramDigestAlgorithmIdentifierFinder;
    this.certStatus = paramCertStatus;
  }
  
  public BigInteger getCertRequestID()
  {
    return this.certStatus.getCertReqId().getValue();
  }
  
  public PKIStatusInfo getStatusInfo()
  {
    return this.certStatus.getStatusInfo();
  }
  
  public boolean isVerified(X509CertificateHolder paramX509CertificateHolder, DigestCalculatorProvider paramDigestCalculatorProvider)
    throws CMPException
  {
    AlgorithmIdentifier localAlgorithmIdentifier = this.digestAlgFinder.find(paramX509CertificateHolder.toASN1Structure().getSignatureAlgorithm());
    if (localAlgorithmIdentifier == null) {
      throw new CMPException("cannot find algorithm for digest from signature");
    }
    try
    {
      DigestCalculator localDigestCalculator = paramDigestCalculatorProvider.get(localAlgorithmIdentifier);
      CMPUtil.derEncodeToStream(paramX509CertificateHolder.toASN1Structure(), localDigestCalculator.getOutputStream());
      return Arrays.areEqual(this.certStatus.getCertHash().getOctets(), localDigestCalculator.getDigest());
    }
    catch (OperatorCreationException localOperatorCreationException)
    {
      throw new CMPException("unable to create digester: " + localOperatorCreationException.getMessage(), localOperatorCreationException);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cert.cmp.CertificateStatus
 * JD-Core Version:    0.7.0.1
 */