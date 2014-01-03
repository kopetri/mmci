package org.spongycastle.jcajce.provider.symmetric.util;

import java.io.IOException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import javax.crypto.spec.IvParameterSpec;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.util.Arrays;

public class IvAlgorithmParameters
  extends BaseAlgorithmParameters
{
  private byte[] iv;
  
  protected byte[] engineGetEncoded()
    throws IOException
  {
    return engineGetEncoded("ASN.1");
  }
  
  protected byte[] engineGetEncoded(String paramString)
    throws IOException
  {
    if (isASN1FormatString(paramString)) {
      return new DEROctetString(engineGetEncoded("RAW")).getEncoded();
    }
    if (paramString.equals("RAW")) {
      return Arrays.clone(this.iv);
    }
    return null;
  }
  
  protected void engineInit(AlgorithmParameterSpec paramAlgorithmParameterSpec)
    throws InvalidParameterSpecException
  {
    if (!(paramAlgorithmParameterSpec instanceof IvParameterSpec)) {
      throw new InvalidParameterSpecException("IvParameterSpec required to initialise a IV parameters algorithm parameters object");
    }
    this.iv = ((IvParameterSpec)paramAlgorithmParameterSpec).getIV();
  }
  
  protected void engineInit(byte[] paramArrayOfByte)
    throws IOException
  {
    if ((paramArrayOfByte.length % 8 != 0) && (paramArrayOfByte[0] == 4) && (paramArrayOfByte[1] == -2 + paramArrayOfByte.length)) {
      paramArrayOfByte = ((ASN1OctetString)ASN1Primitive.fromByteArray(paramArrayOfByte)).getOctets();
    }
    this.iv = Arrays.clone(paramArrayOfByte);
  }
  
  protected void engineInit(byte[] paramArrayOfByte, String paramString)
    throws IOException
  {
    if (isASN1FormatString(paramString)) {
      try
      {
        engineInit(((ASN1OctetString)ASN1Primitive.fromByteArray(paramArrayOfByte)).getOctets());
        return;
      }
      catch (Exception localException)
      {
        throw new IOException("Exception decoding: " + localException);
      }
    }
    if (paramString.equals("RAW"))
    {
      engineInit(paramArrayOfByte);
      return;
    }
    throw new IOException("Unknown parameters format in IV parameters object");
  }
  
  protected String engineToString()
  {
    return "IV Parameters";
  }
  
  protected AlgorithmParameterSpec localEngineGetParameterSpec(Class paramClass)
    throws InvalidParameterSpecException
  {
    if (paramClass == IvParameterSpec.class) {
      return new IvParameterSpec(this.iv);
    }
    throw new InvalidParameterSpecException("unknown parameter spec passed to IV parameters object.");
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jcajce.provider.symmetric.util.IvAlgorithmParameters
 * JD-Core Version:    0.7.0.1
 */