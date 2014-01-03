package org.spongycastle.crypto.engines;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.params.KeyParameter;

public class CamelliaLightEngine
  implements BlockCipher
{
  private static final int BLOCK_SIZE = 16;
  private static final int MASK8 = 255;
  private static final byte[] SBOX1 = { 112, -126, 44, -20, -77, 39, -64, -27, -28, -123, 87, 53, -22, 12, -82, 65, 35, -17, 107, -109, 69, 25, -91, 33, -19, 14, 79, 78, 29, 101, -110, -67, -122, -72, -81, -113, 124, -21, 31, -50, 62, 48, -36, 95, 94, -59, 11, 26, -90, -31, 57, -54, -43, 71, 93, 61, -39, 1, 90, -42, 81, 86, 108, 77, -117, 13, -102, 102, -5, -52, -80, 45, 116, 18, 43, 32, -16, -79, -124, -103, -33, 76, -53, -62, 52, 126, 118, 5, 109, -73, -87, 49, -47, 23, 4, -41, 20, 88, 58, 97, -34, 27, 17, 28, 50, 15, -100, 22, 83, 24, -14, 34, -2, 68, -49, -78, -61, -75, 122, -111, 36, 8, -24, -88, 96, -4, 105, 80, -86, -48, -96, 125, -95, -119, 98, -105, 84, 91, 30, -107, -32, -1, 100, -46, 16, -60, 0, 72, -93, -9, 117, -37, -118, 3, -26, -38, 9, 63, -35, -108, -121, 92, -125, 2, -51, 74, -112, 51, 115, 103, -10, -13, -99, 127, -65, -30, 82, -101, -40, 38, -56, 55, -58, 59, -127, -106, 111, 75, 19, -66, 99, 46, -23, 121, -89, -116, -97, 110, -68, -114, 41, -11, -7, -74, 47, -3, -76, 89, 120, -104, 6, 106, -25, 70, 113, -70, -44, 37, -85, 66, -120, -94, -115, -6, 114, 7, -71, 85, -8, -18, -84, 10, 54, 73, 42, 104, 60, 56, -15, -92, 64, 40, -45, 123, -69, -55, 67, -63, 21, -29, -83, -12, 119, -57, -128, -98 };
  private static final int[] SIGMA = { -1600231809, 1003262091, -1233459112, 1286239154, -957401297, -380665154, 1426019237, -237801700, 283453434, -563598051, -1336506174, -1276722691 };
  private boolean _keyis128;
  private boolean initialized;
  private int[] ke = new int[12];
  private int[] kw = new int[8];
  private int[] state = new int[4];
  private int[] subkey = new int[96];
  
  private int bytes2int(byte[] paramArrayOfByte, int paramInt)
  {
    int i = 0;
    for (int j = 0; j < 4; j++) {
      i = (i << 8) + (0xFF & paramArrayOfByte[(j + paramInt)]);
    }
    return i;
  }
  
  private void camelliaF2(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt)
  {
    int i = paramArrayOfInt1[0] ^ paramArrayOfInt2[(paramInt + 0)];
    int j = sbox4(i & 0xFF) | sbox3(0xFF & i >>> 8) << 8 | sbox2(0xFF & i >>> 16) << 16 | (0xFF & SBOX1[(0xFF & i >>> 24)]) << 24;
    int k = paramArrayOfInt1[1] ^ paramArrayOfInt2[(paramInt + 1)];
    int m = leftRotate(0xFF & SBOX1[(k & 0xFF)] | sbox4(0xFF & k >>> 8) << 8 | sbox3(0xFF & k >>> 16) << 16 | sbox2(0xFF & k >>> 24) << 24, 8);
    int n = j ^ m;
    int i1 = n ^ leftRotate(m, 8);
    int i2 = i1 ^ rightRotate(n, 8);
    paramArrayOfInt1[2] ^= i2 ^ leftRotate(i1, 16);
    paramArrayOfInt1[3] ^= leftRotate(i2, 8);
    int i3 = paramArrayOfInt1[2] ^ paramArrayOfInt2[(paramInt + 2)];
    int i4 = sbox4(i3 & 0xFF) | sbox3(0xFF & i3 >>> 8) << 8 | sbox2(0xFF & i3 >>> 16) << 16 | (0xFF & SBOX1[(0xFF & i3 >>> 24)]) << 24;
    int i5 = paramArrayOfInt1[3] ^ paramArrayOfInt2[(paramInt + 3)];
    int i6 = leftRotate(0xFF & SBOX1[(i5 & 0xFF)] | sbox4(0xFF & i5 >>> 8) << 8 | sbox3(0xFF & i5 >>> 16) << 16 | sbox2(0xFF & i5 >>> 24) << 24, 8);
    int i7 = i4 ^ i6;
    int i8 = i7 ^ leftRotate(i6, 8);
    int i9 = i8 ^ rightRotate(i7, 8);
    paramArrayOfInt1[0] ^= i9 ^ leftRotate(i8, 16);
    paramArrayOfInt1[1] ^= leftRotate(i9, 8);
  }
  
  private void camelliaFLs(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt)
  {
    paramArrayOfInt1[1] ^= leftRotate(paramArrayOfInt1[0] & paramArrayOfInt2[(paramInt + 0)], 1);
    paramArrayOfInt1[0] ^= (paramArrayOfInt2[(paramInt + 1)] | paramArrayOfInt1[1]);
    paramArrayOfInt1[2] ^= (paramArrayOfInt2[(paramInt + 3)] | paramArrayOfInt1[3]);
    paramArrayOfInt1[3] ^= leftRotate(paramArrayOfInt2[(paramInt + 2)] & paramArrayOfInt1[2], 1);
  }
  
  private static void decroldq(int paramInt1, int[] paramArrayOfInt1, int paramInt2, int[] paramArrayOfInt2, int paramInt3)
  {
    paramArrayOfInt2[(paramInt3 + 2)] = (paramArrayOfInt1[(paramInt2 + 0)] << paramInt1 | paramArrayOfInt1[(paramInt2 + 1)] >>> 32 - paramInt1);
    paramArrayOfInt2[(paramInt3 + 3)] = (paramArrayOfInt1[(paramInt2 + 1)] << paramInt1 | paramArrayOfInt1[(paramInt2 + 2)] >>> 32 - paramInt1);
    paramArrayOfInt2[(paramInt3 + 0)] = (paramArrayOfInt1[(paramInt2 + 2)] << paramInt1 | paramArrayOfInt1[(paramInt2 + 3)] >>> 32 - paramInt1);
    paramArrayOfInt2[(paramInt3 + 1)] = (paramArrayOfInt1[(paramInt2 + 3)] << paramInt1 | paramArrayOfInt1[(paramInt2 + 0)] >>> 32 - paramInt1);
    paramArrayOfInt1[(paramInt2 + 0)] = paramArrayOfInt2[(paramInt3 + 2)];
    paramArrayOfInt1[(paramInt2 + 1)] = paramArrayOfInt2[(paramInt3 + 3)];
    paramArrayOfInt1[(paramInt2 + 2)] = paramArrayOfInt2[(paramInt3 + 0)];
    paramArrayOfInt1[(paramInt2 + 3)] = paramArrayOfInt2[(paramInt3 + 1)];
  }
  
  private static void decroldqo32(int paramInt1, int[] paramArrayOfInt1, int paramInt2, int[] paramArrayOfInt2, int paramInt3)
  {
    paramArrayOfInt2[(paramInt3 + 2)] = (paramArrayOfInt1[(paramInt2 + 1)] << paramInt1 - 32 | paramArrayOfInt1[(paramInt2 + 2)] >>> 64 - paramInt1);
    paramArrayOfInt2[(paramInt3 + 3)] = (paramArrayOfInt1[(paramInt2 + 2)] << paramInt1 - 32 | paramArrayOfInt1[(paramInt2 + 3)] >>> 64 - paramInt1);
    paramArrayOfInt2[(paramInt3 + 0)] = (paramArrayOfInt1[(paramInt2 + 3)] << paramInt1 - 32 | paramArrayOfInt1[(paramInt2 + 0)] >>> 64 - paramInt1);
    paramArrayOfInt2[(paramInt3 + 1)] = (paramArrayOfInt1[(paramInt2 + 0)] << paramInt1 - 32 | paramArrayOfInt1[(paramInt2 + 1)] >>> 64 - paramInt1);
    paramArrayOfInt1[(paramInt2 + 0)] = paramArrayOfInt2[(paramInt3 + 2)];
    paramArrayOfInt1[(paramInt2 + 1)] = paramArrayOfInt2[(paramInt3 + 3)];
    paramArrayOfInt1[(paramInt2 + 2)] = paramArrayOfInt2[(paramInt3 + 0)];
    paramArrayOfInt1[(paramInt2 + 3)] = paramArrayOfInt2[(paramInt3 + 1)];
  }
  
  private void int2bytes(int paramInt1, byte[] paramArrayOfByte, int paramInt2)
  {
    for (int i = 0; i < 4; i++)
    {
      paramArrayOfByte[(paramInt2 + (3 - i))] = ((byte)paramInt1);
      paramInt1 >>>= 8;
    }
  }
  
  private byte lRot8(byte paramByte, int paramInt)
  {
    return (byte)(paramByte << paramInt | (paramByte & 0xFF) >>> 8 - paramInt);
  }
  
  private static int leftRotate(int paramInt1, int paramInt2)
  {
    return (paramInt1 << paramInt2) + (paramInt1 >>> 32 - paramInt2);
  }
  
  private int processBlock128(byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, int paramInt2)
  {
    for (int i = 0; i < 4; i++)
    {
      this.state[i] = bytes2int(paramArrayOfByte1, paramInt1 + i * 4);
      int[] arrayOfInt5 = this.state;
      arrayOfInt5[i] ^= this.kw[i];
    }
    camelliaF2(this.state, this.subkey, 0);
    camelliaF2(this.state, this.subkey, 4);
    camelliaF2(this.state, this.subkey, 8);
    camelliaFLs(this.state, this.ke, 0);
    camelliaF2(this.state, this.subkey, 12);
    camelliaF2(this.state, this.subkey, 16);
    camelliaF2(this.state, this.subkey, 20);
    camelliaFLs(this.state, this.ke, 4);
    camelliaF2(this.state, this.subkey, 24);
    camelliaF2(this.state, this.subkey, 28);
    camelliaF2(this.state, this.subkey, 32);
    int[] arrayOfInt1 = this.state;
    arrayOfInt1[2] ^= this.kw[4];
    int[] arrayOfInt2 = this.state;
    arrayOfInt2[3] ^= this.kw[5];
    int[] arrayOfInt3 = this.state;
    arrayOfInt3[0] ^= this.kw[6];
    int[] arrayOfInt4 = this.state;
    arrayOfInt4[1] ^= this.kw[7];
    int2bytes(this.state[2], paramArrayOfByte2, paramInt2);
    int2bytes(this.state[3], paramArrayOfByte2, paramInt2 + 4);
    int2bytes(this.state[0], paramArrayOfByte2, paramInt2 + 8);
    int2bytes(this.state[1], paramArrayOfByte2, paramInt2 + 12);
    return 16;
  }
  
  private int processBlock192or256(byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, int paramInt2)
  {
    for (int i = 0; i < 4; i++)
    {
      this.state[i] = bytes2int(paramArrayOfByte1, paramInt1 + i * 4);
      int[] arrayOfInt5 = this.state;
      arrayOfInt5[i] ^= this.kw[i];
    }
    camelliaF2(this.state, this.subkey, 0);
    camelliaF2(this.state, this.subkey, 4);
    camelliaF2(this.state, this.subkey, 8);
    camelliaFLs(this.state, this.ke, 0);
    camelliaF2(this.state, this.subkey, 12);
    camelliaF2(this.state, this.subkey, 16);
    camelliaF2(this.state, this.subkey, 20);
    camelliaFLs(this.state, this.ke, 4);
    camelliaF2(this.state, this.subkey, 24);
    camelliaF2(this.state, this.subkey, 28);
    camelliaF2(this.state, this.subkey, 32);
    camelliaFLs(this.state, this.ke, 8);
    camelliaF2(this.state, this.subkey, 36);
    camelliaF2(this.state, this.subkey, 40);
    camelliaF2(this.state, this.subkey, 44);
    int[] arrayOfInt1 = this.state;
    arrayOfInt1[2] ^= this.kw[4];
    int[] arrayOfInt2 = this.state;
    arrayOfInt2[3] ^= this.kw[5];
    int[] arrayOfInt3 = this.state;
    arrayOfInt3[0] ^= this.kw[6];
    int[] arrayOfInt4 = this.state;
    arrayOfInt4[1] ^= this.kw[7];
    int2bytes(this.state[2], paramArrayOfByte2, paramInt2);
    int2bytes(this.state[3], paramArrayOfByte2, paramInt2 + 4);
    int2bytes(this.state[0], paramArrayOfByte2, paramInt2 + 8);
    int2bytes(this.state[1], paramArrayOfByte2, paramInt2 + 12);
    return 16;
  }
  
  private static int rightRotate(int paramInt1, int paramInt2)
  {
    return (paramInt1 >>> paramInt2) + (paramInt1 << 32 - paramInt2);
  }
  
  private static void roldq(int paramInt1, int[] paramArrayOfInt1, int paramInt2, int[] paramArrayOfInt2, int paramInt3)
  {
    paramArrayOfInt2[(paramInt3 + 0)] = (paramArrayOfInt1[(paramInt2 + 0)] << paramInt1 | paramArrayOfInt1[(paramInt2 + 1)] >>> 32 - paramInt1);
    paramArrayOfInt2[(paramInt3 + 1)] = (paramArrayOfInt1[(paramInt2 + 1)] << paramInt1 | paramArrayOfInt1[(paramInt2 + 2)] >>> 32 - paramInt1);
    paramArrayOfInt2[(paramInt3 + 2)] = (paramArrayOfInt1[(paramInt2 + 2)] << paramInt1 | paramArrayOfInt1[(paramInt2 + 3)] >>> 32 - paramInt1);
    paramArrayOfInt2[(paramInt3 + 3)] = (paramArrayOfInt1[(paramInt2 + 3)] << paramInt1 | paramArrayOfInt1[(paramInt2 + 0)] >>> 32 - paramInt1);
    paramArrayOfInt1[(paramInt2 + 0)] = paramArrayOfInt2[(paramInt3 + 0)];
    paramArrayOfInt1[(paramInt2 + 1)] = paramArrayOfInt2[(paramInt3 + 1)];
    paramArrayOfInt1[(paramInt2 + 2)] = paramArrayOfInt2[(paramInt3 + 2)];
    paramArrayOfInt1[(paramInt2 + 3)] = paramArrayOfInt2[(paramInt3 + 3)];
  }
  
  private static void roldqo32(int paramInt1, int[] paramArrayOfInt1, int paramInt2, int[] paramArrayOfInt2, int paramInt3)
  {
    paramArrayOfInt2[(paramInt3 + 0)] = (paramArrayOfInt1[(paramInt2 + 1)] << paramInt1 - 32 | paramArrayOfInt1[(paramInt2 + 2)] >>> 64 - paramInt1);
    paramArrayOfInt2[(paramInt3 + 1)] = (paramArrayOfInt1[(paramInt2 + 2)] << paramInt1 - 32 | paramArrayOfInt1[(paramInt2 + 3)] >>> 64 - paramInt1);
    paramArrayOfInt2[(paramInt3 + 2)] = (paramArrayOfInt1[(paramInt2 + 3)] << paramInt1 - 32 | paramArrayOfInt1[(paramInt2 + 0)] >>> 64 - paramInt1);
    paramArrayOfInt2[(paramInt3 + 3)] = (paramArrayOfInt1[(paramInt2 + 0)] << paramInt1 - 32 | paramArrayOfInt1[(paramInt2 + 1)] >>> 64 - paramInt1);
    paramArrayOfInt1[(paramInt2 + 0)] = paramArrayOfInt2[(paramInt3 + 0)];
    paramArrayOfInt1[(paramInt2 + 1)] = paramArrayOfInt2[(paramInt3 + 1)];
    paramArrayOfInt1[(paramInt2 + 2)] = paramArrayOfInt2[(paramInt3 + 2)];
    paramArrayOfInt1[(paramInt2 + 3)] = paramArrayOfInt2[(paramInt3 + 3)];
  }
  
  private int sbox2(int paramInt)
  {
    return 0xFF & lRot8(SBOX1[paramInt], 1);
  }
  
  private int sbox3(int paramInt)
  {
    return 0xFF & lRot8(SBOX1[paramInt], 7);
  }
  
  private int sbox4(int paramInt)
  {
    return 0xFF & SBOX1[(0xFF & lRot8((byte)paramInt, 1))];
  }
  
  private void setKey(boolean paramBoolean, byte[] paramArrayOfByte)
  {
    int[] arrayOfInt1 = new int[8];
    int[] arrayOfInt2 = new int[4];
    int[] arrayOfInt3 = new int[4];
    int[] arrayOfInt4 = new int[4];
    switch (paramArrayOfByte.length)
    {
    default: 
      throw new IllegalArgumentException("key sizes are only 16/24/32 bytes.");
    case 16: 
      this._keyis128 = true;
      arrayOfInt1[0] = bytes2int(paramArrayOfByte, 0);
      arrayOfInt1[1] = bytes2int(paramArrayOfByte, 4);
      arrayOfInt1[2] = bytes2int(paramArrayOfByte, 8);
      arrayOfInt1[3] = bytes2int(paramArrayOfByte, 12);
      arrayOfInt1[7] = 0;
      arrayOfInt1[6] = 0;
      arrayOfInt1[5] = 0;
      arrayOfInt1[4] = 0;
    }
    for (;;)
    {
      for (int i = 0; i < 4; i++) {
        arrayOfInt1[i] ^= arrayOfInt1[(i + 4)];
      }
      arrayOfInt1[0] = bytes2int(paramArrayOfByte, 0);
      arrayOfInt1[1] = bytes2int(paramArrayOfByte, 4);
      arrayOfInt1[2] = bytes2int(paramArrayOfByte, 8);
      arrayOfInt1[3] = bytes2int(paramArrayOfByte, 12);
      arrayOfInt1[4] = bytes2int(paramArrayOfByte, 16);
      arrayOfInt1[5] = bytes2int(paramArrayOfByte, 20);
      arrayOfInt1[6] = (0xFFFFFFFF ^ arrayOfInt1[4]);
      arrayOfInt1[7] = (0xFFFFFFFF ^ arrayOfInt1[5]);
      this._keyis128 = false;
      continue;
      arrayOfInt1[0] = bytes2int(paramArrayOfByte, 0);
      arrayOfInt1[1] = bytes2int(paramArrayOfByte, 4);
      arrayOfInt1[2] = bytes2int(paramArrayOfByte, 8);
      arrayOfInt1[3] = bytes2int(paramArrayOfByte, 12);
      arrayOfInt1[4] = bytes2int(paramArrayOfByte, 16);
      arrayOfInt1[5] = bytes2int(paramArrayOfByte, 20);
      arrayOfInt1[6] = bytes2int(paramArrayOfByte, 24);
      arrayOfInt1[7] = bytes2int(paramArrayOfByte, 28);
      this._keyis128 = false;
    }
    camelliaF2(arrayOfInt2, SIGMA, 0);
    for (int j = 0; j < 4; j++) {
      arrayOfInt2[j] ^= arrayOfInt1[j];
    }
    camelliaF2(arrayOfInt2, SIGMA, 4);
    if (this._keyis128)
    {
      if (paramBoolean)
      {
        this.kw[0] = arrayOfInt1[0];
        this.kw[1] = arrayOfInt1[1];
        this.kw[2] = arrayOfInt1[2];
        this.kw[3] = arrayOfInt1[3];
        roldq(15, arrayOfInt1, 0, this.subkey, 4);
        roldq(30, arrayOfInt1, 0, this.subkey, 12);
        roldq(15, arrayOfInt1, 0, arrayOfInt4, 0);
        this.subkey[18] = arrayOfInt4[2];
        this.subkey[19] = arrayOfInt4[3];
        roldq(17, arrayOfInt1, 0, this.ke, 4);
        roldq(17, arrayOfInt1, 0, this.subkey, 24);
        roldq(17, arrayOfInt1, 0, this.subkey, 32);
        this.subkey[0] = arrayOfInt2[0];
        this.subkey[1] = arrayOfInt2[1];
        this.subkey[2] = arrayOfInt2[2];
        this.subkey[3] = arrayOfInt2[3];
        roldq(15, arrayOfInt2, 0, this.subkey, 8);
        roldq(15, arrayOfInt2, 0, this.ke, 0);
        roldq(15, arrayOfInt2, 0, arrayOfInt4, 0);
        this.subkey[16] = arrayOfInt4[0];
        this.subkey[17] = arrayOfInt4[1];
        roldq(15, arrayOfInt2, 0, this.subkey, 20);
        roldqo32(34, arrayOfInt2, 0, this.subkey, 28);
        roldq(17, arrayOfInt2, 0, this.kw, 4);
        return;
      }
      this.kw[4] = arrayOfInt1[0];
      this.kw[5] = arrayOfInt1[1];
      this.kw[6] = arrayOfInt1[2];
      this.kw[7] = arrayOfInt1[3];
      decroldq(15, arrayOfInt1, 0, this.subkey, 28);
      decroldq(30, arrayOfInt1, 0, this.subkey, 20);
      decroldq(15, arrayOfInt1, 0, arrayOfInt4, 0);
      this.subkey[16] = arrayOfInt4[0];
      this.subkey[17] = arrayOfInt4[1];
      decroldq(17, arrayOfInt1, 0, this.ke, 0);
      decroldq(17, arrayOfInt1, 0, this.subkey, 8);
      decroldq(17, arrayOfInt1, 0, this.subkey, 0);
      this.subkey[34] = arrayOfInt2[0];
      this.subkey[35] = arrayOfInt2[1];
      this.subkey[32] = arrayOfInt2[2];
      this.subkey[33] = arrayOfInt2[3];
      decroldq(15, arrayOfInt2, 0, this.subkey, 24);
      decroldq(15, arrayOfInt2, 0, this.ke, 4);
      decroldq(15, arrayOfInt2, 0, arrayOfInt4, 0);
      this.subkey[18] = arrayOfInt4[2];
      this.subkey[19] = arrayOfInt4[3];
      decroldq(15, arrayOfInt2, 0, this.subkey, 12);
      decroldqo32(34, arrayOfInt2, 0, this.subkey, 4);
      roldq(17, arrayOfInt2, 0, this.kw, 0);
      return;
    }
    for (int k = 0; k < 4; k++) {
      arrayOfInt2[k] ^= arrayOfInt1[(k + 4)];
    }
    camelliaF2(arrayOfInt3, SIGMA, 8);
    if (paramBoolean)
    {
      this.kw[0] = arrayOfInt1[0];
      this.kw[1] = arrayOfInt1[1];
      this.kw[2] = arrayOfInt1[2];
      this.kw[3] = arrayOfInt1[3];
      roldqo32(45, arrayOfInt1, 0, this.subkey, 16);
      roldq(15, arrayOfInt1, 0, this.ke, 4);
      roldq(17, arrayOfInt1, 0, this.subkey, 32);
      roldqo32(34, arrayOfInt1, 0, this.subkey, 44);
      roldq(15, arrayOfInt1, 4, this.subkey, 4);
      roldq(15, arrayOfInt1, 4, this.ke, 0);
      roldq(30, arrayOfInt1, 4, this.subkey, 24);
      roldqo32(34, arrayOfInt1, 4, this.subkey, 36);
      roldq(15, arrayOfInt2, 0, this.subkey, 8);
      roldq(30, arrayOfInt2, 0, this.subkey, 20);
      this.ke[8] = arrayOfInt2[1];
      this.ke[9] = arrayOfInt2[2];
      this.ke[10] = arrayOfInt2[3];
      this.ke[11] = arrayOfInt2[0];
      roldqo32(49, arrayOfInt2, 0, this.subkey, 40);
      this.subkey[0] = arrayOfInt3[0];
      this.subkey[1] = arrayOfInt3[1];
      this.subkey[2] = arrayOfInt3[2];
      this.subkey[3] = arrayOfInt3[3];
      roldq(30, arrayOfInt3, 0, this.subkey, 12);
      roldq(30, arrayOfInt3, 0, this.subkey, 28);
      roldqo32(51, arrayOfInt3, 0, this.kw, 4);
      return;
    }
    this.kw[4] = arrayOfInt1[0];
    this.kw[5] = arrayOfInt1[1];
    this.kw[6] = arrayOfInt1[2];
    this.kw[7] = arrayOfInt1[3];
    decroldqo32(45, arrayOfInt1, 0, this.subkey, 28);
    decroldq(15, arrayOfInt1, 0, this.ke, 4);
    decroldq(17, arrayOfInt1, 0, this.subkey, 12);
    decroldqo32(34, arrayOfInt1, 0, this.subkey, 0);
    decroldq(15, arrayOfInt1, 4, this.subkey, 40);
    decroldq(15, arrayOfInt1, 4, this.ke, 8);
    decroldq(30, arrayOfInt1, 4, this.subkey, 20);
    decroldqo32(34, arrayOfInt1, 4, this.subkey, 8);
    decroldq(15, arrayOfInt2, 0, this.subkey, 36);
    decroldq(30, arrayOfInt2, 0, this.subkey, 24);
    this.ke[2] = arrayOfInt2[1];
    this.ke[3] = arrayOfInt2[2];
    this.ke[0] = arrayOfInt2[3];
    this.ke[1] = arrayOfInt2[0];
    decroldqo32(49, arrayOfInt2, 0, this.subkey, 4);
    this.subkey[46] = arrayOfInt3[0];
    this.subkey[47] = arrayOfInt3[1];
    this.subkey[44] = arrayOfInt3[2];
    this.subkey[45] = arrayOfInt3[3];
    decroldq(30, arrayOfInt3, 0, this.subkey, 32);
    decroldq(30, arrayOfInt3, 0, this.subkey, 16);
    roldqo32(51, arrayOfInt3, 0, this.kw, 0);
  }
  
  public String getAlgorithmName()
  {
    return "Camellia";
  }
  
  public int getBlockSize()
  {
    return 16;
  }
  
  public void init(boolean paramBoolean, CipherParameters paramCipherParameters)
  {
    if (!(paramCipherParameters instanceof KeyParameter)) {
      throw new IllegalArgumentException("only simple KeyParameter expected.");
    }
    setKey(paramBoolean, ((KeyParameter)paramCipherParameters).getKey());
    this.initialized = true;
  }
  
  public int processBlock(byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, int paramInt2)
    throws IllegalStateException
  {
    if (!this.initialized) {
      throw new IllegalStateException("Camellia is not initialized");
    }
    if (paramInt1 + 16 > paramArrayOfByte1.length) {
      throw new DataLengthException("input buffer too short");
    }
    if (paramInt2 + 16 > paramArrayOfByte2.length) {
      throw new DataLengthException("output buffer too short");
    }
    if (this._keyis128) {
      return processBlock128(paramArrayOfByte1, paramInt1, paramArrayOfByte2, paramInt2);
    }
    return processBlock192or256(paramArrayOfByte1, paramInt1, paramArrayOfByte2, paramInt2);
  }
  
  public void reset() {}
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.engines.CamelliaLightEngine
 * JD-Core Version:    0.7.0.1
 */