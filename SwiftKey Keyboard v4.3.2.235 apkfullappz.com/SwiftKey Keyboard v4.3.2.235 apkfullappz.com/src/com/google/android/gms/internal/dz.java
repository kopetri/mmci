package com.google.android.gms.internal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.plus.PlusClient;
import com.google.android.gms.plus.PlusOneButton.OnPlusOneClickListener;

public class dz
  extends LinearLayout
  implements GooglePlayServicesClient.ConnectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener
{
  private static final int hy = Color.parseColor("#666666");
  private int K = 3;
  protected int bl = 1;
  protected int hA = 0;
  protected final LinearLayout hB;
  protected final FrameLayout hC;
  protected final CompoundButton hD;
  private final ProgressBar hE;
  protected final ea hF;
  private final dt[] hG = new dt[4];
  private int hH = 2;
  private Uri[] hI;
  private String[] hJ;
  private String[] hK;
  protected du hM;
  protected final Resources hN;
  protected final LayoutInflater hO;
  private b hP = new b();
  protected PlusClient hd;
  protected boolean hz;
  
  public dz(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    x.b(paramContext, "Context must not be null.");
    if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(paramContext) != 0) {
      this.hN = null;
    }
    Point localPoint;
    Context localContext;
    for (this.hO = null;; this.hO = ((LayoutInflater)localContext.getSystemService("layout_inflater")))
    {
      this.K = a(paramContext, paramAttributeSet);
      this.hH = b(paramContext, paramAttributeSet);
      localPoint = new Point();
      a(localPoint);
      if (!isInEditMode()) {
        break;
      }
      TextView localTextView = new TextView(paramContext);
      localTextView.setGravity(17);
      localTextView.setText("[ +1 ]");
      addView(localTextView, new LinearLayout.LayoutParams(localPoint.x, localPoint.y));
      this.hF = null;
      this.hE = null;
      this.hD = null;
      this.hC = null;
      this.hB = null;
      return;
      localContext = k(paramContext);
      this.hN = localContext.getResources();
    }
    setFocusable(true);
    this.hB = new LinearLayout(paramContext);
    this.hB.setGravity(17);
    this.hB.setOrientation(0);
    addView(this.hB);
    this.hD = new c(paramContext);
    this.hD.setBackgroundDrawable(null);
    this.hF = n(paramContext);
    this.hC = l(paramContext);
    this.hC.addView(this.hD, new FrameLayout.LayoutParams(localPoint.x, localPoint.y, 17));
    b(localPoint);
    this.hE = m(paramContext);
    this.hE.setVisibility(4);
    this.hC.addView(this.hE, new FrameLayout.LayoutParams(localPoint.x, localPoint.y, 17));
    int i = this.hG.length;
    for (int j = 0; j < i; j++) {
      this.hG[j] = o(getContext());
    }
    bn();
  }
  
  private int a(Context paramContext, AttributeSet paramAttributeSet)
  {
    String str = ab.a("http://schemas.android.com/apk/lib/com.google.android.gms.plus", "size", paramContext, paramAttributeSet, true, false, "PlusOneButton");
    if ("SMALL".equalsIgnoreCase(str)) {
      return 0;
    }
    if ("MEDIUM".equalsIgnoreCase(str)) {
      return 1;
    }
    if ("TALL".equalsIgnoreCase(str)) {
      return 2;
    }
    "STANDARD".equalsIgnoreCase(str);
    return 3;
  }
  
  private void a(Point paramPoint)
  {
    int i = 24;
    int j = 20;
    switch (this.K)
    {
    default: 
      int k = i;
      i = 38;
      j = k;
    }
    for (;;)
    {
      DisplayMetrics localDisplayMetrics = getResources().getDisplayMetrics();
      float f1 = TypedValue.applyDimension(1, i, localDisplayMetrics);
      float f2 = TypedValue.applyDimension(1, j, localDisplayMetrics);
      paramPoint.x = ((int)(0.5D + f1));
      paramPoint.y = ((int)(0.5D + f2));
      return;
      i = 32;
      continue;
      j = 14;
      continue;
      i = 50;
    }
  }
  
  private int b(Context paramContext, AttributeSet paramAttributeSet)
  {
    String str = ab.a("http://schemas.android.com/apk/lib/com.google.android.gms.plus", "annotation", paramContext, paramAttributeSet, true, false, "PlusOneButton");
    int i;
    if ("INLINE".equalsIgnoreCase(str)) {
      i = 2;
    }
    boolean bool;
    do
    {
      return i;
      bool = "NONE".equalsIgnoreCase(str);
      i = 0;
    } while (bool);
    "BUBBLE".equalsIgnoreCase(str);
    return 1;
  }
  
  private void b(Point paramPoint)
  {
    paramPoint.y = ((int)(paramPoint.y - TypedValue.applyDimension(1, 6.0F, getResources().getDisplayMetrics())));
    paramPoint.x = paramPoint.y;
  }
  
  private void bi()
  {
    int i = 1;
    int j = (int)TypedValue.applyDimension(i, 5.0F, getContext().getResources().getDisplayMetrics());
    int k = (int)TypedValue.applyDimension(i, 1.0F, getContext().getResources().getDisplayMetrics());
    int m = this.hG.length;
    int n = 0;
    if (n < m)
    {
      LinearLayout.LayoutParams localLayoutParams;
      if (this.hG[n].getVisibility() == 0)
      {
        localLayoutParams = new LinearLayout.LayoutParams(this.hG[n].getLayoutParams());
        if (i == 0) {
          break label120;
        }
        localLayoutParams.setMargins(j, 0, k, 0);
        i = 0;
      }
      for (;;)
      {
        this.hG[n].setLayoutParams(localLayoutParams);
        n++;
        break;
        label120:
        localLayoutParams.setMargins(k, 0, k, 0);
      }
    }
  }
  
  private LinearLayout.LayoutParams bk()
  {
    LinearLayout.LayoutParams localLayoutParams;
    int i;
    label50:
    int k;
    switch (this.hH)
    {
    default: 
      localLayoutParams = new LinearLayout.LayoutParams(-2, -2);
      if (this.K == 2)
      {
        i = 1;
        localLayoutParams.bottomMargin = i;
        int j = this.K;
        k = 0;
        if (j != 2) {
          break label110;
        }
      }
      break;
    }
    for (;;)
    {
      localLayoutParams.leftMargin = k;
      return localLayoutParams;
      localLayoutParams = new LinearLayout.LayoutParams(-2, -1);
      break;
      localLayoutParams = new LinearLayout.LayoutParams(-2, -2);
      break;
      i = 0;
      break label50;
      label110:
      k = 1;
    }
  }
  
  private void bq()
  {
    switch (this.hH)
    {
    default: 
      this.hF.f(null);
      this.hF.setVisibility(8);
      return;
    case 2: 
      this.hF.f(this.hJ);
      this.hF.setVisibility(0);
      return;
    }
    this.hF.f(this.hK);
    this.hF.setVisibility(0);
  }
  
  private void br()
  {
    int i = 0;
    if ((this.hI != null) && (this.hH == 2))
    {
      Point localPoint = new Point();
      a(localPoint);
      localPoint.x = localPoint.y;
      int k = this.hG.length;
      int m = this.hI.length;
      int n = 0;
      if (n < k)
      {
        Uri localUri;
        if (n < m)
        {
          localUri = this.hI[n];
          label78:
          if (localUri != null) {
            break label107;
          }
          this.hG[n].setVisibility(8);
        }
        for (;;)
        {
          n++;
          break;
          localUri = null;
          break label78;
          label107:
          this.hG[n].setLayoutParams(new LinearLayout.LayoutParams(localPoint.x, localPoint.y));
          this.hG[n].a(localUri, localPoint.y);
          this.hG[n].setVisibility(0);
        }
      }
    }
    else
    {
      int j = this.hG.length;
      while (i < j)
      {
        this.hG[i].setVisibility(8);
        i++;
      }
    }
    bi();
  }
  
  private Drawable bs()
  {
    if (this.hN == null) {
      return null;
    }
    return this.hN.getDrawable(this.hN.getIdentifier(bt(), "drawable", "com.google.android.gms"));
  }
  
  private String bt()
  {
    switch (this.K)
    {
    default: 
      return "ic_plusone_standard";
    case 0: 
      return "ic_plusone_small";
    case 1: 
      return "ic_plusone_medium";
    }
    return "ic_plusone_tall";
  }
  
  private Uri bu()
  {
    return y.i(bv());
  }
  
  private String bv()
  {
    switch (this.K)
    {
    default: 
      return "global_count_bubble_standard";
    case 1: 
      return "global_count_bubble_medium";
    case 0: 
      return "global_count_bubble_small";
    }
    return "global_count_bubble_tall";
  }
  
  private void c(int paramInt1, int paramInt2)
  {
    this.bl = paramInt2;
    this.K = paramInt1;
    bj();
  }
  
  private void c(View paramView)
  {
    int i = (int)TypedValue.applyDimension(1, 3.0F, getContext().getResources().getDisplayMetrics());
    int j = (int)TypedValue.applyDimension(1, 5.0F, getContext().getResources().getDisplayMetrics());
    if (this.hH == 2) {
      if ((this.K != 2) || (this.hH != 1)) {
        break label76;
      }
    }
    for (;;)
    {
      paramView.setPadding(j, 0, 0, i);
      return;
      j = 0;
      break;
      label76:
      i = 0;
    }
  }
  
  private static int d(int paramInt1, int paramInt2)
  {
    switch (paramInt1)
    {
    case 1: 
    default: 
    case 2: 
      do
      {
        return 13;
      } while (paramInt2 == 2);
      return 15;
    }
    return 11;
  }
  
  private Context k(Context paramContext)
  {
    try
    {
      Context localContext = getContext().createPackageContext("com.google.android.gms", 4);
      return localContext;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      if (Log.isLoggable("PlusOneButton", 5)) {
        Log.w("PlusOneButton", "Google Play services is not installed");
      }
    }
    return null;
  }
  
  private FrameLayout l(Context paramContext)
  {
    FrameLayout localFrameLayout = new FrameLayout(paramContext);
    localFrameLayout.setFocusable(false);
    return localFrameLayout;
  }
  
  private ProgressBar m(Context paramContext)
  {
    ProgressBar localProgressBar = new ProgressBar(paramContext, null, 16843400);
    localProgressBar.setFocusable(false);
    localProgressBar.setIndeterminate(true);
    return localProgressBar;
  }
  
  private ea n(Context paramContext)
  {
    ea localea = new ea(paramContext);
    localea.setFocusable(false);
    localea.setGravity(17);
    localea.setSingleLine();
    localea.setTextSize(0, TypedValue.applyDimension(2, d(this.K, this.hH), paramContext.getResources().getDisplayMetrics()));
    localea.setTextColor(hy);
    localea.setVisibility(0);
    return localea;
  }
  
  private dt o(Context paramContext)
  {
    dt localdt = new dt(paramContext);
    localdt.setVisibility(8);
    return localdt;
  }
  
  protected void bj()
  {
    int i = 0;
    if (isInEditMode()) {
      return;
    }
    this.hB.removeAllViews();
    Point localPoint = new Point();
    a(localPoint);
    this.hD.setLayoutParams(new FrameLayout.LayoutParams(localPoint.x, localPoint.y, 17));
    b(localPoint);
    this.hE.setLayoutParams(new FrameLayout.LayoutParams(localPoint.x, localPoint.y, 17));
    if (this.hH == 1)
    {
      this.hF.b(bu());
      br();
      this.hF.setLayoutParams(bk());
      float f = TypedValue.applyDimension(2, d(this.K, this.hH), getContext().getResources().getDisplayMetrics());
      this.hF.setTextSize(0, f);
      c(this.hF);
      if ((this.K != 2) || (this.hH != 1)) {
        break label223;
      }
      this.hB.setOrientation(1);
      this.hB.addView(this.hF);
      this.hB.addView(this.hC);
    }
    for (;;)
    {
      requestLayout();
      return;
      this.hF.b(null);
      break;
      label223:
      this.hB.setOrientation(0);
      this.hB.addView(this.hC);
      int j = this.hG.length;
      while (i < j)
      {
        this.hB.addView(this.hG[i]);
        i++;
      }
      this.hB.addView(this.hF);
    }
  }
  
  public void bl()
  {
    setType(2);
    this.hE.setVisibility(0);
    bp();
  }
  
  protected void bn()
  {
    setType(1);
    this.hE.setVisibility(4);
    bp();
  }
  
  protected void bp()
  {
    this.hD.setButtonDrawable(bs());
    switch (this.bl)
    {
    default: 
      this.hD.setEnabled(false);
      this.hD.setChecked(false);
      return;
    case 0: 
      this.hD.setEnabled(true);
      this.hD.setChecked(true);
      return;
    case 1: 
      this.hD.setEnabled(true);
      this.hD.setChecked(false);
      return;
    }
    this.hD.setEnabled(false);
    this.hD.setChecked(true);
  }
  
  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    if (this.hd != null)
    {
      if (!this.hd.isConnectionCallbacksRegistered(this)) {
        this.hd.registerConnectionCallbacks(this);
      }
      if (!this.hd.isConnectionFailedListenerRegistered(this)) {
        this.hd.registerConnectionFailedListener(this);
      }
    }
  }
  
  protected void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    if (this.hd != null)
    {
      if (this.hd.isConnectionCallbacksRegistered(this)) {
        this.hd.unregisterConnectionCallbacks(this);
      }
      if (this.hd.isConnectionFailedListenerRegistered(this)) {
        this.hd.unregisterConnectionFailedListener(this);
      }
    }
  }
  
  public boolean performClick()
  {
    return this.hD.performClick();
  }
  
  public void setAnnotation(int paramInt)
  {
    x.b(Integer.valueOf(paramInt), "Annotation must not be null.");
    this.hH = paramInt;
    bq();
    bj();
  }
  
  public void setOnClickListener(View.OnClickListener paramOnClickListener)
  {
    this.hD.setOnClickListener(paramOnClickListener);
    this.hF.setOnClickListener(paramOnClickListener);
  }
  
  public void setOnPlusOneClickListener(PlusOneButton.OnPlusOneClickListener paramOnPlusOneClickListener)
  {
    setOnClickListener(new a(paramOnPlusOneClickListener));
  }
  
  public void setSize(int paramInt)
  {
    c(paramInt, this.bl);
  }
  
  public void setType(int paramInt)
  {
    c(this.K, paramInt);
  }
  
  final class a
    implements View.OnClickListener, PlusOneButton.OnPlusOneClickListener
  {
    private final PlusOneButton.OnPlusOneClickListener hQ;
    
    public a(PlusOneButton.OnPlusOneClickListener paramOnPlusOneClickListener)
    {
      this.hQ = paramOnPlusOneClickListener;
    }
    
    public void onClick(View paramView)
    {
      if ((paramView == dz.this.hD) || (paramView == dz.this.hF)) {
        if (dz.this.hM != null) {
          break label52;
        }
      }
      label52:
      for (Intent localIntent = null; this.hQ != null; localIntent = dz.this.hM.getIntent())
      {
        this.hQ.onPlusOneClick(localIntent);
        return;
      }
      onPlusOneClick(localIntent);
    }
    
    public void onPlusOneClick(Intent paramIntent)
    {
      Context localContext = dz.this.getContext();
      if (((localContext instanceof Activity)) && (paramIntent != null)) {
        ((Activity)localContext).startActivityForResult(paramIntent, dz.this.hA);
      }
    }
  }
  
  public final class b
  {
    protected b() {}
  }
  
  final class c
    extends CompoundButton
  {
    public c(Context paramContext)
    {
      super();
    }
    
    public void toggle()
    {
      if (dz.this.hz)
      {
        super.toggle();
        return;
      }
      dz.this.hz = true;
      dz.this.bl();
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.dz
 * JD-Core Version:    0.7.0.1
 */