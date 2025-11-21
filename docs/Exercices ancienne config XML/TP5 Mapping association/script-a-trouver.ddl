create sequence test.hibernate_sequence start 1 increment 1;

    create table test.Declaration (
       id int4 not null,
        date timestamp,
        montant float8,
        entreprise_id int4,
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
        secteur_id int4,
        primary key (id)
    );

    create table test.Indice (
       id int4 not null,
        derniereMaj timestamp,
        valeur float8,
        secteur_id int4,
        primary key (id)
    );

    create table test.IndiceAnnuel (
       year timestamp,
        id int4 not null,
        primary key (id)
    );

    create table test.IndiceMensuel (
       month timestamp,
        id int4 not null,
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

    alter table test.Declaration 
       add constraint FK90tkl56kk5ijv7ctibus032a0 
       foreign key (entreprise_id) 
       references test.Entreprise;

    alter table test.Entreprise 
       add constraint FKe6dwh12l8bd8ahuputqypvm1d 
       foreign key (secteur_id) 
       references test.Secteur;

    alter table test.Indice 
       add constraint FK5w5thq9ylbut3h9y111aexct5 
       foreign key (secteur_id) 
       references test.Secteur;

    alter table test.IndiceAnnuel 
       add constraint FK11u2aoco7d7cd6t8d73c8dcry 
       foreign key (id) 
       references test.Indice;

    alter table test.IndiceMensuel 
       add constraint FKlncp5r8qwpolm0hycoefpmfe4 
       foreign key (id) 
       references test.Indice;
