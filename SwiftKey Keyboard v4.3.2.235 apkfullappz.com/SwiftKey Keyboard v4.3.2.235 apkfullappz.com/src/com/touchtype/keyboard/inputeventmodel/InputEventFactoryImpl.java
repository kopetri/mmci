package com.touchtype.keyboard.inputeventmodel;

import android.annotation.TargetApi;
import android.os.Build.VERSION;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.inputmethod.CompletionInfo;
import com.touchtype.keyboard.candidates.Candidate;
import com.touchtype.keyboard.inputeventmodel.events.CompletionInputEvent;
import com.touchtype.keyboard.inputeventmodel.events.ConnectionInputEvent;
import com.touchtype.keyboard.inputeventmodel.events.ConnectionlessInputEvent;
import com.touchtype.keyboard.inputeventmodel.events.DeleteInputEvent;
import com.touchtype.keyboard.inputeventmodel.events.DeleteInputEvent.DeleteType;
import com.touchtype.keyboard.inputeventmodel.events.FlowBegunEvent;
import com.touchtype.keyboard.inputeventmodel.events.FlowCompleteEvent;
import com.touchtype.keyboard.inputeventmodel.events.HardKeyInputEvent;
import com.touchtype.keyboard.inputeventmodel.events.MultipleFlowSamplesEvent;
import com.touchtype.keyboard.inputeventmodel.events.PredictionInputEvent;
import com.touchtype.keyboard.inputeventmodel.events.SelectionChangedInputEvent;
import com.touchtype.keyboard.inputeventmodel.events.SingleFlowSampleEvent;
import com.touchtype.keyboard.inputeventmodel.events.SoftKeyInputEvent;
import com.touchtype.keyboard.inputeventmodel.events.TextInputEvent;
import com.touchtype.keyboard.inputeventmodel.events.UpdateShiftStateEvent;
import com.touchtype.keyboard.inputeventmodel.events.VoiceInputEvent;
import com.touchtype.keyboard.inputeventmodel.keytranslation.KeyTranslationLayer;
import com.touchtype.keyboard.inputeventmodel.keytranslation.KeyTranslationLayer.TranslationKeyEvent;
import com.touchtype.keyboard.key.actions.ActionType;
import com.touchtype.keyboard.view.touch.FlowEvent;
import com.touchtype.keyboard.view.touch.LegacyTouchUtils;
import com.touchtype_fluency.service.TouchTypeExtractedText;
import java.util.EnumSet;
import java.util.List;

