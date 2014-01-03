package org.spongycastle.asn1.eac;

import java.io.IOException;
import java.util.Hashtable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERApplicationSpecific;

public class CertificateHolderAuthorization
  extends ASN1Object
{
  static BidirectionalMap AuthorizationRole;
  public static final int CVCA = 192;
  public static final int DV_DOMESTIC = 128;
  public static final int DV_FOREIGN = 64;
  public static final int IS = 0;
  public static final int RADG3 = 1;
  public static final int RADG4 = 2;
  static Hashtable ReverseMap;
  static Hashtable RightsDecodeMap;
  public static final ASN1ObjectIdentifier id_role_EAC = EACObjectIdentifiers.bsi_de.branch("3.1.2.1");
  DERApplicationSpecific accessRights;
  ASN1ObjectIdentifier oid;
  
  static
  {
    RightsDecodeMap = new Hashtable();
    AuthorizationRole = new BidirectionalMap();
    ReverseMap = new Hashtable();
    RightsDecodeMap.put(new Integer(2), "RADG4");
    RightsDecodeMap.put(new Integer(1), "RADG3");
    AuthorizationRole.put(new Integer(192), "CVCA");
    AuthorizationRole.put(new Integer(128), "DV_DOMESTIC");
    AuthorizationRole.put(new Integer(64), "DV_FOREIGN");
    AuthorizationRole.put(new Integer(0), "IS");
  }
  
  public CertificateHolderAuthorization(ASN1ObjectIdentifier paramASN1ObjectIdentifier, int paramInt)
    throws IOException
  {
    setOid(paramASN1ObjectIdentifier);
    setAccessRights((byte)paramInt);
  }
  
  public CertificateHolderAuthorization(DERApplicationSpecific paramDERApplicationSpecific)
    throws IOException
  {
    if (paramDERApplicationSpecific.getApplicationTag() == 76) {
      setPrivateData(new ASN1InputStream(paramDERApplicationSpecific.getContents()));
    }
  }
  
  public static int GetFlag(String paramString)
  {
    Integer localInteger = (Integer)AuthorizationRole.getReverse(paramString);
    if (localInteger == null) {
      throw new IllegalArgumentException("Unknown value " + paramString);
    }
    return localInteger.intValue();
  }
  
  public static String GetRoleDescription(int paramInt)
  {
    return (String)AuthorizationRole.get(new Integer(paramInt));
  }
  
  private void setAccessRights(byte paramByte)
  {
    byte[] arrayOfByte = { paramByte };
    this.accessRights = new DERApplicationSpecific(EACTags.getTag(83), arrayOfByte);
  }
  
  private void setOid(ASN1ObjectIdentifier paramASN1ObjectIdentifier)
  {
    this.oid = paramASN1ObjectIdentifier;
  }
  
  private void setPrivateData(ASN1InputStream paramASN1InputStream)
    throws IOException
  {
    ASN1Primitive localASN1Primitive1 = paramASN1InputStream.readObject();
    if ((localASN1Primitive1 instanceof ASN1ObjectIdentifier))
    {
      this.oid = ((ASN1ObjectIdentifier)localASN1Primitive1);
      ASN1Primitive localASN1Primitive2 = paramASN1InputStream.readObject();
      if ((localASN1Primitive2 instanceof DERApplicationSpecific)) {
        this.accessRights = ((DERApplicationSpecific)localASN1Primitive2);
      }
    }
    else
    {
      throw new IllegalArgumentException("no Oid in CerticateHolderAuthorization");
    }
    throw new IllegalArgumentException("No access rights in CerticateHolderAuthorization");
  }
  
  public int getAccessRights()
  {
    return 0xFF & this.accessRights.getContents()[0];
  }
  
  public ASN1ObjectIdentifier getOid()
  {
    return this.oid;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.oid);
    localASN1EncodableVector.add(this.accessRights);
    return new DERApplicationSpecific(76, localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.eac.CertificateHolderAuthorization
 * JD-Core Version:    0.7.0.1
 */