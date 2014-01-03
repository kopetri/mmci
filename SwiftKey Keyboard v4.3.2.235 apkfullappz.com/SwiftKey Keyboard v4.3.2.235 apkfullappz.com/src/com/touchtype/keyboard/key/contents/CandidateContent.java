package com.touchtype.keyboard.key.contents;

import com.google.common.base.Objects;
import com.google.common.collect.Sets;
import com.touchtype.keyboard.candidates.Candidate;
import com.touchtype.keyboard.key.KeyState;
import com.touchtype.keyboard.keys.view.ResizeDrawable;
import com.touchtype.keyboard.service.TouchTypeSoftKeyboard.ShiftState;
import com.touchtype.keyboard.theme.KeyStyle.StyleId;
import com.touchtype.keyboard.theme.KeyStyle.SubStyle;
import com.touchtype.keyboard.theme.renderer.ThemeRenderer;
import com.touchtype.keyboard.theme.util.TextRendering.HAlign;
import com.touchtype.keyboard.theme.util.TextRendering.VAlign;
import java.util.Locale;
import java.util.Set;

public final class CandidateContent
  extends TextContent
{
  private Candidate mCandidate = Candidate.empty();
  private float mHeightLimit;
  
  public CandidateContent()
  {
    this(TextRendering.HAlign.CENTRE, TextRendering.VAlign.CENTRE, 0.6F);
  }
  
  private CandidateContent(TextRendering.HAlign paramHAlign, TextRendering.VAlign paramVAlign, float paramFloat)
  {
    super("", "", Locale.getDefault(), paramHAlign, paramVAlign, paramFloat);
    this.mHeightLimit = paramFloat;
  }
  
  public KeyContent applyKeyState(KeyState paramKeyState)
  {
    return this;
  }
  
  public TextContent applyShiftState(TouchTypeSoftKeyboard.ShiftState paramShiftState)
  {
    return this;
  }
  
  public boolean equals(Object paramObject)
  {
    if (paramObject == null) {}
    CandidateContent localCandidateContent;
    do
    {
      do
      {
        return false;
      } while (!(paramObject instanceof CandidateContent));
      if (paramObject == this) {
        return true;
      }
      localCandidateContent = (CandidateContent)paramObject;
    } while ((!super.equals(paramObject)) || (!this.mCandidate.equals(localCandidateContent.mCandidate)) || (this.mHeightLimit != localCandidateContent.mHeightLimit));
    return true;
  }
  
  public float getHeightLimit()
  {
    return this.mHeightLimit;
  }
  
  public Set<String> getInputStrings()
  {
    String[] arrayOfString = new String[1];
    arrayOfString[0] = getLabel();
    return Sets.newHashSet(arrayOfString);
  }
  
  public String getLabel()
  {
    return this.mCandidate.toString();
  }
  
  public String getText()
  {
    return this.mCandidate.toString();
  }
  
  public int hashCode()
  {
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = Integer.valueOf(super.hashCode());
    arrayOfObject[1] = this.mCandidate;
    arrayOfObject[2] = Float.valueOf(this.mHeightLimit);
    return Objects.hashCode(arrayOfObject);
  }
  
  public ResizeDrawable render(ThemeRenderer paramThemeRenderer, KeyStyle.StyleId paramStyleId, KeyStyle.SubStyle paramSubStyle)
  {
    return paramThemeRenderer.getContentDrawable(this, paramStyleId, paramSubStyle);
  }
  
  public void setHeightLimit(float paramFloat)
  {
    this.mHeightLimit = paramFloat;
  }
  
  public void updateCandidate(Candidate paramCandidate)
  {
    this.mCandidate = paramCandidate;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.key.contents.CandidateContent
 * JD-Core Version:    0.7.0.1
 */