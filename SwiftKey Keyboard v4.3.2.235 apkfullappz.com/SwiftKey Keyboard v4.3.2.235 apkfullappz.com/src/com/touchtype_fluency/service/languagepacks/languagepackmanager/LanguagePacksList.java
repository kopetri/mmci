package com.touchtype_fluency.service.languagepacks.languagepackmanager;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import java.text.Collator;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Vector;

class LanguagePacksList
{
  public static final String CONFIG_FILENAME = "languagePacks.json";
  private final Vector<LanguagePack> languagePacks;
  
  public LanguagePacksList(String paramString, LanguagePackFactory paramLanguagePackFactory, LanguagePacks paramLanguagePacks)
  {
    JsonElement localJsonElement = new JsonParser().parse(paramString.trim());
    if (!localJsonElement.isJsonArray()) {
      throw new JsonSyntaxException("languagePacks.json does not contain an array: " + paramString);
    }
    JsonArray localJsonArray = localJsonElement.getAsJsonArray();
    int i = localJsonArray.size();
    this.languagePacks = new Vector(i);
    for (int j = 0; j < i; j++) {
      this.languagePacks.add(paramLanguagePackFactory.create(localJsonArray.get(j).getAsJsonObject(), paramLanguagePacks));
    }
    sortByName();
  }
  
  private void sortByName()
  {
    Collections.sort(this.languagePacks, new Comparator()
    {
      public int compare(LanguagePack paramAnonymousLanguagePack1, LanguagePack paramAnonymousLanguagePack2)
      {
        return Collator.getInstance().compare(paramAnonymousLanguagePack1.getName(), paramAnonymousLanguagePack2.getName());
      }
    });
  }
  
  public LanguagePack findByID(String paramString)
  {
    Iterator localIterator = this.languagePacks.iterator();
    while (localIterator.hasNext())
    {
      LanguagePack localLanguagePack = (LanguagePack)localIterator.next();
      if (localLanguagePack.getID().startsWith(paramString)) {
        return localLanguagePack;
      }
    }
    return null;
  }
  
  public LanguagePack findLanguage(String paramString1, String paramString2)
  {
    LanguagePack localLanguagePack = findByID(paramString1 + "_" + paramString2);
    if (localLanguagePack == null) {
      localLanguagePack = findByID(paramString1);
    }
    return localLanguagePack;
  }
  
  public Vector<LanguagePack> getLanguagePacks()
  {
    return this.languagePacks;
  }
  
  public void merge(LanguagePacksList paramLanguagePacksList, boolean paramBoolean)
  {
    Iterator localIterator = paramLanguagePacksList.languagePacks.iterator();
    while (localIterator.hasNext())
    {
      LanguagePack localLanguagePack1 = (LanguagePack)localIterator.next();
      LanguagePack localLanguagePack2 = findByID(localLanguagePack1.getID());
      if (localLanguagePack2 != null) {
        localLanguagePack2.merge(localLanguagePack1, paramBoolean);
      } else {
        this.languagePacks.add(localLanguagePack1);
      }
    }
  }
  
  public String toJSON()
  {
    JsonArray localJsonArray = new JsonArray();
    Iterator localIterator = this.languagePacks.iterator();
    while (localIterator.hasNext()) {
      localJsonArray.add(((LanguagePack)localIterator.next()).toJSON());
    }
    return localJsonArray.toString();
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    Iterator localIterator = this.languagePacks.iterator();
    while (localIterator.hasNext()) {
      localStringBuilder.append((LanguagePack)localIterator.next());
    }
    return localStringBuilder.toString();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.languagepacks.languagepackmanager.LanguagePacksList
 * JD-Core Version:    0.7.0.1
 */