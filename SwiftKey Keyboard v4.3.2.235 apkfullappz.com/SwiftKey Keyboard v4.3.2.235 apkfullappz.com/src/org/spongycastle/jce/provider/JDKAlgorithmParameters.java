package org.spongycastle.jce.provider;

import java.io.IOException;
import java.math.BigInteger;
import java.security.AlgorithmParametersSpi;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import javax.crypto.spec.PBEParameterSpec;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERInteger;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.pkcs.PBKDF2Params;
import org.spongycastle.asn1.pkcs.PKCS12PBEParams;
import org.spongycastle.jce.spec.IESParameterSpec;

public abstract class JDKAlgorithmParameters
  extends AlgorithmParametersSpi
{
  protected AlgorithmParameterSpec engineGetParameterSpec(Class paramClass)
    throws InvalidParameterSpecException
  {
    if (paramClass == null) {
      throw new NullPointerException("argument to getParameterSpec must not be null");
    }
    return localEngineGetParameterSpec(paramClass);
  }
  
  protected boolean isASN1FormatString(String paramString)
  {
    return (paramString == null) || (paramString.equals("ASN.1"));
  }
  
  protected abstract AlgorithmParameterSpec localEngineGetParameterSpec(Class paramClass)
    throws InvalidParameterSpecException;
  
  public static class IES
    extends JDKAlgorithmParameters
  {
    IESParameterSpec currentSpec;
    
    protected byte[] engineGetEncoded()
    {
      try
      {
        ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
        localASN1EncodableVector.add(new DEROctetString(this.currentSpec.getDerivationV()));
        localASN1EncodableVector.add(new DEROctetString(this.currentSpec.getEncodingV()));
        localASN1EncodableVector.add(new DERInteger(this.currentSpec.getMacKeySize()));
        byte[] arrayOfByte = new DERSequence(localASN1EncodableVector).getEncoded("DER");
        return arrayOfByte;
      }
      catch (IOException localIOException)
      {
        throw new RuntimeException("Error encoding IESParameters");
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
      if (!(paramAlgorithmParameterSpec instanceof IESParameterSpec)) {
        throw new InvalidParameterSpecException("IESParameterSpec required to initialise a IES algorithm parameters object");
      }
      this.currentSpec = ((IESParameterSpec)paramAlgorithmParameterSpec);
    }
    
    protected void engineInit(byte[] paramArrayOfByte)
      throws IOException
    {
      try
      {
        ASN1Sequence localASN1Sequence = (ASN1Sequence)ASN1Primitive.fromByteArray(paramArrayOfByte);
        this.currentSpec = new IESParameterSpec(((ASN1OctetString)localASN1Sequence.getObjectAt(0)).getOctets(), ((ASN1OctetString)localASN1Sequence.getObjectAt(0)).getOctets(), ((DERInteger)localASN1Sequence.getObjectAt(0)).getValue().intValue());
        return;
      }
      catch (ClassCastException localClassCastException)
      {
        throw new IOException("Not a valid IES Parameter encoding.");
      }
      catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException)
      {
        throw new IOException("Not a valid IES Parameter encoding.");
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
      return "IES Parameters";
    }
    
    protected AlgorithmParameterSpec localEngineGetParameterSpec(Class paramClass)
      throws InvalidParameterSpecException
    {
      if (paramClass == IESParameterSpec.class) {
        return this.currentSpec;
      }
      throw new InvalidParameterSpecException("unknown parameter spec passed to ElGamal parameters object.");
    }
  }
  
  public static class PBKDF2
    extends JDKAlgorithmParameters
  {
    PBKDF2Params params;
    
    protected byte[] engineGetEncoded()
    {
      try
      {
        byte[] arrayOfByte = this.params.getEncoded("DER");
        return arrayOfByte;
      }
      catch (IOException localIOException)
      {
        throw new RuntimeException("Oooops! " + localIOException.toString());
      }
    }
    
    protected byte[] engineGetEncoded(String paramString)
    {
      if (isASN1FormatString(paramString)) {
        return engineGetEncoded();
      }
      return null;
    }
    
    protected void engineInit(AlgorithmParameterSpec paramAlgorithmParameterSpec)
      throws InvalidParameterSpecException
    {
      if (!(paramAlgorithmParameterSpec instanceof PBEParameterSpec)) {
        throw new InvalidParameterSpecException("PBEParameterSpec required to initialise a PKCS12 PBE parameters algorithm parameters object");
      }
      PBEParameterSpec localPBEParameterSpec = (PBEParameterSpec)paramAlgorithmParameterSpec;
      this.params = new PBKDF2Params(localPBEParameterSpec.getSalt(), localPBEParameterSpec.getIterationCount());
    }
    
    protected void engineInit(byte[] paramArrayOfByte)
      throws IOException
    {
      this.params = PBKDF2Params.getInstance(ASN1Primitive.fromByteArray(paramArrayOfByte));
    }
    
    protected void engineInit(byte[] paramArrayOfByte, String paramString)
      throws IOException
    {
      if (isASN1FormatString(paramString))
      {
        engineInit(paramArrayOfByte);
        return;
      }
      throw new IOException("Unknown parameters format in PWRIKEK parameters object");
    }
    
    protected String engineToString()
    {
      return "PBKDF2 Parameters";
    }
    
    protected AlgorithmParameterSpec localEngineGetParameterSpec(Class paramClass)
      throws InvalidParameterSpecException
    {
      if (paramClass == PBEParameterSpec.class) {
        return new PBEParameterSpec(this.params.getSalt(), this.params.getIterationCount().intValue());
      }
      throw new InvalidParameterSpecException("unknown parameter spec passed to PKCS12 PBE parameters object.");
    }
  }
  
  public static class PKCS12PBE
    extends JDKAlgorithmParameters
  {
    PKCS12PBEParams params;
    
    protected byte[] engineGetEncoded()
    {
      try
      {
        byte[] arrayOfByte = this.params.getEncoded("DER");
        return arrayOfByte;
      }
      catch (IOException localIOException)
      {
        throw new RuntimeException("Oooops! " + localIOException.toString());
      }
    }
    
    protected byte[] engineGetEncoded(String paramString)
    {
      if (isASN1FormatString(paramString)) {
        return engineGetEncoded();
      }
      return null;
    }
    
    protected void engineInit(AlgorithmParameterSpec paramAlgorithmParameterSpec)
      throws InvalidParameterSpecException
    {
      if (!(paramAlgorithmParameterSpec instanceof PBEParameterSpec)) {
        throw new InvalidParameterSpecException("PBEParameterSpec required to initialise a PKCS12 PBE parameters algorithm parameters object");
      }
      PBEParameterSpec localPBEParameterSpec = (PBEParameterSpec)paramAlgorithmParameterSpec;
      this.params = new PKCS12PBEParams(localPBEParameterSpec.getSalt(), localPBEParameterSpec.getIterationCount());
    }
    
    protected void engineInit(byte[] paramArrayOfByte)
      throws IOException
    {
      this.params = PKCS12PBEParams.getInstance(ASN1Primitive.fromByteArray(paramArrayOfByte));
    }
    
    protected void engineInit(byte[] paramArrayOfByte, String paramString)
      throws IOException
    {
      if (isASN1FormatString(paramString))
      {
        engineInit(paramArrayOfByte);
        return;
      }
      throw new IOException("Unknown parameters format in PKCS12 PBE parameters object");
    }
    
    protected String engineToString()
    {
      return "PKCS12 PBE Parameters";
    }
    
    protected AlgorithmParameterSpec localEngineGetParameterSpec(Class paramClass)
      throws InvalidParameterSpecException
    {
      if (paramClass == PBEParameterSpec.class) {
        return new PBEParameterSpec(this.params.getIV(), this.params.getIterations().intValue());
      }
      throw new InvalidParameterSpecException("unknown parameter spec passed to PKCS12 PBE parameters object.");
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jce.provider.JDKAlgorithmParameters
 * JD-Core Version:    0.7.0.1
 */