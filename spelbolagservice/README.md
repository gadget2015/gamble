# Spelbolag tjänsten
Har några CRUD operationer för att kunna spara och uppdatera TODO's.


# Bygga
Tjänsten är en REST-tjänst som körs i en Express server med Node.js version 12.

> npm start

http://localhost:4001/api/v1/spelbolag/5

# Testning
* Använder Jest, https://basarat.gitbooks.io/typescript/content/docs/testing/jest.html
* Mockar Request och Response med https://www.npmjs.com/package/node-mocks-http.
* npm i jest @types/jest ts-jest -D
* Utför tester med en uppstartad MySQL databas, Det behöver finnas lite data i databasen.

``INSERT INTO `stryktipsbolag`.`transaktion` (`ID`,`BESKRIVNING`,`DEBIT`,`KREDIT`,`TID`) VALUES (1, 'Spelar stryktipset', 0, 50,CURRENT_TIMESTAMP); ``


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
  `BESKRIVNING` varchar(255) DEFAULT NULL,
  `DEBIT` int(11) DEFAULT NULL,
  `KREDIT` int(11) DEFAULT NULL,
  `TID` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
</pre>


# Deploy
``cd /var/local/spelbolagservice``

``tar -xvf spelbolagservice.tgz``

``node dist/app.js``
