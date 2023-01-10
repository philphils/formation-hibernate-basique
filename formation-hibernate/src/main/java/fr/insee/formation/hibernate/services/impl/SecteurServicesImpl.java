package fr.insee.formation.hibernate.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.insee.formation.hibernate.dao.SecteurDAO;
import fr.insee.formation.hibernate.model.Secteur;
import fr.insee.formation.hibernate.services.SecteurServices;
import jakarta.transaction.Transactional;

@Service
public class SecteurServicesImpl implements SecteurServices {

    @Autowired
    private SecteurDAO secteurDAO;

    @Override
    @Transactional
    public Secteur calculerIndicesSecteurByCodeNaf(String codeNaf) {

        Secteur secteur = secteurDAO.findByCodeNaf(codeNaf);

        return calculerIndiceSecteur(secteur);
    }

    private Secteur calculerIndiceSecteur(Secteur secteur) {

        // TODO TP6 Réaliser la méthode de calcul des indices mensuels et annuels,
        // observer avec le debugger les requêtes qui sont générées. Seul le secteur est récupéré plus haut
        // avec la méthode secteurDAO.findByCodeNaf(codeNaf)

        // On part du principe que les Indices d'un secteur sont déjà créés et que leur valeur est initialisée à 0. Pas de création d'indice à faire donc.
        // La valeur de chaque Indice doit correspondre simplement à la somme des valeurs des déclarations pour la période concernée.
        // Ex : l'indice mensuel du mois d'août 2016 pour un certain secteur doit valoir la somme des montants déclarés par les entreprises de ce secteur pour
        // ce mois.
        // Idem pour les Indices annuels mais la période est l'année

        // Petite aide, code pour obtenir le Year et le YearMonth à partir de la date de la déclaration :
        // Year.from(declaration.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
        // YearMonth.from(declaration.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())

        return secteur;
    }

    @Override
    @Transactional
    public Secteur calculerIndicesSecteurByCodeNafRequeteJPQL(String codeNaf) {

        Secteur secteur = secteurDAO.findByCodeNafWithEntreprisesAndDeclarationAndIndicesJPQL(codeNaf);

        return calculerIndiceSecteur(secteur);
    }

    @Override
    @Transactional
    public Secteur calculerIndicesSecteurByCodeNafRequeteCriteria(String codeNaf) {

        Secteur secteur = secteurDAO.findByCodeNafWithEntreprisesAndDeclarationAndIndicesCriteria(codeNaf);

        return calculerIndiceSecteur(secteur);

    }

}
