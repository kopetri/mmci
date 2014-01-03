package com.touchtype.keyboard;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import com.touchtype.keyboard.key.Key;
import com.touchtype.keyboard.key.KeyArea;
import com.touchtype.keyboard.key.KeyFactory;
import com.touchtype.keyboard.key.contents.TextContent;
import com.touchtype.keyboard.theme.util.TextRendering.HAlign;
import com.touchtype.keyboard.theme.util.TextRendering.VAlign;
import com.touchtype.util.LogUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

public class MiniKeyboard
  extends BaseKeyboard<Key>
{
  private static final String TAG = MiniKeyboard.class.getSimpleName();
  private KeySplit mCurrentSplit;
  private final float mKeyHeight;
  private final float mMaxWidth;
  private final Orientation mOrientation;
  private final Key mPrimaryKey;
  private int mPrimaryKeyPadding = 0;
  private final boolean mReorder;
  private final int mRows;
  
  private MiniKeyboard(List<Key> paramList, Key paramKey1, Key paramKey2, Context paramContext, Orientation paramOrientation, float paramFloat1, float paramFloat2, int paramInt, boolean paramBoolean)
  {
    super(paramList, paramKey2, paramInt);
    this.mPrimaryKeyPadding = paramContext.getResources().getDimensionPixelSize(2131361861);
    this.mOrientation = paramOrientation;
    this.mPrimaryKey = paramKey1;
    this.mReorder = paramBoolean;
    this.mMaxWidth = paramFloat1;
    this.mKeyHeight = paramFloat2;
    this.mRows = paramInt;
  }
  
  private KeySplit createKeySplit(RectF paramRectF, PointF paramPointF, float paramFloat)
  {
    if (this.mOrientation == Orientation.HORIZONTAL) {
      return new HorizontalKeySplit(paramRectF, paramPointF, paramFloat, null);
    }
    return new VerticalKeySplit(paramRectF, paramPointF);
  }
  
  public static MiniKeyboard createMiniKeyboard(Context paramContext, KeyFactory paramKeyFactory, List<String> paramList, KeyArea paramKeyArea, Orientation paramOrientation, boolean paramBoolean)
  {
    return createMiniKeyboard(paramContext, paramKeyFactory, paramList, (String)paramList.get(0), paramKeyArea, paramOrientation, true, paramBoolean);
  }
  
  public static MiniKeyboard createMiniKeyboard(Context paramContext, KeyFactory paramKeyFactory, List<String> paramList, String paramString, KeyArea paramKeyArea, Orientation paramOrientation, boolean paramBoolean1, boolean paramBoolean2)
  {
    LinkedHashMap localLinkedHashMap = new LinkedHashMap();
    if (paramOrientation == Orientation.VERTICAL) {
      Collections.reverse(paramList);
    }
    float f1 = Float.parseFloat(paramContext.getString(2131296352)) * paramKeyArea.getDrawBounds().height();
    float f2;
    float f3;
    label87:
    int i;
    label103:
    int j;
    label131:
    float f4;
    float f5;
    int k;
    KeyboardLoader.Row localRow;
    Object localObject;
    Iterator localIterator;
    if (paramOrientation == Orientation.HORIZONTAL)
    {
      f2 = paramKeyArea.getDrawBounds().width();
      if (paramOrientation != Orientation.HORIZONTAL) {
        break label360;
      }
      if (paramList.size() == 0) {
        break label354;
      }
      f3 = 1.0F / paramList.size();
      if (paramOrientation != Orientation.VERTICAL) {
        break label366;
      }
      i = paramList.size();
      KeyboardLoader.KeyboardAttributes localKeyboardAttributes = new KeyboardLoader.KeyboardAttributes(paramContext, 0, f3, i, null);
      if (paramOrientation != Orientation.VERTICAL) {
        break label372;
      }
      j = 3;
      f4 = 0.0F;
      f5 = 0.0F;
      k = 0;
      localRow = new KeyboardLoader.Row(localKeyboardAttributes, j);
      localObject = null;
      localIterator = paramList.iterator();
    }
    label401:
    for (;;)
    {
      label164:
      if (!localIterator.hasNext()) {
        break label403;
      }
      String str = (String)localIterator.next();
      if (!localLinkedHashMap.containsKey(str))
      {
        float f6 = f4 + localRow.mDefaultKeyWidth;
        float f7 = f5 + localRow.mDefaultKeyHeight;
        RectF localRectF = new RectF(f4, f5, f6, f7);
        TextContent localTextContent = new TextContent(str, str, paramKeyFactory.mLocale, TextRendering.HAlign.CENTRE, TextRendering.VAlign.CENTRE, 0.8F);
        Key localKey2 = paramKeyFactory.createMiniKeyboardKey(new KeyArea(localRectF, localRow.mEdgeFlags), localTextContent);
        localLinkedHashMap.put(str, localKey2);
        if (paramOrientation == Orientation.HORIZONTAL) {
          f4 += localKey2.getArea().getBounds().width();
        }
        for (k = 1;; k++)
        {
          if ((paramString == null) || (!str.equals(paramString))) {
            break label401;
          }
          localObject = localKey2;
          break label164;
          f2 = 2.0F * paramKeyArea.getDrawBounds().width();
          break;
          label354:
          f3 = 0.0F;
          break label87;
          label360:
          f3 = 1.0F;
          break label87;
          label366:
          i = 1;
          break label103;
          label372:
          j = 12;
          break label131;
          f5 += localKey2.getArea().getBounds().height();
        }
      }
    }
    label403:
    if (paramOrientation == Orientation.VERTICAL)
    {
      KeyArea localKeyArea = localObject.getArea();
      localKeyArea.mEdgeFlags = (0x8 | localKeyArea.mEdgeFlags);
    }
    ArrayList localArrayList = new ArrayList(localLinkedHashMap.values());
    Key localKey1 = paramKeyFactory.createEmptyKey();
    return new MiniKeyboard(localArrayList, localObject, localKey1, paramContext, paramOrientation, f2, f1, k, paramBoolean1);
  }
  
  private boolean needsReorder(KeySplit paramKeySplit)
  {
    if (!this.mReorder) {}
    while (this.mCurrentSplit.isSplit()) {
      return false;
    }
    return true;
  }
  
  private void order(KeySplit paramKeySplit)
  {
    if (!needsReorder(paramKeySplit)) {
      return;
    }
    List localList = paramKeySplit.split();
    PointF localPointF1 = new PointF();
    Iterator localIterator = localList.iterator();
    while (localIterator.hasNext())
    {
      Key localKey = (Key)localIterator.next();
      PointF localPointF2 = paramKeySplit.repositionKey(localKey, localPointF1);
      localPointF1.offset(localPointF2.x, localPointF2.y);
      localKey.getArea().mEdgeFlags = paramKeySplit.getDefaultEdgeFlags();
    }
    ((Key)localList.get(0)).getArea().mEdgeFlags = paramKeySplit.getFirstEdgeFlags();
    ((Key)localList.get(-1 + localList.size())).getArea().mEdgeFlags = paramKeySplit.getLastEdgeFlags();
  }
  
  public RectF fitAtMost(RectF paramRectF, PointF paramPointF)
  {
    this.mCurrentSplit = createKeySplit(paramRectF, paramPointF, this.mMaxWidth);
    order(this.mCurrentSplit);
    return this.mCurrentSplit.getSpaceInParent();
  }
  
  public Key getInitialKey()
  {
    if (this.mPrimaryKey != null) {
      return this.mPrimaryKey;
    }
    if (this.mOrientation == Orientation.VERTICAL) {
      return (Key)this.mKeys.get(0);
    }
    return null;
  }
  
  private final class HorizontalKeySplit
    extends MiniKeyboard.KeySplit
  {
    private final Point mKeySpace;
    private final float mKeyWidth;
    private final List<Key> mLeftList = new ArrayList();
    private final List<Key> mRightList = new ArrayList();
    private RectF mSpaceInParent;
    private boolean mSplit = false;
    
    private HorizontalKeySplit(RectF paramRectF, PointF paramPointF, float paramFloat)
    {
      super(paramRectF, paramPointF);
      this.mKeyWidth = calculateWidth(paramRectF, paramPointF, paramFloat);
      this.mKeySpace = getAvailableKeySpace(paramRectF, paramPointF, this.mKeyWidth);
    }
    
    private List<Key> build(Key paramKey)
    {
      ArrayList localArrayList = new ArrayList();
      Collections.reverse(this.mRightList);
      localArrayList.addAll(this.mLeftList);
      localArrayList.add(paramKey);
      localArrayList.addAll(this.mRightList);
      return localArrayList;
    }
    
    private float calculateWidth(RectF paramRectF, PointF paramPointF, float paramFloat)
    {
      Point localPoint = getAvailableKeySpace(paramRectF, paramPointF, paramFloat);
      if (localPoint.x + localPoint.y < -1 + MiniKeyboard.this.mKeys.size()) {
        paramFloat = getMaxWidth(paramRectF, paramPointF, MiniKeyboard.this.mKeys.size());
      }
      return paramFloat;
    }
    
    private float getAlignmentOffset(float paramFloat1, float paramFloat2, float paramFloat3)
    {
      float f = (paramFloat1 - paramFloat2) % paramFloat3 / paramFloat3 - 0.5F;
      if (f < 0.0F) {
        f += 1.0F;
      }
      return f;
    }
    
    private Point getAvailableKeySpace(RectF paramRectF, PointF paramPointF, float paramFloat)
    {
      PointF localPointF = getAvailableSpace(paramRectF, paramPointF, paramFloat);
      return new Point((int)Math.floor(localPointF.x / paramFloat), (int)Math.floor(localPointF.y / paramFloat));
    }
    
    private PointF getAvailableSpace(RectF paramRectF, PointF paramPointF, float paramFloat)
    {
      return new PointF(paramPointF.x - MiniKeyboard.this.mPrimaryKeyPadding - paramFloat / 2.0F, paramRectF.width() - paramPointF.x - paramFloat / 2.0F - MiniKeyboard.this.mPrimaryKeyPadding);
    }
    
    private float getMaxWidth(RectF paramRectF, PointF paramPointF, int paramInt)
    {
      float f1;
      if ((paramRectF.width() == 0.0F) || (paramInt == 0)) {
        f1 = 0.0F;
      }
      float f2;
      Point localPoint;
      do
      {
        return f1;
        f1 = paramRectF.width() / paramInt;
        f2 = getAlignmentOffset(paramRectF.width(), paramPointF.x, f1);
        localPoint = getMaximumKeySpace(paramRectF, paramPointF);
      } while (f2 == 0.0F);
      if (f2 < 0.5D) {
        return paramPointF.x / (0.5F + localPoint.x);
      }
      return (paramRectF.width() - paramPointF.x) / (0.5F + localPoint.y);
    }
    
    private Point getMaximumKeySpace(RectF paramRectF, PointF paramPointF)
    {
      float f = paramPointF.x / paramRectF.width();
      return new Point((int)Math.floor(f * MiniKeyboard.this.mKeys.size()), (int)Math.floor(MiniKeyboard.this.mKeys.size() * (1.0F - f)));
    }
    
    private void left(Key paramKey)
    {
      if (this.mLeftList.size() < this.mKeySpace.x)
      {
        this.mLeftList.add(paramKey);
        return;
      }
      if (this.mRightList.size() < this.mKeySpace.y)
      {
        this.mRightList.add(paramKey);
        return;
      }
      LogUtil.w("KeySplit", "Both Lists full. Not displaying: " + paramKey.getContent().toString());
    }
    
    private void right(Key paramKey)
    {
      if (this.mRightList.size() < this.mKeySpace.y)
      {
        this.mRightList.add(paramKey);
        return;
      }
      if (this.mLeftList.size() < this.mKeySpace.x)
      {
        this.mLeftList.add(paramKey);
        return;
      }
      LogUtil.w("KeySplit", "Both lists full. Not displaying: " + paramKey.getContent().toString());
    }
    
    private void splitKeys()
    {
      ArrayList localArrayList = new ArrayList(MiniKeyboard.this.mKeys);
      localArrayList.remove(MiniKeyboard.this.mPrimaryKey);
      int i = 0;
      Iterator localIterator = localArrayList.iterator();
      if (localIterator.hasNext())
      {
        Key localKey = (Key)localIterator.next();
        if (i != 0)
        {
          right(localKey);
          label71:
          if (i != 0) {
            break label89;
          }
        }
        label89:
        for (i = 1;; i = 0)
        {
          break;
          left(localKey);
          break label71;
        }
      }
    }
    
    public int getDefaultEdgeFlags()
    {
      return 12;
    }
    
    public int getFirstEdgeFlags()
    {
      return 0x1 | getDefaultEdgeFlags();
    }
    
    public int getLastEdgeFlags()
    {
      return 0x2 | getDefaultEdgeFlags();
    }
    
    public RectF getSpaceInParent()
    {
      if (this.mSpaceInParent == null) {
        this.mSpaceInParent = new RectF(this.mOwningCentre.x - (0.5F + this.mLeftList.size()) * this.mKeyWidth, this.mOwningCentre.y - 1.5F * MiniKeyboard.this.mKeyHeight, this.mOwningCentre.x + (0.5F + this.mRightList.size()) * this.mKeyWidth, this.mOwningCentre.y - 0.5F * MiniKeyboard.this.mKeyHeight);
      }
      return this.mSpaceInParent;
    }
    
    public boolean isSplit()
    {
      return this.mSplit;
    }
    
    public PointF repositionKey(Key paramKey, PointF paramPointF)
    {
      if (!this.mSplit) {
        throw new IllegalStateException("KeySplit cannot reposition key until split() has been called");
      }
      if (MiniKeyboard.this.mKeys.size() == 0) {}
      for (float f = 0.0F;; f = 1.0F / MiniKeyboard.this.mKeys.size())
      {
        RectF localRectF = new RectF(paramPointF.x, 0.0F, f + paramPointF.x, 1.0F);
        paramKey.getArea().set(localRectF);
        return new PointF(paramKey.getArea().getBounds().width(), 0.0F);
      }
    }
    
    public List<Key> split()
    {
      this.mSplit = true;
      splitKeys();
      return build(MiniKeyboard.this.mPrimaryKey);
    }
  }
  
  private abstract class KeySplit
  {
    protected final RectF mAvailableSpace;
    protected final PointF mOwningCentre;
    
    protected KeySplit(RectF paramRectF, PointF paramPointF)
    {
      this.mOwningCentre = paramPointF;
      this.mAvailableSpace = paramRectF;
    }
    
    public abstract int getDefaultEdgeFlags();
    
    public abstract int getFirstEdgeFlags();
    
    public abstract int getLastEdgeFlags();
    
    public abstract RectF getSpaceInParent();
    
    public abstract boolean isSplit();
    
    public abstract PointF repositionKey(Key paramKey, PointF paramPointF);
    
    public abstract List<Key> split();
  }
  
  public static enum Orientation
  {
    static
    {
      HORIZONTAL = new Orientation("HORIZONTAL", 1);
      Orientation[] arrayOfOrientation = new Orientation[2];
      arrayOfOrientation[0] = VERTICAL;
      arrayOfOrientation[1] = HORIZONTAL;
      $VALUES = arrayOfOrientation;
    }
    
    private Orientation() {}
  }
  
  private final class VerticalKeySplit
    extends MiniKeyboard.KeySplit
  {
    public VerticalKeySplit(RectF paramRectF, PointF paramPointF)
    {
      super(paramRectF, paramPointF);
    }
    
    public int getDefaultEdgeFlags()
    {
      return 3;
    }
    
    public int getFirstEdgeFlags()
    {
      return 0x4 | getDefaultEdgeFlags();
    }
    
    public int getLastEdgeFlags()
    {
      return 0x8 | getDefaultEdgeFlags();
    }
    
    public RectF getSpaceInParent()
    {
      return new RectF(this.mOwningCentre.x - 0.5F * MiniKeyboard.this.mMaxWidth, this.mOwningCentre.y - (0.5F + MiniKeyboard.this.mRows) * MiniKeyboard.this.mKeyHeight, this.mOwningCentre.x + 0.5F * MiniKeyboard.this.mMaxWidth, this.mOwningCentre.y - 0.5F * MiniKeyboard.this.mKeyHeight);
    }
    
    public boolean isSplit()
    {
      return true;
    }
    
    public PointF repositionKey(Key paramKey, PointF paramPointF)
    {
      if (MiniKeyboard.this.mKeys.size() == 0) {}
      for (float f = 0.0F;; f = 1.0F / MiniKeyboard.this.mKeys.size())
      {
        RectF localRectF = new RectF(0.0F, paramPointF.y, 1.0F, f + paramPointF.y);
        paramKey.getArea().set(localRectF);
        return new PointF(0.0F, paramKey.getArea().getBounds().height());
      }
    }
    
    public List<Key> split()
    {
      return MiniKeyboard.this.mKeys;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.MiniKeyboard
 * JD-Core Version:    0.7.0.1
 */