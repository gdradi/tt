package com.technogym.myrow.network;

/***
 * Interfaccia relativa al componente per la gestione dei filestream attraverso la rete
 * @author Federico Foschini
 *
 */
public interface INetworkFileStreamManager {

	/***
	 * Metodo per impostare il file locale di cui gestire il contenuto
	 * @param fileFolderPath: percorso della cartella dove risiede il file
	 * @param fileName: nome del file
	 * @param pathSeparator: separatore delle componenti del percorso
	 */
	void setLocalFile(String fileFolderPath, String fileName, String pathSeparator);
	
	/***
	 * Metodo per impostare il file remoto di cui gestire il contenuto
	 * @param fileFolderPath: percorso della cartella dove risiede il file
	 * @param fileName: nome del file
	 * @param pathSeparator: separatore delle componenti del percorso
	 */
	void setRemoteFile(String fileFolderPath, String fileName, String pathSeparator);
	
	/***
	 * Metodo per effettuare il backup del file locale
	 * @param backupFolderPath: cartella dove inserire il backup
	 * @param backupFileName: nome del file di backup
	 * @param pathSeparator: separatore delle componenti del percorso
	 * @return true se il backup termina con successo, false altrimenti
	 */
	boolean backupLocalFile(String backupFolderPath, String backupFileName, String pathSeparator);
	
	/***
	 * Metodo per spostare il contenuto del file locale all'interno del file remoto
	 * @return true se la copia è riuscita con successo, false altrimenti
	 */
	boolean moveLocalFileContentToRemoteFileContent();
}
