package org.spongycastle.crypto.encodings;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.SecureRandom;
import org.spongycastle.crypto.AsymmetricBlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.params.ParametersWithRandom;

public class PKCS1Encoding
  implements AsymmetricBlockCipher
{
  private static final int HEADER_LENGTH = 10;
  public static final String STRICT_LENGTH_ENABLED_PROPERTY = "org.spongycastle.pkcs1.strict";
  private AsymmetricBlockCipher engine;
  private boolean forEncryption;
  private boolean forPrivateKey;
  private SecureRandom random;
  private boolean useStrictLength;
  
  public PKCS1Encoding(AsymmetricBlockCipher paramAsymmetricBlockCipher)
  {
    this.engine = paramAsymmetricBlockCipher;
    this.useStrictLength = useStrict();
  }
  
  private byte[] decodeBlock(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws InvalidCipherTextException
  {
    byte[] arrayOfByte1 = this.engine.processBlock(paramArrayOfByte, paramInt1, paramInt2);
    if (arrayOfByte1.length < getOutputBlockSize()) {
      throw new InvalidCipherTextException("block truncated");
    }
    int i = arrayOfByte1[0];
    if ((i != 1) && (i != 2)) {
      throw new InvalidCipherTextException("unknown block type");
    }
    if ((this.useStrictLength) && (arrayOfByte1.length != this.engine.getOutputBlockSize())) {
      throw new InvalidCipherTextException("block incorrect size");
    }
    for (int j = 1; j != arrayOfByte1.length; j++)
    {
      int m = arrayOfByte1[j];
      if (m == 0) {
        break;
      }
      if ((i == 1) && (m != -1)) {
        throw new InvalidCipherTextException("block padding incorrect");
      }
    }
    int k = j + 1;
    if ((k > arrayOfByte1.length) || (k < 10)) {
      throw new InvalidCipherTextException("no data in block");
    }
    byte[] arrayOfByte2 = new byte[arrayOfByte1.length - k];
    System.arraycopy(arrayOfByte1, k, arrayOfByte2, 0, arrayOfByte2.length);
    return arrayOfByte2;
  }
  
  private byte[] encodeBlock(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws InvalidCipherTextException
  {
    if (paramInt2 > getInputBlockSize()) {
      throw new IllegalArgumentException("input data too large");
    }
    byte[] arrayOfByte = new byte[this.engine.getInputBlockSize()];
    if (this.forPrivateKey)
    {
      arrayOfByte[0] = 1;
      for (int j = 1; j != -1 + (arrayOfByte.length - paramInt2); j++) {
        arrayOfByte[j] = -1;
      }
    }
    this.random.nextBytes(arrayOfByte);
    arrayOfByte[0] = 2;
    for (int i = 1; i != -1 + (arrayOfByte.length - paramInt2); i++) {
      while (arrayOfByte[i] == 0) {
        arrayOfByte[i] = ((byte)this.random.nextInt());
      }
    }
    arrayOfByte[(-1 + (arrayOfByte.length - paramInt2))] = 0;
    System.arraycopy(paramArrayOfByte, paramInt1, arrayOfByte, arrayOfByte.length - paramInt2, paramInt2);
    return this.engine.processBlock(arrayOfByte, 0, arrayOfByte.length);
  }
  
  private boolean useStrict()
  {
    String str = (String)AccessController.doPrivileged(new PrivilegedAction()
    {
      public Object run()
      {
        return System.getProperty("org.spongycastle.pkcs1.strict");
      }
    });
    return (str == null) || (str.equals("true"));
  }
  
  public int getInputBlockSize()
  {
    int i = this.engine.getInputBlockSize();
    if (this.forEncryption) {
      i -= 10;
    }
    return i;
  }
  
  public int getOutputBlockSize()
  {
    int i = this.engine.getOutputBlockSize();
    if (this.forEncryption) {
      return i;
    }
    return i - 10;
  }
  
  public AsymmetricBlockCipher getUnderlyingCipher()
  {
    return this.engine;
  }
  
  public void init(boolean paramBoolean, CipherParameters paramCipherParameters)
  {
    ParametersWithRandom localParametersWithRandom;
    if ((paramCipherParameters instanceof ParametersWithRandom))
    {
      localParametersWithRandom = (ParametersWithRandom)paramCipherParameters;
      this.random = localParametersWithRandom.getRandom();
    }
    for (AsymmetricKeyParameter localAsymmetricKeyParameter = (AsymmetricKeyParameter)localParametersWithRandom.getParameters();; localAsymmetricKeyParameter = (AsymmetricKeyParameter)paramCipherParameters)
    {
      this.engine.init(paramBoolean, paramCipherParameters);
      this.forPrivateKey = localAsymmetricKeyParameter.isPrivate();
      this.forEncryption = paramBoolean;
      return;
      this.random = new SecureRandom();
    }
  }
  
  public byte[] processBlock(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws InvalidCipherTextException
  {
    if (this.forEncryption) {
      return encodeBlock(paramArrayOfByte, paramInt1, paramInt2);
    }
    return decodeBlock(paramArrayOfByte, paramInt1, paramInt2);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.encodings.PKCS1Encoding
 * JD-Core Version:    0.7.0.1
 */