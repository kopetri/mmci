package com.google.android.gms.maps;

import android.content.Context;
import android.os.RemoteException;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.dynamic.LifecycleDelegate;
import com.google.android.gms.internal.bb;
import com.google.android.gms.internal.bd;
import com.google.android.gms.internal.be;
import com.google.android.gms.internal.cl;
import com.google.android.gms.internal.cw;
import com.google.android.gms.internal.x;
import com.google.android.gms.maps.internal.IMapViewDelegate;
import com.google.android.gms.maps.model.RuntimeRemoteException;

public class MapView
  extends FrameLayout
{
  private final b fB;
  private GoogleMap fx;
  
  public MapView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.fB = new b(this, paramContext, GoogleMapOptions.createFromAttributes(paramContext, paramAttributeSet));
  }
  
  public MapView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    this.fB = new b(this, paramContext, GoogleMapOptions.createFromAttributes(paramContext, paramAttributeSet));
  }
  
  public final GoogleMap getMap()
  {
    if (this.fx != null) {
      return this.fx;
    }
    this.fB.aQ();
    if (this.fB.ag() == null) {
      return null;
    }
    try
    {
      this.fx = new GoogleMap(((a)this.fB.ag()).aR().getMap());
      return this.fx;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }
  
  static final class a
    implements LifecycleDelegate
  {
    private final ViewGroup fC;
    private final IMapViewDelegate fD;
    
    public a(ViewGroup paramViewGroup, IMapViewDelegate paramIMapViewDelegate)
    {
      this.fD = ((IMapViewDelegate)x.d(paramIMapViewDelegate));
      this.fC = ((ViewGroup)x.d(paramViewGroup));
    }
    
    public IMapViewDelegate aR()
    {
      return this.fD;
    }
  }
  
  static final class b
    extends bb<MapView.a>
  {
    protected be<MapView.a> fA;
    private final ViewGroup fF;
    private final GoogleMapOptions fG;
    private final Context mContext;
    
    b(ViewGroup paramViewGroup, Context paramContext, GoogleMapOptions paramGoogleMapOptions)
    {
      this.fF = paramViewGroup;
      this.mContext = paramContext;
      this.fG = paramGoogleMapOptions;
    }
    
    public void aQ()
    {
      if ((this.fA != null) && (ag() == null)) {}
      try
      {
        IMapViewDelegate localIMapViewDelegate = cw.g(this.mContext).a(bd.f(this.mContext), this.fG);
        this.fA.a(new MapView.a(this.fF, localIMapViewDelegate));
        return;
      }
      catch (RemoteException localRemoteException)
      {
        throw new RuntimeRemoteException(localRemoteException);
      }
      catch (GooglePlayServicesNotAvailableException localGooglePlayServicesNotAvailableException) {}
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.MapView
 * JD-Core Version:    0.7.0.1
 */