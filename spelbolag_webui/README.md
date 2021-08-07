## Website för www.stryktipsbolag.se



Använder spelbolagservice.

Använder UI komponenter ifrån https://material-ui.com/.

Verifiera id_token med tjänsten:
https://oauth2.googleapis.com/tokeninfo?id_token=eyJhbGci

Se dokumentation: https://developers.google.com/identity/sign-in/web/reference#gapiauth2authresponse

Google Developer console - ställ in Oauth2:
https://console.developers.google.com/apis/dashboard?project=react-ui-spelbolagtest&hl=sv

### `npm start`

Runs the app in the development mode.<br />
Open [http://localhost:3000](http://localhost:3000) to view it in the browser.

The page will reload if you make edits.<br />
You will also see any lint errors in the console.

### `npm test`

Launches the test runner in the interactive watch mode.<br />
See the section about [running tests](https://facebook.github.io/create-react-app/docs/running-tests) for more information.

### `npm run build`

Builds the app for production to the `build` folder.<br />
It correctly bundles React in production mode and optimizes the build for the best performance.

The build is minified and the filenames include the hashes.<br />
Your app is ready to be deployed!

See the section about [deployment](https://facebook.github.io/create-react-app/docs/deployment) for more information.

### Deploy

1. Bygg gamble Swing GUI med Java8, gå till gamble katalogen och skriv > mvn install
2. Signera filerna: signjars.cmd
3. Kopiera över Swing GUI applikationern {tips-1.0.jar, xercesImpl-2.6.2.jar, xmlParserAPIs-2.0.0.jar} 
   till folder ./public
4. Bygg först > npm run build
5. Skapa bundle > npm run bundle
6. kopiera över (/home/ec2-user/) till Amazon EC2 i foldern:  /var/local/webapps/spelbolag/
7. tar xvf spelbolag_ui.tgz

Driftsätt i en subfolder: /spelbolag/
https://skryvets.com/blog/2018/09/20/an-elegant-solution-of-deploying-react-app-into-a-subdirectory/

