# We do not have to manually specify the primary key IDs when inserting rows into the tables,
# but we do so anyways in this script so that we can easily reference rows from other tables.

insert into units values (1, 'cs');
insert into units values (2, 'me');

# This user is used in at least one of the unit tests. Do not modify unless necessary.
# Password: 'abcd'
insert into users (id, username, hash, department, unitId, firstName, lastName, email, phoneNumber, position, enabled) values
    (1, 'techit', '$2a$10$Xm5I2iYA/4vZytuGpVIro.zUAHGG0eAAcY2.aX20kRcM8u7AEmFom', 'Test', 1, 'Techit', 'Admin', 'admin@techit.com', '696-969-6969', 0, 1);
    
# This user is used in at least one of the unit tests. Do not modify unless necessary.
# Password: 'abcd'
insert into users (id, username, hash, department, unitId, firstName, lastName, email, phoneNumber, position, enabled) values
    (2, 'amgarcia', '$2a$10$Xm5I2iYA/4vZytuGpVIro.zUAHGG0eAAcY2.aX20kRcM8u7AEmFom', 'Test', 1, 'Andrew', 'Garcia', 'test@techit.com', '323-224-5678', 2, 1);

# This user is used one of the tickets below. Do not delete.
# Password: 'abcd'
insert into users (id, username, hash, department, unitId, firstName, lastName, email, phoneNumber, position, enabled) values
	(3, 'rsanchez', '$2a$10$Xm5I2iYA/4vZytuGpVIro.zUAHGG0eAAcY2.aX20kRcM8u7AEmFom', 'Test', 1, 'Rick', 'Sanchez', 'test@gmail.com', '626-234-9999', 2, 1);
  
# Password: 'abcd'  
insert into users (id, username, hash, department, unitId, firstName, lastName, email, phoneNumber, position, enabled) values
    (4, 'msanchez', '$2a$10$Xm5I2iYA/4vZytuGpVIro.zUAHGG0eAAcY2.aX20kRcM8u7AEmFom', 'Test', 1, 'Morty', 'Sanchez', 'hello@gmail.com', '562-234-9876', 2, 1);

# Password: 'abcd'
insert into users (id, username, hash, department, unitId, firstName, lastName, email, phoneNumber, position, enabled) values
	(5, 'mshakibi', '$2a$10$Xm5I2iYA/4vZytuGpVIro.zUAHGG0eAAcY2.aX20kRcM8u7AEmFom', 'Test', 1, 'Mahdi', 'Shakibi', 'mshakibi@techit.com', '626-417-3378', 3, 1);

# Password: 'abcd'
insert into users (id, username, hash, department, unitId, firstName, lastName, email, phoneNumber, position, enabled) values
	(6, 'jcota', '$2a$10$Xm5I2iYA/4vZytuGpVIro.zUAHGG0eAAcY2.aX20kRcM8u7AEmFom', 'Joe', 1, 'Cota', 'Sanchez', 'jcota@techit.com', '333-333-3333', 3, 1);

# Password: 'abcd'
insert into users (id, username, hash, department, unitId, firstName, lastName, email, phoneNumber, position, enabled) values
	(7, 'jdoe', '$2a$10$Xm5I2iYA/4vZytuGpVIro.zUAHGG0eAAcY2.aX20kRcM8u7AEmFom', 'Test', 1, 'Jane', 'Doe', 'jdoe@techit.com', '444-444-44449', 3, 1);

# A completed ticket.
insert into tickets (id, status, priority, subject, details, location, unitId, createdById, createdDate, startDate, lastUpdated, endDate, completionDetails) values
	(1, 3, 1, 'Test Ticket 1', 'This ticket should be completed.', 'Library 4th Floor', 1, 2, '2017-06-14', '2018-01-02', '2018-04-02', '2018-04-05', 'This was completed after a few months.');

# An in-progress ticket.
insert into tickets (id, status, priority, subject, details, location, unitId, createdById, createdDate, startDate, lastUpdated) values
	(2, 1, 0, 'Test Ticket 2', 'Some description', 'E&T A309', 1, 3, '2018-02-14', '2018-03-02', '2018-03-05');

# An open ticket.
insert into tickets (id, status, priority, subject, details, location, unitId, createdById, createdDate) values
	(3, 0, 0, 'Test Ticket 3', 'Some description', 'Salazar Hall Entrance', 2, 3, '2018-02-14');

insert into updates (modifiedDate, updateDetails, modifiedById, ticketId) values ('2018-02-10', 'details of stuff', 1, 1);
insert into updates (modifiedDate, updateDetails, modifiedById, ticketId) values ('2018-11-23', 'stuff is in the details', 2, 2);
insert into updates (modifiedDate, updateDetails, modifiedById, ticketId) values ('2018-12-13', 'there are some detailed stuff', 2, 1);

insert into tickets_xref_technicians (ticketId, technicianId) values (1, 1);
insert into tickets_xref_technicians (ticketId, technicianId) values (2, 7);
insert into tickets_xref_technicians (ticketId, technicianId) values (3, 1);
insert into tickets_xref_technicians (ticketId, technicianId) values (3, 7);