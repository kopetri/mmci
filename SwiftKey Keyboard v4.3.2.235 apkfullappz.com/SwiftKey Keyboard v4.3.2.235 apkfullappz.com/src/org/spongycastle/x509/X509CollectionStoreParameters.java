package org.spongycastle.x509;

import java.util.ArrayList;
import java.util.Collection;

public class X509CollectionStoreParameters
  implements X509StoreParameters
{
  private Collection collection;
  
  public X509CollectionStoreParameters(Collection paramCollection)
  {
    if (paramCollection == null) {
      throw new NullPointerException("collection cannot be null");
    }
    this.collection = paramCollection;
  }
  
  public Object clone()
  {
    return new X509CollectionStoreParameters(this.collection);
  }
  
  public Collection getCollection()
  {
    return new ArrayList(this.collection);
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("X509CollectionStoreParameters: [\n");
    localStringBuffer.append("  collection: " + this.collection + "\n");
    localStringBuffer.append("]");
    return localStringBuffer.toString();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.x509.X509CollectionStoreParameters
 * JD-Core Version:    0.7.0.1
 */