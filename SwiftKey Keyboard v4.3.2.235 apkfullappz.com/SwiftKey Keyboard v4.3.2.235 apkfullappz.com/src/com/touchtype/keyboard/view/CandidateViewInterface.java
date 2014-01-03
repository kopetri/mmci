package com.touchtype.keyboard.view;

import android.view.View;
import com.touchtype.keyboard.candidates.Candidate;
import java.util.List;

public abstract interface CandidateViewInterface
{
  public abstract View getView();
  
  public abstract boolean hasDifferentPadding(float paramFloat1, float paramFloat2);
  
  public abstract void updateCandidateKeys(List<Candidate> paramList, boolean paramBoolean);
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.view.CandidateViewInterface
 * JD-Core Version:    0.7.0.1
 */