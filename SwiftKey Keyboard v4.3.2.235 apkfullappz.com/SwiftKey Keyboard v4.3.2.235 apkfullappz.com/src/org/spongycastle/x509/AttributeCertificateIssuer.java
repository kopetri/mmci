package org.spongycastle.x509;

import java.io.IOException;
import java.math.BigInteger;
import java.security.Principal;
import java.security.cert.CertSelector;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import javax.security.auth.x500.X500Principal;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x509.AttCertIssuer;
import org.spongycastle.asn1.x509.GeneralName;
import org.spongycastle.asn1.x509.GeneralNames;
import org.spongycastle.asn1.x509.IssuerSerial;
import org.spongycastle.asn1.x509.V2Form;
import org.spongycastle.jce.X509Principal;
import org.spongycastle.util.Selector;

public class AttributeCertificateIssuer
  implements CertSelector, Selector
{
  final ASN1Encodable form;
  
  public AttributeCertificateIssuer(X500Principal paramX500Principal)
    throws IOException
  {
    this(new X509Principal(paramX500Principal.getEncoded()));
  }
  
  public AttributeCertificateIssuer(AttCertIssuer paramAttCertIssuer)
  {
    this.form = paramAttCertIssuer.getIssuer();
  }
  
  public AttributeCertificateIssuer(X509Principal paramX509Principal)
  {
    this.form = new V2Form(GeneralNames.getInstance(new DERSequence(new GeneralName(paramX509Principal))));
  }
  
  private Object[] getNames()
  {
    if ((this.form instanceof V2Form)) {}
    ArrayList localArrayList;
    for (GeneralNames localGeneralNames = ((V2Form)this.form).getIssuerName();; localGeneralNames = (GeneralNames)this.form)
    {
      GeneralName[] arrayOfGeneralName = localGeneralNames.getNames();
      localArrayList = new ArrayList(arrayOfGeneralName.length);
      int i = 0;
      while (i != arrayOfGeneralName.length)
      {
        if (arrayOfGeneralName[i].getTagNo() == 4) {}
        try
        {
          localArrayList.add(new X500Principal(arrayOfGeneralName[i].getName().toASN1Primitive().getEncoded()));
          i++;
        }
        catch (IOException localIOException)
        {
          throw new RuntimeException("badly formed Name object");
        }
      }
    }
    return localArrayList.toArray(new Object[localArrayList.size()]);
  }
  
  private boolean matchesDN(X500Principal paramX500Principal, GeneralNames paramGeneralNames)
  {
    GeneralName[] arrayOfGeneralName = paramGeneralNames.getNames();
    for (int i = 0; i != arrayOfGeneralName.length; i++)
    {
      GeneralName localGeneralName = arrayOfGeneralName[i];
      if (localGeneralName.getTagNo() == 4) {
        try
        {
          boolean bool = new X500Principal(localGeneralName.getName().toASN1Primitive().getEncoded()).equals(paramX500Principal);
          if (bool) {
            return true;
          }
        }
        catch (IOException localIOException) {}
      }
    }
    return false;
  }
  
  public Object clone()
  {
    return new AttributeCertificateIssuer(AttCertIssuer.getInstance(this.form));
  }
  
  public boolean equals(Object paramObject)
  {
    if (paramObject == this) {
      return true;
    }
    if (!(paramObject instanceof AttributeCertificateIssuer)) {
      return false;
    }
    AttributeCertificateIssuer localAttributeCertificateIssuer = (AttributeCertificateIssuer)paramObject;
    return this.form.equals(localAttributeCertificateIssuer.form);
  }
  
  public Principal[] getPrincipals()
  {
    Object[] arrayOfObject = getNames();
    ArrayList localArrayList = new ArrayList();
    for (int i = 0; i != arrayOfObject.length; i++) {
      if ((arrayOfObject[i] instanceof Principal)) {
        localArrayList.add(arrayOfObject[i]);
      }
    }
    return (Principal[])localArrayList.toArray(new Principal[localArrayList.size()]);
  }
  
  public int hashCode()
  {
    return this.form.hashCode();
  }
  
  public boolean match(Object paramObject)
  {
    if (!(paramObject instanceof X509Certificate)) {
      return false;
    }
    return match((Certificate)paramObject);
  }
  
  public boolean match(Certificate paramCertificate)
  {
    if (!(paramCertificate instanceof X509Certificate)) {}
    X509Certificate localX509Certificate;
    label105:
    GeneralNames localGeneralNames1;
    do
    {
      GeneralNames localGeneralNames2;
      do
      {
        V2Form localV2Form;
        do
        {
          return false;
          localX509Certificate = (X509Certificate)paramCertificate;
          if (!(this.form instanceof V2Form)) {
            break label105;
          }
          localV2Form = (V2Form)this.form;
          if (localV2Form.getBaseCertificateID() == null) {
            break;
          }
        } while ((!localV2Form.getBaseCertificateID().getSerial().getValue().equals(localX509Certificate.getSerialNumber())) || (!matchesDN(localX509Certificate.getIssuerX500Principal(), localV2Form.getBaseCertificateID().getIssuer())));
        return true;
        localGeneralNames2 = localV2Form.getIssuerName();
      } while (!matchesDN(localX509Certificate.getSubjectX500Principal(), localGeneralNames2));
      return true;
      localGeneralNames1 = (GeneralNames)this.form;
    } while (!matchesDN(localX509Certificate.getSubjectX500Principal(), localGeneralNames1));
    return true;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.x509.AttributeCertificateIssuer
 * JD-Core Version:    0.7.0.1
 */