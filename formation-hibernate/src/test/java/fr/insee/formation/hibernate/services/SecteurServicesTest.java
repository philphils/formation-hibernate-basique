package fr.insee.formation.hibernate.services;

import static org.junit.Assert.assertEquals;

import java.time.Month;
import java.time.Year;
import java.time.YearMonth;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import fr.insee.formation.hibernate.dao.SecteurDAO;
import fr.insee.formation.hibernate.model.Secteur;
import fr.insee.formation.hibernate.util.JeuxTestUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring-test-datasource.xml", "/spring-core.xml" })
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class SecteurServicesTest {

	@Autowired
	private JeuxTestUtil jeuxTestUtil;

	@Autowired
	private SecteurServices secteurServices;

	@Autowired
	private SecteurDAO secteurDAO;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Before
	public void testMappingAssociation() {
		jeuxTestUtil.creerJeuxMappingAssociation();
	}

	@Test
	public void testCalculerIndices() {

		secteurServices.calculerIndicesSecteurByCodeNaf("1104Z");

		transactionTemplate.execute(new TransactionCallback<Object>() {

			public Object doInTransaction(TransactionStatus arg0) {

				Secteur secteur = secteurDAO.findByCodeNaf("1104Z");

				assertEquals("L'indice annuel de 2016 doit valoir 1170", new Double(468), secteur.getIndicesAnnuels().get(Year.parse("2016")).getValeur());

				assertEquals("L'indice mensuel de décembre 2016 doit valoir 180", new Double(72),
		                secteur.getIndicesMensuels().get(YearMonth.of(2016, Month.DECEMBER)).getValeur());

				return null;
			}
		});

	}

}
