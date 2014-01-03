package com.touchtype.runtimeconfigurator;

import java.io.UnsupportedEncodingException;

public class Base64
{
  public static final int CRLF = 4;
  public static final int DEFAULT = 0;
  public static final int NO_CLOSE = 16;
  public static final int NO_PADDING = 1;
  public static final int NO_WRAP = 2;
  public static final int URL_SAFE = 8;
  
  static
  {
    if (!Base64.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      a = bool;
      return;
    }
  }
  
  public static byte[] decode(String paramString, int paramInt)
  {
    return decode(paramString.getBytes(), paramInt);
  }
  
  public static byte[] decode(byte[] paramArrayOfByte, int paramInt)
  {
    return decode(paramArrayOfByte, 0, paramArrayOfByte.length, paramInt);
  }
  
  public static byte[] decode(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3)
  {
    b localb = new b(paramInt3, new byte[paramInt2 * 3 / 4]);
    if (!localb.a(paramArrayOfByte, paramInt1, paramInt2, true)) {
      throw new IllegalArgumentException("bad base-64");
    }
    if (localb.b == localb.a.length) {
      return localb.a;
    }
    byte[] arrayOfByte = new byte[localb.b];
    System.arraycopy(localb.a, 0, arrayOfByte, 0, localb.b);
    return arrayOfByte;
  }
  
  public static byte[] encode(byte[] paramArrayOfByte, int paramInt)
  {
    return encode(paramArrayOfByte, 0, paramArrayOfByte.length, paramInt);
  }
  
  public static byte[] encode(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3)
  {
    c localc = new c(paramInt3, null);
    int i = paramInt2 / 3 << 2;
    int j;
    if (localc.c)
    {
      if (paramInt2 % 3 > 0) {
        i += 4;
      }
      if ((localc.d) && (paramInt2 > 0))
      {
        j = 1 + (paramInt2 - 1) / 57;
        if (!localc.e) {
          break label167;
        }
      }
    }
    label167:
    for (int k = 2;; k = 1)
    {
      i += k * j;
      localc.a = new byte[i];
      localc.a(paramArrayOfByte, paramInt1, paramInt2, true);
      if ((a) || (localc.b == i)) {
        break label173;
      }
      throw new AssertionError();
      switch (paramInt2 % 3)
      {
      case 0: 
      default: 
        break;
      case 1: 
        i += 2;
        break;
      case 2: 
        i += 3;
        break;
      }
    }
    label173:
    return localc.a;
  }
  
  public static String encodeToString(byte[] paramArrayOfByte, int paramInt)
  {
    try
    {
      String str = new String(encode(paramArrayOfByte, paramInt), "US-ASCII");
      return str;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      throw new AssertionError(localUnsupportedEncodingException);
    }
  }
  
  public static String encodeToString(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3)
  {
    try
    {
      String str = new String(encode(paramArrayOfByte, paramInt1, paramInt2, paramInt3), "US-ASCII");
      return str;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      throw new AssertionError(localUnsupportedEncodingException);
    }
  }
  
  static abstract class a
  {
    public byte[] a;
    public int b;
  }
  
