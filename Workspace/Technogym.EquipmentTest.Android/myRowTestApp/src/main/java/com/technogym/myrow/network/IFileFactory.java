package com.technogym.myrow.network;

/***
 * Interfaccia relativa al componente per la gestione e creazione dei file
 * @author Federico Foschini
 *
 */
public interface IFileFactory {

	/***
	 * Metodo per creare un'istanza del riferimento al file definito secondo i parametri
	 * @param fileFolderPath: percorso della cartella dove risiede il file
	 * @param fileName: nome del file
	 * @param pathSeparator: separatore delle componenti del percorso
	 * @param fileType: tipo di file, in base all'enumeratore {link FileTypeNetworkManager}
	 * @return un'istanza del tipo di File definito attraverso il fileType
	 */
	Object createFile(String fileFolderPath, String fileName, String pathSeparator, FileTypeNetworkManager fileType) throws Exception;
}
