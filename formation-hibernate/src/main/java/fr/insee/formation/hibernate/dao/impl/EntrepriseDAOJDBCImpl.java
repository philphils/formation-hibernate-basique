package fr.insee.formation.hibernate.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import fr.insee.formation.hibernate.dao.EntrepriseDAO;
import fr.insee.formation.hibernate.model.Entreprise;

@Repository
public class EntrepriseDAOJDBCImpl implements EntrepriseDAO {

	private Logger logger = LoggerFactory.getLogger(EntrepriseDAOJDBCImpl.class);

	@Value("${fr.insee.formation.hibernate.schema}")
	private String schema;

	@Autowired
	DataSource dataSource;

	@Override
	public List<Entreprise> findAllOrderByDateCreation() {

		Connection connection = getConnection();

		// TODO TP1 Ecrire la requête.
		// Aide : Vous pouvez récupérer le schéma courant dans l'attribut schema de
		// cette classe
		String requete = "";

		PreparedStatement statement = null;

		List<Entreprise> resultEntreprises = new ArrayList<Entreprise>();

		try {

			statement = connection.prepareStatement(requete);

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {

				// TODO TP1 Créer les objets Java Entreprise et renseigner leur champs

			}

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}

		finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					logger.error(e.getMessage(), e);
					throw new RuntimeException(e);
				}
			}
		}

		return resultEntreprises;
	}

	public Connection getConnection() {
		Connection connection = null;

		try {
			connection = dataSource.getConnection();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
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
