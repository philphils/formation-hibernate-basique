package fr.insee.formation.hibernate.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.insee.formation.hibernate.model.Entreprise;
import fr.insee.formation.hibernate.util.JeuxTestUtil;

@RunWith(SpringJUnit4ClassRunner.class)
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

	@Before
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

		assertEquals("Il doit y avoir 4 entreprises", 4, entreprises.size());

	}

	@Test
	public void testUpdate() {

		entrepriseDAO.upperDenomination();

		Entreprise entreprise = entityManager.find(Entreprise.class, 2);

		assertEquals("La dénomination doit être en majuscule", entreprise.getDenomination().toUpperCase(), entreprise.getDenomination());

	}

	@Test
	public void testRemove() {

		entrepriseDAO.removeEntrepriseById(3);

		List<Entreprise> entreprises = entityManager.createQuery("SELECT ent FROM Entreprise ent", Entreprise.class).getResultList();

		assertEquals("Il doit y avoir 2 entreprises", 2, entreprises.size());
	}

}
