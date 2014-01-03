package com.touchtype.keyboard.key;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.ViewConfiguration;
import com.touchtype.keyboard.LanguageSwitchData;
import com.touchtype.keyboard.MiniKeyboard;
import com.touchtype.keyboard.key.callbacks.DragEvent;
import com.touchtype.keyboard.key.callbacks.DragEvent.Range;
import com.touchtype.keyboard.key.callbacks.DragFilter;
import com.touchtype.keyboard.key.callbacks.DragFilterFactory;
import com.touchtype.keyboard.key.contents.IconContent;
import com.touchtype.keyboard.key.contents.KeyContent;
import com.touchtype.keyboard.service.TouchTypeSoftKeyboard.ShiftState;
import com.touchtype.keyboard.theme.KeyStyle.StyleId;
import com.touchtype.keyboard.theme.painter.PopupPainter;
import com.touchtype.keyboard.theme.renderer.ThemeRenderer;
import com.touchtype.keyboard.theme.util.TextRendering.HAlign;
import com.touchtype.keyboard.theme.util.TextRendering.VAlign;
import com.touchtype.keyboard.view.PreviewPopup;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public abstract interface PopupContent
{
  public static final PopupContent EMPTY_CONTENT = new EmptyPopupContent(null);
  
  public abstract PopupContent applyShiftState(TouchTypeSoftKeyboard.ShiftState paramShiftState);
  
  public abstract Set<String> getInputStrings();
  
  public abstract KeyStyle.StyleId getStyleId();
  
  public abstract PopupPainter<PreviewPopup> render(ThemeRenderer paramThemeRenderer, KeyArea paramKeyArea, KeyStyle.StyleId paramStyleId);
  
  public abstract void setDrag(DragEvent paramDragEvent);
  
  public static abstract class AnimatedPopupContent
    implements PopupContent
  {
    protected final int mAnimationResource;
    
    protected AnimatedPopupContent(int paramInt)
    {
      this.mAnimationResource = paramInt;
    }
    
    public int getAnimation()
    {
      return this.mAnimationResource;
    }
  }
  
  public static final class CasedPopupContent
    implements PopupContent
  {
    private final PopupContent mLowerCase;
    private boolean mShifted = false;
    private final PopupContent mUpperCase;
    
    public CasedPopupContent(PopupContent paramPopupContent1, PopupContent paramPopupContent2)
    {
      this.mLowerCase = paramPopupContent1;
      this.mUpperCase = paramPopupContent2;
    }
    
    private PopupContent getCurrent()
    {
      if (this.mShifted) {
        return this.mUpperCase;
      }
      return this.mLowerCase;
    }
    
    public PopupContent applyShiftState(TouchTypeSoftKeyboard.ShiftState paramShiftState)
    {
      if ((paramShiftState == TouchTypeSoftKeyboard.ShiftState.SHIFTED) || (paramShiftState == TouchTypeSoftKeyboard.ShiftState.CAPSLOCKED)) {}
      for (boolean bool = true;; bool = false)
      {
        this.mShifted = bool;
        return getCurrent();
      }
    }
    
    public Set<String> getInputStrings()
    {
      Set localSet = this.mUpperCase.getInputStrings();
      localSet.addAll(this.mLowerCase.getInputStrings());
      return localSet;
    }
    
    public KeyStyle.StyleId getStyleId()
    {
      return KeyStyle.StyleId.BASE;
    }
    
    public PopupPainter<PreviewPopup> render(ThemeRenderer paramThemeRenderer, KeyArea paramKeyArea, KeyStyle.StyleId paramStyleId)
    {
      return paramThemeRenderer.getPopupPainter(paramKeyArea, getCurrent(), paramStyleId);
    }
    
    public void setDrag(DragEvent paramDragEvent)
    {
      getCurrent().setDrag(paramDragEvent);
    }
  }
  
  public static final class EmptyPopupContent
    implements PopupContent
  {
    public PopupContent applyShiftState(TouchTypeSoftKeyboard.ShiftState paramShiftState)
    {
      return this;
    }
    
    public Set<String> getInputStrings()
    {
      return new HashSet();
    }
    
    public KeyStyle.StyleId getStyleId()
    {
      return KeyStyle.StyleId.BASE;
    }
    
    public PopupPainter<PreviewPopup> render(ThemeRenderer paramThemeRenderer, KeyArea paramKeyArea, KeyStyle.StyleId paramStyleId)
    {
      new PopupPainter()
      {
        public boolean paint(PreviewPopup paramAnonymousPreviewPopup)
        {
          return false;
        }
      };
    }
    
    public void setDrag(DragEvent paramDragEvent) {}
  }
  
  public static final class LSSBPopupContent
    extends PopupContent.AnimatedPopupContent
  {
    private static final DragFilter mFilter = DragFilterFactory.makeFilter(DragEvent.Range.LEFT_RIGHT);
    public float mDrag;
    public final LanguageSwitchData mLanguageSwitchData;
    public final IconContent mLeftContent = new IconContent(KeyIcon.leftArrow, TextRendering.HAlign.LEFT, TextRendering.VAlign.CENTRE, 1.0F, false, false);
    public final IconContent mRightContent = new IconContent(KeyIcon.rightArrow, TextRendering.HAlign.RIGHT, TextRendering.VAlign.CENTRE, 1.0F, false, false);
    public final float mScale;
    public final int mTouchSlop;
    
    public LSSBPopupContent(int paramInt, Context paramContext, LanguageSwitchData paramLanguageSwitchData)
    {
      super();
      this.mTouchSlop = ViewConfiguration.get(paramContext).getScaledTouchSlop();
      this.mLanguageSwitchData = paramLanguageSwitchData;
      this.mScale = paramContext.getResources().getDisplayMetrics().density;
      this.mDrag = 0.0F;
    }
    
    public LSSBPopupContent(Context paramContext, LanguageSwitchData paramLanguageSwitchData)
    {
      this(0, paramContext, paramLanguageSwitchData);
    }
    
    public PopupContent applyShiftState(TouchTypeSoftKeyboard.ShiftState paramShiftState)
    {
      return this;
    }
    
    public Set<String> getInputStrings()
    {
      return new HashSet();
    }
    
    public KeyStyle.StyleId getStyleId()
    {
      return KeyStyle.StyleId.BASE;
    }
    
    public PopupPainter<PreviewPopup> render(ThemeRenderer paramThemeRenderer, KeyArea paramKeyArea, KeyStyle.StyleId paramStyleId)
    {
      return paramThemeRenderer.getPopupPainter(paramKeyArea, this, paramStyleId);
    }
    
    public void setDrag(DragEvent paramDragEvent)
    {
      this.mDrag = mFilter.getDrag(paramDragEvent, 0.0F);
    }
  }
  
  public static final class MiniKeyboardContent
    extends PopupContent.AnimatedPopupContent
  {
    public final MiniKeyboard mKeyboard;
    public final KeyArea mOwningArea;
    
    public MiniKeyboardContent(MiniKeyboard paramMiniKeyboard, KeyArea paramKeyArea)
    {
      this(paramMiniKeyboard, paramKeyArea, 0);
    }
    
    public MiniKeyboardContent(MiniKeyboard paramMiniKeyboard, KeyArea paramKeyArea, int paramInt)
    {
      super();
      this.mKeyboard = paramMiniKeyboard;
      this.mOwningArea = paramKeyArea;
    }
    
    public PopupContent applyShiftState(TouchTypeSoftKeyboard.ShiftState paramShiftState)
    {
      return this;
    }
    
    public Set<String> getInputStrings()
    {
      HashSet localHashSet = new HashSet();
      Iterator localIterator = this.mKeyboard.getKeys().iterator();
      while (localIterator.hasNext()) {
        localHashSet.addAll(((Key)localIterator.next()).getContent().getInputStrings());
      }
      return localHashSet;
    }
    
    public KeyStyle.StyleId getStyleId()
    {
      return KeyStyle.StyleId.BASE;
    }
    
    public PopupPainter<PreviewPopup> render(ThemeRenderer paramThemeRenderer, KeyArea paramKeyArea, KeyStyle.StyleId paramStyleId)
    {
      return paramThemeRenderer.getPopupPainter(paramKeyArea, this, paramStyleId);
    }
    
    public void setDrag(DragEvent paramDragEvent) {}
  }
  
  public static final class PreviewContent
    extends PopupContent.AnimatedPopupContent
  {
    private final boolean mIsCaseSensitive;
    public final String mLabel;
    
    public PreviewContent(String paramString)
    {
      this(paramString, 0, true);
    }
    
    public PreviewContent(String paramString, int paramInt, boolean paramBoolean)
    {
      super();
      this.mLabel = paramString;
      this.mIsCaseSensitive = paramBoolean;
    }
    
    public PopupContent applyShiftState(TouchTypeSoftKeyboard.ShiftState paramShiftState)
    {
      if (this.mIsCaseSensitive) {
        if ((paramShiftState != TouchTypeSoftKeyboard.ShiftState.SHIFTED) && (paramShiftState != TouchTypeSoftKeyboard.ShiftState.CAPSLOCKED)) {
          break label40;
        }
      }
      label40:
      for (String str = this.mLabel.toUpperCase();; str = this.mLabel.toLowerCase())
      {
        this = new PreviewContent(str);
        return this;
      }
    }
    
    public Set<String> getInputStrings()
    {
      return new HashSet();
    }
    
    public KeyStyle.StyleId getStyleId()
    {
      return KeyStyle.StyleId.BASE;
    }
    
    public PopupPainter<PreviewPopup> render(ThemeRenderer paramThemeRenderer, KeyArea paramKeyArea, KeyStyle.StyleId paramStyleId)
    {
      return paramThemeRenderer.getPopupPainter(paramKeyArea, this, paramStyleId);
    }
    
    public void setDrag(DragEvent paramDragEvent) {}
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.key.PopupContent
 * JD-Core Version:    0.7.0.1
 */