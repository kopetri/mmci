package org.spongycastle.math.ntru.polynomial;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import org.spongycastle.math.ntru.euclid.BigIntEuclidean;
import org.spongycastle.math.ntru.util.ArrayEncoder;
import org.spongycastle.math.ntru.util.Util;
import org.spongycastle.util.Arrays;

public class IntegerPolynomial
  implements Polynomial
{
  private static final List BIGINT_PRIMES;
  private static final int NUM_EQUAL_RESULTANTS = 3;
  private static final int[] PRIMES = { 4507, 4513, 4517, 4519, 4523, 4547, 4549, 4561, 4567, 4583, 4591, 4597, 4603, 4621, 4637, 4639, 4643, 4649, 4651, 4657, 4663, 4673, 4679, 4691, 4703, 4721, 4723, 4729, 4733, 4751, 4759, 4783, 4787, 4789, 4793, 4799, 4801, 4813, 4817, 4831, 4861, 4871, 4877, 4889, 4903, 4909, 4919, 4931, 4933, 4937, 4943, 4951, 4957, 4967, 4969, 4973, 4987, 4993, 4999, 5003, 5009, 5011, 5021, 5023, 5039, 5051, 5059, 5077, 5081, 5087, 5099, 5101, 5107, 5113, 5119, 5147, 5153, 5167, 5171, 5179, 5189, 5197, 5209, 5227, 5231, 5233, 5237, 5261, 5273, 5279, 5281, 5297, 5303, 5309, 5323, 5333, 5347, 5351, 5381, 5387, 5393, 5399, 5407, 5413, 5417, 5419, 5431, 5437, 5441, 5443, 5449, 5471, 5477, 5479, 5483, 5501, 5503, 5507, 5519, 5521, 5527, 5531, 5557, 5563, 5569, 5573, 5581, 5591, 5623, 5639, 5641, 5647, 5651, 5653, 5657, 5659, 5669, 5683, 5689, 5693, 5701, 5711, 5717, 5737, 5741, 5743, 5749, 5779, 5783, 5791, 5801, 5807, 5813, 5821, 5827, 5839, 5843, 5849, 5851, 5857, 5861, 5867, 5869, 5879, 5881, 5897, 5903, 5923, 5927, 5939, 5953, 5981, 5987, 6007, 6011, 6029, 6037, 6043, 6047, 6053, 6067, 6073, 6079, 6089, 6091, 6101, 6113, 6121, 6131, 6133, 6143, 6151, 6163, 6173, 6197, 6199, 6203, 6211, 6217, 6221, 6229, 6247, 6257, 6263, 6269, 6271, 6277, 6287, 6299, 6301, 6311, 6317, 6323, 6329, 6337, 6343, 6353, 6359, 6361, 6367, 6373, 6379, 6389, 6397, 6421, 6427, 6449, 6451, 6469, 6473, 6481, 6491, 6521, 6529, 6547, 6551, 6553, 6563, 6569, 6571, 6577, 6581, 6599, 6607, 6619, 6637, 6653, 6659, 6661, 6673, 6679, 6689, 6691, 6701, 6703, 6709, 6719, 6733, 6737, 6761, 6763, 6779, 6781, 6791, 6793, 6803, 6823, 6827, 6829, 6833, 6841, 6857, 6863, 6869, 6871, 6883, 6899, 6907, 6911, 6917, 6947, 6949, 6959, 6961, 6967, 6971, 6977, 6983, 6991, 6997, 7001, 7013, 7019, 7027, 7039, 7043, 7057, 7069, 7079, 7103, 7109, 7121, 7127, 7129, 7151, 7159, 7177, 7187, 7193, 7207, 7211, 7213, 7219, 7229, 7237, 7243, 7247, 7253, 7283, 7297, 7307, 7309, 7321, 7331, 7333, 7349, 7351, 7369, 7393, 7411, 7417, 7433, 7451, 7457, 7459, 7477, 7481, 7487, 7489, 7499, 7507, 7517, 7523, 7529, 7537, 7541, 7547, 7549, 7559, 7561, 7573, 7577, 7583, 7589, 7591, 7603, 7607, 7621, 7639, 7643, 7649, 7669, 7673, 7681, 7687, 7691, 7699, 7703, 7717, 7723, 7727, 7741, 7753, 7757, 7759, 7789, 7793, 7817, 7823, 7829, 7841, 7853, 7867, 7873, 7877, 7879, 7883, 7901, 7907, 7919, 7927, 7933, 7937, 7949, 7951, 7963, 7993, 8009, 8011, 8017, 8039, 8053, 8059, 8069, 8081, 8087, 8089, 8093, 8101, 8111, 8117, 8123, 8147, 8161, 8167, 8171, 8179, 8191, 8209, 8219, 8221, 8231, 8233, 8237, 8243, 8263, 8269, 8273, 8287, 8291, 8293, 8297, 8311, 8317, 8329, 8353, 8363, 8369, 8377, 8387, 8389, 8419, 8423, 8429, 8431, 8443, 8447, 8461, 8467, 8501, 8513, 8521, 8527, 8537, 8539, 8543, 8563, 8573, 8581, 8597, 8599, 8609, 8623, 8627, 8629, 8641, 8647, 8663, 8669, 8677, 8681, 8689, 8693, 8699, 8707, 8713, 8719, 8731, 8737, 8741, 8747, 8753, 8761, 8779, 8783, 8803, 8807, 8819, 8821, 8831, 8837, 8839, 8849, 8861, 8863, 8867, 8887, 8893, 8923, 8929, 8933, 8941, 8951, 8963, 8969, 8971, 8999, 9001, 9007, 9011, 9013, 9029, 9041, 9043, 9049, 9059, 9067, 9091, 9103, 9109, 9127, 9133, 9137, 9151, 9157, 9161, 9173, 9181, 9187, 9199, 9203, 9209, 9221, 9227, 9239, 9241, 9257, 9277, 9281, 9283, 9293, 9311, 9319, 9323, 9337, 9341, 9343, 9349, 9371, 9377, 9391, 9397, 9403, 9413, 9419, 9421, 9431, 9433, 9437, 9439, 9461, 9463, 9467, 9473, 9479, 9491, 9497, 9511, 9521, 9533, 9539, 9547, 9551, 9587, 9601, 9613, 9619, 9623, 9629, 9631, 9643, 9649, 9661, 9677, 9679, 9689, 9697, 9719, 9721, 9733, 9739, 9743, 9749, 9767, 9769, 9781, 9787, 9791, 9803, 9811, 9817, 9829, 9833, 9839, 9851, 9857, 9859, 9871, 9883, 9887, 9901, 9907, 9923, 9929, 9931, 9941, 9949, 9967, 9973 };
  public int[] coeffs;
  
  static
  {
    BIGINT_PRIMES = new ArrayList();
    for (int i = 0; i != PRIMES.length; i++) {
      BIGINT_PRIMES.add(BigInteger.valueOf(PRIMES[i]));
    }
  }
  
  public IntegerPolynomial(int paramInt)
  {
    this.coeffs = new int[paramInt];
  }
  
  public IntegerPolynomial(BigIntPolynomial paramBigIntPolynomial)
  {
    this.coeffs = new int[paramBigIntPolynomial.coeffs.length];
    for (int i = 0; i < paramBigIntPolynomial.coeffs.length; i++) {
      this.coeffs[i] = paramBigIntPolynomial.coeffs[i].intValue();
    }
  }
  
  public IntegerPolynomial(int[] paramArrayOfInt)
  {
    this.coeffs = paramArrayOfInt;
  }
  
  private boolean equalsAbsOne()
  {
    int i = 1;
    if (i < this.coeffs.length) {
      if (this.coeffs[i] == 0) {}
    }
    while (Math.abs(this.coeffs[0]) != 1)
    {
      return false;
      i++;
      break;
    }
    return true;
  }
  
  private boolean equalsZero()
  {
    for (int i = 0; i < this.coeffs.length; i++) {
      if (this.coeffs[i] != 0) {
        return false;
      }
    }
    return true;
  }
  
  public static IntegerPolynomial fromBinary(InputStream paramInputStream, int paramInt1, int paramInt2)
    throws IOException
  {
    return new IntegerPolynomial(ArrayEncoder.decodeModQ(paramInputStream, paramInt1, paramInt2));
  }
  
  public static IntegerPolynomial fromBinary(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    return new IntegerPolynomial(ArrayEncoder.decodeModQ(paramArrayOfByte, paramInt1, paramInt2));
  }
  
  public static IntegerPolynomial fromBinary3Sves(byte[] paramArrayOfByte, int paramInt)
  {
    return new IntegerPolynomial(ArrayEncoder.decodeMod3Sves(paramArrayOfByte, paramInt));
  }
  
  public static IntegerPolynomial fromBinary3Tight(InputStream paramInputStream, int paramInt)
    throws IOException
  {
    return new IntegerPolynomial(ArrayEncoder.decodeMod3Tight(paramInputStream, paramInt));
  }
  
  public static IntegerPolynomial fromBinary3Tight(byte[] paramArrayOfByte, int paramInt)
  {
    return new IntegerPolynomial(ArrayEncoder.decodeMod3Tight(paramArrayOfByte, paramInt));
  }
  
  private IntegerPolynomial mod2ToModq(IntegerPolynomial paramIntegerPolynomial, int paramInt)
  {
    if ((Util.is64BitJVM()) && (paramInt == 2048))
    {
      LongPolynomial2 localLongPolynomial21 = new LongPolynomial2(this);
      Object localObject = new LongPolynomial2(paramIntegerPolynomial);
      int j = 2;
      while (j < paramInt)
      {
        j *= 2;
        LongPolynomial2 localLongPolynomial22 = (LongPolynomial2)((LongPolynomial2)localObject).clone();
        localLongPolynomial22.mult2And(j - 1);
        localLongPolynomial22.subAnd(localLongPolynomial21.mult((LongPolynomial2)localObject).mult((LongPolynomial2)localObject), j - 1);
        localObject = localLongPolynomial22;
      }
      return ((LongPolynomial2)localObject).toIntegerPolynomial();
    }
    int i = 2;
    while (i < paramInt)
    {
      i *= 2;
      IntegerPolynomial localIntegerPolynomial = new IntegerPolynomial(Arrays.copyOf(paramIntegerPolynomial.coeffs, paramIntegerPolynomial.coeffs.length));
      localIntegerPolynomial.mult2(i);
      localIntegerPolynomial.sub(mult(paramIntegerPolynomial, i).mult(paramIntegerPolynomial, i), i);
      paramIntegerPolynomial = localIntegerPolynomial;
    }
    return paramIntegerPolynomial;
  }
  
  private void mult2(int paramInt)
  {
    for (int i = 0; i < this.coeffs.length; i++)
    {
      int[] arrayOfInt1 = this.coeffs;
      arrayOfInt1[i] = (2 * arrayOfInt1[i]);
      int[] arrayOfInt2 = this.coeffs;
      arrayOfInt2[i] %= paramInt;
    }
  }
  
  private IntegerPolynomial multRecursive(IntegerPolynomial paramIntegerPolynomial)
  {
    int[] arrayOfInt1 = this.coeffs;
    int[] arrayOfInt2 = paramIntegerPolynomial.coeffs;
    int i = paramIntegerPolynomial.coeffs.length;
    if (i <= 32)
    {
      int i3 = -1 + i * 2;
      localIntegerPolynomial10 = new IntegerPolynomial(new int[i3]);
      for (int i4 = 0; i4 < i3; i4++) {
        for (int i5 = Math.max(0, 1 + (i4 - i));; i5++)
        {
          int i6 = i - 1;
          if (i5 > Math.min(i4, i6)) {
            break;
          }
          int[] arrayOfInt5 = localIntegerPolynomial10.coeffs;
          arrayOfInt5[i4] += arrayOfInt2[i5] * arrayOfInt1[(i4 - i5)];
        }
      }
    }
    int j = i / 2;
    IntegerPolynomial localIntegerPolynomial1 = new IntegerPolynomial(Arrays.copyOf(arrayOfInt1, j));
    IntegerPolynomial localIntegerPolynomial2 = new IntegerPolynomial(Arrays.copyOfRange(arrayOfInt1, j, i));
    IntegerPolynomial localIntegerPolynomial3 = new IntegerPolynomial(Arrays.copyOf(arrayOfInt2, j));
    IntegerPolynomial localIntegerPolynomial4 = new IntegerPolynomial(Arrays.copyOfRange(arrayOfInt2, j, i));
    IntegerPolynomial localIntegerPolynomial5 = (IntegerPolynomial)localIntegerPolynomial1.clone();
    localIntegerPolynomial5.add(localIntegerPolynomial2);
    IntegerPolynomial localIntegerPolynomial6 = (IntegerPolynomial)localIntegerPolynomial3.clone();
    localIntegerPolynomial6.add(localIntegerPolynomial4);
    IntegerPolynomial localIntegerPolynomial7 = localIntegerPolynomial1.multRecursive(localIntegerPolynomial3);
    IntegerPolynomial localIntegerPolynomial8 = localIntegerPolynomial2.multRecursive(localIntegerPolynomial4);
    IntegerPolynomial localIntegerPolynomial9 = localIntegerPolynomial5.multRecursive(localIntegerPolynomial6);
    localIntegerPolynomial9.sub(localIntegerPolynomial7);
    localIntegerPolynomial9.sub(localIntegerPolynomial8);
    IntegerPolynomial localIntegerPolynomial10 = new IntegerPolynomial(-1 + i * 2);
    for (int k = 0; k < localIntegerPolynomial7.coeffs.length; k++) {
      localIntegerPolynomial10.coeffs[k] = localIntegerPolynomial7.coeffs[k];
    }
    for (int m = 0; m < localIntegerPolynomial9.coeffs.length; m++)
    {
      int[] arrayOfInt4 = localIntegerPolynomial10.coeffs;
      int i2 = j + m;
      arrayOfInt4[i2] += localIntegerPolynomial9.coeffs[m];
    }
    for (int n = 0; n < localIntegerPolynomial8.coeffs.length; n++)
    {
      int[] arrayOfInt3 = localIntegerPolynomial10.coeffs;
      int i1 = n + j * 2;
      arrayOfInt3[i1] += localIntegerPolynomial8.coeffs[n];
    }
    return localIntegerPolynomial10;
  }
  
  private void multShiftSub(IntegerPolynomial paramIntegerPolynomial, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = this.coeffs.length;
    for (int j = paramInt2; j < i; j++) {
      this.coeffs[j] = ((this.coeffs[j] - paramInt1 * paramIntegerPolynomial.coeffs[(j - paramInt2)]) % paramInt3);
    }
  }
  
  private void sort(int[] paramArrayOfInt)
  {
    int i = 1;
    while (i != 0)
    {
      i = 0;
      for (int j = 0; j != -1 + paramArrayOfInt.length; j++) {
        if (paramArrayOfInt[j] > paramArrayOfInt[(j + 1)])
        {
          int k = paramArrayOfInt[j];
          paramArrayOfInt[j] = paramArrayOfInt[(j + 1)];
          paramArrayOfInt[(j + 1)] = k;
          i = 1;
        }
      }
    }
  }
  
  private BigInteger squareSum()
  {
    BigInteger localBigInteger = Constants.BIGINT_ZERO;
    for (int i = 0; i < this.coeffs.length; i++) {
      localBigInteger = localBigInteger.add(BigInteger.valueOf(this.coeffs[i] * this.coeffs[i]));
    }
    return localBigInteger;
  }
  
  public void add(IntegerPolynomial paramIntegerPolynomial)
  {
    if (paramIntegerPolynomial.coeffs.length > this.coeffs.length) {
      this.coeffs = Arrays.copyOf(this.coeffs, paramIntegerPolynomial.coeffs.length);
    }
    for (int i = 0; i < paramIntegerPolynomial.coeffs.length; i++)
    {
      int[] arrayOfInt = this.coeffs;
      arrayOfInt[i] += paramIntegerPolynomial.coeffs[i];
    }
  }
  
  public void add(IntegerPolynomial paramIntegerPolynomial, int paramInt)
  {
    add(paramIntegerPolynomial);
    mod(paramInt);
  }
  
  public void center0(int paramInt)
  {
    for (int i = 0; i < this.coeffs.length; i++)
    {
      while (this.coeffs[i] < -paramInt / 2)
      {
        int[] arrayOfInt2 = this.coeffs;
        arrayOfInt2[i] = (paramInt + arrayOfInt2[i]);
      }
      while (this.coeffs[i] > paramInt / 2)
      {
        int[] arrayOfInt1 = this.coeffs;
        arrayOfInt1[i] -= paramInt;
      }
    }
  }
  
  public long centeredNormSq(int paramInt)
  {
    int i = this.coeffs.length;
    IntegerPolynomial localIntegerPolynomial = (IntegerPolynomial)clone();
    localIntegerPolynomial.shiftGap(paramInt);
    long l1 = 0L;
    long l2 = 0L;
    for (int j = 0; j != localIntegerPolynomial.coeffs.length; j++)
    {
      int k = localIntegerPolynomial.coeffs[j];
      l1 += k;
      l2 += k * k;
    }
    return l2 - l1 * l1 / i;
  }
  
  public void clear()
  {
    for (int i = 0; i < this.coeffs.length; i++) {
      this.coeffs[i] = 0;
    }
  }
  
  public Object clone()
  {
    return new IntegerPolynomial((int[])this.coeffs.clone());
  }
  
  public int count(int paramInt)
  {
    int i = 0;
    for (int j = 0; j != this.coeffs.length; j++) {
      if (this.coeffs[j] == paramInt) {
        i++;
      }
    }
    return i;
  }
  
  int degree()
  {
    for (int i = -1 + this.coeffs.length; (i > 0) && (this.coeffs[i] == 0); i--) {}
    return i;
  }
  
  public void div(int paramInt)
  {
    int i = (paramInt + 1) / 2;
    int j = 0;
    if (j < this.coeffs.length)
    {
      int[] arrayOfInt1 = this.coeffs;
      int k = arrayOfInt1[j];
      if (this.coeffs[j] > 0) {}
      for (int m = i;; m = -i)
      {
        arrayOfInt1[j] = (m + k);
        int[] arrayOfInt2 = this.coeffs;
        arrayOfInt2[j] /= paramInt;
        j++;
        break;
      }
    }
  }
  
  public void ensurePositive(int paramInt)
  {
    for (int i = 0; i < this.coeffs.length; i++) {
      while (this.coeffs[i] < 0)
      {
        int[] arrayOfInt = this.coeffs;
        arrayOfInt[i] = (paramInt + arrayOfInt[i]);
      }
    }
  }
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject instanceof IntegerPolynomial)) {
      return Arrays.areEqual(this.coeffs, ((IntegerPolynomial)paramObject).coeffs);
    }
    return false;
  }
  
  public boolean equalsOne()
  {
    int i = 1;
    if (i < this.coeffs.length) {
      if (this.coeffs[i] == 0) {}
    }
    while (this.coeffs[0] != 1)
    {
      return false;
      i++;
      break;
    }
    return true;
  }
  
  public IntegerPolynomial invertF3()
  {
    int i = this.coeffs.length;
    int j = 0;
    Object localObject1 = new IntegerPolynomial(i + 1);
    ((IntegerPolynomial)localObject1).coeffs[0] = 1;
    Object localObject2 = new IntegerPolynomial(i + 1);
    Object localObject3 = new IntegerPolynomial(i + 1);
    ((IntegerPolynomial)localObject3).coeffs = Arrays.copyOf(this.coeffs, i + 1);
    ((IntegerPolynomial)localObject3).modPositive(3);
    Object localObject4 = new IntegerPolynomial(i + 1);
    ((IntegerPolynomial)localObject4).coeffs[0] = -1;
    ((IntegerPolynomial)localObject4).coeffs[i] = 1;
    for (;;)
    {
      if (localObject3.coeffs[0] == 0)
      {
        for (int i1 = 1; i1 <= i; i1++)
        {
          ((IntegerPolynomial)localObject3).coeffs[(i1 - 1)] = localObject3.coeffs[i1];
          ((IntegerPolynomial)localObject2).coeffs[(i + 1 - i1)] = localObject2.coeffs[(i - i1)];
        }
        ((IntegerPolynomial)localObject3).coeffs[i] = 0;
        ((IntegerPolynomial)localObject2).coeffs[0] = 0;
        j++;
        if (((IntegerPolynomial)localObject3).equalsZero()) {
          return null;
        }
      }
      else
      {
        if (((IntegerPolynomial)localObject3).equalsAbsOne()) {
          break;
        }
        if (((IntegerPolynomial)localObject3).degree() < ((IntegerPolynomial)localObject4).degree())
        {
          Object localObject5 = localObject3;
          localObject3 = localObject4;
          localObject4 = localObject5;
          Object localObject6 = localObject1;
          localObject1 = localObject2;
          localObject2 = localObject6;
        }
        if (localObject3.coeffs[0] == localObject4.coeffs[0])
        {
          ((IntegerPolynomial)localObject3).sub((IntegerPolynomial)localObject4, 3);
          ((IntegerPolynomial)localObject1).sub((IntegerPolynomial)localObject2, 3);
        }
        else
        {
          ((IntegerPolynomial)localObject3).add((IntegerPolynomial)localObject4, 3);
          ((IntegerPolynomial)localObject1).add((IntegerPolynomial)localObject2, 3);
        }
      }
    }
    if (localObject1.coeffs[i] != 0) {
      return null;
    }
    IntegerPolynomial localIntegerPolynomial = new IntegerPolynomial(i);
    int k = j % i;
    for (int m = i - 1; m >= 0; m--)
    {
      int n = m - k;
      if (n < 0) {
        n += i;
      }
      localIntegerPolynomial.coeffs[n] = (localObject3.coeffs[0] * localObject1.coeffs[m]);
    }
    localIntegerPolynomial.ensurePositive(3);
    return localIntegerPolynomial;
  }
  
  public IntegerPolynomial invertFq(int paramInt)
  {
    int i = this.coeffs.length;
    int j = 0;
    Object localObject1 = new IntegerPolynomial(i + 1);
    ((IntegerPolynomial)localObject1).coeffs[0] = 1;
    Object localObject2 = new IntegerPolynomial(i + 1);
    Object localObject3 = new IntegerPolynomial(i + 1);
    ((IntegerPolynomial)localObject3).coeffs = Arrays.copyOf(this.coeffs, i + 1);
    ((IntegerPolynomial)localObject3).modPositive(2);
    Object localObject4 = new IntegerPolynomial(i + 1);
    ((IntegerPolynomial)localObject4).coeffs[0] = 1;
    ((IntegerPolynomial)localObject4).coeffs[i] = 1;
    for (;;)
    {
      if (localObject3.coeffs[0] == 0)
      {
        for (int i1 = 1; i1 <= i; i1++)
        {
          ((IntegerPolynomial)localObject3).coeffs[(i1 - 1)] = localObject3.coeffs[i1];
          ((IntegerPolynomial)localObject2).coeffs[(i + 1 - i1)] = localObject2.coeffs[(i - i1)];
        }
        ((IntegerPolynomial)localObject3).coeffs[i] = 0;
        ((IntegerPolynomial)localObject2).coeffs[0] = 0;
        j++;
        if (((IntegerPolynomial)localObject3).equalsZero()) {
          return null;
        }
      }
      else
      {
        if (((IntegerPolynomial)localObject3).equalsOne()) {
          break;
        }
        if (((IntegerPolynomial)localObject3).degree() < ((IntegerPolynomial)localObject4).degree())
        {
          Object localObject5 = localObject3;
          localObject3 = localObject4;
          localObject4 = localObject5;
          Object localObject6 = localObject1;
          localObject1 = localObject2;
          localObject2 = localObject6;
        }
        ((IntegerPolynomial)localObject3).add((IntegerPolynomial)localObject4, 2);
        ((IntegerPolynomial)localObject1).add((IntegerPolynomial)localObject2, 2);
      }
    }
    if (localObject1.coeffs[i] != 0) {
      return null;
    }
    IntegerPolynomial localIntegerPolynomial = new IntegerPolynomial(i);
    int k = j % i;
    for (int m = i - 1; m >= 0; m--)
    {
      int n = m - k;
      if (n < 0) {
        n += i;
      }
      localIntegerPolynomial.coeffs[n] = localObject1.coeffs[m];
    }
    return mod2ToModq(localIntegerPolynomial, paramInt);
  }
  
  public void mod(int paramInt)
  {
    for (int i = 0; i < this.coeffs.length; i++)
    {
      int[] arrayOfInt = this.coeffs;
      arrayOfInt[i] %= paramInt;
    }
  }
  
  public void mod3()
  {
    for (int i = 0; i < this.coeffs.length; i++)
    {
      int[] arrayOfInt1 = this.coeffs;
      arrayOfInt1[i] %= 3;
      if (this.coeffs[i] > 1)
      {
        int[] arrayOfInt3 = this.coeffs;
        arrayOfInt3[i] = (-3 + arrayOfInt3[i]);
      }
      if (this.coeffs[i] < -1)
      {
        int[] arrayOfInt2 = this.coeffs;
        arrayOfInt2[i] = (3 + arrayOfInt2[i]);
      }
    }
  }
  
  void modCenter(int paramInt)
  {
    mod(paramInt);
    for (int i = 0; i < this.coeffs.length; i++)
    {
      while (this.coeffs[i] < paramInt / 2)
      {
        int[] arrayOfInt2 = this.coeffs;
        arrayOfInt2[i] = (paramInt + arrayOfInt2[i]);
      }
      while (this.coeffs[i] >= paramInt / 2)
      {
        int[] arrayOfInt1 = this.coeffs;
        arrayOfInt1[i] -= paramInt;
      }
    }
  }
  
  public void modPositive(int paramInt)
  {
    mod(paramInt);
    ensurePositive(paramInt);
  }
  
  public BigIntPolynomial mult(BigIntPolynomial paramBigIntPolynomial)
  {
    return new BigIntPolynomial(this).mult(paramBigIntPolynomial);
  }
  
  public IntegerPolynomial mult(IntegerPolynomial paramIntegerPolynomial)
  {
    int i = this.coeffs.length;
    if (paramIntegerPolynomial.coeffs.length != i) {
      throw new IllegalArgumentException("Number of coefficients must be the same");
    }
    IntegerPolynomial localIntegerPolynomial = multRecursive(paramIntegerPolynomial);
    if (localIntegerPolynomial.coeffs.length > i)
    {
      for (int j = i; j < localIntegerPolynomial.coeffs.length; j++)
      {
        int[] arrayOfInt = localIntegerPolynomial.coeffs;
        int k = j - i;
        arrayOfInt[k] += localIntegerPolynomial.coeffs[j];
      }
      localIntegerPolynomial.coeffs = Arrays.copyOf(localIntegerPolynomial.coeffs, i);
    }
    return localIntegerPolynomial;
  }
  
  public IntegerPolynomial mult(IntegerPolynomial paramIntegerPolynomial, int paramInt)
  {
    IntegerPolynomial localIntegerPolynomial = mult(paramIntegerPolynomial);
    localIntegerPolynomial.mod(paramInt);
    return localIntegerPolynomial;
  }
  
  public void mult(int paramInt)
  {
    for (int i = 0; i < this.coeffs.length; i++)
    {
      int[] arrayOfInt = this.coeffs;
      arrayOfInt[i] = (paramInt * arrayOfInt[i]);
    }
  }
  
  public void mult3(int paramInt)
  {
    for (int i = 0; i < this.coeffs.length; i++)
    {
      int[] arrayOfInt1 = this.coeffs;
      arrayOfInt1[i] = (3 * arrayOfInt1[i]);
      int[] arrayOfInt2 = this.coeffs;
      arrayOfInt2[i] %= paramInt;
    }
  }
  
  public ModularResultant resultant(int paramInt)
  {
    int[] arrayOfInt1 = Arrays.copyOf(this.coeffs, 1 + this.coeffs.length);
    IntegerPolynomial localIntegerPolynomial = new IntegerPolynomial(arrayOfInt1);
    int i = arrayOfInt1.length;
    Object localObject1 = new IntegerPolynomial(i);
    ((IntegerPolynomial)localObject1).coeffs[0] = -1;
    ((IntegerPolynomial)localObject1).coeffs[(i - 1)] = 1;
    Object localObject2 = new IntegerPolynomial(localIntegerPolynomial.coeffs);
    Object localObject3 = new IntegerPolynomial(i);
    Object localObject4 = new IntegerPolynomial(i);
    ((IntegerPolynomial)localObject4).coeffs[0] = 1;
    int j = i - 1;
    int k = ((IntegerPolynomial)localObject2).degree();
    int m = j;
    int n = 1;
    while (k > 0)
    {
      int i3 = Util.invert(localObject2.coeffs[k], paramInt) * localObject1.coeffs[j] % paramInt;
      ((IntegerPolynomial)localObject1).multShiftSub((IntegerPolynomial)localObject2, i3, j - k, paramInt);
      int i4 = j - k;
      ((IntegerPolynomial)localObject3).multShiftSub((IntegerPolynomial)localObject4, i3, i4, paramInt);
      j = ((IntegerPolynomial)localObject1).degree();
      if (j < k)
      {
        n = n * Util.pow(localObject2.coeffs[k], m - j, paramInt) % paramInt;
        if ((m % 2 == 1) && (k % 2 == 1)) {
          n = -n % paramInt;
        }
        Object localObject5 = localObject1;
        localObject1 = localObject2;
        localObject2 = localObject5;
        int i5 = j;
        j = k;
        Object localObject6 = localObject3;
        localObject3 = localObject4;
        localObject4 = localObject6;
        m = k;
        k = i5;
      }
    }
    int i1 = n * Util.pow(localObject2.coeffs[0], j, paramInt) % paramInt;
    int i2 = Util.invert(localObject2.coeffs[0], paramInt);
    ((IntegerPolynomial)localObject4).mult(i2);
    ((IntegerPolynomial)localObject4).mod(paramInt);
    ((IntegerPolynomial)localObject4).mult(i1);
    ((IntegerPolynomial)localObject4).mod(paramInt);
    int[] arrayOfInt2 = Arrays.copyOf(((IntegerPolynomial)localObject4).coeffs, -1 + ((IntegerPolynomial)localObject4).coeffs.length);
    ((IntegerPolynomial)localObject4).coeffs = arrayOfInt2;
    BigIntPolynomial localBigIntPolynomial = new BigIntPolynomial((IntegerPolynomial)localObject4);
    return new ModularResultant(localBigIntPolynomial, BigInteger.valueOf(i1), BigInteger.valueOf(paramInt));
  }
  
  public Resultant resultant()
  {
    int i = this.coeffs.length;
    LinkedList localLinkedList = new LinkedList();
    BigInteger localBigInteger1 = null;
    Object localObject = Constants.BIGINT_ONE;
    BigInteger localBigInteger2 = Constants.BIGINT_ONE;
    int j = 1;
    Iterator localIterator = BIGINT_PRIMES.iterator();
    for (;;)
    {
      label60:
      BigInteger localBigInteger4;
      BigInteger localBigInteger7;
      if (localIterator.hasNext())
      {
        localBigInteger1 = (BigInteger)localIterator.next();
        ModularResultant localModularResultant = resultant(localBigInteger1.intValue());
        localLinkedList.add(localModularResultant);
        BigInteger localBigInteger3 = ((BigInteger)localObject).multiply(localBigInteger1);
        BigIntEuclidean localBigIntEuclidean = BigIntEuclidean.calculate(localBigInteger1, (BigInteger)localObject);
        localBigInteger4 = localBigInteger2;
        BigInteger localBigInteger5 = localBigIntEuclidean.x.multiply(localBigInteger1);
        localBigInteger2 = localBigInteger2.multiply(localBigInteger5).add(localModularResultant.res.multiply(localBigIntEuclidean.y.multiply((BigInteger)localObject))).mod(localBigInteger3);
        localObject = localBigInteger3;
        BigInteger localBigInteger6 = localBigInteger3.divide(BigInteger.valueOf(2L));
        localBigInteger7 = localBigInteger6.negate();
        if (localBigInteger2.compareTo(localBigInteger6) <= 0) {
          break label245;
        }
        localBigInteger2 = localBigInteger2.subtract((BigInteger)localObject);
      }
      for (;;)
      {
        if (!localBigInteger2.equals(localBigInteger4)) {
          break label267;
        }
        j++;
        if (j < 3) {
          break;
        }
        while (localLinkedList.size() > 1) {
          localLinkedList.addLast(ModularResultant.combineRho((ModularResultant)localLinkedList.removeFirst(), (ModularResultant)localLinkedList.removeFirst()));
        }
        localBigInteger1 = localBigInteger1.nextProbablePrime();
        break label60;
        label245:
        if (localBigInteger2.compareTo(localBigInteger7) < 0) {
          localBigInteger2 = localBigInteger2.add((BigInteger)localObject);
        }
      }
      label267:
      j = 1;
    }
    BigIntPolynomial localBigIntPolynomial = ((ModularResultant)localLinkedList.getFirst()).rho;
    BigInteger localBigInteger8 = ((BigInteger)localObject).divide(BigInteger.valueOf(2L));
    BigInteger localBigInteger9 = localBigInteger8.negate();
    if (localBigInteger2.compareTo(localBigInteger8) > 0) {
      localBigInteger2 = localBigInteger2.subtract((BigInteger)localObject);
    }
    if (localBigInteger2.compareTo(localBigInteger9) < 0) {
      localBigInteger2 = localBigInteger2.add((BigInteger)localObject);
    }
    for (int k = 0; k < i; k++)
    {
      BigInteger localBigInteger10 = localBigIntPolynomial.coeffs[k];
      if (localBigInteger10.compareTo(localBigInteger8) > 0) {
        localBigIntPolynomial.coeffs[k] = localBigInteger10.subtract((BigInteger)localObject);
      }
      if (localBigInteger10.compareTo(localBigInteger9) < 0) {
        localBigIntPolynomial.coeffs[k] = localBigInteger10.add((BigInteger)localObject);
      }
    }
    Resultant localResultant = new Resultant(localBigIntPolynomial, localBigInteger2);
    return localResultant;
  }
  
  public Resultant resultantMultiThread()
  {
    int i = this.coeffs.length;
    BigInteger localBigInteger1 = squareSum().pow((i + 1) / 2).multiply(BigInteger.valueOf(2L).pow((1 + degree()) / 2)).multiply(BigInteger.valueOf(2L));
    BigInteger localBigInteger2 = BigInteger.valueOf(10000L);
    BigInteger localBigInteger3 = Constants.BIGINT_ONE;
    LinkedBlockingQueue localLinkedBlockingQueue = new LinkedBlockingQueue();
    Iterator localIterator = BIGINT_PRIMES.iterator();
    ExecutorService localExecutorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    if (localBigInteger3.compareTo(localBigInteger1) < 0)
    {
      if (localIterator.hasNext()) {}
      for (localBigInteger2 = (BigInteger)localIterator.next();; localBigInteger2 = localBigInteger2.nextProbablePrime())
      {
        ModResultantTask localModResultantTask = new ModResultantTask(localBigInteger2.intValue(), null);
        localLinkedBlockingQueue.add(localExecutorService.submit(localModResultantTask));
        localBigInteger3 = localBigInteger3.multiply(localBigInteger2);
        break;
      }
    }
    BigInteger localBigInteger4;
    BigIntPolynomial localBigIntPolynomial;
    for (;;)
    {
      boolean bool = localLinkedBlockingQueue.isEmpty();
      ModularResultant localModularResultant = null;
      if (!bool) {}
      try
      {
        Future localFuture1 = (Future)localLinkedBlockingQueue.take();
        Future localFuture2 = (Future)localLinkedBlockingQueue.poll();
        if (localFuture2 == null)
        {
          localModularResultant = (ModularResultant)localFuture1.get();
          localExecutorService.shutdown();
          localBigInteger4 = localModularResultant.res;
          localBigIntPolynomial = localModularResultant.rho;
          BigInteger localBigInteger5 = localBigInteger3.divide(BigInteger.valueOf(2L));
          BigInteger localBigInteger6 = localBigInteger5.negate();
          if (localBigInteger4.compareTo(localBigInteger5) > 0) {
            localBigInteger4 = localBigInteger4.subtract(localBigInteger3);
          }
          if (localBigInteger4.compareTo(localBigInteger6) < 0) {
            localBigInteger4 = localBigInteger4.add(localBigInteger3);
          }
          for (int j = 0; j < i; j++)
          {
            BigInteger localBigInteger7 = localBigIntPolynomial.coeffs[j];
            if (localBigInteger7.compareTo(localBigInteger5) > 0) {
              localBigIntPolynomial.coeffs[j] = localBigInteger7.subtract(localBigInteger3);
            }
            if (localBigInteger7.compareTo(localBigInteger6) < 0) {
              localBigIntPolynomial.coeffs[j] = localBigInteger7.add(localBigInteger3);
            }
          }
        }
        CombineTask localCombineTask = new CombineTask((ModularResultant)localFuture1.get(), (ModularResultant)localFuture2.get(), null);
        localLinkedBlockingQueue.add(localExecutorService.submit(localCombineTask));
      }
      catch (Exception localException)
      {
        throw new IllegalStateException(localException.toString());
      }
    }
    Resultant localResultant = new Resultant(localBigIntPolynomial, localBigInteger4);
    return localResultant;
  }
  
  public void rotate1()
  {
    int i = this.coeffs[(-1 + this.coeffs.length)];
    for (int j = -1 + this.coeffs.length; j > 0; j--) {
      this.coeffs[j] = this.coeffs[(j - 1)];
    }
    this.coeffs[0] = i;
  }
  
  void shiftGap(int paramInt)
  {
    modCenter(paramInt);
    int[] arrayOfInt = Arrays.clone(this.coeffs);
    sort(arrayOfInt);
    int i = 0;
    int j = 0;
    for (int k = 0; k < -1 + arrayOfInt.length; k++)
    {
      int i2 = arrayOfInt[(k + 1)] - arrayOfInt[k];
      if (i2 > i)
      {
        i = i2;
        j = arrayOfInt[k];
      }
    }
    int m = arrayOfInt[0];
    int n = arrayOfInt[(-1 + arrayOfInt.length)];
    if (m + (paramInt - n) > i) {}
    for (int i1 = (n + m) / 2;; i1 = j + i / 2 + paramInt / 2)
    {
      sub(i1);
      return;
    }
  }
  
  void sub(int paramInt)
  {
    for (int i = 0; i < this.coeffs.length; i++)
    {
      int[] arrayOfInt = this.coeffs;
      arrayOfInt[i] -= paramInt;
    }
  }
  
  public void sub(IntegerPolynomial paramIntegerPolynomial)
  {
    if (paramIntegerPolynomial.coeffs.length > this.coeffs.length) {
      this.coeffs = Arrays.copyOf(this.coeffs, paramIntegerPolynomial.coeffs.length);
    }
    for (int i = 0; i < paramIntegerPolynomial.coeffs.length; i++)
    {
      int[] arrayOfInt = this.coeffs;
      arrayOfInt[i] -= paramIntegerPolynomial.coeffs[i];
    }
  }
  
  public void sub(IntegerPolynomial paramIntegerPolynomial, int paramInt)
  {
    sub(paramIntegerPolynomial);
    mod(paramInt);
  }
  
  public int sumCoeffs()
  {
    int i = 0;
    for (int j = 0; j < this.coeffs.length; j++) {
      i += this.coeffs[j];
    }
    return i;
  }
  
  public byte[] toBinary(int paramInt)
  {
    return ArrayEncoder.encodeModQ(this.coeffs, paramInt);
  }
  
  public byte[] toBinary3Sves()
  {
    return ArrayEncoder.encodeMod3Sves(this.coeffs);
  }
  
  public byte[] toBinary3Tight()
  {
    BigInteger localBigInteger = Constants.BIGINT_ZERO;
    for (int i = -1 + this.coeffs.length; i >= 0; i--) {
      localBigInteger = localBigInteger.multiply(BigInteger.valueOf(3L)).add(BigInteger.valueOf(1 + this.coeffs[i]));
    }
    int j = (7 + BigInteger.valueOf(3L).pow(this.coeffs.length).bitLength()) / 8;
    byte[] arrayOfByte1 = localBigInteger.toByteArray();
    if (arrayOfByte1.length < j)
    {
      byte[] arrayOfByte2 = new byte[j];
      System.arraycopy(arrayOfByte1, 0, arrayOfByte2, j - arrayOfByte1.length, arrayOfByte1.length);
      return arrayOfByte2;
    }
    if (arrayOfByte1.length > j) {
      arrayOfByte1 = Arrays.copyOfRange(arrayOfByte1, 1, arrayOfByte1.length);
    }
    return arrayOfByte1;
  }
  
  public IntegerPolynomial toIntegerPolynomial()
  {
    return (IntegerPolynomial)clone();
  }
  
  private class CombineTask
    implements Callable<ModularResultant>
  {
    private ModularResultant modRes1;
    private ModularResultant modRes2;
    
    private CombineTask(ModularResultant paramModularResultant1, ModularResultant paramModularResultant2)
    {
      this.modRes1 = paramModularResultant1;
      this.modRes2 = paramModularResultant2;
    }
    
    public ModularResultant call()
    {
      return ModularResultant.combineRho(this.modRes1, this.modRes2);
    }
  }
  
  private class ModResultantTask
    implements Callable<ModularResultant>
  {
    private int modulus;
    
    private ModResultantTask(int paramInt)
    {
      this.modulus = paramInt;
    }
    
    public ModularResultant call()
    {
      return IntegerPolynomial.this.resultant(this.modulus);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.math.ntru.polynomial.IntegerPolynomial
 * JD-Core Version:    0.7.0.1
 */