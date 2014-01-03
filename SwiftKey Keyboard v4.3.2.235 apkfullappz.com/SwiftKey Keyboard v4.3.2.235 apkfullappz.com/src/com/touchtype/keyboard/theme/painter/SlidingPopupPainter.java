package com.touchtype.keyboard.theme.painter;

import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import com.touchtype.keyboard.keys.view.SlidingLayoutDrawable;
import com.touchtype.keyboard.theme.util.RectUtil;
import com.touchtype.keyboard.view.MainKeyboardView;
import com.touchtype.keyboard.view.PreviewPopup;

public final class SlidingPopupPainter
  extends PreviewPopupPainter
{
  private final RectF mAnchoringRect;
  private final float mInitialDrag;
  private final SlidingLayoutDrawable mSlidingDrawable;
  
  private SlidingPopupPainter(RectF paramRectF, Drawable paramDrawable, int paramInt, float paramFloat, SlidingLayoutDrawable paramSlidingLayoutDrawable)
  {
    super(paramRectF, paramDrawable, paramInt);
    this.mAnchoringRect = paramRectF;
    this.mSlidingDrawable = paramSlidingLayoutDrawable;
    this.mInitialDrag = paramFloat;
  }
  
  public static SlidingPopupPainter createSlidingPopupPainter(RectF paramRectF, Drawable paramDrawable, int paramInt, SlidingLayoutDrawable paramSlidingLayoutDrawable, float paramFloat)
  {
    return new SlidingPopupPainter(paramRectF, paramDrawable, paramInt, paramFloat, paramSlidingLayoutDrawable);
  }
  
  public boolean paint(PreviewPopup paramPreviewPopup)
  {
    if (paramPreviewPopup == null) {}
    do
    {
      return false;
      Rect localRect = paramPreviewPopup.getParent().transformVirtualRect(this.mAnchoringRect);
      this.mSlidingDrawable.setBounds(RectUtil.shrinkToPad(localRect, getPadding()));
    } while (!super.paint(paramPreviewPopup));
    this.mSlidingDrawable.setDrag(paramPreviewPopup.getParent().transformVirtualPoint(new PointF(this.mInitialDrag, 0.0F)).x);
    ImageView localImageView = new ImageView(paramPreviewPopup.getContext());
    localImageView.setImageDrawable(this.mSlidingDrawable);
    paramPreviewPopup.setContent(localImageView);
    return true;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.theme.painter.SlidingPopupPainter
 * JD-Core Version:    0.7.0.1
 */