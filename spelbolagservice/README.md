# Spelbolag tjänsten
Har några CRUD operationer för att kunna spara och uppdatera TODO's.


# Bygga
Tjänsten är en REST-tjänst som körs i en Express server med Node.js version 12.

> npm start

http://localhost:4001/api/v1/spelbolag/5

Vid utveckling så startas tjänsten i development mode med hotreload/watch.

> npm run dev


# Testning
* Använder Jest, https://basarat.gitbooks.io/typescript/content/docs/testing/jest.html
* Mockar Request och Response med https://www.npmjs.com/package/node-mocks-http.
* npm i jest @types/jest ts-jest -D
* Utför tester med en uppstartad MySQL databas, Det behöver finnas lite data i databasen.

``INSERT INTO `stryktipsbolag`.`transaktion` (`ID`,`BESKRIVNING`,`DEBET`,`KREDIT`,`TID`) VALUES (1, 'Spelar stryktipset', 0, 50,CURRENT_TIMESTAMP); ``
``INSERT INTO `stryktipsbolag`.`transaktion` (`ID`,`BESKRIVNING`,`DEBET`,`KREDIT`,`TID`) VALUES (2, 'Spelar stryktipset', 0, 25,CURRENT_TIMESTAMP); ``
``INSERT INTO `stryktipsbolag`.`transaktion` (`ID`,`BESKRIVNING`,`DEBET`,`KREDIT`,`TID`) VALUES (3, 'Spelar stryktipset', 0, 50,CURRENT_TIMESTAMP); ``
``INSERT INTO `stryktipsbolag`.`konto` (`ID`,`KONTONR`) VALUES (1, 234);``
``INSERT INTO `stryktipsbolag`.`konto` (`ID`,`KONTONR`) VALUES (2, 1967);``
``INSERT INTO `stryktipsbolag`.`konto` (`ID`,`KONTONR`) VALUES (3, 88);``
``INSERT INTO `stryktipsbolag`.`konto` (`ID`,`KONTONR`) VALUES (4, 89);``
``INSERT INTO `stryktipsbolag`.`konto` (`ID`,`KONTONR`) VALUES (5, 90);``
``INSERT INTO `stryktipsbolag`.`konto` (`ID`,`KONTONR`) VALUES (6, 201);``
``INSERT INTO `stryktipsbolag`.`konto_transaktion` (`KONTO_ID`,`TRANSAKTIONER_ID`) VALUES (1, 1);``
``INSERT INTO `stryktipsbolag`.`konto_transaktion` (`KONTO_ID`,`TRANSAKTIONER_ID`) VALUES (1, 2);``
``INSERT INTO `stryktipsbolag`.`konto_transaktion` (`KONTO_ID`,`TRANSAKTIONER_ID`) VALUES (2, 3);``
``INSERT INTO `stryktipsbolag`.`spelare` (`ID`, `userid`,`administratorforspelbolag_id`,`konto_id`) VALUES (1, 'robert.georen@gmail.com', 1, 1);``
``INSERT INTO `stryktipsbolag`.`spelare` (`ID`, `userid`,`administratorforspelbolag_id`,`konto_id`) VALUES (2, 'sune.mags@gmail.com', 0, 4);``
``INSERT INTO `stryktipsbolag`.`spelare` (`ID`, `userid`,`administratorforspelbolag_id`,`konto_id`) VALUES (3, 'hjalmar.branting@gmail.com', 0, 5);``
``INSERT INTO `stryktipsbolag`.`spelbolag` (`ID`,`insatsperomgang`,`namn`,`konto_id`) VALUES (1, 50, 'The gamblers', 3);``
``INSERT INTO `stryktipsbolag`.`spelbolag` (`ID`,`insatsperomgang`,`namn`,`konto_id`) VALUES (2, 25, 'Lucky boys', 6);``
``INSERT INTO `stryktipsbolag`.`spelbolag_spelare` (`spelare_id`,`spelbolag_id`) VALUES (1, 1);``
``INSERT INTO `stryktipsbolag`.`spelbolag_spelare` (`spelare_id`,`spelbolag_id`) VALUES (2, 1);``
``INSERT INTO `stryktipsbolag`.`spelbolag_spelare` (`spelare_id`,`spelbolag_id`) VALUES (3, 1);``

Starta testning med .
> npm test
>
# Databas
MySQL 5.7.26 används.  
Den startas med
> mysqld --console.  

Logga in i databasen.
> mysql -u root -p  

MySQL Workbench 8.0 används som GUI mot databasen. 

Skapa en databas som heter spelbolag.

`` CREATE SCHEMA `stryktipsbolag;``

Tjänsten består av tabellerna transaktion,   
<pre>
CREATE TABLE `transaktion` (
  `ID` bigint(8) NOT NULL,
  `beskrivning` varchar(255) DEFAULT NULL,
  `debet` int(11) DEFAULT NULL,
  `kredit` int(11) DEFAULT NULL,
  `tid` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
</pre>

<pre>
CREATE TABLE `konto` (
  `ID` bigint(8) NOT NULL,
  `kontonr` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `ID_index` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
</pre>

<pre>
CREATE TABLE `konto_transaktion` (
  `konto_id` bigint(8) DEFAULT NULL,
  `transaktioner_id` bigint(8) DEFAULT NULL,
  KEY `transaktion_id_fk_idx` (`transaktioner_id`),
  KEY `konto_id_fk` (`konto_id`),
  CONSTRAINT `konto_id_fk` FOREIGN KEY (`konto_id`) REFERENCES `konto` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `transaktion_id_fk` FOREIGN KEY (`transaktioner_id`) REFERENCES `transaktion` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
</pre>

<pre>
CREATE TABLE `spelare` (
  `ID` bigint(8) NOT NULL,
  `userid` varchar(255) DEFAULT NULL,
  `administratorforspelbolag_id` bigint(8) DEFAULT NULL,
  `konto_id` bigint(8) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `fk_kontoid_idx` (`konto_id`),
  CONSTRAINT `fk_kontoid` FOREIGN KEY (`konto_id`) REFERENCES `konto` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
</pre>

<pre>
CREATE TABLE `spelbolag` (
  `ID` bigint(8) NOT NULL,
  `insatsperomgang` int(11) DEFAULT NULL,
  `namn` varchar(45) DEFAULT NULL,
  `konto_id` bigint(8) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
</pre>

<pre>
CREATE TABLE `spelbolag_spelare` (
  `spelare_id` bigint(8) NOT NULL,
  `spelbolag_id` bigint(8) NOT NULL,
  KEY `spelare_FK_idx` (`spelare_id`),
  KEY `spelbolag_FK_idx` (`spelbolag_id`),
  CONSTRAINT `spelare_FK` FOREIGN KEY (`spelare_id`) REFERENCES `spelare` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `spelbolag_FK` FOREIGN KEY (`spelbolag_id`) REFERENCES `spelbolag` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
</pre>
# Deploy
``cd /var/local/spelbolagservice``

``tar -xvf spelbolagservice.tgz``

``node dist/app.js``
