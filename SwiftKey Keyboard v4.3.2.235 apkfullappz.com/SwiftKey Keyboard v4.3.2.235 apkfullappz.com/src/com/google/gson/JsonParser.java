package com.google.gson;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.MalformedJsonException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public final class JsonParser
{
  /* Error */
  public JsonElement parse(JsonReader paramJsonReader)
    throws JsonIOException, JsonSyntaxException
  {
    // Byte code:
    //   0: aload_1
    //   1: invokevirtual 24	com/google/gson/stream/JsonReader:isLenient	()Z
    //   4: istore_2
    //   5: aload_1
    //   6: iconst_1
    //   7: invokevirtual 28	com/google/gson/stream/JsonReader:setLenient	(Z)V
    //   10: aload_1
    //   11: invokestatic 32	com/google/gson/internal/Streams:parse	(Lcom/google/gson/stream/JsonReader;)Lcom/google/gson/JsonElement;
    //   14: astore 6
    //   16: aload_1
    //   17: iload_2
    //   18: invokevirtual 28	com/google/gson/stream/JsonReader:setLenient	(Z)V
    //   21: aload 6
    //   23: areturn
    //   24: astore 5
    //   26: new 34	com/google/gson/JsonParseException
    //   29: dup
    //   30: new 36	java/lang/StringBuilder
    //   33: dup
    //   34: ldc 38
    //   36: invokespecial 41	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   39: aload_1
    //   40: invokevirtual 45	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   43: ldc 47
    //   45: invokevirtual 50	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   48: invokevirtual 54	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   51: aload 5
    //   53: invokespecial 57	com/google/gson/JsonParseException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   56: athrow
    //   57: astore 4
    //   59: aload_1
    //   60: iload_2
    //   61: invokevirtual 28	com/google/gson/stream/JsonReader:setLenient	(Z)V
    //   64: aload 4
    //   66: athrow
    //   67: astore_3
    //   68: new 34	com/google/gson/JsonParseException
    //   71: dup
    //   72: new 36	java/lang/StringBuilder
    //   75: dup
    //   76: ldc 38
    //   78: invokespecial 41	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   81: aload_1
    //   82: invokevirtual 45	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   85: ldc 47
    //   87: invokevirtual 50	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   90: invokevirtual 54	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   93: aload_3
    //   94: invokespecial 57	com/google/gson/JsonParseException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   97: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	98	0	this	JsonParser
    //   0	98	1	paramJsonReader	JsonReader
    //   4	57	2	bool	boolean
    //   67	27	3	localOutOfMemoryError	java.lang.OutOfMemoryError
    //   57	8	4	localObject	Object
    //   24	28	5	localStackOverflowError	java.lang.StackOverflowError
    //   14	8	6	localJsonElement	JsonElement
    // Exception table:
    //   from	to	target	type
    //   10	16	24	java/lang/StackOverflowError
    //   10	16	57	finally
    //   26	57	57	finally
    //   68	98	57	finally
    //   10	16	67	java/lang/OutOfMemoryError
  }
  
  public JsonElement parse(Reader paramReader)
    throws JsonIOException, JsonSyntaxException
  {
    JsonElement localJsonElement;
    try
    {
      JsonReader localJsonReader = new JsonReader(paramReader);
      localJsonElement = parse(localJsonReader);
      if ((!localJsonElement.isJsonNull()) && (localJsonReader.peek() != JsonToken.END_DOCUMENT)) {
        throw new JsonSyntaxException("Did not consume the entire document.");
      }
    }
    catch (MalformedJsonException localMalformedJsonException)
    {
      throw new JsonSyntaxException(localMalformedJsonException);
    }
    catch (IOException localIOException)
    {
      throw new JsonIOException(localIOException);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      throw new JsonSyntaxException(localNumberFormatException);
    }
    return localJsonElement;
  }
  
  public JsonElement parse(String paramString)
    throws JsonSyntaxException
  {
    return parse(new StringReader(paramString));
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.gson.JsonParser
 * JD-Core Version:    0.7.0.1
 */