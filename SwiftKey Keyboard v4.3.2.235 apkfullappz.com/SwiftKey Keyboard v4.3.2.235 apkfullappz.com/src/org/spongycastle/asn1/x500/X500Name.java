package org.spongycastle.asn1.x500;

import java.util.Enumeration;
import org.spongycastle.asn1.ASN1Choice;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x500.style.BCStyle;

public class X500Name
  extends ASN1Object
  implements ASN1Choice
{
  private static X500NameStyle defaultStyle = BCStyle.INSTANCE;
  private int hashCodeValue;
  private boolean isHashCodeCalculated;
  private RDN[] rdns;
  private X500NameStyle style;
  
  public X500Name(String paramString)
  {
    this(defaultStyle, paramString);
  }
  
  private X500Name(ASN1Sequence paramASN1Sequence)
  {
    this(defaultStyle, paramASN1Sequence);
  }
  
  public X500Name(X500NameStyle paramX500NameStyle, String paramString)
  {
    this(paramX500NameStyle.fromString(paramString));
    this.style = paramX500NameStyle;
  }
  
  private X500Name(X500NameStyle paramX500NameStyle, ASN1Sequence paramASN1Sequence)
  {
    this.style = paramX500NameStyle;
    this.rdns = new RDN[paramASN1Sequence.size()];
    int i = 0;
    Enumeration localEnumeration = paramASN1Sequence.getObjects();
    while (localEnumeration.hasMoreElements())
    {
      RDN[] arrayOfRDN = this.rdns;
      int j = i + 1;
      arrayOfRDN[i] = RDN.getInstance(localEnumeration.nextElement());
      i = j;
    }
  }
  
  public X500Name(X500NameStyle paramX500NameStyle, X500Name paramX500Name)
  {
    this.rdns = paramX500Name.rdns;
    this.style = paramX500NameStyle;
  }
  
  public X500Name(X500NameStyle paramX500NameStyle, RDN[] paramArrayOfRDN)
  {
    this.rdns = paramArrayOfRDN;
    this.style = paramX500NameStyle;
  }
  
  public X500Name(RDN[] paramArrayOfRDN)
  {
    this(defaultStyle, paramArrayOfRDN);
  }
  
  public static X500NameStyle getDefaultStyle()
  {
    return defaultStyle;
  }
  
  public static X500Name getInstance(Object paramObject)
  {
    if ((paramObject instanceof X500Name)) {
      return (X500Name)paramObject;
    }
    if (paramObject != null) {
      return new X500Name(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public static X500Name getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean)
  {
    return getInstance(ASN1Sequence.getInstance(paramASN1TaggedObject, true));
  }
  
  public static X500Name getInstance(X500NameStyle paramX500NameStyle, Object paramObject)
  {
    if ((paramObject instanceof X500Name)) {
      return getInstance(paramX500NameStyle, ((X500Name)paramObject).toASN1Primitive());
    }
    if (paramObject != null) {
      return new X500Name(paramX500NameStyle, ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public static void setDefaultStyle(X500NameStyle paramX500NameStyle)
  {
    if (paramX500NameStyle == null) {
      throw new NullPointerException("cannot set style to null");
    }
    defaultStyle = paramX500NameStyle;
  }
  
  public boolean equals(Object paramObject)
  {
    if (paramObject == this) {
      return true;
    }
    if ((!(paramObject instanceof X500Name)) && (!(paramObject instanceof ASN1Sequence))) {
      return false;
    }
    ASN1Primitive localASN1Primitive = ((ASN1Encodable)paramObject).toASN1Primitive();
    if (toASN1Primitive().equals(localASN1Primitive)) {
      return true;
    }
    try
    {
      boolean bool = this.style.areEqual(this, new X500Name(ASN1Sequence.getInstance(((ASN1Encodable)paramObject).toASN1Primitive())));
      return bool;
    }
    catch (Exception localException) {}
    return false;
  }
  
  public ASN1ObjectIdentifier[] getAttributeTypes()
  {
    int i = 0;
    for (int j = 0; j != this.rdns.length; j++) {
      i += this.rdns[j].size();
    }
    ASN1ObjectIdentifier[] arrayOfASN1ObjectIdentifier = new ASN1ObjectIdentifier[i];
    int k = 0;
    for (int m = 0; m != this.rdns.length; m++)
    {
      RDN localRDN = this.rdns[m];
      if (localRDN.isMultiValued())
      {
        AttributeTypeAndValue[] arrayOfAttributeTypeAndValue = localRDN.getTypesAndValues();
        int i1 = 0;
        while (i1 != arrayOfAttributeTypeAndValue.length)
        {
          int i2 = k + 1;
          arrayOfASN1ObjectIdentifier[k] = arrayOfAttributeTypeAndValue[i1].getType();
          i1++;
          k = i2;
        }
      }
      if (localRDN.size() != 0)
      {
        int n = k + 1;
        arrayOfASN1ObjectIdentifier[k] = localRDN.getFirst().getType();
        k = n;
      }
    }
    return arrayOfASN1ObjectIdentifier;
  }
  
  public RDN[] getRDNs()
  {
    RDN[] arrayOfRDN = new RDN[this.rdns.length];
    System.arraycopy(this.rdns, 0, arrayOfRDN, 0, arrayOfRDN.length);
    return arrayOfRDN;
  }
  
  public RDN[] getRDNs(ASN1ObjectIdentifier paramASN1ObjectIdentifier)
  {
    RDN[] arrayOfRDN1 = new RDN[this.rdns.length];
    int i = 0;
    int j = 0;
    if (j != this.rdns.length)
    {
      RDN localRDN = this.rdns[j];
      int m;
      if (localRDN.isMultiValued())
      {
        AttributeTypeAndValue[] arrayOfAttributeTypeAndValue = localRDN.getTypesAndValues();
        m = 0;
        label51:
        if (m != arrayOfAttributeTypeAndValue.length)
        {
          if (!arrayOfAttributeTypeAndValue[m].getType().equals(paramASN1ObjectIdentifier)) {
            break label93;
          }
          int n = i + 1;
          arrayOfRDN1[i] = localRDN;
          i = n;
        }
      }
      for (;;)
      {
        j++;
        break;
        label93:
        m++;
        break label51;
        if (localRDN.getFirst().getType().equals(paramASN1ObjectIdentifier))
        {
          int k = i + 1;
          arrayOfRDN1[i] = localRDN;
          i = k;
        }
      }
    }
    RDN[] arrayOfRDN2 = new RDN[i];
    System.arraycopy(arrayOfRDN1, 0, arrayOfRDN2, 0, arrayOfRDN2.length);
    return arrayOfRDN2;
  }
  
  public int hashCode()
  {
    if (this.isHashCodeCalculated) {
      return this.hashCodeValue;
    }
    this.isHashCodeCalculated = true;
    this.hashCodeValue = this.style.calculateHashCode(this);
    return this.hashCodeValue;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    return new DERSequence(this.rdns);
  }
  
  public String toString()
  {
    return this.style.toString(this);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.x500.X500Name
 * JD-Core Version:    0.7.0.1
 */