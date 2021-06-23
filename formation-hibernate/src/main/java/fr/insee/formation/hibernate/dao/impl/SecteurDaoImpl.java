package fr.insee.formation.hibernate.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import fr.insee.formation.hibernate.dao.SecteurDAO;
import fr.insee.formation.hibernate.model.Secteur;

@Repository
public class SecteurDaoImpl implements SecteurDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Secteur find(int id) {

		return entityManager.find(Secteur.class, id);

	}

	@Override
	public Secteur findByCodeNaf(String codeNaf) {
		return entityManager.createQuery("SELECT sect FROM Secteur sect WHERE sect.codeNaf = :codeNaf", Secteur.class)
				.setParameter("codeNaf", codeNaf).getSingleResult();
	}

	@Override
	public Secteur findByCodeNafWithEntreprisesAndDeclarationAndIndicesJPQL(String codeNaf) {
		
		//TODO : TP7 Réaliser la  méthode de récupération du secteurs avec ses associations avec JPQL

		return null;
	}

	@Override
	public Secteur findByCodeNafWithEntreprisesAndDeclarationAndIndicesCriteria(String codeNaf) {

		//TODO : TP8 Réaliser la méthode de récupération du secteurs avec ses associations en Criteria
		
		return null;
	}

}
