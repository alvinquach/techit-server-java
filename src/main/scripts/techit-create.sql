use techit2;

    create table hibernate_sequence (
       next_val bigint
    ) engine=InnoDB;

    insert into hibernate_sequence values ( 1 );

    insert into hibernate_sequence values ( 1 );

    insert into hibernate_sequence values ( 1 );

    insert into hibernate_sequence values ( 1 );

    create table tickets (
       id bigint not null,
        completionDetails longtext,
        currentPriority integer not null,
        currentProgress integer not null,
        details longtext,
        endDate date,
        lastUpdated date,
        lastUpdatedTime varchar(255),
        location varchar(255),
        startDate date,
        startDateTime varchar(255),
        subject varchar(255),
        requestorId bigint not null,
        unitId bigint,
        primary key (id)
    ) engine=InnoDB;

    create table tickets_xref_users (
       ticketId bigint not null,
        userId bigint not null
    ) engine=InnoDB;

    create table units (
       id bigint not null,
        name varchar(255) not null,
        primary key (id)
    ) engine=InnoDB;

    create table updates (
       id bigint not null,
        modifiedDate date not null,
        updateDetails longtext,
        modifiedById bigint not null,
        ticketId bigint not null,
        primary key (id)
    ) engine=InnoDB;

    create table users (
       id bigint not null,
        department varchar(255),
        email varchar(255),
        enabled bit not null,
        firstName varchar(255) not null,
        lastName varchar(255) not null,
        password varchar(255) not null,
        phoneNumber varchar(255),
        position integer not null,
        username varchar(255) not null,
        unitId bigint,
        primary key (id)
    ) engine=InnoDB;

    alter table users 
       add constraint UK_r43af9ap4edm43mmtq01oddj6 unique (username);

    alter table tickets 
       add constraint FK2kslelauh037pxdpkwh94iixa 
       foreign key (requestorId) 
       references users (id);

    alter table tickets 
       add constraint FKmyghb1ptw4f8ogabfyqthx1u3 
       foreign key (unitId) 
       references units (id);

    alter table tickets_xref_users 
       add constraint FK9ni8cyqa5kr1wtpdg6xtla7o5 
       foreign key (userId) 
       references users (id);

    alter table tickets_xref_users 
       add constraint FKp59p9e6jhd42tsyasxqup7d38 
       foreign key (ticketId) 
       references tickets (id);

    alter table updates 
       add constraint FKdys2c21lpn507ur3r8gk8n0h5 
       foreign key (modifiedById) 
       references users (id);

    alter table updates 
       add constraint FKjl7d0xf7smhs4oib7otx250w0 
       foreign key (ticketId) 
       references tickets (id);

    alter table users 
       add constraint FK1gdvehntuq847hrr9m2y6csln 
       foreign key (unitId) 
       references units (id);
       
insert into units values (1, 'cs');
insert into units values (2, 'me');

insert into users values (1, 'Test', 'test@calstatela.edu', 1, 'Andrew', 'Garcia', sha2('password', 256), '323-224-5678', 3, 'amgarcia', 1);
insert into users values (2, 'Test', 'test@gmail.com', 1, 'Rick', 'Sanchez', sha2('password', 256), '626-234-9999', 1, 'rsanchez', 1);
insert into users values (3, 'Test', 'hello@gmail.com', 1, 'Morty', 'Sanchez', sha2('password', 256), '562-234-9876', 2, 'msanchez', 1);
insert into users values (4, 'Test', 'mshakibi@techit.com', 1, 'mahdi', 'shakibi', sha2('password', 256), '626-417-3378', 2, 'mshakibi', 1);
insert into users values (5, 'Test', 'admin@techit.com', 1, 'Local', 'Admin', sha2('password', 256), '222-222-2222', 0, 'admin', 1);
insert into users values (6, 'Test', 'technician2@techit.com', 1, 'joe', 'cota', sha2('password', 256), '333-333-3333', 2, 'jcota', 1);


insert into tickets values (1, 'completionDetails', 1, 1, 'details', '2009-09-22', '2009-09-22', 'lastUpdatedTime', 'location:', '2009-09-22', 'startDateTime', 'subject', 1, 1);
insert into tickets values (2, 'completionDetails2', 2, 2, 'details2', '2017-09-22', '2017-09-22', 'lastUpdatedTime2', 'location2', '2017-09-22', 'startDateTime2', 'subject2', 2, 1);
insert into tickets values (3, 'completionDetails3', 2, 2, 'details3', '2018-01-22', '2018-01-22', 'lastUpdatedTime2', 'Location3', '2018-01-22', 'startDateTime3', 'subject3', 2, 2);


insert into updates (`id`, `modifiedDate`, `updateDetails`, `modifiedById`, `ticketId`) values ('1', '2018-02-10', 'details of stuff', '1', '1');
insert into updates (`id`, `modifiedDate`, `updateDetails`, `modifiedById`, `ticketId`) values ('2', '2018-11-23', 'stuff is in the details', '2', '2');
insert into updates (`id`, `modifiedDate`, `updateDetails`, `modifiedById`, `ticketId`) values ('3', '2018-12-13', 'there are some detailed stuff', '2', '1');



