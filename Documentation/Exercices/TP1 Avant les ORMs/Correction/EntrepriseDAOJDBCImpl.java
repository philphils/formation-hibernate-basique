package fr.insee.formation.hibernate.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import fr.insee.config.InseeConfig;
import fr.insee.config.exception.PoolException;
import fr.insee.formation.hibernate.dao.EntrepriseDAO;
import fr.insee.formation.hibernate.model.Adresse;
import fr.insee.formation.hibernate.model.Entreprise;
import fr.insee.formation.hibernate.model.TypeVoie;

public class EntrepriseDAOJDBCImpl implements EntrepriseDAO {

	private Logger logger = Logger.getLogger(EntrepriseDAOJDBCImpl.class);

	@Value("${fr.insee.formation.hibernate.schema}")
	private String schema;

	@Override
	public List<Entreprise> findAllOrderByDateCreation() {

		Connection connection = getConnection();

		//TODO Ecrire la requÃªte
		String requete = "SELECT * FROM " + schema + ".ENTREPRISE ORDER BY dateCreation";

		PreparedStatement statement = null;

		List<Entreprise> resultEntreprises = new ArrayList<Entreprise>();

		try {

			statement = connection.prepareStatement(requete);

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {

				//TODO CrÃ©er les objets Java Entreprise et renseigner leur champs
				Entreprise entreprise = new Entreprise();

				entreprise.setId(resultSet.getInt("id"));

				entreprise.setSiren(resultSet.getString("siren"));

				entreprise.setDenomination(resultSet.getString("denomination"));

				Adresse adresse = new Adresse();

				adresse.setNomVoie(resultSet.getString("rue"));

				adresse.setVille(resultSet.getString("ville"));

				adresse.setTypeVoie(TypeVoie.valueOf(resultSet.getString("typevoie")));

				entreprise.setAdresse(adresse);

				resultEntreprises.add(entreprise);

			}

		} catch (SQLException e) {
			logger.error(e, e);
			throw new RuntimeException(e);
		}

		finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					logger.error(e, e);
					throw new RuntimeException(e);
				}
			}
		}

		return resultEntreprises;
	}

	public Connection getConnection() {
		Connection connection = null;

		try {
			connection = InseeConfig.getPool("hibernate").getConnection();
		} catch (SQLException e) {
			logger.error(e, e);
			throw new RuntimeException(e);
		} catch (PoolException e) {
			logger.error(e, e);
			throw new RuntimeException(e);
		}
		return connection;
	}

	@Override
	public void persist(Entreprise entreprise) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void upperDenomination() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeEntrepriseById(int identifiant) {
		// TODO Auto-generated method stub
		
	}

}

