package org.spongycastle.asn1.pkcs;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Enumeration;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1Set;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;

public class PrivateKeyInfo
  extends ASN1Object
{
  private AlgorithmIdentifier algId;
  private ASN1Set attributes;
  private ASN1OctetString privKey;
  
  public PrivateKeyInfo(ASN1Sequence paramASN1Sequence)
  {
    Enumeration localEnumeration = paramASN1Sequence.getObjects();
    if (((ASN1Integer)localEnumeration.nextElement()).getValue().intValue() != 0) {
      throw new IllegalArgumentException("wrong version for private key info");
    }
    this.algId = AlgorithmIdentifier.getInstance(localEnumeration.nextElement());
    this.privKey = ASN1OctetString.getInstance(localEnumeration.nextElement());
    if (localEnumeration.hasMoreElements()) {
      this.attributes = ASN1Set.getInstance((ASN1TaggedObject)localEnumeration.nextElement(), false);
    }
  }
  
  public PrivateKeyInfo(AlgorithmIdentifier paramAlgorithmIdentifier, ASN1Encodable paramASN1Encodable)
    throws IOException
  {
    this(paramAlgorithmIdentifier, paramASN1Encodable, null);
  }
  
  public PrivateKeyInfo(AlgorithmIdentifier paramAlgorithmIdentifier, ASN1Encodable paramASN1Encodable, ASN1Set paramASN1Set)
    throws IOException
  {
    this.privKey = new DEROctetString(paramASN1Encodable.toASN1Primitive().getEncoded("DER"));
    this.algId = paramAlgorithmIdentifier;
    this.attributes = paramASN1Set;
  }
  
  public static PrivateKeyInfo getInstance(Object paramObject)
  {
    if ((paramObject instanceof PrivateKeyInfo)) {
      return (PrivateKeyInfo)paramObject;
    }
    if (paramObject != null) {
      return new PrivateKeyInfo(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public static PrivateKeyInfo getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean)
  {
    return getInstance(ASN1Sequence.getInstance(paramASN1TaggedObject, paramBoolean));
  }
  
  public AlgorithmIdentifier getAlgorithmId()
  {
    return this.algId;
  }
  
  public ASN1Set getAttributes()
  {
    return this.attributes;
  }
  
  public ASN1Primitive getPrivateKey()
  {
    try
    {
      ASN1Primitive localASN1Primitive = parsePrivateKey().toASN1Primitive();
      return localASN1Primitive;
    }
    catch (IOException localIOException)
    {
      throw new IllegalStateException("unable to parse private key");
    }
  }
  
  public AlgorithmIdentifier getPrivateKeyAlgorithm()
  {
    return this.algId;
  }
  
  public ASN1Encodable parsePrivateKey()
    throws IOException
  {
    return ASN1Primitive.fromByteArray(this.privKey.getOctets());
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(new ASN1Integer(0));
    localASN1EncodableVector.add(this.algId);
    localASN1EncodableVector.add(this.privKey);
    if (this.attributes != null) {
      localASN1EncodableVector.add(new DERTaggedObject(false, 0, this.attributes));
    }
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.pkcs.PrivateKeyInfo
 * JD-Core Version:    0.7.0.1
 */