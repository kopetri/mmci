package org.spongycastle.asn1.tsp;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;

public class Accuracy
  extends ASN1Object
{
  protected static final int MAX_MICROS = 999;
  protected static final int MAX_MILLIS = 999;
  protected static final int MIN_MICROS = 1;
  protected static final int MIN_MILLIS = 1;
  ASN1Integer micros;
  ASN1Integer millis;
  ASN1Integer seconds;
  
  protected Accuracy() {}
  
  public Accuracy(ASN1Integer paramASN1Integer1, ASN1Integer paramASN1Integer2, ASN1Integer paramASN1Integer3)
  {
    this.seconds = paramASN1Integer1;
    if ((paramASN1Integer2 != null) && ((paramASN1Integer2.getValue().intValue() <= 0) || (paramASN1Integer2.getValue().intValue() > 999))) {
      throw new IllegalArgumentException("Invalid millis field : not in (1..999)");
    }
    this.millis = paramASN1Integer2;
    if ((paramASN1Integer3 != null) && ((paramASN1Integer3.getValue().intValue() <= 0) || (paramASN1Integer3.getValue().intValue() > 999))) {
      throw new IllegalArgumentException("Invalid micros field : not in (1..999)");
    }
    this.micros = paramASN1Integer3;
  }
  
  private Accuracy(ASN1Sequence paramASN1Sequence)
  {
    this.seconds = null;
    this.millis = null;
    this.micros = null;
    int i = 0;
    if (i < paramASN1Sequence.size())
    {
      if ((paramASN1Sequence.getObjectAt(i) instanceof ASN1Integer)) {
        this.seconds = ((ASN1Integer)paramASN1Sequence.getObjectAt(i));
      }
      do
      {
        DERTaggedObject localDERTaggedObject;
        do
        {
          do
          {
            i++;
            break;
          } while (!(paramASN1Sequence.getObjectAt(i) instanceof DERTaggedObject));
          localDERTaggedObject = (DERTaggedObject)paramASN1Sequence.getObjectAt(i);
          switch (localDERTaggedObject.getTagNo())
          {
          default: 
            throw new IllegalArgumentException("Invalig tag number");
          case 0: 
            this.millis = ASN1Integer.getInstance(localDERTaggedObject, false);
          }
        } while ((this.millis.getValue().intValue() > 0) && (this.millis.getValue().intValue() <= 999));
        throw new IllegalArgumentException("Invalid millis field : not in (1..999).");
        this.micros = ASN1Integer.getInstance(localDERTaggedObject, false);
      } while ((this.micros.getValue().intValue() > 0) && (this.micros.getValue().intValue() <= 999));
      throw new IllegalArgumentException("Invalid micros field : not in (1..999).");
    }
  }
  
  public static Accuracy getInstance(Object paramObject)
  {
    if ((paramObject instanceof Accuracy)) {
      return (Accuracy)paramObject;
    }
    if (paramObject != null) {
      return new Accuracy(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public ASN1Integer getMicros()
  {
    return this.micros;
  }
  
  public ASN1Integer getMillis()
  {
    return this.millis;
  }
  
  public ASN1Integer getSeconds()
  {
    return this.seconds;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    if (this.seconds != null) {
      localASN1EncodableVector.add(this.seconds);
    }
    if (this.millis != null) {
      localASN1EncodableVector.add(new DERTaggedObject(false, 0, this.millis));
    }
    if (this.micros != null) {
      localASN1EncodableVector.add(new DERTaggedObject(false, 1, this.micros));
    }
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.tsp.Accuracy
 * JD-Core Version:    0.7.0.1
 */