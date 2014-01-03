package com.touchtype.backup;

import android.content.Context;
import com.touchtype_fluency.service.DynamicModelHandler;
import com.touchtype_fluency.service.FluencyService;
import com.touchtype_fluency.service.Predictor;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

public final class DynamicLMBackupHelper
  extends FilesBackupHelper
{
  private final Context context;
  private Predictor predictor;
  
  public DynamicLMBackupHelper(Context paramContext)
  {
    this.context = paramContext;
  }
  
  private Predictor getPredictorInstance(Context paramContext)
    throws IOException
  {
    if (this.predictor == null) {
      this.predictor = BackupUtil.enableFluencyService(paramContext).getPredictor();
    }
    return this.predictor;
  }
  
  public List<File> backupData()
    throws IOException
  {
    synchronized (getPredictorInstance(this.context))
    {
      List localList = super.backupData();
      return localList;
    }
  }
  
  protected LinkedHashMap<String, File> getBackupFiles()
    throws IOException
  {
    DynamicModelHandler localDynamicModelHandler = BackupUtil.enableFluencyService(this.context).getDynamicModelHandler();
    LinkedHashMap localLinkedHashMap = new LinkedHashMap();
    localLinkedHashMap.put("model", localDynamicModelHandler.getUserModelFile());
    localLinkedHashMap.put("config", localDynamicModelHandler.getUserConfigFile());
    return localLinkedHashMap;
  }
  
  protected long getStateInternal()
    throws IOException
  {
    synchronized (getPredictorInstance(this.context))
    {
      long l = super.getStateInternal();
      return l;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.backup.DynamicLMBackupHelper
 * JD-Core Version:    0.7.0.1
 */