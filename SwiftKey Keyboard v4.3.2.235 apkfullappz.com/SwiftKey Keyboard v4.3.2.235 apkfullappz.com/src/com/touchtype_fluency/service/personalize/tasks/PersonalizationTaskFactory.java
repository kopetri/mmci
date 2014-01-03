package com.touchtype_fluency.service.personalize.tasks;

import android.content.Context;
import com.touchtype_fluency.service.Predictor;
import java.io.File;

public class PersonalizationTaskFactory
{
  private static final String TAG = PersonalizationTaskFactory.class.getSimpleName();
  
  public static PersonalizationTask createDeleteRemoteTask(String paramString1, String paramString2, PersonalizationTaskExecutor paramPersonalizationTaskExecutor, PersonalizationTaskListener paramPersonalizationTaskListener)
  {
    return new DeleteRemoteTask(paramPersonalizationTaskExecutor, paramString2, paramPersonalizationTaskListener, paramString1);
  }
  
  public static PersonalizationTask createRemotePersonalizationChain(Context paramContext, String paramString1, String paramString2, PersonalizationTaskExecutor paramPersonalizationTaskExecutor, PersonalizationTaskListener paramPersonalizationTaskListener, File paramFile, Predictor paramPredictor)
  {
    return new InitialRequestTask(paramPersonalizationTaskExecutor, paramString2, paramPersonalizationTaskListener, paramString1, new PollRequestTask(paramPersonalizationTaskExecutor, paramPersonalizationTaskListener, new DownloadTask(paramPersonalizationTaskExecutor, paramPersonalizationTaskListener, paramFile, new MergeTask(paramContext, paramPersonalizationTaskListener, paramPredictor))));
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.personalize.tasks.PersonalizationTaskFactory
 * JD-Core Version:    0.7.0.1
 */