package fr.treeptik.annuaire.dao;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class DAOFactory {

	private static String confDao;

	/**
	 * Permet de charger la propriété du choix de DAO
	 */
	public static void loadProperties() {
		Properties properties = new Properties();

		try {
			properties.load(new FileReader("application.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		confDao = properties.getProperty("conf.dao");

	}

	public static PersonneDAO getPersonneDAO() {
		loadProperties(); 
		PersonneDAO personneDAO = null;

		if ("xml".equalsIgnoreCase(confDao)) {
			personneDAO = new PersonneXMLDAO();
			System.out.println("choix xml");
		} 
		else if ("file".equalsIgnoreCase(confDao)) {
			personneDAO = new PersonneFileDAO();
			System.out.println("choix fichier");

		}
		else if ("jdbc".equalsIgnoreCase(confDao)){
			personneDAO = new PersonneJDBCDAO();
			System.out.println("choix jdbc");

		}else if ("jpa".equalsIgnoreCase(confDao)){
			personneDAO = new PersonneJPADAO();
			System.out.println("choix JPA");

		}

		return personneDAO;

	}

	
	public static NumeroDAO getNumeroDAO() {

		NumeroDAO numeroDAO = null;

		if ("xml".equalsIgnoreCase(confDao)) {
			numeroDAO = new NumeroXMLDAO();
			System.out.println("choix xml");
		} 
		else if ("file".equalsIgnoreCase(confDao)) {
			numeroDAO = new NumeroFileDAO();
			System.out.println("choix fichier");

		}
		else if ("jdbc".equalsIgnoreCase(confDao)){
			numeroDAO = new NumeroJDBCDAO();
			System.out.println("choix jdbc");

		}else if ("jpa".equalsIgnoreCase(confDao)){
			numeroDAO = new NumeroJPADAO();
			System.out.println("choix JPA");

		}

		return numeroDAO;

	}
	
}
