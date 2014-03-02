package fr.treeptik.annuaire.builder;

import java.util.Date;
import java.util.List;

import fr.treeptik.annuaire.jaxb.Numero;
import fr.treeptik.annuaire.jaxb.Personne;
import fr.treeptik.annuaire.utils.DateUtil;

public class PersonneBuilder {

	private static  Personne personne;
	private static PersonneBuilder instance;
	
	public static PersonneBuilder getInstance(){
		personne = new Personne();
		instance =  new PersonneBuilder();
		return instance;
	}
	
	public PersonneBuilder withNom(String nom){
		PersonneBuilder.personne.setNom(nom);
		return instance;
	}
	
	public PersonneBuilder withPrenom(String prenom){
		PersonneBuilder.personne.setPrenom(prenom);
		return instance;
	}
	public PersonneBuilder withDateNaissance(String dateNaissance){
		PersonneBuilder.personne.setDateNaissance(DateUtil.parse(dateNaissance));
		return instance;
	}
	public PersonneBuilder withDateNaissance(Date dateNaissance){
		PersonneBuilder.personne.setDateNaissance(dateNaissance);
		return instance;
	}
	public PersonneBuilder withNumeros(List<Numero> numeros){
		PersonneBuilder.personne.setNumeros(numeros);
		return instance;
	}
	
	
	public Personne build(){
		return PersonneBuilder.personne;
	}
	
}
