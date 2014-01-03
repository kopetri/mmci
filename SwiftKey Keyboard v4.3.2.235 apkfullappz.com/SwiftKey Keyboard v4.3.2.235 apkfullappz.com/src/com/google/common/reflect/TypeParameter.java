package com.google.common.reflect;

import com.google.common.base.Preconditions;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

public abstract class TypeParameter<T>
  extends TypeCapture<T>
{
  final TypeVariable<?> typeVariable;
  
  protected TypeParameter()
  {
    Type localType = capture();
    Preconditions.checkArgument(localType instanceof TypeVariable, "%s should be a type variable.", new Object[] { localType });
    this.typeVariable = ((TypeVariable)localType);
  }
  
  public final boolean equals(Object paramObject)
  {
    if ((paramObject instanceof TypeParameter))
    {
      TypeParameter localTypeParameter = (TypeParameter)paramObject;
      return this.typeVariable.equals(localTypeParameter.typeVariable);
    }
    return false;
  }
  
  public final int hashCode()
  {
    return this.typeVariable.hashCode();
  }
  
  public String toString()
  {
    return this.typeVariable.toString();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.common.reflect.TypeParameter
 * JD-Core Version:    0.7.0.1
 */