package com.touchtype_fluency.service.util;

import java.io.Serializable;
import org.apache.http.NameValuePair;

public class SerializableNameValuePair
  implements Serializable, NameValuePair
{
  private final String name;
  private final String value;
  
  public SerializableNameValuePair(String paramString1, String paramString2)
  {
    this.name = paramString1;
    this.value = paramString2;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {}
    SerializableNameValuePair localSerializableNameValuePair;
    do
    {
      return true;
      if (!(paramObject instanceof SerializableNameValuePair)) {
        return false;
      }
      localSerializableNameValuePair = (SerializableNameValuePair)paramObject;
      if (getName() != null)
      {
        if (getName().equals(localSerializableNameValuePair.getName())) {}
      }
      else {
        while (localSerializableNameValuePair.getName() != null) {
          return false;
        }
      }
      if (getValue() == null) {
        break;
      }
    } while (getValue().equals(localSerializableNameValuePair.getValue()));
    for (;;)
    {
      return false;
      if (localSerializableNameValuePair.getValue() == null) {
        break;
      }
    }
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public String getValue()
  {
    return this.value;
  }
  
  public int hashCode()
  {
    if (this.name != null) {}
    for (int i = this.name.hashCode();; i = 0)
    {
      int j = i * 31;
      String str = this.value;
      int k = 0;
      if (str != null) {
        k = this.value.hashCode();
      }
      return k + j;
    }
  }
  
  public String toString()
  {
    return this.name + "=" + this.value;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.util.SerializableNameValuePair
 * JD-Core Version:    0.7.0.1
 */