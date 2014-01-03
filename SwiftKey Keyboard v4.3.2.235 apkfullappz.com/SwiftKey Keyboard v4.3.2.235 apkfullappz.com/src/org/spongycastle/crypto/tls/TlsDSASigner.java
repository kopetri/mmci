package org.spongycastle.crypto.tls;

import java.security.SecureRandom;
import org.spongycastle.crypto.CryptoException;
import org.spongycastle.crypto.DSA;
import org.spongycastle.crypto.Signer;
import org.spongycastle.crypto.digests.NullDigest;
import org.spongycastle.crypto.digests.SHA1Digest;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.crypto.signers.DSADigestSigner;

abstract class TlsDSASigner
  implements TlsSigner
{
  public byte[] calculateRawSignature(SecureRandom paramSecureRandom, AsymmetricKeyParameter paramAsymmetricKeyParameter, byte[] paramArrayOfByte)
    throws CryptoException
  {
    DSADigestSigner localDSADigestSigner = new DSADigestSigner(createDSAImpl(), new NullDigest());
    localDSADigestSigner.init(true, new ParametersWithRandom(paramAsymmetricKeyParameter, paramSecureRandom));
    localDSADigestSigner.update(paramArrayOfByte, 16, 20);
    return localDSADigestSigner.generateSignature();
  }
  
  protected abstract DSA createDSAImpl();
  
  public Signer createVerifyer(AsymmetricKeyParameter paramAsymmetricKeyParameter)
  {
    DSADigestSigner localDSADigestSigner = new DSADigestSigner(createDSAImpl(), new SHA1Digest());
    localDSADigestSigner.init(false, paramAsymmetricKeyParameter);
    return localDSADigestSigner;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.tls.TlsDSASigner
 * JD-Core Version:    0.7.0.1
 */