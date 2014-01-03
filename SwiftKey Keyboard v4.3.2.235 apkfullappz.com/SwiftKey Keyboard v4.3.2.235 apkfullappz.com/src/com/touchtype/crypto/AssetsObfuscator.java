package com.touchtype.crypto;

import com.touchtype.common.crypto.AESNoSaltObfuscator;
import java.security.GeneralSecurityException;

public final class AssetsObfuscator
  extends AESNoSaltObfuscator
{
  private static final byte[] IV = { -112, -54, -57, 48, -96, -27, 81, -56, -11, 114, -128, 99, -58, -63, 116, -54 };
  private static final byte[] KEY = { -60, -54, 66, 56, -96, -71, 35, -126, 13, -52, 80, -102, 111, 117, -124, -101 };
  
  public AssetsObfuscator()
    throws GeneralSecurityException
  {
    super(IV, KEY);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.crypto.AssetsObfuscator
 * JD-Core Version:    0.7.0.1
 */