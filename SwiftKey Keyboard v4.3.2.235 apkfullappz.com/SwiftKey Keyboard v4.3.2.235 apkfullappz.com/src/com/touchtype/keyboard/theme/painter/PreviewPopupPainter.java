package com.touchtype.keyboard.theme.painter;

import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import com.touchtype.keyboard.theme.util.RectUtil;
import com.touchtype.keyboard.view.MainKeyboardView;
import com.touchtype.keyboard.view.PreviewPopup;
import com.touchtype.util.LogUtil;

public abstract class PreviewPopupPainter
  implements PopupPainter<PreviewPopup>
{
  private static final String TAG = PreviewPopupPainter.class.getSimpleName();
  private final int mAnimationResource;
  private final Drawable mBackground;
  protected RectF mLocation = null;
  
  protected PreviewPopupPainter(RectF paramRectF, Drawable paramDrawable, int paramInt)
  {
    this.mLocation = paramRectF;
    this.mBackground = paramDrawable;
    this.mAnimationResource = paramInt;
  }
  
  protected PreviewPopupPainter(Drawable paramDrawable, int paramInt)
  {
    this(null, paramDrawable, paramInt);
  }
  
  private Rect transformBounds(PreviewPopup paramPreviewPopup)
  {
    Rect localRect = RectUtil.expandToPad(paramPreviewPopup.getParent().transformVirtualRect(this.mLocation), getPadding());
    localRect.offset(0, -getPadding().bottom);
    return localRect;
  }
  
  protected Rect getPadding()
  {
    return RectUtil.getPadding(this.mBackground);
  }
  
  public boolean paint(PreviewPopup paramPreviewPopup)
  {
    if (paramPreviewPopup == null)
    {
      LogUtil.w(TAG, "Attempted to paint a null popup");
      return false;
    }
    if (this.mLocation == null)
    {
      LogUtil.w(TAG, "Attempted to paint a popup with no location");
      return false;
    }
    Rect localRect1 = transformBounds(paramPreviewPopup);
    Rect localRect2 = paramPreviewPopup.getBackground().getBounds();
    if ((paramPreviewPopup.getBackground() != this.mBackground) || (localRect1.width() != localRect2.width()) || (localRect1.height() != localRect2.height())) {
      paramPreviewPopup.dismiss();
    }
    paramPreviewPopup.setClippingEnabled(true);
    paramPreviewPopup.setTouchable(false);
    paramPreviewPopup.setBounds(localRect1);
    paramPreviewPopup.setBackgroundDrawable(this.mBackground);
    paramPreviewPopup.setAnimationStyle(this.mAnimationResource);
    return true;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.theme.painter.PreviewPopupPainter
 * JD-Core Version:    0.7.0.1
 */