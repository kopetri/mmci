package org.spongycastle.crypto.generators;

import java.io.PrintStream;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Vector;
import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.spongycastle.crypto.KeyGenerationParameters;
import org.spongycastle.crypto.params.NaccacheSternKeyGenerationParameters;
import org.spongycastle.crypto.params.NaccacheSternKeyParameters;
import org.spongycastle.crypto.params.NaccacheSternPrivateKeyParameters;

public class NaccacheSternKeyPairGenerator
  implements AsymmetricCipherKeyPairGenerator
{
  private static final BigInteger ONE = BigInteger.valueOf(1L);
  private static int[] smallPrimes = { 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199, 211, 223, 227, 229, 233, 239, 241, 251, 257, 263, 269, 271, 277, 281, 283, 293, 307, 311, 313, 317, 331, 337, 347, 349, 353, 359, 367, 373, 379, 383, 389, 397, 401, 409, 419, 421, 431, 433, 439, 443, 449, 457, 461, 463, 467, 479, 487, 491, 499, 503, 509, 521, 523, 541, 547, 557 };
  private NaccacheSternKeyGenerationParameters param;
  
  private static Vector findFirstPrimes(int paramInt)
  {
    Vector localVector = new Vector(paramInt);
    for (int i = 0; i != paramInt; i++) {
      localVector.addElement(BigInteger.valueOf(smallPrimes[i]));
    }
    return localVector;
  }
  
  private static BigInteger generatePrime(int paramInt1, int paramInt2, SecureRandom paramSecureRandom)
  {
    for (BigInteger localBigInteger = new BigInteger(paramInt1, paramInt2, paramSecureRandom); localBigInteger.bitLength() != paramInt1; localBigInteger = new BigInteger(paramInt1, paramInt2, paramSecureRandom)) {}
    return localBigInteger;
  }
  
  private static int getInt(SecureRandom paramSecureRandom, int paramInt)
  {
    if ((paramInt & -paramInt) == paramInt) {
      return (int)(paramInt * (0x7FFFFFFF & paramSecureRandom.nextInt()) >> 31);
    }
    int i;
    int j;
    do
    {
      i = 0x7FFFFFFF & paramSecureRandom.nextInt();
      j = i % paramInt;
    } while (i - j + (paramInt - 1) < 0);
    return j;
  }
  
  private static Vector permuteList(Vector paramVector, SecureRandom paramSecureRandom)
  {
    Vector localVector1 = new Vector();
    Vector localVector2 = new Vector();
    for (int i = 0; i < paramVector.size(); i++) {
      localVector2.addElement(paramVector.elementAt(i));
    }
    localVector1.addElement(localVector2.elementAt(0));
    localVector2.removeElementAt(0);
    while (localVector2.size() != 0)
    {
      localVector1.insertElementAt(localVector2.elementAt(0), getInt(paramSecureRandom, 1 + localVector1.size()));
      localVector2.removeElementAt(0);
    }
    return localVector1;
  }
  
  public AsymmetricCipherKeyPair generateKeyPair()
  {
    int i = this.param.getStrength();
    SecureRandom localSecureRandom = this.param.getRandom();
    int j = this.param.getCertainty();
    boolean bool = this.param.isDebug();
    if (bool) {
      System.out.println("Fetching first " + this.param.getCntSmallPrimes() + " primes.");
    }
    Vector localVector1 = permuteList(findFirstPrimes(this.param.getCntSmallPrimes()), localSecureRandom);
    BigInteger localBigInteger1 = ONE;
    BigInteger localBigInteger2 = ONE;
    for (int k = 0;; k++)
    {
      int m = localVector1.size() / 2;
      if (k >= m) {
        break;
      }
      BigInteger localBigInteger18 = (BigInteger)localVector1.elementAt(k);
      localBigInteger1 = localBigInteger1.multiply(localBigInteger18);
    }
    for (int n = localVector1.size() / 2;; n++)
    {
      int i1 = localVector1.size();
      if (n >= i1) {
        break;
      }
      BigInteger localBigInteger17 = (BigInteger)localVector1.elementAt(n);
      localBigInteger2 = localBigInteger2.multiply(localBigInteger17);
    }
    BigInteger localBigInteger3 = localBigInteger1.multiply(localBigInteger2);
    int i2 = -48 + (i - localBigInteger3.bitLength());
    BigInteger localBigInteger4 = generatePrime(1 + i2 / 2, j, localSecureRandom);
    BigInteger localBigInteger5 = generatePrime(1 + i2 / 2, j, localSecureRandom);
    long l1 = 0L;
    if (bool) {
      System.out.println("generating p and q");
    }
    BigInteger localBigInteger6 = localBigInteger4.multiply(localBigInteger1).shiftLeft(1);
    BigInteger localBigInteger7 = localBigInteger5.multiply(localBigInteger2).shiftLeft(1);
    BigInteger localBigInteger8;
    BigInteger localBigInteger9;
    BigInteger localBigInteger10;
    BigInteger localBigInteger11;
    for (;;)
    {
      l1 += 1L;
      localBigInteger8 = generatePrime(24, j, localSecureRandom);
      localBigInteger9 = localBigInteger8.multiply(localBigInteger6).add(ONE);
      if (localBigInteger9.isProbablePrime(j))
      {
        do
        {
          do
          {
            localBigInteger10 = generatePrime(24, j, localSecureRandom);
          } while (localBigInteger8.equals(localBigInteger10));
          localBigInteger11 = localBigInteger10.multiply(localBigInteger7).add(ONE);
        } while (!localBigInteger11.isProbablePrime(j));
        if (localBigInteger3.gcd(localBigInteger8.multiply(localBigInteger10)).equals(ONE))
        {
          if (localBigInteger9.multiply(localBigInteger11).bitLength() >= i) {
            break;
          }
          if (bool) {
            System.out.println("key size too small. Should be " + i + " but is actually " + localBigInteger9.multiply(localBigInteger11).bitLength());
          }
        }
      }
    }
    if (bool) {
      System.out.println("needed " + l1 + " tries to generate p and q.");
    }
    BigInteger localBigInteger12 = localBigInteger9.multiply(localBigInteger11);
    BigInteger localBigInteger13 = localBigInteger9.subtract(ONE).multiply(localBigInteger11.subtract(ONE));
    long l2 = 0L;
    if (bool) {
      System.out.println("generating g");
    }
    BigInteger localBigInteger14;
    for (;;)
    {
      Vector localVector2 = new Vector();
      for (int i3 = 0;; i3++)
      {
        int i4 = localVector1.size();
        if (i3 == i4) {
          break;
        }
        BigInteger localBigInteger15 = localBigInteger13.divide((BigInteger)localVector1.elementAt(i3));
        BigInteger localBigInteger16;
        do
        {
          l2 += 1L;
          localBigInteger16 = new BigInteger(i, j, localSecureRandom);
        } while (localBigInteger16.modPow(localBigInteger15, localBigInteger12).equals(ONE));
        localVector2.addElement(localBigInteger16);
      }
      localBigInteger14 = ONE;
      for (int i5 = 0;; i5++)
      {
        int i6 = localVector1.size();
        if (i5 >= i6) {
          break;
        }
        localBigInteger14 = localBigInteger14.multiply(((BigInteger)localVector2.elementAt(i5)).modPow(localBigInteger3.divide((BigInteger)localVector1.elementAt(i5)), localBigInteger12)).mod(localBigInteger12);
      }
      for (int i7 = 0;; i7++)
      {
        int i8 = localVector1.size();
        int i9 = i7;
        int i10 = 0;
        if (i9 < i8)
        {
          if (localBigInteger14.modPow(localBigInteger13.divide((BigInteger)localVector1.elementAt(i7)), localBigInteger12).equals(ONE))
          {
            if (bool) {
              System.out.println("g has order phi(n)/" + localVector1.elementAt(i7) + "\n g: " + localBigInteger14);
            }
            i10 = 1;
          }
        }
        else
        {
          if (i10 != 0) {
            break;
          }
          if (!localBigInteger14.modPow(localBigInteger13.divide(BigInteger.valueOf(4L)), localBigInteger12).equals(ONE)) {
            break label858;
          }
          if (!bool) {
            break;
          }
          System.out.println("g has order phi(n)/4\n g:" + localBigInteger14);
          break;
        }
      }
      label858:
      if (localBigInteger14.modPow(localBigInteger13.divide(localBigInteger8), localBigInteger12).equals(ONE))
      {
        if (bool) {
          System.out.println("g has order phi(n)/p'\n g: " + localBigInteger14);
        }
      }
      else if (localBigInteger14.modPow(localBigInteger13.divide(localBigInteger10), localBigInteger12).equals(ONE))
      {
        if (bool) {
          System.out.println("g has order phi(n)/q'\n g: " + localBigInteger14);
        }
      }
      else if (localBigInteger14.modPow(localBigInteger13.divide(localBigInteger4), localBigInteger12).equals(ONE))
      {
        if (bool) {
          System.out.println("g has order phi(n)/a\n g: " + localBigInteger14);
        }
      }
      else
      {
        if (!localBigInteger14.modPow(localBigInteger13.divide(localBigInteger5), localBigInteger12).equals(ONE)) {
          break;
        }
        if (bool) {
          System.out.println("g has order phi(n)/b\n g: " + localBigInteger14);
        }
      }
    }
    if (bool)
    {
      System.out.println("needed " + l2 + " tries to generate g");
      System.out.println();
      System.out.println("found new NaccacheStern cipher variables:");
      System.out.println("smallPrimes: " + localVector1);
      System.out.println("sigma:...... " + localBigInteger3 + " (" + localBigInteger3.bitLength() + " bits)");
      System.out.println("a:.......... " + localBigInteger4);
      System.out.println("b:.......... " + localBigInteger5);
      System.out.println("p':......... " + localBigInteger8);
      System.out.println("q':......... " + localBigInteger10);
      System.out.println("p:.......... " + localBigInteger9);
      System.out.println("q:.......... " + localBigInteger11);
      System.out.println("n:.......... " + localBigInteger12);
      System.out.println("phi(n):..... " + localBigInteger13);
      System.out.println("g:.......... " + localBigInteger14);
      System.out.println();
    }
    NaccacheSternKeyParameters localNaccacheSternKeyParameters = new NaccacheSternKeyParameters(false, localBigInteger14, localBigInteger12, localBigInteger3.bitLength());
    AsymmetricCipherKeyPair localAsymmetricCipherKeyPair = new AsymmetricCipherKeyPair(localNaccacheSternKeyParameters, new NaccacheSternPrivateKeyParameters(localBigInteger14, localBigInteger12, localBigInteger3.bitLength(), localVector1, localBigInteger13));
    return localAsymmetricCipherKeyPair;
  }
  
  public void init(KeyGenerationParameters paramKeyGenerationParameters)
  {
    this.param = ((NaccacheSternKeyGenerationParameters)paramKeyGenerationParameters);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.generators.NaccacheSternKeyPairGenerator
 * JD-Core Version:    0.7.0.1
 */