package com.technogym.equipmenttest.appbleuart.manager;

import com.technogym.equipmenttest.library.network.file.manager.FileTypeNetworkManager;
import com.technogym.equipmenttest.library.network.file.manager.IFileFactory;
import com.technogym.equipmenttest.library.network.file.manager.INetworkFileStreamManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;

import it.spot.android.logger.domain.Logger;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileOutputStream;

/***
 * Classe relativa al componente per la gestione dei filestream attraverso la rete
 * @author Federico Foschini
 *
 */
public class NetworkFileStreamManager implements INetworkFileStreamManager {

	protected SmbFile remoteFile;			// File remoto
	protected File localFile;				// File locale
	protected IFileFactory fileFactory;		// Factory per la creazione del riferimento ai file

	/***
	 * Costruttore
	 */
	public NetworkFileStreamManager() {
		fileFactory = new FileFactory();
	}

	@Override
	public void setLocalFile(String fileFolderPath, String fileName,
			String pathSeparator) {
		try {
			localFile = (File)fileFactory.createFile(fileFolderPath, fileName, pathSeparator, FileTypeNetworkManager.LOCAL);
			initFile(localFile, FileTypeNetworkManager.LOCAL);
		} catch (Exception e) {
			e.printStackTrace();
			Logger.getInstance().logError(
					getClass().getSimpleName(), "[NetworkFileStreamManager - setLocalFile]" + e.getLocalizedMessage());
		}		
	}

	@Override
	public void setRemoteFile(String fileFolderPath, String fileName,
			String pathSeparator) {
		try {
			Logger.getInstance().logDebug(
					getClass().getSimpleName(), "[NetworkFileStreamManager - setRemoteFile] " 
							+ "Remote file path: " + fileFolderPath + pathSeparator + fileName);
			remoteFile = (SmbFile)fileFactory.createFile(fileFolderPath, fileName, pathSeparator, FileTypeNetworkManager.SMBFILE);
			initFile(remoteFile, FileTypeNetworkManager.SMBFILE);
		} catch (Exception e) {
			e.printStackTrace();
			Logger.getInstance().logError(
					getClass().getSimpleName(), "[NetworkFileStreamManager - setRemoteFile]" + e.getLocalizedMessage());
		}
	}

	@Override
	public boolean moveLocalFileContentToRemoteFileContent() {
		try {
            /*
             * Apertura degli stream dei file e degli appositi reader
             */
//			Log.i("[NetworkFileStreamManager]", "[copyLocalFileContentToRemoteFileContent] Open Streams");
			FileInputStream fis = new FileInputStream(localFile);
	        SmbFileOutputStream sfos = new SmbFileOutputStream(remoteFile);
	        BufferedReader localReader = new BufferedReader(new InputStreamReader(fis));

            /*
             * Copia di tutte le righe del contenuto del file locale
             * all'interno del file remoto
             */
//			Logger.getInstance().logDebug(
//					getClass().getSimpleName(), "[NetworkFileStreamManager - copyLocalFileContentToRemoteFileContent] " 
//							+ "Start copying content");

	        String line = null;
            while ((line = localReader.readLine()) != null) {
            	line = line + "\n";
            	sfos.write(line.getBytes());
            }
            
            /*
             * Chiusura degli stream dei file
             */
//			Logger.getInstance().logDebug(
//					getClass().getSimpleName(), "[NetworkFileStreamManager - copyLocalFileContentToRemoteFileContent] " 
//							+ "Closing Streams");

            localReader.close();
            fis.close();
            sfos.close();

            /*
             * Pulizia del file locale in modo da renderlo vuoto
             */
            FileWriter fos = new FileWriter(localFile);
			fos.write("\n");
			fos.flush();
			fos.close();
            
            return true;
		} catch (Exception e) {
			e.printStackTrace();
			Logger.getInstance().logError(
					getClass().getSimpleName(), "[NetworkFileStreamManager - copyLocalFileContentToRemoteFileContent]" 
							+ e.getLocalizedMessage());
		}
		return false;
	}
	
	/*
	 * Metodo per inizializzare il file e crearlo se non esiste
	 * @param file: file da controllare
	 * @param fileType: tipo del file da controllare
	 */
	protected void initFile(Object file, FileTypeNetworkManager fileType) {
		try {
			if(fileType.equals(FileTypeNetworkManager.LOCAL)) {
				File toCheck = (File)file;
				if(!toCheck.exists()) {
					toCheck.createNewFile();
					Logger.getInstance().logDebug(
							getClass().getSimpleName(), "[NetworkFileStreamManager - initFile] " 
									+ "File [LOCAL] created");
				}
			} else if(fileType.equals(FileTypeNetworkManager.SMBFILE)) {
				SmbFile toCheck = (SmbFile)file;
				if(!toCheck.exists()) {
					toCheck.createNewFile();
					Logger.getInstance().logDebug(
							getClass().getSimpleName(), "[NetworkFileStreamManager - initFile] " 
									+ "File [SMBFILE] created");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Logger.getInstance().logError(
					getClass().getSimpleName(), "[NetworkFileStreamManager - initFile]" 
							+ e.getLocalizedMessage());
		}
	}

	@Override
	public boolean backupLocalFile(String backupFolderPath, String backupFileName, String pathSeparator) {
		try {
			/*
			 * Creazione della cartella per i backup nel caso non esista
			 */
			File backupDir = new File(backupFolderPath);
			if(!backupDir.exists()) {
				backupDir.mkdir();
//				Logger.getInstance().logDebug(
//						getClass().getSimpleName(), "[NetworkFileStreamManager - backupLocalFile] " 
//								+ "Directory backup created");
			}
			
			/*
	         * Apertura degli stream dei file e degli appositi reader
	         */
//			Logger.getInstance().logDebug(
//					getClass().getSimpleName(), "[NetworkFileStreamManager - backupLocalFile] " 
//							+ "Open Streams");
			FileInputStream fis = new FileInputStream(localFile);
			File backup = new File(backupFolderPath + pathSeparator + backupFileName);
		    OutputStream out = new FileOutputStream(backup);
	        BufferedReader localReader = new BufferedReader(new InputStreamReader(fis));
	        
	        /*
	         * Copia di tutte le righe del contenuto del file locale all'interno del file di backup
	         */
//			Logger.getInstance().logDebug(
//					getClass().getSimpleName(), "[NetworkFileStreamManager - backupLocalFile] " 
//							+ "Start copying content");
	        String line = null;
	        while ((line = localReader.readLine()) != null) {
	        	line = line + "\n";
	        	out.write(line.getBytes());
	        }
	
	        /*
	         * Chiusura degli stream dei file
	         */
//			Logger.getInstance().logDebug(
//					getClass().getSimpleName(), "[NetworkFileStreamManager - backupLocalFile] " 
//							+ "Closing Streams");
	        localReader.close();
	        fis.close();
	        out.close();

            /*
             * Pulizia del file locale in modo da renderlo vuoto
             */
            FileWriter fos = new FileWriter(localFile);
			fos.write("\n");
			fos.flush();
			fos.close();
            
	        return true;
		} catch (Exception e) {
			e.printStackTrace();
			Logger.getInstance().logError(
					getClass().getSimpleName(), "[NetworkFileStreamManager - backupLocalFile]" 
							+ e.getLocalizedMessage());
		}
		return false;
	}
}
