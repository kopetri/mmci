package org.spongycastle.jcajce.provider.symmetric.util;

import java.lang.reflect.Constructor;
import java.security.InvalidKeyException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactorySpi;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import org.spongycastle.asn1.DERObjectIdentifier;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.params.DESParameters;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;

public class BaseSecretKeyFactory
  extends SecretKeyFactorySpi
  implements PBE
{
  protected String algName;
  protected DERObjectIdentifier algOid;
  
  protected BaseSecretKeyFactory(String paramString, DERObjectIdentifier paramDERObjectIdentifier)
  {
    this.algName = paramString;
    this.algOid = paramDERObjectIdentifier;
  }
  
  protected SecretKey engineGenerateSecret(KeySpec paramKeySpec)
    throws InvalidKeySpecException
  {
    if ((paramKeySpec instanceof SecretKeySpec)) {
      return (SecretKey)paramKeySpec;
    }
    throw new InvalidKeySpecException("Invalid KeySpec");
  }
  
  protected KeySpec engineGetKeySpec(SecretKey paramSecretKey, Class paramClass)
    throws InvalidKeySpecException
  {
    if (paramClass == null) {
      throw new InvalidKeySpecException("keySpec parameter is null");
    }
    if (paramSecretKey == null) {
      throw new InvalidKeySpecException("key parameter is null");
    }
    if (SecretKeySpec.class.isAssignableFrom(paramClass)) {
      return new SecretKeySpec(paramSecretKey.getEncoded(), this.algName);
    }
    try
    {
      Constructor localConstructor = paramClass.getConstructor(new Class[] { [B.class });
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = paramSecretKey.getEncoded();
      KeySpec localKeySpec = (KeySpec)localConstructor.newInstance(arrayOfObject);
      return localKeySpec;
    }
    catch (Exception localException)
    {
      throw new InvalidKeySpecException(localException.toString());
    }
  }
  
  protected SecretKey engineTranslateKey(SecretKey paramSecretKey)
    throws InvalidKeyException
  {
    if (paramSecretKey == null) {
      throw new InvalidKeyException("key parameter is null");
    }
    if (!paramSecretKey.getAlgorithm().equalsIgnoreCase(this.algName)) {
      throw new InvalidKeyException("Key not of type " + this.algName + ".");
    }
    return new SecretKeySpec(paramSecretKey.getEncoded(), this.algName);
  }
  
  public static class DES
    extends BaseSecretKeyFactory
  {
    public DES()
    {
      super(null);
    }
    
    protected SecretKey engineGenerateSecret(KeySpec paramKeySpec)
      throws InvalidKeySpecException
    {
      if ((paramKeySpec instanceof DESKeySpec)) {
        return new SecretKeySpec(((DESKeySpec)paramKeySpec).getKey(), "DES");
      }
      return super.engineGenerateSecret(paramKeySpec);
    }
  }
  
  public static class DESPBEKeyFactory
    extends BaseSecretKeyFactory
  {
    private int digest;
    private boolean forCipher;
    private int ivSize;
    private int keySize;
    private int scheme;
    
    public DESPBEKeyFactory(String paramString, DERObjectIdentifier paramDERObjectIdentifier, boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      super(paramDERObjectIdentifier);
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
        CipherParameters localCipherParameters;
        if (this.forCipher)
        {
          localCipherParameters = PBE.Util.makePBEParameters(localPBEKeySpec, this.scheme, this.digest, this.keySize, this.ivSize);
          if (!(localCipherParameters instanceof ParametersWithIV)) {
            break label162;
          }
        }
        label162:
        for (KeyParameter localKeyParameter = (KeyParameter)((ParametersWithIV)localCipherParameters).getParameters();; localKeyParameter = (KeyParameter)localCipherParameters)
        {
          DESParameters.setOddParity(localKeyParameter.getKey());
          return new BCPBEKey(this.algName, this.algOid, this.scheme, this.digest, this.keySize, this.ivSize, localPBEKeySpec, localCipherParameters);
          localCipherParameters = PBE.Util.makePBEMacParameters(localPBEKeySpec, this.scheme, this.digest, this.keySize);
          break;
        }
      }
      throw new InvalidKeySpecException("Invalid KeySpec");
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jcajce.provider.symmetric.util.BaseSecretKeyFactory
 * JD-Core Version:    0.7.0.1
 */