package com.touchtype.settings.custompreferences;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.touchtype.preferences.PrioritisedChooserActivity;
import com.touchtype.util.LogUtil;

public class UsageStatPreference
  extends Preference
{
  private static final String TAG = UsageStatPreference.class.getSimpleName();
  private String titleValue;
  
  public UsageStatPreference(Context paramContext)
  {
    super(paramContext);
  }
  
  public UsageStatPreference(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public UsageStatPreference(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  protected void onBindView(View paramView)
  {
    super.onBindView(paramView);
    TextView localTextView = (TextView)paramView.findViewById(2131230905);
    if (localTextView != null) {
      localTextView.setText(this.titleValue);
    }
  }
  
  protected void onClick()
  {
    Context localContext = getContext();
    Intent localIntent;
    if (localContext.getResources().getBoolean(2131492914))
    {
      localIntent = new Intent("android.intent.action.SEND");
      localIntent.setType("text/plain");
      localIntent.putExtra("android.intent.extra.TEXT", getSummary() + " " + localContext.getString(2131297073));
      localIntent.putExtra("android.intent.extra.SUBJECT", localContext.getString(2131297072));
    }
    try
    {
      localContext.startActivity(PrioritisedChooserActivity.createChooser(localContext, localIntent, localContext.getString(2131297071)));
      return;
    }
    catch (ActivityNotFoundException localActivityNotFoundException)
    {
      LogUtil.e(TAG, "Failed to create ACTION_SEND chooser activity", localActivityNotFoundException);
      Toast.makeText(localContext, 2131297076, 1).show();
    }
  }
  
  public void setTitleValue(String paramString)
  {
    this.titleValue = paramString;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.settings.custompreferences.UsageStatPreference
 * JD-Core Version:    0.7.0.1
 */