public final class InputEventFactoryImpl
  implements InputEventFactory
{
  private final int ENTER = 10;
  private final int NON_PRINTABLE_KEY = 0;
  private final InputConnectionProxy mInputConnectionProxy;
  private final KeyTranslationLayer mKeyTranslationLayer;
  private final LegacyTouchUtils mLTUs;
  
  public InputEventFactoryImpl(InputConnectionProxy paramInputConnectionProxy, KeyTranslationLayer paramKeyTranslationLayer, LegacyTouchUtils paramLegacyTouchUtils)
  {
    this.mInputConnectionProxy = paramInputConnectionProxy;
    this.mKeyTranslationLayer = paramKeyTranslationLayer;
    this.mLTUs = paramLegacyTouchUtils;
  }
  
  private static boolean delegateOnKeyRepeats(KeyEvent paramKeyEvent, boolean paramBoolean)
  {
    if (paramBoolean) {}
    while (paramKeyEvent.getRepeatCount() <= 0) {
      return false;
    }
    return true;
  }
  
  private TouchTypeExtractedText getExtractedText()
  {
    TouchTypeExtractedText localTouchTypeExtractedText = this.mInputConnectionProxy.getTouchTypeExtractedText(true);
    if ((localTouchTypeExtractedText != null) && ((localTouchTypeExtractedText.selectionStart < 0) || (localTouchTypeExtractedText.selectionEnd < 0)))
    {
      localTouchTypeExtractedText.selectionEnd = 0;
      localTouchTypeExtractedText.selectionStart = 0;
    }
    return localTouchTypeExtractedText;
  }
  
  @TargetApi(11)
  private static boolean isCtrlShortcut(KeyEvent paramKeyEvent)
  {
    if (Build.VERSION.SDK_INT >= 11) {
      return paramKeyEvent.isCtrlPressed();
    }
    return false;
  }
  
  @TargetApi(11)
  public static boolean isFullKeyboard(KeyEvent paramKeyEvent)
  {
    if (Build.VERSION.SDK_INT < 11) {}
    while (paramKeyEvent.getKeyCharacterMap().getKeyboardType() != 4) {
      return false;
    }
    return true;
  }
  
  @TargetApi(12)
  private static boolean isGamepadButton(int paramInt)
  {
    boolean bool;
    if (Build.VERSION.SDK_INT > 11) {
      bool = KeyEvent.isGamepadButton(paramInt);
    }
    do
    {
      do
      {
        int i;
        do
        {
          return bool;
          i = Build.VERSION.SDK_INT;
          bool = false;
        } while (i < 9);
        bool = false;
      } while (paramInt < 96);
      bool = false;
    } while (paramInt > 110);
    return true;
  }
  
  private static boolean isPrivateUseChar(int paramInt)
  {
    return Character.getType(paramInt) == 18;
  }
  
  public DeleteInputEvent createDeleteLastWordEvent(EnumSet<ActionType> paramEnumSet)
  {
    return new DeleteInputEvent(DeleteInputEvent.DeleteType.WORD, paramEnumSet);
  }
  
  public CompletionInputEvent createEventFromCompletion(CompletionInfo paramCompletionInfo)
  {
    return new CompletionInputEvent(paramCompletionInfo);
  }
  
  public ConnectionInputEvent createEventFromHardKey(KeyEvent paramKeyEvent, int paramInt, boolean paramBoolean)
  {
    boolean bool = isFullKeyboard(paramKeyEvent);
    if ((isCtrlShortcut(paramKeyEvent)) || (paramBoolean) || (delegateOnKeyRepeats(paramKeyEvent, bool))) {}
    int i;
    int j;
    do
    {
      return null;
      i = this.mKeyTranslationLayer.getUnicodeChar(new KeyTranslationLayer.TranslationKeyEvent(paramKeyEvent), paramInt);
      j = paramKeyEvent.getKeyCode();
      if ((j == 67) || (j == -5)) {
        return new DeleteInputEvent(DeleteInputEvent.DeleteType.CHARACTER, null);
      }
    } while ((j == 23) || (i == 0) || (i == 10) || (isPrivateUseChar(i)) || (isGamepadButton(j)));
    return new HardKeyInputEvent(i, bool);
  }
  
  public ConnectionlessInputEvent createEventFromPoint(FlowEvent paramFlowEvent)
  {
    return new SingleFlowSampleEvent(paramFlowEvent, this.mLTUs);
  }
  
  public ConnectionlessInputEvent createEventFromPoints(List<FlowEvent> paramList)
  {
    return new MultipleFlowSamplesEvent(paramList, this.mLTUs);
  }
  
  public PredictionInputEvent createEventFromPrediction(Candidate paramCandidate)
  {
    return new PredictionInputEvent(paramCandidate);
  }
  
  public ConnectionInputEvent createEventFromSoftKey(KeyEvent paramKeyEvent)
  {
    if (paramKeyEvent.getAction() == 2) {
      return new TextInputEvent(paramKeyEvent.getCharacters());
    }
    switch (paramKeyEvent.getKeyCode())
    {
    default: 
      return new SoftKeyInputEvent(paramKeyEvent, this.mLTUs);
    case 8: 
      return new DeleteInputEvent(DeleteInputEvent.DeleteType.CHARACTER, null);
    }
    return null;
  }
  
  public VoiceInputEvent createEventFromVoiceInput(CharSequence paramCharSequence)
  {
    return new VoiceInputEvent(getExtractedText(), paramCharSequence);
  }
  
  public FlowBegunEvent createFlowBegunEvent()
  {
    return new FlowBegunEvent();
  }
  
  public FlowCompleteEvent createFlowCompleteEvent()
  {
    return new FlowCompleteEvent();
  }
  
  public SelectionChangedInputEvent createResetComposingTextEvent(int paramInt)
  {
    TouchTypeExtractedText localTouchTypeExtractedText = getExtractedText();
    if ((localTouchTypeExtractedText == null) || (localTouchTypeExtractedText.text == null)) {
      throw new ExtractedTextUnavailableException("Could not create reset composing text event");
    }
    int i = paramInt;
    if (paramInt < 0)
    {
      localTouchTypeExtractedText.getSelectionStartInField();
      paramInt = localTouchTypeExtractedText.getSelectionEndInField();
    }
    return new SelectionChangedInputEvent(paramInt, paramInt, paramInt, paramInt, -2, i);
  }
  
  public SelectionChangedInputEvent createSelectionChangedEvent(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    TouchTypeExtractedText localTouchTypeExtractedText = getExtractedText();
    if ((localTouchTypeExtractedText == null) || (localTouchTypeExtractedText.text == null)) {
      throw new ExtractedTextUnavailableException("Could not create selection changed event");
    }
    if ((paramInt3 < 0) || (paramInt4 < 0))
    {
      paramInt3 = localTouchTypeExtractedText.getSelectionStartInField();
      paramInt4 = localTouchTypeExtractedText.getSelectionEndInField();
    }
    if ((paramInt1 < 0) || (paramInt2 < 0))
    {
      paramInt1 = paramInt3;
      paramInt2 = paramInt4;
    }
    return new SelectionChangedInputEvent(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
  }
  
  public UpdateShiftStateEvent createUpdateShiftStateEvent()
  {
    return new UpdateShiftStateEvent();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.inputeventmodel.InputEventFactoryImpl
 * JD-Core Version:    0.7.0.1
 */