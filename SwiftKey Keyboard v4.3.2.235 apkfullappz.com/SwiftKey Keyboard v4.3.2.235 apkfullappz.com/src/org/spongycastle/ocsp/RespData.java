package org.spongycastle.ocsp;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERGeneralizedTime;
import org.spongycastle.asn1.DERObjectIdentifier;
import org.spongycastle.asn1.ocsp.ResponseData;
import org.spongycastle.asn1.ocsp.SingleResponse;
import org.spongycastle.asn1.x509.X509Extensions;

public class RespData
  implements java.security.cert.X509Extension
{
  ResponseData data;
  
  public RespData(ResponseData paramResponseData)
  {
    this.data = paramResponseData;
  }
  
  private Set getExtensionOIDs(boolean paramBoolean)
  {
    HashSet localHashSet = new HashSet();
    X509Extensions localX509Extensions = getResponseExtensions();
    if (localX509Extensions != null)
    {
      Enumeration localEnumeration = localX509Extensions.oids();
      while (localEnumeration.hasMoreElements())
      {
        DERObjectIdentifier localDERObjectIdentifier = (DERObjectIdentifier)localEnumeration.nextElement();
        if (paramBoolean == localX509Extensions.getExtension(localDERObjectIdentifier).isCritical()) {
          localHashSet.add(localDERObjectIdentifier.getId());
        }
      }
    }
    return localHashSet;
  }
  
  public Set getCriticalExtensionOIDs()
  {
    return getExtensionOIDs(true);
  }
  
  public byte[] getExtensionValue(String paramString)
  {
    X509Extensions localX509Extensions = getResponseExtensions();
    if (localX509Extensions != null)
    {
      org.spongycastle.asn1.x509.X509Extension localX509Extension = localX509Extensions.getExtension(new DERObjectIdentifier(paramString));
      if (localX509Extension != null) {
        try
        {
          byte[] arrayOfByte = localX509Extension.getValue().getEncoded("DER");
          return arrayOfByte;
        }
        catch (Exception localException)
        {
          throw new RuntimeException("error encoding " + localException.toString());
        }
      }
    }
    return null;
  }
  
  public Set getNonCriticalExtensionOIDs()
  {
    return getExtensionOIDs(false);
  }
  
  public Date getProducedAt()
  {
    try
    {
      Date localDate = this.data.getProducedAt().getDate();
      return localDate;
    }
    catch (ParseException localParseException)
    {
      throw new IllegalStateException("ParseException:" + localParseException.getMessage());
    }
  }
  
  public RespID getResponderId()
  {
    return new RespID(this.data.getResponderID());
  }
  
  public X509Extensions getResponseExtensions()
  {
    return X509Extensions.getInstance(this.data.getResponseExtensions());
  }
  
  public SingleResp[] getResponses()
  {
    ASN1Sequence localASN1Sequence = this.data.getResponses();
    SingleResp[] arrayOfSingleResp = new SingleResp[localASN1Sequence.size()];
    for (int i = 0; i != arrayOfSingleResp.length; i++) {
      arrayOfSingleResp[i] = new SingleResp(SingleResponse.getInstance(localASN1Sequence.getObjectAt(i)));
    }
    return arrayOfSingleResp;
  }
  
  public int getVersion()
  {
    return 1 + this.data.getVersion().getValue().intValue();
  }
  
  public boolean hasUnsupportedCriticalExtension()
  {
    Set localSet = getCriticalExtensionOIDs();
    return (localSet != null) && (!localSet.isEmpty());
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.ocsp.RespData
 * JD-Core Version:    0.7.0.1
 */