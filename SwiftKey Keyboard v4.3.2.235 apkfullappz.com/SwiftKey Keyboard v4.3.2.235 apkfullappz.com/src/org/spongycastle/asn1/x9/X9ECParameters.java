package org.spongycastle.asn1.x9;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECCurve.F2m;
import org.spongycastle.math.ec.ECCurve.Fp;
import org.spongycastle.math.ec.ECPoint;

public class X9ECParameters
  extends ASN1Object
  implements X9ObjectIdentifiers
{
  private static final BigInteger ONE = BigInteger.valueOf(1L);
  private ECCurve curve;
  private X9FieldID fieldID;
  private ECPoint g;
  private BigInteger h;
  private BigInteger n;
  private byte[] seed;
  
  private X9ECParameters(ASN1Sequence paramASN1Sequence)
  {
    if ((!(paramASN1Sequence.getObjectAt(0) instanceof ASN1Integer)) || (!((ASN1Integer)paramASN1Sequence.getObjectAt(0)).getValue().equals(ONE))) {
      throw new IllegalArgumentException("bad version in X9ECParameters");
    }
    X9Curve localX9Curve = new X9Curve(new X9FieldID((ASN1Sequence)paramASN1Sequence.getObjectAt(1)), (ASN1Sequence)paramASN1Sequence.getObjectAt(2));
    this.curve = localX9Curve.getCurve();
    this.g = new X9ECPoint(this.curve, (ASN1OctetString)paramASN1Sequence.getObjectAt(3)).getPoint();
    this.n = ((ASN1Integer)paramASN1Sequence.getObjectAt(4)).getValue();
    this.seed = localX9Curve.getSeed();
    if (paramASN1Sequence.size() == 6) {
      this.h = ((ASN1Integer)paramASN1Sequence.getObjectAt(5)).getValue();
    }
  }
  
  public X9ECParameters(ECCurve paramECCurve, ECPoint paramECPoint, BigInteger paramBigInteger)
  {
    this(paramECCurve, paramECPoint, paramBigInteger, ONE, null);
  }
  
  public X9ECParameters(ECCurve paramECCurve, ECPoint paramECPoint, BigInteger paramBigInteger1, BigInteger paramBigInteger2)
  {
    this(paramECCurve, paramECPoint, paramBigInteger1, paramBigInteger2, null);
  }
  
  public X9ECParameters(ECCurve paramECCurve, ECPoint paramECPoint, BigInteger paramBigInteger1, BigInteger paramBigInteger2, byte[] paramArrayOfByte)
  {
    this.curve = paramECCurve;
    this.g = paramECPoint;
    this.n = paramBigInteger1;
    this.h = paramBigInteger2;
    this.seed = paramArrayOfByte;
    if ((paramECCurve instanceof ECCurve.Fp)) {
      this.fieldID = new X9FieldID(((ECCurve.Fp)paramECCurve).getQ());
    }
    while (!(paramECCurve instanceof ECCurve.F2m)) {
      return;
    }
    ECCurve.F2m localF2m = (ECCurve.F2m)paramECCurve;
    this.fieldID = new X9FieldID(localF2m.getM(), localF2m.getK1(), localF2m.getK2(), localF2m.getK3());
  }
  
  public static X9ECParameters getInstance(Object paramObject)
  {
    if ((paramObject instanceof X9ECParameters)) {
      return (X9ECParameters)paramObject;
    }
    if (paramObject != null) {
      return new X9ECParameters(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public ECCurve getCurve()
  {
    return this.curve;
  }
  
  public ECPoint getG()
  {
    return this.g;
  }
  
  public BigInteger getH()
  {
    if (this.h == null) {
      return ONE;
    }
    return this.h;
  }
  
  public BigInteger getN()
  {
    return this.n;
  }
  
  public byte[] getSeed()
  {
    return this.seed;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(new ASN1Integer(1));
    localASN1EncodableVector.add(this.fieldID);
    localASN1EncodableVector.add(new X9Curve(this.curve, this.seed));
    localASN1EncodableVector.add(new X9ECPoint(this.g));
    localASN1EncodableVector.add(new ASN1Integer(this.n));
    if (this.h != null) {
      localASN1EncodableVector.add(new ASN1Integer(this.h));
    }
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.x9.X9ECParameters
 * JD-Core Version:    0.7.0.1
 */