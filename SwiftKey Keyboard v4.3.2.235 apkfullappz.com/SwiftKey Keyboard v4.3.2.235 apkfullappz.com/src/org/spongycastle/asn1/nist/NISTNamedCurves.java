package org.spongycastle.asn1.nist;

import java.util.Enumeration;
import java.util.Hashtable;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.sec.SECNamedCurves;
import org.spongycastle.asn1.sec.SECObjectIdentifiers;
import org.spongycastle.asn1.x9.X9ECParameters;
import org.spongycastle.util.Strings;

public class NISTNamedCurves
{
  static final Hashtable names;
  static final Hashtable objIds = new Hashtable();
  
  static
  {
    names = new Hashtable();
    defineCurve("B-571", SECObjectIdentifiers.sect571r1);
    defineCurve("B-409", SECObjectIdentifiers.sect409r1);
    defineCurve("B-283", SECObjectIdentifiers.sect283r1);
    defineCurve("B-233", SECObjectIdentifiers.sect233r1);
    defineCurve("B-163", SECObjectIdentifiers.sect163r2);
    defineCurve("P-521", SECObjectIdentifiers.secp521r1);
    defineCurve("P-384", SECObjectIdentifiers.secp384r1);
    defineCurve("P-256", SECObjectIdentifiers.secp256r1);
    defineCurve("P-224", SECObjectIdentifiers.secp224r1);
    defineCurve("P-192", SECObjectIdentifiers.secp192r1);
  }
  
  static void defineCurve(String paramString, ASN1ObjectIdentifier paramASN1ObjectIdentifier)
  {
    objIds.put(paramString, paramASN1ObjectIdentifier);
    names.put(paramASN1ObjectIdentifier, paramString);
  }
  
  public static X9ECParameters getByName(String paramString)
  {
    ASN1ObjectIdentifier localASN1ObjectIdentifier = (ASN1ObjectIdentifier)objIds.get(Strings.toUpperCase(paramString));
    if (localASN1ObjectIdentifier != null) {
      return getByOID(localASN1ObjectIdentifier);
    }
    return null;
  }
  
  public static X9ECParameters getByOID(ASN1ObjectIdentifier paramASN1ObjectIdentifier)
  {
    return SECNamedCurves.getByOID(paramASN1ObjectIdentifier);
  }
  
  public static String getName(ASN1ObjectIdentifier paramASN1ObjectIdentifier)
  {
    return (String)names.get(paramASN1ObjectIdentifier);
  }
  
  public static Enumeration getNames()
  {
    return objIds.keys();
  }
  
  public static ASN1ObjectIdentifier getOID(String paramString)
  {
    return (ASN1ObjectIdentifier)objIds.get(Strings.toUpperCase(paramString));
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.nist.NISTNamedCurves
 * JD-Core Version:    0.7.0.1
 */