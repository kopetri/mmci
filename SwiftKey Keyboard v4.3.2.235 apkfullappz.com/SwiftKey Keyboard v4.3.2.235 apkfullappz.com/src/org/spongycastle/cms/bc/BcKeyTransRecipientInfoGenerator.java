package org.spongycastle.cms.bc;

import org.spongycastle.asn1.cms.IssuerAndSerialNumber;
import org.spongycastle.cert.X509CertificateHolder;
import org.spongycastle.cms.KeyTransRecipientInfoGenerator;
import org.spongycastle.operator.bc.BcAsymmetricKeyWrapper;

public abstract class BcKeyTransRecipientInfoGenerator
  extends KeyTransRecipientInfoGenerator
{
  public BcKeyTransRecipientInfoGenerator(X509CertificateHolder paramX509CertificateHolder, BcAsymmetricKeyWrapper paramBcAsymmetricKeyWrapper)
  {
    super(new IssuerAndSerialNumber(paramX509CertificateHolder.toASN1Structure()), paramBcAsymmetricKeyWrapper);
  }
  
  public BcKeyTransRecipientInfoGenerator(byte[] paramArrayOfByte, BcAsymmetricKeyWrapper paramBcAsymmetricKeyWrapper)
  {
    super(paramArrayOfByte, paramBcAsymmetricKeyWrapper);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.bc.BcKeyTransRecipientInfoGenerator
 * JD-Core Version:    0.7.0.1
 */