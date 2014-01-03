package org.spongycastle.jce.provider;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.MacSpi;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEParameterSpec;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Mac;
import org.spongycastle.crypto.digests.MD2Digest;
import org.spongycastle.crypto.digests.MD4Digest;
import org.spongycastle.crypto.digests.MD5Digest;
import org.spongycastle.crypto.digests.RIPEMD128Digest;
import org.spongycastle.crypto.digests.RIPEMD160Digest;
import org.spongycastle.crypto.digests.SHA1Digest;
import org.spongycastle.crypto.digests.SHA224Digest;
import org.spongycastle.crypto.digests.SHA256Digest;
import org.spongycastle.crypto.digests.SHA384Digest;
import org.spongycastle.crypto.digests.SHA512Digest;
import org.spongycastle.crypto.digests.TigerDigest;
import org.spongycastle.crypto.engines.DESEngine;
import org.spongycastle.crypto.engines.RC2Engine;
import org.spongycastle.crypto.macs.CBCBlockCipherMac;
import org.spongycastle.crypto.macs.CFBBlockCipherMac;
import org.spongycastle.crypto.macs.HMac;
import org.spongycastle.crypto.macs.ISO9797Alg3Mac;
import org.spongycastle.crypto.macs.OldHMac;
import org.spongycastle.crypto.paddings.ISO7816d4Padding;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.jcajce.provider.symmetric.util.BCPBEKey;
import org.spongycastle.jcajce.provider.symmetric.util.PBE;
import org.spongycastle.jcajce.provider.symmetric.util.PBE.Util;

public class JCEMac
  extends MacSpi
  implements PBE
{
  private int keySize = 160;
  private Mac macEngine;
  private int pbeHash = 1;
  private int pbeType = 2;
  
  protected JCEMac(Mac paramMac)
  {
    this.macEngine = paramMac;
  }
  
  protected JCEMac(Mac paramMac, int paramInt1, int paramInt2, int paramInt3)
  {
    this.macEngine = paramMac;
    this.pbeType = paramInt1;
    this.pbeHash = paramInt2;
    this.keySize = paramInt3;
  }
  
  protected byte[] engineDoFinal()
  {
    byte[] arrayOfByte = new byte[engineGetMacLength()];
    this.macEngine.doFinal(arrayOfByte, 0);
    return arrayOfByte;
  }
  
  protected int engineGetMacLength()
  {
    return this.macEngine.getMacSize();
  }
  
  protected void engineInit(Key paramKey, AlgorithmParameterSpec paramAlgorithmParameterSpec)
    throws InvalidKeyException, InvalidAlgorithmParameterException
  {
    if (paramKey == null) {
      throw new InvalidKeyException("key is null");
    }
    BCPBEKey localBCPBEKey;
    Object localObject;
    if ((paramKey instanceof BCPBEKey))
    {
      localBCPBEKey = (BCPBEKey)paramKey;
      if (localBCPBEKey.getParam() != null) {
        localObject = localBCPBEKey.getParam();
      }
    }
    for (;;)
    {
      this.macEngine.init((CipherParameters)localObject);
      return;
      if ((paramAlgorithmParameterSpec instanceof PBEParameterSpec))
      {
        localObject = PBE.Util.makePBEMacParameters(localBCPBEKey, paramAlgorithmParameterSpec);
      }
      else
      {
        throw new InvalidAlgorithmParameterException("PBE requires PBE parameters to be set.");
        if ((paramAlgorithmParameterSpec instanceof IvParameterSpec))
        {
          localObject = new ParametersWithIV(new KeyParameter(paramKey.getEncoded()), ((IvParameterSpec)paramAlgorithmParameterSpec).getIV());
        }
        else
        {
          if (paramAlgorithmParameterSpec != null) {
            break;
          }
          localObject = new KeyParameter(paramKey.getEncoded());
        }
      }
    }
    throw new InvalidAlgorithmParameterException("unknown parameter type.");
  }
  
  protected void engineReset()
  {
    this.macEngine.reset();
  }
  
  protected void engineUpdate(byte paramByte)
  {
    this.macEngine.update(paramByte);
  }
  
  protected void engineUpdate(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    this.macEngine.update(paramArrayOfByte, paramInt1, paramInt2);
  }
  
  public static class DES
    extends JCEMac
  {
    public DES()
    {
      super();
    }
  }
  
  public static class DES64
    extends JCEMac
  {
    public DES64()
    {
      super();
    }
  }
  
  public static class DES9797Alg3
    extends JCEMac
  {
    public DES9797Alg3()
    {
      super();
    }
  }
  
  public static class DES9797Alg3with7816d4
    extends JCEMac
  {
    public DES9797Alg3with7816d4()
    {
      super();
    }
  }
  
  public static class DESCFB8
    extends JCEMac
  {
    public DESCFB8()
    {
      super();
    }
  }
  
  public static class MD2
    extends JCEMac
  {
    public MD2()
    {
      super();
    }
  }
  
  public static class MD4
    extends JCEMac
  {
    public MD4()
    {
      super();
    }
  }
  
  public static class MD5
    extends JCEMac
  {
    public MD5()
    {
      super();
    }
  }
  
  public static class OldSHA384
    extends JCEMac
  {
    public OldSHA384()
    {
      super();
    }
  }
  
  public static class OldSHA512
    extends JCEMac
  {
    public OldSHA512()
    {
      super();
    }
  }
  
  public static class PBEWithRIPEMD160
    extends JCEMac
  {
    public PBEWithRIPEMD160()
    {
      super(2, 2, 160);
    }
  }
  
  public static class PBEWithSHA
    extends JCEMac
  {
    public PBEWithSHA()
    {
      super(2, 1, 160);
    }
  }
  
  public static class PBEWithTiger
    extends JCEMac
  {
    public PBEWithTiger()
    {
      super(2, 3, 192);
    }
  }
  
  public static class RC2
    extends JCEMac
  {
    public RC2()
    {
      super();
    }
  }
  
  public static class RIPEMD128
    extends JCEMac
  {
    public RIPEMD128()
    {
      super();
    }
  }
  
  public static class RIPEMD160
    extends JCEMac
  {
    public RIPEMD160()
    {
      super();
    }
  }
  
  public static class SHA1
    extends JCEMac
  {
    public SHA1()
    {
      super();
    }
  }
  
  public static class SHA224
    extends JCEMac
  {
    public SHA224()
    {
      super();
    }
  }
  
  public static class SHA256
    extends JCEMac
  {
    public SHA256()
    {
      super();
    }
  }
  
  public static class SHA384
    extends JCEMac
  {
    public SHA384()
    {
      super();
    }
  }
  
  public static class SHA512
    extends JCEMac
  {
    public SHA512()
    {
      super();
    }
  }
  
  public static class Tiger
    extends JCEMac
  {
    public Tiger()
    {
      super();
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jce.provider.JCEMac
 * JD-Core Version:    0.7.0.1
 */