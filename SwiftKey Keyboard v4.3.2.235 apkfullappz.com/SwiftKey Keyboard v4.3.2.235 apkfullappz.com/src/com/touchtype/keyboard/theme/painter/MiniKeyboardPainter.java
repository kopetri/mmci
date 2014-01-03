package com.touchtype.keyboard.theme.painter;

import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.view.View.MeasureSpec;
import com.touchtype.keyboard.MainKeyboard;
import com.touchtype.keyboard.MiniKeyboard;
import com.touchtype.keyboard.key.Key;
import com.touchtype.keyboard.view.MainKeyboardView;
import com.touchtype.keyboard.view.MiniKeyboardView;
import com.touchtype.keyboard.view.PreviewPopup;

public final class MiniKeyboardPainter
  extends PreviewPopupPainter
{
  private final PointF mKeyCentre;
  private final MiniKeyboard mKeyboard;
  
  public MiniKeyboardPainter(Drawable paramDrawable, int paramInt, PointF paramPointF, MiniKeyboard paramMiniKeyboard)
  {
    super(paramDrawable, paramInt);
    this.mKeyCentre = paramPointF;
    this.mKeyboard = paramMiniKeyboard;
  }
  
  public boolean paint(PreviewPopup paramPreviewPopup)
  {
    if (paramPreviewPopup == null) {
      return false;
    }
    Key localKey = ((MainKeyboard)paramPreviewPopup.getParent().getKeyboard()).getKeyAt(this.mKeyCentre.x, this.mKeyCentre.y);
    MiniKeyboardView localMiniKeyboardView = new MiniKeyboardView(paramPreviewPopup.getContext(), this.mKeyboard, localKey, paramPreviewPopup.getParent());
    localMiniKeyboardView.measure(View.MeasureSpec.makeMeasureSpec(1, -2147483648), View.MeasureSpec.makeMeasureSpec(1, -2147483648));
    this.mLocation = localMiniKeyboardView.getDisplayRect();
    if (!super.paint(paramPreviewPopup)) {
      return false;
    }
    paramPreviewPopup.setClippingEnabled(false);
    paramPreviewPopup.setTouchable(true);
    paramPreviewPopup.setContent(localMiniKeyboardView);
    return true;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.theme.painter.MiniKeyboardPainter
 * JD-Core Version:    0.7.0.1
 */