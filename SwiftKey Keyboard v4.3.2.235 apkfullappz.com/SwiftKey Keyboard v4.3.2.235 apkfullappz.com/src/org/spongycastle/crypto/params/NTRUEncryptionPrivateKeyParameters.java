package org.spongycastle.crypto.params;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.spongycastle.math.ntru.polynomial.DenseTernaryPolynomial;
import org.spongycastle.math.ntru.polynomial.IntegerPolynomial;
import org.spongycastle.math.ntru.polynomial.Polynomial;
import org.spongycastle.math.ntru.polynomial.ProductFormPolynomial;
import org.spongycastle.math.ntru.polynomial.SparseTernaryPolynomial;

public class NTRUEncryptionPrivateKeyParameters
  extends NTRUEncryptionKeyParameters
{
  public IntegerPolynomial fp;
  public IntegerPolynomial h;
  public Polynomial t;
  
  public NTRUEncryptionPrivateKeyParameters(InputStream paramInputStream, NTRUEncryptionParameters paramNTRUEncryptionParameters)
    throws IOException
  {
    super(true, paramNTRUEncryptionParameters);
    int n;
    Object localObject;
    NTRUEncryptionPrivateKeyParameters localNTRUEncryptionPrivateKeyParameters;
    if (paramNTRUEncryptionParameters.polyType == 1)
    {
      int i = paramNTRUEncryptionParameters.N;
      int j = paramNTRUEncryptionParameters.df1;
      int k = paramNTRUEncryptionParameters.df2;
      int m = paramNTRUEncryptionParameters.df3;
      if (paramNTRUEncryptionParameters.fastFp)
      {
        n = paramNTRUEncryptionParameters.df3;
        this.h = IntegerPolynomial.fromBinary(paramInputStream, paramNTRUEncryptionParameters.N, paramNTRUEncryptionParameters.q);
        localObject = ProductFormPolynomial.fromBinary(paramInputStream, i, j, k, m, n);
        localNTRUEncryptionPrivateKeyParameters = this;
      }
    }
    for (;;)
    {
      localNTRUEncryptionPrivateKeyParameters.t = ((Polynomial)localObject);
      init();
      return;
      n = -1 + paramNTRUEncryptionParameters.df3;
      break;
      this.h = IntegerPolynomial.fromBinary(paramInputStream, paramNTRUEncryptionParameters.N, paramNTRUEncryptionParameters.q);
      IntegerPolynomial localIntegerPolynomial = IntegerPolynomial.fromBinary3Tight(paramInputStream, paramNTRUEncryptionParameters.N);
      if (paramNTRUEncryptionParameters.sparse)
      {
        localObject = new SparseTernaryPolynomial(localIntegerPolynomial);
        localNTRUEncryptionPrivateKeyParameters = this;
      }
      else
      {
        localObject = new DenseTernaryPolynomial(localIntegerPolynomial);
        localNTRUEncryptionPrivateKeyParameters = this;
      }
    }
  }
  
  public NTRUEncryptionPrivateKeyParameters(IntegerPolynomial paramIntegerPolynomial1, Polynomial paramPolynomial, IntegerPolynomial paramIntegerPolynomial2, NTRUEncryptionParameters paramNTRUEncryptionParameters)
  {
    super(true, paramNTRUEncryptionParameters);
    this.h = paramIntegerPolynomial1;
    this.t = paramPolynomial;
    this.fp = paramIntegerPolynomial2;
  }
  
  public NTRUEncryptionPrivateKeyParameters(byte[] paramArrayOfByte, NTRUEncryptionParameters paramNTRUEncryptionParameters)
    throws IOException
  {
    this(new ByteArrayInputStream(paramArrayOfByte), paramNTRUEncryptionParameters);
  }
  
  private void init()
  {
    if (this.params.fastFp)
    {
      this.fp = new IntegerPolynomial(this.params.N);
      this.fp.coeffs[0] = 1;
      return;
    }
    this.fp = this.t.toIntegerPolynomial().invertF3();
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {}
    NTRUEncryptionPrivateKeyParameters localNTRUEncryptionPrivateKeyParameters;
    do
    {
      return true;
      if (paramObject == null) {
        return false;
      }
      if (!(paramObject instanceof NTRUEncryptionPrivateKeyParameters)) {
        return false;
      }
      localNTRUEncryptionPrivateKeyParameters = (NTRUEncryptionPrivateKeyParameters)paramObject;
      if (this.params == null)
      {
        if (localNTRUEncryptionPrivateKeyParameters.params != null) {
          return false;
        }
      }
      else if (!this.params.equals(localNTRUEncryptionPrivateKeyParameters.params)) {
        return false;
      }
      if (this.t == null)
      {
        if (localNTRUEncryptionPrivateKeyParameters.t != null) {
          return false;
        }
      }
      else if (!this.t.equals(localNTRUEncryptionPrivateKeyParameters.t)) {
        return false;
      }
    } while (this.h.equals(localNTRUEncryptionPrivateKeyParameters.h));
    return false;
  }
  
  public byte[] getEncoded()
  {
    byte[] arrayOfByte1 = this.h.toBinary(this.params.q);
    if ((this.t instanceof ProductFormPolynomial)) {}
    for (byte[] arrayOfByte2 = ((ProductFormPolynomial)this.t).toBinary();; arrayOfByte2 = this.t.toIntegerPolynomial().toBinary3Tight())
    {
      byte[] arrayOfByte3 = new byte[arrayOfByte1.length + arrayOfByte2.length];
      System.arraycopy(arrayOfByte1, 0, arrayOfByte3, 0, arrayOfByte1.length);
      System.arraycopy(arrayOfByte2, 0, arrayOfByte3, arrayOfByte1.length, arrayOfByte2.length);
      return arrayOfByte3;
    }
  }
  
  public int hashCode()
  {
    int i;
    int k;
    label26:
    int m;
    int n;
    if (this.params == null)
    {
      i = 0;
      int j = 31 * (i + 31);
      if (this.t != null) {
        break label65;
      }
      k = 0;
      m = 31 * (j + k);
      IntegerPolynomial localIntegerPolynomial = this.h;
      n = 0;
      if (localIntegerPolynomial != null) {
        break label76;
      }
    }
    for (;;)
    {
      return m + n;
      i = this.params.hashCode();
      break;
      label65:
      k = this.t.hashCode();
      break label26;
      label76:
      n = this.h.hashCode();
    }
  }
  
  public void writeTo(OutputStream paramOutputStream)
    throws IOException
  {
    paramOutputStream.write(getEncoded());
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.params.NTRUEncryptionPrivateKeyParameters
 * JD-Core Version:    0.7.0.1
 */