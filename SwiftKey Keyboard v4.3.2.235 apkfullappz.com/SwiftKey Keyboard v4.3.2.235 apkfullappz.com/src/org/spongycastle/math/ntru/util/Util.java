package org.spongycastle.math.ntru.util;

import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.spongycastle.math.ntru.euclid.IntEuclidean;
import org.spongycastle.math.ntru.polynomial.DenseTernaryPolynomial;
import org.spongycastle.math.ntru.polynomial.SparseTernaryPolynomial;
import org.spongycastle.math.ntru.polynomial.TernaryPolynomial;

public class Util
{
  private static volatile boolean IS_64_BITNESS_KNOWN;
  private static volatile boolean IS_64_BIT_JVM;
  
  public static TernaryPolynomial generateRandomTernary(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean, SecureRandom paramSecureRandom)
  {
    if (paramBoolean) {
      return SparseTernaryPolynomial.generateRandom(paramInt1, paramInt2, paramInt3, paramSecureRandom);
    }
    return DenseTernaryPolynomial.generateRandom(paramInt1, paramInt2, paramInt3, paramSecureRandom);
  }
  
  public static int[] generateRandomTernary(int paramInt1, int paramInt2, int paramInt3, SecureRandom paramSecureRandom)
  {
    Integer localInteger1 = new Integer(1);
    Integer localInteger2 = new Integer(-1);
    Integer localInteger3 = new Integer(0);
    ArrayList localArrayList = new ArrayList();
    for (int i = 0; i < paramInt2; i++) {
      localArrayList.add(localInteger1);
    }
    for (int j = 0; j < paramInt3; j++) {
      localArrayList.add(localInteger2);
    }
    while (localArrayList.size() < paramInt1) {
      localArrayList.add(localInteger3);
    }
    Collections.shuffle(localArrayList, paramSecureRandom);
    int[] arrayOfInt = new int[paramInt1];
    for (int k = 0; k < paramInt1; k++) {
      arrayOfInt[k] = ((Integer)localArrayList.get(k)).intValue();
    }
    return arrayOfInt;
  }
  
  public static int invert(int paramInt1, int paramInt2)
  {
    int i = paramInt1 % paramInt2;
    if (i < 0) {
      i += paramInt2;
    }
    return IntEuclidean.calculate(i, paramInt2).x;
  }
  
  public static boolean is64BitJVM()
  {
    if (!IS_64_BITNESS_KNOWN)
    {
      String str1 = System.getProperty("os.arch");
      String str2 = System.getProperty("sun.arch.data.model");
      if ((!"amd64".equals(str1)) && (!"x86_64".equals(str1)) && (!"ppc64".equals(str1)) && (!"64".equals(str2))) {
        break label68;
      }
    }
    label68:
    for (boolean bool = true;; bool = false)
    {
      IS_64_BIT_JVM = bool;
      IS_64_BITNESS_KNOWN = true;
      return IS_64_BIT_JVM;
    }
  }
  
  public static int pow(int paramInt1, int paramInt2, int paramInt3)
  {
    int i = 1;
    for (int j = 0; j < paramInt2; j++) {
      i = i * paramInt1 % paramInt3;
    }
    return i;
  }
  
  public static long pow(long paramLong1, int paramInt, long paramLong2)
  {
    long l = 1L;
    for (int i = 0; i < paramInt; i++) {
      l = l * paramLong1 % paramLong2;
    }
    return l;
  }
  
  public static byte[] readFullLength(InputStream paramInputStream, int paramInt)
    throws IOException
  {
    byte[] arrayOfByte = new byte[paramInt];
    if (paramInputStream.read(arrayOfByte) != arrayOfByte.length) {
      throw new IOException("Not enough bytes to read.");
    }
    return arrayOfByte;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.math.ntru.util.Util
 * JD-Core Version:    0.7.0.1
 */