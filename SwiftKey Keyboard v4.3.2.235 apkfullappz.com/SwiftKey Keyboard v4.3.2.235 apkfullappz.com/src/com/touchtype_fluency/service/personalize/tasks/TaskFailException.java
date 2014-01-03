package com.touchtype_fluency.service.personalize.tasks;

import com.touchtype_fluency.service.personalize.PersonalizationFailedReason;

class TaskFailException
  extends Exception
{
  private PersonalizationFailedReason mReason;
  
  TaskFailException(String paramString, PersonalizationFailedReason paramPersonalizationFailedReason)
  {
    super(paramString + " Failed reason: " + paramPersonalizationFailedReason.toString());
    this.mReason = paramPersonalizationFailedReason;
  }
  
  PersonalizationFailedReason getReason()
  {
    return this.mReason;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.personalize.tasks.TaskFailException
 * JD-Core Version:    0.7.0.1
 */