package org.noranj.formak.server.io;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.Channels;

import com.google.appengine.api.files.AppEngineFile;
import com.google.appengine.api.files.FileService;
import com.google.appengine.api.files.FileServiceFactory;
import com.google.appengine.api.files.FileWriteChannel;

/**
 * 
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
public class BlobOutputStream extends OutputStream {

  
  FileWriteChannel writeChannel=null;
  private OutputStream outs;
  
  /** 
   * open an existing file to append to it. This is not a typical output stream function so think about it.
   * @param mimeType
   * @param encoding
   * @param blobInfoUploadedfileName
   * @param lock
   */
  public BlobOutputStream(String path, String mimeType, String blobInfoUploadedfileName, boolean lock) throws IOException {

    /** Get a file service */
    FileService fileService = FileServiceFactory.getFileService();

    // Write more to the file in a separate request:
    AppEngineFile file = new AppEngineFile(path);
    
    writeChannel = fileService.openWriteChannel(file, lock);
  
    outs = Channels.newOutputStream(writeChannel);
    
    if (outs == null)
      throw new IOException();
   
  }
  
  /** 
   * 
   * @param mimeType
   * @param encoding
   * @param blobInfoUploadedfileName
   * @param lock
   */
  public BlobOutputStream(String mimeType, String blobInfoUploadedfileName, boolean lock) throws IOException {

    /** Get a file service */
    FileService fileService = FileServiceFactory.getFileService();

    // Create a new Blob file with mime-type "text/plain"
    AppEngineFile file = fileService.createNewBlobFile(mimeType); //"text/plain");
    
    FileWriteChannel writeChannel = fileService.openWriteChannel(file, lock);
    
    outs = Channels.newOutputStream(writeChannel);
    
    if (outs == null)
      throw new IOException();
  }
  
	/** Closes this output stream and releases any system resources associated with this stream. */
  @Override
  public void close() throws IOException {
    outs.close();
    // Now finalize
    writeChannel.closeFinally();
	}
   
  /** Flushes this output stream and forces any buffered output bytes to be written out. */
  @Override
  public void flush() throws IOException {
    outs.flush();
  }
    
  /** Writes b.length bytes from the specified byte array to this output stream. */ 
  @Override
  public void write(byte[] b) throws IOException {
    outs.write(b);
  }
   
  /** Writes len bytes from the specified byte array starting at offset off to this output stream. */
  @Override
  public void write(byte[] b, int off, int len) throws IOException {
    outs.write(b, off, len);
  }
   
  /** Writes the specified byte to this output stream. */
  @Override
  public void write(int b) throws IOException {
    outs.write(b);
  }
	
}
