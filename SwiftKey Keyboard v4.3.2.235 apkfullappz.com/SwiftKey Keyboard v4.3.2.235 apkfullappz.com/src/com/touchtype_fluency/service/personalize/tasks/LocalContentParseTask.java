package com.touchtype_fluency.service.personalize.tasks;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import com.google.common.base.Preconditions;
import com.touchtype.cloud.CloudService;
import com.touchtype.cloud.CloudService.LocalBinder;
import com.touchtype_fluency.Sequence.Type;
import com.touchtype_fluency.service.Predictor;
import com.touchtype_fluency.service.personalize.PersonalizationFailedReason;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public abstract class LocalContentParseTask
  extends PersonalizationTask
{
  private static final int RETRIES = 2;
  private CountDownLatch mBindToCloudServiceLatch = null;
  private boolean mCancelRequest = false;
  private List<String> mCloudContentFieldHints = null;
  private List<String> mCloudContentToLearn = null;
  private CloudService mCloudService = null;
  private ServiceConnection mCloudServiceConnection = null;
  private final Uri mContentUri;
  private Context mContext;
  private Predictor mPredictor;
  
  public LocalContentParseTask(PersonalizationTaskExecutor paramPersonalizationTaskExecutor, Context paramContext, PersonalizationTaskListener paramPersonalizationTaskListener, Predictor paramPredictor, Uri paramUri)
  {
    super(paramPersonalizationTaskExecutor, paramPersonalizationTaskListener, 2, paramUri.toString());
    this.mContext = paramContext.getApplicationContext();
    Preconditions.checkNotNull(paramPredictor);
    this.mPredictor = paramPredictor;
    this.mContentUri = paramUri;
  }
  
  private void initialiseCloudService()
  {
    this.mBindToCloudServiceLatch = new CountDownLatch(1);
    this.mCloudServiceConnection = new ServiceConnection()
    {
      public void onServiceConnected(ComponentName paramAnonymousComponentName, IBinder paramAnonymousIBinder)
      {
        LocalContentParseTask.access$002(LocalContentParseTask.this, ((CloudService.LocalBinder)paramAnonymousIBinder).getService());
        LocalContentParseTask.access$102(LocalContentParseTask.this, new ArrayList());
        LocalContentParseTask.access$202(LocalContentParseTask.this, new ArrayList());
        LocalContentParseTask.this.mBindToCloudServiceLatch.countDown();
      }
      
      public void onServiceDisconnected(ComponentName paramAnonymousComponentName)
      {
        LocalContentParseTask.access$002(LocalContentParseTask.this, null);
        LocalContentParseTask.access$102(LocalContentParseTask.this, null);
        LocalContentParseTask.access$202(LocalContentParseTask.this, null);
      }
    };
    this.mContext.bindService(new Intent(this.mContext, CloudService.class), this.mCloudServiceConnection, 1);
  }
  
  public void cancel()
  {
    this.mCancelRequest = true;
  }
  
  /* Error */
  public void compute()
    throws TaskFailException
  {
    // Byte code:
    //   0: getstatic 114	com/touchtype_fluency/service/personalize/tasks/LocalContentParseTask$ProgressResult:PROGRESS_FAIL	Lcom/touchtype_fluency/service/personalize/tasks/LocalContentParseTask$ProgressResult;
    //   3: astore_1
    //   4: aload_0
    //   5: getfield 56	com/touchtype_fluency/service/personalize/tasks/LocalContentParseTask:mContext	Landroid/content/Context;
    //   8: invokevirtual 118	android/content/Context:getContentResolver	()Landroid/content/ContentResolver;
    //   11: aload_0
    //   12: getfield 66	com/touchtype_fluency/service/personalize/tasks/LocalContentParseTask:mContentUri	Landroid/net/Uri;
    //   15: aload_0
    //   16: invokevirtual 122	com/touchtype_fluency/service/personalize/tasks/LocalContentParseTask:getProjection	()[Ljava/lang/String;
    //   19: aload_0
    //   20: invokevirtual 125	com/touchtype_fluency/service/personalize/tasks/LocalContentParseTask:getSelection	()Ljava/lang/String;
    //   23: aconst_null
    //   24: aconst_null
    //   25: invokevirtual 131	android/content/ContentResolver:query	(Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   28: astore 6
    //   30: aload 6
    //   32: ifnull +445 -> 477
    //   35: aload 6
    //   37: invokeinterface 137 1 0
    //   42: ifne +20 -> 62
    //   45: getstatic 140	com/touchtype_fluency/service/personalize/tasks/LocalContentParseTask$ProgressResult:NO_CONTENT	Lcom/touchtype_fluency/service/personalize/tasks/LocalContentParseTask$ProgressResult;
    //   48: astore_1
    //   49: aload 6
    //   51: invokeinterface 143 1 0
    //   56: aload_0
    //   57: aload_1
    //   58: invokevirtual 147	com/touchtype_fluency/service/personalize/tasks/LocalContentParseTask:evaluateProgressResult	(Lcom/touchtype_fluency/service/personalize/tasks/LocalContentParseTask$ProgressResult;)V
    //   61: return
    //   62: aload_0
    //   63: getfield 38	com/touchtype_fluency/service/personalize/tasks/LocalContentParseTask:mCancelRequest	Z
    //   66: ifne +324 -> 390
    //   69: aload 6
    //   71: invokeinterface 151 1 0
    //   76: ifeq +314 -> 390
    //   79: aload_0
    //   80: getfield 56	com/touchtype_fluency/service/personalize/tasks/LocalContentParseTask:mContext	Landroid/content/Context;
    //   83: invokestatic 157	com/touchtype/preferences/TouchTypePreferences:getInstance	(Landroid/content/Context;)Lcom/touchtype/preferences/TouchTypePreferences;
    //   86: invokevirtual 160	com/touchtype/preferences/TouchTypePreferences:isCloudAccountSetup	()Z
    //   89: istore 7
    //   91: aload_0
    //   92: getfield 42	com/touchtype_fluency/service/personalize/tasks/LocalContentParseTask:mCloudService	Lcom/touchtype/cloud/CloudService;
    //   95: ifnonnull +204 -> 299
    //   98: iload 7
    //   100: ifeq +199 -> 299
    //   103: aload_0
    //   104: invokespecial 162	com/touchtype_fluency/service/personalize/tasks/LocalContentParseTask:initialiseCloudService	()V
    //   107: aload_0
    //   108: getfield 40	com/touchtype_fluency/service/personalize/tasks/LocalContentParseTask:mBindToCloudServiceLatch	Ljava/util/concurrent/CountDownLatch;
    //   111: invokevirtual 165	java/util/concurrent/CountDownLatch:await	()V
    //   114: aload_0
    //   115: aload 6
    //   117: invokevirtual 169	com/touchtype_fluency/service/personalize/tasks/LocalContentParseTask:getRowContentToLearn	(Landroid/database/Cursor;)Ljava/util/List;
    //   120: invokeinterface 175 1 0
    //   125: astore 9
    //   127: aload 9
    //   129: invokeinterface 180 1 0
    //   134: ifeq -72 -> 62
    //   137: aload 9
    //   139: invokeinterface 184 1 0
    //   144: checkcast 186	java/lang/String
    //   147: astore 10
    //   149: aload 10
    //   151: ifnull +219 -> 370
    //   154: aload 10
    //   156: invokevirtual 189	java/lang/String:length	()I
    //   159: ifle +211 -> 370
    //   162: aload_0
    //   163: aload 10
    //   165: invokevirtual 193	com/touchtype_fluency/service/personalize/tasks/LocalContentParseTask:getFieldHint	(Ljava/lang/String;)Ljava/lang/String;
    //   168: astore 11
    //   170: aload_0
    //   171: getfield 64	com/touchtype_fluency/service/personalize/tasks/LocalContentParseTask:mPredictor	Lcom/touchtype_fluency/service/Predictor;
    //   174: aload 10
    //   176: aload_0
    //   177: invokevirtual 197	com/touchtype_fluency/service/personalize/tasks/LocalContentParseTask:getSequenceType	()Lcom/touchtype_fluency/Sequence$Type;
    //   180: aload 11
    //   182: invokeinterface 203 4 0
    //   187: aload_0
    //   188: getfield 42	com/touchtype_fluency/service/personalize/tasks/LocalContentParseTask:mCloudService	Lcom/touchtype/cloud/CloudService;
    //   191: ifnull -64 -> 127
    //   194: aload_0
    //   195: getfield 46	com/touchtype_fluency/service/personalize/tasks/LocalContentParseTask:mCloudContentToLearn	Ljava/util/List;
    //   198: aload 10
    //   200: invokeinterface 207 2 0
    //   205: pop
    //   206: aload_0
    //   207: getfield 48	com/touchtype_fluency/service/personalize/tasks/LocalContentParseTask:mCloudContentFieldHints	Ljava/util/List;
    //   210: aload 11
    //   212: invokeinterface 207 2 0
    //   217: pop
    //   218: goto -91 -> 127
    //   221: astore 5
    //   223: aload_0
    //   224: getfield 211	com/touchtype_fluency/service/personalize/tasks/LocalContentParseTask:TAG	Ljava/lang/String;
    //   227: new 213	java/lang/StringBuilder
    //   230: dup
    //   231: ldc 215
    //   233: invokespecial 218	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   236: aload 5
    //   238: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   241: invokevirtual 223	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   244: invokestatic 229	com/touchtype/util/LogUtil:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   247: aload_0
    //   248: aload_1
    //   249: invokevirtual 147	com/touchtype_fluency/service/personalize/tasks/LocalContentParseTask:evaluateProgressResult	(Lcom/touchtype_fluency/service/personalize/tasks/LocalContentParseTask$ProgressResult;)V
    //   252: return
    //   253: astore 8
    //   255: aload_0
    //   256: getfield 211	com/touchtype_fluency/service/personalize/tasks/LocalContentParseTask:TAG	Ljava/lang/String;
    //   259: ldc 231
    //   261: invokestatic 229	com/touchtype/util/LogUtil:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   264: goto -150 -> 114
    //   267: astore 4
    //   269: aload_0
    //   270: getfield 211	com/touchtype_fluency/service/personalize/tasks/LocalContentParseTask:TAG	Ljava/lang/String;
    //   273: new 213	java/lang/StringBuilder
    //   276: dup
    //   277: ldc 233
    //   279: invokespecial 218	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   282: aload 4
    //   284: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   287: invokevirtual 223	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   290: invokestatic 229	com/touchtype/util/LogUtil:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   293: aload_0
    //   294: aload_1
    //   295: invokevirtual 147	com/touchtype_fluency/service/personalize/tasks/LocalContentParseTask:evaluateProgressResult	(Lcom/touchtype_fluency/service/personalize/tasks/LocalContentParseTask$ProgressResult;)V
    //   298: return
    //   299: aload_0
    //   300: getfield 42	com/touchtype_fluency/service/personalize/tasks/LocalContentParseTask:mCloudService	Lcom/touchtype/cloud/CloudService;
    //   303: ifnull -189 -> 114
    //   306: iload 7
    //   308: ifne -194 -> 114
    //   311: aload_0
    //   312: getfield 56	com/touchtype_fluency/service/personalize/tasks/LocalContentParseTask:mContext	Landroid/content/Context;
    //   315: aload_0
    //   316: getfield 44	com/touchtype_fluency/service/personalize/tasks/LocalContentParseTask:mCloudServiceConnection	Landroid/content/ServiceConnection;
    //   319: invokevirtual 237	android/content/Context:unbindService	(Landroid/content/ServiceConnection;)V
    //   322: aload_0
    //   323: aconst_null
    //   324: putfield 42	com/touchtype_fluency/service/personalize/tasks/LocalContentParseTask:mCloudService	Lcom/touchtype/cloud/CloudService;
    //   327: aload_0
    //   328: aconst_null
    //   329: putfield 46	com/touchtype_fluency/service/personalize/tasks/LocalContentParseTask:mCloudContentToLearn	Ljava/util/List;
    //   332: aload_0
    //   333: aconst_null
    //   334: putfield 48	com/touchtype_fluency/service/personalize/tasks/LocalContentParseTask:mCloudContentFieldHints	Ljava/util/List;
    //   337: goto -223 -> 114
    //   340: astore_3
    //   341: aload_0
    //   342: getfield 211	com/touchtype_fluency/service/personalize/tasks/LocalContentParseTask:TAG	Ljava/lang/String;
    //   345: new 213	java/lang/StringBuilder
    //   348: dup
    //   349: ldc 239
    //   351: invokespecial 218	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   354: aload_3
    //   355: invokevirtual 222	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   358: invokevirtual 223	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   361: invokestatic 229	com/touchtype/util/LogUtil:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   364: aload_0
    //   365: aload_1
    //   366: invokevirtual 147	com/touchtype_fluency/service/personalize/tasks/LocalContentParseTask:evaluateProgressResult	(Lcom/touchtype_fluency/service/personalize/tasks/LocalContentParseTask$ProgressResult;)V
    //   369: return
    //   370: aload_0
    //   371: getfield 211	com/touchtype_fluency/service/personalize/tasks/LocalContentParseTask:TAG	Ljava/lang/String;
    //   374: ldc 241
    //   376: invokestatic 244	com/touchtype/util/LogUtil:w	(Ljava/lang/String;Ljava/lang/String;)V
    //   379: goto -252 -> 127
    //   382: astore_2
    //   383: aload_0
    //   384: aload_1
    //   385: invokevirtual 147	com/touchtype_fluency/service/personalize/tasks/LocalContentParseTask:evaluateProgressResult	(Lcom/touchtype_fluency/service/personalize/tasks/LocalContentParseTask$ProgressResult;)V
    //   388: aload_2
    //   389: athrow
    //   390: aload_0
    //   391: getfield 38	com/touchtype_fluency/service/personalize/tasks/LocalContentParseTask:mCancelRequest	Z
    //   394: ifne +62 -> 456
    //   397: aload_0
    //   398: getfield 64	com/touchtype_fluency/service/personalize/tasks/LocalContentParseTask:mPredictor	Lcom/touchtype_fluency/service/Predictor;
    //   401: ifnull +55 -> 456
    //   404: aload_0
    //   405: getfield 64	com/touchtype_fluency/service/personalize/tasks/LocalContentParseTask:mPredictor	Lcom/touchtype_fluency/service/Predictor;
    //   408: invokeinterface 247 1 0
    //   413: aload_0
    //   414: getfield 42	com/touchtype_fluency/service/personalize/tasks/LocalContentParseTask:mCloudService	Lcom/touchtype/cloud/CloudService;
    //   417: ifnull +32 -> 449
    //   420: aload_0
    //   421: getfield 42	com/touchtype_fluency/service/personalize/tasks/LocalContentParseTask:mCloudService	Lcom/touchtype/cloud/CloudService;
    //   424: aload_0
    //   425: getfield 46	com/touchtype_fluency/service/personalize/tasks/LocalContentParseTask:mCloudContentToLearn	Ljava/util/List;
    //   428: getstatic 253	com/touchtype_fluency/Sequence$Type:MESSAGE_START	Lcom/touchtype_fluency/Sequence$Type;
    //   431: aload_0
    //   432: getfield 48	com/touchtype_fluency/service/personalize/tasks/LocalContentParseTask:mCloudContentFieldHints	Ljava/util/List;
    //   435: invokevirtual 257	com/touchtype/cloud/CloudService:addMultipleTextSequencesToSyncModel	(Ljava/util/List;Lcom/touchtype_fluency/Sequence$Type;Ljava/util/List;)V
    //   438: aload_0
    //   439: getfield 56	com/touchtype_fluency/service/personalize/tasks/LocalContentParseTask:mContext	Landroid/content/Context;
    //   442: aload_0
    //   443: getfield 44	com/touchtype_fluency/service/personalize/tasks/LocalContentParseTask:mCloudServiceConnection	Landroid/content/ServiceConnection;
    //   446: invokevirtual 237	android/content/Context:unbindService	(Landroid/content/ServiceConnection;)V
    //   449: getstatic 260	com/touchtype_fluency/service/personalize/tasks/LocalContentParseTask$ProgressResult:PROGRESS_SUCCESS	Lcom/touchtype_fluency/service/personalize/tasks/LocalContentParseTask$ProgressResult;
    //   452: astore_1
    //   453: goto -404 -> 49
    //   456: aload_0
    //   457: getfield 38	com/touchtype_fluency/service/personalize/tasks/LocalContentParseTask:mCancelRequest	Z
    //   460: ifeq +10 -> 470
    //   463: getstatic 263	com/touchtype_fluency/service/personalize/tasks/LocalContentParseTask$ProgressResult:PROGRESS_CANCELLED	Lcom/touchtype_fluency/service/personalize/tasks/LocalContentParseTask$ProgressResult;
    //   466: astore_1
    //   467: goto -418 -> 49
    //   470: getstatic 114	com/touchtype_fluency/service/personalize/tasks/LocalContentParseTask$ProgressResult:PROGRESS_FAIL	Lcom/touchtype_fluency/service/personalize/tasks/LocalContentParseTask$ProgressResult;
    //   473: astore_1
    //   474: goto -425 -> 49
    //   477: aload_0
    //   478: getfield 211	com/touchtype_fluency/service/personalize/tasks/LocalContentParseTask:TAG	Ljava/lang/String;
    //   481: ldc_w 265
    //   484: invokestatic 229	com/touchtype/util/LogUtil:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   487: goto -431 -> 56
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	490	0	this	LocalContentParseTask
    //   3	471	1	localProgressResult	ProgressResult
    //   382	7	2	localObject	Object
    //   340	15	3	localSecurityException	java.lang.SecurityException
    //   267	16	4	localSQLiteException	android.database.sqlite.SQLiteException
    //   221	16	5	localPredictorNotReadyException	com.touchtype_fluency.service.PredictorNotReadyException
    //   28	88	6	localCursor	Cursor
    //   89	218	7	bool	boolean
    //   253	1	8	localInterruptedException	java.lang.InterruptedException
    //   125	13	9	localIterator	java.util.Iterator
    //   147	52	10	str1	String
    //   168	43	11	str2	String
    // Exception table:
    //   from	to	target	type
    //   4	30	221	com/touchtype_fluency/service/PredictorNotReadyException
    //   35	49	221	com/touchtype_fluency/service/PredictorNotReadyException
    //   49	56	221	com/touchtype_fluency/service/PredictorNotReadyException
    //   62	98	221	com/touchtype_fluency/service/PredictorNotReadyException
    //   103	107	221	com/touchtype_fluency/service/PredictorNotReadyException
    //   107	114	221	com/touchtype_fluency/service/PredictorNotReadyException
    //   114	127	221	com/touchtype_fluency/service/PredictorNotReadyException
    //   127	149	221	com/touchtype_fluency/service/PredictorNotReadyException
    //   154	218	221	com/touchtype_fluency/service/PredictorNotReadyException
    //   255	264	221	com/touchtype_fluency/service/PredictorNotReadyException
    //   299	306	221	com/touchtype_fluency/service/PredictorNotReadyException
    //   311	337	221	com/touchtype_fluency/service/PredictorNotReadyException
    //   370	379	221	com/touchtype_fluency/service/PredictorNotReadyException
    //   390	449	221	com/touchtype_fluency/service/PredictorNotReadyException
    //   449	453	221	com/touchtype_fluency/service/PredictorNotReadyException
    //   456	467	221	com/touchtype_fluency/service/PredictorNotReadyException
    //   470	474	221	com/touchtype_fluency/service/PredictorNotReadyException
    //   477	487	221	com/touchtype_fluency/service/PredictorNotReadyException
    //   107	114	253	java/lang/InterruptedException
    //   4	30	267	android/database/sqlite/SQLiteException
    //   35	49	267	android/database/sqlite/SQLiteException
    //   49	56	267	android/database/sqlite/SQLiteException
    //   62	98	267	android/database/sqlite/SQLiteException
    //   103	107	267	android/database/sqlite/SQLiteException
    //   107	114	267	android/database/sqlite/SQLiteException
    //   114	127	267	android/database/sqlite/SQLiteException
    //   127	149	267	android/database/sqlite/SQLiteException
    //   154	218	267	android/database/sqlite/SQLiteException
    //   255	264	267	android/database/sqlite/SQLiteException
    //   299	306	267	android/database/sqlite/SQLiteException
    //   311	337	267	android/database/sqlite/SQLiteException
    //   370	379	267	android/database/sqlite/SQLiteException
    //   390	449	267	android/database/sqlite/SQLiteException
    //   449	453	267	android/database/sqlite/SQLiteException
    //   456	467	267	android/database/sqlite/SQLiteException
    //   470	474	267	android/database/sqlite/SQLiteException
    //   477	487	267	android/database/sqlite/SQLiteException
    //   4	30	340	java/lang/SecurityException
    //   35	49	340	java/lang/SecurityException
    //   49	56	340	java/lang/SecurityException
    //   62	98	340	java/lang/SecurityException
    //   103	107	340	java/lang/SecurityException
    //   107	114	340	java/lang/SecurityException
    //   114	127	340	java/lang/SecurityException
    //   127	149	340	java/lang/SecurityException
    //   154	218	340	java/lang/SecurityException
    //   255	264	340	java/lang/SecurityException
    //   299	306	340	java/lang/SecurityException
    //   311	337	340	java/lang/SecurityException
    //   370	379	340	java/lang/SecurityException
    //   390	449	340	java/lang/SecurityException
    //   449	453	340	java/lang/SecurityException
    //   456	467	340	java/lang/SecurityException
    //   470	474	340	java/lang/SecurityException
    //   477	487	340	java/lang/SecurityException
    //   4	30	382	finally
    //   35	49	382	finally
    //   49	56	382	finally
    //   62	98	382	finally
    //   103	107	382	finally
    //   107	114	382	finally
    //   114	127	382	finally
    //   127	149	382	finally
    //   154	218	382	finally
    //   223	247	382	finally
    //   255	264	382	finally
    //   269	293	382	finally
    //   299	306	382	finally
    //   311	337	382	finally
    //   341	364	382	finally
    //   370	379	382	finally
    //   390	449	382	finally
    //   449	453	382	finally
    //   456	467	382	finally
    //   470	474	382	finally
    //   477	487	382	finally
  }
  
  protected void evaluateProgressResult(ProgressResult paramProgressResult)
    throws TaskFailException
  {
    switch (2.$SwitchMap$com$touchtype_fluency$service$personalize$tasks$LocalContentParseTask$ProgressResult[paramProgressResult.ordinal()])
    {
    default: 
      throw new TaskFailException("Content Parse failed, preparing the retry", PersonalizationFailedReason.OTHER);
    case 1: 
      notifyListener(true, null);
      return;
    case 2: 
      notifyListener(false, PersonalizationFailedReason.NO_LOCAL_CONTENT);
      return;
    }
    notifyListener(false, PersonalizationFailedReason.OTHER);
  }
  
  abstract String getFieldHint(String paramString);
  
  abstract String[] getProjection();
  
  abstract List<String> getRowContentToLearn(Cursor paramCursor);
  
  abstract String getSelection();
  
  abstract Sequence.Type getSequenceType();
  
  protected static enum ProgressResult
  {
    static
    {
      NO_CONTENT = new ProgressResult("NO_CONTENT", 1);
      PROGRESS_SUCCESS = new ProgressResult("PROGRESS_SUCCESS", 2);
      PROGRESS_CANCELLED = new ProgressResult("PROGRESS_CANCELLED", 3);
      ProgressResult[] arrayOfProgressResult = new ProgressResult[4];
      arrayOfProgressResult[0] = PROGRESS_FAIL;
      arrayOfProgressResult[1] = NO_CONTENT;
      arrayOfProgressResult[2] = PROGRESS_SUCCESS;
      arrayOfProgressResult[3] = PROGRESS_CANCELLED;
      $VALUES = arrayOfProgressResult;
    }
    
    private ProgressResult() {}
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.personalize.tasks.LocalContentParseTask
 * JD-Core Version:    0.7.0.1
 */