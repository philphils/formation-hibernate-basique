package fr.insee.formation.hibernate.dao;


import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import fr.insee.formation.hibernate.model.Entreprise;
import fr.insee.formation.hibernate.model.TypeVoie;

/**
 * Classe de test pour le TP1
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "/spring-test-datasource-jdbc.xml" })
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class EntrepriseDAOJDBCTest {

	@Autowired
	private EntrepriseDAO entrepriseDAO;

	@Test
	public void testFindAllOrderByDateCreation() {

		List<Entreprise> entreprises = entrepriseDAO.findAllOrderByDateCreation();

		assertEquals(3, entreprises.size(), "Il doit y avoir 3 entreprises");

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
