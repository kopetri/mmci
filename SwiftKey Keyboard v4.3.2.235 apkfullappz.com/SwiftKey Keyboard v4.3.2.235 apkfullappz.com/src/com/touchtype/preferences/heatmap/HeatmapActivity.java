package com.touchtype.preferences.heatmap;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.touchtype.preferences.PrioritisedChooserActivity;
import com.touchtype.preferences.TouchTypePreferences;
import com.touchtype.report.TouchTypeStats;
import com.touchtype.util.LogUtil;
import java.io.File;
import java.io.IOException;
import org.json.JSONException;

public class HeatmapActivity
  extends Activity
  implements ModelReceiver
{
  private static final String TAG = HeatmapActivity.class.getSimpleName();
  private ViewGroup mDialog;
  private TextView mHelpText;
  protected boolean mHelpVisible;
  private ImageView mImageView;
  private ModelMaker mLoader;
  private ModelHandler mModel;
  private ProgressBar mProgressBar;
  private boolean mQuickChange;
  
  private boolean displayHeatmap(Bitmap paramBitmap)
  {
    if (paramBitmap != null)
    {
      this.mImageView.setImageBitmap(paramBitmap);
      return true;
    }
    return false;
  }
  
  private static void toastNone(Context paramContext)
  {
    Toast.makeText(paramContext, paramContext.getString(2131296645), 0).show();
  }
  
  private static void toastSaveDone(Context paramContext)
  {
    Toast.makeText(paramContext, paramContext.getString(2131296654), 0).show();
  }
  
  private static void toastSaveError(Context paramContext)
  {
    Toast.makeText(paramContext, paramContext.getString(2131296655), 0).show();
  }
  
  private static void toastShareError(Context paramContext)
  {
    Toast.makeText(paramContext, paramContext.getString(2131296653), 0).show();
  }
  
  private static void toastUpdate(Context paramContext)
  {
    Toast.makeText(paramContext, paramContext.getString(2131296646), 0).show();
  }
  
  public boolean hasLoader()
  {
    return this.mLoader != null;
  }
  
  public void markAsDone(boolean paramBoolean)
  {
    if (paramBoolean) {
      displayHeatmap(this.mModel.fetch(this));
    }
    this.mProgressBar.setVisibility(8);
    this.mLoader = null;
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903078);
    this.mImageView = ((ImageView)findViewById(2131230842));
    this.mProgressBar = ((ProgressBar)findViewById(2131230843));
    this.mDialog = ((ViewGroup)findViewById(2131230840));
    this.mHelpText = ((TextView)findViewById(2131230845));
    this.mHelpText.setMovementMethod(ScrollingMovementMethod.getInstance());
    this.mProgressBar.setVisibility(8);
    this.mHelpVisible = false;
    InstanceData localInstanceData = (InstanceData)getLastNonConfigurationInstance();
    if (localInstanceData != null)
    {
      this.mLoader = localInstanceData.getLoader();
      if (localInstanceData.getToggle()) {
        showHelp(null);
      }
    }
    try
    {
      this.mModel = ModelHandler.createKeyPressModel(this);
      if ((this.mModel == null) || (this.mModel.getWidth() == 0) || (this.mModel.getHeight() == 0))
      {
        toastNone(this);
        finish();
        return;
      }
    }
    catch (IOException localIOException)
    {
      for (;;)
      {
        LogUtil.e(TAG, "Unable to access input model", localIOException);
      }
    }
    catch (JSONException localJSONException)
    {
      for (;;)
      {
        LogUtil.e(TAG, "Incompatible input model", localJSONException);
      }
      getWindow().setLayout(-2, -2);
      this.mDialog.setBackgroundColor(this.mModel.getBackground());
      View localView = getWindow().findViewById(16908310);
      if (localView != null)
      {
        ViewParent localViewParent = localView.getParent();
        if ((localViewParent != null) && ((localViewParent instanceof View))) {
          ((View)localViewParent).setBackgroundColor(this.mModel.getBackground());
        }
      }
      displayHeatmap(this.mModel.fetch(this));
    }
  }
  
  protected void onDestroy()
  {
    super.onDestroy();
    this.mProgressBar.setVisibility(8);
    if ((!this.mQuickChange) && (this.mLoader != null)) {
      this.mLoader.stop(true);
    }
  }
  
  public Object onRetainNonConfigurationInstance()
  {
    this.mQuickChange = true;
    if (this.mLoader != null) {
      this.mLoader.detach();
    }
    return new InstanceData(this.mLoader, this.mHelpVisible);
  }
  
  protected void onStop()
  {
    super.onStop();
    this.mQuickChange = false;
  }
  
  public void refresh()
  {
    toastUpdate(this);
    this.mProgressBar.setVisibility(0);
    this.mLoader.attach(this);
    updateProgress(this.mLoader.getProgress());
    if (this.mLoader.isDone()) {
      this.mProgressBar.setVisibility(8);
    }
  }
  
  public void saveHeatmap(View paramView)
  {
    if (this.mLoader != null)
    {
      toastUpdate(this);
      return;
    }
    if (this.mModel.save())
    {
      toastSaveDone(this);
      return;
    }
    toastSaveError(this);
  }
  
  public void setLoader(ModelMaker paramModelMaker)
  {
    this.mLoader = paramModelMaker;
  }
  
  public void shareHeatmap(View paramView)
  {
    if (this.mLoader != null)
    {
      toastUpdate(this);
      return;
    }
    File localFile = this.mModel.getClone();
    if (localFile == null)
    {
      toastShareError(this);
      return;
    }
    TouchTypePreferences.getInstance(getApplicationContext()).getTouchTypeStats().incrementStatistic("stats_shared_heatmap");
    Uri localUri = Uri.fromFile(localFile);
    Intent localIntent = new Intent();
    localIntent.setAction("android.intent.action.SEND");
    localIntent.setType("image/png");
    localIntent.putExtra("android.intent.extra.STREAM", localUri);
    localIntent.putExtra("android.intent.extra.SUBJECT", getString(2131296651));
    localIntent.putExtra("android.intent.extra.TEXT", getString(2131296652) + " " + getString(2131297073));
    localIntent.putExtra("android.intent.extra.TITLE", getString(2131296652) + " " + getString(2131297073));
    startActivity(PrioritisedChooserActivity.createChooser(this, localIntent, getString(2131296650)));
  }
  
  public void showHelp(View paramView)
  {
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
    localBuilder.setTitle(getResources().getString(2131296643));
    localBuilder.setMessage(getResources().getString(2131296658));
    localBuilder.setCancelable(true);
    this.mHelpVisible = true;
    localBuilder.setNeutralButton(2131297201, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        HeatmapActivity.this.mHelpVisible = false;
      }
    });
    localBuilder.create().show();
  }
  
  public void updateProgress(int paramInt)
  {
    this.mProgressBar.setProgress(paramInt);
  }
  
  private static final class InstanceData
  {
    private ModelMaker mSavedAsync;
    private boolean mToggleState;
    
    public InstanceData(ModelMaker paramModelMaker, boolean paramBoolean)
    {
      this.mSavedAsync = paramModelMaker;
      this.mToggleState = paramBoolean;
    }
    
    public ModelMaker getLoader()
    {
      return this.mSavedAsync;
    }
    
    public boolean getToggle()
    {
      return this.mToggleState;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.preferences.heatmap.HeatmapActivity
 * JD-Core Version:    0.7.0.1
 */