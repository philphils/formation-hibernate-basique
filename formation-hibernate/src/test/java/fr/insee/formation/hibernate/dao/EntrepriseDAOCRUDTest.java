package fr.insee.formation.hibernate.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import fr.insee.formation.hibernate.model.Entreprise;
import fr.insee.formation.hibernate.util.JeuxTestUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "/spring-test-datasource.xml", "/spring-core.xml" })
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class EntrepriseDAOCRUDTest {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private EntrepriseDAO entrepriseDAO;

	@Autowired
	private JeuxTestUtil jeuxTestUtil;

	@Value("${fr.insee.formation.hibernate.schema}")
	private String schema;

	@BeforeEach
	public void init() {

		jeuxTestUtil.insererJeuxVolumeReduit();

	}

	@Test
	public void testInsert() {

		Entreprise entreprise = new Entreprise();

		entreprise.setDenomination("Nouvelle entreprise à créer");

		entreprise.setSiren("000000000");

		entrepriseDAO.persist(entreprise);

		List<Entreprise> entreprises = entityManager.createQuery("SELECT ent FROM Entreprise ent", Entreprise.class).getResultList();

		assertEquals(4, entreprises.size(), "Il doit y avoir 4 entreprises");

	}

	@Test
	public void testUpdate() {

		entrepriseDAO.upperDenomination();

		Entreprise entreprise = entityManager.find(Entreprise.class, 2);

		assertEquals(entreprise.getDenomination().toUpperCase(), entreprise.getDenomination(), "La dénomination doit être en majuscule");

	}

	@Test
	public void testRemove() {

		entrepriseDAO.removeEntrepriseById(3);

		List<Entreprise> entreprises = entityManager.createQuery("SELECT ent FROM Entreprise ent", Entreprise.class).getResultList();

		assertEquals(2, entreprises.size(), "Il doit y avoir 2 entreprises");
	}

}
