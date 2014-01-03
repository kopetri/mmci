package org.spongycastle.jcajce.provider.asymmetric.dh;

import java.io.IOException;
import java.math.BigInteger;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import javax.crypto.spec.DHParameterSpec;
import org.spongycastle.asn1.pkcs.DHParameter;

public class AlgorithmParametersSpi
  extends java.security.AlgorithmParametersSpi
{
  DHParameterSpec currentSpec;
  
  protected byte[] engineGetEncoded()
  {
    DHParameter localDHParameter = new DHParameter(this.currentSpec.getP(), this.currentSpec.getG(), this.currentSpec.getL());
    try
    {
      byte[] arrayOfByte = localDHParameter.getEncoded("DER");
      return arrayOfByte;
    }
    catch (IOException localIOException)
    {
      throw new RuntimeException("Error encoding DHParameters");
    }
  }
  
  protected byte[] engineGetEncoded(String paramString)
  {
    if (isASN1FormatString(paramString)) {
      return engineGetEncoded();
    }
    return null;
  }
  
  protected AlgorithmParameterSpec engineGetParameterSpec(Class paramClass)
    throws InvalidParameterSpecException
  {
    if (paramClass == null) {
      throw new NullPointerException("argument to getParameterSpec must not be null");
    }
    return localEngineGetParameterSpec(paramClass);
  }
  
  protected void engineInit(AlgorithmParameterSpec paramAlgorithmParameterSpec)
    throws InvalidParameterSpecException
  {
    if (!(paramAlgorithmParameterSpec instanceof DHParameterSpec)) {
      throw new InvalidParameterSpecException("DHParameterSpec required to initialise a Diffie-Hellman algorithm parameters object");
    }
    this.currentSpec = ((DHParameterSpec)paramAlgorithmParameterSpec);
  }
  
  protected void engineInit(byte[] paramArrayOfByte)
    throws IOException
  {
    try
    {
      DHParameter localDHParameter = DHParameter.getInstance(paramArrayOfByte);
      if (localDHParameter.getL() != null)
      {
        this.currentSpec = new DHParameterSpec(localDHParameter.getP(), localDHParameter.getG(), localDHParameter.getL().intValue());
        return;
      }
      this.currentSpec = new DHParameterSpec(localDHParameter.getP(), localDHParameter.getG());
      return;
    }
    catch (ClassCastException localClassCastException)
    {
      throw new IOException("Not a valid DH Parameter encoding.");
    }
    catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException)
    {
      throw new IOException("Not a valid DH Parameter encoding.");
    }
  }
  
  protected void engineInit(byte[] paramArrayOfByte, String paramString)
    throws IOException
  {
    if (isASN1FormatString(paramString))
    {
      engineInit(paramArrayOfByte);
      return;
    }
    throw new IOException("Unknown parameter format " + paramString);
  }
  
  protected String engineToString()
  {
    return "Diffie-Hellman Parameters";
  }
  
  protected boolean isASN1FormatString(String paramString)
  {
    return (paramString == null) || (paramString.equals("ASN.1"));
  }
  
  protected AlgorithmParameterSpec localEngineGetParameterSpec(Class paramClass)
    throws InvalidParameterSpecException
  {
    if (paramClass == DHParameterSpec.class) {
      return this.currentSpec;
    }
    throw new InvalidParameterSpecException("unknown parameter spec passed to DH parameters object.");
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jcajce.provider.asymmetric.dh.AlgorithmParametersSpi
 * JD-Core Version:    0.7.0.1
 */