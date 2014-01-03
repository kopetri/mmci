package org.spongycastle.math.ntru.polynomial;

import java.math.BigDecimal;

public class BigDecimalPolynomial
{
  private static final BigDecimal ONE_HALF = new BigDecimal("0.5");
  private static final BigDecimal ZERO = new BigDecimal("0");
  BigDecimal[] coeffs;
  
  BigDecimalPolynomial(int paramInt)
  {
    this.coeffs = new BigDecimal[paramInt];
    for (int i = 0; i < paramInt; i++) {
      this.coeffs[i] = ZERO;
    }
  }
  
  public BigDecimalPolynomial(BigIntPolynomial paramBigIntPolynomial)
  {
    int i = paramBigIntPolynomial.coeffs.length;
    this.coeffs = new BigDecimal[i];
    for (int j = 0; j < i; j++) {
      this.coeffs[j] = new BigDecimal(paramBigIntPolynomial.coeffs[j]);
    }
  }
  
  BigDecimalPolynomial(BigDecimal[] paramArrayOfBigDecimal)
  {
    this.coeffs = paramArrayOfBigDecimal;
  }
  
  private BigDecimal[] copyOf(BigDecimal[] paramArrayOfBigDecimal, int paramInt)
  {
    BigDecimal[] arrayOfBigDecimal = new BigDecimal[paramInt];
    if (paramArrayOfBigDecimal.length < paramInt) {
      paramInt = paramArrayOfBigDecimal.length;
    }
    System.arraycopy(paramArrayOfBigDecimal, 0, arrayOfBigDecimal, 0, paramInt);
    return arrayOfBigDecimal;
  }
  
  private BigDecimal[] copyOfRange(BigDecimal[] paramArrayOfBigDecimal, int paramInt1, int paramInt2)
  {
    int i = paramInt2 - paramInt1;
    BigDecimal[] arrayOfBigDecimal = new BigDecimal[i];
    if (paramArrayOfBigDecimal.length - paramInt1 < i) {
      i = paramArrayOfBigDecimal.length - paramInt1;
    }
    System.arraycopy(paramArrayOfBigDecimal, paramInt1, arrayOfBigDecimal, 0, i);
    return arrayOfBigDecimal;
  }
  
  private BigDecimalPolynomial multRecursive(BigDecimalPolynomial paramBigDecimalPolynomial)
  {
    BigDecimal[] arrayOfBigDecimal1 = this.coeffs;
    BigDecimal[] arrayOfBigDecimal2 = paramBigDecimalPolynomial.coeffs;
    int i = paramBigDecimalPolynomial.coeffs.length;
    BigDecimalPolynomial localBigDecimalPolynomial10;
    if (i <= 1)
    {
      BigDecimal[] arrayOfBigDecimal3 = (BigDecimal[])this.coeffs.clone();
      for (int i1 = 0; i1 < this.coeffs.length; i1++) {
        arrayOfBigDecimal3[i1] = arrayOfBigDecimal3[i1].multiply(paramBigDecimalPolynomial.coeffs[0]);
      }
      BigDecimalPolynomial localBigDecimalPolynomial11 = new BigDecimalPolynomial(arrayOfBigDecimal3);
      localBigDecimalPolynomial10 = localBigDecimalPolynomial11;
    }
    for (;;)
    {
      return localBigDecimalPolynomial10;
      int j = i / 2;
      BigDecimalPolynomial localBigDecimalPolynomial1 = new BigDecimalPolynomial(copyOf(arrayOfBigDecimal1, j));
      BigDecimalPolynomial localBigDecimalPolynomial2 = new BigDecimalPolynomial(copyOfRange(arrayOfBigDecimal1, j, i));
      BigDecimalPolynomial localBigDecimalPolynomial3 = new BigDecimalPolynomial(copyOf(arrayOfBigDecimal2, j));
      BigDecimalPolynomial localBigDecimalPolynomial4 = new BigDecimalPolynomial(copyOfRange(arrayOfBigDecimal2, j, i));
      BigDecimalPolynomial localBigDecimalPolynomial5 = (BigDecimalPolynomial)localBigDecimalPolynomial1.clone();
      localBigDecimalPolynomial5.add(localBigDecimalPolynomial2);
      BigDecimalPolynomial localBigDecimalPolynomial6 = (BigDecimalPolynomial)localBigDecimalPolynomial3.clone();
      localBigDecimalPolynomial6.add(localBigDecimalPolynomial4);
      BigDecimalPolynomial localBigDecimalPolynomial7 = localBigDecimalPolynomial1.multRecursive(localBigDecimalPolynomial3);
      BigDecimalPolynomial localBigDecimalPolynomial8 = localBigDecimalPolynomial2.multRecursive(localBigDecimalPolynomial4);
      BigDecimalPolynomial localBigDecimalPolynomial9 = localBigDecimalPolynomial5.multRecursive(localBigDecimalPolynomial6);
      localBigDecimalPolynomial9.sub(localBigDecimalPolynomial7);
      localBigDecimalPolynomial9.sub(localBigDecimalPolynomial8);
      localBigDecimalPolynomial10 = new BigDecimalPolynomial(-1 + i * 2);
      for (int k = 0; k < localBigDecimalPolynomial7.coeffs.length; k++) {
        localBigDecimalPolynomial10.coeffs[k] = localBigDecimalPolynomial7.coeffs[k];
      }
      for (int m = 0; m < localBigDecimalPolynomial9.coeffs.length; m++) {
        localBigDecimalPolynomial10.coeffs[(j + m)] = localBigDecimalPolynomial10.coeffs[(j + m)].add(localBigDecimalPolynomial9.coeffs[m]);
      }
      for (int n = 0; n < localBigDecimalPolynomial8.coeffs.length; n++) {
        localBigDecimalPolynomial10.coeffs[(n + j * 2)] = localBigDecimalPolynomial10.coeffs[(n + j * 2)].add(localBigDecimalPolynomial8.coeffs[n]);
      }
    }
  }
  
