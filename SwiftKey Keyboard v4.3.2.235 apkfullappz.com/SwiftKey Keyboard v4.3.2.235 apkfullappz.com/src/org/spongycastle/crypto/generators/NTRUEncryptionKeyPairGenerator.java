package org.spongycastle.crypto.generators;

import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.spongycastle.crypto.KeyGenerationParameters;
import org.spongycastle.crypto.params.NTRUEncryptionKeyGenerationParameters;
import org.spongycastle.crypto.params.NTRUEncryptionParameters;
import org.spongycastle.crypto.params.NTRUEncryptionPrivateKeyParameters;
import org.spongycastle.crypto.params.NTRUEncryptionPublicKeyParameters;
import org.spongycastle.math.ntru.polynomial.DenseTernaryPolynomial;
import org.spongycastle.math.ntru.polynomial.IntegerPolynomial;
import org.spongycastle.math.ntru.polynomial.Polynomial;
import org.spongycastle.math.ntru.polynomial.ProductFormPolynomial;
import org.spongycastle.math.ntru.util.Util;

public class NTRUEncryptionKeyPairGenerator
  implements AsymmetricCipherKeyPairGenerator
{
  private NTRUEncryptionKeyGenerationParameters params;
  
  public AsymmetricCipherKeyPair generateKeyPair()
  {
    int i = this.params.N;
    int j = this.params.q;
    int k = this.params.df;
    int m = this.params.df1;
    int n = this.params.df2;
    int i1 = this.params.df3;
    int i2 = this.params.dg;
    boolean bool1 = this.params.fastFp;
    boolean bool2 = this.params.sparse;
    IntegerPolynomial localIntegerPolynomial1 = null;
    IntegerPolynomial localIntegerPolynomial2;
    if (bool1)
    {
      if (this.params.polyType == 0) {}
      for (localObject = Util.generateRandomTernary(i, k, k, bool2, this.params.getRandom());; localObject = ProductFormPolynomial.generateRandom(i, m, n, i1, i1, this.params.getRandom()))
      {
        localIntegerPolynomial2 = ((Polynomial)localObject).toIntegerPolynomial();
        localIntegerPolynomial2.mult(3);
        int[] arrayOfInt = localIntegerPolynomial2.coeffs;
        arrayOfInt[0] = (1 + arrayOfInt[0]);
        label145:
        IntegerPolynomial localIntegerPolynomial3 = localIntegerPolynomial2.invertFq(j);
        if (localIntegerPolynomial3 == null) {
          break;
        }
        if (bool1)
        {
          localIntegerPolynomial1 = new IntegerPolynomial(i);
          localIntegerPolynomial1.coeffs[0] = 1;
        }
        DenseTernaryPolynomial localDenseTernaryPolynomial;
        do
        {
          localDenseTernaryPolynomial = DenseTernaryPolynomial.generateRandom(i, i2, i2 - 1, this.params.getRandom());
        } while (localDenseTernaryPolynomial.invertFq(j) == null);
        IntegerPolynomial localIntegerPolynomial4 = localDenseTernaryPolynomial.mult(localIntegerPolynomial3, j);
        localIntegerPolynomial4.mult3(j);
        localIntegerPolynomial4.ensurePositive(j);
        localDenseTernaryPolynomial.clear();
        localIntegerPolynomial3.clear();
        NTRUEncryptionParameters localNTRUEncryptionParameters = this.params.getEncryptionParameters();
        NTRUEncryptionPrivateKeyParameters localNTRUEncryptionPrivateKeyParameters = new NTRUEncryptionPrivateKeyParameters(localIntegerPolynomial4, (Polynomial)localObject, localIntegerPolynomial1, localNTRUEncryptionParameters);
        NTRUEncryptionPublicKeyParameters localNTRUEncryptionPublicKeyParameters = new NTRUEncryptionPublicKeyParameters(localIntegerPolynomial4, this.params.getEncryptionParameters());
        return new AsymmetricCipherKeyPair(localNTRUEncryptionPublicKeyParameters, localNTRUEncryptionPrivateKeyParameters);
      }
    }
    if (this.params.polyType == 0) {}
    for (Object localObject = Util.generateRandomTernary(i, k, k - 1, bool2, this.params.getRandom());; localObject = ProductFormPolynomial.generateRandom(i, m, n, i1, i1 - 1, this.params.getRandom()))
    {
      localIntegerPolynomial2 = ((Polynomial)localObject).toIntegerPolynomial();
      localIntegerPolynomial1 = localIntegerPolynomial2.invertF3();
      if (localIntegerPolynomial1 == null) {
        break;
      }
      break label145;
    }
  }
  
  public void init(KeyGenerationParameters paramKeyGenerationParameters)
  {
    this.params = ((NTRUEncryptionKeyGenerationParameters)paramKeyGenerationParameters);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.generators.NTRUEncryptionKeyPairGenerator
 * JD-Core Version:    0.7.0.1
 */