package com.google.android.voiceime;

import android.inputmethodservice.InputMethodService;

public final class VoiceRecognitionTrigger
{
  private ImeTrigger mImeTrigger;
  private final InputMethodService mInputMethodService;
  private IntentApiTrigger mIntentApiTrigger;
  private Trigger mTrigger;
  
  public VoiceRecognitionTrigger(InputMethodService paramInputMethodService)
  {
    this.mInputMethodService = paramInputMethodService;
    this.mTrigger = getTrigger();
  }
  
  private Trigger getImeTrigger()
  {
    if (this.mImeTrigger == null) {
      this.mImeTrigger = new ImeTrigger(this.mInputMethodService);
    }
    return this.mImeTrigger;
  }
  
  private Trigger getIntentTrigger()
  {
    if (this.mIntentApiTrigger == null) {
      this.mIntentApiTrigger = new IntentApiTrigger(this.mInputMethodService);
    }
    return this.mIntentApiTrigger;
  }
  
  private Trigger getTrigger()
  {
    if (ImeTrigger.isInstalled(this.mInputMethodService)) {
      return getImeTrigger();
    }
    if (IntentApiTrigger.isInstalled(this.mInputMethodService)) {
      return getIntentTrigger();
    }
    return null;
  }
  
  public void onStartInputView()
  {
    if (this.mTrigger != null) {
      this.mTrigger.onStartInputView();
    }
    this.mTrigger = getTrigger();
  }
  
  public void startVoiceRecognition()
  {
    startVoiceRecognition(null);
  }
  
  public void startVoiceRecognition(String paramString)
  {
    if (this.mTrigger != null) {
      this.mTrigger.startVoiceRecognition(paramString);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.voiceime.VoiceRecognitionTrigger
 * JD-Core Version:    0.7.0.1
 */