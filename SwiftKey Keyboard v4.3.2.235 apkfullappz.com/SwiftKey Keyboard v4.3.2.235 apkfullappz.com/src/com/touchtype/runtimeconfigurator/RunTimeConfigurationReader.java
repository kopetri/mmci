package com.touchtype.runtimeconfigurator;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;

public class RunTimeConfigurationReader
{
  private final InputStream a;
  private String b = "NoReferrer";
  private int c = -1;
  
  public RunTimeConfigurationReader(InputStream paramInputStream)
  {
    if (paramInputStream == null) {
      throw new IllegalArgumentException("InputStream cannot be null!");
    }
    this.a = paramInputStream;
  }
  
  public String getReferrerId()
  {
    return this.b;
  }
  
  public int getTrialPeriodDays()
  {
    return this.c;
  }
  
  public void readEncryptedFile()
    throws IOException, GeneralSecurityException, NullPointerException
  {
    RunTimeConfigurationParams localRunTimeConfigurationParams = RunTimeConfigurationCryptoUtil.readEncryptedjsonFile(this.a);
    this.b = localRunTimeConfigurationParams.referrerId;
    this.c = localRunTimeConfigurationParams.days;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.runtimeconfigurator.RunTimeConfigurationReader
 * JD-Core Version:    0.7.0.1
 */