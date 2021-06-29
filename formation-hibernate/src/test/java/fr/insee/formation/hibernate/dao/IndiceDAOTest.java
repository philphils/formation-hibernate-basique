package fr.insee.formation.hibernate.dao;

import static org.junit.Assert.assertEquals;

import java.time.Month;
import java.time.YearMonth;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.insee.formation.hibernate.model.Indice;
import fr.insee.formation.hibernate.model.IndiceAnnuel;
import fr.insee.formation.hibernate.model.IndiceMensuel;
import fr.insee.formation.hibernate.util.JeuxTestUtil;

/**
 * Test pour le TP4 mapping de l'héritage
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring-test-datasource.xml", "/spring-core.xml" })
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class IndiceDAOTest {

	@Autowired
	private IndiceDAO indiceDAO;

	@Autowired
	private JeuxTestUtil jeuxTestUtil;

	@Test
	public void testMappingIndices() {

		int identifiant1 = jeuxTestUtil.creerIndiceAnnuel().getId();

		int indentifiant2 = jeuxTestUtil.creerIndiceMensuel().getId();

		IndiceAnnuel indiceAnnuel = indiceDAO.findIndiceAnnuel(identifiant1);

		assertEquals("L'indice vaut 10", Double.valueOf(10), indiceAnnuel.getValeur());

		assertEquals("L'annuel est 2017", "2017", indiceAnnuel.getYear().toString());

		IndiceMensuel indiceMensuel = indiceDAO.findIndiceMensuel(indentifiant2);

		assertEquals("Le mois doit être decemnbre", YearMonth.of(2017, Month.DECEMBER), indiceMensuel.getMonth());

		assertEquals("L'indice doit valoir 10.4", Double.valueOf(10.4d), indiceMensuel.getValeur());

		Set<Indice> indices = indiceDAO.findAll();

		assertEquals("On doit récupérer les 2 indices", 2, indices.size());

	}

}
