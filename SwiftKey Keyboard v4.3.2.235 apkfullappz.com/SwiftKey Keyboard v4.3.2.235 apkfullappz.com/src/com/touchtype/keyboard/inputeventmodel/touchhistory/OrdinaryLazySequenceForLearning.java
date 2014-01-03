package com.touchtype.keyboard.inputeventmodel.touchhistory;

import com.touchtype_fluency.Sequence;
import com.touchtype_fluency.Sequence.Type;
import com.touchtype_fluency.service.TokenizationProvider;

public final class OrdinaryLazySequenceForLearning
  implements LazySequence
{
  private final String mFieldHint;
  private final int mStartOffset;
  private final String mText;
  private final TokenizationProvider mTokenizationProvider;
  
  public OrdinaryLazySequenceForLearning(String paramString1, int paramInt, String paramString2, TokenizationProvider paramTokenizationProvider)
  {
    this.mText = paramString1;
    this.mStartOffset = paramInt;
    this.mFieldHint = paramString2;
    this.mTokenizationProvider = paramTokenizationProvider;
  }
  
  public Sequence get()
  {
    if (this.mText.length() > 2048) {
      return new Sequence();
    }
    String str = LearnWordsInContextFilter.filter(this.mText, this.mStartOffset);
    Sequence localSequence = this.mTokenizationProvider.getSequence(str);
    if (localSequence == null) {
      return new Sequence();
    }
    if (this.mFieldHint.length() > 0) {
      localSequence.setFieldHint(this.mFieldHint);
    }
    if (this.mStartOffset == 0)
    {
      localSequence.setType(Sequence.Type.MESSAGE_START);
      return localSequence;
    }
    localSequence.setType(Sequence.Type.NORMAL);
    return localSequence;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.inputeventmodel.touchhistory.OrdinaryLazySequenceForLearning
 * JD-Core Version:    0.7.0.1
 */