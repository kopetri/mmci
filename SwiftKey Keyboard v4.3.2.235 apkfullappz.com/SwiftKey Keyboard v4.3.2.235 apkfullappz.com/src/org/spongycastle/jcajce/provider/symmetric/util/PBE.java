package org.spongycastle.jcajce.provider.symmetric.util;

import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.PBEParametersGenerator;
import org.spongycastle.crypto.digests.MD2Digest;
import org.spongycastle.crypto.digests.MD5Digest;
import org.spongycastle.crypto.digests.RIPEMD160Digest;
import org.spongycastle.crypto.digests.SHA1Digest;
import org.spongycastle.crypto.digests.SHA256Digest;
import org.spongycastle.crypto.digests.TigerDigest;
import org.spongycastle.crypto.generators.OpenSSLPBEParametersGenerator;
import org.spongycastle.crypto.generators.PKCS12ParametersGenerator;
import org.spongycastle.crypto.generators.PKCS5S1ParametersGenerator;
import org.spongycastle.crypto.generators.PKCS5S2ParametersGenerator;
import org.spongycastle.crypto.params.DESParameters;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;

public abstract interface PBE
{
  public static final int MD2 = 5;
  public static final int MD5 = 0;
  public static final int OPENSSL = 3;
  public static final int PKCS12 = 2;
  public static final int PKCS5S1 = 0;
  public static final int PKCS5S2 = 1;
  public static final int RIPEMD160 = 2;
  public static final int SHA1 = 1;
  public static final int SHA256 = 4;
  public static final int TIGER = 3;
  
  public static class Util
  {
    private static PBEParametersGenerator makePBEGenerator(int paramInt1, int paramInt2)
    {
      if (paramInt1 == 0)
      {
        switch (paramInt2)
        {
        case 2: 
        case 3: 
        case 4: 
        default: 
          throw new IllegalStateException("PKCS5 scheme 1 only supports MD2, MD5 and SHA1.");
        case 5: 
          return new PKCS5S1ParametersGenerator(new MD2Digest());
        case 0: 
          return new PKCS5S1ParametersGenerator(new MD5Digest());
        }
        return new PKCS5S1ParametersGenerator(new SHA1Digest());
      }
      if (paramInt1 == 1) {
        return new PKCS5S2ParametersGenerator();
      }
      if (paramInt1 == 2)
      {
        switch (paramInt2)
        {
        default: 
          throw new IllegalStateException("unknown digest scheme for PBE encryption.");
        case 5: 
          return new PKCS12ParametersGenerator(new MD2Digest());
        case 0: 
          return new PKCS12ParametersGenerator(new MD5Digest());
        case 1: 
          return new PKCS12ParametersGenerator(new SHA1Digest());
        case 2: 
          return new PKCS12ParametersGenerator(new RIPEMD160Digest());
        case 3: 
          return new PKCS12ParametersGenerator(new TigerDigest());
        }
        return new PKCS12ParametersGenerator(new SHA256Digest());
      }
      return new OpenSSLPBEParametersGenerator();
    }
    
    public static CipherParameters makePBEMacParameters(PBEKeySpec paramPBEKeySpec, int paramInt1, int paramInt2, int paramInt3)
    {
      PBEParametersGenerator localPBEParametersGenerator = makePBEGenerator(paramInt1, paramInt2);
      if (paramInt1 == 2) {}
      CipherParameters localCipherParameters;
      for (byte[] arrayOfByte = PBEParametersGenerator.PKCS12PasswordToBytes(paramPBEKeySpec.getPassword());; arrayOfByte = PBEParametersGenerator.PKCS5PasswordToBytes(paramPBEKeySpec.getPassword()))
      {
        localPBEParametersGenerator.init(arrayOfByte, paramPBEKeySpec.getSalt(), paramPBEKeySpec.getIterationCount());
        localCipherParameters = localPBEParametersGenerator.generateDerivedMacParameters(paramInt3);
        for (int i = 0; i != arrayOfByte.length; i++) {
          arrayOfByte[i] = 0;
        }
      }
      return localCipherParameters;
    }
    
