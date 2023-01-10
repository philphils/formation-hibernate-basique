package fr.insee.formation.hibernate.dao.impl;

import java.util.List;

import fr.insee.formation.hibernate.dao.EntrepriseDAO;
import fr.insee.formation.hibernate.model.Entreprise;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

/**
 * Classe à compléter pour le TP3. Dans toutes les méthodes vous pouvez accéder
 * directement à l'entityManager qui est un attribut de la classe. 
 * <br/><br/>
 * Celui-ci ne
 * sera pas null car toutes la classe est annotée @Transactionnal et donc toutes
 * les méthodes s'exécutent au sein d'une transaction
 *
 */
//@Repository
@Transactional
public class EntrepriseDAOJPAImpl implements EntrepriseDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Entreprise> findAllOrderByDateCreation() {
		return entityManager.createQuery("SELECT ent FROM Entreprise ent ORDER BY dateCreation", Entreprise.class)
				.getResultList();
	}

	@Override
	public void persist(Entreprise entreprise) {
		// TODO TP3 Ecrire la méthode pour persister une entreprise
	}

	@Override
	public void upperDenomination() {
		// TODO TP3 Ecrire une méthode qui met en majuscule la dénmination de
		// toutes les entreprises
		// Indication : Vous pouvez vous appuyer sur la méthode
		// findAllOrderByDateCreation plus haut pour récupérer toutes les
		// entreprises
	}

	@Override
	public void removeEntrepriseById(int identifiant) {
		// TODO TP3 Ecrire une méthode qui permet de supprimer une entreprise
	}

}
