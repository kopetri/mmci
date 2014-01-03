package org.spongycastle.crypto.agreement;

import java.math.BigInteger;
import org.spongycastle.crypto.BasicAgreement;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.params.ECDomainParameters;
import org.spongycastle.crypto.params.ECPrivateKeyParameters;
import org.spongycastle.crypto.params.ECPublicKeyParameters;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.ec.ECPoint;

public class ECDHCBasicAgreement
  implements BasicAgreement
{
  ECPrivateKeyParameters key;
  
  public BigInteger calculateAgreement(CipherParameters paramCipherParameters)
  {
    ECPublicKeyParameters localECPublicKeyParameters = (ECPublicKeyParameters)paramCipherParameters;
    ECDomainParameters localECDomainParameters = localECPublicKeyParameters.getParameters();
    return localECPublicKeyParameters.getQ().multiply(localECDomainParameters.getH().multiply(this.key.getD())).getX().toBigInteger();
  }
  
  public void init(CipherParameters paramCipherParameters)
  {
    this.key = ((ECPrivateKeyParameters)paramCipherParameters);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.agreement.ECDHCBasicAgreement
 * JD-Core Version:    0.7.0.1
 */