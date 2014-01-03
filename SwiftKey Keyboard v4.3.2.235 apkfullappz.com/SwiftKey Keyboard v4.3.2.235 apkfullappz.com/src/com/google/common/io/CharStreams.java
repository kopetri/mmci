package com.google.common.io;

import com.google.common.base.Preconditions;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

public final class CharStreams
{
  public static long copy(Readable paramReadable, Appendable paramAppendable)
    throws IOException
  {
    CharBuffer localCharBuffer = CharBuffer.allocate(2048);
    long l = 0L;
    while (paramReadable.read(localCharBuffer) != -1)
    {
      localCharBuffer.flip();
      paramAppendable.append(localCharBuffer);
      l += localCharBuffer.remaining();
      localCharBuffer.clear();
    }
    return l;
  }
  
  public static InputSupplier<InputStreamReader> newReaderSupplier(InputSupplier<? extends InputStream> paramInputSupplier, final Charset paramCharset)
  {
    Preconditions.checkNotNull(paramInputSupplier);
    Preconditions.checkNotNull(paramCharset);
    new InputSupplier()
    {
      public InputStreamReader getInput()
        throws IOException
      {
        return new InputStreamReader((InputStream)this.val$in.getInput(), paramCharset);
      }
    };
  }
  
  public static OutputSupplier<OutputStreamWriter> newWriterSupplier(OutputSupplier<? extends OutputStream> paramOutputSupplier, final Charset paramCharset)
  {
    Preconditions.checkNotNull(paramOutputSupplier);
    Preconditions.checkNotNull(paramCharset);
    new OutputSupplier()
    {
      public OutputStreamWriter getOutput()
        throws IOException
      {
        return new OutputStreamWriter((OutputStream)this.val$out.getOutput(), paramCharset);
      }
    };
  }
  
  public static <R extends Readable,  extends Closeable> String toString(InputSupplier<R> paramInputSupplier)
    throws IOException
  {
    return toStringBuilder(paramInputSupplier).toString();
  }
  
  public static String toString(Readable paramReadable)
    throws IOException
  {
    return toStringBuilder(paramReadable).toString();
  }
  
  private static <R extends Readable,  extends Closeable> StringBuilder toStringBuilder(InputSupplier<R> paramInputSupplier)
    throws IOException
  {
    Readable localReadable = (Readable)paramInputSupplier.getInput();
    try
    {
      StringBuilder localStringBuilder = toStringBuilder(localReadable);
      Closeables.close((Closeable)localReadable, false);
      return localStringBuilder;
    }
    finally
    {
      Closeables.close((Closeable)localReadable, true);
    }
  }
  
  private static StringBuilder toStringBuilder(Readable paramReadable)
    throws IOException
  {
    StringBuilder localStringBuilder = new StringBuilder();
    copy(paramReadable, localStringBuilder);
    return localStringBuilder;
  }
  
  public static <W extends Appendable,  extends Closeable> void write(CharSequence paramCharSequence, OutputSupplier<W> paramOutputSupplier)
    throws IOException
  {
    Preconditions.checkNotNull(paramCharSequence);
    Appendable localAppendable = (Appendable)paramOutputSupplier.getOutput();
    try
    {
      localAppendable.append(paramCharSequence);
      Closeables.close((Closeable)localAppendable, false);
      return;
    }
    finally
    {
      Closeables.close((Closeable)localAppendable, true);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.common.io.CharStreams
 * JD-Core Version:    0.7.0.1
 */