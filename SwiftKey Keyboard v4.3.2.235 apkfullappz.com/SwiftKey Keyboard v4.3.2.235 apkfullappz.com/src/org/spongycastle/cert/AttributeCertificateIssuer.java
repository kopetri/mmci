package org.spongycastle.cert;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.x500.X500Name;
import org.spongycastle.asn1.x509.AttCertIssuer;
import org.spongycastle.asn1.x509.GeneralName;
import org.spongycastle.asn1.x509.GeneralNames;
import org.spongycastle.asn1.x509.IssuerSerial;
import org.spongycastle.asn1.x509.V2Form;
import org.spongycastle.util.Selector;

public class AttributeCertificateIssuer
  implements Selector
{
  final ASN1Encodable form;
  
  public AttributeCertificateIssuer(X500Name paramX500Name)
  {
    this.form = new V2Form(new GeneralNames(new GeneralName(paramX500Name)));
  }
  
  public AttributeCertificateIssuer(AttCertIssuer paramAttCertIssuer)
  {
    this.form = paramAttCertIssuer.getIssuer();
  }
  
  private boolean matchesDN(X500Name paramX500Name, GeneralNames paramGeneralNames)
  {
    GeneralName[] arrayOfGeneralName = paramGeneralNames.getNames();
    for (int i = 0; i != arrayOfGeneralName.length; i++)
    {
      GeneralName localGeneralName = arrayOfGeneralName[i];
      if ((localGeneralName.getTagNo() == 4) && (X500Name.getInstance(localGeneralName.getName()).equals(paramX500Name))) {
        return true;
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
  
  public X500Name[] getNames()
  {
    if ((this.form instanceof V2Form)) {}
    ArrayList localArrayList;
    for (GeneralNames localGeneralNames = ((V2Form)this.form).getIssuerName();; localGeneralNames = (GeneralNames)this.form)
    {
      GeneralName[] arrayOfGeneralName = localGeneralNames.getNames();
      localArrayList = new ArrayList(arrayOfGeneralName.length);
      for (int i = 0; i != arrayOfGeneralName.length; i++) {
        if (arrayOfGeneralName[i].getTagNo() == 4) {
          localArrayList.add(X500Name.getInstance(arrayOfGeneralName[i].getName()));
        }
      }
    }
    return (X500Name[])localArrayList.toArray(new X500Name[localArrayList.size()]);
  }
  
  public int hashCode()
  {
    return this.form.hashCode();
  }
  
  public boolean match(Object paramObject)
  {
    if (!(paramObject instanceof X509CertificateHolder)) {}
    X509CertificateHolder localX509CertificateHolder;
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
          localX509CertificateHolder = (X509CertificateHolder)paramObject;
          if (!(this.form instanceof V2Form)) {
            break label105;
          }
          localV2Form = (V2Form)this.form;
          if (localV2Form.getBaseCertificateID() == null) {
            break;
          }
        } while ((!localV2Form.getBaseCertificateID().getSerial().getValue().equals(localX509CertificateHolder.getSerialNumber())) || (!matchesDN(localX509CertificateHolder.getIssuer(), localV2Form.getBaseCertificateID().getIssuer())));
        return true;
        localGeneralNames2 = localV2Form.getIssuerName();
      } while (!matchesDN(localX509CertificateHolder.getSubject(), localGeneralNames2));
      return true;
      localGeneralNames1 = (GeneralNames)this.form;
    } while (!matchesDN(localX509CertificateHolder.getSubject(), localGeneralNames1));
    return true;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cert.AttributeCertificateIssuer
 * JD-Core Version:    0.7.0.1
 */