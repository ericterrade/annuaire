package fr.treeptik.annuaire.dao;

import java.util.List;

import fr.treeptik.annuaire.jaxb.Personne;



public interface PersonneDAO {

	List<Personne> findAll() throws DAOException;
	void save(Personne personne) throws DAOException;
	Integer count() throws DAOException;
	
	
}
