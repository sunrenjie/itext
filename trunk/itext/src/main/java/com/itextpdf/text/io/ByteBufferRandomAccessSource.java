package com.itextpdf.text.io;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * A RandomAccessSource that is based on an underlying {@link ByteBuffer}.  This class takes steps to ensure that the byte buffer
 * is completely freed from memory during {@link ByteBufferRandomAccessSource#close()}
 * @since 5.3.5
 */
class ByteBufferRandomAccessSource implements RandomAccessSource {
    
    /**
     * Internal cache of memory mapped buffers
     */
    private final ByteBuffer byteBuffer;
    
    /**
     * Constructs a new {@link ByteBufferRandomAccessSource} based on the specified ByteBuffer
     * @param byteBuffer the buffer to use as the backing store
     */
	public ByteBufferRandomAccessSource(final ByteBuffer byteBuffer) {
        this.byteBuffer = byteBuffer;
	}

    /** 
     * {@inheritDoc} 
     * <p>
     * Note: Because ByteBuffers don't support long indexing, the position must be a valid positive int
     * @param position the position to read the byte from - must be less than Integer.MAX_VALUE 
     */  
	public int get(long position) throws IOException {
		if (position > Integer.MAX_VALUE)
			throw new IllegalArgumentException("Position must be less than Integer.MAX_VALUE");
		
        try {

            if (position >= byteBuffer.limit())
                return -1;
            
            byte b = byteBuffer.get((int)position);

            int n = b & 0xff;

            return n;
        } catch (BufferUnderflowException e) {
            return -1; // EOF
        }
	}
	
    /** 
     * {@inheritDoc} 
     * <p>
     * Note: Because ByteBuffers don't support long indexing, the position must be a valid positive int
     * @param position the position to read the byte from - must be less than Integer.MAX_VALUE 
     */  
	public int get(long position, byte[] bytes, int off, int len) throws IOException {
		if (position > Integer.MAX_VALUE)
			throw new IllegalArgumentException("Position must be less than Integer.MAX_VALUE");

		if (position >= byteBuffer.limit())
            return -1;

		byteBuffer.position((int)position);
        int bytesFromThisBuffer = Math.min(len, byteBuffer.remaining());
		byteBuffer.get(bytes, off, bytesFromThisBuffer);
		
		return bytesFromThisBuffer;
		
	}

	
    /** 
     * {@inheritDoc} 
     */  
	public long length() {
		return byteBuffer.limit();
	}

    /**
     * @see java.io.RandomAccessFile#close()
     * Cleans the mapped bytebuffers and closes the channel
     */
    public void close() throws IOException {
    	clean(byteBuffer);
    }

    /**
     * invokes the clean method on the ByteBuffer's cleaner
     * @param buffer ByteBuffer
     * @return boolean true on success
     */
    private static boolean clean(final java.nio.ByteBuffer buffer) {
        if (buffer == null || !buffer.isDirect())
            return false;

        Boolean b = (Boolean) AccessController.doPrivileged(new PrivilegedAction<Boolean>() {
            public Boolean run() {
                Boolean success = Boolean.FALSE;
                try {
                    Method getCleanerMethod = buffer.getClass().getMethod("cleaner", (Class<?>[])null);
                    getCleanerMethod.setAccessible(true);
                    Object cleaner = getCleanerMethod.invoke(buffer, (Object[])null);
                    Method clean = cleaner.getClass().getMethod("clean", (Class<?>[])null);
                    clean.invoke(cleaner, (Object[])null);
                    success = Boolean.TRUE;
                } catch (Exception e) {
                    // This really is a show stopper on windows
                    //e.printStackTrace();
                }
                return success;
            }
        });

        return b.booleanValue();
    }
}
