package org.spongycastle.cert.crmf;

import org.spongycastle.asn1.x509.AlgorithmIdentifier;

public abstract interface PKMACValuesCalculator
{
  public abstract byte[] calculateDigest(byte[] paramArrayOfByte)
    throws CRMFException;
  
  public abstract byte[] calculateMac(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    throws CRMFException;
  
  public abstract void setup(AlgorithmIdentifier paramAlgorithmIdentifier1, AlgorithmIdentifier paramAlgorithmIdentifier2)
    throws CRMFException;
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cert.crmf.PKMACValuesCalculator
 * JD-Core Version:    0.7.0.1
 */