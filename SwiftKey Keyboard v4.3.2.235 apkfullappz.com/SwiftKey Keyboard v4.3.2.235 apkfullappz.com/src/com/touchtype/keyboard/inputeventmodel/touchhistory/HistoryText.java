package com.touchtype.keyboard.inputeventmodel.touchhistory;

import android.text.SpannableStringBuilder;
import android.view.inputmethod.ExtractedText;
import com.google.common.collect.Range;
import com.touchtype_fluency.service.TokenizationProvider;
import com.touchtype_fluency.service.TouchTypeExtractedText;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HistoryText
  extends TouchTypeExtractedText
{
  private static final String TAG = HistoryText.class.getSimpleName();
  
  public HistoryText(ExtractedText paramExtractedText, TokenizationProvider paramTokenizationProvider)
  {
    super(paramExtractedText, paramTokenizationProvider);
    if (this.text == null)
    {
      this.text = "";
      this.selectionEnd = 0;
      this.selectionStart = 0;
      this.startOffset = 0;
      this.partialStartOffset = 0;
      this.partialEndOffset = 0;
    }
    this.text = new SpannableStringBuilder(this.text);
  }
  
  private TouchHistoryMarker[] getAllHistoryMarkers()
  {
    return (TouchHistoryMarker[])getTextSpannable().getSpans(0, this.text.length(), TouchHistoryMarker.class);
  }
  
  private CursorMarker getCurrentCursorMarker(Range<Integer> paramRange)
  {
    CursorMarker[] arrayOfCursorMarker = (CursorMarker[])getTextSpannable().getSpans(((Integer)paramRange.lowerEndpoint()).intValue(), ((Integer)paramRange.upperEndpoint()).intValue(), CursorMarker.class);
    if (arrayOfCursorMarker.length == 1) {
      return arrayOfCursorMarker[0];
    }
    return null;
  }
  
  private void setMarker(CursorMarker paramCursorMarker, Range<Integer> paramRange)
  {
    getTextSpannable().setSpan(paramCursorMarker, ((Integer)paramRange.lowerEndpoint()).intValue(), ((Integer)paramRange.upperEndpoint()).intValue(), 17);
  }
  
  public TouchHistoryMarker getCurrentHistoryMarker()
  {
    int i = getSelectionStartInText();
    return getHistoryMarker(i - getCurrentWord().length(), i);
  }
  
  public CursorMarker getCursorMarker(int paramInt, boolean paramBoolean)
  {
    CursorMarker localCursorMarker1 = CursorMarker.createStationaryCursorMarker();
    Range localRange = getCurrentWordBounds();
    if ((!paramBoolean) && (!localRange.isEmpty()) && (!localRange.contains(Integer.valueOf(paramInt))))
    {
      localCursorMarker1 = getCurrentCursorMarker(localRange);
      if (localCursorMarker1 == null)
      {
        localCursorMarker1 = CursorMarker.createJumpingCursorMarker(localRange);
        setMarker(localCursorMarker1, localRange);
      }
    }
    for (CursorMarker localCursorMarker2 : (CursorMarker[])getTextSpannable().getSpans(0, this.text.length(), CursorMarker.class)) {
      if (localCursorMarker2 != localCursorMarker1) {
        getTextSpannable().removeSpan(localCursorMarker2);
      }
    }
    return localCursorMarker1;
  }
  
  public int getEnd(TouchHistoryMarker paramTouchHistoryMarker)
  {
    return getTextSpannable().getSpanEnd(paramTouchHistoryMarker);
  }
  
  public TouchHistoryMarker getHistoryMarker(int paramInt1, int paramInt2)
  {
    TouchHistoryMarker[] arrayOfTouchHistoryMarker = (TouchHistoryMarker[])getTextSpannable().getSpans(paramInt1, paramInt2, TouchHistoryMarker.class);
    ArrayList localArrayList = new ArrayList();
    int i = arrayOfTouchHistoryMarker.length;
    for (int j = 0; j < i; j++)
    {
      TouchHistoryMarker localTouchHistoryMarker = arrayOfTouchHistoryMarker[j];
      if ((getStart(localTouchHistoryMarker) == paramInt1) && (getEnd(localTouchHistoryMarker) == paramInt2)) {
        localArrayList.add(localTouchHistoryMarker);
      }
    }
    if (localArrayList.size() == 0) {}
    for (;;)
    {
      return null;
      if (localArrayList.size() <= 1) {
        break;
      }
      Iterator localIterator = localArrayList.iterator();
      while (localIterator.hasNext()) {
        removeMarker((TouchHistoryMarker)localIterator.next());
      }
    }
    return (TouchHistoryMarker)localArrayList.get(0);
  }
  
  public TouchHistoryMarker getHistoryMarkerEndingAt(int paramInt)
  {
    TouchHistoryMarker[] arrayOfTouchHistoryMarker = (TouchHistoryMarker[])getTextSpannable().getSpans(paramInt, paramInt, TouchHistoryMarker.class);
    ArrayList localArrayList = new ArrayList();
    int i = arrayOfTouchHistoryMarker.length;
    for (int j = 0; j < i; j++)
    {
      TouchHistoryMarker localTouchHistoryMarker = arrayOfTouchHistoryMarker[j];
      if (getEnd(localTouchHistoryMarker) == paramInt) {
        localArrayList.add(localTouchHistoryMarker);
      }
    }
    if (localArrayList.size() == 1) {
      return (TouchHistoryMarker)localArrayList.get(0);
    }
    localArrayList.size();
    return null;
  }
  
  public int getStart(TouchHistoryMarker paramTouchHistoryMarker)
  {
    return getTextSpannable().getSpanStart(paramTouchHistoryMarker);
  }
  
  SpannableStringBuilder getTextSpannable()
  {
    return (SpannableStringBuilder)this.text;
  }
  
  public void removeAllMarkers()
  {
    SpannableStringBuilder localSpannableStringBuilder = getTextSpannable();
    TouchHistoryMarker[] arrayOfTouchHistoryMarker = getAllHistoryMarkers();
    int i = arrayOfTouchHistoryMarker.length;
    for (int j = 0; j < i; j++) {
      localSpannableStringBuilder.removeSpan(arrayOfTouchHistoryMarker[j]);
    }
  }
  
  public void removeHistoryMarkersWithSameStart(int paramInt, TouchHistoryMarker paramTouchHistoryMarker)
  {
    for (TouchHistoryMarker localTouchHistoryMarker : (TouchHistoryMarker[])getTextSpannable().getSpans(paramInt, paramInt, TouchHistoryMarker.class)) {
      if ((getStart(localTouchHistoryMarker) == paramInt) && (!localTouchHistoryMarker.equals(paramTouchHistoryMarker))) {
        removeMarker(localTouchHistoryMarker);
      }
    }
  }
  
  public void removeMarker(TouchHistoryMarker paramTouchHistoryMarker)
  {
    getTextSpannable().removeSpan(paramTouchHistoryMarker);
  }
  
  public void setMarker(TouchHistoryMarker paramTouchHistoryMarker, int paramInt1, int paramInt2)
  {
    getTextSpannable().setSpan(paramTouchHistoryMarker, paramInt1, paramInt2, 17);
  }
  
  protected void setText(CharSequence paramCharSequence)
  {
    if ((paramCharSequence instanceof SpannableStringBuilder)) {}
    for (this.text = paramCharSequence;; this.text = new SpannableStringBuilder(paramCharSequence))
    {
      this.selectionEnd = 0;
      this.selectionStart = 0;
      return;
    }
  }
  
  public void stripNegativeWidthSpans()
  {
    if ((this.text instanceof SpannableStringBuilder))
    {
      SpannableStringBuilder localSpannableStringBuilder = (SpannableStringBuilder)this.text;
      Object[] arrayOfObject = localSpannableStringBuilder.getSpans(0, this.text.length(), Object.class);
      for (int i = 0; i < arrayOfObject.length; i++)
      {
        Object localObject = arrayOfObject[i];
        if (localSpannableStringBuilder.getSpanStart(localObject) > localSpannableStringBuilder.getSpanEnd(localObject)) {
          localSpannableStringBuilder.removeSpan(localObject);
        }
      }
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.inputeventmodel.touchhistory.HistoryText
 * JD-Core Version:    0.7.0.1
 */