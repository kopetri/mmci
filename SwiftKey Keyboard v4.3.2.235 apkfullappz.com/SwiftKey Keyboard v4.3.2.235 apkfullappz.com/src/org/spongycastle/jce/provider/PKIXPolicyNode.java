package org.spongycastle.jce.provider;

import java.security.cert.PolicyNode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class PKIXPolicyNode
  implements PolicyNode
{
  protected List children;
  protected boolean critical;
  protected int depth;
  protected Set expectedPolicies;
  protected PolicyNode parent;
  protected Set policyQualifiers;
  protected String validPolicy;
  
  public PKIXPolicyNode(List paramList, int paramInt, Set paramSet1, PolicyNode paramPolicyNode, Set paramSet2, String paramString, boolean paramBoolean)
  {
    this.children = paramList;
    this.depth = paramInt;
    this.expectedPolicies = paramSet1;
    this.parent = paramPolicyNode;
    this.policyQualifiers = paramSet2;
    this.validPolicy = paramString;
    this.critical = paramBoolean;
  }
  
  public void addChild(PKIXPolicyNode paramPKIXPolicyNode)
  {
    this.children.add(paramPKIXPolicyNode);
    paramPKIXPolicyNode.setParent(this);
  }
  
  public Object clone()
  {
    return copy();
  }
  
  public PKIXPolicyNode copy()
  {
    HashSet localHashSet1 = new HashSet();
    Iterator localIterator1 = this.expectedPolicies.iterator();
    while (localIterator1.hasNext()) {
      localHashSet1.add(new String((String)localIterator1.next()));
    }
    HashSet localHashSet2 = new HashSet();
    Iterator localIterator2 = this.policyQualifiers.iterator();
    while (localIterator2.hasNext()) {
      localHashSet2.add(new String((String)localIterator2.next()));
    }
    PKIXPolicyNode localPKIXPolicyNode1 = new PKIXPolicyNode(new ArrayList(), this.depth, localHashSet1, null, localHashSet2, new String(this.validPolicy), this.critical);
    Iterator localIterator3 = this.children.iterator();
    while (localIterator3.hasNext())
    {
      PKIXPolicyNode localPKIXPolicyNode2 = ((PKIXPolicyNode)localIterator3.next()).copy();
      localPKIXPolicyNode2.setParent(localPKIXPolicyNode1);
      localPKIXPolicyNode1.addChild(localPKIXPolicyNode2);
    }
    return localPKIXPolicyNode1;
  }
  
  public Iterator getChildren()
  {
    return this.children.iterator();
  }
  
  public int getDepth()
  {
    return this.depth;
  }
  
  public Set getExpectedPolicies()
  {
    return this.expectedPolicies;
  }
  
  public PolicyNode getParent()
  {
    return this.parent;
  }
  
  public Set getPolicyQualifiers()
  {
    return this.policyQualifiers;
  }
  
  public String getValidPolicy()
  {
    return this.validPolicy;
  }
  
  public boolean hasChildren()
  {
    return !this.children.isEmpty();
  }
  
  public boolean isCritical()
  {
    return this.critical;
  }
  
  public void removeChild(PKIXPolicyNode paramPKIXPolicyNode)
  {
    this.children.remove(paramPKIXPolicyNode);
  }
  
  public void setCritical(boolean paramBoolean)
  {
    this.critical = paramBoolean;
  }
  
  public void setParent(PKIXPolicyNode paramPKIXPolicyNode)
  {
    this.parent = paramPKIXPolicyNode;
  }
  
  public String toString()
  {
    return toString("");
  }
  
  public String toString(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(paramString);
    localStringBuffer.append(this.validPolicy);
    localStringBuffer.append(" {\n");
    for (int i = 0; i < this.children.size(); i++) {
      localStringBuffer.append(((PKIXPolicyNode)this.children.get(i)).toString(paramString + "    "));
    }
    localStringBuffer.append(paramString);
    localStringBuffer.append("}\n");
    return localStringBuffer.toString();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jce.provider.PKIXPolicyNode
 * JD-Core Version:    0.7.0.1
 */