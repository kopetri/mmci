package com.touchtype.keyboard.inputeventmodel.handlers;

import com.touchtype.keyboard.inputeventmodel.InputConnectionProxy;
import com.touchtype.keyboard.inputeventmodel.KeyboardState;
import com.touchtype.keyboard.inputeventmodel.events.ConnectionInputEvent;
import com.touchtype.keyboard.inputeventmodel.events.SelectionChangedInputEvent;
import com.touchtype.keyboard.inputeventmodel.touchhistory.CursorMarker;
import com.touchtype.keyboard.inputeventmodel.touchhistory.TouchHistoryManager;
import com.touchtype_fluency.service.ImmutableExtractedText;
import com.touchtype_fluency.service.TouchTypeExtractedText;

public final class CursorInputEventHandler
  implements ConnectionInputEventHandler
{
  private KeyboardState mKeyboardState;
  private ConnectionInputEventHandler mShiftStateHandler;
  private TouchHistoryManager mTouchHistoryManager;
  
  public CursorInputEventHandler(KeyboardState paramKeyboardState, TouchHistoryManager paramTouchHistoryManager)
  {
    this.mKeyboardState = paramKeyboardState;
    this.mTouchHistoryManager = paramTouchHistoryManager;
  }
  
  public void handleInput(InputConnectionProxy paramInputConnectionProxy, ConnectionInputEvent paramConnectionInputEvent)
    throws UnhandledInputEventException
  {
    TouchTypeExtractedText localTouchTypeExtractedText = paramInputConnectionProxy.getTouchTypeExtractedText(false);
    if ((paramConnectionInputEvent instanceof SelectionChangedInputEvent))
    {
      SelectionChangedInputEvent localSelectionChangedInputEvent = (SelectionChangedInputEvent)paramConnectionInputEvent;
      if ((localSelectionChangedInputEvent.getStart() != localSelectionChangedInputEvent.getEnd()) || (localTouchTypeExtractedText.getSelectionStartInField() != localTouchTypeExtractedText.getSelectionEndInField()))
      {
        paramInputConnectionProxy.nonZeroLengthSelectionMade(localTouchTypeExtractedText.getSelectionStartInField(), localTouchTypeExtractedText.getSelectionEndInField());
        paramInputConnectionProxy.finishComposingText();
        this.mShiftStateHandler.handleInput(paramInputConnectionProxy, paramConnectionInputEvent);
      }
      CursorMarker localCursorMarker;
      do
      {
        do
        {
          do
          {
            return;
            if (this.mKeyboardState.composeTextWordByWord())
            {
              int j = localTouchTypeExtractedText.getSelectionEndInField();
              int k = j - localTouchTypeExtractedText.getCurrentWord().length();
              int m = localTouchTypeExtractedText.getLastCharacter();
              if ((k != localSelectionChangedInputEvent.getComposingRegionStart()) || (j != localSelectionChangedInputEvent.getComposingRegionEnd()) || ((!TouchTypeExtractedText.isSpace(m)) && ((m == 0) || (m == 10)))) {
                paramInputConnectionProxy.setComposingRegion(k, j, localTouchTypeExtractedText);
              }
            }
          } while (!localSelectionChangedInputEvent.hasMoved());
          this.mShiftStateHandler.handleInput(paramInputConnectionProxy, paramConnectionInputEvent);
        } while ((!this.mKeyboardState.isPredictionEnabled()) || (!localSelectionChangedInputEvent.hasMovedAbruptly()));
        localCursorMarker = this.mTouchHistoryManager.getCursorMarker(localTouchTypeExtractedText, localSelectionChangedInputEvent.getOldEnd());
      } while ((localCursorMarker == null) || (!localCursorMarker.shouldJump()));
      int i = localCursorMarker.jumpTo();
      paramInputConnectionProxy.setSelection(i, i);
      localCursorMarker.jumped();
      return;
    }
    throw new UnhandledInputEventException("Unrecognised Cursor InputEvent: " + paramConnectionInputEvent);
  }
  
  public boolean logKeyStroke()
  {
    return false;
  }
  
  public void setDelegateHandlers(ConnectionInputEventHandler paramConnectionInputEventHandler)
  {
    this.mShiftStateHandler = paramConnectionInputEventHandler;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.inputeventmodel.handlers.CursorInputEventHandler
 * JD-Core Version:    0.7.0.1
 */