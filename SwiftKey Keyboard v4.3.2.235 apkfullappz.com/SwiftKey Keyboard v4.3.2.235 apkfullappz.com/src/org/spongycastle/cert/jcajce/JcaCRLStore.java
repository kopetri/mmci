package org.spongycastle.cert.jcajce;

import java.io.IOException;
import java.security.cert.CRLException;
import java.security.cert.X509CRL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.spongycastle.cert.X509CRLHolder;
import org.spongycastle.util.CollectionStore;

public class JcaCRLStore
  extends CollectionStore
{
  public JcaCRLStore(Collection paramCollection)
    throws CRLException
  {
    super(convertCRLs(paramCollection));
  }
  
  private static Collection convertCRLs(Collection paramCollection)
    throws CRLException
  {
    ArrayList localArrayList = new ArrayList(paramCollection.size());
    Iterator localIterator = paramCollection.iterator();
    while (localIterator.hasNext())
    {
      Object localObject = localIterator.next();
      if ((localObject instanceof X509CRL)) {
        try
        {
          localArrayList.add(new X509CRLHolder(((X509CRL)localObject).getEncoded()));
        }
        catch (IOException localIOException)
        {
          throw new CRLException("cannot read encoding: " + localIOException.getMessage());
        }
      } else {
        localArrayList.add((X509CRLHolder)localObject);
      }
    }
    return localArrayList;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cert.jcajce.JcaCRLStore
 * JD-Core Version:    0.7.0.1
 */