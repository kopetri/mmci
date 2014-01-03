package org.spongycastle.jcajce.provider.symmetric.util;

import javax.crypto.interfaces.PBEKey;
import javax.crypto.spec.PBEKeySpec;
import org.spongycastle.asn1.DERObjectIdentifier;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.PBEParametersGenerator;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;

public class BCPBEKey
  implements PBEKey
{
  String algorithm;
  int digest;
  int ivSize;
  int keySize;
  DERObjectIdentifier oid;
  CipherParameters param;
  PBEKeySpec pbeKeySpec;
  boolean tryWrong = false;
  int type;
  
  public BCPBEKey(String paramString, DERObjectIdentifier paramDERObjectIdentifier, int paramInt1, int paramInt2, int paramInt3, int paramInt4, PBEKeySpec paramPBEKeySpec, CipherParameters paramCipherParameters)
  {
    this.algorithm = paramString;
    this.oid = paramDERObjectIdentifier;
    this.type = paramInt1;
    this.digest = paramInt2;
    this.keySize = paramInt3;
    this.ivSize = paramInt4;
    this.pbeKeySpec = paramPBEKeySpec;
    this.param = paramCipherParameters;
  }
  
  public String getAlgorithm()
  {
    return this.algorithm;
  }
  
  int getDigest()
  {
    return this.digest;
  }
  
  public byte[] getEncoded()
  {
    if (this.param != null)
    {
      if ((this.param instanceof ParametersWithIV)) {}
      for (KeyParameter localKeyParameter = (KeyParameter)((ParametersWithIV)this.param).getParameters();; localKeyParameter = (KeyParameter)this.param) {
        return localKeyParameter.getKey();
      }
    }
    if (this.type == 2) {
      return PBEParametersGenerator.PKCS12PasswordToBytes(this.pbeKeySpec.getPassword());
    }
    return PBEParametersGenerator.PKCS5PasswordToBytes(this.pbeKeySpec.getPassword());
  }
  
  public String getFormat()
  {
    return "RAW";
  }
  
  public int getIterationCount()
  {
    return this.pbeKeySpec.getIterationCount();
  }
  
  public int getIvSize()
  {
    return this.ivSize;
  }
  
  int getKeySize()
  {
    return this.keySize;
  }
  
  public DERObjectIdentifier getOID()
  {
    return this.oid;
  }
  
  public CipherParameters getParam()
  {
    return this.param;
  }
  
  public char[] getPassword()
  {
    return this.pbeKeySpec.getPassword();
  }
  
  public byte[] getSalt()
  {
    return this.pbeKeySpec.getSalt();
  }
  
  int getType()
  {
    return this.type;
  }
  
  public void setTryWrongPKCS12Zero(boolean paramBoolean)
  {
    this.tryWrong = paramBoolean;
  }
  
  boolean shouldTryWrongPKCS12()
  {
    return this.tryWrong;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jcajce.provider.symmetric.util.BCPBEKey
 * JD-Core Version:    0.7.0.1
 */