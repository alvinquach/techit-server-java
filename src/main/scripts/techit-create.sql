use techit2;

    create table hibernate_sequence (
       next_val bigint
    ) engine=InnoDB;

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
        hash varchar(60) not null,
        lastName varchar(255) not null,
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