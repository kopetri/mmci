package com.touchtype.keyboard;

import com.touchtype.keyboard.inputeventmodel.KeyPressModelLayout;
import com.touchtype.keyboard.key.Key;
import com.touchtype.keyboard.key.KeyFactory.FlowOrSwipe;
import java.util.List;
import java.util.Set;

public final class MainKeyboard
  extends BaseKeyboard<Key>
{
  private final KeyFactory.FlowOrSwipe mFlowOrSwipe;
  private final Set<String> mIntentionalEventFilter;
  private final KeyPressModelLayout mLayout;
  private final LayoutType mLayoutType;
  private final Set<String> mPredictionFilter;
  private final float mSplitEnd;
  private final float mSplitStart;
  
  public MainKeyboard(List<Key> paramList, KeyPressModelLayout paramKeyPressModelLayout, Set<String> paramSet1, Set<String> paramSet2, KeyFactory.FlowOrSwipe paramFlowOrSwipe, LayoutType paramLayoutType, Key paramKey, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    super(paramList, paramKey, paramFloat1);
    this.mLayout = paramKeyPressModelLayout;
    this.mPredictionFilter = paramSet1;
    this.mIntentionalEventFilter = paramSet2;
    this.mFlowOrSwipe = paramFlowOrSwipe;
    this.mLayoutType = paramLayoutType;
    this.mSplitStart = paramFloat2;
    this.mSplitEnd = paramFloat3;
  }
  
  public KeyFactory.FlowOrSwipe getFlowOrSwipe()
  {
    return this.mFlowOrSwipe;
  }
  
  public Set<String> getIntentionalEventFilter()
  {
    return this.mIntentionalEventFilter;
  }
  
  public KeyPressModelLayout getLayout()
  {
    return this.mLayout;
  }
  
  public LayoutType getLayoutType()
  {
    return this.mLayoutType;
  }
  
  public Set<String> getPredictionFilter()
  {
    return this.mPredictionFilter;
  }
  
  public float getSplitEnd()
  {
    return this.mSplitEnd;
  }
  
  public float getSplitStart()
  {
    return this.mSplitStart;
  }
  
  public void mergeFilters(MainKeyboard paramMainKeyboard)
  {
    this.mPredictionFilter.addAll(paramMainKeyboard.mPredictionFilter);
    paramMainKeyboard.mPredictionFilter.addAll(this.mPredictionFilter);
    this.mIntentionalEventFilter.addAll(paramMainKeyboard.mIntentionalEventFilter);
    paramMainKeyboard.mIntentionalEventFilter.addAll(this.mIntentionalEventFilter);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.MainKeyboard
 * JD-Core Version:    0.7.0.1
 */