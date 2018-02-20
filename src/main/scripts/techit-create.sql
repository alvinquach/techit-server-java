use test;

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
        currentPriority integer,
        currentProgress integer,
        details varchar(255),
        endDate date,
        lastUpdated date,
        lastUpdatedTime varchar(255),
        location varchar(255),
        startDate date,
        startDateTime varchar(255),
        subject varchar(255),
        requester_id bigint,
        primary key (id)
    ) engine=InnoDB;

    create table ticketsXRefUsers (
       ticket_id bigint not null,
        user_id bigint not null
    ) engine=InnoDB;

    create table units (
       id bigint not null,
        name varchar(255) not null,
        primary key (id)
    ) engine=InnoDB;

    create table updates (
       id integer not null,
        ticket_id bigint,
        primary key (id)
    ) engine=InnoDB;

    create table users (
       id bigint not null,
        email varchar(255),
        enabled bit not null,
        firstName varchar(255) not null,
        lastName varchar(255) not null,
        password varchar(255) not null,
        phoneNumber varchar(255),
        status integer,
        username varchar(255) not null,
        unit_id bigint,
        primary key (id)
    ) engine=InnoDB;

    alter table users 
       add constraint UK_r43af9ap4edm43mmtq01oddj6 unique (username);

    alter table tickets 
       add constraint FKdp5i1hou98n2co3e49fffh9fp 
       foreign key (requester_id) 
       references users (id);

    alter table ticketsXRefUsers 
       add constraint FKmhfam8q9lwdwrgpm7n987pinr 
       foreign key (user_id) 
       references users (id);

    alter table ticketsXRefUsers 
       add constraint FKe0ouvikera975nv1gyjb3vq67 
       foreign key (ticket_id) 
       references tickets (id);

    alter table updates 
       add constraint FK3fnl74oyd1raon25v5lo3hyag 
       foreign key (ticket_id) 
       references tickets (id);

    alter table users 
       add constraint FKp2hfld4bhbwtakwrmt4xq6een 
       foreign key (unit_id) 
       references units (id);
       
insert into units values(1, 'cs');
insert into units values(2, 'me');

insert into users values (1, 'test@calstatela.edu',1, 'Andrew', 'Garcia', sha2('abcd', 256),
'323-224-5678', 3, 'amgarcia', 1);

insert into users values (2, 'test@gmail.com',1, 'Rick', 'Sanchez', sha2('hello', 256),
'626-234-9999', 1, 'rsanchez', 1);

insert into users values (3, 'hello@gmail.com',1, 'Morty', 'Sanchez', sha2('test', 256),
'562-234-9876', 2, 'msanchez', 1);