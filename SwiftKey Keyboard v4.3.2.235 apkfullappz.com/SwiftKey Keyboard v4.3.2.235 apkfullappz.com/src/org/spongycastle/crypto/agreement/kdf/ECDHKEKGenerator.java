package org.spongycastle.crypto.agreement.kdf;

import java.io.IOException;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.DerivationFunction;
import org.spongycastle.crypto.DerivationParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.generators.KDF2BytesGenerator;
import org.spongycastle.crypto.params.KDFParameters;

public class ECDHKEKGenerator
  implements DerivationFunction
{
  private ASN1ObjectIdentifier algorithm;
  private DerivationFunction kdf;
  private int keySize;
  private byte[] z;
  
  public ECDHKEKGenerator(Digest paramDigest)
  {
    this.kdf = new KDF2BytesGenerator(paramDigest);
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
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(new AlgorithmIdentifier(this.algorithm, new DERNull()));
    localASN1EncodableVector.add(new DERTaggedObject(true, 2, new DEROctetString(integerToBytes(this.keySize))));
    try
    {
      this.kdf.init(new KDFParameters(this.z, new DERSequence(localASN1EncodableVector).getEncoded("DER")));
      return this.kdf.generateBytes(paramArrayOfByte, paramInt1, paramInt2);
    }
    catch (IOException localIOException)
    {
      throw new IllegalArgumentException("unable to initialise kdf: " + localIOException.getMessage());
    }
  }
  
  public Digest getDigest()
  {
    return this.kdf.getDigest();
  }
  
  public void init(DerivationParameters paramDerivationParameters)
  {
    DHKDFParameters localDHKDFParameters = (DHKDFParameters)paramDerivationParameters;
    this.algorithm = localDHKDFParameters.getAlgorithm();
    this.keySize = localDHKDFParameters.getKeySize();
    this.z = localDHKDFParameters.getZ();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.agreement.kdf.ECDHKEKGenerator
 * JD-Core Version:    0.7.0.1
 */