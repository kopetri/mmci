package com.touchtype.runtimeconfigurator;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class AESObfuscator
  implements Obfuscator
{
  private static final String a = Charset.forName("ISO_8859_1").name();
  private static final byte[] b = { 16, 74, 71, -80, 32, 101, -47, 72, 117, -14, 0, -29, 70, 65, -12, 74 };
  private Cipher c;
  private Cipher d;
  
  public AESObfuscator(byte[] paramArrayOfByte, String paramString)
  {
    if (paramArrayOfByte == null) {
      throw new IllegalArgumentException("Salt cannot be null!");
    }
    if (paramString.isEmpty()) {
      throw new IllegalArgumentException("Password cannot be empty!");
    }
    try
    {
      SecretKeySpec localSecretKeySpec = new SecretKeySpec(SecretKeyFactory.getInstance("PBEWITHSHAAND128BITAES-CBC-BC", "SC").generateSecret(new PBEKeySpec(paramString.toCharArray(), paramArrayOfByte, 1024, 128)).getEncoded(), "AES");
      this.c = Cipher.getInstance("AES/CBC/PKCS5Padding");
      this.c.init(1, localSecretKeySpec, new IvParameterSpec(b));
      this.d = Cipher.getInstance("AES/CBC/PKCS5Padding");
      this.d.init(2, localSecretKeySpec, new IvParameterSpec(b));
      return;
    }
    catch (GeneralSecurityException localGeneralSecurityException)
    {
      localGeneralSecurityException.printStackTrace();
    }
  }
  
  public String obfuscate(String paramString)
  {
    if (paramString == null) {
      return null;
    }
    try
    {
      String str = Base64.encodeToString(this.c.doFinal(("com.android.vending.licensing.AESObfuscator-1|" + paramString).getBytes(a)), 2);
      return str;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      localUnsupportedEncodingException.printStackTrace();
      return null;
    }
    catch (GeneralSecurityException localGeneralSecurityException)
    {
      localGeneralSecurityException.printStackTrace();
    }
    return null;
  }
  
  public String unobfuscate(String paramString)
    throws GeneralSecurityException
  {
    if (paramString == null) {
      return null;
    }
    try
    {
      str1 = new String(this.d.doFinal(Base64.decode(paramString.getBytes(a), 0)), a);
      if (str1.indexOf("com.android.vending.licensing.AESObfuscator-1|") != 0) {
        throw new GeneralSecurityException("Header not found (invalid data or key):" + paramString);
      }
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      String str1;
      throw new GeneralSecurityException(localIllegalArgumentException);
      String str2 = str1.substring(46, str1.length());
      return str2;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      throw new GeneralSecurityException(localUnsupportedEncodingException);
    }
    catch (NullPointerException localNullPointerException)
    {
      throw new GeneralSecurityException(localNullPointerException);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.runtimeconfigurator.AESObfuscator
 * JD-Core Version:    0.7.0.1
 */