package org.spongycastle.crypto.engines;

import java.util.Hashtable;
import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithSBox;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Strings;

public class GOST28147Engine
  implements BlockCipher
{
  protected static final int BLOCK_SIZE = 8;
  private static byte[] DSbox_A;
  private static byte[] DSbox_Test;
  private static byte[] ESbox_A;
  private static byte[] ESbox_B;
  private static byte[] ESbox_C;
  private static byte[] ESbox_D;
  private static byte[] ESbox_Test;
  private static byte[] Sbox_Default = { 4, 10, 9, 2, 13, 8, 0, 14, 6, 11, 1, 12, 7, 15, 5, 3, 14, 11, 4, 12, 6, 13, 15, 10, 2, 3, 8, 1, 0, 7, 5, 9, 5, 8, 1, 13, 10, 3, 4, 2, 14, 15, 12, 7, 6, 0, 9, 11, 7, 13, 10, 1, 0, 8, 9, 15, 14, 4, 6, 12, 11, 2, 5, 3, 6, 12, 7, 1, 5, 15, 13, 8, 4, 10, 9, 14, 0, 3, 11, 2, 4, 11, 10, 0, 7, 2, 1, 13, 3, 6, 8, 5, 9, 12, 15, 14, 13, 11, 4, 1, 3, 15, 5, 9, 0, 10, 14, 7, 6, 8, 2, 12, 1, 15, 13, 0, 5, 7, 10, 4, 9, 2, 3, 14, 6, 11, 8, 12 };
  private static Hashtable sBoxes;
  private byte[] S = Sbox_Default;
  private boolean forEncryption;
  private int[] workingKey = null;
  
  static
  {
    ESbox_Test = new byte[] { 4, 2, 15, 5, 9, 1, 0, 8, 14, 3, 11, 12, 13, 7, 10, 6, 12, 9, 15, 14, 8, 1, 3, 10, 2, 7, 4, 13, 6, 0, 11, 5, 13, 8, 14, 12, 7, 3, 9, 10, 1, 5, 2, 4, 6, 15, 0, 11, 14, 9, 11, 2, 5, 15, 7, 1, 0, 13, 12, 6, 10, 4, 3, 8, 3, 14, 5, 9, 6, 8, 0, 13, 10, 11, 7, 12, 2, 1, 15, 4, 8, 15, 6, 11, 1, 9, 12, 5, 13, 3, 7, 10, 0, 14, 2, 4, 9, 11, 12, 0, 3, 6, 7, 5, 4, 8, 14, 15, 1, 10, 2, 13, 12, 6, 5, 2, 11, 0, 9, 13, 3, 14, 7, 10, 15, 4, 1, 8 };
    ESbox_A = new byte[] { 9, 6, 3, 2, 8, 11, 1, 7, 10, 4, 14, 15, 12, 0, 13, 5, 3, 7, 14, 9, 8, 10, 15, 0, 5, 2, 6, 12, 11, 4, 13, 1, 14, 4, 6, 2, 11, 3, 13, 8, 12, 15, 5, 10, 0, 7, 1, 9, 14, 7, 10, 12, 13, 1, 3, 9, 0, 2, 11, 4, 15, 8, 5, 6, 11, 5, 1, 9, 8, 13, 15, 0, 14, 4, 2, 3, 12, 7, 10, 6, 3, 10, 13, 12, 1, 2, 0, 11, 7, 5, 9, 4, 8, 15, 14, 6, 1, 13, 2, 9, 7, 10, 6, 0, 8, 12, 4, 5, 15, 3, 11, 14, 11, 10, 15, 5, 0, 12, 14, 8, 6, 2, 3, 9, 1, 7, 13, 4 };
    ESbox_B = new byte[] { 8, 4, 11, 1, 3, 5, 0, 9, 2, 14, 10, 12, 13, 6, 7, 15, 0, 1, 2, 10, 4, 13, 5, 12, 9, 7, 3, 15, 11, 8, 6, 14, 14, 12, 0, 10, 9, 2, 13, 11, 7, 5, 8, 15, 3, 6, 1, 4, 7, 5, 0, 13, 11, 6, 1, 2, 3, 10, 12, 15, 4, 14, 9, 8, 2, 7, 12, 15, 9, 5, 10, 11, 1, 4, 0, 13, 6, 8, 14, 3, 8, 3, 2, 6, 4, 13, 14, 11, 12, 1, 7, 15, 10, 0, 9, 5, 5, 2, 10, 11, 9, 1, 12, 3, 7, 4, 13, 0, 6, 15, 8, 14, 0, 4, 11, 14, 8, 3, 7, 1, 10, 2, 9, 6, 15, 13, 5, 12 };
    ESbox_C = new byte[] { 1, 11, 12, 2, 9, 13, 0, 15, 4, 5, 8, 14, 10, 7, 6, 3, 0, 1, 7, 13, 11, 4, 5, 2, 8, 14, 15, 12, 9, 10, 6, 3, 8, 2, 5, 0, 4, 9, 15, 10, 3, 7, 12, 13, 6, 14, 1, 11, 3, 6, 0, 1, 5, 13, 10, 8, 11, 2, 9, 7, 14, 15, 12, 4, 8, 13, 11, 0, 4, 5, 1, 2, 9, 3, 12, 14, 6, 15, 10, 7, 12, 9, 11, 1, 8, 14, 2, 4, 7, 3, 6, 5, 10, 0, 15, 13, 10, 9, 6, 8, 13, 14, 2, 0, 15, 3, 5, 11, 4, 1, 12, 7, 7, 4, 0, 5, 10, 2, 15, 14, 12, 6, 1, 11, 13, 9, 3, 8 };
    ESbox_D = new byte[] { 15, 12, 2, 10, 6, 4, 5, 0, 7, 9, 14, 13, 1, 11, 8, 3, 11, 6, 3, 4, 12, 15, 14, 2, 7, 13, 8, 0, 5, 10, 9, 1, 1, 12, 11, 0, 15, 14, 6, 5, 10, 13, 4, 8, 9, 3, 7, 2, 1, 5, 14, 12, 10, 7, 0, 13, 6, 2, 11, 4, 9, 3, 15, 8, 0, 12, 8, 9, 13, 2, 10, 11, 7, 3, 6, 5, 4, 14, 15, 1, 8, 0, 15, 3, 2, 5, 14, 11, 1, 10, 4, 7, 12, 9, 13, 6, 3, 0, 6, 15, 1, 14, 9, 2, 13, 8, 12, 4, 11, 10, 5, 7, 1, 10, 6, 8, 15, 11, 0, 4, 12, 3, 5, 9, 7, 13, 2, 14 };
    DSbox_Test = new byte[] { 4, 10, 9, 2, 13, 8, 0, 14, 6, 11, 1, 12, 7, 15, 5, 3, 14, 11, 4, 12, 6, 13, 15, 10, 2, 3, 8, 1, 0, 7, 5, 9, 5, 8, 1, 13, 10, 3, 4, 2, 14, 15, 12, 7, 6, 0, 9, 11, 7, 13, 10, 1, 0, 8, 9, 15, 14, 4, 6, 12, 11, 2, 5, 3, 6, 12, 7, 1, 5, 15, 13, 8, 4, 10, 9, 14, 0, 3, 11, 2, 4, 11, 10, 0, 7, 2, 1, 13, 3, 6, 8, 5, 9, 12, 15, 14, 13, 11, 4, 1, 3, 15, 5, 9, 0, 10, 14, 7, 6, 8, 2, 12, 1, 15, 13, 0, 5, 7, 10, 4, 9, 2, 3, 14, 6, 11, 8, 12 };
    DSbox_A = new byte[] { 10, 4, 5, 6, 8, 1, 3, 7, 13, 12, 14, 0, 9, 2, 11, 15, 5, 15, 4, 0, 2, 13, 11, 9, 1, 7, 6, 3, 12, 14, 10, 8, 7, 15, 12, 14, 9, 4, 1, 0, 3, 11, 5, 2, 6, 10, 8, 13, 4, 10, 7, 12, 0, 15, 2, 8, 14, 1, 6, 5, 13, 11, 9, 3, 7, 6, 4, 11, 9, 12, 2, 10, 1, 8, 0, 14, 15, 13, 3, 5, 7, 6, 2, 4, 13, 9, 15, 0, 10, 1, 5, 11, 8, 14, 12, 3, 13, 14, 4, 1, 7, 0, 5, 10, 3, 12, 8, 15, 6, 2, 9, 11, 1, 3, 10, 9, 5, 11, 4, 15, 8, 6, 7, 14, 13, 0, 2, 12 };
    sBoxes = new Hashtable();
    addSBox("Default", Sbox_Default);
    addSBox("E-TEST", ESbox_Test);
    addSBox("E-A", ESbox_A);
    addSBox("E-B", ESbox_B);
    addSBox("E-C", ESbox_C);
    addSBox("E-D", ESbox_D);
    addSBox("D-TEST", DSbox_Test);
    addSBox("D-A", DSbox_A);
  }
  
  private void GOST28147Func(int[] paramArrayOfInt, byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, int paramInt2)
  {
    int i = bytesToint(paramArrayOfByte1, paramInt1);
    int j = bytesToint(paramArrayOfByte1, paramInt1 + 4);
    if (this.forEncryption)
    {
      for (int i4 = 0; i4 < 3; i4++) {
        for (int i7 = 0; i7 < 8; i7++)
        {
          int i8 = i;
          i = j ^ GOST28147_mainStep(i, paramArrayOfInt[i7]);
          j = i8;
        }
      }
      for (int i5 = 7; i5 > 0; i5--)
      {
        int i6 = i;
        i = j ^ GOST28147_mainStep(i, paramArrayOfInt[i5]);
        j = i6;
      }
    }
    for (int k = 0; k < 8; k++)
    {
      int i3 = i;
      i = j ^ GOST28147_mainStep(i, paramArrayOfInt[k]);
      j = i3;
    }
    for (int m = 0; m < 3; m++) {
      for (int i1 = 7; (i1 >= 0) && ((m != 2) || (i1 != 0)); i1--)
      {
        int i2 = i;
        i = j ^ GOST28147_mainStep(i, paramArrayOfInt[i1]);
        j = i2;
      }
    }
    int n = j ^ GOST28147_mainStep(i, paramArrayOfInt[0]);
    intTobytes(i, paramArrayOfByte2, paramInt2);
    intTobytes(n, paramArrayOfByte2, paramInt2 + 4);
  }
  
  private int GOST28147_mainStep(int paramInt1, int paramInt2)
  {
    int i = paramInt2 + paramInt1;
    int j = (this.S[(0 + (0xF & i >> 0))] << 0) + (this.S[(16 + (0xF & i >> 4))] << 4) + (this.S[(32 + (0xF & i >> 8))] << 8) + (this.S[(48 + (0xF & i >> 12))] << 12) + (this.S[(64 + (0xF & i >> 16))] << 16) + (this.S[(80 + (0xF & i >> 20))] << 20) + (this.S[(96 + (0xF & i >> 24))] << 24) + (this.S[(112 + (0xF & i >> 28))] << 28);
    return j << 11 | j >>> 21;
  }
  
  private static void addSBox(String paramString, byte[] paramArrayOfByte)
  {
    sBoxes.put(Strings.toUpperCase(paramString), paramArrayOfByte);
  }
  
  private int bytesToint(byte[] paramArrayOfByte, int paramInt)
  {
    return (0xFF000000 & paramArrayOfByte[(paramInt + 3)] << 24) + (0xFF0000 & paramArrayOfByte[(paramInt + 2)] << 16) + (0xFF00 & paramArrayOfByte[(paramInt + 1)] << 8) + (0xFF & paramArrayOfByte[paramInt]);
  }
  
  private int[] generateWorkingKey(boolean paramBoolean, byte[] paramArrayOfByte)
  {
    this.forEncryption = paramBoolean;
    if (paramArrayOfByte.length != 32) {
      throw new IllegalArgumentException("Key length invalid. Key needs to be 32 byte - 256 bit!!!");
    }
    int[] arrayOfInt = new int[8];
    for (int i = 0; i != 8; i++) {
      arrayOfInt[i] = bytesToint(paramArrayOfByte, i * 4);
    }
    return arrayOfInt;
  }
  
  public static byte[] getSBox(String paramString)
  {
    byte[] arrayOfByte = (byte[])sBoxes.get(Strings.toUpperCase(paramString));
    if (arrayOfByte == null) {
      throw new IllegalArgumentException("Unknown S-Box - possible types: \"Default\", \"E-Test\", \"E-A\", \"E-B\", \"E-C\", \"E-D\", \"D-Test\", \"D-A\".");
    }
    return Arrays.clone(arrayOfByte);
  }
  
  private void intTobytes(int paramInt1, byte[] paramArrayOfByte, int paramInt2)
  {
    paramArrayOfByte[(paramInt2 + 3)] = ((byte)(paramInt1 >>> 24));
    paramArrayOfByte[(paramInt2 + 2)] = ((byte)(paramInt1 >>> 16));
    paramArrayOfByte[(paramInt2 + 1)] = ((byte)(paramInt1 >>> 8));
    paramArrayOfByte[paramInt2] = ((byte)paramInt1);
  }
  
  public String getAlgorithmName()
  {
    return "GOST28147";
  }
  
  public int getBlockSize()
  {
    return 8;
  }
  
  public void init(boolean paramBoolean, CipherParameters paramCipherParameters)
  {
    if ((paramCipherParameters instanceof ParametersWithSBox))
    {
      ParametersWithSBox localParametersWithSBox = (ParametersWithSBox)paramCipherParameters;
      byte[] arrayOfByte = localParametersWithSBox.getSBox();
      if (arrayOfByte.length != Sbox_Default.length) {
        throw new IllegalArgumentException("invalid S-box passed to GOST28147 init");
      }
      this.S = Arrays.clone(arrayOfByte);
      if (localParametersWithSBox.getParameters() != null) {
        this.workingKey = generateWorkingKey(paramBoolean, ((KeyParameter)localParametersWithSBox.getParameters()).getKey());
      }
    }
    do
    {
      return;
      if ((paramCipherParameters instanceof KeyParameter))
      {
        this.workingKey = generateWorkingKey(paramBoolean, ((KeyParameter)paramCipherParameters).getKey());
        return;
      }
    } while (paramCipherParameters == null);
    throw new IllegalArgumentException("invalid parameter passed to GOST28147 init - " + paramCipherParameters.getClass().getName());
  }
  
  public int processBlock(byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, int paramInt2)
  {
    if (this.workingKey == null) {
      throw new IllegalStateException("GOST28147 engine not initialised");
    }
    if (paramInt1 + 8 > paramArrayOfByte1.length) {
      throw new DataLengthException("input buffer too short");
    }
    if (paramInt2 + 8 > paramArrayOfByte2.length) {
      throw new DataLengthException("output buffer too short");
    }
    GOST28147Func(this.workingKey, paramArrayOfByte1, paramInt1, paramArrayOfByte2, paramInt2);
    return 8;
  }
  
  public void reset() {}
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.engines.GOST28147Engine
 * JD-Core Version:    0.7.0.1
 */