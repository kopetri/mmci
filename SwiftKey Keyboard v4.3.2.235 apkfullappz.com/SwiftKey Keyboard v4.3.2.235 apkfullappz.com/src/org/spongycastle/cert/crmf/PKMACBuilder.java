package org.spongycastle.cert.crmf;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.SecureRandom;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.cmp.CMPObjectIdentifiers;
import org.spongycastle.asn1.cmp.PBMParameter;
import org.spongycastle.asn1.iana.IANAObjectIdentifiers;
import org.spongycastle.asn1.oiw.OIWObjectIdentifiers;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.operator.GenericKey;
import org.spongycastle.operator.MacCalculator;
import org.spongycastle.operator.RuntimeOperatorException;
import org.spongycastle.util.Strings;

public class PKMACBuilder
{
  private PKMACValuesCalculator calculator;
  private int iterationCount;
  private AlgorithmIdentifier mac;
  private int maxIterations;
  private AlgorithmIdentifier owf;
  private PBMParameter parameters;
  private SecureRandom random;
  private int saltLength = 20;
  
  private PKMACBuilder(AlgorithmIdentifier paramAlgorithmIdentifier1, int paramInt, AlgorithmIdentifier paramAlgorithmIdentifier2, PKMACValuesCalculator paramPKMACValuesCalculator)
  {
    this.owf = paramAlgorithmIdentifier1;
    this.iterationCount = paramInt;
    this.mac = paramAlgorithmIdentifier2;
    this.calculator = paramPKMACValuesCalculator;
  }
  
  public PKMACBuilder(PKMACValuesCalculator paramPKMACValuesCalculator)
  {
    this(new AlgorithmIdentifier(OIWObjectIdentifiers.idSHA1), 1000, new AlgorithmIdentifier(IANAObjectIdentifiers.hmacSHA1, DERNull.INSTANCE), paramPKMACValuesCalculator);
  }
  
  public PKMACBuilder(PKMACValuesCalculator paramPKMACValuesCalculator, int paramInt)
  {
    this.maxIterations = paramInt;
    this.calculator = paramPKMACValuesCalculator;
  }
  
  private void checkIterationCountCeiling(int paramInt)
  {
    if ((this.maxIterations > 0) && (paramInt > this.maxIterations)) {
      throw new IllegalArgumentException("iteration count exceeds limit (" + paramInt + " > " + this.maxIterations + ")");
    }
  }
  
  private MacCalculator genCalculator(final PBMParameter paramPBMParameter, char[] paramArrayOfChar)
    throws CRMFException
  {
    byte[] arrayOfByte1 = Strings.toUTF8ByteArray(paramArrayOfChar);
    byte[] arrayOfByte2 = paramPBMParameter.getSalt().getOctets();
    final byte[] arrayOfByte3 = new byte[arrayOfByte1.length + arrayOfByte2.length];
    System.arraycopy(arrayOfByte1, 0, arrayOfByte3, 0, arrayOfByte1.length);
    System.arraycopy(arrayOfByte2, 0, arrayOfByte3, arrayOfByte1.length, arrayOfByte2.length);
    this.calculator.setup(paramPBMParameter.getOwf(), paramPBMParameter.getMac());
    int i = paramPBMParameter.getIterationCount().getValue().intValue();
    do
    {
      arrayOfByte3 = this.calculator.calculateDigest(arrayOfByte3);
      i--;
    } while (i > 0);
    new MacCalculator()
    {
      ByteArrayOutputStream bOut = new ByteArrayOutputStream();
      
      public AlgorithmIdentifier getAlgorithmIdentifier()
      {
        return new AlgorithmIdentifier(CMPObjectIdentifiers.passwordBasedMac, paramPBMParameter);
      }
      
      public GenericKey getKey()
      {
        return new GenericKey(arrayOfByte3);
      }
      
      public byte[] getMac()
      {
        try
        {
          byte[] arrayOfByte = PKMACBuilder.this.calculator.calculateMac(arrayOfByte3, this.bOut.toByteArray());
          return arrayOfByte;
        }
        catch (CRMFException localCRMFException)
        {
          throw new RuntimeOperatorException("exception calculating mac: " + localCRMFException.getMessage(), localCRMFException);
        }
      }
      
      public OutputStream getOutputStream()
      {
        return this.bOut;
      }
    };
  }
  
  public MacCalculator build(char[] paramArrayOfChar)
    throws CRMFException
  {
    if (this.parameters != null) {
      return genCalculator(this.parameters, paramArrayOfChar);
    }
    byte[] arrayOfByte = new byte[this.saltLength];
    if (this.random == null) {
      this.random = new SecureRandom();
    }
    this.random.nextBytes(arrayOfByte);
    return genCalculator(new PBMParameter(arrayOfByte, this.owf, this.iterationCount, this.mac), paramArrayOfChar);
  }
  
  public PKMACBuilder setIterationCount(int paramInt)
  {
    if (paramInt < 100) {
      throw new IllegalArgumentException("iteration count must be at least 100");
    }
    checkIterationCountCeiling(paramInt);
    this.iterationCount = paramInt;
    return this;
  }
  
  public PKMACBuilder setParameters(PBMParameter paramPBMParameter)
  {
    checkIterationCountCeiling(paramPBMParameter.getIterationCount().getValue().intValue());
    this.parameters = paramPBMParameter;
    return this;
  }
  
  public PKMACBuilder setSaltLength(int paramInt)
  {
    if (paramInt < 8) {
      throw new IllegalArgumentException("salt length must be at least 8 bytes");
    }
    this.saltLength = paramInt;
    return this;
  }
  
  public PKMACBuilder setSecureRandom(SecureRandom paramSecureRandom)
  {
    this.random = paramSecureRandom;
    return this;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cert.crmf.PKMACBuilder
 * JD-Core Version:    0.7.0.1
 */