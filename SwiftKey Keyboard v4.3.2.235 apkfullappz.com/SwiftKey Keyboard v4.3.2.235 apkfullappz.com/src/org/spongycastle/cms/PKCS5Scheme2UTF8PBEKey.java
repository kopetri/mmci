package org.spongycastle.cms;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import org.spongycastle.crypto.PBEParametersGenerator;
import org.spongycastle.crypto.generators.PKCS5S2ParametersGenerator;
import org.spongycastle.crypto.params.KeyParameter;

public class PKCS5Scheme2UTF8PBEKey
  extends CMSPBEKey
{
  public PKCS5Scheme2UTF8PBEKey(char[] paramArrayOfChar, AlgorithmParameters paramAlgorithmParameters)
    throws InvalidAlgorithmParameterException
  {
    super(paramArrayOfChar, getParamSpec(paramAlgorithmParameters));
  }
  
  public PKCS5Scheme2UTF8PBEKey(char[] paramArrayOfChar, byte[] paramArrayOfByte, int paramInt)
  {
    super(paramArrayOfChar, paramArrayOfByte, paramInt);
  }
  
  byte[] getEncoded(String paramString)
  {
    PKCS5S2ParametersGenerator localPKCS5S2ParametersGenerator = new PKCS5S2ParametersGenerator();
    localPKCS5S2ParametersGenerator.init(PBEParametersGenerator.PKCS5PasswordToUTF8Bytes(getPassword()), getSalt(), getIterationCount());
    return ((KeyParameter)localPKCS5S2ParametersGenerator.generateDerivedParameters(CMSEnvelopedHelper.INSTANCE.getKeySize(paramString))).getKey();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.PKCS5Scheme2UTF8PBEKey
 * JD-Core Version:    0.7.0.1
 */