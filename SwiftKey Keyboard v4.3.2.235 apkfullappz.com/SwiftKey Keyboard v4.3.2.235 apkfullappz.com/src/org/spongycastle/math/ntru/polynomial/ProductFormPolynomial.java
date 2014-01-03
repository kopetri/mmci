package org.spongycastle.math.ntru.polynomial;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import org.spongycastle.util.Arrays;

public class ProductFormPolynomial
  implements Polynomial
{
  private SparseTernaryPolynomial f1;
  private SparseTernaryPolynomial f2;
  private SparseTernaryPolynomial f3;
  
  public ProductFormPolynomial(SparseTernaryPolynomial paramSparseTernaryPolynomial1, SparseTernaryPolynomial paramSparseTernaryPolynomial2, SparseTernaryPolynomial paramSparseTernaryPolynomial3)
  {
    this.f1 = paramSparseTernaryPolynomial1;
    this.f2 = paramSparseTernaryPolynomial2;
    this.f3 = paramSparseTernaryPolynomial3;
  }
  
  public static ProductFormPolynomial fromBinary(InputStream paramInputStream, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
    throws IOException
  {
    return new ProductFormPolynomial(SparseTernaryPolynomial.fromBinary(paramInputStream, paramInt1, paramInt2, paramInt2), SparseTernaryPolynomial.fromBinary(paramInputStream, paramInt1, paramInt3, paramInt3), SparseTernaryPolynomial.fromBinary(paramInputStream, paramInt1, paramInt4, paramInt5));
  }
  
  public static ProductFormPolynomial fromBinary(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
    throws IOException
  {
    return fromBinary(new ByteArrayInputStream(paramArrayOfByte), paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
  }
  
  public static ProductFormPolynomial generateRandom(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, SecureRandom paramSecureRandom)
  {
    return new ProductFormPolynomial(SparseTernaryPolynomial.generateRandom(paramInt1, paramInt2, paramInt2, paramSecureRandom), SparseTernaryPolynomial.generateRandom(paramInt1, paramInt3, paramInt3, paramSecureRandom), SparseTernaryPolynomial.generateRandom(paramInt1, paramInt4, paramInt5, paramSecureRandom));
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {}
    ProductFormPolynomial localProductFormPolynomial;
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
        localProductFormPolynomial = (ProductFormPolynomial)paramObject;
        if (this.f1 == null)
        {
          if (localProductFormPolynomial.f1 != null) {
            return false;
          }
        }
        else if (!this.f1.equals(localProductFormPolynomial.f1)) {
          return false;
        }
        if (this.f2 == null)
        {
          if (localProductFormPolynomial.f2 != null) {
            return false;
          }
        }
        else if (!this.f2.equals(localProductFormPolynomial.f2)) {
          return false;
        }
        if (this.f3 != null) {
          break;
        }
      } while (localProductFormPolynomial.f3 == null);
      return false;
    } while (this.f3.equals(localProductFormPolynomial.f3));
    return false;
  }
  
  public int hashCode()
  {
    int i;
    int k;
    label26:
    int m;
    int n;
    if (this.f1 == null)
    {
      i = 0;
      int j = 31 * (i + 31);
      if (this.f2 != null) {
        break label65;
      }
      k = 0;
      m = 31 * (j + k);
      SparseTernaryPolynomial localSparseTernaryPolynomial = this.f3;
      n = 0;
      if (localSparseTernaryPolynomial != null) {
        break label76;
      }
    }
    for (;;)
    {
      return m + n;
      i = this.f1.hashCode();
      break;
      label65:
      k = this.f2.hashCode();
      break label26;
      label76:
      n = this.f3.hashCode();
    }
  }
  
  public BigIntPolynomial mult(BigIntPolynomial paramBigIntPolynomial)
  {
    BigIntPolynomial localBigIntPolynomial1 = this.f1.mult(paramBigIntPolynomial);
    BigIntPolynomial localBigIntPolynomial2 = this.f2.mult(localBigIntPolynomial1);
    localBigIntPolynomial2.add(this.f3.mult(paramBigIntPolynomial));
    return localBigIntPolynomial2;
  }
  
  public IntegerPolynomial mult(IntegerPolynomial paramIntegerPolynomial)
  {
    IntegerPolynomial localIntegerPolynomial1 = this.f1.mult(paramIntegerPolynomial);
    IntegerPolynomial localIntegerPolynomial2 = this.f2.mult(localIntegerPolynomial1);
    localIntegerPolynomial2.add(this.f3.mult(paramIntegerPolynomial));
    return localIntegerPolynomial2;
  }
  
  public IntegerPolynomial mult(IntegerPolynomial paramIntegerPolynomial, int paramInt)
  {
    IntegerPolynomial localIntegerPolynomial = mult(paramIntegerPolynomial);
    localIntegerPolynomial.mod(paramInt);
    return localIntegerPolynomial;
  }
  
  public byte[] toBinary()
  {
    byte[] arrayOfByte1 = this.f1.toBinary();
    byte[] arrayOfByte2 = this.f2.toBinary();
    byte[] arrayOfByte3 = this.f3.toBinary();
    byte[] arrayOfByte4 = Arrays.copyOf(arrayOfByte1, arrayOfByte1.length + arrayOfByte2.length + arrayOfByte3.length);
    System.arraycopy(arrayOfByte2, 0, arrayOfByte4, arrayOfByte1.length, arrayOfByte2.length);
    System.arraycopy(arrayOfByte3, 0, arrayOfByte4, arrayOfByte1.length + arrayOfByte2.length, arrayOfByte3.length);
    return arrayOfByte4;
  }
  
  public IntegerPolynomial toIntegerPolynomial()
  {
    IntegerPolynomial localIntegerPolynomial = this.f1.mult(this.f2.toIntegerPolynomial());
    localIntegerPolynomial.add(this.f3.toIntegerPolynomial());
    return localIntegerPolynomial;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.math.ntru.polynomial.ProductFormPolynomial
 * JD-Core Version:    0.7.0.1
 */