package com.google.android.gms.common.images;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.os.ResultReceiver;
import android.util.Log;
import com.google.android.gms.internal.af;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Map;

public final class ImageManager
{
  private final af<Uri, WeakReference<Drawable.ConstantState>> aj;
  private final Map<Uri, b> al;
  private final Context mContext;
  
  public final class b
    extends ResultReceiver
  {
    private final ArrayList<ImageManager.d> ao;
    private final Uri mUri;
    
    public void onReceiveResult(int paramInt, Bundle paramBundle)
    {
      ParcelFileDescriptor localParcelFileDescriptor = (ParcelFileDescriptor)paramBundle.getParcelable("com.google.android.gms.extra.fileDescriptor");
      Bitmap localBitmap;
      if (localParcelFileDescriptor != null) {
        localBitmap = BitmapFactory.decodeFileDescriptor(localParcelFileDescriptor.getFileDescriptor());
      }
      try
      {
        localParcelFileDescriptor.close();
        BitmapDrawable localBitmapDrawable = new BitmapDrawable(ImageManager.a(this.an).getResources(), localBitmap);
        ImageManager.b(this.an).put(this.mUri, new WeakReference(localBitmapDrawable.getConstantState()));
        ImageManager.c(this.an).remove(this.mUri);
        int i = 0;
        int j = this.ao.size();
        while (i < j)
        {
          this.ao.get(i);
          i++;
        }
      }
      catch (IOException localIOException)
      {
        for (;;)
        {
          Log.e("ImageManager", "closed failed", localIOException);
        }
      }
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.images.ImageManager
 * JD-Core Version:    0.7.0.1
 */