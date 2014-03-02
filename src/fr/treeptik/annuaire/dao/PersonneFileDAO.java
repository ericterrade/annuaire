package fr.treeptik.annuaire.dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import fr.treeptik.annuaire.jaxb.Numero;
import fr.treeptik.annuaire.jaxb.Personne;
import fr.treeptik.annuaire.utils.DateUtil;

public class PersonneFileDAO implements PersonneDAO {

	@Override
	public List<Personne> findAll() throws DAOException {

		LinkedList<Personne> personnes = new LinkedList<Personne>();

		try {
			FileReader fileReader = new FileReader("annuaire.txt");
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			String ligne = null;
			while ((ligne = bufferedReader.readLine()) != null) {
				String[] attributPersonne = ligne.split(";");

				Personne personne = new Personne();

				personne.setId(Integer.valueOf(attributPersonne[0]));
				personne.setNom(attributPersonne[1]);
				personne.setPrenom(attributPersonne[2]);

				personne.setDateNaissance(DateUtil.parse(attributPersonne[3]));

				personne.setNumeros(lireTelephone(attributPersonne));

				personnes.add(personne);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new DAOException();
		} catch (IOException e) {
			e.printStackTrace();
			throw new DAOException();
		} 

		return personnes;
	}

	private List<Numero> lireTelephone(String[] attributPersonne) {

		LinkedList<Numero> telephones = new LinkedList<Numero>();

		for (int i = 4; i < attributPersonne.length; i = i + 2) {
			Numero numero = new Numero();
			numero.setType(attributPersonne[i]);
			numero.setTel(attributPersonne[i + 1]);

			telephones.add(numero);
		}

		return telephones;
	}

	@Override
	public void save(Personne personne) throws DAOException {

		FileWriter fileWriter = null;
		try {
			
			fileWriter = new FileWriter("annuaire.txt", true);

			StringBuilder stringBuilder = new StringBuilder();
			
			stringBuilder.append(System.getProperty("line.separator"));
			
			stringBuilder.append(personne.getId()).append(";")
					.append(personne.getNom()).append(";")
					.append(personne.getPrenom()).append(";");

			stringBuilder.append(DateUtil.format(personne.getDateNaissance())).append(";");
			
			for (Numero numero : personne.getNumeros()){
				stringBuilder.append(numero.getType()).append(";");
				stringBuilder.append(numero.getTel()).append(";");
			}
			
			fileWriter.write(stringBuilder.toString());
			
			
		} catch (IOException e) {
			e.printStackTrace();
			throw new DAOException();
			
		}finally {
			try {
				fileWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public Integer count() throws DAOException {
		return findAll().size();
	}

}
