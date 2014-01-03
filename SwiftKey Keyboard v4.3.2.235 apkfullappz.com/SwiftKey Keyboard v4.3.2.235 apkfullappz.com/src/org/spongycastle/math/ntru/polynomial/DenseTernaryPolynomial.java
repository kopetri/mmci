package org.spongycastle.math.ntru.polynomial;

import java.security.SecureRandom;
import org.spongycastle.math.ntru.util.Util;
import org.spongycastle.util.Arrays;

public class DenseTernaryPolynomial
  extends IntegerPolynomial
  implements TernaryPolynomial
{
  DenseTernaryPolynomial(int paramInt)
  {
    super(paramInt);
    checkTernarity();
  }
  
  public DenseTernaryPolynomial(IntegerPolynomial paramIntegerPolynomial)
  {
    this(paramIntegerPolynomial.coeffs);
  }
  
  public DenseTernaryPolynomial(int[] paramArrayOfInt)
  {
    super(paramArrayOfInt);
    checkTernarity();
  }
  
  private void checkTernarity()
  {
    for (int i = 0; i != this.coeffs.length; i++)
    {
      int j = this.coeffs[i];
      if ((j < -1) || (j > 1)) {
        throw new IllegalStateException("Illegal value: " + j + ", must be one of {-1, 0, 1}");
      }
    }
  }
  
  public static DenseTernaryPolynomial generateRandom(int paramInt1, int paramInt2, int paramInt3, SecureRandom paramSecureRandom)
  {
    return new DenseTernaryPolynomial(Util.generateRandomTernary(paramInt1, paramInt2, paramInt3, paramSecureRandom));
  }
  
  public static DenseTernaryPolynomial generateRandom(int paramInt, SecureRandom paramSecureRandom)
  {
    DenseTernaryPolynomial localDenseTernaryPolynomial = new DenseTernaryPolynomial(paramInt);
    for (int i = 0; i < paramInt; i++) {
      localDenseTernaryPolynomial.coeffs[i] = (-1 + paramSecureRandom.nextInt(3));
    }
    return localDenseTernaryPolynomial;
  }
  
  public int[] getNegOnes()
  {
    int i = this.coeffs.length;
    int[] arrayOfInt = new int[i];
    int j = 0;
    int k = 0;
    int m;
    if (j < i)
    {
      if (this.coeffs[j] != -1) {
        break label58;
      }
      m = k + 1;
      arrayOfInt[k] = j;
    }
    for (;;)
    {
      j++;
      k = m;
      break;
      return Arrays.copyOf(arrayOfInt, k);
      label58:
      m = k;
    }
  }
  
  public int[] getOnes()
  {
    int i = this.coeffs.length;
    int[] arrayOfInt = new int[i];
    int j = 0;
    int k = 0;
    int m;
    if (j < i)
    {
      if (this.coeffs[j] != 1) {
        break label58;
      }
      m = k + 1;
      arrayOfInt[k] = j;
    }
    for (;;)
    {
      j++;
      k = m;
      break;
      return Arrays.copyOf(arrayOfInt, k);
      label58:
      m = k;
    }
  }
  
  public IntegerPolynomial mult(IntegerPolynomial paramIntegerPolynomial, int paramInt)
  {
    if (paramInt == 2048)
    {
      IntegerPolynomial localIntegerPolynomial = (IntegerPolynomial)paramIntegerPolynomial.clone();
      localIntegerPolynomial.modPositive(2048);
      return new LongPolynomial5(localIntegerPolynomial).mult(this).toIntegerPolynomial();
    }
    return super.mult(paramIntegerPolynomial, paramInt);
  }
  
  public int size()
  {
    return this.coeffs.length;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.math.ntru.polynomial.DenseTernaryPolynomial
 * JD-Core Version:    0.7.0.1
 */