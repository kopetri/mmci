package com.touchtype.installer.x;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.touchtype.installer.InstallerPreferences;
import com.touchtype_fluency.service.languagepacks.LanguagePackManager;
import com.touchtype_fluency.service.languagepacks.languagepackmanager.LanguagePack;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public final class InstallerDialogFragment
  extends DialogFragment
{
  private XInstaller mInstaller;
  private InstallerPreferences mInstallerPrefs;
  private LanguagePackManager mLanguagePackManager;
  private Resources mResources;
  
  public static InstallerDialogFragment newInstance(XInstaller paramXInstaller, InstallerPreferences paramInstallerPreferences, LanguagePackManager paramLanguagePackManager, int paramInt)
  {
    InstallerDialogFragment localInstallerDialogFragment = new InstallerDialogFragment();
    Bundle localBundle = new Bundle();
    localBundle.putInt("id", paramInt);
    localInstallerDialogFragment.setArguments(localBundle);
    localInstallerDialogFragment.setVariables(paramXInstaller, paramInstallerPreferences, paramLanguagePackManager);
    return localInstallerDialogFragment;
  }
  
  private void setVariables(XInstaller paramXInstaller, InstallerPreferences paramInstallerPreferences, LanguagePackManager paramLanguagePackManager)
  {
    this.mInstaller = paramXInstaller;
    this.mInstallerPrefs = paramInstallerPreferences;
    this.mLanguagePackManager = paramLanguagePackManager;
    this.mResources = paramXInstaller.getResources();
  }
  
  public Dialog onCreateDialog(Bundle paramBundle)
  {
    int i = getArguments().getInt("id");
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(this.mInstaller);
    String str1 = this.mResources.getString(2131296303);
    switch (i)
    {
    case 7: 
    default: 
      return null;
    case 1: 
      Vector localVector = this.mLanguagePackManager.getLanguagePacks();
      ArrayList localArrayList = new ArrayList(localVector.size());
      Iterator localIterator = localVector.iterator();
      while (localIterator.hasNext()) {
        localArrayList.add(((LanguagePack)localIterator.next()).getName());
      }
      localBuilder.setTitle(2131296428);
      localBuilder.setOnCancelListener(new DialogInterface.OnCancelListener()
      {
        public void onCancel(DialogInterface paramAnonymousDialogInterface)
        {
          InstallerDialogFragment.this.mInstallerPrefs.setInstallerStepChooseLang("returned");
        }
      });
      localBuilder.setAdapter(new LeftOnlyListAdapater(this.mInstaller.getApplicationContext(), 2131230862, localArrayList), new InstallerDialogListenerBuilder(this.mInstaller).getListener(i));
      return localBuilder.create();
    case 2: 
      localBuilder.setTitle(this.mResources.getString(2131296429));
      localBuilder.setMessage(this.mResources.getString(2131296431) + " " + this.mResources.getString(2131296430));
      localBuilder.setCancelable(true);
      localBuilder.setNeutralButton(2131296434, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          InstallerDialogFragment.this.mInstaller.resetLanguageDownloadCounter();
        }
      });
      localBuilder.setNegativeButton(2131296435, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          InstallerDialogFragment.this.mInstaller.cancelLanguageDownload();
        }
      });
      return localBuilder.create();
    case 3: 
      localBuilder.setTitle(this.mResources.getString(2131296429));
      localBuilder.setCancelable(false);
      localBuilder.setMessage(this.mResources.getString(2131296432) + " " + this.mResources.getString(2131296430));
      localBuilder.setNeutralButton(2131296433, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {}
      });
      return localBuilder.create();
    case 4: 
      localBuilder.setTitle(this.mResources.getString(2131296443));
      localBuilder.setMessage(this.mResources.getString(2131296444));
      localBuilder.setCancelable(true);
      localBuilder.setNeutralButton(2131296439, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          InstallerDialogFragment.this.mInstallerPrefs.setInstallerStepChooseLang("continued");
        }
      });
      localBuilder.setNegativeButton(2131296442, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          InstallerDialogFragment.this.mInstallerPrefs.setInstallerStepChooseLang("stopped");
          InstallerDialogFragment.this.mInstaller.stopLanguageDownload();
        }
      });
      localBuilder.setOnCancelListener(new DialogInterface.OnCancelListener()
      {
        public void onCancel(DialogInterface paramAnonymousDialogInterface)
        {
          InstallerDialogFragment.this.mInstallerPrefs.setInstallerStepChooseLang("continued");
        }
      });
      return localBuilder.create();
    case 8: 
      localBuilder.setTitle(this.mResources.getString(2131296440));
      localBuilder.setMessage(String.format(this.mResources.getString(2131296404), new Object[] { str1 }));
      localBuilder.setCancelable(true);
      localBuilder.setNeutralButton(2131296435, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {}
      });
      localBuilder.setNegativeButton(2131296440, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          InstallerDialogFragment.this.mInstaller.exitWithError();
        }
      });
      return localBuilder.create();
    case 5: 
      localBuilder.setTitle(String.format(this.mResources.getString(2131296445), new Object[] { str1 }));
      localBuilder.setCancelable(false);
      localBuilder.setMessage(this.mResources.getString(2131296408));
      localBuilder.setNeutralButton(2131296433, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          InstallerDialogFragment.this.mInstaller.enableSwiftkey();
        }
      });
      return localBuilder.create();
    }
    String str2 = String.format(this.mResources.getString(2131296446), new Object[] { str1 });
    String str3 = String.format(this.mResources.getString(2131296409), new Object[] { str1 });
    localBuilder.setTitle(str2);
    localBuilder.setCancelable(false);
    localBuilder.setMessage(str3);
    localBuilder.setNeutralButton(2131296433, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        InstallerDialogFragment.this.mInstaller.exitWithError();
      }
    });
    return localBuilder.create();
  }
  
  private final class LeftOnlyListAdapater
    extends ArrayAdapter<String>
  {
    private List<String> mItems;
    
    public LeftOnlyListAdapater(int paramInt, List<String> paramList)
    {
      super(paramList, localList);
      this.mItems = localList;
    }
    
    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      View localView = paramView;
      if (paramView == null) {
        localView = ((LayoutInflater)InstallerDialogFragment.this.mInstaller.getSystemService("layout_inflater")).inflate(2130903084, null);
      }
      String str = (String)this.mItems.get(paramInt);
      if (str != null)
      {
        TextView localTextView = (TextView)localView.findViewById(2131230862);
        if (localTextView != null) {
          localTextView.setText(str);
        }
      }
      return localView;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.installer.x.InstallerDialogFragment
 * JD-Core Version:    0.7.0.1
 */