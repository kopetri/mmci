package com.touchtype_fluency.service.languagepacks.languagepackmanager;

import android.content.Context;
import com.google.gson.JsonObject;
import com.touchtype.social.UserNotificationManager;
import com.touchtype.storage.AndroidTmpDirectoryHandler;
import com.touchtype.storage.FolderDecorator;

class LanguagePackFactory
{
  private final FolderDecorator languagePacksFolder;
  private final Context mContext;
  private final UserNotificationManager userNotificationManager;
  
  public LanguagePackFactory(Context paramContext, FolderDecorator paramFolderDecorator, UserNotificationManager paramUserNotificationManager)
  {
    this.mContext = paramContext;
    this.languagePacksFolder = paramFolderDecorator;
    this.userNotificationManager = paramUserNotificationManager;
  }
  
  public LanguagePack create(JsonObject paramJsonObject, LanguagePacks paramLanguagePacks)
  {
    return new LanguagePack(paramJsonObject, paramLanguagePacks, this.languagePacksFolder, new AndroidTmpDirectoryHandler(this.mContext), this.userNotificationManager);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.languagepacks.languagepackmanager.LanguagePackFactory
 * JD-Core Version:    0.7.0.1
 */