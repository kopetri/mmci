package org.spongycastle.math.ntru.polynomial;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.spongycastle.util.Arrays;

public class BigIntPolynomial
{
  private static final double LOG_10_2 = Math.log10(2.0D);
  BigInteger[] coeffs;
  
  BigIntPolynomial(int paramInt)
  {
    this.coeffs = new BigInteger[paramInt];
    for (int i = 0; i < paramInt; i++) {
      this.coeffs[i] = Constants.BIGINT_ZERO;
    }
  }
  
  public BigIntPolynomial(IntegerPolynomial paramIntegerPolynomial)
  {
    this.coeffs = new BigInteger[paramIntegerPolynomial.coeffs.length];
    for (int i = 0; i < this.coeffs.length; i++) {
      this.coeffs[i] = BigInteger.valueOf(paramIntegerPolynomial.coeffs[i]);
    }
  }
  
  BigIntPolynomial(BigInteger[] paramArrayOfBigInteger)
  {
    this.coeffs = paramArrayOfBigInteger;
  }
  
  static BigIntPolynomial generateRandomSmall(int paramInt1, int paramInt2, int paramInt3)
  {
    ArrayList localArrayList = new ArrayList();
    for (int i = 0; i < paramInt2; i++) {
      localArrayList.add(Constants.BIGINT_ONE);
    }
    for (int j = 0; j < paramInt3; j++) {
      localArrayList.add(BigInteger.valueOf(-1L));
    }
    while (localArrayList.size() < paramInt1) {
      localArrayList.add(Constants.BIGINT_ZERO);
    }
    Collections.shuffle(localArrayList, new SecureRandom());
    BigIntPolynomial localBigIntPolynomial = new BigIntPolynomial(paramInt1);
    for (int k = 0; k < localArrayList.size(); k++) {
      localBigIntPolynomial.coeffs[k] = ((BigInteger)localArrayList.get(k));
    }
    return localBigIntPolynomial;
  }
  
  private BigInteger maxCoeffAbs()
  {
    Object localObject = this.coeffs[0].abs();
    for (int i = 1; i < this.coeffs.length; i++)
    {
      BigInteger localBigInteger = this.coeffs[i].abs();
      if (localBigInteger.compareTo((BigInteger)localObject) > 0) {
        localObject = localBigInteger;
      }
    }
    return localObject;
  }
  
  private BigIntPolynomial multRecursive(BigIntPolynomial paramBigIntPolynomial)
  {
    BigInteger[] arrayOfBigInteger1 = this.coeffs;
    BigInteger[] arrayOfBigInteger2 = paramBigIntPolynomial.coeffs;
    int i = paramBigIntPolynomial.coeffs.length;
    BigIntPolynomial localBigIntPolynomial10;
    if (i <= 1)
    {
      BigInteger[] arrayOfBigInteger3 = Arrays.clone(this.coeffs);
      for (int i1 = 0; i1 < this.coeffs.length; i1++) {
        arrayOfBigInteger3[i1] = arrayOfBigInteger3[i1].multiply(paramBigIntPolynomial.coeffs[0]);
      }
      BigIntPolynomial localBigIntPolynomial11 = new BigIntPolynomial(arrayOfBigInteger3);
      localBigIntPolynomial10 = localBigIntPolynomial11;
    }
    for (;;)
    {
      return localBigIntPolynomial10;
      int j = i / 2;
      BigIntPolynomial localBigIntPolynomial1 = new BigIntPolynomial(Arrays.copyOf(arrayOfBigInteger1, j));
      BigIntPolynomial localBigIntPolynomial2 = new BigIntPolynomial(Arrays.copyOfRange(arrayOfBigInteger1, j, i));
      BigIntPolynomial localBigIntPolynomial3 = new BigIntPolynomial(Arrays.copyOf(arrayOfBigInteger2, j));
      BigIntPolynomial localBigIntPolynomial4 = new BigIntPolynomial(Arrays.copyOfRange(arrayOfBigInteger2, j, i));
      BigIntPolynomial localBigIntPolynomial5 = (BigIntPolynomial)localBigIntPolynomial1.clone();
      localBigIntPolynomial5.add(localBigIntPolynomial2);
      BigIntPolynomial localBigIntPolynomial6 = (BigIntPolynomial)localBigIntPolynomial3.clone();
      localBigIntPolynomial6.add(localBigIntPolynomial4);
      BigIntPolynomial localBigIntPolynomial7 = localBigIntPolynomial1.multRecursive(localBigIntPolynomial3);
      BigIntPolynomial localBigIntPolynomial8 = localBigIntPolynomial2.multRecursive(localBigIntPolynomial4);
      BigIntPolynomial localBigIntPolynomial9 = localBigIntPolynomial5.multRecursive(localBigIntPolynomial6);
      localBigIntPolynomial9.sub(localBigIntPolynomial7);
      localBigIntPolynomial9.sub(localBigIntPolynomial8);
      localBigIntPolynomial10 = new BigIntPolynomial(-1 + i * 2);
      for (int k = 0; k < localBigIntPolynomial7.coeffs.length; k++) {
        localBigIntPolynomial10.coeffs[k] = localBigIntPolynomial7.coeffs[k];
      }
      for (int m = 0; m < localBigIntPolynomial9.coeffs.length; m++) {
        localBigIntPolynomial10.coeffs[(j + m)] = localBigIntPolynomial10.coeffs[(j + m)].add(localBigIntPolynomial9.coeffs[m]);
      }
      for (int n = 0; n < localBigIntPolynomial8.coeffs.length; n++) {
        localBigIntPolynomial10.coeffs[(n + j * 2)] = localBigIntPolynomial10.coeffs[(n + j * 2)].add(localBigIntPolynomial8.coeffs[n]);
      }
    }
  }
  
