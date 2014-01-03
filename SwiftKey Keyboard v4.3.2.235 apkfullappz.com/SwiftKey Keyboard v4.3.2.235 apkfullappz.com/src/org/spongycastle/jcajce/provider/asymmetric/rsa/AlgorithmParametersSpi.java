package org.spongycastle.jcajce.provider.asymmetric.rsa;

import java.io.IOException;
import java.math.BigInteger;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.PSSParameterSpec;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource.PSpecified;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.pkcs.RSAESOAEPparams;
import org.spongycastle.asn1.pkcs.RSASSAPSSparams;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.jcajce.provider.util.DigestFactory;

public abstract class AlgorithmParametersSpi
  extends java.security.AlgorithmParametersSpi
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
  
  public static class OAEP
    extends AlgorithmParametersSpi
  {
    OAEPParameterSpec currentSpec;
    
    protected byte[] engineGetEncoded()
    {
      AlgorithmIdentifier localAlgorithmIdentifier1 = new AlgorithmIdentifier(DigestFactory.getOID(this.currentSpec.getDigestAlgorithm()), new DERNull());
      MGF1ParameterSpec localMGF1ParameterSpec = (MGF1ParameterSpec)this.currentSpec.getMGFParameters();
      AlgorithmIdentifier localAlgorithmIdentifier2 = new AlgorithmIdentifier(PKCSObjectIdentifiers.id_mgf1, new AlgorithmIdentifier(DigestFactory.getOID(localMGF1ParameterSpec.getDigestAlgorithm()), new DERNull()));
      PSource.PSpecified localPSpecified = (PSource.PSpecified)this.currentSpec.getPSource();
      RSAESOAEPparams localRSAESOAEPparams = new RSAESOAEPparams(localAlgorithmIdentifier1, localAlgorithmIdentifier2, new AlgorithmIdentifier(PKCSObjectIdentifiers.id_pSpecified, new DEROctetString(localPSpecified.getValue())));
      try
      {
        byte[] arrayOfByte = localRSAESOAEPparams.getEncoded("DER");
        return arrayOfByte;
      }
      catch (IOException localIOException)
      {
        throw new RuntimeException("Error encoding OAEPParameters");
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
      if (!(paramAlgorithmParameterSpec instanceof OAEPParameterSpec)) {
        throw new InvalidParameterSpecException("OAEPParameterSpec required to initialise an OAEP algorithm parameters object");
      }
      this.currentSpec = ((OAEPParameterSpec)paramAlgorithmParameterSpec);
    }
    
    protected void engineInit(byte[] paramArrayOfByte)
      throws IOException
    {
      try
      {
        RSAESOAEPparams localRSAESOAEPparams = RSAESOAEPparams.getInstance(paramArrayOfByte);
        this.currentSpec = new OAEPParameterSpec(localRSAESOAEPparams.getHashAlgorithm().getAlgorithm().getId(), localRSAESOAEPparams.getMaskGenAlgorithm().getAlgorithm().getId(), new MGF1ParameterSpec(AlgorithmIdentifier.getInstance(localRSAESOAEPparams.getMaskGenAlgorithm().getParameters()).getAlgorithm().getId()), new PSource.PSpecified(ASN1OctetString.getInstance(localRSAESOAEPparams.getPSourceAlgorithm().getParameters()).getOctets()));
        return;
      }
      catch (ClassCastException localClassCastException)
      {
        throw new IOException("Not a valid OAEP Parameter encoding.");
      }
      catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException)
      {
        throw new IOException("Not a valid OAEP Parameter encoding.");
      }
    }
    
    protected void engineInit(byte[] paramArrayOfByte, String paramString)
      throws IOException
    {
      if ((paramString.equalsIgnoreCase("X.509")) || (paramString.equalsIgnoreCase("ASN.1")))
      {
        engineInit(paramArrayOfByte);
        return;
      }
      throw new IOException("Unknown parameter format " + paramString);
    }
    
    protected String engineToString()
    {
      return "OAEP Parameters";
    }
    
    protected AlgorithmParameterSpec localEngineGetParameterSpec(Class paramClass)
      throws InvalidParameterSpecException
    {
      if ((paramClass == OAEPParameterSpec.class) && (this.currentSpec != null)) {
        return this.currentSpec;
      }
      throw new InvalidParameterSpecException("unknown parameter spec passed to OAEP parameters object.");
    }
  }
  
  public static class PSS
    extends AlgorithmParametersSpi
  {
    PSSParameterSpec currentSpec;
    
    protected byte[] engineGetEncoded()
      throws IOException
    {
      PSSParameterSpec localPSSParameterSpec = this.currentSpec;
      AlgorithmIdentifier localAlgorithmIdentifier = new AlgorithmIdentifier(DigestFactory.getOID(localPSSParameterSpec.getDigestAlgorithm()), new DERNull());
      MGF1ParameterSpec localMGF1ParameterSpec = (MGF1ParameterSpec)localPSSParameterSpec.getMGFParameters();
      return new RSASSAPSSparams(localAlgorithmIdentifier, new AlgorithmIdentifier(PKCSObjectIdentifiers.id_mgf1, new AlgorithmIdentifier(DigestFactory.getOID(localMGF1ParameterSpec.getDigestAlgorithm()), new DERNull())), new ASN1Integer(localPSSParameterSpec.getSaltLength()), new ASN1Integer(localPSSParameterSpec.getTrailerField())).getEncoded("DER");
    }
    
    protected byte[] engineGetEncoded(String paramString)
      throws IOException
    {
      if ((paramString.equalsIgnoreCase("X.509")) || (paramString.equalsIgnoreCase("ASN.1"))) {
        return engineGetEncoded();
      }
      return null;
    }
    
    protected void engineInit(AlgorithmParameterSpec paramAlgorithmParameterSpec)
      throws InvalidParameterSpecException
    {
      if (!(paramAlgorithmParameterSpec instanceof PSSParameterSpec)) {
        throw new InvalidParameterSpecException("PSSParameterSpec required to initialise an PSS algorithm parameters object");
      }
      this.currentSpec = ((PSSParameterSpec)paramAlgorithmParameterSpec);
    }
    
    protected void engineInit(byte[] paramArrayOfByte)
      throws IOException
    {
      try
      {
        RSASSAPSSparams localRSASSAPSSparams = RSASSAPSSparams.getInstance(paramArrayOfByte);
        this.currentSpec = new PSSParameterSpec(localRSASSAPSSparams.getHashAlgorithm().getAlgorithm().getId(), localRSASSAPSSparams.getMaskGenAlgorithm().getAlgorithm().getId(), new MGF1ParameterSpec(AlgorithmIdentifier.getInstance(localRSASSAPSSparams.getMaskGenAlgorithm().getParameters()).getAlgorithm().getId()), localRSASSAPSSparams.getSaltLength().intValue(), localRSASSAPSSparams.getTrailerField().intValue());
        return;
      }
      catch (ClassCastException localClassCastException)
      {
        throw new IOException("Not a valid PSS Parameter encoding.");
      }
      catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException)
      {
        throw new IOException("Not a valid PSS Parameter encoding.");
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
      return "PSS Parameters";
    }
    
    protected AlgorithmParameterSpec localEngineGetParameterSpec(Class paramClass)
      throws InvalidParameterSpecException
    {
      if ((paramClass == PSSParameterSpec.class) && (this.currentSpec != null)) {
        return this.currentSpec;
      }
      throw new InvalidParameterSpecException("unknown parameter spec passed to PSS parameters object.");
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jcajce.provider.asymmetric.rsa.AlgorithmParametersSpi
 * JD-Core Version:    0.7.0.1
 */