package org.spongycastle.crypto.params;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.spongycastle.math.ntru.polynomial.IntegerPolynomial;

public class NTRUSigningPublicKeyParameters
  extends AsymmetricKeyParameter
{
  public IntegerPolynomial h;
  private NTRUSigningParameters params;
  
  public NTRUSigningPublicKeyParameters(InputStream paramInputStream, NTRUSigningParameters paramNTRUSigningParameters)
    throws IOException
  {
    super(false);
    this.h = IntegerPolynomial.fromBinary(paramInputStream, paramNTRUSigningParameters.N, paramNTRUSigningParameters.q);
    this.params = paramNTRUSigningParameters;
  }
  
  public NTRUSigningPublicKeyParameters(IntegerPolynomial paramIntegerPolynomial, NTRUSigningParameters paramNTRUSigningParameters)
  {
    super(false);
    this.h = paramIntegerPolynomial;
    this.params = paramNTRUSigningParameters;
  }
  
  public NTRUSigningPublicKeyParameters(byte[] paramArrayOfByte, NTRUSigningParameters paramNTRUSigningParameters)
  {
    super(false);
    this.h = IntegerPolynomial.fromBinary(paramArrayOfByte, paramNTRUSigningParameters.N, paramNTRUSigningParameters.q);
    this.params = paramNTRUSigningParameters;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {}
    NTRUSigningPublicKeyParameters localNTRUSigningPublicKeyParameters;
    do
    {
      do
      {
        return true;
        if (paramObject == null) {
          return false;
        }
        if (getClass() != paramObject.getClass()) {
          return false;
        }
        localNTRUSigningPublicKeyParameters = (NTRUSigningPublicKeyParameters)paramObject;
        if (this.h == null)
        {
          if (localNTRUSigningPublicKeyParameters.h != null) {
            return false;
          }
        }
        else if (!this.h.equals(localNTRUSigningPublicKeyParameters.h)) {
          return false;
        }
        if (this.params != null) {
          break;
        }
      } while (localNTRUSigningPublicKeyParameters.params == null);
      return false;
    } while (this.params.equals(localNTRUSigningPublicKeyParameters.params));
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
      NTRUSigningParameters localNTRUSigningParameters = this.params;
      k = 0;
      if (localNTRUSigningParameters != null) {
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
 * Qualified Name:     org.spongycastle.crypto.params.NTRUSigningPublicKeyParameters
 * JD-Core Version:    0.7.0.1
 */