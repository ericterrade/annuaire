package fr.treeptik.annuaire.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

import fr.treeptik.annuaire.jaxb.Personne;



public class PersonneJPADAO implements PersonneDAO {

	static EntityManager entityManager = Persistence.createEntityManagerFactory("tpannuaire").createEntityManager();
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Personne> findAll() throws DAOException {

		Query query = entityManager.createQuery("Select p From Personne p ");
		
		return query.getResultList();
	}

	@Override
	public void save(Personne personne) throws DAOException {

		entityManager.getTransaction().begin();
		
		entityManager.persist(personne);
		
		entityManager.getTransaction().commit();
		
	}

	@Override
	public Integer count() throws DAOException {
		
		Query query = entityManager.createQuery("Select count(p) From Personne p  ");
		Long count = (Long) query.getSingleResult();
		
		return count.intValue();
	}

}
