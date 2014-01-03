package org.spongycastle.asn1.isismtt.x509;

import java.util.Enumeration;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1String;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERIA5String;
import org.spongycastle.asn1.DERObjectIdentifier;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.isismtt.ISISMTTObjectIdentifiers;
import org.spongycastle.asn1.x500.DirectoryString;

public class NamingAuthority
  extends ASN1Object
{
  public static final ASN1ObjectIdentifier id_isismtt_at_namingAuthorities_RechtWirtschaftSteuern = new ASN1ObjectIdentifier(ISISMTTObjectIdentifiers.id_isismtt_at_namingAuthorities + ".1");
  private ASN1ObjectIdentifier namingAuthorityId;
  private DirectoryString namingAuthorityText;
  private String namingAuthorityUrl;
  
  public NamingAuthority(ASN1ObjectIdentifier paramASN1ObjectIdentifier, String paramString, DirectoryString paramDirectoryString)
  {
    this.namingAuthorityId = paramASN1ObjectIdentifier;
    this.namingAuthorityUrl = paramString;
    this.namingAuthorityText = paramDirectoryString;
  }
  
  private NamingAuthority(ASN1Sequence paramASN1Sequence)
  {
    if (paramASN1Sequence.size() > 3) {
      throw new IllegalArgumentException("Bad sequence size: " + paramASN1Sequence.size());
    }
    Enumeration localEnumeration = paramASN1Sequence.getObjects();
    ASN1Encodable localASN1Encodable3;
    ASN1Encodable localASN1Encodable2;
    if (localEnumeration.hasMoreElements())
    {
      localASN1Encodable3 = (ASN1Encodable)localEnumeration.nextElement();
      if ((localASN1Encodable3 instanceof ASN1ObjectIdentifier)) {
        this.namingAuthorityId = ((ASN1ObjectIdentifier)localASN1Encodable3);
      }
    }
    else if (localEnumeration.hasMoreElements())
    {
      localASN1Encodable2 = (ASN1Encodable)localEnumeration.nextElement();
      if (!(localASN1Encodable2 instanceof DERIA5String)) {
        break label227;
      }
      this.namingAuthorityUrl = DERIA5String.getInstance(localASN1Encodable2).getString();
    }
    ASN1Encodable localASN1Encodable1;
    for (;;)
    {
      if (localEnumeration.hasMoreElements())
      {
        localASN1Encodable1 = (ASN1Encodable)localEnumeration.nextElement();
        if (!(localASN1Encodable1 instanceof ASN1String)) {
          break label275;
        }
        this.namingAuthorityText = DirectoryString.getInstance(localASN1Encodable1);
      }
      return;
      if ((localASN1Encodable3 instanceof DERIA5String))
      {
        this.namingAuthorityUrl = DERIA5String.getInstance(localASN1Encodable3).getString();
        break;
      }
      if ((localASN1Encodable3 instanceof ASN1String))
      {
        this.namingAuthorityText = DirectoryString.getInstance(localASN1Encodable3);
        break;
      }
      throw new IllegalArgumentException("Bad object encountered: " + localASN1Encodable3.getClass());
      label227:
      if (!(localASN1Encodable2 instanceof ASN1String)) {
        break label247;
      }
      this.namingAuthorityText = DirectoryString.getInstance(localASN1Encodable2);
    }
    label247:
    throw new IllegalArgumentException("Bad object encountered: " + localASN1Encodable2.getClass());
    label275:
    throw new IllegalArgumentException("Bad object encountered: " + localASN1Encodable1.getClass());
  }
  
  public NamingAuthority(DERObjectIdentifier paramDERObjectIdentifier, String paramString, DirectoryString paramDirectoryString)
  {
    this.namingAuthorityId = new ASN1ObjectIdentifier(paramDERObjectIdentifier.getId());
    this.namingAuthorityUrl = paramString;
    this.namingAuthorityText = paramDirectoryString;
  }
  
  public static NamingAuthority getInstance(Object paramObject)
  {
    if ((paramObject == null) || ((paramObject instanceof NamingAuthority))) {
      return (NamingAuthority)paramObject;
    }
    if ((paramObject instanceof ASN1Sequence)) {
      return new NamingAuthority((ASN1Sequence)paramObject);
    }
    throw new IllegalArgumentException("illegal object in getInstance: " + paramObject.getClass().getName());
  }
  
  public static NamingAuthority getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean)
  {
    return getInstance(ASN1Sequence.getInstance(paramASN1TaggedObject, paramBoolean));
  }
  
  public ASN1ObjectIdentifier getNamingAuthorityId()
  {
    return this.namingAuthorityId;
  }
  
  public DirectoryString getNamingAuthorityText()
  {
    return this.namingAuthorityText;
  }
  
  public String getNamingAuthorityUrl()
  {
    return this.namingAuthorityUrl;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    if (this.namingAuthorityId != null) {
      localASN1EncodableVector.add(this.namingAuthorityId);
    }
    if (this.namingAuthorityUrl != null) {
      localASN1EncodableVector.add(new DERIA5String(this.namingAuthorityUrl, true));
    }
    if (this.namingAuthorityText != null) {
      localASN1EncodableVector.add(this.namingAuthorityText);
    }
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.isismtt.x509.NamingAuthority
 * JD-Core Version:    0.7.0.1
 */