    public static CipherParameters makePBEMacParameters(BCPBEKey paramBCPBEKey, AlgorithmParameterSpec paramAlgorithmParameterSpec)
    {
      if ((paramAlgorithmParameterSpec == null) || (!(paramAlgorithmParameterSpec instanceof PBEParameterSpec))) {
        throw new IllegalArgumentException("Need a PBEParameter spec with a PBE key.");
      }
      PBEParameterSpec localPBEParameterSpec = (PBEParameterSpec)paramAlgorithmParameterSpec;
      PBEParametersGenerator localPBEParametersGenerator = makePBEGenerator(paramBCPBEKey.getType(), paramBCPBEKey.getDigest());
      byte[] arrayOfByte = paramBCPBEKey.getEncoded();
      if (paramBCPBEKey.shouldTryWrongPKCS12()) {
        arrayOfByte = new byte[2];
      }
      localPBEParametersGenerator.init(arrayOfByte, localPBEParameterSpec.getSalt(), localPBEParameterSpec.getIterationCount());
      CipherParameters localCipherParameters = localPBEParametersGenerator.generateDerivedMacParameters(paramBCPBEKey.getKeySize());
      for (int i = 0; i != arrayOfByte.length; i++) {
        arrayOfByte[i] = 0;
      }
      return localCipherParameters;
    }
    
    public static CipherParameters makePBEParameters(PBEKeySpec paramPBEKeySpec, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      PBEParametersGenerator localPBEParametersGenerator = makePBEGenerator(paramInt1, paramInt2);
      byte[] arrayOfByte;
      if (paramInt1 == 2)
      {
        arrayOfByte = PBEParametersGenerator.PKCS12PasswordToBytes(paramPBEKeySpec.getPassword());
        localPBEParametersGenerator.init(arrayOfByte, paramPBEKeySpec.getSalt(), paramPBEKeySpec.getIterationCount());
        if (paramInt4 == 0) {
          break label86;
        }
      }
      label86:
      for (CipherParameters localCipherParameters = localPBEParametersGenerator.generateDerivedParameters(paramInt3, paramInt4);; localCipherParameters = localPBEParametersGenerator.generateDerivedParameters(paramInt3))
      {
        for (int i = 0; i != arrayOfByte.length; i++) {
          arrayOfByte[i] = 0;
        }
        arrayOfByte = PBEParametersGenerator.PKCS5PasswordToBytes(paramPBEKeySpec.getPassword());
        break;
      }
      return localCipherParameters;
    }
    
    public static CipherParameters makePBEParameters(BCPBEKey paramBCPBEKey, AlgorithmParameterSpec paramAlgorithmParameterSpec, String paramString)
    {
      if ((paramAlgorithmParameterSpec == null) || (!(paramAlgorithmParameterSpec instanceof PBEParameterSpec))) {
        throw new IllegalArgumentException("Need a PBEParameter spec with a PBE key.");
      }
      PBEParameterSpec localPBEParameterSpec = (PBEParameterSpec)paramAlgorithmParameterSpec;
      PBEParametersGenerator localPBEParametersGenerator = makePBEGenerator(paramBCPBEKey.getType(), paramBCPBEKey.getDigest());
      byte[] arrayOfByte = paramBCPBEKey.getEncoded();
      if (paramBCPBEKey.shouldTryWrongPKCS12()) {
        arrayOfByte = new byte[2];
      }
      localPBEParametersGenerator.init(arrayOfByte, localPBEParameterSpec.getSalt(), localPBEParameterSpec.getIterationCount());
      CipherParameters localCipherParameters;
      if (paramBCPBEKey.getIvSize() != 0)
      {
        localCipherParameters = localPBEParametersGenerator.generateDerivedParameters(paramBCPBEKey.getKeySize(), paramBCPBEKey.getIvSize());
        if (paramString.startsWith("DES"))
        {
          if (!(localCipherParameters instanceof ParametersWithIV)) {
            break label165;
          }
          DESParameters.setOddParity(((KeyParameter)((ParametersWithIV)localCipherParameters).getParameters()).getKey());
        }
      }
      for (;;)
      {
        for (int i = 0; i != arrayOfByte.length; i++) {
          arrayOfByte[i] = 0;
        }
        localCipherParameters = localPBEParametersGenerator.generateDerivedParameters(paramBCPBEKey.getKeySize());
        break;
        label165:
        DESParameters.setOddParity(((KeyParameter)localCipherParameters).getKey());
      }
      return localCipherParameters;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jcajce.provider.symmetric.util.PBE
 * JD-Core Version:    0.7.0.1
 */