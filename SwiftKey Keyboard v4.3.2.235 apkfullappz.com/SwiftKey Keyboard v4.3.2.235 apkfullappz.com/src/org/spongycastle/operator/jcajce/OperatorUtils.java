package org.spongycastle.operator.jcajce;

import java.security.Key;
import javax.crypto.spec.SecretKeySpec;
import org.spongycastle.operator.GenericKey;

class OperatorUtils
{
  static Key getJceKey(GenericKey paramGenericKey)
  {
    if ((paramGenericKey.getRepresentation() instanceof Key)) {
      return (Key)paramGenericKey.getRepresentation();
    }
    if ((paramGenericKey.getRepresentation() instanceof byte[])) {
      return new SecretKeySpec((byte[])paramGenericKey.getRepresentation(), "ENC");
    }
    throw new IllegalArgumentException("unknown generic key type");
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.operator.jcajce.OperatorUtils
 * JD-Core Version:    0.7.0.1
 */