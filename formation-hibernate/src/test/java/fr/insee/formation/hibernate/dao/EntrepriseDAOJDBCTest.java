package fr.insee.formation.hibernate.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.insee.formation.hibernate.model.Entreprise;
import fr.insee.formation.hibernate.model.TypeVoie;

/**
 * Classe de test pour le TP1
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring-test-datasource-jdbc.xml" })
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class EntrepriseDAOJDBCTest {

	@Autowired
	private EntrepriseDAO entrepriseDAO;

	@Test
	public void testFindAllOrderByDateCreation() {

		List<Entreprise> entreprises = entrepriseDAO.findAllOrderByDateCreation();

		assertEquals("Il doit y avoir 3 entreprises", 3, entreprises.size());

		Entreprise entreprise1 = entreprises.get(0);

		assertEquals(entreprise1.getId(), 3);

		assertEquals("Le p'tit nantais", entreprise1.getDenomination());

		assertEquals("Alfred Alfred", entreprise1.getAdresse().getNomVoie());

		assertEquals("345678912", entreprise1.getSiren());

		Entreprise entreprise3 = entreprises.get(2);

		assertEquals(entreprise3.getId(), 2);

		assertEquals("Confiserie", entreprise3.getDenomination());

		assertEquals("RENNES", entreprise3.getAdresse().getVille());

		assertEquals(TypeVoie.BOULEVARD, entreprise3.getAdresse().getTypeVoie());

	}

}
