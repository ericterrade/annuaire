package fr.treeptik.annuaire.dao;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import fr.treeptik.annuaire.jaxb.Annuaire;
import fr.treeptik.annuaire.jaxb.Numero;
import fr.treeptik.annuaire.jaxb.Personne;

public class PersonneXMLDAO implements PersonneDAO {

	@Override
	public List<Personne> findAll() throws DAOException {

		List<Personne> personnes = new LinkedList<Personne>();

		try {

			JAXBContext context = JAXBContext
					.newInstance("fr.treeptik.annuaire.jaxb");
			Unmarshaller unmarshaller = context.createUnmarshaller();

			Annuaire annuaire = (Annuaire) unmarshaller.unmarshal(new File(
					"annuaire.xml"));
			personnes = annuaire.getPersonne();

		} catch (JAXBException e) {
			e.printStackTrace();
			throw new DAOException();
		}

		return personnes;
	}

	private List<Numero> lireNumero(List<Numero> numeros) {

		numeros = new LinkedList<Numero>();
		for (Numero numero : numeros) {
			numero = new Numero(numero.getTel(),
					numero.getType());

			numeros.add(numero);
		}

		return numeros;

	}

	@Override
	public void save(Personne personne) throws DAOException {

		try {
			
			JAXBContext context = JAXBContext.newInstance("fr.treeptik.annuaire.jaxb");
			//créer un unMarshaller pour récuperer la liste de personnes déjà enregistrée
			Unmarshaller unmarshaller = context.createUnmarshaller();
			Annuaire annuaire = (Annuaire) unmarshaller.unmarshal(new File(
					"personne.xml"));
			Marshaller marshaller = context.createMarshaller();
			
			//Ajout de la nouvelle personne créée
			annuaire.getPersonne().add(personne);
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(annuaire, new File("personne.xml"));
			
		} catch (JAXBException e) {
			e.printStackTrace();
			throw new DAOException();
		}

	}

	@Override
	public Integer count() throws DAOException {
		return findAll().size();
	}

}
