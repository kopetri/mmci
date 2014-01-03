package org.spongycastle.crypto.tls;

import java.security.SecureRandom;
import org.spongycastle.crypto.CryptoException;
import org.spongycastle.crypto.Signer;
import org.spongycastle.crypto.digests.NullDigest;
import org.spongycastle.crypto.encodings.PKCS1Encoding;
import org.spongycastle.crypto.engines.RSABlindedEngine;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.crypto.params.RSAKeyParameters;
import org.spongycastle.crypto.signers.GenericSigner;

class TlsRSASigner
  implements TlsSigner
{
  public byte[] calculateRawSignature(SecureRandom paramSecureRandom, AsymmetricKeyParameter paramAsymmetricKeyParameter, byte[] paramArrayOfByte)
    throws CryptoException
  {
    GenericSigner localGenericSigner = new GenericSigner(new PKCS1Encoding(new RSABlindedEngine()), new NullDigest());
    localGenericSigner.init(true, new ParametersWithRandom(paramAsymmetricKeyParameter, paramSecureRandom));
    localGenericSigner.update(paramArrayOfByte, 0, paramArrayOfByte.length);
    return localGenericSigner.generateSignature();
  }
  
  public Signer createVerifyer(AsymmetricKeyParameter paramAsymmetricKeyParameter)
  {
    GenericSigner localGenericSigner = new GenericSigner(new PKCS1Encoding(new RSABlindedEngine()), new CombinedHash());
    localGenericSigner.init(false, paramAsymmetricKeyParameter);
    return localGenericSigner;
  }
  
  public boolean isValidPublicKey(AsymmetricKeyParameter paramAsymmetricKeyParameter)
  {
    return ((paramAsymmetricKeyParameter instanceof RSAKeyParameters)) && (!paramAsymmetricKeyParameter.isPrivate());
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.tls.TlsRSASigner
 * JD-Core Version:    0.7.0.1
 */