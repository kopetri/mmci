package org.spongycastle.crypto.generators;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.spongycastle.crypto.KeyGenerationParameters;
import org.spongycastle.crypto.params.NTRUSigningKeyGenerationParameters;
import org.spongycastle.crypto.params.NTRUSigningPrivateKeyParameters;
import org.spongycastle.crypto.params.NTRUSigningPrivateKeyParameters.Basis;
import org.spongycastle.crypto.params.NTRUSigningPublicKeyParameters;
import org.spongycastle.math.ntru.euclid.BigIntEuclidean;
import org.spongycastle.math.ntru.polynomial.BigDecimalPolynomial;
import org.spongycastle.math.ntru.polynomial.BigIntPolynomial;
import org.spongycastle.math.ntru.polynomial.DenseTernaryPolynomial;
import org.spongycastle.math.ntru.polynomial.IntegerPolynomial;
import org.spongycastle.math.ntru.polynomial.ModularResultant;
import org.spongycastle.math.ntru.polynomial.Polynomial;
import org.spongycastle.math.ntru.polynomial.ProductFormPolynomial;
import org.spongycastle.math.ntru.polynomial.Resultant;

public class NTRUSigningKeyPairGenerator
  implements AsymmetricCipherKeyPairGenerator
{
  private NTRUSigningKeyGenerationParameters params;
  
  private FGBasis generateBasis()
  {
    int i = this.params.N;
    int j = this.params.q;
    int k = this.params.d;
    int m = this.params.d1;
    int n = this.params.d2;
    int i1 = this.params.d3;
    int i2 = this.params.basisType;
    int i3 = 1 + i * 2;
    boolean bool = this.params.primeCheck;
    Object localObject1;
    IntegerPolynomial localIntegerPolynomial1;
    IntegerPolynomial localIntegerPolynomial2;
    Resultant localResultant1;
    while (this.params.polyType == 0)
    {
      localObject1 = DenseTernaryPolynomial.generateRandom(i, k + 1, k, new SecureRandom());
      localIntegerPolynomial1 = ((Polynomial)localObject1).toIntegerPolynomial();
      if ((!bool) || (!localIntegerPolynomial1.resultant(i3).res.equals(BigInteger.ZERO)))
      {
        localIntegerPolynomial2 = localIntegerPolynomial1.invertFq(j);
        if (localIntegerPolynomial2 != null)
        {
          localResultant1 = localIntegerPolynomial1.resultant();
          label156:
          if (this.params.polyType != 0) {
            break label437;
          }
        }
      }
    }
    IntegerPolynomial localIntegerPolynomial3;
    Resultant localResultant2;
    BigIntPolynomial localBigIntPolynomial1;
    BigIntPolynomial localBigIntPolynomial2;
    int[] arrayOfInt1;
    int[] arrayOfInt2;
    label437:
    for (Object localObject2 = DenseTernaryPolynomial.generateRandom(i, k + 1, k, new SecureRandom());; localObject2 = ProductFormPolynomial.generateRandom(i, m, n, i1 + 1, i1, new SecureRandom()))
    {
      localIntegerPolynomial3 = ((Polynomial)localObject2).toIntegerPolynomial();
      if (((bool) && (localIntegerPolynomial3.resultant(i3).res.equals(BigInteger.ZERO))) || (localIntegerPolynomial3.invertFq(j) == null)) {
        break label156;
      }
      localResultant2 = localIntegerPolynomial3.resultant();
      BigIntEuclidean localBigIntEuclidean = BigIntEuclidean.calculate(localResultant1.res, localResultant2.res);
      if (!localBigIntEuclidean.gcd.equals(BigInteger.ONE)) {
        break label156;
      }
      localBigIntPolynomial1 = (BigIntPolynomial)localResultant1.rho.clone();
      localBigIntPolynomial1.mult(localBigIntEuclidean.x.multiply(BigInteger.valueOf(j)));
      localBigIntPolynomial2 = (BigIntPolynomial)localResultant2.rho.clone();
      localBigIntPolynomial2.mult(localBigIntEuclidean.y.multiply(BigInteger.valueOf(-j)));
      if (this.params.keyGenAlg != 0) {
        break label697;
      }
      arrayOfInt1 = new int[i];
      arrayOfInt2 = new int[i];
      arrayOfInt1[0] = localIntegerPolynomial1.coeffs[0];
      arrayOfInt2[0] = localIntegerPolynomial3.coeffs[0];
      for (int i6 = 1; i6 < i; i6++)
      {
        arrayOfInt1[i6] = localIntegerPolynomial1.coeffs[(i - i6)];
        arrayOfInt2[i6] = localIntegerPolynomial3.coeffs[(i - i6)];
      }
      localObject1 = ProductFormPolynomial.generateRandom(i, m, n, i1 + 1, i1, new SecureRandom());
      break;
    }
    IntegerPolynomial localIntegerPolynomial7 = new IntegerPolynomial(arrayOfInt1);
    IntegerPolynomial localIntegerPolynomial8 = new IntegerPolynomial(arrayOfInt2);
    IntegerPolynomial localIntegerPolynomial9 = ((Polynomial)localObject1).mult(localIntegerPolynomial7);
    localIntegerPolynomial9.add(((Polynomial)localObject2).mult(localIntegerPolynomial8));
    Resultant localResultant3 = localIntegerPolynomial9.resultant();
    BigIntPolynomial localBigIntPolynomial6 = localIntegerPolynomial7.mult(localBigIntPolynomial2);
    localBigIntPolynomial6.add(localIntegerPolynomial8.mult(localBigIntPolynomial1));
    BigIntPolynomial localBigIntPolynomial3 = localBigIntPolynomial6.mult(localResultant3.rho);
    BigInteger localBigInteger = localResultant3.res;
    localBigIntPolynomial3.div(localBigInteger);
    BigIntPolynomial localBigIntPolynomial4 = (BigIntPolynomial)localBigIntPolynomial2.clone();
    localBigIntPolynomial4.sub(((Polynomial)localObject1).mult(localBigIntPolynomial3));
    BigIntPolynomial localBigIntPolynomial5 = (BigIntPolynomial)localBigIntPolynomial1.clone();
    localBigIntPolynomial5.sub(((Polynomial)localObject2).mult(localBigIntPolynomial3));
    IntegerPolynomial localIntegerPolynomial4 = new IntegerPolynomial(localBigIntPolynomial4);
    IntegerPolynomial localIntegerPolynomial5 = new IntegerPolynomial(localBigIntPolynomial5);
    minimizeFG(localIntegerPolynomial1, localIntegerPolynomial3, localIntegerPolynomial4, localIntegerPolynomial5, i);
    Object localObject3;
    if (i2 == 0) {
      localObject3 = localIntegerPolynomial4;
    }
    for (IntegerPolynomial localIntegerPolynomial6 = ((Polynomial)localObject2).mult(localIntegerPolynomial2, j);; localIntegerPolynomial6 = localIntegerPolynomial4.mult(localIntegerPolynomial2, j))
    {
      localIntegerPolynomial6.modPositive(j);
      return new FGBasis((Polynomial)localObject1, (Polynomial)localObject3, localIntegerPolynomial6, localIntegerPolynomial4, localIntegerPolynomial5, this.params);
      label697:
      int i4 = 0;
      int i5 = 1;
      while (i5 < i)
      {
        i4++;
        i5 *= 10;
      }
      BigDecimalPolynomial localBigDecimalPolynomial1 = localResultant1.rho.div(new BigDecimal(localResultant1.res), i4 + (1 + localBigIntPolynomial2.getMaxCoeffLength()));
      BigDecimalPolynomial localBigDecimalPolynomial2 = localResultant2.rho.div(new BigDecimal(localResultant2.res), i4 + (1 + localBigIntPolynomial1.getMaxCoeffLength()));
      BigDecimalPolynomial localBigDecimalPolynomial3 = localBigDecimalPolynomial1.mult(localBigIntPolynomial2);
      localBigDecimalPolynomial3.add(localBigDecimalPolynomial2.mult(localBigIntPolynomial1));
      localBigDecimalPolynomial3.halve();
      localBigIntPolynomial3 = localBigDecimalPolynomial3.round();
      break;
      localObject3 = localObject2;
    }
  }
  
  private void minimizeFG(IntegerPolynomial paramIntegerPolynomial1, IntegerPolynomial paramIntegerPolynomial2, IntegerPolynomial paramIntegerPolynomial3, IntegerPolynomial paramIntegerPolynomial4, int paramInt)
  {
    int i = 0;
    for (int j = 0; j < paramInt; j++) {
      i += paramInt * 2 * (paramIntegerPolynomial1.coeffs[j] * paramIntegerPolynomial1.coeffs[j] + paramIntegerPolynomial2.coeffs[j] * paramIntegerPolynomial2.coeffs[j]);
    }
    int k = i - 4;
    IntegerPolynomial localIntegerPolynomial1 = (IntegerPolynomial)paramIntegerPolynomial1.clone();
    IntegerPolynomial localIntegerPolynomial2 = (IntegerPolynomial)paramIntegerPolynomial2.clone();
    int m = 0;
    int n = 0;
    if ((n < paramInt) && (m < paramInt))
    {
      int i1 = 0;
      for (int i2 = 0; i2 < paramInt; i2++)
      {
        int i4 = paramIntegerPolynomial3.coeffs[i2] * paramIntegerPolynomial1.coeffs[i2];
        int i5 = paramIntegerPolynomial4.coeffs[i2] * paramIntegerPolynomial2.coeffs[i2];
        i1 += paramInt * 4 * (i4 + i5);
      }
      int i3 = i1 - 4 * (paramIntegerPolynomial3.sumCoeffs() + paramIntegerPolynomial4.sumCoeffs());
      if (i3 > k)
      {
        paramIntegerPolynomial3.sub(localIntegerPolynomial1);
        paramIntegerPolynomial4.sub(localIntegerPolynomial2);
        n++;
        m = 0;
      }
      for (;;)
      {
        m++;
        localIntegerPolynomial1.rotate1();
        localIntegerPolynomial2.rotate1();
        break;
        if (i3 < -k)
        {
          paramIntegerPolynomial3.add(localIntegerPolynomial1);
          paramIntegerPolynomial4.add(localIntegerPolynomial2);
          n++;
          m = 0;
        }
      }
    }
  }
  
  public NTRUSigningPrivateKeyParameters.Basis generateBoundedBasis()
  {
    FGBasis localFGBasis;
    do
    {
      localFGBasis = generateBasis();
    } while (!localFGBasis.isNormOk());
    return localFGBasis;
  }
  
  public AsymmetricCipherKeyPair generateKeyPair()
  {
    NTRUSigningPublicKeyParameters localNTRUSigningPublicKeyParameters = null;
    ExecutorService localExecutorService = Executors.newCachedThreadPool();
    ArrayList localArrayList1 = new ArrayList();
    for (int i = this.params.B; i >= 0; i--) {
      localArrayList1.add(localExecutorService.submit(new BasisGenerationTask(null)));
    }
    localExecutorService.shutdown();
    ArrayList localArrayList2 = new ArrayList();
    int j = this.params.B;
    while (j >= 0)
    {
      Future localFuture = (Future)localArrayList1.get(j);
      try
      {
        localArrayList2.add(localFuture.get());
        if (j == this.params.B) {
          localNTRUSigningPublicKeyParameters = new NTRUSigningPublicKeyParameters(((NTRUSigningPrivateKeyParameters.Basis)localFuture.get()).h, this.params.getSigningParameters());
        }
        j--;
      }
      catch (Exception localException)
      {
        throw new IllegalStateException(localException);
      }
    }
    return new AsymmetricCipherKeyPair(localNTRUSigningPublicKeyParameters, new NTRUSigningPrivateKeyParameters(localArrayList2, localNTRUSigningPublicKeyParameters));
  }
  
  public AsymmetricCipherKeyPair generateKeyPairSingleThread()
  {
    ArrayList localArrayList = new ArrayList();
    NTRUSigningPublicKeyParameters localNTRUSigningPublicKeyParameters = null;
    for (int i = this.params.B; i >= 0; i--)
    {
      NTRUSigningPrivateKeyParameters.Basis localBasis = generateBoundedBasis();
      localArrayList.add(localBasis);
      if (i == 0) {
        localNTRUSigningPublicKeyParameters = new NTRUSigningPublicKeyParameters(localBasis.h, this.params.getSigningParameters());
      }
    }
    return new AsymmetricCipherKeyPair(localNTRUSigningPublicKeyParameters, new NTRUSigningPrivateKeyParameters(localArrayList, localNTRUSigningPublicKeyParameters));
  }
  
  public void init(KeyGenerationParameters paramKeyGenerationParameters)
  {
    this.params = ((NTRUSigningKeyGenerationParameters)paramKeyGenerationParameters);
  }
  
  private class BasisGenerationTask
    implements Callable<NTRUSigningPrivateKeyParameters.Basis>
  {
    private BasisGenerationTask() {}
    
    public NTRUSigningPrivateKeyParameters.Basis call()
      throws Exception
    {
      return NTRUSigningKeyPairGenerator.this.generateBoundedBasis();
    }
  }
  
  public class FGBasis
    extends NTRUSigningPrivateKeyParameters.Basis
  {
    public IntegerPolynomial F;
    public IntegerPolynomial G;
    
    FGBasis(Polynomial paramPolynomial1, Polynomial paramPolynomial2, IntegerPolynomial paramIntegerPolynomial1, IntegerPolynomial paramIntegerPolynomial2, IntegerPolynomial paramIntegerPolynomial3, NTRUSigningKeyGenerationParameters paramNTRUSigningKeyGenerationParameters)
    {
      super(paramPolynomial2, paramIntegerPolynomial1, paramNTRUSigningKeyGenerationParameters);
      this.F = paramIntegerPolynomial2;
      this.G = paramIntegerPolynomial3;
    }
    
    boolean isNormOk()
    {
      double d = NTRUSigningKeyPairGenerator.this.params.keyNormBoundSq;
      int i = NTRUSigningKeyPairGenerator.this.params.q;
      return (this.F.centeredNormSq(i) < d) && (this.G.centeredNormSq(i) < d);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.generators.NTRUSigningKeyPairGenerator
 * JD-Core Version:    0.7.0.1
 */