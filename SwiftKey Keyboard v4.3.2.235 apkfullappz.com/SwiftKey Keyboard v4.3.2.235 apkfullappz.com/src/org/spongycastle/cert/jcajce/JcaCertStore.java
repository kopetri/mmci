package org.spongycastle.cert.jcajce;

import java.io.IOException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.spongycastle.cert.X509CertificateHolder;
import org.spongycastle.util.CollectionStore;

public class JcaCertStore
  extends CollectionStore
{
  public JcaCertStore(Collection paramCollection)
    throws CertificateEncodingException
  {
    super(convertCerts(paramCollection));
  }
  
  private static Collection convertCerts(Collection paramCollection)
    throws CertificateEncodingException
  {
    ArrayList localArrayList = new ArrayList(paramCollection.size());
    Iterator localIterator = paramCollection.iterator();
    while (localIterator.hasNext())
    {
      Object localObject = localIterator.next();
      if ((localObject instanceof X509Certificate))
      {
        X509Certificate localX509Certificate = (X509Certificate)localObject;
        try
        {
          localArrayList.add(new X509CertificateHolder(localX509Certificate.getEncoded()));
        }
        catch (IOException localIOException)
        {
          throw new CertificateEncodingException("unable to read encoding: " + localIOException.getMessage());
        }
      }
      else
      {
        localArrayList.add((X509CertificateHolder)localObject);
      }
    }
    return localArrayList;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cert.jcajce.JcaCertStore
 * JD-Core Version:    0.7.0.1
 */