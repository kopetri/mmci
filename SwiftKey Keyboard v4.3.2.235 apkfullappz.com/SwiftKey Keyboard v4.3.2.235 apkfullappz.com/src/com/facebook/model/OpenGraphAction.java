package com.facebook.model;

import java.util.Date;
import java.util.List;
import org.json.JSONObject;

public abstract interface OpenGraphAction
  extends GraphObject
{
  public abstract GraphObject getApplication();
  
  public abstract JSONObject getComments();
  
  public abstract Date getCreatedTime();
  
  public abstract Date getEndTime();
  
  public abstract Date getExpiresTime();
  
  public abstract GraphUser getFrom();
  
  public abstract String getId();
  
  public abstract List<JSONObject> getImage();
  
  public abstract JSONObject getLikes();
  
  public abstract String getMessage();
  
  public abstract GraphPlace getPlace();
  
  public abstract Date getPublishTime();
  
  public abstract String getRef();
  
  public abstract Date getStartTime();
  
  public abstract List<GraphObject> getTags();
  
  public abstract void setApplication(GraphObject paramGraphObject);
  
  public abstract void setComments(JSONObject paramJSONObject);
  
  public abstract void setCreatedTime(Date paramDate);
  
  public abstract void setEndTime(Date paramDate);
  
  public abstract void setExpiresTime(Date paramDate);
  
  public abstract void setFrom(GraphUser paramGraphUser);
  
  public abstract void setId(String paramString);
  
  public abstract void setImage(List<JSONObject> paramList);
  
  public abstract void setLikes(JSONObject paramJSONObject);
  
  public abstract void setMessage(String paramString);
  
  public abstract void setPlace(GraphPlace paramGraphPlace);
  
  public abstract void setPublishTime(Date paramDate);
  
  public abstract void setRef(String paramString);
  
  public abstract void setStartTime(Date paramDate);
  
  public abstract void setTags(List<? extends GraphObject> paramList);
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.facebook.model.OpenGraphAction
 * JD-Core Version:    0.7.0.1
 */