package com.google.gson;

import com.google.gson.internal.Excluder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class GsonBuilder
{
  private boolean complexMapKeySerialization;
  private String datePattern;
  private int dateStyle = 2;
  private boolean escapeHtmlChars = true;
  private Excluder excluder = Excluder.DEFAULT;
  private final List<TypeAdapterFactory> factories = new ArrayList();
  private FieldNamingStrategy fieldNamingPolicy = FieldNamingPolicy.IDENTITY;
  private boolean generateNonExecutableJson;
  private final List<TypeAdapterFactory> hierarchyFactories = new ArrayList();
  private final Map<Type, InstanceCreator<?>> instanceCreators = new HashMap();
  private LongSerializationPolicy longSerializationPolicy = LongSerializationPolicy.DEFAULT;
  private boolean prettyPrinting;
  private boolean serializeNulls;
  private boolean serializeSpecialFloatingPointValues;
  private int timeStyle = 2;
  
  private void addTypeAdaptersForDate(String paramString, int paramInt1, int paramInt2, List<TypeAdapterFactory> paramList)
  {
    if ((paramString != null) && (!"".equals(paramString.trim()))) {}
    for (DefaultDateTypeAdapter localDefaultDateTypeAdapter = new DefaultDateTypeAdapter(paramString);; localDefaultDateTypeAdapter = new DefaultDateTypeAdapter(paramInt1, paramInt2))
    {
      paramList.add(TreeTypeAdapter.newFactory(TypeToken.get(java.util.Date.class), localDefaultDateTypeAdapter));
      paramList.add(TreeTypeAdapter.newFactory(TypeToken.get(Timestamp.class), localDefaultDateTypeAdapter));
      paramList.add(TreeTypeAdapter.newFactory(TypeToken.get(java.sql.Date.class), localDefaultDateTypeAdapter));
      do
      {
        return;
      } while ((paramInt1 == 2) || (paramInt2 == 2));
    }
  }
  
  public Gson create()
  {
    ArrayList localArrayList = new ArrayList();
    localArrayList.addAll(this.factories);
    Collections.reverse(localArrayList);
    localArrayList.addAll(this.hierarchyFactories);
    addTypeAdaptersForDate(this.datePattern, this.dateStyle, this.timeStyle, localArrayList);
    return new Gson(this.excluder, this.fieldNamingPolicy, this.instanceCreators, this.serializeNulls, this.complexMapKeySerialization, this.generateNonExecutableJson, this.escapeHtmlChars, this.prettyPrinting, this.serializeSpecialFloatingPointValues, this.longSerializationPolicy, localArrayList);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.gson.GsonBuilder
 * JD-Core Version:    0.7.0.1
 */