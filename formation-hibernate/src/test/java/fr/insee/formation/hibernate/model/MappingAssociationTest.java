package fr.insee.formation.hibernate.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import fr.insee.formation.hibernate.dao.SecteurDAO;
import fr.insee.formation.hibernate.util.JeuxTestUtil;

/**
 * Test pour le TP5
 *
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "/spring-test-datasource.xml", "/spring-core.xml" })
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class MappingAssociationTest {

	@Autowired
	private JeuxTestUtil jeuxTestUtil;

	@Autowired
	private SecteurDAO secteurDAO;

	@BeforeAll
	public void testMappingAssociation() {

		jeuxTestUtil.creerJeuxMappingAssociation();

	}

	@Test
	public void testSecteur() {

		Secteur secteur = secteurDAO.findByCodeNaf("1104Z");

		assertEquals(3, secteur.getEntreprises().size(), "Il doit y avoir 3 entreprise");

		for (Entreprise entreprise : secteur.getEntreprises()) {

			assertEquals(12, entreprise.getDeclarations().size(), "l'entreprise doit avoir 12 declaration");

		}

		assertEquals(13, secteur.getIndices().size(), "Le secteur doit avoir 13 indices");

	}

}
