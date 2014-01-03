package org.spongycastle.crypto.agreement.kdf;

import java.io.IOException;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.DERObjectIdentifier;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.DerivationFunction;
import org.spongycastle.crypto.DerivationParameters;
import org.spongycastle.crypto.Digest;

public class DHKEKGenerator
  implements DerivationFunction
{
  private DERObjectIdentifier algorithm;
  private final Digest digest;
  private int keySize;
  private byte[] partyAInfo;
  private byte[] z;
  
  public DHKEKGenerator(Digest paramDigest)
  {
    this.digest = paramDigest;
  }
  
  private byte[] integerToBytes(int paramInt)
  {
    byte[] arrayOfByte = new byte[4];
    arrayOfByte[0] = ((byte)(paramInt >> 24));
    arrayOfByte[1] = ((byte)(paramInt >> 16));
    arrayOfByte[2] = ((byte)(paramInt >> 8));
    arrayOfByte[3] = ((byte)paramInt);
    return arrayOfByte;
  }
  
  public int generateBytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws DataLengthException, IllegalArgumentException
  {
    if (paramArrayOfByte.length - paramInt2 < paramInt1) {
      throw new DataLengthException("output buffer too small");
    }
    long l = paramInt2;
    int i = this.digest.getDigestSize();
    if (l > 8589934591L) {
      throw new IllegalArgumentException("Output length too large");
    }
    int j = (int)((l + i - 1L) / i);
    byte[] arrayOfByte1 = new byte[this.digest.getDigestSize()];
    int k = 1;
    int m = 0;
    if (m < j)
    {
      this.digest.update(this.z, 0, this.z.length);
      ASN1EncodableVector localASN1EncodableVector1 = new ASN1EncodableVector();
      ASN1EncodableVector localASN1EncodableVector2 = new ASN1EncodableVector();
      localASN1EncodableVector2.add(this.algorithm);
      localASN1EncodableVector2.add(new DEROctetString(integerToBytes(k)));
      localASN1EncodableVector1.add(new DERSequence(localASN1EncodableVector2));
      if (this.partyAInfo != null) {
        localASN1EncodableVector1.add(new DERTaggedObject(true, 0, new DEROctetString(this.partyAInfo)));
      }
      localASN1EncodableVector1.add(new DERTaggedObject(true, 2, new DEROctetString(integerToBytes(this.keySize))));
      for (;;)
      {
        try
        {
          byte[] arrayOfByte2 = new DERSequence(localASN1EncodableVector1).getEncoded("DER");
          this.digest.update(arrayOfByte2, 0, arrayOfByte2.length);
          this.digest.doFinal(arrayOfByte1, 0);
          if (paramInt2 > i)
          {
            System.arraycopy(arrayOfByte1, 0, paramArrayOfByte, paramInt1, i);
            paramInt1 += i;
            paramInt2 -= i;
            k++;
            m++;
          }
        }
        catch (IOException localIOException)
        {
          throw new IllegalArgumentException("unable to encode parameter info: " + localIOException.getMessage());
        }
        System.arraycopy(arrayOfByte1, 0, paramArrayOfByte, paramInt1, paramInt2);
      }
    }
    this.digest.reset();
    return paramInt2;
  }
  
  public Digest getDigest()
  {
    return this.digest;
  }
  
  public void init(DerivationParameters paramDerivationParameters)
  {
    DHKDFParameters localDHKDFParameters = (DHKDFParameters)paramDerivationParameters;
    this.algorithm = localDHKDFParameters.getAlgorithm();
    this.keySize = localDHKDFParameters.getKeySize();
    this.z = localDHKDFParameters.getZ();
    this.partyAInfo = localDHKDFParameters.getExtraInfo();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.agreement.kdf.DHKEKGenerator
 * JD-Core Version:    0.7.0.1
 */