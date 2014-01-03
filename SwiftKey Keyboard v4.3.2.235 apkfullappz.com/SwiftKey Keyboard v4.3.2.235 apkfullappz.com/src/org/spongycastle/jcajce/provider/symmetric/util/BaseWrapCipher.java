package org.spongycastle.jcajce.provider.symmetric.util;

import java.io.PrintStream;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.CipherSpi;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.RC2ParameterSpec;
import javax.crypto.spec.RC5ParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.spongycastle.asn1.pkcs.PrivateKeyInfo;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.Wrapper;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.jce.provider.BouncyCastleProvider;

public abstract class BaseWrapCipher
  extends CipherSpi
  implements PBE
{
  private Class[] availableSpecs = { IvParameterSpec.class, PBEParameterSpec.class, RC2ParameterSpec.class, RC5ParameterSpec.class };
  protected AlgorithmParameters engineParams = null;
  private byte[] iv;
  private int ivSize;
  protected int pbeHash = 1;
  protected int pbeIvSize;
  protected int pbeKeySize;
  protected int pbeType = 2;
  protected Wrapper wrapEngine = null;
  
  protected BaseWrapCipher() {}
  
  protected BaseWrapCipher(Wrapper paramWrapper)
  {
    this(paramWrapper, 0);
  }
  
  protected BaseWrapCipher(Wrapper paramWrapper, int paramInt)
  {
    this.wrapEngine = paramWrapper;
    this.ivSize = paramInt;
  }
  
  protected int engineDoFinal(byte[] paramArrayOfByte1, int paramInt1, int paramInt2, byte[] paramArrayOfByte2, int paramInt3)
    throws IllegalBlockSizeException, BadPaddingException
  {
    return 0;
  }
  
  protected byte[] engineDoFinal(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IllegalBlockSizeException, BadPaddingException
  {
    return null;
  }
  
  protected int engineGetBlockSize()
  {
    return 0;
  }
  
  protected byte[] engineGetIV()
  {
    return (byte[])this.iv.clone();
  }
  
  protected int engineGetKeySize(Key paramKey)
  {
    return paramKey.getEncoded().length;
  }
  
  protected int engineGetOutputSize(int paramInt)
  {
    return -1;
  }
  
  protected AlgorithmParameters engineGetParameters()
  {
    return null;
  }
  
  protected void engineInit(int paramInt, Key paramKey, AlgorithmParameters paramAlgorithmParameters, SecureRandom paramSecureRandom)
    throws InvalidKeyException, InvalidAlgorithmParameterException
  {
    Object localObject = null;
    if (paramAlgorithmParameters != null)
    {
      int i = 0;
      for (;;)
      {
        int j = this.availableSpecs.length;
        localObject = null;
        if (i != j) {}
        try
        {
          AlgorithmParameterSpec localAlgorithmParameterSpec = paramAlgorithmParameters.getParameterSpec(this.availableSpecs[i]);
          localObject = localAlgorithmParameterSpec;
          if (localObject != null) {
            break;
          }
          throw new InvalidAlgorithmParameterException("can't handle parameter " + paramAlgorithmParameters.toString());
        }
        catch (Exception localException)
        {
          i++;
        }
      }
    }
    this.engineParams = paramAlgorithmParameters;
    engineInit(paramInt, paramKey, localObject, paramSecureRandom);
  }
  
  protected void engineInit(int paramInt, Key paramKey, SecureRandom paramSecureRandom)
    throws InvalidKeyException
  {
    try
    {
      engineInit(paramInt, paramKey, null, paramSecureRandom);
      return;
    }
    catch (InvalidAlgorithmParameterException localInvalidAlgorithmParameterException)
    {
      throw new IllegalArgumentException(localInvalidAlgorithmParameterException.getMessage());
    }
  }
  
  protected void engineInit(int paramInt, Key paramKey, AlgorithmParameterSpec paramAlgorithmParameterSpec, SecureRandom paramSecureRandom)
    throws InvalidKeyException, InvalidAlgorithmParameterException
  {
    BCPBEKey localBCPBEKey;
    Object localObject;
    if ((paramKey instanceof BCPBEKey))
    {
      localBCPBEKey = (BCPBEKey)paramKey;
      if ((paramAlgorithmParameterSpec instanceof PBEParameterSpec)) {
        localObject = PBE.Util.makePBEParameters(localBCPBEKey, paramAlgorithmParameterSpec, this.wrapEngine.getAlgorithmName());
      }
    }
    for (;;)
    {
      if ((paramAlgorithmParameterSpec instanceof IvParameterSpec)) {
        localObject = new ParametersWithIV((CipherParameters)localObject, ((IvParameterSpec)paramAlgorithmParameterSpec).getIV());
      }
      if (((localObject instanceof KeyParameter)) && (this.ivSize != 0))
      {
        this.iv = new byte[this.ivSize];
        paramSecureRandom.nextBytes(this.iv);
        localObject = new ParametersWithIV((CipherParameters)localObject, this.iv);
      }
      switch (paramInt)
      {
      default: 
        System.out.println("eeek!");
        return;
        if (localBCPBEKey.getParam() != null)
        {
          localObject = localBCPBEKey.getParam();
        }
        else
        {
          throw new InvalidAlgorithmParameterException("PBE requires PBE parameters to be set.");
          localObject = new KeyParameter(paramKey.getEncoded());
        }
        break;
      }
    }
    this.wrapEngine.init(true, (CipherParameters)localObject);
    return;
    this.wrapEngine.init(false, (CipherParameters)localObject);
    return;
    throw new IllegalArgumentException("engine only valid for wrapping");
  }
  
  protected void engineSetMode(String paramString)
    throws NoSuchAlgorithmException
  {
    throw new NoSuchAlgorithmException("can't support mode " + paramString);
  }
  
  protected void engineSetPadding(String paramString)
    throws NoSuchPaddingException
  {
    throw new NoSuchPaddingException("Padding " + paramString + " unknown.");
  }
  
  protected Key engineUnwrap(byte[] paramArrayOfByte, String paramString, int paramInt)
    throws InvalidKeyException
  {
    Object localObject1;
    for (;;)
    {
      Object localObject2;
      try
      {
        if (this.wrapEngine == null)
        {
          byte[] arrayOfByte2 = engineDoFinal(paramArrayOfByte, 0, paramArrayOfByte.length);
          localObject1 = arrayOfByte2;
          if (paramInt == 3)
          {
            localObject2 = new SecretKeySpec((byte[])localObject1, paramString);
            return localObject2;
          }
        }
        else
        {
          byte[] arrayOfByte1 = this.wrapEngine.unwrap(paramArrayOfByte, 0, paramArrayOfByte.length);
          localObject1 = arrayOfByte1;
          continue;
        }
        if (!paramString.equals("")) {
          break;
        }
      }
      catch (InvalidCipherTextException localInvalidCipherTextException)
      {
        throw new InvalidKeyException(localInvalidCipherTextException.getMessage());
      }
      catch (BadPaddingException localBadPaddingException)
      {
        throw new InvalidKeyException(localBadPaddingException.getMessage());
      }
      catch (IllegalBlockSizeException localIllegalBlockSizeException)
      {
        throw new InvalidKeyException(localIllegalBlockSizeException.getMessage());
      }
      if (paramInt == 2) {
        try
        {
          PrivateKeyInfo localPrivateKeyInfo = PrivateKeyInfo.getInstance(localObject1);
          localObject2 = BouncyCastleProvider.getPrivateKey(localPrivateKeyInfo);
          if (localObject2 == null) {
            throw new InvalidKeyException("algorithm " + localPrivateKeyInfo.getPrivateKeyAlgorithm().getAlgorithm() + " not supported");
          }
        }
        catch (Exception localException)
        {
          throw new InvalidKeyException("Invalid key encoding.");
        }
      }
    }
    try
    {
      KeyFactory localKeyFactory = KeyFactory.getInstance(paramString, BouncyCastleProvider.PROVIDER_NAME);
      if (paramInt == 1) {
        return localKeyFactory.generatePublic(new X509EncodedKeySpec((byte[])localObject1));
      }
      if (paramInt == 2)
      {
        PrivateKey localPrivateKey = localKeyFactory.generatePrivate(new PKCS8EncodedKeySpec((byte[])localObject1));
        return localPrivateKey;
      }
    }
    catch (NoSuchProviderException localNoSuchProviderException)
    {
      throw new InvalidKeyException("Unknown key type " + localNoSuchProviderException.getMessage());
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      throw new InvalidKeyException("Unknown key type " + localNoSuchAlgorithmException.getMessage());
    }
    catch (InvalidKeySpecException localInvalidKeySpecException)
    {
      throw new InvalidKeyException("Unknown key type " + localInvalidKeySpecException.getMessage());
    }
    throw new InvalidKeyException("Unknown key type " + paramInt);
  }
  
  protected int engineUpdate(byte[] paramArrayOfByte1, int paramInt1, int paramInt2, byte[] paramArrayOfByte2, int paramInt3)
    throws ShortBufferException
  {
    throw new RuntimeException("not supported for wrapping");
  }
  
  protected byte[] engineUpdate(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    throw new RuntimeException("not supported for wrapping");
  }
  
  protected byte[] engineWrap(Key paramKey)
    throws IllegalBlockSizeException, InvalidKeyException
  {
    byte[] arrayOfByte1 = paramKey.getEncoded();
    if (arrayOfByte1 == null) {
      throw new InvalidKeyException("Cannot wrap key, null encoding.");
    }
    try
    {
      if (this.wrapEngine == null) {
        return engineDoFinal(arrayOfByte1, 0, arrayOfByte1.length);
      }
      byte[] arrayOfByte2 = this.wrapEngine.wrap(arrayOfByte1, 0, arrayOfByte1.length);
      return arrayOfByte2;
    }
    catch (BadPaddingException localBadPaddingException)
    {
      throw new IllegalBlockSizeException(localBadPaddingException.getMessage());
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jcajce.provider.symmetric.util.BaseWrapCipher
 * JD-Core Version:    0.7.0.1
 */