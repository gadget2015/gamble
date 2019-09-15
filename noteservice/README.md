# Noteservice tjänsten
Har några CRUD operationer för att kunna spara och uppdatera TODO's.

# Bygga
Tjänsten är en REST-tjänst som körs i en Express server med Node.js.

> npm start

# Testning
* Använder Jest, https://basarat.gitbooks.io/typescript/content/docs/testing/jest.html
* Mockar Request och Response med https://www.npmjs.com/package/node-mocks-http.
* npm i jest @types/jest ts-jest -D
* Utför tester med en uppstartad MySQL databas, > mysqld --console. Förutsätter att det finns en Note/TODO med Id: 5.

> npm test


