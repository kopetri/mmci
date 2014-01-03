package org.spongycastle.jcajce.provider.asymmetric.elgamal;

import java.io.IOException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import javax.crypto.spec.DHParameterSpec;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.oiw.ElGamalParameter;
import org.spongycastle.jce.provider.JDKAlgorithmParameters;
import org.spongycastle.jce.spec.ElGamalParameterSpec;

public class AlgorithmParametersSpi
  extends JDKAlgorithmParameters
{
  ElGamalParameterSpec currentSpec;
  
  protected byte[] engineGetEncoded()
  {
    ElGamalParameter localElGamalParameter = new ElGamalParameter(this.currentSpec.getP(), this.currentSpec.getG());
    try
    {
      byte[] arrayOfByte = localElGamalParameter.getEncoded("DER");
      return arrayOfByte;
    }
    catch (IOException localIOException)
    {
      throw new RuntimeException("Error encoding ElGamalParameters");
    }
  }
  
  protected byte[] engineGetEncoded(String paramString)
  {
    if ((isASN1FormatString(paramString)) || (paramString.equalsIgnoreCase("X.509"))) {
      return engineGetEncoded();
    }
    return null;
  }
  
  protected void engineInit(AlgorithmParameterSpec paramAlgorithmParameterSpec)
    throws InvalidParameterSpecException
  {
    if ((!(paramAlgorithmParameterSpec instanceof ElGamalParameterSpec)) && (!(paramAlgorithmParameterSpec instanceof DHParameterSpec))) {
      throw new InvalidParameterSpecException("DHParameterSpec required to initialise a ElGamal algorithm parameters object");
    }
    if ((paramAlgorithmParameterSpec instanceof ElGamalParameterSpec))
    {
      this.currentSpec = ((ElGamalParameterSpec)paramAlgorithmParameterSpec);
      return;
    }
    DHParameterSpec localDHParameterSpec = (DHParameterSpec)paramAlgorithmParameterSpec;
    this.currentSpec = new ElGamalParameterSpec(localDHParameterSpec.getP(), localDHParameterSpec.getG());
  }
  
  protected void engineInit(byte[] paramArrayOfByte)
    throws IOException
  {
    try
    {
      ElGamalParameter localElGamalParameter = new ElGamalParameter((ASN1Sequence)ASN1Primitive.fromByteArray(paramArrayOfByte));
      this.currentSpec = new ElGamalParameterSpec(localElGamalParameter.getP(), localElGamalParameter.getG());
      return;
    }
    catch (ClassCastException localClassCastException)
    {
      throw new IOException("Not a valid ElGamal Parameter encoding.");
    }
    catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException)
    {
      throw new IOException("Not a valid ElGamal Parameter encoding.");
    }
  }
  
  protected void engineInit(byte[] paramArrayOfByte, String paramString)
    throws IOException
  {
    if ((isASN1FormatString(paramString)) || (paramString.equalsIgnoreCase("X.509")))
    {
      engineInit(paramArrayOfByte);
      return;
    }
    throw new IOException("Unknown parameter format " + paramString);
  }
  
  protected String engineToString()
  {
    return "ElGamal Parameters";
  }
  
  protected AlgorithmParameterSpec localEngineGetParameterSpec(Class paramClass)
    throws InvalidParameterSpecException
  {
    if (paramClass == ElGamalParameterSpec.class) {
      return this.currentSpec;
    }
    if (paramClass == DHParameterSpec.class) {
      return new DHParameterSpec(this.currentSpec.getP(), this.currentSpec.getG());
    }
    throw new InvalidParameterSpecException("unknown parameter spec passed to ElGamal parameters object.");
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jcajce.provider.asymmetric.elgamal.AlgorithmParametersSpi
 * JD-Core Version:    0.7.0.1
 */