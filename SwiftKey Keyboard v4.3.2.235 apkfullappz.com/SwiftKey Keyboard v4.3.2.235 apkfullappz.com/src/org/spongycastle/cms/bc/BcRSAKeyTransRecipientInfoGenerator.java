package org.spongycastle.cms.bc;

import java.io.IOException;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.cert.X509CertificateHolder;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.operator.bc.BcRSAAsymmetricKeyWrapper;

public class BcRSAKeyTransRecipientInfoGenerator
  extends BcKeyTransRecipientInfoGenerator
{
  public BcRSAKeyTransRecipientInfoGenerator(X509CertificateHolder paramX509CertificateHolder)
    throws IOException
  {
    super(paramX509CertificateHolder, new BcRSAAsymmetricKeyWrapper(paramX509CertificateHolder.getSubjectPublicKeyInfo().getAlgorithmId(), paramX509CertificateHolder.getSubjectPublicKeyInfo()));
  }
  
  public BcRSAKeyTransRecipientInfoGenerator(byte[] paramArrayOfByte, AlgorithmIdentifier paramAlgorithmIdentifier, AsymmetricKeyParameter paramAsymmetricKeyParameter)
  {
    super(paramArrayOfByte, new BcRSAAsymmetricKeyWrapper(paramAlgorithmIdentifier, paramAsymmetricKeyParameter));
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.bc.BcRSAKeyTransRecipientInfoGenerator
 * JD-Core Version:    0.7.0.1
 */