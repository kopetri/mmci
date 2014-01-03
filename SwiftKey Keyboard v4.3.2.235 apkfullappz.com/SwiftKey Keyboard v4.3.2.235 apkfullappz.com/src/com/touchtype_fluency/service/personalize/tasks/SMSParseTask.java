package com.touchtype_fluency.service.personalize.tasks;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import com.touchtype_fluency.Sequence.Type;
import com.touchtype_fluency.service.Predictor;
import java.util.ArrayList;
import java.util.List;

public class SMSParseTask
  extends LocalContentParseTask
{
  private static final String PERSON_SELECTION = "person is NULL";
  private static final String SENT_URI = "content://sms/sent";
  
  public SMSParseTask(PersonalizationTaskExecutor paramPersonalizationTaskExecutor, Context paramContext, PersonalizationTaskListener paramPersonalizationTaskListener, Predictor paramPredictor)
  {
    super(paramPersonalizationTaskExecutor, paramContext, paramPersonalizationTaskListener, paramPredictor, Uri.parse("content://sms/sent"));
  }
  
  String getFieldHint(String paramString)
  {
    return "short_message";
  }
  
  String[] getProjection()
  {
    return new String[] { "body" };
  }
  
  List<String> getRowContentToLearn(Cursor paramCursor)
  {
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(paramCursor.getString(0));
    return localArrayList;
  }
  
  String getSelection()
  {
    return "person is NULL";
  }
  
  Sequence.Type getSequenceType()
  {
    return Sequence.Type.MESSAGE_START;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.personalize.tasks.SMSParseTask
 * JD-Core Version:    0.7.0.1
 */