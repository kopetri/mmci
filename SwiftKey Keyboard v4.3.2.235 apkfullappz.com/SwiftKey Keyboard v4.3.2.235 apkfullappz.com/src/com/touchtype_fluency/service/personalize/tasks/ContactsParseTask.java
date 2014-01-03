package com.touchtype_fluency.service.personalize.tasks;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract.Contacts;
import com.touchtype_fluency.Sequence.Type;
import com.touchtype_fluency.service.Predictor;
import com.touchtype_fluency.service.languagepacks.LanguagePackManager;
import com.touchtype_fluency.service.languagepacks.languagepackmanager.LanguagePack;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class ContactsParseTask
  extends LocalContentParseTask
{
  private static final Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
  private static final String NAME_COLUMN = "display_name";
  
  public ContactsParseTask(PersonalizationTaskExecutor paramPersonalizationTaskExecutor, Context paramContext, PersonalizationTaskListener paramPersonalizationTaskListener, Predictor paramPredictor, LanguagePackManager paramLanguagePackManager)
  {
    super(paramPersonalizationTaskExecutor, paramContext, paramPersonalizationTaskListener, paramPredictor, CONTENT_URI);
    Iterator localIterator = paramLanguagePackManager.getEnabledLanguagePacks().iterator();
    while (localIterator.hasNext()) {
      if (((LanguagePack)localIterator.next()).getID().startsWith("zh")) {
        paramPredictor.setInputType("pinyin");
      }
    }
  }
  
  String getFieldHint(String paramString)
  {
    return "name";
  }
  
  String[] getProjection()
  {
    String[] arrayOfString = new String[2];
    arrayOfString[0] = "_id";
    arrayOfString[1] = NAME_COLUMN;
    return arrayOfString;
  }
  
  List<String> getRowContentToLearn(Cursor paramCursor)
  {
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(paramCursor.getString(paramCursor.getColumnIndex(NAME_COLUMN)));
    return localArrayList;
  }
  
  String getSelection()
  {
    return null;
  }
  
  Sequence.Type getSequenceType()
  {
    return Sequence.Type.NORMAL;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.personalize.tasks.ContactsParseTask
 * JD-Core Version:    0.7.0.1
 */