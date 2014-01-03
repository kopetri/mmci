package com.google.android.gms.internal;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.plus.PlusClient;
import com.google.android.gms.plus.PlusClient.a;

public final class dt
  extends ImageView
  implements GooglePlayServicesClient.ConnectionCallbacks, PlusClient.a
{
  private int gZ;
  private boolean ha;
  private boolean hb;
  private Bitmap hc;
  private PlusClient hd;
  private Uri mUri;
  
  public dt(Context paramContext)
  {
    super(paramContext);
  }
  
  private void bd()
  {
    int i;
    if ((this.mUri != null) && ("android.resource".equals(this.mUri.getScheme())))
    {
      i = 1;
      if (this.hb) {
        break label37;
      }
    }
    label37:
    do
    {
      return;
      i = 0;
      break;
      if (this.mUri == null)
      {
        setImageBitmap(null);
        return;
      }
    } while ((i == 0) && ((this.hd == null) || (!this.hd.isConnected())));
    if (i != 0) {
      setImageURI(this.mUri);
    }
    for (;;)
    {
      this.hb = false;
      return;
      this.hd.a(this, this.mUri, this.gZ);
    }
  }
  
  public void a(Uri paramUri, int paramInt)
  {
    boolean bool;
    if (this.mUri == null) {
      if (paramUri == null) {
        bool = true;
      }
    }
    for (;;)
    {
      int i = this.gZ;
      int j = 0;
      if (i == paramInt) {
        j = 1;
      }
      if ((!bool) || (j == 0)) {
        break;
      }
      return;
      bool = false;
      continue;
      bool = this.mUri.equals(paramUri);
    }
    this.mUri = paramUri;
    this.gZ = paramInt;
    this.hb = true;
    bd();
  }
  
  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    this.ha = true;
    if ((this.hd != null) && (!this.hd.isConnectionCallbacksRegistered(this))) {
      this.hd.registerConnectionCallbacks(this);
    }
    if (this.hc != null) {
      setImageBitmap(this.hc);
    }
  }
  
  protected void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    this.ha = false;
    if ((this.hd != null) && (this.hd.isConnectionCallbacksRegistered(this))) {
      this.hd.unregisterConnectionCallbacks(this);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.dt
 * JD-Core Version:    0.7.0.1
 */