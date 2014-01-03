package org.spongycastle.cert.cmp;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.cmp.CertConfirmContent;
import org.spongycastle.asn1.cmp.CertStatus;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.Certificate;
import org.spongycastle.cert.X509CertificateHolder;
import org.spongycastle.operator.DefaultDigestAlgorithmIdentifierFinder;
import org.spongycastle.operator.DigestAlgorithmIdentifierFinder;
import org.spongycastle.operator.DigestCalculator;
import org.spongycastle.operator.DigestCalculatorProvider;
import org.spongycastle.operator.OperatorCreationException;

public class CertificateConfirmationContentBuilder
{
  private List acceptedCerts = new ArrayList();
  private List acceptedReqIds = new ArrayList();
  private DigestAlgorithmIdentifierFinder digestAlgFinder;
  
  public CertificateConfirmationContentBuilder()
  {
    this(new DefaultDigestAlgorithmIdentifierFinder());
  }
  
  public CertificateConfirmationContentBuilder(DigestAlgorithmIdentifierFinder paramDigestAlgorithmIdentifierFinder)
  {
    this.digestAlgFinder = paramDigestAlgorithmIdentifierFinder;
  }
  
  public CertificateConfirmationContentBuilder addAcceptedCertificate(X509CertificateHolder paramX509CertificateHolder, BigInteger paramBigInteger)
  {
    this.acceptedCerts.add(paramX509CertificateHolder);
    this.acceptedReqIds.add(paramBigInteger);
    return this;
  }
  
  public CertificateConfirmationContent build(DigestCalculatorProvider paramDigestCalculatorProvider)
    throws CMPException
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    int i = 0;
    while (i != this.acceptedCerts.size())
    {
      X509CertificateHolder localX509CertificateHolder = (X509CertificateHolder)this.acceptedCerts.get(i);
      BigInteger localBigInteger = (BigInteger)this.acceptedReqIds.get(i);
      AlgorithmIdentifier localAlgorithmIdentifier = this.digestAlgFinder.find(localX509CertificateHolder.toASN1Structure().getSignatureAlgorithm());
      if (localAlgorithmIdentifier == null) {
        throw new CMPException("cannot find algorithm for digest from signature");
      }
      try
      {
        DigestCalculator localDigestCalculator = paramDigestCalculatorProvider.get(localAlgorithmIdentifier);
        CMPUtil.derEncodeToStream(localX509CertificateHolder.toASN1Structure(), localDigestCalculator.getOutputStream());
        localASN1EncodableVector.add(new CertStatus(localDigestCalculator.getDigest(), localBigInteger));
        i++;
      }
      catch (OperatorCreationException localOperatorCreationException)
      {
        throw new CMPException("unable to create digest: " + localOperatorCreationException.getMessage(), localOperatorCreationException);
      }
    }
    return new CertificateConfirmationContent(CertConfirmContent.getInstance(new DERSequence(localASN1EncodableVector)), this.digestAlgFinder);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cert.cmp.CertificateConfirmationContentBuilder
 * JD-Core Version:    0.7.0.1
 */