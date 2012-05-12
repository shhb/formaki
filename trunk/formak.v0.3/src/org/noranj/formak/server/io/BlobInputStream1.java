package org.noranj.formak.server.io;

import java.io.IOException;
import java.io.InputStream;

/** 
 * First copied all the methods from InputStream
 * Then I will override them all using FileService.
 * 
 * For other cases you don't need to override everything but for
 * BlobStore I have to override most of them.
 * 
 * For example, I used the same idea to write a class that encrypts and decrypts the data as it writes/reads.
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 * @since 0.3
 * @version 0.3
 * @change
 *
 */
public class BlobInputStream1 extends InputStream {

	/**    
	 * Reads the next byte of data from the input stream.
	 */
	@Override
	public int read() throws IOException {
		//TODO
		return 0;
	}
	
	/**
	 * Returns an estimate of the number of bytes that can be read (or skipped over) from this input stream without blocking by the next invocation of a method for this input stream. 
	 */
	public int available() {
	//FXIME
		return (0);
	}
	
	
  /** Closes this input stream and releases any system resources associated with the stream. */ 
	public void close() {
	//FXIME
	}
	
	/** Marks the current position in this input stream. */
	public void mark(int readlimit) {
	//FXIME
	}
    
	/** Tests if this input stream supports the mark and reset methods. */
	public boolean markSupported() {
	//FXIME
		return(false);
	}

	/**    
	 * Reads some number of bytes from the input stream and stores them into the buffer array b. 
	 */
	public int read(byte[] b) {
	//FXIME
		return(0);
	}

  /** 
   * Reads up to len bytes of data from the input stream into an array of bytes.
   */
	public int read(byte[] b, int off, int len) {
	//FXIME
		return(0);
	}
	
  /** Repositions this stream to the position at the time the mark method was last called on this input stream. */ 
	public void reset() {
	//FXIME
	}
	
  /** Skips over and discards n bytes of data from this input stream. */ 
	public long skip(long n) {
		//FXIME
		return(0);
	}

}
