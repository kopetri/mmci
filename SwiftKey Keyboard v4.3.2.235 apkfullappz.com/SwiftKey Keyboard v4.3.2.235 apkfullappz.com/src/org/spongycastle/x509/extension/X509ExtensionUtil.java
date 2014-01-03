package org.spongycastle.x509.extension;

import java.io.IOException;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1String;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x500.X500Name;
import org.spongycastle.asn1.x509.GeneralName;
import org.spongycastle.asn1.x509.X509Extension;

public class X509ExtensionUtil
{
  public static ASN1Primitive fromExtensionValue(byte[] paramArrayOfByte)
    throws IOException
  {
    return ASN1Primitive.fromByteArray(((ASN1OctetString)ASN1Primitive.fromByteArray(paramArrayOfByte)).getOctets());
  }
  
  private static Collection getAlternativeNames(byte[] paramArrayOfByte)
    throws CertificateParsingException
  {
    if (paramArrayOfByte == null) {
      return Collections.EMPTY_LIST;
    }
    ArrayList localArrayList1;
    GeneralName localGeneralName;
    ArrayList localArrayList2;
    try
    {
      localArrayList1 = new ArrayList();
      Enumeration localEnumeration = DERSequence.getInstance(fromExtensionValue(paramArrayOfByte)).getObjects();
      if (!localEnumeration.hasMoreElements()) {
        break label293;
      }
      localGeneralName = GeneralName.getInstance(localEnumeration.nextElement());
      localArrayList2 = new ArrayList();
      localArrayList2.add(new Integer(localGeneralName.getTagNo()));
      switch (localGeneralName.getTagNo())
      {
      default: 
        throw new IOException("Bad tag number: " + localGeneralName.getTagNo());
      }
    }
    catch (Exception localException)
    {
      throw new CertificateParsingException(localException.getMessage());
    }
    localArrayList2.add(localGeneralName.getName().toASN1Primitive());
    for (;;)
    {
      localArrayList1.add(localArrayList2);
      break;
      localArrayList2.add(X500Name.getInstance(localGeneralName.getName()).toString());
      continue;
      localArrayList2.add(((ASN1String)localGeneralName.getName()).getString());
      continue;
      localArrayList2.add(ASN1ObjectIdentifier.getInstance(localGeneralName.getName()).getId());
      continue;
      localArrayList2.add(DEROctetString.getInstance(localGeneralName.getName()).getOctets());
    }
    label293:
    Collection localCollection = Collections.unmodifiableCollection(localArrayList1);
    return localCollection;
  }
  
  public static Collection getIssuerAlternativeNames(X509Certificate paramX509Certificate)
    throws CertificateParsingException
  {
    return getAlternativeNames(paramX509Certificate.getExtensionValue(X509Extension.issuerAlternativeName.getId()));
  }
  
  public static Collection getSubjectAlternativeNames(X509Certificate paramX509Certificate)
    throws CertificateParsingException
  {
    return getAlternativeNames(paramX509Certificate.getExtensionValue(X509Extension.subjectAlternativeName.getId()));
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.x509.extension.X509ExtensionUtil
 * JD-Core Version:    0.7.0.1
 */