package com.touchtype.settings.dialogs;

import android.content.Context;
import android.content.res.Resources;
import android.preference.DialogPreference;
import android.preference.PreferenceActivity;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.touchtype.util.NetworkUtil;
import com.touchtype_fluency.service.personalize.Personalizer;
import com.touchtype_fluency.service.personalize.Personalizer.PersonalizerAuthenticationCallback;
import com.touchtype_fluency.service.personalize.ServiceConfiguration;

public class PersonalizerPreference
  extends DialogPreference
{
  private Personalizer.PersonalizerAuthenticationCallback mCallback = null;
  private Context mContext;
  private CharSequence mDefaultSummary;
  private boolean mFromInstaller;
  private Personalizer mPersonalizer = null;
  private ServiceConfiguration mService;
  
  public PersonalizerPreference(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init(paramContext, paramAttributeSet, 0);
  }
  
  public PersonalizerPreference(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    init(paramContext, paramAttributeSet, paramInt);
  }
  
  private void init(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    this.mDefaultSummary = getSummary();
    this.mContext = paramContext;
    setLayoutResource(2130903096);
    String str = getKey();
    if (str != null) {
      this.mService = ServiceConfiguration.getServicesByPref(str);
    }
  }
  
  public Personalizer getPersonalizer()
  {
    if (this.mPersonalizer == null) {
      this.mPersonalizer = new Personalizer(getContext(), this.mService, this.mCallback);
    }
    return this.mPersonalizer;
  }
  
  public Personalizer.PersonalizerAuthenticationCallback getPersonalizerCallback()
  {
    return this.mCallback;
  }
  
  protected ServiceConfiguration getService()
  {
    return this.mService;
  }
  
  public boolean isFromInstaller()
  {
    return this.mFromInstaller;
  }
  
  public void launchPersonalizer()
  {
    if ((NetworkUtil.isInternetAvailable(getContext())) || (this.mService == ServiceConfiguration.SMS))
    {
      this.mPersonalizer = new Personalizer(getContext(), this.mService, this.mCallback);
      this.mPersonalizer.setFromInstaller(this.mFromInstaller);
      this.mPersonalizer.startPersonalization((PreferenceActivity)getContext());
      return;
    }
    Toast.makeText(getContext(), 2131297208, 1).show();
    this.mPersonalizer = null;
  }
  
  protected void onBindView(View paramView)
  {
    super.onBindView(paramView);
    ((ImageView)paramView.findViewById(2131230896)).setImageDrawable(this.mContext.getResources().getDrawable(this.mService.getIconId()));
  }
  
  protected void onClick()
  {
    if ((this.mPersonalizer != null) && (this.mPersonalizer.isActive()))
    {
      String str1 = this.mService.getName();
      String str2 = String.format(getContext().getResources().getString(2131297188), new Object[] { str1 });
      Toast.makeText(getContext(), str2, 1).show();
      return;
    }
    launchPersonalizer();
  }
  
  public void registerCallback(Personalizer.PersonalizerAuthenticationCallback paramPersonalizerAuthenticationCallback)
  {
    this.mCallback = paramPersonalizerAuthenticationCallback;
  }
  
  public void setFromInstaller(boolean paramBoolean)
  {
    this.mFromInstaller = paramBoolean;
  }
  
  protected void setPersonalizer(Personalizer paramPersonalizer)
  {
    this.mPersonalizer = paramPersonalizer;
  }
  
  protected void setService(ServiceConfiguration paramServiceConfiguration)
  {
    this.mService = paramServiceConfiguration;
  }
  
  public void update(String paramString)
  {
    if (paramString != null) {}
    for (;;)
    {
      setSummary(paramString);
      return;
      paramString = this.mDefaultSummary;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.settings.dialogs.PersonalizerPreference
 * JD-Core Version:    0.7.0.1
 */