# Noteservice tjänsten
Har några CRUD operationer för att kunna spara och uppdatera TODO's.

Källa: https://github.com/ggadget2015/gamble/tree/master/noteservice

# Bygga
Tjänsten är en REST-tjänst som körs i en Express server med Node.js version 12.

> npm start

http://localhost:3000/api/v1/notes/5

# Testning
* Använder Jest, https://basarat.gitbooks.io/typescript/content/docs/testing/jest.html
* Mockar Request och Response med https://www.npmjs.com/package/node-mocks-http.
* npm i jest @types/jest ts-jest -D
* Utför tester med en uppstartad MySQL databas, Förutsätter att det finns en Note/TODO med Id: 5.
`INSERT INTO noterepo.note (ID, ADMINUSERID, PRIVATEACCESS, TEXT, LASTSAVED) VALUES (67,'robert.georen@gmail.com',1,'TODO',CURRENT_TIMESTAMP);`

Starta testning med .
> npm test
>
# Databas
MySQL 5.7.26 används.  
Den startas med
> mysqld --console.  
> mysql -u root -p  

MySQL Workbench 8.0 används som GUI mot databasen. 

Tjänsten består av två tabeller, note och sequence.  
<pre>
  CREATE TABLE `note` (  
  `ID` bigint(20) NOT NULL,  
  `ADMINUSERID` varchar(255) DEFAULT NULL,  
  `PRIVATEACCESS` smallint(6) DEFAULT NULL,  
  `TEXT` varchar(4000) DEFAULT NULL,  
  `LASTSAVED` timestamp NULL DEFAULT NULL,  
  PRIMARY KEY (`ID`)   
) ENGINE=InnoDB DEFAULT CHARSET=latin1;  
</pre>

<pre>
CREATE TABLE `sequence` (
  `SEQ_NAME` varchar(40) NOT NULL,
  `SEQ_COUNT` decimal(10,0) DEFAULT NULL,
  PRIMARY KEY (`SEQ_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
</pre>