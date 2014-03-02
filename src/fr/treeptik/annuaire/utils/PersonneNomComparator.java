package fr.treeptik.annuaire.utils;

import java.util.Comparator;

import fr.treeptik.annuaire.jaxb.Personne;



public class PersonneNomComparator implements Comparator<Personne> {

	@Override
	public int compare(Personne o1, Personne o2) {
		
		int result = o1.getNom().compareTo(o2.getNom());
		
		if (result == 0){
			result =  o1.getPrenom().compareTo(o2.getPrenom());
		}
		
		return result ;
	}

}
