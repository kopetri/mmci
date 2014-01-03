package com.touchtype.keyboard.inputeventmodel.handlers;

import com.touchtype.keyboard.candidates.Candidate;
import com.touchtype.keyboard.candidates.CandidatesUpdateRequestType;
import com.touchtype.keyboard.inputeventmodel.DefaultPredictionProvider;
import com.touchtype.keyboard.inputeventmodel.InputConnectionProxy;
import com.touchtype.keyboard.inputeventmodel.events.ConnectionInputEvent;
import com.touchtype.keyboard.inputeventmodel.events.SoftKeyInputEvent;
import com.touchtype_fluency.service.ImmutableExtractedText;
import com.touchtype_fluency.service.TouchTypeExtractedText;

public final class FlowBegunEventHandler
  implements ConnectionInputEventHandler
{
  private final DefaultPredictionProvider mDefaultPredictionProvider;
  private KeyInputEventHandler mKeyEventHandler;
  private final Punctuator mPunctuator;
  
  public FlowBegunEventHandler(DefaultPredictionProvider paramDefaultPredictionProvider, Punctuator paramPunctuator)
  {
    this.mDefaultPredictionProvider = paramDefaultPredictionProvider;
    this.mPunctuator = paramPunctuator;
  }
  
  public void handleInput(InputConnectionProxy paramInputConnectionProxy, ConnectionInputEvent paramConnectionInputEvent)
    throws UnhandledInputEventException
  {
    TouchTypeExtractedText localTouchTypeExtractedText = paramInputConnectionProxy.getTouchTypeExtractedText(false);
    if (localTouchTypeExtractedText.getCurrentWord().length() == 1)
    {
      String str = this.mDefaultPredictionProvider.getDefaultPrediction(true, CandidatesUpdateRequestType.DEFAULT).toString();
      if ((str.length() == 1) && (Character.isLetterOrDigit(str.charAt(0))))
      {
        SoftKeyInputEvent localSoftKeyInputEvent = new SoftKeyInputEvent(this.mPunctuator.getWordSeparator(localTouchTypeExtractedText).charAt(0), false);
        this.mKeyEventHandler.handleInput(paramInputConnectionProxy, localSoftKeyInputEvent);
      }
    }
  }
  
  public boolean logKeyStroke()
  {
    return false;
  }
  
  public void setDelegateHandlers(KeyInputEventHandler paramKeyInputEventHandler)
  {
    this.mKeyEventHandler = paramKeyInputEventHandler;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.inputeventmodel.handlers.FlowBegunEventHandler
 * JD-Core Version:    0.7.0.1
 */