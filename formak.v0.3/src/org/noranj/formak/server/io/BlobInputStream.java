package org.noranj.formak.server.io;

import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.nio.channels.IllegalBlockingModeException;

import com.google.appengine.api.files.AppEngineFile;
import com.google.appengine.api.files.FileReadChannel;
import com.google.appengine.api.files.FileService;
import com.google.appengine.api.files.FileServiceFactory;

/**
 * First Constructor needs to be added.
 * 
 * It can get as many parameter as needs.
 * The class can have any attributes that needs.
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
public class BlobInputStream extends InputStream {

  /** 
   * an open channel to read from blob.
   * we need this as attribute so later we can close it when we are done.
   */
  FileReadChannel readChannel=null;
  
  /**
   * this object provides access to channel as an input stream.
   */
  InputStream ins = null;
  
  /** 
   * @param fullPath blob full path. This is the value that is returned when the blob is created. See BlobOutputStream.
   * @param lock allows to restrict access top the file. false means let other people read at the same time
   * @throws IOException
   */
  public BlobInputStream(String fullPath, boolean lock) throws IOException {
    
  	try {
	  	/** Get a file service */
	    FileService fileService = FileServiceFactory.getFileService();
	    
	    // Write more to the file in a separate request:
			AppEngineFile file = new AppEngineFile(fullPath);
		
		  FileReadChannel readChannel = fileService.openReadChannel(file, lock);
	
		  // Again, different standard Java ways of reading from the channel.
	  	ins = Channels.newInputStream(readChannel);
	  } catch (IllegalBlockingModeException ibmex) {
	  	//FIXME logger?!?!?!
	  	throw new IOException ("Failed to open Blob ["+fullPath+"] as input stream due to a IllegalBlockingModeException - ["+ibmex.getMessage()+"]", ibmex);
	  } catch (Exception ex) {
	  	throw new IOException ("Failed to open Blob ["+fullPath+"] as input stream due to a Exception - ["+ex.getMessage()+"]", ex);
	  }
	  
	  if (ins == null)
	  	throw new IOException("Failed to open the blob as Inputstream.");
	  
  }
	
	/**    
	 * Reads the next byte of data from the input stream.
	 */
	@Override
	public int read() throws IOException {
		return ins.read();
	}
	
	/**
	 * Returns an estimate of the number of bytes that can be read (or skipped over) from this input stream without blocking by the next invocation of a method for this input stream. 
	 */
	@Override
	public int available() throws IOException {
		return (ins.available());
	}
	
	
  /** Closes this input stream and releases any system resources associated with the stream. */ 
	@Override
	public void close() throws IOException {
		ins.close();
		readChannel.close();
	}
	
	/** Marks the current position in this input stream. */
	@Override
	public void mark(int readlimit) {
		ins.mark(readlimit);
	}
    
	/** Tests if this input stream supports the mark and reset methods. */
	@Override
	public boolean markSupported() {
		return(ins.markSupported());
	}

	/**    
	 * Reads some number of bytes from the input stream and stores them into the buffer array b. 
	 */
	@Override
	public int read(byte[] b) throws IOException {
		return(ins.read(b));
	}

  /** 
   * Reads up to len bytes of data from the input stream into an array of bytes.
   */
	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		return(ins.read(b, off, len));
	}
	
  /** Repositions this stream to the position at the time the mark method was last called on this input stream. */ 
	@Override
	public void reset() throws IOException {
		ins.reset();
	}
	
  /** Skips over and discards n bytes of data from this input stream. */ 
	@Override
	public long skip(long n) throws IOException {
		return(ins.skip(n));
	}

}
