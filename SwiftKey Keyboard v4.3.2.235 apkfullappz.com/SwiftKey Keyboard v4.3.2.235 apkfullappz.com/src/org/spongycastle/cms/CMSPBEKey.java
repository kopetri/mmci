package org.spongycastle.cms;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.spec.InvalidParameterSpecException;
import javax.crypto.interfaces.PBEKey;
import javax.crypto.spec.PBEParameterSpec;

public abstract class CMSPBEKey
  implements PBEKey
{
  private int iterationCount;
  private char[] password;
  private byte[] salt;
  
  public CMSPBEKey(char[] paramArrayOfChar, PBEParameterSpec paramPBEParameterSpec)
  {
    this(paramArrayOfChar, paramPBEParameterSpec.getSalt(), paramPBEParameterSpec.getIterationCount());
  }
  
  public CMSPBEKey(char[] paramArrayOfChar, byte[] paramArrayOfByte, int paramInt)
  {
    this.password = paramArrayOfChar;
    this.salt = paramArrayOfByte;
    this.iterationCount = paramInt;
  }
  
  protected static PBEParameterSpec getParamSpec(AlgorithmParameters paramAlgorithmParameters)
    throws InvalidAlgorithmParameterException
  {
    try
    {
      PBEParameterSpec localPBEParameterSpec = (PBEParameterSpec)paramAlgorithmParameters.getParameterSpec(PBEParameterSpec.class);
      return localPBEParameterSpec;
    }
    catch (InvalidParameterSpecException localInvalidParameterSpecException)
    {
      throw new InvalidAlgorithmParameterException("cannot process PBE spec: " + localInvalidParameterSpecException.getMessage());
    }
  }
  
  public String getAlgorithm()
  {
    return "PKCS5S2";
  }
  
  public byte[] getEncoded()
  {
    return null;
  }
  
  abstract byte[] getEncoded(String paramString);
  
  public String getFormat()
  {
    return "RAW";
  }
  
  public int getIterationCount()
  {
    return this.iterationCount;
  }
  
  public char[] getPassword()
  {
    return this.password;
  }
  
  public byte[] getSalt()
  {
    return this.salt;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.CMSPBEKey
 * JD-Core Version:    0.7.0.1
 */