  static final class b
    extends Base64.a
  {
    private static final int[] c = { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -2, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 };
    private static final int[] d = { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -2, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, 63, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 };
    private int e;
    private int f;
    private final int[] g;
    
    public b(int paramInt, byte[] paramArrayOfByte)
    {
      this.a = paramArrayOfByte;
      if ((paramInt & 0x8) == 0) {}
      for (int[] arrayOfInt = c;; arrayOfInt = d)
      {
        this.g = arrayOfInt;
        this.e = 0;
        this.f = 0;
        return;
      }
    }
    
    public final boolean a(byte[] paramArrayOfByte, int paramInt1, int paramInt2, boolean paramBoolean)
    {
      if (this.e == 6) {
        return false;
      }
      int i = paramInt2 + paramInt1;
      int j = this.e;
      int k = this.f;
      int m = 0;
      byte[] arrayOfByte = this.a;
      int[] arrayOfInt = this.g;
      int n = j;
      int i1 = paramInt1;
      if (i1 < i) {
        if (n == 0)
        {
          while (i1 + 4 <= i)
          {
            k = arrayOfInt[(0xFF & paramArrayOfByte[i1])] << 18 | arrayOfInt[(0xFF & paramArrayOfByte[(i1 + 1)])] << 12 | arrayOfInt[(0xFF & paramArrayOfByte[(i1 + 2)])] << 6 | arrayOfInt[(0xFF & paramArrayOfByte[(i1 + 3)])];
            if (k < 0) {
              break;
            }
            arrayOfByte[(m + 2)] = ((byte)k);
            arrayOfByte[(m + 1)] = ((byte)(k >> 8));
            arrayOfByte[m] = ((byte)(k >> 16));
            m += 3;
            i1 += 4;
          }
          if (i1 >= i) {}
        }
        else
        {
          int i5 = i1 + 1;
          int i6 = arrayOfInt[(0xFF & paramArrayOfByte[i1])];
          switch (n)
          {
          }
          do
          {
            do
            {
              do
              {
                do
                {
                  do
                  {
                    do
                    {
                      i1 = i5;
                      break;
                      if (i6 >= 0)
                      {
                        n++;
                        k = i6;
                        i1 = i5;
                        break;
                      }
                    } while (i6 == -1);
                    this.e = 6;
                    return false;
                    if (i6 >= 0)
                    {
                      k = i6 | k << 6;
                      n++;
                      i1 = i5;
                      break;
                    }
                  } while (i6 == -1);
                  this.e = 6;
                  return false;
                  if (i6 >= 0)
                  {
                    k = i6 | k << 6;
                    n++;
                    i1 = i5;
                    break;
                  }
                  if (i6 == -2)
                  {
                    int i7 = m + 1;
                    arrayOfByte[m] = ((byte)(k >> 4));
                    n = 4;
                    m = i7;
                    i1 = i5;
                    break;
                  }
                } while (i6 == -1);
                this.e = 6;
                return false;
                if (i6 >= 0)
                {
                  k = i6 | k << 6;
                  arrayOfByte[(m + 2)] = ((byte)k);
                  arrayOfByte[(m + 1)] = ((byte)(k >> 8));
                  arrayOfByte[m] = ((byte)(k >> 16));
                  m += 3;
                  i1 = i5;
                  n = 0;
                  break;
                }
                if (i6 == -2)
                {
                  arrayOfByte[(m + 1)] = ((byte)(k >> 2));
                  arrayOfByte[m] = ((byte)(k >> 10));
                  m += 2;
                  n = 5;
                  i1 = i5;
                  break;
                }
              } while (i6 == -1);
              this.e = 6;
              return false;
              if (i6 == -2)
              {
                n++;
                i1 = i5;
                break;
              }
            } while (i6 == -1);
            this.e = 6;
            return false;
          } while (i6 == -1);
          this.e = 6;
          return false;
        }
      }
      int i2 = k;
      switch (n)
      {
      case 0: 
      default: 
      case 1: 
      case 2: 
      case 3: 
        for (;;)
        {
          this.e = n;
          this.b = m;
          return true;
          this.e = 6;
          return false;
          int i4 = m + 1;
          arrayOfByte[m] = ((byte)(i2 >> 4));
          m = i4;
          continue;
          int i3 = m + 1;
          arrayOfByte[m] = ((byte)(i2 >> 10));
          m = i3 + 1;
          arrayOfByte[i3] = ((byte)(i2 >> 2));
        }
      }
      this.e = 6;
      return false;
    }
  }
  
