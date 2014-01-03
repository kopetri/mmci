package com.touchtype.runtimeconfigurator;

import java.security.GeneralSecurityException;

public abstract interface Obfuscator
{
  public abstract String obfuscate(String paramString);
  
  public abstract String unobfuscate(String paramString)
    throws GeneralSecurityException;
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.runtimeconfigurator.Obfuscator
 * JD-Core Version:    0.7.0.1
 */