package com.touchtype.runtimeconfigurator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.security.GeneralSecurityException;

public class RunTimeConfigurationGenerator
{
  private String a = null;
  private String b = "";
  private int c = -1;
  
  private boolean a(String[] paramArrayOfString)
  {
    int i = 1;
    this.b = (paramArrayOfString[0] + "/trialext.dat");
    for (int j = i;; j++)
    {
      int k;
      if (j < paramArrayOfString.length)
      {
        if (paramArrayOfString[j].equals("-r")) {
          this.a = paramArrayOfString[(j + 1)];
        }
        if (paramArrayOfString[j].equals("-d")) {
          k = j + 1;
        }
      }
      else
      {
        try
        {
          this.c = Integer.parseInt(paramArrayOfString[k]);
          if (this.c <= 0)
          {
            System.err.println("Specify a POSITIVE number for the number of days!");
            i = 0;
            return i;
          }
        }
        catch (NumberFormatException localNumberFormatException)
        {
          System.err.println("Days parameter is NOT a number!");
          return false;
        }
      }
    }
  }
  
  public static void main(String[] paramArrayOfString)
    throws IOException, GeneralSecurityException, NullPointerException
  {
    RunTimeConfigurationGenerator localRunTimeConfigurationGenerator = new RunTimeConfigurationGenerator();
    int i = paramArrayOfString.length;
    int j = 0;
    if (i >= 3)
    {
      int k = paramArrayOfString.length;
      j = 0;
      if (k != 4)
      {
        int m = paramArrayOfString.length;
        j = 0;
        if (m <= 5) {
          break label278;
        }
      }
    }
    for (;;)
    {
      if (j == 0)
      {
        localRunTimeConfigurationGenerator.printUsage();
        System.exit(-1);
      }
      if (!localRunTimeConfigurationGenerator.a(paramArrayOfString))
      {
        localRunTimeConfigurationGenerator.printUsage();
        System.exit(-1);
      }
      System.out.println("File name:\t" + localRunTimeConfigurationGenerator.b);
      if (!localRunTimeConfigurationGenerator.a.isEmpty()) {
        System.out.println("Referrer-Id:\t" + localRunTimeConfigurationGenerator.a);
      }
      if (localRunTimeConfigurationGenerator.c != 0) {
        System.out.println("Days:\t\t" + localRunTimeConfigurationGenerator.c);
      }
      String str1 = RunTimeConfigurationCryptoUtil.makeJson(localRunTimeConfigurationGenerator.a, localRunTimeConfigurationGenerator.c);
      System.out.println("Plaintext Json:\t" + str1);
      String str2 = RunTimeConfigurationCryptoUtil.writeEncryptedJsonFile(str1, new File(localRunTimeConfigurationGenerator.b));
      System.err.println("Encrypted Blob:\t" + str2);
      System.out.println("Decrypted text:\t" + RunTimeConfigurationCryptoUtil.readEncryptedjsonFile(new FileInputStream(localRunTimeConfigurationGenerator.b)).toJson());
      return;
      label278:
      if ((!paramArrayOfString[1].equals("-r")) && (!paramArrayOfString[1].equals("-d")))
      {
        localRunTimeConfigurationGenerator.printUsage();
        j = 0;
      }
      else
      {
        if (paramArrayOfString.length == 5)
        {
          if ((!paramArrayOfString[3].equals("-r")) && (!paramArrayOfString[3].equals("-d")))
          {
            localRunTimeConfigurationGenerator.printUsage();
            j = 0;
            continue;
          }
          if (paramArrayOfString[3].equals(paramArrayOfString[1]))
          {
            localRunTimeConfigurationGenerator.printUsage();
            j = 0;
            continue;
          }
        }
        j = 1;
      }
    }
  }
  
  public void printUsage()
  {
    System.err.println("Usage: java -jar RunTimeConfigurationGenerator.jar <PATH> [-r <REFERRER-ID>] [-d <DAYS>]");
    System.err.println("Specify at least at one parameter, either <REFERRER-ID> or <DAYS>");
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.runtimeconfigurator.RunTimeConfigurationGenerator
 * JD-Core Version:    0.7.0.1
 */