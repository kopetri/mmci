package org.spongycastle.asn1.isismtt.x509;

import java.util.Enumeration;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERPrintableString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.x500.DirectoryString;

public class ProfessionInfo
  extends ASN1Object
{
  public static final ASN1ObjectIdentifier Notar;
  public static final ASN1ObjectIdentifier Notariatsverwalter;
  public static final ASN1ObjectIdentifier Notariatsverwalterin;
  public static final ASN1ObjectIdentifier Notarin;
  public static final ASN1ObjectIdentifier Notarvertreter;
  public static final ASN1ObjectIdentifier Notarvertreterin;
  public static final ASN1ObjectIdentifier Patentanwalt = new ASN1ObjectIdentifier(NamingAuthority.id_isismtt_at_namingAuthorities_RechtWirtschaftSteuern + ".19");
  public static final ASN1ObjectIdentifier Patentanwltin;
  public static final ASN1ObjectIdentifier Rechtsanwalt;
  public static final ASN1ObjectIdentifier Rechtsanwltin = new ASN1ObjectIdentifier(NamingAuthority.id_isismtt_at_namingAuthorities_RechtWirtschaftSteuern + ".1");
  public static final ASN1ObjectIdentifier Rechtsbeistand;
  public static final ASN1ObjectIdentifier Steuerberater;
  public static final ASN1ObjectIdentifier Steuerberaterin;
  public static final ASN1ObjectIdentifier Steuerbevollmchtigte;
  public static final ASN1ObjectIdentifier Steuerbevollmchtigter;
  public static final ASN1ObjectIdentifier VereidigteBuchprferin;
  public static final ASN1ObjectIdentifier VereidigterBuchprfer;
  public static final ASN1ObjectIdentifier Wirtschaftsprfer;
  public static final ASN1ObjectIdentifier Wirtschaftsprferin;
  private ASN1OctetString addProfessionInfo;
  private NamingAuthority namingAuthority;
  private ASN1Sequence professionItems;
  private ASN1Sequence professionOIDs;
  private String registrationNumber;
  
  static
  {
    Rechtsanwalt = new ASN1ObjectIdentifier(NamingAuthority.id_isismtt_at_namingAuthorities_RechtWirtschaftSteuern + ".2");
    Rechtsbeistand = new ASN1ObjectIdentifier(NamingAuthority.id_isismtt_at_namingAuthorities_RechtWirtschaftSteuern + ".3");
    Steuerberaterin = new ASN1ObjectIdentifier(NamingAuthority.id_isismtt_at_namingAuthorities_RechtWirtschaftSteuern + ".4");
    Steuerberater = new ASN1ObjectIdentifier(NamingAuthority.id_isismtt_at_namingAuthorities_RechtWirtschaftSteuern + ".5");
    Steuerbevollmchtigte = new ASN1ObjectIdentifier(NamingAuthority.id_isismtt_at_namingAuthorities_RechtWirtschaftSteuern + ".6");
    Steuerbevollmchtigter = new ASN1ObjectIdentifier(NamingAuthority.id_isismtt_at_namingAuthorities_RechtWirtschaftSteuern + ".7");
    Notarin = new ASN1ObjectIdentifier(NamingAuthority.id_isismtt_at_namingAuthorities_RechtWirtschaftSteuern + ".8");
    Notar = new ASN1ObjectIdentifier(NamingAuthority.id_isismtt_at_namingAuthorities_RechtWirtschaftSteuern + ".9");
    Notarvertreterin = new ASN1ObjectIdentifier(NamingAuthority.id_isismtt_at_namingAuthorities_RechtWirtschaftSteuern + ".10");
    Notarvertreter = new ASN1ObjectIdentifier(NamingAuthority.id_isismtt_at_namingAuthorities_RechtWirtschaftSteuern + ".11");
    Notariatsverwalterin = new ASN1ObjectIdentifier(NamingAuthority.id_isismtt_at_namingAuthorities_RechtWirtschaftSteuern + ".12");
    Notariatsverwalter = new ASN1ObjectIdentifier(NamingAuthority.id_isismtt_at_namingAuthorities_RechtWirtschaftSteuern + ".13");
    Wirtschaftsprferin = new ASN1ObjectIdentifier(NamingAuthority.id_isismtt_at_namingAuthorities_RechtWirtschaftSteuern + ".14");
    Wirtschaftsprfer = new ASN1ObjectIdentifier(NamingAuthority.id_isismtt_at_namingAuthorities_RechtWirtschaftSteuern + ".15");
    VereidigteBuchprferin = new ASN1ObjectIdentifier(NamingAuthority.id_isismtt_at_namingAuthorities_RechtWirtschaftSteuern + ".16");
    VereidigterBuchprfer = new ASN1ObjectIdentifier(NamingAuthority.id_isismtt_at_namingAuthorities_RechtWirtschaftSteuern + ".17");
    Patentanwltin = new ASN1ObjectIdentifier(NamingAuthority.id_isismtt_at_namingAuthorities_RechtWirtschaftSteuern + ".18");
  }
  
  private ProfessionInfo(ASN1Sequence paramASN1Sequence)
  {
    if (paramASN1Sequence.size() > 5) {
      throw new IllegalArgumentException("Bad sequence size: " + paramASN1Sequence.size());
    }
    Enumeration localEnumeration = paramASN1Sequence.getObjects();
    ASN1Encodable localASN1Encodable1 = (ASN1Encodable)localEnumeration.nextElement();
    if ((localASN1Encodable1 instanceof ASN1TaggedObject))
    {
      if (((ASN1TaggedObject)localASN1Encodable1).getTagNo() != 0) {
        throw new IllegalArgumentException("Bad tag number: " + ((ASN1TaggedObject)localASN1Encodable1).getTagNo());
      }
      this.namingAuthority = NamingAuthority.getInstance((ASN1TaggedObject)localASN1Encodable1, true);
      localASN1Encodable1 = (ASN1Encodable)localEnumeration.nextElement();
    }
    this.professionItems = ASN1Sequence.getInstance(localASN1Encodable1);
    ASN1Encodable localASN1Encodable4;
    ASN1Encodable localASN1Encodable3;
    if (localEnumeration.hasMoreElements())
    {
      localASN1Encodable4 = (ASN1Encodable)localEnumeration.nextElement();
      if ((localASN1Encodable4 instanceof ASN1Sequence)) {
        this.professionOIDs = ASN1Sequence.getInstance(localASN1Encodable4);
      }
    }
    else if (localEnumeration.hasMoreElements())
    {
      localASN1Encodable3 = (ASN1Encodable)localEnumeration.nextElement();
      if (!(localASN1Encodable3 instanceof DERPrintableString)) {
        break label317;
      }
      this.registrationNumber = DERPrintableString.getInstance(localASN1Encodable3).getString();
    }
    ASN1Encodable localASN1Encodable2;
    for (;;)
    {
      if (localEnumeration.hasMoreElements())
      {
        localASN1Encodable2 = (ASN1Encodable)localEnumeration.nextElement();
        if (!(localASN1Encodable2 instanceof DEROctetString)) {
          break label365;
        }
        this.addProfessionInfo = ((DEROctetString)localASN1Encodable2);
      }
      return;
      if ((localASN1Encodable4 instanceof DERPrintableString))
      {
        this.registrationNumber = DERPrintableString.getInstance(localASN1Encodable4).getString();
        break;
      }
      if ((localASN1Encodable4 instanceof ASN1OctetString))
      {
        this.addProfessionInfo = ASN1OctetString.getInstance(localASN1Encodable4);
        break;
      }
      throw new IllegalArgumentException("Bad object encountered: " + localASN1Encodable4.getClass());
      label317:
      if (!(localASN1Encodable3 instanceof DEROctetString)) {
        break label337;
      }
      this.addProfessionInfo = ((DEROctetString)localASN1Encodable3);
    }
    label337:
    throw new IllegalArgumentException("Bad object encountered: " + localASN1Encodable3.getClass());
    label365:
    throw new IllegalArgumentException("Bad object encountered: " + localASN1Encodable2.getClass());
  }
  
  public ProfessionInfo(NamingAuthority paramNamingAuthority, DirectoryString[] paramArrayOfDirectoryString, ASN1ObjectIdentifier[] paramArrayOfASN1ObjectIdentifier, String paramString, ASN1OctetString paramASN1OctetString)
  {
    this.namingAuthority = paramNamingAuthority;
    ASN1EncodableVector localASN1EncodableVector1 = new ASN1EncodableVector();
    for (int i = 0; i != paramArrayOfDirectoryString.length; i++) {
      localASN1EncodableVector1.add(paramArrayOfDirectoryString[i]);
    }
    this.professionItems = new DERSequence(localASN1EncodableVector1);
    if (paramArrayOfASN1ObjectIdentifier != null)
    {
      ASN1EncodableVector localASN1EncodableVector2 = new ASN1EncodableVector();
      for (int j = 0; j != paramArrayOfASN1ObjectIdentifier.length; j++) {
        localASN1EncodableVector2.add(paramArrayOfASN1ObjectIdentifier[j]);
      }
      this.professionOIDs = new DERSequence(localASN1EncodableVector2);
    }
    this.registrationNumber = paramString;
    this.addProfessionInfo = paramASN1OctetString;
  }
  
  public static ProfessionInfo getInstance(Object paramObject)
  {
    if ((paramObject == null) || ((paramObject instanceof ProfessionInfo))) {
      return (ProfessionInfo)paramObject;
    }
    if ((paramObject instanceof ASN1Sequence)) {
      return new ProfessionInfo((ASN1Sequence)paramObject);
    }
    throw new IllegalArgumentException("illegal object in getInstance: " + paramObject.getClass().getName());
  }
  
  public ASN1OctetString getAddProfessionInfo()
  {
    return this.addProfessionInfo;
  }
  
  public NamingAuthority getNamingAuthority()
  {
    return this.namingAuthority;
  }
  
  public DirectoryString[] getProfessionItems()
  {
    DirectoryString[] arrayOfDirectoryString = new DirectoryString[this.professionItems.size()];
    int i = 0;
    Enumeration localEnumeration = this.professionItems.getObjects();
    while (localEnumeration.hasMoreElements())
    {
      int j = i + 1;
      arrayOfDirectoryString[i] = DirectoryString.getInstance(localEnumeration.nextElement());
      i = j;
    }
    return arrayOfDirectoryString;
  }
  
  public ASN1ObjectIdentifier[] getProfessionOIDs()
  {
    ASN1ObjectIdentifier[] arrayOfASN1ObjectIdentifier;
    if (this.professionOIDs == null) {
      arrayOfASN1ObjectIdentifier = new ASN1ObjectIdentifier[0];
    }
    for (;;)
    {
      return arrayOfASN1ObjectIdentifier;
      arrayOfASN1ObjectIdentifier = new ASN1ObjectIdentifier[this.professionOIDs.size()];
      int i = 0;
      Enumeration localEnumeration = this.professionOIDs.getObjects();
      while (localEnumeration.hasMoreElements())
      {
        int j = i + 1;
        arrayOfASN1ObjectIdentifier[i] = ASN1ObjectIdentifier.getInstance(localEnumeration.nextElement());
        i = j;
      }
    }
  }
  
  public String getRegistrationNumber()
  {
    return this.registrationNumber;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    if (this.namingAuthority != null) {
      localASN1EncodableVector.add(new DERTaggedObject(true, 0, this.namingAuthority));
    }
    localASN1EncodableVector.add(this.professionItems);
    if (this.professionOIDs != null) {
      localASN1EncodableVector.add(this.professionOIDs);
    }
    if (this.registrationNumber != null) {
      localASN1EncodableVector.add(new DERPrintableString(this.registrationNumber, true));
    }
    if (this.addProfessionInfo != null) {
      localASN1EncodableVector.add(this.addProfessionInfo);
    }
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.isismtt.x509.ProfessionInfo
 * JD-Core Version:    0.7.0.1
 */