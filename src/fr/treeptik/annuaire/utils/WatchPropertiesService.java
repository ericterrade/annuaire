package fr.treeptik.annuaire.utils;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;

import fr.treeptik.annuaire.dao.DAOFactory;

public class WatchPropertiesService implements Runnable {

	@Override
	public void run() {

		try {
			//Creation d'un objet FileSytem pour créer un un objet watchService
			FileSystem fileSystem = FileSystems.getDefault();
			WatchService watchService = fileSystem.newWatchService();

			Path path = fileSystem.getPath(".");
			// on lie le path ave le watchService et le type d'évènements devant être surveiller
			path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

			WatchKey watchKey = null;
			do {
				//Extrait et retire un eévènement si'il en a un , attend s'il n'y en a pas de présente
				watchKey = watchService.take();
				
				//Polling des événemments en attente
				List<WatchEvent<?>> events = watchKey.pollEvents();
				for (WatchEvent<?> watchEvent : events) {

					Path newPath = (Path) watchEvent.context();
					String absolutePath = newPath.toFile().getAbsolutePath();
					
					// Condition pour lancer DAOFactory.loadProperties()
					if (absolutePath.endsWith("application.properties")){
						System.out.println("Changement de la configuration ...");
						DAOFactory.loadProperties();
					}

				}
				// Réactive la watchKey si nécessaire 
			} while (watchKey.reset());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
