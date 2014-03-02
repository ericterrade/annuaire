package fr.treeptik.annuaire.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

import fr.treeptik.annuaire.jaxb.Numero;

public class NumeroJPADAO implements NumeroDAO {

	static EntityManager entityManager = Persistence
			.createEntityManagerFactory("tpannuaire").createEntityManager();

	@Override
	public List<Numero> findAll() {
		Query query = entityManager.createQuery("Select n From Numero n ");

		return query.getResultList();
	}

	@Override
	public void save(Numero numero) {
		entityManager.getTransaction().begin();

		entityManager.persist(numero);

		entityManager.getTransaction().commit();

	}

	@Override
	public Integer count() {
		Query query = entityManager.createQuery("Select count(n) From Numero n  ");
		Long count = (Long) query.getSingleResult();
		return count.intValue();
	}

}
