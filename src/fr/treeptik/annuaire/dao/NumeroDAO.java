package fr.treeptik.annuaire.dao;

import java.util.List;

import fr.treeptik.annuaire.jaxb.Numero;

public interface NumeroDAO {

	List<Numero> findAll();
	void save(Numero numero);
	Integer count();
	
}
