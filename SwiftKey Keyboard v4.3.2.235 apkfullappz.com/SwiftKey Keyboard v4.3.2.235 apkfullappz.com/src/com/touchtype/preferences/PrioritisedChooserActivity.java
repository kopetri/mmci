package com.touchtype.preferences;

import android.app.ListActivity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;
import com.touchtype.util.LogUtil;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import junit.framework.Assert;

public class PrioritisedChooserActivity
  extends ListActivity
{
  static final String[] ACTION_SEND_DATA = { "android.intent.extra.EMAIL", "android.intent.extra.CC", "android.intent.extra.BCC", "android.intent.extra.SUBJECT" };
  private static final String[][] PRIORITISED_APPS;
  private static final String TAG = PrioritisedChooserActivity.class.getSimpleName();
  Intent mData = null;
  ViewFlipper spinnerFlipper;
  
  static
  {
    PRIORITISED_APPS = new String[][] { { "com.google.android.gm", "com.htc.android.mail", "com.android.email" }, { "com.twitter.android", "com.thedeck.android.app" }, { "com.facebook.katana" }, { "com.android.mms" } };
  }
  
  private void addRecentApplication(String paramString)
  {
    TouchTypePreferences localTouchTypePreferences = TouchTypePreferences.getInstance(getApplicationContext());
    List localList = localTouchTypePreferences.getRecentSharingApps();
    if (localList.contains(paramString)) {
      localList.remove(paramString);
    }
    if (localList.size() == 3) {
      localList.remove(2);
    }
    localList.add(0, paramString);
    localTouchTypePreferences.setRecentSharingApps(localList);
  }
  
  public static Intent createChooser(Context paramContext, Intent paramIntent, CharSequence paramCharSequence)
  {
    Intent localIntent = new Intent(paramIntent);
    localIntent.setComponent(new ComponentName(paramContext, PrioritisedChooserActivity.class));
    localIntent.setFlags(268435456);
    if (paramCharSequence != null) {
      localIntent.putExtra("android.intent.extra.TITLE", paramCharSequence);
    }
    return localIntent;
  }
  
  private void onResolveInfoSelected(ResolveInfo paramResolveInfo)
  {
    ActivityInfo localActivityInfo = paramResolveInfo.activityInfo;
    Intent localIntent = new Intent(this.mData);
    localIntent.setFlags(268435456);
    localIntent.setComponent(new ComponentName(localActivityInfo.packageName, localActivityInfo.name));
    startActivity(localIntent);
    finish();
  }
  
  protected static <T> void promoteToTop(T paramT, LinkedList<T> paramLinkedList)
  {
    if (paramLinkedList.contains(paramT))
    {
      paramLinkedList.remove(paramT);
      paramLinkedList.add(0, paramT);
    }
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903114);
    this.spinnerFlipper = ((ViewFlipper)findViewById(2131230929));
    Intent localIntent = getIntent();
    if (localIntent.hasExtra("android.intent.extra.TITLE")) {
      setTitle(localIntent.getStringExtra("android.intent.extra.TITLE"));
    }
    new AsyncTask()
    {
      protected ArrayAdapter<ResolveInfo> doInBackground(Intent... paramAnonymousVarArgs)
      {
        Assert.assertEquals(paramAnonymousVarArgs.length, 1);
        PrioritisedChooserActivity.this.mData = new Intent(PrioritisedChooserActivity.this.getIntent());
        PrioritisedChooserActivity.this.mData.setComponent(null);
        final PackageManager localPackageManager = PrioritisedChooserActivity.this.getPackageManager();
        final LinkedList localLinkedList = new LinkedList(localPackageManager.queryIntentActivities(PrioritisedChooserActivity.this.mData, 0));
        HashMap localHashMap = new HashMap();
        Iterator localIterator1 = localLinkedList.iterator();
        while (localIterator1.hasNext())
        {
          ResolveInfo localResolveInfo = (ResolveInfo)localIterator1.next();
          localHashMap.put(localResolveInfo.activityInfo.packageName, localResolveInfo);
        }
        int i = -1 + PrioritisedChooserActivity.PRIORITISED_APPS.length;
        if (i >= 0)
        {
          String[] arrayOfString = PrioritisedChooserActivity.PRIORITISED_APPS[i];
          int j = arrayOfString.length;
          for (int k = 0;; k++) {
            if (k < j)
            {
              String str2 = arrayOfString[k];
              if (localHashMap.containsKey(str2)) {
                PrioritisedChooserActivity.promoteToTop(localHashMap.get(str2), localLinkedList);
              }
            }
            else
            {
              i--;
              break;
            }
          }
        }
        Iterator localIterator2 = TouchTypePreferences.getInstance(PrioritisedChooserActivity.this.getApplicationContext()).getRecentSharingApps().iterator();
        while (localIterator2.hasNext())
        {
          String str1 = (String)localIterator2.next();
          if (localHashMap.containsKey(str1)) {
            PrioritisedChooserActivity.promoteToTop(localHashMap.get(str1), localLinkedList);
          }
        }
        new ArrayAdapter(PrioritisedChooserActivity.this, 2130903115, 2131230930, localLinkedList)
        {
          public View getView(int paramAnonymous2Int, View paramAnonymous2View, ViewGroup paramAnonymous2ViewGroup)
          {
            View localView = super.getView(paramAnonymous2Int, paramAnonymous2View, paramAnonymous2ViewGroup);
            String str = ((ResolveInfo)localLinkedList.get(paramAnonymous2Int)).loadLabel(localPackageManager).toString();
            ((TextView)localView.findViewById(2131230930)).setText(str);
            Drawable localDrawable = ((ResolveInfo)localLinkedList.get(paramAnonymous2Int)).loadIcon(localPackageManager);
            ((ImageView)localView.findViewById(2131230868)).setImageDrawable(localDrawable);
            return localView;
          }
        };
      }
      
      protected void onPostExecute(ArrayAdapter<ResolveInfo> paramAnonymousArrayAdapter)
      {
        switch (paramAnonymousArrayAdapter.getCount())
        {
        }
        for (;;)
        {
          ListView localListView = PrioritisedChooserActivity.this.getListView();
          if (localListView == null) {
            break;
          }
          localListView.setAdapter(paramAnonymousArrayAdapter);
          PrioritisedChooserActivity.this.spinnerFlipper.showNext();
          return;
          LogUtil.w(PrioritisedChooserActivity.TAG, "No applications available to handle this intent");
          PrioritisedChooserActivity.this.finish();
          return;
          PrioritisedChooserActivity.this.onResolveInfoSelected((ResolveInfo)paramAnonymousArrayAdapter.getItem(0));
        }
        LogUtil.e(PrioritisedChooserActivity.TAG, "ListView not found");
        PrioritisedChooserActivity.this.finish();
      }
    }.execute(new Intent[] { localIntent });
  }
  
  public void onDestroy()
  {
    super.onDestroy();
    this.mData = null;
  }
  
  protected void onListItemClick(ListView paramListView, View paramView, int paramInt, long paramLong)
  {
    super.onListItemClick(paramListView, paramView, paramInt, paramLong);
    ResolveInfo localResolveInfo = (ResolveInfo)getListView().getAdapter().getItem(paramInt);
    onResolveInfoSelected(localResolveInfo);
    addRecentApplication(localResolveInfo.activityInfo.packageName);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.preferences.PrioritisedChooserActivity
 * JD-Core Version:    0.7.0.1
 */