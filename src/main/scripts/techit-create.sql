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
        completionDetails varchar(255),
        currentPriority integer not null,
        currentProgress integer not null,
        details varchar(255),
        endDate date,
        lastUpdated date,
        lastUpdatedTime varchar(255),
        location varchar(255),
        startDate date,
        startDateTime varchar(255),
        subject varchar(255),
        requesterId bigint not null,
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
        updateDetails varchar(255),
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
        status integer not null,
        username varchar(255) not null,
        unitId bigint,
        primary key (id)
    ) engine=InnoDB;

    alter table users 
       add constraint UK_r43af9ap4edm43mmtq01oddj6 unique (username);

    alter table tickets 
       add constraint FK2ibmglwc67bynljpqydbaej4e 
       foreign key (requesterId) 
       references users (id);

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
       
insert into units values(1, 'cs');
insert into units values(2, 'me');

insert into users values (1, 'Test', 'test@calstatela.edu',1, 'Andrew', 'Garcia', sha2('abcd', 256),
'323-224-5678', 3, 'amgarcia', 1);

insert into users values (2, 'Test', 'test@gmail.com',1, 'Rick', 'Sanchez', sha2('hello', 256),
'626-234-9999', 1, 'rsanchez', 1);

insert into users values (3, 'Test', 'hello@gmail.com',1, 'Morty', 'Sanchez', sha2('test', 256),
'562-234-9876', 2, 'msanchez', 1);