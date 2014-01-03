package org.spongycastle.pkcs;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import org.spongycastle.asn1.pkcs.MacData;
import org.spongycastle.asn1.pkcs.PKCS12PBEParams;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.DigestInfo;
import org.spongycastle.operator.MacCalculator;

class MacDataGenerator
{
  private PKCS12MacCalculatorBuilder builder;
  
  MacDataGenerator(PKCS12MacCalculatorBuilder paramPKCS12MacCalculatorBuilder)
  {
    this.builder = paramPKCS12MacCalculatorBuilder;
  }
  
  public MacData build(char[] paramArrayOfChar, byte[] paramArrayOfByte)
    throws PKCSException
  {
    MacCalculator localMacCalculator = this.builder.build(paramArrayOfChar);
    AlgorithmIdentifier localAlgorithmIdentifier = localMacCalculator.getAlgorithmIdentifier();
    OutputStream localOutputStream = localMacCalculator.getOutputStream();
    try
    {
      localOutputStream.write(paramArrayOfByte);
      localOutputStream.close();
      DigestInfo localDigestInfo = new DigestInfo(this.builder.getDigestAlgorithmIdentifier(), localMacCalculator.getMac());
      PKCS12PBEParams localPKCS12PBEParams = PKCS12PBEParams.getInstance(localAlgorithmIdentifier.getParameters());
      return new MacData(localDigestInfo, localPKCS12PBEParams.getIV(), localPKCS12PBEParams.getIterations().intValue());
    }
    catch (IOException localIOException)
    {
      throw new PKCSException("unable to process data: " + localIOException.getMessage(), localIOException);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.pkcs.MacDataGenerator
 * JD-Core Version:    0.7.0.1
 */