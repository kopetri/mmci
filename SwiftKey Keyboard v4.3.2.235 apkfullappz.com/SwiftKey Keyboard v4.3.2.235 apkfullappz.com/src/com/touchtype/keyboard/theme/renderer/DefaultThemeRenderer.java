package com.touchtype.keyboard.theme.renderer;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.text.TextPaint;
import com.touchtype.keyboard.key.KeyArea;
import com.touchtype.keyboard.key.KeyIcon;
import com.touchtype.keyboard.key.KeyState;
import com.touchtype.keyboard.key.PopupContent;
import com.touchtype.keyboard.key.PopupContent.LSSBPopupContent;
import com.touchtype.keyboard.key.PopupContent.MiniKeyboardContent;
import com.touchtype.keyboard.key.PopupContent.PreviewContent;
import com.touchtype.keyboard.key.contents.CandidateContent;
import com.touchtype.keyboard.key.contents.DualKeyContent;
import com.touchtype.keyboard.key.contents.IconContent;
import com.touchtype.keyboard.key.contents.KeyContent;
import com.touchtype.keyboard.key.contents.LSSBContent;
import com.touchtype.keyboard.key.contents.ScaleLinkedTextContent;
import com.touchtype.keyboard.key.contents.TextContent;
import com.touchtype.keyboard.keys.view.DualContentKeyDrawable;
import com.touchtype.keyboard.keys.view.IconDrawable;
import com.touchtype.keyboard.keys.view.MainTextKeyDrawable;
import com.touchtype.keyboard.keys.view.MainTextKeyDrawableLegacy;
import com.touchtype.keyboard.keys.view.ResizeDrawable;
import com.touchtype.keyboard.keys.view.ResizeDrawable.EmptyDrawable;
import com.touchtype.keyboard.keys.view.ScalingInsetLayerDrawable;
import com.touchtype.keyboard.keys.view.ScalingInsetLayerDrawable.Inset;
import com.touchtype.keyboard.keys.view.SlidingLayoutDrawable;
import com.touchtype.keyboard.keys.view.SpacebarLanguageDrawable;
import com.touchtype.keyboard.theme.KeyStyle;
import com.touchtype.keyboard.theme.KeyStyle.StyleId;
import com.touchtype.keyboard.theme.KeyStyle.SubStyle;
import com.touchtype.keyboard.theme.painter.MiniKeyboardPainter;
import com.touchtype.keyboard.theme.painter.PreviewPopupPainter;
import com.touchtype.keyboard.theme.painter.SlidingPopupPainter;
import com.touchtype.keyboard.theme.painter.TextPainter;
import com.touchtype.keyboard.theme.util.RectUtil;
import com.touchtype.keyboard.theme.util.TextSizeLimiter;
import java.util.Map;