  public void add(BigIntPolynomial paramBigIntPolynomial)
  {
    if (paramBigIntPolynomial.coeffs.length > this.coeffs.length)
    {
      int j = this.coeffs.length;
      this.coeffs = Arrays.copyOf(this.coeffs, paramBigIntPolynomial.coeffs.length);
      for (int k = j; k < this.coeffs.length; k++) {
        this.coeffs[k] = Constants.BIGINT_ZERO;
      }
    }
    for (int i = 0; i < paramBigIntPolynomial.coeffs.length; i++) {
      this.coeffs[i] = this.coeffs[i].add(paramBigIntPolynomial.coeffs[i]);
    }
  }
  
  void add(BigIntPolynomial paramBigIntPolynomial, BigInteger paramBigInteger)
  {
    add(paramBigIntPolynomial);
    mod(paramBigInteger);
  }
  
  public Object clone()
  {
    return new BigIntPolynomial((BigInteger[])this.coeffs.clone());
  }
  
  public BigDecimalPolynomial div(BigDecimal paramBigDecimal, int paramInt)
  {
    int i = 1 + (int)(maxCoeffAbs().bitLength() * LOG_10_2);
    BigDecimal localBigDecimal = Constants.BIGDEC_ONE.divide(paramBigDecimal, 1 + (i + paramInt), 6);
    BigDecimalPolynomial localBigDecimalPolynomial = new BigDecimalPolynomial(this.coeffs.length);
    for (int j = 0; j < this.coeffs.length; j++) {
      localBigDecimalPolynomial.coeffs[j] = new BigDecimal(this.coeffs[j]).multiply(localBigDecimal).setScale(paramInt, 6);
    }
    return localBigDecimalPolynomial;
  }
  
  public void div(BigInteger paramBigInteger)
  {
    BigInteger localBigInteger1 = paramBigInteger.add(Constants.BIGINT_ONE).divide(BigInteger.valueOf(2L));
    int i = 0;
    if (i < this.coeffs.length)
    {
      BigInteger[] arrayOfBigInteger = this.coeffs;
      if (this.coeffs[i].compareTo(Constants.BIGINT_ZERO) > 0) {}
      for (BigInteger localBigInteger2 = this.coeffs[i].add(localBigInteger1);; localBigInteger2 = this.coeffs[i].add(localBigInteger1.negate()))
      {
        arrayOfBigInteger[i] = localBigInteger2;
        this.coeffs[i] = this.coeffs[i].divide(paramBigInteger);
        i++;
        break;
      }
    }
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {}
    BigIntPolynomial localBigIntPolynomial;
    do
    {
      return true;
      if (paramObject == null) {
        return false;
      }
      if (getClass() != paramObject.getClass()) {
        return false;
      }
      localBigIntPolynomial = (BigIntPolynomial)paramObject;
    } while (Arrays.areEqual(this.coeffs, localBigIntPolynomial.coeffs));
    return false;
  }
  
  public BigInteger[] getCoeffs()
  {
    return Arrays.clone(this.coeffs);
  }
  
  public int getMaxCoeffLength()
  {
    return 1 + (int)(maxCoeffAbs().bitLength() * LOG_10_2);
  }
  
  public int hashCode()
  {
    return 31 + Arrays.hashCode(this.coeffs);
  }
  
  public void mod(BigInteger paramBigInteger)
  {
    for (int i = 0; i < this.coeffs.length; i++) {
      this.coeffs[i] = this.coeffs[i].mod(paramBigInteger);
    }
  }
  
  public BigIntPolynomial mult(BigIntPolynomial paramBigIntPolynomial)
  {
    int i = this.coeffs.length;
    if (paramBigIntPolynomial.coeffs.length != i) {
      throw new IllegalArgumentException("Number of coefficients must be the same");
    }
    BigIntPolynomial localBigIntPolynomial = multRecursive(paramBigIntPolynomial);
    if (localBigIntPolynomial.coeffs.length > i)
    {
      for (int j = i; j < localBigIntPolynomial.coeffs.length; j++) {
        localBigIntPolynomial.coeffs[(j - i)] = localBigIntPolynomial.coeffs[(j - i)].add(localBigIntPolynomial.coeffs[j]);
      }
      localBigIntPolynomial.coeffs = Arrays.copyOf(localBigIntPolynomial.coeffs, i);
    }
    return localBigIntPolynomial;
  }
  
  void mult(int paramInt)
  {
    mult(BigInteger.valueOf(paramInt));
  }
  
  public void mult(BigInteger paramBigInteger)
  {
    for (int i = 0; i < this.coeffs.length; i++) {
      this.coeffs[i] = this.coeffs[i].multiply(paramBigInteger);
    }
  }
  
  public void sub(BigIntPolynomial paramBigIntPolynomial)
  {
    if (paramBigIntPolynomial.coeffs.length > this.coeffs.length)
    {
      int j = this.coeffs.length;
      this.coeffs = Arrays.copyOf(this.coeffs, paramBigIntPolynomial.coeffs.length);
      for (int k = j; k < this.coeffs.length; k++) {
        this.coeffs[k] = Constants.BIGINT_ZERO;
      }
    }
    for (int i = 0; i < paramBigIntPolynomial.coeffs.length; i++) {
      this.coeffs[i] = this.coeffs[i].subtract(paramBigIntPolynomial.coeffs[i]);
    }
  }
  
  BigInteger sumCoeffs()
  {
    BigInteger localBigInteger = Constants.BIGINT_ZERO;
    for (int i = 0; i < this.coeffs.length; i++) {
      localBigInteger = localBigInteger.add(this.coeffs[i]);
    }
    return localBigInteger;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.math.ntru.polynomial.BigIntPolynomial
 * JD-Core Version:    0.7.0.1
 */