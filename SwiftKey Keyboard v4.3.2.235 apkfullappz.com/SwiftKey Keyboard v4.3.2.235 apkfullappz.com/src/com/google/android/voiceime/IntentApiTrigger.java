package com.google.android.voiceime;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.inputmethodservice.InputMethodService;
import android.os.Handler;
import android.os.IBinder;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.view.inputmethod.ExtractedText;
import android.view.inputmethod.ExtractedTextRequest;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

final class IntentApiTrigger
  implements Trigger
{
  private final Handler mHandler;
  private final InputMethodService mInputMethodService;
  private String mLastRecognitionResult;
  private final ServiceBridge mServiceBridge;
  private IBinder mToken;
  private Set<Character> mUpperCaseChars;
  
  public IntentApiTrigger(InputMethodService paramInputMethodService)
  {
    this.mInputMethodService = paramInputMethodService;
    this.mServiceBridge = new ServiceBridge(new Callback()
    {
      public void onRecognitionResult(String paramAnonymousString)
      {
        IntentApiTrigger.this.postResult(paramAnonymousString);
      }
    });
    this.mUpperCaseChars = new HashSet();
    this.mUpperCaseChars.add(Character.valueOf('.'));
    this.mUpperCaseChars.add(Character.valueOf('!'));
    this.mUpperCaseChars.add(Character.valueOf('?'));
    this.mUpperCaseChars.add(Character.valueOf('\n'));
    this.mHandler = new Handler();
  }
  
  private void commitResult()
  {
    if (this.mLastRecognitionResult == null) {}
    String str;
    InputConnection localInputConnection;
    do
    {
      return;
      str = this.mLastRecognitionResult;
      localInputConnection = this.mInputMethodService.getCurrentInputConnection();
    } while ((localInputConnection == null) || (!localInputConnection.beginBatchEdit()));
    try
    {
      ExtractedTextRequest localExtractedTextRequest = new ExtractedTextRequest();
      localExtractedTextRequest.flags = 1;
      ExtractedText localExtractedText = localInputConnection.getExtractedText(localExtractedTextRequest, 0);
      if (localExtractedText == null) {}
      do
      {
        return;
        if (localExtractedText.text != null)
        {
          if (localExtractedText.selectionStart != localExtractedText.selectionEnd) {
            localInputConnection.deleteSurroundingText(localExtractedText.selectionStart, localExtractedText.selectionEnd);
          }
          str = format(localExtractedText, str);
        }
      } while (!localInputConnection.commitText(str, 0));
      this.mLastRecognitionResult = null;
      return;
    }
    finally
    {
      localInputConnection.endBatchEdit();
    }
  }
  
  private String format(ExtractedText paramExtractedText, String paramString)
  {
    for (int i = -1 + paramExtractedText.selectionStart;; i--) {
      if ((i <= 0) || (!Character.isWhitespace(paramExtractedText.text.charAt(i))))
      {
        if ((i == -1) || (this.mUpperCaseChars.contains(Character.valueOf(paramExtractedText.text.charAt(i))))) {
          paramString = Character.toUpperCase(paramString.charAt(0)) + paramString.substring(1);
        }
        if ((-1 + paramExtractedText.selectionStart > 0) && (!Character.isWhitespace(paramExtractedText.text.charAt(-1 + paramExtractedText.selectionStart)))) {
          paramString = " " + paramString;
        }
        if ((paramExtractedText.selectionEnd < paramExtractedText.text.length()) && (!Character.isWhitespace(paramExtractedText.text.charAt(paramExtractedText.selectionEnd)))) {
          paramString = paramString + " ";
        }
        return paramString;
      }
    }
  }
  
  private InputMethodManager getInputMethodManager()
  {
    return (InputMethodManager)this.mInputMethodService.getSystemService("input_method");
  }
  
  public static boolean isInstalled(InputMethodService paramInputMethodService)
  {
    int i = paramInputMethodService.getPackageManager().queryIntentActivities(new Intent("android.speech.action.RECOGNIZE_SPEECH"), 0).size();
    boolean bool = false;
    if (i > 0) {
      bool = true;
    }
    return bool;
  }
  
  private void postResult(String paramString)
  {
    this.mLastRecognitionResult = paramString;
    getInputMethodManager().showSoftInputFromInputMethod(this.mToken, 1);
  }
  
  private void scheduleCommit()
  {
    this.mHandler.post(new Runnable()
    {
      public void run()
      {
        IntentApiTrigger.this.commitResult();
      }
    });
  }
  
  public void onStartInputView()
  {
    if (this.mLastRecognitionResult != null) {
      scheduleCommit();
    }
  }
  
  public void startVoiceRecognition(String paramString)
  {
    this.mToken = this.mInputMethodService.getWindow().getWindow().getAttributes().token;
    this.mServiceBridge.startVoiceRecognition(this.mInputMethodService, paramString);
  }
  
  static abstract interface Callback
  {
    public abstract void onRecognitionResult(String paramString);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.voiceime.IntentApiTrigger
 * JD-Core Version:    0.7.0.1
 */