public class DefaultThemeRenderer
  extends ThemeRenderer
{
  private static final String TAG = DefaultThemeRenderer.class.getSimpleName();
  
  public DefaultThemeRenderer(Map<KeyStyle.StyleId, KeyStyle> paramMap, Map<KeyIcon, Drawable> paramMap1, Context paramContext)
  {
    super(paramMap, paramMap1, paramContext);
  }
  
  private ResizeDrawable createNewOrLegacyMainTextKeyDrawable(TextContent paramTextContent, KeyStyle.StyleId paramStyleId, KeyStyle.SubStyle paramSubStyle)
  {
    TextPaint localTextPaint = paramTextContent.applyTextStyle(getStyle(paramStyleId).getTextPaint(paramSubStyle));
    TextSizeLimiter localTextSizeLimiter = new TextSizeLimiter(this.mContext);
    if (Build.VERSION.SDK_INT >= 9) {
      return new MainTextKeyDrawable(paramTextContent.getLabel(), localTextPaint, paramTextContent.mHAlign, paramTextContent.mVAlign, paramTextContent.getHeightLimit(), localTextSizeLimiter);
    }
    return new MainTextKeyDrawableLegacy(paramTextContent.getLabel(), localTextPaint, paramTextContent.mHAlign, paramTextContent.mVAlign, paramTextContent.getHeightLimit(), localTextSizeLimiter);
  }
  
  protected Drawable getBackgroundDrawable(KeyState paramKeyState, KeyArea paramKeyArea, KeyStyle.StyleId paramStyleId)
  {
    Drawable localDrawable = getStyle(paramStyleId).mBackground;
    localDrawable.setState(paramKeyState.getPressedDrawableState());
    localDrawable.clearColorFilter();
    return localDrawable;
  }
  
  public ResizeDrawable getContentDrawable(CandidateContent paramCandidateContent, KeyStyle.StyleId paramStyleId, KeyStyle.SubStyle paramSubStyle)
  {
    return createNewOrLegacyMainTextKeyDrawable(paramCandidateContent, paramStyleId, paramSubStyle);
  }
  
  public ResizeDrawable getContentDrawable(DualKeyContent paramDualKeyContent, KeyStyle.StyleId paramStyleId, KeyStyle.SubStyle paramSubStyle)
  {
    return getDualContentKeyDrawable(paramDualKeyContent.mTopContent.render(this, paramStyleId, KeyStyle.SubStyle.TOP), paramDualKeyContent.mBottomContent.render(this, paramStyleId, KeyStyle.SubStyle.BOTTOM), paramDualKeyContent.mRatio, paramStyleId);
  }
  
  public ResizeDrawable getContentDrawable(IconContent paramIconContent, KeyStyle.StyleId paramStyleId, KeyStyle.SubStyle paramSubStyle)
  {
    Drawable localDrawable = (Drawable)this.mIcons.get(paramIconContent.mIcon);
    if (localDrawable == null) {
      return null;
    }
    localDrawable.setState(paramIconContent.getIconState());
    localDrawable.setColorFilter(null);
    return new IconDrawable(localDrawable, paramIconContent.mHAlign, paramIconContent.mVAlign, paramIconContent.mMaxSize, paramIconContent.mPositionByFullscale, paramIconContent.mLimitByArea);
  }
  
  public ResizeDrawable getContentDrawable(KeyContent paramKeyContent, KeyStyle.StyleId paramStyleId, KeyStyle.SubStyle paramSubStyle)
  {
    return new ResizeDrawable.EmptyDrawable();
  }
  
  public ResizeDrawable getContentDrawable(LSSBContent paramLSSBContent, KeyStyle.StyleId paramStyleId, KeyStyle.SubStyle paramSubStyle)
  {
    ResizeDrawable localResizeDrawable1 = paramLSSBContent.mLeftContent.render(this, paramStyleId, paramSubStyle);
    ResizeDrawable localResizeDrawable2 = paramLSSBContent.mRightContent.render(this, paramStyleId, paramSubStyle);
    TextPaint localTextPaint = getStyle(paramStyleId).getTextPaint(paramSubStyle);
    return new SpacebarLanguageDrawable(paramLSSBContent.mDensity, localTextPaint, localResizeDrawable1, localResizeDrawable2, paramLSSBContent.mName, paramLSSBContent.mShortName);
  }
  
  public ResizeDrawable getContentDrawable(ScaleLinkedTextContent paramScaleLinkedTextContent, KeyStyle.StyleId paramStyleId, KeyStyle.SubStyle paramSubStyle)
  {
    TextPaint localTextPaint = paramScaleLinkedTextContent.applyTextStyle(getStyle(paramStyleId).getTextPaint(paramSubStyle));
    TextSizeLimiter localTextSizeLimiter = new TextSizeLimiter(this.mContext);
    return new MainTextKeyDrawable(paramScaleLinkedTextContent.getLabel(), localTextPaint, paramScaleLinkedTextContent.mHAlign, paramScaleLinkedTextContent.mVAlign, paramScaleLinkedTextContent.getHeightLimit(), paramScaleLinkedTextContent.getLinkSet(), localTextSizeLimiter);
  }
  
  public ResizeDrawable getContentDrawable(TextContent paramTextContent, KeyStyle.StyleId paramStyleId, KeyStyle.SubStyle paramSubStyle)
  {
    return createNewOrLegacyMainTextKeyDrawable(paramTextContent, paramStyleId, paramSubStyle);
  }
  
  protected ScalingInsetLayerDrawable.Inset getContentInset(KeyArea paramKeyArea, Drawable paramDrawable)
  {
    return new ScalingInsetLayerDrawable.Inset(paramKeyArea.getPadding(), RectUtil.getPadding(paramDrawable));
  }
  
  protected ResizeDrawable getDualContentKeyDrawable(ResizeDrawable paramResizeDrawable1, ResizeDrawable paramResizeDrawable2, float paramFloat, KeyStyle.StyleId paramStyleId)
  {
    paramResizeDrawable1.setColorFilter(null);
    paramResizeDrawable2.setColorFilter(null);
    return new DualContentKeyDrawable(paramResizeDrawable1, paramResizeDrawable2, paramFloat);
  }
  
  public Drawable getKeyDrawable(KeyContent paramKeyContent, KeyState paramKeyState, KeyArea paramKeyArea, KeyStyle.StyleId paramStyleId)
  {
    Drawable localDrawable = getBackgroundDrawable(paramKeyState, paramKeyArea, paramStyleId);
    ScalingInsetLayerDrawable localScalingInsetLayerDrawable = new ScalingInsetLayerDrawable(new Drawable[] { localDrawable, paramKeyContent.render(this, paramStyleId, KeyStyle.SubStyle.MAIN) });
    localScalingInsetLayerDrawable.setScalingInset(0, getKeyInset(paramKeyArea));
    localScalingInsetLayerDrawable.setScalingInset(1, getContentInset(paramKeyArea, localDrawable));
    return localScalingInsetLayerDrawable;
  }
  
  protected ScalingInsetLayerDrawable.Inset getKeyInset(KeyArea paramKeyArea)
  {
    return new ScalingInsetLayerDrawable.Inset(paramKeyArea.getPadding(), new Rect());
  }
  
  protected Drawable getMiniKeyboardBackground(KeyStyle.StyleId paramStyleId)
  {
    Drawable localDrawable = getStyle(paramStyleId).mMiniKeyboardBackground;
    localDrawable.clearColorFilter();
    return localDrawable;
  }
  
  protected Drawable getPopupBackground(KeyStyle.StyleId paramStyleId)
  {
    Drawable localDrawable = getStyle(paramStyleId).mPopupBackground;
    localDrawable.clearColorFilter();
    return localDrawable;
  }
  
  protected RectF getPopupLocation(KeyArea paramKeyArea)
  {
    RectF localRectF = paramKeyArea.getDrawBounds();
    float f1 = 1.0F * localRectF.width();
    float f2 = 1.0F * localRectF.height();
    float f3 = localRectF.top - f2;
    float f4 = localRectF.centerX() - f1 / 2.0F;
    return new RectF(f4, f3, f4 + f1, f3 + f2);
  }
  
  public PreviewPopupPainter getPopupPainter(KeyArea paramKeyArea, PopupContent.LSSBPopupContent paramLSSBPopupContent, KeyStyle.StyleId paramStyleId)
  {
    ResizeDrawable localResizeDrawable1 = paramLSSBPopupContent.mLeftContent.render(this, paramStyleId, KeyStyle.SubStyle.MAIN);
    ResizeDrawable localResizeDrawable2 = paramLSSBPopupContent.mRightContent.render(this, paramStyleId, KeyStyle.SubStyle.MAIN);
    return SlidingPopupPainter.createSlidingPopupPainter(getPopupLocation(paramKeyArea), getPopupBackground(paramStyleId), paramLSSBPopupContent.getAnimation(), new SlidingLayoutDrawable(paramLSSBPopupContent.mScale, getStyle(paramStyleId).mPopupTextPaint, localResizeDrawable1, localResizeDrawable2, paramLSSBPopupContent.mTouchSlop, paramLSSBPopupContent.mLanguageSwitchData), paramLSSBPopupContent.mDrag);
  }
  
  public PreviewPopupPainter getPopupPainter(KeyArea paramKeyArea, PopupContent.MiniKeyboardContent paramMiniKeyboardContent, KeyStyle.StyleId paramStyleId)
  {
    PointF localPointF = new PointF(paramMiniKeyboardContent.mOwningArea.getDrawBounds().centerX(), paramMiniKeyboardContent.mOwningArea.getDrawBounds().centerY());
    return new MiniKeyboardPainter(getMiniKeyboardBackground(paramStyleId), paramMiniKeyboardContent.getAnimation(), localPointF, paramMiniKeyboardContent.mKeyboard);
  }
  
  public PreviewPopupPainter getPopupPainter(KeyArea paramKeyArea, PopupContent.PreviewContent paramPreviewContent, KeyStyle.StyleId paramStyleId)
  {
    KeyStyle localKeyStyle = getStyle(paramStyleId);
    return new TextPainter(getPopupLocation(paramKeyArea), getPopupBackground(paramStyleId), paramPreviewContent.getAnimation(), paramPreviewContent.mLabel, localKeyStyle.mPopupTextPaint);
  }
  
  public PreviewPopupPainter getPopupPainter(KeyArea paramKeyArea, PopupContent paramPopupContent, KeyStyle.StyleId paramStyleId)
  {
    return null;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.theme.renderer.DefaultThemeRenderer
 * JD-Core Version:    0.7.0.1
 */