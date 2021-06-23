create sequence test.hibernate_sequence start 1 increment 1;

    create table test.Declaration (
       id int4 not null,
        date timestamp,
        montant float8,
        primary key (id)
    );

    create table test.Entreprise (
       id int4 not null,
        rue varchar(255),
        numero varchar(255),
        pays varchar(255),
        typeVoie varchar(255),
        ville varchar(255),
        dateCreation date,
        denomination varchar(255),
        formeJuridique varchar(255),
        siren varchar(9) not null,
        telephone varchar(10),
        primary key (id)
    );

    create table test.Secteur (
       id int4 not null,
        codeNaf varchar(255),
        libelleNomenclature varchar(255),
        primary key (id)
    );

    alter table test.Entreprise 
       add constraint UK_ep28ukm4mifb7h3pelb50fj83 unique (siren);
