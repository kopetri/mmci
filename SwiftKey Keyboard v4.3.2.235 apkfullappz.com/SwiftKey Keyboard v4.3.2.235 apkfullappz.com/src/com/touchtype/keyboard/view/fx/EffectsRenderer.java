package com.touchtype.keyboard.view.fx;

import android.graphics.Color;
import android.opengl.GLSurfaceView.Renderer;
import android.os.SystemClock;
import com.touchtype.keyboard.view.touch.FlowEvent;
import com.touchtype.keyboard.view.touch.FlowEvent.ActionType;
import com.touchtype_fluency.Point;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class EffectsRenderer
  implements GLSurfaceView.Renderer
{
  private static final byte[] TEXTURE_VALUES = new byte[1024];
  private long mCheckTime = 0L;
  private List<FlowEvent> mEventBuffer = new ArrayList();
  private int mLastTrailsSize = 1;
  private Observer mObserver;
  private int mThemeHeadColor;
  private int mThemeTailColor;
  private List<TrailEffect> mTrails = new ArrayList();
  private int[] m_bufferIndices;
  private int[] m_colourBuffer;
  private IntBuffer m_colourBufferDirect;
  private float[] m_headColourRGB;
  private float m_height;
  private short[] m_indexBuffer;
  private ShortBuffer m_indexBufferDirect;
  private float[] m_tailColourRGB;
  private FloatBuffer m_texCoordBuffer;
  private IntBuffer m_textures;
  private int[] m_vertexBuffer;
  private IntBuffer m_vertexBufferDirect;
  private float m_width;
  
  static
  {
    for (int i = 0; i < 256; i++)
    {
      int j = (byte)(int)(50.0D + 200.0D * Math.abs(Math.sin(9.424777960769379D * i / 256.0D) * Math.sin(3.141592653589793D * i / 256.0D)));
      TEXTURE_VALUES[(0 + i * 4)] = j;
      TEXTURE_VALUES[(1 + i * 4)] = j;
      TEXTURE_VALUES[(2 + i * 4)] = j;
      TEXTURE_VALUES[(3 + i * 4)] = j;
    }
  }
  
  public EffectsRenderer(int paramInt1, int paramInt2, Observer paramObserver)
  {
    this.mThemeHeadColor = paramInt1;
    this.mThemeTailColor = paramInt2;
    this.mObserver = paramObserver;
  }
  
  private void allocateBuffers()
  {
    this.m_vertexBuffer = new int[1056];
    ByteBuffer localByteBuffer1 = ByteBuffer.allocateDirect(4224);
    localByteBuffer1.order(ByteOrder.nativeOrder());
    this.m_vertexBufferDirect = localByteBuffer1.asIntBuffer();
    this.m_indexBuffer = new short[1056];
    ByteBuffer localByteBuffer2 = ByteBuffer.allocateDirect(2112);
    localByteBuffer2.order(ByteOrder.nativeOrder());
    this.m_indexBufferDirect = localByteBuffer2.asShortBuffer();
    this.m_colourBuffer = new int[1408];
    ByteBuffer localByteBuffer3 = ByteBuffer.allocateDirect(5632);
    localByteBuffer3.order(ByteOrder.nativeOrder());
    this.m_colourBufferDirect = localByteBuffer3.asIntBuffer();
    ByteBuffer localByteBuffer4 = ByteBuffer.allocateDirect(2816);
    localByteBuffer4.order(ByteOrder.nativeOrder());
    this.m_texCoordBuffer = localByteBuffer4.asFloatBuffer();
    int i = this.m_texCoordBuffer.capacity();
    float[] arrayOfFloat = new float[i];
    int j = 0;
    if (j < i)
    {
      if (j % 4 < 2) {}
      for (float f = 0.0F;; f = 1.0F)
      {
        arrayOfFloat[j] = f;
        j++;
        break;
      }
    }
    this.m_texCoordBuffer.put(arrayOfFloat);
    this.m_texCoordBuffer.rewind();
    this.m_bufferIndices = new int[3];
  }
  
  private void allocateTexBuffer(GL10 paramGL10)
  {
    this.m_textures = IntBuffer.allocate(1);
    paramGL10.glGenTextures(1, this.m_textures);
  }
  
  public static Vector2 catmullRom(Vector2 paramVector21, Vector2 paramVector22, Vector2 paramVector23, Vector2 paramVector24, float paramFloat)
  {
    Vector2 localVector2 = new Vector2(0.0F, 0.0F);
    localVector2.add(Vector2.scaled(paramVector22, 2.0F)).add(Vector2.subtract(paramVector23, paramVector21).scale(paramFloat)).add(Vector2.scaled(paramVector21, 2.0F).add(Vector2.scaled(paramVector22, -5.0F)).add(Vector2.scaled(paramVector23, 4.0F)).subtract(paramVector24).scale(paramFloat * paramFloat)).add(Vector2.subtract(Vector2.scaled(paramVector22, 3.0F), paramVector21).add(Vector2.scaled(paramVector23, -3.0F)).add(paramVector24).scale(paramFloat * (paramFloat * paramFloat))).scale(0.5F);
    return localVector2;
  }
  
  private void flushEventBuffer()
  {
    List localList = this.mEventBuffer;
    int i = 0;
    int k;
    for (;;)
    {
      try
      {
        if (i < this.mEventBuffer.size())
        {
          if ((this.mTrails.size() == 0) || (((FlowEvent)this.mEventBuffer.get(i)).action == FlowEvent.ActionType.DOWN)) {
            this.mTrails.add(new TrailEffect());
          }
          FlowEvent localFlowEvent = (FlowEvent)this.mEventBuffer.get(i);
          ((TrailEffect)this.mTrails.get(-1 + this.mTrails.size())).appendEvent(localFlowEvent.transform(localFlowEvent, this.m_width / this.m_height, 1.0F));
          i++;
          continue;
        }
        this.mEventBuffer.clear();
        int j = 175 - 4 * this.mTrails.size();
        k = -1 + this.mTrails.size();
        if ((k < 0) || (j <= 0)) {
          break;
        }
        int n = ((TrailEffect)this.mTrails.get(k)).mEvents.size();
        int i1 = n - j;
        if (i1 > 0)
        {
          int i2 = 0;
          if (i2 >= i1) {
            break;
          }
          ((TrailEffect)this.mTrails.get(k)).removeTailEvent();
          i2++;
          continue;
        }
        j -= n;
      }
      finally {}
      k--;
    }
    for (int m = 0; m < k; m++) {
      this.mTrails.remove(0);
    }
  }
  
  private void generateTexture(GL10 paramGL10)
  {
    if ((this.m_textures != null) && (this.m_textures.capacity() > 0)) {
      paramGL10.glDeleteTextures(1, this.m_textures);
    }
    allocateTexBuffer(paramGL10);
    ByteBuffer localByteBuffer = ByteBuffer.allocateDirect(1024);
    localByteBuffer.put(TEXTURE_VALUES);
    localByteBuffer.rewind();
    paramGL10.glBindTexture(3553, this.m_textures.get(0));
    paramGL10.glTexParameterx(3553, 10242, 10497);
    paramGL10.glTexParameterx(3553, 10243, 10497);
    paramGL10.glTexParameterx(3553, 10240, 9729);
    paramGL10.glTexParameterx(3553, 10241, 9729);
    paramGL10.glTexEnvf(8960, 8704, 8448.0F);
    paramGL10.glTexImage2D(3553, 0, 6408, 1, 256, 0, 6408, 5121, localByteBuffer);
    paramGL10.glBindTexture(3553, 0);
  }
  
  public void enforceFrameLimit()
  {
    long l1 = SystemClock.uptimeMillis() - this.mCheckTime;
    long l2;
    if (l1 < 33L) {
      l2 = 33L - l1;
    }
    try
    {
      Thread.sleep(l2);
      label27:
      this.mCheckTime = SystemClock.uptimeMillis();
      return;
    }
    catch (InterruptedException localInterruptedException)
    {
      break label27;
    }
  }
  
  public void onDrawFrame(GL10 paramGL10)
  {
    paramGL10.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
    paramGL10.glClear(16640);
    paramGL10.glMatrixMode(5888);
    paramGL10.glLoadIdentity();
    paramGL10.glEnable(3553);
    int i = 0;
    while (i < this.mTrails.size())
    {
      ((TrailEffect)this.mTrails.get(i)).advance();
      if (((TrailEffect)this.mTrails.get(i)).deletable()) {
        this.mTrails.remove(i);
      } else {
        i++;
      }
    }
    this.m_bufferIndices[0] = 0;
    this.m_bufferIndices[1] = 0;
    this.m_bufferIndices[2] = 0;
    for (int j = 0; j < this.mTrails.size(); j++) {
      ((TrailEffect)this.mTrails.get(j)).generateRenderData(this.m_vertexBuffer, this.m_colourBuffer, this.m_texCoordBuffer, this.m_indexBuffer, this.m_headColourRGB, this.m_tailColourRGB, this.m_bufferIndices);
    }
    int k = this.m_bufferIndices[BufferIndex.IndexBufferIndex.index()];
    this.m_vertexBufferDirect.put(this.m_vertexBuffer);
    this.m_colourBufferDirect.put(this.m_colourBuffer);
    this.m_indexBufferDirect.put(this.m_indexBuffer);
    this.m_vertexBufferDirect.position(0);
    this.m_indexBufferDirect.position(0);
    this.m_colourBufferDirect.position(0);
    this.m_texCoordBuffer.position(0);
    paramGL10.glBindTexture(3553, this.m_textures.get(0));
    paramGL10.glNormal3f(0.0F, 0.0F, 1.0F);
    paramGL10.glDrawElements(4, k, 5123, this.m_indexBufferDirect);
    paramGL10.glBindTexture(3553, 0);
    flushEventBuffer();
    int m = this.mTrails.size();
    if ((m == 0) && (this.mLastTrailsSize == 0)) {
      this.mObserver.onRendererFinished();
    }
    this.mLastTrailsSize = m;
    enforceFrameLimit();
  }
  
  public void onFlowEvent(FlowEvent paramFlowEvent)
  {
    synchronized (this.mEventBuffer)
    {
      this.mEventBuffer.add(paramFlowEvent);
      return;
    }
  }
  
  public void onSurfaceChanged(GL10 paramGL10, int paramInt1, int paramInt2)
  {
    this.m_width = paramInt1;
    this.m_height = paramInt2;
    paramGL10.glViewport(0, 0, paramInt1, paramInt2);
    paramGL10.glMatrixMode(5889);
    paramGL10.glLoadIdentity();
    paramGL10.glOrthof(0.0F, 1.0F * (paramInt1 / paramInt2), 1.0F, 0.0F, -1.0F, 1.0F);
    paramGL10.glDisable(2929);
    paramGL10.glDisable(2884);
    paramGL10.glEnable(3042);
    paramGL10.glBlendFunc(770, 771);
    this.m_vertexBufferDirect.position(0);
    this.m_indexBufferDirect.position(0);
    this.m_colourBufferDirect.position(0);
    this.m_texCoordBuffer.position(0);
    paramGL10.glEnableClientState(32884);
    paramGL10.glEnableClientState(32886);
    paramGL10.glEnableClientState(32888);
    paramGL10.glVertexPointer(3, 5126, 0, this.m_vertexBufferDirect);
    paramGL10.glColorPointer(4, 5126, 0, this.m_colourBufferDirect);
    paramGL10.glTexCoordPointer(2, 5126, 0, this.m_texCoordBuffer);
  }
  
  public void onSurfaceCreated(GL10 paramGL10, EGLConfig paramEGLConfig)
  {
    paramGL10.glClearColor(1.0F, 0.0F, 0.5F, 0.1F);
    allocateBuffers();
    generateTexture(paramGL10);
    this.m_headColourRGB = new float[3];
    this.m_tailColourRGB = new float[3];
    setColours(this.mThemeHeadColor, this.mThemeTailColor);
  }
  
  public void setColours(int paramInt1, int paramInt2)
  {
    this.m_headColourRGB[0] = (Color.red(paramInt1) / 255.0F);
    this.m_headColourRGB[1] = (Color.green(paramInt1) / 255.0F);
    this.m_headColourRGB[2] = (Color.blue(paramInt1) / 255.0F);
    this.m_tailColourRGB[0] = (Color.red(paramInt2) / 255.0F);
    this.m_tailColourRGB[1] = (Color.green(paramInt2) / 255.0F);
    this.m_tailColourRGB[2] = (Color.blue(paramInt2) / 255.0F);
  }
  
  private static enum BufferIndex
  {
    private final int indexID;
    
    static
    {
      ColourBufferIndex = new BufferIndex("ColourBufferIndex", 1, 1);
      IndexBufferIndex = new BufferIndex("IndexBufferIndex", 2, 2);
      BufferIndex[] arrayOfBufferIndex = new BufferIndex[3];
      arrayOfBufferIndex[0] = VertexBufferIndex;
      arrayOfBufferIndex[1] = ColourBufferIndex;
      arrayOfBufferIndex[2] = IndexBufferIndex;
      $VALUES = arrayOfBufferIndex;
    }
    
    private BufferIndex(int paramInt)
    {
      this.indexID = paramInt;
    }
    
    public int index()
    {
      return this.indexID;
    }
  }
  
  public static abstract interface Observer
  {
    public abstract void onRendererFinished();
  }
  
  private final class TrailEffect
  {
    private List<Vector2> mEvents = new LinkedList();
    private long mLastAdvanceMillis = SystemClock.uptimeMillis();
    private long mLastInputMillis;
    private long mLastPointTimestamp;
    private float mTailVelocity = 0.0F;
    private float mTrailLength = 0.0F;
    
    static
    {
      if (!EffectsRenderer.class.desiredAssertionStatus()) {}
      for (boolean bool = true;; bool = false)
      {
        $assertionsDisabled = bool;
        return;
      }
    }
    
    public TrailEffect() {}
    
    void advance()
    {
      long l = SystemClock.uptimeMillis();
      float f1 = (float)(l - this.mLastAdvanceMillis) / 1000.0F;
      if (f1 < 0.0F) {
        f1 = 0.02F;
      }
      this.mLastAdvanceMillis = l;
      float f2 = f1 * this.mTailVelocity;
      this.mTrailLength = Math.min(3.5F, Math.max(0.0F, this.mTrailLength - f2));
      if (((this.mEvents.size() > 1) && (this.mTrailLength < 1.0E-006F)) || (SystemClock.uptimeMillis() - this.mLastInputMillis > 350L)) {
        this.mEvents.clear();
      }
    }
    
    void appendEvent(FlowEvent paramFlowEvent)
    {
      Point localPoint = paramFlowEvent.toPoint();
      Vector2 localVector21 = new Vector2(localPoint.getX(), localPoint.getY());
      this.mTailVelocity = 0.0F;
      int i = this.mEvents.size();
      float f = 0.0F;
      if (i > 0)
      {
        if (paramFlowEvent.time < this.mLastPointTimestamp) {}
        Vector2 localVector22;
        do
        {
          return;
          localVector22 = Vector2.subtract(localVector21, (Vector2)this.mEvents.get(-1 + this.mEvents.size()));
          f = localVector22.length();
        } while (f < 0.02F);
        if (this.mEvents.size() > 2)
        {
          Vector2 localVector23 = Vector2.normalised(localVector22);
          if (Vector2.dotProduct(Vector2.subtract((Vector2)this.mEvents.get(-1 + this.mEvents.size()), (Vector2)this.mEvents.get(-2 + this.mEvents.size())).normalise(), localVector23) < 0.7F)
          {
            Vector2 localVector24 = EffectsRenderer.catmullRom((Vector2)this.mEvents.get(-2 + this.mEvents.size()), (Vector2)this.mEvents.get(-1 + this.mEvents.size()), localVector21, localVector21, 0.5F);
            this.mEvents.add(localVector24);
          }
        }
      }
      this.mLastInputMillis = SystemClock.uptimeMillis();
      this.mEvents.add(localVector21);
      this.mTrailLength = (f + this.mTrailLength);
      this.mLastPointTimestamp = paramFlowEvent.time;
    }
    
    boolean deletable()
    {
      return this.mEvents.size() == 0;
    }
    
    void generateRenderData(int[] paramArrayOfInt1, int[] paramArrayOfInt2, FloatBuffer paramFloatBuffer, short[] paramArrayOfShort, float[] paramArrayOfFloat1, float[] paramArrayOfFloat2, int[] paramArrayOfInt3)
    {
      if (this.mEvents.size() < 2) {
        return;
      }
      float f1 = 0.0F;
      float f2 = Math.max(0.0F, 1.0F - (float)(SystemClock.uptimeMillis() - this.mLastInputMillis) / 350.0F);
      int i = paramArrayOfInt3[EffectsRenderer.BufferIndex.VertexBufferIndex.index()];
      int j = paramArrayOfInt3[EffectsRenderer.BufferIndex.ColourBufferIndex.index()];
      int k = paramFloatBuffer.position();
      int m = paramArrayOfInt3[EffectsRenderer.BufferIndex.IndexBufferIndex.index()];
      assert (i / 6 == j / 8);
      assert (i / 6 == k / 4);
      assert (m % 3 == 0);
      Vector2 localVector21 = Vector2.subtract((Vector2)this.mEvents.get(-2 + this.mEvents.size()), (Vector2)this.mEvents.get(-1 + this.mEvents.size())).normalise();
      Vector2 localVector22 = Vector2.perpendicularCW(localVector21);
      Vector2 localVector23 = Vector2.subtract((Vector2)this.mEvents.get(-1 + this.mEvents.size()), Vector2.scaled(localVector21, 0.075F));
      int n = Float.floatToRawIntBits(0.0F);
      int i1 = Float.floatToRawIntBits(paramArrayOfFloat1[0]);
      int i2 = Float.floatToRawIntBits(paramArrayOfFloat1[1]);
      int i3 = Float.floatToRawIntBits(paramArrayOfFloat1[2]);
      paramArrayOfInt1[(i + 0)] = Float.floatToRawIntBits(localVector23.x + 0.5F * (0.03F * localVector22.x) - 0.075F * localVector21.x);
      paramArrayOfInt1[(i + 1)] = Float.floatToRawIntBits(localVector23.y + 0.5F * (0.03F * localVector22.y) - 0.075F * localVector21.y);
      paramArrayOfInt1[(i + 2)] = n;
      paramArrayOfInt2[(j + 0)] = i1;
      paramArrayOfInt2[(j + 1)] = i2;
      paramArrayOfInt2[(j + 2)] = i3;
      paramArrayOfInt2[(j + 3)] = n;
      paramArrayOfInt1[(i + 3)] = Float.floatToRawIntBits(localVector23.x - 0.5F * (0.03F * localVector22.x) - 0.075F * localVector21.x);
      paramArrayOfInt1[(i + 4)] = Float.floatToRawIntBits(localVector23.y - 0.5F * (0.03F * localVector22.y) - 0.075F * localVector21.y);
      paramArrayOfInt1[(i + 5)] = n;
      paramArrayOfInt2[(j + 4)] = i1;
      paramArrayOfInt2[(j + 5)] = i2;
      paramArrayOfInt2[(j + 6)] = i3;
      paramArrayOfInt2[(j + 7)] = n;
      int i4 = i / 3;
      paramArrayOfShort[(m + 0)] = ((short)(i4 + 0));
      paramArrayOfShort[(m + 1)] = ((short)(i4 + 1));
      paramArrayOfShort[(m + 2)] = ((short)(i4 + 2));
      paramArrayOfShort[(m + 3)] = ((short)(i4 + 2));
      paramArrayOfShort[(m + 4)] = ((short)(i4 + 1));
      paramArrayOfShort[(m + 5)] = ((short)(i4 + 3));
      int i5 = i + 6;
      int i6 = j + 8;
      int i7 = k + 4;
      int i8 = m + 6;
      int i9 = Float.floatToRawIntBits(0.2F);
      paramArrayOfInt1[(i5 + 0)] = Float.floatToRawIntBits(localVector23.x + 0.95F * (0.03F * localVector22.x));
      paramArrayOfInt1[(i5 + 1)] = Float.floatToRawIntBits(localVector23.y + 0.95F * (0.03F * localVector22.y));
      paramArrayOfInt1[(i5 + 2)] = n;
      paramArrayOfInt2[(i6 + 0)] = i1;
      paramArrayOfInt2[(i6 + 1)] = i2;
      paramArrayOfInt2[(i6 + 2)] = i3;
      paramArrayOfInt2[(i6 + 3)] = i9;
      paramArrayOfInt1[(i5 + 3)] = Float.floatToRawIntBits(localVector23.x - 0.95F * (0.03F * localVector22.x));
      paramArrayOfInt1[(i5 + 4)] = Float.floatToRawIntBits(localVector23.y - 0.95F * (0.03F * localVector22.y));
      paramArrayOfInt1[(i5 + 5)] = n;
      paramArrayOfInt2[(i6 + 4)] = i1;
      paramArrayOfInt2[(i6 + 5)] = i2;
      paramArrayOfInt2[(i6 + 6)] = i3;
      paramArrayOfInt2[(i6 + 7)] = i9;
      int i10 = i5 / 3;
      paramArrayOfShort[(i8 + 0)] = ((short)(i10 + 0));
      paramArrayOfShort[(i8 + 1)] = ((short)(i10 + 1));
      paramArrayOfShort[(i8 + 2)] = ((short)(i10 + 2));
      paramArrayOfShort[(i8 + 3)] = ((short)(i10 + 2));
      paramArrayOfShort[(i8 + 4)] = ((short)(i10 + 1));
      paramArrayOfShort[(i8 + 5)] = ((short)(i10 + 3));
      int i11 = i5 + 6;
      int i12 = i6 + 8;
      int i13 = i7 + 4;
      int i14 = i8 + 6;
      int i15 = -1 + this.mEvents.size();
      if ((i15 > 0) && (f1 < this.mTrailLength))
      {
        Vector2 localVector24 = new Vector2((Vector2)this.mEvents.get(i15));
        Vector2 localVector25 = Vector2.perpendicularCW(Vector2.subtract((Vector2)this.mEvents.get(i15 - 1), localVector24).normalise());
        Vector2 localVector26;
        float f3;
        label1049:
        float f8;
        if (i15 < -1 + this.mEvents.size())
        {
          localVector26 = Vector2.subtract((Vector2)this.mEvents.get(i15), (Vector2)this.mEvents.get(i15 + 1));
          f3 = localVector26.length();
          localVector26.normalise();
          if (f1 + f3 <= this.mTrailLength) {
            break label1536;
          }
          f8 = f1 + f3 - this.mTrailLength;
          localVector24.subtract(Vector2.scaled(localVector26, f8));
        }
        label1536:
        for (f1 += f3 - f8;; f1 += f3)
        {
          float f4 = f1 / this.mTrailLength;
          float f5 = 0.1F + 0.9F * (1.0F - f4 * f4);
          float f6 = 1.0F - f4;
          float f7 = 1.0F - f4 * f4;
          int i16 = Float.floatToRawIntBits(1.0F - f5);
          int i17 = Float.floatToRawIntBits(f2 * f7);
          int i18 = Float.floatToRawIntBits(paramArrayOfFloat2[0] + f6 * (paramArrayOfFloat1[0] - paramArrayOfFloat2[0]));
          int i19 = Float.floatToRawIntBits(paramArrayOfFloat2[1] + f6 * (paramArrayOfFloat1[1] - paramArrayOfFloat2[1]));
          int i20 = Float.floatToRawIntBits(paramArrayOfFloat2[2] + f6 * (paramArrayOfFloat1[2] - paramArrayOfFloat2[2]));
          paramArrayOfInt1[(i11 + 0)] = Float.floatToRawIntBits(localVector24.x + f5 * (0.03F * localVector25.x));
          paramArrayOfInt1[(i11 + 1)] = Float.floatToRawIntBits(localVector24.y + f5 * (0.03F * localVector25.y));
          paramArrayOfInt1[(i11 + 2)] = i16;
          paramArrayOfInt2[(i12 + 0)] = i18;
          paramArrayOfInt2[(i12 + 1)] = i19;
          paramArrayOfInt2[(i12 + 2)] = i20;
          paramArrayOfInt2[(i12 + 3)] = i17;
          paramArrayOfInt1[(i11 + 3)] = Float.floatToRawIntBits(localVector24.x - f5 * (0.03F * localVector25.x));
          paramArrayOfInt1[(i11 + 4)] = Float.floatToRawIntBits(localVector24.y - f5 * (0.03F * localVector25.y));
          paramArrayOfInt1[(i11 + 5)] = i16;
          paramArrayOfInt2[(i12 + 4)] = i18;
          paramArrayOfInt2[(i12 + 5)] = i19;
          paramArrayOfInt2[(i12 + 6)] = i20;
          paramArrayOfInt2[(i12 + 7)] = i17;
          if ((i15 > 1) && (f1 < this.mTrailLength))
          {
            int i21 = i11 / 3;
            paramArrayOfShort[(i14 + 0)] = ((short)(i21 + 0));
            paramArrayOfShort[(i14 + 1)] = ((short)(i21 + 1));
            paramArrayOfShort[(i14 + 2)] = ((short)(i21 + 2));
            paramArrayOfShort[(i14 + 3)] = ((short)(i21 + 2));
            paramArrayOfShort[(i14 + 4)] = ((short)(i21 + 1));
            paramArrayOfShort[(i14 + 5)] = ((short)(i21 + 3));
            i14 += 6;
          }
          i11 += 6;
          i12 += 8;
          i13 += 4;
          i15--;
          break;
          localVector26 = new Vector2(0.0F, 0.0F);
          f3 = 0.0F;
          break label1049;
        }
      }
      paramArrayOfInt3[EffectsRenderer.BufferIndex.VertexBufferIndex.index()] = i11;
      paramArrayOfInt3[EffectsRenderer.BufferIndex.ColourBufferIndex.index()] = i12;
      paramFloatBuffer.position(i13);
      paramArrayOfInt3[EffectsRenderer.BufferIndex.IndexBufferIndex.index()] = i14;
    }
    
    void removeTailEvent()
    {
      if (this.mEvents.size() == 0) {
        return;
      }
      int i = this.mEvents.size();
      float f = 0.0F;
      if (i > 1) {
        f = Vector2.subtract((Vector2)this.mEvents.get(0), (Vector2)this.mEvents.get(1)).length();
      }
      this.mTrailLength -= f;
      this.mEvents.remove(0);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.view.fx.EffectsRenderer
 * JD-Core Version:    0.7.0.1
 */