package com.touchtype.settings;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.os.Environment;
import android.os.Handler;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import com.touchtype.settings.custompreferences.LanguagePackPreference;
import com.touchtype.settings.custompreferences.NoLanguagesPreference;
import com.touchtype.settings.custompreferences.PreferenceUpdateLanguagesButton;
import com.touchtype.settings.dialogs.LanguageDialogFragment;
import com.touchtype.settings.dialogs.LanguagePreferenceDialog;
import com.touchtype.social.UserNotificationManager;
import com.touchtype.util.LogUtil;
import com.touchtype_fluency.service.FluencyServiceProxy;
import com.touchtype_fluency.service.languagepacks.LanguagePackManager;
import com.touchtype_fluency.service.languagepacks.languagepackmanager.LanguagePack;
import com.touchtype_fluency.service.languagepacks.languagepackmanager.LanguagePackListener;
import com.touchtype_fluency.service.receiver.SDCardListener;
import com.touchtype_fluency.service.receiver.SDCardReceiver;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class LanguagePreferenceConfiguration
  extends PreferenceWrapper
{
  private static final String TAG = LanguagePreferenceConfiguration.class.getSimpleName();
  private final LanguagePackListener configurationListener = new ManagerLanguagePackListener(this);
  private boolean mActivityRunning = false;
  private Context mApplicationContext;
  private LanguagePreferenceDeleteLangListener mDeleteLanguageListener;
  private final FluencyServiceProxy mFluencyServiceProxy = new FluencyServiceProxy();
  private Handler mHandler;
  private int mLanguagePackCount;
  private LanguagePackManager mLanguagePackManager;
  private boolean mSDCardAvailable = false;
  private SDCardListener mSDCardListener;
  private PreferenceUpdateLanguagesButton mUpdateButton;
  private boolean mUpdatingInProgress = false;
  private UserNotificationManager mUserNotificationManager;
  
  public LanguagePreferenceConfiguration(PreferenceActivity paramPreferenceActivity)
  {
    super(paramPreferenceActivity);
    initialiseFluency();
    addPreference(2131034797);
  }
  
  @TargetApi(11)
  public LanguagePreferenceConfiguration(PreferenceFragment paramPreferenceFragment)
  {
    super(paramPreferenceFragment);
    initialiseFluency();
    this.mApplicationContext = paramPreferenceFragment.getActivity().getApplicationContext();
    addPreference(2131034797);
  }
  
  private Vector<LanguagePack> getDownloadedLanguages()
  {
    Vector localVector1 = new Vector(0);
    if (this.mLanguagePackManager != null) {
      synchronized (this.mLanguagePackManager.getLanguagePacks())
      {
        Iterator localIterator = ???.iterator();
        while (localIterator.hasNext())
        {
          LanguagePack localLanguagePack = (LanguagePack)localIterator.next();
          if (localLanguagePack.isDownloaded()) {
            localVector1.add(localLanguagePack);
          }
        }
      }
    }
    return localVector1;
  }
  
  private void initialiseFluency()
  {
    final Context localContext = getApplicationContext();
    this.mFluencyServiceProxy.onCreate(localContext);
    this.mFluencyServiceProxy.runWhenConnected(new Runnable()
    {
      public void run()
      {
        LanguagePreferenceConfiguration.access$002(LanguagePreferenceConfiguration.this, LanguagePreferenceConfiguration.this.mFluencyServiceProxy.getLanguagePackManager());
        LanguagePreferenceConfiguration.this.mLanguagePackManager.addListener(LanguagePreferenceConfiguration.this.configurationListener);
        if ((LanguagePreferenceConfiguration.this.mLanguagePackManager != null) && (LanguagePreferenceConfiguration.this.mLanguagePackManager.isReady())) {
          LanguagePreferenceConfiguration.this.configurationListener.onChange(localContext);
        }
        if ((LanguagePreferenceConfiguration.this.mActivityRunning) && (LanguagePreferenceConfiguration.this.mLanguagePackManager != null)) {
          LanguagePreferenceConfiguration.this.mLanguagePackManager.startDeferringNotifications();
        }
      }
    });
  }
  
  private void updateLanguageList()
  {
    this.mUpdatingInProgress = true;
    synchronized (this.mLanguagePackManager.getLanguagePacks())
    {
      this.mLanguagePackCount = ???.size();
      this.mLanguagePackManager.downloadConfiguration();
      return;
    }
  }
  
  public void createWidgets()
  {
    if (this.mLanguagePackManager == null) {
      return;
    }
    runOnUiThread(new Runnable()
    {
      public void run()
      {
        LanguagePreferenceConfiguration.this.createWidgetsImpl();
      }
    });
  }
  
  public void createWidgetsImpl()
  {
    int i;
    if ((this.mLanguagePackManager != null) && (this.mLanguagePackManager.isReady()))
    {
      i = 1;
      if (i != 0) {
        break label60;
      }
      this.mHandler = new Handler();
      LanguageManagerReadyPoller localLanguageManagerReadyPoller = new LanguageManagerReadyPoller(this);
      this.mHandler.post(localLanguageManagerReadyPoller);
    }
    label60:
    PreferenceScreen localPreferenceScreen;
    int n;
    label471:
    do
    {
      return;
      i = 0;
      break;
      localPreferenceScreen = getPreferenceScreen();
      for (int j = -1 + localPreferenceScreen.getPreferenceCount(); j >= 0; j--)
      {
        Preference localPreference1 = localPreferenceScreen.getPreference(j);
        if ((localPreference1 instanceof PreferenceCategory))
        {
          PreferenceCategory localPreferenceCategory = (PreferenceCategory)localPreference1;
          for (int i3 = -1 + localPreferenceCategory.getPreferenceCount(); i3 >= 0; i3--)
          {
            Preference localPreference2 = localPreferenceCategory.getPreference(i3);
            if (((localPreference2 instanceof LanguagePackPreference)) || ((localPreference2 instanceof NoLanguagesPreference))) {
              ((PreferenceCategory)localPreference1).removePreference(localPreference2);
            }
          }
        }
        if (((localPreference1 instanceof LanguagePackPreference)) || ((localPreference1 instanceof NoLanguagesPreference))) {
          localPreferenceScreen.removePreference(localPreference1);
        }
      }
      int k = 1;
      int m = 0;
      n = 1;
      List localList = this.mLanguagePackManager.getSortedLanguagePacks();
      Vector localVector = this.mLanguagePackManager.getDownloadedLanguagePacks();
      Iterator localIterator = localList.iterator();
      if (localIterator.hasNext())
      {
        LanguagePack localLanguagePack = (LanguagePack)localIterator.next();
        k++;
        int i1 = k;
        if ((localLanguagePack.isDownloaded()) || (localLanguagePack.isEnabled()) || (localLanguagePack.isPreinstalled())) {
          m++;
        }
        for (int i2 = i1 + 200;; i2 = i1 + 300)
        {
          LanguagePackPreference localLanguagePackPreference = new LanguagePackPreference(getContext(), i2, localLanguagePack, this.mSDCardAvailable, localVector, this);
          localLanguagePackPreference.setEnabled(true);
          if (!this.mSDCardAvailable) {
            localLanguagePackPreference.setEnabled(false);
          }
          localPreferenceScreen.addPreference(localLanguagePackPreference);
          if (localLanguagePack.isPreinstalled()) {
            break;
          }
          n = 0;
          break;
        }
      }
      NoLanguagesPreference localNoLanguagesPreference;
      if ((!this.mSDCardAvailable) || (m <= 0))
      {
        localNoLanguagesPreference = new NoLanguagesPreference(getContext());
        if (this.mSDCardAvailable) {
          break label471;
        }
        localNoLanguagesPreference.setTitle(2131297094);
        localNoLanguagesPreference.setSummary(2131297095);
      }
      for (;;)
      {
        localNoLanguagesPreference.setOrder(300);
        localPreferenceScreen.addPreference(localNoLanguagesPreference);
        if ((n == 0) || (this.mUpdateButton != null)) {
          break;
        }
        View.OnClickListener local3 = new View.OnClickListener()
        {
          public void onClick(View paramAnonymousView)
          {
            if (LanguagePreferenceConfiguration.this.mUpdateButton != null) {
              LanguagePreferenceConfiguration.this.mUpdateButton.setEnabled(false);
            }
            LanguagePreferenceConfiguration.this.updateLanguageList();
          }
        };
        this.mUpdateButton = new PreferenceUpdateLanguagesButton(getContext(), 301, local3);
        localPreferenceScreen.addPreference(this.mUpdateButton);
        return;
        if (m <= 0)
        {
          localNoLanguagesPreference.setTitle(2131297088);
          localNoLanguagesPreference.setSummary(2131297089);
        }
      }
    } while ((n != 0) || (this.mUpdateButton == null));
    localPreferenceScreen.removePreference(this.mUpdateButton);
    this.mUpdateButton = null;
  }
  
  public DialogInterface.OnClickListener getDeleteLanguageListener()
  {
    return this.mDeleteLanguageListener;
  }
  
  public DialogFragment getDialogFragment(int paramInt)
  {
    return LanguageDialogFragment.newInstance(paramInt, this);
  }
  
  public Vector<LanguagePack> getDisabledLanguages()
  {
    Vector localVector1 = new Vector(0);
    if (this.mLanguagePackManager != null) {
      synchronized (this.mLanguagePackManager.getLanguagePacks())
      {
        Iterator localIterator = ???.iterator();
        while (localIterator.hasNext())
        {
          LanguagePack localLanguagePack = (LanguagePack)localIterator.next();
          if ((localLanguagePack.isDownloaded()) && (!localLanguagePack.isEnabled())) {
            localVector1.add(localLanguagePack);
          }
        }
      }
    }
    return localVector1;
  }
  
  public Handler getHandler()
  {
    return this.mHandler;
  }
  
  public int getLanguagePackCount()
  {
    return this.mLanguagePackCount;
  }
  
  public LanguagePackManager getLanguagePackManager()
  {
    return this.mLanguagePackManager;
  }
  
  public PreferenceUpdateLanguagesButton getUpdateButton()
  {
    return this.mUpdateButton;
  }
  
  public boolean isUpdatingInProgress()
  {
    return this.mUpdatingInProgress;
  }
  
  public Dialog onCreateDialog(int paramInt)
  {
    LanguagePreferenceDialog localLanguagePreferenceDialog = new LanguagePreferenceDialog(this);
    Object localObject = null;
    switch (paramInt)
    {
    }
    for (;;)
    {
      return localLanguagePreferenceDialog.getDialog(paramInt, (DialogInterface.OnClickListener)localObject);
      localObject = this.mDeleteLanguageListener;
    }
  }
  
  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    getActivity().getMenuInflater().inflate(2131755009, paramMenu);
    return true;
  }
  
  public void onDestroy()
  {
    if (this.mLanguagePackManager != null)
    {
      this.mLanguagePackManager.removeListener(this.configurationListener);
      this.mLanguagePackManager = null;
    }
    this.mUserNotificationManager = null;
    if (this.mSDCardListener != null)
    {
      SDCardReceiver.removeListener(this.mSDCardListener);
      this.mSDCardListener = null;
    }
  }
  
  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    switch (paramMenuItem.getItemId())
    {
    default: 
      String str = TAG;
      Object[] arrayOfObject = new Object[3];
      arrayOfObject[0] = Integer.valueOf(paramMenuItem.getItemId());
      arrayOfObject[1] = Integer.valueOf(2131231057);
      arrayOfObject[2] = Integer.valueOf(2131231058);
      LogUtil.e(str, String.format("Invalid item id: %d (expected %d or %d)", arrayOfObject));
      return false;
    case 2131231057: 
      Vector localVector = getDownloadedLanguages();
      int i = 0;
      Iterator localIterator = localVector.iterator();
      while (localIterator.hasNext()) {
        if (!((LanguagePack)localIterator.next()).isEnabled()) {
          i++;
        }
      }
      if (i == 0) {
        showDialog(2);
      }
      for (;;)
      {
        return true;
        showDialog(1);
      }
    }
    Toast.makeText(getContext(), 2131297197, 0).show();
    updateLanguageList();
    return true;
  }
  
  public void onPause()
  {
    this.mActivityRunning = false;
    if (this.mLanguagePackManager != null) {
      this.mLanguagePackManager.stopDeferringNotifications();
    }
    this.mUserNotificationManager.enableNotifications();
  }
  
  public boolean onPrepareOptionsMenu(Menu paramMenu)
  {
    MenuItem localMenuItem = paramMenu.findItem(2131231057);
    Vector localVector = getDownloadedLanguages();
    if ((localVector == null) || (localVector.size() < 2))
    {
      localMenuItem.setEnabled(false);
      return true;
    }
    localMenuItem.setEnabled(true);
    return true;
  }
  
  public void onResume()
  {
    this.mUserNotificationManager.disableNotifications();
    this.mActivityRunning = true;
    if (this.mSDCardListener == null)
    {
      this.mSDCardListener = new SDCardListener()
      {
        public void onMediaMounted()
        {
          LanguagePreferenceConfiguration.access$602(LanguagePreferenceConfiguration.this, true);
          LanguagePreferenceConfiguration.this.createWidgets();
        }
        
        public void onMediaUnmounted()
        {
          LanguagePreferenceConfiguration.access$602(LanguagePreferenceConfiguration.this, false);
          LanguagePreferenceConfiguration.this.createWidgets();
        }
      };
      SDCardReceiver.addListener(this.mSDCardListener);
    }
  }
  
  public void setUpdatingInProgress(boolean paramBoolean)
  {
    this.mUpdatingInProgress = paramBoolean;
  }
  
  public void setup(PreferenceActivity paramPreferenceActivity)
  {
    this.mUserNotificationManager = UserNotificationManager.getInstance(getApplicationContext());
    if (Environment.getExternalStorageState().equals("mounted")) {
      this.mSDCardAvailable = true;
    }
    this.mDeleteLanguageListener = new LanguagePreferenceDeleteLangListener(this, 1);
  }
  
  public void showLPMFailure()
  {
    if (this.mApplicationContext != null) {}
    for (Context localContext = this.mApplicationContext;; localContext = getApplicationContext())
    {
      if (localContext != null) {
        Toast.makeText(localContext, 2131297090, 1).show();
      }
      return;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.settings.LanguagePreferenceConfiguration
 * JD-Core Version:    0.7.0.1
 */