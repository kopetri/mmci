package org.spongycastle.asn1.icao;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERPrintableString;
import org.spongycastle.asn1.DERSequence;

public class LDSVersionInfo
  extends ASN1Object
{
  private DERPrintableString ldsVersion;
  private DERPrintableString unicodeVersion;
  
  public LDSVersionInfo(String paramString1, String paramString2)
  {
    this.ldsVersion = new DERPrintableString(paramString1);
    this.unicodeVersion = new DERPrintableString(paramString2);
  }
  
  private LDSVersionInfo(ASN1Sequence paramASN1Sequence)
  {
    if (paramASN1Sequence.size() != 2) {
      throw new IllegalArgumentException("sequence wrong size for LDSVersionInfo");
    }
    this.ldsVersion = DERPrintableString.getInstance(paramASN1Sequence.getObjectAt(0));
    this.unicodeVersion = DERPrintableString.getInstance(paramASN1Sequence.getObjectAt(1));
  }
  
  public static LDSVersionInfo getInstance(Object paramObject)
  {
    if ((paramObject instanceof LDSVersionInfo)) {
      return (LDSVersionInfo)paramObject;
    }
    if (paramObject != null) {
      return new LDSVersionInfo(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public String getLdsVersion()
  {
    return this.ldsVersion.getString();
  }
  
  public String getUnicodeVersion()
  {
    return this.unicodeVersion.getString();
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.ldsVersion);
    localASN1EncodableVector.add(this.unicodeVersion);
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.icao.LDSVersionInfo
 * JD-Core Version:    0.7.0.1
 */