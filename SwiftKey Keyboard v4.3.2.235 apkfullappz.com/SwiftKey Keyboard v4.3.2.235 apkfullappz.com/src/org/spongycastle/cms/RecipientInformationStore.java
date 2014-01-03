package org.spongycastle.cms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class RecipientInformationStore
{
  private final List all;
  private final Map table = new HashMap();
  
  public RecipientInformationStore(Collection paramCollection)
  {
    Iterator localIterator = paramCollection.iterator();
    while (localIterator.hasNext())
    {
      RecipientInformation localRecipientInformation = (RecipientInformation)localIterator.next();
      RecipientId localRecipientId = localRecipientInformation.getRID();
      ArrayList localArrayList = (ArrayList)this.table.get(localRecipientId);
      if (localArrayList == null)
      {
        localArrayList = new ArrayList(1);
        this.table.put(localRecipientId, localArrayList);
      }
      localArrayList.add(localRecipientInformation);
    }
    this.all = new ArrayList(paramCollection);
  }
  
  public RecipientInformation get(RecipientId paramRecipientId)
  {
    Collection localCollection = getRecipients(paramRecipientId);
    if (localCollection.size() == 0) {
      return null;
    }
    return (RecipientInformation)localCollection.iterator().next();
  }
  
  public Collection getRecipients()
  {
    return new ArrayList(this.all);
  }
  
  public Collection getRecipients(RecipientId paramRecipientId)
  {
    if ((paramRecipientId instanceof KeyTransRecipientId))
    {
      KeyTransRecipientId localKeyTransRecipientId = (KeyTransRecipientId)paramRecipientId;
      byte[] arrayOfByte = localKeyTransRecipientId.getSubjectKeyIdentifier();
      if ((localKeyTransRecipientId.getIssuer() != null) && (arrayOfByte != null))
      {
        ArrayList localArrayList3 = new ArrayList();
        Collection localCollection1 = getRecipients(new KeyTransRecipientId(localKeyTransRecipientId.getIssuer(), localKeyTransRecipientId.getSerialNumber()));
        if (localCollection1 != null) {
          localArrayList3.addAll(localCollection1);
        }
        Collection localCollection2 = getRecipients(new KeyTransRecipientId(arrayOfByte));
        if (localCollection2 != null) {
          localArrayList3.addAll(localCollection2);
        }
        return localArrayList3;
      }
      ArrayList localArrayList2 = (ArrayList)this.table.get(paramRecipientId);
      if (localArrayList2 == null) {
        return new ArrayList();
      }
      return new ArrayList(localArrayList2);
    }
    ArrayList localArrayList1 = (ArrayList)this.table.get(paramRecipientId);
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
 * Qualified Name:     org.spongycastle.cms.RecipientInformationStore
 * JD-Core Version:    0.7.0.1
 */