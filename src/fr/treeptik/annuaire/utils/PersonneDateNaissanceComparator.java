package fr.treeptik.annuaire.utils;

import java.util.Comparator;

import fr.treeptik.annuaire.jaxb.Personne;



public class PersonneDateNaissanceComparator implements Comparator<Personne> {

	@Override
	public int compare(Personne o1, Personne o2) {
		return o1.getDateNaissance().compareTo(o2.getDateNaissance());
	}

}
