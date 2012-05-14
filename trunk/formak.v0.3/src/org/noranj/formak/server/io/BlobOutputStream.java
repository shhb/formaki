package org.noranj.formak.server.io;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.Channels;

import com.google.appengine.api.blobstore.BlobKey;
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
 * @since 0.3.20120510
 * @version 0.3.20120513
 * @change
 *
 */
public class BlobOutputStream extends OutputStream {

  /** stores the file information in case we need it. * /
  private AppEngineFile file=null;
  */
  
  private FileWriteChannel writeChannel=null;
  
  private OutputStream outs;
  
  private AppEngineFile file;
  
  /** 
   * open an existing file to append to it. This is not a typical output stream function so think about it.
   * @param mimeType
   * @param encoding
   * @param blobInfoUploadedfileName
   * @param lock
   */
  public BlobOutputStream(String fullPath, String mimeType, String blobInfoUploadedfileName, boolean lock) throws IOException {

    try {
      /** Get a file service */
      FileService fileService = FileServiceFactory.getFileService();
  
      // Write more to the file in a separate request:
      file = new AppEngineFile(fullPath);
      
      writeChannel = fileService.openWriteChannel(file, lock);
    
      outs = Channels.newOutputStream(writeChannel);
    } catch (Exception ex) {
      throw new IOException("Failed to open Blob ["+fullPath+"] to write to it due to an exception ["+ex.getMessage()+"].", ex);
    }
    
    //FIXME it may never used
    if (outs == null)
      throw new IOException("Failed to open Blob ["+fullPath+"] to write to it for unknown reason.");
   
  }
  
  /** 
   * 
   * @param mimeType
   * @param encoding
   * @param blobInfoUploadedfileName
   * @param lock
   */
  public BlobOutputStream(String mimeType, String blobInfoUploadedfileName, boolean lock) throws IOException {

    try {
      /** Get a file service */
      FileService fileService = FileServiceFactory.getFileService();
  
      // Create a new Blob file with mime-type "text/plain"
      file = fileService.createNewBlobFile(mimeType); //"text/plain");

      writeChannel = fileService.openWriteChannel(file, lock);
      
      outs = Channels.newOutputStream(writeChannel);
    } catch (Exception ex) {
      throw new IOException("Failed to create a Blob with mime-type["+mimeType+"] to write to it due to an exception ["+ex.getMessage()+"].", ex);
    }
    
    //FIXME it may never used
    if (outs == null)
      throw new IOException("Failed to create a Blob with mime-type["+mimeType+"] to write to it for unknown reason.");
  }
  
  /**
   * NOTE: Must be called after the file is closed.
   * 
   * The blob key to the file stored in blobstore.
   * It can be used to read the data later.
   * @return the blob key to the stored data in blobstore.
   */
  public BlobKey getBlobKey() {
    /** Get a file service */
    FileService fileService = FileServiceFactory.getFileService();
    return(fileService.getBlobKey(file));
  }
  
  /**
   * The full path to the file stored in blobstore.
   * It can be used to read the data later.
   * @return the full path to the stored data in blobstore.
   */
  public String getFileFullPath() {
    return(file.getFullPath());
  }
  
  /**
   * The name of file stored in blobstore.
   * @return the name of file that is stored in blobstore.
   */
  public String getFileNamePart() {
    return(file.getNamePart());
  }
  
  /**
   *
   * @return file system used to store the data!!! (not sure what it is).(12-05-13)
   */
  public String getFileSystemName() {
    return(file.getFileSystem().getName());
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
