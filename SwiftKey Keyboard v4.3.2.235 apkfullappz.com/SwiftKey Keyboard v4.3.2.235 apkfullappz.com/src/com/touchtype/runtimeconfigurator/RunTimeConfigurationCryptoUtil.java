package com.touchtype.runtimeconfigurator;

import com.google.common.io.Files;
import com.google.gson.Gson;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.Security;
import org.apache.commons.io.IOUtils;
import org.spongycastle.jce.provider.BouncyCastleProvider;

public class RunTimeConfigurationCryptoUtil
{
  private static final Charset a = Charset.forName("ISO_8859_1");
  private static final byte[] b = { 72, 85, -104, 91, -32, -9, -57, 118, 111, 31, 84, 97, -100, -24, -102, 117, -29, 19, -69 };
  
  static
  {
    Security.insertProviderAt(new BouncyCastleProvider(), 1);
  }
  
  public static void main(String[] paramArrayOfString)
    throws IOException, GeneralSecurityException
  {
    String str1 = makeJson("T-Mobile", 99);
    System.out.println("Plaintext Json:\t" + str1);
    String str2 = writeEncryptedJsonFile(str1, new File("/tmp/trialext.dat"));
    System.err.println("Encrypted Blob:\t" + str2);
    System.out.println("Decrypted text:\t" + readEncryptedjsonFile(new FileInputStream("/tmp/trialext.dat")));
  }
  
  public static String makeJson(String paramString, int paramInt)
  {
    if ((paramString == null) && (paramInt <= 0)) {
      throw new IllegalArgumentException("Wrong args, specify at least one between 'referrer-id' and 'days'");
    }
    if (paramString == null) {
      return new RunTimeConfigurationParams(paramInt).toJson();
    }
    if (paramInt <= 0) {
      return new RunTimeConfigurationParams(paramString).toJson();
    }
    return new RunTimeConfigurationParams(paramString, paramInt).toJson();
  }
  
  public static RunTimeConfigurationParams readEncryptedjsonFile(InputStream paramInputStream)
    throws IllegalArgumentException, IOException, GeneralSecurityException
  {
    if (paramInputStream == null) {
      throw new IllegalArgumentException("InputStream cannot be null!");
    }
    InputStreamReader localInputStreamReader = new InputStreamReader(new ByteArrayInputStream(new AESObfuscator(b, "luckybrews55aHSooNwynSnlIcCLPwLIycTS0mg5QE8").unobfuscate(IOUtils.toString(paramInputStream, a.name())).getBytes(a.name())));
    return (RunTimeConfigurationParams)new Gson().fromJson(localInputStreamReader, RunTimeConfigurationParams.class);
  }
  
  public static String writeEncryptedJsonFile(String paramString, File paramFile)
    throws IOException
  {
    String str = new AESObfuscator(b, "luckybrews55aHSooNwynSnlIcCLPwLIycTS0mg5QE8").obfuscate(paramString);
    Files.write(str, paramFile, a);
    return str;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.runtimeconfigurator.RunTimeConfigurationCryptoUtil
 * JD-Core Version:    0.7.0.1
 */