package com.touchtype.common.crypto;

import android.util.Base64;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public abstract class AESNoSaltObfuscator
{
  private final Cipher decrypt;
  private final Cipher encrypt = Cipher.getInstance("AES/CBC/PKCS5Padding");
  
  public AESNoSaltObfuscator(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    throws GeneralSecurityException
  {
    this.encrypt.init(1, new SecretKeySpec(paramArrayOfByte2, "AES"), new IvParameterSpec(paramArrayOfByte1));
    this.decrypt = Cipher.getInstance("AES/CBC/PKCS5Padding");
    this.decrypt.init(2, new SecretKeySpec(paramArrayOfByte2, "AES"), new IvParameterSpec(paramArrayOfByte1));
  }
  
  public String unobfuscate(String paramString)
    throws GeneralSecurityException
  {
    try
    {
      String str = new String(this.decrypt.doFinal(Base64.decode(paramString.getBytes("UTF-8"), 0)), "UTF-8");
      return str;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      throw new GeneralSecurityException(localIllegalArgumentException);
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      throw new GeneralSecurityException(localUnsupportedEncodingException);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.common.crypto.AESNoSaltObfuscator
 * JD-Core Version:    0.7.0.1
 */