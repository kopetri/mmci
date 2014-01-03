package org.spongycastle.cms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SignerInformationStore
{
  private ArrayList all = new ArrayList();
  private Map table = new HashMap();
  
  public SignerInformationStore(Collection paramCollection)
  {
    Iterator localIterator = paramCollection.iterator();
    while (localIterator.hasNext())
    {
      SignerInformation localSignerInformation = (SignerInformation)localIterator.next();
      SignerId localSignerId = localSignerInformation.getSID();
      ArrayList localArrayList = (ArrayList)this.table.get(localSignerId);
      if (localArrayList == null)
      {
        localArrayList = new ArrayList(1);
        this.table.put(localSignerId, localArrayList);
      }
      localArrayList.add(localSignerInformation);
    }
    this.all = new ArrayList(paramCollection);
  }
  
  public SignerInformation get(SignerId paramSignerId)
  {
    Collection localCollection = getSigners(paramSignerId);
    if (localCollection.size() == 0) {
      return null;
    }
    return (SignerInformation)localCollection.iterator().next();
  }
  
  public Collection getSigners()
  {
    return new ArrayList(this.all);
  }
  
  public Collection getSigners(SignerId paramSignerId)
  {
    if ((paramSignerId.getIssuer() != null) && (paramSignerId.getSubjectKeyIdentifier() != null))
    {
      ArrayList localArrayList2 = new ArrayList();
      Collection localCollection1 = getSigners(new SignerId(paramSignerId.getIssuer(), paramSignerId.getSerialNumber()));
      if (localCollection1 != null) {
        localArrayList2.addAll(localCollection1);
      }
      Collection localCollection2 = getSigners(new SignerId(paramSignerId.getSubjectKeyIdentifier()));
      if (localCollection2 != null) {
        localArrayList2.addAll(localCollection2);
      }
      return localArrayList2;
    }
    ArrayList localArrayList1 = (ArrayList)this.table.get(paramSignerId);
    if (localArrayList1 == null) {
      return new ArrayList();
    }
    return new ArrayList(localArrayList1);
  }
  
  public int size()
  {
    return this.all.size();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.SignerInformationStore
 * JD-Core Version:    0.7.0.1
 */