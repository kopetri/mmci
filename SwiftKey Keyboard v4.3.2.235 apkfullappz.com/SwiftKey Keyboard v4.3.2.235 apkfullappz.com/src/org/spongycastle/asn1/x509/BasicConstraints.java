package org.spongycastle.asn1.x509;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERBoolean;
import org.spongycastle.asn1.DERSequence;

public class BasicConstraints
  extends ASN1Object
{
  DERBoolean cA = new DERBoolean(false);
  ASN1Integer pathLenConstraint = null;
  
  public BasicConstraints(int paramInt)
  {
    this.cA = new DERBoolean(true);
    this.pathLenConstraint = new ASN1Integer(paramInt);
  }
  
  private BasicConstraints(ASN1Sequence paramASN1Sequence)
  {
    if (paramASN1Sequence.size() == 0)
    {
      this.cA = null;
      this.pathLenConstraint = null;
    }
    for (;;)
    {
      return;
      if ((paramASN1Sequence.getObjectAt(0) instanceof DERBoolean)) {
        this.cA = DERBoolean.getInstance(paramASN1Sequence.getObjectAt(0));
      }
      while (paramASN1Sequence.size() > 1)
      {
        if (this.cA == null) {
          break label110;
        }
        this.pathLenConstraint = ASN1Integer.getInstance(paramASN1Sequence.getObjectAt(1));
        return;
        this.cA = null;
        this.pathLenConstraint = ASN1Integer.getInstance(paramASN1Sequence.getObjectAt(0));
      }
    }
    label110:
    throw new IllegalArgumentException("wrong sequence in constructor");
  }
  
  public BasicConstraints(boolean paramBoolean)
  {
    if (paramBoolean) {}
    for (this.cA = new DERBoolean(true);; this.cA = null)
    {
      this.pathLenConstraint = null;
      return;
    }
  }
  
  public static BasicConstraints getInstance(Object paramObject)
  {
    if ((paramObject instanceof BasicConstraints)) {
      return (BasicConstraints)paramObject;
    }
    if ((paramObject instanceof X509Extension)) {
      return getInstance(X509Extension.convertValueToObject((X509Extension)paramObject));
    }
    if (paramObject != null) {
      return new BasicConstraints(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public static BasicConstraints getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean)
  {
    return getInstance(ASN1Sequence.getInstance(paramASN1TaggedObject, paramBoolean));
  }
  
  public BigInteger getPathLenConstraint()
  {
    if (this.pathLenConstraint != null) {
      return this.pathLenConstraint.getValue();
    }
    return null;
  }
  
  public boolean isCA()
  {
    return (this.cA != null) && (this.cA.isTrue());
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    if (this.cA != null) {
      localASN1EncodableVector.add(this.cA);
    }
    if (this.pathLenConstraint != null) {
      localASN1EncodableVector.add(this.pathLenConstraint);
    }
    return new DERSequence(localASN1EncodableVector);
  }
  
  public String toString()
  {
    if (this.pathLenConstraint == null)
    {
      if (this.cA == null) {
        return "BasicConstraints: isCa(false)";
      }
      return "BasicConstraints: isCa(" + isCA() + ")";
    }
    return "BasicConstraints: isCa(" + isCA() + "), pathLenConstraint = " + this.pathLenConstraint.getValue();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.x509.BasicConstraints
 * JD-Core Version:    0.7.0.1
 */