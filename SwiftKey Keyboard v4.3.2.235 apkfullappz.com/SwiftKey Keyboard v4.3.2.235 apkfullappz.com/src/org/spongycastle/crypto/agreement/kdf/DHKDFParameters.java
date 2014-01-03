package org.spongycastle.crypto.agreement.kdf;

import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.DERObjectIdentifier;
import org.spongycastle.crypto.DerivationParameters;

public class DHKDFParameters
  implements DerivationParameters
{
  private ASN1ObjectIdentifier algorithm;
  private byte[] extraInfo;
  private int keySize;
  private byte[] z;
  
  public DHKDFParameters(DERObjectIdentifier paramDERObjectIdentifier, int paramInt, byte[] paramArrayOfByte)
  {
    this(paramDERObjectIdentifier, paramInt, paramArrayOfByte, null);
  }
  
  public DHKDFParameters(DERObjectIdentifier paramDERObjectIdentifier, int paramInt, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    this.algorithm = new ASN1ObjectIdentifier(paramDERObjectIdentifier.getId());
    this.keySize = paramInt;
    this.z = paramArrayOfByte1;
    this.extraInfo = paramArrayOfByte2;
  }
  
  public ASN1ObjectIdentifier getAlgorithm()
  {
    return this.algorithm;
  }
  
  public byte[] getExtraInfo()
  {
    return this.extraInfo;
  }
  
  public int getKeySize()
  {
    return this.keySize;
  }
  
  public byte[] getZ()
  {
    return this.z;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.agreement.kdf.DHKDFParameters
 * JD-Core Version:    0.7.0.1
 */