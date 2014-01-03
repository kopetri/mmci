package org.spongycastle.crypto.tls;

import java.security.SecureRandom;
import org.spongycastle.crypto.CryptoException;
import org.spongycastle.crypto.Signer;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;

abstract interface TlsSigner
{
  public abstract byte[] calculateRawSignature(SecureRandom paramSecureRandom, AsymmetricKeyParameter paramAsymmetricKeyParameter, byte[] paramArrayOfByte)
    throws CryptoException;
  
  public abstract Signer createVerifyer(AsymmetricKeyParameter paramAsymmetricKeyParameter);
  
  public abstract boolean isValidPublicKey(AsymmetricKeyParameter paramAsymmetricKeyParameter);
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.tls.TlsSigner
 * JD-Core Version:    0.7.0.1
 */