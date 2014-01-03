package org.spongycastle.cert.selector;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.spongycastle.asn1.x509.GeneralName;
import org.spongycastle.cert.AttributeCertificateHolder;
import org.spongycastle.cert.AttributeCertificateIssuer;
import org.spongycastle.cert.X509AttributeCertificateHolder;

public class X509AttributeCertificateHolderSelectorBuilder
{
  private X509AttributeCertificateHolder attributeCert;
  private Date attributeCertificateValid;
  private AttributeCertificateHolder holder;
  private AttributeCertificateIssuer issuer;
  private BigInteger serialNumber;
  private Collection targetGroups = new HashSet();
  private Collection targetNames = new HashSet();
  
  private Set extractGeneralNames(Collection paramCollection)
    throws IOException
  {
    HashSet localHashSet;
    if ((paramCollection == null) || (paramCollection.isEmpty())) {
      localHashSet = new HashSet();
    }
    for (;;)
    {
      return localHashSet;
      localHashSet = new HashSet();
      Iterator localIterator = paramCollection.iterator();
      while (localIterator.hasNext()) {
        localHashSet.add(GeneralName.getInstance(localIterator.next()));
      }
    }
  }
  
  public void addTargetGroup(GeneralName paramGeneralName)
  {
    this.targetGroups.add(paramGeneralName);
  }
  
  public void addTargetName(GeneralName paramGeneralName)
  {
    this.targetNames.add(paramGeneralName);
  }
  
  public X509AttributeCertificateHolderSelector build()
  {
    return new X509AttributeCertificateHolderSelector(this.holder, this.issuer, this.serialNumber, this.attributeCertificateValid, this.attributeCert, Collections.unmodifiableCollection(new HashSet(this.targetNames)), Collections.unmodifiableCollection(new HashSet(this.targetGroups)));
  }
  
  public void setAttributeCert(X509AttributeCertificateHolder paramX509AttributeCertificateHolder)
  {
    this.attributeCert = paramX509AttributeCertificateHolder;
  }
  
  public void setAttributeCertificateValid(Date paramDate)
  {
    if (paramDate != null)
    {
      this.attributeCertificateValid = new Date(paramDate.getTime());
      return;
    }
    this.attributeCertificateValid = null;
  }
  
  public void setHolder(AttributeCertificateHolder paramAttributeCertificateHolder)
  {
    this.holder = paramAttributeCertificateHolder;
  }
  
  public void setIssuer(AttributeCertificateIssuer paramAttributeCertificateIssuer)
  {
    this.issuer = paramAttributeCertificateIssuer;
  }
  
  public void setSerialNumber(BigInteger paramBigInteger)
  {
    this.serialNumber = paramBigInteger;
  }
  
  public void setTargetGroups(Collection paramCollection)
    throws IOException
  {
    this.targetGroups = extractGeneralNames(paramCollection);
  }
  
  public void setTargetNames(Collection paramCollection)
    throws IOException
  {
    this.targetNames = extractGeneralNames(paramCollection);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cert.selector.X509AttributeCertificateHolderSelectorBuilder
 * JD-Core Version:    0.7.0.1
 */