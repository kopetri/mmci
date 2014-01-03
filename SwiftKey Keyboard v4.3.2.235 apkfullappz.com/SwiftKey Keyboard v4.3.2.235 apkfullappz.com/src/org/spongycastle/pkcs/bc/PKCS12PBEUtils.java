package org.spongycastle.pkcs.bc;

import java.io.OutputStream;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.pkcs.PKCS12PBEParams;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.ExtendedDigest;
import org.spongycastle.crypto.engines.DESedeEngine;
import org.spongycastle.crypto.engines.RC2Engine;
import org.spongycastle.crypto.generators.PKCS12ParametersGenerator;
import org.spongycastle.crypto.io.MacOutputStream;
import org.spongycastle.crypto.macs.HMac;
import org.spongycastle.crypto.modes.CBCBlockCipher;
import org.spongycastle.crypto.paddings.PKCS7Padding;
import org.spongycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.spongycastle.crypto.params.DESedeParameters;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.operator.GenericKey;
import org.spongycastle.operator.MacCalculator;

class PKCS12PBEUtils
{
  private static Set desAlgs;
  private static Map keySizes = new HashMap();
  private static Set noIvAlgs = new HashSet();
  
  static
  {
    desAlgs = new HashSet();
    keySizes.put(PKCSObjectIdentifiers.pbeWithSHAAnd128BitRC4, new Integer(128));
    keySizes.put(PKCSObjectIdentifiers.pbeWithSHAAnd40BitRC4, new Integer(40));
    keySizes.put(PKCSObjectIdentifiers.pbeWithSHAAnd3_KeyTripleDES_CBC, new Integer(192));
    keySizes.put(PKCSObjectIdentifiers.pbeWithSHAAnd2_KeyTripleDES_CBC, new Integer(128));
    keySizes.put(PKCSObjectIdentifiers.pbeWithSHAAnd128BitRC2_CBC, new Integer(128));
    keySizes.put(PKCSObjectIdentifiers.pbeWithSHAAnd40BitRC2_CBC, new Integer(40));
    noIvAlgs.add(PKCSObjectIdentifiers.pbeWithSHAAnd128BitRC4);
    noIvAlgs.add(PKCSObjectIdentifiers.pbeWithSHAAnd40BitRC4);
    desAlgs.add(PKCSObjectIdentifiers.pbeWithSHAAnd3_KeyTripleDES_CBC);
    desAlgs.add(PKCSObjectIdentifiers.pbeWithSHAAnd3_KeyTripleDES_CBC);
  }
  
  static CipherParameters createCipherParameters(ASN1ObjectIdentifier paramASN1ObjectIdentifier, ExtendedDigest paramExtendedDigest, int paramInt, PKCS12PBEParams paramPKCS12PBEParams, char[] paramArrayOfChar)
  {
    PKCS12ParametersGenerator localPKCS12ParametersGenerator = new PKCS12ParametersGenerator(paramExtendedDigest);
    localPKCS12ParametersGenerator.init(PKCS12ParametersGenerator.PKCS12PasswordToBytes(paramArrayOfChar), paramPKCS12PBEParams.getIV(), paramPKCS12PBEParams.getIterations().intValue());
    CipherParameters localCipherParameters;
    if (hasNoIv(paramASN1ObjectIdentifier)) {
      localCipherParameters = localPKCS12ParametersGenerator.generateDerivedParameters(getKeySize(paramASN1ObjectIdentifier));
    }
    do
    {
      return localCipherParameters;
      localCipherParameters = localPKCS12ParametersGenerator.generateDerivedParameters(getKeySize(paramASN1ObjectIdentifier), paramInt * 8);
    } while (!isDesAlg(paramASN1ObjectIdentifier));
    DESedeParameters.setOddParity(((KeyParameter)((ParametersWithIV)localCipherParameters).getParameters()).getKey());
    return localCipherParameters;
  }
  
  static MacCalculator createMacCalculator(ASN1ObjectIdentifier paramASN1ObjectIdentifier, ExtendedDigest paramExtendedDigest, final PKCS12PBEParams paramPKCS12PBEParams, final char[] paramArrayOfChar)
  {
    PKCS12ParametersGenerator localPKCS12ParametersGenerator = new PKCS12ParametersGenerator(paramExtendedDigest);
    localPKCS12ParametersGenerator.init(PKCS12ParametersGenerator.PKCS12PasswordToBytes(paramArrayOfChar), paramPKCS12PBEParams.getIV(), paramPKCS12PBEParams.getIterations().intValue());
    KeyParameter localKeyParameter = (KeyParameter)localPKCS12ParametersGenerator.generateDerivedMacParameters(8 * paramExtendedDigest.getDigestSize());
    final HMac localHMac = new HMac(paramExtendedDigest);
    localHMac.init(localKeyParameter);
    new MacCalculator()
    {
      public AlgorithmIdentifier getAlgorithmIdentifier()
      {
        return new AlgorithmIdentifier(this.val$digestAlgorithm, paramPKCS12PBEParams);
      }
      
      public GenericKey getKey()
      {
        return new GenericKey(PKCS12ParametersGenerator.PKCS12PasswordToBytes(paramArrayOfChar));
      }
      
      public byte[] getMac()
      {
        byte[] arrayOfByte = new byte[localHMac.getMacSize()];
        localHMac.doFinal(arrayOfByte, 0);
        return arrayOfByte;
      }
      
      public OutputStream getOutputStream()
      {
        return new MacOutputStream(localHMac);
      }
    };
  }
  
  static PaddedBufferedBlockCipher getEngine(ASN1ObjectIdentifier paramASN1ObjectIdentifier)
  {
    if ((paramASN1ObjectIdentifier.equals(PKCSObjectIdentifiers.pbeWithSHAAnd3_KeyTripleDES_CBC)) || (paramASN1ObjectIdentifier.equals(PKCSObjectIdentifiers.pbeWithSHAAnd2_KeyTripleDES_CBC))) {}
    for (Object localObject = new DESedeEngine();; localObject = new RC2Engine())
    {
      return new PaddedBufferedBlockCipher(new CBCBlockCipher((BlockCipher)localObject), new PKCS7Padding());
      if ((!paramASN1ObjectIdentifier.equals(PKCSObjectIdentifiers.pbeWithSHAAnd128BitRC2_CBC)) && (!paramASN1ObjectIdentifier.equals(PKCSObjectIdentifiers.pbeWithSHAAnd40BitRC2_CBC))) {
        break;
      }
    }
    throw new IllegalStateException("unknown algorithm");
  }
  
  static int getKeySize(ASN1ObjectIdentifier paramASN1ObjectIdentifier)
  {
    return ((Integer)keySizes.get(paramASN1ObjectIdentifier)).intValue();
  }
  
  static boolean hasNoIv(ASN1ObjectIdentifier paramASN1ObjectIdentifier)
  {
    return noIvAlgs.contains(paramASN1ObjectIdentifier);
  }
  
  static boolean isDesAlg(ASN1ObjectIdentifier paramASN1ObjectIdentifier)
  {
    return desAlgs.contains(paramASN1ObjectIdentifier);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.pkcs.bc.PKCS12PBEUtils
 * JD-Core Version:    0.7.0.1
 */