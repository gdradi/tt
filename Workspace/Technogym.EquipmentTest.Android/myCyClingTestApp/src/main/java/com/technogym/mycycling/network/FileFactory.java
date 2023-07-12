package com.technogym.mycycling.network;

import com.technogym.mycycling.Configuration;

import java.io.File;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;

/***
 * Classe relativa al componente per la gestione e creazione dei file
 * @author Federico Foschini
 *
 */
public class FileFactory implements IFileFactory {

	@Override
	public Object createFile(String fileFolderPath, String fileName,
			String pathSeparator, FileTypeNetworkManager fileType) throws Exception {

        /*
         * In base al tipo restituisco il relativo file, in caso di tipo non valido sarà restituito NULL
         */
		if(fileType.equals(FileTypeNetworkManager.LOCAL)) {
			File target = new File(fileFolderPath + pathSeparator + fileName);
			if(!target.exists()){
				target.createNewFile();
			}
			return (target);
		} else if(fileType.equals(FileTypeNetworkManager.SMBFILE)) {
			NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(
						Configuration.MYCYCLING_LOG_SHARED_FOLDER_DOMAIN,
						Configuration.MYCYCLING_LOG_SHARED_FOLDER_USERNAME,
						Configuration.MYCYCLING_LOG_SHARED_FOLDER_PASSWORD
					);
			SmbFile target = new SmbFile(fileFolderPath + pathSeparator + fileName, auth);
			if(!target.exists()){
				target.createNewFile();
			}
			return (target);
		}
		return null;
	}
}