  static final class c
    extends Base64.a
  {
    private static final byte[] f;
    private static final byte[] g;
    public final boolean c;
    public final boolean d;
    public final boolean e;
    private final byte[] h;
    private int i;
    private int j;
    private final byte[] k;
    
    static
    {
      if (!Base64.class.desiredAssertionStatus()) {}
      for (boolean bool = true;; bool = false)
      {
        l = bool;
        f = new byte[] { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47 };
        g = new byte[] { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 45, 95 };
        return;
      }
    }
    
    public c(int paramInt, byte[] paramArrayOfByte)
    {
      this.a = null;
      boolean bool2;
      boolean bool3;
      label35:
      label47:
      byte[] arrayOfByte;
      if ((paramInt & 0x1) == 0)
      {
        bool2 = bool1;
        this.c = bool2;
        if ((paramInt & 0x2) != 0) {
          break label106;
        }
        bool3 = bool1;
        this.d = bool3;
        if ((paramInt & 0x4) == 0) {
          break label112;
        }
        this.e = bool1;
        if ((paramInt & 0x8) != 0) {
          break label117;
        }
        arrayOfByte = f;
        label64:
        this.k = arrayOfByte;
        this.h = new byte[2];
        this.i = 0;
        if (!this.d) {
          break label125;
        }
      }
      label106:
      label112:
      label117:
      label125:
      for (int m = 19;; m = -1)
      {
        this.j = m;
        return;
        bool2 = false;
        break;
        bool3 = false;
        break label35;
        bool1 = false;
        break label47;
        arrayOfByte = g;
        break label64;
      }
    }
    
    public final boolean a(byte[] paramArrayOfByte, int paramInt1, int paramInt2, boolean paramBoolean)
    {
      byte[] arrayOfByte1 = this.k;
      byte[] arrayOfByte2 = this.a;
      int m = this.j;
      int n = paramInt2 + paramInt1;
      int i4;
      int i2;
      label58:
      int i36;
      int i37;
      int i6;
      int i5;
      switch (this.i)
      {
      default: 
        i4 = -1;
        i2 = paramInt1;
        if (i4 != -1)
        {
          arrayOfByte2[0] = arrayOfByte1[(0x3F & i4 >> 18)];
          arrayOfByte2[1] = arrayOfByte1[(0x3F & i4 >> 12)];
          arrayOfByte2[2] = arrayOfByte1[(0x3F & i4 >> 6)];
          i36 = 4;
          arrayOfByte2[3] = arrayOfByte1[(i4 & 0x3F)];
          i37 = m - 1;
          if (i37 == 0)
          {
            if (this.e)
            {
              i36 = 5;
              arrayOfByte2[4] = 13;
            }
            i6 = i36 + 1;
            arrayOfByte2[i36] = 10;
            i5 = 19;
          }
        }
        break;
      }
      for (;;)
      {
        label168:
        int i32;
        int i33;
        int i34;
        int i35;
        if (i2 + 3 <= n)
        {
          int i31 = (0xFF & paramArrayOfByte[i2]) << 16 | (0xFF & paramArrayOfByte[(i2 + 1)]) << 8 | 0xFF & paramArrayOfByte[(i2 + 2)];
          arrayOfByte2[i6] = arrayOfByte1[(0x3F & i31 >> 18)];
          arrayOfByte2[(i6 + 1)] = arrayOfByte1[(0x3F & i31 >> 12)];
          arrayOfByte2[(i6 + 2)] = arrayOfByte1[(0x3F & i31 >> 6)];
          arrayOfByte2[(i6 + 3)] = arrayOfByte1[(i31 & 0x3F)];
          i32 = i2 + 3;
          i33 = i6 + 4;
          i34 = i5 - 1;
          if (i34 != 0) {
            break label1120;
          }
          if (!this.e) {
            break label1113;
          }
          i35 = i33 + 1;
          arrayOfByte2[i33] = 13;
        }
        for (;;)
        {
          i6 = i35 + 1;
          arrayOfByte2[i35] = 10;
          i2 = i32;
          i5 = 19;
          break label168;
          i4 = -1;
          i2 = paramInt1;
          break label58;
          if (paramInt1 + 2 > n) {
            break;
          }
          int i38 = (0xFF & this.h[0]) << 16;
          int i39 = paramInt1 + 1;
          int i40 = i38 | (0xFF & paramArrayOfByte[paramInt1]) << 8;
          int i41 = i39 + 1;
          int i42 = i40 | 0xFF & paramArrayOfByte[i39];
          this.i = 0;
          i4 = i42;
          i2 = i41;
          break label58;
          if (paramInt1 + 1 > n) {
            break;
          }
          int i1 = (0xFF & this.h[0]) << 16 | (0xFF & this.h[1]) << 8;
          i2 = paramInt1 + 1;
          int i3 = i1 | 0xFF & paramArrayOfByte[paramInt1];
          this.i = 0;
          i4 = i3;
          break label58;
          if (i2 - this.i == n - 1)
          {
            int i24;
            int i23;
            if (this.i > 0)
            {
              byte[] arrayOfByte5 = this.h;
              i24 = 1;
              i23 = arrayOfByte5[0];
            }
            for (;;)
            {
              int i25 = (i23 & 0xFF) << 4;
              this.i -= i24;
              int i26 = i6 + 1;
              arrayOfByte2[i6] = arrayOfByte1[(0x3F & i25 >> 6)];
              int i27 = i26 + 1;
              arrayOfByte2[i26] = arrayOfByte1[(i25 & 0x3F)];
              if (this.c)
              {
                int i30 = i27 + 1;
                arrayOfByte2[i27] = 61;
                i27 = i30 + 1;
                arrayOfByte2[i30] = 61;
              }
              if (this.d)
              {
                if (this.e)
                {
                  int i29 = i27 + 1;
                  arrayOfByte2[i27] = 13;
                  i27 = i29;
                }
                int i28 = i27 + 1;
                arrayOfByte2[i27] = 10;
                i27 = i28;
              }
              i6 = i27;
              if ((l) || (this.i == 0)) {
                break;
              }
              throw new AssertionError();
              int i22 = i2 + 1;
              i23 = paramArrayOfByte[i2];
              i2 = i22;
              i24 = 0;
            }
          }
          int i10;
          int i9;
          label765:
          int i13;
          label806:
          int i17;
          int i18;
          if (i2 - this.i == n - 2) {
            if (this.i > 1)
            {
              byte[] arrayOfByte4 = this.h;
              i10 = 1;
              i9 = arrayOfByte4[0];
              int i11 = (i9 & 0xFF) << 10;
              if (this.i <= 0) {
                break label990;
              }
              byte[] arrayOfByte3 = this.h;
              int i21 = i10 + 1;
              i13 = arrayOfByte3[i10];
              i10 = i21;
              int i14 = i11 | (i13 & 0xFF) << 2;
              this.i -= i10;
              int i15 = i6 + 1;
              arrayOfByte2[i6] = arrayOfByte1[(0x3F & i14 >> 12)];
              int i16 = i15 + 1;
              arrayOfByte2[i15] = arrayOfByte1[(0x3F & i14 >> 6)];
              i17 = i16 + 1;
              arrayOfByte2[i16] = arrayOfByte1[(i14 & 0x3F)];
              if (!this.c) {
                break label1106;
              }
              i18 = i17 + 1;
              arrayOfByte2[i17] = 61;
            }
          }
          for (;;)
          {
            if (this.d)
            {
              if (this.e)
              {
                int i20 = i18 + 1;
                arrayOfByte2[i18] = 13;
                i18 = i20;
              }
              int i19 = i18 + 1;
              arrayOfByte2[i18] = 10;
              i18 = i19;
            }
            i6 = i18;
            break;
            int i8 = i2 + 1;
            i9 = paramArrayOfByte[i2];
            i2 = i8;
            i10 = 0;
            break label765;
            label990:
            int i12 = i2 + 1;
            i13 = paramArrayOfByte[i2];
            i2 = i12;
            break label806;
            if ((!this.d) || (i6 <= 0) || (i5 == 19)) {
              break;
            }
            int i7;
            if (this.e)
            {
              i7 = i6 + 1;
              arrayOfByte2[i6] = 13;
            }
            for (;;)
            {
              i6 = i7 + 1;
              arrayOfByte2[i7] = 10;
              break;
              if ((!l) && (i2 != n)) {
                throw new AssertionError();
              }
              this.b = i6;
              this.j = i5;
              return true;
              i7 = i6;
            }
            label1106:
            i18 = i17;
          }
          label1113:
          i35 = i33;
        }
        label1120:
        i5 = i34;
        i6 = i33;
        i2 = i32;
        continue;
        i5 = i37;
        i6 = i36;
        continue;
        i5 = m;
        i6 = 0;
      }
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.runtimeconfigurator.Base64
 * JD-Core Version:    0.7.0.1
 */