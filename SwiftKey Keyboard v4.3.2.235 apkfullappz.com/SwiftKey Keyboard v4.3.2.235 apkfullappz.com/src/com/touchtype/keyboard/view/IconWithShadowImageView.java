package com.touchtype.keyboard.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class IconWithShadowImageView
  extends ImageView
{
  private boolean mIconShadow;
  private float mPaddingLeft;
  private float mPaddingTop;
  private Paint mShadow;
  
  public IconWithShadowImageView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init();
  }
  
  public IconWithShadowImageView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    init();
  }
  
  private void init()
  {
    this.mPaddingLeft = getPaddingLeft();
    this.mPaddingTop = getPaddingTop();
    int i = getResources().getColor(17170444);
    this.mShadow = new Paint();
    this.mShadow.setColor(i);
    this.mShadow.setShadowLayer(10.0F, 0.0F, 2.0F, i);
    this.mShadow.setColorFilter(new PorterDuffColorFilter(i, PorterDuff.Mode.SRC));
  }
  
  protected void onDraw(Canvas paramCanvas)
  {
    Bitmap localBitmap = ((BitmapDrawable)getDrawable()).getBitmap();
    if (this.mIconShadow) {
      paramCanvas.drawBitmap(localBitmap, this.mPaddingLeft, this.mPaddingTop, this.mShadow);
    }
    paramCanvas.drawBitmap(localBitmap, this.mPaddingLeft, this.mPaddingTop, null);
  }
  
  public void setShadow(boolean paramBoolean)
  {
    this.mIconShadow = paramBoolean;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.view.IconWithShadowImageView
 * JD-Core Version:    0.7.0.1
 */