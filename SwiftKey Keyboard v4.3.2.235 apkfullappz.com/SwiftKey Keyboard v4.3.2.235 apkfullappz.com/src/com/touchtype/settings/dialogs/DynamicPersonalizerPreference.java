package com.touchtype.settings.dialogs;

import android.content.Context;
import android.preference.PreferenceActivity;
import android.widget.Toast;
import com.touchtype.util.NetworkUtil;
import com.touchtype_fluency.service.personalize.DynamicPersonalizerModel;
import com.touchtype_fluency.service.personalize.Personalizer;
import com.touchtype_fluency.service.personalize.ServiceConfiguration;

public class DynamicPersonalizerPreference
  extends PersonalizerPreference
{
  private final DynamicPersonalizerModel mDynamicPersonalizerData;
  
  public DynamicPersonalizerPreference(Context paramContext, DynamicPersonalizerModel paramDynamicPersonalizerModel)
  {
    super(paramContext, null);
    this.mDynamicPersonalizerData = paramDynamicPersonalizerModel;
    setService(this.mDynamicPersonalizerData.getService());
    setLayoutResource(2130903096);
    String str = this.mDynamicPersonalizerData.getAccountName();
    if (str != null) {}
    for (;;)
    {
      setTitle(str);
      return;
      str = this.mDynamicPersonalizerData.getService().getName();
    }
  }
  
  public DynamicPersonalizerModel getDynamicPersonalizer()
  {
    return this.mDynamicPersonalizerData;
  }
  
  public void launchPersonalizer()
  {
    ServiceConfiguration localServiceConfiguration = getService();
    Personalizer localPersonalizer;
    if ((NetworkUtil.isInternetAvailable(getContext())) || (localServiceConfiguration == ServiceConfiguration.SMS))
    {
      localPersonalizer = new Personalizer(getContext(), localServiceConfiguration, getPersonalizerCallback());
      localPersonalizer.setFromInstaller(isFromInstaller());
      localPersonalizer.startPersonalization((PreferenceActivity)getContext(), this.mDynamicPersonalizerData);
    }
    for (;;)
    {
      setPersonalizer(localPersonalizer);
      return;
      Toast.makeText(getContext(), 2131297208, 1).show();
      localPersonalizer = null;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.settings.dialogs.DynamicPersonalizerPreference
 * JD-Core Version:    0.7.0.1
 */