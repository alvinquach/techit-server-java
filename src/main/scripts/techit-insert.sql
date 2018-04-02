insert into units values (1, 'cs');
insert into units values (2, 'me');

# This user is used in at least one of the unit tests. Do not modify unless necessary.
# Password: 'abcd'
insert into users (`id`, `department`, `email`, `unitId`, `firstName`, `lastName`, `hash`, `phoneNumber`, `position`, `username`, `enabled`) values
	(1, 'Test', 'admin@techit.com', 1, 'Techit', 'Admin', '$2a$10$Xm5I2iYA/4vZytuGpVIro.zUAHGG0eAAcY2.aX20kRcM8u7AEmFom', '696-969-6969', 0, 'techit', 1);
    
# This user is used in at least one of the unit tests. Do not modify unless necessary.
# Password: 'abcd'
insert into users (`id`, `department`, `email`, `unitId`, `firstName`, `lastName`, `hash`, `phoneNumber`, `position`, `username`, `enabled`) values
    (2, 'Test', 'test@calstatela.edu', 1, 'Andrew', 'Garcia', '$2a$10$Xm5I2iYA/4vZytuGpVIro.zUAHGG0eAAcY2.aX20kRcM8u7AEmFom', '323-224-5678', 3, 'amgarcia', 1);

# Password: 'abcd'
insert into users (`id`, `department`, `email`, `unitId`, `firstName`, `lastName`, `hash`, `phoneNumber`, `position`, `username`, `enabled`) values
	(3, 'Test', 'test@gmail.com', 1, 'Rick', 'Sanchez', '$2a$10$Xm5I2iYA/4vZytuGpVIro.zUAHGG0eAAcY2.aX20kRcM8u7AEmFom', '626-234-9999', 1, 'rsanchez', 1);
  
# Password: 'abcd'  
insert into users (`id`, `department`, `email`, `unitId`, `firstName`, `lastName`, `hash`, `phoneNumber`, `position`, `username`, `enabled`) values
	(4, 'Test', 'hello@gmail.com', 1, 'Morty', 'Sanchez', '$2a$10$Xm5I2iYA/4vZytuGpVIro.zUAHGG0eAAcY2.aX20kRcM8u7AEmFom', '562-234-9876', 2, 'msanchez', 1);

# Password: 'abcd'
insert into users (`id`, `department`, `email`, `unitId`, `firstName`, `lastName`, `hash`, `phoneNumber`, `position`, `username`, `enabled`) values
	(5, 'Test', 'mshakibi@techit.com', 1, 'Mahdi', 'Shakibi', '$2a$10$Xm5I2iYA/4vZytuGpVIro.zUAHGG0eAAcY2.aX20kRcM8u7AEmFom', '626-417-3378', 2, 'mshakibi', 1);

# Password: 'abcd'
insert into users (`id`, `department`, `email`, `unitId`, `firstName`, `lastName`, `hash`, `phoneNumber`, `position`, `username`, `enabled`) values
	(6, 'Test', 'technician2@techit.com', 1, 'Joe', 'Cota', '$2a$10$Xm5I2iYA/4vZytuGpVIro.zUAHGG0eAAcY2.aX20kRcM8u7AEmFom', '333-333-3333', 2, 'jcota', 1);

# Password: 'abcd'
insert into users (`id`, `department`, `email`, `unitId`, `firstName`, `lastName`, `hash`, `phoneNumber`, `position`, `username`, `enabled`) values
	(7, 'Test', 'tech@techit.com', 1, 'Jane', 'Doe', '$2a$10$Xm5I2iYA/4vZytuGpVIro.zUAHGG0eAAcY2.aX20kRcM8u7AEmFom', '444-444-4444', 3, 'JDoe', 1);


insert into tickets values (1, 'completionDetails', 1, 1, 'details', '2009-09-22', '2009-09-22', 'lastUpdatedTime', 'location:', '2009-09-22', 'startDateTime', 'subject', 1, 1);
insert into tickets values (2, 'completionDetails2', 2, 2, 'details2', '2017-09-22', '2017-09-22', 'lastUpdatedTime2', 'location2', '2017-09-22', 'startDateTime2', 'subject2', 2, 1);
insert into tickets values (3, 'completionDetails3', 2, 2, 'details3', '2018-01-22', '2018-01-22', 'lastUpdatedTime2', 'Location3', '2018-01-22', 'startDateTime3', 'subject3', 2, 2);


insert into updates (`id`, `modifiedDate`, `updateDetails`, `modifiedById`, `ticketId`) values ('1', '2018-02-10', 'details of stuff', '1', '1');
insert into updates (`id`, `modifiedDate`, `updateDetails`, `modifiedById`, `ticketId`) values ('2', '2018-11-23', 'stuff is in the details', '2', '2');
insert into updates (`id`, `modifiedDate`, `updateDetails`, `modifiedById`, `ticketId`) values ('3', '2018-12-13', 'there are some detailed stuff', '2', '1');

insert into tickets_xref_users (ticketId, userId) values (1, 1);
insert into tickets_xref_users (ticketId, userId) values (2, 7);
insert into tickets_xref_users (ticketId, userId) values (3, 1);
insert into tickets_xref_users (ticketId, userId) values (3, 7);