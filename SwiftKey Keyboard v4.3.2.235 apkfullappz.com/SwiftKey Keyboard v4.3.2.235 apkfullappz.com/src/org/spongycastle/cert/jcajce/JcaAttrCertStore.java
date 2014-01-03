package org.spongycastle.cert.jcajce;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.spongycastle.util.CollectionStore;
import org.spongycastle.x509.X509AttributeCertificate;

public class JcaAttrCertStore
  extends CollectionStore
{
  public JcaAttrCertStore(Collection paramCollection)
    throws IOException
  {
    super(convertCerts(paramCollection));
  }
  
  public JcaAttrCertStore(X509AttributeCertificate paramX509AttributeCertificate)
    throws IOException
  {
    this(Collections.singletonList(paramX509AttributeCertificate));
  }
  
  private static Collection convertCerts(Collection paramCollection)
    throws IOException
  {
    ArrayList localArrayList = new ArrayList(paramCollection.size());
    Iterator localIterator = paramCollection.iterator();
    while (localIterator.hasNext())
    {
      Object localObject = localIterator.next();
      if ((localObject instanceof X509AttributeCertificate)) {
        localArrayList.add(new JcaX509AttributeCertificateHolder((X509AttributeCertificate)localObject));
      } else {
        localArrayList.add(localObject);
      }
    }
    return localArrayList;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cert.jcajce.JcaAttrCertStore
 * JD-Core Version:    0.7.0.1
 */