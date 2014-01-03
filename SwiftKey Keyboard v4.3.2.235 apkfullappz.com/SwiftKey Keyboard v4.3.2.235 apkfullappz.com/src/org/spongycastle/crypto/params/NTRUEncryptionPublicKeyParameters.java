package org.spongycastle.crypto.params;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.spongycastle.math.ntru.polynomial.IntegerPolynomial;

public class NTRUEncryptionPublicKeyParameters
  extends NTRUEncryptionKeyParameters
{
  public IntegerPolynomial h;
  
  public NTRUEncryptionPublicKeyParameters(InputStream paramInputStream, NTRUEncryptionParameters paramNTRUEncryptionParameters)
    throws IOException
  {
    super(false, paramNTRUEncryptionParameters);
    this.h = IntegerPolynomial.fromBinary(paramInputStream, paramNTRUEncryptionParameters.N, paramNTRUEncryptionParameters.q);
  }
  
  public NTRUEncryptionPublicKeyParameters(IntegerPolynomial paramIntegerPolynomial, NTRUEncryptionParameters paramNTRUEncryptionParameters)
  {
    super(false, paramNTRUEncryptionParameters);
    this.h = paramIntegerPolynomial;
  }
  
  public NTRUEncryptionPublicKeyParameters(byte[] paramArrayOfByte, NTRUEncryptionParameters paramNTRUEncryptionParameters)
  {
    super(false, paramNTRUEncryptionParameters);
    this.h = IntegerPolynomial.fromBinary(paramArrayOfByte, paramNTRUEncryptionParameters.N, paramNTRUEncryptionParameters.q);
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {}
    NTRUEncryptionPublicKeyParameters localNTRUEncryptionPublicKeyParameters;
    do
    {
      do
      {
        return true;
        if (paramObject == null) {
          return false;
        }
        if (!(paramObject instanceof NTRUEncryptionPublicKeyParameters)) {
          return false;
        }
        localNTRUEncryptionPublicKeyParameters = (NTRUEncryptionPublicKeyParameters)paramObject;
        if (this.h == null)
        {
          if (localNTRUEncryptionPublicKeyParameters.h != null) {
            return false;
          }
        }
        else if (!this.h.equals(localNTRUEncryptionPublicKeyParameters.h)) {
          return false;
        }
        if (this.params != null) {
          break;
        }
      } while (localNTRUEncryptionPublicKeyParameters.params == null);
      return false;
    } while (this.params.equals(localNTRUEncryptionPublicKeyParameters.params));
    return false;
  }
  
  public byte[] getEncoded()
  {
    return this.h.toBinary(this.params.q);
  }
  
  public int hashCode()
  {
    int i;
    int j;
    int k;
    if (this.h == null)
    {
      i = 0;
      j = 31 * (i + 31);
      NTRUEncryptionParameters localNTRUEncryptionParameters = this.params;
      k = 0;
      if (localNTRUEncryptionParameters != null) {
        break label45;
      }
    }
    for (;;)
    {
      return j + k;
      i = this.h.hashCode();
      break;
      label45:
      k = this.params.hashCode();
    }
  }
  
  public void writeTo(OutputStream paramOutputStream)
    throws IOException
  {
    paramOutputStream.write(getEncoded());
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.params.NTRUEncryptionPublicKeyParameters
 * JD-Core Version:    0.7.0.1
 */