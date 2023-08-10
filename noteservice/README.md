# Noteservice tjänsten
Har några CRUD operationer för att kunna spara och uppdatera TODO's.

Källa: https://github.com/ggadget2015/gamble/tree/master/noteservice

# Bygga
Tjänsten är en REST-tjänst som körs i en Express server med Node.js version 12.

> npm start

http://localhost:4000/api/v1/notes/5

# Testning
* Använder Jest, https://basarat.gitbooks.io/typescript/content/docs/testing/jest.html
* Mockar Request och Response med https://www.npmjs.com/package/node-mocks-http.
* npm i jest @types/jest ts-jest -D
* Utför tester med en uppstartad MySQL databas, Förutsätter att det finns en Note/TODO med Id: 5 och 6.
`INSERT INTO noterepo.note (ID, ADMINUSERID, PRIVATEACCESS, TEXT, LASTSAVED) VALUES (5,'robert.georen@gmail.com',1,'TODO',CURRENT_TIMESTAMP); 
INSERT INTO noterepo.note (ID, ADMINUSERID, PRIVATEACCESS, TEXT, LASTSAVED) VALUES (6,'robert.georen@gmail.com',1,'TODO',CURRENT_TIMESTAMP);`

Starta testning med .
> npm test


Prestandatest:
for ((i=1;i<=100;i++)); do   curl http://localhost:4000/api/v1/notes/67; done


# Databas
MySQL 5.7.26 används.  
Den startas med
> mysqld --console.  
> mysql -u root -p  

SQLDeveloper används som GUI mot databasen. 

Skapa en databas som heter noterepo.

Tjänsten består av en tabell, note.  
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


# Deploy
1. Bygg > npm start
2. bundle > npm run bundle
``cd /var/local/noteservice

``tar -xvf noteservice.tgz``



Loggfil: /var/local/noteservice/noteservice.log