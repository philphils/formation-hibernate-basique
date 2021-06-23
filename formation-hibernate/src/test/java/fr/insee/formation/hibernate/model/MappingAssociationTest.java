package fr.insee.formation.hibernate.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.insee.formation.hibernate.dao.SecteurDAO;
import fr.insee.formation.hibernate.util.JeuxTestUtil;

/**
 * Test pour le TP5
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring-test-datasource.xml", "/spring-core.xml" })
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class MappingAssociationTest {

	@Autowired
	private JeuxTestUtil jeuxTestUtil;

	@Autowired
	private SecteurDAO secteurDAO;

	@Before
	public void testMappingAssociation() {

		jeuxTestUtil.creerJeuxMappingAssociation();

	}

	@Test
	public void testSecteur() {

		Secteur secteur = secteurDAO.findByCodeNaf("1104Z");

		assertEquals("Il doit y avoir 3 entreprise", 3, secteur.getEntreprises().size());

		for (Entreprise entreprise : secteur.getEntreprises()) {

			assertEquals("l'entreprise doit avoir 12 declaration", 12, entreprise.getDeclarations().size());

		}

		assertEquals("Le secteur doit avoir 13 indices", 13, secteur.getIndices().size());

	}

}
