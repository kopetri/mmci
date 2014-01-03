package org.spongycastle.crypto.params;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.spongycastle.math.ntru.polynomial.DenseTernaryPolynomial;
import org.spongycastle.math.ntru.polynomial.IntegerPolynomial;
import org.spongycastle.math.ntru.polynomial.Polynomial;
import org.spongycastle.math.ntru.polynomial.ProductFormPolynomial;
import org.spongycastle.math.ntru.polynomial.SparseTernaryPolynomial;

public class NTRUSigningPrivateKeyParameters
  extends AsymmetricKeyParameter
{
  private List<Basis> bases;
  private NTRUSigningPublicKeyParameters publicKey;
  
  public NTRUSigningPrivateKeyParameters(InputStream paramInputStream, NTRUSigningKeyGenerationParameters paramNTRUSigningKeyGenerationParameters)
    throws IOException
  {
    super(true);
    this.bases = new ArrayList();
    int i = 0;
    if (i <= paramNTRUSigningKeyGenerationParameters.B)
    {
      if (i != 0) {}
      for (boolean bool = true;; bool = false)
      {
        add(new Basis(paramInputStream, paramNTRUSigningKeyGenerationParameters, bool));
        i++;
        break;
      }
    }
    this.publicKey = new NTRUSigningPublicKeyParameters(paramInputStream, paramNTRUSigningKeyGenerationParameters.getSigningParameters());
  }
  
  public NTRUSigningPrivateKeyParameters(List<Basis> paramList, NTRUSigningPublicKeyParameters paramNTRUSigningPublicKeyParameters)
  {
    super(true);
    this.bases = new ArrayList(paramList);
    this.publicKey = paramNTRUSigningPublicKeyParameters;
  }
  
  public NTRUSigningPrivateKeyParameters(byte[] paramArrayOfByte, NTRUSigningKeyGenerationParameters paramNTRUSigningKeyGenerationParameters)
    throws IOException
  {
    this(new ByteArrayInputStream(paramArrayOfByte), paramNTRUSigningKeyGenerationParameters);
  }
  
  private void add(Basis paramBasis)
  {
    this.bases.add(paramBasis);
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {}
    for (;;)
    {
      return true;
      if (paramObject == null) {
        return false;
      }
      if (getClass() != paramObject.getClass()) {
        return false;
      }
      NTRUSigningPrivateKeyParameters localNTRUSigningPrivateKeyParameters = (NTRUSigningPrivateKeyParameters)paramObject;
      if ((this.bases == null) && (localNTRUSigningPrivateKeyParameters.bases != null)) {
        return false;
      }
      if (this.bases.size() != localNTRUSigningPrivateKeyParameters.bases.size()) {
        return false;
      }
      for (int i = 0; i < this.bases.size(); i++)
      {
        Basis localBasis1 = (Basis)this.bases.get(i);
        Basis localBasis2 = (Basis)localNTRUSigningPrivateKeyParameters.bases.get(i);
        if (!localBasis1.f.equals(localBasis2.f)) {
          return false;
        }
        if (!localBasis1.fPrime.equals(localBasis2.fPrime)) {
          return false;
        }
        if ((i != 0) && (!localBasis1.h.equals(localBasis2.h))) {
          return false;
        }
        if (!localBasis1.params.equals(localBasis2.params)) {
          return false;
        }
      }
    }
  }
  
  public Basis getBasis(int paramInt)
  {
    return (Basis)this.bases.get(paramInt);
  }
  
  public byte[] getEncoded()
    throws IOException
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    int i = 0;
    if (i < this.bases.size())
    {
      Basis localBasis = (Basis)this.bases.get(i);
      if (i != 0) {}
      for (boolean bool = true;; bool = false)
      {
        localBasis.encode(localByteArrayOutputStream, bool);
        i++;
        break;
      }
    }
    localByteArrayOutputStream.write(this.publicKey.getEncoded());
    return localByteArrayOutputStream.toByteArray();
  }
  
  public NTRUSigningPublicKeyParameters getPublicKey()
  {
    return this.publicKey;
  }
  
  public int hashCode()
  {
    if (this.bases == null) {}
    int j;
    for (int i = 0;; i = this.bases.hashCode())
    {
      j = i + 31;
      Iterator localIterator = this.bases.iterator();
      while (localIterator.hasNext()) {
        j += ((Basis)localIterator.next()).hashCode();
      }
    }
    return j;
  }
  
  public void writeTo(OutputStream paramOutputStream)
    throws IOException
  {
    paramOutputStream.write(getEncoded());
  }
  
  public static class Basis
  {
    public Polynomial f;
    public Polynomial fPrime;
    public IntegerPolynomial h;
    NTRUSigningKeyGenerationParameters params;
    
    Basis(InputStream paramInputStream, NTRUSigningKeyGenerationParameters paramNTRUSigningKeyGenerationParameters, boolean paramBoolean)
      throws IOException
    {
      int i = paramNTRUSigningKeyGenerationParameters.N;
      int j = paramNTRUSigningKeyGenerationParameters.q;
      int k = paramNTRUSigningKeyGenerationParameters.d1;
      int m = paramNTRUSigningKeyGenerationParameters.d2;
      int n = paramNTRUSigningKeyGenerationParameters.d3;
      boolean bool = paramNTRUSigningKeyGenerationParameters.sparse;
      this.params = paramNTRUSigningKeyGenerationParameters;
      Object localObject;
      Basis localBasis;
      if (paramNTRUSigningKeyGenerationParameters.polyType == 1)
      {
        localObject = ProductFormPolynomial.fromBinary(paramInputStream, i, k, m, n + 1, n);
        localBasis = this;
      }
      IntegerPolynomial localIntegerPolynomial2;
      for (;;)
      {
        localBasis.f = ((Polynomial)localObject);
        if (paramNTRUSigningKeyGenerationParameters.basisType != 0) {
          break label210;
        }
        localIntegerPolynomial2 = IntegerPolynomial.fromBinary(paramInputStream, i, j);
        for (int i1 = 0; i1 < localIntegerPolynomial2.coeffs.length; i1++)
        {
          int[] arrayOfInt = localIntegerPolynomial2.coeffs;
          arrayOfInt[i1] -= j / 2;
        }
        IntegerPolynomial localIntegerPolynomial1 = IntegerPolynomial.fromBinary3Tight(paramInputStream, i);
        if (bool)
        {
          localObject = new SparseTernaryPolynomial(localIntegerPolynomial1);
          localBasis = this;
        }
        else
        {
          localObject = new DenseTernaryPolynomial(localIntegerPolynomial1);
          localBasis = this;
        }
      }
      this.fPrime = localIntegerPolynomial2;
      for (;;)
      {
        if (paramBoolean) {
          this.h = IntegerPolynomial.fromBinary(paramInputStream, i, j);
        }
        return;
        label210:
        if (paramNTRUSigningKeyGenerationParameters.polyType == 1) {
          this.fPrime = ProductFormPolynomial.fromBinary(paramInputStream, i, k, m, n + 1, n);
        } else {
          this.fPrime = IntegerPolynomial.fromBinary3Tight(paramInputStream, i);
        }
      }
    }
    
    protected Basis(Polynomial paramPolynomial1, Polynomial paramPolynomial2, IntegerPolynomial paramIntegerPolynomial, NTRUSigningKeyGenerationParameters paramNTRUSigningKeyGenerationParameters)
    {
      this.f = paramPolynomial1;
      this.fPrime = paramPolynomial2;
      this.h = paramIntegerPolynomial;
      this.params = paramNTRUSigningKeyGenerationParameters;
    }
    
    private byte[] getEncoded(Polynomial paramPolynomial)
    {
      if ((paramPolynomial instanceof ProductFormPolynomial)) {
        return ((ProductFormPolynomial)paramPolynomial).toBinary();
      }
      return paramPolynomial.toIntegerPolynomial().toBinary3Tight();
    }
    
    void encode(OutputStream paramOutputStream, boolean paramBoolean)
      throws IOException
    {
      int i = this.params.q;
      paramOutputStream.write(getEncoded(this.f));
      if (this.params.basisType == 0)
      {
        IntegerPolynomial localIntegerPolynomial = this.fPrime.toIntegerPolynomial();
        for (int j = 0; j < localIntegerPolynomial.coeffs.length; j++)
        {
          int[] arrayOfInt = localIntegerPolynomial.coeffs;
          arrayOfInt[j] += i / 2;
        }
        paramOutputStream.write(localIntegerPolynomial.toBinary(i));
      }
      for (;;)
      {
        if (paramBoolean) {
          paramOutputStream.write(this.h.toBinary(i));
        }
        return;
        paramOutputStream.write(getEncoded(this.fPrime));
      }
    }
    
    public boolean equals(Object paramObject)
    {
      if (this == paramObject) {}
      Basis localBasis;
      do
      {
        do
        {
          return true;
          if (paramObject == null) {
            return false;
          }
          if (!(paramObject instanceof Basis)) {
            return false;
          }
          localBasis = (Basis)paramObject;
          if (this.f == null)
          {
            if (localBasis.f != null) {
              return false;
            }
          }
          else if (!this.f.equals(localBasis.f)) {
            return false;
          }
          if (this.fPrime == null)
          {
            if (localBasis.fPrime != null) {
              return false;
            }
          }
          else if (!this.fPrime.equals(localBasis.fPrime)) {
            return false;
          }
          if (this.h == null)
          {
            if (localBasis.h != null) {
              return false;
            }
          }
          else if (!this.h.equals(localBasis.h)) {
            return false;
          }
          if (this.params != null) {
            break;
          }
        } while (localBasis.params == null);
        return false;
      } while (this.params.equals(localBasis.params));
      return false;
    }
    
    public int hashCode()
    {
      int i;
      int k;
      label26:
      int n;
      label44:
      int i1;
      int i2;
      if (this.f == null)
      {
        i = 0;
        int j = 31 * (i + 31);
        if (this.fPrime != null) {
          break label85;
        }
        k = 0;
        int m = 31 * (j + k);
        if (this.h != null) {
          break label96;
        }
        n = 0;
        i1 = 31 * (m + n);
        NTRUSigningKeyGenerationParameters localNTRUSigningKeyGenerationParameters = this.params;
        i2 = 0;
        if (localNTRUSigningKeyGenerationParameters != null) {
          break label108;
        }
      }
      for (;;)
      {
        return i1 + i2;
        i = this.f.hashCode();
        break;
        label85:
        k = this.fPrime.hashCode();
        break label26;
        label96:
        n = this.h.hashCode();
        break label44;
        label108:
        i2 = this.params.hashCode();
      }
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.params.NTRUSigningPrivateKeyParameters
 * JD-Core Version:    0.7.0.1
 */