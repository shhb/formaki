package org.noranj.formak.server.utils;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.noranj.formak.server.domain.core.Attachment;
import org.noranj.formak.server.domain.sa.SystemUser;
import org.noranj.formak.server.io.BlobInputStream;
import org.noranj.formak.server.io.BlobOutputStream;
import org.noranj.formak.shared.type.DocumentType;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreFailureException;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.files.AppEngineFile;
import com.google.appengine.api.files.FileService;
import com.google.appengine.api.files.FileServiceFactory;

/**
 * This class is developed to work with files on blobstore.
 * The File Service used in this class is experimental APIs in GAE so it is important to hide them in this class.
 * 
 * 
 * NOTE: BlobStore is NOT segmented by NAMESPACES.
 * 
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @see BlobInputStream, BlobOutputStream
 * @author
 * @since 0.3.20120510
 * @version 0.3.20120513
 * @change
 *
 */
public class FileServiceHelper {

  private static Logger logger = Logger.getLogger(FileServiceHelper.class.getName());


  /**
   * 
   * @param blobKeyString
   * @throws IOException
   */
  public static void deleteBlobFile(String blobKeyString) throws IOException {

    //FIXME maybe FINE is better level!!
    if(logger.isLoggable(Level.INFO))
      logger.info("deleteBlobFile: blobKey["+blobKeyString+"]");
    
    try {
      BlobstoreService blobStoreService = BlobstoreServiceFactory.getBlobstoreService();
      BlobKey[] bks = new BlobKey[1]; 
      bks[0]=new BlobKey(blobKeyString);
      blobStoreService.delete(bks);
    } catch (BlobstoreFailureException bfex) {
      throw new IOException("Failed to delete the blob with the key["+blobKeyString+"] due to a BlobstoreFailureException["+bfex.getMessage()+"]");
    }

    if(logger.isLoggable(Level.INFO))
      logger.info("deleteBlobFile: deleted successfully blobKey["+blobKeyString+"]");

  }
  
  /**
   * 
   * @param docType
   * @param originatorUser
   * @param receiverUser
   * @param attachment
   * @return
   * @throws IOException
   */
	public static String writeToBlobFile(DocumentType docType, SystemUser originatorUser, SystemUser receiverUser, Attachment attachment) throws IOException {
	  
    //FIXME maybe FINE is better level!!
	  if(logger.isLoggable(Level.INFO))
	    logger.info("writeToFile: DocumentType["+docType+"] originator["+originatorUser+"] receiver["+receiverUser+"] attachment["+attachment+"]");
	  
	  // Create a Name to be stored in blob Info. This is not mandatory and I used it to have extra information about the data in blobstore.
	  String blobInfoUploadedfileName = System.currentTimeMillis() + "-" + originatorUser.getId();
	  
	  String mimeType = attachment.getMimeType().toString();
	  
	  BlobOutputStream bos = new BlobOutputStream(mimeType, blobInfoUploadedfileName, true);
	  bos.write(attachment.getContent());
	  bos.close();
    BlobKey blobKey = bos.getBlobKey();
	  bos = null;

	  //FIXME maybe FINE is better level!!
	  if(logger.isLoggable(Level.INFO))
	    logger.info("writeToFile: blobKey["+blobKey.getKeyString()+"] blobInfoUploadedfileName["+blobInfoUploadedfileName+"] DocumentType["+docType+"] originator["+originatorUser+"] receiver["+receiverUser+"] attachment["+attachment+"]");

	  return(blobKey.getKeyString());
	  
	} // writeTo
	
	
	/**
	 * 
	 * @param path
	 * @throws IOException
	 */
	public static void readFromBlobFile(String blobKeyString) throws IOException {
	  
    //FIXME maybe FINE is better level!!
    if(logger.isLoggable(Level.INFO))
      logger.info("readFromFile: blobKeyString["+blobKeyString+"]");
    
    
    BlobInputStream bos = new BlobInputStream(blobKeyString, false); // no need to lock it.
    //bos.read(attachment.getContent());
    bos.close();
    bos = null;

    //FIXME maybe FINE is better level!!
    if(logger.isLoggable(Level.INFO))
      logger.info("readFromFile: blobkey["+blobKeyString+"]");

	} 
	
	/**
	 * 
	 * @param path
	 * @param start
	 * @param end
	 * @return
	 * @deprecated Not sure why we need this. 2012-05-10
	 */
	public String readFromByBlobAPI(String path, long start, long end) {

		// Get a file service
	  FileService fileService = FileServiceFactory.getFileService();

	  // Write more to the file in a separate request:
		AppEngineFile file = new AppEngineFile(path);
		
	  // Now read from the file using the Blobstore API
	  BlobKey blobKey = fileService.getBlobKey(file);
	  BlobstoreService blobStoreService = BlobstoreServiceFactory.getBlobstoreService();
	  String segment = new String(blobStoreService.fetchData(blobKey, start, end));
	  return(segment);
	} 
  
}
