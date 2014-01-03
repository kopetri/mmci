package com.touchtype;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.InputConnection;
import android.widget.ArrayAdapter;
import com.touchtype.keyboard.inputeventmodel.InputEventModel;
import com.touchtype.keyboard.service.TouchTypeSoftKeyboard;
import com.touchtype.preferences.TouchTypePreferences;
import com.touchtype.report.TouchTypeStats;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Locale;

public class VoiceRecognition
  extends Activity
{
  private static final Long FREE_FORM_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS;
  private static final String TAG = VoiceRecognition.class.getSimpleName();
  private static final Intent VOICE_INTENT;
  private static CharSequence mInitialTextBeforeCursor;
  private AlertDialog alertDialog;
  private ArrayList<String> matches;
  
  static
  {
    FREE_FORM_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS = Long.valueOf(1500L);
    Intent localIntent = new Intent("android.speech.action.RECOGNIZE_SPEECH");
    VOICE_INTENT = localIntent;
    localIntent.setFlags(1085014016);
  }
  
  public static void attemptVoiceRecognition(Context paramContext, boolean paramBoolean, InputConnection paramInputConnection)
  {
    if (serviceAvailable(paramContext))
    {
      mInitialTextBeforeCursor = getTextBeforeCursor(paramInputConnection);
      Intent localIntent = getVoiceDetailsIntent(paramContext.getApplicationContext());
      if (localIntent != null) {
        paramContext.sendOrderedBroadcast(localIntent, null, new BroadcastReceiver()
        {
          public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
          {
            String str = (String)getResultExtras(true).get("android.speech.extra.LANGUAGE_PREFERENCE");
            VoiceRecognition.start(paramAnonymousContext, this.val$searchField, str);
          }
        }, null, -1, null, null);
      }
    }
    else
    {
      return;
    }
    start(paramContext, paramBoolean);
  }
  
  private void dismissDialog()
  {
    if (this.alertDialog != null) {
      this.alertDialog.dismiss();
    }
  }
  
  private TouchTypeStats getStatistics()
  {
    return TouchTypePreferences.getInstance(getApplicationContext()).getTouchTypeStats();
  }
  
  private static CharSequence getTextBeforeCursor(InputConnection paramInputConnection)
  {
    if (paramInputConnection != null) {
      return paramInputConnection.getTextBeforeCursor(100000, 0);
    }
    return null;
  }
  
  private static Intent getVoiceDetailsIntent(Context paramContext)
  {
    Intent localIntent1 = new Intent("android.speech.action.WEB_SEARCH");
    ResolveInfo localResolveInfo = paramContext.getPackageManager().resolveActivity(localIntent1, 128);
    if ((localResolveInfo == null) || (localResolveInfo.activityInfo == null) || (localResolveInfo.activityInfo.metaData == null)) {}
    String str;
    do
    {
      return null;
      str = localResolveInfo.activityInfo.metaData.getString("android.speech.DETAILS");
    } while (str == null);
    Intent localIntent2 = new Intent("android.speech.action.GET_LANGUAGE_DETAILS");
    localIntent2.setComponent(new ComponentName(localResolveInfo.activityInfo.packageName, str));
    localIntent2.toString();
    return localIntent2;
  }
  
  private void handleValidMatches()
  {
    Object localObject = "";
    int i = this.matches.size();
    if (i > 1)
    {
      showResultsDialog(this.matches);
      return;
    }
    if (i == 1) {
      localObject = (CharSequence)this.matches.get(0);
    }
    saveVoiceRecognitionText((CharSequence)localObject);
    finish();
  }
  
  private void invalidateMatches()
  {
    this.matches = null;
  }
  
  private void removedUnrecognisedMatches(ArrayList<String> paramArrayList)
  {
    int i = paramArrayList.size();
    int j = 0;
    while (j < i) {
      if (unrecognisedMatch((String)paramArrayList.get(j)))
      {
        paramArrayList.remove(j);
        i--;
      }
      else
      {
        j++;
      }
    }
  }
  
  private void saveVoiceRecognitionText(CharSequence paramCharSequence)
  {
    TouchTypeSoftKeyboard.getInstance().saveVoiceRecognitionText(new VoiceRecognizedContent(paramCharSequence, null));
  }
  
  private static boolean serviceAvailable(Context paramContext)
  {
    if (paramContext.getPackageManager().resolveActivity(VOICE_INTENT, 65536) != null) {}
    for (int i = 1; i != 0; i = 0) {
      return true;
    }
    Launcher.launchInstallerExtras(paramContext.getApplicationContext(), "com.touchtype.installer.LAUNCH_VOICE_RECOGNIZER");
    return false;
  }
  
  private void showResultsDialog(final ArrayList<String> paramArrayList)
  {
    this.alertDialog = new AlertDialog.Builder(this).setIcon(getResources().getDrawable(2130837990)).setTitle(getString(2131296868)).setAdapter(new ArrayAdapter(this, 2130903123, paramArrayList), new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        if (paramAnonymousInt >= 0)
        {
          VoiceRecognition.this.getStatistics().incrementVoicePredictions(paramAnonymousInt);
          VoiceRecognition.this.saveVoiceRecognitionText((CharSequence)paramArrayList.get(paramAnonymousInt));
        }
        for (;;)
        {
          VoiceRecognition.this.finish();
          return;
          VoiceRecognition.this.saveVoiceRecognitionText("");
        }
      }
    }).setNegativeButton(getString(2131296869), new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        VoiceRecognition.this.saveVoiceRecognitionText("");
        VoiceRecognition.this.finish();
      }
    }).setOnKeyListener(new DialogInterface.OnKeyListener()
    {
      public boolean onKey(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt, KeyEvent paramAnonymousKeyEvent)
      {
        if ((paramAnonymousInt == 4) && (paramAnonymousKeyEvent.getRepeatCount() == 0))
        {
          VoiceRecognition.this.finish();
          return true;
        }
        return false;
      }
    }).create();
    this.alertDialog.show();
  }
  
  private static void start(Context paramContext, boolean paramBoolean)
  {
    start(paramContext, paramBoolean, Locale.getDefault().getLanguage());
  }
  
  private static void start(Context paramContext, boolean paramBoolean, String paramString)
  {
    String str;
    if (paramBoolean)
    {
      str = "web_search";
      if (!paramBoolean) {
        break label85;
      }
    }
    label85:
    for (Object localObject = null;; localObject = FREE_FORM_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS)
    {
      paramContext.startActivity(new Intent(paramContext, VoiceRecognition.class).putExtra("android.speech.extra.LANGUAGE_MODEL", str).putExtra("android.speech.extra.PROMPT", paramContext.getString(2131296867)).putExtra("android.speech.extra.LANGUAGE", paramString).putExtra("android.speech.extra.MAX_RESULTS", 3).putExtra("android.speech.extras.SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS", (Serializable)localObject).addFlags(277610496));
      return;
      str = "free_form";
      break;
    }
  }
  
  private void startVoiceRecognitionActivity()
  {
    startActivityForResult(new Intent(VOICE_INTENT).putExtras(getIntent()), 0);
  }
  
  private boolean unrecognisedMatch(String paramString)
  {
    return paramString.replace("#", "").length() == 0;
  }
  
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if (paramInt2 == -1)
    {
      getStatistics().incrementStatistic("stats_voice_recognition_uses");
      this.matches = paramIntent.getStringArrayListExtra("android.speech.extra.RESULTS");
      removedUnrecognisedMatches(this.matches);
      return;
    }
    this.matches = new ArrayList();
  }
  
  protected void onPause()
  {
    dismissDialog();
    invalidateMatches();
    super.onPause();
  }
  
  protected void onResume()
  {
    super.onResume();
    if (this.matches != null)
    {
      handleValidMatches();
      return;
    }
    startVoiceRecognitionActivity();
  }
  
  public static final class VoiceRecognizedContent
  {
    private final CharSequence fill;
    private final CharSequence initialTextBeforeCursor;
    
    private VoiceRecognizedContent(CharSequence paramCharSequence)
    {
      this(paramCharSequence, VoiceRecognition.mInitialTextBeforeCursor);
      VoiceRecognition.access$402(null);
    }
    
    VoiceRecognizedContent(CharSequence paramCharSequence1, CharSequence paramCharSequence2)
    {
      this.fill = paramCharSequence1;
      this.initialTextBeforeCursor = paramCharSequence2;
    }
    
    private String trimAndLowerCase(CharSequence paramCharSequence)
    {
      if (paramCharSequence != null) {
        return paramCharSequence.toString().trim().toLowerCase();
      }
      return "";
    }
    
    private boolean wasSuccessfullyFilled(InputConnection paramInputConnection)
    {
      CharSequence localCharSequence = VoiceRecognition.getTextBeforeCursor(paramInputConnection);
      if (localCharSequence == null) {}
      for (;;)
      {
        return false;
        String str1 = trimAndLowerCase(localCharSequence);
        String str2 = trimAndLowerCase(this.initialTextBeforeCursor);
        String str3 = trimAndLowerCase(this.fill);
        boolean bool = str1.endsWith(str3);
        if (str1.length() >= str2.length() + str3.length()) {}
        for (int i = 1; (bool) && (i != 0); i = 0) {
          return true;
        }
      }
    }
    
    public void fill(InputEventModel paramInputEventModel)
    {
      paramInputEventModel.handleVoiceInput(this.fill);
    }
    
    public boolean verifiedFill(InputEventModel paramInputEventModel, InputConnection paramInputConnection)
    {
      fill(paramInputEventModel);
      return wasSuccessfullyFilled(paramInputConnection);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.VoiceRecognition
 * JD-Core Version:    0.7.0.1
 */