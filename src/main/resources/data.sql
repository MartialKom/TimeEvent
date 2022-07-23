Drop table if exists utilisateur;

create table utilisateur (id int AUTO_INCREMENT primary key,
first_name varchar(250) not null,
last_name varchar(250) not null,
mail varchar(250) not null,
mdp varchar(250) not null);

Drop table if exists evenement;
create table evenement (id int AUTO_INCREMENT primary key,
titre varchar(250) not null,
descriptionEvenement varchar(255) DEFAULT null,
dateE varchar(15) not null,
id_utilisateur int not null);

