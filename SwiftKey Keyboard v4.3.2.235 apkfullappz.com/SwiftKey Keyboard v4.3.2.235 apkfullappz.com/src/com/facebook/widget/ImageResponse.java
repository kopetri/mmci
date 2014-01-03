package com.facebook.widget;

import android.graphics.Bitmap;

class ImageResponse
{
  private Bitmap bitmap;
  private Exception error;
  private boolean isCachedRedirect;
  private ImageRequest request;
  
  ImageResponse(ImageRequest paramImageRequest, Exception paramException, boolean paramBoolean, Bitmap paramBitmap)
  {
    this.request = paramImageRequest;
    this.error = paramException;
    this.bitmap = paramBitmap;
    this.isCachedRedirect = paramBoolean;
  }
  
  Bitmap getBitmap()
  {
    return this.bitmap;
  }
  
  Exception getError()
  {
    return this.error;
  }
  
  ImageRequest getRequest()
  {
    return this.request;
  }
  
  boolean isCachedRedirect()
  {
    return this.isCachedRedirect;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.facebook.widget.ImageResponse
 * JD-Core Version:    0.7.0.1
 */