package org.spongycastle.jcajce.provider.symmetric.util;

import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import javax.crypto.SecretKey;
import javax.crypto.spec.PBEKeySpec;
import org.spongycastle.asn1.DERObjectIdentifier;
import org.spongycastle.crypto.CipherParameters;

public class PBESecretKeyFactory
  extends BaseSecretKeyFactory
  implements PBE
{
  private int digest;
  private boolean forCipher;
  private int ivSize;
  private int keySize;
  private int scheme;
  
  public PBESecretKeyFactory(String paramString, DERObjectIdentifier paramDERObjectIdentifier, boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super(paramString, paramDERObjectIdentifier);
    this.forCipher = paramBoolean;
    this.scheme = paramInt1;
    this.digest = paramInt2;
    this.keySize = paramInt3;
    this.ivSize = paramInt4;
  }
  
  protected SecretKey engineGenerateSecret(KeySpec paramKeySpec)
    throws InvalidKeySpecException
  {
    if ((paramKeySpec instanceof PBEKeySpec))
    {
      PBEKeySpec localPBEKeySpec = (PBEKeySpec)paramKeySpec;
      if (localPBEKeySpec.getSalt() == null) {
        return new BCPBEKey(this.algName, this.algOid, this.scheme, this.digest, this.keySize, this.ivSize, localPBEKeySpec, null);
      }
      if (this.forCipher) {}
      for (CipherParameters localCipherParameters = PBE.Util.makePBEParameters(localPBEKeySpec, this.scheme, this.digest, this.keySize, this.ivSize);; localCipherParameters = PBE.Util.makePBEMacParameters(localPBEKeySpec, this.scheme, this.digest, this.keySize)) {
        return new BCPBEKey(this.algName, this.algOid, this.scheme, this.digest, this.keySize, this.ivSize, localPBEKeySpec, localCipherParameters);
      }
    }
    throw new InvalidKeySpecException("Invalid KeySpec");
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jcajce.provider.symmetric.util.PBESecretKeyFactory
 * JD-Core Version:    0.7.0.1
 */