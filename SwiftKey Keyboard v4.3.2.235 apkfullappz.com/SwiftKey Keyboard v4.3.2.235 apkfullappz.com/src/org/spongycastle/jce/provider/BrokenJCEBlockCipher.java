package org.spongycastle.jce.provider;

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
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.RC2ParameterSpec;
import javax.crypto.spec.RC5ParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.BufferedBlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.engines.DESEngine;
import org.spongycastle.crypto.engines.DESedeEngine;
import org.spongycastle.crypto.engines.TwofishEngine;
import org.spongycastle.crypto.modes.CBCBlockCipher;
import org.spongycastle.crypto.modes.CFBBlockCipher;
import org.spongycastle.crypto.modes.CTSBlockCipher;
import org.spongycastle.crypto.modes.OFBBlockCipher;
import org.spongycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.crypto.params.RC2Parameters;
import org.spongycastle.crypto.params.RC5Parameters;
import org.spongycastle.jcajce.provider.symmetric.util.BCPBEKey;
import org.spongycastle.util.Strings;

public class BrokenJCEBlockCipher
  implements BrokenPBE
{
  private Class[] availableSpecs = { IvParameterSpec.class, PBEParameterSpec.class, RC2ParameterSpec.class, RC5ParameterSpec.class };
  private BufferedBlockCipher cipher;
  private AlgorithmParameters engineParams = null;
  private int ivLength = 0;
  private ParametersWithIV ivParam;
  private int pbeHash = 1;
  private int pbeIvSize;
  private int pbeKeySize;
  private int pbeType = 2;
  
  protected BrokenJCEBlockCipher(BlockCipher paramBlockCipher)
  {
    this.cipher = new PaddedBufferedBlockCipher(paramBlockCipher);
  }
  
  protected BrokenJCEBlockCipher(BlockCipher paramBlockCipher, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.cipher = new PaddedBufferedBlockCipher(paramBlockCipher);
    this.pbeType = paramInt1;
    this.pbeHash = paramInt2;
    this.pbeKeySize = paramInt3;
    this.pbeIvSize = paramInt4;
  }
  
  protected int engineDoFinal(byte[] paramArrayOfByte1, int paramInt1, int paramInt2, byte[] paramArrayOfByte2, int paramInt3)
    throws IllegalBlockSizeException, BadPaddingException
  {
    int i = 0;
    if (paramInt2 != 0) {
      i = this.cipher.processBytes(paramArrayOfByte1, paramInt1, paramInt2, paramArrayOfByte2, paramInt3);
    }
    try
    {
      int j = this.cipher.doFinal(paramArrayOfByte2, paramInt3 + i);
      return j + i;
    }
    catch (DataLengthException localDataLengthException)
    {
      throw new IllegalBlockSizeException(localDataLengthException.getMessage());
    }
    catch (InvalidCipherTextException localInvalidCipherTextException)
    {
      throw new BadPaddingException(localInvalidCipherTextException.getMessage());
    }
  }
  
  protected byte[] engineDoFinal(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IllegalBlockSizeException, BadPaddingException
  {
    byte[] arrayOfByte1 = new byte[engineGetOutputSize(paramInt2)];
    int i = 0;
    if (paramInt2 != 0) {
      i = this.cipher.processBytes(paramArrayOfByte, paramInt1, paramInt2, arrayOfByte1, 0);
    }
    try
    {
      int j = this.cipher.doFinal(arrayOfByte1, i);
      int k = i + j;
      byte[] arrayOfByte2 = new byte[k];
      System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, k);
      return arrayOfByte2;
    }
    catch (DataLengthException localDataLengthException)
    {
      throw new IllegalBlockSizeException(localDataLengthException.getMessage());
    }
    catch (InvalidCipherTextException localInvalidCipherTextException)
    {
      throw new BadPaddingException(localInvalidCipherTextException.getMessage());
    }
  }
  
  protected int engineGetBlockSize()
  {
    return this.cipher.getBlockSize();
  }
  
  protected byte[] engineGetIV()
  {
    if (this.ivParam != null) {
      return this.ivParam.getIV();
    }
    return null;
  }
  
  protected int engineGetKeySize(Key paramKey)
  {
    return paramKey.getEncoded().length;
  }
  
  protected int engineGetOutputSize(int paramInt)
  {
    return this.cipher.getOutputSize(paramInt);
  }
  
  protected AlgorithmParameters engineGetParameters()
  {
    String str;
    if ((this.engineParams == null) && (this.ivParam != null))
    {
      str = this.cipher.getUnderlyingCipher().getAlgorithmName();
      if (str.indexOf('/') >= 0) {
        str = str.substring(0, str.indexOf('/'));
      }
    }
    try
    {
      this.engineParams = AlgorithmParameters.getInstance(str, BouncyCastleProvider.PROVIDER_NAME);
      this.engineParams.init(this.ivParam.getIV());
      return this.engineParams;
    }
    catch (Exception localException)
    {
      throw new RuntimeException(localException.toString());
    }
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
    Object localObject;
    if ((paramKey instanceof BCPBEKey))
    {
      localObject = BrokenPBE.Util.makePBEParameters((BCPBEKey)paramKey, paramAlgorithmParameterSpec, this.pbeType, this.pbeHash, this.cipher.getUnderlyingCipher().getAlgorithmName(), this.pbeKeySize, this.pbeIvSize);
      if (this.pbeIvSize != 0) {
        this.ivParam = ((ParametersWithIV)localObject);
      }
    }
    label276:
    RC2ParameterSpec localRC2ParameterSpec;
    do
    {
      for (;;)
      {
        if ((this.ivLength != 0) && (!(localObject instanceof ParametersWithIV)))
        {
          if (paramSecureRandom == null) {
            paramSecureRandom = new SecureRandom();
          }
          if ((paramInt != 1) && (paramInt != 3)) {
            break label466;
          }
          byte[] arrayOfByte = new byte[this.ivLength];
          paramSecureRandom.nextBytes(arrayOfByte);
          ParametersWithIV localParametersWithIV1 = new ParametersWithIV((CipherParameters)localObject, arrayOfByte);
          this.ivParam = ((ParametersWithIV)localParametersWithIV1);
          localObject = localParametersWithIV1;
        }
        switch (paramInt)
        {
        default: 
          System.out.println("eeek!");
          return;
          if (paramAlgorithmParameterSpec == null)
          {
            localObject = new KeyParameter(paramKey.getEncoded());
          }
          else
          {
            if (!(paramAlgorithmParameterSpec instanceof IvParameterSpec)) {
              break label276;
            }
            if (this.ivLength != 0)
            {
              localObject = new ParametersWithIV(new KeyParameter(paramKey.getEncoded()), ((IvParameterSpec)paramAlgorithmParameterSpec).getIV());
              this.ivParam = ((ParametersWithIV)localObject);
            }
            else
            {
              localObject = new KeyParameter(paramKey.getEncoded());
            }
          }
          break;
        }
      }
      if (!(paramAlgorithmParameterSpec instanceof RC2ParameterSpec)) {
        break;
      }
      localRC2ParameterSpec = (RC2ParameterSpec)paramAlgorithmParameterSpec;
      localObject = new RC2Parameters(paramKey.getEncoded(), ((RC2ParameterSpec)paramAlgorithmParameterSpec).getEffectiveKeyBits());
    } while ((localRC2ParameterSpec.getIV() == null) || (this.ivLength == 0));
    ParametersWithIV localParametersWithIV2 = new ParametersWithIV((CipherParameters)localObject, localRC2ParameterSpec.getIV());
    for (this.ivParam = ((ParametersWithIV)localParametersWithIV2);; this.ivParam = ((ParametersWithIV)localParametersWithIV2))
    {
      localObject = localParametersWithIV2;
      break;
      if (!(paramAlgorithmParameterSpec instanceof RC5ParameterSpec)) {
        break label456;
      }
      RC5ParameterSpec localRC5ParameterSpec = (RC5ParameterSpec)paramAlgorithmParameterSpec;
      localObject = new RC5Parameters(paramKey.getEncoded(), ((RC5ParameterSpec)paramAlgorithmParameterSpec).getRounds());
      if (localRC5ParameterSpec.getWordSize() != 32) {
        throw new IllegalArgumentException("can only accept RC5 word size 32 (at the moment...)");
      }
      if ((localRC5ParameterSpec.getIV() == null) || (this.ivLength == 0)) {
        break;
      }
      localParametersWithIV2 = new ParametersWithIV((CipherParameters)localObject, localRC5ParameterSpec.getIV());
    }
    label456:
    throw new InvalidAlgorithmParameterException("unknown parameter type.");
    label466:
    throw new InvalidAlgorithmParameterException("no IV set when one expected");
    this.cipher.init(true, (CipherParameters)localObject);
    return;
    this.cipher.init(false, (CipherParameters)localObject);
  }
  
  protected void engineSetMode(String paramString)
  {
    String str = Strings.toUpperCase(paramString);
    if (str.equals("ECB"))
    {
      this.ivLength = 0;
      this.cipher = new PaddedBufferedBlockCipher(this.cipher.getUnderlyingCipher());
      return;
    }
    if (str.equals("CBC"))
    {
      this.ivLength = this.cipher.getUnderlyingCipher().getBlockSize();
      this.cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(this.cipher.getUnderlyingCipher()));
      return;
    }
    if (str.startsWith("OFB"))
    {
      this.ivLength = this.cipher.getUnderlyingCipher().getBlockSize();
      if (str.length() != 3)
      {
        int j = Integer.parseInt(str.substring(3));
        this.cipher = new PaddedBufferedBlockCipher(new OFBBlockCipher(this.cipher.getUnderlyingCipher(), j));
        return;
      }
      this.cipher = new PaddedBufferedBlockCipher(new OFBBlockCipher(this.cipher.getUnderlyingCipher(), 8 * this.cipher.getBlockSize()));
      return;
    }
    if (str.startsWith("CFB"))
    {
      this.ivLength = this.cipher.getUnderlyingCipher().getBlockSize();
      if (str.length() != 3)
      {
        int i = Integer.parseInt(str.substring(3));
        this.cipher = new PaddedBufferedBlockCipher(new CFBBlockCipher(this.cipher.getUnderlyingCipher(), i));
        return;
      }
      this.cipher = new PaddedBufferedBlockCipher(new CFBBlockCipher(this.cipher.getUnderlyingCipher(), 8 * this.cipher.getBlockSize()));
      return;
    }
    throw new IllegalArgumentException("can't support mode " + paramString);
  }
  
  protected void engineSetPadding(String paramString)
    throws NoSuchPaddingException
  {
    String str = Strings.toUpperCase(paramString);
    if (str.equals("NOPADDING"))
    {
      this.cipher = new BufferedBlockCipher(this.cipher.getUnderlyingCipher());
      return;
    }
    if ((str.equals("PKCS5PADDING")) || (str.equals("PKCS7PADDING")) || (str.equals("ISO10126PADDING")))
    {
      this.cipher = new PaddedBufferedBlockCipher(this.cipher.getUnderlyingCipher());
      return;
    }
    if (str.equals("WITHCTS"))
    {
      this.cipher = new CTSBlockCipher(this.cipher.getUnderlyingCipher());
      return;
    }
    throw new NoSuchPaddingException("Padding " + paramString + " unknown.");
  }
  
  protected Key engineUnwrap(byte[] paramArrayOfByte, String paramString, int paramInt)
    throws InvalidKeyException
  {
    byte[] arrayOfByte;
    try
    {
      arrayOfByte = engineDoFinal(paramArrayOfByte, 0, paramArrayOfByte.length);
      if (paramInt == 3) {
        return new SecretKeySpec(arrayOfByte, paramString);
      }
    }
    catch (BadPaddingException localBadPaddingException)
    {
      throw new InvalidKeyException(localBadPaddingException.getMessage());
    }
    catch (IllegalBlockSizeException localIllegalBlockSizeException)
    {
      throw new InvalidKeyException(localIllegalBlockSizeException.getMessage());
    }
    try
    {
      KeyFactory localKeyFactory = KeyFactory.getInstance(paramString, BouncyCastleProvider.PROVIDER_NAME);
      if (paramInt == 1) {
        return localKeyFactory.generatePublic(new X509EncodedKeySpec(arrayOfByte));
      }
      if (paramInt == 2)
      {
        PrivateKey localPrivateKey = localKeyFactory.generatePrivate(new PKCS8EncodedKeySpec(arrayOfByte));
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
  {
    return this.cipher.processBytes(paramArrayOfByte1, paramInt1, paramInt2, paramArrayOfByte2, paramInt3);
  }
  
  protected byte[] engineUpdate(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    int i = this.cipher.getUpdateOutputSize(paramInt2);
    if (i > 0)
    {
      byte[] arrayOfByte = new byte[i];
      this.cipher.processBytes(paramArrayOfByte, paramInt1, paramInt2, arrayOfByte, 0);
      return arrayOfByte;
    }
    this.cipher.processBytes(paramArrayOfByte, paramInt1, paramInt2, null, 0);
    return null;
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
      byte[] arrayOfByte2 = engineDoFinal(arrayOfByte1, 0, arrayOfByte1.length);
      return arrayOfByte2;
    }
    catch (BadPaddingException localBadPaddingException)
    {
      throw new IllegalBlockSizeException(localBadPaddingException.getMessage());
    }
  }
  
  public static class BrokePBEWithMD5AndDES
    extends BrokenJCEBlockCipher
  {
    public BrokePBEWithMD5AndDES()
    {
      super(0, 0, 64, 64);
    }
  }
  
  public static class BrokePBEWithSHA1AndDES
    extends BrokenJCEBlockCipher
  {
    public BrokePBEWithSHA1AndDES()
    {
      super(0, 1, 64, 64);
    }
  }
  
  public static class BrokePBEWithSHAAndDES2Key
    extends BrokenJCEBlockCipher
  {
    public BrokePBEWithSHAAndDES2Key()
    {
      super(2, 1, 128, 64);
    }
  }
  
  public static class BrokePBEWithSHAAndDES3Key
    extends BrokenJCEBlockCipher
  {
    public BrokePBEWithSHAAndDES3Key()
    {
      super(2, 1, 192, 64);
    }
  }
  
  public static class OldPBEWithSHAAndDES3Key
    extends BrokenJCEBlockCipher
  {
    public OldPBEWithSHAAndDES3Key()
    {
      super(3, 1, 192, 64);
    }
  }
  
  public static class OldPBEWithSHAAndTwofish
    extends BrokenJCEBlockCipher
  {
    public OldPBEWithSHAAndTwofish()
    {
      super(3, 1, 256, 128);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jce.provider.BrokenJCEBlockCipher
 * JD-Core Version:    0.7.0.1
 */