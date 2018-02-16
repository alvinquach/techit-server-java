create table hibernate_sequence (
    next_val bigint
);

insert into hibernate_sequence values ( 100 );

create table users (
    id          bigint primary key,
    username    varchar(255) unique not null,
    password    varchar(255) not null,
    enabled bit not null default 1
);

insert into users (id, username, password) values (1, 'admin', 'abcd');
insert into users (id, username, password) values (2, 'cysun', 'abcd');
insert into users (id, username, password) values ('root', sha2('admin',256));


    create table Assignments (
       ticketId integer not null,
        technicianUser varchar(255) not null,
        primary key (ticketId)
    );

    create table Ticket (
       id integer not null,
        completionDetails varchar(255),
        currentPriority integer,
        currentProgress integer,
        department varchar(255),
        details varchar(255) not null,
        email varchar(255) not null,
        endDate date,
        lastUpdated date,
        lastUpdatedTime varchar(255),
        phone varchar(255) not null,
        startDate date,
        startDateTime varchar(255),
        subject varchar(255) not null,
        ticketLocation varchar(255) not null,
        unitId integer not null,
        userFirstName varchar(255) not null,
        userLastName varchar(255) not null,
        username varchar(255) not null,
        primary key (id)
    );

    create table Ticket_Update (
       Ticket_id integer not null,
        updates_id integer not null
    ) engine=InnoDB;

    create table Ticket_users (
       Ticket_id integer not null,
        technicians_id bigint not null
    ) engine=InnoDB;

    create table Unit (
       id integer not null,
        description varchar(255) not null,
        email varchar(255) not null,
        location varchar(255) not null,
        name varchar(255) not null,
        phone varchar(255) not null,
        primary key (id)
    );

    insert into units (name, description) values ('TechOPs', concat_ws(' ',
    'Technical Operations, or TechOps,  is a unit in the ECST College.'
    'TechOps runs the Hydrogen Station, and provides technical assistance to',
    'the ECST departments such as creating and replacing part for senior',
    'design project.'));

    create table Update (
       id integer not null,
        modifiedDate varchar(255) not null,
        modifier varchar(255) not null,
        ticketId integer not null,
        updateDetails varchar(255),
        primary key (id)
    );

    create table users (
       id bigint not null,
        enabled bit not null,
        password varchar(255) not null,
        username varchar(255) not null,
        primary key (id)
    );

    alter table Ticket_Update 
       add constraint UK_sphpbmusprst9t0aexokmj487 unique (updates_id);

    alter table Ticket_users 
       add constraint UK_3i944f4mw10vdo3tmcuk24ldu unique (technicians_id);

    alter table users 
       add constraint UK_r43af9ap4edm43mmtq01oddj6 unique (username);

    alter table Ticket_Update 
       add constraint FKvhtng4rdf8db0afa8s4jef1d 
       foreign key (updates_id) 
       references Update (id);

    alter table Ticket_Update 
       add constraint FKmys7nb2i4q5tuqugha6hhmhih 
       foreign key (Ticket_id) 
       references Ticket (id);

    alter table Ticket_users 
       add constraint FKb6fc5yp1cfi7l935nd5vod8nn 
       foreign key (technicians_id) 
       references users (id);

    alter table Ticket_users 
       add constraint FKb5wik8f6ypopcwhe7l3iccrx9 
       foreign key (Ticket_id) 
       references Ticket (id);

