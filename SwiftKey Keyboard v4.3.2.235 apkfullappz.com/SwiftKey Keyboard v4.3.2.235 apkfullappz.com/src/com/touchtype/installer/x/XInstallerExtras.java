package com.touchtype.installer.x;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.touchtype.Launcher;
import com.touchtype.preferences.TouchTypePreferences;
import com.touchtype.referral.ReferralSource;
import com.touchtype.report.TouchTypeStats;
import com.touchtype.resources.ProductConfiguration;
import com.touchtype.settings.UsageStatsPreferenceConfiguration;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class XInstallerExtras
  extends Activity
{
  private Button mLeft;
  private int mPackageNumber = 0;
  private List<String> mPreviousPackages;
  private Button mRight;
  private LinearLayout mRootView;
  private XInstallerExtras thisActivity;
  
  private View buildExpiry(LayoutInflater paramLayoutInflater)
  {
    long l1 = new Date().getTime();
    long l2 = ((Date)getIntent().getExtras().get("license_expiry")).getTime();
    final boolean bool1;
    int i;
    label50:
    View localView;
    label109:
    TouchTypeStats localTouchTypeStats;
    String str2;
    TextView localTextView1;
    String str7;
    if (l1 > l2)
    {
      bool1 = true;
      if (!bool1) {
        break label399;
      }
      i = 2130903082;
      localView = paramLayoutInflater.inflate(i, null);
      this.mLeft.setText(2131296436);
      final boolean bool2 = getResources().getBoolean(2131492916);
      final boolean bool3 = getResources().getBoolean(2131492917);
      if ((!bool2) || (bool3)) {
        break label406;
      }
      this.mRight.setText(2131296890);
      Button localButton = this.mRight;
      View.OnClickListener local5 = new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          XInstallerExtras localXInstallerExtras;
          ReferralSource localReferralSource;
          if (bool2) {
            if (bool3)
            {
              localXInstallerExtras = XInstallerExtras.this.thisActivity;
              localReferralSource = ReferralSource.BETA_EXPIRED;
            }
          }
          for (;;)
          {
            Launcher.launchAndroidMarket(localXInstallerExtras, localReferralSource);
            do
            {
              for (;;)
              {
                XInstallerExtras.this.setResult(-1);
                XInstallerExtras.this.finish();
                return;
                XInstallerExtras.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(XInstallerExtras.this.getResources().getString(2131296924))));
              }
            } while (TextUtils.isEmpty(XInstallerExtras.this.getString(2131296891)));
            localXInstallerExtras = XInstallerExtras.this.thisActivity;
            if (bool1) {
              localReferralSource = ReferralSource.TRIAL_EXPIRED;
            } else {
              localReferralSource = ReferralSource.EXPIRY_WARNING;
            }
          }
        }
      };
      localButton.setOnClickListener(local5);
      localTouchTypeStats = TouchTypePreferences.getInstance(this).getTouchTypeStats();
      if (bool1) {
        break label418;
      }
      String str4 = getIntent().getStringExtra("expiry_message_title");
      if (str4 != null) {
        ((TextView)localView.findViewById(2131230858)).setText(str4);
      }
      String str5 = DateFormat.getDateInstance().format(Long.valueOf(l2));
      String str6 = getString(2131296908);
      Object[] arrayOfObject2 = new Object[4];
      arrayOfObject2[0] = str5;
      arrayOfObject2[1] = Integer.valueOf(UsageStatsPreferenceConfiguration.efficiency(localTouchTypeStats));
      arrayOfObject2[2] = Integer.valueOf(localTouchTypeStats.getKeyStrokesSaved());
      arrayOfObject2[3] = Integer.valueOf(localTouchTypeStats.getStatisticInt("stats_chars_corrected"));
      str2 = String.format(str6, arrayOfObject2);
      localTextView1 = (TextView)localView.findViewById(2131230859);
      str7 = getResources().getString(2131296909);
    }
    label399:
    label406:
    label418:
    String str3;
    for (SpannableStringBuilder localSpannableStringBuilder = new SpannableStringBuilder(str7);; localSpannableStringBuilder = new SpannableStringBuilder(str3))
    {
      localTextView1.setText(str2);
      if (!getResources().getBoolean(2131492916))
      {
        int j = localSpannableStringBuilder.toString().indexOf("www.swiftkey.net");
        if (j >= 0)
        {
          ClickableSpan local6 = new ClickableSpan()
          {
            public void onClick(View paramAnonymousView)
            {
              Intent localIntent = new Intent("android.intent.action.VIEW");
              localIntent.setData(Uri.parse("http://www.swiftkey.net"));
              localIntent.addFlags(268435456);
              XInstallerExtras.this.startActivity(localIntent);
            }
          };
          int k = j + 16;
          localSpannableStringBuilder.setSpan(local6, j, k, 33);
          TextView localTextView2 = (TextView)localView.findViewById(2131230860);
          localTextView2.setText(localSpannableStringBuilder);
          localTextView2.setMovementMethod(LinkMovementMethod.getInstance());
        }
      }
      return localView;
      bool1 = false;
      break;
      i = 2130903081;
      break label50;
      this.mRight.setText(2131296884);
      break label109;
      String str1 = getString(2131296897);
      Object[] arrayOfObject1 = new Object[3];
      arrayOfObject1[0] = Integer.valueOf(UsageStatsPreferenceConfiguration.efficiency(localTouchTypeStats));
      arrayOfObject1[1] = Integer.valueOf(localTouchTypeStats.getKeyStrokesSaved());
      arrayOfObject1[2] = Integer.valueOf(localTouchTypeStats.getStatisticInt("stats_chars_corrected"));
      str2 = String.format(str1, arrayOfObject1);
      localTextView1 = (TextView)localView.findViewById(2131230861);
      str3 = getResources().getString(2131296898);
    }
  }
  
  private View buildUpgradeTrial(LayoutInflater paramLayoutInflater)
  {
    this.mPreviousPackages = ProductConfiguration.buildAvailablePackageList(getApplicationContext());
    this.mLeft.setText(2131296435);
    this.mRight.setText(2131296437);
    this.mRight.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        XInstallerExtras.this.removeNextPackage();
      }
    });
    View localView = paramLayoutInflater.inflate(2130903089, null);
    if (this.mPreviousPackages.size() > 1)
    {
      ((TextView)localView.findViewById(2131230866)).setText(2131296394);
      ((TextView)localView.findViewById(2131230875)).setText(2131296396);
    }
    return localView;
  }
  
  private View buildVoiceRecognition(LayoutInflater paramLayoutInflater)
  {
    this.mLeft.setText(2131296436);
    this.mRight.setText(2131296438);
    this.mRight.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        Launcher.launchAndroidMarketVoiceSearch(XInstallerExtras.this.thisActivity);
        XInstallerExtras.this.setResult(-1);
        XInstallerExtras.this.finish();
      }
    });
    return paramLayoutInflater.inflate(2130903090, null);
  }
  
  private boolean removeNextPackage()
  {
    List localList = this.mPreviousPackages;
    boolean bool = false;
    if (localList != null)
    {
      int i = this.mPreviousPackages.size();
      bool = false;
      if (i > 0)
      {
        int j = this.mPackageNumber;
        int k = this.mPreviousPackages.size();
        bool = false;
        if (j < k)
        {
          String str = (String)this.mPreviousPackages.get(this.mPackageNumber);
          this.mPackageNumber = (1 + this.mPackageNumber);
          startActivityForResult(new Intent("android.intent.action.DELETE", Uri.parse("package:" + str)), 0);
          bool = true;
        }
      }
    }
    return bool;
  }
  
  private void setContent(Intent paramIntent)
  {
    if (paramIntent == null) {}
    LayoutInflater localLayoutInflater;
    do
    {
      String str;
      do
      {
        return;
        str = paramIntent.getAction();
        localLayoutInflater = (LayoutInflater)getApplicationContext().getSystemService("layout_inflater");
        this.mLeft.setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramAnonymousView)
          {
            XInstallerExtras.this.setResult(0);
            XInstallerExtras.this.finish();
          }
        });
        View.OnFocusChangeListener local2 = new View.OnFocusChangeListener()
        {
          public void onFocusChange(View paramAnonymousView, boolean paramAnonymousBoolean)
          {
            if (paramAnonymousBoolean)
            {
              paramAnonymousView.setBackgroundResource(2130838073);
              return;
            }
            paramAnonymousView.setBackgroundResource(2130838072);
          }
        };
        this.mLeft.setOnFocusChangeListener(local2);
        this.mRight.setOnFocusChangeListener(local2);
        if (str == null) {
          break;
        }
        if (str.equals("com.touchtype.installer.LAUNCH_VOICE_RECOGNIZER"))
        {
          this.mRootView.addView(buildVoiceRecognition(localLayoutInflater));
          return;
        }
        if ((str.equals("com.touchtype.installer.LAUNCH_EXPIRED")) || (str.equals("com.touchtype.installer.LAUNCH_INVALID_LICENSE")))
        {
          this.mRootView.addView(buildExpiry(localLayoutInflater));
          return;
        }
      } while ((!str.equals("com.touchtype.installer.LAUNCH_UPGRADE")) && (!paramIntent.toString().contains(getApplicationContext().getString(2131296683))));
      this.mRootView.addView(buildUpgradeTrial(localLayoutInflater));
      return;
    } while (!paramIntent.getComponent().getClassName().contains(getApplicationContext().getString(2131296683)));
    this.mRootView.addView(buildUpgradeTrial(localLayoutInflater));
  }
  
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    switch (paramInt1)
    {
    }
    do
    {
      return;
    } while (removeNextPackage());
    setResult(-1);
    finish();
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.thisActivity = this;
    requestWindowFeature(1);
    setContentView(2130903128);
    this.mRootView = ((LinearLayout)findViewById(2131230976));
    this.mLeft = ((Button)findViewById(2131230977));
    this.mRight = ((Button)findViewById(2131230978));
    setContent(getIntent());
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.installer.x.XInstallerExtras
 * JD-Core Version:    0.7.0.1
 */