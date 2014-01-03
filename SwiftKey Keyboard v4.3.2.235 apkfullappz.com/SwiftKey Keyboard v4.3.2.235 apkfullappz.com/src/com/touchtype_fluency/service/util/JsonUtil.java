package com.touchtype_fluency.service.util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.util.List;

public class JsonUtil
{
  public static void addOpt(JsonObject paramJsonObject, String paramString1, String paramString2)
  {
    if (paramString2 != null) {
      paramJsonObject.addProperty(paramString1, paramString2);
    }
  }
  
  public static JsonArray buildJsonArray(List<String> paramList)
  {
    return new Gson().toJsonTree(paramList).getAsJsonArray();
  }
  
  public static float getOpt(JsonObject paramJsonObject, String paramString, float paramFloat)
    throws NumberFormatException
  {
    if (paramJsonObject.has(paramString)) {
      paramFloat = paramJsonObject.get(paramString).getAsFloat();
    }
    return paramFloat;
  }
  
  public static int getOpt(JsonObject paramJsonObject, String paramString, int paramInt)
    throws NumberFormatException
  {
    if (paramJsonObject.has(paramString)) {
      paramInt = paramJsonObject.get(paramString).getAsInt();
    }
    return paramInt;
  }
  
  public static String getOpt(JsonObject paramJsonObject, String paramString1, String paramString2)
  {
    if (paramJsonObject.has(paramString1)) {
      paramString2 = paramJsonObject.get(paramString1).getAsString();
    }
    return paramString2;
  }
  
  public static boolean getOpt(JsonObject paramJsonObject, String paramString, boolean paramBoolean)
  {
    if (paramJsonObject.has(paramString)) {
      paramBoolean = paramJsonObject.get(paramString).getAsBoolean();
    }
    return paramBoolean;
  }
  
  public static JsonElement getOrThrow(JsonObject paramJsonObject, String paramString)
  {
    JsonElement localJsonElement = paramJsonObject.get(paramString);
    if (localJsonElement == null) {
      throw new JsonParseException("Element \"" + paramString + "\" doesn't exist on: " + paramJsonObject);
    }
    return localJsonElement;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.util.JsonUtil
 * JD-Core Version:    0.7.0.1
 */