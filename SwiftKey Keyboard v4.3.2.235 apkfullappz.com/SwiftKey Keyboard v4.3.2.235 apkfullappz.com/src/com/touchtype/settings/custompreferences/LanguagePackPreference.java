package com.touchtype.settings.custompreferences;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Resources;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.touchtype.preferences.TouchTypePreferences;
import com.touchtype.report.TouchTypeStats;
import com.touchtype.settings.LanguagePreferenceConfiguration;
import com.touchtype.util.LogUtil;
import com.touchtype_fluency.service.ProgressListener;
import com.touchtype_fluency.service.languagepacks.LanguagePackManager;
import com.touchtype_fluency.service.languagepacks.languagepackmanager.DownloadRequiredException;
import com.touchtype_fluency.service.languagepacks.languagepackmanager.LanguagePack;
import com.touchtype_fluency.service.languagepacks.languagepackmanager.MaximumLanguagesException;
import com.touchtype_fluency.service.languagepacks.layouts.LayoutData.LayoutMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class LanguagePackPreference
  extends ProgressPreference
{
  private static final String TAG = LanguagePackPreference.class.getSimpleName();
  private CheckBox mCheckbox;
  private LanguagePack mLanguagePack;
  private final LanguagePackManager mLanguagePackManager;
  private final LanguagePreferenceConfiguration mLanguagePreferenceConfiguration;
  private List<String> mLayoutNames;
  private List<LayoutData.LayoutMap> mLayouts;
  private ImageView mLayoutsImageView;
  private ProgressListener mListener;
  private int mOrder;
  private boolean mStorageAvailable;
  private CharSequence mSummaryText;
  private TextView mSummaryTextView;
  private final boolean mTogglingEnabled;
  private Button mUpdateButton;
  
  public LanguagePackPreference(Context paramContext, int paramInt, LanguagePack paramLanguagePack, boolean paramBoolean, Vector<LanguagePack> paramVector, LanguagePreferenceConfiguration paramLanguagePreferenceConfiguration)
  {
    super(paramContext, null);
    setLayoutResource(2130903093);
    this.mOrder = paramInt;
    this.mLanguagePack = paramLanguagePack;
    this.mStorageAvailable = paramBoolean;
    this.mLanguagePreferenceConfiguration = paramLanguagePreferenceConfiguration;
    this.mLanguagePackManager = paramLanguagePreferenceConfiguration.getLanguagePackManager();
    this.mTogglingEnabled = paramContext.getResources().getBoolean(2131492929);
    setOrder(this.mOrder);
    setKey(this.mLanguagePack.getID());
    setTitle(this.mLanguagePack.getName());
    setSummaryText();
    setupLayouts(paramVector);
  }
  
  private void setPreferredLayout()
  {
    LanguagePackManager localLanguagePackManager = this.mLanguagePreferenceConfiguration.getLanguagePackManager();
    if (localLanguagePackManager == null) {
      LogUtil.w(TAG, "LPM was null!");
    }
    LayoutData.LayoutMap localLayoutMap;
    do
    {
      LanguagePack localLanguagePack;
      do
      {
        return;
        Iterator localIterator;
        while (!localIterator.hasNext())
        {
          Vector localVector = localLanguagePackManager.getEnabledLanguagePacks();
          setupLayouts(localLanguagePackManager.getDownloadedLanguagePacks());
          localIterator = localVector.iterator();
        }
        localLanguagePack = (LanguagePack)localIterator.next();
      } while ((!localLanguagePack.isEnabled()) || (localLanguagePack.equals(this.mLanguagePack)));
      localLayoutMap = this.mLanguagePackManager.getCurrentLayout(localLanguagePack);
    } while ((!this.mLayouts.contains(localLayoutMap)) || (localLayoutMap.equals(this.mLanguagePack.getDefaultLayout())));
    this.mLanguagePackManager.setCurrentLayout(this.mLanguagePack, localLayoutMap.getLayoutName(), false);
  }
  
  private void setSummaryText()
  {
    boolean bool = false;
    if (!this.mStorageAvailable) {
      this.mSummaryText = getContext().getString(2131297102);
    }
    for (;;)
    {
      if (getContext().getResources().getBoolean(2131492905)) {
        this.mSummaryText = (this.mSummaryText + " [" + this.mLanguagePack.getDefaultLayoutName() + "]");
      }
      setSummary(this.mSummaryText);
      if (this.mSummaryTextView != null) {
        this.mSummaryTextView.setClickable(bool);
      }
      return;
      if (this.mLanguagePack.isDownloadInProgress())
      {
        this.mSummaryText = "";
        bool = false;
      }
      else if (this.mLanguagePack.isBroken())
      {
        this.mSummaryText = getContext().getString(2131297098);
        bool = false;
      }
      else if (this.mLanguagePack.isDownloaded())
      {
        if (this.mLanguagePack.isUpdateAvailable())
        {
          this.mSummaryText = getContext().getString(2131297099);
          bool = false;
        }
        else if (this.mLanguagePack.isEnabled())
        {
          LayoutData.LayoutMap localLayoutMap = this.mLanguagePackManager.getCurrentLayout(this.mLanguagePack);
          this.mSummaryText = getContext().getResources().getString(localLayoutMap.getNameResourceId(), new Object[] { null });
          bool = this.mLanguagePack.hasMultipleLayouts();
        }
        else
        {
          this.mSummaryText = getContext().getString(2131297101);
          bool = false;
        }
      }
      else if (this.mLanguagePack.isPreinstalled())
      {
        this.mSummaryText = getContext().getString(2131297097);
        bool = false;
      }
      else
      {
        this.mSummaryText = getContext().getString(2131297096);
        bool = false;
      }
    }
  }
  
  private void setupLayouts(Vector<LanguagePack> paramVector)
  {
    this.mLayoutNames = new ArrayList();
    this.mLayouts = new ArrayList();
    Resources localResources = getContext().getResources();
    Iterator localIterator1 = this.mLanguagePack.getAvailableLayouts().iterator();
    while (localIterator1.hasNext())
    {
      LayoutData.LayoutMap localLayoutMap2 = (LayoutData.LayoutMap)localIterator1.next();
      this.mLayouts.add(localLayoutMap2);
      this.mLayoutNames.add(localResources.getString(localLayoutMap2.getNameResourceId(), new Object[] { null }));
    }
    if (this.mLanguagePack.hasUnextendedLatinLayout())
    {
      Iterator localIterator2 = paramVector.iterator();
      while (localIterator2.hasNext())
      {
        LanguagePack localLanguagePack = (LanguagePack)localIterator2.next();
        if (!this.mLanguagePack.getID().equals(localLanguagePack.getID()))
        {
          Iterator localIterator3 = localLanguagePack.getAvailableLayouts().iterator();
          while (localIterator3.hasNext())
          {
            LayoutData.LayoutMap localLayoutMap1 = (LayoutData.LayoutMap)localIterator3.next();
            if ((localLayoutMap1.isLayoutSelectable()) && (localLayoutMap1.extendsQwerty()) && (!this.mLayouts.contains(localLayoutMap1)))
            {
              this.mLayouts.add(localLayoutMap1);
              this.mLayoutNames.add(localResources.getString(localLayoutMap1.getNameResourceId(), new Object[] { null }));
            }
          }
        }
      }
    }
  }
  
  private void showLayoutChooser()
  {
    Resources localResources = getContext().getResources();
    int i = -1;
    int j = 0;
    Iterator localIterator = this.mLayouts.iterator();
    while (localIterator.hasNext())
    {
      if (((LayoutData.LayoutMap)localIterator.next()).equals(this.mLanguagePackManager.getCurrentLayout(this.mLanguagePack))) {
        i = j;
      }
      j++;
    }
    if (i == -1) {
      i = 0;
    }
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(getContext());
    localBuilder.setTitle(localResources.getString(2131296475));
    localBuilder.setSingleChoiceItems((CharSequence[])this.mLayoutNames.toArray(new String[0]), i, new DialogInterface.OnClickListener()
    {
      private void reportLayoutChange(LanguagePack paramAnonymousLanguagePack, String paramAnonymousString)
      {
        TouchTypePreferences.getInstance(LanguagePackPreference.this.getContext()).getTouchTypeStats().recordLanguageLayoutChange(paramAnonymousLanguagePack, paramAnonymousString);
      }
      
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        String str = ((LayoutData.LayoutMap)LanguagePackPreference.this.mLayouts.get(paramAnonymousInt)).getLayoutName();
        LanguagePackPreference.this.mLanguagePackManager.setCurrentLayout(LanguagePackPreference.this.mLanguagePack, str, true);
        reportLayoutChange(LanguagePackPreference.this.mLanguagePack, str);
        paramAnonymousDialogInterface.dismiss();
      }
    });
    localBuilder.create().show();
  }
  
  private void showmLanguagePackDownloading(boolean paramBoolean)
  {
    final boolean bool = this.mLanguagePack.isDownloaded();
    final Activity localActivity = this.mLanguagePreferenceConfiguration.getActivity();
    if (this.mListener == null) {
      this.mListener = new ProgressListener()
      {
        public void onComplete(final boolean paramAnonymousBoolean1, final boolean paramAnonymousBoolean2, boolean paramAnonymousBoolean3, boolean paramAnonymousBoolean4)
        {
          localActivity.runOnUiThread(new Runnable()
          {
            public void run()
            {
              LanguagePackPreference.this.hideProgress();
              LanguagePackPreference.this.setSummary(null);
              if (!paramAnonymousBoolean1)
              {
                LanguagePackPreference.this.mLanguagePack.resetProgress();
                LanguagePackPreference.this.setSummaryText();
                if (!paramAnonymousBoolean2) {
                  Toast.makeText(LanguagePackPreference.5.this.val$activity, LanguagePackPreference.5.this.val$activity.getString(2131297085), 1).show();
                }
              }
              for (;;)
              {
                LanguagePackPreference.this.notifyChanged();
                return;
                if (LanguagePackPreference.5.this.val$wasDownloaded)
                {
                  if (LanguagePackPreference.this.mTogglingEnabled) {
                    LanguagePackPreference.this.mCheckbox.setVisibility(0);
                  }
                  LanguagePackPreference.this.mCheckbox.setChecked(LanguagePackPreference.this.mLanguagePack.isEnabled());
                }
                else
                {
                  try
                  {
                    LanguagePackPreference.this.setPreferredLayout();
                    LanguagePackPreference.this.mLanguagePackManager.enableLanguage(LanguagePackPreference.this.mLanguagePack, true);
                  }
                  catch (MaximumLanguagesException localMaximumLanguagesException)
                  {
                    LanguagePackPreference.this.mLanguagePreferenceConfiguration.createWidgets();
                  }
                  catch (DownloadRequiredException localDownloadRequiredException) {}
                }
              }
            }
          });
        }
        
        public void onProgress(final int paramAnonymousInt1, final int paramAnonymousInt2)
        {
          localActivity.runOnUiThread(new Runnable()
          {
            public void run()
            {
              LanguagePackPreference.5.this.val$preference.onProgress(paramAnonymousInt1, paramAnonymousInt2);
            }
          });
        }
      };
    }
    if (paramBoolean) {
      this.mLanguagePack.download(this.mListener);
    }
    if (this.mLanguagePack.isDownloadInProgress())
    {
      this.mUpdateButton.setVisibility(8);
      showProgress(this.mLanguagePack.getCurrentProgress(), this.mLanguagePack.getCurrentMax());
      setSummary(null);
    }
  }
  
  protected void onBindView(View paramView)
  {
    super.onBindView(paramView);
    this.mCheckbox = ((CheckBox)paramView.findViewById(2131230878));
    this.mUpdateButton = ((Button)paramView.findViewById(2131230879));
    this.mLayoutsImageView = ((ImageView)paramView.findViewById(2131230877));
    this.mSummaryTextView = ((TextView)paramView.findViewById(16908304));
    int i = 8;
    int j = 8;
    int k = 8;
    if (this.mUpdateButton != null) {
      this.mUpdateButton.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          LanguagePackPreference.this.showmLanguagePackDownloading(true);
        }
      });
    }
    View.OnClickListener local2 = new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        LanguagePackPreference.this.showLayoutChooser();
      }
    };
    if (this.mLayoutsImageView != null) {
      this.mLayoutsImageView.setOnClickListener(local2);
    }
    if (this.mSummaryTextView != null) {
      this.mSummaryTextView.setOnClickListener(local2);
    }
    if (this.mCancelButton != null) {
      this.mCancelButton.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          if ((LanguagePackPreference.this.mListener != null) && (LanguagePackPreference.this.mLanguagePack.hasListener(LanguagePackPreference.this.mListener)))
          {
            LanguagePackPreference.this.mLanguagePack.removeListener(LanguagePackPreference.this.mListener);
            LanguagePackPreference.access$202(LanguagePackPreference.this, null);
          }
          LanguagePackPreference.this.mLanguagePack.cancelDownload();
          LanguagePackPreference.this.hideProgress();
          Button localButton = LanguagePackPreference.this.mUpdateButton;
          if (LanguagePackPreference.this.mLanguagePack.isUpdateAvailable()) {}
          for (int i = 0;; i = 8)
          {
            localButton.setVisibility(i);
            LanguagePackPreference.this.setSummary(LanguagePackPreference.this.getContext().getString(2131297096));
            return;
          }
        }
      });
    }
    if (this.mLanguagePack.isDownloadInProgress()) {
      showmLanguagePackDownloading(false);
    }
    while (!this.mLanguagePack.isDownloaded())
    {
      if (this.mCheckbox != null) {
        this.mCheckbox.setVisibility(i);
      }
      if (this.mUpdateButton != null) {
        this.mUpdateButton.setVisibility(j);
      }
      if (this.mLayoutsImageView != null) {
        this.mLayoutsImageView.setVisibility(k);
      }
      setSummaryText();
      return;
    }
    if (this.mTogglingEnabled) {
      i = 0;
    }
    if ((this.mLanguagePack.isEnabled()) && (this.mLayouts.size() > 1)) {}
    for (k = 0;; k = 8)
    {
      if (this.mCheckbox != null) {
        this.mCheckbox.setChecked(this.mLanguagePack.isEnabled());
      }
      if ((!this.mLanguagePack.isUpdateAvailable()) || (!this.mLanguagePack.isEnabled())) {
        break;
      }
      j = 0;
      break;
    }
  }
  
  protected void onClick()
  {
    super.onClick();
    int i;
    if (!getContext().getResources().getBoolean(2131492924)) {
      i = 1;
    }
    int j;
    for (;;)
    {
      if ((this.mLanguagePack.isDownloaded()) && (!this.mLanguagePack.isBroken()))
      {
        boolean bool2 = this.mTogglingEnabled;
        j = 0;
        boolean bool3;
        if (bool2)
        {
          if (this.mLanguagePack.isEnabled()) {
            break label181;
          }
          bool3 = true;
        }
        try
        {
          for (;;)
          {
            this.mLanguagePackManager.enableLanguage(this.mLanguagePack, bool3);
            this.mCheckbox.setChecked(bool3);
            j = bool3;
            if ((j != 0) && (i != 0) && (this.mLanguagePack.isBeta()))
            {
              Context localContext1 = getContext();
              Object[] arrayOfObject1 = new Object[1];
              arrayOfObject1[0] = this.mLanguagePack.getName();
              String str1 = localContext1.getString(2131296860, arrayOfObject1);
              if ((str1 != null) && (str1.length() > 0)) {
                Toast.makeText(getContext().getApplicationContext(), str1, 1).show();
              }
            }
            return;
            i = 0;
            break;
            label181:
            bool3 = false;
          }
        }
        catch (MaximumLanguagesException localMaximumLanguagesException)
        {
          for (;;)
          {
            Context localContext2 = getContext();
            Object[] arrayOfObject2 = new Object[1];
            arrayOfObject2[0] = Integer.valueOf(localMaximumLanguagesException.getMaxLanguagePacks());
            String str2 = localContext2.getString(2131297296, arrayOfObject2);
            Toast.makeText(getContext().getApplicationContext(), str2, 1).show();
            bool3 = false;
          }
        }
        catch (DownloadRequiredException localDownloadRequiredException)
        {
          for (;;)
          {
            bool3 = false;
          }
        }
      }
    }
    if (!this.mLanguagePack.isDownloadInProgress()) {}
    for (boolean bool1 = true;; bool1 = false)
    {
      showmLanguagePackDownloading(bool1);
      j = bool1;
      break;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.settings.custompreferences.LanguagePackPreference
 * JD-Core Version:    0.7.0.1
 */