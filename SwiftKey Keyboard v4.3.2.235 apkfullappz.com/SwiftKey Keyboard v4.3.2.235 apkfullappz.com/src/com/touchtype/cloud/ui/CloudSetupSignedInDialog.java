package com.touchtype.cloud.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import com.touchtype.cloud.CloudSetupActivity;

public final class CloudSetupSignedInDialog
  extends CloudSetupDialogFragment
{
  private CloudSetupFragmentCallbacks.CloudSetupSignedInCallback callback;
  
  private View.OnClickListener createNoOnClickListener()
  {
    new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        CloudSetupSignedInDialog.this.callback.onPressedNo();
      }
    };
  }
  
  private View.OnClickListener createOkOnClickListener()
  {
    new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        CloudSetupSignedInDialog.this.callback.onPressedOk();
      }
    };
  }
  
  private View.OnClickListener createYesOnClickListener()
  {
    new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        CloudSetupSignedInDialog.this.callback.onPressedYes();
      }
    };
  }
  
  private String getWelcomeMessage(int paramInt1, int paramInt2)
  {
    if (paramInt1 == 0) {
      return getString(2131297246);
    }
    if (paramInt2 > 1)
    {
      String str = getString(2131297248);
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(paramInt2);
      return String.format(str, arrayOfObject);
    }
    return getString(2131297247);
  }
  
  public static CloudSetupSignedInDialog newInstance(int paramInt1, String paramString, int paramInt2)
  {
    CloudSetupSignedInDialog localCloudSetupSignedInDialog = new CloudSetupSignedInDialog();
    Bundle localBundle = new Bundle();
    localBundle.putInt("CloudSetupSignedInDialog.DialogStyle", paramInt1);
    localBundle.putString("CloudSetupSignedInDialog.AccountUsername", paramString);
    localBundle.putInt("CloudSetupSignedInDialog.NumDevices", paramInt2);
    localCloudSetupSignedInDialog.setArguments(localBundle);
    return localCloudSetupSignedInDialog;
  }
  
  private void setupDialogHeader(View paramView)
  {
    String str = getArguments().getString("CloudSetupSignedInDialog.AccountUsername");
    ((TextView)paramView.findViewById(2131230786)).setText(str);
  }
  
  private void setupPersonalizationView(View paramView)
  {
    setupDialogHeader(paramView);
    paramView.findViewById(2131230788).setOnClickListener(createYesOnClickListener());
    paramView.findViewById(2131230787).setOnClickListener(createNoOnClickListener());
  }
  
  private void setupWelcomeView(View paramView, int paramInt1, int paramInt2)
  {
    setupDialogHeader(paramView);
    ((TextView)paramView.findViewById(2131230789)).setText(getWelcomeMessage(paramInt1, paramInt2));
    paramView.findViewById(2131230790).setOnClickListener(createOkOnClickListener());
  }
  
  protected void initCallback(CloudSetupActivity paramCloudSetupActivity)
  {
    this.callback = paramCloudSetupActivity.createSignedInDialogCallback();
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    int i = getArguments().getInt("CloudSetupSignedInDialog.DialogStyle");
    if (i == 2)
    {
      View localView2 = paramLayoutInflater.inflate(2130903051, paramViewGroup, false);
      setupPersonalizationView(localView2);
      return localView2;
    }
    View localView1 = paramLayoutInflater.inflate(2130903052, paramViewGroup, false);
    setupWelcomeView(localView1, i, getArguments().getInt("CloudSetupSignedInDialog.NumDevices"));
    return localView1;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.cloud.ui.CloudSetupSignedInDialog
 * JD-Core Version:    0.7.0.1
 */