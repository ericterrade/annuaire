package fr.treeptik.annuaire.runtime;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import fr.treeptik.annuaire.dao.DAOException;
import fr.treeptik.annuaire.dao.DAOFactory;
import fr.treeptik.annuaire.dao.NumeroDAO;
import fr.treeptik.annuaire.dao.NumeroFileDAO;
import fr.treeptik.annuaire.dao.NumeroXMLDAO;
import fr.treeptik.annuaire.dao.PersonneDAO;
import fr.treeptik.annuaire.dao.PersonneFileDAO;
import fr.treeptik.annuaire.dao.PersonneJDBCDAO;
import fr.treeptik.annuaire.dao.PersonneXMLDAO;
import fr.treeptik.annuaire.jaxb.Numero;
import fr.treeptik.annuaire.jaxb.Personne;
import fr.treeptik.annuaire.utils.DateUtil;
import fr.treeptik.annuaire.utils.JDBCUtils;
import fr.treeptik.annuaire.utils.PersonneDateNaissanceComparator;
import fr.treeptik.annuaire.utils.PersonneNomComparator;
import fr.treeptik.annuaire.utils.WatchPropertiesService;

public class Runtime {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		dynamicChoice();
//		staticInsert();
//		Personne personne2 = PersonneBuilder.getInstance().withNom("TOTO")
//				.withPrenom("TOTO").withDateNaissance("12/03/1960").build();
//System.out.println(personne2);
	}
	
	
	
	/**
	 * Pour commencer à "jouer" avec les modèle et DAO créer on va les tester avec un simple insert statique
	 */
	public static void staticInsert(){
		
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		Date dateNaissance = null;

		
		// Un objet date est créé
		try {

			dateNaissance = dateFormat.parse("27/03/1980");

		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		// Deux objets numero sont créé
		Numero proPersonne1 = new Numero(1, "0491635548",
				"PRO");
		Numero fixePersonne1 = new Numero(2, "0476776734",
				"FIXE");

		// retourne une liste sérialisable de numero
		// Utilisation de la méthode Static asList() de la Classe Arrays (au pluriel)
		List<Numero> telephones = Arrays.asList(proPersonne1, fixePersonne1);
		System.out.println(dateNaissance);
		
		//un objet personne est créé avec l'objet date et l'objet list de numero créés avant
		Personne personne1 = new Personne(1, "NEwPersonne1", "NEwPersonne1",
				dateNaissance, telephones);
		System.out.println(personne1);
		
		// On inspecte le choix dy type de persistence utilisé
		DAOFactory.loadProperties();
		//création d'un processus parallèle charger de surveiller le fichier de conf indiquant le type de persistence désirée
		Thread thread = new Thread(new WatchPropertiesService());
		thread.start();

		
		PersonneDAO persDao = DAOFactory.getPersonneDAO();
		NumeroDAO numeroDAO = DAOFactory.getNumeroDAO();
		
//		PersonneDAO fileDao = DAOFactory.getPersonneDAO();

		try {
			persDao.save(personne1);
			numeroDAO.save(fixePersonne1);
			numeroDAO.save(proPersonne1);
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
		// afficherPersonne(personne1);
		//
		// System.out.println("################# FILE ");
		//
		// PersonneDAO fileDao = DAOFactory.getPersonneDAO();
		//
		// List<Personne> personnes = fileDao.findAll();
		//
		// for (Personne personne : personnes) {
		//
		// afficherPersonne(personne);
		//
		// }
		//
		// fileDao.save(personne1);
		//
		// Personne personne2 = PersonneBuilder.getInstance().withNom("TOTO")
		// .withPrenom("TOTO").withDateNaissance("12/03/1960").build();
		//
		// Personne personne3 = PersonneBuilder.getInstance().withNom("Nom")
		// .build();

		/**
		 * 
		 * Choix dynammique de l'action
		 * @throws DAOException 
		 * @throws SQLException 
		 * 
		 */
		public static void dynamicChoice() throws DAOException, SQLException{
			

		
		DAOFactory.loadProperties();
		Thread thread = new Thread(new WatchPropertiesService());
		thread.start();

		String choix = "";

		while (!choix.equalsIgnoreCase("q")) {

			System.out.println("######## MENU ############");
			System.out.println("");
			System.out.println("1 - Afficher l'annuaire ");
			System.out.println("2 - Saisir une personne ");
			System.out.println("3 - Afficher l'annuaire ordonnee par nom ");
			System.out.println("4 - Afficher l'annuaire ordonnee par la date ");
			System.out.println("5 - Nombre de personne ");
			System.out.println("q - Pour quitter");

			Scanner scanner = new Scanner(System.in);
			choix = scanner.nextLine();

			PersonneDAO persDao = DAOFactory.getPersonneDAO();
			NumeroDAO numeroDAO = DAOFactory.getNumeroDAO();

			if ("1".equals(choix)) {

				List<Personne> personnes = persDao.findAll();
				for (Personne personne : personnes) {

					afficherPersonne(personne);

				}
			} else if ("2".equals(choix)) {

				System.out.println("Saisir  Nom Prenom jj/mm/yyyy");
				String[] attributPersonne = scanner.nextLine().split(" ");
				Personne personne = new Personne();
				personne.setNom(attributPersonne[0]);
				personne.setPrenom(attributPersonne[1]);
				personne.setDateNaissance(DateUtil.parse(attributPersonne[2]));
				
				if (persDao instanceof PersonneXMLDAO || persDao instanceof PersonneFileDAO) {
					System.out.println("saisir l'ID :");
					int i = Integer.parseInt(scanner.nextLine());

					personne.setId(i);

				}
				System.out.println("Saisir Le ou les Téléphones : type numéro");
				Numero numero = saisirNumero(numeroDAO, scanner);
				List<Numero> numeros = personne.getNumeros();
				numeros.add(numero);
				System.out.println("voulez vous saisir d'autres numéros o/n");
				String s = scanner.next();
				while (!s.equals("o") && !s.equals("n")){
				if (s.equals("o")){
				numero = saisirNumero(numeroDAO, scanner);
				numeros = new ArrayList<>();
				numeros.add(numero);
			}
				else if (s.equals("n")) {
					System.out.println("merci !!");
				}
				else {
					System.out.println("veuillez saisir juste o pour oui et n pour non");
				}
				}
				personne.setNumeros(numeros);
				persDao.save(personne);
				
				//Commit le resultat si JDBC est utilisé
				if (persDao instanceof PersonneXMLDAO || persDao instanceof PersonneJDBCDAO) {
					
					JDBCUtils.getConnection().commit();
				}

			} else if ("3".equals(choix)) {

				List<Personne> personnes = persDao.findAll();
				Collections.sort(personnes, new PersonneNomComparator());

				for (Personne personne : personnes) {

					afficherPersonne(personne);

				}

			} else if ("4".equals(choix)) {

				List<Personne> personnes = persDao.findAll();

				Collections.sort(personnes,
						new PersonneDateNaissanceComparator());

				for (Personne personne : personnes) {

					afficherPersonne(personne);

				}

			} else if ("5".equals(choix)) {

				System.out.println("Nombre de personnes : " + persDao.count());

			} else if ("q".equalsIgnoreCase(choix)) {
				System.out.println("Merci a bientot");
			} else {
				System.out.println("Erreur de saisie");
			}
		}
	}
	
		
	public static Numero saisirNumero(NumeroDAO numeroDAO, Scanner scanner) {
		
		String[] attributNumero = scanner.nextLine().split(" ");
		Numero numero;
		if (numeroDAO instanceof NumeroXMLDAO || numeroDAO instanceof NumeroFileDAO) {
		numero = new Numero(new Random().nextInt(200),attributNumero[0],attributNumero[1]);
		}
		else{
		numero = new Numero(attributNumero[0],attributNumero[1]);
			numeroDAO.save(numero);
		}
		
		return numero;
	}
		
		
	public static void afficherPersonne(Personne personne) {

		System.out.println("Nom : " + personne.getNom());
		System.out.println("Prenom : " + personne.getPrenom());

		System.out.println("Date Naissance : "
				+ DateUtil.format(personne.getDateNaissance()));

		for (Numero numero : personne.getNumeros()) {
			System.out.println(numero.getType() + " : "
					+ numero.getTel());
		}

	}
}
