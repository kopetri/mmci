package com.touchtype_fluency.service.personalize;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PersonalizationUtils
{
  protected static String getPersonalizationDialogTitle(Resources paramResources)
  {
    if (paramResources.getBoolean(2131492938))
    {
      String str = paramResources.getString(2131296938);
      if (str.equals("")) {
        return paramResources.getString(2131296927);
      }
      return paramResources.getString(2131296927) + " - " + str;
    }
    return paramResources.getString(2131296927) + " - " + paramResources.getString(2131296302);
  }
  
  protected static List<String> getServiceRegisteredAccounts(Context paramContext, ServiceConfiguration paramServiceConfiguration)
  {
    ArrayList localArrayList = new ArrayList();
    SharedPreferences localSharedPreferences = paramContext.getSharedPreferences("DynamicPersonalizers", 0);
    Iterator localIterator = localSharedPreferences.getAll().keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      if (DynamicPersonalizerModel.isDynamicPersonalizerKey(str))
      {
        DynamicPersonalizerModel localDynamicPersonalizerModel = new DynamicPersonalizerModel(localSharedPreferences.getString(str, null), null, null);
        if (localDynamicPersonalizerModel.getService().equals(paramServiceConfiguration)) {
          localArrayList.add(localDynamicPersonalizerModel.getAccountName());
        }
      }
    }
    return localArrayList;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.personalize.PersonalizationUtils
 * JD-Core Version:    0.7.0.1
 */