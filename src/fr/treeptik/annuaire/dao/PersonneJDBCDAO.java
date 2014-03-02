package fr.treeptik.annuaire.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import fr.treeptik.annuaire.jaxb.Personne;
import fr.treeptik.annuaire.utils.DateUtil;
import fr.treeptik.annuaire.utils.JDBCUtils;

public class PersonneJDBCDAO implements PersonneDAO {

	@Override
	public List<Personne> findAll() throws DAOException {

		Connection connection = JDBCUtils.getConnection();
		LinkedList<Personne> personnes = new LinkedList<Personne>();
		try {
			
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("Select * from T_Person ");
			
			while (resultSet.next()){
				
				Personne personne = new Personne();
				personne.setId(resultSet.getInt("id"));
				personne.setNom(resultSet.getString("lastName"));
				personne.setPrenom(resultSet.getString("firstName"));
				personne.setDateNaissance(
						DateUtil.toUtilDate(resultSet.getDate("birthday")));
				
				personnes.add(personne);
				
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return personnes;
	}

	@Override
	public void save(Personne personne) throws DAOException {

		try {

			Connection connection = JDBCUtils.getConnection();
			
			PreparedStatement statement = connection.prepareStatement(
					"Insert Into T_Person (lastName, firstName, birthday) Values (?,?,?) ");

			statement.setString(1, personne.getNom());
			statement.setString(2, personne.getPrenom());
			statement.setDate(3, DateUtil.toSqlDate(personne.getDateNaissance()));
			
			statement.executeUpdate();
			
		} 
		catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException();
		}

	}
	
	@Override
	public Integer count() throws DAOException {
		
		Connection connection = JDBCUtils.getConnection();
		Statement stm;
		String requete;
		ResultSet rs = null;
		int count;
		try {
			
			stm = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			requete = "select * from T_Person";
			rs = stm.executeQuery(requete);
			rs.last();
			count = rs.getRow();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException();
			
		}
		
		return count;
		
	}

}





