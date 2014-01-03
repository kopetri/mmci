package com.touchtype.preferences.heatmap.util;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.touchtype.util.LogUtil;
import java.io.File;
import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public final class JsonUtil
{
  private static final String TAG = JsonUtil.class.getSimpleName();
  
  public static int getFreshRec(File paramFile, String paramString)
    throws JSONException, IOException
  {
    return getJSONObject(paramFile).getInt(paramString);
  }
  
  private static JSONObject getJSONObject(File paramFile)
    throws IOException, JSONException
  {
    if (!paramFile.exists()) {
      return new JSONObject();
    }
    return (JSONObject)new JSONTokener(Files.toString(paramFile, Charsets.UTF_8)).nextValue();
  }
  
  public static boolean isAlphabeticLayoutModel(File paramFile)
  {
    try
    {
      JSONObject localJSONObject = getJSONObject(paramFile);
      int i = localJSONObject.length();
      boolean bool = false;
      if (i >= 20) {
        bool = true;
      }
      return bool;
    }
    catch (JSONException localJSONException)
    {
      LogUtil.e(TAG, "Incompatible input model file found", localJSONException);
      return false;
    }
    catch (IOException localIOException)
    {
      LogUtil.e(TAG, "Unable to access input model file", localIOException);
    }
    return false;
  }
  
  public static void setFreshRec(File paramFile, String paramString, int paramInt)
    throws JSONException, IOException
  {
    JSONObject localJSONObject = getJSONObject(paramFile);
    localJSONObject.put(paramString, paramInt);
    Files.write(localJSONObject.toString(), paramFile, Charsets.UTF_8);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.preferences.heatmap.util.JsonUtil
 * JD-Core Version:    0.7.0.1
 */