package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.plus.PlusClient.a;

public final class dy
  extends p<dx>
{
  public void a(PlusClient.a parama, Uri paramUri, int paramInt)
  {
    n();
    Bundle localBundle = new Bundle();
    localBundle.putInt("bounding_box", paramInt);
    c localc = new c(parama);
    try
    {
      ((dx)o()).a(localc, paramUri, localBundle);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      localc.a(8, null, null);
    }
  }
  
  final class c
    extends dv
  {
    private final PlusClient.a hp;
    
    public c(PlusClient.a parama)
    {
      this.hp = parama;
    }
    
    public void a(int paramInt, Bundle paramBundle, ParcelFileDescriptor paramParcelFileDescriptor)
    {
      PendingIntent localPendingIntent = null;
      if (paramBundle != null) {
        localPendingIntent = (PendingIntent)paramBundle.getParcelable("pendingIntent");
      }
      ConnectionResult localConnectionResult = new ConnectionResult(paramInt, localPendingIntent);
      dy.this.a(new dy.d(dy.this, this.hp, localConnectionResult, paramParcelFileDescriptor));
    }
  }
  
  final class d
    extends p<dx>.b<PlusClient.a>
  {
    private final ConnectionResult hm;
    private final ParcelFileDescriptor hq;
    
    public d(PlusClient.a parama, ConnectionResult paramConnectionResult, ParcelFileDescriptor paramParcelFileDescriptor)
    {
      super(parama);
      this.hm = paramConnectionResult;
      this.hq = paramParcelFileDescriptor;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.dy
 * JD-Core Version:    0.7.0.1
 */