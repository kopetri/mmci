package org.spongycastle.crypto.signers;

import java.io.IOException;
import java.util.Hashtable;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.nist.NISTObjectIdentifiers;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.teletrust.TeleTrusTObjectIdentifiers;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.DigestInfo;
import org.spongycastle.asn1.x509.X509ObjectIdentifiers;
import org.spongycastle.crypto.AsymmetricBlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.CryptoException;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.Signer;
import org.spongycastle.crypto.encodings.PKCS1Encoding;
import org.spongycastle.crypto.engines.RSABlindedEngine;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.util.Arrays;

public class RSADigestSigner
  implements Signer
{
  private static final Hashtable oidMap;
  private final AlgorithmIdentifier algId;
  private final Digest digest;
  private boolean forSigning;
  private final AsymmetricBlockCipher rsaEngine = new PKCS1Encoding(new RSABlindedEngine());
  
  static
  {
    Hashtable localHashtable = new Hashtable();
    oidMap = localHashtable;
    localHashtable.put("RIPEMD128", TeleTrusTObjectIdentifiers.ripemd128);
    oidMap.put("RIPEMD160", TeleTrusTObjectIdentifiers.ripemd160);
    oidMap.put("RIPEMD256", TeleTrusTObjectIdentifiers.ripemd256);
    oidMap.put("SHA-1", X509ObjectIdentifiers.id_SHA1);
    oidMap.put("SHA-224", NISTObjectIdentifiers.id_sha224);
    oidMap.put("SHA-256", NISTObjectIdentifiers.id_sha256);
    oidMap.put("SHA-384", NISTObjectIdentifiers.id_sha384);
    oidMap.put("SHA-512", NISTObjectIdentifiers.id_sha512);
    oidMap.put("MD2", PKCSObjectIdentifiers.md2);
    oidMap.put("MD4", PKCSObjectIdentifiers.md4);
    oidMap.put("MD5", PKCSObjectIdentifiers.md5);
  }
  
  public RSADigestSigner(Digest paramDigest)
  {
    this.digest = paramDigest;
    this.algId = new AlgorithmIdentifier((ASN1ObjectIdentifier)oidMap.get(paramDigest.getAlgorithmName()), DERNull.INSTANCE);
  }
  
  private byte[] derEncode(byte[] paramArrayOfByte)
    throws IOException
  {
    return new DigestInfo(this.algId, paramArrayOfByte).getEncoded("DER");
  }
  
  public byte[] generateSignature()
    throws CryptoException, DataLengthException
  {
    if (!this.forSigning) {
      throw new IllegalStateException("RSADigestSigner not initialised for signature generation.");
    }
    byte[] arrayOfByte1 = new byte[this.digest.getDigestSize()];
    this.digest.doFinal(arrayOfByte1, 0);
    try
    {
      byte[] arrayOfByte2 = derEncode(arrayOfByte1);
      byte[] arrayOfByte3 = this.rsaEngine.processBlock(arrayOfByte2, 0, arrayOfByte2.length);
      return arrayOfByte3;
    }
    catch (IOException localIOException)
    {
      throw new CryptoException("unable to encode signature: " + localIOException.getMessage(), localIOException);
    }
  }
  
  public String getAlgorithmName()
  {
    return this.digest.getAlgorithmName() + "withRSA";
  }
  
  public void init(boolean paramBoolean, CipherParameters paramCipherParameters)
  {
    this.forSigning = paramBoolean;
    if ((paramCipherParameters instanceof ParametersWithRandom)) {}
    for (AsymmetricKeyParameter localAsymmetricKeyParameter = (AsymmetricKeyParameter)((ParametersWithRandom)paramCipherParameters).getParameters(); (paramBoolean) && (!localAsymmetricKeyParameter.isPrivate()); localAsymmetricKeyParameter = (AsymmetricKeyParameter)paramCipherParameters) {
      throw new IllegalArgumentException("signing requires private key");
    }
    if ((!paramBoolean) && (localAsymmetricKeyParameter.isPrivate())) {
      throw new IllegalArgumentException("verification requires public key");
    }
    reset();
    this.rsaEngine.init(paramBoolean, paramCipherParameters);
  }
  
  public void reset()
  {
    this.digest.reset();
  }
  
  public void update(byte paramByte)
  {
    this.digest.update(paramByte);
  }
  
  public void update(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    this.digest.update(paramArrayOfByte, paramInt1, paramInt2);
  }
  
  public boolean verifySignature(byte[] paramArrayOfByte)
  {
    boolean bool = true;
    if (this.forSigning) {
      throw new IllegalStateException("RSADigestSigner not initialised for verification");
    }
    byte[] arrayOfByte1 = new byte[this.digest.getDigestSize()];
    this.digest.doFinal(arrayOfByte1, 0);
    int k;
    do
    {
      byte[] arrayOfByte2;
      byte[] arrayOfByte3;
      try
      {
        arrayOfByte2 = this.rsaEngine.processBlock(paramArrayOfByte, 0, paramArrayOfByte.length);
        arrayOfByte3 = derEncode(arrayOfByte1);
        if (arrayOfByte2.length == arrayOfByte3.length)
        {
          bool = Arrays.constantTimeAreEqual(arrayOfByte2, arrayOfByte3);
          return bool;
        }
      }
      catch (Exception localException)
      {
        return false;
      }
      if (arrayOfByte2.length != -2 + arrayOfByte3.length) {
        break;
      }
      int i = -2 + (arrayOfByte2.length - arrayOfByte1.length);
      int j = -2 + (arrayOfByte3.length - arrayOfByte1.length);
      arrayOfByte3[bool] = ((byte)(-2 + arrayOfByte3[bool]));
      arrayOfByte3[3] = ((byte)(-2 + arrayOfByte3[3]));
      k = 0;
      for (int m = 0; m < arrayOfByte1.length; m++) {
        k |= arrayOfByte2[(i + m)] ^ arrayOfByte3[(j + m)];
      }
      for (int n = 0; n < i; n++) {
        k |= arrayOfByte2[n] ^ arrayOfByte3[n];
      }
    } while (k == 0);
    return false;
    return false;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.signers.RSADigestSigner
 * JD-Core Version:    0.7.0.1
 */