  public void add(BigDecimalPolynomial paramBigDecimalPolynomial)
  {
    if (paramBigDecimalPolynomial.coeffs.length > this.coeffs.length)
    {
      int j = this.coeffs.length;
      this.coeffs = copyOf(this.coeffs, paramBigDecimalPolynomial.coeffs.length);
      for (int k = j; k < this.coeffs.length; k++) {
        this.coeffs[k] = ZERO;
      }
    }
    for (int i = 0; i < paramBigDecimalPolynomial.coeffs.length; i++) {
      this.coeffs[i] = this.coeffs[i].add(paramBigDecimalPolynomial.coeffs[i]);
    }
  }
  
  public Object clone()
  {
    return new BigDecimalPolynomial((BigDecimal[])this.coeffs.clone());
  }
  
  public BigDecimal[] getCoeffs()
  {
    BigDecimal[] arrayOfBigDecimal = new BigDecimal[this.coeffs.length];
    System.arraycopy(this.coeffs, 0, arrayOfBigDecimal, 0, this.coeffs.length);
    return arrayOfBigDecimal;
  }
  
  public void halve()
  {
    for (int i = 0; i < this.coeffs.length; i++) {
      this.coeffs[i] = this.coeffs[i].multiply(ONE_HALF);
    }
  }
  
  public BigDecimalPolynomial mult(BigDecimalPolynomial paramBigDecimalPolynomial)
  {
    int i = this.coeffs.length;
    if (paramBigDecimalPolynomial.coeffs.length != i) {
      throw new IllegalArgumentException("Number of coefficients must be the same");
    }
    BigDecimalPolynomial localBigDecimalPolynomial = multRecursive(paramBigDecimalPolynomial);
    if (localBigDecimalPolynomial.coeffs.length > i)
    {
      for (int j = i; j < localBigDecimalPolynomial.coeffs.length; j++) {
        localBigDecimalPolynomial.coeffs[(j - i)] = localBigDecimalPolynomial.coeffs[(j - i)].add(localBigDecimalPolynomial.coeffs[j]);
      }
      localBigDecimalPolynomial.coeffs = copyOf(localBigDecimalPolynomial.coeffs, i);
    }
    return localBigDecimalPolynomial;
  }
  
  public BigDecimalPolynomial mult(BigIntPolynomial paramBigIntPolynomial)
  {
    return mult(new BigDecimalPolynomial(paramBigIntPolynomial));
  }
  
  public BigIntPolynomial round()
  {
    int i = this.coeffs.length;
    BigIntPolynomial localBigIntPolynomial = new BigIntPolynomial(i);
    for (int j = 0; j < i; j++) {
      localBigIntPolynomial.coeffs[j] = this.coeffs[j].setScale(0, 6).toBigInteger();
    }
    return localBigIntPolynomial;
  }
  
  void sub(BigDecimalPolynomial paramBigDecimalPolynomial)
  {
    if (paramBigDecimalPolynomial.coeffs.length > this.coeffs.length)
    {
      int j = this.coeffs.length;
      this.coeffs = copyOf(this.coeffs, paramBigDecimalPolynomial.coeffs.length);
      for (int k = j; k < this.coeffs.length; k++) {
        this.coeffs[k] = ZERO;
      }
    }
    for (int i = 0; i < paramBigDecimalPolynomial.coeffs.length; i++) {
      this.coeffs[i] = this.coeffs[i].subtract(paramBigDecimalPolynomial.coeffs[i]);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.math.ntru.polynomial.BigDecimalPolynomial
 * JD-Core Version:    0.7.0.1
 */