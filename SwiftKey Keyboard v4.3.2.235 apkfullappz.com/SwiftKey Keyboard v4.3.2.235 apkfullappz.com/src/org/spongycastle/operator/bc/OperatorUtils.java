package org.spongycastle.operator.bc;

import java.security.Key;
import org.spongycastle.operator.GenericKey;

class OperatorUtils
{
  static byte[] getKeyBytes(GenericKey paramGenericKey)
  {
    if ((paramGenericKey.getRepresentation() instanceof Key)) {
      return ((Key)paramGenericKey.getRepresentation()).getEncoded();
    }
    if ((paramGenericKey.getRepresentation() instanceof byte[])) {
      return (byte[])paramGenericKey.getRepresentation();
    }
    throw new IllegalArgumentException("unknown generic key type");
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.operator.bc.OperatorUtils
 * JD-Core Version:    0.7.0.1
 */