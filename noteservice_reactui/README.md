# GUI för Notes utvecklat i React.js.
This project was bootstrapped with [Create React App](https://github.com/facebook/create-react-app).


# Beroenden
Text editor, https://www.npmjs.com/package/react-quill

# Bygga för AWS
`` npm run build ``
 
``npm run bundle``

# Deploy
Deploy sker i katalogen noterepo, vilket kräver konfig (https://skryvets.com/blog/2018/09/20/an-elegant-solution-of-deploying-react-app-into-a-subdirectory/).
``cd /var/local/webapps/noterepo``

``tar xvf noteservice_ui.tgz``

Restart Linux system or run:

``node build/httpserver``

# Uppgradera
Till CRA version 4: https://medium.com/better-programming/upgrade-create-react-app-based-projects-to-version-4-cra-4-d7962aee11a6
