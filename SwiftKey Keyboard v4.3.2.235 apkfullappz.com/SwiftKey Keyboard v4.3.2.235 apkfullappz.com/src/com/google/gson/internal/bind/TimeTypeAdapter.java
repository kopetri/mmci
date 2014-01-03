package com.google.gson.internal.bind;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public final class TimeTypeAdapter
  extends TypeAdapter<Time>
{
  public static final TypeAdapterFactory FACTORY = new TypeAdapterFactory()
  {
    public <T> TypeAdapter<T> create(Gson paramAnonymousGson, TypeToken<T> paramAnonymousTypeToken)
    {
      if (paramAnonymousTypeToken.getRawType() == Time.class) {
        return new TimeTypeAdapter();
      }
      return null;
    }
  };
  private final DateFormat format = new SimpleDateFormat("hh:mm:ss a");
  
  /* Error */
  public Time read(com.google.gson.stream.JsonReader paramJsonReader)
    throws IOException
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_1
    //   3: invokevirtual 43	com/google/gson/stream/JsonReader:peek	()Lcom/google/gson/stream/JsonToken;
    //   6: getstatic 49	com/google/gson/stream/JsonToken:NULL	Lcom/google/gson/stream/JsonToken;
    //   9: if_acmpne +13 -> 22
    //   12: aload_1
    //   13: invokevirtual 52	com/google/gson/stream/JsonReader:nextNull	()V
    //   16: aconst_null
    //   17: astore_3
    //   18: aload_0
    //   19: monitorexit
    //   20: aload_3
    //   21: areturn
    //   22: new 54	java/sql/Time
    //   25: dup
    //   26: aload_0
    //   27: getfield 28	com/google/gson/internal/bind/TimeTypeAdapter:format	Ljava/text/DateFormat;
    //   30: aload_1
    //   31: invokevirtual 58	com/google/gson/stream/JsonReader:nextString	()Ljava/lang/String;
    //   34: invokevirtual 64	java/text/DateFormat:parse	(Ljava/lang/String;)Ljava/util/Date;
    //   37: invokevirtual 70	java/util/Date:getTime	()J
    //   40: invokespecial 73	java/sql/Time:<init>	(J)V
    //   43: astore_3
    //   44: goto -26 -> 18
    //   47: astore 4
    //   49: new 75	com/google/gson/JsonSyntaxException
    //   52: dup
    //   53: aload 4
    //   55: invokespecial 78	com/google/gson/JsonSyntaxException:<init>	(Ljava/lang/Throwable;)V
    //   58: athrow
    //   59: astore_2
    //   60: aload_0
    //   61: monitorexit
    //   62: aload_2
    //   63: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	64	0	this	TimeTypeAdapter
    //   0	64	1	paramJsonReader	com.google.gson.stream.JsonReader
    //   59	4	2	localObject	Object
    //   17	27	3	localTime	Time
    //   47	7	4	localParseException	java.text.ParseException
    // Exception table:
    //   from	to	target	type
    //   22	44	47	java/text/ParseException
    //   2	16	59	finally
    //   22	44	59	finally
    //   49	59	59	finally
  }
  
  public void write(JsonWriter paramJsonWriter, Time paramTime)
    throws IOException
  {
    if (paramTime == null) {}
    String str;
    for (Object localObject2 = null;; localObject2 = str)
    {
      try
      {
        paramJsonWriter.value((String)localObject2);
        return;
      }
      finally {}
      str = this.format.format(paramTime);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.gson.internal.bind.TimeTypeAdapter
 * JD-Core Version:    0.7.0.1
 */